package com.zava.mvplab.artist;

import android.content.Context;
import android.content.Intent;


public interface ArtistContract {

    interface View extends com.zava.mvplab.base.Presenter.View {

        void showLoading();

        void hideLoading();

        void showArtistNotFoundMessage();

        void showConnectionErrorMessage();

        void showServerError();

        void renderArtists(java.util.List<com.zava.mvplab.artist.model.Artist> artists);


        Intent getStartIntent(Context context, Artist artist);
    }

    interface Presenter {
        void launchArtistDetail(Context context,Artist artist);

        void onSearchArtist(String name);
    }

    interface Interactor {
        io.reactivex.Observable<java.util.List<com.zava.mvplab.artist.model.Artist>> searchArtists(String query);
    }
}
