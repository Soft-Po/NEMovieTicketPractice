package com.example.softpo.nemovieticketpractice.parsers;

import android.os.Handler;
import android.util.Log;


import com.example.softpo.nemovieticketpractice.beans.CinemaDistrictRel;
import com.example.softpo.nemovieticketpractice.beans.City;
import com.example.softpo.nemovieticketpractice.beans.District;
import com.example.softpo.nemovieticketpractice.constants.HandlerCommenConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2016/6/27.
 */
public class BaseDataJsonParser {
    public static void parseJsonString(String jsonString, DbManager dbManager) {
//        只是更新地区列表即不需要 更新城市列表
        parseJsonString(jsonString,dbManager,null);
    }

    public static void parseJsonString(String jsonString, DbManager dbManager, Handler handler) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray_districtList = jsonObject.optJSONArray("districtList");
            List<District> districtList = new ArrayList<>();
            for (int i = 0; i < jsonArray_districtList.length(); i++) {
                JSONObject jsonObject_district = jsonArray_districtList.optJSONObject(i);
                District district = new District();

                district.setId(jsonObject_district.optInt("id"));
                district.setName(jsonObject_district.optString("name"));
                districtList.add(district);
            }

            JSONArray jsonArray_cinemaDistrictRelList = jsonObject.optJSONArray("cinemaDistrictRelList");
            List<CinemaDistrictRel> cinemaDistrictRelList = new ArrayList<>();
            for (int i = 0; i < jsonArray_cinemaDistrictRelList.length(); i++) {
                JSONObject jsonObject_cinemaDistrictRel = jsonArray_cinemaDistrictRelList.optJSONObject(i);
                CinemaDistrictRel cinemaDistrictRel = new CinemaDistrictRel();

                cinemaDistrictRel.setCinemaId(jsonObject_cinemaDistrictRel.optInt("cinemaId"));
                cinemaDistrictRel.setDistrictId(jsonObject_cinemaDistrictRel.optInt("districtId"));
                cinemaDistrictRelList.add(cinemaDistrictRel);
            }

            JSONArray jsonArray_cityList = jsonObject.optJSONArray("cityList");
            List<City> cityList = new ArrayList<>();
            for (int i = 0; i < jsonArray_cityList.length(); i++) {
                City city = new City();
                JSONObject jsonObject_city = jsonArray_cityList.optJSONObject(i);

                city.setCode(jsonObject_city.optInt("code"));
                city.setName(jsonObject_city.optString("name"));
                city.setSpell(jsonObject_city.optString("spell"));

                cityList.add(city);
            }

            try {
                dbManager.dropTable(District.class);
            } catch (DbException e) {
                e.printStackTrace();
            }try {
                dbManager.dropTable(CinemaDistrictRel.class);
            } catch (DbException e) {
                e.printStackTrace();
            }

//            将获得的数据保存到数据库中
            try {

                dbManager.save(districtList);
                dbManager.save(cinemaDistrictRelList);
                Log.d("dbManager", "-------------->区域信息 更新成功！");
            } catch (DbException e) {
                e.printStackTrace();
            }

//            初始化，获取城市数据库时，会继续向下执行
            if (handler != null) {
                try {
                    dbManager.dropTable(City.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                try {
                    dbManager.save(cityList);
                    handler.sendEmptyMessage(HandlerCommenConst.PARSE_BASEDATA_OK);
                    Log.d("dbManager", "-------------->basedata table 保存成功");
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
