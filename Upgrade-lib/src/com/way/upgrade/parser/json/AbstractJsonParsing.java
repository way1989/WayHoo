package com.way.upgrade.parser.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractJsonParsing<T> {

	public abstract T readJsonItem(JSONObject jsonItem) throws JSONException;

	/**
	 * 输入InputStream
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public ArrayList<T> readJsonArray(JSONArray jsonArray) {

		try {
			ArrayList<T> listOperating = new ArrayList<T>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				T itemBean = readJsonItem(item);
				listOperating.add(itemBean);
			}
			return listOperating;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
