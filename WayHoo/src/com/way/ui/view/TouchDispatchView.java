package com.way.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class TouchDispatchView extends RelativeLayout {
	private boolean isInterceptTouches = true;

	public TouchDispatchView(Context context) {
		super(context);
	}

	public TouchDispatchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TouchDispatchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		return isInterceptTouches;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (!isInterceptTouches)
			return super.onTouchEvent(event);
		int count = getChildCount();
		if (count < 0)
			return isInterceptTouches;
		
		for (int i = 0; i < count; ++i) {
			View childView = getChildAt(i);
			float oldX = event.getX();
			float oldY = event.getY();
			float x = event.getX() - childView.getLeft();
			float y = event.getY() - childView.getTop();
			if (((y >= 0.0F) && (x >= 0.0F))
					|| ((MotionEvent.ACTION_MASK & event.getAction()) != MotionEvent.ACTION_DOWN)){
				//L.i("liweiping", "new touch --> x = " + x +", y = " + y);
				event.setLocation(x, y);
			}else{
				//L.i("liweiping", "old touch --> oldX = " + oldX +", oldY = " + oldY);
				event.setLocation(oldX, oldY);
			}
			childView.dispatchTouchEvent(event);
		}
		return isInterceptTouches;
	}

	public void setInterceptTouches(boolean isInterceptTouches) {
		this.isInterceptTouches = isInterceptTouches;
	}
}
