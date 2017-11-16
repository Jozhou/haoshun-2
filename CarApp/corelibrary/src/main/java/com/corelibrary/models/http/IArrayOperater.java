package com.corelibrary.models.http;

import com.corelibrary.models.entry.ArrayEntry;

public interface IArrayOperater<T> {

	ArrayEntry<T> getDataEntry();
	boolean getFirstPage(boolean loadcache, BaseOperater.RspListener callback);
	void getNextPage(BaseOperater.RspListener callback);
	boolean getCacheData(BaseOperater.RspListener callback);
	
	public static enum PageType {
		CachePage,
		FirstPage,
		NextPage
	}
	
}
