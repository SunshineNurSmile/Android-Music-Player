package com.yuanyiwang.musicplayer.utils;

import static com.yuanyiwang.musicplayer.activity.MainActivity.musicFiles;
import static com.yuanyiwang.musicplayer.adapter.FolderAdapter.folderFiles;

import com.yuanyiwang.musicplayer.data.MusicFiles;

import java.util.ArrayList;

public class GetFolderContents {

    public static ArrayList<MusicFiles> getFolderContents(String folderName) {
        int j = 0;
        ArrayList<MusicFiles> folderSongList = new ArrayList<>();
        for (int i = 0; i < musicFiles.size(); i++) {
            if (musicFiles.get(i).getPath().contains(folderName)) {
                folderSongList.add(j, musicFiles.get(i));
                j++;
            }
        }
        return folderSongList;
    }

    public static String getFolderPath(int position) {
        String folderPath = folderFiles.get(position).getPath();
        folderPath = folderPath.substring(0, folderPath.lastIndexOf("/"));
        folderPath = folderPath.substring(folderPath.lastIndexOf("/") + 1);
        return folderPath;
    }
}
