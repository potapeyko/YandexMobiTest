package com.project.dmitry.yandextest;

import java.util.ArrayList;
import java.util.Calendar;

interface LinkGetterCallback {
    enum Mode{
        INTERNET,
        SHARED
    }
    void urlPostResults(ArrayList<ImageData> data, Mode mode, Calendar lastUpdate);
}
