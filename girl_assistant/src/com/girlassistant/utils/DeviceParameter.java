package com.girlassistant.utils;

import java.lang.reflect.Field;

import com.girlassistant.base.App;
import com.girlassistant.base.DeviceTypes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;

/**
 * 适配
 * 
 * @author gaoj
 */
public class DeviceParameter {
	private static DeviceTypes deviceType = null;
	private static String deviceId = null;
	private static float xdpi = -1f;

	/**
	 * @author penghaitao.misa Date 2011-2-16
	 */
	public static DisplayMetrics getDisplayMetrics() {
		return App.getInstance().getResources().getDisplayMetrics();
	}

	public static void initDisplayHeight(Activity activity) {
		int statusBarHeight = 0;
		int height = 0;
		int width = 0;
		Window window = activity.getWindow();
		Class<?> c;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = activity.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DisplayMetrics dm = getDisplayMetrics();
		height = dm.heightPixels;
		width = dm.widthPixels;

		MLog.i("initDisplayHeight height=" + height + ",width=" + width);
		Rect rect = new Rect();
		window.getDecorView().getWindowVisibleDisplayFrame(rect);
		if (rect.top == 0 && getDeviceType() != DeviceTypes.NORMAL) {
			height = height + statusBarHeight;
		} else if (((rect.top + rect.bottom) < height) || ((rect.top + rect.bottom) > height && getDeviceType() == DeviceTypes.XLARGE)) {
			statusBarHeight += height - rect.bottom;
		}
		if (statusBarHeight <= 0) {
			if (isDeviceXLarge()) {
				statusBarHeight = 48;
			}
			height = height + statusBarHeight;
		}
		PreferenceUtils.saveStatusBarHeight(statusBarHeight, activity);
		if (DeviceParameter.isDeviceXLarge()) {
			if (height > width) {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT_XLARGE, height);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH_XLARGE, width);
			} else {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT_XLARGE, width);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH_XLARGE, height);
			}
		} else {
			if (height > width) {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT, height);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH, width);
			} else {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT, width);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH, height);
			}
		}
	}

	public static float getXDPI() {
		if (xdpi == -1f) {
			DisplayMetrics dm = getDisplayMetrics();
			xdpi = dm.xdpi;
		}
		return xdpi;
	}

	public static int getStatusBarHeight(Context context) {
		return PreferenceUtils.getStatusBarHeight(context);
	}

	public static DeviceTypes getDeviceType() {
		if (deviceType != null) {
			return deviceType;
		}
		App appContext = App.getInstance();
		if ((appContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			deviceType = DeviceTypes.XLARGE;
		} else if ((appContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			deviceType = DeviceTypes.LARGE;
		} else {
			deviceType = DeviceTypes.NORMAL;
		}
		return deviceType;
	}

	// api调用devicetype参数
	public static int getDeviceTypeInt() {
		if (getDeviceType() == DeviceTypes.XLARGE) {
			return 2;
		} else {
			return 5;
		}
	}

	public static int getDisplayWidth(Context context, int orientation) {
		int width = 0;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			width = getPhysicalWidth(context);
		} else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			width = getPhysicalHeight(context);
		}
		return width;
	}

	public static int getDisplayHeight(Context context, int orientation) {
		int height = 0;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			height = getPhysicalHeight(context) - getStatusBarHeight(context);
		} else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			height = getPhysicalWidth(context) - getStatusBarHeight(context);
		}
		return height;
	}

	public static int getPhysicalWidth(Context context) {
		if (DeviceParameter.isDeviceXLarge()) {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_WIDTH_XLARGE, 0, context);
		} else {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_WIDTH, 0, context);
		}
	}

	public static int getPhysicalHeight(Context context) {
		if (DeviceParameter.isDeviceXLarge()) {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_HEIGHT_XLARGE, 0, context);
		} else {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_HEIGHT, 0, context);
		}
	}

	public static float getScreenHeight() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.heightPixels;
	}

	public static float getScreenWidth() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.widthPixels;
	}

	public static int getIntScreenWidth() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.widthPixels;
	}

	public static float getDensity() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.density;
	}

	public static String getDeviceId() {
		if (deviceId != null) {
			return deviceId;
		}
		Context appContext = App.getInstance();
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = telephonyManager.getDeviceId();
		if (TextUtils.isEmpty(deviceId)) {
			deviceId = System.getString(appContext.getContentResolver(), Secure.ANDROID_ID);
		}
		return deviceId;
	}

	public static String getAndroid_ID() {
		return System.getString(App.getInstance().getContentResolver(), Secure.ANDROID_ID);
	}

	public static String getIMEI() {
		Context appContext = App.getInstance();
		return ((TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public static String getIMSI() {
		Context appContext = App.getInstance();
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSubscriberId();
	}

	public static boolean isDeviceXLarge() {
		return getDeviceType() == DeviceTypes.XLARGE;
	}

	public static String getProvidersName() {
		Context appContext = App.getInstance();
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		String ProvidersName = null;
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。

		if (IMSI != null) {
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				ProvidersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "中国电信";
			}
		} else {
			ProvidersName = "未知";
		}

		return ProvidersName;
	}

	public static String getNetWorkType() {
		Context appContext = App.getInstance();
		ConnectivityManager cm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm != null) {
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				int networkInfoType = info.getType();
				if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
					return "wifi";
				} else if (networkInfoType == ConnectivityManager.TYPE_MOBILE) {
					return "mobile";
				}

			}

		}
		return "wifi";
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
