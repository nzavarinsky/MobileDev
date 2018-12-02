

package com.zava.mvplab.track.model;

import com.google.gson.annotations.SerializedName;
import com.zava.mvplab.artist.model.ArtistImage;

import java.util.List;

import static com.zava.mvplab.data.api.Constants.Serialized.IMAGES;
import static com.zava.mvplab.data.api.Constants.Serialized.NAME;

public class Album {
  @SerializedName(NAME)
  private String mAlbumName;
  @SerializedName(IMAGES)
  private List<com.zava.mvplab.artist.model.ArtistImage> mTrackImages;

  public String getAlbumName() {
    return mAlbumName;
  }

  public void setAlbumName(String mAlbumName) {
    this.mAlbumName = mAlbumName;
  }

  public List<ArtistImage> getTrackImages() {
    return mTrackImages;
  }

  public void setTrackImages(List<ArtistImage> mTrackImages) {
    this.mTrackImages = mTrackImages;
  }

}
