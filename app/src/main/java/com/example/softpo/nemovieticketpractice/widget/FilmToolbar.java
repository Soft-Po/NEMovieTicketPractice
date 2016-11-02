package com.example.softpo.nemovieticketpractice.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.softpo.nemovieticketpractice.R;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by home on 2016/6/28.
 */
public class FilmToolbar extends AutoRelativeLayout {

    private TextView text_cityName;
    private ImageButton mImageButton_search,mImageButton_changeView;

    private TabLayout mTabLayout;

    public FilmToolbar(Context context) {
        super(context);
    }

    public FilmToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_actionbar_film,this);
        text_cityName = (TextView) findViewById(R.id.myActionbar_textCityName);
        mImageButton_search = (ImageButton) findViewById(R.id.myActionbar_btnSearch);
        mImageButton_changeView = (ImageButton) findViewById(R.id.myActionbar_btnChangeView);
        mTabLayout = (TabLayout) findViewById(R.id.myActionbar_tabLayout);

//        初始化TabLayout
        initTabLayout();
    }

    private void initTabLayout() {
//        添加tablayout的标签项
        TabLayout.Tab tabHot = mTabLayout.newTab();
        TabLayout.Tab tabPre = mTabLayout.newTab();
        tabHot.setText("热映");
        tabPre.setText("预告");
        mTabLayout.addTab(tabHot);
        mTabLayout.addTab(tabPre);

        mTabLayout.setTabTextColors(Color.parseColor("#9d9d9d"),Color.parseColor("#f26500"));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f26500"));
    }

    public void changeCity(String str){
        text_cityName.setText(str);
    }

    /**
     * 设置TabLayout的监听
     * @param onTabSelectedListener
     */
    public void addTabLayoutSelectListener(TabLayout.OnTabSelectedListener onTabSelectedListener){
        mTabLayout.setOnTabSelectedListener(onTabSelectedListener);
    }

    /**
     * 设置cityname的监听
     * @param listener
     */
    public void addCityNameClickListener(View.OnClickListener listener){
        text_cityName.setOnClickListener(listener);
    }

    /**
     * 设置页面切换按钮的点击事件
     * @param listener
     */
    public void addViewChangeListener(View.OnClickListener listener){
        mImageButton_changeView.setOnClickListener(listener);
    }

//    改变变换列表展示触发按钮的图片指示
    public void setBtnViewChangerImage(int rid){
        mImageButton_changeView.setImageResource(rid);
    }

    /**
     * 设置搜索按钮的点击事件监听
     * @param listener
     */
    public void addSearchGoListener(View.OnClickListener listener){
        mImageButton_search.setOnClickListener(listener);
    }
}
