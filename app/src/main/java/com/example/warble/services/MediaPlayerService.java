package com.example.warble.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.warble.R;

import java.io.IOException;

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mPlayer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String filePath = "file://"+intent.getStringExtra("file_path");
        String songName = intent.getStringExtra("song_name");
        Uri uri = Uri.parse(filePath);
        mPlayer = new MediaPlayer();
        mPlayer.setAudioAttributes(new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
        );
//        Log.d("songservice1", filePath);
//        Log.d("songservice2", uri.toString());
//        Log.d("songservice3", Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.d("songservice5", songName);

        try {
            mPlayer.setDataSource(getApplicationContext(), uri);
        } catch (IOException e) {
//            Log.d("songservice4e", e.getMessage());
            Toast.makeText(getApplicationContext(), "Song not found", Toast.LENGTH_SHORT).show();
        }
        mPlayer.setOnPreparedListener(this);
        mPlayer.prepareAsync();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
        if(mPlayer != null)
            mPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
