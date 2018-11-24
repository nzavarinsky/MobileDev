

package com.zava.mvplab.artist.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zava.mvplab.data.api.client.SpotifyClient;
import com.zava.mvplab.track.view.TracksActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.zava.mvplab.R;

public class ArtistsFragment extends Fragment
    implements com.zava.mvplab.artist.ArtistContract.View, SearchView.OnQueryTextListener {

  public @BindView(R.id.toolbar)
  Toolbar toolbar;
  public @BindView(R.id.rv_artists)
  RecyclerView artist_recyclerView;
  public @BindView(R.id.pv_artists)
  ProgressBar artist_ProgressBar;
  public @BindView(R.id.iv_artists)
  ImageView artist_imageView;
  public @BindView(R.id.txt_line_artists)
  TextView artist_info_textView;
  public @BindView(R.id.txt_subline_artists)
  TextView artist_description_textView;
  private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";

  private com.zava.mvplab.artist.ArtistsPresenter artistsPresenter;

  public ArtistsFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    artistsPresenter = new com.zava.mvplab.artist.ArtistsPresenter(new com.zava.mvplab.artist.ArtistsInteractor(new SpotifyClient()));
    artistsPresenter.setView(this);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_artists, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    setupToolbar();
    setupRecyclerView();
  }

  @Override
  public void onDestroy() {
    artistsPresenter.terminate();
    super.onDestroy();
  }

  @Override
  public Context getContext() {
    return getActivity();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_music, menu);
    setupSearchView(menu);
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    artistsPresenter.onSearchArtist(query);
    return true;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    return true;
  }

  @Override
  public void showLoading() {
    artist_ProgressBar.setVisibility(View.VISIBLE);
    artist_imageView.setVisibility(View.GONE);
    artist_info_textView.setVisibility(View.GONE);
    artist_description_textView.setVisibility(View.GONE);
    artist_recyclerView.setVisibility(View.GONE);
  }

  @Override
  public void hideLoading() {
    artist_ProgressBar.setVisibility(View.GONE);
    artist_recyclerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void showArtistNotFoundMessage() {
    artist_ProgressBar.setVisibility(View.GONE);
    artist_info_textView.setVisibility(View.VISIBLE);
    artist_imageView.setVisibility(View.VISIBLE);
    artist_info_textView.setText(getString(R.string.error_artist_not_found));
    artist_imageView.setImageResource(R.mipmap.ic_not_found);
  }

  @Override
  public void showConnectionErrorMessage() {
    artist_ProgressBar.setVisibility(View.GONE);
    artist_info_textView.setVisibility(View.VISIBLE);
    artist_imageView.setVisibility(View.VISIBLE);
    artist_info_textView.setText(getString(R.string.error_internet_connection));
    artist_imageView.setImageResource(R.mipmap.ic_not_internet);
  }

  @Override
  public void showServerError() {
    artist_ProgressBar.setVisibility(View.GONE);
    artist_info_textView.setVisibility(View.VISIBLE);
    artist_imageView.setVisibility(View.VISIBLE);
    artist_info_textView.setText(getString(R.string.error_server_internal));
    artist_imageView.setImageResource(R.mipmap.ic_not_found);
  }

  @Override
  public void renderArtists(List<com.zava.mvplab.artist.model.Artist> artists) {
    ArtistsAdapter adapter = (ArtistsAdapter) artist_recyclerView.getAdapter();
    adapter.setArtists(artists);
    adapter.notifyDataSetChanged();
  }

  private void setupSearchView(Menu menu) {
    SearchManager searchManager =
        (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    searchView.setQueryHint(getString(R.string.search_hint));
    searchView.setMaxWidth(toolbar.getWidth());
    searchView.setOnQueryTextListener(this);
  }

  private void setupToolbar() {
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.mipmap.ic_action_navigation_menu);
    }
  }

  public void setupRecyclerView() {
    ArtistsAdapter adapter = new ArtistsAdapter();
    adapter.setItemClickListener(
        (com.zava.mvplab.artist.model.Artist artist, int position) -> artistsPresenter
            .launchArtistDetail(getContext(),artist));
    artist_recyclerView.setAdapter(adapter);
  }

  @Override
  public Intent getStartIntent(Context context, com.zava.mvplab.artist.Artist artist) {
    Intent intent = new Intent(context, TracksActivity.class);
    intent.putExtra(TracksActivity.EXTRA_REPOSITORY, artist);
    return intent;
  }

  @Override
  public Context context() {
    return null;
  }
}
