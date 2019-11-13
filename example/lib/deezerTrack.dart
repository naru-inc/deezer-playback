class DeezerTrack {
  String id;
  String title;
  int duration;
  String artistName;
  String albumName;
  DeezerTrack({
    this.id,
    this.title,
    this.duration,
    this.artistName,
    this.albumName,
  });

  factory DeezerTrack.fromDeezer(Map<String, dynamic> json) {
    return new DeezerTrack(
        id: json['id'].toString() ?? null,
        title: json['title'] ?? false,
        duration: json['duration'] ?? false,
        artistName: json['artist']['name'] ?? null,
        albumName: json['album']['name'] ?? null);
  }
}
