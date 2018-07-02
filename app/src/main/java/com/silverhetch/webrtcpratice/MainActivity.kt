package com.silverhetch.webrtcpratice

import android.Manifest.permission
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.silverhetch.webrtcpratice.user.User
import com.silverhetch.webrtcpratice.user.UserImpl
import com.silverhetch.webrtcpratice.webrtc.WebRtcImpl
import org.webrtc.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_PERMISSION: Int = 992
    }

    private val user: User = UserImpl()
    private val webrtc = WebRtcImpl(user)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, permission.CAMERA) == PERMISSION_GRANTED) {
            initialize()
        } else {
            requestPermissions(this, arrayOf(permission.CAMERA), REQUEST_CODE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    initialize()
                } else {
                    Toast.makeText(this, "Permission required", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initialize() {
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
            webrtc.login(findViewById<EditText>(R.id.main_loginName).text.toString())
        }

        findViewById<Button>(R.id.main_call).setOnClickListener {
            webrtc.call(findViewById<EditText>(R.id.main_callTo).text.toString())
        }
    }

}
