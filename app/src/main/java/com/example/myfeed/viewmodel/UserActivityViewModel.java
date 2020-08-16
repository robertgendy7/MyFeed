package com.example.myfeed.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfeed.DownloadUtil;
import com.example.myfeed.DownloadsDataListener;
import com.example.myfeed.Repository;
import com.example.myfeed.data.UserFeed;
import com.example.myfeed.remote.GoogleHttpClientFunctions;
import com.example.myfeed.remote.Remote;

import java.util.ArrayList;


public class UserActivityViewModel extends ViewModel {

    private  MutableLiveData<ArrayList<UserFeed>> mediaFiles;
    private  MutableLiveData<ArrayList<UserFeed>> downloadRequests;

    /** must keep a strong reference to the DownloadListener instance.
     *  else it will be garbage collected and new
     *  downloads will not get reflected live
     * */
    private DownloadsDataListener listener;
    private  Repository repository=new Repository(new Remote(new GoogleHttpClientFunctions()));




    public MutableLiveData<ArrayList<UserFeed>> getMediaFiles(){
        if(mediaFiles==null){
            mediaFiles=new MutableLiveData<>();
            repository.getData(mediaFiles);
        }
       return mediaFiles;
    }

    public MutableLiveData<ArrayList<UserFeed>> getDownloadRequests(){
        if(downloadRequests == null){
            downloadRequests = new MutableLiveData<>();
            downloadRequests.setValue( DownloadUtil.getInstance().getDownloadRequests());
            keepDownloadsUpdated();
            return downloadRequests;
        }
        return downloadRequests;

    }

     void keepDownloadsUpdated(){
      listener =new DownloadsDataListener();
         listener.notifyWithChanges(downloads -> downloadRequests.setValue(downloads));
    }





}
