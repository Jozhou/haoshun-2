package com.goodoil.aft.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.StoreEntry;
import com.goodoil.aft.view.store.StoreWebView;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.LogcatUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;
import com.corelibrary.view.WebView;


public class ActivityWebStore extends BaseActivity {

	public static final String AES_KEY = "RR5CbPegy5YVeuzoJ0lBeQ==";
	
	@ViewInject("titlebar")
	private TitleBar titleBar;
	@ViewInject("web_content")
	private StoreWebView webContent;
	private String webUrl;
	private String title;
	/**
	 * 是否自动获取web的title，如果是，则从html的header的title读取并赋值至titlebar
	 */
	private boolean autoTitle;

	private StoreEntry storeEntry;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_store);
	}
	
	@Override
	protected void onQueryArguments(Intent intent) {
		super.onQueryArguments(intent);
		String schema = getIntent().getScheme();
		if(!TextUtils.isEmpty(schema) && schema.equals("app")) {
			Uri uri = getIntent().getData();
			webUrl = uri.getQueryParameter(IntentCode.INTENT_WEB_URL);
			title = uri.getQueryParameter(IntentCode.INTENT_WEB_TITLE);
			autoTitle = "true".equals(uri.getQueryParameter(IntentCode.INTENT_WEB_AUTOTITLE));
		} else {
			webUrl = getIntent().getStringExtra(IntentCode.INTENT_WEB_URL);
			title = getIntent().getStringExtra(IntentCode.INTENT_WEB_TITLE);
			autoTitle = getIntent().getBooleanExtra(IntentCode.INTENT_WEB_AUTOTITLE, false);
		}
		storeEntry = (StoreEntry) getIntent().getSerializableExtra(IntentCode.INTENT_STORE_ITEM);
	}
	
	@Override
	protected void onBindListener() {
		super.onBindListener();
		titleBar.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if (autoTitle) {
			webContent.setWebViewCallback(new WebView.IWebViewCallback() {
				@Override
				public void onReceivedTitle(android.webkit.WebView webview, String title) {
					titleBar.setTitle(title);
				}
			});
		}
	}
	
	@Override
	protected void onApplyData() {
		super.onApplyData();
		if(title == null && !autoTitle){
			titleBar.setVisibility(View.GONE);
		}else {
			titleBar.setVisibility(View.VISIBLE);
			titleBar.setTitle(title);
		}
		webContent.gotoBlank();
		LogcatUtils.i(BaseOperater.TAG, webUrl);
		webContent.loadUrl(webUrl);
		webContent.setStoreEntry(storeEntry);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(webContent.onKeyDown(keyCode, event)){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		webContent.destroy();
		super.onDestroy();
		System.exit(0);
	}
	
//	public static String getSignUrl(String url, HashMap<String, String> params){
//		StringBuffer sb = new StringBuffer(url);
//		if(url.contains("?")) {
//			sb.append("&key=");
//		} else {
//			sb.append("?key=");
//		}
//		String jsonStr = new JSONObject(params).toString();
//		LogcatUtils.i("DCarTask", jsonStr);
//		sb.append(AESCryptor.encrypt(jsonStr, ActivityWeb.AES_KEY));
//		String requestUrl = sb.toString();
//		return requestUrl;
//	}

}