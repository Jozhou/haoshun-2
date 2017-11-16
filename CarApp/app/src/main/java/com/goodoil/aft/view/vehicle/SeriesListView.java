package com.goodoil.aft.view.vehicle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.models.operater.GetSeriesOperater;
import com.corelibrary.models.http.IArrayOperater;
import com.corelibrary.view.adapterview.PullToRefreshMoreView;

/**
 * Created by Administrator on 2017/10/9.
 */

public class SeriesListView extends PullToRefreshMoreView<VehicleItemEntry> {

    private GetSeriesOperater operater;
    private String brand_id = "";

    public SeriesListView(Context context) {
        super(context);
    }

    public SeriesListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeriesListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParams(String brand_id) {
        this.brand_id = brand_id;
    }

    @Override
    protected IArrayOperater<VehicleItemEntry> createMode() {
        if (operater == null) {
            operater = new GetSeriesOperater(mContext);
            operater.setParams(brand_id);
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
