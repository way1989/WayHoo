package com.way.beans;

import com.way.weather.plugin.bean.WeatherInfo;

public class MainItem {
	private City city;
	private WeatherInfo weatherInfo;

	public MainItem() {
		super();
	}

	public MainItem(City city, WeatherInfo weatherInfo) {
		super();
		this.city = city;
		this.weatherInfo = weatherInfo;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public WeatherInfo getWeatherInfo() {
		return weatherInfo;
	}

	public void setWeatherInfo(WeatherInfo weatherInfo) {
		this.weatherInfo = weatherInfo;
	}

}
