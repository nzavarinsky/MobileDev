package com.example.zava.mymobileapp.model;

import java.util.List;


public class AlbumArtist {

    private String artistId;
    private String artistName;
    private String artistImageUrl;
    private List<String> artistGenres;
    private float artistPopularity;
    private List<ArtistAlbum> artistAlbumList;


  
    public AlbumArtist(String artistId, String artistName, String artistImageUrl, List<String> artistGenres, float artistPopularity, List<ArtistAlbum> artistAlbumList) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistImageUrl = artistImageUrl;
        this.artistGenres = artistGenres;
        this.artistPopularity = artistPopularity;
        this.artistAlbumList = artistAlbumList;
    }

   
    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImageUrl() {
        return artistImageUrl;
    }

    public void setArtistImageUrl(String artistImageUrl) {
        this.artistImageUrl = artistImageUrl;
    }

    public List<String> getArtistGenres() {
        return artistGenres;
    }

    public void setArtistGenres(List<String> artistGenres) {
        this.artistGenres = artistGenres;
    }

    public float getArtistPopularity() {
        return artistPopularity;
    }

    public void setArtistPopularity(float artistPopularity) {
        this.artistPopularity = artistPopularity;
    }

    public List<ArtistAlbum> getArtistAlbumList() {
        return artistAlbumList;
    }

    public void setArtistAlbumList(List<ArtistAlbum> artistAlbumList) {
        this.artistAlbumList = artistAlbumList;
    }
}
