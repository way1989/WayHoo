package com.way.weather.plugin.bean;

import java.util.Arrays;

import com.way.weather.plugin.util.Constants;

public class Forecast {
	public static final int DAY_NUM = 6;
	private String pid;
	private Long pub_time;
	private Integer[] humiditys = new Integer[DAY_NUM];
	private Integer[] pressures = new Integer[DAY_NUM];
	private Long[] sunrise = new Long[DAY_NUM];
	private Long[] sunset = new Long[DAY_NUM];
	private Integer[] tmpHighs = new Integer[DAY_NUM];
	private Integer[] tmpLows = new Integer[DAY_NUM];
	private String[] weatherNames = new String[DAY_NUM];
	private String[] weatherNamesFrom = new String[DAY_NUM];
	private String[] weatherNamesTo = new String[DAY_NUM];
	private String[] winds = new String[DAY_NUM];

	private int[] types = new int[DAY_NUM];

	public int getHumidity(int position) {
		if (position >= DAY_NUM || position < 0
				|| this.humiditys[position] == null)
			return Constants.NO_VALUE_FLAG;
		return humiditys[position];
	}

	public String getPid() {
		return pid;
	}

	public int getPressure(int position) {
		if (position >= DAY_NUM || position < 0 || pressures[position] == null)
			return Constants.NO_VALUE_FLAG;
		return pressures[position];
	}

	public long getPubtime() {
		return pub_time;
	}

	public long getSunrise(int position) {
		if (position >= DAY_NUM || position < 0)
			return Constants.NO_VALUE_FLAG;
		return sunrise[position];
	}

	public long getSunset(int position) {
		if (position >= DAY_NUM || position < 0)
			return Constants.NO_VALUE_FLAG;
		return sunset[position];
	}

	public int getTmpHigh(int position) {
		if (position >= DAY_NUM || position < 0 || tmpHighs[position] == null)
			return Constants.NO_VALUE_FLAG;
		return tmpHighs[position];
	}

	public int getTmpLow(int position) {
		if (position >= DAY_NUM || position < 0 || tmpLows[position] == null)
			return Constants.NO_VALUE_FLAG;
		return tmpLows[position];
	}

	public String getWeatherNames(int position) {
		if (position >= DAY_NUM || position < 0)
			return "";
		return weatherNames[position];
	}

	public String getWeatherNamesFrom(int position) {
		if (position >= DAY_NUM || position < 0)
			return "";
		return weatherNamesFrom[position];
	}

	public String getWeatherNamesTo(int position) {
		if (position >= DAY_NUM || position < 0)
			return "";
		return weatherNamesTo[position];
	}

	public String getWinds(int position) {
		if (position >= DAY_NUM || position < 0)
			return "";
		return winds[position];
	}

	public void setHumiditys(int position, int humidity) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.humiditys[position] = humidity;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setPressures(int position, int pressure) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.pressures[position] = pressure;
	}

	public void setPubtime(Long pubtime) {
		this.pub_time = pubtime;
	}

	public void setSunrise(int position, long sunrise) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.sunrise[position] = sunrise;
	}

	public void setSunset(int position, long sunset) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.sunset[position] = sunset;
	}

	public void setTmpHighs(int position, int tmpHigh) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.tmpHighs[position] = tmpHigh;
	}

	public void setTmpLows(int position, int tmpLow) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.tmpLows[position] = tmpLow;
	}

	public void setWeatherNames(int position, String weatherName) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.weatherNames[position] = weatherName;
	}

	public void setWeatherNamesFrom(int position, String weatherNameFrom) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.weatherNamesFrom[position] = weatherNameFrom;
	}

	public void setWeatherNamesTo(int position, String weatherNameTo) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.weatherNamesTo[position] = weatherNameTo;
	}

	public void setWinds(int position, String wind) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.winds[position] = wind;
	}

	public int getType(int position) {
		if (position >= DAY_NUM || position < 0)
			return Constants.NO_VALUE_FLAG;
		return types[position];
	}

	public void setType(int position, int type) {
		if (position >= DAY_NUM || position < 0)
			return;
		this.types[position] = type;
	}

	@Override
	public String toString() {
		return "Forecast [humiditys=" + Arrays.toString(humiditys) + ", pid="
				+ pid + ", pressures=" + Arrays.toString(pressures)
				+ ", pub_time=" + pub_time + ", sunrise="
				+ Arrays.toString(sunrise) + ", sunset="
				+ Arrays.toString(sunset) + ", tmpHighs="
				+ Arrays.toString(tmpHighs) + ", tmpLows="
				+ Arrays.toString(tmpLows) + ", weatherNames="
				+ Arrays.toString(weatherNames) + ", weatherNamesFrom="
				+ Arrays.toString(weatherNamesFrom) + ", weatherNamesTo="
				+ Arrays.toString(weatherNamesTo) + ", winds="
				+ Arrays.toString(winds) + ", types=" + Arrays.toString(types)
				+ "]";
	}

}
