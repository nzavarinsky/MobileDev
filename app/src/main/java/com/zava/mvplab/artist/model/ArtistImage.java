

package com.zava.mvplab.artist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.zava.mvplab.data.api.Constants.Serialized.HEIGHT;
import static com.zava.mvplab.data.api.Constants.Serialized.URL;
import static com.zava.mvplab.data.api.Constants.Serialized.WIDTH;

public class ArtistImage implements Parcelable {

  public static final Creator<ArtistImage> CREATOR = new Creator<ArtistImage>() {

    public ArtistImage createFromParcel(Parcel source) {
      return new ArtistImage(source);
    }

    public ArtistImage[] newArray(int size) {
      return new ArtistImage[size];
    }
  };

  @SerializedName(HEIGHT) private int mHeight;
  @SerializedName(URL) public String mUrl;
  @SerializedName(WIDTH) private int mWidth;

  private ArtistImage(Parcel in) {
    this.mHeight = in.readInt();
    this.mUrl = in.readString();
    this.mWidth = in.readInt();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(this.mHeight);
    parcel.writeString(this.mUrl);
    parcel.writeInt(this.mWidth);
  }
}
