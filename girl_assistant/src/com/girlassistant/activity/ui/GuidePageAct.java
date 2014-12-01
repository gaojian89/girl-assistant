package com.girlassistant.activity.ui;

import com.girlassistant.activity.R;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 引导页面
 * 
 * @author gaoj
 * 
 */
public class GuidePageAct extends BaseActivity implements OnPageChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_page_act);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
