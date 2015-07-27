package com.way.upgrade.locale;

/**
 * 
 * @author way
 * @since 2014/10/29
 */
public class LocaleChinaTW extends LocaleChinese {
	public static final String defaultLocale = "zh_TW";

	@Override
	public String getDialogTitle() {
		return "發現新版本，是否升級？";
	}

	@Override
	public String getProgressDialogTitle() {
		return "檢查更新";
	}

	@Override
	public String getProgressDialogMessage() {
		return "正在檢查...";
	}

	@Override
	public String getToastMessage() {
		return "已是最新版本";
	}

	@Override
	public String getToastNetErrorMessage() {
		return "網路異常";
	}

}
