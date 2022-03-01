/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

//
//  AppDelegate.m
//  HelloWorld
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "AppDelegate.h"
#import "MainViewController.h"
#import <UserNotifications/UserNotifications.h>
#import "CooeeSDK/CooeeSDK-Swift.h"

@interface AppDelegate () <UNUserNotificationCenterDelegate>
@end

@implementation AppDelegate

    - (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
    {
        self.viewController = [[MainViewController alloc] init];
        [AppController configure];

        // Request notification permission & register for remote notification
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
                NSLog( @"ERROR: %@ - %@", error.localizedFailureReason, error.localizedDescription );
            }
        }];

        return [super application:application didFinishLaunchingWithOptions:launchOptions];
    }

    - (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
    {
        // Send device token to the Cooee
        [[CooeeSDK getInstance] setDeviceTokenWithToken:deviceToken];
    }

    - (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler
    {
        // Cooee can show only In-App while app is in foreground. Here Cooee will check for there notification and
        // decide what action should be taken on foreground notification. If Push notification do not belong to
        // Cooee then default [.badge,.alert,.sound] is return
        completionHandler([[CooeeSDK getInstance] presentNotification:notification]);
    }

    - (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler
    {
        // Cooee will perform click action associated with Push Notification
        [[CooeeSDK getInstance] notificationAction:response];
        completionHandler();
    }
@end
