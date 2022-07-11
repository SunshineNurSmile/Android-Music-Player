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

public class FolderContentsAdapter extends RecyclerView.Adapter<FolderContentsAdapter.ViewHolder> {
    public static ArrayList<MusicFiles> folderSongList;
    private final Context context;

    public FolderContentsAdapter(Context context, ArrayList<MusicFiles> folderSongList) {
        this.context = context;
        FolderContentsAdapter.folderSongList = folderSongList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item, parent, false);
        return new FolderContentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.folderSongListName.setSelected(true);
        holder.folderSongListName.setText(folderSongList.get(position).getTitle());
        holder.folderSongListArtist.setSelected(true);
        holder.folderSongListArtist.setText(folderSongList.get(position).getArtist());
        setAlbumArt(holder, position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MusicPlayerActivity.class);
            intent.putExtra("album/folder_Content", "Folder Content");
            intent.putExtra("Position", position);
            context.startActivity(intent);
        });
    }

    private void setAlbumArt(ViewHolder holder, int position) {
        byte[] albumArt = GetAlbumArt.getAlbumArt(folderSongList.get(position).getPath());
        if (albumArt != null) {
            Glide.with(context).asBitmap().load(albumArt).into(holder.folderSongListImage);
        }
        else {
            Glide.with(context).asBitmap().load(R.drawable.default_album_art_small).into(holder.folderSongListImage);
        }
    }

    @Override
    public int getItemCount() {
        return folderSongList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderSongListImage;
        TextView folderSongListName, folderSongListArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderSongListImage = itemView.findViewById(R.id.albumArtList);
            folderSongListName = itemView.findViewById(R.id.fileName);
            folderSongListArtist = itemView.findViewById(R.id.artist);
        }
    }
}
