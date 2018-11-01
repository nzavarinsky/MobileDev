package com.example.zava.mymobileapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.zava.mymobileapp.R;
import com.example.zava.mymobileapp.model.AlbumTrack;

import java.util.List;

public class AlbumTrackAdapter extends RecyclerView.Adapter<AlbumTrackAdapter.AlbumTracksViewHolder> {

    private List<AlbumTrack> albumTrackList;
    private LayoutInflater layoutInflater;

    
    public AlbumTrackAdapter(List<AlbumTrack> albumTrackList, Context context) {
        this.albumTrackList = albumTrackList;
        this.layoutInflater = LayoutInflater.from(context);
    }

   
    @NonNull
    @Override
    public AlbumTracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.albumtrack_item,parent,false);
        return new AlbumTracksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumTracksViewHolder holder, int position) {
        AlbumTrack albumTrack = albumTrackList.get(position);
        holder.textView_trackName.setText(albumTrack.getTrackName());
        holder.textView_trackDuration.setText(albumTrack.getTrackDuration());
        holder.textView_trackPopularity.setNumStars(5);
        holder.textView_trackPopularity.setStepSize(1);
        holder.textView_trackPopularity.setRating(albumTrack.getTrackPopularity());
    }

    @Override
    public int getItemCount() {
        return albumTrackList.size();
    }

    
    class AlbumTracksViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_trackName;
        private TextView textView_trackDuration;
        private RatingBar textView_trackPopularity;

      public AlbumTracksViewHolder(View itemView) {
            super(itemView);
            textView_trackName = itemView.findViewById(R.id.tracks_textView_track_name);
            textView_trackDuration = itemView.findViewById(R.id.tracks_textView_track_duration);
            textView_trackPopularity =  itemView.findViewById(R.id.tracks_textView_track_popularity);
        itemView.findViewById(R.id.tracks_linearLayout_innerContainer);
        }
    }


}
