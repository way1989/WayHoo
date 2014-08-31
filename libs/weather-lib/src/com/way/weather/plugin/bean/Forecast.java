package com.way.weather.plugin.bean;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

import com.way.weather.plugin.util.Constants;

public class Forecast implements Parcelable {
	public static final int DayNum = 6;
	private Integer[] humiditys = new Integer[DayNum];
	private String pid;
	private Integer[] pressures = new Integer[DayNum];
	private Long pub_time;
	private Long[] sunrise = new Long[DayNum];
	private Long[] sunset = new Long[DayNum];
	private Integer[] tmpHighs = new Integer[DayNum];
	private Integer[] tmpLows = new Integer[DayNum];
	private String[] weatherNames = new String[DayNum];
	private String[] weatherNamesFrom = new String[DayNum];
	private String[] weatherNamesTo = new String[DayNum];
	private String[] winds = new String[DayNum];

	private int[] types = new int[6];

	public int getHumidity(int position) {
		if ((position > 5) || (position < 0)
				|| (this.humiditys[position] == null))
			return Constants.NO_VALUE_FLAG;
		return this.humiditys[position].intValue();
	}

	public String getPid() {
		return this.pid;
	}

	public int getPressure(int position) {
		if ((position > 5) || (position < 0)
				|| (this.pressures[position] == null))
			return Constants.NO_VALUE_FLAG;
		return this.pressures[position].intValue();
	}

	public Long getPubtime() {
		return this.pub_time;
	}

	public String getSunrise(int position) {
		if ((position > 5) || (position < 0))
			return "";
		return this.sunrise[position].toString();
	}

	public String getSunset(int position) {
		if ((position > 5) || (position < 0))
			return "";
		return this.sunset[position].toString();
	}

	public int getTmpHigh(int position) {
		if ((position > 5) || (position < 0)
				|| (this.tmpHighs[position] == null))
			return Constants.NO_VALUE_FLAG;
		return this.tmpHighs[position].intValue();
	}

	public int getTmpLow(int position) {
		if ((position > 5) || (position < 0)
				|| (this.tmpLows[position] == null))
			return Constants.NO_VALUE_FLAG;
		return this.tmpLows[position].intValue();
	}

	public String getWeatherNames(int position) {
		if ((position > 5) || (position < 0))
			return "";
		return this.weatherNames[position];
	}

	public String getWeatherNamesFrom(int position) {
		if ((position > 5) || (position < 0))
			return "";
		return this.weatherNamesFrom[position];
	}

	public String getWeatherNamesTo(int position) {
		if ((position > 5) || (position < 0))
			return "";
		return this.weatherNamesTo[position];
	}

	public String getWinds(int position) {
		if ((position > 5) || (position < 0))
			return "";
		return this.winds[position];
	}

	public void setHumiditys(int position, int value) {
		if ((position > 5) || (position < 0))
			return;
		this.humiditys[position] = Integer.valueOf(value);
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setPressures(int position, int value) {
		if ((position > 5) || (position < 0))
			return;
		this.pressures[position] = Integer.valueOf(value);
	}

	public void setPubtime(Long pubtime) {
		this.pub_time = pubtime;
	}

	public void setSunrise(int position, Long time) {
		if ((position > 5) || (position < 0))
			return;
		this.sunrise[position] = time;
	}

	public void setSunset(int position, Long time) {
		if ((position > 5) || (position < 0))
			return;
		this.sunset[position] = time;
	}

	public void setTmpHighs(int position, int value) {
		if ((position > 5) || (position < 0))
			return;
		this.tmpHighs[position] = Integer.valueOf(value);
	}

	public void setTmpLows(int position, int value) {
		if ((position > 5) || (position < 0))
			return;
		this.tmpLows[position] = Integer.valueOf(value);
	}

	public void setWeatherNames(int position, String value) {
		if ((position > 5) || (position < 0))
			return;
		this.weatherNames[position] = value;
	}

	public void setWeatherNamesFrom(int position, String weatherName) {
		if ((position > 5) || (position < 0))
			return;
		this.weatherNamesFrom[position] = weatherName;
	}

	public void setWeatherNamesTo(int position, String weatherName) {
		if ((position > 5) || (position < 0))
			return;
		this.weatherNamesTo[position] = weatherName;
	}

	public void setWinds(int position, String wind) {
		if ((position > 5) || (position < 0))
			return;
		this.winds[position] = wind;
	}

	public int getType(int position) {
		if (position > 5 || position < 0)
			return Constants.NO_VALUE_FLAG;
		return types[position];
	}

	public void setType(int position, int type) {
		if (position > 5 || position < 0)
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeArray(humiditys);
		dest.writeString(pid);
		dest.writeArray(pressures);
		dest.writeLong(pub_time);
		dest.writeArray(sunrise);
		dest.writeArray(sunset);
		dest.writeArray(tmpHighs);
		dest.writeArray(tmpLows);
		dest.writeStringArray(weatherNames);
		dest.writeStringArray(weatherNamesFrom);
		dest.writeStringArray(weatherNamesTo);
		dest.writeStringArray(winds);
		dest.writeIntArray(types);
	}

	public static final Parcelable.Creator<Forecast> CREATOR = new Creator<Forecast>() {

		@Override
		public Forecast[] newArray(int size) {
			return new Forecast[size];
		}

		@Override
		public Forecast createFromParcel(Parcel source) {
			Forecast item = new Forecast();
			item.humiditys = (Integer[]) source.readArray(getClass()
					.getClassLoader());
			item.pid = source.readString();
			item.pressures = (Integer[]) source.readArray(getClass()
					.getClassLoader());
			item.pub_time = source.readLong();
			item.sunrise = (Long[]) source.readArray(getClass()
					.getClassLoader());
			item.sunset = (Long[]) source
					.readArray(getClass().getClassLoader());
			item.tmpHighs = (Integer[]) source.readArray(getClass()
					.getClassLoader());
			item.tmpLows = (Integer[]) source.readArray(getClass()
					.getClassLoader());
			item.weatherNames = new String[DayNum];
			source.readStringArray(item.weatherNames);
			item.weatherNamesFrom = new String[DayNum];
			source.readStringArray(item.weatherNamesFrom);
			item.weatherNamesTo = new String[DayNum];
			source.readStringArray(item.weatherNamesTo);
			item.winds = new String[DayNum];
			source.readStringArray(item.winds);
			item.types = new int[DayNum];
			source.readIntArray(item.types);
			return item;
		}
	};
}
