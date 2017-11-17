package com.goodoil.aft.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.amap.api.maps.offlinemap.City;
import com.corelibrary.utils.DialogUtils;
import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.CityItemEntry;
import com.goodoil.aft.view.mine.CityListView;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Administrator on 2017/10/16.
 */

@RuntimePermissions
public class CityListActivity extends BaseActivity {


    @ViewInject(value = "titlebar")
    private TitleBar titlebar;
    @ViewInject(value = "v_city_list")
    private CityListView cityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
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

        cityListView.setOnItemClickListener(new CityListView.OnItemClickListener() {
            @Override
            public void onItemClick(CityItemEntry entry) {
                Intent intent = new Intent();
                intent.putExtra(IntentCode.INTENT_CITY_ITEM, entry);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();

        CityListActivityPermissionsDispatcher.requestDataWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    public void requestData() {
        cityListView.refreshData();
    }

    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showDeniedForCamera() {
        DialogUtils.showToastMessage("权限被拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showNeverAskForCamera() {
        DialogUtils.showToastMessage("权限被永久拒绝");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CityListActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
