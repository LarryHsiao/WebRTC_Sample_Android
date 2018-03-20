package com.silverhetch.webrtcpratice.signaling

import com.silverhetch.webrtcpratice.User.User

/**
 * Created by mikes on 3/19/2018.
 */
class SignalingFactory(private val user: User) {

    fun singling(): Signaling {
        return SignalingImpl(user)
    }
}