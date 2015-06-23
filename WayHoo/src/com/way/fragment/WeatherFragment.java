package com.way.fragment;

import org.json.JSONException;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.way.yahoo.BaseActivity;
import com.way.yahoo.ManagerCityActivity;
import com.way.yahoo.R;

public class WeatherFragment extends Fragment implements OnRefreshListener,
		OnPullEventListener {
	public static final String ARG_CITY = "city";
	public static final String ARG_WEATHERINFO = "weatherInfo";
	public static final String REFRESH_TIME_KEY = "refreshTime";
	// private GetDataTask mGetDataTask;
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
	private BaseActivity mActivity;
	private City mCurCity;

	public WeatherFragment() {
	}

	public WeatherFragment(BaseActivity activity, City city) {
		mActivity = activity;
		mCurCity = city;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContentResolver = getActivity().getContentResolver();
	}

	private View mRootView;
	// 分别表示当前Fragment是否可见,是否已准备(表示已经走过onCreateView方法)以及是否数据已加载
	private boolean isVisible = false;
	private boolean isPrepared = false;
	private boolean isLoaded = false;
	private AsynTaskState mAsynState = AsynTaskState.INIT;

	/**
	 * 异步数据处理的状态枚举
	 */
	private enum AsynTaskState {

		/**
		 * 初始化，应该进行异步数据加载，应继续使用Loading布局
		 */
		INIT,

		/**
		 * 处理中，继续使用Loading布局
		 */
		PROCESSING,

		/**
		 * 处理完成，可以加载UI，执行加载内容布局
		 */
		RPOCESSED,

		/**
		 * 加载完成，继续使用内容布局
		 */
		COMPLETE

	}

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
			onLoadedData();
			// onFragmentVisible();
		} else {
			// onFragmentInvisible();
		}
	}

	/**
	 * 在Fragment可见时进行判断是否载入数据
	 */
	private void onLoadedData() {
		if (!isPrepared)
			return;
		if (isLoaded) {
			updateWeatherView();
		} else {
			switch (mAsynState) {
			case INIT:
				// mGetDataTask = new GetDataTask();
				// mGetDataTask.execute();
				if(isNeedRequestNet()){
					mAsynState = AsynTaskState.PROCESSING;
					getWeather(mCurCity.getPostID());
					if(mPullRefreshScrollView.getState() != State.MANUAL_REFRESHING)
						mPullRefreshScrollView.setRefreshing();
				}else{
					loadWeatherInfoFromLocal();
				}
				break;
			case PROCESSING:
				if(mPullRefreshScrollView.getState() != State.MANUAL_REFRESHING)
				mPullRefreshScrollView.setRefreshing();
				break;
			case RPOCESSED:
				if(mPullRefreshScrollView.getState() != State.MANUAL_REFRESHING)
				mPullRefreshScrollView.setRefreshing();
				break;
			case COMPLETE:
				updateWeatherView();
				break;
			}
		}
	}
	private boolean isNeedRequestNet(){
		int netState = NetUtil.getNetworkState(getActivity());
		if(netState == NetUtil.NETWORN_NONE){
			return false;
		}
		long refreshTime = getRefreshTime();
		if(netState == NetUtil.NETWORN_WIFI){
			return ((System.currentTimeMillis() - refreshTime) > (1000 * 60 * 30));//wifi网络30分钟过期
		}
		if(netState == NetUtil.NETWORN_MOBILE){
			return ((System.currentTimeMillis() - refreshTime) > (1000 * 60 * 60 * 2));//移动网络两个小时过期
		}
		
		return false;
	}
	
	private void loadWeatherInfoFromLocal(){
		if(mCurCity == null)
			return;
		try {
			mWeatherInfo = WeatherSpider
				.getWeatherInfo(mActivity, mCurCity.getPostID(), mCurCity.getWeatherInfoStr());
			if(!WeatherSpider.isEmpty(mWeatherInfo)){
				isLoaded = true;
				updateWeatherView();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
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
				if(isNeedRequestNet()){
					getWeather(mCurCity.getPostID());
				}else{
					loadWeatherInfoFromLocal();
				}
			} else {
				loadWeatherInfoFromLocal();
			}
		} else {
			ViewGroup mRootParent = (ViewGroup) mRootView.getParent();
			if (mRootParent != null) {
				mRootParent.removeView(mRootView);
			}
			if (isLoaded) {
				updateWeatherView();
			} else {
				if (isVisible) {
					switch (mAsynState) {
					case INIT:
						if(isNeedRequestNet()){
							mAsynState = AsynTaskState.PROCESSING;
							getWeather(mCurCity.getPostID());
							if(mPullRefreshScrollView.getState() != State.MANUAL_REFRESHING)
							mPullRefreshScrollView.setRefreshing();
						}else{
							loadWeatherInfoFromLocal();
						}
						break;
					case PROCESSING:
						if(mPullRefreshScrollView.getState() != State.MANUAL_REFRESHING)
						mPullRefreshScrollView.setRefreshing();
						break;
					case RPOCESSED:
						if(mPullRefreshScrollView.getState() != State.MANUAL_REFRESHING)
						mPullRefreshScrollView.setRefreshing();
						break;
					case COMPLETE:
						updateWeatherView();
						break;
					}
				} else {
				}
			}
		}
		// L.i("liweiping","Fragment onCreateView");
		return mRootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (isNeedDestroy()) {
			mRootView = null;
			isVisible = false;
			isPrepared = false;
			isLoaded = false;

			mAsynState = AsynTaskState.INIT;
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

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// initViews(view);
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
		mWeatherAdapter = new WeatherListAdapter(getActivity());
		mListView.setAdapter(mWeatherAdapter);
		mListView.setOnScrollListener(mOnScrollListener);// 监听滑动
		initCurWeatherViews(view);
		mListView.setSelection(0);// 默认选中最上面一个view

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

		// updateWeatherView(App.mMainMap.get(mCurCity.getPostID()), false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// mListView.setSelection(0);// 选中第一个view，当fragment被回收后再重新创建时恢复状态
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
				// L.i("liweiping",
				// "topChild != mHeaderView =" + mHeaderView.getHeight());
			} else {
				onNewScroll(-topChild.getTop());
				// L.i("liweiping",
				// "-topChild.getTop() = " + (-topChild.getTop()));
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
			} else if (scrollPosition > mHeaderHeight / 2) {
				// 上滑距离超过HeaderView高度一半时，取消刷新，同时要注意停止异步任务
				mPullRefreshScrollView.onRefreshComplete();
				// if (mGetDataTask != null)
				// mGetDataTask.cancel(true);
				App.getVolleyRequestQueue().cancelAll(mCurCity.getPostID());
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

	/**
	 * 异步任务获取天气信息
	 * 
	 */
	private static final String WEATHER_ALL = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=%s";

	private void getWeather(final String postID) {
		mAsynState = AsynTaskState.PROCESSING;
		// Call setRefreshing when the list begin to refresh.
		mPullRefreshScrollView.setRefreshing();

		StringRequest sr = new StringRequest(
				String.format(WEATHER_ALL, postID),
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						mAsynState = AsynTaskState.RPOCESSED;
						mPullRefreshScrollView.onRefreshComplete();
						try {
							WeatherInfo weatherInfo = WeatherSpider
									.getWeatherInfo(mActivity, postID, response);

							if (!WeatherSpider.isEmpty(weatherInfo)) {
								mWeatherInfo = weatherInfo;
								isLoaded = true;
								if (getActivity() != null)
									Toast.makeText(getActivity(),
											"刷新成功:" + mCurCity.getName(),
											Toast.LENGTH_SHORT).show();
								save2Database(postID, response);
								
								updateWeatherView();
							} else {
								if (getActivity() != null)
									Toast.makeText(getActivity(),
											"刷新失败:" + mCurCity.getName(),
											Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							if (getActivity() != null)
								Toast.makeText(getActivity(),
										"刷新失败:" + mCurCity.getName(),
										Toast.LENGTH_SHORT).show();
						}
						mAsynState = AsynTaskState.COMPLETE;
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						mAsynState = AsynTaskState.RPOCESSED;
						mPullRefreshScrollView.onRefreshComplete();
						if (getActivity() != null)
							Toast.makeText(getActivity(),
									"刷新失败:" + mCurCity.getName(),
									Toast.LENGTH_SHORT).show();
						mAsynState = AsynTaskState.COMPLETE;
					}
				});
		sr.setTag(postID);
		App.getVolleyRequestQueue().add(sr);
	}

	protected void save2Database(String postID, String response) {
		long pubTime = mWeatherInfo.getRealTime().getPub_time();
		long savePubTime = getPubTime();
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
	
	private long getPubTime(){
		Cursor c = mContentResolver.query(CityProvider.TMPCITY_CONTENT_URI,
				new String[] { CityConstants.PUB_TIME },
				CityConstants.POST_ID + "=?",
				new String[] { mCurCity.getPostID() }, null);

		long time = 0L;
		if (c.moveToFirst())
			time = c.getLong(c.getColumnIndex(CityConstants.PUB_TIME));
		return time;
	}
	
	private long getRefreshTime(){
		Cursor c = mContentResolver.query(CityProvider.TMPCITY_CONTENT_URI,
				new String[] { CityConstants.REFRESH_TIME },
				CityConstants.POST_ID + "=?",
				new String[] { mCurCity.getPostID() }, null);

		long time = 0L;
		if (c.moveToFirst())
			time = c.getLong(c.getColumnIndex(CityConstants.REFRESH_TIME));
		return time;
	}

	private WeatherInfo mWeatherInfo;

	/**
	 * 更新天气信息界面
	 */
	private void updateWeatherView() {
		WeatherInfo weatherInfo = mWeatherInfo;
		if (WeatherSpider.isEmpty(weatherInfo)) {
			return;
		}
		RealTime realTime = weatherInfo.getRealTime();
		AQI aqi = weatherInfo.getAqi();
		Forecast forecast = weatherInfo.getForecast();
		Index index = weatherInfo.getIndex();

		int type = realTime.getAnimation_type();
		// mNormalImageView.setImageResource(WeatherIconUtils
		// .getWeatherNromalBg(type));
		// mBlurredImageView.setImageResource(WeatherIconUtils
		// .getWeatherBlurBg(type));
		mNormalImageView.setImageBitmap(SystemUtils.readBitMap(mActivity,
				WeatherIconUtils.getRawNromalBg(type)));
		mBlurredImageView.setImageBitmap(SystemUtils.readBitMap(mActivity,
				WeatherIconUtils.getRawBlurBg(type)));
		mCurWeatherIV.setImageResource(WeatherIconUtils.getWeatherIcon(type));
		mCurWeatherTV.setText(realTime.getWeather_name());
		mCurFeelsTempTV.setText(realTime.getTemp() + "");
		mCurHighTempTV.setText(forecast.getTmpHigh(1) + "°");
		mCurLowTempTV.setText(forecast.getTmpLow(1) + "°");
		mCurWeatherCopyTV.setText(TimeUtils.getDay(realTime.getPub_time())
				+ "发布");

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
		// 如果正在刷新，则返回
		getWeather(mCurCity.getPostID());
		// if (mGetDataTask != null && mGetDataTask.getStatus() ==
		// Status.RUNNING)
		// return;
		// mGetDataTask = new GetDataTask();
		// mGetDataTask.execute();
	}
}
