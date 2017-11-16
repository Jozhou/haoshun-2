package com.corelibrary.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings.TextSize;

import com.corelibrary.R;
import com.corelibrary.utils.LogcatUtils;
import com.corelibrary.view.loading.ErrorView;
import com.corelibrary.view.loading.RelativeLayout;
import com.corelibrary.widget.WebView.MWebViewClient;
import com.corelibrary.widget.WebView.MWebChromeClient;

public class WebView extends RelativeLayout {

	private static final String TAG = WebView.class.getName();
	private com.corelibrary.widget.WebView webView;
	
	private boolean bSucc;
	private IWebViewCallback mCallback;
	private static final String BACK_FLAG = "/app/haoshun/goback";

	/**
	 * 设置webview的callback回调
	 * @param c
	 */
	public void setWebViewCallback(IWebViewCallback c) {
		mCallback = c;
	}
	
	@Override
	protected View getErrorView() {
		return new ErrorView(mContext);
	}
	
	@Override
	protected boolean hideContentViewOnLoading() {
		return true;
	}
	
	public WebView(Context context) {
		super(context);
	}
	
	public WebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public WebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.view_webview;
	}
	
	@Override
	protected void onFindView() {
		super.onFindView();
		webView = (com.corelibrary.widget.WebView) findViewById(R.id.web);
	}
	
	@Override
	protected void onBindListener() {
		super.onBindListener();
		webView.addJavascriptInterface(this, "moto");
		webView.setWebViewClient(new MWebViewClient(webView, mContext){
			@Override
			public void onReceivedError(android.webkit.WebView view,
					int errorCode, String description, String failingUrl) {
				LogcatUtils.e(TAG, "onReceivedError");
				bSucc = false;
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			
			@Override
			public void onPageStarted(android.webkit.WebView view, String url,
					Bitmap favicon) {
				LogcatUtils.e(TAG, "onPageStarted");
				gotoLoading();
				bSucc = true;
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public void onPageFinished(android.webkit.WebView view, String url) {
				LogcatUtils.e(TAG, "onPageFinished");
				if(bSucc){
					gotoSuccessful();
				}else {
					gotoError();
				}
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageCommitVisible(android.webkit.WebView view, String url) {
				LogcatUtils.e(TAG, "onPageCommitVisible");
				super.onPageCommitVisible(view, url);
			}

			@Override
			public void onReceivedSslError(android.webkit.WebView view,
					SslErrorHandler handler, SslError error) {
				LogcatUtils.e(TAG, "onReceivedSslError");
				bSucc = false;
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(
					android.webkit.WebView view, String url) {
				if (url.contains(BACK_FLAG)) {
					if(mContext instanceof Activity) {
						((Activity) mContext).finish();
					}
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		webView.setWebChromeClient(new MWebChromeClient(webView, mContext) {
			@Override
			public void onReceivedTitle(android.webkit.WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (mCallback != null) {
					mCallback.onReceivedTitle(view, title);
				}
			}
		});
	}

	@Override
	protected void onApplyData() {
		super.onApplyData();
		webView.setDownloadPicOnLoad(false);
		webView.setBackgroundColor(Color.parseColor("#00000000"));
//		webView.setBackgroundResource(R.drawable.bg);
		webView.getSettings().setTextSize(TextSize.NORMAL);
	}

	@Override
	public void onApplyLoadingData() {
		super.onApplyLoadingData();
		webView.reload();
	}
	
	@JavascriptInterface
	public void goback(){
		if(mContext instanceof Activity) {
			((Activity) mContext).finish();
		}
	}

    public void destroy() {
    	webView.destroy();
    }
	
	public void loadUrl(String url) {
		webView.loadUrl(url);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public interface IWebViewCallback {
		void onReceivedTitle(android.webkit.WebView webview, String title);
	}

}
