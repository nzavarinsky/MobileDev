
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

public class Artist extends com.zava.mvplab.artist.Artist implements Parcelable {

  public static final Creator<Artist> CREATOR = new Creator<Artist>() {

    public Artist createFromParcel(Parcel source) {
      return new Artist(source);
    }

    public Artist[] newArray(int size) {
      return new Artist[size];
    }
  };

  @SerializedName(FOLLOWERS) public Followers mFollowers;
  @SerializedName(HREF) private String mHref;
  @SerializedName(ID) public String mId;
  @SerializedName(IMAGES) public List<ArtistImage> mArtistImages;
  @SerializedName(NAME) public String mName;
  @SerializedName(POPULARITY) private int mPopularity;

  public Artist() {
  }

  protected Artist(Parcel in) {
    this.mHref = in.readString();
    this.mId = in.readString();
    this.mName = in.readString();
    this.mFollowers = in.readParcelable(Followers.class.getClassLoader());
    this.mPopularity = in.readInt();

    if (this.mArtistImages == null) {
      this.mArtistImages = new ArrayList();
    }
    in.readTypedList(this.mArtistImages, ArtistImage.CREATOR);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.mHref);
    parcel.writeString(this.mId);
    parcel.writeString(this.mName);
    parcel.writeParcelable(this.mFollowers, 0);
    parcel.writeInt(this.mPopularity);
    parcel.writeTypedList(this.mArtistImages);
  }
}
