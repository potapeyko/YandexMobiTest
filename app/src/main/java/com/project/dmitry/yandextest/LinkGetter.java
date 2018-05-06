package com.project.dmitry.yandextest;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;

public class LinkGetter implements LinkGetterCallback {
   private OkHttpTask okHttpTask;

    public enum Status {
        INTERNET,
        NO
    }

    @Override
    public void urlPostResults(ArrayList<ImageData> data, @NonNull Mode mode, Calendar lastUpdate) {
        ActivityCallback listener = activityWeakReference.get();
        if(mode == Mode.INTERNET){
            if(data!=null && data.size()!=0){
                result = data;//запомнили ссылки
                this.lastUpdate = Calendar.getInstance();//запомнили время
            }
            if(listener!=null){
                listener.urlPostResults(data,this.lastUpdate);//отдаем результаты
            }
        }
    }

    private ArrayList<ImageData> result;
    private WeakReference<ActivityCallback> activityWeakReference;
    private Calendar lastUpdate;
    private static final String YANDEX_DAY_FOTO_URL = "https://api-fotki.yandex.ru/api/podhistory/?format=json";


    public void getUrls(Context appContext, ActivityCallback activity, Status stat) {
        activityWeakReference = new WeakReference<>(activity);
        if (stat == Status.INTERNET) {//хотим получить из сети. Вызывается если свайпом обновить
            if (ConnectionUtils.haveNetworkConnection(appContext)) {//есть подключение, нужно пытаться
                //запускать новую нужно, если старой нет или она уже отработала
                if(okHttpTask==null||okHttpTask.getStatus()== OkHttpTask.Status.FINISHED) {
                    okHttpTask = new OkHttpTask();
                    okHttpTask.link(this);
                    okHttpTask.execute(YANDEX_DAY_FOTO_URL);
                }
            }
        }
        if (stat == Status.NO) {//просто получение ссылок. Либо из хранимых, либо из Shared
            if (result != null) {//уже знаем ссылки - возвращаем их
                activity.urlPostResults(result, lastUpdate);
                return;
            }
        }
    }
}
