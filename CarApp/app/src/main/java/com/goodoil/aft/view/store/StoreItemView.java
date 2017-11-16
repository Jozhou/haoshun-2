package com.goodoil.aft.view.store;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.StoreEntry;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.layoutview.MRelativeLayout;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/10/13.
 */
public class StoreItemView extends MRelativeLayout<StoreEntry> {

    @ViewInject("iv_store")
    private ImageView ivStore;
    @ViewInject("tv_name")
    private TextView tvName;
    @ViewInject("iv_star")
    private ImageView ivStar;
    @ViewInject("tv_time")
    private TextView tvTime;
    @ViewInject("tv_tel")
    private TextView tvTel;
    @ViewInject("tv_distance")
    private TextView tvDistance;
    @ViewInject("tv_address")
    private TextView tvAddress;

    private String lon;
    private String lat;
    private DecimalFormat decimalFormat;

    public StoreItemView(Context context) {
        super(context);
    }

    public StoreItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_store;
    }

    @Override
    protected void initData() {
        super.initData();
        decimalFormat = new DecimalFormat("##0.0");
    }

    public void setDataSource(StoreEntry entry, String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
        super.setDataSource(entry);
    }

    @Override
    protected void onApplyData() {
        Glide.with(mContext).load(mDataItem.image).dontAnimate().into(ivStore);
        tvName.setText(mDataItem.shopname);
        if (mDataItem.evolution == 10) {
            ivStar.setImageResource(R.drawable.shop_xingxing_5);
        } else if (mDataItem.evolution >=8) {
            ivStar.setImageResource(R.drawable.shop_xingxing_4);
        } else if (mDataItem.evolution >=6) {
            ivStar.setImageResource(R.drawable.shop_xingxing_3);
        } else if (mDataItem.evolution >=4) {
            ivStar.setImageResource(R.drawable.shop_xingxing_2);
        } else {
            ivStar.setImageResource(R.drawable.shop_xingxing_1);
        }
//        tvTime.setText(mDataItem.businessstart + "-" + mDataItem.businessend);
        tvTime.setText(mDataItem.businessTime);
        tvTel.setText(mDataItem.tel);
        tvAddress.setText(mDataItem.shoploacl);

//        if (isEmpty(lon) || isEmpty(lat)
//                || isEmpty(mDataItem.shoplon)
//                || isEmpty(mDataItem.shoplat)) {
//            tvDistance.setText(0 + "km");
//        } else {
//            try {
//                float lonF = Float.parseFloat(lon);
//                float latF = Float.parseFloat(lat);
//                float shopLon = Float.parseFloat(mDataItem.shoplon);
//                float shopLat = Float.parseFloat(mDataItem.shoplat);
//                LatLng latLng1 = new LatLng(latF, lonF);
//                LatLng latLng2 = new LatLng(shopLat, shopLon);
//                float distance = AMapUtils.calculateLineDistance(latLng1,latLng2);
//                tvDistance.setText(decimalFormat.format(distance) + "km");
//            } catch (NumberFormatException e) {
//                tvDistance.setText(0 + "km");
//            } catch (Exception e) {
//                tvDistance.setText(0 + "km");
//            }
//        }
        tvDistance.setText(mDataItem.distance + "km");
    }

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str);
    }
}
