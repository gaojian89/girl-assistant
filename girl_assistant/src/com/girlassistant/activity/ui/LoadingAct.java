package com.girlassistant.activity.ui;

import com.girlassistant.activity.R;
import com.girlassistant.utils.DeviceParameter;
import com.girlassistant.utils.PreferenceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingAct extends BaseActivity {
	public static long DELAY_LOAD_TIME = 1500;
	private boolean firstInstall;
	public static final String FIRST_INSTALL = "user_first_install";// 是否第一次登陆

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.loading_act);
		firstInstall = PreferenceUtils.getBoolPreference(FIRST_INSTALL, true, this);
		initViews();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				intentTo();
			}
		}, DELAY_LOAD_TIME);

	}

	private void initViews() {
		DeviceParameter.initDisplayHeight(this);
	}

	private void intentTo() {
		Intent intent = new Intent();
		if (firstInstall) {
			PreferenceUtils.saveBoolPreference(FIRST_INSTALL, false, this);
			intent.setClass(this, GuidePageAct.class);
		} else {
			intent.setClass(this, HomeAct.class);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}
}
