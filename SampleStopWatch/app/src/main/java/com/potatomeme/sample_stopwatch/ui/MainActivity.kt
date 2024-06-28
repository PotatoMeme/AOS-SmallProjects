package com.potatomeme.sample_stopwatch.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.potatomeme.sample_stopwatch.data.DataStoreSource
import com.potatomeme.sample_stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val permissionNotificationRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionRequest: Map<String, Boolean> ->
            when {
                permissionArray.all {
                    permissionRequest.getOrDefault(
                        it,
                        false
                    )
                } -> { // 모든 권한 O
                    Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> { // notification 권한 X
                    finish()
                }
            }

        }

    private val permissionArray = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(application)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permissionArray.all { ContextCompat.checkSelfPermission(
                this,
                it
            ) != PackageManager.PERMISSION_GRANTED }
        ) {
            permissionNotificationRequest.launch(permissionArray)
        } else {
            Toast.makeText(this,"All Permission Granted",Toast.LENGTH_SHORT).show()
        }

        binding.initViews()
    }

    private fun ActivityMainBinding.initViews() {
        setContentView(root)
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.viewModel = this@MainActivity.viewModel
        binding.lifecycleOwner = this@MainActivity
        lifecycleScope.launch {
            viewModel?.countFlow?.collect { count: Long ->
                binding.timeTextView.text = "${count / 1000}"
            }
        }
        lifecycleScope.launch {
            viewModel?.outputWorkerInfo?.collect { workInfos ->
                Log.d(TAG, "workInfoCollect: ${workInfos.size}")
                if (workInfos.isNotEmpty()){
                    Log.d(TAG, "workInfoCollect: currentState, ${workInfos[0].state}")
                }
            }
        }

        button2.setOnClickListener {
            viewModel!!.enqueueStopWatch()
        }

        /*button.setOnClickListener {
            viewModel!!.stopStopWatch()
        }
        button2.setOnClickListener {
            viewModel!!.enqueueStopWatch()
        }
        button3.setOnClickListener {
            viewModel!!.pauseStopWatch()
        }*/
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}