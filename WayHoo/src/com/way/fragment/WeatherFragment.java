package com.way.fragment;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.way.adapter.WeatherListAdapter;
import com.way.beans.City;
import com.way.common.util.NetUtil;
import com.way.common.util.SystemUtils;
import com.way.common.util.TimeUtils;
import com.way.common.util.WeatherIconUtils;
import com.way.db.CityProvider;
import com.way.db.CityProvider.CityConstants;
import com.way.fragment.BaseFragment.ABaseTask;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.RealTime;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.weather.plugin.spider.WeatherSpider;
import com.way.yahoo.App;
import com.way.yahoo.MainActivity;
import com.way.yahoo.R;

public class WeatherFragment extends Fragment implements ITaskManager,
		SwipeRefreshLayout.OnRefreshListener {
	public static final String ARG_CITY = "city";
	public static final String ALPHA_KEY = "alpha";
	private Handler mHandler = new Handler();
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	private WeatherListAdapter mWeatherAdapter;
	private ImageView mNormalImageView;
	private ImageView mBlurredImageView;
	private View mListHeaderView;

	private int mHeaderHeight = -1;

	// 当前天气的View
	private ImageView mCurWeatherIV;
	private TextView mCurWeatherTV;
	private TextView mCurHighTempTV;
	private TextView mCurLowTempTV;
	private TextView mCurFeelsTempTV;
	private TextView mCurWeatherCopyTV;

	private ContentResolver mContentResolver;
	private MainActivity mActivity;
	private City mCurCity;

	public WeatherFragment() {
	}

	public static WeatherFragment newInstance(City city) {
		WeatherFragment fragment = new WeatherFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(ARG_CITY, city);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		taskManager = new TaskManager();
		if (savedInstanceState != null)
			taskManager.restore(savedInstanceState);
		mActivity = (MainActivity) getActivity();
		mContentResolver = getActivity().getContentResolver();
	}

	private View mRootView;
	// 分别表示当前Fragment是否可见,是否已准备(表示已经走过onCreateView方法)以及是否数据已加载
	private boolean isVisible = false;
	private boolean isPrepared = false;
	private boolean isLoaded = false;

	/**
	 * 不提供覆写，需监听可见性的子类可覆写{@link #onFragmentVisible()}和
	 * {@link #onFragmentInvisible()}方法
	 * 
	 * @param isVisibleToUser
	 *            当前Fragment的可见性
	 */
	@Override
	public final void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		isVisible = isVisibleToUser;
		if (getUserVisibleHint()) {
			mHandler.removeCallbacks(delayRefresh);
			mHandler.postDelayed(delayRefresh, 500);
		} else {
			mHandler.removeCallbacks(delayRefresh);
		}
	}

	/**
	 * 在Fragment可见时进行判断是否载入数据
	 */
	private void onLoadedData() {
		if (!isPrepared /* || !isVisible */)
			return;
		if (isLoaded && !isNeedRequestNet()) {
			return;
		} else {
			requestData(false);
		}
	}

	private boolean isNeedRequestNet() {
		if (!isVisible)
			return false;
		int netState = NetUtil.getNetworkState(getActivity());
		if (netState == NetUtil.NETWORN_NONE) {
			return false;
		}
		long refreshTime = getRefreshTime();
		if (netState == NetUtil.NETWORN_WIFI) {
			return ((System.currentTimeMillis() - refreshTime) > (1000 * 60 * 30));// wifi网络30分钟过期
		}
		if (netState == NetUtil.NETWORN_MOBILE) {
			return ((System.currentTimeMillis() - refreshTime) > (1000 * 60 * 60 * 2));// 移动网络两个小时过期
		}

		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.weather_fragment, container,
					false);
			initViews(mRootView, savedInstanceState);
			isPrepared = true;
			isLoaded = false;
			// if (isVisible) {
			mHandler.removeCallbacks(delayRefresh);
			mHandler.post(delayRefresh);

			// } else {
			// loadWeatherInfoFromLocal();
			// }
		} else {
			// ViewGroup mRootParent = (ViewGroup) mRootView.getParent();
			// if (mRootParent != null) {
			// mRootParent.removeView(mRootView);
			// }
			// onLoadedData();
		}
		return mRootView;
	}

	Runnable delayRefresh = new Runnable() {

		@Override
		public void run() {
			onLoadedData();
		}
	};

	Runnable startRefreshAnim = new Runnable() {

		@Override
		public void run() {
			if (!mSwipeRefreshLayout.isRefreshing())
				mSwipeRefreshLayout.setRefreshing(true);
		}
	};
	Runnable stopRefreshAnim = new Runnable() {

		@Override
		public void run() {
			if (mSwipeRefreshLayout.isRefreshing())
				mSwipeRefreshLayout.setRefreshing(false);
		}
	};

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (isNeedDestroy()) {
			mRootView = null;
			isVisible = false;
			isPrepared = false;
			isLoaded = false;
		}
	}

	/**
	 * 如果此Fragment占用的数据量过大，可覆写此方法返回true， 表示需要当Fragment无效时进行数据清理，然后覆写
	 * {@link #onClearDataset()}方法 对占用内存的数据进行清理和对视图控件引用的释放(否则内存不会释放)，
	 * 这样此Fragment再次回到台前时会重新加载所有的数据
	 * 
	 * @return true表示子类选择了数据销毁，当Fragment不可见的时候，这样下次Fragment可见时会重新加载数据
	 */
	protected boolean isNeedDestroy() {
		return false;
	}

	/**
	 * 初始化所有的view
	 * 
	 * @param view
	 */
	private void initViews(View view, Bundle savedInstanceState) {
		mSwipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swiperefresh);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mListView = (ListView) view.findViewById(R.id.drag_list);

		mNormalImageView = (ImageView) view
				.findViewById(R.id.weather_background);
		mBlurredImageView = (ImageView) view
				.findViewById(R.id.weather_background_blurred);
		if (savedInstanceState != null) {
			float alpha = savedInstanceState.getFloat(ALPHA_KEY);
			mBlurredImageView.setAlpha(alpha);
		} else {
			mBlurredImageView.setAlpha(0f);// 设置默认模糊背景为透明
		}

		mListHeaderView = LayoutInflater.from(getActivity()).inflate(
				R.layout.weather_current_condition, null);
		// 获取屏幕高度
		int displayHeight = SystemUtils.getDisplayHeight(getActivity());
		// HeaderView高度=屏幕高度-标题栏高度
		mHeaderHeight = displayHeight
				- getResources().getDimensionPixelSize(
						R.dimen.abs__action_bar_default_height);
		mListHeaderView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, mHeaderHeight));
		// 计算背景View的高度，适当比屏幕高度多一点，
		// 之所以多1/8是为了后面滑动ListView时背景能跟随滑动。
		// int backgroundHeight = displayHeight + mHeaderHeight / 8;
		// mNormalImageView.setLayoutParams(new FrameLayout.LayoutParams(
		// FrameLayout.LayoutParams.MATCH_PARENT, backgroundHeight));
		// mBlurredImageView.setLayoutParams(new FrameLayout.LayoutParams(
		// FrameLayout.LayoutParams.MATCH_PARENT, backgroundHeight));

		mListView.addHeaderView(mListHeaderView, null, false);// 给ListView添加HeaderView
		mWeatherAdapter = new WeatherListAdapter(getActivity());
		mListView.setAdapter(mWeatherAdapter);
		mListView.setOnScrollListener(mOnScrollListener);// 监听滑动
		initCurWeatherViews(view);

	}

	/**
	 * 初始化当前天气的view，即ListView的HeaderView
	 * 
	 * @param view
	 */
	private void initCurWeatherViews(View view) {
		mCurWeatherIV = (ImageView) view.findViewById(R.id.main_icon);
		mCurWeatherTV = (TextView) view.findViewById(R.id.weather_description);
		mCurHighTempTV = (TextView) view.findViewById(R.id.temp_high);
		mCurLowTempTV = (TextView) view.findViewById(R.id.temp_low);
		mCurFeelsTempTV = (TextView) view.findViewById(R.id.temperature);
		mCurWeatherCopyTV = (TextView) view.findViewById(R.id.copyright);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	// ListView滑动监听，更新背景模糊度和移动距离
	private OnScrollListener mOnScrollListener = new OnScrollListener() {
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			View topChild = view.getChildAt(0);// 获取ListView的第一个View
			if (topChild == null) {
				onNewScroll(0);
			} else if (topChild != mListHeaderView) {
				onNewScroll(mListHeaderView.getHeight());
			} else {
				onNewScroll(-topChild.getTop());
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// do nothing
		}
	};

	/**
	 * 更新背景模糊度和移动距离
	 * 
	 * @param scrollPosition
	 */
	private void onNewScroll(int scrollPosition) {
		// 控制模糊背景的alpha值
		float ratio = Math.min(1.5f * (-mListHeaderView.getTop())
				/ mHeaderHeight, 1.0f);
		// Apply on the ImageView if needed
		mBlurredImageView.setAlpha(ratio);

		// 控制背景滑动距离
		// int dampedScroll = Math.round(scrollPosition * 0.125f);
		// int offset = mLastDampedScroll - dampedScroll;
		// mBlurredImageView.offsetTopAndBottom(offset);
		// mNormalImageView.offsetTopAndBottom(offset);
		// L.i("liweiping", "offset = " + offset);
		// mLastDampedScroll = dampedScroll;
	}

	private WeatherInfo loadWeatherInfoFromLocal() throws TaskException {
		if (mCurCity == null)
			mCurCity = getArguments().getParcelable(ARG_CITY);
		try {
			WeatherInfo weatherInfo = WeatherSpider.getWeatherInfo(mActivity,
					mCurCity.getPostID(), mCurCity.getWeatherInfoStr());
			if (!WeatherSpider.isEmpty(weatherInfo)) {
				return weatherInfo;
			}
		} catch (JSONException e) {
			throw new TaskException(
					TaskException.TaskError.resultIllegal.toString());
		}
		return null;
	}

	private WeatherInfo loadWeatherInfoFromNetwork() throws TaskException {
		if (NetUtil.getNetworkState(mActivity) == NetUtil.NETWORN_NONE)
			throw new TaskException(
					TaskException.TaskError.noneNetwork.toString());
		if (mCurCity == null)
			mCurCity = getArguments().getParcelable(ARG_CITY);
		final String postID = mCurCity.getPostID();
		return WeatherSpider.getWeatherInfo(postID);

	}

	protected void save2Database(WeatherInfo weatherInfo, String postID,
			String response) {
		long pubTime = weatherInfo.getRealTime().getPub_time();
		long savePubTime = getPubTime(postID);
		if (pubTime != savePubTime) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(CityConstants.REFRESH_TIME,
					System.currentTimeMillis());
			contentValues.put(CityConstants.PUB_TIME, pubTime);
			contentValues.put(CityConstants.WEATHER_INFO, response);
			mContentResolver.update(CityProvider.TMPCITY_CONTENT_URI,
					contentValues, CityConstants.POST_ID + "=?",
					new String[] { postID });
		}
	}

	private long getPubTime(String postID) {
		Cursor c = mContentResolver.query(CityProvider.TMPCITY_CONTENT_URI,
				new String[] { CityConstants.PUB_TIME }, CityConstants.POST_ID
						+ "=?", new String[] { postID }, null);

		long time = 0L;
		if (c.moveToFirst())
			time = c.getLong(c.getColumnIndex(CityConstants.PUB_TIME));
		return time;
	}

	private long getRefreshTime() {
		if (mCurCity == null)
			mCurCity = getArguments().getParcelable(ARG_CITY);
		Cursor c = mContentResolver.query(CityProvider.TMPCITY_CONTENT_URI,
				new String[] { CityConstants.REFRESH_TIME },
				CityConstants.POST_ID + "=?",
				new String[] { mCurCity.getPostID() }, null);

		long time = 0L;
		if (c.moveToFirst())
			time = c.getLong(c.getColumnIndex(CityConstants.REFRESH_TIME));
		return time;
	}

	/**
	 * 更新天气信息界面
	 */
	private void updateWeatherView(WeatherInfo weatherInfo) {

		if (WeatherSpider.isEmpty(weatherInfo)) {
			Toast.makeText(mActivity, "刷新失败...", Toast.LENGTH_SHORT).show();
			return;
		}
		isLoaded = true;
		if (weatherInfo.isNewData())
			Toast.makeText(mActivity, mCurCity.getName() + " 刷新成功...",
					Toast.LENGTH_SHORT).show();
		RealTime realTime = weatherInfo.getRealTime();
		Forecast forecast = weatherInfo.getForecast();

		int type = realTime.getAnimation_type();
		mNormalImageView.setImageResource(WeatherIconUtils
				.getWeatherNromalBg(type));
		mBlurredImageView.setImageResource(WeatherIconUtils
				.getWeatherBlurBg(type));
		mCurWeatherIV.setImageResource(WeatherIconUtils.getWeatherIcon(type));
		mCurWeatherTV.setText(realTime.getWeather_name());
		mCurFeelsTempTV.setText(realTime.getTemp() + "");
		mCurHighTempTV.setText(forecast.getTmpHigh(1) + "°");
		mCurLowTempTV.setText(forecast.getTmpLow(1) + "°");
		mCurWeatherCopyTV.setText(TimeUtils.getDay(getPubTime(mCurCity
				.getPostID())) + "发布");

		mWeatherAdapter.setWeather(weatherInfo);
	}

	@Override
	public void onRefresh() {
		requestData(true);
	}

	public void requestData(boolean force) {
		if (mCurCity == null)
			mCurCity = getArguments().getParcelable(ARG_CITY);
		new WeatherTask(mCurCity.getPostID()).execute(force);
	}

	class WeatherTask extends WorkTask<Boolean, Void, WeatherInfo> {
		public WeatherTask(String postID) {
			super(postID, WeatherFragment.this);
		}

		@Override
		protected void onPrepare() {
			super.onPrepare();
			taskStateChanged(ABaseTaskState.prepare, null);
		}

		@Override
		public WeatherInfo workInBackground(Boolean... params)
				throws TaskException {
			mWeatherAdapter.initViews();
			boolean isForce = params[0];
			WeatherInfo weatherInfo = null;
			if (isNeedRequestNet() || isForce) {
				try {
					weatherInfo = loadWeatherInfoFromNetwork();
				} catch (Exception e) {
				}
				if (WeatherSpider.isEmpty(weatherInfo)) {
					weatherInfo = loadWeatherInfoFromLocal();
					weatherInfo.setNewData(false);
				} else {
					weatherInfo.setNewData(true);
				}
			} else {
				weatherInfo = loadWeatherInfoFromLocal();
			}
			if (WeatherSpider.isEmpty(weatherInfo))
				weatherInfo = loadWeatherInfoFromLocal();
			return weatherInfo;
		}

		@Override
		protected void onSuccess(WeatherInfo result) {
			super.onSuccess(result);
			updateWeatherView(result);
			taskStateChanged(ABaseTaskState.success, null);
		}

		@Override
		protected void onFailure(TaskException exception) {
			super.onFailure(exception);
			taskStateChanged(ABaseTaskState.falid, exception.getMessage());
		}

		@Override
		protected void onFinished() {
			super.onFinished();
			taskStateChanged(ABaseTaskState.finished, null);
		}
	}

	/**
	 * 根据{@link ABaseTask} 的加载状态，刷新视图
	 *
	 * @param state
	 *
	 * @param tag
	 */
	protected void taskStateChanged(ABaseTaskState state, Serializable tag) {
		// 开始Task
		if (state == ABaseTaskState.prepare) {
			mHandler.removeCallbacks(startRefreshAnim);
			mHandler.post(startRefreshAnim);
		}
		// Task成功
		else if (state == ABaseTaskState.success) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 1000);
		}
		// 取消Task
		else if (state == ABaseTaskState.canceled) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 1000);
		}
		// Task失败
		else if (state == ABaseTaskState.falid) {
			Toast.makeText(mActivity, "刷新失败..." + tag, Toast.LENGTH_SHORT)
					.show();
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 1000);
		}
		// Task结束
		else if (state == ABaseTaskState.finished) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 1000);

		}
	}

	private TaskManager taskManager;// 管理线程

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (taskManager != null) {
			taskManager.save(outState);
			outState.putFloat(ALPHA_KEY, mBlurredImageView.getAlpha());
		}
	}

	protected ITaskManager getTaskManager() {
		return taskManager;
	}

	@Override
	public void addTask(WorkTask task) {
		taskManager.addTask(task);
	}

	@Override
	public void removeTask(String taskId, boolean cancelIfRunning) {
		taskManager.removeTask(taskId, cancelIfRunning);
	}

	@Override
	public void removeAllTask(boolean cancelIfRunning) {
		taskManager.removeAllTask(cancelIfRunning);
	}

	@Override
	public int getTaskCount(String taskId) {
		return taskManager.getTaskCount(taskId);
	}

	protected enum ABaseTaskState {
		none, prepare, falid, success, finished, canceled
	}

	public void refreshUI() {
		Log.i("refreshUI", "refreshUI isVisible = " + isVisible
				+ ", isPrepared = " + isPrepared);
		// onLoadedData();
	}

	public void releaseImageViewByIds() {
		// TODO Auto-generated method stub

	}

}
