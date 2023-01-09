package com.example.myhouse.myHouse1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myhouse.R;

public class AdapterMaquette extends PagerAdapter {
    private Context ctx;
    private int[] ImgaeArray = new int[] {R.drawable.mur1,R.drawable.mur2,R.drawable.mur3,R.drawable.mur4};

    public AdapterMaquette(Context context){
        ctx = context;
    }

    @Override
    public int getCount() {
        return ImgaeArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        ImageView imageView = new ImageView(ctx);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(ImgaeArray[position]);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object){
        container.removeView((ImageView) object);
    }
}
