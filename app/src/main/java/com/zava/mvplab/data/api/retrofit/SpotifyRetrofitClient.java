package com.zava.mvplab.data.api.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zava.mvplab.data.api.Constants;
import com.zava.mvplab.data.api.retrofit.deserializer.ArtistsDeserializer;
import com.zava.mvplab.data.api.retrofit.deserializer.TracksDeserializer;
import com.zava.mvplab.data.model.Artist;
import com.zava.mvplab.data.model.Track;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class SpotifyRetrofitClient {

  private SpotifyRetrofitService spotifyRetrofitService;

  public SpotifyRetrofitClient() {
    initRetrofit();
  }

  private void initRetrofit() {
    Retrofit retrofit = retrofitBuilder();
    spotifyRetrofitService = retrofit.create(getSpotifyServiceClass());
  }

  private Retrofit retrofitBuilder() {
    return new Retrofit.Builder().baseUrl(Constants.SPOTIFY_API)
        .addConverterFactory(GsonConverterFactory.create(getSpotifyDeserializer()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getOkHttpClient())
        .build();
  }

  private OkHttpClient getOkHttpClient() {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    ApiInterceptor apiInterceptor = new ApiInterceptor();
    client.addInterceptor(apiInterceptor);
    return client.build();
  }


  private Class<SpotifyRetrofitService> getSpotifyServiceClass() {
    return SpotifyRetrofitService.class;
  }

  private Gson getSpotifyDeserializer() {
    return new GsonBuilder().registerTypeAdapter(new TypeToken<List<Artist>>() {
    }.getType(), new ArtistsDeserializer<Artist>())
        .registerTypeAdapter(new TypeToken<List<Track>>() {
        }.getType(), new TracksDeserializer<Track>())
        .create();
  }

  protected SpotifyRetrofitService getSpotifyService() {
    return spotifyRetrofitService;
  }
}

