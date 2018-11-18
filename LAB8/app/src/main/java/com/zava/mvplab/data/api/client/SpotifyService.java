package com.zava.mvplab.data.api.client;

import com.zava.mvplab.data.model.Artist;
import com.zava.mvplab.data.model.Track;

import java.util.List;

import io.reactivex.Observable;

public interface SpotifyService {

  Observable<List<Artist>> search(String query);

  Observable<List<Track>> getTracks(String artistId);
}
