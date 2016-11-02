package com.example.softpo.nemovieticketpractice.parsers;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2016/6/25.
 */
public class SplanshJsonParser {

    public static void parseNow(final String result) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.optJSONArray("startupList");
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonImg = jsonArray.optJSONObject(i);
                        String picUrl = jsonImg.optString("picLargePath");
                        list.add(picUrl);
                    }

                    EventBus.getDefault().post(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
