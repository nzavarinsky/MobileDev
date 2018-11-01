package com.example.zava.mymobileapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.adapters.ArtistAlbumsAdapter;
import com.example.zava.mymobileapp.model.AlbumArtist;
import com.example.zava.mymobileapp.model.ArtistAlbum;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;

public class ArtistActivity extends AppCompatActivity implements ArtistAlbumsAdapter.ItemClickCallBack {

  private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
  private static final String EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID";
  private static final String EXTRA_ALBUM_NAME = "EXTRA_ALBUM_NAME";
  private static final String EXTRA_ALBUM_IMAGE = "EXTRA_ALBUM_IMAGE";
  private static final String EXTRA_ALBUM_ARTIST_NAME = "EXTRA_ALBUM_ARTIST_NAME";
  private static final String EXTRA_ALBUM_RELEASE_DATE = "EXTRA_ALBUM_RELEASE_DATE";
  private static final String EXTRA_SPOTIFY_ACCESS_TOKEN = "EXTRA_SPOTIFY_ACCESS_TOKEN";
  private static final String EXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

  private AlbumArtist mAlbumArtistData;
  private List<ArtistAlbum> mArtistAlbumList = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private ArtistAlbumsAdapter mArtistAlbumsAdapter;
  private String mSpotifyAccessToken;
  private ProgressBar mLoadArtistActivity;
  private ImageView mImgViewArtistDetailImage;
  private TextView mTxtViewArtistDetailArtistName;
  private TextView mTxtViewArtistDetailArtistGenres;
  private RatingBar mRatingBarArtistDetailPopularity;


