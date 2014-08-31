package com.way.weather.plugin.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtils {
	private static final String TAG = "HttpUtils";

	public static String getText(String uri) {

		// Log.d(TAG, String.format("request get uri:%s", new Object[] { uri
		// }));
		try {
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.d(TAG, String.format("get StatusCode:%s", httpResponse
					.getStatusLine().getStatusCode()));
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			// Log.i("liweiping", "result = " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("way", "HttpUtils exception" + e.getMessage());
		}
		return null;
	}
}
