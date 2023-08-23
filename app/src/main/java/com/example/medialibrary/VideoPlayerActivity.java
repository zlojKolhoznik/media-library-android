package com.example.medialibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoPlayer = findViewById(R.id.videoPlayer);
        videoPlayer.setVideoURI(Uri.parse(getIntent().getExtras().get("fileURIString").toString()));

        MediaController controller = new MediaController(this);
        videoPlayer.setMediaController(controller);
        controller.setAnchorView(videoPlayer);
    }

    public void openMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}