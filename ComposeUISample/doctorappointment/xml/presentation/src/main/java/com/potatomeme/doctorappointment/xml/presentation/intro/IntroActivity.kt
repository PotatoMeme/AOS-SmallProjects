package com.potatomeme.doctorappointment.xml.presentation.intro

import android.content.Intent
import android.os.Bundle
import com.potatomeme.doctorappointment.xml.presentation.databinding.ActivityIntroBinding
import com.potatomeme.doctorappointment.xml.presentation.main.MainActivity

class IntroActivity : BasicActivity() {
    private lateinit var binding : ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startBtn.setOnClickListener {
                startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}