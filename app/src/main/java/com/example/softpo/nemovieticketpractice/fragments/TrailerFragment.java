package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.adapters.MyHeadersListAdapter;
import com.example.softpo.nemovieticketpractice.beans.Trailer;
import com.example.softpo.nemovieticketpractice.constants.EventBusConst;
import com.example.softpo.nemovieticketpractice.widget.PullToRefreshListHeaders;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by home on 2016/6/28.
 */
public class TrailerFragment extends BaseTrailerFragment {

    private PullToRefreshListHeaders pullToRefreshBase;

    //    handler接收操作的信号，进行下一步操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TRAILER_LIST_GET_OK:
                    getUpListHeaders();
                    break;

                case TRAILER_LIST_UPDATE_OK:
                    transformMaxNotify();
                    mAdapter.notifyDataSetChanged();
                    pullToRefreshBase.onRefreshComplete();
                    break;
            }
        }
    };

    private void transformMaxNotify() {
        List<Trailer.TrailerItem> trailerItems = new ArrayList<>();
        trailerItems.addAll(mTrailerItems);

        for (int i1 = 0; i1 < mMaxNotifyTrailerItems.size(); i1++) {
            Trailer.TrailerItem trailerItem = mMaxNotifyTrailerItems.get(i1);
            String id = trailerItem.getId();
            for (int i = 0; i < trailerItems.size(); i++) {
                Trailer.TrailerItem item = trailerItems.get(i);
                if (id.equals(item.getId())) {
                    trailerItems.remove(i);
                    break;
                }
            }
        }
        mMaxNotifyTrailerItems.addAll(trailerItems);
    }

    private MyHeadersListAdapter mAdapter;

    private void getUpListHeaders() {
        StickyListHeadersListView headersListView = pullToRefreshBase.getRefreshableView();
        if (mMaxNotifyTrailerItems.size()!=mTrailerItems.size()) {
            transformMaxNotify();
        }
//        Log.d("flag", "-------------->final adapter list size:" + mMaxNotifyTrailerItems.size());

        mAdapter = new MyHeadersListAdapter(mContext,mMaxNotifyTrailerItems);
//        将适配器设置给控件来展示数据
        headersListView.setAdapter(mAdapter);
    }

    private void initPullBase() {
        pullToRefreshBase.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshBase.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<StickyListHeadersListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<StickyListHeadersListView> refreshView) {
//                发起刷新的网络请求
                getDataFromNet(true,mHandler);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getSubscribedMessage(Integer order) {
        switch (order) {
            case EventBusConst.SELECTED_CITY_CHANGED_SIGNAL:
                getDataFromNet(false,mHandler);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mContext == null) {
            mContext = activity;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailer,container,false);
        pullToRefreshBase = (PullToRefreshListHeaders) view.findViewById(R.id.trailerfragment_pullBase);
        initPullBase();

        if (mMaxNotifyTrailerItems == null) {
            //        初次加载，请求网络数据
            getDataFromNet(false,mHandler);
        }else {
            getUpListHeaders();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
