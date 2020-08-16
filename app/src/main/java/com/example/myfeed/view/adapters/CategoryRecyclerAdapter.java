package com.example.myfeed.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfeed.databinding.CategoryLayoutBinding;
import com.example.myfeed.databinding.DownlodsRecyclerLayoutBinding;
import com.example.myfeed.view.MainFragmentDirections;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {


     private NavController navController;
     private CategorySelectedCallback categorySelectedCallback;
     private ArrayList<String> categoryNames;


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 1;
        }
        return 2;

    }

    public CategoryRecyclerAdapter(ArrayList<String> categoryNames, NavController navController) {
        this.categoryNames=categoryNames;
        this.navController=navController;
        addDownloadTabs();

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=null;
        if(viewType==1){
            DownlodsRecyclerLayoutBinding binding=DownlodsRecyclerLayoutBinding.inflate(layoutInflater,parent,false);
            view=binding.getRoot();
        }
        if(viewType==2){
            CategoryLayoutBinding binding = CategoryLayoutBinding.inflate(layoutInflater,parent,false);
            view=binding.getRoot();
        }
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
       if(position==0){
           holder.root.setOnClickListener(v -> navController.navigate(MainFragmentDirections.actionMainFragmentToDownloadsFragment()));
       }else{

          holder.categoryTitle.setText(categoryNames.get(position));
          holder.root.setOnClickListener(v-> {
               if(this.categorySelectedCallback!=null){
                   categorySelectedCallback.onClicked(holder.categoryTitle.getText().toString());
               }
           });
       }
    }

    @Override
    public int getItemCount() {
        return categoryNames.size();
    }

    public void setCategorySelectedCallback(CategorySelectedCallback categorySelectedCallback) {
        this.categorySelectedCallback = categorySelectedCallback;
    }


    static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        CategoryLayoutBinding binding = CategoryLayoutBinding.bind(itemView);
        RelativeLayout root=binding.categoryLayout;
        TextView categoryTitle=binding.categoryText;
    }


    private void addDownloadTabs(){
        categoryNames.add(0,"");
    }

    public interface  CategorySelectedCallback{
        void onClicked(String name);
    }

}
