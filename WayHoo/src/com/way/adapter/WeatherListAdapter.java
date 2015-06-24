package com.way.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.way.ui.view.WeatherAqiView;
import com.way.ui.view.WeatherDetailsView;
import com.way.ui.view.WeatherForecastView;
import com.way.ui.view.WeatherIndexView;
import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.Index;
import com.way.weather.plugin.bean.RealTime;
import com.way.yahoo.R;

public class WeatherListAdapter extends BaseAdapter {
	private static final int FORECAST_TYPE = 0;
	private static final int WEATHER_DETAILS_TYPE = 1;
	private static final int AQI_TYPE = 2;
	private static final int INDEX_TYPE = 3;

	private LayoutInflater mLayoutInflater;
	private List<Integer> mTypes;
	private List<View> mTypeViews;

	public WeatherListAdapter(Context context) {
		mTypes = new ArrayList<Integer>();
		mTypes.add(FORECAST_TYPE);
		mTypes.add(WEATHER_DETAILS_TYPE);
		mTypes.add(AQI_TYPE);
		mTypes.add(INDEX_TYPE);
		mLayoutInflater = LayoutInflater.from(context);
		mTypeViews = new ArrayList<View>();
		WeatherForecastView forecastView = (WeatherForecastView) mLayoutInflater
				.inflate(R.layout.weather_forecast, null);
		WeatherDetailsView detailsView = (WeatherDetailsView) mLayoutInflater
				.inflate(R.layout.weather_details, null);
		WeatherAqiView aqiView = (WeatherAqiView) mLayoutInflater.inflate(
				R.layout.weather_aqi, null);
		WeatherIndexView indexView = (WeatherIndexView) mLayoutInflater
				.inflate(R.layout.weather_index, null);
		mTypeViews.add(FORECAST_TYPE, forecastView);
		mTypeViews.add(WEATHER_DETAILS_TYPE, detailsView);
		mTypeViews.add(AQI_TYPE, aqiView);
		mTypeViews.add(INDEX_TYPE, indexView);

	}

	public void setWeather(RealTime realTime, AQI aqi, Forecast forecast,
			Index index) {
		if ((aqi == null || aqi.getAqi() == -999) && mTypes.contains(AQI_TYPE))// 如果没有空气质量信息，则移除该选项。
			mTypes.remove(AQI_TYPE);

		((WeatherForecastView) mTypeViews.get(FORECAST_TYPE))
				.setWeatherInfo(forecast);
		((WeatherDetailsView) mTypeViews.get(WEATHER_DETAILS_TYPE))
				.setWeatherInfo(realTime);
		((WeatherAqiView) mTypeViews.get(AQI_TYPE)).setWeatherInfo(aqi);
		((WeatherIndexView) mTypeViews.get(INDEX_TYPE)).setWeatherInfo(index);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTypes.size();
	}

	@Override
	public Object getItem(int position) {
		return mTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return mTypes.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (position < mTypes.size())
			return mTypes.get(position);
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int itemType = getItemViewType(position);
		if (convertView == null)
			switch (itemType) {
			case FORECAST_TYPE:
				convertView = mTypeViews.get(FORECAST_TYPE);
				break;
			case WEATHER_DETAILS_TYPE:
				convertView = mTypeViews.get(WEATHER_DETAILS_TYPE);
				break;
			case AQI_TYPE:
				convertView = mTypeViews.get(AQI_TYPE);
				break;
			case INDEX_TYPE:
				convertView = mTypeViews.get(INDEX_TYPE);
				break;
			default:
				break;
			}
		return convertView;
	}

}
