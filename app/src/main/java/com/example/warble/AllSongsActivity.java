package com.example.warble;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warble.adapters.AllSongsAdapter;
import com.example.warble.models.AudioModel;
import com.example.warble.services.MediaPlayerService;

import java.util.ArrayList;
import java.util.List;

public class AllSongsActivity extends AppCompatActivity {
    private Context context = this;
    private RecyclerView recyclerView;
    private int PERMISSION_CODE = 77;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsongs);
        recyclerView = findViewById(R.id.song_recyclerview);
        requestPermission();
        findViewById(R.id.bottom_play_pause_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSongsActivity.this, MediaPlayerService.class);
                stopService(intent);
                ImageView bottomPlayPause = findViewById(R.id.bottom_play_pause_button);
                bottomPlayPause.setImageResource(R.drawable.play);
            }
        });
    }

    private void getAllSongs(){
        final List<AudioModel> audioList = new ArrayList<>();

        Uri uri  = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.AudioColumns.DURATION};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null , null);
        if (cursor!= null){
            while (cursor.moveToNext()) {
                AudioModel audioModel = new AudioModel();
                String path = cursor.getString(0);
                String songName = cursor.getString(1);
                String artistName = cursor.getString(3);
                String duration = cursor.getString(4);
                audioModel.setPath(path);
                audioModel.setSongName(songName);
                audioModel.setArtistName(artistName);
                audioModel.setDuration(duration);
                audioList.add(audioModel);
            }
            cursor.close();
        }

        AllSongsAdapter allSongsAdapter = new AllSongsAdapter(audioList, AllSongsActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(allSongsAdapter);
    }

    public void requestPermission(){
        String[] permission;
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    AllSongsActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
        else {
            getAllSongs();
        }
    }

    public void onItemClicked(String songName){
        TextView bottomSongName = findViewById(R.id.bottom_song_name);
        bottomSongName.setText(songName);
        ImageView bottomPlayPause = findViewById(R.id.bottom_play_pause_button);
        bottomPlayPause.setImageResource(R.drawable.pause);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getAllSongs();
            }
            else {
                Toast.makeText(context, "Storage Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}

