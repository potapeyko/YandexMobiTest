package com.project.dmitry.yandextest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class GalleryActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView galleryRv;
    private GalleryAdapter rvAdapter;
    private TextView updateTimeTV;
    private Calendar lastUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        this.setTitle(getResources().getString(R.string.gallery_label));
        updateTimeTV = findViewById(R.id.ac_gallery_update_time);
        swipeRefresh = findViewById(R.id.ac_gallery_swipe_refresh);
        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // обновляем картинки попыткой похода в сеть
                refreshItems();
            }
        });
        galleryRv = findViewById(R.id.ac_gallery_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);//картинки в 2 столбца
        galleryRv.setLayoutManager(layoutManager);
        rvAdapter = new GalleryAdapter(this);
        galleryRv.setAdapter(rvAdapter);

//тестовый кусок
        swipeRefresh.setRefreshing(false);
        galleryRv.setVisibility(View.VISIBLE);
        rvAdapter.setData(ImageData.getData());
        rvAdapter.notifyDataSetChanged();

    }
    private void refreshItems() {
    }
}
