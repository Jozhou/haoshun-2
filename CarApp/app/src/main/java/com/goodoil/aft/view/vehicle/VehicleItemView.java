package com.goodoil.aft.view.vehicle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.layoutview.MLinearLayout;

/**
 * Created by Administrator on 2017/10/9.
 */

public class VehicleItemView extends MLinearLayout<VehicleItemEntry> {

    @ViewInject("tv_name")
    private TextView tvName;

    public VehicleItemView(Context context) {
        super(context);
    }

    public VehicleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VehicleItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onApplyData() {
        tvName.setText(mDataItem.name);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_vehicle;
    }
}
