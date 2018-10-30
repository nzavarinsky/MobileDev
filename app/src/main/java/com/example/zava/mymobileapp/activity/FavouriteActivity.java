package com.example.zava.mymobileapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zava.mymobileapp.DB.DBHelp;
import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.adapters.CardAdapter;
import com.example.zava.mymobileapp.model.CardAlbum;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements CardAdapter.CardClickCallBack {
  private static final String sBUNDLE_EXTRA = "sBUNDLE_EXTRA";
  private static final String sEXTRA_ALBUM_ID = "sEXTRA_ALBUM_ID";
  private static final String sEXTRA_ALBUM_NAME = "sEXTRA_ALBUM_NAME";
  private static final String sEXTRA_ALBUM_IMAGE = "sEXTRA_ALBUM_IMAGE";
  private static final String sEXTRA_ALBUM_ARTIST_NAME = "sEXTRA_ALBUM_ARTIST_NAME";
  private static final String sEXTRA_ALBUM_RELEASE_DATE = "sEXTRA_ALBUM_RELEASE_DATE";
  private static final String sEXTRA_SPOTIFY_ACCESS_TOKEN = "sEXTRA_SPOTIFY_ACCESS_TOKEN";
  private static final String sEXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

  private RecyclerView mRecyclerView;
  private CardAdapter mCardAdapter;
  private List<CardAlbum> mCardAlbumListData;
  private Cursor c;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favourite);
    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_for_favourite_activity);

    loadFilms();
  }

  public void loadSpotifyNewReleaseData(List<CardAlbum> cardAlbumList) {

    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    if (cardAlbumList.size() > 0) {
      mCardAdapter = new CardAdapter(cardAlbumList, this);
    } else {
      Log.d("NULLList", "We are getting a Null List from the background thread !!!");
    }
    mRecyclerView.setAdapter(mCardAdapter);
    mCardAdapter.setCardClickCallBack(this);
  }

  private void loadFilms() {
//    mCardAlbumListData.clear();
    String albumName, artistName, albumImageURL, albumReleaseDate;
    DBHelp db = new DBHelp(this);
    CardAlbum cardAlbum;
    c = db.queueAll();
    mCardAlbumListData = new ArrayList<CardAlbum>();
    while (c.moveToNext()) {
      albumName = c.getString(0);
      artistName = c.getString(1);
      albumImageURL = c.getString(2);
      albumReleaseDate = c.getString(3);
      cardAlbum = new CardAlbum(albumName, artistName, albumImageURL, albumReleaseDate);
      mCardAlbumListData.add(cardAlbum);
    }
    loadSpotifyNewReleaseData(mCardAlbumListData);
    if (!(mCardAlbumListData.size() < 1)) {
      mRecyclerView.setAdapter(mCardAdapter);
    }
  }

  @Override
  public void onCardClick(int position) {
    CardAlbum cardAlbum = mCardAlbumListData.get(position);


    Intent intent = new Intent(this, DetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(sEXTRA_ALBUM_ID, cardAlbum.getAlbumId());
    bundle.putString(sEXTRA_ALBUM_NAME, cardAlbum.getAlbumName());
    bundle.putString(sEXTRA_ALBUM_ARTIST_NAME, cardAlbum.getArtistName());
    bundle.putString(sEXTRA_ALBUM_IMAGE, cardAlbum.getAlbumImageURL());
    bundle.putString(sEXTRA_ALBUM_RELEASE_DATE, cardAlbum.getAlbumReleaseDate());
    intent.putExtra(sBUNDLE_EXTRA, bundle);
    startActivity(intent);
  }

  @Override
  public void onCardButtonClick(int position) {
    CardAlbum cardAlbum = mCardAlbumListData.get(position);
    Intent intent = new Intent(this, ArtistActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(sEXTRA_ALBUM_ARTIST_ID, cardAlbum.getArtistId());
    bundle.putString(sEXTRA_ALBUM_ARTIST_NAME, cardAlbum.getArtistName());
    intent.putExtra(sBUNDLE_EXTRA, bundle);
    startActivity(intent);

  }

}
