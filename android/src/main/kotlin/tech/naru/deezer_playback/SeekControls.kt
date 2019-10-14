package tech.naru.deezer

import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.player.PlayerWrapper;
import com.deezer.sdk.player.PlayerWrapper.RepeatMode;
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

class SeekControls(val mDeezerConnect: DeezerConnect?, val mPlayer : PlayerWrapper?) : BaseControl(mDeezerConnect) {
    internal fun seekTo(
            time: String?,
            result: Result
    ) {
        if (mPlayer != null && time != null) {
            mPlayer.seek(time.toLong())
            result.success(true)

        } else {
            result.error("seekTo", "error", "no Deezer Player")
        }
    }
    internal fun seekToRelativePosition(
            relativeTime: String?,
            result: Result
    ) {
        if (mPlayer != null && relativeTime != null) {
            mPlayer.seek(relativeTime.toLong());
            result.success(true)
        } else {
            result.error("seekTo", "error", "no Deezer Player")
        }
    }
}