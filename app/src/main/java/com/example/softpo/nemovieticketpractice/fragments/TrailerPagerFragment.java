package com.example.softpo.nemovieticketpractice.fragments;

import android.app.Activity;
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
import com.example.softpo.nemovieticketpractice.adapters.MyPagerAdapter;
import com.example.softpo.nemovieticketpractice.beans.Trailer;
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
public class TrailerPagerFragment extends BaseTrailerFragment {

    private ViewPager mViewPager;
    private TextView mTextView_name,mTextView_grade, mTextView_highlight, mBtn_clickable;
    private View mBackground;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TRAILER_LIST_GET_OK:
                    setUpTrailerPager();
                    break;
            }
        }
    };
    private List<ImageView> mImageViewList;

    private void setUpTrailerPager() {
        mImageViewList = getImageViewList();
        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(false, new FilmPagerTransformer());
        MyPagerAdapter adapter = new MyPagerAdapter(mImageViewList);
        mViewPager.setAdapter(adapter);
        final int size = mImageViewList.size();
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                position = position % size;
                bindDataToView(position);
            }
        });
        int index = size * 3;
        mViewPager.setCurrentItem(index);
        bindDataToView(0);
    }

    private void bindDataToView(int i) {
        Trailer.TrailerItem trailerItem = mTrailerItems.get(i);
        mTextView_name.setText(trailerItem.getName());
        mTextView_grade.setText(trailerItem.getGrade());
        mTextView_highlight.setText(trailerItem.getHighlight());
        final String logo3Url = trailerItem.getLogo3();

        final File blurredFile = new File(mContext.getCacheDir(),"blurredBitmap" + logo3Url + ".jpg");
        final String filePath = blurredFile.getAbsolutePath();
        if (!blurredFile.exists()){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    RequestCreator load = Picasso.with(mContext).load(logo3Url);
                    FileOutputStream os = null;
                    try {
                        File parentFile = blurredFile.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }
                        Bitmap bitmap = load.get();
                        Bitmap blurredBitmap = Blur.fastblur(mContext, bitmap, 14);
                        os = new FileOutputStream(blurredFile);
                        blurredBitmap.compress(Bitmap.CompressFormat.JPEG, 60, os);
                        ((Activity)mContext).runOnUiThread(new Runnable() {
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

    private void updateView(String filePath) {
        Bitmap bmpBlurred = BitmapFactory.decodeFile(filePath);
        Drawable drawable = new BitmapDrawable(bmpBlurred);
        mBackground.setBackground(drawable);
    }

    private List<ImageView> getImageViewList() {
        if (mImageViewList != null) {
            return mImageViewList;
        }
        List<ImageView> list = new ArrayList<>();
        int size = mTrailerItems.size();
        for (int i = 0; i < size; i++) {
            Trailer.TrailerItem trailerItem = mTrailerItems.get(i);
            String logo3Url = trailerItem.getLogo3();
            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(mContext).load(logo3Url).placeholder(R.mipmap.default_movie).into(imageView);
            list.add(imageView);
        }
        return list;
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
        View view = inflater.inflate(R.layout.fragment_hotmovie_pager, container, false);

//        初始化控件
        initView(view);

//        获取数据源
        if (mTrailerItems == null||mTrailerItems.size()==0) {
//            下载数据
            getDataFromNet(false, mHandler);
        }else{
//            将数据设置到控件上
            setUpTrailerPager();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
