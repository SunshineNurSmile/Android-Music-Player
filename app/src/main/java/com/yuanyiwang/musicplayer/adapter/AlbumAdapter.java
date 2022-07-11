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
import com.yuanyiwang.musicplayer.activity.AlbumContents;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.utils.GetAlbumArt;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<MusicFiles> albumFiles;

    public AlbumAdapter(Context context, ArrayList<MusicFiles> albumFiles) {
        this.context = context;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.albumName.setSelected(true);
        holder.albumName.setText(albumFiles.get(position).getAlbum());
        holder.artist.setSelected(true);
        holder.artist.setText(albumFiles.get(position).getArtist());
        setAlbumArt(holder, position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlbumContents.class);
            intent.putExtra("Album Name", albumFiles.get(position).getAlbum());
            context.startActivity(intent);
        });
    }

    private void setAlbumArt(ViewHolder holder, int position) {
        byte[] albumArt = GetAlbumArt.getAlbumArt(albumFiles.get(position).getPath());
        if (albumArt != null) {
            Glide.with(context).asBitmap().load(albumArt).into(holder.albumImage);
        }
        else {
            Glide.with(context).asBitmap().load(R.drawable.default_album_art_small).into(holder.albumImage);
        }
    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumName, artist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImage = itemView.findViewById(R.id.albumArtAlbum);
            albumName = itemView.findViewById(R.id.albumName);
            artist = itemView.findViewById(R.id.albumArtist);
        }
    }
}
