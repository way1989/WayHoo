package com.way.weather.plugin.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Index implements Parcelable {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(city_code);
		dest.writeList(index);
	}

	public static final Parcelable.Creator<Index> CREATOR = new Creator<Index>() {

		@Override
		public Index[] newArray(int size) {
			return new Index[size];
		}

		@Override
		public Index createFromParcel(Parcel source) {
			Index item = new Index();
			item.city_code = source.readString();
			item.index = new ArrayList<IndexDetail>();
			source.readList(item.index, getClass().getClassLoader());
			return item;
		}
	};

}
