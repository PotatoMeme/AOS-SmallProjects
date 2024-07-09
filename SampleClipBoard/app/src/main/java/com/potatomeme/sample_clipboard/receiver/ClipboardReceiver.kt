package com.potatomeme.sample_clipboard.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepository
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepositoryImpl
import com.potatomeme.sample_clipboard.data.source.ClipboardDataStoreSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClipboardReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: 수신 ${intent.action}")
        val action = intent.action ?: return

        val repository: ClipboardRepository =
            ClipboardDataStoreSource.getInstance(context).let { dataStore ->
                ClipboardRepositoryImpl.getInstance(dataStore)
            }

        CoroutineScope(Dispatchers.IO).launch {
            when (action) {
                ACTION_PLAY -> repository.stopWatchStateChanged(StopWatchState.Play)
                ACTION_PAUSE -> repository.stopWatchStateChanged(StopWatchState.Pause)
                ACTION_STOP -> repository.stopWatchStateChanged(StopWatchState.Stop)
            }
        }
    }

    companion object {
        private const val TAG = "ClipboardReceiver"
        val ACTION_PLAY = "ACTION_PLAY"
        val ACTION_PAUSE = "ACTION_PAUSE"
        val ACTION_STOP = "ACTION_STOP"
    }
}