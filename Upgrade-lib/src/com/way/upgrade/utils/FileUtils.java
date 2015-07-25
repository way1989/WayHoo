package com.way.upgrade.utils;

import java.io.File;

/**
 * @author way 2013/12/6
 */
public class FileUtils {
	private static final String TAG = "FileUtils";

	/**
	 * @param f
	 * @throws Exception
	 */
	public static void createNewFile(File f) throws Exception {
		Log.i(TAG, "create file:" + f, Log.APP);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		if (!f.exists()) {
			f.createNewFile();
		}
	}
}
