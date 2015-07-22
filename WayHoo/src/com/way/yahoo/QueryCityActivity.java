package com.way.yahoo;

import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.way.adapter.QueryCityAdapter;
import com.way.beans.City;
import com.way.common.util.L;
import com.way.common.util.LocationUtils.LocationListener;
import com.way.common.util.SystemUtils;
import com.way.common.util.T;
import com.way.db.CityProvider;
import com.way.db.CityProvider.CityConstants;
import com.way.ui.view.CountDownView;
import com.way.ui.view.CountDownView.OnCountDownFinishedListener;

public class QueryCityActivity extends BaseActivity implements OnClickListener,
		TextWatcher, OnItemClickListener {
	public static final String CITY_EXTRA_KEY = "city";
	private LayoutInflater mInflater;
	private RelativeLayout mRootView;
	private CountDownView mCountDownView;
	private ImageView mBackBtn;
	private TextView mLocationTV;
	private EditText mQueryCityET;
	private ImageButton mQueryCityExitBtn;
	private ListView mQueryCityListView;
	private GridView mHotCityGridView;
	private List<City> mTmpCitys;
	private List<City> mHotCitys;
	private List<City> mCities;
	private QueryCityAdapter mSearchCityAdapter;
	private Filter mFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_query_layout);
		L.i("liweiping", "QueryCityActivity onCreate...");
		initDatas();
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		L.i("liweiping", "QueryCityActivity onResume...");
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mRootView = (RelativeLayout) findViewById(R.id.city_add_bg);
		mBackBtn = (ImageView) findViewById(R.id.back_image);
		mLocationTV = (TextView) findViewById(R.id.location_text);
		mQueryCityET = (EditText) findViewById(R.id.queryCityText);
		mQueryCityExitBtn = (ImageButton) findViewById(R.id.queryCityExit);

		mQueryCityListView = (ListView) findViewById(R.id.cityList);
		mQueryCityListView.setOnItemClickListener(this);
		mSearchCityAdapter = new QueryCityAdapter(QueryCityActivity.this,
				mCities);
		mQueryCityListView.setAdapter(mSearchCityAdapter);
		mQueryCityListView.setTextFilterEnabled(true);
		mFilter = mSearchCityAdapter.getFilter();

		// mEmptyCityView = (TextView) findViewById(R.id.noCityText);

		mHotCityGridView = (GridView) findViewById(R.id.hotCityGrid);
		mHotCityGridView.setOnItemClickListener(this);
		mHotCityGridView.setAdapter(new HotCityAdapter());

		mBackBtn.setOnClickListener(this);
		mLocationTV.setOnClickListener(this);
		mQueryCityExitBtn.setOnClickListener(this);
		mQueryCityET.addTextChangedListener(this);

		String cityName = getCityName();
		if (TextUtils.isEmpty(cityName)) {
			startLocation(mCityNameStatus);
		} else {
			mLocationTV.setText(formatBigMessage(cityName));
		}
	}

	// This is the message string used in bigText and bigPicture notifications.
	public CharSequence formatBigMessage(String city) {
		final TextAppearanceSpan notificationSubjectSpan = new TextAppearanceSpan(
				this, R.style.NotificationPrimaryText);

		// Change multiple newlines (with potential white space between), into a
		// single new line
		final String message = !TextUtils.isEmpty(city) ? city : "";
		String afterStr = "(点击重新定位)";
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
				message);
		if (!TextUtils.isEmpty(afterStr)) {
			spannableStringBuilder.append(afterStr);
			spannableStringBuilder.setSpan(notificationSubjectSpan,
					message.length(), message.length() + afterStr.length(), 0);
		}
		return spannableStringBuilder;
	}

	private String getCityName() {
		Cursor c = mContentResolver.query(CityProvider.TMPCITY_CONTENT_URI,
				new String[] { CityConstants.NAME }, CityConstants.ISLOCATION
						+ "=?", new String[] { "1" }, null);

		String name = "";
		if (c.moveToFirst())
			name = c.getString(c.getColumnIndex(CityConstants.NAME));
		return name;
	}

	private void initDatas() {
		Cursor cityCursor = mContentResolver.query(
				CityProvider.CITY_CONTENT_URI, null, null, null, null);
		mCities = SystemUtils.getAllCities(cityCursor);

		Cursor hotCityCursor = mContentResolver.query(
				CityProvider.HOTCITY_CONTENT_URI, null, null, null, null);
		mHotCitys = SystemUtils.getHotCities(hotCityCursor);
		Cursor tmpCityCursor = mContentResolver.query(
				CityProvider.TMPCITY_CONTENT_URI, null, null, null, null);
		mTmpCitys = SystemUtils.getTmpCities(tmpCityCursor);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.cityList:
			City city = mSearchCityAdapter.getItem(position);
			L.i("liweiping", city.getName());
			addToTmpCityTable(city);
			break;
		case R.id.hotCityGrid:
			City hotCity = mHotCitys.get(position);
			L.i("liweiping", hotCity.getName());
			addToTmpCityTable(hotCity);
			break;
		default:
			break;
		}
	}

	private void addToTmpCityTable(City city) {
		// 已经存在此城市，提示一下，直接返回
		if (mTmpCitys.contains(city)) {
			Toast.makeText(this, R.string.city_exists, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 存储
		ContentValues tmpContentValues = new ContentValues();
		tmpContentValues.put(CityConstants.NAME, city.getName());
		tmpContentValues.put(CityConstants.POST_ID, city.getPostID());
		tmpContentValues.put(CityConstants.REFRESH_TIME, 0L);// 无刷新时间
		tmpContentValues.put(CityConstants.ISLOCATION, 0);// 手动选择的城市存储为0
		tmpContentValues.put(CityConstants.ORDER_INDEX, mTmpCitys.size());
		mContentResolver.insert(CityProvider.TMPCITY_CONTENT_URI,
				tmpContentValues);

		Intent i = new Intent();
		i.putExtra(CITY_EXTRA_KEY, city);
		setResult(RESULT_OK, i);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_image:
			finish();
			break;
		case R.id.location_text:
			startLocation(mCityNameStatus);
			break;
		case R.id.queryCityExit:
			mQueryCityET.setText("");
			break;
		case R.id.cancel_locate_city_btn:
			T.showShort(this, R.string.cancle_auto_get_location);
			stopLocation();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopLocation();
	}

	@Override
	protected void stopLocation() {
		super.stopLocation();
		dismissCountDownView();
	}

	@Override
	public void onBackPressed() {
		if (mCountDownView != null && mCountDownView.isCountingDown()) {
			T.showShort(this, R.string.cancle_auto_get_location);
			mCountDownView.cancelCountDown();
		} else {
			super.onBackPressed();
		}
	}

	public boolean enoughToFilter() {
		return mQueryCityET.getText().length() > 0;
	}

	private void doBeforeTextChanged() {
		if (mQueryCityListView.getVisibility() == View.GONE) {
			mQueryCityListView.setVisibility(View.VISIBLE);
		}
	}

	private void doAfterTextChanged() {
		if (enoughToFilter()) {
			L.i("liweiping", "onTextChanged  s = "
					+ mQueryCityET.getText().toString());
			if (mFilter != null) {
				mFilter.filter(mQueryCityET.getText().toString().trim());
			}
		} else {
			if (mQueryCityListView.getVisibility() == View.VISIBLE) {
				mQueryCityListView.setVisibility(View.GONE);
			}
			if (mFilter != null) {
				mFilter.filter(null);
			}
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		doBeforeTextChanged();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (TextUtils.isEmpty(s)) {
			mQueryCityExitBtn.setVisibility(View.GONE);
		} else {
			mQueryCityExitBtn.setVisibility(View.VISIBLE);
		}
		doAfterTextChanged();
	}

	@Override
	public void afterTextChanged(Editable s) {
		// do nothing
	}

	OnCountDownFinishedListener countDownFinishedListener = new OnCountDownFinishedListener() {

		@Override
		public void onCountDownFinished() {
			Toast.makeText(QueryCityActivity.this, R.string.getlocation_fail,
					Toast.LENGTH_SHORT).show();
			stopLocation();
		}
	};

	LocationListener mCityNameStatus = new LocationListener() {

		@Override
		public void detecting() {
			L.i("liweiping", "detecting...");
			showCountDownView();
		}

		@Override
		public void succeed(String name) {
			L.i("liweiping", name);
			dismissCountDownView();

			City city = getLocationCityFromDB(name);
			if (TextUtils.isEmpty(city.getPostID())) {
				Toast.makeText(QueryCityActivity.this, R.string.no_this_city,
						Toast.LENGTH_SHORT).show();
			} else {
				L.i("liweiping", "location" + city.toString());
				addOrUpdateLocationCity(city);
				T.showShort(
						QueryCityActivity.this,
						String.format(
								getResources().getString(
										R.string.get_location_scuess), name));
				mLocationTV.setText(formatBigMessage(name));
			}
		}

		@Override
		public void failed() {
			Toast.makeText(QueryCityActivity.this, R.string.getlocation_fail,
					Toast.LENGTH_SHORT).show();
		}

	};

	private void showCountDownView() {
		mInflater.inflate(R.layout.count_down_to_location, mRootView, true);
		mCountDownView = (CountDownView) mRootView
				.findViewById(R.id.count_down_to_locate);
		Button btn = (Button) mRootView
				.findViewById(R.id.cancel_locate_city_btn);
		btn.setOnClickListener(this);
		mCountDownView.setCountDownFinishedListener(countDownFinishedListener);
		mCountDownView.startCountDown(30);
	}

	private void dismissCountDownView() {
		if (mCountDownView != null && mCountDownView.isCountingDown())
			mCountDownView.cancelCountDown();
	}

	private class HotCityAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mHotCitys.size();
		}

		@Override
		public Object getItem(int position) {
			return mHotCitys.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			City hotCity = (City) getItem(position);
			ViewHoler viewHoler;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.city_query_hotcity_grid_item, parent, false);
				viewHoler = new ViewHoler();
				viewHoler.hotCityTV = (TextView) convertView
						.findViewById(R.id.grid_city_name);
				viewHoler.selectedIV = (ImageView) convertView
						.findViewById(R.id.grid_city_selected_iv);
				convertView.setTag(viewHoler);
			} else {
				viewHoler = (ViewHoler) convertView.getTag();
			}
			viewHoler.hotCityTV.setText(hotCity.getName());
			if (mTmpCitys.contains(hotCity)) {
				viewHoler.selectedIV.setVisibility(View.VISIBLE);
			} else {
				viewHoler.selectedIV.setVisibility(View.GONE);
			}

			return convertView;
		}

	}

	static class ViewHoler {
		TextView hotCityTV;
		ImageView selectedIV;
	}

}
