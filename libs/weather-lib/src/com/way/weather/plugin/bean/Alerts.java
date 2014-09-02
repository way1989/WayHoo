package com.way.weather.plugin.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Alerts implements Parcelable {
	private ArrayList<Alert> arryAlert;
	private String pid;

	public ArrayList<Alert> getArryAlert() {
		return this.arryAlert;
	}

	public String getPid() {
		return this.pid;
	}

	public void setArryAlert(ArrayList<Alert> paramArrayList) {
		this.arryAlert = paramArrayList;
	}

	public void setPid(String paramString) {
		this.pid = paramString;
	}

	@Override
	public String toString() {
		return "Alerts [arryAlert=" + arryAlert + ", pid=" + pid + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(arryAlert);
		dest.writeString(pid);
	}

	public static final Parcelable.Creator<Alerts> CREATOR = new Creator<Alerts>() {

		@Override
		public Alerts[] newArray(int size) {
			return new Alerts[size];
		}

		@Override
		public Alerts createFromParcel(Parcel source) {
			Alerts alerts = new Alerts();
			alerts.arryAlert = new ArrayList<Alerts.Alert>();
			source.readList(alerts.arryAlert, getClass().getClassLoader());
			return alerts;
		}
	};

	public static class Alert implements Parcelable {
		private String abnormal;
		private String detail;
		private String holiday;
		private String level;
		private Long pubTime;
		private String title;

		public String getAbnormal() {
			return this.abnormal;
		}

		public String getDetail() {
			return this.detail;
		}

		public String getHoliday() {
			return this.holiday;
		}

		public String getLevel() {
			return this.level;
		}

		public Long getPubTime() {
			return this.pubTime;
		}

		public String getTitle() {
			return this.title;
		}

		public void setAbnormal(String paramString) {
			this.abnormal = paramString;
		}

		public void setDetail(String paramString) {
			this.detail = paramString;
		}

		public void setHoliday(String paramString) {
			this.holiday = paramString;
		}

		public void setLevel(String paramString) {
			this.level = paramString;
		}

		public void setPubTime(long paramLong) {
			this.pubTime = paramLong;
		}

		public void setTitle(String paramString) {
			this.title = paramString;
		}

		@Override
		public String toString() {
			return "Alert [abnormal=" + abnormal + ", detail=" + detail
					+ ", holiday=" + holiday + ", level=" + level
					+ ", pubTime=" + pubTime + ", title=" + title + "]";
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(abnormal);
			dest.writeString(detail);
			dest.writeString(holiday);
			dest.writeString(level);
			dest.writeLong(pubTime);
			dest.writeString(title);
		}

		public static final Parcelable.Creator<Alert> CREATOR = new Creator<Alerts.Alert>() {

			@Override
			public Alert[] newArray(int size) {
				return new Alert[size];
			}

			@Override
			public Alert createFromParcel(Parcel source) {
				Alert alert = new Alert();
				alert.abnormal = source.readString();
				alert.detail = source.readString();
				alert.holiday = source.readString();
				alert.level = source.readString();
				alert.pubTime = source.readLong();
				alert.title = source.readString();
				return alert;
			}
		};

	}

}
