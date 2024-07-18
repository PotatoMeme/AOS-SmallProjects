package com.potatomeme.sample_clipboard_webview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.potatomeme.sample_clipboard_webview.databinding.FragmentClipboardBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ClipboardFragment private constructor() : Fragment() {
    private var _binding: FragmentClipboardBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: MainViewModel by activityViewModels {
        MainViewModelFactory(application = ClipboardApplication.getApplication())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentClipboardBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewmodel.clipboardsFlow.collectLatest { list ->

                binding.tvClipboard?.text = buildString {
                    list.forEach {
                        appendLine(it)
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ClipboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}