#ifndef _IMAGE_PROCESS_H_
#define _IMAGE_PROCESS_H_

void gaussBlurProcess( uint8_t *colorImg, int width, int height );
void clearColorProcess(uint8_t *image, int width, int height, int color);
void boxBlurProcess(uint8_t *image, int width, int height, int stride, int radius);

void stackBlurProcess(uint8_t *image, int width, int height, int stride, int radius);
void oilPaintProcess(uint8_t *image, int width, int height, int stride, int radius);
void colorWaterPaint( int * pSrcImage, int width, int heiht, int stride, int radius );


#endif
