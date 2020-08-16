package com.example.myfeed;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;
import com.example.myfeed.data.UserFeed;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class DownloadUtil {

    public final String DOWNLOADS_REQUESTS_SHARED="com.example.myfeed_requests_shared";
    public final String DOWNLOADS_LIST_KEY="com.example.myfeed_requests_shared";
    private ArrayList<UserFeed> downloads=new ArrayList<>();

    private static volatile DownloadUtil downloadingUtil;

    public static DownloadUtil getInstance() {
        synchronized (DownloadUtil.class){
            if(downloadingUtil==null){
                downloadingUtil=new DownloadUtil();
                return downloadingUtil;
            }
            return downloadingUtil;
        }
    }


    /**
     * create directory to store app downloads
     * in the external storage.
     * */
    private  File createAppDownloadsDirectory(){
        File file=new File(App.appContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"MyFeedDownloads");
        if(!file.exists()){
            if(file.mkdir()){
                return file;
            }else {
                return null;
            }
        }
        return file;

    }

    /**
     *  File must be in the external storage to work with the download manager API
     *  or it will be stored in the cache which could be freed anytime by the system
     *  thats why we prevent downloading without SD-card, if no destination file provided.
     *  passing an internal storage file will result in exception.
     * */

    private Uri prepareFilePath(String extension){
        File directory=createAppDownloadsDirectory();
        if(directory==null){
            return null;
        }
        long l=System.currentTimeMillis();
        String fileName="media"+l+extension;
        File file=new File(directory,fileName);
        return Uri.fromFile(file);
    }


    public Long downloadLink(String uri, UserFeed userFeed){
        if(!externalStorageCheck()){
          return null;
        }

        DownloadManager.Request request= new DownloadManager.Request(Uri.parse(uri));
        String typeExtension;
        if(userFeed.isVideo()){
            request.setMimeType("video/*");
            typeExtension=".mp4";
        }else{
            typeExtension=".jpg";
            request.setMimeType("image/*");
        }

        Uri downloadFile= prepareFilePath(typeExtension);
        if(downloadFile!=null){
            userFeed.setFilePath(downloadFile.getPath());
            request.addRequestHeader("AUTHENTICATION",App.appContext().getString(R.string.PEXELS_API_KEY));
            request.setDestinationUri(downloadFile);
            DownloadManager downloadManager = (DownloadManager) App.appContext().getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                Long downloadID=downloadManager.enqueue(request);
                Toast.makeText(App.appContext(),"Downloading your file",Toast.LENGTH_LONG).show();
                userFeed.setFeedID(downloadID+"");
                saveDownloadRequests(new UserFeed(userFeed));
                return downloadID;
            }
        }
        Toast.makeText(App.appContext(),"Error Downloading your file",Toast.LENGTH_LONG).show();
        return null;
    }

    public void saveDownloadRequests(UserFeed userFeed){
        SharedPreferences sharedPreferences =App.appContext().getSharedPreferences(DOWNLOADS_REQUESTS_SHARED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        downloads=getDownloadRequests();
        downloads.add(userFeed);
        Gson gson=new Gson();
        String listString=gson.toJson(downloads);
        editor.putString(DOWNLOADS_LIST_KEY,listString);
        editor.apply();
    }

    public  ArrayList<UserFeed> getDownloadRequests(){
        SharedPreferences sharedPreferences =App.appContext().getSharedPreferences(DOWNLOADS_REQUESTS_SHARED,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String listString=sharedPreferences.getString(DOWNLOADS_REQUESTS_SHARED,null);
        if(listString!=null){
            Type type=new TypeToken<ArrayList<UserFeed>>(){}.getType();
            return gson.fromJson(listString,type);
        }else {
            return new ArrayList<>();
        }
    }

    public boolean removeFromList(final String id){
        boolean b=false;
        downloads=getDownloadRequests();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           b=downloads.removeIf(userFeed-> userFeed.getFeedID().equals(id));
            if(b){
                saveList();
            }
            return b;
        }

        for(UserFeed download : downloads){
            if(download.getFeedID().equals(id)){
                b= downloads.remove(download);
               if(b){
                   saveList();
               }
            }
        }

        return b;
    }


    private void saveList(){
        Gson gson=new Gson();
        SharedPreferences sharedPreferences =App.appContext().getSharedPreferences(DOWNLOADS_REQUESTS_SHARED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String listString=gson.toJson(downloads);
        editor.putString(DOWNLOADS_LIST_KEY,listString);
        editor.apply();
    }

    private boolean externalStorageCheck(){
       return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
