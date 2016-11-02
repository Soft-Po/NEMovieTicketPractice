package com.example.softpo.nemovieticketpractice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.adapters.MyHeadersListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by home on 2016/6/28.
 */
public class PullToRefreshListHeaders extends PullToRefreshBase<StickyListHeadersListView> {
    public PullToRefreshListHeaders(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListHeaders(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListHeaders(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

//    定义刷新的方向
    @Override
    public Orientation getPullToRefreshScrollDirection() {

        return Orientation.VERTICAL;
    }

//    创建被刷新的View
    @Override
    protected StickyListHeadersListView createRefreshableView(Context context, AttributeSet attrs) {
        StickyListHeadersListView headersListView = new StickyListHeadersListView(context,attrs);
        headersListView.setId(R.id.headersListView);
        return headersListView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        StickyListHeadersListView headersListView = getRefreshableView();
        View view = headersListView.getChildAt(headersListView.getChildCount() - 1);

        return view.getBottom()==headersListView.getHeight()
                -headersListView.getPaddingBottom();//适配器中的条目个数与控件中的条目个数进行对比
    }

    @Override
    protected boolean isReadyForPullStart() {
        StickyListHeadersListView headersListView = getRefreshableView();

        int firstVisiblePosition = headersListView.getFirstVisiblePosition();



//        Log.d("flag","-------------------------!!!!!>fistPosition: "+firstVisiblePosition);
//        获得RecyclerView正展示的第一个
        ViewGroup view = (ViewGroup) headersListView.getChildAt(0);
//
        ViewGroup wrapperView = (ViewGroup) view.getChildAt(0);
//        ViewGroup wrapperView1 = (ViewGroup) view.getChildAt(1);
//        View item = wrapperView.getChildAt(0);
//        View item1 = wrapperView1.getChildAt(0);
//
//        Log.d("flag","####>headerslistView-> stickylistheaders-> wrapperview->item#### "+item.toString());
//        Log.d("flag","####>headerslistView-> stickylistheaders-> wrapperview->???#### "+item1.toString());

        MyHeadersListAdapter adapter = (MyHeadersListAdapter) headersListView.getAdapter();
//        加2是提供一个偏移量，使得更容易下拉刷新
        boolean b = firstVisiblePosition==0&&headersListView.getPaddingTop() <= wrapperView.getTop()-adapter.headerHeight+2;
//        Log.d("flag", "------------>下拉刷新判定条件" + b);
        return b;
    }
}
