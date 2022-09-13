#import "AppDelegate+CooeeSDKPlugin.h"
#import <UserNotifications/UserNotifications.h>
#import "CooeeSDK/CooeeSDK-Swift.h"

@interface AppDelegate () <UNUserNotificationCenterDelegate>
@end

static BOOL isAfterForeground = false;
@implementation AppDelegate (CooeeSDKPlugin)

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary<UIApplicationLaunchOptionsKey,id> *)launchOptions{
    [NewSessionExecutor updateWrapperInformationWithWrapperType:WrapperTypeCORDOVA versionNumber:VERSION_NAME versionCode:VERSION_CODE];
    [AppController configure];

    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    center.delegate = self;
    [center requestAuthorizationWithOptions:(UNAuthorizationOptionSound | UNAuthorizationOptionAlert | UNAuthorizationOptionBadge) completionHandler:^(BOOL granted, NSError * _Nullable error) {
        if( !error ) {
            // Once permission granted register for remote notification
            dispatch_async(dispatch_get_main_queue(), ^{
                [[UIApplication sharedApplication] registerForRemoteNotifications];
            });
        } else {
            // Handle error thrown by permission request
            NSLog( @"CooeePlugin: ERROR: %@ - %@", error.localizedFailureReason, error.localizedDescription );
        }
    }];

    return  [super application:application didFinishLaunchingWithOptions:launchOptions];
}

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    [[CooeeSDK getInstance] setDeviceTokenWithToken:deviceToken];
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler
{
    completionHandler([[CooeeSDK getInstance] presentNotification:notification]);
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler
{
    [[CooeeSDK getInstance] notificationAction:response];
    completionHandler();
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error{
    NSLog(@"CooeePlugin: Fail to register remote notification with reson: %@", error.description);
}

- (void)applicationWillEnterForeground:(UIApplication *)application{
    isAfterForeground = true;
}

- (void)applicationDidBecomeActive:(UIApplication *)application{
    if (isAfterForeground) {
        return;
    }

    [[NSNotificationCenter defaultCenter] postNotificationName:UIApplicationWillEnterForegroundNotification object:nil];
}
@end
