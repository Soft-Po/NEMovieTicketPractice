package com.example.softpo.nemovieticketpractice.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.beans.Trailer;
import com.example.softpo.nemovieticketpractice.widget.MyTextButtonFrameLayout;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by home on 2016/6/29.
 */
public class MyHeadersListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private static final long MAX_POP = 0;
    public int headerHeight = 0;
    private List<Trailer.TrailerItem> mList;
    private LayoutInflater mInflater;
    private String mYearCurrent;
    private Context mContext;

    public MyHeadersListAdapter(Context context, List<Trailer.TrailerItem> itemList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mList = itemList;

        long timeMillis = System.currentTimeMillis();
        Date dateCurrent = new Date(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        mYearCurrent = simpleDateFormat.format(dateCurrent);
//        Log.d("flag", "---------------headers list current year:" + mYearCurrent);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderHolder headerHolder;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.trailerheaders_layout,parent,false);
            headerHolder = new HeaderHolder();
            headerHolder.headerName = (TextView) convertView.findViewById(R.id.trailerheader_nameText);
            convertView.setTag(headerHolder);
        }else {
            headerHolder = (HeaderHolder) convertView.getTag();
        }
        long headerId = getHeaderId(position);
        int filmCounts = getFIlmCountsForThisMonth(headerId);
        if (headerId==MAX_POP){
            headerHolder.headerName.setText("最受关注电影");
        }else if (headerId<13){
//            本年度将上映的电影
            headerHolder.headerName.setText(headerId+"月上映 ("+filmCounts+"部)");
        }else {
            headerId -= 20;
            headerHolder.headerName.setText("明年"+headerId+"月上映 ("+filmCounts+"部)");
        }
        if (position==0){
//            Log.d("flag","------------------------>getHeaderView(): "+convertView.toString());
            headerHeight = convertView.getHeight();

        }
        return convertView;
    }

    private int getFIlmCountsForThisMonth(long headerId) {
        int size = mList.size();
        int count = 0;
        for (int i = 3; i < size; i++) {
            Trailer.TrailerItem trailerItem = mList.get(i);
            String releaseDate = trailerItem.getReleaseDate();
            long certifyDate = certifyDate(releaseDate);
            if (certifyDate ==headerId) {
                count++;
            }
            if (certifyDate>headerId){
                break;
            }
        }
        return count;
    }

    @Override
    public long getHeaderId(int position) {
        if (position < 3) {
            return MAX_POP;
        }
        String releaseDate = mList.get(position).getReleaseDate();
        return certifyDate(releaseDate);
    }

    /**
     * 将月份重新整理
     * @param
     * @return
     */
    private long certifyDate(String releaseDate){
        String[] date = releaseDate.split("-");
        String year = date[0];
        long l = Long.parseLong(year);
        long l1 = Long.parseLong(mYearCurrent);
        String month = date[1];
        if (month.startsWith("0")) {
            month = String.valueOf(month.charAt(1));
        }
        long monthInt = Long.parseLong(month);
        if (l==l1){
            return monthInt;
        }else if (l==l1+1){
            return monthInt;
        }
        return 100;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ItemViewHolder itemViewHolder;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.traileritem_layout, parent, false);
            itemViewHolder = new ItemViewHolder();
            itemViewHolder.mButtonFrameLayout = (MyTextButtonFrameLayout) convertView.findViewById(R.id.trailerItem_textButton);

//            图片类
            itemViewHolder.mImageView_logo = (ImageView) convertView.findViewById(R.id.trailerItem_logoImage);
            itemViewHolder.mImageView_type = (ImageView) convertView.findViewById(R.id.trailerItem_iconTypeImage);
            itemViewHolder.mImageView_cinemaItem = (ImageView) convertView.findViewById(R.id.trailerItem_cinemaItem);
            itemViewHolder.mImageView_time = (ImageView) convertView.findViewById(R.id.trailerItem_screeningsFlag);

//            文本类的
            itemViewHolder.mTextView_name = (TextView) convertView.findViewById(R.id.trailerItem_titleText);
            itemViewHolder.mTextView_highlight = (TextView) convertView.findViewById(R.id.trailerItem_highLight);
            itemViewHolder.mTextView_screenings = (TextView) convertView.findViewById(R.id.trailerItem_screenings);
            itemViewHolder.mTextView_buttonText = (TextView) convertView.findViewById(R.id.trailerItem_textButtonContent);
            itemViewHolder.mTextView_grade = (TextView) convertView.findViewById(R.id.trailerItem_grade);

            convertView.setTag(itemViewHolder);
        }else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }
        Trailer.TrailerItem trailerItem = mList.get(position);
        itemViewHolder.mTextView_name.setText(trailerItem.getName());
        itemViewHolder.mTextView_highlight.setText(trailerItem.getHighlight());
//        获取展示状态
        String onShowStatus = trailerItem.getOnShowStatus();
        switch (onShowStatus) {
            case "1"://已经可以订票的情况
                itemViewHolder.mTextView_buttonText.setText("选座购票");
                String text = trailerItem.getGrade()+"分";
                itemViewHolder.mTextView_grade.setText(text);
                break;
            case "0":
                itemViewHolder.mTextView_buttonText.setText("查看影讯");
                String text1 = trailerItem.getNotifyCount()+"人想看";
                itemViewHolder.mTextView_grade.setText(text1);
                break;
        }

        String screenings = trailerItem.getScreenings();
        TextView text_screenings = itemViewHolder.mTextView_screenings;
        text_screenings.setText(screenings);
        if (screenings.startsWith("本周") && screenings.endsWith("上映")) {
            text_screenings.setTextColor(Color.parseColor("#f26500"));
            itemViewHolder.mImageView_time.setImageResource(R.mipmap.order_icon_clock);
        }else{
            text_screenings.setTextColor(Color.parseColor("#666666"));
            itemViewHolder.mImageView_time.setImageResource(R.mipmap.order_icon_time);
        }

//        设置影片类型的上浮标
        String dimensional = trailerItem.getDimensional();
        ImageView image_type = itemViewHolder.mImageView_type;
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
        String isNew = trailerItem.getIsNew();
        ImageView image_cinemaItem = itemViewHolder.mImageView_cinemaItem;
        if (screenings.endsWith("上映")){
            image_cinemaItem.setImageResource(R.mipmap.cinema_item_upcoming_movie);
            image_cinemaItem.setVisibility(View.VISIBLE);
        } else if (isNew.equals("1")) {
            image_cinemaItem.setImageResource(R.mipmap.cinema_item_new_movie);
            image_cinemaItem.setVisibility(View.VISIBLE);
        } else {
            image_cinemaItem.setVisibility(View.INVISIBLE);
        }

        ImageView image_logo = itemViewHolder.mImageView_logo;
        String logo_url = trailerItem.getLogo();
        Picasso.with(mContext).load(logo_url).into(image_logo);
//
//        if(position == 0){
//            Log.d("flag","------------------------>getVeiw(): "+convertView.toString());
//        }

        return convertView;
    }

    private class ItemViewHolder {
        private ImageView mImageView_logo,mImageView_type, mImageView_cinemaItem,mImageView_time;
        private TextView mTextView_name,mTextView_highlight,mTextView_screenings, mTextView_buttonText,mTextView_grade;
//        按钮，点击后跳转到二级页面
        private MyTextButtonFrameLayout mButtonFrameLayout;
    }

    private class HeaderHolder{
        private TextView headerName;

    }
}
