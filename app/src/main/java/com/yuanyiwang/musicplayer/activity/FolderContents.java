package com.yuanyiwang.musicplayer.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.adapter.FolderContentsAdapter;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.utils.GetFolderContents;

import java.util.ArrayList;
import java.util.Objects;

public class FolderContents extends AppCompatActivity {
    RecyclerView recyclerView;
    String folderName;
    ArrayList<MusicFiles> folderSongList = new ArrayList<>();
    FolderContentsAdapter folderContentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_contents);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Folders");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(233, 30, 99)));

        initViews();

        folderSongList = GetFolderContents.getFolderContents(folderName);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.folderContentRecyclerView);
        recyclerView.setHasFixedSize(true);
        folderName = getIntent().getStringExtra("Folder Name");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (folderSongList.size() > 0) {
            folderContentsAdapter = new FolderContentsAdapter(this, folderSongList);
            recyclerView.setAdapter(folderContentsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
    }
}