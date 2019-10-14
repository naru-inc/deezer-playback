#import "DeezerPlaybackPlugin.h"
#import <deezer_playback/deezer_playback-Swift.h>

@implementation DeezerPlaybackPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftDeezerPlaybackPlugin registerWithRegistrar:registrar];
}
@end
