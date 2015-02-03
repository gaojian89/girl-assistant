package com.girlassistant.controller;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import com.girlassistant.activity.fragment.BaseListFragment;
import com.girlassistant.view.pulltorefresh.PullToRefreshListView;

public class WebController extends BaseListController{
	private PullToRefreshListView mListView;
	private Activity context;
	public WebController(BaseListFragment fragment) {
		this.fragment = fragment;
	}
	@Override
	public void getData() {
		mListView = fragment.getListView();
		context = fragment.getMActivity();
		Map<String, Object> params = new HashMap<String, Object>();
		ConnectWebResultListener resultListener = new ConnectWebResultListener();
		
	}
	class ConnectWebResultListener implements IResultListener {

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFinish(Map<String, Object> result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFail(int statusCode) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
