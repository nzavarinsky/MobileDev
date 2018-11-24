

package com.zava.mvplab.track.model;

import com.google.gson.annotations.SerializedName;
import com.zava.mvplab.artist.model.ArtistImage;

import java.util.List;

import static com.zava.mvplab.data.api.Constants.Serialized.IMAGES;
import static com.zava.mvplab.data.api.Constants.Serialized.NAME;

public class Album {
  @SerializedName(NAME)
  public String albumName;
  @SerializedName(IMAGES)
  public List<com.zava.mvplab.artist.model.ArtistImage> trackImages;

  public String getAlbumName() {
    return albumName;
  }

  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }

  public List<ArtistImage> getTrackImages() {
    return trackImages;
  }

  public void setTrackImages(List<ArtistImage> trackImages) {
    this.trackImages = trackImages;
  }

}
