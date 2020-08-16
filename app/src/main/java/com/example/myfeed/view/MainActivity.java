 package com.example.myfeed.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.myfeed.databinding.ActivityMainBinding;


 public class MainActivity extends AppCompatActivity {

     private ActivityMainBinding binding;

     @Override
     protected void onDestroy() {
         super.onDestroy();
         binding=null;
     }

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this,UserActivity.class));
            finish();
        },2000);



    }
}