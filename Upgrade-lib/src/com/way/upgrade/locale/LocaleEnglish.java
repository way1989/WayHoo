package com.way.upgrade.locale;

public class LocaleEnglish extends LocaleHandler {
	public static final String defaultLocale = "en";

	@Override
	public String getDialogTitle() {
		return "Find New Version,Update Now?";
	}

	@Override
	public String getProgressDialogTitle() {
		return "Check Update";
	}

	@Override
	public String getProgressDialogMessage() {
		return "Checking";
	}

	@Override
	public String getToastMessage() {
		return "Is the latest version";
	}

	@Override
	public String getToastNetErrorMessage() {
		return "Net Error";
	}

}
