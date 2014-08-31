package com.way.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationUtils {
	private LocationClient mLocationClient = null;
	private CityNameStatus mCityNameStatus;

	public static abstract interface CityNameStatus {
		public abstract void detecting();

		public abstract void update(String city);
	}

	public LocationUtils(Context context, CityNameStatus cityNameStatus) {
		mCityNameStatus = cityNameStatus;
		mLocationClient = new LocationClient(context,
				getLocationClientOption(context));
		mLocationClient.registerLocationListener(mLocationListener);
	}

	// 开始定位
	public void startLocation() {
		mLocationClient.start();
		mCityNameStatus.detecting();
	}

	// 结束定位
	public void stopLocation() {
		mLocationClient.stop();

	}

	public boolean isStarted() {
		return mLocationClient.isStarted();
	}

	private LocationClientOption getLocationClientOption(Context context) {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(2000);// 设置发起定位请求的间隔时间为2000ms
		option.setProdName(context.getPackageName());
		option.setIsNeedAddress(true);
		return option;
	}

	/**
	 * 实现定位回调监听
	 */
	BDLocationListener mLocationListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || TextUtils.isEmpty(location.getCity()))
				return;
			if (location.getLocType() != 161)
				return;

			String city = location.getCity().replace("市", "");
			mCityNameStatus.update(city);
			mLocationClient.stop();// 停止定位
		}

	};
}
