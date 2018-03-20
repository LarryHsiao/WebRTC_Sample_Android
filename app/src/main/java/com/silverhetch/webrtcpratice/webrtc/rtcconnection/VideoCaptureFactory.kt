package com.silverhetch.webrtcpratice.webrtc.rtcconnection

import org.webrtc.Camera1Enumerator
import org.webrtc.VideoCapturer

/**
 * Created by mikes on 3/21/2018.
 */
internal class VideoCaptureFactory {
    fun instance(): VideoCapturer {
        val camera = Camera1Enumerator(false) // set false, otherwise not frame shows up.
        for (deviceName in camera.deviceNames) {
            if (camera.isFrontFacing(deviceName)) {
                return camera.createCapturer(deviceName, null)!!
            }
        }
        throw RuntimeException("Video capture creation failed.")
    }
}