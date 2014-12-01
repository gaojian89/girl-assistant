package com.girlassistant.utils;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

/**
 * User: kai
 * Date: 13-1-5
 * Time: 下午6:33
 * Desp: 一些方法的兼容工具包
 */
public class CompatUtils {


    /**
     * 设置图片的透明度
     *
     * @param imageView
     * @param alpha     16进制  0xff 或者是 0-255
     */
    public static void setImageViewAlpha(ImageView imageView, int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setAlpha(alpha);
        } else {
            imageView.setImageAlpha(alpha);
        }
    }

    /**
     * 设置View背景
     *
     * @param view
     * @param drawable 背景
     */
    public static void setViewBackground(View view, Drawable drawable) {
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * 获取一个兼容4.0主题的Dialog
     *
     * @param activity
     * @return
     */
    @SuppressLint("NewApi")
    public static AlertDialog.Builder getCompatDialogBuilder(Activity activity) {
        AlertDialog.Builder alertDialogBuilder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            alertDialogBuilder = new AlertDialog.Builder(activity);
        } else {
            if (ThemeSettingsHelper.getThemeSettingsHelper().isDefaultTheme()) {
                alertDialogBuilder = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT);
            } else {
                alertDialogBuilder = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK);
            }
        }
        return alertDialogBuilder;
    }

    /**
     * 获取一个兼容4.0主题的Dialog
     *
     * @param activity
     * @return
     */
    @SuppressLint("NewApi")
    public static ProgressDialog getCompatProgressDialog(Activity activity) {
        ProgressDialog progressDialog;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            progressDialog = new ProgressDialog(activity);
        } else {
            if (ThemeSettingsHelper.getThemeSettingsHelper().isDefaultTheme()) {
                progressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                progressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_DARK);
            }
        }
        return progressDialog;
    }

    /**
     * 兼容魅族mx2
     *
     * @param actionbar
     * @param collapsable
     */
    public static void setActionBarViewCollapsable(ActionBar actionbar, boolean collapsable) {

        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionBarViewCollapsable", new Class[]{boolean.class});
            try {
                method.invoke(actionbar, collapsable);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * 兼容魅族mx2
     *
     * @param actionbar
     * @param hidden
     */
    public static void setActionModeHeaderHidden(ActionBar actionbar, boolean hidden) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionModeHeaderHidden", new Class[]{boolean.class});
            try {
                method.invoke(actionbar, hidden);
            } catch (IllegalArgumentException e) {

                e.printStackTrace();

            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * SharePrefernce apply()
     *
     * @param editor
     */
    public static void applyEditor(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    public static void compatDisableViewHardware(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            try {
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            } catch (NoSuchMethodError e) {
            }
        }
    }

    public static <Params, Progress, Result> void executeAsyncTask(AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}
