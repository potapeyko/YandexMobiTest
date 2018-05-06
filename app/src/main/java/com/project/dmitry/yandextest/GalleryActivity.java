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

public class GalleryActivity extends AppCompatActivity implements ActivityCallback {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView galleryRv;
    private GalleryAdapter rvAdapter;
    private TextView updateTimeTV;
    private final Handler handler = new Handler();
    private Runnable runnableCode;
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
        ((MyApplication) getApplication()).getUrls(this,LinkGetter.Status.NO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPeriodicallyUpdateTimeTV();//возобновляем обновление текста
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnableCode);//Отменяем обнвление текста
    }

    private void startPeriodicallyUpdateTimeTV() {
        runnableCode = new Runnable() {
            @Override
            public void run() {
                if(lastUpdate!=null){
                    updateTimeTV.setText(getTimePeriod(lastUpdate,Calendar.getInstance()));
                }
                handler.postDelayed(this, 2*60*1000);//каждые 2 минуты
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }

    private void refreshItems() {
        ((MyApplication) getApplication()).getUrls(this,LinkGetter.Status.INTERNET);//запрашиваем попытку обновиться по сети
    }
    @Override
    public void urlPostResults(ArrayList<ImageData> data, Calendar lastUpdate) {
        swipeRefresh.setRefreshing(false);
        if (data == null) {// пришло ничего, значит не удалось прочитать
            Toast.makeText(this, R.string.image_download_faiil, Toast.LENGTH_SHORT).show();
        }
        else {
            if(lastUpdate!=null){
                this.lastUpdate=lastUpdate;
                Calendar calendar = Calendar.getInstance();
                updateTimeTV.setText(getTimePeriod(lastUpdate,calendar));
            }
            galleryRv.setVisibility(View.VISIBLE);
            rvAdapter.setData(data);
            rvAdapter.notifyDataSetChanged();
        }
    }
    /**
     * По разнице во времени возвращает строковую фразу для описания времени последнего обновления
     */
    private String getTimePeriod(Calendar lastUpdate, Calendar calendar) {
        long milsecs1= lastUpdate.getTimeInMillis();
        long milsecs2 = calendar.getTimeInMillis();
        long diff = milsecs2 - milsecs1;
        if(diff<0){
            diff=-diff;
        }
        long dminutes = diff / (60 * 1000);
        long dhours = diff / (60 * 60 * 1000);
        if(dhours>12)return getString(R.string.time_12_and_more);
        if(dhours>0)return getString(R.string.time_1_12_hour)+(int)dhours;
        if(dminutes>0)return getString(R.string.time_1_59_minutes)+(int)dminutes;
        return getString(R.string.time_less_minute);
    }
}
