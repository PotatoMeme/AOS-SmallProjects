package com.potatomeme.sample_workmanager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViewById<Button>(R.id.button1).setOnClickListener {
            viewModel.enqueueWorker()
        }

        bindWorkInfo()
    }

    private fun bindWorkInfo(){
        lifecycleScope.launch {
            viewModel.outputWorkInfo.collect{ workInfos : List<WorkInfo> ->
                Log.e(TAG, "updated Work Info Size : ${workInfos.size}")
                if (workInfos.isNotEmpty()){
                    Log.e(TAG, "updated Work Info State : ${workInfos[0].state}")
                    if (workInfos[0].state == WorkInfo.State.SUCCEEDED){
                        workInfos[0].outputData.keyValueMap.forEach { (key,value) ->
                            Log.e(TAG, "updated Work Info Out Put $key $value")
                        }
                    }
                }
            }
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}