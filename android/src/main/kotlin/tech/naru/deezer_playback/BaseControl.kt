package tech.naru.deezer
import com.deezer.sdk.network.connect.DeezerConnect
import android.app.Activity
import com.deezer.sdk.player.PlayerWrapper;
import io.flutter.plugin.common.PluginRegistry

open class BaseControl( mDeezerConnect: DeezerConnect?) {
    init {
        if (mDeezerConnect == null) {
            print("Deezer is not initialize please call DeezerConnect first")
        }
    }
}