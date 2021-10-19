# Cordova Plugin for Cooee SDK

[![npm version](https://badge.fury.io/js/@letscooee%2Fcordova-plugin.svg)](https://badge.fury.io/js/@letscooee%2Fcordova-plugin)

## Platforms

- Android (Minimum Android 5.0/API level 21)
- iOS (Coming Soon)

## Installation

### Using Cordova

```shell script
cordova plugin add @letscooee/cordova-plugin \
        --variable COOEE_APP_ID="your-cooee-app-id" \
        --variable COOEE_APP_SECRET="your-cooee-app-secret" 
```

### Using Ionic

```shell script
ionic cordova plugin add @letscooee/cordova-plugin \
        --variable COOEE_APP_ID="your-cooee-app-id" \
        --variable COOEE_APP_SECRET="your-cooee-app-secret" 
```

## Usage

### 1. Track mandatory Events

Once you install the plugin, Cooee will automatically start tracking events. Apart from these, you must track mandatory
events as well per your app domain which are defined in the document. For example-

```js
const props = {
    id: 'FOO-B076H19JPJ',
    name: 'Multisport Shoe'
};

cordova.plugins.Cooee.sendEvent("Add To Cart", props);
```

### 2. Setting user properties

As the user launches the app for the first time, Cooee will create a user profile for them. By default, we add multiple
properties for a particular user which you can see in System Default User Properties. Along with these default
properties, additional custom attributes/properties can also be shared. We encourage mobile apps to share all properties
for better machine learning modelling.

```js
const userProps = {
    loggedIn: true,
    foo: 'bar'
};

cordova.plugins.Cooee.updateUserProperties(userProps);
```

### 3. Setting user data

This method would update custom user data and send it to server. It takes one parameter `userData`.

```js
const userData = {
    name: 'John Doe',
    email: 'john@example.com',
    mobile: '9876543210'
};

cordova.plugins.Cooee.updateUserData(userData);
```

### 4. In-App Trigger Button Click Callback

Cooee plugin supports callback on the click of in-app notification & push notifications actions by returning a map of 
key value pairs. Add `document.addEventListener()` to accept data which we send when user perform any action on 
In-App Trigger.

```js
document.addEventListener('onCooeeCTAListener', this.onCooeeCTAListener, false);

function onCooeeCTAListener(payload) {
    if (!payload) {
        return;
    }

    if (payload.get("actionType") == "VIEW_ITEM") {
        // Use payload.get("item")
    } else if (payload.get("actionType") == "GO_TO_SCREEN") {
        // Use payload.get("screenName")
    }
}
```

