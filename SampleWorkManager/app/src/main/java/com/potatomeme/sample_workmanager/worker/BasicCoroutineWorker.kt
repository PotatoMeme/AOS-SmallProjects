package com.potatomeme.sample_workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class BasicCoroutineWorker(context : Context, workerParams: WorkerParameters) : CoroutineWorker(context,workerParams) {
    override suspend fun doWork(): Result {

        return Result.success()
    }

    companion object{

    }
}