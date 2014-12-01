package com.girlassistant.activity.adapter;

import java.util.ArrayList;

import com.girlassistant.activity.R;
import com.girlassistant.activity.ui.HomeAct;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuidePageAdapter extends PagerAdapter {
	private ArrayList<Integer> list;
	private LayoutInflater inflater;
	private Context context;

	public GuidePageAdapter(Context context, ArrayList<Integer> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		ViewGroup view = (ViewGroup) object;
		ImageView imageView = (ImageView) view.findViewById(R.id.guide_page_item_image);
		imageView.setImageResource(0);
		((ViewPager) container).removeView(view);

	}

	@Override
	public Object instantiateItem(View container, int position) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.guide_page_item, null);
		ImageView imgView = (ImageView) view.findViewById(R.id.guide_page_item_image);
		imgView.setImageResource(list.get(position));
		ImageView goBtn = (ImageView) view.findViewById(R.id.guide_page_item_btn);
		if (position == list.size() - 1) {
			goBtn.setVisibility(View.VISIBLE);
			goBtn.setEnabled(true);
		} else {
			goBtn.setVisibility(View.GONE);
			goBtn.setEnabled(false);
		}
		goBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HomeAct.class);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				((Activity) context).finish();
			}
		});
		((ViewGroup) container).addView(view);
		return view;
	}

}
