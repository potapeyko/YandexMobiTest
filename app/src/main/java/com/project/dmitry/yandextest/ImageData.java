package com.project.dmitry.yandextest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ImageData implements Parcelable {

    private String mUrl;

    public ImageData(String url) {
        mUrl = url;
    }

    protected ImageData(Parcel in) {
        mUrl = in.readString();
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

      @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
    }

    public static ArrayList<ImageData> getData(){
        ArrayList<ImageData> data = new ArrayList<>(8);
        data.add(new ImageData("https://stihi.ru/pics/2011/05/16/7214.jpg"));
        data.add(new ImageData("https://stihi.ru/pics/2011/05/16/7214.jpg"));
        data.add(new ImageData("https://img1.goodfon.ru/original/1366x768/9/87/priroda-gory-leto-svet-solnca.jpg"));
        data.add(new ImageData("https://img1.goodfon.ru/original/1366x768/9/87/priroda-gory-leto-svet-solnca.jpg"));
        data.add(new ImageData("http://humor.fm/uploads/posts/2015-05/12/002.jpg"));
        data.add(new ImageData("http://humor.fm/uploads/posts/2015-05/12/002.jpg"));
        data.add(new ImageData("http://www.mobilmusic.ru/mfile/1a/9a/7f/798363-320.jpg"));
        data.add(new ImageData("http://www.mobilmusic.ru/mfile/1a/9a/7f/798363-320.jpg"));
        return data;
    }
}