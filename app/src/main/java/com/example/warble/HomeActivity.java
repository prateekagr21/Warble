package com.example.warble;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
                getAllSongs();
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
}
