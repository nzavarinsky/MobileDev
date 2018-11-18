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

package com.zava.mvplab.data.api;

import static com.zava.mvplab.data.api.Constants.Params.ARTIST_ID;

public class Constants {

  public static final String SPOTIFY_API = "https://api.spotify.com";
  public static final String API_KEY = "Authorization";

  public static final String ACCESS_TOKEN = "Bearer BQCR3metDjjSnB2cPOGmknIx4wmr9zZhZW6SwxYtWJWSDITgoABNX0VyioP_nwr4V6wmhpcVXU-bOj5qSi0QBC5GBvRILFV5cmarIKUo9HN7j1MvCOHG3D-YV2w6lN3pojtkkbBXB39bHFUd0Kkh_ItnwXz50RfFAavSRTs6YTHQ_I5gMrFe5rMTZ3sHp2NfIcCmGTQZIIBZXD1GCSG8V5SfH17KbYi_nbdqfGqHd32cqN46qehT9yJtY8MFZNY9mQIXcERA66Vz_Kyu_-BAnLYvRHG7VwHIRLI";

  public static final class Endpoint {

    public static final String ARTIST_SEARCH = "/v1/search?type=artist";
    public static final String ARTIST_TRACKS =
        "v1/artists/{" + ARTIST_ID + "}/top-tracks?country=SE";
  }

  public static final class Params {
    public static final String QUERY_SEARCH = "q";
    public static final String ARTIST_ID = "artistId";
  }

  public static final class Serialized {

    public static final String NAME = "name";
    public static final String IMAGES = "images";
    public static final String FOLLOWERS = "followers";
    public static final String HREF = "href";
    public static final String ID = "id";
    public static final String POPULARITY = "popularity";
    public static final String HEIGHT = "height";
    public static final String URL = "url";
    public static final String WIDTH = "width";
    public static final String TOTAL = "total";
    public static final String PREVIEW_URL = "preview_url";
    public static final String TRACK_NUMBER = "track_number";
    public static final String ALBUM = "album";


  }

  public static final class Deserializer {

    public static final String ARTISTS = "artists";
    public static final String ITEMS = "items";
    public static final String TRACKS = "tracks";
  }
}
