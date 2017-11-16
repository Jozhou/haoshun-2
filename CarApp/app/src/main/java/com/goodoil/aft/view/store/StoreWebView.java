package com.goodoil.aft.view.store;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.goodoil.aft.R;
import com.goodoil.aft.activity.ConversationQuerylActivity;
import com.goodoil.aft.models.entry.StoreEntry;
import com.goodoil.aft.models.operater.ShopOrderOperater;
import com.goodoil.aft.utils.NavUtils;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.ButtonUtils;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.OSUtils;
import com.corelibrary.view.WebView;
import com.goodoil.aft.view.popup.PopConfirm;

import java.net.URISyntaxException;


public class StoreWebView extends WebView implements View.OnClickListener {

	private static final String GAODEAPP = "com.autonavi.minimap";
	private static final String BAIDUAPP = "com.baidu.BaiduMap";

	private TextView tvOrder;
	private TextView tvQuery;

	private StoreEntry storeEntry;
	private LatLng latLng;

	PopConfirm popConfirm;

	public StoreWebView(Context context) {
		super(context);
	}

	public StoreWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StoreWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.view_webview_store;
	}

	public void setStoreEntry(StoreEntry storeEntry) {
		this.storeEntry = storeEntry;
	}

	@Override
	protected void onFindView() {
		super.onFindView();
		tvOrder = (TextView) findViewById(R.id.tv_order);
		tvQuery = (TextView) findViewById(R.id.tv_conversation_query);
		tvOrder.setOnClickListener(this);
		tvQuery.setOnClickListener(this);
	}

	@Override
	protected void onApplyData() {
		super.onApplyData();
		popConfirm = new PopConfirm(mContext);
		popConfirm.setText(mContext.getText(R.string.gaode_nav), mContext.getString(R.string.baidu_nav));
		popConfirm.setOneClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NavUtils.setUpGaodeAppByLoca(mContext, latLng);
//				setUpGaodeAppByMine(latLng);
			}
		});

		popConfirm.setTwoClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NavUtils.setUpBaiduAPPByName(mContext, latLng);
//				setUpBaiduAPPByMine(latLng);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (ButtonUtils.isFastDoubleClick()) {
			return;
		}
		int id = v.getId();
		if (id == R.id.tv_order) {
			order();
		} else if (id == R.id.tv_conversation_query) {
			Intent intent = new Intent(mContext, ConversationQuerylActivity.class);
			mContext.startActivity(intent);
		}
	}

	private void order() {
		final ShopOrderOperater operater = new ShopOrderOperater(mContext);
		operater.setParams(Integer.parseInt(storeEntry.shopid));
		operater.onReq(new BaseOperater.RspListener() {
			@Override
			public void onRsp(boolean success, Object obj) {
				if (success) {
					DialogUtils.showToastMessage(operater.getMsg());
				}
			}
		});
	}

	private static final double LATITUDE_ZHONGDIAN = 31.578278;  //终点纬度
	private static final double LONGTITUDE_ZHONGDIAN = 121.019691;  //终点经度

	@JavascriptInterface
	public void tohere(String addr) {
		if (TextUtils.isEmpty(addr)) {
			DialogUtils.showToastMessage(mContext.getString(R.string.addr_error));
			return;
		}
		String[] strings = addr.split(",");
		if (strings.length != 2 || TextUtils.isEmpty(strings[0]) || TextUtils.isEmpty(strings[1])) {
			DialogUtils.showToastMessage(mContext.getString(R.string.addr_error));
			return;
		}
		try {
			latLng = new LatLng(Double.parseDouble(strings[1]), Double.parseDouble(strings[0]));
			popConfirm.show();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			DialogUtils.showToastMessage(mContext.getString(R.string.addr_error));
		}
	}

	void setUpBaiduAPPByMine(LatLng latLng){
		try {
			Intent intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=latlng:"+latLng.latitude+","+latLng.longitude+"|name:终点&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if(OSUtils.isInstallByread("com.baidu.BaiduMap")){
				mContext.startActivity(intent);
			}else {
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 我的位置BY高德
	 */
	void setUpGaodeAppByMine(LatLng latLng){
		try {
			Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat="+latLng.latitude+"&dlon="+latLng.longitude+"&dname="+"目的地"+"&dev=0&m=0&t=1");
			if(OSUtils.isInstallByread("com.autonavi.minimap")){
				mContext.startActivity(intent);
			}else {
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
