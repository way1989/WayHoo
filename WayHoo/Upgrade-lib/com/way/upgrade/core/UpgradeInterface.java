package com.way.upgrade.core;

import com.way.upgrade.bean.UpgradeInfo;

/**
 * 
 * @author way
 * @since 2014/4/28
 */
public interface UpgradeInterface {
	/**
	 * 请求服务器是否有新版本,并弹出对话框
	 * 
	 * @return
	 */
	public void askForNewVersion();

	/**
	 * 请求服务器是否有新版本
	 * 
	 * @return
	 */
	public void askForNewVersionFlag(
			CheckNewVersionListener checkversionListener);

	/**
	 * 下载新版本
	 * 
	 * @return
	 */
	public void downloadNewVersion(UpgradeInfo upgradeInfo);
}
