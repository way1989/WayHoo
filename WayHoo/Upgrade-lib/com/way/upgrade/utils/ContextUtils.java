package com.way.upgrade.utils;

import java.lang.reflect.Method;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * 上下文操作，如创建对话框，进度条,版本信息...
 * 
 * @author way 2013/12/6
 */
public class ContextUtils {
	private static final String TAG = "ContextUtils";

	/**
	 * Show a Toast(Toast.LENGTH_SHORT).
	 * 
	 * @param text
	 *            the content shown on the Toast.
	 */
	public static void showToast(Context context, String text, int length) {
		Toast.makeText(context, text, length).show();
	}

	/**
	 * Show a Toast(Toast.LENGTH_SHORT).
	 * 
	 * @param text
	 *            the content shown on the Toast.
	 */
	public static void showToast(Context context, int resId, int length) {
		Toast.makeText(context, resId, length).show();
	}

	public static ProgressDialog createProgressDialog(Context context) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		return dialog;
	}

	/**
	 * show progress dialog
	 * 
	 * @param context
	 * @param title
	 *            Dialog title
	 * @param message
	 *            Dialog message
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int title,
			int message) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(context.getResources().getString(message));
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.show();
		return dialog;
	}

	/**
	 * close progress dialog
	 * 
	 * @param progressDialog
	 */
	public static void closeProgressDialog(Dialog progressDialog) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.cancel();
		}
	}

	/**
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param buttonTexts
	 * @param listeners
	 * @return
	 */
	public static AlertDialog.Builder showDialog(Context context, int title,
			int message, int[] buttonTexts, OnClickListener[] listeners) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		if (buttonTexts.length == 1) {
			dialog.setNeutralButton(buttonTexts[0], listeners[0]);
		} else if (buttonTexts.length == 2) {
			dialog.setPositiveButton(buttonTexts[0], listeners[0]);
			dialog.setNegativeButton(buttonTexts[1], listeners[1]);
		} else if (buttonTexts.length == 3) {
			dialog.setPositiveButton(buttonTexts[0], listeners[0]);
			dialog.setNeutralButton(buttonTexts[1], listeners[1]);
			dialog.setNegativeButton(buttonTexts[2], listeners[2]);
		}
		dialog.show();
		return dialog;
	}

	/**
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param buttonTexts
	 * @param listeners
	 * @return
	 */
	public static AlertDialog.Builder showDialog(Context context, String title,
			String message, int[] buttonTexts, OnClickListener[] listeners) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		if (buttonTexts.length == 1) {
			dialog.setNeutralButton(buttonTexts[0], listeners[0]);
		} else if (buttonTexts.length == 2) {
			dialog.setPositiveButton(buttonTexts[0], listeners[0]);
			dialog.setNegativeButton(buttonTexts[1], listeners[1]);
		} else if (buttonTexts.length == 3) {
			dialog.setPositiveButton(buttonTexts[0], listeners[0]);
			dialog.setNeutralButton(buttonTexts[1], listeners[1]);
			dialog.setNegativeButton(buttonTexts[2], listeners[2]);
		}
		dialog.show();
		return dialog;
	}

	/**
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param buttonTexts
	 * @param listeners
	 * @return
	 */
	public static AlertDialog showAlertDialog(Context context, String title,
			String message, int[] buttonTexts, OnClickListener[] listeners) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		if (buttonTexts.length == 1) {
			dialog.setNeutralButton(buttonTexts[0], listeners[0]);
		} else if (buttonTexts.length == 2) {
			dialog.setPositiveButton(buttonTexts[0], listeners[0]);
			dialog.setNegativeButton(buttonTexts[1], listeners[1]);
		} else if (buttonTexts.length == 3) {
			dialog.setPositiveButton(buttonTexts[0], listeners[0]);
			dialog.setNeutralButton(buttonTexts[1], listeners[1]);
			dialog.setNegativeButton(buttonTexts[2], listeners[2]);
		}
		return dialog.create();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getCustomVersion() {
		try {
			String className = "android.os.SystemProperties";
			String methodName = "get";
			String key = "ro.custom.build.version";
			Class clazz = Class.forName(className);
			// Constructor con = clazz.getEnclosingConstructor();
			Method method = clazz.getDeclaredMethod(methodName, String.class);
			return (String) method.invoke(clazz, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = -1;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return versionCode;
	}

	/**
	 * 获取版本信息
	 * 
	 * @param mContext
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			versionName = info.versionName;
			// int versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return versionName;
	}

	public static String getUserAgent(Context context) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			throw new IllegalStateException("getPackageInfo failed");
		}
		return String.format("%s/%s; %s/%s/%s/%s; %s/%s/%s",
				packageInfo.packageName, packageInfo.versionName, Build.BRAND,
				Build.DEVICE, Build.MODEL, Build.ID, Build.VERSION.SDK_INT,
				Build.VERSION.RELEASE, Build.VERSION.INCREMENTAL);
	}

	public static Intent getActivityIntent(Context context, String packageName,
			String className) {
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent();
		ComponentName compoentName = new ComponentName(packageName, className);
		intent.setComponent(compoentName);
		ResolveInfo ri = pm.resolveActivity(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (ri != null) {
			return intent;
		}
		return null;
	}
}
