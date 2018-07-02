package com.silverhetch.webrtcpratice.user

/**
 * Created by mikes on 3/20/2018.
 */
interface User {
    fun userName(): String
    fun userSignedIn(userName: String): User
}