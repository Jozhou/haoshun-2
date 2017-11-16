package com.goodoil.aft.utils.location;

/**
 * 定位方式
 * @author malijun
 *
 */
public enum GpsMode {

	/**
	 * 无gps
	 */
	none(0),
	/**
	 * gps
	 */
	gps(1);


	private int value;

	private GpsMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static GpsMode fromValue(int value) {
		switch (value) {
			case 1:
				return gps;
			case 0:
			default:
				return none;
		}
	}

}
