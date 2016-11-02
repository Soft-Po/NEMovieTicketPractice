package com.example.softpo.nemovieticketpractice.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import com.example.softpo.nemovieticketpractice.MainActivity;
import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.beans.CinemaDistrictRel;
import com.example.softpo.nemovieticketpractice.beans.City;
import com.example.softpo.nemovieticketpractice.beans.District;
import com.example.softpo.nemovieticketpractice.constants.HandlerCommenConst;
import com.example.softpo.nemovieticketpractice.parsers.BaseDataJsonParser;
import com.example.softpo.nemovieticketpractice.parsers.SplanshJsonParser;
import com.example.softpo.nemovieticketpractice.singleton.MyDbUtils;
import com.example.softpo.nemovieticketpractice.singleton.MyLocationUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.db.Selector;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class SplanshActivity extends AppCompatActivity implements BDLocationListener {

    public static final int DISPLAY_AD_IMAGE = 100;
    private ImageView bacImg,logoImg;
    private int indexOfList;
    private ConnectivityManager mConnectivityManager;
    private SharedPreferences mSplanshRecord;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DISPLAY_AD_IMAGE:
                    String url = (String) msg.obj;
                    //        启用Picasso下载图片
                    Picasso.with(SplanshActivity.this).load(url).into(bacImg);
                    logoImg.setImageResource(R.mipmap.startup_logo_white);
                    applyImageAnimation();
                    break;
                case HandlerCommenConst.PARSE_BASEDATA_OK:
                    //        启动定位
                    mLocationClient.start();
//                    Log.i("map", "------->location service is starting again");
                    break;
            }
        }
    };
    private SharedPreferences mDbStateRecord;
    private DbManager mDbManager;
    private SharedPreferences mGeoInfoRecord;

    private void applyImageAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.1f, 1.0f, 1.1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2000);
        scaleAnimation.setStartOffset(1000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());

        bacImg.startAnimation(scaleAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splansh);
//        注册EventBus
        EventBus.getDefault().register(this);

        initView();

        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

//        从sharedPreferences中获取上次展示的广告序号
        mSplanshRecord = getSharedPreferences("splanshRecord", MODE_PRIVATE);

        mDbStateRecord = getSharedPreferences("dbStatus", MODE_PRIVATE);

        mGeoInfoRecord = getSharedPreferences("geoInfo", MODE_PRIVATE);

//        初始化定位
        initLocation();
        Log.d("flag","-------------------->loaction start");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mLocationClient.unRegisterLocationListener(this);
    }

    //    接收EventBus回传的图片url地址的列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(List<String> list) {
//        判断该下载并设置哪张图片
        int listSize = list.size();
        int index = mSplanshRecord.getInt("indexOfList", -1);
        if (index == -1 || index == listSize - 1) {
//            说明是第一次播放或者已经播放过最后一张
            indexOfList = 0;
        } else {
            indexOfList++;
        }
        String url = list.get(indexOfList);
