package com.corelibrary.models.entry;

import java.util.ArrayList;

public class ArrayEntry<T> extends BaseEntry {

	private static final long serialVersionUID = 3776599349241014151L;
	
	protected int totalCount = -1;
	protected ArrayList<T> array;
	protected int index = 0;

	public void setTotalCount(int v) {
		totalCount = v;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public ArrayEntry() {
		array = new ArrayList<T>();
	}
	
	public ArrayList<T> getArray() {
		return array;
	}
	
	/**
	 * 是否为最后一页
	 * @return
	 */
	public boolean isLastPage() {
		return index >= totalCount;
	}
	
}
