package com.example.zava.mymobileapp.activity;

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

import com.example.zava.mymobileapp.DB.DBHelp;
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

  private static final String sBUNDLE_EXTRA = "sBUNDLE_EXTRA";
  private static final String sEXTRA_ALBUM_ID = "sEXTRA_ALBUM_ID";
  private static final String sEXTRA_ALBUM_NAME = "sEXTRA_ALBUM_NAME";
  private static final String sEXTRA_ALBUM_IMAGE = "sEXTRA_ALBUM_IMAGE";
  private static final String sEXTRA_ALBUM_ARTIST_NAME = "sEXTRA_ALBUM_ARTIST_NAME";
  private static final String sEXTRA_ALBUM_RELEASE_DATE = "sEXTRA_ALBUM_RELEASE_DATE";
  private static final String sEXTRA_SPOTIFY_ACCESS_TOKEN = "sEXTRA_SPOTIFY_ACCESS_TOKEN";

  private List<AlbumTrack> mAlbumTrackList;
  private RecyclerView mRecyclerView;
  private AlbumTrackAdapter mAlbumTrackAdapter;
  private ProgressBar loadDetailActivity;
  private TextView txtViewAlbumDetailAlbumName;
  private TextView txtViewAlbumDetailArtistName;
  private TextView txtViewAlbumDetailAlbumReleaseDate;
  private ImageView imgViewAlbumDetailAlbumImage;
  private Bundle extras;
  private DBHelp dbHelp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    loadDetailActivity = (ProgressBar) findViewById(R.id.load_detailactivity);
    txtViewAlbumDetailAlbumName = (TextView) findViewById(R.id.textView_album_detail_album_name);
    txtViewAlbumDetailArtistName = (TextView) findViewById(R.id.textView_album_detail_artist_name);
    txtViewAlbumDetailAlbumReleaseDate = (TextView) findViewById(R.id.textView_album_detail_album_release_date);
    imgViewAlbumDetailAlbumImage = (ImageView) findViewById(R.id.imageView_album_detail_image);
    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_for_detail_activity_track_details);
    extras = getIntent().getBundleExtra(sBUNDLE_EXTRA);
    dbHelp = new DBHelp(this);
    new SpotifyAlbum(extras.getString(sEXTRA_ALBUM_ID),
        extras.getString(sEXTRA_SPOTIFY_ACCESS_TOKEN)).execute();


  }

  public void loadSelectedAlbum(List<AlbumTrack> albumTracks) {
    try {

      //Picasso Experiment Begin
      Picasso.with(this).setLoggingEnabled(true);
      Picasso.with(this).load(extras.getString(sEXTRA_ALBUM_IMAGE)).into
          (imgViewAlbumDetailAlbumImage);
      //Experiment Experiment End
      txtViewAlbumDetailAlbumName.setText(extras.getString(sEXTRA_ALBUM_NAME));
      txtViewAlbumDetailArtistName.setText(extras.getString
          (sEXTRA_ALBUM_ARTIST_NAME));
      txtViewAlbumDetailAlbumReleaseDate.setText(extras.getString
          (sEXTRA_ALBUM_RELEASE_DATE));

      mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      if (mAlbumTrackList.size() > 0) {
        mAlbumTrackAdapter = new AlbumTrackAdapter(mAlbumTrackList, this);
      }
      mRecyclerView.setAdapter(mAlbumTrackAdapter);
    } catch (NullPointerException nP) {
      Log.v("NullPointerException", nP.getMessage());
    }
  }

  public void showProgress() {
    loadDetailActivity.setVisibility(View.VISIBLE);
    txtViewAlbumDetailAlbumName.setVisibility(View.INVISIBLE);
    txtViewAlbumDetailArtistName.setVisibility(View.INVISIBLE);
    txtViewAlbumDetailAlbumReleaseDate.setVisibility(View.INVISIBLE);
    imgViewAlbumDetailAlbumImage.setVisibility(View.INVISIBLE);
    mRecyclerView.setVisibility(View.INVISIBLE);
  }

  public void showData() {
    loadDetailActivity.setVisibility(View.INVISIBLE);
    txtViewAlbumDetailAlbumName.setVisibility(View.VISIBLE);
    txtViewAlbumDetailArtistName.setVisibility(View.VISIBLE);
    txtViewAlbumDetailAlbumReleaseDate.setVisibility(View.VISIBLE);
    imgViewAlbumDetailAlbumImage.setVisibility(View.VISIBLE);
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
        String alb_name = extras.getString(sEXTRA_ALBUM_NAME);
        String art_name = extras.getString(sEXTRA_ALBUM_ARTIST_NAME);
        saveToDB(alb_name, art_name, extras.getString(sEXTRA_ALBUM_IMAGE), extras
            .getString(sEXTRA_ALBUM_RELEASE_DATE));
        return true;
      case (R.id.remove_from_favourite):
        removeFromDB(extras.getString(sEXTRA_ALBUM_IMAGE));
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
      List<AlbumTrack> mAlbumTrackList = new ArrayList<>();
      mAlbumTrackList = getAlbumTracks();
      return mAlbumTrackList;
    }

    @Override
    protected void onPostExecute(List<AlbumTrack> albumTracks) {
      mAlbumTrackList = albumTracks;
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
      List<AlbumTrack> mAlbumTrackList = new ArrayList<>();
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
          mAlbumTrackList.add(new AlbumTrack(trackId, trackName, trackDuration, trackPopularity));
        }
      } catch (NullPointerException nP) {
        Log.d("SpotifyAlbum", nP.getMessage());
      }
      return mAlbumTrackList;
    }

  }
}
