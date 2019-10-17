import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';
import 'credentials.dart';
import 'package:deezer_playback/deezer_playback.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  bool _connectedToDeezer = false;
  bool _initDeezer = false;
  bool isPlaying = false;

  @override
  void initState() {
    super.initState();
    initPlatformState();
    initConnector();
    connect();
    play("3135580");
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await DeezerPlayback.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  /// Initialize the Deezer playback sdk, by calling DeezerConnect
  Future<void> initConnector() async {
    try {
      await DeezerPlayback.iniatilizeDeezer(appId: Credentials.appId).then(
          (connected) {
        if (!mounted) return;
        // If the method call is successful, update the state to reflect this change
        setState(() {
          _initDeezer = connected;
        });
        print(_initDeezer);
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
        print("we authorized deezer");
      }, onError: (error) {
        // If the method call trows an error, print the error to see what went wrong
        print(error);
      });
    } on PlatformException {
      print('Failed to connect.');
    }
  }

  /// Play an song by Deezer track/album/playlist id
  Future<void> play(String id) async {
    try {
      await DeezerPlayback.play(id).then((success) {
        print(success);
        isPlaying = true;
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to play.');
    }
  }

  Future<void> pauseorResume() async {
    if (isPlaying) {
      pause();
    } else {
      resume();
    }
  }

  /// Pause the currently playing track
  Future<void> pause() async {
    try {
      await DeezerPlayback.pause().then((success) {
        print(success);
        isPlaying = false;
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to pause.');
    }
  }

  /// Resume the currently paused track
  Future<void> resume() async {
    try {
      await DeezerPlayback.resume().then((success) {
        print(success);
        isPlaying = true;
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to resume.');
    }
  }

  /// Seek to a defined time in a song
  Future<void> seekTo() async {
    try {
      await DeezerPlayback.seekTo(20000).then((success) {
        print(success);
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to play.');
    }
  }

  /// Seek to a a defined time relative to the current time
  Future<void> seekToRelativePosition() async {
    try {
      await DeezerPlayback.seekToRelativePosition(5000).then((success) {
        print(success);
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to play.');
    }
  }

  /// Gets the currently playing track's playback position
  Future<void> getPlaybackPosition() async {
    try {
      await DeezerPlayback.getPlaybackPosition().then((position) {
        print(
            "playback position -->\nmin:${DateTime.fromMillisecondsSinceEpoch(position).minute}\nseconds:${DateTime.fromMillisecondsSinceEpoch(position).second}");
        return position;
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to resume.');
    }
  }

  /// Play the previous song
  Future<void> skipPrevious() async {
    try {
      await DeezerPlayback.skipPrevious().then((success) {
        print(success);
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to play.');
    }
  }

  ///Play the next song
  Future<void> skipNext() async {
    try {
      await DeezerPlayback.skipNext().then((success) {
        print(success);
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to play next song.');
    }
  }

  /// Toggle repeat
  Future<void> toggleRepeat() async {
    try {
      await DeezerPlayback.toggleRepeat().then((success) {
        print(success);
      }, onError: (error) {
        print(error);
      });
    } on PlatformException {
      print('Failed to toggle repeat.');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Deezer PlayBack Example'),
        ),
        body: Center(
            child: FlatButton(
          color: Colors.blue,
          textColor: Colors.white,
          disabledColor: Colors.grey,
          disabledTextColor: Colors.black,
          padding: EdgeInsets.all(8.0),
          splashColor: Colors.blueAccent,
          onPressed: () {
            seekTo();
          },
          child: Text(
            "Play/Pause",
          ),
        )),
      ),
    );
  }
}
