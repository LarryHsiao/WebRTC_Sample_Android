package com.silverhetch.webrtcpratice.signaling

/**
 * Created by mikes on 3/20/2018.
 */
interface Signaling {
    fun connect()
    fun enter(name: String)
    fun leave()
}