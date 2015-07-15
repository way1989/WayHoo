package com.way.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.way.beans.City;
import com.way.fragment.WeatherFragment;

public class WeatherPagerAdapter extends FragmentPagerAdapter {
	private static final String TAG = "WeatherPagerAdapter";
	private final HashMap<String, WeakReference<WeatherFragment>> mFragments = new HashMap<String, WeakReference<WeatherFragment>>();
	// private final SparseArray<WeakReference<Fragment>> mFragmentArray = new
	// SparseArray<WeakReference<Fragment>>();
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

	public WeatherFragment getFragment(final int position) {
		Log.i(TAG, "getFragment...");
		final WeakReference<WeatherFragment> weakFragment = mFragments
				.get(mCitys.get(position).getPostID());
		if (weakFragment != null && weakFragment.get() != null) {
			return weakFragment.get();
		}
		return getItem(position);
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		Log.i(TAG, "instantiateItem...");
		WeatherFragment fragment = (WeatherFragment) super.instantiateItem(
				container, position);
		String fragmentTag = fragment.getTag();
		final WeakReference<WeatherFragment> weakFragment = mFragments
				.get(mCitys.get(position).getPostID());
		if (weakFragment != null
				&& !TextUtils.equals(mCitys.get(position).getPostID(), fragment
						.getCity().getPostID())) {
			// weakFragment.clear();
			FragmentTransaction ft = mActivity.getFragmentManager()
					.beginTransaction();
			// 移除旧的fragment
			ft.remove(fragment);
			// 换成新的fragment
			fragment = getFragment(position);

			// 添加新fragment时必须用前面获得的tag ❶
			ft.add(container.getId(), fragment, fragmentTag);
			ft.attach(fragment);
			ft.commit();
		}
		mFragments.put(mCitys.get(position).getPostID(),
				new WeakReference<WeatherFragment>(fragment));

		return fragment;
	}

	@Override
	public void destroyItem(final ViewGroup container, final int position,
			final Object object) {
		Log.i(TAG, "destroyItem...");
		super.destroyItem(container, position, object);
		final WeakReference<WeatherFragment> weakFragment = mFragments
				.get(mCitys.get(position).getPostID());
		if (weakFragment != null) {
			weakFragment.clear();
		}
	}

	@Override
	public WeatherFragment getItem(int position) {
		Log.i(TAG, "getItem...");
		final City info = mCitys.get(position);
		final WeatherFragment mFragment = WeatherFragment.newInstance(info);
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
	protected void setCurrentPage(final int currentPage) {
		mCurrentPage = currentPage;
	}

	@Override
	public int getItemPosition(Object object) {
		// return super.getItemPosition(object);
		Log.i(TAG, "getItemPosition...");
		return POSITION_NONE;
	}
}
