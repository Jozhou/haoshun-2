package com.goodoil.aft.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.corelibrary.utils.DeviceUtils;

import java.util.List;

public class AdAdapter extends BaseAdapter {

	public static final float AD_RATIO = 9.0f/16;

	private LayoutInflater mInflater;
	private List<String> mData;
	private Context mContext;

	public AdAdapter(Context context, List<String> data) {
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mData = data;
	}

	@Override
	public int getCount() {
		if (mData == null || mData.isEmpty()) {
			return 0;
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public String getItem(int position) {
		return mData.get(position % mData.size());
	}

	@Override
	public long getItemId(int position) {
		return position % mData.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_ad, null);
			holder.ivAd = (ImageView) convertView.findViewById(R.id.iv_ad);
			int width = DeviceUtils.getScreenWidth();
			int height = (int) (width * AD_RATIO);
			ViewGroup.LayoutParams params = holder.ivAd.getLayoutParams();
			params.width = width;
			params.height = height;
			holder.ivAd.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Glide.with(mContext).load(getItem(position)).dontAnimate().into(holder.ivAd);
		return convertView;
	}

	static class ViewHolder {
		ImageView ivAd;
	}

}
