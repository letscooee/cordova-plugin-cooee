# Change Log

# 0.0.6

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

# 0.0.5

Fixed Android build because of the wrong package.

# 0.0.3, 0.0.4

Using latest v0.1.1 of Android SDK hence added a new method `setCurrentScreen`.

# 0.0.2

Using latest v0.0.3 Android SDK version.

# 0.0.1

The first working wrapper for Android SDK.
