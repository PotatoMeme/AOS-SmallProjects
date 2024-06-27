package com.potatomeme.sample_stopwatch.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.potatomeme.sample_stopwatch.data.DataStoreSource
import com.potatomeme.sample_stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels {
        MainViewModelFactory(application)
    }
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.initViews()
    }

    private fun ActivityMainBinding.initViews(){
        setContentView(root)
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.viewModel = this@MainActivity.viewModel
        binding.lifecycleOwner = this@MainActivity
        lifecycleScope.launch {
            viewModel?.countFlow?.collect{ count:Long ->
                binding.timeTextView.text = "${count / 1000}"
            }
        }
        button.setOnClickListener {
            viewModel!!.stopStopWatch()
        }
        button2.setOnClickListener {
            viewModel!!.enqueueStopWatch()
        }
        button3.setOnClickListener {
            viewModel!!.pauseStopWatch()
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}