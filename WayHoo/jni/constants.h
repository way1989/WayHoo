#ifndef _CONSTANTS_H_
#define _CONSTANTS_H_

#include <jni.h>
#include <android/log.h>
#include <stdlib.h>
#include "com_way_util_blur_jni_FrostedGlassUtil.h"

#define RGB565_MASK_RED		0xF800   
#define RGB565_MASK_GREEN	0x07E0   
#define RGB565_MASK_BLUE	0x001F  

#define ARGB8888_MASK_ALPHA	0xFF000000
#define ARGB8888_MASK_RED	0x00FF0000
#define ARGB8888_MASK_GREEN	0x0000FF00
#define ARGB8888_MASK_BLUE	0x000000FF

#define ARGB4444_MASK_RED	0x0F00;
#define ARGB4444_MASK_GREEN	0x00F0;
#define ARGB4444_MASK_BLUE	0x000F;


#define ARGB8888_2_COLOR(a,r,g,b) (((a)<<24)|((r)<<16)|((g)<<8)|(b))

#define LOG_TAG "FrostedGlass"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)  
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)  


#endif
