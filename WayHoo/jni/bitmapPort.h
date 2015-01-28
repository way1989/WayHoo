
#include <string.h>
#include <jni.h>
#include <malloc.h>
#include <android/bitmap.h>
#include "constants.h"


#ifndef _BITMAP_PORT_H_
#define _BITMAP_PORT_H_


//declare
int allocateMemory();

void getUnifiedImageData(jobject bitmap, JNIEnv *env);

void getARGB8888Data(jobject bitmap, JNIEnv *env);

void getARGB4444Data(jobject bitmap, JNIEnv *env);

void getRGB565Data(jobject bitmap, JNIEnv *env);

void getAlpha8Data(jobject bitmap, JNIEnv *env);

void setUnifiedImageData(jobject bitmap, JNIEnv *env);

void setARGB8888Data(jobject bitmap, JNIEnv *env);

void setARGB4444Data(jobject bitmap, JNIEnv *env);

void setRGB565Data(jobject bitmap, JNIEnv *env);

void setAlpha8Data(jobject bitmap, JNIEnv *env);


void blurPort();
void logBitmapPort();

void clearColorPort(int color);
void boxBlurPort(int radius);

void stackBlurPort(int radius);

#endif
