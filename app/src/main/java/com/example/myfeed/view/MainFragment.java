package com.example.myfeed.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfeed.view.adapters.CategoryRecyclerAdapter;
import com.example.myfeed.view.adapters.MediaRecyclerAdapter;
import com.example.myfeed.databinding.FragmentMainBinding;
import com.example.myfeed.viewmodel.UserActivityViewModel;

import java.util.ArrayList;


public class MainFragment extends Fragment {


   private FragmentMainBinding binding;


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding =null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =FragmentMainBinding.inflate(inflater);
        View view= binding.getRoot();
        NavController navController= NavHostFragment.findNavController(this);


        UserActivityViewModel model=new ViewModelProvider(requireActivity()).get(UserActivityViewModel.class);
        model.getMediaFiles().observe(requireActivity(), userFeeds -> {
            MediaRecyclerAdapter mediaRecyclerAdapter =new MediaRecyclerAdapter(requireContext(),userFeeds);
            LinearLayoutManager layoutManager1=new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
            binding.videoRecyclerView.setLayoutManager(layoutManager1);
            binding.videoRecyclerView.setAdapter(mediaRecyclerAdapter);
        });


        // must be changed in production , just for example
        // leave the first elemnt in the list empty
        ArrayList<String> categoryNames=new ArrayList<>();
        categoryNames.add("Nature");
        categoryNames.add("People");
        categoryNames.add("Music");
        categoryNames.add("Games");
        categoryNames.add("Tigers");

        CategoryRecyclerAdapter adapter=new CategoryRecyclerAdapter(categoryNames,navController);
        adapter.setCategorySelectedCallback(name -> {
            Toast.makeText(requireContext(),name,Toast.LENGTH_SHORT).show();
        });

        LinearLayoutManager layoutManager =new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.categoriesRecyclerView.setLayoutManager(layoutManager);
        binding.categoriesRecyclerView.setAdapter(adapter);



        return view;
    }





}

