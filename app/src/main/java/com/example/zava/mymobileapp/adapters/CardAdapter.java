package com.example.zava.mymobileapp.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.model.CardAlbum;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

  private List<CardAlbum> cardAlbumList;
  private LayoutInflater layoutInflater;
  private CardClickCallBack cardClickCallBack;
  private Context context;

  public CardAdapter(List<CardAlbum> cardAlbumList, Context context) {
    this.cardAlbumList = cardAlbumList;
    this.context = context;
    this.layoutInflater = LayoutInflater.from(context);
  }

  public interface CardClickCallBack {
    void onCardClick(int position);

    void onCardButtonClick(int position);
  }

  public void setCardClickCallBack(CardClickCallBack cardClickCallBack) {
    this.cardClickCallBack = cardClickCallBack;
  }

  @NonNull
  @Override
  public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.card_item, parent, false);
    new CardViewHolder(view);
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
  public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
    CardAlbum cardAlbum = cardAlbumList.get(position);
    //Picasso Experiment Begin
    Picasso.with(context).setLoggingEnabled(true);
    Picasso.with(context).load(cardAlbum.getAlbumImageURL()).into(holder.imageView_Avatar);
    //Experiment Experiment End
    holder.textView_albumName.setText(cardAlbum.getAlbumName());
    holder.textView_artistName.setText(cardAlbum.getArtistName());
    holder.button_aboutArtist.setText("About " + cardAlbum.getArtistName());
  }


  @Override
  public int getItemCount() {
    return cardAlbumList.size();
  }


  class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private ImageView imageView_Avatar;
    private TextView textView_albumName;
    private TextView textView_artistName;
    private Button button_aboutArtist;
    private View viewContainer;

    CardViewHolder(View itemView) {
      super(itemView);

      imageView_Avatar = itemView.findViewById(R.id.imageView_for_album_photo);
      textView_albumName = itemView.findViewById(R.id.textView_for_album_name);
      textView_artistName =  itemView.findViewById(R.id.textView_for_artist_names);
      button_aboutArtist = itemView.findViewById(R.id.button_for_about_artist);
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

    MyMenuItemClickListener() {
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



