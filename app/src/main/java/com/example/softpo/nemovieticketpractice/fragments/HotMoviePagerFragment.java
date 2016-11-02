package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.adapters.MyHotMoviePagerAdapter;
import com.example.softpo.nemovieticketpractice.beans.HotMovie;
import com.example.softpo.nemovieticketpractice.constants.EventBusConst;
import com.example.softpo.nemovieticketpractice.transformers.FilmPagerTransformer;
import com.example.softpo.nemovieticketpractice.utils.Blur;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2016/7/2.
 */
public class HotMoviePagerFragment extends BaseHotMovieFragment {

    private String url = "http://piao.163.com/m/movie/list.html?type=0&city=370200&mobileType=android";

    private ViewPager mViewPager;
    private TextView mTextView_name,mTextView_grade, mTextView_highlight, mBtn_clickable;
    private View mBackground;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_DATA_OK:
                    setUpViewPager();
                    break;
            }
        }
    };
    private MyHotMoviePagerAdapter adapter;
    private List<ImageView> mImageViewList;
    private Context context;

    private void setUpViewPager() {
        mImageViewList = getImageViewList();
        adapter = new MyHotMoviePagerAdapter(mImageViewList);
        mViewPager.setPageTransformer(false,new FilmPagerTransformer());
        mViewPager.setOffscreenPageLimit(3);
//        设置page之间的距离
        mViewPager.setPageMargin(40);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                转换为列表中的位置
                position = position % mImageViewList.size();
                bindDataWithView(position);
            }
        });

        int index =  mImageViewList.size()*3;
        mViewPager.setCurrentItem(index);
    }


    private List<ImageView> getImageViewList() {
        if (mImageViewList == null||mImageViewList.size()==0) {
            List<ImageView> list = new ArrayList<>();
            for (int i = 0; i < mHotMovieList.size(); i++) {
                HotMovie.ListBean listBean = mHotMovieList.get(i);
                String logoUrl = listBean.getLogo3();
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                Picasso.with(context).load(logoUrl).placeholder(R.mipmap.default_movie).into(imageView);
                list.add(imageView);
            }
            return list;
        }else {
            return mImageViewList;
        }

    }

    private void bindDataWithView(int position) {
        HotMovie.ListBean listBean = mHotMovieList.get(position);
        mTextView_name.setText(listBean.getName());
        mTextView_grade.setText(listBean.getGrade());
        mTextView_highlight.setText(listBean.getHighlight());
        final String logo3Url = listBean.getLogo3();

        final File blurredFile = new File(context.getCacheDir(),"blurredBitmap" + logo3Url + ".jpg");
        final String filePath = blurredFile.getAbsolutePath();
        if (!blurredFile.exists()){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    RequestCreator load = Picasso.with(context).load(logo3Url);
                    FileOutputStream os = null;
                    try {
                        File parentFile = blurredFile.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }
                        Bitmap bitmap = load.get();
                        Bitmap blurredBitmap = Blur.fastblur(context, bitmap, 14);
                        os = new FileOutputStream(blurredFile);
                        blurredBitmap.compress(Bitmap.CompressFormat.JPEG, 60, os);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateView(filePath);
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
        }else {
            updateView(filePath);
        }
    }

    private void updateView(String picPath) {
        Bitmap bmpBlurred = BitmapFactory.decodeFile(picPath);
        Drawable drawable = new BitmapDrawable(bmpBlurred);
        mBackground.setBackground(drawable);
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

        SharedPreferences sharedPreferences = context.getSharedPreferences("geoInfo", Context.MODE_PRIVATE);
        int cityCode = sharedPreferences.getInt("cityCode", -1);
        if (cityCode!=-1) {
            mCityCode = cityCode;
        }
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.hotMoviePagerFragment_viewPager);
        mTextView_name = (TextView) view.findViewById(R.id.hotMoviePagerFragment_nameText);
        mTextView_grade = (TextView) view.findViewById(R.id.hotMoviePagerFragment_gradeText);
        mTextView_highlight = (TextView) view.findViewById(R.id.hotMoviePagerFragment_highlight);
        mBtn_clickable = (TextView) view.findViewById(R.id.hotMoviePagerFragment_btnText);

//        TODO点击事件

        mBackground = view.findViewById(R.id.hotMoviePagerFragment_frame);

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmovie_pager, container, false);
        initView(view);

//        更新城市信息
        updateCityInfo();

//        加载数据
        if (mHotMovieList == null||mHotMovieList.size()==0) {
            startDownloadingData(false,mHandler);
        }else{
            setUpViewPager();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
