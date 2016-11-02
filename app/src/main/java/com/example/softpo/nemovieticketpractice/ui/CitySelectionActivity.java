package com.example.softpo.nemovieticketpractice.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.softpo.nemovieticketpractice.MainActivity;
import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.adapters.MyCityListAdapter;
import com.example.softpo.nemovieticketpractice.beans.City;
import com.example.softpo.nemovieticketpractice.constants.EventBusConst;
import com.example.softpo.nemovieticketpractice.singleton.MyDbUtils;
import com.example.softpo.nemovieticketpractice.singleton.MyLocationUtils;
import com.example.softpo.nemovieticketpractice.widget.ShortNameSideBar;

import org.greenrobot.eventbus.EventBus;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2016/7/7.
 */
public class CitySelectionActivity extends AppCompatActivity implements BDLocationListener,MyCityListAdapter.OnCityItemClickListener {

    private ImageButton btn_back;
    private TextView textView_locCity,textView_floatingMention,textView_topGroupName;
    private ShortNameSideBar mShortNameSideBar;
    private ListView mListView;
    private View mView_floatingBck;
    private DbManager mDbManager;
    private LocationClient mLocationClient;
    private SharedPreferences mSharedPreferences;
    private List<City> mCityList;
    private MyCityListAdapter mMyCityListAdapter;

    private void startLocation() {
        mLocationClient = MyLocationUtils.getInstance(this).mLocationClient;
        mLocationClient.registerLocationListener(this);
        mLocationClient.start();
    }

    private void initView() {
        btn_back = (ImageButton) findViewById(R.id.cityList_btnBack);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left ,R.anim.slide_out_to_right);
                CitySelectionActivity.this.finish();
            }
        });

        textView_locCity = (TextView) findViewById(R.id.cityList_locationCity);
        textView_floatingMention = (TextView) findViewById(R.id.cityList_floatingMention);
        textView_topGroupName = (TextView) findViewById(R.id.cityList_presentGroupName);
        mShortNameSideBar = (ShortNameSideBar) findViewById(R.id.citylist_floatingmention_sideBar);
        mListView = (ListView) findViewById(R.id.cityList_listView);
        mView_floatingBck = findViewById(R.id.cityList_floatingBck);

        mShortNameSideBar.setTextView(textView_floatingMention);
        mShortNameSideBar.setAlphaBck(mView_floatingBck);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        String city = bdLocation.getCity();
        if (city != null && !city.equals("")) {
            String cityQueryKeyword = city.substring(0, 2);
            mDbManager = MyDbUtils.getDbManagerInstance(this).mDbManager;
            try {
                City cityObj = mDbManager.selector(City.class).where("name", "like", cityQueryKeyword).findAll().get(0);
                int cityObjCode = cityObj.getCode();
                initLocationButton(city, cityObjCode);
            } catch (DbException e) {
                e.printStackTrace();
            }
            mLocationClient.stop();
        }
    }

    private void initLocationButton(final String city, final int cityObjCode) {
        textView_locCity.setText(city);
        textView_locCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataChangeOperating(city, cityObjCode);
            }
        });
    }

//    选择列表中某一项执行的方法
    private void dataChangeOperating(String city, int cityObjCode) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("cityCode", cityObjCode);
        int i = city.indexOf("市");
        if (i!=-1) {
            city = city.substring(0, i);
        }
        editor.putString("cityName", city);
        editor.commit();
        EventBus.getDefault().postSticky(EventBusConst.SELECTED_CITY_CHANGED_SIGNAL);
        Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
        overridePendingTransition(R.anim.slide_in_from_left , R.anim.slide_out_to_right);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citylist_activity_layout);

        initView();

        mSharedPreferences = getSharedPreferences("geoInfo", MODE_PRIVATE);

//        先要获取数据源 启动定位获得当前定位城市并设置当前定位城市的点击事件
        startLocation();

//        初始化数据源
        initData();

//        设置列表及适配器以及sidebar
        finalSetUp();

    }

    private int lastFirstVisibleItem = -1;

    private void finalSetUp() {
        mMyCityListAdapter = new MyCityListAdapter(this, mCityList);
        mMyCityListAdapter.setOnCityItemClickListener(this);
        mListView.setAdapter(mMyCityListAdapter);
        mShortNameSideBar.setOnTouchingLetterChangedListener(new ShortNameSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int section = -1;
                if (s.equals("热")) {
                    section = -1;
                }else {
                    section = (int) (s.toLowerCase().charAt(0));
                }
                int position = mMyCityListAdapter.getPositionForSection(section);
                mListView.setSelection(position);
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount) {
//                获取了第一个显示条目的分组：热、A……
                int section = mMyCityListAdapter.getSectionForPosition(firstVisibleItem);

//                获取了下一个个显示条目的分组：热、A……
                int nextSection = mMyCityListAdapter.getSectionForPosition(firstVisibleItem + 1);

//               获取分好的组中，某一个城市的位置
                int nextSecPosition = mMyCityListAdapter.getPositionForSection(nextSection);

                if (firstVisibleItem != lastFirstVisibleItem) {//正好完全吻合，小概率事件
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) textView_topGroupName.getLayoutParams();
                    params.topMargin = 0;
                    textView_topGroupName.setLayoutParams(params);
                    int firstPositionForSection = mMyCityListAdapter.getPositionForSection(section);
                    City city = mCityList.get(firstPositionForSection);
                    String spell = city.getSpell();
                    String str = null;
                    if (spell.equals("热")) {
                        str = "热";
                    } else {
                        str = spell.toUpperCase().charAt(0)+"";
                    }
                    textView_topGroupName.setText(str);
                }
                if (nextSecPosition == firstVisibleItem + 1) {//说明不是同一个section了
                    View childView = view.getChildAt(0);//可见的第一个listView的Item
                    if (childView != null) {
                        int titleHeight = textView_topGroupName.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) textView_topGroupName.getLayoutParams();
//                        实现了向上移动的动画
                        if (bottom < titleHeight) {
//                            bottom titleHeight（控件自身的高度）
                            float pushedDistance = bottom - titleHeight;
//                            滑动过程中修改params的参数
                            params.topMargin = (int) pushedDistance;
//                            setLayoutParams------>requestLayout-------->重新绘制画面
                            textView_topGroupName.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                textView_topGroupName.setLayoutParams(params);
                            }
                        }
                    }
                }
//                上一次的首个
                lastFirstVisibleItem = firstVisibleItem;//记录第一项的位置
            }
        });
    }

    /**
     * 初始化数据源的方法
     */
    private void initData() {
        String[] hotCities = getResources().getStringArray(R.array.hotCity);
        DbManager dbManager = MyDbUtils.getDbManagerInstance(this).mDbManager;
        List<City> list = null;
        try {
            list = dbManager.selector(City.class).where("code", ">", "0").orderBy("spell").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        mCityList = new ArrayList<>();
        for (int i = 0; i < hotCities.length; i++) {
            String hotCity = hotCities[i];
            int num = hotCity.indexOf("市");
            hotCity = hotCity.substring(0, num);
            int index = 0;
            for (City city:list){
                if (city.getName().startsWith(hotCity)) {
                    City tempCity = new City();
                    tempCity.setName(city.getName());
                    tempCity.setCode(city.getCode());
                    tempCity.setSpell("热");
                    mCityList.add(tempCity);
                    break;
                }
                index++;
            }
        }
//        获得了最终的数据源
        mCityList.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(this);
    }

    @Override
    public void cityItemClicked(int cityCode, String cityName) {
//        点击事件最终执行的方法
        dataChangeOperating(cityName,cityCode);
    }
}
