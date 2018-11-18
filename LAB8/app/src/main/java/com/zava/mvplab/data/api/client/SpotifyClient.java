package com.zava.mvplab.data.api.client;

import com.zava.mvplab.data.api.retrofit.SpotifyRetrofitClient;
import com.zava.mvplab.data.model.Artist;
import com.zava.mvplab.data.model.Track;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpotifyClient extends SpotifyRetrofitClient implements SpotifyService {

  @Override public Observable<List<Artist>> search(String query) {
    return getSpotifyService().searchArtist(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<List<Track>> getTracks(String artistId) {
    return getSpotifyService().getTracks(artistId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
