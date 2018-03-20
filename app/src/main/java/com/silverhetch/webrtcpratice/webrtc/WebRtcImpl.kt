package com.silverhetch.webrtcpratice.webrtc

import com.silverhetch.webrtcpratice.User.User
import com.silverhetch.webrtcpratice.webrtc.rtcconnection.Renderer
import com.silverhetch.webrtcpratice.webrtc.rtcconnection.RtcConnection
import com.silverhetch.webrtcpratice.webrtc.rtcconnection.RtcConnectionImpl
import com.silverhetch.webrtcpratice.webrtc.signaling.Signaling
import com.silverhetch.webrtcpratice.webrtc.signaling.SignalingImpl

/**
 * Created by mikes on 3/20/2018.
 */
internal class WebRtcImpl(private val user: User) : WebRtc {
    private val signaling: Signaling = SignalingImpl(user)
    private val rtcConnection: RtcConnection = RtcConnectionImpl()
    override fun start(renderer: Renderer) {
        signaling.connect(rtcConnection)
        rtcConnection.start(signaling, renderer)
    }

    override fun stop() {
        signaling.disconnect()
        rtcConnection.stop()
    }

    override fun login(name: String) {
        signaling.enter(name)
    }

    override fun call(name: String) {
        rtcConnection.call(name)
    }
}