package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.models.operater.UpdateCarcodeOperater;
import com.goodoil.aft.view.vehicle.SelBrandView;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

/**
 * Created by Administrator on 2017/10/9.
 */

public class SelBrandActivity extends BaseActivity {

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("rl_container")
    private RelativeLayout rlContainer;
    @ViewInject("v_sel_brand")
    private SelBrandView selBrandView;

//    private PullToRefreshMoreView refreshMoreView;

    public static final int SEL_BRAND = 1;
    public static final int SEL_SERIES = 2;
    public static final int SEL_YEAR_STYLE = 3;
    public static final int SEL_VERSION = 4;

    public static final int FROM_REIGSTER = 1;
    public static final int FROM_PERSONAL = 2;
    public static final int FROM_Conversation = 3;

    private int from;
    private int type;
    private VehicleItemEntry brand;
    private VehicleItemEntry series;
    private VehicleItemEntry yearStyle;
    private VehicleItemEntry version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_brand);
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
        from = intent.getIntExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, FROM_REIGSTER);
        type = intent.getIntExtra(IntentCode.INTENT_TYPE, 1);
        brand = (VehicleItemEntry) intent.getSerializableExtra(IntentCode.INTENT_BRAND);
        series = (VehicleItemEntry) intent.getSerializableExtra(IntentCode.INTENT_SERIES);
        yearStyle = (VehicleItemEntry) intent.getSerializableExtra(IntentCode.INTENT_YEAR_STYLE);
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selBrandView.setOnItemClickListener(new SelBrandView.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, VehicleItemEntry entry) {
                if (type == SEL_BRAND) {
                    Intent intent = new Intent(SelBrandActivity.this, SelVehicleActivity.class);
                    intent.putExtra(IntentCode.INTENT_TYPE, SelBrandActivity.SEL_SERIES);
                    intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, from);
                    intent.putExtra(IntentCode.INTENT_BRAND, entry);
                    startActivityForResult(intent, SEL_SERIES);
                } else if (type == SEL_SERIES) {
                    Intent intent = new Intent(SelBrandActivity.this, SelVehicleActivity.class);
                    intent.putExtra(IntentCode.INTENT_TYPE, SelBrandActivity.SEL_YEAR_STYLE);
                    intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, from);
                    intent.putExtra(IntentCode.INTENT_BRAND, brand);
                    intent.putExtra(IntentCode.INTENT_SERIES, entry);
                    startActivityForResult(intent, SelBrandActivity.SEL_YEAR_STYLE);
                } else if (type == SEL_YEAR_STYLE) {
                    Intent intent = new Intent(SelBrandActivity.this, SelVehicleActivity.class);
                    intent.putExtra(IntentCode.INTENT_TYPE, SelBrandActivity.SEL_VERSION);
                    intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, from);
                    intent.putExtra(IntentCode.INTENT_BRAND, brand);
                    intent.putExtra(IntentCode.INTENT_SERIES, series);
                    intent.putExtra(IntentCode.INTENT_YEAR_STYLE, entry);
                    startActivityForResult(intent, SelBrandActivity.SEL_VERSION);
                } else if (type == SEL_VERSION) {
                    if (from == FROM_REIGSTER || from == FROM_Conversation) {
                        Intent intent = new Intent();
                        intent.putExtra(IntentCode.INTENT_BRAND, brand);
                        intent.putExtra(IntentCode.INTENT_SERIES, series);
                        intent.putExtra(IntentCode.INTENT_YEAR_STYLE, yearStyle);
                        intent.putExtra(IntentCode.INTENT_VERSION, entry);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if (from == FROM_PERSONAL) {
                        version = entry;
                        updateCarcode();
                    }
                }
            }
        });

    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        titleBar.setTitle(getTitleRes(type));
