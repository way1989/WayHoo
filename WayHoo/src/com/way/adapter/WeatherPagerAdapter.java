package com.way.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.way.beans.City;
import com.way.fragment.WeatherFragment;

public class WeatherPagerAdapter extends FragmentPagerAdapter {

	private final HashMap<City, WeakReference<Fragment>> mFragments = new HashMap<City, WeakReference<Fragment>>();
	private final ArrayList<City> mCitys = new ArrayList<City>();
	private int mCurrentPage;

	public WeatherPagerAdapter(Activity activity) {
		super(activity.getFragmentManager());
	}

	public void setData(List<City> mainItems) {
		synchronized (mCitys) {
			mCitys.clear();
			mCitys.addAll(mainItems);
			notifyDataSetChanged();
		}
	}

	public Fragment getFragment(final int position) {
		final WeakReference<Fragment> weakFragment = mFragments.get(mCitys
				.get(position));
		if (weakFragment != null && weakFragment.get() != null) {
			return weakFragment.get();
		}
		return getItem(position);
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		final Fragment mFragment = (Fragment) super.instantiateItem(container,
				position);
		final WeakReference<Fragment> weakFragment = mFragments.get(mCitys
				.get(position));
		if (weakFragment != null) {
			weakFragment.clear();
		}
		mFragments.put(mCitys.get(position), new WeakReference<Fragment>(
				mFragment));
		return mFragment;
	}

	@Override
	public void destroyItem(final ViewGroup container, final int position,
			final Object object) {
		super.destroyItem(container, position, object);
		final WeakReference<Fragment> weakFragment = mFragments.get(position);
		if (weakFragment != null) {
			weakFragment.clear();
		}
	}

	@Override
	public Fragment getItem(int position) {
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
	protected void setCurrentPage(final int currentPage) {
		mCurrentPage = currentPage;
	}
}
