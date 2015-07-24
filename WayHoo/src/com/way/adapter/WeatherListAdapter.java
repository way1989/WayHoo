package com.way.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.way.ui.view.WeatherBaseView;
import com.way.ui.view.WeatherForecastView;
import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.weather.plugin.spider.WeatherSpider;
import com.way.yahoo.R;

public class WeatherListAdapter extends BaseAdapter {
	private static final int FORECAST_TYPE = 0;
	private static final int WEATHER_DETAILS_TYPE = 1;
	private static final int AQI_TYPE = 2;
	private static final int INDEX_TYPE = 3;

	private LayoutInflater mLayoutInflater;
	private List<Integer> mTypes;
	private WeatherInfo mWeatherInfo;

	public WeatherListAdapter(Context context) {
		mTypes = new ArrayList<Integer>();
		mTypes.add(FORECAST_TYPE);
		mTypes.add(WEATHER_DETAILS_TYPE);
		mTypes.add(AQI_TYPE);
		mTypes.add(INDEX_TYPE);
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void setWeather(WeatherInfo weatherInfo) {
		if(WeatherSpider.isEmpty(weatherInfo))
			return;
		mWeatherInfo = weatherInfo;
		AQI aqi = weatherInfo.getAqi();
		if ((aqi == null || aqi.getAqi() == -999) && mTypes.contains(AQI_TYPE))// 如果没有空气质量信息，则移除该选项。
			mTypes.remove(AQI_TYPE);

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
				convertView = (WeatherForecastView) mLayoutInflater
						.inflate(R.layout.weather_forecast, parent,	false);
				break;
			case WEATHER_DETAILS_TYPE:
				convertView = mLayoutInflater
						.inflate(R.layout.weather_details, parent, false);
				break;
			case AQI_TYPE:
				convertView = mLayoutInflater.inflate(
						R.layout.weather_aqi, parent, false);
				break;
			case INDEX_TYPE:
				convertView = mLayoutInflater
						.inflate(R.layout.weather_index,  parent, false);
				break;
			default:
				break;
			}
		if(convertView instanceof WeatherBaseView && !WeatherSpider.isEmpty(mWeatherInfo)){
			WeatherBaseView baseView = (WeatherBaseView) convertView;
			baseView.setWeatherInfo(mWeatherInfo);
			return baseView;
		}
		return convertView;
	}
}
