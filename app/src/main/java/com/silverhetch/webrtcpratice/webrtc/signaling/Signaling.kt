package com.silverhetch.webrtcpratice.webrtc.signaling

import com.silverhetch.webrtcpratice.webrtc.rtcconnection.RemotePeer
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

/**
 * Created by mikes on 3/20/2018.
 */
interface Signaling {
    fun connect(callback: Callback)
    fun enter(name: String)
    fun answer(remotePeer: RemotePeer, localDescription: SessionDescription)
    fun candidate(remotePeer: RemotePeer, candidate: IceCandidate?)
    fun leave()
    fun disconnect()
    fun offer(remotePeer: RemotePeer, localDescription: SessionDescription)

    interface Callback {
        fun onOffer(remotePeer: RemotePeer, remoteSdp: String)
        fun onAnswer(sdp: String)
        fun onCandidate(sdpMid: String, sdpMLineIndex: Int, candidate: String)
    }
}