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

public class AlbumContentsAdapter extends RecyclerView.Adapter<AlbumContentsAdapter.ViewHolder> {
    public static ArrayList<MusicFiles> albumSongList;
    private final Context context;

    public AlbumContentsAdapter(Context context, ArrayList<MusicFiles> albumSongList) {
        this.context = context;
        AlbumContentsAdapter.albumSongList = albumSongList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.albumSongListName.setSelected(true);
        holder.albumSongListName.setText(albumSongList.get(position).getTitle());
        holder.albumSongListArtist.setSelected(true);
        holder.albumSongListArtist.setText(albumSongList.get(position).getArtist());
        setAlbumArt(holder, position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MusicPlayerActivity.class);
            intent.putExtra("album/folder_Content", "Album Content");
            intent.putExtra("Position", position);
            context.startActivity(intent);
        });
    }

    private void setAlbumArt(ViewHolder holder, int position) {
        byte[] albumArt = GetAlbumArt.getAlbumArt(albumSongList.get(position).getPath());
        if (albumArt != null) {
            Glide.with(context).asBitmap().load(albumArt).into(holder.albumSongListImage);
        }
        else {
            Glide.with(context).asBitmap().load(R.drawable.default_album_art_small).into(holder.albumSongListImage);
        }
    }

    @Override
    public int getItemCount() {
        return albumSongList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumSongListImage;
        TextView albumSongListName, albumSongListArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumSongListImage = itemView.findViewById(R.id.albumArtList);
            albumSongListName = itemView.findViewById(R.id.fileName);
            albumSongListArtist = itemView.findViewById(R.id.artist);
        }
    }
}
