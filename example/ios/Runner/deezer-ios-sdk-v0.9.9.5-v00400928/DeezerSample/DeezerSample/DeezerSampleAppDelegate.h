#import <UIKit/UIKit.h>

#import "DeezerAudioPlayerController.h"

@interface DeezerSampleAppDelegate : UIResponder <UIApplicationDelegate, UITabBarControllerDelegate>
{
    UIBackgroundTaskIdentifier	bgTask;
}
@property (strong, nonatomic) UIWindow *window;

- (DeezerAudioPlayerController*)playerViewController;
@end
