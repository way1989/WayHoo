package com.way.upgrade.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 版本更新信息
 * 
 * @author way
 * @since 2014/4/28
 */
public class UpgradeInfo implements Parcelable{
	private int id;
	
	/**唯一标识apk*/
	private String key;

	/** 版本信息 */
	private String version;
	
	/** 版本名称 */
	private String versionName;
	
	/** 是否有更新 */
	private String result;

	/** 新版本地址 */
	private String url;

	/** 版本描述 */
	private String description;
	
	/** 已下载的大小  */
	private long downloadSize;
	
	private String mustUpdate;
	private String changeLog;
	
	private String errorCode;
	
	
	public static final Parcelable.Creator<UpgradeInfo> CREATOR = new Creator<UpgradeInfo>() {

		@Override
		public UpgradeInfo[] newArray(int size) {
			return new UpgradeInfo[size];
		}

		@Override
		public UpgradeInfo createFromParcel(Parcel source) {
			return new UpgradeInfo(source);
		}
	};

	public UpgradeInfo() {

	}
	
	public UpgradeInfo(Parcel in) {
		id = in.readInt();
		key = in.readString();
		version = in.readString();
		versionName = in.readString();
		result = in.readString();
		url = in.readString();
		description = in.readString();
		downloadSize = in.readLong();
		result = in.readString();
		mustUpdate = in.readString();
		changeLog = in.readString();
		errorCode = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(key);
		dest.writeString(version);
		dest.writeString(versionName);
		dest.writeString(result);
		dest.writeString(url);
		dest.writeString(description);
		dest.writeLong(downloadSize);
		dest.writeString(result);
		dest.writeString(mustUpdate);
		dest.writeString(changeLog);
		dest.writeString(errorCode);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDownloadSize() {
		return downloadSize;
	}

	public void setDownloadSize(long downloadSize) {
		this.downloadSize = downloadSize;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String isResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(String mustUpdate) {
		this.mustUpdate = mustUpdate;
	}
	
	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getResult() {
		return result;
	}


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "UpgradeInfo [id=" + id + ", key=" + key + ", version="
				+ version + ", versionName=" + versionName + ", result="
				+ result + ", url=" + url + ", description=" + description
				+ ", downloadSize=" + downloadSize + ", mustUpdate="
				+ mustUpdate + ", changeLog=" + changeLog
				+ ", errorCode=" + errorCode + "]";
	}

	
}
