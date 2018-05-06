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
}