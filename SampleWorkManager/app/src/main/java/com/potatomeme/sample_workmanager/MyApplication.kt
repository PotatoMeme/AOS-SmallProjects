package com.potatomeme.sample_workmanager

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    init{
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        NotificationUtil.createChannel(this)
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getApplicationContext() : Context {
            return instance.applicationContext
        }
    }
}