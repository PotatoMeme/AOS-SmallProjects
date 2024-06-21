package com.potatomeme.sample_workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class BasicWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.e(TAG, "Basic Worker Started")

        inputData.keyValueMap.forEach { (key, value) ->
            Log.e(TAG, "InputData $key:$value")
        }

        return Result.success(
            workDataOf("output_key" to "output_value")
        ) // 작업이 성공적으로 완료됨.
        //return Result.failure() // 작업 실패.
        //return Result.retry() // 작업이 실패했으며 재시도 정책에 따라 나중에 재시도해야함.
    }

    companion object {
        private const val TAG = "BasicWorker"
    }
}