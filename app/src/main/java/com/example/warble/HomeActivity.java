package com.example.warble;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warble.adapters.AllSongsAdapter;
import com.example.warble.adapters.RecentlyPlayedAdapter;
import com.example.warble.models.AudioModel;
import com.example.warble.services.MediaPlayerService;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Context context = this;
    private Button button;
    private RecyclerView recentlyPlayedRecyclerView;
    private List<AudioModel> recentlyPlayedList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        button = findViewById(R.id.allsongs_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AllSongsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.bottom_play_pause_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MediaPlayerService.class);
                stopService(intent);
                ImageView bottomPlayPause = findViewById(R.id.bottom_play_pause_button);
                bottomPlayPause.setImageResource(R.drawable.play);
            }
        });

            //TODO Create recenltly played list and add in below code
//            RecentlyPlayedAdapter adapter= new RecentlyPlayedAdapter(recentlyPlayedList);
//            recentlyPlayedRecyclerView = findViewById(R.id.recentlyplayed_recyclerview);
//            recentlyPlayedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recentlyPlayedRecyclerView.setAdapter(adapter);
    }


}
