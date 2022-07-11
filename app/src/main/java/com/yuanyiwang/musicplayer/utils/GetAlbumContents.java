package com.yuanyiwang.musicplayer.utils;

import static com.yuanyiwang.musicplayer.activity.MainActivity.musicFiles;

import com.yuanyiwang.musicplayer.data.MusicFiles;

import java.util.ArrayList;

public class GetAlbumContents {

    public static ArrayList<MusicFiles> getAlbumContents(String albumName) {
        int j = 0;
        ArrayList<MusicFiles> albumSongList = new ArrayList<>();
        for (int i = 0; i < musicFiles.size(); i++) {
            if (albumName.equals(musicFiles.get(i).getAlbum())) {
                albumSongList.add(j, musicFiles.get(i));
                j++;
            }
        }
        return albumSongList;
    }
}
