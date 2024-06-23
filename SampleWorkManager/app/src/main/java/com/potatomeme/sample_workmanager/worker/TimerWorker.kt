package com.potatomeme.sample_workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.potatomeme.sample_workmanager.NotificationUtil
import kotlinx.coroutines.delay

class TimerWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val duration = inputData.getLong("duration", 0L)

        for (i in duration downTo 1) {
            delay(1000L)
            sendNotification(i)
        }

        sendNotification(0, true)

        return Result.success()
    }

    private fun sendNotification(
        timeLeft: Long,
        isFinished: Boolean = false,
    ) {
        val contentText = if (isFinished) "타이머가 종료되었습니다." else "타이머 남은 시간: $timeLeft 초"
        NotificationUtil.createNotification(applicationContext,contentText)
    }
}