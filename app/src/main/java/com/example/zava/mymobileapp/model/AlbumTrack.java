package com.example.zava.mymobileapp.model;


public class AlbumTrack {

    private String trackId;
    private String trackName;
    private String trackDuration;
    private float trackPopularity;

  
    public AlbumTrack(String trackId, String trackName, String trackDuration, float trackPopularity) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.trackPopularity = trackPopularity;
    }

   
    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(String trackDuration) {
        this.trackDuration = trackDuration;
    }

    public float getTrackPopularity() {
        return trackPopularity;
    }

    public void setTrackPopularity(float trackPopularity) {
        this.trackPopularity = trackPopularity;
    }
}
