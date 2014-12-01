package com.girlassistant.activity.ui;

import java.lang.ref.WeakReference;
import com.girlassistant.activity.R;
import com.girlassistant.utils.CommonUtils;
import com.girlassistant.utils.IntentUtils;
import com.girlassistant.utils.PreferenceUtils;
import com.girlassistant.base.App;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	protected static final int DIALOG_DEFAULT = 1;
	protected ProgressDialog progressDialog;
	protected View myView;
	protected WindowManager mWindowManager;
	private WeakReference<Activity> reference;
	protected boolean refreshFlag = true;
	/**
	 * 监听是否点击了home键将客户端推到后台
	 */
	private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
		String SYSTEM_REASON = "reason";
		String SYSTEM_HOME_KEY = "homekey";
		String SYSTEM_HOME_KEY_LONG = "recentapps";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				String reason = intent.getStringExtra(SYSTEM_REASON);
				if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
					// 表示按了home键,程序到了后台

				} else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
					// 表示长按home键,显示最近使用的程序列表

				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		reference = ((App) getApplication()).addActivity(this);
		// 注册广播
		registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mHomeKeyEventReceiver);
		((App) getApplication()).removeActivity(reference);
		/**
		 * 屏蔽闪退 ((App)getApplication()).exit();
		 * */
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DEFAULT:
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(getString(R.string.processing));
			return progressDialog;
		default:
			return null;
		}
	}

	protected void setNightOrLightMode(boolean isNightMode) throws Exception {
		if (isNightMode) {
			WindowManager.LayoutParams params = new WindowManager.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
			params.gravity = Gravity.BOTTOM;
			params.y = 10;
			if (myView == null) {
				myView = new TextView(this);
				int color = this.getResources().getColor(R.color.night_mode);
				myView.setBackgroundColor(color);
			}
			mWindowManager.addView(myView, params);

		} else {
			if (myView != null) {
				mWindowManager.removeView(myView);
			}
		}
	}

	protected void removeSkin() throws Exception {
		if (myView != null) {
			mWindowManager.removeView(myView);
		}
	}

	protected void showDefaultDialog(int res) {
		try {
			showDialog(DIALOG_DEFAULT);
			progressDialog.setMessage(getString(res));
		} catch (Exception e) {
		}
	}

	protected void showDefaultDialog(String res) {
		try {
			showDialog(DIALOG_DEFAULT);
			progressDialog.setMessage(res);
		} catch (Exception e) {
		}
	}

	protected void dismissDefaultDialog() {
		try {
			dismissDialog(DIALOG_DEFAULT);
		} catch (Exception e) {
		}
	}

	public void toast(int resourceId) {
		IntentUtils.displayMsg(resourceId, this);
	}

	public void toast(String msg) {
		IntentUtils.displayMsg(msg, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		try {
			setNightOrLightMode(PreferenceUtils.getBoolPreference(PreferenceUtils.NIGHT_MODE, this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (PreferenceUtils.getBoolPreference(PreferenceUtils.NIGHT_MODE, this)) {
			try {
				removeSkin();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {

		int itemHeight = 0;

		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
			itemHeight = listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);

	}

	public void checkNewWork() {
		if (!CommonUtils.isNetworkConnected()) {
			IntentUtils.displayMsg(R.string.error_web_notify_text, this);
			return;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		back();
	}

	private void back() {
		this.finish();
		overridePendingTransition(R.anim.unhold, R.anim.unfade);
	}

	public boolean isRefreshFlag() {
		return refreshFlag;
	}

	public void setRefreshFlag(boolean refreshFlag) {
		this.refreshFlag = refreshFlag;
	}
}
