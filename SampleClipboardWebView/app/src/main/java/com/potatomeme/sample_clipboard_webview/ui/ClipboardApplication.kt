package com.potatomeme.sample_clipboard_webview.ui

import android.app.Application

class ClipboardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object{
        private var application: Application? = null

        fun getApplication() : Application{
            when(application){
                null -> throw Exception("Application 이 null 값 입니다.")
                else -> return application!!
            }
        }
    }
}