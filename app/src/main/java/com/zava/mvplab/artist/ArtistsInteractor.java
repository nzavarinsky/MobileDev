

package com.zava.mvplab.artist;

import com.zava.mvplab.data.api.client.SpotifyService;
import com.zava.mvplab.artist.Artist;

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
