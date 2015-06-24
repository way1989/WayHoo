package com.way.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.way.beans.City;
import com.way.common.util.L;
import com.way.fragment.WeatherFragment;
import com.way.yahoo.BaseActivity;

public class WeatherPagerAdapter extends FragmentStatePagerAdapter {
	private final ArrayList<ItemInfo> mItemInfos;

	static final class ItemInfo {
		private final City city;
		private Fragment fragment;

		ItemInfo(City city) {
			this.city = city;
		}
	}

	public WeatherPagerAdapter(BaseActivity activity) {
		super(activity.getFragmentManager());
		mItemInfos = new ArrayList<ItemInfo>();
	}

	public void addItem(City city) {
		L.i("liweiping", "addItem city = " + city);
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
		notifyDataSetChanged();
	}
	@Override
	public int getItemPosition(Object object) {
		//return super.getItemPosition(object);
		return POSITION_NONE;
	}
	@Override
	public Fragment getItem(int position) {
		ItemInfo info = mItemInfos.get(position);
		if (info.fragment == null) {
			info.fragment = WeatherFragment.newInstance(info.city);
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
