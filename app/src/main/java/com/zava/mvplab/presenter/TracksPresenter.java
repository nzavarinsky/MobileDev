/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zava.mvplab.presenter;

import com.zava.mvplab.data.model.Track;
import com.zava.mvplab.interactor.TracksInteractor;

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
