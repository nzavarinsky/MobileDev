package com.zava.mvplab.track;

import com.zava.mvplab.track.model.Track;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zava on 21.11.2018.
 */

public interface TrackContract {

   interface View extends com.zava.mvplab.base.Presenter.View {

    void showLoading();

    void hideLoading();

    void showTracksNotFoundMessage();

    void showConnectionErrorMessage();

    void renderTracks(List<Track> tracks);

    void launchTrackDetail(List<Track> tracks, Track track, int position);
  }

  interface Presenter {
    void onSearchTracks(String string);

    void launchArtistDetail(List<Track> tracks, Track track, int position);
  }

  interface Interactor {
     Observable<List<Track>> loadData(String artistId);
  }
}
