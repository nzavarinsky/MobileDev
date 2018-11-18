/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zava.mvplab.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.zava.mvplab.data.api.Constants.Serialized.HEIGHT;
import static com.zava.mvplab.data.api.Constants.Serialized.URL;
import static com.zava.mvplab.data.api.Constants.Serialized.WIDTH;

public class ArtistImages implements Parcelable {

  public static final Creator<ArtistImages> CREATOR = new Creator<ArtistImages>() {

    public ArtistImages createFromParcel(Parcel source) {
      return new ArtistImages(source);
    }

    public ArtistImages[] newArray(int size) {
      return new ArtistImages[size];
    }
  };

  @SerializedName(HEIGHT) private int heigth;
  @SerializedName(URL) public String url;
  @SerializedName(WIDTH) private int width;

  private ArtistImages(Parcel in) {
    this.heigth = in.readInt();
    this.url = in.readString();
    this.width = in.readInt();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(this.heigth);
    parcel.writeString(this.url);
    parcel.writeInt(this.width);
  }
}
