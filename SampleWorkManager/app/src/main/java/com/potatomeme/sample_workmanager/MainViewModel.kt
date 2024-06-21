package com.potatomeme.sample_workmanager

import androidx.lifecycle.ViewModel
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.workDataOf
import com.potatomeme.sample_workmanager.worker.BasicWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val workManager = WorkManager.getInstance(MyApplication.getApplicationContext())

    //internal val outputWorkInfo: Flow<List<WorkInfo>> = workManager.getWorkInfosByTagFlow(WORK_TAG)

    internal val outputWorkInfo: Flow<List<WorkInfo>> = workManager.getWorkInfosFlow(
        WorkQuery.Builder
            .fromTags(listOf(WORK_TAG))
            //.addStates(listOf(WorkInfo.State.FAILED, WorkInfo.State.CANCELLED))
            .addUniqueWorkNames(listOf(WORK_NAME))
            .build()
    )

    fun enqueueWorker() {
        val request = buildOnTimeWorkRequest(BasicWorker::class.java)
        workManager.enqueueRequest(request)
    }

    fun cancelWorker(){
        workManager.cancelUniqueWork(WORK_NAME)
    }

    /**
     * OneTimeWorkRequest : 반복되지 않는 작업을 생성
     * */
    private fun buildOnTimeWorkRequest(workerClass: Class<out ListenableWorker>): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(workerClass)
            .addTag(WORK_TAG)
            .setInputData(workDataOf("sample_key" to "sample_value"))
            //.setConstraints(buildConstraints())
            .build()
    }

    /**
     * PeriodicWorkRequest : 일정 간격으로 반복되는 작업
     */
    private fun buildPeriodicWorkRequest(workerClass: Class<out ListenableWorker>): PeriodicWorkRequest {
        // workerClass - The ListenableWorker class to run for this work
        // repeatInterval - The repeat interval in repeatIntervalTimeUnit units
        // repeatIntervalTimeUnit - The TimeUnit for repeatInterval

        //The interval duration for a PeriodicWorkRequest must be at least 15 minutes.
        return PeriodicWorkRequest
            .Builder(
                workerClass,
                1, TimeUnit.HOURS, // 작업 실행 주기와 시간 단위
                15, TimeUnit.MINUTES // 가변 주기와 시간 단위
            )//.setConstraints(buildConstraints())
            .build()
    }

    /**
     * Constraints : 작업을 실행하기 위해 충족시켜야 하는 제약 조건을 설정
     */
    private fun buildConstraints(): Constraints {
        //일부 장치는 충전과 유휴 상태를 동시에 고려하지 않습니다. 이러한 제약 조건 중 하나를 제거하는 것이 좋습니다.
        return Constraints.Builder()
            // NetworkType : 작업 실행 시 필요한 네트워크 유형을 제한
            .setRequiredNetworkType(NetworkType.CONNECTED)
            // BatteryNotLow : true로 설정하면 배터리 부족 상태에서 작업이 실행되지 않음
            .setRequiresBatteryNotLow(true)
            // RequiresCharging : true로 설정하면 충전 중이어야 작업이 실행
            .setRequiresCharging(true)
            // DeviceIdle : true로 설정하면 디바이스가 유휴 상태이어야 작업이 실행
            .setRequiresDeviceIdle(true)
            // StorageNotLow : true로 설정하면 저장공간이 부족하지 않아야 작업이 실행
            .setRequiresStorageNotLow(true)
            .build()
    }

    /**
     * Request 빌드전 상세상항 추가입력
     */
    private fun OneTimeWorkRequest.Builder.setRequestDetails(): OneTimeWorkRequest.Builder {
        return this
            // 작업 딜레이
            .setInitialDelay(10, TimeUnit.MINUTES)
            // 백오프 정책
            // 백오프 딜레이 : 작업을 처음으로 재시도하기 전 대기해야 하는 시간
            // 백오프 정책 : 작업 재시도 횟수가 증가함에 따라 백오프 딜레이가 증가하는 방식 ( BackoffPolicy.LINEAR, BackoffPolicy.EXPONENTIAL
            .setBackoffCriteria(
                // 백오프 정책: 선형
                BackoffPolicy.LINEAR,
                // 백오프 딜레이: 10초 (단위 고려 필요)
                1000,
                // 시간 단위: ms
                TimeUnit.MILLISECONDS
            )
            // 태그 추가
            .addTag("Sample Tag")
            // 입력 데이터
            .setInputData(workDataOf("IMAGE_URI" to "http://...", Pair("SampleNum", 30)))
    }

    private fun WorkManager.enqueueRequest(request: OneTimeWorkRequest) {
        this.enqueueUniqueWork(
            WORK_NAME,
            // ExistingWorkPolicy : (일회성 작업) 같은 고유 이름을 가진 작업과 충돌할 경우 동작 정책을 나타내는 enum 값
            // REPLACE ( 기존 작업을 취소 새 작업 대체 )
            // KEEP ( 새 작업을 무시 기존 작업 유지 )
            // APPEND ( 새 작업을 기존 작업의 끝에 추가, 기존 작업이 취소 실패시 새 작업도 취소 실패 )
            // APPEND_OR_REPLACE ( 새 작업을 기존 작업의 끝에 추가, 기존 작업이 취소 실패시 새 작업은 실행 )
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    private fun WorkManager.enqueueRequest(request: PeriodicWorkRequest) {
        this.enqueueUniquePeriodicWork(
            WORK_NAME,
            // ExistingPeriodicWorkPolicy : (주기성 작업) 같은 고유 이름을 가진 작업과 충돌할 경우 동작 정책을 나타내는 enum 값
            // CANCEL_AND_REENQUEUE ( 기존 작업을 취소 새 작업 대체 )
            // KEEP ( 새 작업을 무시 기존 작업 유지 )
            // UPDATE ( 기존 작업을 취소하지 않고 다음 실행 시 새 설정으로 업데이트 )
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    companion object {
        private const val WORK_NAME = "unique WorkName"
        private const val WORK_TAG = "unique WorkTag"
    }

}