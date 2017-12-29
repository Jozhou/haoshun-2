package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.common.data.Account;
import com.goodoil.aft.models.adapter.ConversationWrapper;
import com.goodoil.aft.models.entry.ConversationDetailItem;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */

public class GetConversationDetailOperater extends BaseOperater {

    private List<ConversationDetailItem> mData;

    public GetConversationDetailOperater(Context context) {
        super(context);
    }

    public void setParams(String carcode) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("carcode", carcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/vehicle/maintenance";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
        mData = new ArrayList<>();
        try {
            JSONArray array = jsonObject.getJSONObject("content").getJSONArray("maintenance");
            for(int i = 0; i < array.length(); i ++) {
                ConversationDetailItem conversationDetailItem = new ConversationDetailItem();
                JSONObject obj = array.getJSONObject(i);
                conversationDetailItem.key = obj.getString("key");

                List<ConversationDetailItem.DetailItem> values = new ArrayList<>();
                JSONArray valueArray = obj.getJSONArray("value");
                for (int j = 0; j < valueArray.length(); j ++) {
                    ConversationDetailItem.DetailItem detailItem = new ConversationDetailItem.DetailItem();
                    JSONObject valueObj = valueArray.getJSONObject(j);
                    if (!valueObj.isNull("name")) {
                        detailItem.name = valueObj.optString("name");
                    }
                    if (!valueObj.isNull("img")) {
                        detailItem.img = valueObj.optString("img");
                    }
                    values.add(detailItem);
                }
                conversationDetailItem.items = values;
                mData.add(conversationDetailItem);

                JSONArray itemsArray = obj.optJSONArray("items");
                if (itemsArray == null) {
                    continue;
                }
                for (int k = 0; k < itemsArray.length(); k ++) {
                    JSONObject itemJsonObj = itemsArray.getJSONObject(k);
                    ConversationDetailItem conversationDetailItem1 = new ConversationDetailItem();
                    conversationDetailItem1.key = itemJsonObj.getString("key");
                    conversationDetailItem1.isHeader = false;
                    conversationDetailItem1.parent = conversationDetailItem.key;

                    List<ConversationDetailItem.DetailItem> values1 = new ArrayList<>();
                    JSONArray valueArray1 = itemJsonObj.getJSONArray("value");
                    for (int j = 0; j < valueArray1.length(); j ++) {
                        ConversationDetailItem.DetailItem detailItem = new ConversationDetailItem.DetailItem();
                        JSONObject valueObj = valueArray1.getJSONObject(j);
                        if (!valueObj.isNull("name")) {
                            detailItem.name = valueObj.optString("name");
                        }
                        if (!valueObj.isNull("img")) {
                            detailItem.img = valueObj.optString("img");
                        }
                        values1.add(detailItem);
                    }
                    conversationDetailItem1.items = values1;
                    mData.add(conversationDetailItem1);
                }
            }
            if (!mData.isEmpty()) {
                ConversationDetailItem item = new ConversationDetailItem();
                item.key = "总费用";
                List<ConversationDetailItem.DetailItem> detailItems = new ArrayList<>();
                ConversationDetailItem.DetailItem detailItem = new ConversationDetailItem.DetailItem();
                detailItem.name = ConversationWrapper.getTotalPrice(mData) + "";
                detailItems.add(detailItem);
                item.items = detailItems;
                mData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {
    }

    public List<ConversationDetailItem> getData() {
        return this.mData;
    }

}
