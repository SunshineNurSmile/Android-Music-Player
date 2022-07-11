package com.yuanyiwang.musicplayer.fragment;

import static com.yuanyiwang.musicplayer.activity.MainActivity.albumFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.adapter.AlbumAdapter;

public class AlbumsFragment extends Fragment {
    RecyclerView recyclerView;
    AlbumAdapter albumAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        recyclerView = view.findViewById(R.id.albumsRecyclerView);
        recyclerView.setHasFixedSize(true);

        albumAdapter = new AlbumAdapter(getContext(), albumFiles);
        recyclerView.setAdapter(albumAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return view;
    }
}