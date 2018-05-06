package com.project.dmitry.yandextest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

class SharedWriteTask extends AsyncTask<Void, Void, Void> {

    private static String STARED_NAME = "com.project.dmitry.yandextest.shared";
    private static String STARED_KEY_HREFS_COUNT = "com.project.dmitry.yandextest.count";
    private static String STARED_KEY_HREFS = "com.project.dmitry.yandextest.href";
    private static String STARED_KEY_DATE = "com.project.dmitry.yandextest.date";
    private ArrayList<ImageData> data;
    private Calendar lastUpdate;

    /**
     * @param data - список ссылок длязаписи
     * @param lastUpdate - дата и время обновления данных
     */
    public SharedWriteTask(@NonNull ArrayList<ImageData> data, @NonNull Calendar lastUpdate) {
        this.data = data;
        this.lastUpdate = lastUpdate;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SharedPreferences sPref = MyApplication.getContext().getSharedPreferences(STARED_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sPref.edit();
        editor.clear();

        editor.putInt(STARED_KEY_HREFS_COUNT,data.size());
        editor.putLong(STARED_KEY_DATE, lastUpdate.getTimeInMillis());
        for (int i =0; i<data.size();i++) {
            editor.putString(STARED_KEY_HREFS+i,data.get(i).getUrl());
        }
        editor.commit();
        return null;
    }
}