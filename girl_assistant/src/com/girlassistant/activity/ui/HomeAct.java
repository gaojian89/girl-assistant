package com.girlassistant.activity.ui;

import com.girlassistant.activity.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomeAct extends BaseActivity implements OnClickListener {
	private TextView tvHomePage, tvSquare, tvPersonalCenter, tvRelease;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.home_act);
		init();
	}

	private void init() {
		tvHomePage = (TextView) findViewById(R.id.home_act_home_page);
		tvSquare = (TextView) findViewById(R.id.home_act_square);
		tvPersonalCenter = (TextView) findViewById(R.id.home_act_personal_center);
		tvRelease = (TextView) findViewById(R.id.home_act_release);
		tvHomePage.setOnClickListener(this);
		tvSquare.setOnClickListener(this);
		tvPersonalCenter.setOnClickListener(this);
		tvRelease.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_act_home_page:
			tvHomePage.setEnabled(false);
			tvSquare.setEnabled(true);
			tvPersonalCenter.setEnabled(true);
			tvRelease.setEnabled(true);
			break;
		case R.id.home_act_square:
			tvHomePage.setEnabled(true);
			tvSquare.setEnabled(false);
			tvPersonalCenter.setEnabled(true);
			tvRelease.setEnabled(true);
			break;
		case R.id.home_act_personal_center:
			tvHomePage.setEnabled(true);
			tvSquare.setEnabled(true);
			tvPersonalCenter.setEnabled(false);
			tvRelease.setEnabled(true);
			break;
		case R.id.home_act_release:
			tvHomePage.setEnabled(true);
			tvSquare.setEnabled(true);
			tvPersonalCenter.setEnabled(true);
			tvRelease.setEnabled(false);
			break;

		default:
			break;
		}

	}

}
