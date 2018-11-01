package com.example.zava.mymobileapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zava.mymobileapp.model.ArtistAlbum;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;
import java.util.List;

import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.adapters.CardAdapter;
import com.example.zava.mymobileapp.model.CardAlbum;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;

public class AudioListActivity extends AppCompatActivity implements CardAdapter.CardClickCallBack {

  private static final String clientId = "973f03d1cf7b412eabf015fa6fa66b23";
  private static final String redirectUri = "spotify-meta-data-on-android://callback";

  private static final int request_Code = 1337;

  private static String accessToken;

  private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
  private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
  private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
  private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
  private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
  private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
  private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";
  private static final String EXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

  private RecyclerView mRecyclerView;
  private CardAdapter mCardAdapter;
  private List<CardAlbum> mCardAlbumListData;
  private ProgressBar mLoadMainActivity;

  public static String getAccessToken() {
    return accessToken;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_audio_list);
    mLoadMainActivity = findViewById(R.id.load_mainactivity);
    mRecyclerView = findViewById(R.id.recyclerview_for_main_activity);
    Toolbar toolbarMainActivity = findViewById(R.id.activity_main_toolbar);
    setSupportActionBar(toolbarMainActivity);
    AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
        (clientId, AuthenticationResponse.Type.TOKEN, redirectUri);
    AuthenticationRequest request = builder.build();
    AuthenticationClient.openLoginActivity(this, request_Code, request);

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == request_Code) {
      AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
      if (response.getType() == AuthenticationResponse.Type.TOKEN) {
        accessToken = response.getAccessToken();
      }
    }
    new SpotifyNewRelease(accessToken).execute();
  }


  public void showProgress() {
    mLoadMainActivity.setVisibility(View.VISIBLE);
    mRecyclerView.setVisibility(View.INVISIBLE);
  }


  public void showData() {
    mLoadMainActivity.setVisibility(View.INVISIBLE);
    mRecyclerView.setVisibility(View.VISIBLE);
  }

  public void loadSpotifyNewReleaseData(List<CardAlbum> cardAlbumList) {
    //Setting the LayoutManager for the RecyclerView
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    //Instantiating CardAdapter class using the defined constructor
    if (cardAlbumList.size() > 0) {
      mCardAdapter = new CardAdapter(cardAlbumList, this);
    } else {
      Log.d("NULLList", "We are getting a Null List from the background thread !!!");
    }
    //Giving the reference of the Adapter class instance to the RecyclerView
    mRecyclerView.setAdapter(mCardAdapter);
    //Giving the reference of the Callback interface to the Card Adapter so that the CallbackInterface methods can be called upon events.
    mCardAdapter.setCardClickCallBack(this);
    Toast.makeText(AudioListActivity.this, "New Releases from Spotify", Toast.LENGTH_SHORT).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.mainactivity_toolbar_overflowmenu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int selectedItmId = item.getItemId();

    switch (selectedItmId) {
      case (R.id.item_action_refresh):
        new SpotifyNewRelease(accessToken).execute();
        return true;
      case (R.id.look_favourites):
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void getStartIntent(CardAlbum cardAlbum) {
    Intent intent = new Intent(this, DetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(EXTRA_ALBUM_ID, cardAlbum.getAlbumId());
    bundle.putString(EXTRA_ALBUM_NAME, cardAlbum.getAlbumName());
    bundle.putString(EXTRA_ALBUM_ARTIST_NAME, cardAlbum.getArtistName());
    bundle.putString(EXTRA_ALBUM_IMAGE, cardAlbum.getAlbumImageURL());
    bundle.putString(EXTRA_ALBUM_RELEASE_DATE, cardAlbum.getAlbumReleaseDate());
    bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN, getAccessToken());
    intent.putExtra(BUNDLE_EXTRA, bundle);
    startActivity(intent);
  }

  @Override
  public void onCardClick(int position) {
    CardAlbum cardAlbum = mCardAlbumListData.get(position);
    getStartIntent(cardAlbum);
  }


  @Override
  public void onCardButtonClick(int position) {

    CardAlbum cardAlbum = mCardAlbumListData.get(position);
    Intent intent = new Intent(this, ArtistActivity.class);
    Bundle bundle = new Bundle();
    new Bundle();
    bundle.putString(EXTRA_ALBUM_ARTIST_ID, cardAlbum.getArtistId());
    bundle.putString(EXTRA_ALBUM_ARTIST_NAME, cardAlbum.getArtistName());
    bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN, getAccessToken());
    intent.putExtra(BUNDLE_EXTRA, bundle);
    startActivity(intent);
  }


  public class SpotifyNewRelease extends AsyncTask<Void, Void, List<CardAlbum>> {
    private String accessToken;
    private List<CardAlbum> cardAlbumList;


    SpotifyNewRelease(String accessToken) {
      this.accessToken = accessToken;
    }

    String getAccessToken() {
      return accessToken;
    }

    @Override
    protected void onPreExecute() {
      showProgress();
    }

    @Override
    protected List<CardAlbum> doInBackground(Void... voids) {
      cardAlbumList = getNewReleasedAlbums();
      return cardAlbumList;
    }

    @Override
    protected void onPostExecute(List<CardAlbum> cardAlbumList) {
      mCardAlbumListData = cardAlbumList;
      loadSpotifyNewReleaseData(cardAlbumList);
      showData();
    }

    SpotifyService getSpotifyService() {
      //Creates and configures a REST adapter for Spotify Web API.
      SpotifyApi wrapper = new SpotifyApi();
      if (!getAccessToken().equals("") && getAccessToken() != null) {
        wrapper.setAccessToken(getAccessToken());
      } else {
        Log.d("SpotifyNewRelease", "Invalid Access Token");
      }
      SpotifyService spotifyService;
      spotifyService = wrapper.getService();
      return spotifyService;
    }

    Album getSpotifyAlbumById(String albumId) {
      SpotifyService spotifyService = getSpotifyService();
      return spotifyService.getAlbum(albumId);
    }

    List<CardAlbum> getNewReleasedAlbums() {
      List<CardAlbum> cardAlbumList = new ArrayList<>();
      String albumId;
      String artistId = null;
      String artistName = null;
      String albumImageURL = null;
      String albumName;
      int albumPopularity;
      String albumReleaseDate;

      SpotifyService spotifyService = getSpotifyService();
      if (spotifyService != null) {
        NewReleases newReleases = spotifyService.getNewReleases();
        System.out.println(spotifyService.getNewReleases());
        Pager<AlbumSimple> albumSimplePager = newReleases.albums;

        //Getting the Album Name for CardAlbum
        List<AlbumSimple> albumSimpleList = albumSimplePager.items;
        for (AlbumSimple simpleAlbum : albumSimpleList) {
          albumId = simpleAlbum.id;
          albumName = simpleAlbum.name;

          //Getting the list of Album Artists for CardAlbum
          Album album = getSpotifyAlbumById(albumId);
          albumPopularity = album.popularity;
          albumReleaseDate = album.release_date;
          List<ArtistSimple> simpleArtistList = album.artists;
          for (ArtistSimple simpleArtist : simpleArtistList) {
            artistId = simpleArtist.id;
            artistName = simpleArtist.name;
          }

          List<Image> albumImages = simpleAlbum.images;
          int maxWidth = 0;
          for (Image albumImage : albumImages) {
            if (albumImage.width > maxWidth)
              maxWidth = albumImage.width;
          }
          for (Image albumImage : albumImages) {
            if (albumImage.width == maxWidth) {
              albumImageURL = albumImage.url;
            }
          }
          //Constructing the List of CardAlbumInstances
          if (simpleAlbum.name != null && albumImageURL != null && artistName != null) {
            cardAlbumList.add(new CardAlbum(albumId, albumName, artistId, artistName, albumImageURL, albumPopularity, albumReleaseDate));
          }
        }
      } else {
        Log.d("SpotifyNewRelease", "Invalid Instance Of the SpotifyService");
      }
      return cardAlbumList;
    }
  }
}
