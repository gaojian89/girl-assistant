package com.girlassistant.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;

import com.baidu.frontia.FrontiaApplication;

/**
 * 应用程序全局信息 存放全局变量
 */
public class App extends FrontiaApplication {

	private List<WeakReference<Activity>> mActivities = new LinkedList<WeakReference<Activity>>();

	private HashMap<String, Object> cache = new HashMap<String, Object>();
	private static App instance = null;
	public static final String KEY_SYSTEM_MAX_MEMORY = "system_max_memory";
	public static final String KEY_COVER_IMAGE_PATH = "CoverImagePath";
	private Map<String, Long> newsInterval = new HashMap<String, Long>();
	private boolean isAppRunning;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public WeakReference<Activity> addActivity(Activity activity) {
		synchronized (mActivities) {
			WeakReference<Activity> reference = new WeakReference<Activity>(activity);
			mActivities.add(reference);
			return reference;
		}
	}

	public int getCurrentRunningActivitySize() {

		return mActivities.size();
	}

	public String getTopActivity() {

		ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity).getClassName().toString();
		else
			return null;
	}

	public void removeActivity(WeakReference<Activity> reference) {
		synchronized (mActivities) {
			mActivities.remove(reference);
		}
	}

	public void exit() {
		synchronized (mActivities) {
			for (WeakReference<Activity> activityRef : mActivities) {
				Activity activity = activityRef.get();
				if (activity != null) {
					activity.finish();
				}
			}
			mActivities.clear();
		}
	}

	public static App getInstance() {
		if (instance == null) {
			throw new NullPointerException("app not create should be terminated!");
		}
		return instance;
	}

	public Object get(String key) {
		return cache.get(key);
	}

	public Object remove(String key) {
		return cache.remove(key);
	}

	public void put(String key, Object value) {
		cache.put(key, value);
	}

	public void clearCache() {
		cache.clear();
	}
	public void setItemNewsInterval(String key, long interval) {
		newsInterval.put(key, interval);
	}
	
	public void setNewsInterval(Map<String, Long> newsInterval) {
		this.newsInterval = newsInterval;
	}

	public int getSystemMaxMemory() {
		Integer memory = (Integer) cache.get(KEY_SYSTEM_MAX_MEMORY);
		if (memory == null) {
			return 16;
		}
		return memory;
	}

	public void saveSystemMaxMemory(int maxMemory) {
		cache.put(KEY_SYSTEM_MAX_MEMORY, maxMemory);
	}

	public synchronized void init() {
	}

	public boolean isAppRunning() {
		return isAppRunning;
	}

	public void setAppRunning(boolean isAppRunning) {
		this.isAppRunning = isAppRunning;
	}

	public String getCoverPath() {
		return (String) cache.get(KEY_COVER_IMAGE_PATH);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		exit();
		// DBConnectManager.closeDB();
		// DeviceParameter.destoryLocation(location);
		// Utils.setLogText(getApplicationContext(), Utils.logStringCache);
	}
}
