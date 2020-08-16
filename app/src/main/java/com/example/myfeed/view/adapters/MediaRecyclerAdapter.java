package com.example.myfeed.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfeed.DownloadUtil;
import com.example.myfeed.data.UserFeed;
import com.example.myfeed.databinding.VideoLayoutBinding;

import java.util.ArrayList;

public class MediaRecyclerAdapter extends RecyclerView.Adapter<MediaRecyclerAdapter.MediaViewHolder>{


    private Context context;
    private ArrayList<UserFeed> userFeeds;

    public MediaRecyclerAdapter(Context context, ArrayList<UserFeed> userFeeds) {
        this.context = context;
        this.userFeeds = userFeeds;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=VideoLayoutBinding.inflate(layoutInflater,parent,false).getRoot();
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
       final UserFeed userFeed = userFeeds.get(position);
        holder.mediaTitle.setText(userFeed.getTitle());
        Glide.with(context).load(userFeed.getThumbnail()).into(holder.thumbnail);
       if(!userFeed.isVideo()){
          holder.mediaDuration.setVisibility(ViewGroup.INVISIBLE);
          holder.playButton.setVisibility(ViewGroup.INVISIBLE);

       }else{
           holder.mediaDuration.setText(userFeed.getDuration()+"min");
       }

       holder.shareButton.setOnClickListener(new View.OnClickListener() {
           // this will only share the link in order to share the actual media file
           // we need it in form of a file , must be downloaded first ,so we leave
           // that functionality to the downloaded part of the app.

           String link=userFeed.getLink();
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Intent.ACTION_SEND);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra(Intent.EXTRA_TEXT,link);
               intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               Log.d("te~st", "onClick: "+link);
               intent.setType("text/plain");
               Intent.createChooser(intent,"Share Link");
               context.startActivity(intent);

           }
       });

       holder.downloadButton.setOnClickListener(v -> {
           DownloadUtil downloadingUtil = DownloadUtil.getInstance();
           Long result =downloadingUtil.downloadLink(userFeed.getLink(),userFeed);
             if(result==null){
                 Toast.makeText(context,"External storage is not ready !",Toast.LENGTH_SHORT).show();
              }
       });
    }

    @Override
    public int getItemCount() {
        return userFeeds.size();
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        VideoLayoutBinding videoLayoutBinding =VideoLayoutBinding.bind(itemView);
        ImageView thumbnail=videoLayoutBinding.mediaThumbnail;
        TextView mediaTitle=videoLayoutBinding.mediaTitle;
        TextView mediaDuration=videoLayoutBinding.mediaDuration;
        ImageView playButton=videoLayoutBinding.playButton;
        TextView shareButton=videoLayoutBinding.shareButton;
        TextView downloadButton=videoLayoutBinding.downloadButton;
    }
}
