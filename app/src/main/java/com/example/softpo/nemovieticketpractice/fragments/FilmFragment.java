package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.constants.EventBusConst;
import com.example.softpo.nemovieticketpractice.ui.CitySelectionActivity;
import com.example.softpo.nemovieticketpractice.widget.FilmToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by home on 2016/6/27.
 */
public class FilmFragment extends Fragment {
    private FilmToolbar mFilmToolbar;
    private SharedPreferences mGeoInfo;
    private FragmentManager mFragmentManager;
    private HotMovieFragment mHotMovieFragment;
    private HotMoviePagerFragment mHotMoviePagerFragment;
    private TrailerFragment mTrailerFragment;
    private TrailerPagerFragment mTrailerPagerFragment;
    private int currentPosition = 0;
    private Activity mActivity;
    private Context mContext;

    private void refreshLocationButtonText() {
        mGeoInfo = mActivity.getSharedPreferences("geoInfo", Context.MODE_PRIVATE);
        mFilmToolbar.changeCity(mGeoInfo.getString("cityName","未知"));
    }

//    声明视图类型的变量 true代表使用pager样式 false代表使用列表样式
    private boolean isPagerStyleShowing = false;

//    设置filmToolbar的监听的方法
    private void setFilmToolbar() {
//        设置Toolbar上tablayout的监听
        mFilmToolbar.addTabLayoutSelectListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (currentPosition != position) {
                    currentPosition = position;
                    showRelativeFragment(position);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mFilmToolbar.addViewChangeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionChangeFragmentStyle();
            }
        });

        mFilmToolbar.addCityNameClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CitySelectionActivity.class);
                mContext.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_from_bottom_mine ,
                        R.anim.just_show_in_there);
            }
        });
        mFilmToolbar.addSearchGoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int entryCount = mFragmentManager.getBackStackEntryCount();

                Log.d("flag","---------------------->entryCount: "+entryCount);
            }
        });
    }

    /**
     * 样式切换的方法
     */
    private void transitionChangeFragmentStyle() {
        Bundle bundle = new Bundle();
        bundle.putInt("cityCode",mGeoInfo.getInt("cityCode",-1));
        if (isPagerStyleShowing){//从PagerFragment 切换到 List
            if (currentPosition == 0){
                if (!mHotMovieFragment.isAdded()) {//热映的ListView没有被添加
                    FragmentTransaction transaction =
                            mFragmentManager.beginTransaction();
//                    参数以进入动画，参数二退出的动画
                    transaction.setCustomAnimations(
                            R.animator.rotationy_clockwise_list,
                            R.animator.rotationy_clockwise_pager);
                    transaction.add(R.id.filmFragment_relativeContainer,mHotMovieFragment,"mHotMovieFragment");
                    transaction.hide(mHotMoviePagerFragment);
                    transaction.commit();
                }else {
                    FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                    transaction1.setCustomAnimations(
                            R.animator.rotationy_clockwise_list,//ListView进入动画
                            R.animator.rotationy_clockwise_pager);//ViewPager退出动画
                    transaction1.hide(mHotMoviePagerFragment);
                    transaction1.show(mHotMovieFragment);
                    transaction1.commit();
                }
            }else {
                if (mTrailerFragment == null) {
                    mTrailerFragment = new TrailerFragment();
                    mTrailerFragment.setArguments(bundle);
                }
                if(!mTrailerFragment.isAdded()){//预告ListView形式展示数据的Fragment从来没有被添加过
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.rotationy_clockwise_list,R.animator.rotationy_clockwise_pager);
                    transaction.add(R.id.filmFragment_relativeContainer, mTrailerFragment,"mTrailerFragment");
                    transaction.hide(mTrailerPagerFragment);
                    transaction.commit();
                }else {
                    FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                    transaction1.setCustomAnimations(R.animator.rotationy_clockwise_list,R.animator.rotationy_clockwise_pager);
                    transaction1.hide(mTrailerPagerFragment);
                    transaction1.show(mTrailerFragment);
                    transaction1.commit();
                }

            }
            mFilmToolbar.setBtnViewChangerImage(R.mipmap.icon_main_large_mode);
            isPagerStyleShowing = false;
        }else {//从 List 切换到 PagerFragment
            Log.d("flag","-------------------->从 List 切换到 PagerFragment");
            if (currentPosition == 0) {
                if(!mHotMoviePagerFragment.isAdded()){//没有被添加过
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.rotationy_anticlockwise_pager, R.animator.rotationy_anticlockwise_list);
                    transaction.hide(mHotMovieFragment);
                    transaction.add(R.id.filmFragment_relativeContainer, mHotMoviePagerFragment,"mHotMoviePagerFragment");
                    transaction.commit();
                }else {
                    FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                    transaction1.setCustomAnimations(
                            R.animator.rotationy_anticlockwise_pager,
                            R.animator.rotationy_anticlockwise_list);
                    transaction1.hide(mHotMovieFragment);
                    transaction1.show(mHotMoviePagerFragment);
                    transaction1.commit();
                }
            } else {
                if (mTrailerPagerFragment == null) {
                    mTrailerPagerFragment = new TrailerPagerFragment();
                    mTrailerPagerFragment.setArguments(bundle);
                }
                if(!mTrailerPagerFragment.isAdded()){//从来没有被添加过
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.rotationy_anticlockwise_pager, R.animator.rotationy_anticlockwise_list);
                    transaction.add(R.id.filmFragment_relativeContainer, mTrailerPagerFragment,"mTrailerPagerFragment");
                    transaction.hide(mTrailerFragment);
                    transaction.commit();
                }else {
                    FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                    transaction1.setCustomAnimations(R.animator.rotationy_anticlockwise_pager, R.animator.rotationy_anticlockwise_list);
                    transaction1.hide(mTrailerFragment);
                    transaction1.show(mTrailerPagerFragment);
                    transaction1.commit();
                }

            }

            mFilmToolbar.setBtnViewChangerImage(R.mipmap.icon_main_list_mode);
            isPagerStyleShowing = true;
        }
    }

    //    管理Fragment Tab之间切换的方法
    private void showRelativeFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("cityCode",mGeoInfo.getInt("cityCode",-1));
        if (isPagerStyleShowing){//viewPager展示数据
            if (position == 0){
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (mHotMoviePagerFragment == null) {
                    mHotMoviePagerFragment = new HotMoviePagerFragment();
                    mHotMoviePagerFragment.setArguments(bundle);
                }
                //解决环形切换不显示的方法，否则在Add方法后还需要加入是否为隐藏并将其显示的方法
                if (mTrailerPagerFragment!=null&&!mTrailerPagerFragment.isHidden()){
                    transaction.hide(mTrailerPagerFragment);
                }
                if(!mHotMoviePagerFragment.isAdded()){//replace只会替换掉一个Fragment，另一个也被干掉，保证只显示一个，不产生叠图问题！
                    transaction.add(R.id.filmFragment_relativeContainer,mHotMoviePagerFragment,"mHotMoviePagerFragment");
                }else {
                    Fragment mHotMoviePagerFragment = mFragmentManager.findFragmentByTag("mHotMoviePagerFragment");
                    transaction.setCustomAnimations(R.animator.rotationy_pager_type_to_0,0);
                    transaction.show(mHotMoviePagerFragment);
                }
                transaction.commit();


            }else {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (mTrailerPagerFragment == null) {
                    mTrailerPagerFragment = new TrailerPagerFragment();
                    mTrailerPagerFragment.setArguments(bundle);
                }if (mHotMoviePagerFragment!=null&&!mHotMoviePagerFragment.isHidden()){
                    transaction.hide(mHotMoviePagerFragment);
                }
                if(!mTrailerPagerFragment.isAdded()){
                    transaction.add(R.id.filmFragment_relativeContainer, mTrailerPagerFragment,"mTrailerPagerFragment");
                }else {
                    Fragment mTrailerPagerFragment = mFragmentManager.findFragmentByTag("mTrailerPagerFragment");
                    Log.d("flag","------------->mTrailerPagerFragment: "+mTrailerPagerFragment.toString());
                    transaction.setCustomAnimations(R.animator.rotationy_pager_type_to_0,0);
                    transaction.show(mTrailerPagerFragment);
                }
                transaction.commit();

            }
        }else {//ListView展示数据
            if (position == 0){//热映
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (mHotMovieFragment == null) {
                    mHotMovieFragment = new HotMovieFragment();
                    mHotMovieFragment.setArguments(bundle);
                }
                if (mTrailerFragment!=null&&!mTrailerFragment.isHidden()){
                    transaction.hide(mTrailerFragment);
                }
                if(!mHotMovieFragment.isAdded()){
                    transaction.add(R.id.filmFragment_relativeContainer, mHotMovieFragment,"mHotMovieFragment");
                }else {
                    Fragment mHotMovieFragment = mFragmentManager.findFragmentByTag("mHotMovieFragment");
                    Log.d("flag","------------->mHotMovieFragment: "+mHotMovieFragment.toString());
                    transaction.setCustomAnimations(R.animator.rotationy_list_typt_to_0,0);
                    transaction.show(mHotMovieFragment);
                }
                transaction.commit();

            }else {//预告
                FragmentTransaction transaction = mFragmentManager.beginTransaction();

                if (mTrailerFragment == null) {
                    mTrailerFragment = new TrailerFragment();
                    mTrailerFragment.setArguments(bundle);
                }
                if (mHotMovieFragment!=null&&!mHotMovieFragment.isHidden()){
                    transaction.hide(mHotMovieFragment);
                }
                if(!mTrailerFragment.isAdded()){
                    transaction.add(R.id.filmFragment_relativeContainer, mTrailerFragment,"mTrailerFragment");
                }else {
                    Fragment mTrailerFragment = mFragmentManager.findFragmentByTag("mTrailerFragment");
                    Log.d("flag","------------->mT: "+mTrailerFragment.toString());
                    transaction.setCustomAnimations(R.animator.rotationy_list_typt_to_0,0);

                    transaction.show(mTrailerFragment);
                }
                transaction.commit();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribedMessage(Integer order) {
        switch (order){
            case EventBusConst.SELECTED_CITY_CHANGED_SIGNAL:
                refreshLocationButtonText();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {//为了向下兼容
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filmfragment_layout,container,false);
        mFilmToolbar = (FilmToolbar) view.findViewById(R.id.filmFragment_toolbar);
//        刷新一下城市的名字
        refreshLocationButtonText();

        if (mHotMoviePagerFragment == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("cityCode",mGeoInfo.getInt("cityCode",-1));
            mHotMoviePagerFragment = new HotMoviePagerFragment();
            mHotMoviePagerFragment.setArguments(bundle);
        }

//        fragmentManager，管理该页面下的fragment
        mFragmentManager = getFragmentManager();

//        设置mFilmToolbar的监听
        setFilmToolbar();

//        开始显示热映
        showRelativeFragment(currentPosition);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
