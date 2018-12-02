

package com.zava.mvplab.track.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.zava.mvplab.data.api.Constants.Serialized.HREF;
import static com.zava.mvplab.data.api.Constants.Serialized.TOTAL;

public class Followers implements Parcelable {

  public static final Creator<Followers> CREATOR = new Creator<Followers>() {

    public Followers createFromParcel(Parcel source) {
      return new Followers(source);
    }

    public Followers[] newArray(int size) {
      return new Followers[size];
    }
  };

  @SerializedName(HREF) private String mHref;
  @SerializedName(TOTAL) public int totalFollowers;

  private Followers(Parcel in) {
    this.mHref = in.readString();
    this.totalFollowers = in.readInt();
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.mHref);
    parcel.writeInt(this.totalFollowers);
  }

  @Override public int describeContents() {
    return 0;
  }
}
