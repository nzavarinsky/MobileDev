package com.example.zava.mymobileapp.activity;

import android.content.Intent;
import android.database.Cursor;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zava.mymobileapp.DB.DBHelp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

  private List<AlbumTrack> albumTrackList;
  private RecyclerView recyclerView;
  private AlbumTrackAdapter albumTrackAdapter;
  private ProgressBar load_detailactivity;
  private TextView textView_textView_album_detail_album_name;
  private TextView textView_textView_album_detail_artist_name;
  private TextView textView_textView_album_detail_album_release_date;
  private ImageView imageView_imageView_album_detail_image;
  private String fullScreenInd;
  private Toolbar toolbar;
  private Bundle extras;
  private boolean zoomOut = false;
  private DBHelp dbHelp;
  private Cursor cursor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    load_detailactivity = (ProgressBar) findViewById(R.id.load_detailactivity);
    textView_textView_album_detail_album_name = (TextView) findViewById(R.id.textView_album_detail_album_name);
    textView_textView_album_detail_artist_name = (TextView) findViewById(R.id.textView_album_detail_artist_name);
    textView_textView_album_detail_album_release_date = (TextView) findViewById(R.id.textView_album_detail_album_release_date);
    imageView_imageView_album_detail_image = (ImageView) findViewById(R.id.imageView_album_detail_image);
    recyclerView = (RecyclerView) findViewById(R.id.recyclerView_for_detail_activity_track_details);
    extras = getIntent().getBundleExtra(BUNDLE_EXTRA);
    dbHelp = new DBHelp(this);
    new SpotifyAlbum(extras.getString(EXTRA_ALBUM_ID),
        extras.getString(EXTRA_SPOTIFY_ACCESS_TOKEN)).execute();


  }

  public void loadSelectedAlbum(List<AlbumTrack> albumTracks) {
    try {

      //Picasso Experiment Begin
      Picasso.with(this).setLoggingEnabled(true);
      Picasso.with(this).load(extras.getString(EXTRA_ALBUM_IMAGE)).into(imageView_imageView_album_detail_image);
      //Experiment Experiment End
      textView_textView_album_detail_album_name.setText(extras.getString(EXTRA_ALBUM_NAME));
      textView_textView_album_detail_artist_name.setText(extras.getString(EXTRA_ALBUM_ARTIST_NAME));
      textView_textView_album_detail_album_release_date.setText(extras.getString(EXTRA_ALBUM_RELEASE_DATE));

      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      if (albumTrackList.size() > 0) {
        albumTrackAdapter = new AlbumTrackAdapter(albumTrackList, this);
      }
      recyclerView.setAdapter(albumTrackAdapter);
    } catch (NullPointerException nP) {
      Log.v("NullPointerException", nP.getMessage());
    }
  }

  public void showProgress() {
    load_detailactivity.setVisibility(View.VISIBLE);
    textView_textView_album_detail_album_name.setVisibility(View.INVISIBLE);
    textView_textView_album_detail_artist_name.setVisibility(View.INVISIBLE);
    textView_textView_album_detail_album_release_date.setVisibility(View.INVISIBLE);
    imageView_imageView_album_detail_image.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
  }

  public void showData() {
    load_detailactivity.setVisibility(View.INVISIBLE);
    textView_textView_album_detail_album_name.setVisibility(View.VISIBLE);
    textView_textView_album_detail_artist_name.setVisibility(View.VISIBLE);
    textView_textView_album_detail_album_release_date.setVisibility(View.VISIBLE);
    imageView_imageView_album_detail_image.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
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
        String alb_name = extras.getString(EXTRA_ALBUM_NAME);
        String art_name = extras.getString(EXTRA_ALBUM_ARTIST_NAME);
        saveToDB(alb_name, art_name, extras.getString(EXTRA_ALBUM_IMAGE), extras
            .getString(EXTRA_ALBUM_RELEASE_DATE));
        return true;
      case (R.id.remove_from_favourite):
        removeFromDB(extras.getString(EXTRA_ALBUM_IMAGE));
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  private void saveToDB(String albumName, String artistName, String
      albumImageURL, String albumReleaseDate) {

    boolean insert = dbHelp.insert(albumName, artistName,
        albumImageURL, albumReleaseDate);
    if (insert) {
      Toast.makeText(DetailActivity.this, "Add to favourites", Toast.LENGTH_SHORT).show();
    }
  }

  private void removeFromDB(String albumImageURL) {
    dbHelp.delete(albumImageURL);
    Toast.makeText(DetailActivity.this, "AdRemoved from favourites", Toast.LENGTH_SHORT).show();
  }

  
  public class SpotifyAlbum extends AsyncTask<Void, Void, List<AlbumTrack>> {

    private String spotifyAlbumId;
    private String spotifyAccessToken;

    public SpotifyAlbum(String spotifyAlbumId, String spotifyAccessToken) {
      this.spotifyAlbumId = spotifyAlbumId;
      this.spotifyAccessToken = spotifyAccessToken;
    }

    public String getSpotifyAlbumId() {
      return spotifyAlbumId;
    }

    public String getSpotifyAccessToken() {
      return spotifyAccessToken;
    }

    @Override
    protected void onPreExecute() {
      showProgress();
    }

    @Override
    protected List<AlbumTrack> doInBackground(Void... voids) {
      List<AlbumTrack> albumTrackList = new ArrayList<>();
      albumTrackList = getAlbumTracks();
      return albumTrackList;
    }

    @Override
    protected void onPostExecute(List<AlbumTrack> albumTracks) {
      albumTrackList = albumTracks;
      loadSelectedAlbum(albumTracks);
      showData();
    }

    public SpotifyService getSpotifyService() {
      //Creates and configures a REST adapter for Spotify Web API.
      SpotifyApi wrapper = new SpotifyApi();
      if (!getSpotifyAccessToken().equals("") && getSpotifyAccessToken() != null) {
        wrapper.setAccessToken(getSpotifyAccessToken());
      } else {
        Log.d("SpotifyAlbum", "Invalid Access Token");
      }
      SpotifyService spotifyService = wrapper.getService();
      return spotifyService;
    }

    public List<AlbumTrack> getAlbumTracks() {
      List<AlbumTrack> albumTrackList = new ArrayList<>();
      String trackId = null;
      String trackName = null;
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
          albumTrackList.add(new AlbumTrack(trackId, trackName, trackDuration, trackPopularity));
        }
      } catch (NullPointerException nP) {
        Log.d("SpotifyAlbum", nP.getMessage());
      }
      return albumTrackList;
    }

  }
}
