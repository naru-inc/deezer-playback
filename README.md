# Deezer Playback

Deezer Playback Plugin.

[**No iOS Implementation yet, WIP..**](https://github.com/naru-inc/deezer-playback/projects/1)

## Features

* Connect / Authorize 
* Play (track)
* Resume / pause
* Queue
* Playback position
* Seek
* Seek to relative position
* Play Next
* Play Previous
* Repeat 

## Installation

**`IMPORTANT:` Make sure you have the Deezer app installed and that you are logged in or your test device!**

First, add `deezer_playback` as a dependency in your `pubspec.yaml` file. 

Afterwards, download the Deezer Android SDK [here](https://developers.deezer.com/sdk/android) and move the spotify-app-remote-release-x.x.x.aar file to `android/app/libs/` in your project.

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

## Changelog

See [CHANGELOG.md](CHANGELOG.md).

## Contributing

Feel free to contribute by opening issues and/or pull requests. Your feedback is very welcome!

## License

MIT License

Copyright (c) [2019] [NARU ENTREPRISE]