//        if (type == SEL_BRAND) {
//            refreshMoreView = new BrandListView(this);
//        } else if (type == SEL_SERIES) {
//            refreshMoreView = new SeriesListView(this);
//            ((SeriesListView)refreshMoreView).setParams(brand.id);
//        } else if (type == SEL_YEAR_STYLE) {
//            refreshMoreView = new YearStyleListView(this);
//            ((YearStyleListView)refreshMoreView).setParams(series.id);
//        } else if (type == SEL_VERSION) {
//            refreshMoreView = new VersionListView(this);
//            ((VersionListView)refreshMoreView).setParams(series.id, yearStyle.name);
//        }
//        refreshMoreView.addItemDecoration(new SimpleListItemDecoration(this));
//        rlContainer.addView(refreshMoreView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        refreshMoreView.refresh();

//        refreshMoreView.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                VehicleItemEntry entry = (VehicleItemEntry)adapter.getData().get(position);
//                if (type == SEL_BRAND) {
//                    Intent intent = new Intent(SelBrandActivity.this, SelBrandActivity.class);
//                    intent.putExtra(IntentCode.INTENT_TYPE, SelBrandActivity.SEL_SERIES);
//                    intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, from);
//                    intent.putExtra(IntentCode.INTENT_BRAND, entry);
//                    startActivityForResult(intent, SEL_SERIES);
//                } else if (type == SEL_SERIES) {
//                    Intent intent = new Intent(SelBrandActivity.this, SelBrandActivity.class);
//                    intent.putExtra(IntentCode.INTENT_TYPE, SelBrandActivity.SEL_YEAR_STYLE);
//                    intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, from);
//                    intent.putExtra(IntentCode.INTENT_BRAND, brand);
//                    intent.putExtra(IntentCode.INTENT_SERIES, entry);
//                    startActivityForResult(intent, SelBrandActivity.SEL_YEAR_STYLE);
//                } else if (type == SEL_YEAR_STYLE) {
//                    Intent intent = new Intent(SelBrandActivity.this, SelBrandActivity.class);
//                    intent.putExtra(IntentCode.INTENT_TYPE, SelBrandActivity.SEL_VERSION);
//                    intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, from);
//                    intent.putExtra(IntentCode.INTENT_BRAND, brand);
//                    intent.putExtra(IntentCode.INTENT_SERIES, series);
//                    intent.putExtra(IntentCode.INTENT_YEAR_STYLE, entry);
//                    startActivityForResult(intent, SelBrandActivity.SEL_VERSION);
//                } else if (type == SEL_VERSION) {
//                    if (from == FROM_REIGSTER || from == FROM_Conversation) {
//                        Intent intent = new Intent();
//                        intent.putExtra(IntentCode.INTENT_BRAND, brand);
//                        intent.putExtra(IntentCode.INTENT_SERIES, series);
//                        intent.putExtra(IntentCode.INTENT_YEAR_STYLE, yearStyle);
//                        intent.putExtra(IntentCode.INTENT_VERSION, entry);
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    } else if (from == FROM_PERSONAL) {
//                        version = entry;
//                        updateCarcode();
//                    }
//                }
//            }
//        });

        selBrandView.refreshData();
    }

    private int getTitleRes(int type) {
        int resId = 0;
        if (type == SEL_BRAND) {
            resId = R.string.title_sel_brand;
        } else if (type == SEL_SERIES) {
            resId = R.string.title_sel_series;
        } else if (type == SEL_YEAR_STYLE) {
            resId = R.string.title_sel_year_style;
        } else if (type == SEL_VERSION) {
            resId = R.string.title_sel_version;
        }
        return resId;
    }

    private void updateCarcode() {
        final UpdateCarcodeOperater operater = new UpdateCarcodeOperater(this);
        operater.setParams(version.carcode);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    DialogUtils.showToastMessage(operater.getMsg());
                    Intent intent = new Intent();
                    intent.putExtra(IntentCode.INTENT_BRAND, brand);
                    intent.putExtra(IntentCode.INTENT_SERIES, series);
                    intent.putExtra(IntentCode.INTENT_YEAR_STYLE, yearStyle);
                    intent.putExtra(IntentCode.INTENT_VERSION, version);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
