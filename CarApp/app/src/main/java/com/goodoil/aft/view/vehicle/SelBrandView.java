package com.goodoil.aft.view.vehicle;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.adapter.BrandAdapter;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.models.operater.GetBrandOperater2;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.loading.FrameLayout;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * Created by Administrator on 2017/10/31.
 */

public class SelBrandView extends FrameLayout {

    @ViewInject("indexableLayout")
    private IndexableLayout indexableLayout;

    private BrandAdapter mAdapter;
    private List<VehicleItemEntry> vehicleItemEntries;

    public SelBrandView(Context context) {
        super(context);
    }

    public SelBrandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelBrandView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.view_sel_brand;
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();

        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("长安",  new String[]{"CHANG", "AN"});
                        map.put("长城",  new String[]{"CHANG", "CHENG"});
                        map.put("长丰",  new String[]{"CHANG", "FENG"});
                        return map;
                    }
                }));
        indexableLayout.setLayoutManager(new LinearLayoutManager(mContext));
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        mAdapter = new BrandAdapter(mContext);
        indexableLayout.setAdapter(mAdapter);

        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<VehicleItemEntry>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, VehicleItemEntry entity) {
                if (originalPosition >= 0) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(originalPosition, entity);
                    }
                }
            }
        });
    }

    public void refreshData() {
        getBrandData();
    }

    private void getBrandData() {
        final GetBrandOperater2 operater = new GetBrandOperater2(mContext);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    vehicleItemEntries = operater.getVehicleItemEntries();
                    if (vehicleItemEntries.size() == 0) {
                        gotoBlank();
                    } else {
                        gotoSuccessful();
                        mAdapter.setDatas(vehicleItemEntries);
                    }
                } else {
                    gotoError();
                }
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, VehicleItemEntry entry);
    }

    @Override
    public void onApplyLoadingData() {
        super.onApplyLoadingData();
        refreshData();
    }
}
