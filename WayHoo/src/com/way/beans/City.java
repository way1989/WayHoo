package com.way.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
	private String province;
	private String city;
	private String name;
	private String pinyin;
	private String py;
	private String phoneCode;
	private String areaCode;
	private String postID;
	private int isSelected;
	private long refreshTime;
	private int isLocation;

	public City() {
	}

	public City(String name, String postID, long refreshTime, int isLocation) {
		super();
		this.name = name;
		this.postID = postID;
		this.refreshTime = refreshTime;
		this.isLocation = isLocation;
	}

	public City(String name, String postID, int isSelected) {
		super();
		this.name = name;
		this.postID = postID;
		this.isSelected = isSelected;
	}

	public City(String province, String city, String name, String pinyin,
			String py, String phoneCode, String areaCode, String postID) {
		super();
		this.province = province;
		this.city = city;
		this.name = name;
		this.pinyin = pinyin;
		this.py = py;
		this.phoneCode = phoneCode;
		this.areaCode = areaCode;
		this.postID = postID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public boolean getIsSelected() {
		return isSelected == 0 ? false : true;
	}

	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}

	public boolean getIsLocation() {
		return isLocation == 0 ? false : true;
	}

	public void setIsLocation(int isLocation) {
		this.isLocation = isLocation;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + postID != null ? postID.hashCode() : 0;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o instanceof City) {
			City item = (City) o;
			if (item.getPostID().equals(this.postID))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "City [province=" + province + ", city=" + city + ", name="
				+ name + ", pinyin=" + pinyin + ", py=" + py + ", phoneCode="
				+ phoneCode + ", areaCode=" + areaCode + ", postID=" + postID
				+ ", isSelected=" + isSelected + ", refreshTime=" + refreshTime
				+ ", isLocation=" + isLocation + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(province);
		dest.writeString(city);
		dest.writeString(name);
		dest.writeString(pinyin);
		dest.writeString(py);
		dest.writeString(phoneCode);
		dest.writeString(areaCode);
		dest.writeString(postID);
		dest.writeInt(isSelected);
		dest.writeLong(refreshTime);
		dest.writeInt(isLocation);
	}

	public static final Parcelable.Creator<City> CREATOR = new Creator<City>() {

		@Override
		public City createFromParcel(Parcel source) {
			City city = new City();
			city.province = source.readString();
			city.city = source.readString();
			city.name = source.readString();
			city.pinyin = source.readString();
			city.py = source.readString();
			city.phoneCode = source.readString();
			city.areaCode = source.readString();
			city.postID = source.readString();
			city.isSelected = source.readInt();
			city.refreshTime = source.readLong();
			city.isLocation = source.readInt();
			return city;
		}

		@Override
		public City[] newArray(int size) {
			return new City[size];
		}

	};

}