  public String getSpotifyAccessToken() {
    return mSpotifyAccessToken;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_artist);
    Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRA);
    mLoadArtistActivity = findViewById(R.id.load_artistactivity);
    mImgViewArtistDetailImage = findViewById(R.id.imageView_artist_detail_image);
    mTxtViewArtistDetailArtistName = findViewById(R.id.textView_artist_detail_artistName);
    mTxtViewArtistDetailArtistGenres = findViewById(R.id.textView_artist_detail_artist_genres);
    mRatingBarArtistDetailPopularity = findViewById(R.id.ratingBar_artist_detail_artistPopularity);
    mRecyclerView = findViewById(R.id.recyclerView_artist_detail_artist_albums);
    mSpotifyAccessToken = extras.getString(EXTRA_SPOTIFY_ACCESS_TOKEN);
    new SpotifyArtist(mSpotifyAccessToken, extras.getString(EXTRA_ALBUM_ARTIST_ID)).execute();
  }


  public void loadSelectedArtist(AlbumArtist albumArtist) {
    Picasso.with(this).setLoggingEnabled(true);
    Picasso.with(this).load(albumArtist.getArtistImageUrl()).into(mImgViewArtistDetailImage);

    mTxtViewArtistDetailArtistName.setText(albumArtist.getArtistName());
    for (String artistGenre : albumArtist.getArtistGenres()) {
      mTxtViewArtistDetailArtistGenres.append(artistGenre + " ");
    }

    mRatingBarArtistDetailPopularity.setNumStars(5);
    mRatingBarArtistDetailPopularity.setStepSize(1);
    mRatingBarArtistDetailPopularity.setRating(albumArtist.getArtistPopularity());

    try {
      mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
      mArtistAlbumList = albumArtist.getArtistAlbumList();
      if (mArtistAlbumList.size() > 0) {
        mArtistAlbumsAdapter = new ArtistAlbumsAdapter(mArtistAlbumList, this);
      }
      mRecyclerView.setAdapter(mArtistAlbumsAdapter);
      mArtistAlbumsAdapter.setItemClickCallBack(this);
    } finally {

    }
  }


  @Override
  public void onItemClick(int position) {
    ArtistAlbum eachSingleArtistAlbum = mArtistAlbumList.get(position);
    Intent intent = DetailActivity.getStartIntent(ArtistActivity.this,
        eachSingleArtistAlbum,
        mAlbumArtistData.getArtistName(),
        getSpotifyAccessToken());
  }


  public void showProgress() {
    mLoadArtistActivity.setVisibility(View.VISIBLE);
    mImgViewArtistDetailImage.setVisibility(View.INVISIBLE);
    mTxtViewArtistDetailArtistName.setVisibility(View.INVISIBLE);
    mTxtViewArtistDetailArtistGenres.setVisibility(View.INVISIBLE);
    mRatingBarArtistDetailPopularity.setVisibility(View.INVISIBLE);
    mRecyclerView.setVisibility(View.INVISIBLE);
  }


  public void showData() {
    mLoadArtistActivity.setVisibility(View.INVISIBLE);
    mImgViewArtistDetailImage.setVisibility(View.VISIBLE);
    mTxtViewArtistDetailArtistName.setVisibility(View.VISIBLE);
    mTxtViewArtistDetailArtistGenres.setVisibility(View.VISIBLE);
    mRatingBarArtistDetailPopularity.setVisibility(View.VISIBLE);
    mRecyclerView.setVisibility(View.VISIBLE);
  }


  public class SpotifyArtist extends AsyncTask<Void, Void, AlbumArtist> {


    private String mSpotifyAccessToken;
    private String spotifyArtistId;


    SpotifyArtist(String mSpotifyAccessToken, String spotifyArtistId) {
      this.mSpotifyAccessToken = mSpotifyAccessToken;
      this.spotifyArtistId = spotifyArtistId;
    }


    String getSpotifyAccessToken() {
      return mSpotifyAccessToken;
    }

    String getSpotifyArtistId() {
      return spotifyArtistId;
    }

    @Override
    protected void onPreExecute() {
      showProgress();
    }

    @Override
    protected AlbumArtist doInBackground(Void... voids) {
      return getSpotifyArtistDetail();
    }

    @Override
    protected void onPostExecute(AlbumArtist albumArtist) {
      mAlbumArtistData = albumArtist;
      loadSelectedArtist(albumArtist);
      showData();
    }


    SpotifyService getSpotifyService() {
      //Creates and configures a REST adapter for Spotify Web API.
      SpotifyApi wrapper = new SpotifyApi();
      if (!getSpotifyAccessToken().equals("") && getSpotifyAccessToken() != null) {
        wrapper.setAccessToken(getSpotifyAccessToken());
      } else {
        Log.d("SpotifyArtist", "Invalid Access Token");
      }
      return wrapper.getService();
    }


    AlbumArtist getSpotifyArtistDetail() {

      String artistName;
      String artistImageURL = null;
      List<String> listedArtistGenres = new ArrayList<>();
      List<String> finalArtistGenres;
      float artistPopularity;
      float artistAlbumPopularity;
      List<ArtistAlbum> listofAlbumsOfArtist = new ArrayList<>();
      String individualAlbumImageURL = null;

      SpotifyService spotifyService = getSpotifyService();
      ArtistSimple simpleArtist = spotifyService.getArtist(getSpotifyArtistId());
      artistName = simpleArtist.name;

      Artist artist = spotifyService.getArtist(getSpotifyArtistId());
      List<Image> artistImageList = artist.images;
      int maxArtistImageWidth = 0;
      for (Image image : artistImageList) {
        if (image.width > maxArtistImageWidth)
          maxArtistImageWidth = image.width;
      }
      for (Image image : artistImageList) {
        if (image.width == maxArtistImageWidth) {
          artistImageURL = image.url;
        }
      }
      listedArtistGenres.addAll(artist.genres);
      //Reducing the Artist Genres is favor of the displaying in the UI
      if (listedArtistGenres.size() > 3) {
        finalArtistGenres = listedArtistGenres.subList(0, 2);
      } else {
        finalArtistGenres = listedArtistGenres;
      }
      artistPopularity = ((float) (artist.popularity / 100.0) * 5);

      Pager<Album> simpleAlbumPager = spotifyService.getArtistAlbums(getSpotifyArtistId());
      List<Album> mArtistAlbumList = simpleAlbumPager.items;
      for (Album artistAlbum : mArtistAlbumList) {
        Album individualAlbum = spotifyService.getAlbum(artistAlbum.id);
        artistAlbumPopularity = ((float) (individualAlbum.popularity / 100.0) * 5);

        int maxAlbumImageWidth = 0;
        for (Image albumImage : individualAlbum.images) {
          if (albumImage.width > maxAlbumImageWidth)
            maxAlbumImageWidth = albumImage.width;
        }
        for (Image albumImage : individualAlbum.images) {
          if (albumImage.width == maxAlbumImageWidth) {
            individualAlbumImageURL = albumImage.url;
          }
        }
        listofAlbumsOfArtist.add(new
            ArtistAlbum(artistAlbum.id, individualAlbum.name, individualAlbum.release_date, artistAlbumPopularity, individualAlbumImageURL));
      }
      return new AlbumArtist(getSpotifyArtistId(), artistName, artistImageURL, finalArtistGenres, artistPopularity, listofAlbumsOfArtist);
    }
  }

}
