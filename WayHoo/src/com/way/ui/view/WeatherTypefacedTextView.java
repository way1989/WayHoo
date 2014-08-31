package com.way.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.way.yahoo.R;

public class WeatherTypefacedTextView extends TextView {
	public WeatherTypefacedTextView(Context context) {
		this(context, null);
	}

	public WeatherTypefacedTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeatherTypefacedTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode())
			return;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.WeatherTypefacedTextView, defStyle, 0);
		String typeface = a
				.getString(R.styleable.WeatherTypefacedTextView_typeface);
		if (!TextUtils.isEmpty(typeface)) {
			Typeface face = Typeface.createFromAsset(getContext().getAssets(),
					typeface);
			if (face != null)
				setTypeface(face);
		}
		a.recycle();
	}
}