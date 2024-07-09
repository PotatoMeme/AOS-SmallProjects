package com.potatomeme.sample_clipboard.data.model

import com.potatomeme.sample_clipboard.Clipboard

sealed class ClipboardState {
    open val value: String = ""
    open val time: String = ""

    data class TextClipBoard(
        override val value: String,
        override val time: String,
    ) : ClipboardState()

    data class ImageClipBoard(
        override val value: String,
        override val time: String,
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
        ClipboardState.STATE_TEXT -> ClipboardState.TextClipBoard(value, time)
        ClipboardState.STATE_IMAGE -> ClipboardState.ImageClipBoard(value, time)
        else -> throw IllegalArgumentException("Clipboard wrong type, current value $type")
    }
}