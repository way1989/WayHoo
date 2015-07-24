package com.way.ui.view;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.way.weather.plugin.bean.Index;
import com.way.weather.plugin.bean.IndexDetail;
import com.way.weather.plugin.bean.WeatherInfo;
import com.way.yahoo.R;

public class WeatherIndexView extends WeatherBaseView {
	// 指数
	View windIndexView;
	View uaIndexView;// 紫外线指数
	View clotheIndexView;
	View comfortIndexView;
	View carIndexView;
	View insolationIndexView;// 晾晒
	View sportIndexView;
	View travelIndexDivider;
	View travelIndexView;
	// 图标
	ImageView windIndexIcon;
	ImageView uaIndexIcon;
	ImageView clotheIndexIcon;
	ImageView comfortIndexIcon;
	ImageView carIndexIcon;
	ImageView insolationIndexIcon;
	ImageView sportIndexIcon;
	ImageView travelIndexIcon;
	// 标题
	TextView windIndexTitle;
	TextView uaIndexTitle;
	TextView clotheIndexTitle;
	TextView comfortIndexTitle;
	TextView carIndexTitle;
	TextView insolationIndexTitle;
	TextView sportIndexTitle;
	TextView travelIndexTitle;
	TextView windIndexDesc;
	TextView uandexDesc;
	TextView clotheIndexDesc;
	TextView comfortIndexDesc;
	TextView carIndexDesc;
	TextView insolationIndexDesc;
	TextView sportIndexDesc;
	TextView travelIndexDesc;
	// 详细
	TextView windIndexDetail;
	TextView uandexDetail;
	TextView clotheIndexDetail;
	TextView comfortIndexDetail;
	TextView carIndexDetail;
	TextView insolationIndexDetail;
	TextView sportIndexDetail;
	TextView travelIndexDetail;

	public WeatherIndexView(Context c) {
		this(c, null);
	}

	public WeatherIndexView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeatherIndexView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 指数
		windIndexView = findViewById(R.id.wind_index);
		uaIndexView = findViewById(R.id.ua_index);
		clotheIndexView = findViewById(R.id.clothe_index);
		comfortIndexView = findViewById(R.id.comfort_index);
		carIndexView = findViewById(R.id.car_index);
		insolationIndexView = findViewById(R.id.insolation_index);
		sportIndexView = findViewById(R.id.sport_index);
		travelIndexDivider = findViewById(R.id.travel_divider);
		travelIndexView = findViewById(R.id.travel_index);
		// 图标
		windIndexIcon = (ImageView) windIndexView
				.findViewById(R.id.index_icon_iv);
		uaIndexIcon = (ImageView) uaIndexView.findViewById(R.id.index_icon_iv);
		clotheIndexIcon = (ImageView) clotheIndexView
				.findViewById(R.id.index_icon_iv);
		comfortIndexIcon = (ImageView) comfortIndexView
				.findViewById(R.id.index_icon_iv);
		carIndexIcon = (ImageView) carIndexView
				.findViewById(R.id.index_icon_iv);
		insolationIndexIcon = (ImageView) insolationIndexView
				.findViewById(R.id.index_icon_iv);
		sportIndexIcon = (ImageView) sportIndexView
				.findViewById(R.id.index_icon_iv);
		travelIndexIcon = (ImageView) travelIndexView
				.findViewById(R.id.index_icon_iv);
		// 标题
		windIndexTitle = (TextView) windIndexView
				.findViewById(R.id.index_title_tv);
		uaIndexTitle = (TextView) uaIndexView.findViewById(R.id.index_title_tv);
		clotheIndexTitle = (TextView) clotheIndexView
				.findViewById(R.id.index_title_tv);
		comfortIndexTitle = (TextView) comfortIndexView
				.findViewById(R.id.index_title_tv);
		carIndexTitle = (TextView) carIndexView
				.findViewById(R.id.index_title_tv);
		insolationIndexTitle = (TextView) insolationIndexView
				.findViewById(R.id.index_title_tv);
		sportIndexTitle = (TextView) sportIndexView
				.findViewById(R.id.index_title_tv);
		travelIndexTitle = (TextView) travelIndexView
				.findViewById(R.id.index_title_tv);

