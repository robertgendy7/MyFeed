package com.example.myfeed.view;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myfeed.databinding.ActivityUserBinding;
import com.example.myfeed.viewmodel.UserActivityViewModel;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;



    @Override
    protected void onDestroy() {
        binding=null;
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        UserActivityViewModel model=new ViewModelProvider(this).get(UserActivityViewModel.class);
        model.getMediaFiles();

        IntentFilter intentFilter =new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(broadcastReceiver,intentFilter);

    }


    // will be triggered for  every successful download.
    // in production check with the download ID.
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Download finished",Toast.LENGTH_LONG).show();
        }
    };

}