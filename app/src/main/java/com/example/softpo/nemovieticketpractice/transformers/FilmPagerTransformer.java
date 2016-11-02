package com.example.softpo.nemovieticketpractice.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by home on 2016/7/3.
 */
public class FilmPagerTransformer implements ViewPager.PageTransformer {

    private static final float DEFAULT_MIN_ALPHA = 0.6f;
    private static final float DEFAULT_TRANSITIONY = 90.0f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;
    private float transitionY = DEFAULT_TRANSITIONY;

    @Override
    public void transformPage(View page, float position) {
        if (position<-1){
            page.setAlpha(mMinAlpha);
            page.setTranslationY(transitionY);
        } else if (position <= 1) {
            if (position < 0) {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                float realTransition = transitionY *(-1 * position);
                page.setAlpha(factor);
                page.setTranslationY(realTransition);
            }else {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                float realTransition = transitionY * position;
                page.setAlpha(factor);
                page.setTranslationY(realTransition);
            }
        }else {
            page.setAlpha(mMinAlpha);
            page.setTranslationY(transitionY);
        }
    }
}
