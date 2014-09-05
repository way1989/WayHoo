package com.way.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationUtils {
	private static final int MAX_TRY_COUNT = 5;
	private LocationClient mLocationClient = null;
	private LocationListener mListener;
	private int mTryCount;

	public static abstract interface LocationListener {
		public abstract void detecting();

		public abstract void succeed(String city);

		public abstract void failed();
	}

	public LocationUtils(Context context, LocationListener listener) {
		if (listener == null)
			new NullPointerException("LocationListener can't be null");
		mListener = listener;
		mLocationClient = new LocationClient(context,
				getLocationClientOption(context));
		mLocationClient.registerLocationListener(mLocationListener);
	}

	// 开始定位
	public void startLocation() {
		mLocationClient.start();
		mListener.detecting();
		mTryCount = 0;
	}

	// 结束定位
	public void stopLocation() {
		mLocationClient.stop();
		mTryCount = 0;
	}

	public boolean isStarted() {
		return mLocationClient.isStarted();
	}

	private LocationClientOption getLocationClientOption(Context context) {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms
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
			if (location == null || location.getLocType() != 161
					|| TextUtils.isEmpty(location.getCity())) {
				mTryCount++;
				if (mTryCount >= MAX_TRY_COUNT) {
					mListener.failed();
					stopLocation();
				}
				return;
			}

			String city = location.getCity().replace("市", "");
			mListener.succeed(city);
			stopLocation();// 停止定位
		}

	};
}
