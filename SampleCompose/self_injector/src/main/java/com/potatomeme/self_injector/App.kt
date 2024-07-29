package com.potatomeme.self_injector

import android.app.Application

class App : Application() {
    val appContainer:AppContainer = AppContainer(context = this)
}