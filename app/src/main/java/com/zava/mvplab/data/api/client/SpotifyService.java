package com.zava.mvplab.data.api.client;


import com.zava.mvplab.track.model.Track;

public interface SpotifyService {

  io.reactivex.Observable<java.util.List<com.zava.mvplab.artist.model.Artist>> search(String query);

  io.reactivex.Observable<java.util.List<Track>> getTracks(String artistId);
}
