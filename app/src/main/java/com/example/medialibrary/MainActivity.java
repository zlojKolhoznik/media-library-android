package com.example.medialibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDatabase();
    }

    private void initializeDatabase() {
        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS media (id INT PRIMARY KEY, filename TEXT UNIQUE, type TEXT)");

        Cursor cursor = db.rawQuery("SELECT * FROM media", null);
        if (!cursor.moveToFirst()) {
            db.execSQL("INSERT INTO media (id, filename, type) VALUES (" + R.raw.cat + ", 'cat.mp4', 'video')");
            db.execSQL("INSERT INTO media (id, filename, type) VALUES (" + R.raw.dancin + ", 'dancin.mp4', 'video')");
            db.execSQL("INSERT INTO media (id, filename, type) VALUES (" + R.raw.gossip + ", 'gossip.mp3', 'audio')");
            db.execSQL("INSERT INTO media (id, filename, type) VALUES (" + R.raw.monster + ", 'monster.mp3', 'audio')");
        }

        cursor.close();
    }

    public void search(View view) {
        EditText searchBar = findViewById(R.id.searchBar);
        TableLayout table = findViewById(R.id.resultsTable);
        table.removeAllViews();
        String query = searchBar.getText().toString();
        Cursor cursor = db.rawQuery("SELECT * FROM media WHERE filename LIKE '%" + query + "%'", null);
        if (!cursor.moveToFirst()) {
            Toast.makeText(this, "No media files with such name found", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<MediaFile> files = new ArrayList<>();
        do {
            files.add(new MediaFile(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        } while (cursor.moveToNext());

        for (int i = 0; i < files.size(); i++) {
            MediaFile file = files.get(i);

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView filename = new TextView(this);
            filename.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            filename.setText(file.getFilename());
            row.addView(filename);

            Button playButton = new Button(this);
            playButton.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            playButton.setText("Play");
            if (file.getType().equalsIgnoreCase("video")) {
                playButton.setOnClickListener(v -> {
                    // TODO: open video player activity
                    Intent intent = new Intent(this, VideoPlayerActivity.class);
                    String uriString = "android.resource://" + getPackageName() + "/" + file.getId();
                    intent.putExtra("fileURIString", uriString);
                    startActivity(intent);
                });
            } else {
                playButton.setOnClickListener(v -> {
                    // TODO: open audio player activity
                    Intent intent = new Intent(this, AudioPlayerActivity.class);
                    String uriString = "android.resource://" + getPackageName() + "/" + file.getId();
                    intent.putExtra("fileURIString", uriString);
                    startActivity(intent);
                });
            }

            row.addView(playButton, 1);
            table.addView(row, i);
        }

        cursor.close();
    }
}