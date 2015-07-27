package com.way.upgrade.receiver;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.way.upgrade.utils.Preferences;

/**
 * 下载完成通知接收器
 * 
 * @author way
 * 
 */
public class DownloadCompleteReveiver extends BroadcastReceiver {
	private DownloadManager downloadManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			if (id == Preferences.getDownloadId(context)) {
				Query query = new Query();
				query.setFilterById(id);
				downloadManager = (DownloadManager) context
						.getSystemService(Context.DOWNLOAD_SERVICE);
				Cursor cursor = downloadManager.query(query);

				int columnCount = cursor.getColumnCount();
				String path = null;
				while (cursor.moveToNext()) {
					for (int j = 0; j < columnCount; j++) {
						String columnName = cursor.getColumnName(j);
						String string = cursor.getString(j);
						if ("local_uri".equals(columnName)) {
							path = string;
						}
					}
				}
				cursor.close();

				if (path != null) {
					Preferences.setDownloadPath(context, path);
					Preferences.setDownloadStatus(context, -1);
					Intent installApkIntent = new Intent();
					installApkIntent.setAction(Intent.ACTION_VIEW);
					installApkIntent.setDataAndType(Uri.parse(path),
							"application/vnd.android.package-archive");
					installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					context.startActivity(installApkIntent);
				}
			}
		} else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
			long[] ids = intent
					.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
			if (ids.length > 0 && ids[0] == Preferences.getDownloadId(context)) {
				Intent downloadIntent = new Intent();
				downloadIntent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
				downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				context.startActivity(downloadIntent);
			}
		}
	}

}
