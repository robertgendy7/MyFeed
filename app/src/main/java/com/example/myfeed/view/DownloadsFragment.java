package com.example.myfeed.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfeed.App;
import com.example.myfeed.data.UserFeed;
import com.example.myfeed.databinding.FragmentDownloadsBinding;
import com.example.myfeed.view.adapters.DownloadsRecyclerAdapter;
import com.example.myfeed.viewmodel.UserActivityViewModel;

import java.util.ArrayList;


public class DownloadsFragment extends Fragment {

    private FragmentDownloadsBinding binding;
    UserActivityViewModel model;


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }

    @Override
    public void onStop() {
        super.onStop();
        model.getDownloadRequests().removeObserver(observer);

    }

    @Override
    public void onResume() {
        super.onResume();
        model.getDownloadRequests().observe(requireActivity(),observer);
    }

    private Observer<ArrayList<UserFeed>> observer=new Observer<ArrayList<UserFeed>>() {
        @Override
        public void onChanged(ArrayList<UserFeed> userFeeds) {
            DownloadsRecyclerAdapter adapter=new DownloadsRecyclerAdapter(App.appContext(),userFeeds);
            LinearLayoutManager layoutManager=new LinearLayoutManager(App.appContext());
            binding.downloadsRecyclerView.setLayoutManager(layoutManager);
            binding.downloadsRecyclerView.setAdapter(adapter);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding=FragmentDownloadsBinding.inflate(inflater);
        View view=binding.getRoot();
        model = new ViewModelProvider(requireActivity()).get(UserActivityViewModel.class);
        return view;

    }
}