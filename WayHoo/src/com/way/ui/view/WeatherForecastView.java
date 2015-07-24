package com.way.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.common.util.TimeUtils;
import com.way.common.util.WeatherIconUtils;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.yahoo.R;

public class WeatherForecastView extends WeatherBaseView {
	// 预报
	View forecastViewDay1;
	View forecastViewDay2;
	View forecastViewDay3;
	View forecastViewDay4;
	View forecastViewDay5;
	TextView forecastFootView;
	// 天气图标
	ImageView iconDay1;
	ImageView iconDay2;
	ImageView iconDay3;
	ImageView iconDay4;
	ImageView iconDay5;
	// 星期
	TextView weekDay1;
	TextView weekDay2;
	TextView weekDay3;
	TextView weekDay4;
	TextView weekDay5;
	// 最高温
	TextView highTempDay1;
	TextView highTempDay2;
	TextView highTempDay3;
	TextView highTempDay4;
	TextView highTempDay5;
	// 最低温
	TextView lowTempDay1;
	TextView lowTempDay2;
	TextView lowTempDay3;
	TextView lowTempDay4;
	TextView lowTempDay5;

	public WeatherForecastView(Context c) {
		this(c, null);
	}

	public WeatherForecastView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeatherForecastView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 预报
		forecastViewDay1 = findViewById(R.id.day1);
		forecastViewDay2 = findViewById(R.id.day2);
		forecastViewDay3 = findViewById(R.id.day3);
		forecastViewDay4 = findViewById(R.id.day4);
		forecastViewDay5 = findViewById(R.id.day5);
		forecastFootView = (TextView) findViewById(R.id.forecast_foot);
		// 天气图标
		iconDay1 = (ImageView) forecastViewDay1
				.findViewById(R.id.forecast_icon);
		iconDay2 = (ImageView) forecastViewDay2
				.findViewById(R.id.forecast_icon);
		iconDay3 = (ImageView) forecastViewDay3
				.findViewById(R.id.forecast_icon);
		iconDay4 = (ImageView) forecastViewDay4
				.findViewById(R.id.forecast_icon);
		iconDay5 = (ImageView) forecastViewDay5
				.findViewById(R.id.forecast_icon);
		// 星期
		weekDay1 = (TextView) forecastViewDay1
				.findViewById(R.id.forecast_week_tv);
		weekDay2 = (TextView) forecastViewDay2
				.findViewById(R.id.forecast_week_tv);
		weekDay3 = (TextView) forecastViewDay3
				.findViewById(R.id.forecast_week_tv);
		weekDay4 = (TextView) forecastViewDay4
				.findViewById(R.id.forecast_week_tv);
		weekDay5 = (TextView) forecastViewDay5
				.findViewById(R.id.forecast_week_tv);
		// 最高温
		highTempDay1 = (TextView) forecastViewDay1
				.findViewById(R.id.forecast_high_temp_tv);
		highTempDay2 = (TextView) forecastViewDay2
				.findViewById(R.id.forecast_high_temp_tv);
		highTempDay3 = (TextView) forecastViewDay3
				.findViewById(R.id.forecast_high_temp_tv);
		highTempDay4 = (TextView) forecastViewDay4
				.findViewById(R.id.forecast_high_temp_tv);
		highTempDay5 = (TextView) forecastViewDay5
				.findViewById(R.id.forecast_high_temp_tv);
		// 最低温
		lowTempDay1 = (TextView) forecastViewDay1
				.findViewById(R.id.forecast_low_temp_tv);
		lowTempDay2 = (TextView) forecastViewDay2
				.findViewById(R.id.forecast_low_temp_tv);
		lowTempDay3 = (TextView) forecastViewDay3
				.findViewById(R.id.forecast_low_temp_tv);
		lowTempDay4 = (TextView) forecastViewDay4
				.findViewById(R.id.forecast_low_temp_tv);
		lowTempDay5 = (TextView) forecastViewDay5
				.findViewById(R.id.forecast_low_temp_tv);
	}

	public void setWeatherInfo(Forecast forecast) {
		if (forecast == null || forecast.getType(1) < 0)
			return;
		// 天气图标
		iconDay1.setImageResource(WeatherIconUtils.getWeatherIcon(forecast
				.getType(1)));
		iconDay2.setImageResource(WeatherIconUtils.getWeatherIcon(forecast
				.getType(2)));
		iconDay3.setImageResource(WeatherIconUtils.getWeatherIcon(forecast
				.getType(3)));
		iconDay4.setImageResource(WeatherIconUtils.getWeatherIcon(forecast
				.getType(4)));
		iconDay5.setImageResource(WeatherIconUtils.getWeatherIcon(forecast
				.getType(5)));

		// 星期
		weekDay1.setText("今天");// 从今天开始
		weekDay2.setText(TimeUtils.getWeek(1, TimeUtils.XING_QI));
		weekDay3.setText(TimeUtils.getWeek(2, TimeUtils.XING_QI));
		weekDay4.setText(TimeUtils.getWeek(3, TimeUtils.XING_QI));
		weekDay5.setText(TimeUtils.getWeek(4, TimeUtils.XING_QI));
		// 最高温
		highTempDay1.setText(forecast.getTmpHigh(1) + "°");
		highTempDay2.setText(forecast.getTmpHigh(2) + "°");
		highTempDay3.setText(forecast.getTmpHigh(3) + "°");
		highTempDay4.setText(forecast.getTmpHigh(4) + "°");
		highTempDay5.setText(forecast.getTmpHigh(5) + "°");
		// 最低温
		lowTempDay1.setText(forecast.getTmpLow(1) + "°");
		lowTempDay2.setText(forecast.getTmpLow(2) + "°");
		lowTempDay3.setText(forecast.getTmpLow(3) + "°");
		lowTempDay4.setText(forecast.getTmpLow(4) + "°");
		lowTempDay5.setText(forecast.getTmpLow(5) + "°");

		forecastFootView.setText("");
	}

	@Override
	public void setWeatherInfo(WeatherInfo weatherInfo) {
		setWeatherInfo(weatherInfo.getForecast());
	}
}
