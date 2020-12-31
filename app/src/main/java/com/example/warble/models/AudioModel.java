package com.example.warble.models;

public class AudioModel {

    String songName;
    String artistName;
    String duration;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getDuration() {
        return duration;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
