package com.goodoil.aft.view.mine;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.adapter.CityAdapter;
import com.goodoil.aft.models.adapter.GpsHeaderAdapter;
import com.goodoil.aft.models.entry.CityItemEntry;
import com.goodoil.aft.models.operater.GetCityOperater;
import com.goodoil.aft.utils.location.DLocation;
import com.goodoil.aft.utils.location.LocationListener;
import com.goodoil.aft.utils.location.LocationManager;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.loading.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

/**
 * Created by Administrator on 2017/10/31.
 */

public class CityListView extends FrameLayout {

    @ViewInject("indexableLayout")
    private IndexableLayout indexableLayout;

    private CityAdapter mAdapter;
    private List<CityItemEntry> cityItemEntries;

    private SimpleHeaderAdapter mHotCityAdapter;
    private GpsHeaderAdapter mGpsHeaderAdapter;
    private List<CityItemEntry> gpsCity;

    boolean isLocSucc = false;

    public CityListView(Context context) {
        super(context);
    }

    public CityListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CityListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.view_city_list;
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        indexableLayout.setLayoutManager(new LinearLayoutManager(mContext));
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        mAdapter = new CityAdapter(mContext);
        indexableLayout.setAdapter(mAdapter);

        mHotCityAdapter = new SimpleHeaderAdapter<>(mAdapter, "热", "热门城市", iniyHotCityDatas());
        // 热门城市
        indexableLayout.addHeaderAdapter(mHotCityAdapter);
        // 定位
        gpsCity = iniyGPSCityDatas();
        mGpsHeaderAdapter = new GpsHeaderAdapter(mContext, "", "当前城市", gpsCity);
        indexableLayout.addHeaderAdapter(mGpsHeaderAdapter);

        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityItemEntry>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CityItemEntry entity) {
                if (originalPosition >= 0) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(entity);
                    }
                }
            }
        });

        mGpsHeaderAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CityItemEntry entry) {
                if (!isLocSucc) {
                    return;
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(entry);
                }
            }
        });

    }

    public void refreshData() {
        getCityData();
        startLoction();
    }

    private void getCityData() {
        final GetCityOperater operater = new GetCityOperater(mContext);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    cityItemEntries = operater.getCityItemEntries();
                    if (cityItemEntries.size() == 0) {
                        gotoBlank();
                    } else {
                        gotoSuccessful();
                        mAdapter.setDatas(cityItemEntries);
                    }
                } else {
                    gotoError();
                }
            }
        });
    }

    private void startLoction() {
        LocationManager.getInstance().registLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(DLocation location) {
                Activity activity = (Activity) mContext;
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                gpsCity.get(0).province_name = location.getProvince();
                mGpsHeaderAdapter.notifyDataSetChanged();
                isLocSucc = true;
            }

            @Override
            public void onlocationFail() {
                gpsCity.get(0).province_name = mContext.getString(R.string.location_fail);
                mGpsHeaderAdapter.notifyDataSetChanged();
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CityItemEntry entry);
    }

    @Override
    public void onApplyLoadingData() {
        super.onApplyLoadingData();
        refreshData();
    }

    private List<CityItemEntry> iniyGPSCityDatas() {
        List<CityItemEntry> list = new ArrayList<>();
        list.add(new CityItemEntry("定位中..."));
        return list;
    }

    private List<CityItemEntry> iniyHotCityDatas() {
        List<CityItemEntry> list = new ArrayList<>();
        list.add(new CityItemEntry(""));
        return list;
    }
}
