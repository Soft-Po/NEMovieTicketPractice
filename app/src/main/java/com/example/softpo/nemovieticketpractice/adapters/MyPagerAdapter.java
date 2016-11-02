package com.example.softpo.nemovieticketpractice.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by home on 2016/7/1.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<ImageView> mImageViewList;
    private final int mSize;

    public MyPagerAdapter(List<ImageView> imageViewList) {
        mImageViewList = imageViewList;
        mSize = mImageViewList.size();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % mSize;
        ImageView imageView = mImageViewList.get(realPosition);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = position % mSize;
//        将container中的View移除
        container.removeView(mImageViewList.get(realPosition));
    }
}
