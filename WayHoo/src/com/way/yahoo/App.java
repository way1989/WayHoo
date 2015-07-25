package com.way.yahoo;

import im.fir.sdk.FIR;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.way.common.util.SystemUtils;

public class App extends Application {
	private static App mApplication;
	private static RequestQueue mVolleyRequestQueue;

	public static synchronized RequestQueue getVolleyRequestQueue() {
		if (mVolleyRequestQueue == null)
			mVolleyRequestQueue = Volley.newRequestQueue(mApplication);
		return mVolleyRequestQueue;
	}

	public static synchronized App getApplication() {
		return mApplication;
	}

	@Override
	public void onCreate() {
		FIR.init(this);
		super.onCreate();
		mApplication = this;
		SystemUtils.copyDB(this);// 程序第一次运行将数据库copy过去
	}
}
