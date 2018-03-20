package com.silverhetch.webrtcpratice.User

import kotlin.properties.ObservableProperty

/**
 * Created by mikes on 3/20/2018.
 */
interface User {
    fun userName(): ObservableProperty<String>
    fun userSignedIn(userName: String): String
}