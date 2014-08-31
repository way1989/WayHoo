package com.way.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.way.beans.City;
import com.way.fragment.WeatherFragment;

@SuppressLint("NewApi")
public class WeatherPagerAdapter extends FragmentStatePagerAdapter {
	private final ArrayList<ItemInfo> mItemInfos;

	static final class ItemInfo {
		private final City city;
		private Fragment fragment;

		ItemInfo(City city) {
			this.city = city;
		}
	}

	public WeatherPagerAdapter(Activity activity) {
		super(activity.getFragmentManager());
		mItemInfos = new ArrayList<ItemInfo>();
	}

	public void addItem(City city) {
		Log.i("liweiping", "addItem city = " + city);
		ItemInfo info = new ItemInfo(city);
		mItemInfos.add(info);
		notifyDataSetChanged();
	}

	public void addAllItems(List<City> mainItems) {
		mItemInfos.clear();
		for (City item : mainItems) {
			ItemInfo info = new ItemInfo(item);
			mItemInfos.add(info);
		}
		notifyDataSetChanged();
	}

	public void clearItems() {
		mItemInfos.clear();
		// notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		ItemInfo info = mItemInfos.get(position);
		if (info.fragment == null) {
			info.fragment = new WeatherFragment(info.city);
		}
		return info.fragment;
	}

	@Override
	public int getCount() {
		return mItemInfos.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mItemInfos.get(position).city.getName();
	}

}
