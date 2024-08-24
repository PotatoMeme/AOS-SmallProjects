package com.potatomeme.doctorappointment.xml.presentation.topdoctors

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatomeme.doctorappointment.xml.presentation.databinding.ActivityTopDoctorsBinding
import com.potatomeme.doctorappointment.xml.presentation.detail.DetailActivity
import com.potatomeme.doctorappointment.xml.presentation.domain.model.DoctorsModel
import com.potatomeme.doctorappointment.xml.presentation.main.MainViewModel

class TopDoctorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopDoctorsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTopDoctors()
    }

    private fun initTopDoctors() {
        binding.apply {
            progressBarTopDoctor.visibility = View.VISIBLE
            viewModel.doctors.observe(this@TopDoctorsActivity) {
                viewTopDoctorList.layoutManager = LinearLayoutManager(
                    this@TopDoctorsActivity, LinearLayoutManager.VERTICAL, false
                )
                viewTopDoctorList.adapter = TopDoctorAdapter(it) { item: DoctorsModel ->
                    val intent = Intent(this@TopDoctorsActivity, DetailActivity::class.java)
                    intent.putExtra("object", item)
                    startActivity(intent)
                }
                progressBarTopDoctor.visibility = View.GONE
            }
            viewModel.loadDoctors()

            backBtn.setOnClickListener { finish() }
        }
    }
}