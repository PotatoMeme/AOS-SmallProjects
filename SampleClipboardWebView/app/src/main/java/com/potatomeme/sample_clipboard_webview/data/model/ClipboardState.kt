package com.potatomeme.sample_clipboard_webview.data.model


import com.potatomeme.sample_clipboard_webview.Clipboard

sealed class ClipboardState {
    open val value: String = ""
    open val time: String = ""
    open val reference: String = ""

    data class TextClipBoard(
        override val value: String,
        override val time: String,
        override val reference: String,
    ) : ClipboardState()

    data class ImageClipBoard(
        override val value: String,
        override val time: String,
        override val reference: String,
    ) : ClipboardState()

    companion object {
        const val STATE_TEXT = 0
        const val STATE_IMAGE = 1
    }
}

fun ClipboardState.toDataStoreFormat(): Clipboard {
    val clipboard = Clipboard.newBuilder()
        .setValue(value)
        .setTime(time)
        .setReference(reference)
        .setType(
            when (this) {
                is ClipboardState.TextClipBoard -> ClipboardState.STATE_TEXT
                is ClipboardState.ImageClipBoard -> ClipboardState.STATE_IMAGE
            }
        )
        .build()

    return clipboard
}

fun Clipboard.toClipboardState(): ClipboardState {
    return when (this.type) {
        ClipboardState.STATE_TEXT -> ClipboardState.TextClipBoard(value, time, reference)
        ClipboardState.STATE_IMAGE -> ClipboardState.ImageClipBoard(value, time, reference)
        else -> throw IllegalArgumentException("Clipboard wrong type, current value $type")
    }
}