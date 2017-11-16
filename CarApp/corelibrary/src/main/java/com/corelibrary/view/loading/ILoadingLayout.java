package com.corelibrary.view.loading;

public interface ILoadingLayout {

	void gotoLoading();
	void gotoLoading(boolean delay);
	void gotoSuccessful();
	void gotoBlank();
	void gotoError();
	void onApplyLoadingData();
	
}
