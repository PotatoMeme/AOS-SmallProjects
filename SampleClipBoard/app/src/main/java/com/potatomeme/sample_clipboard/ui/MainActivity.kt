package com.potatomeme.sample_clipboard.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.potatomeme.sample_clipboard.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val permissionArray = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )

    private val permissionNotificationRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionRequest: Map<String, Boolean> ->
            when {
                permissionArray.all {
                    permissionRequest.getOrDefault(
                        it,
                        false
                    )
                } -> { // 모든 권한 O
                    Log.d(TAG, "Notification Permission Granted")
                }

                else -> { // notification 권한 X
                    finish()
                }
            }

        }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(application)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //flow collect
        lifecycleScope.launch {
            launch {
                viewModel.clipboardsFlow.collect {
                    if (it.isNotEmpty()){
                        Log.d(TAG, "update ${it.last().value}")
                    }
                }
            }
            launch {
                viewModel.stateFlow.collect {}
            }
            launch {
                viewModel.countFlow.collect {count: Long ->
                    binding.tvTime.text = "${count / 1000}"
                }
            }
        }

        binding.btnPlayPause.setOnClickListener {
            viewModel.enqueueClipboardWorker()
        }
        binding.btnStop.setOnClickListener {
            viewModel.cancelWorker()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permissionArray.all { ContextCompat.checkSelfPermission(
                this,
                it
            ) != PackageManager.PERMISSION_GRANTED }
        ) {
            permissionNotificationRequest.launch(permissionArray)
        } else {
            Log.d(TAG, "onResume: All Permission Granted")
        }
    }
    companion object{
        private const val TAG = "MainActivity"
    }

}