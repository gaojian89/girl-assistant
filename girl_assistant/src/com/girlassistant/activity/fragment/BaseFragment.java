package com.girlassistant.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected boolean refreshFlag=true;
	protected String pDir;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	public boolean isRefreshFlag() {
		return refreshFlag;
	}
	
	public void setRefreshFlag(boolean refreshFlag) {
		this.refreshFlag = refreshFlag;
	}
	
	public String getpDir() {
		return pDir;
	}
	
	public void setpDir(String pDir) {
		this.pDir = pDir;
	}
}
