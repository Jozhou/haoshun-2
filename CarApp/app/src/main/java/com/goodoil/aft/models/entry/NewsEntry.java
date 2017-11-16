package com.goodoil.aft.models.entry;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.corelibrary.models.entry.BaseEntry;

/**
 * Created by Administrator on 2017/10/13.
 */
public class NewsEntry extends BaseEntry implements MultiItemEntity {

    public int news_id;
    public String title = "";
    public String imgurl = "";
    public String create_date = "";
    public int clickamount;
    public String url = "";

    @Override
    public int getItemType() {
        return 0;
    }
}
