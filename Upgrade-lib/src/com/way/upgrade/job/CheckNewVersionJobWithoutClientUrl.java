package com.way.upgrade.job;

import android.content.Context;

import com.way.upgrade.utils.Constants;

/**
 * 
 * @author way
 * @since 2014/4/28
 */
public class CheckNewVersionJobWithoutClientUrl extends
		AbstractCheckNewVersionJob {
	public CheckNewVersionJobWithoutClientUrl(Context context) {
		super(context);
	}

	@Override
	public String getCheckVersionUrl() {
		return Constants.BASE_URL;
	}

}
