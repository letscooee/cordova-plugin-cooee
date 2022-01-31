#import "AppDelegate+CooeeSDKPlugin.h"
#import <UserNotifications/UserNotifications.h>
#import "CooeeSDK/CooeeSDK-Swift.h"

@interface AppDelegate () <UNUserNotificationCenterDelegate>
@end

@implementation AppDelegate (CooeeSDKPlugin)

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

@end