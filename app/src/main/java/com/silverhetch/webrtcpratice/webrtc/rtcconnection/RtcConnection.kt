package com.silverhetch.webrtcpratice.webrtc.rtcconnection

import com.silverhetch.webrtcpratice.webrtc.signaling.Signaling

/**
 * Created by mikes on 3/20/2018.
 */
interface RtcConnection : Signaling.Callback {
    fun start(signaling: Signaling, renderer: Renderer)
    fun call(name: String)
    fun stop()
}