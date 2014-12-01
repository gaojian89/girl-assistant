package com.girlassistant.utils;

import com.girlassistant.base.App;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {

    public static final String CONFIG_FILE_NAME = "config";//config.xml

    public static final String STATUS_BAR_HEIGHT = "statusBarHeight";//状态栏高度
    public static final String PHYSICAL_HEIGHT = "physicalHeight";//物理高度
    public static final String PHYSICAL_WIDTH = "physicalWidth";//物理宽度

    public static final String STATUS_BAR_HEIGHT_XLARGE = "statusBarHeight_xlarge";
    public static final String PHYSICAL_HEIGHT_XLARGE = "physicalHeight_xlarge";
    public static final String PHYSICAL_WIDTH_XLARGE = "physicalWidth_xlarge";

    public static final String TOKEN_SINA = "token_sina";
    public static final String TOKEN_QQ = "token_qq";

    public static final String SCREEN_NAME_SINA = "screen_name_sina";
    public static final String AVATAR_SINA = "avatar_sina";
    public static final String SCREEN_NAME_QQ = "screen_name_qq";
    public static final String AVATAR_QQ = "avatar_qq";

    public static final String SYSTEM_BRIGHT = "system_bright";// 当前设置屏幕亮度
    public static final String BRIGHT_LEVEL = "bright_level";// 当前设置屏幕亮度
    public static final String THEME_SETTING="theme_setting";//主题设置
    public static final String NIGHT_MODE="night_mode";//夜间模式
    
    public static final String LAST_UPDATE_TIME = "last_update_time";//上次更新时间

    //系统内存
    public static final String SYSTEM_MAX_MEMORY = "system_max_memory";

    public static final String WEBVIEW_USERAGENT = "webview_useragent";//浏览器UA
    
    //列表缓存
    public static final String CACHE_NEWS_KEY="tagId1";
    public static final String CACHE_COMMENT_KEY="tagId2";
    public static final String CACHE_LISTEN_KEY="tagId3";
    public static final String CACHE_VIDEO_KEY="tagId4";
    public static final String CACHE_HELP_KEY="tagId5";
    public static final String CACHE_CHANNEL_KEY="tagId6";
    public static final String CACHE_PICTURE_KEY="tagId7";
    public static final String CACHE_LOCAL_KEY="tagId8";
    
    //详情缓存前缀
    public static final String  DETAIL_PREFIX="detail_";
    
    //获取状态栏的高度
    public static int getStatusBarHeight(Context context) {
        if (DeviceParameter.isDeviceXLarge()) {
            return getIntPreference(STATUS_BAR_HEIGHT_XLARGE, 0, context);
        } else {
            return getIntPreference(STATUS_BAR_HEIGHT, 0, context);
        }
    }

    //保存状态栏的高度
    public static void saveStatusBarHeight(int statusBarHeight, Context context) {
        if (DeviceParameter.isDeviceXLarge()) {
            saveIntPreference(STATUS_BAR_HEIGHT_XLARGE, statusBarHeight);
        } else {
            saveIntPreference(STATUS_BAR_HEIGHT, statusBarHeight);
        }
    }
    //保存配置key-value
    public static void saveStringPreference(String keyName, String value, Context context) {
        Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(keyName, value);
        editor.commit();
    }
    /**
     * 获取配置值
     * @param keyName   key
     * @param defValue  默认值
     * @param context
     * @return
     */
    public static String getStringPreference(String keyName, String defValue, Context context) {
        return context.getSharedPreferences(CONFIG_FILE_NAME,
                Context.MODE_PRIVATE).getString(keyName, defValue);
    }

    /**
     * 该键值存在且不为空
     *
     * @param keyName
     * @param context
     * @return
     */
    public static boolean isStringExist(String keyName, Context context) {
        String value = getStringPreference(keyName, null, context);
        return value != null && value.length() != 0;
    }
    //获取布尔类型，默认值false
    public static boolean getBoolPreference(String keyName, Context context) {
        return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getBoolean(keyName,
                false);
    }
    //获取布尔类型，自定义默认值
    public static boolean getBoolPreference(String keyName, boolean defaultValue, Context context) {
        return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getBoolean(keyName,
                defaultValue);
    }
    //保存布尔类型
    public static void saveBoolPreference(String keyName, boolean value, Context context) {
        Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(keyName, value);
        editor.commit();
    }
    //获取浮点类型，自定义默认值
    public static float getFloatPreference(String keyName, float defautValue, Context context) {
        return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getFloat(keyName, defautValue);
    }
    //保存浮点类型
    public static void saveFloatPreference(String keyName, float value, Context context) {
        Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putFloat(keyName, value);
        editor.commit();
    }
    //获取int类型，自定义默认值
    public static int getIntPreference(String keyName, int defautValue, Context context) {
        return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getInt(keyName,
                defautValue);
    }
    //获取long类型，......
    public static long getLongPreference(String keyName, int defautValue, Context context) {
        return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getLong(keyName,
                defautValue);
    }
    //保存long类型
    public static void saveLongPreference(String keyName, long value, Context context) {
        Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(keyName, value);
        editor.commit();
    }

    //保存int类型
    public static void saveIntPreference(String keyName, int value) {
        Editor editor = App.getInstance().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(keyName, value);
        editor.commit();
    }
    //清除某个配置
    public static void removePreference(String keyName) {
        Editor editor = App.getInstance().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(keyName);
        editor.commit();
    }
    
    public static void removeAllPreference(){
    	Editor editor = App.getInstance().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
    	 editor.clear();  
    	 editor.commit();  

    }
}
