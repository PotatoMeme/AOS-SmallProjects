package com.potatomeme.sample_clipboard_webview.data.serializer

import androidx.datastore.core.Serializer
import com.potatomeme.sample_clipboard_webview.Clipboards
import java.io.InputStream
import java.io.OutputStream

object ClipboardsSerializer : Serializer<Clipboards> {
    override val defaultValue: Clipboards
        get() = Clipboards.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Clipboards {
        try {
            return Clipboards.parseFrom(input)
        } catch (exception: Exception) {
            throw RuntimeException("Error reading user from input stream", exception)
        }
    }

    override suspend fun writeTo(t: Clipboards, output: OutputStream) {
        return t.writeTo(output)
    }
}
