package com.potatomeme.hilt

import android.app.Application

class App : Application() {
    val appContainer: AppContainer = AppContainer(context = this)
}