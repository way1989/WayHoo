package com.way.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.way.beans.City;
import com.way.common.util.L;
import com.way.yahoo.R;

public class QueryCityAdapter extends BaseAdapter implements Filterable {

	private List<City> mAllCities;
	private List<City> mResultCities;
	private LayoutInflater mInflater;
	private Context mContext;

	public QueryCityAdapter(Context context, List<City> allCities) {
		mContext = context;
		mAllCities = allCities;
		mResultCities = new ArrayList<City>();
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mResultCities == null ? 0 : mResultCities.size();
	}

	@Override
	public City getItem(int position) {
		return mResultCities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		City city = getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.city_query_list_item, parent,
					false);
			holder = new ViewHolder();
			holder.nameTv = (TextView) convertView
					.findViewById(R.id.citylst_name);
			holder.cityTv = (TextView) convertView
					.findViewById(R.id.citylst_city);
			holder.provinceTv = (TextView) convertView
					.findViewById(R.id.citylst_province);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.provinceTv.setText(city.getProvince());
		holder.cityTv.setText(city.getCity());
		holder.nameTv.setText(city.getName());
		return convertView;
	}

	static class ViewHolder {
		TextView provinceTv;
		TextView cityTv;
		TextView nameTv;
	}

	@Override
	public android.widget.Filter getFilter() {
		android.widget.Filter filter = new android.widget.Filter() {
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				mResultCities = (ArrayList<City>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

			protected FilterResults performFiltering(CharSequence s) {
				L.i("liweiping", "performFiltering s = " + s);

				FilterResults results = new FilterResults();
				ArrayList<City> queryResultCities = new ArrayList<City>();
				results.values = queryResultCities;
				results.count = queryResultCities.size();

				if (TextUtils.isEmpty(s))
					return results;

				String str = s.toString();
				for (City allCity : mAllCities) {
					// 匹配全拼、首字母、中文城市名、区号、邮政编码
					if (allCity.getPinyin().startsWith(str)
							|| allCity.getPy().startsWith(str)
							|| allCity.getName().startsWith(str)
							|| allCity.getPhoneCode().startsWith(str)
							|| allCity.getAreaCode().startsWith(str))
						queryResultCities.add(allCity);
				}
				results.values = queryResultCities;
				results.count = queryResultCities.size();
				return results;
			}
		};
		return filter;
	}

}
