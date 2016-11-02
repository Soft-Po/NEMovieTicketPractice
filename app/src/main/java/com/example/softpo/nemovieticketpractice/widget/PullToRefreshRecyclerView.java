package com.example.softpo.nemovieticketpractice.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.softpo.nemovieticketpractice.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by home on 2016/6/28.
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

//    定义刷新的方向
    @Override
    public Orientation getPullToRefreshScrollDirection() {

        return Orientation.VERTICAL;
    }

//    创建被刷新的View
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context,attrs);
        recyclerView.setId(R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        RecyclerView recyclerView = getRefreshableView();
        View view = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        int position = recyclerView.getChildAdapterPosition(view);
        int count = recyclerView.getAdapter().getItemCount();
        return position==count-1&&view.getBottom()==recyclerView.getHeight()
                -recyclerView.getPaddingBottom();//适配器中的条目个数与控件中的条目个数进行对比
    }

    @Override
    protected boolean isReadyForPullStart() {
        RecyclerView recyclerView = getRefreshableView();
//        获得RecyclerView正展示的第一个
        View view = recyclerView.getChildAt(0);
        int position = recyclerView.getChildAdapterPosition(view);
        return position == 0 && recyclerView.getPaddingTop() == view.getTop();
    }
}
