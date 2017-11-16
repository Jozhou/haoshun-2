package com.goodoil.aft.view.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.NewsEntry;
import com.goodoil.aft.models.operater.NewsCollectOperater;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.ButtonUtils;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.view.WebView;

public class NewsWebView extends WebView implements View.OnClickListener {

	private View vPraise;
	private TextView tvPraise;

	private NewsEntry newsEntry;

	public NewsWebView(Context context) {
		super(context);
	}

	public NewsWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewsWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.view_webview_news;
	}

	public void setNewsEntry(NewsEntry newsEntry) {
		this.newsEntry = newsEntry;
		tvPraise.setText(newsEntry.clickamount + "");
	}

	@Override
	protected void onFindView() {
		super.onFindView();
		vPraise = findViewById(R.id.ll_praise);
		tvPraise = (TextView) findViewById(R.id.tv_praise);
		vPraise.setOnClickListener(this);
	}

	@Override
	protected void onApplyData() {
		super.onApplyData();
	}

	@Override
	public void onClick(View v) {
		if (ButtonUtils.isFastDoubleClick()) {
			return;
		}
		int id = v.getId();
		if (id == R.id.ll_praise) {
			final NewsCollectOperater operater = new NewsCollectOperater(mContext);
			operater.setParams(newsEntry.news_id);
			operater.onReq(new BaseOperater.RspListener() {
				@Override
				public void onRsp(boolean success, Object obj) {
					if (success) {
						DialogUtils.showToastMessage(operater.getMsg());
//						newsEntry.clickamount = newsEntry.clickamount + 1;
						tvPraise.setText(operater.getCollectetimes());
					}
				}
			});
		}
	}
}
