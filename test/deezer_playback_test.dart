import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:deezer_playback/deezer_playback.dart';

void main() {
  const MethodChannel channel = MethodChannel('deezer_playback');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await DeezerPlayback.platformVersion, '42');
  });
}
