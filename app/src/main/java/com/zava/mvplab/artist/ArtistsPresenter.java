

package com.zava.mvplab.artist;

import android.content.Context;

import io.reactivex.disposables.Disposable;

public class ArtistsPresenter extends com.zava.mvplab.base.Presenter<com.zava.mvplab.artist.ArtistContract.View>
    implements com.zava.mvplab.artist.ArtistContract.Presenter {

  private ArtistsInteractor interactor;

  public ArtistsPresenter(ArtistsInteractor interactor) {
    this.interactor = interactor;
  }

  @Override
  public void onSearchArtist(String name) {
    getView().showLoading();
    Disposable disposable = interactor.searchArtists(name).subscribe(artists -> {
      if (artists.isEmpty()) {
        getView().showArtistNotFoundMessage();
      } else {
        getView().hideLoading();
        getView().renderArtists(artists);

      }
    }, Throwable::printStackTrace);

    addDisposableObserver(disposable);
  }

  @Override
  public void launchArtistDetail(Context context, Artist artist) {
    getView().getStartIntent(context,artist);
  }

  @Override
  public void terminate() {
    super.terminate();
    setView(null);
  }


}
