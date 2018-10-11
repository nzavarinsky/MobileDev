package com.example.zava.mymobileapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.zava.mymobileapp.model.CardAlbum;
import com.example.zava.mymobileapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

  private List<CardAlbum> cardAlbumList;
  private LayoutInflater layoutInflater;
  private CardClickCallBack cardClickCallBack;
  private Context context;

  public CardAdapter(List<CardAlbum> cardAlbumList, Context context) {
    this.cardAlbumList = cardAlbumList;
    this.context = context;
    this.layoutInflater = layoutInflater.from(context);
  }

  public interface CardClickCallBack {
    public void onCardClick(int position);

    public void onCardButtonClick(int position);
  }

  public void setCardClickCallBack(CardClickCallBack cardClickCallBack) {
    this.cardClickCallBack = cardClickCallBack;
  }

  @Override
  public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.card_item, parent, false);
    return new CardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CardViewHolder holder, int position) {
    CardAlbum cardAlbum = cardAlbumList.get(position);
    Picasso.with(context).setLoggingEnabled(true);
    Picasso.with(context).load(cardAlbum.getAlbumImageURL()).into(holder.imageView_Avatar);
    holder.textView_albumName.setText(cardAlbum.getAlbumName());
    holder.textView_artistName.setText(cardAlbum.getArtistName());
    holder.button_aboutArtist.setText("About " + cardAlbum.getArtistName());
  }

  @Override
  public int getItemCount() {
    return cardAlbumList.size();
  }


  class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private View viewContainer;
    @BindView(R.id.imageView_for_album_photo) ImageView imageView_Avatar;
    @BindView(R.id.textView_for_album_name) TextView textView_albumName;
    @BindView(R.id.textView_for_artist_names) TextView textView_artistName;
    @BindView(R.id.button_for_about_artist) Button button_aboutArtist;


    public CardViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
      button_aboutArtist.setOnClickListener(this);
      viewContainer = itemView.findViewById(R.id.cardview_element);
      viewContainer.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
      if (view.getId() == R.id.button_for_about_artist) {
        cardClickCallBack.onCardButtonClick(getAdapterPosition());
      } else {
        cardClickCallBack.onCardClick(getAdapterPosition());
      }
    }
  }
}
