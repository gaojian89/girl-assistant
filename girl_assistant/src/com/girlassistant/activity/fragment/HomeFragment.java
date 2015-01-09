package com.girlassistant.activity.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.girlassistant.activity.R;
import com.girlassistant.activity.adapter.HomePagerAdapter;
import com.girlassistant.activity.ui.HomeAct;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener, OnPageChangeListener {
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;
	private Fragment selectedFragment, attentionFragment;
	private TextView mTabSelectedTv, mTabAttention;
	private ImageView mTabLineIv;
	private int screenWidth;
	private int currentIndex;
	private HomeAct act;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment, null);
		init(view);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.act = (HomeAct) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

	private void init(View view) {

		mTabSelectedTv = (TextView) view.findViewById(R.id.home_fragment_selected);
		mTabSelectedTv.setOnClickListener(this);
		mTabAttention = (TextView) view.findViewById(R.id.home_fragment_attention);
		mTabAttention.setOnClickListener(this);
		mTabLineIv = (ImageView) view.findViewById(R.id.home_fragment_line);
		initTabLineWidth();
		initViewPager(view);

	}

	private void initViewPager(View view) {
		mPager = (ViewPager) view.findViewById(R.id.home_fragment_pager);
		fragmentList = new ArrayList<Fragment>();
		selectedFragment = new HomeSelectedFragment();
		attentionFragment = new HomeAttentionFragment();
		fragmentList.add(selectedFragment);
		fragmentList.add(attentionFragment);
		mPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), fragmentList));
		mPager.setOnPageChangeListener(this);
		mPager.setCurrentItem(0);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_fragment_selected:
			mPager.setCurrentItem(0);
			break;
		case R.id.home_fragment_attention:
			mPager.setCurrentItem(1);
			break;

		default:
			break;
		}

	}

	/**
	 * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		act.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
		lp.width = screenWidth / 2;
		mTabLineIv.setLayoutParams(lp);
	}

	/**
	 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	/**
	 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比 offsetPixels:当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int position, float offset, int offsetPixels) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();

		/**
		 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
		 * 滑动场景： 记2个页面, 从左到右分别为0,1 0->1; 1->0
		 */

		if (currentIndex == 0 && position == 0)// 0->1
		{
			lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex * (screenWidth / 2));

		} else if (currentIndex == 1 && position == 0) // 1->0
		{
			lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 2) + currentIndex * (screenWidth / 2));

		}
		mTabLineIv.setLayoutParams(lp);

	}

	@Override
	public void onPageSelected(int position) {
		currentIndex = position;
	}

}
