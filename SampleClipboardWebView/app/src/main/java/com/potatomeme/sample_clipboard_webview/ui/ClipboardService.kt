package com.potatomeme.sample_clipboard_webview.ui

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log


class ClipboardService : Service(), ClipboardManager.OnPrimaryClipChangedListener {
    private var mManager: ClipboardManager? = null
    override fun onCreate() {
        super.onCreate()
        mManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        // 리스너 등록
        mManager?.addPrimaryClipChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 리스너 해제
        mManager?.removePrimaryClipChangedListener(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onPrimaryClipChanged() {
        if (mManager != null && mManager?.primaryClip != null) {
            val data: ClipData? = mManager?.primaryClip

            // 한번의 복사로 복수 데이터를 넣었을 수 있으므로, 모든 데이터를 가져온다.
            val dataCount = data?.itemCount
            for (i in 0 until dataCount!!) {
                Log.e("Test", "clip data - item : " + data.getItemAt(i).coerceToText(this))
            }
        } else {
            Log.e("Test", "No Manager or No Clip data")
        }
    }
}