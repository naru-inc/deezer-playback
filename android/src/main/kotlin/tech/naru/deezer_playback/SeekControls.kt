package tech.naru.deezer

import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.player.PlayerWrapper;
import com.deezer.sdk.player.PlayerWrapper.RepeatMode;
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry
import com.deezer.sdk.player.*

class SeekControls(val mDeezerConnect: DeezerConnect?, val  trackPlayer : TrackPlayer?) : BaseControl(mDeezerConnect) {
    internal fun seekTo(
            time: String?,
            result: Result
    ) {
        if (mDeezerConnect != null && time != null) {
            trackPlayer!!.seek(time.toLong())
            result.success(true)

        } else {
            result.error("seekTo", "error", "no Deezer Player")
        }
    }
    internal fun seekToRelativePosition(
            relativeTime: String?,
            result: Result
    ) {
        if (mDeezerConnect != null && relativeTime != null) {
            trackPlayer!!.seek(relativeTime.toLong());
            result.success(true)
        } else {
            result.error("seekTo", "error", "no Deezer Player")
        }
    }
}