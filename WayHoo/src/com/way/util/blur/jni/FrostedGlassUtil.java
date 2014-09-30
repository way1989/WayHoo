package com.way.util.blur.jni;

import android.graphics.Bitmap;

public class FrostedGlassUtil {
	private static final String TAG = "FrostedGlass";
	private volatile static FrostedGlassUtil mFrostedGlassUtil;

	public static FrostedGlassUtil getInstance() {
		if (mFrostedGlassUtil == null) {
			synchronized (FrostedGlassUtil.class) {
				if (mFrostedGlassUtil == null) {
					mFrostedGlassUtil = new FrostedGlassUtil();
				}
			}
		}
		return mFrostedGlassUtil;
	}

	public native void boxBlur(Bitmap srcBitmap, int radius);

	public native void stackBlur(Bitmap srcBitmap, int radius);

	public native void oilPaint(Bitmap srcBitmap, int radius);

	public native void colorWaterPaint(Bitmap srcBitmap, int radius);

	public synchronized Bitmap convertToBlur(Bitmap bmp, int radius) {
		stackBlur(bmp, radius);
		return bmp;
	}

	static {
		// load frosted glass lib
		System.loadLibrary("frostedGlass");
	}
}
