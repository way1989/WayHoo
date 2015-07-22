package com.way.weather.plugin.spider;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.Alerts;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.Index;
import com.way.weather.plugin.bean.RealTime;
import com.way.weather.plugin.bean.WeatherInfo;

public class WeatherSpider {

	public static final String WEATHER_ALL = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=%s";

	public static WeatherInfo getWeatherInfo(Context context, String postID,
			String result) throws JSONException {
		String language = context.getResources().getConfiguration().locale
				.toString();
		JSONObject response = new JSONObject(result);
		Forecast forecast = WeatherController.convertToNewForecast(response,
				language, postID);
		// Log.i("way", "jsonObjectRequest forecast = " + forecast);

		RealTime realTime = WeatherController.convertToNewRealTime(
				response.getJSONObject("realtime"), language, postID);
		// Log.i("way", "realTime = " + realTime);

		Alerts alerts = WeatherController.convertToNewAlert(
				response.getJSONArray("alert"), postID);
		// Log.i("way", "alerts = " + alerts);

		Index index = WeatherController.convertToNewIndex(response, language,
				postID);
		// Log.i("way", "index = " + index);

		AQI aqi = WeatherController.convertToNewAQI(
				response.getJSONObject("aqi"), language, postID);
		return new WeatherInfo(realTime, forecast, aqi, index, alerts);
	}

	public static boolean isEmpty(WeatherInfo info) {
		if (info == null)
			return true;
		if (info.getRealTime() == null
				|| info.getRealTime().getAnimation_type() < 0)
			return true;
		if (info.getForecast() == null || info.getForecast().getType(1) < 0)
			return true;
		// if (info.getAqi() == null || info.getAqi().getAqi() < 0)
		// return true;
		if (info.getIndex() == null || info.getIndex().getIndex() == null)
			return true;
		return false;
	}

	public static boolean isEmpty(RealTime info) {
		if (info == null || info.getAnimation_type() < 0)
			return true;
		return false;
	}

	public static boolean isEmpty(Forecast info) {
		if (info == null || info.getType(1) < 0)
			return true;
		return false;
	}

	public static boolean isEmpty(AQI info) {
		if (info == null || info.getAqi() < 0)
			return true;
		return false;
	}

	public static boolean isEmpty(Index info) {
		if (info == null || info.getIndex() == null
				|| info.getIndex().get(0) == null)
			return true;
		return false;
	}

	public static boolean isEmpty(Alerts info) {
		if (info == null || info.getArryAlert() == null
				|| info.getArryAlert().get(0) == null)
			return true;
		return false;
	}

}