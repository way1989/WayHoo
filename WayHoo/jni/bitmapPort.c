
#include <string.h>
#include <jni.h>
#include <malloc.h>
#include <android/bitmap.h>
#include "constants.h"
#include "bitmapPort.h"


static AndroidBitmapInfo imageInfo;
static uint8_t* imageData;


//
int allocateMemory() {
	int ret = 0;
	size_t size = 4*imageInfo.width*imageInfo.height;//color-int
	imageData = (uint8_t*)malloc(size);
	if (imageData == NULL) {
		ret = -1;
	}
	return ret;
}

void freeMemory(){
	if(imageData != NULL){
		free(imageData);
		imageData = NULL;
	}
}

void getUnifiedImageData(jobject bitmap, JNIEnv *env) {
	if (imageData == NULL)
		return;

	switch (imageInfo.format) {
	case ANDROID_BITMAP_FORMAT_RGBA_8888:
		LOGE("get ARGB 8888");
		getARGB8888Data(bitmap, env);
		break;
	case ANDROID_BITMAP_FORMAT_RGBA_4444:
		LOGE("get ARGB 4444");
		getARGB4444Data(bitmap, env);
		break;
	case ANDROID_BITMAP_FORMAT_RGB_565:
		LOGE("get rgb 565");
		getRGB565Data(bitmap, env);
		break;
	case ANDROID_BITMAP_FORMAT_A_8:
		LOGE("get alpha 8");
		getAlpha8Data(bitmap, env);
		break;
	default:
		break;
	}
}

void getARGB8888Data(jobject bitmap, JNIEnv *env)
{
	int ret, row, col;
	void *pixelscolor;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelscolor)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}
	uint8_t *pData = imageData;
	uint32_t colorValue = 0;
	for (row = 0; row < imageInfo.height; row++) {
		uint32_t * line = (uint32_t *) pixelscolor;
		for (col = 0; col < imageInfo.width; col++) {
			colorValue = line[col];

			*pData++ = colorValue & ARGB8888_MASK_BLUE;
			*pData++ = (colorValue & ARGB8888_MASK_GREEN) >> 8;
			*pData++ = (colorValue & ARGB8888_MASK_RED) >> 16;
			*pData++ = (colorValue & ARGB8888_MASK_ALPHA)>>24;
		}
		//stride
		pixelscolor = (uint8_t*)pixelscolor + imageInfo.stride;
	}
	AndroidBitmap_unlockPixels(env, bitmap);
}

void getARGB4444Data(jobject bitmap, JNIEnv *env)
{
	int ret, row, col;
	void *pixelscolor;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelscolor)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}
	uint8_t *pData = imageData;
	for (row = 0; row < imageInfo.height; row++) {
		uint16_t *line = (uint16_t *) pixelscolor;
		for (col = 0; col < imageInfo.width; col++) {
			//*pData++ = (line[col] & ARGB4444_MASK_RED) << 4;
			//*pData++ = (line[col] & ARGB4444_MASK_GREEN) << 4;
			//*pData++ = (line[col] & ARGB4444_MASK_BLUE) << 4;
		}
		pixelscolor = (uint8_t*)pixelscolor + imageInfo.stride;
	}
	AndroidBitmap_unlockPixels(env, bitmap);
}

void getRGB565Data(jobject bitmap, JNIEnv *env)
{
	int ret, row, col;
	void *pixelscolor;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelscolor)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}
	uint8_t *pData = imageData;
	for (row = 0; row < imageInfo.height; row++) {
		uint16_t *line = (uint16_t *) pixelscolor;
		for (col = 0; col < imageInfo.width; col++) {
			*pData++ = ((line[col] & RGB565_MASK_RED) >> 11) << 3;
			*pData++ = ((line[col] & RGB565_MASK_GREEN) >> 5) << 2;
			*pData++ = (line[col] & RGB565_MASK_BLUE) << 3;
		}
		pixelscolor = (uint8_t*)pixelscolor + imageInfo.stride;
	}
	AndroidBitmap_unlockPixels(env, bitmap);
}

void getAlpha8Data(jobject bitmap, JNIEnv *env)
{
	int ret, row, col;
	void *pixelscolor;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelscolor)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}
	uint8_t *pData = imageData;
	for (row = 0; row < imageInfo.height; row++) {
		uint8_t *line = (uint8_t *) pixelscolor;
		for (col = 0; col < imageInfo.width; col++) {
			*pData++ = line[col];
			*pData++ = line[col];
			*pData++ = line[col];
		}
		pixelscolor = (uint8_t*)pixelscolor + imageInfo.stride;
	}
	AndroidBitmap_unlockPixels(env, bitmap);
}

