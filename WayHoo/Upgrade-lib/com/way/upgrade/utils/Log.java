package com.way.upgrade.utils;

public class Log {
	public static final boolean DEBUG = true;
	public static final boolean DATA_DEBUG = true;
	public static final boolean APP_DEBUG = true;
	public static final boolean DISPLAY_DEBUG = false;
	public static final int DATA = 0;
	public static final int DISPLAY = 1;
	public static final int APP = 2;

	public static final String TAG = "zhujianwen";

	public static void v(String tag, String msg, int mod) {
		if (!DEBUG) {
			return;
		}
		if (mod == DATA && DATA_DEBUG) {
			android.util.Log.v(tag, msg);
		} else if (mod == DISPLAY && DISPLAY_DEBUG) {
			android.util.Log.v(tag, msg);
		} else if (mod == APP && APP_DEBUG) {
			android.util.Log.v(tag, msg);
		}
	}

	public static void v(String msg, int mod) {
		v(TAG, msg, mod);
	}

	public static void d(String tag, String msg, int mod) {
		if (!DEBUG) {
			return;
		}
		if (mod == DATA && DATA_DEBUG) {
			android.util.Log.d(tag, msg);
		} else if (mod == DISPLAY && DISPLAY_DEBUG) {
			android.util.Log.d(tag, msg);
		} else if (mod == APP && APP_DEBUG) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void d(String msg, int mod) {
		d(TAG, msg, mod);
	}

	public static void i(String tag, String msg, int mod) {
		if (!DEBUG) {
			return;
		}
		if (mod == DATA && DATA_DEBUG) {
			android.util.Log.i(tag, msg);
		} else if (mod == DISPLAY && DISPLAY_DEBUG) {
			android.util.Log.i(tag, msg);
		} else if (mod == APP && APP_DEBUG) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void i(String msg, int mod) {
		i(TAG, msg, mod);
	}

	public static void w(String tag, String msg, int mod) {
		if (!DEBUG) {
			return;
		}
		if (mod == DATA && DATA_DEBUG) {
			android.util.Log.w(tag, msg);
		} else if (mod == DISPLAY && DISPLAY_DEBUG) {
			android.util.Log.w(tag, msg);
		} else if (mod == APP && APP_DEBUG) {
			android.util.Log.w(tag, msg);
		}
	}

	public static void w(String msg, int mod) {
		w(TAG, msg, mod);
	}

	public static void e(String tag, String msg, int mod) {
		if (!DEBUG) {
			return;
		}
		if (mod == DATA && DATA_DEBUG) {
			android.util.Log.e(tag, msg);
		} else if (mod == DISPLAY && DISPLAY_DEBUG) {
			android.util.Log.e(tag, msg);
		} else if (mod == APP && APP_DEBUG) {
			android.util.Log.e(tag, msg);
		}
	}

	public static void e(String msg, int mod) {
		e(TAG, msg, mod);
	}
}
