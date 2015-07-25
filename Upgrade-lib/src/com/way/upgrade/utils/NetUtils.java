package com.way.upgrade.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络操作
 * 
 * @author way 2013/12/6
 */
public class NetUtils {
	private static final String TAG = "NetUtils";

	private static final String NETTYPE_WIFI = "WIFI";

	private static String multipart_form_data = "multipart/form-data";
	private static String twoHyphens = "--";
	private static String boundary = java.util.UUID.randomUUID().toString(); // 数据分隔
	private static String lineEnd = "\r\n"; // The value is "\r\n" in Windows.

	public enum NETWORK_STATUS {
		STATE_WIFI, STATE_GPRS, STATE_NONE_NETWORK
	}

	private NetUtils() {
	}

	public static NETWORK_STATUS getNetworkType(Context context) {
		NETWORK_STATUS ret = NETWORK_STATUS.STATE_NONE_NETWORK;

		ConnectivityManager connetManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connetManager == null) {
			Log.e(TAG, "isNetWorkAvailable connetManager = null", Log.APP);
			return ret;
		}
		NetworkInfo[] infos = connetManager.getAllNetworkInfo();
		if (infos == null) {
			return ret;
		}
		for (int i = 0; i < infos.length && infos[i] != null; i++) {
			if (infos[i].isConnected() && infos[i].isAvailable()) {

				if (infos[i].getTypeName().equalsIgnoreCase(NETTYPE_WIFI)) {
					ret = NETWORK_STATUS.STATE_WIFI;
				} else {
					ret = NETWORK_STATUS.STATE_GPRS;
				}

				break;
			}
		}

