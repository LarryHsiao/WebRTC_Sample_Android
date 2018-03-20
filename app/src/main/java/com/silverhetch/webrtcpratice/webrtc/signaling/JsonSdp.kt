package com.silverhetch.webrtcpratice.webrtc.signaling

import org.json.JSONObject
import org.webrtc.SessionDescription
import java.util.*

/**
 * Created by mikes on 3/21/2018.
 */
class JsonSdp(private val description: SessionDescription) {
    fun value(): JSONObject {
        val json = JSONObject()
        json.put("type", description.type.name.toLowerCase(Locale.US))
        json.put("sdp", description.description)
        return json
    }
}