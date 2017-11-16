package com.goodoil.aft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.StoreEntry;
import com.goodoil.aft.utils.location.DLocation;
import com.goodoil.aft.utils.location.LocationListener;
import com.goodoil.aft.utils.location.LocationManager;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class StoreMapActivity extends BaseActivity {


    @ViewInject(value = "titlebar")
    private TitleBar titlebar;
    @ViewInject(value = "mapview")
    private MapView mapView;

    private AMap aMap;
    private ArrayList<StoreEntry> storeEntries;
    private List<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        initData();
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
        storeEntries = (ArrayList<StoreEntry>) intent.getSerializableExtra(IntentCode.INTENT_STORE_LIST);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        titlebar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
    }

    private void initData() {
        markers = new ArrayList<>();
        for (int i = 0; i <storeEntries.size(); i ++) {
            StoreEntry storeEntry = storeEntries.get(i);
            if (isEmpty(storeEntry.shoplat) || isEmpty(storeEntry.shoplon)) {
                continue;
            }
            LatLng latLng = new LatLng(Float.parseFloat(storeEntry.shoplat), Float.parseFloat(storeEntry.shoplon));
            Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(storeEntry.shopname).snippet("DefaultMarker"));
            markers.add(marker);
        }

        LocationManager.getInstance().registLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(DLocation location) {
                Activity activity = StoreMapActivity.this;
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15));
            }

            @Override
            public void onlocationFail() {

            }
        });
    }

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
