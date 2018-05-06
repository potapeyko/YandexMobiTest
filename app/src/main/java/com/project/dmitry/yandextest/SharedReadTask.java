package com.project.dmitry.yandextest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

class SharedReadTask extends AsyncTask<String, String, ArrayList<ImageData>> {

    private LinkGetterCallback listener;
    private static String STARED_NAME = "com.project.dmitry.yandextest.shared";
    private static String STARED_KEY_HREFS_COUNT = "com.project.dmitry.yandextest.count";
    private static String STARED_KEY_HREFS = "com.project.dmitry.yandextest.href";
    private static String STARED_KEY_DATE = "com.project.dmitry.yandextest.date";
    private Calendar lastUpdate;


    @Override
    protected ArrayList<ImageData> doInBackground(String... params) {
        SharedPreferences sPref = MyApplication.getContext().getSharedPreferences(STARED_NAME, Context.MODE_PRIVATE);
        long time = sPref.getLong(STARED_KEY_DATE, -1);
        int count = sPref.getInt(STARED_KEY_HREFS_COUNT, 0);
        if (time != -1) {
            lastUpdate = Calendar.getInstance();
            lastUpdate.setTimeInMillis(time);
        }
        String link;
        ArrayList<ImageData> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            link = sPref.getString(STARED_KEY_HREFS + i, null);
            if (link != null) {
                result.add(new ImageData(link));
            }
        }
        if (!result.isEmpty()) {
            return result;
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<ImageData> s) {
        if (listener != null) {
            listener.urlPostResults(s, LinkGetterCallback.Mode.SHARED, lastUpdate);
        }
    }

    public void link(@NonNull LinkGetterCallback listener) {
        this.listener = listener;
    }
}