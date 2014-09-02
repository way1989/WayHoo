package com.way.weather.plugin.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AQI implements Parcelable {
	private int aqi;
	private String aqi_desc;
	private String aqi_level;
	private String city_code;
	private int co;
	private int no2;
	private int o3;
	private int pm10;
	private int pm25;
	private long pub_time;
	private int so2;
	private String source;
	private String spot;

	public int getAqi() {
		return this.aqi;
	}

	public String getAqi_desc() {
		return this.aqi_desc;
	}

	public String getAqi_level() {
		return this.aqi_level;
	}

	public String getCity_code() {
		return this.city_code;
	}

	public int getCo() {
		return this.co;
	}

	public int getNo2() {
		return this.no2;
	}

	public int getO3() {
		return this.o3;
	}

	public int getPm10() {
		return this.pm10;
	}

	public int getPm25() {
		return this.pm25;
	}

	public long getPub_time() {
		return this.pub_time;
	}

	public int getSo2() {
		return this.so2;
	}

	public String getSource() {
		return this.source;
	}

	public String getSpot() {
		return this.spot;
	}

	public void setAqi(int paramInt) {
		this.aqi = paramInt;
	}

	public void setAqi_desc(String paramString) {
		this.aqi_desc = paramString;
	}

	public void setAqi_level(String paramString) {
		this.aqi_level = paramString;
	}

	public void setCity_code(String paramString) {
		this.city_code = paramString;
	}

	public void setCo(int paramInt) {
		this.co = paramInt;
	}

	public void setNo2(int paramInt) {
		this.no2 = paramInt;
	}

	public void setO3(int paramInt) {
		this.o3 = paramInt;
	}

	public void setPm10(int paramInt) {
		this.pm10 = paramInt;
	}

	public void setPm25(int paramInt) {
		this.pm25 = paramInt;
	}

	public void setPub_time(long paramLong) {
		this.pub_time = paramLong;
	}

	public void setSo2(int paramInt) {
		this.so2 = paramInt;
	}

	public void setSource(String paramString) {
		this.source = paramString;
	}

	public void setSpot(String paramString) {
		this.spot = paramString;
	}

	@Override
	public String toString() {
		return "AQI [aqi=" + aqi + ", aqi_desc=" + aqi_desc + ", aqi_level="
				+ aqi_level + ", city_code=" + city_code + ", co=" + co
				+ ", no2=" + no2 + ", o3=" + o3 + ", pm10=" + pm10 + ", pm25="
				+ pm25 + ", pub_time=" + pub_time + ", so2=" + so2
				+ ", source=" + source + ", spot=" + spot + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(aqi);
		dest.writeString(aqi_desc);
		dest.writeString(aqi_level);
		dest.writeString(city_code);
		dest.writeInt(co);
		dest.writeInt(no2);
		dest.writeInt(o3);
		dest.writeInt(pm10);
		dest.writeInt(pm25);
		dest.writeLong(pub_time);
		dest.writeInt(so2);
		dest.writeString(source);
		dest.writeString(spot);
	}

	public static final Parcelable.Creator<AQI> CREATOR = new Creator<AQI>() {

		@Override
		public AQI[] newArray(int size) {
			return new AQI[size];
		}

		@Override
		public AQI createFromParcel(Parcel source) {
			AQI aqi = new AQI();
			aqi.aqi = source.readInt();
			aqi.aqi_desc = source.readString();
			aqi.aqi_level = source.readString();
			aqi.city_code = source.readString();
			aqi.co = source.readInt();
			aqi.no2 = source.readInt();
			aqi.o3 = source.readInt();
			aqi.pm10 = source.readInt();
			aqi.pm25 = source.readInt();
			aqi.pub_time = source.readLong();
			aqi.so2 = source.readInt();
			aqi.source = source.readString();
			aqi.spot = source.readString();
			return aqi;
		}
	};
}
