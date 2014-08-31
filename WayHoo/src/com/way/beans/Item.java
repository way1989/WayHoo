package com.way.beans;

public class Item {
	public static final int INFINITE_ID = -1;
	public static final int SETTING_ID = 999;
	public static final int FEEDBACK_ID = 998;
	public static final int SHARE_ID = 995;
	public static final int ABOUT_ID = 996;

	public int mId;
	public String mTitleStr;
	public int mTitleRes;
	public int mIconRes;

	public Item(int id, String title, int iconRes) {
		mId = id;
		mTitleStr = title;
		mIconRes = iconRes;
	}

	public Item(int id, int title, int iconRes) {
		mId = id;
		mTitleRes = title;
		mIconRes = iconRes;
	}
}
