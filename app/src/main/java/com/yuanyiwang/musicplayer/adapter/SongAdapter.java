package com.yuanyiwang.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.activity.MusicPlayerActivity;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.utils.GetAlbumArt;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<MusicFiles> musicFiles;

    public SongAdapter(Context context, ArrayList<MusicFiles> musicFiles) {
        this.context = context;
        this.musicFiles = musicFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fileName.setSelected(true);
        holder.fileName.setText(musicFiles.get(position).getTitle());
        holder.artist.setSelected(true);
        holder.artist.setText(musicFiles.get(position).getArtist());
        setAlbumArt(holder, position);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MusicPlayerActivity.class);
            intent.putExtra("Position", position);
            context.startActivity(intent);
        });
    }

    private void setAlbumArt(ViewHolder holder, int position) {
        byte[] albumArt = GetAlbumArt.getAlbumArt(musicFiles.get(position).getPath());
        if (albumArt != null) {
            Glide.with(context).asBitmap().load(albumArt).into(holder.albumArtList);
        }
        else {
            Glide.with(context).asBitmap().load(R.drawable.default_album_art_small).into(holder.albumArtList);
        }
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName, artist;
        ImageView albumArtList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            artist = itemView.findViewById(R.id.artist);
            albumArtList = itemView.findViewById(R.id.albumArtList);
        }
    }
}
