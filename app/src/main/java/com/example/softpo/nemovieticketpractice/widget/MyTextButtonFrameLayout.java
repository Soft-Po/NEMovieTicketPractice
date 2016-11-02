package com.example.softpo.nemovieticketpractice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.softpo.nemovieticketpractice.R;


/**
 * Created by home on 2016/6/28.
 */
public class MyTextButtonFrameLayout extends FrameLayout{

    public MyTextButtonFrameLayout(Context context) {
        super(context);
    }

    public MyTextButtonFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(R.drawable.orange_button_bacshape_dark);
                break;
            default:
                setBackgroundResource(R.drawable.orange_button_bacshape);
                break;
        }
        return true;
    }
}
