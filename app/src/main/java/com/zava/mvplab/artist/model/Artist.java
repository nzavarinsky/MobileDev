
package com.zava.mvplab.artist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zava.mvplab.track.model.Followers;

import java.util.ArrayList;
import java.util.List;

import static com.zava.mvplab.data.api.Constants.Serialized.FOLLOWERS;
import static com.zava.mvplab.data.api.Constants.Serialized.HREF;
import static com.zava.mvplab.data.api.Constants.Serialized.ID;
import static com.zava.mvplab.data.api.Constants.Serialized.IMAGES;
import static com.zava.mvplab.data.api.Constants.Serialized.NAME;
import static com.zava.mvplab.data.api.Constants.Serialized.POPULARITY;

public class Artist implements Parcelable {

  public static final Creator<Artist> CREATOR = new Creator<Artist>() {

    public Artist createFromParcel(Parcel source) {
      return new Artist(source);
    }

    public Artist[] newArray(int size) {
      return new Artist[size];
    }
  };

  @SerializedName(FOLLOWERS) public Followers followers;
  @SerializedName(HREF) private String href;
  @SerializedName(ID) public String id;
  @SerializedName(IMAGES) public List<ArtistImage> mArtistImages;
  @SerializedName(NAME) public String name;
  @SerializedName(POPULARITY) private int popularity;

  public Artist() {
  }

  protected Artist(Parcel in) {
    this.href = in.readString();
    this.id = in.readString();
    this.name = in.readString();
    this.followers = in.readParcelable(Followers.class.getClassLoader());
    this.popularity = in.readInt();

    if (this.mArtistImages == null) {
      this.mArtistImages = new ArrayList();
    }
    in.readTypedList(this.mArtistImages, ArtistImage.CREATOR);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.href);
    parcel.writeString(this.id);
    parcel.writeString(this.name);
    parcel.writeParcelable(this.followers, 0);
    parcel.writeInt(this.popularity);
    parcel.writeTypedList(this.mArtistImages);
  }
}
