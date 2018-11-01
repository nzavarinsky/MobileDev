package com.example.zava.mymobileapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.model.ArtistAlbum;


public class ArtistAlbumsAdapter extends RecyclerView.Adapter<ArtistAlbumsAdapter.ArtistAlbumViewHolder> {

  //Instance variables
  private List<ArtistAlbum> mArtistAlbumList;
  private LayoutInflater mLayoutInflater;
  private Context mContext;
  private ItemClickCallBack mItemClickCallBack;


  public ArtistAlbumsAdapter(List<ArtistAlbum> mArtistAlbumList, Context mContext) {
    this.mArtistAlbumList = mArtistAlbumList;
    this.mContext = mContext;
    this.mLayoutInflater = LayoutInflater.from(mContext);
  }


  public interface ItemClickCallBack {
    void onItemClick(int position);
  }


  public void setItemClickCallBack(ItemClickCallBack mItemClickCallBack) {
    this.mItemClickCallBack = mItemClickCallBack;
  }


  @NonNull
  @Override
  public ArtistAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mLayoutInflater.inflate(R.layout.album_item, parent, false);
    return new ArtistAlbumViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ArtistAlbumViewHolder holder, int position) {

    ArtistAlbum artistAlbum = mArtistAlbumList.get(position);
    //Picasso Experiment
    Picasso.with(mContext).setLoggingEnabled(true);
    Picasso.with(mContext).load(artistAlbum.getArtistAlbumImageURL()).into(holder.imageView_artistAlbumImage);
    //Picasso Experiment
    holder.textView_artistAlbumName.setText(artistAlbum.getArtistAlbumName());
    holder.textView_artistAlbumreleaseDate.setText(artistAlbum.getArtistAlbumReleaseDate());
    holder.ratingBar_artistAlbumPopularity.setNumStars(5);
    holder.ratingBar_artistAlbumPopularity.setStepSize(1);
    holder.ratingBar_artistAlbumPopularity.setRating(artistAlbum.getArtistAlbumPopularity());
  }

  @Override
  public int getItemCount() {
    return mArtistAlbumList.size();
  }


  class ArtistAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView_artistAlbumImage;
    private TextView textView_artistAlbumName;
    private TextView textView_artistAlbumreleaseDate;
    private RatingBar ratingBar_artistAlbumPopularity;
    private View viewContainer;


    ArtistAlbumViewHolder(View itemView) {
      super(itemView);

      imageView_artistAlbumImage = itemView.findViewById(R.id.albums_imageView_artist_album_image);
      textView_artistAlbumName = itemView.findViewById(R.id.albums_textView_artist_album_name);
      textView_artistAlbumreleaseDate = itemView.findViewById(R.id.albums_textView_artist_album_release_date);
      ratingBar_artistAlbumPopularity = itemView.findViewById(R.id.albums_ratingBar_artist_album_popularity);
      viewContainer = itemView.findViewById(R.id.cardview_album_item_container);
      viewContainer.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
      if (view.getId() == R.id.cardview_album_item_container) {
        mItemClickCallBack.onItemClick(getAdapterPosition());
      }
    }
  }
}
