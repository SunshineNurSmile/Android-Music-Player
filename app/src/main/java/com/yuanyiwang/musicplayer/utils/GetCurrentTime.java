package com.yuanyiwang.musicplayer.utils;

public class GetCurrentTime {

    public static String getCurrentTime(int currentPosition) {
        String currentTime = currentPosition / 60 + ":";
        int seconds = currentPosition % 60;
        if (seconds < 10) {
            currentTime = currentTime + "0" + seconds;
        }
        else {
            currentTime = currentTime + seconds;
        }
        return currentTime;
    }
}
