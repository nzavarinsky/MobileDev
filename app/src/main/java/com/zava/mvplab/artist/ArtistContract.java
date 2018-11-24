package com.zava.mvplab.artist;

/**
 * Created by Andrii Medvid on 11/21/18.
 * Copyright (c) 2018, LadbrokesCoral. All rights reserved.
 */
public interface ArtistContract {

    interface View extends com.zava.mvplab.base.Presenter.View {

        void showLoading();

        void hideLoading();

        void showArtistNotFoundMessage();

        void showConnectionErrorMessage();

        void showServerError();

        void renderArtists(java.util.List<com.zava.mvplab.artist.model.Artist> artists);

        void launchArtistDetail(com.zava.mvplab.artist.model.Artist artist);
    }

    interface Presenter {
        void launchArtistDetail(com.zava.mvplab.artist.model.Artist artist);

        void onSearchArtist(String name);
    }

    interface Interactor {
        io.reactivex.Observable<java.util.List<com.zava.mvplab.artist.model.Artist>> searchArtists(String query);
    }
}
