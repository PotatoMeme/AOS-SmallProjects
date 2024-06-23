package com.potatomeme.sample_workmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val permissionNotificationRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionRequest: Map<String, Boolean> ->
            when {
                permissionRequest.getOrDefault(
                    Manifest.permission.POST_NOTIFICATIONS,
                    false
                ) -> { // notification 권한 O
                    Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> { // notification 권한 X
                    finish()
                }
            }

        }

    private val permissionArray = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionNotificationRequest.launch(permissionArray)
        } else {
            //NotificationUtil.createNotification(this, "SWM message", MainActivity::class.java)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViewById<Button>(R.id.button1).setOnClickListener {
            //viewModel.enqueueWorker()
            viewModel.enqueueTimerWorker()
        }

        bindWorkInfo()
    }

    private fun bindWorkInfo() {
        lifecycleScope.launch {
            viewModel.outputWorkInfo.collect { workInfos: List<WorkInfo> ->
                Log.e(TAG, "updated Work Info Size : ${workInfos.size}")
                if (workInfos.isNotEmpty()) {
                    Log.e(TAG, "updated Work Info State : ${workInfos[0].state}")
                    if (workInfos[0].state == WorkInfo.State.SUCCEEDED) {
                        workInfos[0].outputData.keyValueMap.forEach { (key, value) ->
                            Log.e(TAG, "updated Work Info Out Put $key $value")
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}