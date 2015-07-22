package com.way.weather.plugin.spider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.Alerts;
import com.way.weather.plugin.bean.Alerts.Alert;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.Index;
import com.way.weather.plugin.bean.IndexDetail;
import com.way.weather.plugin.bean.RealTime;

public class WeatherController {
	public static AQI convertToNewAQI(JSONObject aqiJSONObject,
			String language, String pid) {
		AQI aqi = new AQI();
		aqi.setCity_code(pid);
		try {
			aqi.setPub_time(getAQITime(aqiJSONObject.getString("pub_time")));

			int aqiValue = WeatherUtilities.getAqi(aqiJSONObject
					.getString("aqi"));
			aqi.setAqi(aqiValue);
			aqi.setPm25(WeatherUtilities.getAqi(aqiJSONObject.getString("pm25")));
			aqi.setPm10(WeatherUtilities.getAqi(aqiJSONObject.getString("pm10")));
			aqi.setNo2(WeatherUtilities.getAqi(aqiJSONObject.getString("no2")));
			aqi.setSo2(WeatherUtilities.getAqi(aqiJSONObject.getString("so2")));
			aqi.setCo(WeatherConstants.NO_VALUE_FLAG);
			aqi.setO3(WeatherConstants.NO_VALUE_FLAG);
			aqi.setAqi_level(WeatherUtilities.getAqiLevel(aqiValue, language));
			aqi.setAqi_desc(WeatherUtilities.getAqiDesc(aqiValue, language));
			aqi.setSource(WeatherUtilities.getAQISource(language));
			aqi.setSpot(aqiJSONObject.getString("spot"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return aqi;
	}

	public static Alerts convertToNewAlert(JSONArray alertJSONArray,
			String language) {
		ArrayList<Alert> alertLists = new ArrayList<Alert>();
		Alerts alerts = new Alerts();
		try {
			for (int i = 0; i < alertJSONArray.length(); ++i) {
				JSONObject jsonObject = alertJSONArray.getJSONObject(i);
				Alerts.Alert alert = new Alerts.Alert();
				alert.setAbnormal(jsonObject.getString("abnormal"));
				alert.setDetail(jsonObject.getString("detail"));
				alert.setHoliday(jsonObject.getString("holiday"));
				alert.setLevel(jsonObject.getString("level"));
				alert.setPubTime(Long.valueOf(jsonObject.getLong("pub_time")));
				alert.setTitle(jsonObject.getString("title"));
				alertLists.add(alert);
			}
			alerts.setPid(language);
			alerts.setArryAlert(alertLists);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return alerts;
	}

	public static Forecast convertToNewForecast(JSONObject forecastJSONObject,
			String language, String pid) throws JSONException,
			NumberFormatException {
		Forecast forecast = new Forecast();

		JSONObject forcastJSONObject = forecastJSONObject
				.getJSONObject("forecast");

		// JSONObject todayJSONObject =
		// forecastJSONObject.getJSONObject("today");
		JSONObject yestodayJSONObject = forecastJSONObject
				.getJSONObject("yestoday");

		forecast.setPid(pid);
		forecast.setWinds(0, "");
		for (int i = 1; i < Forecast.DAY_NUM; i++) {
			forecast.setWinds(i, WeatherUtilities.getWind(
					forcastJSONObject.getString("fx" + (i > 2 ? 2 : i)),
					forcastJSONObject.getString("fl" + i), language));
		}

		WeatherUtilities.WeatherName[] weatherName = new WeatherUtilities.WeatherName[5];
		for (int i = 1; i < Forecast.DAY_NUM; i++) {
			weatherName[i - 1] = WeatherUtilities.getWeatherName(
					forcastJSONObject.getString("weather" + i), language);
		}
		for (int i = 1; i < Forecast.DAY_NUM; i++) {
			forecast.setWeatherNames(i, weatherName[i - 1].getName());
		}
		forecast.setWeatherNames(0, yestodayJSONObject.getString("weatherEnd"));

		forecast.setWeatherNamesFrom(
				0,
				WeatherUtilities.getWeatherName(
						yestodayJSONObject.getString("weatherStart"), language)
						.getFrom());
		for (int i = 1; i < Forecast.DAY_NUM; i++) {
			forecast.setWeatherNamesFrom(i, weatherName[i - 1].getFrom());
		}
		forecast.setWeatherNamesTo(
				0,
				WeatherUtilities.getWeatherName(
						yestodayJSONObject.getString("weatherEnd"), language)
						.getTo());
		for (int i = 1; i < Forecast.DAY_NUM; i++) {
			forecast.setWeatherNamesTo(i, weatherName[i - 1].getTo());
		}
		long forcastTime = getForcastTime(forcastJSONObject.getString("date_y")
				+ " " + forcastJSONObject.getString("fchh"));
		forecast.setPubtime(forcastTime);

		int[] forecastTemps = new int[12];
		for (int i = 0; i < (Forecast.DAY_NUM - 1); ++i) {
			String[] temps = forcastJSONObject.getString("temp" + (i + 1))
					.split("~");
			String[] minTemp = temps[0].split("℃");
			String[] maxTemp = temps[1].split("℃");
			forecastTemps[(i * 2)] = Integer.parseInt(minTemp[0]);
			forecastTemps[(1 + i * 2)] = Integer.parseInt(maxTemp[0]);
		}
		forecast.setTmpHighs(0, yestodayJSONObject.getInt("tempMax"));
		forecast.setTmpLows(0, yestodayJSONObject.getInt("tempMin"));
		for (int i = 0; i < (Forecast.DAY_NUM - 1); ++i) {
			forecast.setTmpHighs(i + 1, forecastTemps[(i * 2)]);
			forecast.setTmpLows(i + 1, forecastTemps[(1 + i * 2)]);
		}
		forecast.setType(0, WeatherUtilities
				.getAnimationType(yestodayJSONObject.getString("weatherEnd")));
		for (int i = 1; i < Forecast.DAY_NUM; i++) {
			forecast.setType(i, WeatherUtilities
					.getAnimationType(forcastJSONObject
							.getString("weather" + i)));
		}
		// for (int i = 0; i < Forecast.DAY_NUM; ++i)
		// forecast.setSunrise(i, WeatherConstants.NO_VALUE_FLAG);
		// for (int i = 0; i < Forecast.DAY_NUM; ++i)
		// forecast.setSunset(i, WeatherConstants.NO_VALUE_FLAG);
		for (int i = 0; i < Forecast.DAY_NUM; ++i)
			forecast.setPressures(i, WeatherConstants.NO_VALUE_FLAG);
		for (int i = 0; i < Forecast.DAY_NUM; ++i)
			forecast.setHumiditys(i, WeatherConstants.NO_VALUE_FLAG);
		JSONArray jsonArray = forecastJSONObject.getJSONObject("accu_f5")
				.getJSONArray("DailyForecasts");
		for (int i = 1; i < Forecast.DAY_NUM; ++i) {
			forecast.setSunrise(
					i,
					Long.valueOf(jsonArray.getJSONObject(i - 1).getString(
							"Sun_EpochRise")));
			forecast.setSunset(
					i,
					Long.valueOf(jsonArray.getJSONObject(i - 1).getString(
							"Sun_EpochSet")));
		}
		return forecast;
	}

	public static Index convertToNewIndex(JSONObject indexInfo,
			String language, String pid) throws JSONException {
		Index index = new Index();
		index.setCity_code(pid);

		JSONObject indexJSONObject = indexInfo.getJSONObject("forecast");
		ArrayList<IndexDetail> indexDetailLists = new ArrayList<IndexDetail>();
		IndexDetail windIndexDetail = new IndexDetail();
		windIndexDetail.setDesc(WeatherUtilities.getWind(
				indexJSONObject.getString("fx1"),
				indexJSONObject.getString("fl1"), language));
		windIndexDetail.setTitle(WeatherUtilities.getIndexTitle(
				WeatherConstants.WIND_LEVEL_INDEX, language));
		windIndexDetail.setDetail(WeatherUtilities.getWindIndexDetail(
				indexJSONObject.getString("fl1"), language));
		windIndexDetail.setType(WeatherUtilities
				.getIndexType(WeatherConstants.WIND_LEVEL_INDEX));
		indexDetailLists.add(windIndexDetail);

		for (Map.Entry<String, String> entry : WeatherConstants.INDEX_OLD
				.entrySet()) {
			String key = entry.getKey();
			IndexDetail indexDetail = new IndexDetail();
			indexDetail.setType(WeatherUtilities.getIndexType(key).intValue());
			indexDetail.setTitle(WeatherUtilities.getIndexTitle(key, language));
			String indexValue = indexJSONObject
					.getString(WeatherConstants.INDEX_OLD.get(key));
			indexDetail.setDesc(WeatherUtilities.getIndexDesc(key, indexValue,
					language));

			indexDetail.setDetail(WeatherUtilities.getIndexDetail(key,
					indexValue, language));

			indexDetailLists.add(indexDetail);
		}
		index.setIndex(indexDetailLists);
		return index;
	}

	public static RealTime convertToNewRealTime(JSONObject realTimeJSONObject,
			String language, String pid) throws JSONException {
		RealTime realTime = new RealTime();
		realTime.setCity_code(pid);
		realTime.setPub_time(parseTime(realTimeJSONObject.getString("time")));
		realTime.setTemp(WeatherUtilities.getIntFromJSON(realTimeJSONObject,
				"temp"));
		realTime.setWind(WeatherUtilities.getWind(
				realTimeJSONObject.getString("WD"),
				realTimeJSONObject.getString("WS"), language));
		realTime.setAnimation_type(WeatherUtilities
				.getAnimationType(realTimeJSONObject.getString("weather")));
		realTime.setHumidity(WeatherUtilities.getHumidity(realTimeJSONObject
				.getString("SD")));
		realTime.setRising_tide(WeatherConstants.NO_VALUE_FLAG);
		realTime.setFalling_tide(WeatherConstants.NO_VALUE_FLAG);
		realTime.setPressure(WeatherConstants.NO_VALUE_FLAG);
		realTime.setWater(WeatherConstants.NO_VALUE_FLAG);
		realTime.setWeather_name(WeatherUtilities.getWeatherName(
				realTimeJSONObject.getString("weather"), language).getName());
		return realTime;
	}

	private static long getAQITime(String timeStr) {
		long aqiTime = -7754570281926000640L;
		try {
			aqiTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr)
					.getTime();
		} catch (ParseException e) {
		}
		return aqiTime;
	}

	private static long getForcastTime(String timeStr) {
		long forcastTime = -7754570281926000640L;
		try {
			forcastTime = new SimpleDateFormat("yyyy年M月d日 HH").parse(timeStr)
					.getTime();

		} catch (ParseException e) {
		}
		return forcastTime;
	}

	private static long parseTime(String timeStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String data = simpleDateFormat.format(new Date(System
				.currentTimeMillis()));
		timeStr = data + " " + timeStr;
		long realTime = getAQITime(timeStr);
		Log.i("HeHe", "timeStr = " + timeStr + ", realTime = " + realTime);
		return realTime;
	}
}