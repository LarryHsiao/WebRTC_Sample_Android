package com.silverhetch.webrtcpratice.webrtc.rtcconnection

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

/**
 * Created by mikes on 3/20/2018.
 */
internal class CreateStreamSdpObserver(private val callback: Callback) : SdpObserver {
    interface Callback {
        fun onSuccess(localSdp: SessionDescription)
    }

    override fun onSetFailure(exception: String?) {
        throw RuntimeException(exception)
    }

    override fun onSetSuccess() {
    }

    override fun onCreateSuccess(localSessionDescription: SessionDescription?) {
        callback.onSuccess(localSessionDescription!!)
    }

    override fun onCreateFailure(exception: String?) {
        throw RuntimeException(exception)
    }
}