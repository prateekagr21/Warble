package com.example.warble;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Context context = this;
    private RecyclerView recyclerView;
    private Button button;
    private TextView textView;
    private int PERMISSION_CODE = 77;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.song_recyclerview);
        button = findViewById(R.id.allsongs_button);
        textView = findViewById(R.id.songname_textview);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

    private void getAllSongs(){
        final List<AudioModel> audioList = new ArrayList<>();

        Uri uri  = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null , null);
        if (cursor!= null){
            while (cursor.moveToNext()) {
                AudioModel audioModel = new AudioModel();
                String name = cursor.getString(1);
                audioModel.setSongName(name);
                audioList.add(audioModel);
            }
            cursor.close();
        }
        AllSongsAdapter allSongsAdapter = new AllSongsAdapter(audioList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(allSongsAdapter);
    }

    public void requestPermission(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    HomeActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
                    );
        }
        else {
            getAllSongs();
        }
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
