package com.way.upgrade.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.way.upgrade.bean.UpgradeInfo;
import com.way.upgrade.job.CheckNewVersionJobWithoutClientUrl;
import com.way.upgrade.job.DownloadNewVersionJob;
import com.way.upgrade.locale.LocaleChina;
import com.way.upgrade.locale.LocaleChinaTW;
import com.way.upgrade.locale.LocaleChinese;
import com.way.upgrade.locale.LocaleEnglish;
import com.way.upgrade.locale.LocaleHandler;
import com.way.upgrade.locale.LocaleUS;
import com.way.upgrade.utils.Constants;
import com.way.upgrade.utils.ContextUtils;
import com.way.upgrade.utils.thread.Future;
import com.way.upgrade.utils.thread.FutureListener;
import com.way.upgrade.utils.thread.ThreadPool;

/**
 * 
 * @author way
 * @since 2014/4/28
 */
public class UpgradeManager extends LocaleHandler implements UpgradeInterface {
	private Context mContext;
	private static ThreadPool mThreadPool;
	private Handler mHandler;
	private AlertDialog alertDialog;
	private AlertDialog alertDialogForMustUpdate;
	private ProgressDialog mProgressDialog;

	private Map<String, LocaleHandler> handlers;

	private static Future<UpgradeInfo> mFuture;

	private CheckNewVersionListener mAskForNewVersionFlagListener;

	private FutureListener<UpgradeInfo> mAskForNewVersionListener = new FutureListener<UpgradeInfo>() {
		public void onFutureDone(Future<UpgradeInfo> future) {
			UpgradeInfo upgradeInfo = future.get();
			Message msg = mHandler.obtainMessage();
			if (upgradeInfo != null) {
				if (upgradeInfo.getErrorCode() != null) {
					if (upgradeInfo.getErrorCode().equals(
							Constants.ERROR_CODE_NET)) {
						msg.what = Constants.MSG_NET_ERROR;
					}
				} else if (Boolean.parseBoolean(upgradeInfo.getResult())) {
					mFuture = future;
					msg.what = Constants.MSG_HAVA_NEW_VERSION;
					msg.obj = upgradeInfo;
				} else if (!Boolean.parseBoolean(upgradeInfo.getResult())) {
					msg.what = Constants.MSG_NO_NEW_VERSION;
				}
			} else {
				msg.what = Constants.MSG_NO_NEW_VERSION;
			}
			mHandler.sendMessage(msg);
		};
	};

	public class AskForNewVersionFlag implements FutureListener<UpgradeInfo> {
		@Override
		public void onFutureDone(Future<UpgradeInfo> future) {
			UpgradeInfo upgradeInfo = future.get();
			boolean result = false;
			if (upgradeInfo != null) {
				result = Boolean.parseBoolean(upgradeInfo.getResult());
			}
			Message msg = mHandler.obtainMessage();
			msg.what = Constants.MSG_VERSION_RESULT;
			msg.obj = result;
			mHandler.sendMessage(msg);
		}

	}

	public UpgradeManager(Context context) {
		createHandlers();
		mContext = context;
		init();
	}

