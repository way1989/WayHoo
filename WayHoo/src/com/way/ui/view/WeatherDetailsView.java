package com.way.ui.view;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.way.common.util.LunarCalendar;
import com.way.common.util.LunarCalendarConvertUtil;
import com.way.common.util.WeatherIconUtils;
import com.way.weather.plugin.bean.RealTime;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.yahoo.R;

public class WeatherDetailsView extends WeatherBaseView {
	// 详细信息
	ImageView detailsWeatherIV;
	TextView weatherNameTV;
	TextView feelsTempTV;
	TextView humidityTV;
	TextView windTV;
	TextView windDescTV;
	TextView detailsFootTV;
	private LunarCalendar mLunarCalendar;

	public WeatherDetailsView(Context c) {
		this(c, null);
	}

	public WeatherDetailsView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeatherDetailsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 详细信息
		detailsWeatherIV = (ImageView) findViewById(R.id.details_icon);
		weatherNameTV = (TextView) findViewById(R.id.weather_name_tv);
		feelsTempTV = (TextView) findViewById(R.id.feelsTemp_tv);
		humidityTV = (TextView) findViewById(R.id.humidity_tv);
		windTV = (TextView) findViewById(R.id.wind_tv);
		windDescTV = (TextView) findViewById(R.id.wind_desc);
		detailsFootTV = (TextView) findViewById(R.id.weather_details_foot_tv);
	}

	public void setWeatherInfo(RealTime realTime) {
		if (realTime == null || realTime.getAnimation_type() < 0)
			return;
		// 农历
		mLunarCalendar = new LunarCalendar(getContext());
		Time time = new Time();
		time.set(System.currentTimeMillis());
		LunarCalendarConvertUtil.parseLunarCalendar(time.year, time.month,
				time.monthDay, mLunarCalendar);
		// 绑定数据
		detailsWeatherIV.setImageResource(WeatherIconUtils
				.getWeatherIcon(realTime.getAnimation_type()));
		weatherNameTV.setText(realTime.getWeather_name());
		feelsTempTV.setText(realTime.getTemp() + "°");
		humidityTV.setText(realTime.getHumidity() + "%");
		String[] winds = realTime.getWind().split("，");
		if (winds.length > 1) {
			windTV.setText(winds[1]);
			windDescTV.setText(winds[0]);
		} else {
			windTV.setText(realTime.getWind());
		}
		// detailsFootTV.setText("中国天气网");
		// detailsFootTV.setText(mLunarCalendar.getLunarDayInfo());
		String str[] = mLunarCalendar.getLunarCalendarInfo(false);
		detailsFootTV.setText(mLunarCalendar
				.getLunarYear(mLunarCalendar.lunarYear)
				+ "("
				+ mLunarCalendar.animalsYear(mLunarCalendar.lunarYear)
				+ ")年"
				+ str[1] + str[2]);
	}

	@Override
	public void setWeatherInfo(WeatherInfo weatherInfo) {
		setWeatherInfo(weatherInfo.getRealTime());
	}
}
