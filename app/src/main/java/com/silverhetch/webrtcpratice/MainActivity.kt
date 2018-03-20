package com.silverhetch.webrtcpratice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.silverhetch.webrtcpratice.User.User
import com.silverhetch.webrtcpratice.User.UserImpl
import com.silverhetch.webrtcpratice.signaling.SignalingFactory

class MainActivity : AppCompatActivity() {
    private val user: User = UserImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val singling = SignalingFactory(user).singling()
        singling.connect()


        findViewById<Button>(R.id.main_enter).setOnClickListener {
            singling.enter("Larry")
        }

        findViewById<View>(R.id.main_leave).setOnClickListener {
            singling.leave()
        }
    }
}
