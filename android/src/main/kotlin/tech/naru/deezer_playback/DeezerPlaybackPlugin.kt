package tech.naru.deezer_playback

import android.os.Build
import android.content.Intent;
import android.os.Bundle;
import android.util.Log
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.deezer.sdk.model.Track
import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.network.connect.event.DialogError
import com.deezer.sdk.network.connect.event.DialogListener
import com.deezer.sdk.network.request.event.DeezerError
import com.deezer.sdk.player.event.OnBufferProgressListener
import com.deezer.sdk.player.event.OnPlayerProgressListener
import com.deezer.sdk.player.event.RadioPlayerListener
import com.deezer.sdk.player.exception.TooManyPlayersExceptions
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker
import com.deezer.sdk.model.Permissions
import com.deezer.sdk.network.connect.SessionStore
import com.deezer.sdk.player.*
import com.deezer.sdk.player.event.PlayerState


import tech.naru.deezer.BaseControl
import tech.naru.deezer.PlaybackControls
import tech.naru.deezer.SeekControls
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import io.flutter.plugin.common.PluginRegistry
import java.sql.Connection
import kotlin.concurrent.fixedRateTimer

class DeezerPlaybackPlugin(private var registrar: PluginRegistry.Registrar) : MethodCallHandler {

      // The Deezer global reference
    private var mPlayer: PlayerWrapper ?= null
    private var sessionStore = SessionStore()
    private var  mDeezerConnect: DeezerConnect?= null
    
    private var permissions = arrayOf(Permissions.BASIC_ACCESS, Permissions.MANAGE_LIBRARY, Permissions.LISTENING_HISTORY)
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
        // Register the spotify playback method channel to call the methods from dart
      val channel = MethodChannel(registrar.messenger(), "deezer_playback")
      channel.setMethodCallHandler(DeezerPlaybackPlugin(registrar))
    }
  }
  
  



  /**
   * When an method call is method to the spotify_playback methodChannel
   * It will be handled here, and the correct function will be called
   */
  override fun onMethodCall(
          call: MethodCall,
          result: Result
  ) {

    val playbackControls = PlaybackControls(mDeezerConnect=mDeezerConnect,mPlayer = mPlayer)
    val seekControls = SeekControls(mDeezerConnect=mDeezerConnect,mPlayer = mPlayer)
    when {
      call.method == "iniatilizeDeezer" -> iniatilizeDeezer(
              call.argument("appId"), result
      )
        call.method == "connectDeezer" -> connectToDeezer(
           result
        )
      call.method == "playDeezer" -> play( trackID = call.argument("id"),result = result)
      call.method == "pauseDeezer" -> playbackControls.pause(result = result)
      call.method == "resumeDeezer" -> playbackControls.resume(result = result)
      call.method == "playbackPositionDeezer" -> getPlaybackPosition(result)
      call.method == "isConnected" -> connected(result)
      //call.method == "getCurrentlyPlayingTrack" -> getCurrentlyPlayingTrack(result = result)
      call.method == "skipNext" -> playbackControls.skipNext(result = result)
      call.method == "skipPrevious" -> playbackControls.skipPrevious(result = result)
      call.method == "seekTo" -> seekControls.seekTo(time = call.argument("time"), result = result)
      call.method == "seekToRelativePosition" -> seekControls.seekToRelativePosition(
              relativeTime = call.argument("relativeTime"), result = result
      )
      call.method == "toggleRepeat" -> playbackControls.toggleRepeat(result = result)
    /**  call.method == "getAuthToken" -> getAuthToken(
              clientId = call.argument("clientId"),
              redirectUrl = call.argument("redirectUrl"),
              result = result)*/
    }
  }
  /**
   * Asks the SDK to display a log in dialog for the user
   */

 /** private fun getCurrentlyPlayingTrack(result:Result) {
      if (mDeezerConnect != null) {
          val thistrack : Track
          mPlayer.playerState.
                      result.success(track)


      } else {
          result.error("error", "no mSpotifyAppRemote", "is null")

      }
    }*/
    private var trackPlayer = TrackPlayer(registrar.activity().application,mDeezerConnect ,WifiAndMobileNetworkStateChecker())
    // The listener for authentication events
    private val listener = object : DialogListener {

        override fun onComplete(values: Bundle) {
            // store the current authentication info
            val sessionStore = SessionStore()
            sessionStore.save(mDeezerConnect, registrar.context())

            // Launch the Home activity

        }

        override fun onException(exception: Exception) {

        }


        override fun onCancel() {

        }


    }
    private fun iniatilizeDeezer(appId: String?, result:Result) {
        if (appId != null) {
            mDeezerConnect = DeezerConnect(registrar.context(),appId)
            // The set of Deezer Permissions needed by the app
            result.success(true)
        } else {

            result.error("connect", "error", "the Id you're making is not valid")
        }
    }
    private fun connectToDeezer( result:Result) {
        if (mDeezerConnect != null) {
            mDeezerConnect!!.authorize(registrar.activity(), permissions, listener)
            result.success(true)
        } else {

            result.error("connect", "error", "the Id you're making is not valid")
        }
    }
    internal fun play(
            trackID: String?,
            result: Result
    ) {

        if (mDeezerConnect!=null && trackID !=null) {
            trackPlayer.playTrack(trackID.toLong())
            result.success(true)
        } else {
            result.error("play", "error", "no Deezer Playlist")
        }
    }

    private fun getPlaybackPosition(result: Result) {
        if (mDeezerConnect != null) {
            result.success( mPlayer!!.position)
        }
    }
    private fun connected(result: Result) {
        return if (mDeezerConnect != null) {
            result.success(mDeezerConnect!!.isSessionValid)
        } else {
            result.success(false)
        }
    }
/**
  //THIS SHOULD ONLY BE CALLED ONCE
  private fun getAuthToken(clientId: String?,
                           redirectUrl: String?,
                           result: Result){

    try {
      Connection.openLoginActivity(
              registrar.activity(), 1337,
              AuthenticationRequest.Builder(clientId,AuthenticationResponse.Type.TOKEN,redirectUrl)
                      .setScopes(arrayOf("user-modify-playback-state")).build())
    }catch (err:Throwable){
      Log.v("getAuthTOkenError",err.message)
    }
    registrar.addActivityResultListener { requestCode, resultCode, intent ->
      if (requestCode == 1337){
        result.success(AuthenticationClient.getResponse(resultCode,intent).accessToken)
      }
      true
    }
  }*/

}
