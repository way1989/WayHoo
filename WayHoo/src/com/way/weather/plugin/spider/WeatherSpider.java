package com.way.weather.plugin.spider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.way.common.util.NetUtil;
import com.way.db.CityProvider;
import com.way.db.CityProvider.CityConstants;
import com.way.fragment.TaskException;
import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.Alerts;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.Index;
import com.way.weather.plugin.bean.RealTime;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.yahoo.App;

public class WeatherSpider {

	public static final String WEATHER_ALL = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=%s";

	public static WeatherInfo getWeatherInfo(String postID) throws TaskException {
		if (NetUtil.getNetworkState(App.getApplication()) == NetUtil.NETWORN_NONE)
			throw new TaskException(
					TaskException.TaskError.noneNetwork.toString());
		RequestFuture<String> future = RequestFuture.newFuture();
		StringRequest request = new StringRequest(String.format(
				WeatherSpider.WEATHER_ALL, postID), future, future);
		App.getVolleyRequestQueue().add(request);
		try {
			String result = future.get(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
					TimeUnit.MILLISECONDS);
			WeatherInfo weatherInfo = WeatherSpider.getWeatherInfo(
					App.getApplication(), postID, result);
			if (!WeatherSpider.isEmpty(weatherInfo)) {
				save2Database(weatherInfo, postID, result);// 保存到数据库
				return weatherInfo;
			}
		} catch (InterruptedException e) {
			throw new TaskException(TextUtils.isEmpty(e.getMessage()) ? ""
					: e.getMessage());
		} catch (ExecutionException e) {
			throw new TaskException(TextUtils.isEmpty(e.getMessage()) ? ""
					: e.getMessage());
		} catch (TimeoutException e) {
			throw new TaskException(TaskException.TaskError.timeout.toString());
		} catch (JSONException e) {
			throw new TaskException(
					TaskException.TaskError.resultIllegal.toString());
		}
		return null;
	}

	public static void save2Database(WeatherInfo weatherInfo, String postID,
			String response) {
		long pubTime = weatherInfo.getRealTime().getPub_time();
		long savePubTime = getPubTime(postID);
		if (pubTime != savePubTime) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(CityConstants.REFRESH_TIME,
					System.currentTimeMillis());
			contentValues.put(CityConstants.PUB_TIME, pubTime);
			contentValues.put(CityConstants.WEATHER_INFO, response);
			App.getApplication()
					.getContentResolver()
					.update(CityProvider.TMPCITY_CONTENT_URI, contentValues,
							CityConstants.POST_ID + "=?",
							new String[] { postID });
		}
	}

	public static long getPubTime(String postID) {
		Cursor c = App
				.getApplication()
				.getContentResolver()
				.query(CityProvider.TMPCITY_CONTENT_URI,
						new String[] { CityConstants.PUB_TIME },
						CityConstants.POST_ID + "=?", new String[] { postID },
						null);

		long time = 0L;
		if (c.moveToFirst())
			time = c.getLong(c.getColumnIndex(CityConstants.PUB_TIME));
		return time;
	}

	public static WeatherInfo getWeatherInfo(Context context, String postID,
			String result) throws JSONException {
		String language = context.getResources().getConfiguration().locale
				.toString();
		JSONObject response = new JSONObject(TextUtils.isEmpty(result) ? ""
				: result);
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