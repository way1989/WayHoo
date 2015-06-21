package com.way.yahoo;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

import com.way.common.util.SystemUtils;
import com.way.weather.plugin.bean.WeatherInfo;
public class App extends Application {
	public static Map<String, WeatherInfo> mMainMap;

	@Override
	public void onCreate() {
		super.onCreate();
		SystemUtils.copyDB(this);// 程序第一次运行将数据库copy过去
		mMainMap = new HashMap<String, WeatherInfo>();
	}
}
