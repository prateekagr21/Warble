package com.example.warble.adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warble.R;
import com.example.warble.models.AudioModel;

import java.util.List;

public class RecentlyPlayedAdapter extends RecyclerView.Adapter<RecentlyPlayedAdapter.ViewHolder> {

    private List<AudioModel> audioModels;
    public RecentlyPlayedAdapter(List<AudioModel> audioModels){
        this.audioModels = audioModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recentlyPlayed = layoutInflater.inflate(R.layout.recently_played, parent, false);
        ViewHolder viewHolder = new ViewHolder(recentlyPlayed);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AudioModel audioModel = audioModels.get(position);
        holder.songNameTextView.setText(audioModel.getSongName());
        holder.artistTextView.setText(audioModel.getArtistName());
        int duration = Integer.parseInt(audioModel.getDuration())/1000;
        int min, sec;
        min = duration/60;
        sec = duration%60;
        String durationText = "";
        if(sec <=9){
            durationText = min+":0"+sec+" • ";
        }
        else{
            durationText = min+":"+sec+" • ";
        }
        holder.durationTextView.setText(durationText);
    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView songNameTextView;
        TextView durationTextView;
        TextView artistTextView;
        ImageView songImageView;
        public ViewHolder(View itemView){
            super(itemView);
            this.songImageView = itemView.findViewById(R.id.image_recently_played);
            this.songNameTextView = itemView.findViewById(R.id.songname_recently_played);
            this.artistTextView = itemView.findViewById(R.id.artistname_recent);
            this.durationTextView = itemView.findViewById(R.id.duration_recent);
        }
    }
}
