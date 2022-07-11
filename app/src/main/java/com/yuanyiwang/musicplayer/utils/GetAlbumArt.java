package com.yuanyiwang.musicplayer.utils;

import android.media.MediaMetadataRetriever;

public class GetAlbumArt {

    public static byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] albumArt = retriever.getEmbeddedPicture();
        retriever.release();
        return albumArt;
    }
}
