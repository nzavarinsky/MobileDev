package com.zava.mvplab.artist.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.zava.mvplab.R;
import com.zava.mvplab.data.api.Constants;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

  private List<com.zava.mvplab.artist.model.Artist> artists;
  private ItemClickListener itemClickListener;

  public ArtistsAdapter() {
    artists = Collections.emptyList();
  }

  @Override
  public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
    return new ArtistsViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
    com.zava.mvplab.artist.model.Artist artist = artists.get(position);
    holder.artist = artist;
    holder.textView.setText(artist.name);

    if (artist.mArtistImages.isEmpty()) {
      setDefaultImage(holder);
    } else {
      putImages(holder, position);
    }
    holder.itemView.setOnClickListener((View view) -> {
      if (itemClickListener != null) {
        itemClickListener.onItemClick(artist, position);
      }
    });
  }

  private void putImages(ArtistsViewHolder holder, int position) {
    com.zava.mvplab.artist.model.Artist artist = artists.get(position);
    holder.artist = artist;

    for (int i = 0; i < artist.mArtistImages.size(); i++) {
      if (artist.mArtistImages.get(i) != null && artist.mArtistImages.size() > 0) {
        Picasso.with(holder.imageView.getContext())
            .load(artist.mArtistImages.get(0).url)
            .into(holder.imageView);
      }
    }
  }

  private void setDefaultImage(ArtistsViewHolder holder) {
    final String imageHolder =
        Constants.Serialized.IMAGE_URL;
    Picasso.with(holder.imageView.getContext()).load(imageHolder).into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return artists.size();
  }

  void setArtists(List<com.zava.mvplab.artist.model.Artist> artists) {
    this.artists = artists;
  }

  void setItemClickListener(ItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public interface ItemClickListener {
    void onItemClick(com.zava.mvplab.artist.model.Artist artist, int position);
  }

  static class ArtistsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_view_artist_image)
    ImageView imageView;
    @BindView(R.id.txt_artist_name)
    TextView textView;

    com.zava.mvplab.artist.model.Artist artist;
    View itemView;

    ArtistsViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.itemView = itemView;
    }
  }
}
