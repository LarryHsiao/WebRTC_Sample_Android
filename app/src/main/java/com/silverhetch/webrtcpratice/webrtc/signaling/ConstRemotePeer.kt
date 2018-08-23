package com.silverhetch.webrtcpratice.webrtc.signaling

import com.silverhetch.webrtcpratice.webrtc.rtcconnection.RemotePeer

class ConstRemotePeer(private val name:String) : RemotePeer {
    override fun info(): String {
        return name
    }
}