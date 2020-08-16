package com.example.myfeed.db;

import com.example.myfeed.data.Image;
import com.example.myfeed.data.Video;

import java.util.ArrayList;

public interface LocalDataFunctions {

    void storeVideos(ArrayList<Video> list);
    void storeImages(ArrayList<Image> list);
    ArrayList<Image> fetchImages();
    ArrayList<Video> fetchVideos();

}
