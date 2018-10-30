package com.example.zava.mymobileapp.activity;

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

public class ArtistActivity extends AppCompatActivity implements ArtistAlbumsAdapter.ItemClickCallBack{

  private static final String sBUNDLE_EXTRA = "sBUNDLE_EXTRA";
  private static final String sEXTRA_ALBUM_ID = "sEXTRA_ALBUM_ID";
  private static final String sEXTRA_ALBUM_NAME = "sEXTRA_ALBUM_NAME";
  private static final String sEXTRA_ALBUM_IMAGE = "sEXTRA_ALBUM_IMAGE";
  private static final String sEXTRA_ALBUM_ARTIST_NAME = "sEXTRA_ALBUM_ARTIST_NAME";
  private static final String sEXTRA_ALBUM_RELEASE_DATE = "sEXTRA_ALBUM_RELEASE_DATE";
  private static final String sEXTRA_SPOTIFY_ACCESS_TOKEN = "sEXTRA_SPOTIFY_ACCESS_TOKEN";
  private static final String sEXTRA_ALBUM_ARTIST_ID = "EXTRA_ARTIST_ID";

    private AlbumArtist mAlbumArtistData;
    private List<ArtistAlbum> mArtistAlbumList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ArtistAlbumsAdapter mArtistAlbumsAdapter;
    private String mSpotifyAccessToken;
    private Bundle mExtras;
    private ProgressBar loadArtistActivity;
    private ImageView imgViewArtistDetailImage;
    private TextView txtViewArtistDetailArtistName;
    private TextView txtViewArtistDetailArtistGenres;
    private RatingBar ratingBarArtistDetailPopularity;


    public String getSpotifyAccessToken() {
        return mSpotifyAccessToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        mExtras = getIntent().getBundleExtra(sBUNDLE_EXTRA);
        loadArtistActivity = (ProgressBar) findViewById(R.id.load_artistactivity);
        imgViewArtistDetailImage = (ImageView)findViewById(R.id.imageView_artist_detail_image);
        txtViewArtistDetailArtistName = (TextView)findViewById(R.id.textView_artist_detail_artistName);
        txtViewArtistDetailArtistGenres = (TextView) findViewById(R.id.textView_artist_detail_artist_genres);
        ratingBarArtistDetailPopularity = (RatingBar)findViewById(R.id.ratingBar_artist_detail_artistPopularity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_artist_detail_artist_albums);
        mSpotifyAccessToken = mExtras.getString(sEXTRA_SPOTIFY_ACCESS_TOKEN);
        new SpotifyArtist(mSpotifyAccessToken, mExtras.getString(sEXTRA_ALBUM_ARTIST_ID)).execute();
    }


    public void loadSelectedArtist(AlbumArtist albumArtist){
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(albumArtist.getArtistImageUrl()).into(imgViewArtistDetailImage);

        txtViewArtistDetailArtistName.setText(albumArtist.getArtistName());
        for(String artistGenre: albumArtist.getArtistGenres()) {
            txtViewArtistDetailArtistGenres.append(artistGenre+" ");
        }

        ratingBarArtistDetailPopularity.setNumStars(5);
        ratingBarArtistDetailPopularity.setStepSize(1);
        ratingBarArtistDetailPopularity.setRating(albumArtist.getArtistPopularity());

        try {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
            mArtistAlbumList = albumArtist.getArtistAlbumList();
            if (mArtistAlbumList.size() > 0) {
                mArtistAlbumsAdapter = new ArtistAlbumsAdapter(mArtistAlbumList, this);
            }
            mRecyclerView.setAdapter(mArtistAlbumsAdapter);
            mArtistAlbumsAdapter.setItemClickCallBack(this);
        }catch (NullPointerException nP){
            Log.v("NullPointerException",nP.getMessage());
        }
    }


    @Override
    public void onItemClick(int position) {
        ArtistAlbum eachSingleArtistAlbum = mArtistAlbumList.get(position);

        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(sEXTRA_ALBUM_ID,eachSingleArtistAlbum.getArtistAlbumId());
        bundle.putString(sEXTRA_ALBUM_NAME,eachSingleArtistAlbum.getArtistAlbumName());
        bundle.putString(sEXTRA_ALBUM_IMAGE,eachSingleArtistAlbum.getArtistAlbumImageURL());
        bundle.putString(sEXTRA_ALBUM_ARTIST_NAME,mAlbumArtistData.getArtistName());
        bundle.putString(sEXTRA_ALBUM_RELEASE_DATE,eachSingleArtistAlbum
            .getArtistAlbumReleaseDate());
        bundle.putString(sEXTRA_SPOTIFY_ACCESS_TOKEN,getSpotifyAccessToken());
        intent.putExtra(sBUNDLE_EXTRA,bundle);
        startActivity(intent);
    }


