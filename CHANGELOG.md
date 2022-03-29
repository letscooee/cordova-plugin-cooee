# Change Log

## 1.3.3

### Fixes

1. Fix Android nested event properties.

## 1.3.2

### Improvements

1. Using iOS SDK v1.3.13

### Fixes

1. Fix Push notification content issue
2. Fix CTA callback issue in iOS

## 1.3.1

### Improvements

1. Add Callbacks from iOS platform
2. Using Android SDK v1.3.5
3. Using iOS SDK v1.3.12

## 1.3.0

1. Add hooks to configure `Swift_Version` and Add `Notification Service`.
2. Using Android SDK v1.3.3.
3. Using iOS SDK v1.3.10.
4. Expose `updateUserProfile` method.
5. Deprecate `updateUserData` and `updateUserProperties` methods.

## 1.0.2

1. Remove the need of `COOEE_APP_SECRET`.

## 1.0.1

1. Upgrade to Android SDK v1.1.0
2. Upgrade to iOS SDK v1.3.7

## 1.0.0

1. Support of iOS via v1.3.4
2. Upgrade to Android SDK v1.1.0

## 0.0.6

1. Using Android SDK v1.0.2

### Required Changes

As our new library works with `androidx` libraries you will need to add `androidx` support to your project. Add following tag in `config.xml`.

```xml
<platform name="android">
    ...
    <preference name="AndroidXEnabled" value="true" />
</platform>
```

We also added In-App trigger where we provide data to the app when user performs any action on trigger. To accept data you will need to add following code in your project.

```js
document.addEventListener('onCooeeCTAListener', this.onCooeeCTAListener, false);

function onCooeeCTAListener: function(e) {
    console.log(e.customExtras);
},

```

## 0.0.5

Fixed Android build because of the wrong package.

## 0.0.3, 0.0.4

Using latest v0.1.1 of Android SDK hence added a new method `setCurrentScreen`.

## 0.0.2

Using latest v0.0.3 Android SDK version.

## 0.0.1

The first working wrapper for Android SDK.
