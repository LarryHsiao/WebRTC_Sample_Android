package com.silverhetch.webrtcpratice.User

/**
 * Created by mikes on 3/20/2018.
 */
internal class UserImpl : User {
    private var userName: String = ""
    override fun userSignedIn(userName: String): User {
        this.userName = userName
return this
}

override fun userName(): String = userName
}