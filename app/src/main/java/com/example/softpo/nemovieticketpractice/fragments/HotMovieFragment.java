package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.adapters.MyPullToRefreshRecyclerViewAdapter;
import com.example.softpo.nemovieticketpractice.constants.EventBusConst;
import com.example.softpo.nemovieticketpractice.widget.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by home on 2016/6/28.
 */
public class HotMovieFragment extends BaseHotMovieFragment{

    private PullToRefreshRecyclerView mRecyclerView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DATA_OK:
                    initPullToRefreshRecyclerView();
                    break;
                case REFRESHING_OK:
                    mRefreshRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.onRefreshComplete();
                    break;
            }
        }
    };
    private MyPullToRefreshRecyclerViewAdapter mRefreshRecyclerViewAdapter;
    private Context mContext;

    private void initPullToRefreshRecyclerView() {
        mRefreshRecyclerViewAdapter = new MyPullToRefreshRecyclerViewAdapter(mHotMovieList, mContext);
        RecyclerView refreshableView = mRecyclerView.getRefreshableView();
        refreshableView.setAdapter(mRefreshRecyclerViewAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,
                false);
        refreshableView.setLayoutManager(manager);
    }

    boolean firstCreate = true;
    private void updateCityInfo() {
        if (firstCreate) {
            Bundle arguments = getArguments();
            if (arguments != null) {
                int cityCode = arguments.getInt("cityCode");
                if (cityCode!=-1){
                    mCityCode = cityCode;
                    firstCreate = false;
                    return;
                }
            }
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("geoInfo", Context.MODE_PRIVATE);
        int cityCode = sharedPreferences.getInt("cityCode", -1);
        if (cityCode!=-1) {
            mCityCode = cityCode;
        }
    }

    private void getUpRecyclerView() {
        mRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                TODO 开启联网请求的方法 需要在方法中判断是否为刷新请求，以执行不同的Handler操作
                startDownloadingData(true,mHandler);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getSubscribedMessage(Integer order) {
        switch (order) {
            case EventBusConst.SELECTED_CITY_CHANGED_SIGNAL:
                updateCityInfo();
                startDownloadingData(false,mHandler);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmovie,container,false);
//        获得下拉刷新列表的对象
        mRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.hotMovieFragment_recyclerView);

        updateCityInfo();
//        对recyclerView进行设置
        getUpRecyclerView();

        if (mHotMovieList == null) {
            //      获取数据源
            startDownloadingData(false,mHandler);
        }else {
            initPullToRefreshRecyclerView();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
