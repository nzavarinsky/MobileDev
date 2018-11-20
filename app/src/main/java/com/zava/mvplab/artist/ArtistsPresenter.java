

package com.zava.mvplab.artist;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class ArtistsPresenter extends Presenter<ArtistsPresenter.View> {

  private ArtistsInteractor interactor;

  public ArtistsPresenter(ArtistsInteractor interactor) {
    this.interactor = interactor;
  }

  public void onSearchArtist(String name) {
    getView().showLoading();
    Disposable disposable = interactor.searchArtists(name).subscribe(artists -> {
      if (!artists.isEmpty()) {
        getView().hideLoading();
        getView().renderArtists(artists);
      } else {
        getView().showArtistNotFoundMessage();
      }
    }, Throwable::printStackTrace);

    addDisposableObserver(disposable);
  }

  public void launchArtistDetail(Artist artist) {
    getView().launchArtistDetail(artist);
  }

  @Override public void terminate() {
    super.terminate();
    setView(null);
  }

  public interface View extends Presenter.View {

    void showLoading();

    void hideLoading();

    void showArtistNotFoundMessage();

    void showConnectionErrorMessage();

    void showServerError();

    void renderArtists(List<Artist> artists);

    void launchArtistDetail(Artist artist);
  }
}
