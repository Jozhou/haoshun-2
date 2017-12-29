package com.goodoil.aft.view.popup;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.corelibrary.utils.DeviceUtils;
import com.goodoil.aft.R;
import com.goodoil.aft.models.adapter.ConversationItemAdapter;
import com.goodoil.aft.models.adapter.ConversationSelItemAdapter;
import com.goodoil.aft.models.entry.ConversationDetailItem;

import java.util.List;

public class PopShowSel extends PopupWindow implements OnClickListener {
	private Context mContext;
	private View mContentView;
	private ListView lvSel;
	private View vContent;

	private ConversationSelItemAdapter mAdapter;

	public PopShowSel(Context context) {
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		mContentView = mInflater.inflate(R.layout.pop_conversation_sel, null);
		vContent = mContentView.findViewById(R.id.v_content);
		lvSel = (ListView) mContentView.findViewById(R.id.lv_sel);

		vContent.setOnClickListener(this);

		setWindowLayoutMode(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setOutsideTouchable(true);
		setAnimationStyle(R.style.pop_show_pic);
		setContentView(mContentView);

		mAdapter = new ConversationSelItemAdapter(mContext);
		lvSel.setAdapter(mAdapter);

		mAdapter.setOnSelItemListener(new ConversationSelItemAdapter.OnSelItemListener() {
			@Override
			public void onSleItem(int pos, String key, boolean change) {
				dismiss();
				if (change) {
					if (onSelItemListener != null) {
						onSelItemListener.onSleItem(pos, key, true);
					}
				}
			}
		});
	}

	public void setData(List<ConversationDetailItem.DetailItem> items, int curPos, String key) {
		mAdapter.setData(curPos, items, key);
		showAtLocation(((FragmentActivity)mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.v_content) {
			dismiss();
		}
	}

	private OnSelItemListener onSelItemListener;

	public void setOnSelItemListener(OnSelItemListener onSelItemListener) {
		this.onSelItemListener = onSelItemListener;
	}

	public interface OnSelItemListener {
		void onSleItem(int pos, String key, boolean change);
	}

}
