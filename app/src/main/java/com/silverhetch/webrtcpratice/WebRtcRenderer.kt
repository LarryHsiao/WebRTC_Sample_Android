package com.silverhetch.webrtcpratice

import com.silverhetch.webrtcpratice.webrtc.rtcconnection.Renderer
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoRenderer

/**
 * Created by mikes on 3/21/2018.
 */
class WebRtcRenderer(private val localVideoSurface: SurfaceViewRenderer,
                     private val remoteVideoSurface: SurfaceViewRenderer) : Renderer {
    override fun localVideo(): VideoRenderer {
        return VideoRenderer(localVideoSurface)
    }

    override fun remoteVideo(): VideoRenderer {
        return VideoRenderer(remoteVideoSurface)
    }
}