# deezer_playback_example

Demonstrates how to use the deezer_playback plugin.

## Getting Started

**`IMPORTANT:` Make sure you have the Deezer app installed and that you are logged in or your test device!**

First, add `deezer_playback` as a dependency in your `pubspec.yaml` file. 

Afterwards, download the Deezer Android SDK [here](https://developers.deezer.com/sdk/android) and move the deezer-sdk--x.x.x.aar file to `android/app/libs/` in your project.

**`IMPORTANT:` Make sure you add permissions bellow to your Android Manifest located in `/android/app/src/main/AndroidManifest.xml` !**

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-library android:name="org.apache.http.legacy" android:required="false" />

```

Then initialize the deezer playback sdk like this 

```dart
@override
  void initState() {
    super.initState();
    initConnector();
    connect();
  }

  /// Initialize the Deezer playback sdk, by calling iniatilizeDeezer
  Future<void> initConnector() async {
    try {
      await DeezerPlayBack.iniatilizeDeezer(appId: "").then(
          (connected) {
        if (!mounted) return;
        // If the method call is successful, update the state to reflect this change
        setState(() {
          _connectedToDeezer = connected;
        });
      }, onError: (error) {
        // If the method call trows an error, print the error to see what went wrong
        print(error);
      });
    } on PlatformException {
      print('Failed to connect.');
    }
  }
  /// Initialize the Deezer playback sdk, by calling DeezerConnect
  Future<void> connect() async {
    try {
      await DeezerPlayback.connectToDeezer().then((authorised) {
        if (!mounted) return;
        // If the method call is successful, update the state to reflect this change
        setState(() {
          _connectedToDeezer = authorised;
        });
        print("we authorized deezer"+_connectedToDeezer.toString());
      }, onError: (error) {
        // If the method call trows an error, print the error to see what went wrong
        print(error);
      });
    } on PlatformException {
      print('Failed to connect.');
    }
  }
``` 

After this you can use all the available methods

## Available methods 

| Method        | description           | parameters  | notes |
| ------------- |:-------------:| -----:|-----:|
| iniatilizeDeezer      | Initilizes the deezer playback sdk | appId,  ||
| connectToDeezer      | Connect to  deezer account  |  ||
| play      | Play's a deezer track, album or playlist | track Id ||
| pause      | Pause's the currently playing track      |    ||
| resume |  Resumes the currently paused track      |     ||
| skipNext      | Play's the next track | ||
| skipPrevious      | Play's the previous track |  ||
| seekTo |  Seeks to the passed time     |  time(mS)   ||
|seekToRelativePosition|Seeks to relative position|+-time(mS)||
| toggleRepeat | Toggle Repeat options    |     ||
| getPlaybackPosition | Get's the current tracks playback position       |    ||
| searchTracks | Get tracks by searching      |   any keyword ||
| getTrack | Get track by ID      |   id(string) ||

