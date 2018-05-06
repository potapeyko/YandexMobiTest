package com.project.dmitry.yandextest;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;

public class LinkGetter implements LinkGetterCallback {
    private OkHttpTask okHttpTask;
    private SharedReadTask sharedReadTask;

    public enum Status {
        INTERNET,
        NO
    }

    @Override
    public void urlPostResults(ArrayList<ImageData> data, @NonNull Mode mode, Calendar lastUpdate) {
        ActivityCallback listener = activityWeakReference.get();
        if(mode== Mode.SHARED){//результаты из shared
            result = data;//запомнили ссылки
            this.lastUpdate = lastUpdate;
            if(listener!=null){
                listener.urlPostResults(data,lastUpdate);
            }
        }
        if(mode == Mode.INTERNET){
            if(data!=null && data.size()!=0){
                result = data;//запомнили ссылки
                this.lastUpdate = Calendar.getInstance();//запомнили время
                SharedWriteTask writeTask = new SharedWriteTask(data,this.lastUpdate);
                writeTask.execute();//записали все в файл
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


    public void getUrls(@NonNull Context appContext, @NonNull ActivityCallback activity,@NonNull Status stat) {
        activityWeakReference = new WeakReference<>(activity);
        if (stat == Status.INTERNET) {//хотим получить из сети. Вызывается если свайпом обновить
            if (ConnectionUtils.haveNetworkConnection(appContext)) {//есть подключение, нужно пытаться
                //запускать новую нужно, если старой нет или она уже отработала
                if(okHttpTask==null||okHttpTask.getStatus()==OkHttpTask.Status.FINISHED) {
                    okHttpTask = new OkHttpTask();
                    okHttpTask.link(this);
                    okHttpTask.execute(YANDEX_DAY_FOTO_URL);
                }
            }else{
                activity.urlPostResults(null,null);//сети нет, не можем данные получить. Кидаем ничего
            }
        }
        if (stat == Status.NO) {//просто получение ссылок. Либо из хранимых, либо из Shared
            if (result != null) {//уже знаем ссылки - возвращаем их
                activity.urlPostResults(result, lastUpdate);
                return;
            }
            //не знаем результат, идем в Shared
            //запускать новую нужно, если старой нет или она уже отработала
            if(sharedReadTask==null||sharedReadTask.getStatus()==OkHttpTask.Status.FINISHED) {
                sharedReadTask = new SharedReadTask();
                sharedReadTask.link(this);
                sharedReadTask.execute();
            }
        }
    }
}