	private void init() {
		if (mThreadPool == null) {
			mThreadPool = new ThreadPool();
		}
		mHandler = new Handler(mContext.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.MSG_HAVA_NEW_VERSION:
					try {
						ContextUtils.closeProgressDialog(mProgressDialog);
						UpgradeInfo description = (UpgradeInfo) msg.obj;
						showDialog(description);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				case Constants.MSG_NO_NEW_VERSION:
					try {
						ContextUtils.closeProgressDialog(mProgressDialog);
						ContextUtils.showToast(mContext, getToastMessage(),
								Toast.LENGTH_SHORT);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case Constants.MSG_START_DOWNLOAD:
					UpgradeInfo description1 = (UpgradeInfo) msg.obj;
					downloadNewVersion(description1);
					break;

				case Constants.MSG_VERSION_RESULT:
					Boolean result = (Boolean) msg.obj;
					mAskForNewVersionFlagListener.checkNewVersion(result);
					break;
				case Constants.MSG_NET_ERROR:
					try {
						ContextUtils.closeProgressDialog(mProgressDialog);
					} catch (Exception e) {
						e.printStackTrace();
					}
					ContextUtils.showToast(mContext, getToastNetErrorMessage(),
							Toast.LENGTH_SHORT);
				default:
					break;
				}
			}
		};

		alertDialog = ContextUtils.showAlertDialog(mContext, getDialogTitle(),
				"", new int[] { android.R.string.ok, android.R.string.cancel },
				new DialogInterface.OnClickListener[] {
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if (mFuture != null) {
									Message msg = mHandler.obtainMessage();
									msg.what = Constants.MSG_START_DOWNLOAD;
									msg.obj = mFuture.get();
									mHandler.sendMessage(msg);
								}
							}

						}, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}

						} });

		alertDialogForMustUpdate = ContextUtils
				.showAlertDialog(
						mContext,
						getDialogTitle(),
						"",
						new int[] { android.R.string.ok },
						new DialogInterface.OnClickListener[] { new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if (mFuture != null) {
									Message msg = mHandler.obtainMessage();
									msg.what = Constants.MSG_START_DOWNLOAD;
									msg.obj = mFuture.get();
									mHandler.sendMessage(msg);
								}
							}
						} });
		alertDialogForMustUpdate.setCancelable(false);

		mProgressDialog = ContextUtils.createProgressDialog(mContext);
		mProgressDialog.setTitle(getProgressDialogTitle());
		mProgressDialog.setMessage(getProgressDialogMessage());

	}

	public static UpgradeManager newInstance(Context context) {
		return new UpgradeManager(context);
	}

	private void showProgressDialog() {
		if (mProgressDialog != null) {
			try {
				mProgressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void askForNewVersion() {
		showProgressDialog();
		mThreadPool.submit(new CheckNewVersionJobWithoutClientUrl(mContext),
				mAskForNewVersionListener, ThreadPool.MODE_NETWORK);
	}

	@Override
	public void askForNewVersionFlag(
			CheckNewVersionListener checkversionListener) {
		if (checkversionListener == null) {
			return;
		}
		mAskForNewVersionFlagListener = checkversionListener;
		AskForNewVersionFlag mAskForNewVersionFlagListener = new AskForNewVersionFlag();
		mThreadPool.submit(new CheckNewVersionJobWithoutClientUrl(mContext),
				mAskForNewVersionFlagListener, ThreadPool.MODE_NETWORK);
	}

	@Override
	public void downloadNewVersion(UpgradeInfo upgradeInfo) {
		mThreadPool.submit(new DownloadNewVersionJob(mContext, upgradeInfo),
				null, ThreadPool.MODE_CPU);
	}

	private void createHandlers() {
		handlers = new HashMap<String, LocaleHandler>();
		handlers.put(LocaleChinese.defaultLocale, new LocaleChinese());
		handlers.put(LocaleChinaTW.defaultLocale, new LocaleChinaTW());
		handlers.put(LocaleEnglish.defaultLocale, new LocaleEnglish());
		handlers.put(Locale.CHINA.toString(), new LocaleChina());
		handlers.put(Locale.US.toString(), new LocaleUS());
	}

	private LocaleHandler lookupHandlerBy(String handlerName) {
		LocaleHandler handler = handlers.get(handlerName);
		if (handler == null)
			return handlers.get(Locale.ENGLISH.getLanguage());
		return handlers.get(handlerName);
	}

	private void showDialog(UpgradeInfo description) {
		int mustUpdate = Integer.valueOf(description.getMustUpdate());
		boolean result = Boolean.parseBoolean(description.isResult());
		String descriptionStr = description.getChangeLog();
		Log.i("liweiping", "mustUpdate = " + mustUpdate + ", result = "
				+ result + ", alertDialog = " + alertDialog
				+ ",  alertDialogForMustUpdate = " + alertDialogForMustUpdate);
		if (mustUpdate == Constants.NOT_MUST_UPDATE) {
			if (alertDialog != null && result) {
				alertDialog.setTitle(getDialogTitle());
				alertDialog.setMessage(descriptionStr);
				alertDialog.show();
			}
		} else {
			if (alertDialogForMustUpdate != null && result) {
				alertDialogForMustUpdate.setTitle(getDialogTitle());
				alertDialogForMustUpdate.setMessage(descriptionStr);
				alertDialogForMustUpdate.show();
			}
		}
	}

	private String getLocaleLanguage() {
		return Locale.getDefault().toString();
	}

	@Override
	public String getDialogTitle() {
		LocaleHandler handler = lookupHandlerBy(getLocaleLanguage());
		return handler.getDialogTitle();
	}

	@Override
	public String getProgressDialogTitle() {
		LocaleHandler handler = lookupHandlerBy(getLocaleLanguage());
		return handler.getProgressDialogTitle();
	}

	@Override
	public String getProgressDialogMessage() {
		LocaleHandler handler = lookupHandlerBy(getLocaleLanguage());
		return handler.getProgressDialogMessage();
	}

	@Override
	public String getToastMessage() {
		LocaleHandler handler = lookupHandlerBy(getLocaleLanguage());
		return handler.getToastMessage();
	}

	@Override
	public String getToastNetErrorMessage() {
		LocaleHandler handler = lookupHandlerBy(getLocaleLanguage());
		return handler.getToastNetErrorMessage();
	}
}
