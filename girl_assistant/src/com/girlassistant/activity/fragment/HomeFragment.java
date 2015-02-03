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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener, OnPageChangeListener {
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;
	private Fragment selectedFragment, attentionFragment;
	private HomeAct act;
	private TextView tvTitle;
	private ImageView imgSelected, imgAttention;

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
		tvTitle = (TextView) view.findViewById(R.id.home_fragment_tv_title);
		imgAttention = (ImageView) view.findViewById(R.id.home_fragment_title_img_attention);
		imgSelected = (ImageView) view.findViewById(R.id.home_fragment_title_img_selected);
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

		default:
			break;
		}

	}


	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float offset, int offsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			tvTitle.setText(getResources().getText(R.string.selected));
			imgSelected.setBackgroundResource(R.drawable.home_title_select);
			imgAttention.setBackgroundResource(R.drawable.home_title_unselect);
			break;
		case 1:
			tvTitle.setText(getResources().getText(R.string.attention));
			imgSelected.setBackgroundResource(R.drawable.home_title_unselect);
			imgAttention.setBackgroundResource(R.drawable.home_title_select);
			break;

		default:
			break;
		}
	}

}
