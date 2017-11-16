package com.goodoil.aft.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.StoreEntry;
import com.goodoil.aft.models.operater.GetStoreOperater;
import com.chad.library.adapter.base.BaseViewHolder;
import com.corelibrary.models.http.IArrayOperater;
import com.corelibrary.view.adapterview.PullToRefreshMoreView;

/**
 * Created by Administrator on 2017/10/9.
 */

public class StoreListView extends PullToRefreshMoreView<StoreEntry> {

    private GetStoreOperater operater;

    public StoreListView(Context context) {
        super(context);
    }

    public StoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected IArrayOperater<StoreEntry> createMode() {
        if (operater == null) {
            operater = new GetStoreOperater(mContext);
            operater.setParams(userlon, userlat, shoptype);
            operater.setShowLoading(false);
        }
        return operater;
    }

    @Override
    protected SparseIntArray getItemViewTypeAndResId() {
        SparseIntArray array = new SparseIntArray();
        array.put(0, R.layout.item_store);
        return array;
    }

    @Override
    protected View getLayoutItemView(int resId) {
        return new StoreItemView(mContext);
    }

    @Override
    protected void convert(BaseViewHolder holder, StoreEntry item) {
        ((StoreItemView)holder.itemView).setDataSource(item, userlon, userlat);
    }

    @Override
    public void refresh() {
        getFirstPage(false, true);
    }

    private String userlon;
    private String userlat;
    private int shoptype;

    public void setParams(String userlon, String userlat, int shoptype) {
        this.userlon = userlon;
        this.userlat = userlat;
        this.shoptype = shoptype;
    }

    @Override
    public void onApplyLoadingData() {
        if (onLoadingDataListener != null) {
            onLoadingDataListener.onLoadingData();
        }
    }

    private OnLoadingDataListener onLoadingDataListener;

    public void setOnLoadingDataListener(OnLoadingDataListener onLoadingDataListener) {
        this.onLoadingDataListener = onLoadingDataListener;
    }

    public interface OnLoadingDataListener {
        void onLoadingData();
    }
}
