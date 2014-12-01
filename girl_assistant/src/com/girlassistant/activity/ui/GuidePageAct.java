package com.girlassistant.activity.ui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.girlassistant.activity.R;
import com.girlassistant.activity.adapter.GuidePageAdapter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.widget.ImageView;

/**
 * 引导页面
 * 
 * @author gaoj
 * 
 */
public class GuidePageAct extends BaseActivity implements OnPageChangeListener {
	private ViewPager pager;
	private ImageView indexImg;
	private GestureDetector gestureDetector;
	private ArrayList<Integer> listPic;
	private ArrayList<Integer> listIndex;
	private static final int GUIDE_COUNT = 4;;
	private GuidePageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_page_act);
		init();
	}

	private void init() {
		pager = (ViewPager) findViewById(R.id.guide_page_act_viewpager);
		indexImg = (ImageView) findViewById(R.id.guide_page_act_index);
		listPic = new ArrayList<Integer>();
		listIndex = new ArrayList<Integer>();
		Class<com.girlassistant.activity.R.drawable> cls = R.drawable.class;
		try {
			for (int i = 0; i < GUIDE_COUNT; i++) {
				int idPic = cls.getDeclaredField("cover_" + i).getInt(null);
				int idIndex = cls.getDeclaredField("index_" + i).getInt(null);
				listPic.add(idPic);
				listIndex.add(idIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pager.setAdapter(new GuidePageAdapter(this, listPic));
		pager.setOffscreenPageLimit(1);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {

	}

}
