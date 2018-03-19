package com.silverhetch.webrtcpratice.signaling

import okhttp3.*
import org.json.JSONObject


/**
 * Created by mikes on 3/19/2018.
 */

class Signaling(private val uri: String) : WebSocketListener() {
    private val client: OkHttpClient = OkHttpClient()
    private var socket: WebSocket? = null

    fun connect() {
        socket = client.newWebSocket(Request.Builder().url(uri).build(), this)
        client.dispatcher().executorService().shutdown()
    }

    fun message(message: String) {
        socket!!.send(message)
    }
}
