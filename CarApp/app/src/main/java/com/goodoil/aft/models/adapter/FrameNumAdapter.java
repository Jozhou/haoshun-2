package com.goodoil.aft.models.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.goodoil.aft.view.popup.PopShowPic;

import java.util.List;

public class FrameNumAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<NameValueEntry> mData;
	private Context mContext;
	private PopShowPic popShowPic;

	public FrameNumAdapter(Context context) {
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public FrameNumAdapter(Context context, List<NameValueEntry> data) {
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mData = data;
		this.popShowPic = new PopShowPic(context);
	}

	public void setData(List<NameValueEntry> data) {
		this.mData = data;
	}

	@Override
	public int getCount() {
		if (mData == null) {
			return 0;
		}
		return mData.size();
	}

	@Override
	public NameValueEntry getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_frame_num, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
			holder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_arror);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final NameValueEntry entry = getItem(position);
		holder.tvName.setText(entry.name);
		holder.tvValue.setText(entry.value);

		if (entry.name.equals(mContext.getString(R.string.oil_model)) && !TextUtils.isEmpty(entry.image)) {
			holder.ivArrow.setVisibility(View.VISIBLE);
			convertView.setClickable(true);
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(mContext, OilModelActivity.class);
//					intent.putExtra(IntentCode.INTENT_OIL_MODEL, entry.image);
//					mContext.startActivity(intent);
					popShowPic.setData(entry.image);
				}
			});

		} else {
			holder.ivArrow.setVisibility(View.GONE);
			convertView.setClickable(false);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tvName;
		TextView tvValue;

		ImageView ivArrow;
	}

}
