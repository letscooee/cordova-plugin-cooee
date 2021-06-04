# Change Log

# 0.0.6

1. Using Android SDK v0.2.11
2. Added In-Trigger

### Required Changes

As our new library works with `androidx` libraries you will need to add `cordova-plugin-androidx` plugin to your project. Run following command before you build your project.

```diff
    cordova plugin add cordova-plugin-androidx
```

We also added In-App trigger where we provide data to the app when youser performs any action on trigger. To accept data you will need to add following code in your project.

```js
document.addEventListener('onCooeeInAppButtonClick', this.onCooeeInAppButtonClick, false); // optional, to check if Un-App Trigger button was clicked with custom payload

function onCooeeInAppButtonClick: function(e) {
    console.log(e.customExtras);
},

```

# 0.0.5

Fixed Android build because of the wrong package.

# 0.0.3, 0.0.4

Using latest v0.1.1 of Android SDK hence added a new method `setCurrentScreen`.

# 0.0.2

Using latest v0.0.3 Android SDK version.

# 0.0.1

The first working wrapper for Android SDK.