void setUnifiedImageData(jobject bitmap, JNIEnv *env)
{
	switch (imageInfo.format) {
	case ANDROID_BITMAP_FORMAT_RGBA_8888:
		LOGE("set ARGB 8888");
		setARGB8888Data(bitmap, env);
		break;
	case ANDROID_BITMAP_FORMAT_RGBA_4444:
		LOGE("set ARGB 4444");
		setARGB4444Data(bitmap, env);
		break;
	case ANDROID_BITMAP_FORMAT_RGB_565:
		LOGE("set rgb 565");
		setRGB565Data(bitmap, env);
		break;
	case ANDROID_BITMAP_FORMAT_A_8:
		LOGE("set alpha 8");
		setAlpha8Data(bitmap, env);
		break;
	default:
		break;
	}
}

void setARGB8888Data(jobject bitmap, JNIEnv *env)
{
	int ret, row, col;
	uint32_t red, green, blue, alpha;
	void *pixelscolor;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelscolor)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}
	uint8_t *pData = imageData;
	for (row = 0; row < imageInfo.height; row++) {
		uint32_t *line = (uint32_t *) pixelscolor;
		for (col = 0; col < imageInfo.width; col++) {

			blue  = *pData++;
			green = *pData++;
			red   = *pData++;

			alpha = *pData++;

			line[col] = ((alpha<<24) & ARGB8888_MASK_ALPHA)//(line[col] & ARGB8888_MASK_ALPHA)
				 | ((red << 16) & ARGB8888_MASK_RED)
				| ((green << 8) & ARGB8888_MASK_GREEN)
				| (blue & ARGB8888_MASK_BLUE);
		}
		pixelscolor = (uint8_t*)pixelscolor + imageInfo.stride;
	}
	AndroidBitmap_unlockPixels(env, bitmap);
}

void setARGB4444Data(jobject bitmap, JNIEnv *env)
{}

void setRGB565Data(jobject bitmap, JNIEnv *env)
{
	int ret, row, col;
	uint16_t red, green, blue;
	void *pixelscolor;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelscolor)) < 0) {
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
	}
	uint8_t *pData = imageData;
	for (row = 0; row < imageInfo.height; row++) {
		uint16_t *line = (uint16_t *) pixelscolor;
		for (col = 0; col < imageInfo.width; col++) {
			red = *pData++;
			green = *pData++;
			blue = *pData++;
			line[col] = ((red << 8) & RGB565_MASK_RED)
				| ((green << 3) & RGB565_MASK_GREEN)
				| (blue >> 3);
		}
		pixelscolor = (uint8_t*)pixelscolor + imageInfo.stride;
	}
	AndroidBitmap_unlockPixels(env, bitmap);
	LOGE("set RGB 565 Data finished");
}

void setAlpha8Data(jobject bitmap, JNIEnv *env)
{}


//warp
int AndroidBitmap_getInfo_warp(JNIEnv* env, jobject jbitmap)
{
	return AndroidBitmap_getInfo(env, jbitmap, &imageInfo);
}


void blur(){
	gaussBlurProcess(imageData, imageInfo.width, imageInfo.height);
}

void logBitmapPort(){
	LOGI("color image :: width is %d; height is %d; stride is %d; format is %d;flags is%d",
			    imageInfo.width,imageInfo.height,imageInfo.stride,imageInfo.format,imageInfo.flags);
}

void clearColorPort(int color){
	clearColorProcess(imageData, imageInfo.width, imageInfo.height, color);
}

void boxBlurPort(int radius){
	boxBlurProcess(imageData, imageInfo.width, imageInfo.height, imageInfo.stride, radius);
}


void stackBlurPort(int radius){
	stackBlurProcess(imageData, imageInfo.width, imageInfo.height, imageInfo.stride, radius);
}


void oilPaintPort(int radius){
	oilPaintProcess(imageData, imageInfo.width, imageInfo.height, imageInfo.stride, radius);
}


void colorWaterPaintPort(int radius){
	colorWaterPaint(imageData, imageInfo.width, imageInfo.height, imageInfo.stride, radius);
}
