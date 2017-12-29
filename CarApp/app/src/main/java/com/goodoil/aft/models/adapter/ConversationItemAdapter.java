package com.goodoil.aft.models.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibrary.utils.DialogUtils;
import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.ConversationDetailItem;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.goodoil.aft.view.popup.PopShowPic;
import com.goodoil.aft.view.popup.PopShowSel;

import java.util.List;

public class ConversationItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ConversationDetailItem> mData;
	private Context mContext;
	private PopShowPic popShowPic;
	private PopShowSel popShowSel;

	public ConversationItemAdapter(Context context) {
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public ConversationItemAdapter(Context context, List<ConversationDetailItem> data) {
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mData = data;
		this.popShowPic = new PopShowPic(context);
		this.popShowSel = new PopShowSel(context);

		this.popShowSel.setOnSelItemListener(new PopShowSel.OnSelItemListener() {
			@Override
			public void onSleItem(int pos, String key, boolean change) {
				for (int i = 0; i < mData.size(); i ++) {
					ConversationDetailItem conversationDetailItem = mData.get(i);
					if (conversationDetailItem.isHeader) {
						if (key.equals(conversationDetailItem.key)) {
							conversationDetailItem.curItem = pos;
						}
					} else {
						if (key.equals(conversationDetailItem.parent)) {
							conversationDetailItem.curItem = pos;
						}
					}
				}
				float price = ConversationWrapper.getTotalPrice(mData);
				mData.get(mData.size() - 1).items.get(0).name = price + "";
				notifyDataSetChanged();
			}
		});
	}

	public void setData(List<ConversationDetailItem> data) {
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
	public ConversationDetailItem getItem(int position) {
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
			holder.vArrow = convertView.findViewById(R.id.rl_arror);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ConversationDetailItem entry = getItem(position);
		holder.tvName.setText(entry.key);
		final ConversationDetailItem.DetailItem detailItem = entry.items.get(entry.curItem);
		holder.tvValue.setText(detailItem.name);

		if (entry.isHeader && entry.items.size() > 1) {
			holder.ivArrow.setVisibility(View.VISIBLE);
			holder.vArrow.setClickable(true);
			holder.vArrow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					popShowSel.setData(entry.items, entry.curItem, entry.key);
				}
			});
		} else {
			holder.ivArrow.setVisibility(View.GONE);
			holder.vArrow.setClickable(false);
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(detailItem.img)) {
					popShowPic.setData(detailItem.img);
				}
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView tvName;
		TextView tvValue;

		ImageView ivArrow;
		View vArrow;
	}

}
