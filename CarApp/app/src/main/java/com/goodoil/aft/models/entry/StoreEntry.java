package com.goodoil.aft.models.entry;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.corelibrary.models.entry.BaseEntry;

/**
 * Created by Administrator on 2017/10/13.
 */
public class StoreEntry extends BaseEntry implements MultiItemEntity {

    public String shopid = "";
    public String shopname = "";
    public String image = "";
    public int evolution;
    public String businessstart = "";
    public String businessend = "";
    public String tel = "";
    public String shoploacl = "";
    public String distance = "";
    public String url = "";
    public String shoplon = "";
    public String shoplat = "";

    public String businessTime = "";

    @Override
    public int getItemType() {
        return 0;
    }
}
