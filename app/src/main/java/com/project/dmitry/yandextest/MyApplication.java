package com.project.dmitry.yandextest;

import android.app.Application;

public class MyApplication extends Application {

   private LinkGetter linkGetter;

    @Override
    public void onCreate() {
        super.onCreate();
        linkGetter = new LinkGetter();
    }


    public void getUrls(ActivityCallback activity,LinkGetter.Status status){
        linkGetter.getUrls(getApplicationContext(),activity,status);
    }
}

