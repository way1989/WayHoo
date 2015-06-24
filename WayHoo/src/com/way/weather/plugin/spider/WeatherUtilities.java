package com.way.weather.plugin.spider;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class WeatherUtilities {
	private static String checkLanguageSuport(String language) {
		if (WeatherConstants.SURPORTTED_LANGUAGE_LIST.contains(language
				.toLowerCase()))
			return language.toLowerCase();
		return Locale.US.toString().toLowerCase();
	}

	public static String getAQISource(String paramString) {
		String str = checkLanguageSuport(paramString);
		return ((String) WeatherConstants.AQI_SOURCE_LANGUAGE_MAP.get(str));
	}

	public static int getAnimationType(String weatherStr) {
		int type = -1;
		String[] strs = weatherStr.split("转");
		if (strs.length > 1) {
			String[] arrayOfString3 = strs[0].split("到");
			if (arrayOfString3.length > 1)
				type =  WeatherConstants.WEATHER_ANIMATION_MAP
						.get(arrayOfString3[1]);
			else {
				type =  WeatherConstants.WEATHER_ANIMATION_MAP
						.get(strs[0]);
			}
		} else {
			String[] arrayOfString2 = weatherStr.split("到");
			if (arrayOfString2.length > 1)
				type =  WeatherConstants.WEATHER_ANIMATION_MAP
						.get(arrayOfString2[1]);
			else
				type =  WeatherConstants.WEATHER_ANIMATION_MAP
						.get(weatherStr);
		}
		return type;
	}

	public static int getAqi(String aqiStr) {
		int aqi = WeatherConstants.NO_VALUE_FLAG;
		try {
			aqi = Integer.parseInt(aqiStr);
		} catch (NumberFormatException e) {
		}
		return aqi;
	}

	public static String getAqiDesc(int aqiLevel, String language) {
		String languageKey = checkLanguageSuport(language);
		if (aqiLevel == 0)
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(0));
		else if ((aqiLevel > 0) && (aqiLevel <= 50))
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(50));
		else if ((aqiLevel > 50) && (aqiLevel <= 100))
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(100));
		else if ((aqiLevel > 100) && (aqiLevel <= 150))
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(150));
		else if ((aqiLevel > 150) && (aqiLevel <= 200))
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(200));
		else if ((aqiLevel > 200) && (aqiLevel <= 300))
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(300));
		else if ((aqiLevel > 300) && (aqiLevel < 500))
			return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
					Integer.valueOf(500));
		return WeatherConstants.AQI_DESC_LANGUAGE_MAP.get(languageKey).get(
				Integer.valueOf(500));
	}

	public static String getAqiLevel(int aqiLevel, String language) {
		String languageKey = checkLanguageSuport(language);
		if (aqiLevel == 0)
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(0));
		else if ((aqiLevel > 0) && (aqiLevel <= 50))
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(50));
		else if ((aqiLevel > 50) && (aqiLevel <= 100))
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(100));
		else if ((aqiLevel > 100) && (aqiLevel <= 150))
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(150));
		else if ((aqiLevel > 150) && (aqiLevel <= 200))
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(200));
		else if ((aqiLevel > 200) && (aqiLevel <= 300))
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(300));
		else if ((aqiLevel > 300) && (aqiLevel < 500))
			return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey)
					.get(Integer.valueOf(500));
		return WeatherConstants.AQI_LEVEL_LANGUAGE_MAP.get(languageKey).get(
				Integer.valueOf(500));
	}

	public static String getChina(String paramString) {
		String str = checkLanguageSuport(paramString);
		return ((String) WeatherConstants.CHINA_LANGUAGE_MAP.get(str));
	}

	public static int getHumidity(String humidity) {
		return Integer.parseInt(humidity.split("%")[0]);
	}

	public static String getIndexDesc(String indexTitle, String indexType,
			String language) {
		String languageKey = checkLanguageSuport(language);
		return WeatherConstants.INDEX_DESC_LANGUAGE_MAP.get(languageKey)
				.get(indexTitle).get(indexType);
	}

	public static String getIndexDetail(String paramString1,
			String paramString2, String language) {
		String languageKey = checkLanguageSuport(language);
		return WeatherConstants.INDEX_DETAIL_LANGUAGE_MAP.get(languageKey)
				.get(paramString1).get(paramString2);
	}

	public static String getIndexTitle(String indexTitleKey, String language) {
		String languageKey = checkLanguageSuport(language);
		return WeatherConstants.INDEX_LANGUAGE_MAP.get(languageKey).get(
				indexTitleKey);
	}

	public static Integer getIndexType(String indexTypeKey) {
		return WeatherConstants.INDEX_TYPE.get(indexTypeKey);
	}

	public static int getIntFromJSON(JSONObject jsonObject, String paramString) {
		int result = WeatherConstants.NO_VALUE_FLAG;
		try {
			result = jsonObject.getInt(paramString);
		} catch (JSONException e) {
		}
		return result;
	}

	public static String getWeather(String weatherStr) {
		String[] arrayOfString = weatherStr.split("转");
		if (arrayOfString.length > 1) {
			String str1 = (String) WeatherConstants.CN_WEATHER_TYPE_MAP
					.get(arrayOfString[0]);
			String str2 = (String) WeatherConstants.CN_WEATHER_TYPE_MAP
					.get(arrayOfString[1]);
			return str1 + "-" + str2;
		}
		return ((String) WeatherConstants.CN_WEATHER_TYPE_MAP.get(weatherStr));
	}

	public static WeatherName getWeatherName(String weatherStr, String language) {
		WeatherName weatherName = new WeatherName();
		String languageKey = checkLanguageSuport(language);
		String[] strs = weatherStr.split("转");
		if (strs.length > 1) {
			String str4 = WeatherConstants.CN_WEATHER_TYPE_MAP.get(strs[0]);
			String str5 = WeatherConstants.CN_WEATHER_TYPE_MAP.get(strs[1]);
			String str6 = WeatherConstants.WEATHER_TYPE_LANGUAGE_MAP.get(
					languageKey).get(str4);
			String str7 = WeatherConstants.WEATHER_TYPE_LANGUAGE_MAP.get(
					languageKey).get(str5);
			String str8 = WeatherConstants.TRANSFER_LANGUAGE_MAP
					.get(languageKey);
			weatherName.setName(str6 + str8 + str7);
			weatherName.setFrom(str6);
			weatherName.setTo(str7);
			return weatherName;
		}
		String key = WeatherConstants.CN_WEATHER_TYPE_MAP.get(weatherStr);
		String name = WeatherConstants.WEATHER_TYPE_LANGUAGE_MAP.get(
				languageKey).get(key);
		weatherName.setName(name);
		weatherName.setFrom(name);
		weatherName.setTo(name);
		return weatherName;
	}

	public static String getWind(String windStr, String language) {
		String resultStr = "";
		String[] winds = windStr.split("转");
		String languageKey = checkLanguageSuport(language);
		if (winds.length > 1) {
			String windReal = WeatherConstants.CN_WIND_TYPE_MAP.get(winds[0]);
			resultStr = WeatherConstants.WIND_TYPE_LANGUAGE_MAP
					.get(languageKey).get(windReal);
			if (resultStr == null)
				resultStr = (String) WeatherConstants.CN_WIND_TYPE_MAP
						.get(winds[1]);
		} else {
			resultStr = WeatherConstants.WIND_TYPE_LANGUAGE_MAP
					.get(languageKey).get(windStr);
			if (resultStr == null)
				resultStr = (String) WeatherConstants.CN_WIND_TYPE_MAP
						.get(resultStr);
		}
		return resultStr;
	}

	public static String getWind(String fx, String fl, String language) {
		String resultStr = "";
		String fxValue = WeatherConstants.CN_WIND_TYPE_MAP.get(fx);
		if (fxValue == null)
			fxValue = "0";
		String languageKey = checkLanguageSuport(language);
		String[] winds = fl.split("转");
		if (winds.length > 1) {
			String windBefore = (String) (WeatherConstants.WIND_LEVEL_LANGUAGE_MAP
					.get(languageKey)).get(winds[0]);
			String windAfter = (String) (WeatherConstants.WIND_LEVEL_LANGUAGE_MAP
					.get(languageKey)).get(winds[1]);
			String transferStr = (String) WeatherConstants.TRANSFER_LANGUAGE_MAP
					.get(languageKey);
			resultStr = WeatherConstants.WIND_TYPE_LANGUAGE_MAP
					.get(languageKey).get(fxValue)
					+ WeatherConstants.WIND_TYPE_CONNECTTOR_LANGUAGE_MAP
							.get(languageKey)
					+ windBefore
					+ transferStr
					+ windAfter;
		} else {
			resultStr = (WeatherConstants.WIND_TYPE_LANGUAGE_MAP
					.get(languageKey)).get(fxValue)
					+ WeatherConstants.WIND_TYPE_CONNECTTOR_LANGUAGE_MAP
							.get(languageKey)
					+ WeatherConstants.WIND_LEVEL_LANGUAGE_MAP.get(languageKey)
							.get(fl);
		}
		if ((!(TextUtils.isEmpty(language)))
				&& (language.toLowerCase().contains("en")))
			resultStr = "Wind: " + resultStr;
		return resultStr;
	}

	public static String getWindIndexDetail(String windIndex, String language) {
		String languageKey = checkLanguageSuport(language);
		String[] strs = windIndex.split("转");
		if (strs.length > 1)
			return WeatherConstants.WIND_LEVEL_DETAIL_LANGUAGE_MAP.get(languageKey)
					.get(strs[1]);
		return WeatherConstants.WIND_LEVEL_DETAIL_LANGUAGE_MAP.get(languageKey).get(
				windIndex);
	}

	public static class WeatherName {
		String from;
		String name;
		String to;

		public String getFrom() {
			return this.from;
		}

		public String getName() {
			return this.name;
		}

		public String getTo() {
			return this.to;
		}

		public void setFrom(String paramString) {
			this.from = paramString;
		}

		public void setName(String paramString) {
			this.name = paramString;
		}

		public void setTo(String paramString) {
			this.to = paramString;
		}
	}
}
