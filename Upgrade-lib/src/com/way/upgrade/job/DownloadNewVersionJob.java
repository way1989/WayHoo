package com.way.upgrade.job;

import java.io.File;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.way.upgrade.bean.UpgradeInfo;
import com.way.upgrade.utils.Constants;
import com.way.upgrade.utils.Preferences;
import com.way.upgrade.utils.thread.ThreadPool;
import com.way.upgrade.utils.thread.ThreadPool.JobContext;

public class DownloadNewVersionJob implements ThreadPool.Job<Void> {
	private Context mContext;
	private UpgradeInfo mUpgradeInfo;

	private boolean allowMobileDownload = false;
	private static final long MAX_ALLOWED_DOWNLOAD_BYTES_BY_MOBILE = 3145725;

	public DownloadNewVersionJob(Context context, UpgradeInfo upgradeInfo) {
		this.mContext = context;
		this.mUpgradeInfo = upgradeInfo;
	}

	@Override
	public Void run(JobContext jc) {
		try {
			if (checkDownloadRunning())
				return null;
			if (checkApkExist()) {
				Intent installApkIntent = new Intent();
				installApkIntent.setAction(Intent.ACTION_VIEW);
				installApkIntent.setDataAndType(
						Uri.parse(Preferences.getDownloadPath(mContext)),
						"application/vnd.android.package-archive");
				installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				mContext.startActivity(installApkIntent);
			} else {
				String apkName = mContext.getPackageName()
						+ System.currentTimeMillis() + Constants.APK_SUFFIX;
				// 系统下载程序
				final DownloadManager downloadManager = (DownloadManager) mContext
						.getSystemService(mContext.DOWNLOAD_SERVICE);

				Long recommendedMaxBytes = DownloadManager
						.getRecommendedMaxBytesOverMobile(mContext);

				// 可以在移动网络下下载
				if (recommendedMaxBytes == null
						|| recommendedMaxBytes.longValue() > MAX_ALLOWED_DOWNLOAD_BYTES_BY_MOBILE) {
					allowMobileDownload = true;
				}

				Uri uri = Uri.parse(mUpgradeInfo.getUrl());

				final Request request = new Request(uri);

				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

				int NETWORK_TYPE = DownloadManager.Request.NETWORK_WIFI;
				if (allowMobileDownload) {
					NETWORK_TYPE |= DownloadManager.Request.NETWORK_MOBILE;
				}
				request.setAllowedNetworkTypes(NETWORK_TYPE);
				request.allowScanningByMediaScanner();
				request.setShowRunningNotification(true);
				request.setVisibleInDownloadsUi(true);
				request.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS, apkName);
				PackageManager packageManager = mContext.getPackageManager();
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(mContext.getPackageName(), 0);
				Log.i("liweiping",
						"appName = "
								+ packageManager
										.getApplicationLabel(applicationInfo));
				request.setTitle(packageManager
						.getApplicationLabel(applicationInfo));
				request.setMimeType("application/vnd.android.package-archive");

				// id 保存起来跟之后的广播接收器作对比
				long id = downloadManager.enqueue(request);

				long oldId = Preferences.getDownloadId(mContext);
				if (oldId != -1) {
					downloadManager.remove(oldId);
				}

				Preferences.removeAll(mContext);
				Preferences.setDownloadId(mContext, id);
				Preferences.setUpgradeInfo(mContext, mUpgradeInfo);
				Preferences.setDownloadStatus(mContext,
						Constants.DOWNLOAD_STATUS_RUNNING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean checkApkExist() {
		UpgradeInfo prefUpgradeInfo = Preferences.getUpgradeInfo(mContext);
		String version = prefUpgradeInfo.getVersion();
		String downloadPath = Preferences.getDownloadPath(mContext);

		if (version != null && version.trim().length() != 0
				&& version.equals(mUpgradeInfo.getVersion())
				&& downloadPath != null && downloadPath.trim().length() != 0) {
			String path = Uri.parse(downloadPath).getPath();
			if (path != null && path.endsWith(Constants.APK_SUFFIX)) {
				File file = new File(path);
				if (file.exists()) {
					return true;
				}
			}

		}
		return false;
	}

	@SuppressWarnings("static-access")
	private boolean checkDownloadRunning() {
		UpgradeInfo prefUpgradeInfo = Preferences.getUpgradeInfo(mContext);
		String version = prefUpgradeInfo.getVersion();
		int downloadStatus = Preferences.getDownloadStatus(mContext);
		if (version != null && version.trim().length() != 0
				&& version.equals(mUpgradeInfo.getVersion())) {
			long downloadId = Preferences.getDownloadId(mContext);
			if (downloadId != -1) {
				final DownloadManager downloadManager = (DownloadManager) mContext
						.getSystemService(mContext.DOWNLOAD_SERVICE);
				DownloadManager.Query mDownloadQuery = new DownloadManager.Query();
				mDownloadQuery.setFilterById(downloadId);
				Cursor cursor = downloadManager.query(mDownloadQuery);
				if (cursor != null && cursor.moveToFirst()) {
					int status = cursor.getInt(cursor
							.getColumnIndex(DownloadManager.COLUMN_STATUS));
					if (status == DownloadManager.STATUS_RUNNING
							|| downloadStatus == Constants.DOWNLOAD_STATUS_RUNNING) {
						return true;
					}
				}

			}
		}
		return false;
	}
}
