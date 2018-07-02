package com.silverhetch.webrtcpratice.webrtc.rtcconnection

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

/**
 * Used in stup session description.
 * Ignore all success event which is not needed
 * Created by mikes on 3/20/2018.
 */
internal class SdpSetupObserver : SdpObserver {
    override fun onSetFailure(exception: String?) {
        throw RuntimeException(exception)
    }

    override fun onSetSuccess() {/*ignore*/
    }

    override fun onCreateSuccess(localSessionDescription: SessionDescription?) {/*ignore*/
    }

    override fun onCreateFailure(exception: String?) {
        throw RuntimeException(exception)
    }
}