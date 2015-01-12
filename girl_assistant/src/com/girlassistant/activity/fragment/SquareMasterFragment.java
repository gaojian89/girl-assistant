package com.girlassistant.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.girlassistant.activity.R;
import com.girlassistant.activity.ui.HomeAct;

public class SquareMasterFragment extends Fragment{
	private HomeAct act;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.square_master_fragment, null);
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.act = (HomeAct) activity;
	}
}
