/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>
#include <malloc.h>
#include <android/bitmap.h>
#include "constants.h"
#include "bitmapPort.h"
//#include "ImageProcessor.h"
//#include "Lomo.h"

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */





JNIEXPORT void JNICALL Java_com_way_util_blur_jni_FrostedGlassUtil_boxBlur(JNIEnv *env, jclass obj, jobject srcBitmap, jint radius)
{
  	void* pixelscolor;
	int ret, row, col;
	if ((ret = AndroidBitmap_getInfo_warp(env, srcBitmap)) < 0) {
		LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
		return;
	}

	logBitmapPort();

	if (-1 == allocateMemory()) {
		LOGE("allocate memory failed !");
		return;
	}
	getUnifiedImageData(srcBitmap, env);

	LOGE("get unified image data ");

	boxBlurPort(radius);

	LOGE("conver image data");

	setUnifiedImageData(srcBitmap, env);

	LOGE("set unified image data");

	freeMemory();
}


JNIEXPORT void JNICALL Java_com_way_util_blur_jni_FrostedGlassUtil_stackBlur(JNIEnv *env, jclass obj, jobject srcBitmap, jint radius)
{
  	void* pixelscolor;
	int ret, row, col;
	if ((ret = AndroidBitmap_getInfo_warp(env, srcBitmap)) < 0) {
		LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
		return;
	}

	logBitmapPort();

	if (-1 == allocateMemory()) {
		LOGE("allocate memory failed !");
		return;
	}
	getUnifiedImageData(srcBitmap, env);

	LOGE("get unified image data ");

	stackBlurPort(radius);

	LOGE("conver image data");

	setUnifiedImageData(srcBitmap, env);

	LOGE("set unified image data");

	freeMemory();
}


JNIEXPORT void JNICALL Java_com_way_util_blur_jni_FrostedGlassUtil_oilPaint(JNIEnv *env, jclass obj, jobject srcBitmap, jint radius)
{
  	void* pixelscolor;
	int ret, row, col;
	if ((ret = AndroidBitmap_getInfo_warp(env, srcBitmap)) < 0) {
		LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
		return;
	}

	logBitmapPort();

	if (-1 == allocateMemory()) {
		LOGE("allocate memory failed !");
		return;
	}
	getUnifiedImageData(srcBitmap, env);

	LOGE("get unified image data ");

	oilPaintPort(radius);

	LOGE("conver image data");

	setUnifiedImageData(srcBitmap, env);

	LOGE("set unified image data");

	freeMemory();
}

JNIEXPORT void JNICALL Java_com_way_util_blur_jni_FrostedGlassUtil_colorWaterPaint(JNIEnv *env, jclass obj, jobject srcBitmap, jint radius)
{
  	void* pixelscolor;
	int ret, row, col;
	if ((ret = AndroidBitmap_getInfo_warp(env, srcBitmap)) < 0) {
		LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
		return;
	}

	logBitmapPort();

	if (-1 == allocateMemory()) {
		LOGE("allocate memory failed !");
		return;
	}
	getUnifiedImageData(srcBitmap, env);

	LOGE("get unified image data ");

	colorWaterPaintPort(radius);

	LOGE("conver image data");

	setUnifiedImageData(srcBitmap, env);

	LOGE("set unified image data");

	freeMemory();
}
