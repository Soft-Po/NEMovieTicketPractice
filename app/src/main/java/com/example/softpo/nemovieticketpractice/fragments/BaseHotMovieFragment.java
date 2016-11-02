package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Fragment;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.softpo.nemovieticketpractice.beans.HotMovie;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by home on 2016/7/4.
 */
public class BaseHotMovieFragment extends Fragment {

    protected static final int GET_DATA_OK = 301;
    protected static final int REFRESHING_OK = 302;

    protected static List<HotMovie.ListBean> mHotMovieList;
    protected static HotMovie mHotMovie;
    protected int mCityCode = 370200;

//    下载获取hotMovieList数据的方法
    protected void startDownloadingData(final boolean isRefreashOperating, final Handler handler) {
        String url = "http://piao.163.com/m/movie/list.html?type=0&city="+mCityCode+"&mobileType=android";
        RequestParams entity = new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        mHotMovie = JSON.parseObject(result, HotMovie.class);
                        List<HotMovie.ListBean> list = mHotMovie.getList();
//                        如果是刷新出来的就清空后再加载
                        if (isRefreashOperating){
                            mHotMovieList.clear();
                            mHotMovieList.addAll(list);
                            handler.sendEmptyMessage(REFRESHING_OK);
                        }
                        mHotMovieList = list;
                        handler.sendEmptyMessage(GET_DATA_OK);
                    }
                }.start();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("flag", "----------->hotMovie json数据获取失败");
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
