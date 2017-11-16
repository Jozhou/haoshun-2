package com.goodoil.aft.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.VehicleQueryEntry;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class VehicleQueryItemAdapter extends BaseAdapter {

    private List<VehicleQueryEntry> mData;
    LayoutInflater mInflater;

    private int mSelPos = 0;

    public VehicleQueryItemAdapter(Context context, List<VehicleQueryEntry> data) {
        this.mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.item_vehicle_carcode, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        VehicleQueryEntry entry = mData.get(position);
        viewHolder.tvDetail.setText(entry.detail);
        if (position == mSelPos) {
            viewHolder.ivCheck.setImageResource(R.drawable.addmoreradiobtn);
        } else {
            viewHolder.ivCheck.setImageResource(R.drawable.addmoreradio);
        }

        viewHolder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != mSelPos) {
                    mSelPos = position;
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    public VehicleQueryEntry getSelItem() {
        return this.mData.get(mSelPos);
    }

    public void setSelPos(int pos) {
        this.mSelPos = 0;
    }

    static class ViewHolder {
        TextView tvDetail;
        ImageView ivCheck;

        public ViewHolder(View view) {
            tvDetail = (TextView) view.findViewById(R.id.tv_carcode);
            ivCheck = (ImageView) view.findViewById(R.id.iv_check);
        }
    }
}
