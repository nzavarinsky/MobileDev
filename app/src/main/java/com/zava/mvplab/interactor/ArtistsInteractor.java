

package com.zava.mvplab.interactor;

import com.zava.mvplab.data.api.client.SpotifyService;
import com.zava.mvplab.data.model.Artist;

import java.util.List;

import io.reactivex.Observable;

public class ArtistsInteractor {

  private SpotifyService spotifyService;

  public ArtistsInteractor(SpotifyService spotifyService) {
    this.spotifyService = spotifyService;
  }

  public Observable<List<Artist>> searchArtists(String query) {
    return spotifyService.search(query);
  }

}
