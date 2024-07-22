package com.potatomeme.sample_clipboard_webview.ui

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.DownloadListener
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.potatomeme.sample_clipboard_webview.databinding.FragmentWebViewBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class WebViewFragment private constructor() : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(application = ClipboardApplication.getApplication())
    }

    private val mFormat: SimpleDateFormat = SimpleDateFormat("yyyy:MM:DD hh:mm")

    private val clipboardManager: ClipboardManager =
        ClipboardApplication.getApplication()
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    private var lastClipData: CharSequence? = clipboardManager.primaryClip?.getItemAt(0)?.text
    private val clipListener =
        ClipboardManager.OnPrimaryClipChangedListener {
            Log.d(TAG, "onPrimaryClipChanged")
            val currentClipText = clipboardManager.primaryClip?.getItemAt(0)?.text
            if (currentClipText != null && currentClipText != lastClipData) {
                Log.d(TAG, "value : $currentClipText")
                lastClipData = currentClipText

                lifecycleScope.launch {
                    viewModel.addClipboardTextData(
                        currentClipText.toString(),
                    )
                }
                Log.d(TAG, "value is changed")
            } else {
                Log.d(TAG, "No new clip data or duplicate event")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.apply {
                javaScriptCanOpenWindowsAutomatically = true
                loadsImagesAutomatically = true
                useWideViewPort = false
                defaultTextEncodingName = "UTF-8"
                userAgentString += "MYGOMII"
                allowFileAccess = true
                allowContentAccess = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                mediaPlaybackRequiresUserGesture = false
            }
            loadUrl("https://www.naver.com/")
        }
        clipboardManager.addPrimaryClipChangedListener(clipListener)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clipboardManager.removePrimaryClipChangedListener(clipListener)
        _binding = null
    }

    companion object {
        private const val TAG = "WebViewFragment"

        @JvmStatic
        fun newInstance() =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}