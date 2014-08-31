package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.R;

public class RefreshImageView extends ImageView {
	private int[] images = { R.drawable.sun_00000, R.drawable.sun_00001,
			R.drawable.sun_00002, R.drawable.sun_00003, R.drawable.sun_00004,
			R.drawable.sun_00005, R.drawable.sun_00006, R.drawable.sun_00007,
			R.drawable.sun_00008, R.drawable.sun_00009, R.drawable.sun_00010,
			R.drawable.sun_00011, R.drawable.sun_00012, R.drawable.sun_00013,
			R.drawable.sun_00014, R.drawable.sun_00015, R.drawable.sun_00016,
			R.drawable.sun_00017, R.drawable.sun_00018, R.drawable.sun_00019,
			R.drawable.sun_00020, R.drawable.sun_00021, R.drawable.sun_00022,
			R.drawable.sun_00023, R.drawable.sun_00024, R.drawable.sun_00025,
			R.drawable.sun_00026, R.drawable.sun_00027 };
	private int mMax;

	public RefreshImageView(Context context) {
		this(context, null, 0);
	}

	public RefreshImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RefreshImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.i("lwp", "drawable = " + images[0]);
		// this.setImageResource(images[0]);
		mMax = images.length - 1;
	}

	public synchronized int getMax() {
		return mMax;
	}

	public synchronized void setProgress(float progress) {
		if (progress < 0.0f)
			progress = 0.0f;

		if (progress > 1.0f)
			progress = 1.0f;

		this.setImageResource(images[Math.round(progress * mMax)]);
	}
}
