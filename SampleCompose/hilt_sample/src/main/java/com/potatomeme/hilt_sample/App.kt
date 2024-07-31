package com.potatomeme.hilt_sample

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App :Application() {

    ////Inject : 의존성을 요청한다
    @Inject
    lateinit var myName : MyName
    override fun onCreate() {
        //Log.e(TAG, "test MyNameInstance1  $myName" )
        super.onCreate()//여기서 의존성 주입이 발생
        Log.e(TAG, "test MyNameInstance2  $myName" )
    }
    companion object{
        private const val TAG = "App"
    }
}