package com.project.dmitry.yandextest;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

   private LinkGetter linkGetter;
   private static Context appContext = null;
    @Override
    public void onCreate() {
        appContext = getApplicationContext();
        super.onCreate();
        linkGetter = new LinkGetter();
    }


    public static Context getContext(){
        return appContext;
    }
    public void getUrls(ActivityCallback activity,LinkGetter.Status status){
        linkGetter.getUrls(getApplicationContext(),activity,status);
    }
}

