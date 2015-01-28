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
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, date);
		return calendar.getTimeInMillis();
	}
}
