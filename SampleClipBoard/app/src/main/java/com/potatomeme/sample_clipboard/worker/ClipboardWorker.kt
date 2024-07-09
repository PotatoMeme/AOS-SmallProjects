package com.potatomeme.sample_clipboard.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.potatomeme.sample_clipboard.R
import com.potatomeme.sample_clipboard.data.model.ClipboardState
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepository
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepositoryImpl
import com.potatomeme.sample_clipboard.data.source.ClipboardDataStoreSource
import com.potatomeme.sample_clipboard.receiver.ClipboardReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClipboardWorker(
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    private val dataStoreSource: ClipboardDataStoreSource =
        ClipboardDataStoreSource.getInstance(appContext)
    private val repository: ClipboardRepository =
        ClipboardRepositoryImpl.getInstance(dataStoreSource)

    private val notificationManager: NotificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val clipboardManager: ClipboardManager =
        appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    private val clipboardFlow: StateFlow<List<ClipboardState>> =
        repository.getClipboardsFlow().stateIn(
            CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = listOf()
        )

    private val countFlow: Flow<Long> = repository.getStopWatchCountFlow()
    private val stateFlow: Flow<StopWatchState> = repository.getStopWatchStateFlow()

    private val combinedFlow: StateFlow<Pair<Long, StopWatchState>> =
        countFlow.combine(stateFlow) { time, stopWatchState ->
            Pair(time / 1000, stopWatchState)
        }.stateIn(
            CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = Pair(0L, StopWatchState.Play)
        )

    override suspend fun doWork(): Result = coroutineScope {
        setForeground(createForegroundInfo())


        val job = launch {
            repository.stopWatchStateChanged(StopWatchState.Play)
            var isRunning = false
            var pastTime = System.currentTimeMillis()

            //앱이 포커스 되어있지 않으면 되지 않음
            clipboardManager.addPrimaryClipChangedListener {
                val value = clipboardManager.primaryClip?.getItemAt(0)
                    ?.text
                    ?.toString()
                launch {
                    dataStoreSource.addClipBoard(
                        ClipboardState.TextClipBoard(
                            value = value ?: "null", "${combinedFlow.first().first} 초"
                        )
                    )
                }
            }
            launch {
                while (true) {
                    //Log.d(TAG, "${pastTime / 1000} ")
                    val currentTime = System.currentTimeMillis()
                    if (isRunning) {
                        val diffTime = currentTime - pastTime
                        repository.countStopWatch(diffTime)
                    }
                    pastTime = currentTime
                    delay(500)
                }
            }

            launch {
                combinedFlow.collectLatest { (count, state) ->
                    when (state) {
                        StopWatchState.Pause -> isRunning = false
                        StopWatchState.Play -> isRunning = true
                        StopWatchState.Stop -> {
                            isRunning = false
                            repository.refreshStopWatch()
                            stopSelf()
                        }
                    }

                    updateNotification(isRunning, "$count 초")
                }
            }
            launch {
                clipboardFlow.collectLatest { list ->
                    Log.d(TAG, "doWork: ClipBoardList Size : ${list.size}")
                    if (list.isNotEmpty()) {
                        Log.d(TAG, "doWork: ClipBoardList Current : ${list.last()}")
                    }

                }
            }
        }

        job.join()
        Result.success()
    }

    private fun stopSelf() {
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)
        intent.send()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setTicker(NOTIFICATION_TITLE)
            .setContentText("")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }

    private fun updateNotification(isRunning: Boolean, progress: String) {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setTicker(NOTIFICATION_TITLE)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .addAction(
                android.R.drawable.ic_delete,
                "Stop",
                getPendingIntent(applicationContext, ClipboardReceiver.ACTION_STOP)
            )
            .run {
                if (isRunning) {//현재 플레이중
                    this.addAction(
                        android.R.drawable.ic_media_pause,
                        "Pause",
                        getPendingIntent(applicationContext, ClipboardReceiver.ACTION_PAUSE)
                    )
                } else {//현재 일시정지중
                    this.addAction(
                        android.R.drawable.ic_media_play,
                        "Play",
                        getPendingIntent(applicationContext, ClipboardReceiver.ACTION_PLAY)
                    )
                }
            }
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun getPendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, ClipboardReceiver::class.java).apply {
            this.action = action
        }
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val notiChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Clipboard Channel"
            setShowBadge(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        notificationManager.createNotificationChannel(notiChannel)
    }

    companion object {
        private const val TAG = "ClipboardWorker"

        const val CHANNEL_ID = "CLIPBOARD_WORKER_CHANNEL_ID"
        const val CHANNEL_NAME = "CLIPBOARD_WORKER_CHANNEL_NAME"
        const val NOTIFICATION_ID = 13
        const val NOTIFICATION_TITLE = "Clipboard Worker Notification"
    }
}