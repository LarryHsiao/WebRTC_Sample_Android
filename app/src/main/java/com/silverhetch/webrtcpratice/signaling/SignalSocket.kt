package com.silverhetch.webrtcpratice.signaling

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString


/**
 * Created by mikes on 3/19/2018.
 */

internal class SignalSocket(private val uri: String, private val callback: Callback) : WebSocketListener() {
    interface Callback {
        fun onMessage(bytes: ByteString?)
    }

    private val client: OkHttpClient = OkHttpClient()
    private var socket: WebSocket? = null

    fun connect() {
        socket = client.newWebSocket(Request.Builder().url(uri).build(), this)
        client.dispatcher().executorService().shutdown()
    }

    fun message(message: String) {
        socket!!.send(message)
    }

    fun message(binary: ByteString) {
        socket!!.send(binary)
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
        super.onMessage(webSocket, bytes)
        callback.onMessage(bytes)
    }
}