//        将最新的页面状态储存
        SharedPreferences.Editor editor = mSplanshRecord.edit();
        editor.putInt("indexOfList", indexOfList);
        editor.commit();

        Message msg = mHandler.obtainMessage();
        if (msg == null) {
            msg = new Message();
        }
        msg.what = DISPLAY_AD_IMAGE;
        msg.obj = url;
        mHandler.sendMessage(msg);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplanshActivity.this.jumpToMainActivity(SplanshActivity.this);
            }
        }, 4 * 1000);
    }

    private void requestForBackgroundImage(int cityCode) {
        long timestamp = System.currentTimeMillis();
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (info!=null&&info.isAvailable()) {
            //        TODO 高德地图定位获取城市代码
            int city = 370200;
            if (cityCode!=0){
                city = cityCode;
            }

            String requestUrl = "http://piao.163.com/m/movie/startingUp.html?timestamp="
                    + timestamp + "&mobileType=android&city=" + city;
            RequestParams entity = new RequestParams(requestUrl);
            x.http().get(entity, new Callback.CommonCallback<String>() {

                @Override
                public void onSuccess(String result) {
//                    获得Json字符串，开启异步线程进行解析
                    if (result != null) {
                        SplanshJsonParser.parseNow(result);
                    } else {
                        SplanshActivity.this.jumpToMainActivity(SplanshActivity.this);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SplanshActivity.this.jumpToMainActivity(SplanshActivity.this);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            Toast.makeText(SplanshActivity.this, "网络连接异常，请检查您的网络···", Toast.LENGTH_SHORT).show();
        }

    }

    //    初始化欢迎界面的控件
    private void initView() {
        bacImg = (ImageView) findViewById(R.id.splansh_bacImageView);
        logoImg = (ImageView) findViewById(R.id.splansh_logoImageView);
    }

    public void jumpToMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        SplanshActivity.this.overridePendingTransition(R.anim.just_show_in_there ,R.anim.center_to_fade_out);
    }

    private LocationClient mLocationClient;
    private int mLocationCityCode;
    private String mLocationCity;

    private void initLocation() {
        mLocationClient = MyLocationUtils.getInstance(this).mLocationClient;
        mLocationClient.registerLocationListener(this);
        Log.d("flag","-------------->initLocation");
        mLocationClient.start();
    }

//    获取定位结果时回调的方法
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        mLocationCity = bdLocation.getCity().substring(0,2);
        Log.i("map", "onReceiveLocation: cityname:" + mLocationCity);
        if (mLocationCity==null) {
            return;
        }
        mLocationClient.stop();

        if (mDbManager != null) {
            try {
                Selector<City> citySelector = mDbManager.selector(City.class).where("name", "=", mLocationCity);
                City city = citySelector.findFirst();
                if (city != null) {
                    mLocationCityCode = city.getCode();
//                    Log.i("map", "onReceiveLocation 数据库查询得到: "+mLocationCityCode);
                    SharedPreferences.Editor editor = mGeoInfoRecord.edit();
                    editor.putInt("cityCode", mLocationCityCode);
                    editor.putString("cityName", mLocationCity);
                    editor.commit();
//                    使用xUtils请求网络数据，加载欢迎背景
                    requestForBackgroundImage(mLocationCityCode);
                    return;
                }else{
                    Log.i("map", "onReceiveLocation 未查询到数据！ ");
                }

            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        initBaseData();
    }

    private void initBaseData() {
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo!=null&&activeNetworkInfo.isAvailable()) {
            boolean isLoaded = mDbStateRecord.getBoolean("isLoaded", false);
            int version = mDbStateRecord.getInt("version", 0);
//            判断之前的城市是否为定位到的城市
            String cityName = mGeoInfoRecord.getString("cityName", null);
            boolean isCityMatch = false;
            if (cityName != null) {
                isCityMatch = cityName.equals(mLocationCity);
            }
            SharedPreferences.Editor edit = mDbStateRecord.edit();
            boolean flag = false;
            try {
                mDbManager = MyDbUtils.getDbManagerInstance(this).mDbManager;
                mDbManager.getTable(City.class).tableIsExist();
                mDbManager.getTable(District.class).tableIsExist();
                mDbManager.getTable(CinemaDistrictRel.class).tableIsExist();
                flag = true;
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (isLoaded&&flag&&isCityMatch) {
//                    加载过，无需再次加载
                mLocationClient.start();
            }else{
                loadDbFormally();
                edit.putBoolean("isLoaded", true);
            }
            edit.commit();

        }else{
            Log.d("network", "initBaseData: 无法获得网络！ ");
        }
    }

    private void loadDbFormally() {
//            为了请求数据库，先默认用青岛的城编码
        String url = "http://piao.163.com/m/base_data.html?updateTime=&mobileType=android&city=370200";
        RequestParams entity = new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (result != null&&result.length()>0) {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();

                            BaseDataJsonParser.parseJsonString(result,mDbManager,mHandler);
                        }
                    }.start();
                }else{
                    Log.d("json", "----------->Appliacation initBaseData() = failed");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("json", "----------->Appliacation initBaseData() = failed");
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
