package com.example.myfeed.data.response;

import com.example.myfeed.data.Video;
import com.google.api.client.util.Key;

import java.util.ArrayList;

public class VideoResponse extends ResponseBase{
   @Key
   ArrayList<Video> videos;

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
