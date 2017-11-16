package com.goodoil.aft.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.activity.ActivityWebStore;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.StoreEntry;
import com.goodoil.aft.utils.location.DLocation;
import com.goodoil.aft.utils.location.LocationListener;
import com.goodoil.aft.utils.location.LocationManager;
import com.goodoil.aft.view.store.MyStoreListView;
import com.goodoil.aft.view.store.StoreListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.corelibrary.fragment.base.BaseFragment;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.decoration.SimpleListItemDecoration;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MyStoreFragment extends BaseFragment {

    @ViewInject("rv_all")
    private MyStoreListView rvAll;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_my_store;
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();

        rvAll.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StoreEntry entry = (StoreEntry) adapter.getData().get(position);
                Intent intent = new Intent(mContext, ActivityWebStore.class);
                intent.putExtra(IntentCode.INTENT_WEB_URL, entry.url);
                intent.putExtra(IntentCode.INTENT_STORE_ITEM, entry);
                intent.putExtra(IntentCode.INTENT_WEB_TITLE, getString(R.string.store_detail));
                startActivity(intent);
            }
        });

        rvAll.setOnLoadingDataListener(new MyStoreListView.OnLoadingDataListener() {
            @Override
            public void onLoadingData() {
                getData();
            }
        });

    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        rvAll.addItemDecoration(new SimpleListItemDecoration(mContext, R.drawable.divider_latest_news));
        getData();
    }

    private void getData() {
        rvAll.gotoLoading();
        LocationManager.getInstance().registLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(DLocation location) {
                Activity activity = (Activity) mContext;
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                rvAll.setParams(location.getLongitude() + "", location.getLatitude() + "", 0);
                rvAll.refresh();
            }

            @Override
            public void onlocationFail() {
                rvAll.gotoError();
                DialogUtils.showToastMessage(R.string.location_fail);
            }
        });
    }
}
