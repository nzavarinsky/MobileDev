package com.example.zava.mymobileapp.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.zava.mymobileapp.model.CardAlbum;
import com.example.zava.mymobileapp.R;

import javax.xml.transform.Result;

import static android.content.Context.MODE_PRIVATE;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

  private List<CardAlbum> cardAlbumList;
  private LayoutInflater layoutInflater;
  private CardClickCallBack cardClickCallBack;
  private Context context;
  public static final String ACTION_LIKE_IMAGE_DOUBLE_CLICKED = "action_like_image_button";

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
    final CardViewHolder holder = new CardViewHolder(view);
    return new CardViewHolder(view);
  }

  private void showPopupMenu(View view) {
    // inflate menu
    PopupMenu popup = new PopupMenu(context, view);
    MenuInflater inflater = popup.getMenuInflater();
    inflater.inflate(R.menu.menu_card, popup.getMenu());
    popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
    popup.show();
  }

  @Override
  public void onBindViewHolder(CardViewHolder holder, int position) {
    CardAlbum cardAlbum = cardAlbumList.get(position);
    //Picasso Experiment Begin
    Picasso.with(context).setLoggingEnabled(true);
    Picasso.with(context).load(cardAlbum.getAlbumImageURL()).into(holder.imageView_Avatar);
    //Experiment Experiment End
    holder.textView_albumName.setText(cardAlbum.getAlbumName());
    holder.textView_artistName.setText(cardAlbum.getArtistName());
    holder.button_aboutArtist.setText("About " + cardAlbum.getArtistName());
  }

  private void goToFavourites(int position, Result result) {
    notifyItemChanged(position, ACTION_LIKE_IMAGE_DOUBLE_CLICKED);
    //TODO: create a callback to save result item in db as favourite
  }


  @Override
  public int getItemCount() {
    return cardAlbumList.size();
  }

  public CardAlbum getItem(int position) {
    if (position != RecyclerView.NO_POSITION)
      return cardAlbumList.get(position);
    else
      return null;
  }



  class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private ImageView imageView_Avatar;
    private TextView textView_albumName;
    private TextView textView_artistName;
    private Button button_aboutArtist;
    private ToggleButton mToggleButton;
    private View viewContainer;
    private Context mContext;
    public CardView mCardView;

    public CardViewHolder(View itemView) {
      super(itemView);

      imageView_Avatar = (ImageView) itemView.findViewById(R.id.imageView_for_album_photo);
      textView_albumName = (TextView) itemView.findViewById(R.id.textView_for_album_name);
      textView_artistName = (TextView) itemView.findViewById(R.id.textView_for_artist_names);
      button_aboutArtist = (Button) itemView.findViewById(R.id.button_for_about_artist);
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

  class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    public MyMenuItemClickListener() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
      switch (menuItem.getItemId()) {
        case R.id.action_add_favourite:
          Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
          return true;
      }
      return false;
    }
  }

}



