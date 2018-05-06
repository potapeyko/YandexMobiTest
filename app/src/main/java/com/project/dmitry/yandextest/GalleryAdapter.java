package com.project.dmitry.yandextest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private ArrayList<ImageData> mImageData;
    private Context mContext;

    /**
     * @param context - для Glide и startActivity
     */
    public GalleryAdapter(Context context) {
        mContext = context;
    }

    /**
     * @param imageData - коллекция url на картинки
     */
    public void setData(@NonNull ArrayList<ImageData> imageData) {
        mImageData = imageData;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View photoView = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        if (mImageData == null || mImageData.size() == 0) {
            return; //данных еще нет, нечего показывать
        }
        ImageData imageData = mImageData.get(position);
        ImageView imageView = holder.imageView;
        GlideApp.with(mContext)
                .load(imageData.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mImageData == null? 0:mImageData.size();//если нет данных, то 0
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ImageData imageData = mImageData.get(position);
               //start activity
            }
        }
    }
}
