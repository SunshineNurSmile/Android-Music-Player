package com.yuanyiwang.musicplayer.utils;

import static com.yuanyiwang.musicplayer.activity.MusicPlayerActivity.listOfSongs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetShuffledList {

    public static List<Integer> getRandom() {
        List<Integer> numbers = new ArrayList<>(listOfSongs.size());
        for (int i = 0; i < listOfSongs.size(); i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }
}
