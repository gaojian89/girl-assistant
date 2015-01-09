package com.girlassistant.activity.ui;

import com.girlassistant.activity.R;
import com.girlassistant.activity.fragment.HomeFragment;
import com.girlassistant.activity.fragment.PersonalCenterFragment;
import com.girlassistant.activity.fragment.ReleaseFragment;
import com.girlassistant.activity.fragment.SquareFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomeAct extends BaseActivity implements OnCheckedChangeListener {
	private RadioGroup menu;
	private Fragment homeFragment, squareFragment, personalCenterFragment, releaseFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.home_act);
		init();
	}

	private void init() {
		fragmentManager = getSupportFragmentManager();
		menu = (RadioGroup) findViewById(R.id.home_act_bottom_menu);
		menu.setOnCheckedChangeListener(this);
		((RadioButton) menu.findViewById(R.id.home_act_home_page)).setChecked(true);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_act_home_page:// 首页
			transaction = fragmentManager.beginTransaction();
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
			}
			transaction.replace(R.id.content, homeFragment);
			transaction.commit();
			break;

		case R.id.home_act_square:// 广场
			transaction = fragmentManager.beginTransaction();
			if (squareFragment == null) {
				squareFragment = new SquareFragment();
			}
			transaction.replace(R.id.content, squareFragment);
			transaction.commit();
			break;

		case R.id.home_act_personal_center:// 个人中心
			transaction = fragmentManager.beginTransaction();
			if (personalCenterFragment == null) {
				personalCenterFragment = new PersonalCenterFragment();
			}
			transaction.replace(R.id.content, personalCenterFragment);
			transaction.commit();
			break;

		case R.id.home_act_release:// 发布
			transaction = fragmentManager.beginTransaction();
			if (releaseFragment == null) {
				releaseFragment = new ReleaseFragment();
			}
			transaction.replace(R.id.content, releaseFragment);
			transaction.commit();
			break;

		default:
			break;
		}

	}

}
