package com.silverhetch.webrtcpratice.webrtc.signaling

import android.util.Log
import com.silverhetch.webrtcpratice.user.User
import org.json.JSONObject

/**
 * Created by mikes on 3/20/2018.
 */
internal class SignalingImpl(private val user: User) : Signaling, SignalSocket.Callback {
    private val socket: SignalSocket = SignalSocket("ws://192.168.1.152:9090", this)
    private var callback: Signaling.Callback? = null

    override fun connect(callback: Signaling.Callback) {
        this.callback = callback
        socket.connect()
    }

    override fun disconnect() {
        socket.disconnect()
    }

    override fun enter(name: String) {
        val request = JSONObject()
        request.put("type", "login")
        request.put("name", name)
        socket.message(request.toString())
    }

    override fun offer(name: String, jsonSdp: JsonSdp) {
        val request = JSONObject()
        request.put("type", "offer")
        request.put("name", name)
        request.put("sessionDescription", jsonSdp.value())
        socket.message(request.toString())
    }

    override fun answer(offerName: String, jsonSdp: JsonSdp) {
        val request = JSONObject()
        request.put("type", "answer")
        request.put("offerName", offerName)
        request.put("sessionDescription", jsonSdp.value())
        socket.message(request.toString())
    }

    override fun candidate(name: String, candidate: JSONObject) {
        val request = JSONObject()
        request.put("type", "candidate")
        request.put("name", name)
        request.put("candidate", candidate)
        socket.message(request.toString())
    }

    override fun onMessage(body: String) {
        val json = JSONObject(body)
        when (json.getString("type")) {
            "login" -> {
                if (json.getBoolean("success")) {
                    user.userSignedIn(json.getString("name"))
                }
            }
            "connected" -> {
                Log.i("Signaling", "connected")
            }
            "leaved" -> {
                Log.i("Signaling", "leaved")
            }
            "offer" -> {
                callback!!.onOffer(
                        json.getString("offerName"),
                        json.getJSONObject("sessionDescription").getString("sdp")
                )
            }
            "answer" -> { // answer from peer we offered
                callback!!.onAnswer(json.getJSONObject("sessionDescription").getString("sdp"))
            }
            "candidate" -> {
                callback!!.onCandidate(json.getJSONObject("candidate"))
            }
            else -> {
                Log.i("Signaling", "unknown message")
            }
        }
    }

    override fun leave() {
        val request = JSONObject()
        request.put("type", "leave")
        request.put("name", user.userName())
        socket.message(request.toString())
    }
}