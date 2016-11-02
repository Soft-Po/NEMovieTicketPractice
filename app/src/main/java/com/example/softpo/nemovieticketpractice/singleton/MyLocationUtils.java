package com.example.softpo.nemovieticketpractice.singleton;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by home on 2016/6/27.
 */
public class MyLocationUtils {
    private static MyLocationUtils myDbUtilsInstance;
    private static String currentContextToString;

    public LocationClient mLocationClient = null;

    private MyLocationUtils(Context context) {
        currentContextToString = context.toString();
        mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系bd09ll
        int span=2000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static MyLocationUtils getInstance(Context context){
        String s = context.toString();
        if (myDbUtilsInstance == null|| (currentContextToString!=null&&!s.equals(currentContextToString))) {
            synchronized (MyLocationUtils.class){
                if (myDbUtilsInstance == null|| (currentContextToString!=null&&!s.equals(currentContextToString))) {
                    myDbUtilsInstance = new MyLocationUtils(context);
                    return myDbUtilsInstance;
                }
            }
        }

        return myDbUtilsInstance;
    }

    //    取Double类型的6位有效数字
   /* public static String getEffectiveDouble(double longitude) {
        String[] strings = new String(Double.toString(longitude)).trim().split(".");
        String befPoint = strings[0];
        String aftPoint = strings[1].substring(0, 6);

        return befPoint+"."+aftPoint;
    }*/
}
