package com.goodoil.aft.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.ConversationDetailItem.DetailItem;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class ConversationSelItemAdapter extends BaseAdapter {

    private List<DetailItem> mData;
    private String key;
    LayoutInflater mInflater;

    private int mSelPos = 0;

    public ConversationSelItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(int curPos, List<DetailItem> data, String key) {
        this.mSelPos = curPos;
        this.mData = data;
        this.key = key;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_conversation, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DetailItem entry = mData.get(position);
        viewHolder.tvName.setText(entry.name);
        if (position == mSelPos) {
            viewHolder.ivCheck.setImageResource(R.drawable.addmoreradiobtn);
        } else {
            viewHolder.ivCheck.setImageResource(R.drawable.addmoreradio);
        }

        viewHolder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelItemListener != null) {
                    onSelItemListener.onSleItem(position, key, position != mSelPos);
                }
            }
        });

        return convertView;
    }

    public DetailItem getSelItem() {
        return this.mData.get(mSelPos);
    }

    public void setSelPos(int pos) {
        this.mSelPos = 0;
    }

    static class ViewHolder {
        TextView tvName;
        ImageView ivCheck;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            ivCheck = (ImageView) view.findViewById(R.id.iv_check);
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
