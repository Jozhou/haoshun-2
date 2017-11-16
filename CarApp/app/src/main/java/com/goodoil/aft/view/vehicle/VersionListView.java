package com.goodoil.aft.view.vehicle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.models.operater.GetVersionOperater;
import com.corelibrary.models.http.IArrayOperater;
import com.corelibrary.view.adapterview.PullToRefreshMoreView;

/**
 * Created by Administrator on 2017/10/9.
 */

public class VersionListView extends PullToRefreshMoreView<VehicleItemEntry> {

    private GetVersionOperater operater;
    private String brand_id = "";
    private String series_id = "";
    private String year_style = "";

    public VersionListView(Context context) {
        super(context);
    }

    public VersionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VersionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParams(String brand_id, String series_id, String year_style) {
        this.brand_id = brand_id;
        this.series_id = series_id;
        this.year_style = year_style;
    }

    @Override
    protected IArrayOperater<VehicleItemEntry> createMode() {
        if (operater == null) {
            operater = new GetVersionOperater(mContext);
            operater.setParams(brand_id, series_id, year_style);
            operater.setShowLoading(false);
        }
        return operater;
    }

    @Override
    protected SparseIntArray getItemViewTypeAndResId() {
        SparseIntArray array = new SparseIntArray();
        array.put(0, R.layout.item_vehicle);
        return array;
    }

    @Override
    protected View getLayoutItemView(int resId) {
        return new VehicleItemView(mContext);
    }

}
