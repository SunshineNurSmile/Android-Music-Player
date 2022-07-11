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

import com.yuanyiwang.musicplayer.R;
import com.yuanyiwang.musicplayer.activity.FolderContents;
import com.yuanyiwang.musicplayer.data.MusicFiles;
import com.yuanyiwang.musicplayer.utils.GetFolderContents;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private final Context context;
    public static ArrayList<MusicFiles> folderFiles;

    public FolderAdapter(Context context, ArrayList<MusicFiles> folderFiles) {
        this.context = context;
        FolderAdapter.folderFiles = folderFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.folderName.setSelected(true);
        holder.folderName.setText(GetFolderContents.getFolderPath(position));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FolderContents.class);
            intent.putExtra("Folder Name", folderFiles.get(position).getPath().substring(0, folderFiles.get(position).getPath().lastIndexOf("/")));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return folderFiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderIcon;
        TextView folderName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderIcon = itemView.findViewById(R.id.folderIcon);
            folderName = itemView.findViewById(R.id.folderName);
        }
    }
}
