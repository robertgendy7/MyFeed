package com.example.myfeed.db;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.myfeed.App;
import com.example.myfeed.data.Image;
import com.example.myfeed.data.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MediaFilesMemory implements LocalDataFunctions {



   static String VIDEOS_SHARED_KEY="com.example.myfeed.video_shared_key";
   static String IMAGES_SHARED_KEY="com.example.myfeed.image_shared_key";
   static String VIDEOS_LIST_KEY="com.example.myfeed.video_list_key";
   static String IMAGES_LIST_KEY="com.example.myfeed.image_list_key";


   private static  volatile MediaFilesMemory mediaFilesMemory;
   private static ArrayList<Image> images=new ArrayList<>();
   private static ArrayList<Video> videos=new ArrayList<>();



   public static MediaFilesMemory getInstance() {
        synchronized (MediaFilesMemory.class){
            if(mediaFilesMemory!=null){
                return mediaFilesMemory;
            }
            mediaFilesMemory=new MediaFilesMemory();
            return mediaFilesMemory;
        }
    }

   /**
    * function will reset value of current list stored
    * **/

    @Override
    public void storeVideos(ArrayList<Video> list) {
        Gson gson=new Gson();
        ArrayList<Video> temp=fetchVideos();
       if(temp==null){
           videos=list;
       }else {
           videos=temp;
       }
        String listString=gson.toJson(videos);

        SharedPreferences sharedPreferences= App.appContext().getSharedPreferences(VIDEOS_SHARED_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(VIDEOS_LIST_KEY,listString);
        editor.apply();
    }

    /**
     * function will reset value of current list stored
     * **/
    @Override
    public void storeImages(ArrayList<Image> list) {
        Gson gson=new Gson();
        ArrayList<Image> temp=fetchImages();
        if(temp==null){
            images.addAll(list);
        }else{
            images=temp;
        }

        String listString=gson.toJson(images);
        SharedPreferences sharedPreferences=App.appContext().getSharedPreferences(IMAGES_SHARED_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(IMAGES_LIST_KEY,listString);
        editor.apply();
    }

    @Override
    public ArrayList<Image> fetchImages() {
        Gson gson=new Gson();
        Type type= new TypeToken<ArrayList<Image>>(){}.getType();
        SharedPreferences sharedPreferences=App.appContext().getSharedPreferences(IMAGES_SHARED_KEY,Context.MODE_PRIVATE);
        String listString=sharedPreferences.getString(IMAGES_LIST_KEY,null);
        if(listString==null){
            return null;
        }
        return gson.fromJson(listString,type);
    }

    @Override
    public ArrayList<Video> fetchVideos() {
        Gson gson=new Gson();
        Type type= new TypeToken<ArrayList<Video>>(){}.getType();
        SharedPreferences sharedPreferences=App.appContext().getSharedPreferences(VIDEOS_SHARED_KEY,Context.MODE_PRIVATE);
        String listString=sharedPreferences.getString(VIDEOS_LIST_KEY,null);
        if(listString==null){
            return null;
        }
        return gson.fromJson(listString,type);
    }






}
