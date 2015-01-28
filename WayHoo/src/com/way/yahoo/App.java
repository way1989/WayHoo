package com.way.yahoo;

import java.util.HashMap;
import java.util.Map;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.os.Debug;

import com.way.common.util.SystemUtils;
import com.way.weather.plugin.bean.WeatherInfo;
@ReportsCrashes(formKey = "",
mailTo= "way.ping.li@gmail.com",
mode = ReportingInteractionMode.DIALOG,
resDialogText = R.string.crash_dialog_text,
resDialogIcon = android.R.color.transparent,
resDialogTitle = R.string.crash_dialog_title,
resDialogCommentPrompt = R.string.crash_dialog_comment_prompt,
resDialogOkToast = R.string.crash_dialog_ok_toast)
public class App extends Application {
	public static Map<String, WeatherInfo> mMainMap;

	@Override
	public void onCreate() {
		if (!Debug.isDebuggerConnected()) {
			ACRA.init(this);
		}
		super.onCreate();
		SystemUtils.copyDB(this);// 程序第一次运行将数据库copy过去
		mMainMap = new HashMap<String, WeatherInfo>();
	}
}
