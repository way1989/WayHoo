package com.way.util.blur.jni;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by way on 14-6-17.
 */
public class BitmapUtils {
	public static Bitmap drawViewToBitmap(View view, int width, int height,
			int scaleRatio) {
		return drawViewToBitmap(view, width, height, 0f, 0f, scaleRatio);
	}

	public static Bitmap drawViewToBitmap(View view, int width, int height,
			float translateX, float translateY, int scaleRatio) {
		float scale = 1f / scaleRatio;
		int bmpWidth = (int) (width * scale - translateX / scaleRatio);
		int bmpHeight = (int) (height * scale - translateY / scaleRatio);
		Bitmap dest = Bitmap.createBitmap(bmpWidth, bmpHeight,
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(dest);
		c.translate(-translateX / scaleRatio, -translateY / scaleRatio);
		if (scaleRatio > 1) {
			c.scale(scale, scale);
		}
		view.draw(c);
		return dest;
	}
}