		Log.i(TAG, "get network stype is : " + ret, Log.APP);
		return ret;

	}

	/**
	 * 
	 * @param context
	 * @param typeName
	 *            ("",WIFI,MOBILE)
	 * @return
	 */
	public static boolean isNetWorkAvailable(Context context, String typeName) {

		Log.i(TAG, ">>> isNetWorkAvailable context = " + context
				+ "typeName = " + typeName, Log.APP);

		boolean ret = false;

		ConnectivityManager connetManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connetManager == null) {
			Log.e(TAG, "isNetWorkAvailable connetManager = null", Log.APP);
			return ret;
		}
		NetworkInfo[] infos = connetManager.getAllNetworkInfo();
		if (infos == null) {
			return ret;
		}
		if ((typeName == null) || (typeName.length() <= 0)) {
			for (int i = 0; i < infos.length && infos[i] != null; i++) {
				if (infos[i].isConnected() && infos[i].isAvailable()) {
					ret = true;
					break;
				}
			}
		} else {
			for (int i = 0; i < infos.length && infos[i] != null; i++) {
				if (infos[i].getTypeName().equalsIgnoreCase(typeName)
						&& infos[i].isConnected() && infos[i].isAvailable()) {
					Log.i(TAG,
							"isNetWorkAvailable name is : "
									+ infos[i].getTypeName(), Log.APP);
					ret = true;
					break;
				}
			}
		}

		Log.i(TAG, "isNetWorkAvailable >>> result is : " + ret, Log.APP);
		return ret;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static synchronized InputStream getInputStreamByGet(String url)
			throws IOException {
		Log.i(TAG, "<getInputStreamByGet> url:" + url, Log.APP);
		HttpURLConnection httpConnection = null;
		int currentSize = 0;
		if (url == null) {
			return null;
		}
		URL uri = new URL(url);
		httpConnection = (HttpURLConnection) uri.openConnection();
		httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
		if (currentSize > 0) {
			httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize
					+ "-");
		}
		// 设置超时时间
		httpConnection.setConnectTimeout(10000);// 限制连接超时5秒钟
		httpConnection.setReadTimeout(2 * 10000);
		httpConnection.setRequestProperty("Content-type",
				"text/html;charset=UTF-8");

		httpConnection.setDoOutput(true);
		httpConnection.setRequestMethod("GET");
		httpConnection.setUseCaches(false);
		int requestCode = httpConnection.getResponseCode();
		if (requestCode == 200) {
			InputStream in = httpConnection.getInputStream();
			return in;
		}
		return null;

	}

	/**
	 * post方式从服务器获取json数组
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws JSONException
	 */
	public static JSONArray getJSONArrayByPost(String uri)
			throws ClientProtocolException, IOException, JSONException {
		Log.i(TAG, "<getJSONArrayByPost> uri:" + uri, Log.APP);
		StringBuilder builder = new StringBuilder();
		HttpParams httpParameters = new BasicHttpParams();
		// Set the default socket timeout (SO_TIMEOUT) in milliseconds which is
		// the timeout for waiting for data.
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);
		HttpClient client = new DefaultHttpClient(httpParameters);
		HttpPost post = new HttpPost(uri);

		HttpResponse response = client.execute(post);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				response.getEntity().getContent()));
		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
			builder.append(s);
		}

		String jsonString = new String(builder.toString());

		if ("{}".equals(jsonString)) {
			return null;
		}
		Log.i(TAG, "<getJSONArrayByPost> jsonString:" + jsonString, Log.DATA);
		return new JSONArray(jsonString);
	}

	/**
	 * get方式从服务器获取json数组
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws JSONException
	 */
	public static JSONObject getJSONArrayByGet(String uri)
			throws ClientProtocolException, IOException, JSONException {
		Log.i(TAG, "<getJSONArrayByGet> uri:" + uri, Log.APP);
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response = client.execute(get);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				response.getEntity().getContent()));
		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
			builder.append(s);
		}

		String jsonString = new String(builder.toString());

		if ("{}".equals(jsonString)) {
			return null;
		}
		Log.i(TAG, "<getJSONArrayByGet> jsonString:" + jsonString, Log.DATA);
		return new JSONObject(jsonString);
	}

	/**
	 * 使用post的方式，提交表单，不包括文件上传(新服务器)
	 * 
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static boolean uploadParamsByPost(String actionUrl,
			Map<String, String> params) throws IOException {
		Log.i(TAG, "<uploadParamsByPost> actionUrl:" + actionUrl + " params:"
				+ params, Log.APP);
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

		conn.setReadTimeout(10 * 1000);
		conn.setConnectTimeout(10 * 1000);
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);
		conn.setRequestMethod("POST"); // Post方式
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");

		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());

		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		outStream.close();
		// 得到响应号
		int res = conn.getResponseCode();
		if (res == 200) {
			return true;
		}
		conn.disconnect();

		return false;
	}

	/**
	 * 使用post的方式，提交表单，不包括文件上传(老服务器写的代码使用这种方式)
	 * 
	 * @param actionUrl
	 *            :http://xxx/xxx.json
	 * @param query
	 *            :Helpers.combinaStr("login_name=#&password=#&email=#&name=",
	 *            listParams);
	 * @return
	 */
	public static JSONObject uploadParamsByPost(String actionUrl, String query) {
		Log.i(TAG, "<uploadParamsByPost> actionUrl:" + actionUrl + " query:"
				+ query, Log.APP);
		try {
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

			conn.setReadTimeout(10 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");

			// query is your body
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");// 请求�?
															// 必须设置
			conn.setRequestProperty("Content-Length", query.toString()
					.getBytes("UTF-8").length + "");// 注意是字节长�?
													// 不是字符长度
			conn.getOutputStream().write(query.toString().getBytes("UTF-8"));
			// 得到响应�?
			int res = conn.getResponseCode();

			if (res == HttpURLConnection.HTTP_OK) {
				StringBuffer stringBuffer = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				// 处理响应流，必须与服务器响应流输出的编码�?��
				responseReader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					stringBuffer.append(readLine).append("/n");
				}
				responseReader.close();

				return new JSONObject(stringBuffer.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int uploadFilesByPost(String actionUrl, String fileName, File file) {
		Log.i(TAG, "<uploadFilesByPost> actionUrl:" + actionUrl + " fileName:"
				+ fileName, Log.APP);
		String CHARSET = "UTF-8";

		// 得到响应�?
		int res = 0;
		try {
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(10 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");

			conn.setRequestProperty("Content-Type", multipart_form_data
					+ ";boundary=" + boundary);

			// 输出�?
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());

			// 发�?文件数据
			if (file != null) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(twoHyphens);
				sb1.append(boundary);
				sb1.append(lineEnd);
				// actionData 是自己定义的
				sb1.append("Content-Disposition: form-data; name=\"actionData\"; filename=\""
						+ fileName + "\"" + lineEnd);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + lineEnd);
				sb1.append(lineEnd);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(lineEnd.getBytes());
			}

			// 请求结束标志
			byte[] end_data = (twoHyphens + boundary + twoHyphens + lineEnd)
					.getBytes();

			outStream.write(end_data);
			outStream.flush();
			res = conn.getResponseCode();

			outStream.close();
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;

	}

}
