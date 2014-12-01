package com.girlassistant.utils;

import java.util.List;

import android.text.TextUtils;

public class CheckUtils {
	
	/**
	 * @author gaoj
	 * */

	public static boolean isEmptyList(List arg) {

		if (arg != null && arg.size() > 0) {
			return false;
		}
		return true;
	}
	
	public static boolean isNoEmptyList(List arg) {
		return !isEmptyList(arg);
	}

	public static boolean isEmptyStr(String str) {
		if (TextUtils.isEmpty(str)) {
			return true;
		}
		// 过滤空格
		str=str.trim();
		if (str.equals(" ") || str.equals("")||"NULL".equals(str)||"null".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNoEmptyStr(String str) {
		return !isEmptyStr(str);
	}

}
