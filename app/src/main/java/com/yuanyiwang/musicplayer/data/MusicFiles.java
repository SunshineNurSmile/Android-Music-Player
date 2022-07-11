package com.yuanyiwang.musicplayer.data;

public class MusicFiles {

    private final String title;
    private final String artist;
    private final String duration;
    private final String path;
    private final String bitrate;
    private final String size;
    private final String album;

    public MusicFiles(String title, String artist, String duration, String path, String bitrate, String size, String album) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.path = path;
        this.bitrate = bitrate;
        this.size = size;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public String getBitrate() {
        return bitrate;
    }

    public String getSize() {
        return size;
    }

    public String getAlbum() {
        return album;
    }
}
