package com.way.weather.plugin.bean;


public class RealTime {
	private int animation_type;
	private String city_code;
	private long falling_tide;
	private int humidity;
	private double pressure;
	private long pub_time;
	private long rising_tide;
	private int temp;
	private int water;
	private String weather_name;
	private String wind;

	public int getAnimation_type() {
		return this.animation_type;
	}

	public String getCity_code() {
		return this.city_code;
	}

	public long getFalling_tide() {
		return this.falling_tide;
	}

	public int getHumidity() {
		return this.humidity;
	}

	public double getPressure() {
		return this.pressure;
	}

	public long getPub_time() {
		return this.pub_time;
	}

	public long getRising_tide() {
		return this.rising_tide;
	}

	public int getTemp() {
		return this.temp;
	}

	public int getWater() {
		return this.water;
	}

	public String getWeather_name() {
		return this.weather_name;
	}

	public String getWind() {
		return this.wind;
	}

	public void setAnimation_type(int type) {
		this.animation_type = type;
	}

	public void setCity_code(String cityCode) {
		this.city_code = cityCode;
	}

	public void setFalling_tide(long fallingTide) {
		this.falling_tide = fallingTide;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public void setPub_time(long pubTime) {
		this.pub_time = pubTime;
	}

	public void setRising_tide(long risingTide) {
		this.rising_tide = risingTide;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public void setWeather_name(String weatherName) {
		this.weather_name = weatherName;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	@Override
	public String toString() {
		return "RealTime [animation_type=" + animation_type + ", city_code="
				+ city_code + ", falling_tide=" + falling_tide + ", humidity="
				+ humidity + ", pressure=" + pressure + ", pub_time="
				+ pub_time + ", rising_tide=" + rising_tide + ", temp=" + temp
				+ ", water=" + water + ", weather_name=" + weather_name
				+ ", wind=" + wind + "]";
	}

}