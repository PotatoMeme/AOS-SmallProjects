package com.potatomeme.sample_stopwatch.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.potatomeme.sample_stopwatch.data.DataStoreSource
import com.potatomeme.sample_stopwatch.data.StopWatchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StopwatchReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return

        val dataStore = DataStoreSource.getInstance(context)
        CoroutineScope(Dispatchers.IO).launch {
            when (action) {
                "ACTION_PLAY" -> dataStore.stopWatchStateChanged(StopWatchState.Play)
                "ACTION_PAUSE" -> dataStore.stopWatchStateChanged(StopWatchState.Pause)
                "ACTION_STOP" -> dataStore.stopWatchStateChanged(StopWatchState.Stop)
            }
        }
    }
}