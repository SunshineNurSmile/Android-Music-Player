package com.yuanyiwang.musicplayer.utils;

import static com.yuanyiwang.musicplayer.activity.MusicPlayerActivity.listOfSongs;

import android.media.MediaExtractor;
import android.media.MediaFormat;

import java.io.IOException;

public class GetAdvancedInfo {

    public static String getBitRate(int position) {
        int br = Integer.parseInt(listOfSongs.get(position).getBitrate());
        return br / 1000 + "kbps";
    }

    public static String getSampleRate(int position) {
        MediaFormat mf = mediaFormat(position);
        int sr = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        String sR;
        if (sr <= 384000) {
            int srRemainder = sr % 1000;
            if (srRemainder == 0) {
                sR = sr / 1000 + "kHz";
            }
            else {
                sR = sr / 1000 + "." + srRemainder / 100 + "kHz";
            }
        }
        else {
            int srRemainder = sr % 1000000 / 100000;
            sR = sr / 1000000 + "." + srRemainder + "MHz";
        }
        return sR;
    }

    public static String getBitDepth(int position) {
        MediaFormat mf = mediaFormat(position);
        int br = Integer.parseInt(listOfSongs.get(position).getBitrate());
        int sr = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        int bd = br / sr / 2;
        return bd + "bit";
    }

    private static MediaFormat mediaFormat(int position) {
        MediaExtractor mex = new MediaExtractor();
        String path = listOfSongs.get(position).getPath();
        try {
            mex.setDataSource(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return mex.getTrackFormat(0);
    }
}
