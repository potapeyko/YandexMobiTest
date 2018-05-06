package com.project.dmitry.yandextest;

import java.util.ArrayList;
import java.util.Calendar;

interface ActivityCallback {
    void urlPostResults(ArrayList<ImageData> data, Calendar lastUpdate);
}
