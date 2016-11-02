package com.example.softpo.nemovieticketpractice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;


import com.example.softpo.nemovieticketpractice.R;
import com.example.softpo.nemovieticketpractice.beans.City;

import java.util.List;

/**
 * Created by home on 2016/7/6.
 */
public class MyCityListAdapter extends BaseAdapter implements SectionIndexer {
    private List<City> list = null;

//    外部获取item数据的接口，当被点击时回调
    private OnCityItemClickListener mOnCityItemClickListener;

    public void setOnCityItemClickListener(OnCityItemClickListener onCityItemClickListener) {
        mOnCityItemClickListener = onCityItemClickListener;
    }

    public List<City> getList() {
        return list;
    }

    private Context mContext;

    public MyCityListAdapter(
            Context mContext,
            List<City> list
    ) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<City> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        City city = list.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.citylist_itemlayout, null);
            viewHolder.groupName = (TextView) convertView.findViewById(R.id.cityList_groupName_textView);
            viewHolder.cityName = (TextView) convertView.findViewById(R.id.cityList_cityName_textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        进行赋值
        String name = city.getName();
        if (!name.endsWith("市")) {
            name = name + "市";
        }
        viewHolder.cityName.setText(name);
        final int code = city.getCode();
        final String finalName = name;
        viewHolder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                将结果回调
                mOnCityItemClickListener.cityItemClicked(code, finalName);
            }
        });

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.groupName.setVisibility(View.VISIBLE);
            String text = null;
            if (section ==-1){
                text = "热";
            }else {
                text = String.valueOf(city.getSpell().toUpperCase().charAt(0));
            }
            viewHolder.groupName.setText(text);
        } else {
            viewHolder.groupName.setVisibility(View.GONE);
        }

        return convertView;
    }

    final static class ViewHolder {
        //组名 TextView
        TextView groupName;

        //城市 TextView
        TextView cityName;

    }



    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (sectionIndex==-1) {
            return 0;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String spell = list.get(i).getSpell();
            if (!spell.equals("热")) {
                char c = spell.charAt(0);
                if (c==sectionIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

//    获取section分组：热、A、B……
    @Override
    public int getSectionForPosition(int position) {
        City city = list.get(position);
        String spell = city.getSpell();
        if (spell.equals("热")) {
            return -1;
        }
        char c = spell.charAt(0);
        return c;
    }

    public interface OnCityItemClickListener{
        void cityItemClicked(int cityCode, String cityName);
    }
}
