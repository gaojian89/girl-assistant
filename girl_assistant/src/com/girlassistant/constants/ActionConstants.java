/**
 * File: ActivityConstants.java
 * Description: 
 * Date: 2012-3-14����1:08:54 
 * @author Administrator
 * Email: yuanlei@people.cn
 */
package com.girlassistant.constants;

import com.girlassistant.base.FileCache;

public class ActionConstants {

	public static final String HALF_HOUR = "half_hour";
	public static final String FIVE_MINUTE = "five_minute";
	public static final String TEN_MINUTE = "ten_minute";
	public static final String ONE_HOUR = "one_hour";
	public static final String SIX_HOUR = "six_hour";
	public static final String TWELVE_HOUR = "twelve_hour";
	public static final String THREE_HOUR = "three_hour";
	public static final String FIVE_DAY = "five_day";
	public static final long INTERVAL_ONE_HOUR = 3600000;// 1小时
	public static final long INTERVAL_HALF_HOUR = 1800000;// 半小时
	public static final long INTERVAL_FIVE_MINUTE = 300000;// 5分钟
	public static final long INTERVAL_SIX_HOUR = 3600000 * 6;// 6小时
	public static final long INTERVAL_TWELVE_HOUR = 3600000 * 12;// 12小时
	public static final long INTERVAL_FIVE_DAY = 3600000 * 24 * 5;// 5天

	public static final String IMAGE_DOWNLOAD_FILE_PATH;
	static {
		IMAGE_DOWNLOAD_FILE_PATH = FileCache.getSystemCameraDirectory();
	}

}
