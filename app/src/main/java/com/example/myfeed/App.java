package com.example.myfeed;

import android.app.Application;
import android.content.Context;

public class App extends Application {


   private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static Context appContext(){
      return app.getApplicationContext();
    }

}
