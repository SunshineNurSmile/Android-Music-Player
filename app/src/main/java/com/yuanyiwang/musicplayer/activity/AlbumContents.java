package com.yuanyiwang.musicplayer.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.adapter.AlbumContentsAdapter;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.utils.GetAlbumContents;

import java.util.ArrayList;
import java.util.Objects;

public class AlbumContents extends AppCompatActivity {
    RecyclerView recyclerView;
    String albumName;
    ArrayList<MusicFiles> albumSongList;
    AlbumContentsAdapter albumContentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_contents);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Albums");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(233, 30, 99)));

        initViews();

        albumSongList = GetAlbumContents.getAlbumContents(albumName);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.albumContentRecyclerView);
        recyclerView.setHasFixedSize(true);
        albumName = getIntent().getStringExtra("Album Name");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (albumSongList.size() > 0) {
            albumContentsAdapter = new AlbumContentsAdapter(this, albumSongList);
            recyclerView.setAdapter(albumContentsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
    }
}