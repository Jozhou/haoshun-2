package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.models.entry.CityItemEntry;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GetCityOperater extends BaseOperater {

    List<CityItemEntry> cityItemEntries = new ArrayList<>();

    public GetCityOperater(Context context) {
        super(context);
        setParams();
    }

    public void setParams() {
        try {
            paramsObj.put("size", 10000);
            paramsObj.put("page", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/page/city";
    }

    @Override
    protected void onParser(JSONObject jo) {
        cityItemEntries = new ArrayList<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("list");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                CityItemEntry entry = new CityItemEntry();
                entry.province_id =jsonObject.getString("province_id");
                entry.province_name =jsonObject.getString("province_name");
                cityItemEntries.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public List<CityItemEntry> getCityItemEntries() {
        return cityItemEntries;
    }

}