    public void showProgress(){
        loadArtistActivity.setVisibility(View.VISIBLE);
        imgViewArtistDetailImage.setVisibility(View.INVISIBLE);
        txtViewArtistDetailArtistName.setVisibility(View.INVISIBLE);
        txtViewArtistDetailArtistGenres.setVisibility(View.INVISIBLE);
        ratingBarArtistDetailPopularity.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }


    public void showData(){
        loadArtistActivity.setVisibility(View.INVISIBLE);
        imgViewArtistDetailImage.setVisibility(View.VISIBLE);
        txtViewArtistDetailArtistName.setVisibility(View.VISIBLE);
        txtViewArtistDetailArtistGenres.setVisibility(View.VISIBLE);
        ratingBarArtistDetailPopularity.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    public class SpotifyArtist extends AsyncTask<Void,Void,AlbumArtist>{


        private String mSpotifyAccessToken;
        private String spotifyArtistId;



        public SpotifyArtist(String mSpotifyAccessToken, String spotifyArtistId) {
            this.mSpotifyAccessToken = mSpotifyAccessToken;
            this.spotifyArtistId = spotifyArtistId;
        }


        public String getSpotifyAccessToken() {
            return mSpotifyAccessToken;
        }

        public String getSpotifyArtistId() {
            return spotifyArtistId;
        }

        @Override
        protected void onPreExecute() {
            showProgress();
        }

        @Override
        protected AlbumArtist doInBackground(Void... voids) {
            AlbumArtist albumArtist = getSpotifyArtistDetail();
            return albumArtist;
        }

        @Override
        protected void onPostExecute(AlbumArtist albumArtist) {
            mAlbumArtistData = albumArtist;
            loadSelectedArtist(albumArtist);
            showData();
        }



        public SpotifyService getSpotifyService(){
            //Creates and configures a REST adapter for Spotify Web API.
            SpotifyApi wrapper = new SpotifyApi();
            if(!getSpotifyAccessToken().equals("") && getSpotifyAccessToken()!=null) {
                wrapper.setAccessToken(getSpotifyAccessToken());
            }else{
                Log.d("SpotifyArtist","Invalid Access Token");
            }
            SpotifyService spotifyService = wrapper.getService();
            return spotifyService;
        }

       
        public AlbumArtist getSpotifyArtistDetail(){

            String artistName = null;
            String artistImageURL=null;
            List<String> listedArtistGenres = new ArrayList<>();
            List<String> finalArtistGenres = new ArrayList<>();
            float artistPopularity;
            float artistAlbumPopularity;
            List<ArtistAlbum> listofAlbumsOfArtist = new ArrayList<>();
            String individualAlbumImageURL=null;

            SpotifyService spotifyService = getSpotifyService();
            ArtistSimple simpleArtist = spotifyService.getArtist(getSpotifyArtistId());
            artistName = simpleArtist.name;

            Artist artist = spotifyService.getArtist(getSpotifyArtistId());
            List<Image> artistImageList = artist.images;
            int maxArtistImageWidth = 0;
            for(Image image:artistImageList){
                if(image.width > maxArtistImageWidth)
                    maxArtistImageWidth = image.width;
            }
            for(Image image:artistImageList){
                if (image.width == maxArtistImageWidth) {
                    artistImageURL = image.url;
                }
            }
            for(String genre: artist.genres){
                listedArtistGenres.add(genre);
            }
            //Reducing the Artist Genres is favor of the displaying in the UI
            if(listedArtistGenres.size() > 3){
                finalArtistGenres = listedArtistGenres.subList(0,2);
            }else{
                finalArtistGenres = listedArtistGenres;
            }
            artistPopularity = ((float) (artist.popularity/100.0)*5);

            Pager<Album> simpleAlbumPager = spotifyService.getArtistAlbums(getSpotifyArtistId());
            List<Album> mArtistAlbumList = simpleAlbumPager.items;
            for(Album artistAlbum: mArtistAlbumList){
                Album individualAlbum = spotifyService.getAlbum(artistAlbum.id);
                artistAlbumPopularity = ((float) (individualAlbum.popularity/100.0)*5);

                int maxAlbumImageWidth = 0;
                for(Image albumImage: individualAlbum.images){
                    if(albumImage.width > maxAlbumImageWidth)
                        maxAlbumImageWidth = albumImage.width;
                }
                for (Image albumImage: individualAlbum.images){
                    if(albumImage.width==maxAlbumImageWidth){
                        individualAlbumImageURL = albumImage.url;
                    }
                }
                listofAlbumsOfArtist.add(new
                        ArtistAlbum(artistAlbum.id,individualAlbum.name,individualAlbum.release_date,artistAlbumPopularity,individualAlbumImageURL));
            }
            return new AlbumArtist(getSpotifyArtistId(),artistName,artistImageURL,finalArtistGenres,artistPopularity,listofAlbumsOfArtist);
        }
    }

}
