package com.goodoil.aft.models.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.CityItemEntry;

import java.util.List;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;

/**
 * Created by Administrator on 2017/11/2.
 */

public class GpsHeaderAdapter extends IndexableHeaderAdapter<CityItemEntry> {

    private LayoutInflater mInflater;

    public GpsHeaderAdapter(Context context, String index, String indexTitle, List datas) {
        super(index, indexTitle, datas);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType() {
        return 1001;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_city, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final CityItemEntry entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tv.setText(entity.province_name);
        vh.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(entity);
                }
            }
        });
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;

        public ContentVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    private CityAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CityAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CityItemEntry entry);
    }
}
