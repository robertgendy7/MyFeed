package com.example.myfeed;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.myfeed.data.UserFeed;

import java.util.ArrayList;

public class DownloadsDataListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String DOWNLOADS_REQUESTS_SHARED="com.example.myfeed_requests_shared";

    public static DownloadsDataChangedListener dataChangedListener;

    public DownloadsDataListener() {
        SharedPreferences.OnSharedPreferenceChangeListener listener=this;
        SharedPreferences sharedPreferences =App.appContext().getSharedPreferences(DOWNLOADS_REQUESTS_SHARED,Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
           ArrayList<UserFeed> downloads= DownloadUtil.getInstance().getDownloadRequests();
           dataChangedListener.onChanged(downloads);

    }


   public void notifyWithChanges(DownloadsDataChangedListener listener) {
       dataChangedListener = listener;
   }


   public interface DownloadsDataChangedListener{
        void onChanged(ArrayList<UserFeed> downloads);
    }

}
