package com.silverhetch.webrtcpratice.signaling

import com.silverhetch.webrtcpratice.User.User
import okio.ByteString
import org.json.JSONObject

/**
 * Created by mikes on 3/20/2018.
 */
internal class SignalingImpl(private val user: User) : Signaling, SignalSocket.Callback {
    private val socket: SignalSocket = SignalSocket("ws://192.168.1.96:9090", this)

    override fun connect() {
        socket.connect()
    }

    override fun enter(name: String) {
        val request = JSONObject()
        request.put("type", "login")
        request.put("name", name)
        socket.message(request.toString())
    }

    override fun onMessage(bytes: ByteString?) {
        val json = JSONObject(bytes.toString())
        when (json.getString("type")) {
            "login" -> {
                if (json.getBoolean("success")) {
                    user.userSignedIn(json.getString("name"))
                }
            }
            else -> {
            }
        }
    }

    override fun leave() {
        val request = JSONObject()
        request.put("type", "leave")
        request.put("name", user.userName())
        request.put("otherName", "")
        socket.message(request.toString())
    }
}