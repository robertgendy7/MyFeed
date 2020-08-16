package com.example.myfeed;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myfeed.data.Image;
import com.example.myfeed.data.UserFeed;
import com.example.myfeed.data.Video;
import com.example.myfeed.data.response.ImageResponse;
import com.example.myfeed.data.response.ResponseBase;
import com.example.myfeed.data.response.VideoResponse;
import com.example.myfeed.db.MediaFilesMemory;
import com.example.myfeed.remote.Remote;
import com.example.myfeed.remote.RequestResultListener;

import java.util.ArrayList;

public class Repository {



    private Remote remote;

    private MutableLiveData<ArrayList<UserFeed>> mediaFiles;
    private ArrayList<Image> images;
    private ArrayList<Video> videos;

    HandlerThread thread;

    public Repository(Remote remote) {
        thread=new HandlerThread("Repository thread");
        this.remote = remote;
    }


    public void getData(MutableLiveData<ArrayList<UserFeed>> list){
        this.mediaFiles=list;
        images=MediaFilesMemory.getInstance().fetchImages();
        videos=MediaFilesMemory.getInstance().fetchVideos();
        if(images==null || videos ==null){
            Log.d("te~st", "getData: no saved data");
        }else{
            Log.d("te~st", "getData: preparing the list form saved data");
            mediaFiles=getUserFeedList();
        }
        runRemote();
    }


    void runRemote(){
        thread.start();
        Handler handler=new Handler(thread.getLooper());
        handler.post(() -> remote.fetchImages(new RequestResultListener() {
            @Override
            public void onSuccess(ResponseBase response) {
                ImageResponse imageResponse =(ImageResponse) response;
                images=imageResponse.getPhotos();
                MediaFilesMemory.getInstance().storeImages(images);
                Log.d("te~st", "getData: image fetch success");
                remote.fetchVideos(new RequestResultListener() {
                    @Override
                    public void onSuccess(ResponseBase response) {
                        VideoResponse videoResponse = (VideoResponse) response;
                        videos =videoResponse.getVideos();
                        MediaFilesMemory.getInstance().storeVideos(videos);
                        mediaFiles=getUserFeedList();
                        Log.d("te~st", "getData: video fetch success");

                    }

                    @Override
                    public void onRequestFailed(int httpResponseCode) {
                        Log.d("te~st", "getData: video fetch failed");

                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.d("te~st", "getData: video fetch failed with exception"+e.getCause());

                    }
                });
            }

            @Override
            public void onRequestFailed(int httpResponseCode) {
                Log.d("te~st", "getData: image fetch failed");

            }

            @Override
            public void onFailed(Exception e) {
                Log.d("te~st", "getData: image fetch failed with exception"+e.getCause());

            }
        }));
    }


    /**
     *  function to display only 4 videos and 2 images ,
     *  can be replaced in production , does not carry core functionality
     **/

   MutableLiveData<ArrayList<UserFeed>> getUserFeedList(){
       Log.d("te~st", "getData: getting user feed" );

       final ArrayList<UserFeed> userFeeds=new ArrayList<>();
        for(int i =0 ; i<4 ;i++){
            Video video=videos.get(i);
            String title=video.getUser().getName();
            String thumbnail=video.getImage();
            String id=video.getId()+"";
            String link=video.getVideo_files()[0].getLink();
            UserFeed userFeed=new UserFeed(title,thumbnail,true,link,id);
            userFeed.setDuration(video.getDuration());
            userFeeds.add(userFeed);
        }

        for(int i=0 ; i<2 ;i++){
            Image image=images.get(i);
            String title=image.getPhotographer();
            String thumbnail=image.getSrc().getOriginal();
            String link=image.getSrc().getOriginal();
            String id=image.getId()+"";
            UserFeed userFeed=new UserFeed(title,thumbnail,false,link,id);
            userFeeds.add(userFeed);
        }
        new Handler(Looper.getMainLooper()).post(() -> mediaFiles.setValue(userFeeds));
        return mediaFiles;

    }






}
