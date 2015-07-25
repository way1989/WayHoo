package com.way.upgrade.job;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.way.upgrade.bean.UpgradeInfo;
import com.way.upgrade.parser.json.FirVersionJsonParsing;
import com.way.upgrade.utils.Constants;
import com.way.upgrade.utils.NetUtils;
import com.way.upgrade.utils.NetUtils.NETWORK_STATUS;
import com.way.upgrade.utils.thread.ThreadPool;
import com.way.upgrade.utils.thread.ThreadPool.JobContext;

/**
 * 
 * @author way
 * @since 2014/4/28
 */
public abstract class AbstractCheckNewVersionJob implements
		ThreadPool.Job<UpgradeInfo> {
	protected Context mContext;
	protected String bundle_id;
	protected String api_token;

	public AbstractCheckNewVersionJob(Context context) {
		this.mContext = context;
		try {
			ApplicationInfo appInfo = mContext.getPackageManager()
					.getApplicationInfo(mContext.getPackageName(),
							PackageManager.GET_META_DATA);
			bundle_id = appInfo.metaData.getString("bundle_id");
			api_token = appInfo.metaData.getString("api_token");
		} catch (NameNotFoundException e) {
			throw new NullPointerException(
					"app_id and token must not null in AndroidManifest.xml "
							+ e);
		}
		Log.i("liweiping", "AbstractCheckNewVersionJob app_id = " + bundle_id
				+ ",  api_token = " + api_token);
	}

	@Override
	public UpgradeInfo run(JobContext jc) {

		UpgradeInfo upgradeInfo = null;
		if (NetUtils.getNetworkType(mContext) == NETWORK_STATUS.STATE_NONE_NETWORK) {
			Log.i("liweiping", "NETWORK_STATUS.STATE_NONE_NETWORK");
			upgradeInfo = new UpgradeInfo();
			upgradeInfo.setErrorCode(Constants.ERROR_CODE_NET);
			return upgradeInfo;
		}
		try {
			String checkUpdateUrl = String.format(getCheckVersionUrl(), bundle_id,
					api_token);
			Log.i("liweiping", "checkUpdateUrl = " + checkUpdateUrl);

			JSONObject jo = NetUtils.getJSONArrayByGet(checkUpdateUrl);
			FirVersionJsonParsing firVersionJsonParsing = new FirVersionJsonParsing(
					mContext);
			Log.i("liweiping",
					"upgradeInfo = " + upgradeInfo + ", checkUpdateUrl = "
							+ checkUpdateUrl + ", jo = " + jo.toString());
			upgradeInfo = firVersionJsonParsing.readJsonItem(jo);
		} catch (Exception e) {
			upgradeInfo = new UpgradeInfo();
			upgradeInfo.setErrorCode(Constants.ERROR_CODE_NET);
			Log.i("liweiping",e.getMessage());
			e.printStackTrace();
		}
		return upgradeInfo;
	}

	public abstract String getCheckVersionUrl();

}
