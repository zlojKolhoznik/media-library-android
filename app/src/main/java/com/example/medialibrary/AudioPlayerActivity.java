package com.example.medialibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioPlayerActivity extends AppCompatActivity {

    private MediaPlayer player;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        Uri uri = Uri.parse(getIntent().getExtras().get("fileURIString").toString());
        player = MediaPlayer.create(this, uri);
        player.setOnCompletionListener(mediaPlayer -> {
            stopPlayer();
        });

        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    public void openMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        stopPlayer();
        startActivity(intent);
    }

    public void play(View view) {
        player.start();

        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    public void pause(View view) {
        player.pause();

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stop(View view) {
        stopPlayer();
    }

    private void stopPlayer() {
        player.stop();

        try {
            player.prepare();
            player.seekTo(0);
            playButton.setEnabled(true);
            pauseButton.setEnabled(false);
            stopButton.setEnabled(false);
        } catch (Throwable err) {
            Toast.makeText(this, err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}