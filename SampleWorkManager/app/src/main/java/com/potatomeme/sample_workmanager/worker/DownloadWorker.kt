package com.potatomeme.sample_workmanager.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.potatomeme.sample_workmanager.R
import kotlinx.coroutines.delay

class DownloadWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())
        download()
        return Result.success()
    }

    private suspend fun download() {
        var i = 0L
        while (true){
            delay(1000L)
            updateNotification("${++i} 초")
        }
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setTicker(NOTIFICATION_TITLE)
            .setContentText("")
            .setSmallIcon(R.drawable.ic_timer)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, "cancel", intent)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }

    private fun updateNotification(progress: String) {
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        val notification = NotificationCompat.Builder(applicationContext, CHANEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setTicker(NOTIFICATION_TITLE)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_timer)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, "cancel", intent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val notiChannel = NotificationChannel(
            CHANEL_ID,
            CHANEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "TEST"
            setShowBadge(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        notificationManager.createNotificationChannel(notiChannel)
    }

    companion object {
        const val CHANEL_ID = "DOWN_LOAD_WORKER_CHANEL_ID"
        const val CHANEL_NAME = "DOWN_LOAD_WORKER_CHANEL_NAME"
        const val NOTIFICATION_ID = 10
        const val NOTIFICATION_TITLE = "DOWN_LOAD_WORKER_NOTIFICATION_TITLE"
    }
}