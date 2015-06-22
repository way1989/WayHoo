package com.way.weather.plugin.bean;

import java.util.ArrayList;

public class Alerts {
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

	public static class Alert {
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

	}

}
