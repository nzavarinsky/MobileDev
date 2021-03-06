

package com.zava.mvplab.track;

import com.zava.mvplab.data.api.client.SpotifyService;
import com.zava.mvplab.track.model.Track;

import java.util.List;

import io.reactivex.Observable;

public class TracksInteractor implements com.zava.mvplab.track.TrackContract.Interactor {

  private SpotifyService spotifyService;

  public TracksInteractor(SpotifyService spotifyService) {
    this.spotifyService = spotifyService;
  }

  public Observable<List<Track>> loadData(String artistId) {
    return spotifyService.getTracks(artistId);
  }
}
