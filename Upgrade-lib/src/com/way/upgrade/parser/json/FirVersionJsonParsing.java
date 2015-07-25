package com.way.upgrade.parser.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.way.upgrade.bean.UpgradeInfo;
import com.way.upgrade.utils.Constants;
import com.way.upgrade.utils.ContextUtils;

public class FirVersionJsonParsing extends AbstractJsonParsing<UpgradeInfo> {
	private Context mContext;

	public FirVersionJsonParsing(Context context) {
		mContext = context;
	}

	@Override
	public UpgradeInfo readJsonItem(JSONObject versionJsonObj)
			throws JSONException {
		UpgradeInfo upgradeInfo = new UpgradeInfo();
		String url = versionJsonObj.getString("installUrl");
		String firVersionCode = versionJsonObj.getString("version");
		String firVersionName = versionJsonObj.getString("versionShort");
		String changeLog = versionJsonObj.getString("changelog");
		upgradeInfo.setUrl(url);
		upgradeInfo.setVersion(firVersionCode);
		boolean result = ContextUtils.getVersionCode(mContext) < Integer
				.parseInt(firVersionCode)
				|| (ContextUtils.getVersionCode(mContext) == Integer
						.parseInt(firVersionCode) && !ContextUtils
						.getVersionName(mContext).equals(firVersionName));
		upgradeInfo.setResult(String.valueOf(result));
		upgradeInfo.setVersionName(firVersionName);
		upgradeInfo.setMustUpdate(Constants.NOT_MUST_UPDATE + "");
		upgradeInfo.setChangeLog(changeLog);
		return upgradeInfo;
	}

}
