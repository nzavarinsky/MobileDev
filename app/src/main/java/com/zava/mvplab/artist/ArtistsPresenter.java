

package com.zava.mvplab.artist;

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
            if (!artists.isEmpty()) {
                getView().hideLoading();
                getView().renderArtists(artists);
            } else {
                getView().showArtistNotFoundMessage();
            }
        }, Throwable::printStackTrace);

        addDisposableObserver(disposable);
    }

    @Override
    public void launchArtistDetail(com.zava.mvplab.artist.model.Artist artist) {
        getView().launchArtistDetail(artist);
    }

    @Override
    public void terminate() {
        super.terminate();
        setView(null);
    }



}
