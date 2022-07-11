package com.yuanyiwang.musicplayer.utils;

import static com.yuanyiwang.musicplayer.activity.MusicPlayerActivity.listOfSongs;

public class GetFormattedTime {

    public static String formatTime(int position) {
        String duration = Integer.parseInt(listOfSongs.get(position).getDuration()) / 60000 + ":";
        int durRemainder = Integer.parseInt(listOfSongs.get(position).getDuration()) / 1000 % 60;
        if (durRemainder < 10) {
            duration = duration + "0" + durRemainder;
        }
        else {
            duration = duration + durRemainder;
        }
        return duration;
    }
}
