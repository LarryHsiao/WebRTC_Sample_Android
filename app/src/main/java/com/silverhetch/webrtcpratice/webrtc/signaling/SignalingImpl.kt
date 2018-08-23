package com.silverhetch.webrtcpratice.webrtc.signaling

import android.util.Log
import com.silverhetch.webrtcpratice.user.User
import com.silverhetch.webrtcpratice.webrtc.rtcconnection.RemotePeer
import org.json.JSONObject
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

/**
 * Created by mikes on 3/20/2018.
 */
internal class SignalingImpl(private val user: User) : Signaling, SignalSocket.Callback {
    private val socket: SignalSocket = SignalSocket("ws://172.104.79.181:9999", this)
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

    override fun offer(remotePeer: RemotePeer, localDescription: SessionDescription) {
        val request = JSONObject()
        request.put("type", "offer")
        request.put("name", remotePeer.info())
        request.put("sessionDescription", JsonSdp(localDescription).value())
        socket.message(request.toString())
    }

    override fun answer(remotePeer: RemotePeer, localDescription: SessionDescription) {
        val request = JSONObject()
        request.put("type", "answer")
        request.put("offerName", remotePeer.info())
        request.put("sessionDescription", JsonSdp(localDescription).value())
        socket.message(request.toString())
    }

    override fun candidate(remotePeer: RemotePeer, candidate: IceCandidate?) {
        candidate?.also {
            val candidateJson = JSONObject()
            candidateJson.put("sdpMid", candidate.sdpMid)
            candidateJson.put("sdpMLineIndex", candidate.sdpMLineIndex)
            candidateJson.put("candidate", candidate.sdp)
            val request = JSONObject()
            request.put("type", "candidate")
            request.put("name", remotePeer.info())
            request.put("candidate", candidateJson)
            socket.message(request.toString())
        }
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
                        ConstRemotePeer(json.getString("offerName")),
                        json.getJSONObject("sessionDescription").getString("sdp")
                )
            }
            "answer" -> { // answer from peer we offered
                callback!!.onAnswer(json.getJSONObject("sessionDescription").getString("sdp"))
            }
            "candidate" -> {
                json.getJSONObject("candidate").also {
                    callback!!.onCandidate(
                            it.getString("sdpMid"),
                            it.getInt("sdpMLineIndex"),
                            it.getString("candidate")
                    )
                }
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