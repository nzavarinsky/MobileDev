

package com.zava.mvplab.track.view;

import android.annotation.SuppressLint;
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
import com.zava.mvplab.track.model.Track;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

  private List<Track> mTracks;
  private ItemClickListener mItemClickListener;

  TracksAdapter() {
    mTracks = Collections.emptyList();
  }

  @NonNull
  @Override
  public TracksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
    return new TracksViewHolder(itemView);
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onBindViewHolder(@NonNull TracksViewHolder holder, int position) {
    Track track = mTracks.get(position);

    holder.txt_title_tracks.setText((position + 1) + "." + track.name);
    holder.txt_track_album.setText(track.album.getAlbumName());

    if (track.album.getTrackImages().isEmpty()) {
      setDefaultImage(holder);
    } else {
      putImages(holder, position);

    }

    holder.itemView.setOnClickListener((View view) -> {
      if (mItemClickListener != null) {
        mItemClickListener.onItemClick(mTracks, track, position);
      }
    });
  }

  private void putImages(@NonNull TracksViewHolder holder, int position) {
    Track track = mTracks.get(position);
    holder.track = track;
    holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    for (int i = 0; i < track.album.getTrackImages().size(); i++) {
      if (track.album.getTrackImages().get(i) != null && track.album.getTrackImages().size() > 0) {
        Picasso.with(holder.imageView.getContext())
            .load(track.album.getTrackImages().get(0).mUrl)
            .into(holder.imageView);
      }
    }
  }

  private void setDefaultImage(@NonNull TracksViewHolder holder) {
    Picasso.with(holder.imageView.getContext())
        .load(Constants.Serialized.IMAGE_URL)
        .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return mTracks.size();
  }

  void setTracks(List<Track> mTracks) {
    this.mTracks = mTracks;
  }

  void setItemClickListener(ItemClickListener mItemClickListener) {
    this.mItemClickListener = mItemClickListener;
  }

  public interface ItemClickListener {
    void onItemClick(List<Track> mTracks, Track track, int position);
  }

  public static class TracksViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_track)
    ImageView imageView;
    @BindView(R.id.txt_track_title)
    TextView txt_title_tracks;
    @BindView(R.id.txt_track_album)
    TextView txt_track_album;

    View itemView;
    com.zava.mvplab.track.model.Track track;

    TracksViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.itemView = itemView;
    }
  }
}
