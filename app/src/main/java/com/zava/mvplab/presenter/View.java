package com.zava.mvplab.presenter;

import com.zava.mvplab.track.Track;
import com.zava.mvplab.artist.Presenter;

import java.util.List;

public interface View extends Presenter.View {

  void showLoading();

  void hideLoading();

  void showTracksNotFoundMessage();

  void showConnectionErrorMessage();

  void renderTracks(List<Track> tracks);

  void launchTrackDetail(List<Track> tracks, Track track, int position);
}
