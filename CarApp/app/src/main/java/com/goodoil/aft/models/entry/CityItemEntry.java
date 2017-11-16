package com.goodoil.aft.models.entry;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.corelibrary.models.entry.BaseEntry;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CityItemEntry extends BaseEntry implements MultiItemEntity, IndexableEntity {

    public String province_id = "";
    public String province_name = "";

    private String pinyin;

    public CityItemEntry() {
    }

    public CityItemEntry(String province_name) {
        this.province_name = province_name;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public String getFieldIndexBy() {
        return province_name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.province_name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;

    }
}
