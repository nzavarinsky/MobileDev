

package com.zava.mvplab.track;

import com.zava.mvplab.track.model.Track;

import java.util.List;

public class TracksPresenter extends com.zava.mvplab.base.Presenter<com.zava.mvplab.track
    .TrackContract.View>
    implements com.zava.mvplab.track.TrackContract.Presenter {

  private TracksInteractor interactor;

  public TracksPresenter(TracksInteractor interactor) {
    this.interactor = interactor;
  }

  @Override
  public void terminate() {
    super.terminate();
    setView(null);
  }

  public void onSearchTracks(String string) {
    getView().showLoading();
    interactor.loadData(string).subscribe(tracks -> {
      if (tracks.isEmpty()) {
        getView().showTracksNotFoundMessage();
      } else {
        getView().hideLoading();
        getView().renderTracks(tracks);
      }
    }, Throwable::printStackTrace);
  }

  public void launchTrackDetail(List<Track> tracks, Track track, int position) {
    getView().launchTrackDetail(tracks, track, position);
  }
}
