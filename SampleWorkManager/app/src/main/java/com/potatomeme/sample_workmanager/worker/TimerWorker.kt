package com.potatomeme.sample_workmanager.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.potatomeme.sample_workmanager.NotificationUtil
import com.potatomeme.sample_workmanager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class TimerWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private var isPause: Boolean = false
    override suspend fun doWork(): Result {
        val duration = inputData.getLong("duration", 0L)

        val job = CoroutineScope(Dispatchers.IO).async {
            var i = 0L
            while (true){
                if (isPause) continue
                delay(1000L)
                sendNotification(++i)
            }

            sendNotification(0, true)
        }

        job.await()

        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, "timer_channel")
            .setContentTitle("Timer Running")
            .setContentText("Timer is running")
            .setSmallIcon(R.drawable.ic_timer)
            .build()

        return ForegroundInfo(1, notification)
    }

    private fun sendNotification(
        time: Long,
        isFinished: Boolean = false,
    ) {
        val contentText = if (isFinished) "타이머가 종료되었습니다." else "타이머 시간: $time 초"
        NotificationUtil.createNotification(applicationContext, contentText)
    }
}