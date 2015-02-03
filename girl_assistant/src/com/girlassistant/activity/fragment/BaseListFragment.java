package com.girlassistant.activity.fragment;

import com.girlassistant.activity.R;
import com.girlassistant.controller.BaseListController;
import com.girlassistant.utils.CheckUtils;
import com.girlassistant.view.pulltorefresh.PullToRefreshBase;
import com.girlassistant.view.pulltorefresh.PullToRefreshBase.Mode;
import com.girlassistant.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.girlassistant.view.pulltorefresh.PullToRefreshListView;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class BaseListFragment extends BaseFragment {

	protected Activity mAct;
	protected View parent;
	protected PullToRefreshListView mListView;
	protected BaseListController webController;
	protected BaseListController fileController;
	protected BaseListController moreController;

	protected OnRefreshListener<ListView> mRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			String label = DateUtils.formatDateTime(mAct, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
					| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

			if (mListView.isHeaderShown()) {
				if (refreshFlag) {
					refreshFlag = false;
					if (webController != null) {
						webController.getData();
					}
				}
			} else if (mListView.isFooterShown()) {
				if (moreController != null) {
					moreController.getData();
				}
			}
		}
	};
	
	public Activity getMActivity () {
		return mAct;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		parent = inflater.inflate(R.layout.fragment_list_content, container, false);
		mListView = (PullToRefreshListView) parent.findViewById(R.id.content_listView);
		mListView.setMode(Mode.BOTH);
		mListView.setAutoAddMore(true);
		mListView.setEmptyView(LayoutInflater.from(mAct).inflate(R.layout.empty_layout, null));
		mListView.setOnRefreshListener(mRefreshListener);
		configController();
		if (CheckUtils.isNoEmptyStr(pDir)) {
			if (fileController != null) {
				fileController.getData();
			}
		}
		return parent;
	}

	private void configController() {
		if (webController == null) {
			webController = configRefreshController();
		}
		if (fileController == null) {
			fileController = configFileController();
		}
		if (moreController == null) {
			moreController = configMoreController();
		}
	}
	
	public PullToRefreshListView getListView() {
		return mListView;
	}

	/**
	 * 需配置webController
	 */
	public void refreshData() {
		if (CheckUtils.isNoEmptyStr(pDir)) {
			if (webController != null) {
				webController.getData();
			}
		} else {
			if (webController != null) {
				webController.getData();
			}
		}
	}


	public abstract BaseListController configRefreshController();

	public abstract BaseListController configFileController();

	public abstract BaseListController configMoreController();

}
