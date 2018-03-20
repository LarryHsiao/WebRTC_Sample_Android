package com.silverhetch.webrtcpratice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.silverhetch.webrtcpratice.User.User
import com.silverhetch.webrtcpratice.User.UserImpl
import com.silverhetch.webrtcpratice.webrtc.WebRtcImpl
import org.webrtc.EglBase
import org.webrtc.PeerConnectionFactory
import org.webrtc.SurfaceViewRenderer

class MainActivity : AppCompatActivity() {
    private val user: User = UserImpl()
    private val webrtc = WebRtcImpl(user)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val localVideo = findViewById<SurfaceViewRenderer>(R.id.main_localVideo)
        localVideo.init(EglBase.create().eglBaseContext, null)

        val remoteVideo = findViewById<SurfaceViewRenderer>(R.id.main_remoteVideo)
        remoteVideo.init(EglBase.create().eglBaseContext, null)

        PeerConnectionFactory.initializeAndroidGlobals(this, true)
        findViewById<View>(R.id.main_start).setOnClickListener {
            webrtc.start(WebRtcRenderer(localVideo, remoteVideo))
        }

        findViewById<View>(R.id.main_stop).setOnClickListener {
            webrtc.stop()
        }

        findViewById<Button>(R.id.main_login).setOnClickListener {
            webrtc.login("Larry")
        }

        findViewById<Button>(R.id.main_call).setOnClickListener {
            webrtc.call("Larry2")
        }
    }

}
