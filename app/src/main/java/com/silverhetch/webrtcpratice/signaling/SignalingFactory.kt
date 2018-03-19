package com.silverhetch.webrtcpratice.signaling

import okhttp3.*
import org.json.JSONObject

/**
 * Created by mikes on 3/19/2018.
 */
class SignalingFactory {

    fun singling(): Signaling {
        return Signaling("ws://192.168.1.96:9090")
    }
}