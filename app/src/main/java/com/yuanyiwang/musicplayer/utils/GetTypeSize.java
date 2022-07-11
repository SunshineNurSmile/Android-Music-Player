package com.yuanyiwang.musicplayer.utils;

import static com.yuanyiwang.musicplayer.activity.MusicPlayerActivity.listOfSongs;

public class GetTypeSize {

    public static String getType(int position) {
        String type = listOfSongs.get(position).getPath();
        int index = type.length();
        String dot = type.substring(index - 4, index - 3);
        if (dot.equals("f")) {
            index = index - 4;
        }
        else {
            index = index - 3;
        }
        type = type.substring(index).toUpperCase();
        return type;
    }

    public static String getFileSize(int position) {
        int fs = Integer.parseInt(listOfSongs.get(position).getSize()) / 1024 / 1024;
        return fs + "MB";
    }
}
