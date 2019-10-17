package tech.naru.deezer

import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.player.PlayerWrapper;
import com.deezer.sdk.player.PlayerWrapper.RepeatMode;
import com.deezer.sdk.player.TrackPlayer
import io.flutter.plugin.common.MethodChannel.Result
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker
import com.deezer.sdk.player.*
import io.flutter.plugin.common.PluginRegistry


class PlaybackControls(val mDeezerConnect: DeezerConnect?,val trackPlayer : TrackPlayer?) : BaseControl(mDeezerConnect) {

    /**
     * Play an Deezer track by passing the Deezer track/playlist/album id
     * If the Deezer play call is successful return true
     */


    /**
     * Pauses the currently playing Deezer track, if successful return true
     */
    internal fun pause(
            result: Result
    ) {
        if ( mDeezerConnect != null) {
            trackPlayer!!.pause()
            result.success(true)
        } else {
            result.error("pause", "error", "no Deezer Player")
        }
    }

    /**
     * Resumes the currently paused Deezer track, if successful return true
     */
    internal fun resume(result: Result) {
        if (mDeezerConnect != null) {
            trackPlayer!!.play()
            result.success(true)
        } else {
            result.error("resume", "error", "no Deezer Player")
        }
    }

    /**
     * Plays the next song in the list
     */
    internal fun skipNext(result: Result) {
        if (mDeezerConnect != null) {
            trackPlayer!!.skipToNextTrack()
            result.success(true)

        } else {
            result.error("next", "error", "no Deezer Player")
        }
    }

    /**
     * Plays the next song in the list
     */
    internal fun skipPrevious(result: Result) {
        if (mDeezerConnect != null) {
            trackPlayer!!.skipToPreviousTrack()
            result.success(true)
        } else {
            result.error("prev", "error", "no Deezer Player")
        }
    }

    /**
     * Toggles repeat modes
     */
    internal fun toggleRepeat(result: Result) {
        val current : RepeatMode
        val next: RepeatMode
        if (mDeezerConnect != null) {
            current = trackPlayer!!.getRepeatMode()
            when (current) {
                PlayerWrapper.RepeatMode.NONE -> {
                    next = RepeatMode.ONE
                }
                PlayerWrapper.RepeatMode.ONE -> {
                    next = RepeatMode.ALL

                }
                PlayerWrapper.RepeatMode.ALL -> {
                    next = RepeatMode.NONE

                }
                else -> {
                    next = RepeatMode.NONE

                }
            }
            trackPlayer!!.setRepeatMode(next);
            result.success(true)

        } else {
            result.error("repeat", "error", "no Deezer Player")
        }
    }


}