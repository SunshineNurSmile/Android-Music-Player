package com.yuanyiwang.musicplayer.fragment;

import static com.yuanyiwang.musicplayer.activity.MainActivity.folderFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.adapter.FolderAdapter;

public class FoldersFragment extends Fragment {
    RecyclerView recyclerView;
    FolderAdapter folderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folders, container, false);
        recyclerView = view.findViewById(R.id.foldersRecyclerView);
        recyclerView.setHasFixedSize(true);

        folderAdapter = new FolderAdapter(getContext(), folderFiles);
        recyclerView.setAdapter(folderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return view;
    }
}