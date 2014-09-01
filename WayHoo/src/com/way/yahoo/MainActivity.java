package com.way.yahoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.viewpagerindicator.CirclePageIndicator;
import com.way.adapter.SideMenuAdapter;
import com.way.adapter.WeatherPagerAdapter;
import com.way.beans.Category;
import com.way.beans.City;
import com.way.beans.Item;
import com.way.common.util.L;
import com.way.common.util.LocationUtils.CityNameStatus;
import com.way.common.util.PreferenceUtils;
import com.way.common.util.SystemUtils;
import com.way.common.util.T;
import com.way.common.util.TimeUtils;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.weather.plugin.spider.WeatherSpider;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {
	private static final String INSTANCESTATE_TAB = "tab_index";
	private String mShareNormalStr = "#威震天气#提醒您:今天%s,%s,%s,%s,";// 日期、城市、天气、温度
	private String mAqiShareStr = "空气质量指数(AQI):%s μg/m³,等级[%s];PM2.5浓度值:%s μg/m³。%s ";// aqi、等级、pm2.5、建议
	private String mShareEndStr = "（请关注博客：http://blog.csdn.net/way_ping_li）";
	private MenuDrawer mMenuDrawer;
	private View mSplashView;
	private static final int SHOW_TIME_MIN = 3000;// 最小显示时间
	private long mStartTime;// 开始时间
	private Handler mHandler;
	private SideMenuAdapter mMenuAdapter;
	private int mPagerOffsetPixels;
	private int mPagerPosition;
	private TextView mTitleTextView;
	private ImageView mShareBtn;
	private ImageView mLocationIV;
	private Button mAddCityBtn;

	private ListView mMenuListView;
	private FrameLayout mRootView;
	private ViewPager mMainViewPager;
	private CirclePageIndicator mCirclePageIndicator;
	private WeatherPagerAdapter mFragmentAdapter;
	private List<City> mTmpCities;

	// 提供接口给fragment
	public MenuDrawer getMenuDrawer() {
		return mMenuDrawer;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDatas();
		initMenuDrawer();
		mMenuDrawer.setContentView(R.layout.activity_main);
		initViews();
	}

	private void initDatas() {
		mStartTime = System.currentTimeMillis();// 记录开始时间，
		mHandler = new Handler();
		// mTmpCities = getTmpCities();
		// 第一次进来无定位城市
		if (TextUtils.isEmpty(PreferenceUtils.getPrefString(this,
				AUTO_LOCATION_CITY_KEY, ""))) {
			startLocation(mCityNameStatus);
		}
	}

	private void initViews() {
		setSwipeBackEnable(false);
		mSplashView = findViewById(R.id.splash_view);
		mRootView = (FrameLayout) findViewById(R.id.root_view);
		mAddCityBtn = (Button) findViewById(R.id.add_city_btn);
		mAddCityBtn.setOnClickListener(this);
		mTitleTextView = (TextView) findViewById(R.id.location_city_textview);
		mLocationIV = (ImageView) findViewById(R.id.curr_loc_icon);
		mMainViewPager = (ViewPager) findViewById(R.id.main_viewpager);
		// 设置viewpager缓存view的个数，默认为1个，理论上是越多越好，但是比较耗内存，
		// 我这里设置两个，性能上有点改善
		// mMainViewPager.setOffscreenPageLimit(2);
		mFragmentAdapter = new WeatherPagerAdapter(this);
		mMainViewPager.setAdapter(mFragmentAdapter);
		mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mCirclePageIndicator.setViewPager(mMainViewPager);
		mCirclePageIndicator.setOnPageChangeListener(this);

		mTitleTextView.setOnClickListener(this);
		findViewById(R.id.sidebarButton).setOnClickListener(this);
		mShareBtn = (ImageView) findViewById(R.id.shareButton);
		mShareBtn.setOnClickListener(this);
	}

	private class MyTask extends AsyncTask<List<City>, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(List<City>... params) {
			List<City> cities = params[0];
			try {
				for (City city : cities) {
					WeatherInfo info = WeatherSpider.getInstance()
							.getWeatherInfo(MainActivity.this,
									city.getPostID(), false);
					App.mMainMap.put(city.getPostID(), info);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			updateUI();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		mTmpCities = getTmpCities();
		if (App.mMainMap.isEmpty() && !mTmpCities.isEmpty()) {
			new MyTask().execute(mTmpCities);
		} else if (!App.mMainMap.isEmpty()) {
			updateUI();
		} else {
			// 需要定位
		}
		invisibleSplash();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 保存默认选择页
		PreferenceUtils.setPrefInt(this, INSTANCESTATE_TAB,
				mMainViewPager.getCurrentItem());
	}

	private void updateUI() {

		// mFragmentAdapter = new WeatherPagerAdapter(MainActivity.this);
		mFragmentAdapter.addAllItems(mTmpCities);
		// mMainViewPager.setAdapter(mFragmentAdapter);
		// mCirclePageIndicator.setViewPager(mMainViewPager);
		L.i("MainActivity updateUI...");
		// getTmpCities();
		mMenuAdapter.addContent(mTmpCities);
		mCirclePageIndicator.notifyDataSetChanged();
		// 第一次进来没有数据
		if (mTmpCities.isEmpty()) {
			mMainViewPager.removeAllViews();
			mTitleTextView.setText("--");
			mLocationIV.setVisibility(View.GONE);
			mAddCityBtn.setVisibility(View.VISIBLE);
			mShareBtn.setEnabled(false);
			return;
		}
		mAddCityBtn.setVisibility(View.GONE);
		if (mTmpCities.size() > 1)
			mCirclePageIndicator.setVisibility(View.VISIBLE);
		else
			mCirclePageIndicator.setVisibility(View.GONE);
		mShareBtn.setEnabled(true);

		int defaultTab = PreferenceUtils.getPrefInt(this, INSTANCESTATE_TAB, 0);
		if (defaultTab > (mTmpCities.size() - 1))// 防止手动删除城市之后出现数组越界
			defaultTab = 0;
		mMainViewPager.setCurrentItem(defaultTab, true);
		mTitleTextView.setText(mFragmentAdapter.getPageTitle(defaultTab));
		if (mTmpCities.get(defaultTab).getIsLocation())
			mLocationIV.setVisibility(View.VISIBLE);
		else
			mLocationIV.setVisibility(View.GONE);
	}

	private void invisibleSplash() {
		long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
		if (loadingTime < SHOW_TIME_MIN) {// 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
			mHandler.postDelayed(splashGone, SHOW_TIME_MIN - loadingTime);
		} else {
			mHandler.post(splashGone);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sidebarButton:
			mMenuDrawer.toggleMenu(true);
			break;
		case R.id.shareButton:
			shareTo();
			break;
		case R.id.location_city_textview:
		case R.id.add_city_btn:
			startActivity(new Intent(MainActivity.this,
					ManagerCityActivity.class));
			break;
		default:
			break;
		}
	}

	private void shareTo() {
		new AsyncTask<Void, Void, File>() {
			Dialog dialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog = SystemUtils.getCustomeDialog(MainActivity.this,
						R.style.load_dialog, R.layout.custom_progress_dialog);
				TextView titleTxtv = (TextView) dialog
						.findViewById(R.id.dialogText);
				titleTxtv.setText(R.string.please_wait);
				dialog.show();
			}

			@Override
			protected File doInBackground(Void... params) {
				try {
					new File(getFilesDir(), "share.png").deleteOnExit();
					FileOutputStream fileOutputStream = openFileOutput(
							"share.png", 1);
					mRootView.setDrawingCacheEnabled(true);
					mRootView.getDrawingCache().compress(
							Bitmap.CompressFormat.PNG, 100, fileOutputStream);
					return new File(getFilesDir(), "share.png");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(File result) {
				super.onPostExecute(result);
				dialog.dismiss();
				if (result == null || App.mMainMap.isEmpty()) {
					Toast.makeText(MainActivity.this, R.string.share_fail,
							Toast.LENGTH_SHORT).show();
					return;
				}
				WeatherInfo info = App.mMainMap.get(mTmpCities.get(
						mMainViewPager.getCurrentItem()).getPostID());
				if (info == null || info.getRealTime() == null
						|| info.getRealTime().getAnimation_type() < 0) {
					Toast.makeText(MainActivity.this, R.string.share_fail,
							Toast.LENGTH_SHORT).show();
					return;
				}
				String time = TimeUtils.getDateTime(System.currentTimeMillis());
				String name = mFragmentAdapter.getPageTitle(
						mMainViewPager.getCurrentItem()).toString();
				String weather = info.getRealTime().getWeather_name();
				String temp = info.getRealTime().getTemp() + "°";

				String shareStr = mShareNormalStr + mAqiShareStr + mShareEndStr;
				if (info.getAqi() == null || info.getAqi().getAqi() < 0) {
					shareStr = mShareNormalStr + mShareEndStr;
					shareStr = String.format(shareStr, new Object[] { time,
							name, weather, temp });
				} else {
					shareStr = String.format(shareStr, new Object[] { time,
							name, weather, temp, info.getAqi().getAqi(),
							info.getAqi().getAqi_level(),
							info.getAqi().getPm25(),
							info.getAqi().getAqi_desc() });
				}

				Intent intent = new Intent("android.intent.action.SEND");
				intent.setType("image/*");
				intent.putExtra("sms_body", shareStr);
				intent.putExtra("android.intent.extra.TEXT", shareStr);
				intent.putExtra("android.intent.extra.STREAM",
						Uri.fromFile(result));
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(Intent.createChooser(intent, getResources()
						.getString(R.string.share_to)));
			}
		}.execute();
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// do nothing
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		mPagerPosition = position;
		mPagerOffsetPixels = positionOffsetPixels;
	}

	@Override
	public void onPageSelected(int position) {
		if (position < mFragmentAdapter.getCount())
			mTitleTextView.setText(mFragmentAdapter.getPageTitle(position));
		if (position >= mTmpCities.size()) {
			mLocationIV.setVisibility(View.GONE);
			return;
		}

		City city = mTmpCities.get(position);
		if (city != null && city.getIsLocation())
			mLocationIV.setVisibility(View.VISIBLE);
		else
			mLocationIV.setVisibility(View.GONE);
	}

	private void initMenuDrawer() {
		// 覆盖在View之前的侧边栏菜单
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY);
		mMenuDrawer.setMenuSize(Math.round(0.7f * SystemUtils
				.getDisplayWidth(this)));
		// View之后的侧边栏菜单
		// mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND,
		// Position.LEFT, MenuDrawer.MENU_DRAG_CONTENT);
		mMenuListView = (ListView) LayoutInflater.from(this).inflate(
				R.layout.sidemenu_listview, null);

		mMenuDrawer.setMenuView(mMenuListView);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		mMenuDrawer
				.setOnInterceptMoveEventListener(new MenuDrawer.OnInterceptMoveEventListener() {
					@Override
					public boolean isViewDraggable(View v, int dx, int x, int y) {
						if (v == mMainViewPager) {
							return !(mPagerPosition == 0 && mPagerOffsetPixels == 0)
									|| dx < 0;
						}
						return false;
					}
				});

		mMenuAdapter = new SideMenuAdapter(this);
		// mMenuAdapter.addContent(mTmpCities);
		mMenuListView.setAdapter(mMenuAdapter);
		mMenuListView.setOnItemClickListener(mItemClickListener);
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Object item = mMenuAdapter.getItem(position);
			if (item instanceof Category) {
				return;
			}
			onMenuItemClicked(position, (Item) item);
		}
	};

	protected void onMenuItemClicked(int position, Item item) {
		mMenuDrawer.toggleMenu(true);// 关闭此窗口
		// Toast.makeText(this, item.mTitleStr, Toast.LENGTH_SHORT).show();
		switch (item.mId) {
		case Item.INFINITE_ID:
			startActivity(new Intent(MainActivity.this,
					ManagerCityActivity.class));
			break;
		case Item.SETTING_ID:

			break;
		case Item.SHARE_ID:

			break;
		case Item.FEEDBACK_ID:
			startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
			break;
		case Item.ABOUT_ID:
			startActivity(new Intent(MainActivity.this, AboutActivity.class));
			break;

		default:
			if (mMainViewPager.getCurrentItem() != item.mId)
				mMainViewPager.setCurrentItem(item.mId);
			break;
		}
	}

	CityNameStatus mCityNameStatus = new CityNameStatus() {

		@Override
		public void detecting() {
			// do nothing
		}

		@Override
		public void update(String name) {
			City city = getLocationCityFromDB(name);
			if (TextUtils.isEmpty(city.getPostID())) {
				Toast.makeText(MainActivity.this, R.string.no_this_city,
						Toast.LENGTH_SHORT).show();
			} else {
				PreferenceUtils.setPrefString(MainActivity.this,
						AUTO_LOCATION_CITY_KEY, name);
				L.i("liweiping", "location" + city.toString());
				addOrUpdateLocationCity(city);
				T.showShort(
						MainActivity.this,
						String.format(
								getResources().getString(
										R.string.get_location_scuess), name));

				mTmpCities = getTmpCities();
				new MyTask().execute(mTmpCities);
			}
		}

	};
	private ShowcaseView mShowcaseView;
	private int counter = 0;
	// 进入下一个Activity
	Runnable splashGone = new Runnable() {

		@Override
		public void run() {
			if (mSplashView.getVisibility() != View.VISIBLE)
				return;
			Animation anim = AnimationUtils.loadAnimation(MainActivity.this,
					R.anim.push_right_out);
			anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					mSplashView.setVisibility(View.GONE);
					if (PreferenceUtils.getPrefBoolean(MainActivity.this,
							"showcase", true)) {
						mShowcaseView = new ShowcaseView.Builder(
								MainActivity.this)
								.setTarget(
										new ViewTarget(
												findViewById(R.id.sidebarButton)))
								.setContentTitle("点击查看更多设置")
								.setOnClickListener(showcaseOnClick).build();
						mShowcaseView.setButtonText("下一个");
						mMainViewPager.setAlpha(0.2f);
					}
				}
			});
			mSplashView.startAnimation(anim);
		}
	};

	OnClickListener showcaseOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (counter) {
			case 0:
				mShowcaseView.setContentTitle("点击进入城市管理");
				mShowcaseView.setShowcase(new ViewTarget(mTitleTextView), true);
				break;
			case 1:
				mShowcaseView.setContentTitle("点击分享天气信息");
				mShowcaseView.setShowcase(new ViewTarget(mShareBtn), true);
				break;
			case 2:
				mShowcaseView.setContentTitle("向上滑动查看更多");
				mShowcaseView.setShowcase(new ViewTarget(
						findViewById(R.id.show_last_case)), true);
				mShowcaseView.setButtonPosition(getLastShowCaseBtnLocation());
				mShowcaseView.setButtonText("确定");
				break;
			case 3:
				PreferenceUtils.setPrefBoolean(MainActivity.this, "showcase",
						false);
				mMainViewPager.setAlpha(1.0f);
				mShowcaseView.hide();
				break;
			default:
				break;
			}
			counter++;
		}
	};

	private RelativeLayout.LayoutParams getLastShowCaseBtnLocation() {
		RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lps.addRule(RelativeLayout.CENTER_IN_PARENT);
		lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		int margin = ((Number) (getResources().getDisplayMetrics().density * 12))
				.intValue();
		lps.setMargins(0, 0, margin, 0);
		return lps;
	}
}
