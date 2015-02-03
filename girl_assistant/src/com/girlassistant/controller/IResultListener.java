package com.girlassistant.controller;

import java.util.Map;

/**
 * Activity中的回调接口类，需要在Activity中实现该接口，便于控制器将数据层数据返回给Activity
 * @author zyj
 *
 */
public interface IResultListener {

	public void onStart();
	public void onFinish(Map<String, Object> result);
	public void onFail(int statusCode);
}

