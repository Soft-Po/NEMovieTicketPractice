package com.example.softpo.nemovieticketpractice.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by home on 2016/7/4.
 */
public class MyHotMoviePagerAdapter extends PagerAdapter {
    private List<ImageView> mList;

    public MyHotMoviePagerAdapter(List<ImageView> imageViewList) {
        mList = imageViewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % mList.size();
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        position = position % mList.size();
        container.removeView(mList.get(position));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
