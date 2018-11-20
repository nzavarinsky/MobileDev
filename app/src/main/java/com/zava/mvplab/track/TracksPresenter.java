

package com.zava.mvplab.track;

import com.zava.mvplab.artist.Presenter;

import java.util.List;

public class TracksPresenter extends Presenter<TracksPresenter.View> {

  private TracksInteractor interactor;

  public TracksPresenter(TracksInteractor interactor) {
    this.interactor = interactor;
  }

  @Override public void terminate() {
    super.terminate();
    setView(null);
  }

  public void onSearchTracks(String string) {
    getView().showLoading();
    interactor.loadData(string).subscribe(tracks -> {
      if (!tracks.isEmpty() && tracks.size() > 0) {
        getView().hideLoading();
        getView().renderTracks(tracks);
      } else {
        getView().showTracksNotFoundMessage();
      }
    }, Throwable::printStackTrace);
  }

  public void launchArtistDetail(List<Track> tracks, Track track, int position) {
    getView().launchTrackDetail(tracks, track, position);
  }

  public interface View extends Presenter.View {

    void showLoading();

    void hideLoading();

    void showTracksNotFoundMessage();

    void showConnectionErrorMessage();

    void renderTracks(List<Track> tracks);

    void launchTrackDetail(List<Track> tracks, Track track, int position);
  }
}
