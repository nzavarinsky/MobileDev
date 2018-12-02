

package com.zava.mvplab.artist;

import com.zava.mvplab.data.api.client.SpotifyService;
import io.reactivex.Observable;
import java.util.List;

public class ArtistsInteractor implements com.zava.mvplab.artist.ArtistContract.Interactor {

  private SpotifyService spotifyService;

  public ArtistsInteractor(SpotifyService spotifyService) {
    this.spotifyService = spotifyService;
  }

  @Override
  public Observable<List<com.zava.mvplab.artist.model.Artist>> searchArtists(String query) {
    return spotifyService.search(query);
  }

}
