package com.goodoil.aft.context;

import com.goodoil.aft.R;
import com.corelibrary.application.AppContext;

public class Config extends com.corelibrary.context.Config {
	
	private static final String KEY_IS_DEBUG = "IS_DEBUG";
	private static final String KEY_IS_STORELOG = "IS_STORELOG";

	public static final String COUNTRY_CODE = "86";
	public static final int HEAD_WIDTH = AppContext.get().getResources().getDimensionPixelSize(R.dimen.dd_dimen_96px);

	public static final int HEAD_FOCUS_WIDTH = AppContext.get().getResources().getDimensionPixelSize(R.dimen.dd_dimen_400px);
	
	/**   
	 * 开启debug
	 */
	public static boolean IS_DEBUG = true;
	/**   
	 * 是否存储日志
	 */
	public static boolean IS_STORELOG = true;
	
	private static String API_URL = "http://47.92.150.165:7433";

	private static String IMG_URL = "http://223.202.60.138:8030/teaching/static/upload/images/";

	private static String VIDEO_URL = "http://223.202.60.138:8030/video/";

	public static String CONVERSATION_KNOW = "http://47.92.150.165:7433/APP/curing";
	public static String PROJECT_INTRO = "http://47.92.150.165:7433/APP/introduce";
	public static String SERVICE_ITEM = "http://47.92.150.165:7433/APP/clause";
	public static String ABOUT_US = "http://47.92.150.165:7433/APP/AboutUs";
	public static String FEEDBACK = "http://47.92.150.165:7433/APP/feedback";

	/**
	 * 获取url请求的base地址
	 * @return
	 */
	public static String getBaseUrl() {
		return API_URL;
	}

	/**
	 * 获取图片地址
	 * @return
	 */
	public static String getImageUrl() {
		return IMG_URL;
	}

	/**
	 * 获取影像地址
	 * @return
	 */
	public static String getVideoUrl() {
		return VIDEO_URL;
	}

	/**
	 * 设置开启debug模式
	 * @param v
	 */
	public static void setIsDebug(boolean v) {
		IS_DEBUG = v;
		IS_STORELOG = v;
	}
	
}
