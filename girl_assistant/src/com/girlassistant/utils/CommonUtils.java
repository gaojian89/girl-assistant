package com.girlassistant.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import com.girlassistant.base.App;
import com.girlassistant.constants.ActionConstants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class CommonUtils {

	public static boolean isSupportSinaSSO(Context context) {
		Intent intent = new Intent("com.sina.weibo.remotessoservice");
		List<ResolveInfo> list = context.getPackageManager().queryIntentServices(intent, PackageManager.GET_INTENT_FILTERS);
		return list.size() > 0;
	}

	public static synchronized boolean isNetworkConnected() {
		boolean isConnected = false;

		Context context = App.getInstance();
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						isConnected = true;
					}
				}
			}
		}
		return isConnected;

	}

	public static synchronized boolean isWifiConnected() {
		ConnectivityManager connManager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager != null) {
			NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				int networkInfoType = networkInfo.getType();
				if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
					return networkInfo.isConnected();
				}
			}
		}
		return false;
	}

	public static boolean isMobileNetworkConnected() {
		ConnectivityManager connManager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager != null) {
			NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				int networkInfoType = networkInfo.getType();
				if (networkInfoType == ConnectivityManager.TYPE_MOBILE) {
					return networkInfo.isConnected();
				}
			}
		}
		return false;
	}

	public static boolean isAllowOfflineDownload(int networkType) {
		switch (networkType) {
		case ConnectivityManager.TYPE_ETHERNET:
		case ConnectivityManager.TYPE_WIFI:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 获取渠道名称
	 */
	public static String getChannelFromManifest() {
		try {
			Context context = App.getInstance();
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("CHANNEL");
		} catch (Exception e) {
		}
		return null;
	}

	public static int getCurrentVersionCode() {
		try {
			App app = App.getInstance();
			PackageManager packageManager = app.getPackageManager();
			String packageName = app.getPackageName();
			PackageInfo info = packageManager.getPackageInfo(packageName, 0);
			return info.versionCode;
		} catch (Exception e) {
		}
		return 1;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取当前应用版本名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentVersionName() {
		try {
			App app = App.getInstance();
			PackageManager packageManager = app.getPackageManager();
			String packageName = app.getPackageName();
			PackageInfo info = packageManager.getPackageInfo(packageName, 0);
			return info.versionName;
		} catch (Exception e) {
			return "4.0.0";
		}

	}

	public static void hideKeyBoard(Activity activity) {
		if (activity.getCurrentFocus() != null) {
			((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void setFullscreen(Activity activity, boolean on) {
		if (activity != null) {
			Window win = activity.getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
			if (on) {
				winParams.flags |= bits;
			} else {
				winParams.flags &= ~bits;
			}
			win.setAttributes(winParams);
		}
	}

	public static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		win.setAttributes(winParams);
	}

	public static void setTranslucentNavigation(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		win.setAttributes(winParams);
	}

	// 没有2g 3g判断，手机网络统一为2
	public static int getNetworkType() {
		int type = 0;
		if (isWifiConnected()) {
			type = 1;
		} else if (isMobileNetworkConnected()) {
			type = 2;
		}
		return type;
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return "";
	}

	/**
	 * 判断时间是否超时
	 * 
	 * @param lastUpdate
	 * @return
	 */
	public static boolean isTimeOut(long lastUpdate, String flag) {
		if (lastUpdate == 0) {
			return true;
		}
		long time = new Date().getTime();
		long interval = time - lastUpdate;
		if (ActionConstants.HALF_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_HALF_HOUR) {// 超过半小时
			return true;
		} else if (ActionConstants.FIVE_MINUTE.equals(flag) && interval > ActionConstants.INTERVAL_FIVE_MINUTE) {// 超过5分钟
			return true;
		} else if (ActionConstants.ONE_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_ONE_HOUR) {// 超过1小时
			return true;
		} else if (ActionConstants.SIX_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_SIX_HOUR) {// 超过6小时
			return true;
		} else if (ActionConstants.TWELVE_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_TWELVE_HOUR) {// 超过12小时
			return true;
		}
		return false;
	}

	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		String date = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		return date;
	}

	public static String getFormatDate(String date, String format, String parser) {
		try {
			Date d = new SimpleDateFormat(parser).parse(date);
			date = new SimpleDateFormat(format).format(d);
		} catch (Exception e) {
		}
		return date;
	}

	public static ArrayList<String[]> transStr2Points(String pointsStr) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		if (!CheckUtils.isEmptyStr(pointsStr)) {
			String[] pointsArr = pointsStr.split(";");
			for (String point : pointsArr) {
				list.add(point.split(","));
			}
		}
		return list;
	}

	public static String transPoints2Str(List<String[]> points) {
		String pointStr = "";
		if (!CheckUtils.isEmptyList(points)) {
			StringBuilder sb = new StringBuilder();
			for (String[] pointArr : points) {
				if (pointArr.length == 2) {
					sb.append(pointArr[0]).append(",").append(pointArr[1]).append(";");
				}
			}
			pointStr = sb.substring(0, sb.length() - 1);
		}
		return pointStr;
	}

	/**
	 * 检查版本号是否最新
	 * 
	 * @param updateCode
	 * @param act
	 * @return
	 */
	public static boolean isVersionInvalid(String updateCode, Activity act) {
		if (CheckUtils.isEmptyStr(updateCode)) {
			return false;
		}
		List<PackageInfo> list = act.getPackageManager().getInstalledPackages(0);
		int versionCode = 0;
		for (int i = 0; i < list.size(); i++) {
			PackageInfo packageInfo = list.get(i);

			if (packageInfo.packageName.equals(getPackageName())) {
				versionCode = packageInfo.versionCode;
				break;
			}
		}
		if (String.valueOf(versionCode).compareTo(updateCode) <= -1) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取应用包名
	 * 
	 * @return
	 */
	public static String getPackageName() {
		PackageInfo info;
		try {
			info = App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0);
			String packageNames = info.packageName;
			return packageNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
