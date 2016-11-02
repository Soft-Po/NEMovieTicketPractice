package com.example.softpo.nemovieticketpractice.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.x;

/**
 * Created by softpo on 2016/7/30.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

        SDKInitializer.initialize(this);
    }
}
