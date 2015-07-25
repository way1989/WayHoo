package com.way.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.way.beans.City;
import com.way.fragment.WeatherFragment;

public class WeatherPagerAdapter extends FragmentPagerAdapter {
	private static final String TAG = "WeatherPagerAdapter";
	// private final HashMap<String, WeakReference<WeatherFragment>> mFragments
	// = new HashMap<String, WeakReference<WeatherFragment>>();
	private final SparseArray<WeakReference<Fragment>> mFragmentArray = new SparseArray<WeakReference<Fragment>>();
	private final ArrayList<City> mCitys = new ArrayList<City>();
	private int mCurrentPage;
	private Activity mActivity;

	public WeatherPagerAdapter(Activity activity) {
		super(activity.getFragmentManager());
		mActivity = activity;
	}

	public void setData(List<City> mainItems) {
		synchronized (mCitys) {
			mCitys.clear();
			mCitys.addAll(mainItems);
			notifyDataSetChanged();
		}
	}

	public Fragment getFragment(final int position) {
		Log.i(TAG, "getFragment...");
		final WeakReference<Fragment> weakFragment = mFragmentArray
				.get(position);
		if (weakFragment != null && weakFragment.get() != null) {
			return weakFragment.get();
		}
		return getItem(position);
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		Log.i(TAG, "instantiateItem...");
		Fragment fragment = (Fragment) super.instantiateItem(container,
				position);
		final WeakReference<Fragment> weakFragment = mFragmentArray
				.get(position);
		if (weakFragment != null) {
			weakFragment.clear();
		}
		mFragmentArray.put(position, new WeakReference<Fragment>(fragment));

		return fragment;
	}

	@Override
	public void destroyItem(final ViewGroup container, final int position,
			final Object object) {
		Log.i(TAG, "destroyItem...");
		super.destroyItem(container, position, object);
		final WeakReference<Fragment> weakFragment = mFragmentArray
				.get(position);
		if (weakFragment != null) {
			weakFragment.clear();
		}
	}

	@Override
	public Fragment getItem(int position) {
		Log.i(TAG, "getItem...");
		final City info = mCitys.get(position);
		final Fragment mFragment = WeatherFragment.newInstance(info);
		return mFragment;
	}

	@Override
	public int getCount() {
		return mCitys.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mCitys.get(position).getName();
	}

	/**
	 * Method that returns the current page position.
	 * 
	 * @return int The current page.
	 */
	public int getCurrentPage() {
		return mCurrentPage;
	}

	/**
	 * Method that sets the current page position.
	 * 
	 * @param currentPage
	 *            The current page.
	 */
	public void setCurrentPage(final int currentPage) {
		mCurrentPage = currentPage;
		Log.i("refreshUI", "setCurrentPage mCurrentPage = " + currentPage);
		if (mCurrentPage < 0 || mCurrentPage >= getCount())
			return;
		final WeakReference<Fragment> weakFragment = mFragmentArray
				.get(currentPage);
		Log.i("refreshUI", "weakFragment = " + weakFragment);
		if (weakFragment != null && weakFragment.get() != null) {
			WeatherFragment fragment = (WeatherFragment) weakFragment.get();
			Log.i("refreshUI", "WeatherFragment = " + fragment);
			fragment.refreshUI();
		}
	}

	@Override
	public int getItemPosition(Object object) {
		Log.i(TAG, "getItemPosition...");
		// if (!TextUtils.equals(((Fragment) object).getTag(),
		// makeFragmentName(mCurrentPage)))
		return POSITION_NONE;
		// return super.getItemPosition(object);
	}

	@Override
	protected String makeFragmentName(int position) {
		return mCitys.get(position).getPostID();
	}
}
