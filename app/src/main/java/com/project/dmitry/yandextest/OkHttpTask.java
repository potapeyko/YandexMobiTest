package com.project.dmitry.yandextest;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class OkHttpTask extends AsyncTask<String, String, ArrayList<ImageData>> {

    private OkHttpClient client = new OkHttpClient();
    LinkGetterCallback listener; // объект, хранящийся в Application, через каторый активность просит ссылки
    //собирается при уничтожении приложения
    @Override
    protected ArrayList<ImageData> doInBackground(String... params) {
        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();
        if (!isCancelled()) {
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                JSONObject json = new JSONObject(responseString);
                JSONArray array = json.getJSONArray("entries");//набор информаци о фото
                ArrayList<ImageData> result = new ArrayList<>(array.length());
                String href;
                for (int i = 0; i < array.length(); i++) {
                    href = array.getJSONObject(i).getJSONObject("links").getString("editMedia");//ссылка на фото
                    result.add(new ImageData(href));
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ImageData> s) {
        if (listener != null) {
            listener.urlPostResults(s,LinkGetter.Mode.INTERNET,null);//отдаем данные, что эти данные из сети
        }
    }

    public void link(@NonNull LinkGetterCallback listener) {
        this.listener = listener;
    }
}