package com.goodoil.aft.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import com.amap.api.maps.model.LatLng;
import com.corelibrary.application.AppContext;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.OSUtils;

import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017/11/6.
 */

public class NavUtils {

    private static final String GAODEAPP = "com.autonavi.minimap";
    private static final String BAIDUAPP = "com.baidu.BaiduMap";

    /**
     * 确定起终点坐标BY高德
     */
    public static void setUpGaodeAppByLoca(Context context, LatLng latLng){
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat="+latLng.latitude+"&dlon="+latLng.longitude+"&dname="+"目的地"+"&dev=0&m=0&t=1");
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            DialogUtils.showToastMessage("未安装高德地图");
        }
    }

    /**
     * 通过起终点名字使用百度地图
     */
    public static void setUpBaiduAPPByName(Context context, LatLng latLng){
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=latlng:"+latLng.latitude+","+latLng.longitude+"|name:终点&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            DialogUtils.showToastMessage("未安装百度地图");
        }
    }
}
