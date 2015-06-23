package com.way.yahoo;

import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.way.beans.City;
import com.way.common.util.LocationUtils;
import com.way.common.util.LocationUtils.LocationListener;
import com.way.common.util.NetUtil;
import com.way.common.util.SystemUtils;
import com.way.db.CityProvider;
import com.way.db.CityProvider.CityConstants;
import com.way.ui.swipeback.SwipeBackActivity;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.weather.plugin.spider.WeatherSpider;

public class BaseActivity extends SwipeBackActivity {
	//public static final String AUTO_LOCATION_CITY_KEY = "auto_location";
	protected ContentResolver mContentResolver;
	protected Activity mActivity;
	protected LocationUtils mLocationUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDatas();
	}

	private void initDatas() {
		mActivity = this;
		mContentResolver = getContentResolver();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	protected List<City> getTmpCities() {
		Cursor tmpCityCursor = mContentResolver.query(
				CityProvider.TMPCITY_CONTENT_URI, null, null, null, CityConstants.ORDER_INDEX + " ASC");
		return SystemUtils.getTmpCities(tmpCityCursor);
	}

	protected void startLocation(LocationListener cityNameStatus) {
		if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
			Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
			return;
		}
		if (mLocationUtils == null)
			mLocationUtils = new LocationUtils(this, cityNameStatus);
		if (!mLocationUtils.isStarted()) {
			mLocationUtils.startLocation();// 开始定位
		}
	}

	protected void stopLocation() {
		if (mLocationUtils != null && mLocationUtils.isStarted())
			mLocationUtils.stopLocation();
	}

	protected City getLocationCityFromDB(String name) {
		City city = new City();
		city.setName(name);
		Cursor c = mContentResolver.query(CityProvider.CITY_CONTENT_URI,
				new String[] { CityConstants.POST_ID }, CityConstants.NAME
						+ "=?", new String[] { name }, null);
		if (c != null && c.moveToNext())
			city.setPostID(c.getString(c.getColumnIndex(CityConstants.POST_ID)));
		return city;
	}

	protected void addOrUpdateLocationCity(City city) {
		// 先删除已定位城市
		mContentResolver.delete(CityProvider.TMPCITY_CONTENT_URI,
				CityConstants.ISLOCATION + "=?", new String[] { "1" });

		// 存储
		ContentValues tmpContentValues = new ContentValues();
		tmpContentValues.put(CityConstants.NAME, city.getName());
		tmpContentValues.put(CityConstants.POST_ID, city.getPostID());
		tmpContentValues.put(CityConstants.REFRESH_TIME, 0L);// 无刷新时间
		tmpContentValues.put(CityConstants.ISLOCATION, 1);// 手动选择的城市存储为0
		tmpContentValues.put(CityConstants.ORDER_INDEX, getTmpCities().size());
		mContentResolver.insert(CityProvider.TMPCITY_CONTENT_URI,
				tmpContentValues);
	}

}
