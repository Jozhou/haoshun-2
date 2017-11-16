package com.corelibrary.view;

import android.support.v4.view.PagerAdapter;

public final class VerticalViewPagerCompat {
    private VerticalViewPagerCompat() {
    }

    public static void setDataSetObserver(PagerAdapter adapter, DataSetObserver observer) {
    	if(observer != null){    		
    		adapter.registerDataSetObserver(observer);
    	}
    }
    
    public static void unregisterDataSetObserver(PagerAdapter adapter, DataSetObserver observer) {
    	if(observer != null){  
    		adapter.unregisterDataSetObserver(observer);  
        }  
    }
    
    public static class DataSetObserver extends android.database.DataSetObserver {
    }

}
