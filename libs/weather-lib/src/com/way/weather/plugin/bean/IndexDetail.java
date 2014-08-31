package com.way.weather.plugin.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class IndexDetail implements Parcelable{
	private String desc;
	private String detail;
	private String title;
	private int type;

	public String getDesc() {
		return this.desc;
	}

	public String getDetail() {
		return this.detail;
	}

	public String getTitle() {
		return this.title;
	}

	public int getType() {
		return this.type;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "IndexDetail [desc=" + desc + ", detail=" + detail + ", title="
				+ title + ", type=" + type + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(desc);
		dest.writeString(detail);
		dest.writeString(title);
		dest.writeInt(type);
	}
	public static final Parcelable.Creator<IndexDetail> CREATOR = new Creator<IndexDetail>() {
		
		@Override
		public IndexDetail[] newArray(int size) {
			return new IndexDetail[size];
		}
		
		@Override
		public IndexDetail createFromParcel(Parcel source) {
			IndexDetail indexDetail = new IndexDetail();
			indexDetail.desc = source.readString();
			indexDetail.detail = source.readString();
			indexDetail.title = source.readString();
			indexDetail.type = source.readInt();
			return indexDetail;
		}
	};
}
