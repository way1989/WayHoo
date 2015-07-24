package com.way.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.yahoo.R;

public class WeatherAqiView extends WeatherBaseView {
	// 空气质量
	ImageView aqiIV;
	TextView aqiLevelTV;
	TextView aqiTV;
	TextView pm25TV;
	TextView aqiDescTV;
	TextView aqiFootTV;

	public WeatherAqiView(Context c) {
		this(c, null);
	}

	public WeatherAqiView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeatherAqiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 空气质量
		aqiIV = (ImageView) findViewById(R.id.aqi_icon);
		aqiLevelTV = (TextView) findViewById(R.id.aqi_level);
		aqiTV = (TextView) findViewById(R.id.aqi);
		pm25TV = (TextView) findViewById(R.id.pm25);
		aqiDescTV = (TextView) findViewById(R.id.aqi_desc);
		aqiFootTV = (TextView) findViewById(R.id.weather_aqi_foot_tv);
	}

	public void setWeatherInfo(AQI aqi) {
		if (aqi == null || aqi.getAqi() < 0)
			return;
		aqiIV.setImageResource(getAqiIcon(aqi.getAqi()));
		aqiLevelTV.setText(aqi.getAqi_level());
		aqiTV.setText(aqi.getAqi() + "μg/m³");
		pm25TV.setText(aqi.getPm25() + "μg/m³");
		aqiDescTV.setText(aqi.getAqi_desc());
		aqiFootTV.setText("中国环境检测总站");
	}

	private int getAqiIcon(int aqi) {
		int aqi_img = R.drawable.biz_plugin_weather_0_50;
		if (aqi > 300) {
			aqi_img = R.drawable.biz_plugin_weather_greater_300;
		} else if (aqi > 200) {
			aqi_img = R.drawable.biz_plugin_weather_201_300;
		} else if (aqi > 150) {
			aqi_img = R.drawable.biz_plugin_weather_151_200;
		} else if (aqi > 100) {
			aqi_img = R.drawable.biz_plugin_weather_101_150;
		} else if (aqi > 50) {
			aqi_img = R.drawable.biz_plugin_weather_51_100;
		} else {
			aqi_img = R.drawable.biz_plugin_weather_0_50;
		}
		return aqi_img;
	}

	@Override
	public void setWeatherInfo(WeatherInfo weatherInfo) {
		setWeatherInfo(weatherInfo.getAqi());
	}
}
