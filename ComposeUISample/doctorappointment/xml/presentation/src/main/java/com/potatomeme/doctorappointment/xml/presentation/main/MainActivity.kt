package com.potatomeme.doctorappointment.xml.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatomeme.doctorappointment.xml.presentation.databinding.ActivityMainBinding
import com.potatomeme.doctorappointment.xml.presentation.detail.DetailActivity
import com.potatomeme.doctorappointment.xml.presentation.domain.model.DoctorsModel
import com.potatomeme.doctorappointment.xml.presentation.topdoctors.TopDoctorsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategory()
        initTopDoctors()

    }

    private fun initTopDoctors() {
        binding.apply {
            progressBarTopDoctor.visibility = View.VISIBLE
            viewModel.doctors.observe(this@MainActivity) {
                recyclerViewTopDoctor.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewTopDoctor.adapter = MainTopDoctorAdapter(it) { item: DoctorsModel ->
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)

                    intent.putExtra("object", item)
                    startActivity(intent)
                }
                progressBarTopDoctor.visibility = View.GONE
            }
            viewModel.loadDoctors()

            doctorListTxt.setOnClickListener {
                startActivity(Intent(this@MainActivity, TopDoctorsActivity::class.java))
            }
        }
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this) {
            binding.viewCategory.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        }
        viewModel.loadCategory()
    }
}