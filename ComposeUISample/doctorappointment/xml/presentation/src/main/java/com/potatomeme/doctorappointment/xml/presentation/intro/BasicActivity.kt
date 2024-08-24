package com.potatomeme.doctorappointment.xml.presentation.intro

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )//status bar와 navigation bar를 둘다 투명으로 바꾸는 방법
    }
}