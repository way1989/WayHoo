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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.way.adapter.WeatherListAdapter;
import com.way.beans.City;
import com.way.common.util.NetUtil;
import com.way.common.util.SystemUtils;
import com.way.common.util.TimeUtils;
import com.way.common.util.WeatherIconUtils;
import com.way.db.CityProvider;
import com.way.db.CityProvider.CityConstants;
import com.way.weather.plugin.bean.AQI;
import com.way.weather.plugin.bean.Forecast;
import com.way.weather.plugin.bean.Index;
import com.way.weather.plugin.bean.RealTime;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.weather.plugin.spider.WeatherSpider;
import com.way.yahoo.App;
import com.way.yahoo.MainActivity;
import com.way.yahoo.R;

public class WeatherFragment extends Fragment implements ITaskManager,
		OnRefreshListener, OnPullEventListener {
	public static final String ARG_CITY = "city";
	private Handler mHandler = new Handler();
	private ListView mListView;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private WeatherListAdapter mWeatherAdapter;
	private ImageView mNormalImageView;
	private ImageView mBlurredImageView;
	private View mListHeaderView;

	private int mLastDampedScroll;
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
			// onLoadedData();
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
		if (!isPrepared || !isVisible)
			return;
		if (isLoaded && !isNeedRequestNet()) {
			return;
		} else {
			requestData(false);
		}
	}

	private boolean isNeedRequestNet() {
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
			initViews(mRootView);
			isPrepared = true;
			isLoaded = false;
			if (isVisible) {
				mHandler.removeCallbacks(delayRefresh);
				mHandler.post(delayRefresh);

			} else {
				// loadWeatherInfoFromLocal();
			}
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
			if (!mPullRefreshScrollView.isRefreshing())
				mPullRefreshScrollView.setRefreshing();
		}
	};
	Runnable stopRefreshAnim = new Runnable() {

		@Override
		public void run() {
			if (mPullRefreshScrollView.isRefreshing())
				mPullRefreshScrollView.onRefreshComplete();
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
	private void initViews(View view) {
		mListView = (ListView) view.findViewById(R.id.drag_list);
		mNormalImageView = (ImageView) view
				.findViewById(R.id.weather_background);
		mBlurredImageView = (ImageView) view
				.findViewById(R.id.weather_background_blurred);
		mBlurredImageView.setAlpha(0f);// 设置默认模糊背景为透明

		mPullRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.pull_refresh_scrollview);
		// 添加下拉刷新事件
		mPullRefreshScrollView.setOnRefreshListener(this);
		// 添加下拉刷新状态事件，以便及时现实刷新时间
		mPullRefreshScrollView.setOnPullEventListener(this);
		// mPullRefreshScrollView.setMode(Mode.PULL_FROM_START);// 可以下拉刷新
		mListHeaderView = LayoutInflater.from(getActivity()).inflate(
				R.layout.weather_current_condition, null);
		// 获取屏幕高度
		int displayHeight = SystemUtils.getDisplayHeight(getActivity());
		// HeaderView高度=屏幕高度-标题栏高度-状态栏高度
		mHeaderHeight = displayHeight
				- getResources().getDimensionPixelSize(
						R.dimen.abs__action_bar_default_height);
		// - SystemUtils.getStatusBarHeight(getActivity());
		mListHeaderView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, mHeaderHeight));
		// 计算背景View的高度，适当比屏幕高度多一点，
		// 之所以多1/8是为了后面滑动ListView时背景能跟随滑动。
		int backgroundHeight = displayHeight + mHeaderHeight / 8;
		mNormalImageView.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, backgroundHeight));
		mBlurredImageView.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, backgroundHeight));

		mListView.addHeaderView(mListHeaderView, null, false);// 给ListView添加HeaderView
		// mWeatherAdapter = new WeatherListAdapter(getActivity());
		// mListView.setAdapter(mWeatherAdapter);
		mListView.setOnScrollListener(mOnScrollListener);// 监听滑动
		initCurWeatherViews(view);

		// 本来是想点击headerview可以滑动到下一个view，但是效果不好，就干脆不要了
		// mListHeaderView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.i("liweiping", "mListHeaderView onClick......");
		// mListView.setSelection(1);
		// // mListView.scrollListBy(mHeaderHeight);
		// // mListView.setSelectionFromTop(1, mHeaderHeight);
		// // mListView.setSelectionAfterHeaderView();
		// }
		// });
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
		// 控制是否可以下拉刷新
		if (scrollPosition == 0) {
			mPullRefreshScrollView.setMode(Mode.PULL_FROM_START);// 可以下拉刷新
		} else {
			if (mPullRefreshScrollView.getState() == State.RESET) {
				mPullRefreshScrollView.setMode(Mode.DISABLED);
			}
		}

		// 控制模糊背景的alpha值
		float ratio = Math.min(1.5f * (-mListHeaderView.getTop())
				/ mHeaderHeight, 1.0f);
		// L.i("liweiping", "-mHeaderView.getTop() = " +
		// (-mHeaderView.getTop())
		// + ",  mHeaderHeight = " + mHeaderHeight + ", ratio = " + ratio);
		// Apply on the ImageView if needed
		mBlurredImageView.setAlpha(ratio);

		// 控制背景滑动距离
		int dampedScroll = Math.round(scrollPosition * 0.125f);
		int offset = mLastDampedScroll - dampedScroll;
		mBlurredImageView.offsetTopAndBottom(offset);
		mNormalImageView.offsetTopAndBottom(offset);
		// L.i("liweiping", "offset = " + offset);
		mLastDampedScroll = dampedScroll;
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
		RequestFuture<String> future = RequestFuture.newFuture();
		StringRequest request = new StringRequest(String.format(
				WeatherSpider.WEATHER_ALL, postID), future, future);
		App.getVolleyRequestQueue().add(request);
		try {
			String result = future.get(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
					TimeUnit.MILLISECONDS);
			WeatherInfo weatherInfo = WeatherSpider.getWeatherInfo(mActivity,
					postID, result);
			if (!WeatherSpider.isEmpty(weatherInfo)) {
				save2Database(weatherInfo, postID, result);// 保存到数据库
				return weatherInfo;
			}
		} catch (InterruptedException e) {
			throw new TaskException(TextUtils.isEmpty(e.getMessage()) ? ""
					: e.getMessage());
		} catch (ExecutionException e) {
			throw new TaskException(TextUtils.isEmpty(e.getMessage()) ? ""
					: e.getMessage());
		} catch (TimeoutException e) {
			throw new TaskException(TaskException.TaskError.timeout.toString());
		} catch (JSONException e) {
			throw new TaskException(
					TaskException.TaskError.resultIllegal.toString());
		}
		return null;

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

	// private WeatherInfo mWeatherInfo;

	/**
	 * 更新天气信息界面
	 */
	private void updateWeatherView(WeatherInfo weatherInfo) {

		// WeatherInfo weatherInfo = mWeatherInfo;
		if (WeatherSpider.isEmpty(weatherInfo)) {
			Toast.makeText(mActivity, "刷新失败...", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!isVisible)
			return;
		isLoaded = true;
		Toast.makeText(mActivity, mCurCity.getName() + " 刷新成功...",
				Toast.LENGTH_SHORT).show();
		RealTime realTime = weatherInfo.getRealTime();
		AQI aqi = weatherInfo.getAqi();
		Forecast forecast = weatherInfo.getForecast();
		Index index = weatherInfo.getIndex();

		int type = realTime.getAnimation_type();
		Glide.with(this).load(WeatherIconUtils.getWeatherNromalBg(type))
		// .centerCrop()
				.placeholder(R.drawable.bg_na_blur).error(R.drawable.bg_na)
				// .crossFade()
				.into(mNormalImageView);
		Glide.with(this).load(WeatherIconUtils.getWeatherBlurBg(type))
				// .centerCrop()
				.placeholder(R.drawable.bg_na_blur)
				.error(R.drawable.bg_na_blur)
				// .crossFade()
				.into(mBlurredImageView);
		mCurWeatherIV.setImageResource(WeatherIconUtils.getWeatherIcon(type));
		mCurWeatherTV.setText(realTime.getWeather_name());
		mCurFeelsTempTV.setText(realTime.getTemp() + "");
		mCurHighTempTV.setText(forecast.getTmpHigh(1) + "°");
		mCurLowTempTV.setText(forecast.getTmpLow(1) + "°");
		mCurWeatherCopyTV.setText(TimeUtils.getDay(realTime.getPub_time())
				+ "发布");
		mWeatherAdapter = new WeatherListAdapter(getActivity());
		mListView.setAdapter(mWeatherAdapter);
		mWeatherAdapter.setWeather(realTime, aqi, forecast, index);
	}

	@Override
	public void onPullEvent(PullToRefreshBase refreshView, State state,
			Mode direction) {
		// 开始下拉时更新上次刷新时间
		if (state == State.PULL_TO_REFRESH) {
			long time = getRefreshTime();
			String label = String.format(
					getResources().getString(
							R.string.pull_to_refresh_pull_sub_label),
					getResources().getString(
							R.string.pull_to_refresh_pull_sub_label_none));
			if (time > 0)
				label = String.format(
						getResources().getString(
								R.string.pull_to_refresh_pull_sub_label),
						TimeUtils.getListTime(getResources(), time));
			// 更新下拉刷新时间显示
			mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
					label);
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
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
			boolean isForce = params[0];
			WeatherInfo weatherInfo = null;
			if (isNeedRequestNet() || isForce) {
				weatherInfo = loadWeatherInfoFromNetwork();
			} else {
				weatherInfo = loadWeatherInfoFromLocal();
			}
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
			mHandler.postDelayed(startRefreshAnim, 200);
		}
		// Task成功
		else if (state == ABaseTaskState.success) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 500);
		}
		// 取消Task
		else if (state == ABaseTaskState.canceled) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 500);
		}
		// Task失败
		else if (state == ABaseTaskState.falid) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 500);
		}
		// Task结束
		else if (state == ABaseTaskState.finished) {
			mHandler.removeCallbacks(stopRefreshAnim);
			mHandler.postDelayed(stopRefreshAnim, 500);

		}
	}

	private TaskManager taskManager;// 管理线程

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (taskManager != null)
			taskManager.save(outState);
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
