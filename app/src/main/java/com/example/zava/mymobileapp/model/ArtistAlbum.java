package com.example.zava.mymobileapp.model;


public class ArtistAlbum {

    private String artistAlbumId;
    private String artistAlbumName;
    private String artistAlbumReleaseDate;
    private float artistAlbumPopularity;
    private String artistAlbumImageURL;



    public ArtistAlbum(String artistAlbumId, String artistAlbumName, String artistAlbumReleaseDate, float artistAlbumPopularity, String artistAlbumImageURL) {
        this.artistAlbumId = artistAlbumId;
        this.artistAlbumName = artistAlbumName;
        this.artistAlbumReleaseDate = artistAlbumReleaseDate;
        this.artistAlbumPopularity = artistAlbumPopularity;
        this.artistAlbumImageURL = artistAlbumImageURL;
    }

   
    public String getArtistAlbumId() {
        return artistAlbumId;
    }

    public void setArtistAlbumId(String artistAlbumId) {
        this.artistAlbumId = artistAlbumId;
    }

    public String getArtistAlbumName() {
        return artistAlbumName;
    }

    public void setArtistAlbumName(String artistAlbumName) {
        this.artistAlbumName = artistAlbumName;
    }

    public String getArtistAlbumReleaseDate() {
        return artistAlbumReleaseDate;
    }

    public void setArtistAlbumReleaseDate(String artistAlbumReleaseDate) {
        this.artistAlbumReleaseDate = artistAlbumReleaseDate;
    }

    public float getArtistAlbumPopularity() {
        return artistAlbumPopularity;
    }

    public void setArtistAlbumPopularity(float artistAlbumPopularity) {
        this.artistAlbumPopularity = artistAlbumPopularity;
    }

    public String getArtistAlbumImageURL() {
        return artistAlbumImageURL;
    }

    public void setArtistAlbumImageURL(String artistAlbumImageURL) {
        this.artistAlbumImageURL = artistAlbumImageURL;
    }
}
