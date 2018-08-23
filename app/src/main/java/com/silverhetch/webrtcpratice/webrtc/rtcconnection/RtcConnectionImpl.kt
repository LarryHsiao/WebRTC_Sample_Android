package com.silverhetch.webrtcpratice.webrtc.rtcconnection

import android.util.Log
import com.silverhetch.webrtcpratice.webrtc.signaling.JsonSdp
import com.silverhetch.webrtcpratice.webrtc.signaling.Signaling
import org.json.JSONObject
import org.webrtc.*
import org.webrtc.PeerConnection.IceConnectionState.FAILED
import org.webrtc.PeerConnectionFactory.Options
import org.webrtc.SessionDescription.Type.ANSWER
import org.webrtc.SessionDescription.Type.OFFER

/**
 * Created by mikes on 3/20/2018.
 */
internal class RtcConnectionImpl : RtcConnection, PeerConnection.Observer {
    companion object {
        const val VIDEO_ID = "0176"
        const val MEDIA_STREAM_ID = "0176"
    }

    private lateinit var peerConnection: PeerConnection
    private lateinit var signaling: Signaling
    private lateinit var renderer: Renderer
    private var remoteName: String = ""

    override fun start(signaling: Signaling, renderer: Renderer) {
        this.renderer = renderer
        this.signaling = signaling
        val factory = PeerConnectionFactory(Options())
        val constraints = MediaConstraints()
        constraints.mandatory.add(MediaConstraints.KeyValuePair("offerToRecieveAudio", "false"))
        constraints.mandatory.add(MediaConstraints.KeyValuePair("offerToRecieveVideo", "true"))
        peerConnection = factory.createPeerConnection(
                PeerConnection.RTCConfiguration(mutableListOf<PeerConnection.IceServer>(
                        PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer(),
                        PeerConnection.IceServer.builder("stun:stun1.l.google.com:19302").createIceServer(),
                        PeerConnection.IceServer.builder("stun:stun2.l.google.com:19302").createIceServer(),
                        PeerConnection.IceServer.builder("stun:stun3.l.google.com:19302").createIceServer(),
                        PeerConnection.IceServer.builder("stun:stun4.l.google.com:19302").createIceServer(),
                        PeerConnection.IceServer.builder("turn:172.105.228.27:3478")
                                .setUsername("test")
                                .setPassword("test")
                                .createIceServer()
                )), constraints, this)!!

        val videoCapture: VideoCapturer = VideoCaptureFactory().instance()
        val videoSource: VideoSource = factory.createVideoSource(videoCapture)
        val videoTrack: VideoTrack = factory.createVideoTrack(VIDEO_ID, videoSource)
        videoCapture.startCapture(1000, 1000, 30) // params: width, height, fps
        videoTrack.addRenderer(renderer.localVideo())

        val stream: MediaStream = factory.createLocalMediaStream(MEDIA_STREAM_ID)
        stream.addTrack(videoTrack)

        peerConnection.addStream(stream)
    }

    override fun stop() {
        peerConnection.close()
    }

    override fun call(name: String) {
        peerConnection.createOffer(CreateStreamSdpObserver(object : CreateStreamSdpObserver.Callback {
            override fun onSuccess(localSdp: SessionDescription) {
                peerConnection.setLocalDescription(SdpSetupObserver(), localSdp)
                signaling.offer(name, JsonSdp(localSdp))
            }
        }), MediaConstraints())

        remoteName = name
    }

    override fun onOffer(offerName: String, remoteSdp: String) {
        remoteName = offerName
        // notice: the both of method using same callback definition called SdpObserver
        peerConnection.setRemoteDescription(SdpSetupObserver(), SessionDescription(OFFER, remoteSdp))
        peerConnection.createAnswer(CreateStreamSdpObserver(object : CreateStreamSdpObserver.Callback {
            override fun onSuccess(localSdp: SessionDescription) {
                peerConnection.setLocalDescription(SdpSetupObserver(), localSdp)
                signaling.answer(offerName, JsonSdp(localSdp))
            }
        }), MediaConstraints())
    }

    override fun onAnswer(sdp: String) {
        val description = SessionDescription(
                ANSWER,
                sdp
        )
        peerConnection.setRemoteDescription(SdpSetupObserver(), description)
    }

    override fun onCandidate(candidate: JSONObject) {
        peerConnection.addIceCandidate(IceCandidate(
                candidate.getString("sdpMid"),
                candidate.getInt("sdpMLineIndex"),
                candidate.getString("candidate")
        ))
    }

    override fun onAddStream(p0: MediaStream?) {
        p0!!.videoTracks[0].addRenderer(renderer.remoteVideo())
    }

    override fun onRemoveStream(p0: MediaStream?) {
        /* ignore exit event */
    }

    override fun onAddTrack(p0: RtpReceiver?, p1: Array<out MediaStream>?) {
    }

    override fun onIceCandidate(candidate: IceCandidate?) {
        val json = JSONObject()
        json.put("sdpMid", candidate!!.sdpMid)
        json.put("sdpMLineIndex", candidate.sdpMLineIndex)
        json.put("candidate", candidate.sdp)
        signaling.candidate(remoteName, json)
    }

    override fun onIceConnectionChange(newState: PeerConnection.IceConnectionState?) {
        Log.i("RtcConnection", "State: " + newState!!.name)
        if (FAILED == newState) {
            throw RuntimeException("connection failed")
        }
    }

    override fun onDataChannel(p0: DataChannel?) {
        /*ignore*/
    }

    override fun onIceConnectionReceivingChange(p0: Boolean) {
        /*ignore*/
    }

    override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
        /*ignore*/
    }

    override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
        /*ignore*/
    }

    override fun onIceCandidatesRemoved(candidates: Array<out IceCandidate>?) {
        /*ignore*/
    }

    override fun onRenegotiationNeeded() {
        /*ignore*/
    }
}