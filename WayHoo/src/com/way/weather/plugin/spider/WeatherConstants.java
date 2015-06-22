package com.way.weather.plugin.spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeatherConstants {
	public static final String AIRCURE_INDEX = "晾晒指数";
	public static final Map<String, String> AIRCURE_INDEX_DESC_CN_MAP;
	public static final Map<String, String> AIRCURE_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> AIRCURE_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> AIRCURE_INDEX_DESC_TW_MAP;
	public static final Map<String, String> AIRCURE_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> AIRCURE_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> AIRCURE_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> AIRCURE_INDEX_DETAIL_TW_MAP;
	public static final Map<String, String> ALERM_LEVEL_CN_MAP;
	public static final Map<String, String> ALERM_LEVEL_EN_MAP;
	public static final int AQI_DANGEROUS = 300;
	public static final Map<Integer, String> AQI_DESC_CN_MAP;
	public static final Map<Integer, String> AQI_DESC_EN_MAP;
	public static final Map<String, Map<Integer, String>> AQI_DESC_LANGUAGE_MAP;
	public static final Map<Integer, String> AQI_DESC_TW_MAP;
	public static final int AQI_FINE = 100;
	public static final int AQI_GOOD = 50;
	public static final int AQI_HAZARDOUS = 500;
	public static final Map<Integer, String> AQI_LEVEL_CN_MAP;
	public static final Map<Integer, String> AQI_LEVEL_EN_MAP;
	public static final Map<String, Map<Integer, String>> AQI_LEVEL_LANGUAGE_MAP;
	public static final Map<Integer, String> AQI_LEVEL_TW_MAP;
	public static final int AQI_LIGHT_POLLUTION = 150;
	public static final int AQI_PURE = 0;
	public static final Map<String, String> AQI_SOURCE_LANGUAGE_MAP;
	public static final int AQI_UNHEALTHY = 200;
	public static final String CARWASH_INDEX = "洗车指数";
	public static final Map<String, String> CARWASH_INDEX_DESC_CN_MAP;
	public static final Map<String, String> CARWASH_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> CARWASH_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> CARWASH_INDEX_DESC_TW_MAP;
	public static final Map<String, String> CARWASH_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> CARWASH_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> CARWASH_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> CARWASH_INDEX_DETAIL_TW_MAP;
	public static final Map<String, String> CHINA_LANGUAGE_MAP;
	public static final Map<String, String> CN_ALERM_LEVEL_MAP;
	public static final Map<String, String> CN_WEATHER_TYPE_MAP;
	public static final Map<String, String> CN_WIND_TYPE_MAP;
	public static final String COMFORT_INDEX = "舒适度指数";
	public static final Map<String, String> COMFORT_INDEX_DESC_CN_MAP;
	public static final Map<String, String> COMFORT_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> COMFORT_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> COMFORT_INDEX_DESC_TW_MAP;
	public static final Map<String, String> COMFORT_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> COMFORT_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> COMFORT_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> COMFORT_INDEX_DETAIL_TW_MAP;
	public static final String DRESS_48H_INDEX = "48小时穿衣指数";
	public static final String DRESS_INDEX = "穿衣指数";
	public static final Map<String, String> DRESS_INDEX_DESC_CN_MAP;
	public static final Map<String, String> DRESS_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> DRESS_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> DRESS_INDEX_DESC_TW_MAP;
	public static final Map<String, String> DRESS_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> DRESS_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> DRESS_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> DRESS_INDEX_DETAIL_TW_MAP;
	public static final Map<String, String> EN_ALERM_LEVEL_MAP;
	public static final Map<String, String> EN_WEATHER_TYPE_MAP;
	public static final Map<String, String> EN_WIND_TYPE_MAP;
	public static final String EXERCISE_INDEX = "晨练指数";
	public static final Map<String, String> EXERCISE_INDEX_DESC_CN_MAP;
	public static final Map<String, String> EXERCISE_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> EXERCISE_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> EXERCISE_INDEX_DESC_TW_MAP;
	public static final Map<String, String> EXERCISE_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> EXERCISE_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> EXERCISE_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> EXERCISE_INDEX_DETAIL_TW_MAP;
	public static final Map<String, String> INDEX_CN_MAP;
	public static final Map<String, Map<String, String>> INDEX_DESC_CN_MAP;
	public static final Map<String, Map<String, String>> INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, Map<String, String>>> INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, Map<String, String>> INDEX_DESC_TW_MAP;
	public static final Map<String, Map<String, String>> INDEX_DETAIL_CN_MAP;
	public static final Map<String, Map<String, String>> INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, Map<String, String>>> INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, Map<String, String>> INDEX_DETAIL_TW_MAP;
	public static final Map<String, String> INDEX_EN_MAP;
	public static final Map<String, Map<String, String>> INDEX_LANGUAGE_MAP;
	public static final Map<String, String> INDEX_OLD;
	public static final Map<String, String> INDEX_TW_MAP;
	public static final Map<String, Integer> INDEX_TYPE;
	public static final Integer NO_VALUE_FLAG = Integer.valueOf(-999);
	public static final Map<Integer, String> OLD_ALERM_LEVEL_EN_MAP;
	public static final Map<String, String> PROVINCE_FAKE_CITY_MAP;
	public static final List<String> SURPORTTED_LANGUAGE_LIST;
	public static final Map<String, String> TRANSFER_LANGUAGE_MAP = new HashMap<String, String>();
	public static final String TRAVEL_INDEX = "旅游指数";
	public static final Map<String, String> TRAVEL_INDEX_DESC_CN_MAP;
	public static final Map<String, String> TRAVEL_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> TRAVEL_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> TRAVEL_INDEX_DESC_TW_MAP;
	public static final Map<String, String> TRAVEL_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> TRAVEL_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> TRAVEL_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> TRAVEL_INDEX_DETAIL_TW_MAP;
	public static final String ULTRAVIOLET_48H_INDEX = "48小时紫外线指数";
	public static final String ULTRAVIOLET_INDEX = "紫外线指数";
	public static final Map<String, String> ULTRAVIOLET_INDEX_DESC_CN_MAP;
	public static final Map<String, String> ULTRAVIOLET_INDEX_DESC_EN_MAP;
	public static final Map<String, Map<String, String>> ULTRAVIOLET_INDEX_DESC_LANGUAGE_MAP;
	public static final Map<String, String> ULTRAVIOLET_INDEX_DESC_TW_MAP;
	public static final Map<String, String> ULTRAVIOLET_INDEX_DETAIL_CN_MAP;
	public static final Map<String, String> ULTRAVIOLET_INDEX_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> ULTRAVIOLET_INDEX_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> ULTRAVIOLET_INDEX_DETAIL_TW_MAP;
	public static final Map<String, Integer> WEATHER_ANIMATION_MAP;
	public static final Map<String, String> WEATHER_TYPE_CN_MAP;
	public static final Map<String, String> WEATHER_TYPE_EN_MAP;
	public static final Map<String, Map<String, String>> WEATHER_TYPE_LANGUAGE_MAP;
	public static final Map<String, String> WEATHER_TYPE_TW_MAP;
	public static final Map<String, String> WIND_LEVEL_CN_MAP;
	public static final Map<String, String> WIND_LEVEL_DETAIL_CN_MAP;
	public static final Map<String, String> WIND_LEVEL_DETAIL_EN_MAP;
	public static final Map<String, Map<String, String>> WIND_LEVEL_DETAIL_LANGUAGE_MAP;
	public static final Map<String, String> WIND_LEVEL_DETAIL_TW_MAP;
	public static final Map<String, String> WIND_LEVEL_EN_CN_MAP;
	public static final Map<String, String> WIND_LEVEL_EN_MAP;
	public static final String WIND_LEVEL_INDEX = "风力指数";
	public static final Map<String, String> WIND_LEVEL_KEY_CN_MAP;
	public static final Map<String, Map<String, String>> WIND_LEVEL_LANGUAGE_MAP;
	public static final Map<String, String> WIND_LEVEL_TW_MAP;
	public static final Map<String, String> WIND_TYPE_CN_MAP;
	public static final Map<String, String> WIND_TYPE_CONNECTTOR_LANGUAGE_MAP;
	public static final Map<String, String> WIND_TYPE_EN_MAP;
	public static final Map<String, Map<String, String>> WIND_TYPE_LANGUAGE_MAP;
	public static final String WIND_TYPE_NULL = "0";
	public static final Map<String, String> WIND_TYPE_TW_MAP;

	static {
		TRANSFER_LANGUAGE_MAP
				.put(Locale.US.toString().toLowerCase(), " later ");
		TRANSFER_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(), "转");
		TRANSFER_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(), "轉");
		CHINA_LANGUAGE_MAP = new HashMap();
		CHINA_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(), "China");
		CHINA_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(), "中国");
		CHINA_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(), "中國");
		WEATHER_TYPE_CN_MAP = new HashMap();
		WEATHER_TYPE_EN_MAP = new HashMap();
		WEATHER_TYPE_TW_MAP = new HashMap();
		WEATHER_TYPE_LANGUAGE_MAP = new HashMap();
		WEATHER_TYPE_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				WEATHER_TYPE_EN_MAP);
		WEATHER_TYPE_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				WEATHER_TYPE_CN_MAP);
		WEATHER_TYPE_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				WEATHER_TYPE_TW_MAP);
		CN_WEATHER_TYPE_MAP = new HashMap();
		EN_WEATHER_TYPE_MAP = new HashMap();
		WEATHER_TYPE_CN_MAP.put("00", "晴");
		WEATHER_TYPE_CN_MAP.put("01", "多云");
		WEATHER_TYPE_CN_MAP.put("02", "阴");
		WEATHER_TYPE_CN_MAP.put("03", "阵雨");
		WEATHER_TYPE_CN_MAP.put("04", "雷阵雨");
		WEATHER_TYPE_CN_MAP.put("05", "雷阵雨伴有冰雹");
		WEATHER_TYPE_CN_MAP.put("06", "雨夹雪");
		WEATHER_TYPE_CN_MAP.put("07", "小雨");
		WEATHER_TYPE_CN_MAP.put("08", "中雨");
		WEATHER_TYPE_CN_MAP.put("09", "大雨");
		WEATHER_TYPE_CN_MAP.put("10", "暴雨");
		WEATHER_TYPE_CN_MAP.put("11", "大暴雨");
		WEATHER_TYPE_CN_MAP.put("12", "特大暴雨");
		WEATHER_TYPE_CN_MAP.put("13", "阵雪");
		WEATHER_TYPE_CN_MAP.put("14", "小雪");
		WEATHER_TYPE_CN_MAP.put("15", "中雪");
		WEATHER_TYPE_CN_MAP.put("16", "大雪");
		WEATHER_TYPE_CN_MAP.put("17", "暴雪");
		WEATHER_TYPE_CN_MAP.put("18", "雾");
		WEATHER_TYPE_CN_MAP.put("19", "冻雨");
		WEATHER_TYPE_CN_MAP.put("20", "沙尘暴");
		WEATHER_TYPE_CN_MAP.put("21", "小到中雨");
		WEATHER_TYPE_CN_MAP.put("22", "中到大雨");
		WEATHER_TYPE_CN_MAP.put("23", "大到暴雨");
		WEATHER_TYPE_CN_MAP.put("24", "暴雨到大暴雨");
		WEATHER_TYPE_CN_MAP.put("25", "大暴雨到特大暴雨");
		WEATHER_TYPE_CN_MAP.put("26", "小到中雪");
		WEATHER_TYPE_CN_MAP.put("27", "中到大雪");
		WEATHER_TYPE_CN_MAP.put("28", "大到暴雪");
		WEATHER_TYPE_CN_MAP.put("29", "浮尘");
		WEATHER_TYPE_CN_MAP.put("30", "扬沙");
		WEATHER_TYPE_CN_MAP.put("31", "强沙尘暴");
		WEATHER_TYPE_CN_MAP.put("53", "霾");
		WEATHER_TYPE_CN_MAP.put("99", "无");
		WEATHER_TYPE_EN_MAP.put("00", "Sunny");
		WEATHER_TYPE_EN_MAP.put("01", "Cloudy");
		WEATHER_TYPE_EN_MAP.put("02", "Overcast");
		WEATHER_TYPE_EN_MAP.put("03", "Shower");
		WEATHER_TYPE_EN_MAP.put("04", "Thunderstorm");
		WEATHER_TYPE_EN_MAP.put("05", "Thunderstorm with hail");
		WEATHER_TYPE_EN_MAP.put("06", "Sleet");
		WEATHER_TYPE_EN_MAP.put("07", "Light rain");
		WEATHER_TYPE_EN_MAP.put("08", "Moderate rain");
		WEATHER_TYPE_EN_MAP.put("09", "Heavy rain");
		WEATHER_TYPE_EN_MAP.put("10", "Storm");
		WEATHER_TYPE_EN_MAP.put("11", "Heavy storm");
		WEATHER_TYPE_EN_MAP.put("12", "Severe storm");
		WEATHER_TYPE_EN_MAP.put("13", "Snow flurries");
		WEATHER_TYPE_EN_MAP.put("14", "Light snow");
		WEATHER_TYPE_EN_MAP.put("15", "Moderate snow");
		WEATHER_TYPE_EN_MAP.put("16", "Heavy snow");
		WEATHER_TYPE_EN_MAP.put("17", "Blizzard");
		WEATHER_TYPE_EN_MAP.put("18", "Foggy");
		WEATHER_TYPE_EN_MAP.put("19", "Freezing rain");
		WEATHER_TYPE_EN_MAP.put("20", "Duststorm");
		WEATHER_TYPE_EN_MAP.put("21", "Light to moderate rain");
		WEATHER_TYPE_EN_MAP.put("22", "Moderate to heavy rain");
		WEATHER_TYPE_EN_MAP.put("23", "Heavy rain to storm");
		WEATHER_TYPE_EN_MAP.put("24", "Storm to heavy storm");
		WEATHER_TYPE_EN_MAP.put("25", "Heavy to severe storm");
		WEATHER_TYPE_EN_MAP.put("26", "Light to moderate snow");
		WEATHER_TYPE_EN_MAP.put("27", "Moderate to heavy snow");
		WEATHER_TYPE_EN_MAP.put("28", "Heavy snow to blizzard");
		WEATHER_TYPE_EN_MAP.put("29", "Dust");
		WEATHER_TYPE_EN_MAP.put("30", "Sand");
		WEATHER_TYPE_EN_MAP.put("31", "Sandstorm");
		WEATHER_TYPE_EN_MAP.put("53", "Haze");
		WEATHER_TYPE_EN_MAP.put("99", "Unknown");
		WEATHER_TYPE_TW_MAP.put("00", "晴");
		WEATHER_TYPE_TW_MAP.put("01", "多雲");
		WEATHER_TYPE_TW_MAP.put("02", "陰");
		WEATHER_TYPE_TW_MAP.put("03", "陣雨");
		WEATHER_TYPE_TW_MAP.put("04", "雷陣雨");
		WEATHER_TYPE_TW_MAP.put("05", "雷陣雨伴有冰雹");
		WEATHER_TYPE_TW_MAP.put("06", "雨夾雪");
		WEATHER_TYPE_TW_MAP.put("07", "小雨");
		WEATHER_TYPE_TW_MAP.put("08", "中雨");
		WEATHER_TYPE_TW_MAP.put("09", "大雨");
		WEATHER_TYPE_TW_MAP.put("10", "暴雨");
		WEATHER_TYPE_TW_MAP.put("11", "大暴雨");
		WEATHER_TYPE_TW_MAP.put("12", "特大暴雨");
		WEATHER_TYPE_TW_MAP.put("13", "陣雪");
		WEATHER_TYPE_TW_MAP.put("14", "小雪");
		WEATHER_TYPE_TW_MAP.put("15", "中雪");
		WEATHER_TYPE_TW_MAP.put("16", "大雪");
		WEATHER_TYPE_TW_MAP.put("17", "暴雪");
		WEATHER_TYPE_TW_MAP.put("18", "霧");
		WEATHER_TYPE_TW_MAP.put("19", "冻雨");
		WEATHER_TYPE_TW_MAP.put("20", "沙塵暴");
		WEATHER_TYPE_TW_MAP.put("21", "小到中雨");
		WEATHER_TYPE_TW_MAP.put("22", "中到大雨");
		WEATHER_TYPE_TW_MAP.put("23", "大到暴雨");
		WEATHER_TYPE_TW_MAP.put("24", "暴雨到大暴雨");
		WEATHER_TYPE_TW_MAP.put("25", "大暴雨到特大暴雨");
		WEATHER_TYPE_TW_MAP.put("26", "小到中雪");
		WEATHER_TYPE_TW_MAP.put("27", "中到大雪");
		WEATHER_TYPE_TW_MAP.put("28", "大到暴雪");
		WEATHER_TYPE_TW_MAP.put("29", "浮塵");
		WEATHER_TYPE_TW_MAP.put("30", "揚沙");
		WEATHER_TYPE_TW_MAP.put("31", "強沙塵暴");
		WEATHER_TYPE_TW_MAP.put("53", "霾");
		WEATHER_TYPE_TW_MAP.put("99", "无");
		CN_WEATHER_TYPE_MAP.put("晴", "00");
		CN_WEATHER_TYPE_MAP.put("多云", "01");
		CN_WEATHER_TYPE_MAP.put("阴", "02");
		CN_WEATHER_TYPE_MAP.put("阵雨", "03");
		CN_WEATHER_TYPE_MAP.put("雷阵雨", "04");
		CN_WEATHER_TYPE_MAP.put("雷阵雨伴有冰雹", "05");
		CN_WEATHER_TYPE_MAP.put("雨夹雪", "06");
		CN_WEATHER_TYPE_MAP.put("小雨", "07");
		CN_WEATHER_TYPE_MAP.put("中雨", "08");
		CN_WEATHER_TYPE_MAP.put("大雨", "09");
		CN_WEATHER_TYPE_MAP.put("暴雨", "10");
		CN_WEATHER_TYPE_MAP.put("大暴雨", "11");
		CN_WEATHER_TYPE_MAP.put("特大暴雨", "12");
		CN_WEATHER_TYPE_MAP.put("阵雪", "13");
		CN_WEATHER_TYPE_MAP.put("小雪", "14");
		CN_WEATHER_TYPE_MAP.put("中雪", "15");
		CN_WEATHER_TYPE_MAP.put("大雪", "16");
		CN_WEATHER_TYPE_MAP.put("暴雪", "17");
		CN_WEATHER_TYPE_MAP.put("雾", "18");
		CN_WEATHER_TYPE_MAP.put("冻雨", "19");
		CN_WEATHER_TYPE_MAP.put("沙尘暴", "20");
		CN_WEATHER_TYPE_MAP.put("小到中雨", "21");
		CN_WEATHER_TYPE_MAP.put("中到大雨", "22");
		CN_WEATHER_TYPE_MAP.put("大到暴雨", "23");
		CN_WEATHER_TYPE_MAP.put("暴雨到大暴雨", "24");
		CN_WEATHER_TYPE_MAP.put("大暴雨到特大暴雨", "25");
		CN_WEATHER_TYPE_MAP.put("小到中雪", "26");
		CN_WEATHER_TYPE_MAP.put("中到大雪", "27");
		CN_WEATHER_TYPE_MAP.put("大到暴雪", "28");
		CN_WEATHER_TYPE_MAP.put("浮尘", "29");
		CN_WEATHER_TYPE_MAP.put("扬沙", "30");
		CN_WEATHER_TYPE_MAP.put("强沙尘暴", "31");
		CN_WEATHER_TYPE_MAP.put("霾", "53");
		CN_WEATHER_TYPE_MAP.put("无", "99");
		ALERM_LEVEL_CN_MAP = new HashMap();
		ALERM_LEVEL_EN_MAP = new HashMap();
		CN_ALERM_LEVEL_MAP = new HashMap();
		EN_ALERM_LEVEL_MAP = new HashMap();
		OLD_ALERM_LEVEL_EN_MAP = new HashMap();
		CN_ALERM_LEVEL_MAP.put("蓝色", "01");
		CN_ALERM_LEVEL_MAP.put("黄色", "02");
		CN_ALERM_LEVEL_MAP.put("橙色", "03");
		CN_ALERM_LEVEL_MAP.put("红色", "04");
		EN_ALERM_LEVEL_MAP.put("blue", "01");
		EN_ALERM_LEVEL_MAP.put("yellow", "02");
		EN_ALERM_LEVEL_MAP.put("orange", "03");
		EN_ALERM_LEVEL_MAP.put("re", "04");
		ALERM_LEVEL_CN_MAP.put("01", "蓝色");
		ALERM_LEVEL_CN_MAP.put("02", "黄色");
		ALERM_LEVEL_CN_MAP.put("03", "橙色");
		ALERM_LEVEL_CN_MAP.put("04", "红色");
		ALERM_LEVEL_EN_MAP.put("01", "blue");
		ALERM_LEVEL_EN_MAP.put("02", "yellow");
		ALERM_LEVEL_EN_MAP.put("03", "orange");
		ALERM_LEVEL_EN_MAP.put("04", "red");
		OLD_ALERM_LEVEL_EN_MAP.put(Integer.valueOf(1), "blue");
		OLD_ALERM_LEVEL_EN_MAP.put(Integer.valueOf(2), "yellow");
		OLD_ALERM_LEVEL_EN_MAP.put(Integer.valueOf(3), "orange");
		OLD_ALERM_LEVEL_EN_MAP.put(Integer.valueOf(4), "red");
		WIND_TYPE_CN_MAP = new HashMap<String, String>();
		WIND_TYPE_EN_MAP = new HashMap<String, String>();
		WIND_TYPE_TW_MAP = new HashMap<String, String>();
		CN_WIND_TYPE_MAP = new HashMap<String, String>();
		EN_WIND_TYPE_MAP = new HashMap<String, String>();
		WIND_TYPE_CONNECTTOR_LANGUAGE_MAP = new HashMap<String, String>();
		WIND_TYPE_CONNECTTOR_LANGUAGE_MAP.put(Locale.US.toString()
				.toLowerCase(), ", speed ");
		WIND_TYPE_CONNECTTOR_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), "，风力");
		WIND_TYPE_CONNECTTOR_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), "，風力");
		WIND_TYPE_LANGUAGE_MAP = new HashMap<String, Map<String, String>>();
		WIND_TYPE_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				WIND_TYPE_EN_MAP);
		WIND_TYPE_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				WIND_TYPE_CN_MAP);
		WIND_TYPE_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				WIND_TYPE_TW_MAP);
		WIND_TYPE_CN_MAP.put("0", "微风");
		WIND_TYPE_CN_MAP.put("1", "东北风");
		WIND_TYPE_CN_MAP.put("2", "东风");
		WIND_TYPE_CN_MAP.put("3", "东南风");
		WIND_TYPE_CN_MAP.put("4", "南风");
		WIND_TYPE_CN_MAP.put("5", "西南风");
		WIND_TYPE_CN_MAP.put("6", "西风");
		WIND_TYPE_CN_MAP.put("7", "西北风");
		WIND_TYPE_CN_MAP.put("8", "北风");
		WIND_TYPE_CN_MAP.put("9", "旋转风");
		WIND_TYPE_EN_MAP.put("0", "No wind");
		WIND_TYPE_EN_MAP.put("1", "Northeast");
		WIND_TYPE_EN_MAP.put("2", "East");
		WIND_TYPE_EN_MAP.put("3", "Southeast");
		WIND_TYPE_EN_MAP.put("4", "South");
		WIND_TYPE_EN_MAP.put("5", "Southwest");
		WIND_TYPE_EN_MAP.put("6", "West");
		WIND_TYPE_EN_MAP.put("7", "Northwest");
		WIND_TYPE_EN_MAP.put("8", "North");
		WIND_TYPE_EN_MAP.put("9", "Variable");
		WIND_TYPE_TW_MAP.put("0", "微風");
		WIND_TYPE_TW_MAP.put("1", "東北風");
		WIND_TYPE_TW_MAP.put("2", "東風");
		WIND_TYPE_TW_MAP.put("3", "東南風");
		WIND_TYPE_TW_MAP.put("4", "南風");
		WIND_TYPE_TW_MAP.put("5", "西南風");
		WIND_TYPE_TW_MAP.put("6", "西風");
		WIND_TYPE_TW_MAP.put("7", "西北風");
		WIND_TYPE_TW_MAP.put("8", "北風");
		WIND_TYPE_TW_MAP.put("9", "旋轉風");
		CN_WIND_TYPE_MAP.put("微风", "0");
		CN_WIND_TYPE_MAP.put("无持续风向", "0");
		CN_WIND_TYPE_MAP.put("东北风", "1");
		CN_WIND_TYPE_MAP.put("东风", "2");
		CN_WIND_TYPE_MAP.put("东南风", "3");
		CN_WIND_TYPE_MAP.put("南风", "4");
		CN_WIND_TYPE_MAP.put("西南风", "5");
		CN_WIND_TYPE_MAP.put("西风", "6");
		CN_WIND_TYPE_MAP.put("西北风", "7");
		CN_WIND_TYPE_MAP.put("北风", "8");
		CN_WIND_TYPE_MAP.put("旋转风", "9");
		EN_WIND_TYPE_MAP.put("No wind", "0");
		EN_WIND_TYPE_MAP.put("Northeast", "1");
		EN_WIND_TYPE_MAP.put("East", "2");
		EN_WIND_TYPE_MAP.put("Southeast", "3");
		EN_WIND_TYPE_MAP.put("South", "4");
		EN_WIND_TYPE_MAP.put("Southwest", "5");
		EN_WIND_TYPE_MAP.put("West", "6");
		EN_WIND_TYPE_MAP.put("Northwest", "7");
		EN_WIND_TYPE_MAP.put("North", "8");
		EN_WIND_TYPE_MAP.put("Whirl wind", "9");
		WIND_LEVEL_KEY_CN_MAP = new HashMap<String, String>();
		WIND_LEVEL_KEY_CN_MAP.put("0", "小于3级");
		WIND_LEVEL_KEY_CN_MAP.put("1", "3-4级");
		WIND_LEVEL_KEY_CN_MAP.put("2", "4-5级");
		WIND_LEVEL_KEY_CN_MAP.put("3", "5-6级");
		WIND_LEVEL_KEY_CN_MAP.put("4", "6-7级");
		WIND_LEVEL_KEY_CN_MAP.put("5", "7-8级");
		WIND_LEVEL_KEY_CN_MAP.put("6", "8-9级");
		WIND_LEVEL_KEY_CN_MAP.put("7", "9-10级");
		WIND_LEVEL_KEY_CN_MAP.put("8", "10-11级");
		WIND_LEVEL_KEY_CN_MAP.put("9", "11-12级");
		WIND_LEVEL_EN_MAP = new HashMap<String, String>();
		WIND_LEVEL_EN_CN_MAP = new HashMap<String, String>();
		WIND_LEVEL_TW_MAP = new HashMap<String, String>();
		WIND_LEVEL_CN_MAP = new HashMap<String, String>();
		WIND_LEVEL_LANGUAGE_MAP = new HashMap<String, Map<String, String>>();
		WIND_LEVEL_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				WIND_LEVEL_EN_MAP);
		WIND_LEVEL_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				WIND_LEVEL_CN_MAP);
		WIND_LEVEL_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				WIND_LEVEL_TW_MAP);
		WIND_LEVEL_CN_MAP.put("0级", "小于3级");
		WIND_LEVEL_CN_MAP.put("1级", "1级");
		WIND_LEVEL_CN_MAP.put("2级", "2级");
		WIND_LEVEL_CN_MAP.put("3级", "3级");
		WIND_LEVEL_CN_MAP.put("4级", "4级");
		WIND_LEVEL_CN_MAP.put("5级", "5级");
		WIND_LEVEL_CN_MAP.put("6级", "6级");
		WIND_LEVEL_CN_MAP.put("7级", "7级");
		WIND_LEVEL_CN_MAP.put("8级", "8级");
		WIND_LEVEL_CN_MAP.put("9级", "9级");
		WIND_LEVEL_CN_MAP.put("10级", "10级");
		WIND_LEVEL_CN_MAP.put("11级", "11级");
		WIND_LEVEL_CN_MAP.put("12级", "12级");
		WIND_LEVEL_CN_MAP.put("小于3级", "小于3级");
		WIND_LEVEL_CN_MAP.put("3-4级", "3-4级");
		WIND_LEVEL_CN_MAP.put("4-5级", "4-5级");
		WIND_LEVEL_CN_MAP.put("5-6级", "5-6级");
		WIND_LEVEL_CN_MAP.put("6-7级", "6-7级");
		WIND_LEVEL_CN_MAP.put("7-8级", "7-8级");
		WIND_LEVEL_CN_MAP.put("8-9级", "8-9级");
		WIND_LEVEL_CN_MAP.put("9-10级", "9-10级");
		WIND_LEVEL_CN_MAP.put("10-11级", "10-11级");
		WIND_LEVEL_CN_MAP.put("11-12级", "11-12级");
		WIND_LEVEL_EN_MAP.put("0级", "< 16km/h");
		WIND_LEVEL_EN_MAP.put("1级", "< 16km/h");
		WIND_LEVEL_EN_MAP.put("2级", "< 16km/h");
		WIND_LEVEL_EN_MAP.put("3级", "16km/h");
		WIND_LEVEL_EN_MAP.put("4级", "27km/h");
		WIND_LEVEL_EN_MAP.put("5级", "40km/h");
		WIND_LEVEL_EN_MAP.put("6级", "55km/h");
		WIND_LEVEL_EN_MAP.put("7级", "69km/h");
		WIND_LEVEL_EN_MAP.put("8级", "87km/h");
		WIND_LEVEL_EN_MAP.put("9级", "105km/h");
		WIND_LEVEL_EN_MAP.put("10级", "124km/h");
		WIND_LEVEL_EN_MAP.put("11级", "143km/h");
		WIND_LEVEL_EN_MAP.put("12级", "164km/h");
		WIND_LEVEL_EN_MAP.put("小于3级", "< 16km/h");
		WIND_LEVEL_EN_MAP.put("3-4级", "16-27km/h");
		WIND_LEVEL_EN_MAP.put("4-5级", "27-40km/h");
		WIND_LEVEL_EN_MAP.put("5-6级", "40-55km/h");
		WIND_LEVEL_EN_MAP.put("6-7级", "55-69km/h");
		WIND_LEVEL_EN_MAP.put("7-8级", "69-87km/h");
		WIND_LEVEL_EN_MAP.put("8-9级", "87-105km/h");
		WIND_LEVEL_EN_MAP.put("9-10级", "105-124km/h");
		WIND_LEVEL_EN_MAP.put("10-11级", "124-143km/h");
		WIND_LEVEL_EN_MAP.put("11-12级", "143-164km/h");
		WIND_LEVEL_TW_MAP.put("0级", "小于3級");
		WIND_LEVEL_TW_MAP.put("1级", "1級");
		WIND_LEVEL_TW_MAP.put("2级", "2級");
		WIND_LEVEL_TW_MAP.put("3级", "3級");
		WIND_LEVEL_TW_MAP.put("4级", "4級");
		WIND_LEVEL_TW_MAP.put("5级", "5級");
		WIND_LEVEL_TW_MAP.put("6级", "6級");
		WIND_LEVEL_TW_MAP.put("7级", "7級");
		WIND_LEVEL_TW_MAP.put("8级", "8級");
		WIND_LEVEL_TW_MAP.put("9级", "9級");
		WIND_LEVEL_TW_MAP.put("10级", "10級");
		WIND_LEVEL_TW_MAP.put("11级", "11級");
		WIND_LEVEL_TW_MAP.put("12级", "12級");
		WIND_LEVEL_TW_MAP.put("小于3级", "小于3級");
		WIND_LEVEL_TW_MAP.put("3-4级", "3-4級");
		WIND_LEVEL_TW_MAP.put("4-5级", "4-5級");
		WIND_LEVEL_TW_MAP.put("5-6级", "5-6級");
		WIND_LEVEL_TW_MAP.put("6-7级", "6-7級");
		WIND_LEVEL_TW_MAP.put("7-8级", "7-8級");
		WIND_LEVEL_TW_MAP.put("8-9级", "8-9級");
		WIND_LEVEL_TW_MAP.put("9-10级", "9-10級");
		WIND_LEVEL_TW_MAP.put("10-11级", "10-11級");
		WIND_LEVEL_TW_MAP.put("11-12级", "11-12級");
		WIND_LEVEL_DETAIL_EN_MAP = new HashMap();
		WIND_LEVEL_DETAIL_TW_MAP = new HashMap();
		WIND_LEVEL_DETAIL_CN_MAP = new HashMap();
		WIND_LEVEL_DETAIL_LANGUAGE_MAP = new HashMap();
		WIND_LEVEL_DETAIL_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				WIND_LEVEL_DETAIL_EN_MAP);
		WIND_LEVEL_DETAIL_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), WIND_LEVEL_DETAIL_CN_MAP);
		WIND_LEVEL_DETAIL_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), WIND_LEVEL_DETAIL_TW_MAP);
		WIND_LEVEL_DETAIL_CN_MAP.put("小于3级", "微风拂面，感觉不错，建议适当地去户外放松一下。");
		WIND_LEVEL_DETAIL_CN_MAP.put("3-4级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("4-5级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("5-6级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("6-7级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("7-8级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("8-9级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("9-10级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("10-11级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_CN_MAP.put("11-12级", "今天风有点大，外出的话，请做好防护措施。");
		WIND_LEVEL_DETAIL_EN_MAP.put("小于3级", "Comfortable");
		WIND_LEVEL_DETAIL_EN_MAP.put("3-4级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("4-5级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("5-6级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("6-7级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("7-8级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("8-9级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("9-10级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("10-11级", "Wear layers!");
		WIND_LEVEL_DETAIL_EN_MAP.put("11-12级", "Wear layers!");
		WIND_LEVEL_DETAIL_TW_MAP.put("小于3级", "微風拂面，感覺不錯，建議適當地去戶外放鬆一下。");
		WIND_LEVEL_DETAIL_TW_MAP.put("3-4级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("4-5级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("5-6级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("6-7级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("7-8级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("8-9级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("9-10级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("10-11级", "今天風有點大，外出的話，請做好防護措施。");
		WIND_LEVEL_DETAIL_TW_MAP.put("11-12级", "今天風有點大，外出的話，請做好防護措施。");
		WEATHER_ANIMATION_MAP = new HashMap<String, Integer>();
		WEATHER_ANIMATION_MAP.put("晴", Integer.valueOf(0));
		WEATHER_ANIMATION_MAP.put("多云", Integer.valueOf(1));
		WEATHER_ANIMATION_MAP.put("阴", Integer.valueOf(2));
		WEATHER_ANIMATION_MAP.put("雾", Integer.valueOf(3));
		WEATHER_ANIMATION_MAP.put("特大暴雨", Integer.valueOf(4));
		WEATHER_ANIMATION_MAP.put("大暴雨", Integer.valueOf(5));
		WEATHER_ANIMATION_MAP.put("暴雨", Integer.valueOf(6));
		WEATHER_ANIMATION_MAP.put("雷阵雨", Integer.valueOf(7));
		WEATHER_ANIMATION_MAP.put("阵雨", Integer.valueOf(8));
		WEATHER_ANIMATION_MAP.put("大雨", Integer.valueOf(9));
		WEATHER_ANIMATION_MAP.put("中雨", Integer.valueOf(10));
		WEATHER_ANIMATION_MAP.put("小雨", Integer.valueOf(11));
		WEATHER_ANIMATION_MAP.put("雨夹雪", Integer.valueOf(12));
		WEATHER_ANIMATION_MAP.put("暴雪", Integer.valueOf(13));
		WEATHER_ANIMATION_MAP.put("阵雪", Integer.valueOf(14));
		WEATHER_ANIMATION_MAP.put("大雪", Integer.valueOf(15));
		WEATHER_ANIMATION_MAP.put("中雪", Integer.valueOf(16));
		WEATHER_ANIMATION_MAP.put("小雪", Integer.valueOf(17));
		WEATHER_ANIMATION_MAP.put("强沙尘暴", Integer.valueOf(18));
		WEATHER_ANIMATION_MAP.put("沙尘暴", Integer.valueOf(19));
		WEATHER_ANIMATION_MAP.put("沙尘", Integer.valueOf(20));
		WEATHER_ANIMATION_MAP.put("扬沙", Integer.valueOf(21));
		WEATHER_ANIMATION_MAP.put("冰雹", Integer.valueOf(22));
		WEATHER_ANIMATION_MAP.put("浮尘", Integer.valueOf(23));
		WEATHER_ANIMATION_MAP.put("霾", Integer.valueOf(24));
		INDEX_TYPE = new HashMap<String, Integer>();
		INDEX_TYPE.put("风力指数", Integer.valueOf(0));
		INDEX_TYPE.put("紫外线指数", Integer.valueOf(1));
		INDEX_TYPE.put("穿衣指数", Integer.valueOf(2));
		INDEX_TYPE.put("舒适度指数", Integer.valueOf(3));
		INDEX_TYPE.put("洗车指数", Integer.valueOf(4));
		INDEX_TYPE.put("晾晒指数", Integer.valueOf(5));
		INDEX_TYPE.put("晨练指数", Integer.valueOf(6));
		INDEX_TYPE.put("旅游指数", Integer.valueOf(7));
		INDEX_TYPE.put("48小时穿衣指数", Integer.valueOf(8));
		INDEX_TYPE.put("48小时紫外线指数", Integer.valueOf(9));
		INDEX_OLD = new LinkedHashMap<String, String>();
		INDEX_OLD.put("紫外线指数", "index_uv");
		INDEX_OLD.put("穿衣指数", "index");
		INDEX_OLD.put("舒适度指数", "index_co");
		INDEX_OLD.put("洗车指数", "index_xc");
		INDEX_OLD.put("晾晒指数", "index_ls");
		INDEX_OLD.put("晨练指数", "index_cl");
		INDEX_OLD.put("旅游指数", "index_tr");
		INDEX_OLD.put("48小时穿衣指数", "index48");
		INDEX_OLD.put("48小时紫外线指数", "index48_uv");
		EXERCISE_INDEX_DESC_EN_MAP = new HashMap<String, String>();
		EXERCISE_INDEX_DESC_EN_MAP.put("适宜", "OK");
		EXERCISE_INDEX_DESC_EN_MAP.put("较适宜", "Good");
		EXERCISE_INDEX_DESC_EN_MAP.put("较不宜", "Avoid if possible");
		EXERCISE_INDEX_DESC_EN_MAP.put("不宜", "Not recommended");
		EXERCISE_INDEX_DESC_EN_MAP.put("极适宜", "Ideal");
		EXERCISE_INDEX_DESC_CN_MAP = new HashMap<String, String>();
		EXERCISE_INDEX_DESC_CN_MAP.put("适宜", "适宜");
		EXERCISE_INDEX_DESC_CN_MAP.put("较适宜", "较适宜");
		EXERCISE_INDEX_DESC_CN_MAP.put("较不宜", "较不宜");
		EXERCISE_INDEX_DESC_CN_MAP.put("不宜", "不宜");
		EXERCISE_INDEX_DESC_CN_MAP.put("极适宜", "极适宜");
		EXERCISE_INDEX_DESC_TW_MAP = new HashMap<String, String>();
		EXERCISE_INDEX_DESC_TW_MAP.put("适宜", "適宜");
		EXERCISE_INDEX_DESC_TW_MAP.put("较适宜", "較適宜");
		EXERCISE_INDEX_DESC_TW_MAP.put("较不宜", "較不宜");
		EXERCISE_INDEX_DESC_TW_MAP.put("不宜", "不宜");
		EXERCISE_INDEX_DESC_TW_MAP.put("极适宜", "極適宜");
		CARWASH_INDEX_DESC_EN_MAP = new HashMap<String, String>();
		CARWASH_INDEX_DESC_EN_MAP.put("适宜", "OK");
		CARWASH_INDEX_DESC_EN_MAP.put("较适宜", "Good");
		CARWASH_INDEX_DESC_EN_MAP.put("较不宜", "Avoid if possible");
		CARWASH_INDEX_DESC_EN_MAP.put("不宜", "Not recommended");
		CARWASH_INDEX_DESC_EN_MAP.put("极适宜", "Ideal");
		CARWASH_INDEX_DESC_CN_MAP = new HashMap<String, String>();
		CARWASH_INDEX_DESC_CN_MAP.put("适宜", "适宜");
		CARWASH_INDEX_DESC_CN_MAP.put("较适宜", "较适宜");
		CARWASH_INDEX_DESC_CN_MAP.put("较不宜", "较不宜");
		CARWASH_INDEX_DESC_CN_MAP.put("不宜", "不宜");
		CARWASH_INDEX_DESC_CN_MAP.put("极适宜", "极适宜");
		CARWASH_INDEX_DESC_TW_MAP = new HashMap<String, String>();
		CARWASH_INDEX_DESC_TW_MAP.put("适宜", "適宜");
		CARWASH_INDEX_DESC_TW_MAP.put("较适宜", "較適宜");
		CARWASH_INDEX_DESC_TW_MAP.put("较不宜", "較不宜");
		CARWASH_INDEX_DESC_TW_MAP.put("不宜", "不宜");
		CARWASH_INDEX_DESC_TW_MAP.put("极适宜", "極適宜");
		AIRCURE_INDEX_DESC_EN_MAP = new HashMap<String, String>();
		AIRCURE_INDEX_DESC_EN_MAP.put("适宜", "OK");
		AIRCURE_INDEX_DESC_EN_MAP.put("较适宜", "Good");
		AIRCURE_INDEX_DESC_EN_MAP.put("不太适宜", "Not Good");
		AIRCURE_INDEX_DESC_EN_MAP.put("较不宜", "Avoid if possible");
		AIRCURE_INDEX_DESC_EN_MAP.put("不宜", "Not recommended");
		AIRCURE_INDEX_DESC_EN_MAP.put("极适宜", "Ideal");
		AIRCURE_INDEX_DESC_CN_MAP = new HashMap<String, String>();
		AIRCURE_INDEX_DESC_CN_MAP.put("适宜", "适宜");
		AIRCURE_INDEX_DESC_CN_MAP.put("较适宜", "较适宜");
		AIRCURE_INDEX_DESC_CN_MAP.put("不太适宜", "不太适宜");
		AIRCURE_INDEX_DESC_CN_MAP.put("较不宜", "较不宜");
		AIRCURE_INDEX_DESC_CN_MAP.put("不宜", "不宜");
		AIRCURE_INDEX_DESC_CN_MAP.put("极适宜", "极适宜");
		AIRCURE_INDEX_DESC_TW_MAP = new HashMap<String, String>();
		AIRCURE_INDEX_DESC_TW_MAP.put("适宜", "適宜");
		AIRCURE_INDEX_DESC_TW_MAP.put("较适宜", "較適宜");
		AIRCURE_INDEX_DESC_TW_MAP.put("不太适宜", "不太適宜");
		AIRCURE_INDEX_DESC_TW_MAP.put("较不宜", "較不宜");
		AIRCURE_INDEX_DESC_TW_MAP.put("不宜", "不宜");
		AIRCURE_INDEX_DESC_TW_MAP.put("极适宜", "極適宜");
		TRAVEL_INDEX_DESC_EN_MAP = new HashMap<String, String>();
		TRAVEL_INDEX_DESC_EN_MAP.put("适宜", "OK");
		TRAVEL_INDEX_DESC_EN_MAP.put("较适宜", "Good");
		TRAVEL_INDEX_DESC_EN_MAP.put("较不宜", "Avoid if possible");
		TRAVEL_INDEX_DESC_EN_MAP.put("不宜", "Not recommended");
		TRAVEL_INDEX_DESC_EN_MAP.put("极适宜", "Ideal");
		TRAVEL_INDEX_DESC_CN_MAP = new HashMap<String, String>();
		TRAVEL_INDEX_DESC_CN_MAP.put("适宜", "适宜");
		TRAVEL_INDEX_DESC_CN_MAP.put("较适宜", "较适宜");
		TRAVEL_INDEX_DESC_CN_MAP.put("较不宜", "较不宜");
		TRAVEL_INDEX_DESC_CN_MAP.put("不宜", "不宜");
		TRAVEL_INDEX_DESC_CN_MAP.put("极适宜", "极适宜");
		TRAVEL_INDEX_DESC_TW_MAP = new HashMap<String, String>();
		TRAVEL_INDEX_DESC_TW_MAP.put("适宜", "適宜");
		TRAVEL_INDEX_DESC_TW_MAP.put("较适宜", "較適宜");
		TRAVEL_INDEX_DESC_TW_MAP.put("较不宜", "較不宜");
		TRAVEL_INDEX_DESC_TW_MAP.put("不宜", "不宜");
		TRAVEL_INDEX_DESC_TW_MAP.put("极适宜", "極適宜");
		ULTRAVIOLET_INDEX_DESC_EN_MAP = new HashMap<String, String>();
		ULTRAVIOLET_INDEX_DESC_EN_MAP.put("中等", "Moderate");
		ULTRAVIOLET_INDEX_DESC_EN_MAP.put("最弱", "Weak");
		ULTRAVIOLET_INDEX_DESC_EN_MAP.put("弱", "Medium");
		ULTRAVIOLET_INDEX_DESC_EN_MAP.put("强", "Strong");
		ULTRAVIOLET_INDEX_DESC_EN_MAP.put("很强", "Very strong");
		ULTRAVIOLET_INDEX_DESC_CN_MAP = new HashMap<String, String>();
		ULTRAVIOLET_INDEX_DESC_CN_MAP.put("中等", "中等");
		ULTRAVIOLET_INDEX_DESC_CN_MAP.put("最弱", "最弱");
		ULTRAVIOLET_INDEX_DESC_CN_MAP.put("弱", "弱");
		ULTRAVIOLET_INDEX_DESC_CN_MAP.put("强", "强");
		ULTRAVIOLET_INDEX_DESC_CN_MAP.put("很强", "很强");
		ULTRAVIOLET_INDEX_DESC_TW_MAP = new HashMap<String, String>();
		ULTRAVIOLET_INDEX_DESC_TW_MAP.put("中等", "最弱");
		ULTRAVIOLET_INDEX_DESC_TW_MAP.put("最弱", "最弱");
		ULTRAVIOLET_INDEX_DESC_TW_MAP.put("弱", "弱");
		ULTRAVIOLET_INDEX_DESC_TW_MAP.put("强", "強");
		ULTRAVIOLET_INDEX_DESC_TW_MAP.put("很强", "很強");
		DRESS_INDEX_DESC_EN_MAP = new HashMap<String, String>();
		DRESS_INDEX_DESC_EN_MAP.put("凉", "Cool");
		DRESS_INDEX_DESC_EN_MAP.put("舒适", "Comfortable");
		DRESS_INDEX_DESC_EN_MAP.put("较舒适", "Good");
		DRESS_INDEX_DESC_EN_MAP.put("炎热", "Very hot");
		DRESS_INDEX_DESC_EN_MAP.put("热", "Hot");
		DRESS_INDEX_DESC_EN_MAP.put("暖", "Warm");
		DRESS_INDEX_DESC_EN_MAP.put("温凉", "Cool");
		DRESS_INDEX_DESC_EN_MAP.put("气温较低", "Cooler");
		DRESS_INDEX_DESC_EN_MAP.put("冷", "Cold");
		DRESS_INDEX_DESC_EN_MAP.put("寒冷", "Freezing");
		DRESS_INDEX_DESC_CN_MAP = new HashMap();
		DRESS_INDEX_DESC_CN_MAP.put("凉", "凉");
		DRESS_INDEX_DESC_CN_MAP.put("舒适", "舒适");
		DRESS_INDEX_DESC_CN_MAP.put("较舒适", "较舒适");
		DRESS_INDEX_DESC_CN_MAP.put("炎热", "炎热");
		DRESS_INDEX_DESC_CN_MAP.put("热", "热");
		DRESS_INDEX_DESC_CN_MAP.put("暖", "暖");
		DRESS_INDEX_DESC_CN_MAP.put("温凉", "温凉");
		DRESS_INDEX_DESC_CN_MAP.put("气温较低", "气温较低");
		DRESS_INDEX_DESC_CN_MAP.put("冷", "冷");
		DRESS_INDEX_DESC_CN_MAP.put("寒冷", "寒冷");
		DRESS_INDEX_DESC_TW_MAP = new HashMap();
		DRESS_INDEX_DESC_TW_MAP.put("凉", "涼");
		DRESS_INDEX_DESC_TW_MAP.put("舒适", "舒適");
		DRESS_INDEX_DESC_TW_MAP.put("较舒适", "較舒適");
		DRESS_INDEX_DESC_TW_MAP.put("炎热", "炎熱");
		DRESS_INDEX_DESC_TW_MAP.put("热", "熱");
		DRESS_INDEX_DESC_TW_MAP.put("暖", "暖");
		DRESS_INDEX_DESC_TW_MAP.put("温凉", "溫涼");
		DRESS_INDEX_DESC_TW_MAP.put("气温较低", "氣溫較低");
		DRESS_INDEX_DESC_TW_MAP.put("冷", "冷");
		DRESS_INDEX_DESC_TW_MAP.put("寒冷", "寒冷");
		COMFORT_INDEX_DESC_EN_MAP = new HashMap();
		COMFORT_INDEX_DESC_EN_MAP.put("较舒适", "High");
		COMFORT_INDEX_DESC_EN_MAP.put("舒适", "Middle");
		COMFORT_INDEX_DESC_EN_MAP.put("较不舒适", "Low");
		COMFORT_INDEX_DESC_EN_MAP.put("很不舒适", "Not comfortable");
		COMFORT_INDEX_DESC_EN_MAP.put("不宜", "Not recommended");
		COMFORT_INDEX_DESC_EN_MAP.put("基本适宜", "Suitable conditions");
		COMFORT_INDEX_DESC_EN_MAP.put("适宜", "Ideal conditions");
		COMFORT_INDEX_DESC_CN_MAP = new HashMap();
		COMFORT_INDEX_DESC_CN_MAP.put("较舒适", "较舒适");
		COMFORT_INDEX_DESC_CN_MAP.put("舒适", "舒适");
		COMFORT_INDEX_DESC_CN_MAP.put("较不舒适", "较不舒适");
		COMFORT_INDEX_DESC_CN_MAP.put("很不舒适", "很不舒适");
		COMFORT_INDEX_DESC_CN_MAP.put("不宜", "不宜");
		COMFORT_INDEX_DESC_CN_MAP.put("基本适宜", "基本适宜");
		COMFORT_INDEX_DESC_CN_MAP.put("适宜", "适宜");
		COMFORT_INDEX_DESC_TW_MAP = new HashMap<String, String>();
		COMFORT_INDEX_DESC_TW_MAP.put("较舒适", "較舒適");
		COMFORT_INDEX_DESC_TW_MAP.put("舒适", "舒適");
		COMFORT_INDEX_DESC_TW_MAP.put("较不舒适", "較不舒適");
		COMFORT_INDEX_DESC_TW_MAP.put("很不舒适", "很不舒適");
		COMFORT_INDEX_DESC_TW_MAP.put("不宜", "不宜");
		COMFORT_INDEX_DESC_TW_MAP.put("基本适宜", "基本適宜");
		COMFORT_INDEX_DESC_TW_MAP.put("适宜", "適宜");
		EXERCISE_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		CARWASH_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		AIRCURE_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		TRAVEL_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		ULTRAVIOLET_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		DRESS_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		COMFORT_INDEX_DETAIL_CN_MAP = new HashMap<String, String>();
		EXERCISE_INDEX_DETAIL_CN_MAP.put("适宜",
				"天气晴朗，空气清新，是您晨练的大好时机，建议不同年龄段的人们积极参加户外健身活动。");
		EXERCISE_INDEX_DETAIL_CN_MAP.put("较适宜",
				"早晨气象条件较适宜晨练，建议晨练着装不要过于单薄，有风时选择避风地点，以防感冒。");
		EXERCISE_INDEX_DETAIL_CN_MAP.put("较不宜",
				"早晨天气变化较大，较不宜晨练，若坚持晨练，请适当减少晨练时间，注意保暖并携带雨具。");
		EXERCISE_INDEX_DETAIL_CN_MAP.put("不宜",
				"早晨天气很差，请避免户外晨练，建议在室内做适当锻炼，保持身体健康。");
		EXERCISE_INDEX_DETAIL_CN_MAP.put("极适宜",
				"天气晴朗，空气清新，是您晨练的大好时机，建议不同年龄段的人们积极参加户外健身活动。");
		CARWASH_INDEX_DETAIL_CN_MAP.put("适宜",
				"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。");
		CARWASH_INDEX_DETAIL_CN_MAP.put("较适宜",
				"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。");
		CARWASH_INDEX_DETAIL_CN_MAP.put("较不宜", "较不宜洗车，如果执意擦洗汽车，要做好溅上泥水的心理准备。");
		CARWASH_INDEX_DETAIL_CN_MAP.put("不宜",
				"不宜洗车，未来24小时会有天气变化，如果在此期间洗车，可能会再次弄脏您的爱车。");
		CARWASH_INDEX_DETAIL_CN_MAP.put("极适宜",
				"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。");
		AIRCURE_INDEX_DETAIL_CN_MAP.put("适宜",
				"万里无云，光照充足，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧！");
		AIRCURE_INDEX_DETAIL_CN_MAP.put("较适宜",
				"天气不错，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧。");
		AIRCURE_INDEX_DETAIL_CN_MAP.put("不太适宜", "天气不好，不太适宜晾晒。请随时注意天气变化。");
		AIRCURE_INDEX_DETAIL_CN_MAP.put("较不宜",
				"偶尔的降雨可能会淋湿晾晒的衣物，不太适宜晾晒。请随时注意天气变化。");
		AIRCURE_INDEX_DETAIL_CN_MAP
				.put("不宜", "有降水，不适宜晾晒。如果非晾晒不可，请在室内准备出充足的空间。");
		AIRCURE_INDEX_DETAIL_CN_MAP.put("极适宜",
				"万里无云，光照充足，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧！");
		TRAVEL_INDEX_DETAIL_CN_MAP.put("适宜",
				"天气晴朗，风和日丽，温度适宜，是个好天气哦。这样的天气很适宜旅游，您可以尽情地享受大自然的风光。");
		TRAVEL_INDEX_DETAIL_CN_MAP.put("较适宜", "今天气候温和，天气变化不大，较适宜旅游，是出行游玩的好时机。");
		TRAVEL_INDEX_DETAIL_CN_MAP.put("较不宜", "天气变化较大，会给出行带来麻烦，建议还是多选择在室内活动！");
		TRAVEL_INDEX_DETAIL_CN_MAP.put("不宜", "天气变化很大，会给出行带来很多麻烦，建议还是多选择在室内活动！");
		TRAVEL_INDEX_DETAIL_CN_MAP.put("极适宜",
				"天气晴朗，风和日丽，温度适宜，是个好天气哦。这样的天气很适宜旅游，您可以尽情地享受大自然的风光。");
		ULTRAVIOLET_INDEX_DETAIL_CN_MAP.put("中等",
				"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。");
		ULTRAVIOLET_INDEX_DETAIL_CN_MAP.put("最弱",
				"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。");
		ULTRAVIOLET_INDEX_DETAIL_CN_MAP.put("弱",
				"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。");
		ULTRAVIOLET_INDEX_DETAIL_CN_MAP.put("强",
				"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。");
		ULTRAVIOLET_INDEX_DETAIL_CN_MAP.put("很强",
				"紫外线辐射极强，建议涂擦SPF20以上、PA++的防晒护肤品，尽量避免暴露于日光下。");
		DRESS_INDEX_DETAIL_CN_MAP.put("凉", "建议着薄型套装等春秋过渡装。年老体弱者宜着套装。");
		DRESS_INDEX_DETAIL_CN_MAP.put("舒适", "建议着薄型套装等春秋过渡装。年老体弱者宜着套装。");
		DRESS_INDEX_DETAIL_CN_MAP.put("较舒适",
				"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。");
		DRESS_INDEX_DETAIL_CN_MAP.put("炎热",
				"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫、敞领短袖棉衫等清凉夏季服装。");
		DRESS_INDEX_DETAIL_CN_MAP.put("热",
				"天气较热，建议着短裙、短裤、短套装、T恤等夏季服装。年老体弱者宜着长袖衬衫和单裤。");
		DRESS_INDEX_DETAIL_CN_MAP.put("暖", "较凉爽，建议着长袖衬裤等春秋过渡装。体弱者宜着长袖衬衫和马甲。");
		DRESS_INDEX_DETAIL_CN_MAP.put("温凉", "较凉爽，建议着夹衣加薄羊毛衫等春秋服装。体弱者宜着夹衣加羊毛衫。");
		DRESS_INDEX_DETAIL_CN_MAP.put("气温较低",
				"天气凉，建议着厚外套加毛衣等春秋服装。年老体弱者宜着大衣、呢外套加羊毛衫。");
		DRESS_INDEX_DETAIL_CN_MAP.put("冷",
				"天气冷，建议着棉衣、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣或冬大衣。");
		DRESS_INDEX_DETAIL_CN_MAP.put("寒冷",
				"天气冷，建议着棉衣、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣或冬大衣。");
		COMFORT_INDEX_DETAIL_CN_MAP.put("较舒适", "今日温度较为稳定，天气变化较小，较为舒适");
		COMFORT_INDEX_DETAIL_CN_MAP.put("舒适", "今日温度较为稳定，天气变化较小，较为舒适");
		COMFORT_INDEX_DETAIL_CN_MAP.put("较不舒适",
				"白天虽然天气以阴为主，但由于天热，加上湿度较大，您会感到很闷热，很不舒适。");
		COMFORT_INDEX_DETAIL_CN_MAP.put("很不舒适", "白天天气晴好，但温度不够适宜，您可能会感到不舒适。");
		COMFORT_INDEX_DETAIL_CN_MAP.put("不宜", "今天天气变化较大，可能会令人有些不舒适。");
		COMFORT_INDEX_DETAIL_CN_MAP.put("基本适宜",
				"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。");
		COMFORT_INDEX_DETAIL_CN_MAP.put("适宜",
				"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。");
		EXERCISE_INDEX_DETAIL_EN_MAP = new HashMap();
		CARWASH_INDEX_DETAIL_EN_MAP = new HashMap();
		AIRCURE_INDEX_DETAIL_EN_MAP = new HashMap();
		TRAVEL_INDEX_DETAIL_EN_MAP = new HashMap();
		ULTRAVIOLET_INDEX_DETAIL_EN_MAP = new HashMap();
		DRESS_INDEX_DETAIL_EN_MAP = new HashMap();
		COMFORT_INDEX_DETAIL_EN_MAP = new HashMap();
		EXERCISE_INDEX_DETAIL_EN_MAP.put("适宜",
				"It's a good day for morning exercises!");
		EXERCISE_INDEX_DETAIL_EN_MAP.put("较适宜", "Bring additional layers!");
		EXERCISE_INDEX_DETAIL_EN_MAP.put("较不宜", "Bring additional layers!");
		EXERCISE_INDEX_DETAIL_EN_MAP.put("不宜", "Exercise indoors!");
		EXERCISE_INDEX_DETAIL_EN_MAP.put("极适宜",
				"It's a good day for morning exercises!");
		CARWASH_INDEX_DETAIL_EN_MAP.put("适宜",
				"It's a good day to wash your car!");
		CARWASH_INDEX_DETAIL_EN_MAP.put("较适宜",
				"It's a good day to wash your car.");
		CARWASH_INDEX_DETAIL_EN_MAP.put("较不宜", "Hold off on washing your car.");
		CARWASH_INDEX_DETAIL_EN_MAP.put("不宜", "Hold off on washing your car.");
		CARWASH_INDEX_DETAIL_EN_MAP.put("极适宜",
				"It's a good day to wash your car!");
		AIRCURE_INDEX_DETAIL_EN_MAP.put("适宜",
				"The weather is suitable for drying clothes.");
		AIRCURE_INDEX_DETAIL_EN_MAP.put("较适宜", "Good drying conditions.");
		AIRCURE_INDEX_DETAIL_EN_MAP.put("不太适宜", "Dry your clothing indoors.");
		AIRCURE_INDEX_DETAIL_EN_MAP.put("较不宜", "Dry your clothing indoors.");
		AIRCURE_INDEX_DETAIL_EN_MAP.put("不宜", "Dry your clothing indoors.");
		AIRCURE_INDEX_DETAIL_EN_MAP.put("极适宜", "Ideal drying conditions.");
		TRAVEL_INDEX_DETAIL_EN_MAP.put("适宜", "Clear, sunny, and comfortable!");
		TRAVEL_INDEX_DETAIL_EN_MAP.put("较适宜", "Mild and stable temperatures.");
		TRAVEL_INDEX_DETAIL_EN_MAP.put("较不宜",
				"Today's weather will be volatile.");
		TRAVEL_INDEX_DETAIL_EN_MAP.put("不宜",
				"Today's weather will be volatile.");
		TRAVEL_INDEX_DETAIL_EN_MAP.put("极适宜", "Clear, sunny, and comfortable!");
		ULTRAVIOLET_INDEX_DETAIL_EN_MAP.put("中等",
				"Use sunscreen and wear hat and sunglasses when outdoors.");
		ULTRAVIOLET_INDEX_DETAIL_EN_MAP.put("最弱",
				"Use sunscreen and wear hat and sunglasses when outdoors.");
		ULTRAVIOLET_INDEX_DETAIL_EN_MAP.put("弱",
				"Use sunscreen and wear hat and sunglasses when outdoors.");
		ULTRAVIOLET_INDEX_DETAIL_EN_MAP.put("强",
				"Avoid sun if possible. Use sunscreen if you need to go out!");
		ULTRAVIOLET_INDEX_DETAIL_EN_MAP.put("很强",
				"Avoid sun if possible. Use sunscreen if you need to go out");
		DRESS_INDEX_DETAIL_EN_MAP.put("凉", "Light jacket.");
		DRESS_INDEX_DETAIL_EN_MAP.put("舒适", "Light jacket.");
		DRESS_INDEX_DETAIL_EN_MAP.put("较舒适", "Light jacket.");
		DRESS_INDEX_DETAIL_EN_MAP.put("炎热", "Summer clothing.");
		DRESS_INDEX_DETAIL_EN_MAP.put("热", "Summer clothing.");
		DRESS_INDEX_DETAIL_EN_MAP.put("暖", "Light jacket.");
		DRESS_INDEX_DETAIL_EN_MAP.put("温凉",
				"Remember to bring additional layers!");
		DRESS_INDEX_DETAIL_EN_MAP.put("气温较低", "Bring a jacket!");
		DRESS_INDEX_DETAIL_EN_MAP.put("冷", "Bundle up!");
		DRESS_INDEX_DETAIL_EN_MAP.put("寒冷", "Bundle up!");
		COMFORT_INDEX_DETAIL_EN_MAP.put("较舒适", "Temperature will be stable.");
		COMFORT_INDEX_DETAIL_EN_MAP.put("舒适", "Temperature will be stable.");
		COMFORT_INDEX_DETAIL_EN_MAP
				.put("较不舒适", "Temperature will be volatile!");
		COMFORT_INDEX_DETAIL_EN_MAP.put("很不舒适", "Hot and humid! :(");
		COMFORT_INDEX_DETAIL_EN_MAP.put("不宜", "Temperature will be volatile!");
		COMFORT_INDEX_DETAIL_EN_MAP.put("基本适宜", "Cool and comfortable.");
		COMFORT_INDEX_DETAIL_EN_MAP.put("适宜", "Cool and comfortable.");
		EXERCISE_INDEX_DETAIL_TW_MAP = new HashMap();
		CARWASH_INDEX_DETAIL_TW_MAP = new HashMap();
		AIRCURE_INDEX_DETAIL_TW_MAP = new HashMap();
		TRAVEL_INDEX_DETAIL_TW_MAP = new HashMap();
		ULTRAVIOLET_INDEX_DETAIL_TW_MAP = new HashMap();
		DRESS_INDEX_DETAIL_TW_MAP = new HashMap();
		COMFORT_INDEX_DETAIL_TW_MAP = new HashMap();
		EXERCISE_INDEX_DETAIL_TW_MAP.put("适宜",
				"天氣晴朗，空氣清新，是您晨練的大好時機，建議不同年齡段的人們積極參加戶外健身活動。");
		EXERCISE_INDEX_DETAIL_TW_MAP.put("较适宜",
				"早晨氣象條件較適宜晨練，建議晨練著裝不要過於單薄，有風時選擇避風地點，以防感冒。");
		EXERCISE_INDEX_DETAIL_TW_MAP.put("较不宜",
				"早晨天氣變化較大，較不宜晨練，若堅持晨練，請適當減少晨練時間，注意保暖並攜帶雨具。");
		EXERCISE_INDEX_DETAIL_TW_MAP.put("不宜",
				"早晨天氣很差，請避免戶外晨練，建議在室內做適當鍛煉，保持身體健康。");
		EXERCISE_INDEX_DETAIL_TW_MAP.put("极适宜",
				"天氣晴朗，空氣清新，是您晨練的大好時機，建議不同年齡段的人們積極參加戶外健身活動。");
		CARWASH_INDEX_DETAIL_TW_MAP.put("适宜",
				"適宜洗車，未來持續兩天無雨天氣較好，適合擦洗汽車，藍天白雲、風和日麗將伴您的車子連日潔淨。");
		CARWASH_INDEX_DETAIL_TW_MAP.put("较适宜",
				"較適宜洗車，未來一天無雨，風力較小，擦洗一新的汽車至少能保持一天。");
		CARWASH_INDEX_DETAIL_TW_MAP.put("较不宜", "較不宜洗車，如果執意擦洗汽車，要做好濺上泥水的心理準備。");
		CARWASH_INDEX_DETAIL_TW_MAP.put("不宜",
				"不宜洗車，未來24小時會有天氣變化，如果在此期間洗車，可能會再次弄髒您的愛車。");
		CARWASH_INDEX_DETAIL_TW_MAP.put("极适宜",
				"適宜洗車，未來持續兩天無雨天氣較好，適合擦洗汽車，藍天白雲、風和日麗將伴您的車子連日潔淨。");
		AIRCURE_INDEX_DETAIL_TW_MAP.put("适宜",
				"萬里無雲，光照充足，適宜晾曬。趕緊把久未見陽光的衣物搬出來吸收一下太陽的味道吧！");
		AIRCURE_INDEX_DETAIL_TW_MAP.put("较适宜",
				"天氣不錯，適宜晾曬。趕緊把久未見陽光的衣物搬出來吸收一下太陽的味道吧。");
		AIRCURE_INDEX_DETAIL_TW_MAP.put("不太適宜", "天氣不好，不太適宜晾曬。請隨時注意天氣變化。");
		AIRCURE_INDEX_DETAIL_TW_MAP.put("较不宜",
				"偶爾的降雨可能會淋濕晾曬的衣物，不太適宜晾曬。請隨時注意天氣變化。");
		AIRCURE_INDEX_DETAIL_TW_MAP
				.put("不宜", "有降水，不適宜晾曬。如果非晾曬不可，請在室內準備出充足的空間。");
		AIRCURE_INDEX_DETAIL_TW_MAP.put("极适宜",
				"萬里無雲，光照充足，適宜晾曬。趕緊把久未見陽光的衣物搬出來吸收一下太陽的味道吧！");
		TRAVEL_INDEX_DETAIL_TW_MAP.put("适宜",
				"天氣晴朗，風和日麗，溫度適宜，是個好天氣哦。這樣的天氣很適宜旅遊，您可以盡情地享受大自然的風光。");
		TRAVEL_INDEX_DETAIL_TW_MAP.put("较适宜", "今天氣候溫和，天氣變化不大，較適宜旅遊，是出行遊玩的好時機。");
		TRAVEL_INDEX_DETAIL_TW_MAP.put("较不宜", "天氣變化較大，會給出行帶來麻煩，建議還是多選擇在室內活動！");
		TRAVEL_INDEX_DETAIL_TW_MAP.put("不宜", "天氣變化很大，會給出行帶來很多麻煩，建議還是多選擇在室內活動！");
		TRAVEL_INDEX_DETAIL_TW_MAP.put("极适宜",
				"天氣晴朗，風和日麗，溫度適宜，是個好天氣哦。這樣的天氣很適宜旅遊，您可以盡情地享受大自然的風光。");
		ULTRAVIOLET_INDEX_DETAIL_TW_MAP.put("中等",
				"屬中等強度紫外線輻射天氣，外出時建議塗擦SPF高於15、PA+的防曬護膚品，戴帽子、太陽鏡。");
		ULTRAVIOLET_INDEX_DETAIL_TW_MAP.put("最弱",
				"屬弱紫外線輻射天氣，無需特別防護。若長期在戶外，建議塗擦SPF在8-12之間的防曬護膚品。");
		ULTRAVIOLET_INDEX_DETAIL_TW_MAP.put("弱",
				"紫外線強度較弱，建議出門前塗擦SPF在12-15之間、PA+的防曬護膚品。");
		ULTRAVIOLET_INDEX_DETAIL_TW_MAP.put("强",
				"紫外線輻射強，建議塗擦SPF20左右、PA++的防曬護膚品。避免在10點至14點暴露於日光下。");
		ULTRAVIOLET_INDEX_DETAIL_TW_MAP.put("很强",
				"紫外線輻射極強，建議塗擦SPF20以上、PA++的防曬護膚品，盡量避免暴露於日光下。");
		DRESS_INDEX_DETAIL_TW_MAP.put("凉", "建議著薄型套裝等春秋過渡裝。年老體弱者宜著套裝。");
		DRESS_INDEX_DETAIL_TW_MAP.put("舒适", "建議著薄型套裝等春秋過渡裝。年老體弱者宜著套裝。");
		DRESS_INDEX_DETAIL_TW_MAP.put("较舒适",
				"建議著薄外套、開衫牛仔衫褲等服裝。年老體弱者應適當添加衣物，宜著夾克衫、薄毛衣等。");
		DRESS_INDEX_DETAIL_TW_MAP.put("炎热",
				"天氣炎熱，建議著短衫、短裙、短褲、薄型T恤衫、敞領短袖棉衫等清涼夏季服裝。");
		DRESS_INDEX_DETAIL_TW_MAP.put("热",
				"天氣較熱，建議著短裙、短褲、短套裝、T恤等夏季服裝。年老體弱者宜著長袖襯衫和單褲。");
		DRESS_INDEX_DETAIL_TW_MAP.put("暖", "較涼爽，建議著長袖襯褲等春秋過渡裝。體弱者宜著長袖襯衫和馬甲。");
		DRESS_INDEX_DETAIL_TW_MAP.put("温凉", "較涼爽，建議著裌衣加薄羊毛衫等春秋服裝。體弱者宜著裌衣加羊毛衫。");
		DRESS_INDEX_DETAIL_TW_MAP.put("气温较低",
				"天氣涼，建議著厚外套加毛衣等春秋服裝。年老體弱者宜著大衣、呢外套加羊毛衫。");
		DRESS_INDEX_DETAIL_TW_MAP.put("冷",
				"天氣冷，建議著棉衣、皮夾克加羊毛衫等冬季服裝。年老體弱者宜著厚棉衣或冬大衣。");
		DRESS_INDEX_DETAIL_TW_MAP.put("寒冷",
				"天氣冷，建議著棉衣、皮夾克加羊毛衫等冬季服裝。年老體弱者宜著厚棉衣或冬大衣。");
		COMFORT_INDEX_DETAIL_TW_MAP.put("较舒适", "今日溫度較為穩定，天氣變化較小，較為舒適");
		COMFORT_INDEX_DETAIL_TW_MAP.put("舒适", "今日溫度較為穩定，天氣變化較小，較為舒適");
		COMFORT_INDEX_DETAIL_TW_MAP.put("较不舒适",
				"白天雖然天氣以陰為主，但由於天熱，加上濕度較大，您會感到很悶熱，很不舒適。");
		COMFORT_INDEX_DETAIL_TW_MAP.put("很不舒适", "白天天氣晴好，但溫度不夠適宜，您可能會感到不舒適。");
		COMFORT_INDEX_DETAIL_TW_MAP.put("不宜", "今天天氣變化較大，可能會令人有些不舒適。");
		COMFORT_INDEX_DETAIL_TW_MAP.put("基本适宜",
				"白天不太熱也不太冷，風力不大，相信您在這樣的天氣條件下，應會感到比較清爽和舒適。");
		COMFORT_INDEX_DETAIL_TW_MAP.put("适宜",
				"白天不太熱也不太冷，風力不大，相信您在這樣的天氣條件下，應會感到比較清爽和舒適。");
		INDEX_DETAIL_CN_MAP = new HashMap();
		INDEX_DETAIL_CN_MAP.put("穿衣指数", DRESS_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("48小时穿衣指数", DRESS_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("洗车指数", CARWASH_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("旅游指数", TRAVEL_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("紫外线指数", ULTRAVIOLET_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("48小时紫外线指数", ULTRAVIOLET_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("晾晒指数", AIRCURE_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("晨练指数", EXERCISE_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_CN_MAP.put("舒适度指数", COMFORT_INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_EN_MAP = new HashMap();
		INDEX_DETAIL_EN_MAP.put("穿衣指数", DRESS_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("48小时穿衣指数", DRESS_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("洗车指数", CARWASH_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("旅游指数", TRAVEL_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("紫外线指数", ULTRAVIOLET_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("48小时紫外线指数", ULTRAVIOLET_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("晾晒指数", AIRCURE_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("晨练指数", EXERCISE_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_EN_MAP.put("舒适度指数", COMFORT_INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_TW_MAP = new HashMap();
		INDEX_DETAIL_TW_MAP.put("穿衣指数", DRESS_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("48小时穿衣指数", DRESS_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("洗车指数", CARWASH_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("旅游指数", TRAVEL_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("紫外线指数", ULTRAVIOLET_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("48小时紫外线指数", ULTRAVIOLET_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("晾晒指数", AIRCURE_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("晨练指数", EXERCISE_INDEX_DETAIL_TW_MAP);
		INDEX_DETAIL_TW_MAP.put("舒适度指数", COMFORT_INDEX_DETAIL_TW_MAP);
		INDEX_DESC_CN_MAP = new HashMap();
		INDEX_DESC_CN_MAP.put("穿衣指数", DRESS_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("48小时穿衣指数", DRESS_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("洗车指数", CARWASH_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("旅游指数", TRAVEL_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("紫外线指数", ULTRAVIOLET_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("48小时紫外线指数", ULTRAVIOLET_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("晾晒指数", AIRCURE_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("晨练指数", EXERCISE_INDEX_DESC_CN_MAP);
		INDEX_DESC_CN_MAP.put("舒适度指数", COMFORT_INDEX_DESC_CN_MAP);
		INDEX_DESC_EN_MAP = new HashMap();
		INDEX_DESC_EN_MAP.put("穿衣指数", DRESS_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("48小时穿衣指数", DRESS_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("洗车指数", CARWASH_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("旅游指数", TRAVEL_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("紫外线指数", ULTRAVIOLET_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("48小时紫外线指数", ULTRAVIOLET_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("晾晒指数", AIRCURE_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("晨练指数", EXERCISE_INDEX_DESC_EN_MAP);
		INDEX_DESC_EN_MAP.put("舒适度指数", COMFORT_INDEX_DESC_EN_MAP);
		INDEX_DESC_TW_MAP = new HashMap();
		INDEX_DESC_TW_MAP.put("穿衣指数", DRESS_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("48小时穿衣指数", DRESS_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("洗车指数", CARWASH_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("旅游指数", TRAVEL_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("紫外线指数", ULTRAVIOLET_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("48小时紫外线指数", ULTRAVIOLET_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("晾晒指数", AIRCURE_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("晨练指数", EXERCISE_INDEX_DESC_TW_MAP);
		INDEX_DESC_TW_MAP.put("舒适度指数", COMFORT_INDEX_DESC_TW_MAP);
		INDEX_EN_MAP = new HashMap();
		INDEX_EN_MAP.put("风力指数", "Wind index");
		INDEX_EN_MAP.put("穿衣指数", "Clothing");
		INDEX_EN_MAP.put("48小时穿衣指数", "48 hours Clothing");
		INDEX_EN_MAP.put("洗车指数", "Car wash index");
		INDEX_EN_MAP.put("旅游指数", "Leisure");
		INDEX_EN_MAP.put("紫外线指数", "UV index");
		INDEX_EN_MAP.put("48小时紫外线指数", "48 hours UV index");
		INDEX_EN_MAP.put("晾晒指数", "Drying index");
		INDEX_EN_MAP.put("晨练指数", "Morning exercise");
		INDEX_EN_MAP.put("舒适度指数", "Comfort");
		INDEX_CN_MAP = new HashMap();
		INDEX_CN_MAP.put("风力指数", "风力指数");
		INDEX_CN_MAP.put("穿衣指数", "穿衣指数");
		INDEX_CN_MAP.put("48小时穿衣指数", "48小时穿衣指数");
		INDEX_CN_MAP.put("洗车指数", "洗车指数");
		INDEX_CN_MAP.put("旅游指数", "旅游指数");
		INDEX_CN_MAP.put("紫外线指数", "紫外线指数");
		INDEX_CN_MAP.put("48小时紫外线指数", "48小时紫外线指数");
		INDEX_CN_MAP.put("晾晒指数", "晾晒指数");
		INDEX_CN_MAP.put("晨练指数", "晨练指数");
		INDEX_CN_MAP.put("舒适度指数", "舒适度指数");
		INDEX_TW_MAP = new HashMap();
		INDEX_TW_MAP.put("风力指数", "風力指數");
		INDEX_TW_MAP.put("穿衣指数", "穿衣指數");
		INDEX_TW_MAP.put("48小时穿衣指数", "48小时穿衣指數");
		INDEX_TW_MAP.put("洗车指数", "洗車指數");
		INDEX_TW_MAP.put("旅游指数", "旅遊指數");
		INDEX_TW_MAP.put("紫外线指数", "紫外線");
		INDEX_TW_MAP.put("48小时紫外线指数", "48小时紫外線");
		INDEX_TW_MAP.put("晾晒指数", "晾曬指數");
		INDEX_TW_MAP.put("晨练指数", "晨練指數");
		INDEX_TW_MAP.put("舒适度指数", "舒適指數");
		SURPORTTED_LANGUAGE_LIST = new ArrayList();
		SURPORTTED_LANGUAGE_LIST.add(Locale.US.toString().toLowerCase());
		SURPORTTED_LANGUAGE_LIST.add(Locale.CHINA.toString().toLowerCase());
		SURPORTTED_LANGUAGE_LIST.add(Locale.TAIWAN.toString().toLowerCase());
		INDEX_LANGUAGE_MAP = new HashMap();
		INDEX_LANGUAGE_MAP
				.put(Locale.US.toString().toLowerCase(), INDEX_EN_MAP);
		INDEX_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				INDEX_CN_MAP);
		INDEX_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				INDEX_TW_MAP);
		INDEX_DESC_LANGUAGE_MAP = new HashMap();
		INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				INDEX_DESC_EN_MAP);
		INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				INDEX_DESC_CN_MAP);
		INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				INDEX_DESC_TW_MAP);
		EXERCISE_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		EXERCISE_INDEX_DESC_LANGUAGE_MAP.put(
				Locale.US.toString().toLowerCase(), EXERCISE_INDEX_DESC_EN_MAP);
		EXERCISE_INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), EXERCISE_INDEX_DESC_CN_MAP);
		EXERCISE_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), EXERCISE_INDEX_DESC_TW_MAP);
		CARWASH_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		CARWASH_INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				CARWASH_INDEX_DESC_EN_MAP);
		CARWASH_INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), CARWASH_INDEX_DESC_CN_MAP);
		CARWASH_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), CARWASH_INDEX_DESC_TW_MAP);
		AIRCURE_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		AIRCURE_INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				AIRCURE_INDEX_DESC_EN_MAP);
		AIRCURE_INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), AIRCURE_INDEX_DESC_CN_MAP);
		AIRCURE_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), AIRCURE_INDEX_DESC_TW_MAP);
		ULTRAVIOLET_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		ULTRAVIOLET_INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString()
				.toLowerCase(), ULTRAVIOLET_INDEX_DESC_EN_MAP);
		ULTRAVIOLET_INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), ULTRAVIOLET_INDEX_DESC_CN_MAP);
		ULTRAVIOLET_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), ULTRAVIOLET_INDEX_DESC_TW_MAP);
		DRESS_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		DRESS_INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				DRESS_INDEX_DESC_EN_MAP);
		DRESS_INDEX_DESC_LANGUAGE_MAP.put(
				Locale.CHINA.toString().toLowerCase(), DRESS_INDEX_DESC_CN_MAP);
		DRESS_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), DRESS_INDEX_DESC_TW_MAP);
		COMFORT_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		COMFORT_INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				COMFORT_INDEX_DESC_EN_MAP);
		COMFORT_INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), COMFORT_INDEX_DESC_CN_MAP);
		COMFORT_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), COMFORT_INDEX_DESC_TW_MAP);
		TRAVEL_INDEX_DESC_LANGUAGE_MAP = new HashMap();
		TRAVEL_INDEX_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				TRAVEL_INDEX_DESC_EN_MAP);
		TRAVEL_INDEX_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString()
				.toLowerCase(), TRAVEL_INDEX_DESC_CN_MAP);
		TRAVEL_INDEX_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString()
				.toLowerCase(), TRAVEL_INDEX_DESC_TW_MAP);
		INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		INDEX_DETAIL_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				INDEX_DETAIL_EN_MAP);
		INDEX_DETAIL_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				INDEX_DETAIL_CN_MAP);
		INDEX_DETAIL_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				INDEX_DETAIL_TW_MAP);
		EXERCISE_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		CARWASH_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		AIRCURE_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		ULTRAVIOLET_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		DRESS_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		COMFORT_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		TRAVEL_INDEX_DETAIL_LANGUAGE_MAP = new HashMap();
		AQI_LEVEL_CN_MAP = new HashMap();
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(0), "无污染");
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(50), "优");
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(100), "良");
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(150), "轻度污染");
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(200), "中度污染");
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(300), "重度污染");
		AQI_LEVEL_CN_MAP.put(Integer.valueOf(500), "严重污染");
		AQI_LEVEL_EN_MAP = new HashMap();
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(0), "Excellent");
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(50), "Good");
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(100), "Moderate");
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(150), "Caution");
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(200), "Unhealthy");
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(300), "Dangerous");
		AQI_LEVEL_EN_MAP.put(Integer.valueOf(500), "Hazardous");
		AQI_LEVEL_TW_MAP = new HashMap();
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(0), "无汙染");
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(50), "優");
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(100), "良");
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(150), "輕度汙染");
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(200), "中度汙染");
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(300), "重度汙染");
		AQI_LEVEL_TW_MAP.put(Integer.valueOf(500), "严重汙染");
		AQI_LEVEL_LANGUAGE_MAP = new HashMap();
		AQI_LEVEL_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				AQI_LEVEL_EN_MAP);
		AQI_LEVEL_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				AQI_LEVEL_CN_MAP);
		AQI_LEVEL_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				AQI_LEVEL_TW_MAP);
		AQI_DESC_CN_MAP = new HashMap();
		AQI_DESC_CN_MAP.put(Integer.valueOf(0), "所有人群可在户外正常自由活动。");
		AQI_DESC_CN_MAP.put(Integer.valueOf(50), "所有人群可在户外正常自由活动。");
		AQI_DESC_CN_MAP.put(Integer.valueOf(100), "对空气质量特别敏感的人群应该减少户外活动。");
		AQI_DESC_CN_MAP.put(Integer.valueOf(150),
				"儿童、老人及心脏、呼吸系统疾病患者人群应该减少长时间、高强度的户外锻炼。");
		AQI_DESC_CN_MAP.put(Integer.valueOf(200),
				"儿童、老人及心脏、呼吸系统疾病患者人群应该减少长时间、高强度的户外锻炼，一般人群适量减少户外运动。");
		AQI_DESC_CN_MAP.put(Integer.valueOf(300),
				"儿童、老人及心脏、呼吸系统疾病患者人群应停留在室内，停止户外运动，一般人群减少户外运动。");
		AQI_DESC_CN_MAP.put(Integer.valueOf(500),
				"儿童、老人及心脏、呼吸系统疾病患者人群应停留在室内，并减少体力活动，一般人群应该避免户外活动并注意防护。");
		AQI_DESC_EN_MAP = new HashMap();
		AQI_DESC_EN_MAP.put(Integer.valueOf(0),
				"It's a great day to be active outside!");
		AQI_DESC_EN_MAP.put(Integer.valueOf(50),
				"It's a great day to be active outside!");
		AQI_DESC_EN_MAP
				.put(Integer.valueOf(100),
						"People with preexisting medical conditions should reduce prolonged or heavy exertion.");
		AQI_DESC_EN_MAP
				.put(Integer.valueOf(150),
						"People with heart or lung disease, older adults, and young children should reduce prolonged or heavy exertion.");
		AQI_DESC_EN_MAP.put(Integer.valueOf(200),
				"Reduce prolonged or heavy exertion.");
		AQI_DESC_EN_MAP.put(Integer.valueOf(300),
				"Avoid prolonged or heavy exertion.");
		AQI_DESC_EN_MAP.put(Integer.valueOf(500),
				"Remain indoors and reduce your level of activity.");
		AQI_DESC_TW_MAP = new HashMap();
		AQI_DESC_TW_MAP.put(Integer.valueOf(0), "所有人群可在戶外正常自由活動。");
		AQI_DESC_TW_MAP.put(Integer.valueOf(50), "所有人群可在戶外正常自由活動。");
		AQI_DESC_TW_MAP.put(Integer.valueOf(100), "對空氣質量特別敏感的人群應該減少戶外活動。");
		AQI_DESC_TW_MAP.put(Integer.valueOf(150),
				"兒童、老人及心臟、呼吸系統疾病患者人群應該減少長時間、高強度的戶外鍛煉。");
		AQI_DESC_TW_MAP.put(Integer.valueOf(200),
				"兒童、老人及心臟、呼吸系統疾病患者人群應該減少長時間、高強度的戶外鍛煉，一般人群適量減少戶外運動。");
		AQI_DESC_TW_MAP.put(Integer.valueOf(300),
				"兒童、老人及心臟、呼吸系統疾病患者人群應停留在室內，停止戶外運動，一般人群減少戶外運動。");
		AQI_DESC_TW_MAP.put(Integer.valueOf(500),
				"兒童、老人及心臟、呼吸系統疾病患者人群應停留在室內，並減少體力活動，一般人群應該避免戶外活動並注意防護。");
		AQI_DESC_LANGUAGE_MAP = new HashMap();
		AQI_DESC_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				AQI_DESC_EN_MAP);
		AQI_DESC_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				AQI_DESC_CN_MAP);
		AQI_DESC_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				AQI_DESC_TW_MAP);
		AQI_SOURCE_LANGUAGE_MAP = new HashMap();
		AQI_SOURCE_LANGUAGE_MAP.put(Locale.US.toString().toLowerCase(),
				"China National Environmental Monitoring Center");
		AQI_SOURCE_LANGUAGE_MAP.put(Locale.CHINA.toString().toLowerCase(),
				"中国环境监测总站");
		AQI_SOURCE_LANGUAGE_MAP.put(Locale.TAIWAN.toString().toLowerCase(),
				"中國環境監測總站");
		PROVINCE_FAKE_CITY_MAP = new HashMap<String,String>();
		PROVINCE_FAKE_CITY_MAP.put("101050000", "黑龙江");
		PROVINCE_FAKE_CITY_MAP.put("101060000", "吉林");
		PROVINCE_FAKE_CITY_MAP.put("101070000", "辽宁");
		PROVINCE_FAKE_CITY_MAP.put("101080000", "内蒙古");
		PROVINCE_FAKE_CITY_MAP.put("101090000", "河北");
		PROVINCE_FAKE_CITY_MAP.put("101100000", "山西");
		PROVINCE_FAKE_CITY_MAP.put("101110000", "陕西");
		PROVINCE_FAKE_CITY_MAP.put("101120000", "山东");
		PROVINCE_FAKE_CITY_MAP.put("101130000", "新疆");
		PROVINCE_FAKE_CITY_MAP.put("101140000", "西藏");
		PROVINCE_FAKE_CITY_MAP.put("101150000", "青海");
		PROVINCE_FAKE_CITY_MAP.put("101160000", "甘肃");
		PROVINCE_FAKE_CITY_MAP.put("101170000", "宁夏");
		PROVINCE_FAKE_CITY_MAP.put("101180000", "河南");
		PROVINCE_FAKE_CITY_MAP.put("101190000", "江苏");
		PROVINCE_FAKE_CITY_MAP.put("101200000", "湖北");
		PROVINCE_FAKE_CITY_MAP.put("101210000", "浙江");
		PROVINCE_FAKE_CITY_MAP.put("101220000", "安徽");
		PROVINCE_FAKE_CITY_MAP.put("101230000", "福建");
		PROVINCE_FAKE_CITY_MAP.put("101240000", "江西");
		PROVINCE_FAKE_CITY_MAP.put("101250000", "湖南");
		PROVINCE_FAKE_CITY_MAP.put("101260000", "贵州");
		PROVINCE_FAKE_CITY_MAP.put("101270000", "四川");
		PROVINCE_FAKE_CITY_MAP.put("101280000", "广东");
		PROVINCE_FAKE_CITY_MAP.put("101290000", "云南");
		PROVINCE_FAKE_CITY_MAP.put("101300000", "广西");
		PROVINCE_FAKE_CITY_MAP.put("101310000", "海南");
		PROVINCE_FAKE_CITY_MAP.put("101340000", "台湾");
	}
}
