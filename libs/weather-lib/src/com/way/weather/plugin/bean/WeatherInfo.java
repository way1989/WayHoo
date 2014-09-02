package com.way.weather.plugin.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable {
	private int isNewDatas;
	private RealTime realTime;
	private Forecast forecast;
	private AQI aqi;
	private Index index;
	private Alerts alerts;

	public WeatherInfo() {
		super();
	}

	public WeatherInfo(RealTime realTime, Forecast forecast, AQI aqi,
			Index index, Alerts alerts) {
		super();
		this.realTime = realTime;
		this.forecast = forecast;
		this.aqi = aqi;
		this.index = index;
		this.alerts = alerts;
	}

	public RealTime getRealTime() {
		return realTime;
	}

	public void setRealTime(RealTime realTime) {
		this.realTime = realTime;
	}

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	public AQI getAqi() {
		return aqi;
	}

	public void setAqi(AQI aqi) {
		this.aqi = aqi;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public Alerts getAlerts() {
		return alerts;
	}

	public void setAlerts(Alerts alerts) {
		this.alerts = alerts;
	}

	public boolean getIsNewDatas() {
		return isNewDatas == 0 ? false : true;
	}

	public void setIsNewDatas(int isNewDatas) {
		this.isNewDatas = isNewDatas;
	}

	@Override
	public String toString() {
		return "WeatherInfo [isNewDatas=" + isNewDatas + ", realTime="
				+ realTime + ", forecast=" + forecast + ", aqi=" + aqi
				+ ", index=" + index + ", alerts=" + alerts + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(realTime, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeParcelable(forecast, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeParcelable(aqi, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeParcelable(index, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeParcelable(alerts, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeInt(isNewDatas);
	}

	public static final Parcelable.Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {

		@Override
		public WeatherInfo[] newArray(int size) {
			return new WeatherInfo[size];
		}

		@Override
		public WeatherInfo createFromParcel(Parcel source) {
			WeatherInfo info = new WeatherInfo();
			info.realTime = source.readParcelable(getClass().getClassLoader());
			info.forecast = source.readParcelable(getClass().getClassLoader());
			info.aqi = source.readParcelable(getClass().getClassLoader());
			info.index = source.readParcelable(getClass().getClassLoader());
			info.alerts = source.readParcelable(getClass().getClassLoader());
			info.isNewDatas = source.readInt();
			return info;
		}
	};

}
