package com.silverhetch.webrtcpratice.webrtc.signaling

import okhttp3.*
import okio.ByteString


/**
 * Created by mikes on 3/19/2018.
 */

internal class SignalSocket(private val uri: String, private val callback: Callback) : WebSocketListener() {
    interface Callback {
        fun onMessage(body: String)
    }

    private val client: OkHttpClient = OkHttpClient()
    private var socket: WebSocket? = null

    fun connect() {
        socket = client.newWebSocket(Request.Builder().url(uri).build(), this)
        client.dispatcher().executorService()
    }

    fun disconnect() {
        socket!!.close(1000, null)// check the javaDoc of close() for parameters.
        socket = null
    }

    fun message(message: String) {
        socket!!.send(ByteString.encodeUtf8(message))
    }

    override fun onMessage(webSocket: WebSocket?, text: String?) {
        super.onMessage(webSocket, text)
        if (text == null) {
            return
        }
        callback.onMessage(text)
    }
}
