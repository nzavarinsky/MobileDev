package com.example.zava.mymobileapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class AudioListActivity extends AppCompatActivity{

    /*Your Client id goes here*/
    private static final String clientId = "973f03d1cf7b412eabf015fa6fa66b23";
    /*Your Redirect Uri goes here*/
    private static final String redirectUri = "spotify-meta-data-on-android://callback";

    private static final int request_Code = 1337;
    private static String accessToken;

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardAlbum> cardAlbumListData;
    private ProgressBar loadMainActivity;
    private Toolbar toolbar_main_activity;
    private SwipeRefreshLayout swipeContainer;


  public static String getAccessToken() {
        return accessToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        loadMainActivity = (ProgressBar) findViewById(R.id.load_mainactivity);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_for_main_activity);
        toolbar_main_activity = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar_main_activity);
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (clientId, AuthenticationResponse.Type.TOKEN, redirectUri);
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, request_Code, request);
      swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
      // Setup refresh listener which triggers new data loading
      swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
          new SpotifyNewRelease(accessToken).execute();
          swipeContainer.setRefreshing(false);
        }

      });


    }

    //authetnitcation
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request_Code){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode,data);
            if(response.getType()==AuthenticationResponse.Type.TOKEN){
                accessToken = response.getAccessToken();
            }else{
            }
        }
        new SpotifyNewRelease(accessToken).execute();
    }


    public void showProgress(){
        loadMainActivity.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    public void showData(){
        loadMainActivity.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    public void loadSpotifyNewReleaseData(List<CardAlbum> cardAlbumList){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(cardAlbumList.size() > 0) {
            cardAdapter = new CardAdapter(cardAlbumList, this);
        }else{
            Log.d("NULLList","Null list from background");
        }

        recyclerView.setAdapter(cardAdapter);
        Toast.makeText(AudioListActivity.this,"New Releases from Spotify",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_toolbar_overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItmId = item.getItemId();

        switch (selectedItmId){
            case (R.id.item_action_refresh):
                new SpotifyNewRelease(accessToken).execute();
                return true;
            case (R.id.find_web):
                Toast.makeText(AudioListActivity.this,"Find In Web Selected",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SpotifyNewRelease extends AsyncTask<Void,Void,List<CardAlbum>>{
        private String accessToken;
        private List<CardAlbum> cardAlbumList;

        public SpotifyNewRelease(String accessToken) {
            this.accessToken = accessToken;
        }
        public String getAccessToken() {
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
            cardAlbumListData = cardAlbumList;
            loadSpotifyNewReleaseData(cardAlbumList);
            showData();
        }

        public SpotifyService getSpotifyService(){
            SpotifyApi wrapper = new SpotifyApi();
            if(!getAccessToken().equals("") && getAccessToken()!=null) {
                wrapper.setAccessToken(getAccessToken());
            }else{
                Log.d("SpotifyNewRelease","Invalid Access Token");
            }
            SpotifyService spotifyService = wrapper.getService();
            return spotifyService;
        }


        public Album getSpotifyAlbumById(String albumId){
            SpotifyService spotifyService = getSpotifyService();
            Album spotifyAlbum = spotifyService.getAlbum(albumId);
            return spotifyAlbum;
        }

        public List<CardAlbum> getNewReleasedAlbums(){
            List<CardAlbum> cardAlbumList = new ArrayList<>();
            String albumId = null;
            String artistId = null;
            String artistName = null;
            String albumImageURL=null;
            String albumName = null;
            int albumPopularity;
            String albumReleaseDate;

            SpotifyService spotifyService = getSpotifyService();
            if(spotifyService!=null) {
                NewReleases newReleases = spotifyService.getNewReleases();
                Pager<AlbumSimple> albumSimplePager = newReleases.albums;
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

                    //Getting the Album Image for CardAlbum
                    List<Image> albumImages = simpleAlbum.images;
                    int maxWidth = 0;
                    for (Image albumImage : albumImages) {
                        if(albumImage.width > maxWidth)
                            maxWidth = albumImage.width;
                    }
                    for(Image albumImage : albumImages){
                        if (albumImage.width == maxWidth) {
                            albumImageURL = albumImage.url;
                        }
                    }
                    if (simpleAlbum.name != null && albumImageURL != null && artistName !=null) {
                        cardAlbumList.add(new CardAlbum(albumId,albumName,artistId,artistName,albumImageURL,albumPopularity,albumReleaseDate));
                    }
                }
            }else{
                Log.d("SpotifyNewRelease","Invalid Instance Of the SpotifyService");
            }
            return cardAlbumList;
        }
    }
}
