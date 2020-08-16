package com.example.myfeed.view.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myfeed.App;
import com.example.myfeed.DownloadUtil;
import com.example.myfeed.data.UserFeed;
import com.example.myfeed.databinding.DownloadedLayoutBinding;

import java.io.File;
import java.util.ArrayList;

public class DownloadsRecyclerAdapter extends RecyclerView.Adapter<DownloadsRecyclerAdapter.DownloadsViewHolder>{

    private DownloadedLayoutBinding binding;
    private Context context;
    private ArrayList<UserFeed> downloads;



    public DownloadsRecyclerAdapter(Context context, ArrayList<UserFeed> downloads) {
        this.context = context;
        this.downloads = downloads;
    }

    @NonNull
    @Override
    public DownloadsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        binding=DownloadedLayoutBinding.inflate(layoutInflater,parent,false);
        View view=binding.getRoot();
        return new DownloadsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadsViewHolder holder, int position) {
       final UserFeed download=downloads.get(position);
       holder.title.setText(download.getTitle());
       Glide.with(context).load(download.getThumbnail()).into(holder.thumbnail);
       if(!download.isVideo()){
           holder.playButton.setVisibility(ViewGroup.INVISIBLE);
           holder.duration.setVisibility(View.INVISIBLE);
       }else {
           holder.duration.setText(download.getDuration()+"min");
       }

       holder.shareButton.setOnClickListener(v -> {


           File file=new File(download.getFilePath());
           if(file.exists()){
               Intent intent = new Intent(Intent.ACTION_SEND);
               if(download.isVideo()){
                   intent.setType("video/*");
               }else{
                   intent.setType("image/*");
               }
               intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context,context.getPackageName(),file));
               intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }else {
               Toast.makeText(context,"Error,sharing the file",Toast.LENGTH_LONG).show();
           }

       });

       holder.deleteButton.setOnClickListener(v -> {

              File file=new File(download.getFilePath());
              if(file.exists()){
                  if(file.delete()){
                      if(DownloadUtil.getInstance().removeFromList(download.getFeedID())){
                          Toast.makeText(context,"File deleted",Toast.LENGTH_SHORT).show();

                      }
                  }else {
                      Toast.makeText(context,"Error deleting the file",Toast.LENGTH_SHORT).show();
                  }
              }else{
                  // file is already deleted try to delete it form the UI.
                  Toast.makeText(context,"Error deleting the file ",Toast.LENGTH_SHORT).show();
              }

       });


       holder.root.setOnClickListener(v->{
           File file =new File(download.getFilePath());
           if(file.exists()){
               viewFile(file);
           }
       });

       holder.playButton.setOnClickListener(v->{
           File file =new File(download.getFilePath());
           if(file.exists()){
               viewFile(file);
           }
       });

     }



    @Override
    public int getItemCount() {
       return downloads.size();
    }


    static class DownloadsViewHolder extends RecyclerView.ViewHolder{
        public DownloadsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        DownloadedLayoutBinding binding=DownloadedLayoutBinding.bind(itemView);
        TextView title=binding.downloadedTitle;
        TextView duration=binding.downloadedDuration;
        TextView shareButton=binding.downloadedShare;
        TextView deleteButton=binding.downloadedDelete;
        ImageView playButton=binding.downloadedPlay;
        ImageView thumbnail=binding.downloadedThumbnail;
        CardView root=binding.root;

    }



    // View from link only as in the main stream , is a big part on its own since it
    // requires streaming , with will require a connection to
    // the production API , so it is left for production.
    private void viewFile(File file){
        Context context=App.appContext();
        Uri uri=FileProvider.getUriForFile(context,context.getPackageName(),file);
        try{
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(uri);
            App.appContext().startActivity(intent);
        }catch (ActivityNotFoundException exception){
            Toast.makeText(App.appContext(),"Error ! Cannot view the file",Toast.LENGTH_SHORT).show();
        }
    }
}
