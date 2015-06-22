package com.way.weather.plugin.bean;

import java.util.List;

public class Index {
	private String city_code;
	private List<IndexDetail> index;

	public String getCity_code() {
		return this.city_code;
	}

	public List<IndexDetail> getIndex() {
		return this.index;
	}

	public void setCity_code(String paramString) {
		this.city_code = paramString;
	}

	public void setIndex(List<IndexDetail> paramList) {
		this.index = paramList;
	}

	@Override
	public String toString() {
		return "Index [city_code=" + city_code + ", index=" + index + "]";
	}

}