		// 描述
		windIndexDesc = (TextView) windIndexView
				.findViewById(R.id.index_desc_tv);
		uandexDesc = (TextView) uaIndexView.findViewById(R.id.index_desc_tv);
		clotheIndexDesc = (TextView) clotheIndexView
				.findViewById(R.id.index_desc_tv);
		comfortIndexDesc = (TextView) comfortIndexView
				.findViewById(R.id.index_desc_tv);
		carIndexDesc = (TextView) carIndexView.findViewById(R.id.index_desc_tv);
		insolationIndexDesc = (TextView) insolationIndexView
				.findViewById(R.id.index_desc_tv);
		sportIndexDesc = (TextView) sportIndexView
				.findViewById(R.id.index_desc_tv);
		travelIndexDesc = (TextView) travelIndexView
				.findViewById(R.id.index_desc_tv);
		// 详细
		windIndexDetail = (TextView) windIndexView
				.findViewById(R.id.index_detail_tv);
		uandexDetail = (TextView) uaIndexView
				.findViewById(R.id.index_detail_tv);
		clotheIndexDetail = (TextView) clotheIndexView
				.findViewById(R.id.index_detail_tv);
		comfortIndexDetail = (TextView) comfortIndexView
				.findViewById(R.id.index_detail_tv);
		carIndexDetail = (TextView) carIndexView
				.findViewById(R.id.index_detail_tv);
		insolationIndexDetail = (TextView) insolationIndexView
				.findViewById(R.id.index_detail_tv);
		sportIndexDetail = (TextView) sportIndexView
				.findViewById(R.id.index_detail_tv);
		travelIndexDetail = (TextView) travelIndexView
				.findViewById(R.id.index_detail_tv);
	}

	public void setWeatherInfo(Index index) {
		if (index == null || index.getIndex().get(0) == null)
			return;
		List<IndexDetail> lists = index.getIndex();
		if (TextUtils.isEmpty(lists.get(7).getDesc())) {
			travelIndexDivider.setVisibility(View.GONE);
			travelIndexView.setVisibility(View.GONE);
		} else {
			travelIndexDivider.setVisibility(View.VISIBLE);
			travelIndexView.setVisibility(View.VISIBLE);
		}
		// 图标
		windIndexIcon.setImageResource(R.drawable.ic_lifeindex_wind);
		uaIndexIcon.setImageResource(R.drawable.ic_lifeindex_ultravioletrays);
		clotheIndexIcon.setImageResource(R.drawable.ic_lifeindex_clothes);
		comfortIndexIcon.setImageResource(R.drawable.ic_lifeindex_cold);
		carIndexIcon.setImageResource(R.drawable.ic_lifeindex_carwash);
		insolationIndexIcon.setImageResource(R.drawable.ic_lifeindex_makeup);
		sportIndexIcon.setImageResource(R.drawable.ic_lifeindex_sport);
		travelIndexIcon.setImageResource(R.drawable.ic_lifeindex_tour);

		// 标题
		windIndexTitle.setText(lists.get(0).getTitle());
		uaIndexTitle.setText(lists.get(1).getTitle());
		clotheIndexTitle.setText(lists.get(2).getTitle());
		comfortIndexTitle.setText(lists.get(3).getTitle());
		carIndexTitle.setText(lists.get(4).getTitle());
		insolationIndexTitle.setText(lists.get(5).getTitle());
		sportIndexTitle.setText(lists.get(6).getTitle());
		travelIndexTitle.setText(lists.get(7).getTitle());
		// 描述
		windIndexDesc.setText(lists.get(0).getDesc());
		uandexDesc.setText(lists.get(1).getDesc());
		clotheIndexDesc.setText(lists.get(2).getDesc());
		comfortIndexDesc.setText(lists.get(3).getDesc());
		carIndexDesc.setText(lists.get(4).getDesc());
		insolationIndexDesc.setText(lists.get(5).getDesc());
		sportIndexDesc.setText(lists.get(6).getDesc());
		travelIndexDesc.setText(lists.get(7).getDesc());

		// 详细
		windIndexDetail.setText(lists.get(0).getDetail());
		uandexDetail.setText(lists.get(1).getDetail());
		clotheIndexDetail.setText(lists.get(2).getDetail());
		comfortIndexDetail.setText(lists.get(3).getDetail());
		carIndexDetail.setText(lists.get(4).getDetail());
		insolationIndexDetail.setText(lists.get(5).getDetail());
		sportIndexDetail.setText(lists.get(6).getDetail());
		travelIndexDetail.setText(lists.get(7).getDetail());
	}

	@Override
	public void setWeatherInfo(WeatherInfo weatherInfo) {
		setWeatherInfo(weatherInfo.getIndex());
	}
}
