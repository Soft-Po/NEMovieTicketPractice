package com.example.softpo.nemovieticketpractice.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.beans.HotMovie;
import com.example.softpo.nemovieticketpractice.widget.MyTextButtonFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by home on 2016/6/28.
 */
public class MyPullToRefreshRecyclerViewAdapter extends RecyclerView.Adapter<MyPullToRefreshRecyclerViewAdapter.ViewHolder>{

    private List<HotMovie.ListBean> mList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public MyPullToRefreshRecyclerViewAdapter(List<HotMovie.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

//    设置recyclerView的滚动监听，设置holder的按钮图片还原
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
//        TODO 可设置项目详情的监听
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.filmitem_recyclerview,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HotMovie.ListBean filmItem = mList.get(position);
        holder.text_title.setText(filmItem.getName());
        holder.text_highlight.setText(filmItem.getHighlight());
        holder.text_grade.setText(filmItem.getGrade()+"分");

        String screenings = filmItem.getScreenings();
        TextView text_screenings = holder.text_screenings;
        text_screenings.setText(screenings);
        if (screenings.startsWith("本周") && screenings.endsWith("上映")) {
            text_screenings.setTextColor(Color.parseColor("#f26500"));
            holder.image_time.setImageResource(R.mipmap.order_icon_clock);
        }else{
            text_screenings.setTextColor(Color.parseColor("#666666"));
            holder.image_time.setImageResource(R.mipmap.order_icon_time);
        }

//        设置影片类型的上浮标
        String dimensional = filmItem.getDimensional();
        ImageView image_type = holder.image_type;
        if (dimensional.endsWith("IMAX")){
            image_type.setImageResource(R.mipmap.icon_type_3dimax);
            image_type.setVisibility(View.VISIBLE);
        }else if (dimensional.endsWith("3D")){
            image_type.setImageResource(R.mipmap.icon_type_3d);
            image_type.setVisibility(View.VISIBLE);
        }else {
            image_type.setVisibility(View.INVISIBLE);
        }

//        设置影片的上映类型
        String isNew = filmItem.getIsNew();
        ImageView image_cinemaItem = holder.image_cinemaItem;
        if (screenings.endsWith("上映")){
            image_cinemaItem.setImageResource(R.mipmap.cinema_item_upcoming_movie);
            image_cinemaItem.setVisibility(View.VISIBLE);
        } else if (isNew.equals("1")) {
            image_cinemaItem.setImageResource(R.mipmap.cinema_item_new_movie);
            image_cinemaItem.setVisibility(View.VISIBLE);
        } else {
            image_cinemaItem.setVisibility(View.INVISIBLE);
        }

        ImageView image_logo = holder.image_logo;
        String logo_url = filmItem.getLogo();
        Picasso.with(mContext).load(logo_url).into(image_logo);
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_logo,image_type, image_cinemaItem, image_time;
        private TextView text_title,text_highlight,text_grade, text_screenings;
        private MyTextButtonFrameLayout mButtonFrameLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            image_logo = (ImageView) itemView.findViewById(R.id.filmItem_logoImage);
            image_type = (ImageView) itemView.findViewById(R.id.filmItem_iconTypeImage);
            image_cinemaItem = (ImageView) itemView.findViewById(R.id.filmItem_cinemaItem);
            image_time = (ImageView) itemView.findViewById(R.id.filmItem_screeningsFlag);
//              TODO 需要设置订票跳转的按钮
            mButtonFrameLayout = (MyTextButtonFrameLayout) itemView.findViewById(R.id.filmItem_textButton);

            text_title = (TextView) itemView.findViewById(R.id.filmItem_titleText);
            text_highlight = (TextView) itemView.findViewById(R.id.filmItem_highLight);
            text_grade = (TextView) itemView.findViewById(R.id.filmItem_grade);
            text_screenings = (TextView) itemView.findViewById(R.id.filmItem_screenings);

        }
    }
}
