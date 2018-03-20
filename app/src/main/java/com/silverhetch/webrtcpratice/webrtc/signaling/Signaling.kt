package com.silverhetch.webrtcpratice.webrtc.signaling

import org.json.JSONObject

/**
 * Created by mikes on 3/20/2018.
 */
interface Signaling {
    fun connect(callback: Callback)
    fun enter(name: String)
    fun offer(name: String, jsonSdp: JsonSdp)
    fun answer(offerName: String, jsonSdp: JsonSdp)
    fun candidate(name: String, candidate: JSONObject)
    fun leave()
    fun disconnect()

    interface Callback {
        fun onOffer(offerName: String, sdp: String)
        fun onAnswer(sdp: String)
        fun onCandidate(candidate: JSONObject)
    }
}