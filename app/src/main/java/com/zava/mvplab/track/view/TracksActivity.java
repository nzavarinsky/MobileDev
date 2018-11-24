
package com.zava.mvplab.track.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zava.mvplab.data.api.Constants;
import com.zava.mvplab.data.api.client.SpotifyClient;
import com.zava.mvplab.artist.model.Artist;
import com.zava.mvplab.track.TracksInteractor;
import com.zava.mvplab.track.TracksPresenter;
import com.zava.mvplab.track.model.Track;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import com.zava.mvplab.R;

public class TracksActivity extends AppCompatActivity
    implements com.zava.mvplab.track.TrackContract.View, AppBarLayout.OnOffsetChangedListener {

  public static final String EXTRA_REPOSITORY = "EXTRA_ARTIST";

  public @BindView(R.id.toolbar)
  Toolbar toolbar;
  public @BindView(R.id.appbar_artist)
  AppBarLayout appbar_artist;
  public @BindView(R.id.iv_collapsing_artist)
  ImageView imageView_collaplsedArtist;
  public @BindView(R.id.civ_artist)
  CircleImageView circleImageView_artist;
  public @BindView(R.id.txt_title_artist)
  TextView textView_artist;
  public @BindView(R.id.txt_title_tracks)
  TextView textView_tracks;
  public @BindView(R.id.txt_followers_artist)
  TextView textView_followers;
  public @BindView(R.id.txt_subtitle_artist)
  TextView textView_subTitle;
  public @BindView(R.id.rv_tracks)
  RecyclerView recyclerView_tracks;
  public @BindView(R.id.pv_tracks)
  ProgressBar progressBar_track;
  public @BindView(R.id.iv_tracks)
  ImageView imageView_tracks;
  public @BindView(R.id.txt_line_tracks)
  TextView txt_line_tracks;

  private TracksPresenter tracksPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tracks);

    ButterKnife.bind(this);
    setupToolbar();
    setupRecyclerView();

    tracksPresenter = new TracksPresenter(new TracksInteractor(new SpotifyClient()));
    tracksPresenter.setView(this);

    Artist artist = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
    initializeViews(artist);

    tracksPresenter.onSearchTracks(artist.id);
  }

  @Override
  public void showLoading() {
    progressBar_track.setVisibility(View.VISIBLE);
    imageView_tracks.setVisibility(View.GONE);
    txt_line_tracks.setVisibility(View.GONE);
    recyclerView_tracks.setVisibility(View.GONE);
  }

  @Override
  public void hideLoading() {
    progressBar_track.setVisibility(View.GONE);
    recyclerView_tracks.setVisibility(View.VISIBLE);
  }

  @Override
  public void showTracksNotFoundMessage() {
    progressBar_track.setVisibility(View.GONE);
    txt_line_tracks.setVisibility(View.VISIBLE);
    imageView_tracks.setVisibility(View.VISIBLE);
    txt_line_tracks.setText(R.string.error_tracks_not_found);
    imageView_tracks.setImageDrawable(ContextCompat.getDrawable(context(), R.mipmap.ic_not_found));
  }

  @Override
  public void showConnectionErrorMessage() {
    progressBar_track.setVisibility(View.GONE);
    txt_line_tracks.setVisibility(View.VISIBLE);
    imageView_tracks.setVisibility(View.VISIBLE);
    txt_line_tracks.setText(R.string.error_internet_connection);
    imageView_tracks.setImageDrawable(ContextCompat.getDrawable(TracksActivity.this, R.mipmap.ic_not_internet));
  }

  @Override
  public void renderTracks(List<Track> tracks) {
    TracksAdapter adapter = (TracksAdapter) recyclerView_tracks.getAdapter();
    adapter.setTracks(tracks);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void launchTrackDetail(List<Track> tracks, Track track, int position) {
  }

  @Override
  public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    onOffsetChangedState(appBarLayout, verticalOffset);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void onOffsetChangedState(AppBarLayout appBarLayout, int verticalOffset) {
    if (verticalOffset == 0) {
      hideAndShowTitleToolbar(View.GONE);
    } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
      hideAndShowTitleToolbar(View.VISIBLE);
    } else {
      hideAndShowTitleToolbar(View.GONE);
    }
  }

  private void hideAndShowTitleToolbar(int visibility) {
    textView_tracks.setVisibility(visibility);
    textView_subTitle.setVisibility(visibility);
  }

  private void setupRecyclerView() {
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    recyclerView_tracks.setLayoutManager(linearLayoutManager);
    TracksAdapter adapter = new TracksAdapter();
    adapter.setItemClickListener(
        (tracks, track, position) -> tracksPresenter.launchArtistDetail(tracks, track, position));
    recyclerView_tracks.setAdapter(adapter);

    appbar_artist.addOnOffsetChangedListener(this);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayUseLogoEnabled(false);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  private void initializeViews(Artist artist) {
    if (artist.mArtistImages.isEmpty()) {
      final String imageHolder =
          Constants.Serialized.IMAGE_URL;
      circleImageView_artist.setVisibility(View.GONE);
      Picasso.with(this)
          .load(imageHolder)
          .transform(new BlurEffectUtils(this, 20))
          .into(imageView_collaplsedArtist);
    } else {
      Picasso.with(this)
          .load(artist.mArtistImages.get(0).url)
          .transform(new BlurEffectUtils(this, 20))
          .into(imageView_collaplsedArtist);
      Picasso.with(this).load(artist.mArtistImages.get(0).url).into(circleImageView_artist);
    }


    textView_artist.setText(artist.name);
    textView_subTitle.setText(artist.name);
    String totalFollowers = getResources().getQuantityString(R.plurals.numberOfFollowers,
        artist.followers.totalFollowers, artist.followers.totalFollowers);
    textView_followers.setText(totalFollowers);
  }


  @Override
  public Context context() {
    return TracksActivity.this;
  }
}