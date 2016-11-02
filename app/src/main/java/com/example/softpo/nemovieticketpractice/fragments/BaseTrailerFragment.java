package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.softpo.nemovieticketpractice.beans.Trailer;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2016/7/4.
 */
public class BaseTrailerFragment extends Fragment {

    protected static final int TRAILER_LIST_GET_OK = 401;
    protected static final int TRAILER_LIST_UPDATE_OK = 402;
    protected int mCityCode = 370200;
    protected Context mContext;

    //    总的数据源在更新时不会受到影响，但是作为最终的数据源的mMaxNotifyTrailerItems中只会保留最受关注的三部
//    ！！！！需要再次进行集合操作！！！！
    protected static List<Trailer.TrailerItem> mTrailerItems,mMaxNotifyTrailerItems;

    protected void getDataFromNet(final boolean flag, final Handler handler) {
        SharedPreferences geoInfo = mContext.getSharedPreferences("geoInfo", Context.MODE_PRIVATE);
        int cityCode = geoInfo.getInt("cityCode", -1);
        if (cityCode!=-1) {
            mCityCode = cityCode;
//            Log.d("flag", "------------------>trailer cityCode:" + mCityCode);
        }
        String url = "http://piao.163.com/m/movie/list.html?type=1&city="+mCityCode+"&apiVer=21&apilevel=19&mobileType=android&ver=4.16.2";
        mTrailerItems = new ArrayList<>();
        mMaxNotifyTrailerItems = new ArrayList<>();

        RequestParams entity = new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (result != null&&!result.equals("")) {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            Trailer trailer = JSON.parseObject(result, Trailer.class);
                            if (flag) {
                                mTrailerItems.clear();
                                mMaxNotifyTrailerItems.clear();
                            }

                            mTrailerItems.addAll(trailer.getList());
                            mMaxNotifyTrailerItems.addAll(trailer.getMaxNotifyList());

                            Log.d("json", "-------------->mTrailerItems size:" + mTrailerItems.size()
                                    + ", mMaxNotifyTrailerItems size:" + mMaxNotifyTrailerItems.size());
                            if (flag){
                                handler.sendEmptyMessage(TRAILER_LIST_UPDATE_OK);
                            }else {
                                handler.sendEmptyMessage(TRAILER_LIST_GET_OK);
                            }
                        }
                    }.start();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("json", "---------------->trailer list 获取trailer数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
