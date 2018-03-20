package com.silverhetch.webrtcpratice.webrtc

import com.silverhetch.webrtcpratice.webrtc.rtcconnection.Renderer

/**
 * Created by mikes on 3/20/2018.
 */
interface WebRtc {
    fun start(renderer: Renderer)
    fun stop()
    fun login(name: String)
    fun call(name: String)
}