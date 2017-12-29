package com.goodoil.aft.models.entry;

import com.corelibrary.models.entry.BaseEntry;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class ConversationDetailItem extends BaseEntry {

    public String key = "";
    public boolean isHeader = true;
    public List<DetailItem> items;
    public String parent = "";
    public int curItem = 0;

    public static class DetailItem extends BaseEntry {
        public String name = "";
        public String img = "";

    }
}
