package com.example.warble.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warble.R;
import com.example.warble.models.AudioModel;

import java.security.AllPermission;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.ViewHolder> {

    private List<AudioModel> audioModels;
    public AllSongsAdapter(List<AudioModel> audioModels){
        this.audioModels = audioModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View songItem = layoutInflater.inflate(R.layout.song_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(songItem);
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
        TextView artistTextView;
        TextView durationTextView;
        public ViewHolder(View itemView){
            super(itemView);
            this.songNameTextView = itemView.findViewById(R.id.songname_textview);
            this.artistTextView = itemView.findViewById(R.id.artistname_textview);
            this.durationTextView = itemView.findViewById(R.id.duration_textview);

        }
    }
}




