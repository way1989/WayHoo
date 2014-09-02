package com.way.weather.plugin.util;

import java.util.Calendar;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Tools {

	public static String getDeviceInfo(Context context) {
		Object[] objects = new Object[4];
		objects[0] = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		// "529e2dd3d767bdd3595eec30dd481050";
		objects[1] = "pisces";
		objects[2] = "JXCCNBD20.0";
		objects[3] = "";
		return String
				.format("&imei=%s&device=%s&miuiVersion=%s&modDevice=%s&source=miuiWeatherApp",
						objects);
	}

	public static long getStartMillsInOneDay(Calendar calendar) {
		int i = calendar.get(1);
		int j = calendar.get(2);
		int k = calendar.get(5);
		calendar.clear();
		calendar.set(1, i);
		calendar.set(2, j);
		calendar.set(5, k);
		return calendar.getTimeInMillis();
	}
}
