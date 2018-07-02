package com.silverhetch.webrtcpratice.webrtc.rtcconnection

import org.webrtc.VideoRenderer

/**
 * Created by mikes on 3/21/2018.
 */
interface Renderer {
    fun localVideo(): VideoRenderer
    fun remoteVideo(): VideoRenderer
}