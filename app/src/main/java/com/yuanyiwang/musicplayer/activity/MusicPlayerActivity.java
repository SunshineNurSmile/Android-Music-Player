package com.yuanyiwang.musicplayer.activity;

import static com.yuanyiwang.musicplayer.activity.MainActivity.musicFiles;
import static com.yuanyiwang.musicplayer.adapter.AlbumContentsAdapter.albumSongList;
import static com.yuanyiwang.musicplayer.adapter.FolderContentsAdapter.folderSongList;

import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.utils.GetAdvancedInfo;
import com.yuanyiwang.musicplayer.utils.GetCurrentTime;
import com.yuanyiwang.musicplayer.utils.GetFormattedTime;
import com.yuanyiwang.musicplayer.utils.GetShuffledList;
import com.yuanyiwang.musicplayer.utils.GetTypeSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicPlayerActivity extends AppCompatActivity {
    public static ArrayList<MusicFiles> listOfSongs;
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    ImageView albumArt;
    ImageView playButton, previousButton, nextButton, shuffleButton, repeatButton;
    TextView trackTitle, trackArtist;
    TextView curTime, totalTime;
    TextView bitRate, sampleRate, bitDepth, fileType, fileSize;
    SeekBar seekBar;
    boolean shuffle = false;
    boolean repeat = false;
    int position = -1;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Objects.requireNonNull(getSupportActionBar()).hide();

        initSongs();
        initViews();
        initMediaPlayer();
        initSeekBar();
        initShuffleButton();
        initRepeatButton();
        setShuffleRepeatButton();
        setViewContents(uri, position);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Integer> shuffledList = GetShuffledList.getRandom();

        mediaPlayer.setOnCompletionListener(mediaPlayer -> nextButton.performClick());

        playButton.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                mediaPlayer.pause();
            }
            else {
                playButton.setImageResource(R.drawable.ic_baseline_pause_24);
                mediaPlayer.start();
            }
        });

        nextButton.setOnClickListener(view -> {
            mediaPlayer.stop();
            mediaPlayer.release();

            if (shuffle && !repeat) {
                position = shuffledList.get(index);
                index = (index + 1) % shuffledList.size();
            }
            else if (!shuffle && !repeat) {
                position = (position + 1) % listOfSongs.size();
            }

            buttonFunction(position);
        });

        previousButton.setOnClickListener(view -> {
            mediaPlayer.stop();
            mediaPlayer.release();

            if (shuffle && !repeat) {
                if (index == 0) {
                    index = shuffledList.size() - 1;
                }
                else {
                    index = index - 1;
                }
                position = shuffledList.get(index);
            }
            else if (!shuffle && !repeat) {
                if (position == 0) {
                    position = listOfSongs.size() - 1;
                }
                else {
                    position = position - 1;
                }
            }

            buttonFunction(position);
        });
    }

    private void buttonFunction(int position) {
        uri = Uri.parse(listOfSongs.get(position).getPath());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

        setViewContents(uri, position);

        playButton.setImageResource(R.drawable.ic_baseline_pause_24);

        mediaPlayer.start();

        seekBar.setMax(mediaPlayer.getDuration() / 1000);

        mediaPlayer.setOnCompletionListener(mediaPlayer -> nextButton.performClick());
    }

    private void getAlbumArt(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        byte[] art = retriever.getEmbeddedPicture();

        if (art != null) {
            Glide.with(this).asBitmap().load(art).into(albumArt);
        }
        else {
            Glide.with(this).asBitmap().load(R.drawable.default_album_art).into(albumArt);
        }
    }

    private void saveShuffle(Boolean shuffle) {
        SharedPreferences preferences = getSharedPreferences("Shuffle Preferences", 0);
        preferences.edit().putBoolean("shuffle", shuffle).commit();
    }

    private boolean loadShuffle() {
        SharedPreferences preferences = getSharedPreferences("Shuffle Preferences", 0);
        return preferences.getBoolean("shuffle", false);
    }

    private void saveRepeat(Boolean repeat) {
        SharedPreferences preferences = getSharedPreferences("Repeat Preferences", 0);
        preferences.edit().putBoolean("repeat", repeat).commit();
    }

    private boolean loadRepeat() {
        SharedPreferences preferences = getSharedPreferences("Repeat Preferences", 0);
        return preferences.getBoolean("repeat", false);
    }

    private void initViews() {
        albumArt = findViewById(R.id.albumArt);
        trackTitle = findViewById(R.id.trackTitle);
        trackArtist = findViewById(R.id.trackInfo);
        curTime = findViewById(R.id.curTime);
        totalTime = findViewById(R.id.totalTime);
        playButton = findViewById(R.id.playPause);
        previousButton = findViewById(R.id.previous);
        nextButton = findViewById(R.id.next);
        shuffleButton = findViewById(R.id.shuffle);
        repeatButton = findViewById(R.id.repeat);
        seekBar = findViewById(R.id.seekBar);
        curTime = findViewById(R.id.curTime);
        totalTime = findViewById(R.id.totalTime);
        bitRate = findViewById(R.id.bitRate);
        sampleRate = findViewById(R.id.sampleRate);
        bitDepth = findViewById(R.id.bitDepth);
        fileType = findViewById(R.id.fileType);
        fileSize = findViewById(R.id.fileSize);
    }

    private void initSongs() {
        String intentSender = getIntent().getStringExtra("album/folder_Content");
        if (intentSender != null && intentSender.equals("Album Content")) {
            listOfSongs = albumSongList;
        }
        else if (intentSender != null && intentSender.equals("Folder Content")) {
            listOfSongs = folderSongList;
        }
        else {
            listOfSongs = musicFiles;
        }
        position = getIntent().getIntExtra("Position", -1);
    }

    private void initMediaPlayer() {
        if (listOfSongs != null) {
            playButton.setImageResource(R.drawable.ic_baseline_pause_24);
            uri = Uri.parse(listOfSongs.get(position).getPath());
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
    }

    private void initSeekBar() {
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currentPosition);
                    curTime.setText(GetCurrentTime.getCurrentTime(currentPosition));
                }
                handler.postDelayed(this, 0);
            }
        });
    }

    private void initRepeatButton() {
        repeatButton.setOnClickListener(v -> {
            if (repeat) {
                repeat = false;
                repeatButton.setImageResource(R.drawable.ic_baseline_repeat_off_24);
            }
            else {
                repeat = true;
                repeatButton.setImageResource(R.drawable.ic_baseline_repeat_24);
            }
            saveRepeat(repeat);
        });
    }

    private void initShuffleButton() {
        shuffleButton.setOnClickListener(v -> {
            if (shuffle) {
                shuffle = false;
                shuffleButton.setImageResource(R.drawable.ic_baseline_shuffle_off_24);
            }
            else {
                shuffle = true;
                shuffleButton.setImageResource(R.drawable.ic_baseline_shuffle_24);
            }
            saveShuffle(shuffle);
        });
    }

    private void setShuffleRepeatButton() {
        if (loadShuffle()) {
            shuffleButton.setImageResource(R.drawable.ic_baseline_shuffle_24);
            shuffle = true;
        }
        else {
            shuffleButton.setImageResource(R.drawable.ic_baseline_shuffle_off_24);
            shuffle = false;
        }

        if (loadRepeat()) {
            repeatButton.setImageResource(R.drawable.ic_baseline_repeat_24);
            repeat = true;
        }
        else {
            repeatButton.setImageResource(R.drawable.ic_baseline_repeat_off_24);
            repeat = false;
        }
    }

    private void setViewContents(Uri uri, int position) {
        totalTime.setText(GetFormattedTime.formatTime(position));
        trackTitle.setSelected(true);
        trackTitle.setText(listOfSongs.get(position).getTitle());
        trackArtist.setSelected(true);
        trackArtist.setText(listOfSongs.get(position).getArtist());
        fileType.setText(GetTypeSize.getType(position));
        bitRate.setText(GetAdvancedInfo.getBitRate(position));
        sampleRate.setText(GetAdvancedInfo.getSampleRate(position));
        bitDepth.setText(GetAdvancedInfo.getBitDepth(position));
        fileSize.setText(GetTypeSize.getFileSize(position));
        getAlbumArt(uri);
    }
}