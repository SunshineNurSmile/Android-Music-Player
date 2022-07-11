package com.yuanyiwang.musicplayer.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.adapter.ViewPagerAdapter;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.fragment.AlbumsFragment;
import com.yuanyiwang.musicplayer.fragment.FoldersFragment;
import com.yuanyiwang.musicplayer.fragment.SongsFragment;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 100;
    public static ArrayList<MusicFiles> musicFiles = new ArrayList<>();
    public static ArrayList<MusicFiles> albumFiles = new ArrayList<>();
    public static ArrayList<MusicFiles> folderFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        requestPermissions();
    }

    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new SongsFragment(), "Songs");
        viewPagerAdapter.addFragments(new AlbumsFragment(), "Albums");
        viewPagerAdapter.addFragments(new FoldersFragment(), "Folders");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
        else {
            getMusicFiles(this);
            initViewPager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                getMusicFiles(this);
                initViewPager();
            }
            else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void getMusicFiles(Context context) {
        ArrayList<String> duplicateAlbums = new ArrayList<>();
        ArrayList<String> duplicateFolders = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] infoLoader = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.BITRATE,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM,
        };

        Cursor cursor = context.getContentResolver().query(uri, infoLoader, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(0);
                String artist = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String bitrate = cursor.getString(4);
                String size = cursor.getString(5);
                String album = cursor.getString(6);

                MusicFiles mFiles = new MusicFiles(title, artist, duration, path, bitrate, size, album);
                musicFiles.add(mFiles);

                if (!duplicateAlbums.contains(album)) {
                    albumFiles.add(mFiles);
                    duplicateAlbums.add(album);
                }

                String folder = path.substring(0, path.lastIndexOf("/"));
                if (!duplicateFolders.contains(folder)) {
                    folderFiles.add(mFiles);
                    duplicateFolders.add(folder);
                }
            }
            cursor.close();
        }
    }
}