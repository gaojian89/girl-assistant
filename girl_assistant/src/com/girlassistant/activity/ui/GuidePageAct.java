package com.girlassistant.activity.ui;

import java.util.ArrayList;
import com.girlassistant.activity.R;
import com.girlassistant.activity.adapter.GuidePageAdapter;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
	private int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_page_act);
		init();
		setListener();
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

	private void setListener() {
		pager.setOnPageChangeListener(this);
		pager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (currentPage == 0 || currentPage == 3) {
					return gestureDetector.onTouchEvent(event);
				}
				return false;
			}
		});
		gestureDetector = new GestureDetector(this, new SimpleOnGestureListener() {

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if (e1 != null && e2 != null) {
					int yDistance = (int) Math.abs(e1.getY() - e2.getY());
					int xDistance = (int) (e1.getX() - e2.getX());
					int xDistanceABS = (int) Math.abs(xDistance);

					if (xDistance > 100 && xDistanceABS > yDistance) {

						if (currentPage == 3) {

							Intent intent = new Intent(getApplicationContext(), HomeAct.class);
							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
							GuidePageAct.this.finish();

						} else {

							pager.setCurrentItem(currentPage + 1);

						}

						return true;

					} else if (xDistance < -100 && xDistanceABS > yDistance) {

						if (currentPage == 0) {

						} else {

							pager.setCurrentItem(currentPage - 1);
						}
						return true;
					}
				}
				return false;
			}

		});
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		indexImg.setImageResource(listIndex.get(arg0));
		currentPage = arg0;
	}

}
