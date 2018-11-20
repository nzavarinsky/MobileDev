

package com.zava.mvplab.track;

import com.google.gson.annotations.SerializedName;
import com.zava.mvplab.artist.ArtistImages;

import java.util.List;

import static com.zava.mvplab.data.api.Constants.Serialized.IMAGES;
import static com.zava.mvplab.data.api.Constants.Serialized.NAME;

public class Album {
  @SerializedName(NAME) public String albumName;
  @SerializedName(IMAGES) public List<ArtistImages> trackImages;
}
