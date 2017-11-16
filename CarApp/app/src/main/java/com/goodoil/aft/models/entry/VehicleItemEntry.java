package com.goodoil.aft.models.entry;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.corelibrary.models.entry.BaseEntry;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by Administrator on 2017/10/9.
 */

public class VehicleItemEntry extends BaseEntry implements MultiItemEntity, IndexableEntity {

    public String id = "";
    public String name = "";
    public String number = "";
    public String carcode = "";

    private String pinyin;

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;

    }
}
