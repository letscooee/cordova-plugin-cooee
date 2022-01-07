#import "AppDelegate+CooeeSDKPlugin.h"

@implementation AppDelegate (CooeeSDKPlugin)


// A UIApplication delegate
- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)didFinishLaunchingWithOptions
launchOptions:(bool (^)(NSDictionary *))launchOptions {
    NSLog(@"App launch");
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
    NSLog(@"Remote notification received");
}

@end