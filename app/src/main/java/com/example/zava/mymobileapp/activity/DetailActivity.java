package com.example.zava.mymobileapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zava.mymobileapp.db.DBHelp;
import com.example.zava.mymobileapp.model.ArtistAlbum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.adapters.AlbumTrackAdapter;
import com.example.zava.mymobileapp.model.AlbumTrack;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TrackSimple;

public class DetailActivity extends AppCompatActivity {

  private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
  private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
  private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
  private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
  private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
  private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
  private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";

  private List<AlbumTrack> mAlbumTrackList;
  private RecyclerView mRecyclerView;
  private AlbumTrackAdapter mAlbumTrackAdapter;
  private ProgressBar mLoadDetailActivity;
  private TextView mTxtViewAlbumDetailAlbumName;
  private TextView mTxtViewAlbumDetailArtistName;
  private TextView mTxtViewAlbumDetailAlbumReleaseDate;
  private ImageView mImgViewAlbumDetailAlbumImage;
  private Bundle mExtras;
  private DBHelp mDbHelp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    mLoadDetailActivity = findViewById(R.id.load_detailactivity);
    mTxtViewAlbumDetailAlbumName = findViewById(R.id.textView_album_detail_album_name);
    mTxtViewAlbumDetailArtistName =  findViewById(R.id.textView_album_detail_artist_name);
    mTxtViewAlbumDetailAlbumReleaseDate =  findViewById(R.id.textView_album_detail_album_release_date);
    mImgViewAlbumDetailAlbumImage =  findViewById(R.id.imageView_album_detail_image);
    mRecyclerView =  findViewById(R.id.recyclerView_for_detail_activity_track_details);
    mExtras = getIntent().getBundleExtra(BUNDLE_EXTRA);
    mDbHelp = new DBHelp(this);
    new SpotifyAlbum(mExtras.getString(EXTRA_ALBUM_ID),
        mExtras.getString(EXTRA_SPOTIFY_ACCESS_TOKEN)).execute();


  }

  public static Intent getStartIntent(Context context, ArtistAlbum eachSingleArtistAlbum, String
      artistName, String accessToken){
    Intent intent = new Intent(context, DetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(EXTRA_ALBUM_ID, eachSingleArtistAlbum.getArtistAlbumId());
    bundle.putString(EXTRA_ALBUM_NAME, eachSingleArtistAlbum.getArtistAlbumName());
    bundle.putString(EXTRA_ALBUM_IMAGE, eachSingleArtistAlbum.getArtistAlbumImageURL());
    bundle.putString(EXTRA_ALBUM_ARTIST_NAME, artistName);
    bundle.putString(EXTRA_ALBUM_RELEASE_DATE, eachSingleArtistAlbum
        .getArtistAlbumReleaseDate());
    bundle.putString(EXTRA_SPOTIFY_ACCESS_TOKEN, accessToken);
    intent.putExtra(BUNDLE_EXTRA, bundle);
    return intent;
  }


  public void loadSelectedAlbum() {
    try {
      Picasso.with(this).setLoggingEnabled(true);
      Picasso.with(this).load(mExtras.getString(EXTRA_ALBUM_IMAGE)).into
          (mImgViewAlbumDetailAlbumImage);
      mTxtViewAlbumDetailAlbumName.setText(mExtras.getString(EXTRA_ALBUM_NAME));
      mTxtViewAlbumDetailArtistName.setText(mExtras.getString
          (EXTRA_ALBUM_ARTIST_NAME));
      mTxtViewAlbumDetailAlbumReleaseDate.setText(mExtras.getString
          (EXTRA_ALBUM_RELEASE_DATE));
      mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      if (mAlbumTrackList.size() > 0) {
        mAlbumTrackAdapter = new AlbumTrackAdapter(mAlbumTrackList, this);
      }
      mRecyclerView.setAdapter(mAlbumTrackAdapter);
    } finally {
    }
  }

  public void showProgress() {
    mLoadDetailActivity.setVisibility(View.VISIBLE);
    mTxtViewAlbumDetailAlbumName.setVisibility(View.INVISIBLE);
    mTxtViewAlbumDetailArtistName.setVisibility(View.INVISIBLE);
    mTxtViewAlbumDetailAlbumReleaseDate.setVisibility(View.INVISIBLE);
    mImgViewAlbumDetailAlbumImage.setVisibility(View.INVISIBLE);
    mRecyclerView.setVisibility(View.INVISIBLE);
  }

  public void showData() {
    mLoadDetailActivity.setVisibility(View.INVISIBLE);
    mTxtViewAlbumDetailAlbumName.setVisibility(View.VISIBLE);
    mTxtViewAlbumDetailArtistName.setVisibility(View.VISIBLE);
    mTxtViewAlbumDetailAlbumReleaseDate.setVisibility(View.VISIBLE);
    mImgViewAlbumDetailAlbumImage.setVisibility(View.VISIBLE);
    mRecyclerView.setVisibility(View.VISIBLE);
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
      case (R.id.action_add_favourite):
        Toast.makeText(DetailActivity.this, "Add to favourites", Toast.LENGTH_SHORT).show();
        String alb_name = mExtras.getString(EXTRA_ALBUM_NAME);
        String art_name = mExtras.getString(EXTRA_ALBUM_ARTIST_NAME);
        saveToDB(alb_name, art_name, mExtras.getString(EXTRA_ALBUM_IMAGE), mExtras
            .getString(EXTRA_ALBUM_RELEASE_DATE));
        return true;
      case (R.id.remove_from_favourite):
        removeFromDB(mExtras.getString(EXTRA_ALBUM_IMAGE));
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  private void saveToDB(String albumName, String artistName, String
      albumImageURL, String albumReleaseDate) {

    boolean insert = mDbHelp.insert(albumName, artistName,
        albumImageURL, albumReleaseDate);
    if (insert) {
      Toast.makeText(DetailActivity.this, "Add to favourites", Toast.LENGTH_SHORT).show();
    }
  }

  private void removeFromDB(String albumImageURL) {
    mDbHelp.delete(albumImageURL);
    Toast.makeText(DetailActivity.this, "AdRemoved from favourites", Toast.LENGTH_SHORT).show();
  }


  public class SpotifyAlbum extends AsyncTask<Void, Void, List<AlbumTrack>> {

    private String spotifyAlbumId;
    private String spotifyAccessToken;

    SpotifyAlbum(String spotifyAlbumId, String spotifyAccessToken) {
      this.spotifyAlbumId = spotifyAlbumId;
      this.spotifyAccessToken = spotifyAccessToken;
    }

    String getSpotifyAlbumId() {
      return spotifyAlbumId;
    }

    String getSpotifyAccessToken() {
      return spotifyAccessToken;
    }

    @Override
    protected void onPreExecute() {
      showProgress();
    }

    @Override
    protected List<AlbumTrack> doInBackground(Void... voids) {
      List<AlbumTrack> mAlbumTrackList;
      mAlbumTrackList = getAlbumTracks();
      return mAlbumTrackList;
    }

    @Override
    protected void onPostExecute(List<AlbumTrack> albumTracks) {
      mAlbumTrackList = albumTracks;
      loadSelectedAlbum();
      showData();
    }

    SpotifyService getSpotifyService() {
      //Creates and configures a REST adapter for Spotify Web API.
      SpotifyApi wrapper = new SpotifyApi();
      if (!getSpotifyAccessToken().equals("") && getSpotifyAccessToken() != null) {
        wrapper.setAccessToken(getSpotifyAccessToken());
      } else {
        Log.d("SpotifyAlbum", "Invalid Access Token");
      }
      return wrapper.getService();
    }

    List<AlbumTrack> getAlbumTracks() {
      List<AlbumTrack> mAlbumTrackList = new ArrayList<>();
      String trackId;
      String trackName;
      String trackDuration;
      float trackPopularity;

      try {
        SpotifyService spotifyService = getSpotifyService();
        Album spotifyAlbum = spotifyService.getAlbum(getSpotifyAlbumId());
        Pager<TrackSimple> trackSimplePager = spotifyAlbum.tracks;
        List<TrackSimple> simpleTrackList = trackSimplePager.items;

        for (TrackSimple simpleTrack : simpleTrackList) {
          trackId = simpleTrack.id;
          trackName = simpleTrack.name;
          int seconds = (int) ((simpleTrack.duration_ms / 1000) % 60);
          int minutes = (int) ((simpleTrack.duration_ms / 1000) / 60);
          trackDuration = minutes + "." + seconds;
          Track spotifyTrack = spotifyService.getTrack(trackId);
          trackPopularity = ((float) (spotifyTrack.popularity / 100.0) * 5);
          mAlbumTrackList.add(new AlbumTrack(trackId, trackName, trackDuration, trackPopularity));
        }
      } catch (NullPointerException nP) {
        Log.d("SpotifyAlbum", nP.getMessage());
      }
      return mAlbumTrackList;
    }

  }
}
