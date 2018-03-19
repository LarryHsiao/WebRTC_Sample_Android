package com.silverhetch.webrtcpratice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.silverhetch.webrtcpratice.signaling.SignalingFactory
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val singling = SignalingFactory().singling()
        singling.connect()


        findViewById<Button>(R.id.main_login).setOnClickListener {
            val loginJson = JSONObject()
            loginJson.put("type", "login")
            loginJson.put("name", "Larry")
            singling.message(loginJson.toString())
        }
    }
}
