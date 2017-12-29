package com.goodoil.aft.models.adapter;

import android.text.TextUtils;

import com.goodoil.aft.models.entry.ConversationDetailItem;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ConversationWrapper {

    public static float getTotalPrice(List<ConversationDetailItem> items) {
        float total = 0;
        float workTime = 0f;
        float pricePerHour = 0f;
        float priceHuaqing = 0f;
        float priceTaojian = 0f;
        float oilQuality = 0f;
        float priceOil = 0f;

        for (int i = 0; i < items.size(); i ++) {
            ConversationDetailItem detailItem = items.get(i);
            String name = detailItem.items.get(detailItem.curItem).name;
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            name = name.replace("L", "");
            if (!isNumber(name)) {
                continue;
            }
            float value;
            try {
                value = Float.parseFloat(name);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            if (detailItem.key.equals("工时/h")) {
                workTime = value;
                continue;
            }
            if (detailItem.key.equals("工时费")) {
                pricePerHour = value;
                continue;
            }
            if (detailItem.key.equals("化清剂价格")) {
                priceHuaqing = value;
                continue;
            }
            if (detailItem.key.equals("套装价格")) {
                priceTaojian = value;
                continue;
            }
            if (detailItem.key.equals("机器更换推荐量(L)")) {
                oilQuality = value;
                continue;
            }
            if (detailItem.key.equals("油品价格")) {
                priceOil = value;
                continue;
            }
        }
        total = workTime * pricePerHour + priceHuaqing + priceTaojian + oilQuality * priceOil;
        return total;
    }

    public static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
