package com.corelibrary.view.layoutview;


public interface ILayoutView<T> {
	
	void setPosition(int position, int total);
	
	void setDataSource(T t);
	
	T getDataSource();
	
	void notifyDataSetChanged();

}
