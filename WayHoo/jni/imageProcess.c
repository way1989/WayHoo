#include "stdlib.h"
#include "stdio.h"
#include "math.h"
#include "imageProcess.h"
#include "constants.h"


static void  iblurV(int *pDataSave, int * pData, int *divTable, int width, int height, int radius);
static void  iblurH(int *pDataSave, int * pData, int *divTable, int width, int height, int radius);
static void iStackBlur(int *pData, int width, int height, int radius);
static void iQSort(uint8_t a[],int low,int high);
static void iColorWaterFilter( int * pSrcImage, int * pDstImage, int width, int height  );


void gaussBlurProcess(uint8_t *image, int width, int height)
{

	// 高斯矩阵
	int gauss[16] = { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

	int pixR = 0;
	int pixG = 0;
	int pixB = 0;

	int pixColor = 0;

	int newR = 0;
	int newG = 0;
	int newB = 0;

	int idx = 0;
	int * pixels = 0;

	int * pData = (int *)image;

	int value = 0;

	pixels = (int *)malloc(width * height*sizeof(int));

	if(pixels == NULL){
		return;
	}

	memset((unsigned char *)pixels, 0, sizeof(int)*width * height);


	int i = 0;
	int j = 0;

	int v = 0;
	int h = height - 1;

	int m;
	int n;

	for (j = 1; j < h; j++){

		v = width - 1;
		for (i = 1; i < v; i++){
			idx = 0;

			//initial
			newR = 0;
			newG = 0;
			newB = 0;

			//add
			for (m = -1; m <= 1; m++) {
				for (n = -1; n <= 1; n++) {
					pixColor = pData[(j + m) * width + i + n];

					pixR = (pixColor>>16)&0xFF;
					pixG = (pixColor>>8)&0xFF;
					pixB = (pixColor)&0xFF;

					value = gauss[idx];

					newR = newR + pixR * value;
					newG = newG + pixG * value;
					newB = newB + pixB * value;
					idx++;
				}
			}

			//divide
			newR /= 16;
			newG /= 16;
			newB /= 16;

			//newR = Math.min(255, Math.max(0, newR));
			//newG = Math.min(255, Math.max(0, newG));
			//newB = Math.min(255, Math.max(0, newB));

			//setColor
			pixels[j * width + i] = 0xFF000000|(newR<<16)|(newG<<8)|(newB);//Color.argb(255, newR, newG, newB);
		}
	}

	//copy
	memcpy(image, pixels, width*height);

}



void clearColorProcess(uint8_t *image, int width, int height, int color)
{
	memset(image, 0xFF, width*height*4);

	//image[0] = 0;

	int * pData = (int *)image;

	int i = 0;
	int j = 0;

	for (j = 0; j < height; j++){
		for (i = 0; i < width; i++){
			pData[j * width + i] = color;//Color.argb(255, newR, newG, newB);
		}
	}
}



void  blurH(uint8_t * image, int width, int height, int radius)
{
    int i = 0;
    int j = 0;

    uint32_t color;

    int32_t addR;
    int32_t addG;
    int32_t addB;
    int32_t addA;


    //copy data
    uint8_t * pDataSave = NULL;

    int * pData = (int *)image;

    int widthMinus1 = 0;

    int * divTable = NULL;

    //save length
    j = 2*radius + 1;

    divTable = (int *)malloc(256*j*sizeof(int));
    if(divTable == NULL){
    	return;
    }

    //clear data
    memset(divTable, 0, 256*j*sizeof(int));


    pDataSave = (uint8_t *)malloc(width*height*sizeof(int));
    if(pDataSave == NULL){
    	return;
    }

    //clear data
    memset(pDataSave, 0, width*height*sizeof(int));


    j = 256*(2*radius + 1);

    //change div
    int inIndex = 0;

    widthMinus1 = (2*radius + 1);
    if(j < (1<<15)){
		//GFIXED
		inIndex = (1<<15)/widthMinus1;

		//计算灰度均值表
		for ( i = 0; i < j; i++ ){
			//divide[i] = i/tableSize;
			divTable[i] = (i*inIndex)>>15;
		}
    }else{
		for ( i = 0; i < j; i++ ){
			divTable[i] = i/widthMinus1;
		}
    }


    //set data
    widthMinus1 = width - 1;

    for (j = 0; j < height; j++ )
    {
        int outIndex = j;
        int Plx = 0; //记录像素的灰度累加值
        addR = 0;
        addG = 0;
        addB = 0;
        addA = 0;

        for ( i = - radius; i <= radius; i++ )
        {
            i = (i < 0)?1:0;
            i = (i > width - 1)?i:(width - 1);

            color = pData[inIndex+i];

           // Plx += pData[inIndex+i];

            addA += ((color&ARGB8888_MASK_ALPHA)>>24);
            addR +=  (color&ARGB8888_MASK_RED)>>16;
            addG +=  (color&ARGB8888_MASK_GREEN)>>8;
            addB +=  (color&ARGB8888_MASK_BLUE);
        }

        for ( i = 0; i < width; i++ )
        {
            int i1 = i + radius + 1;
            if ( i1 > widthMinus1 ){
                i1 = widthMinus1;
            }
            int i2 = i - radius;
            if ( i2 < 0 ){
                i2 = 0;
            }
            int cTail = pData[inIndex+i1];
            int cHead = pData[inIndex+i2];

            //Plx += (r1 - r2);
            addA += ((cTail&ARGB8888_MASK_ALPHA)>>24) - ((cHead&ARGB8888_MASK_ALPHA)>>24);
            addR += ((cTail&ARGB8888_MASK_RED)>>16)   - ((cHead&ARGB8888_MASK_RED)>>16);
            addG += ((cTail&ARGB8888_MASK_GREEN)>>8)   - ((cHead&ARGB8888_MASK_GREEN)>>8);
            addB +=  (cTail&ARGB8888_MASK_BLUE)       -  (cHead&ARGB8888_MASK_BLUE);

            //colorAdd/(radius*1+1)
            color = ARGB8888_2_COLOR((divTable[addA]), divTable[addR], divTable[addG], divTable[addB]);

            pDataSave[inIndex + i] = color;
        }

        //next line
        inIndex += width;
    }


    memcpy(pData, pDataSave, width*height*sizeof(int));

    if(pDataSave != NULL){
    	free(pDataSave);
    	pDataSave = NULL;
    }

    if(divTable != NULL){
    	free(divTable);
    	divTable = NULL;
    }

}


void boxBlurProcess(uint8_t *image, int width, int height, int stride, int radius){
	int * divTable = NULL;

	int * pDataSave;

	int * pData = (int *)image;

	int len;


	int widthMinus1 = (2*radius + 1);

	//save length
	len = 2*radius + 1;

	//LOGI("00");



	divTable = (int *)malloc(256*len*sizeof(int));
	if(divTable == NULL){
		return;
	}

	//clear data
	memset(divTable, 0, 256*len*sizeof(int));


	len = 256*(2*radius + 1);

    //change div
    int inIndex = 0;

    int i;

    //LOGI("0");


    /*
    if(len < (1<<15)){
		//GFIXED
		inIndex = (1<<15)/widthMinus1;

		//计算灰度均值表
		for ( i = 0; i < len; i++ ){
			//divide[i] = i/tableSize;
			divTable[i] = (i*inIndex)>>15;
		}
    }else
    */
    {
		for ( i = 0; i < len; i++ ){
			divTable[i] = i/widthMinus1;
		}
    }

    //LOGI("2");

    pDataSave = (int *)malloc(width*height*sizeof(int));
    if(pDataSave == NULL){
    	return;
    }


    iblurH(pDataSave, pData, divTable, width, height, radius);

    //LOGI("3");

    iblurV(pData, pDataSave, divTable, width, height, radius);

    //memcpy(pData, pDataSave, width*height*sizeof(int));

    //memset(pData, 0xFF, width*height*sizeof(int));


    //clearColorProcess((uint8_t *)image, width, height, 0xFFFFFF00);

    //LOGI("4");

    if(divTable != NULL){
    	free(divTable);
    	divTable = NULL;
    }


    if(pDataSave != NULL){
    	free(pDataSave);
    	pDataSave = NULL;
    }
}

static void iblurH(int *pDataSave, int * pData, int *divTable, int width, int height, int radius)
{
    int i = 0;
    int j = 0;

    int color;

    int addR;
    int addG;
    int addB;
    int addA;

    int R;
    int G;
    int B;
    int A;

    int widthMinus1 = 0;

    int lineHead;

    //set data
    widthMinus1 = width - 1;

    lineHead = 0;

    int x;

    for (j = 0; j < height; j++ )
    {
    	//set zero
    	addA = 0;
        addR = 0;
        addG = 0;
        addB = 0;

        //add color
        for ( i = - (radius + 1); i < radius; i++ )
        {
            x = (i < 0)?0:i;
            x = (x > (width - 1))?(width - 1):x;

            color = pData[lineHead + x];

           // Plx += pData[inIndex+i];

            A = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
            R = (color&ARGB8888_MASK_RED)>>16&0xFF;
            G = (color&ARGB8888_MASK_GREEN)>>8&0xFF;
            B = (color&ARGB8888_MASK_BLUE)&0xFF;

            addA += A;
            addR += R;
            addG += G;
            addB += B;
        }


        for ( i = 0; i < width; i++ )
        {
            x = i + radius;
            if ( x > widthMinus1 ){
                x = widthMinus1;
            }

            color = pData[lineHead + x];

            A = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
            R =  (color&ARGB8888_MASK_RED)>>16&0xFF;
            G =  (color&ARGB8888_MASK_GREEN)>>8&0xFF;
            B =  (color&ARGB8888_MASK_BLUE)&0xFF;

            addA += A;
			addR += R;
			addG += G;
			addB += B;


            x = i - (radius + 1);
            if ( x < 0 ){
                x = 0;
            }

			color = pData[lineHead + x];

            A = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
            R =  (color&ARGB8888_MASK_RED)>>16&0xFF;
            G =  (color&ARGB8888_MASK_GREEN)>>8&0xFF;
            B =  (color&ARGB8888_MASK_BLUE)&0xFF;


            addA -= A;
			addR -= R;
			addG -= G;
			addB -= B;

			//div

			A = divTable[addA];//addA/(radius*2+1);
			R = divTable[addR];//addR/(radius*2+1);
			G = divTable[addG];//addG/(radius*2+1);
			B = divTable[addB];//addB/(radius*2+1);

            color = ARGB8888_2_COLOR(A,R,G,B);//(((A))<<24) | (((R))<<16) | (((G))<<8)|(B);

            pDataSave[lineHead + i] = color;
        }

        //next line
        lineHead += width;
    }
}


static void iblurV(int *pDataSave, int * pData, int *divTable, int width, int height, int radius)
{
	int i = 0;
	int j = 0;

	int color;

	int addR;
	int addG;
	int addB;
	int addA;

	int R;
	int G;
	int B;
	int A;

	int heightMinus1 = 0;

	int lineHead;

	//set data
	heightMinus1 = height - 1;

	lineHead = 0;


	int y;

	char name[100];

	for (i = 0; i < width; i++ )
	{
		//set zero
		addA = 0;
		addR = 0;
		addG = 0;
		addB = 0;

		//add color
		for ( j = - (radius + 1); j < radius; j++ )
		{
			y = (j < 0)?0:j;
			y = (y > (height - 1))?(height - 1):y;

			color = pData[lineHead + y*width];

		   // Plx += pData[inIndex+i];

			A = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
			R = (color&ARGB8888_MASK_RED)>>16&0xFF;
			G = (color&ARGB8888_MASK_GREEN)>>8&0xFF;
			B = (color&ARGB8888_MASK_BLUE)&0xFF;

			addA += A;
			addR += R;
			addG += G;
			addB += B;
		}


		for ( j = 0; j < height; j++ )
		{
			y = j + radius;
			if ( y > heightMinus1 ){
				y = heightMinus1;
			}

			color = pData[lineHead + y*width];

			A = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
			R =  (color&ARGB8888_MASK_RED)>>16&0xFF;
			G =  (color&ARGB8888_MASK_GREEN)>>8&0xFF;
			B =  (color&ARGB8888_MASK_BLUE)&0xFF;

			addA += A;
			addR += R;
			addG += G;
			addB += B;


			y = j - (radius + 1);
			if ( y < 0 ){
				y = 0;
			}

			color = pData[lineHead + y*width];

			sprintf(name,"offset=%d,w=%d,h=%d,total=%d",lineHead + y*width,width, height,width*height);

			if(lineHead + y*width<0||lineHead + y*width>=height*width){
				//LOGI(name);
			}

			A = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
			R =  (color&ARGB8888_MASK_RED)>>16&0xFF;
			G =  (color&ARGB8888_MASK_GREEN)>>8&0xFF;
			B =  (color&ARGB8888_MASK_BLUE)&0xFF;


			addA -= A;
			addR -= R;
			addG -= G;
			addB -= B;

			//div

			A = divTable[addA];//addA/(radius*2+1);
			R = divTable[addR];//addR/(radius*2+1);
			G = divTable[addG];//addG/(radius*2+1);
			B = divTable[addB];//addB/(radius*2+1);

			color = ARGB8888_2_COLOR(A,R,G,B);//(((A))<<24) | (((R))<<16) | (((G))<<8)|(B);

			pDataSave[lineHead + j*width] = color;
		}

		//next line
		lineHead ++;
	}
}


//////////////////////////////////////////////////////////////////////////////////////////////

void stackBlurProcess(uint8_t *image, int width, int height, int stride, int radius){
	int * pData = (int *)image;

	int len;
    //LOGI("2");
    //LOGI("3");

    iStackBlur(pData, width, height, radius);


    //LOGI("4");
}

int Math_max(int x, int y){
	return (x>y)?x:y;
}

int Math_min(int x, int y){
	return (x<y)?x:y;
}

int Math_abs(int x){
	return (x>0)?x:(-x);
}

static void iStackBlur(int *pData, int width, int height, int radius){

    if (radius < 1) {
        return;
    }

    int w = width;
    int h = height;

    int* pix = pData;

    //Log.e("pix", w + " " + h + " " + pix.length);

    //bitmap.getPixels(pix, 0, w, 0, 0, w, h);

    int wm = w - 1;
    int hm = h - 1;
    int wh = w * h;
    int div = radius + radius + 1;


    int *r = (int*)malloc(wh*sizeof(int));
    int *g = (int*)malloc(wh*sizeof(int));
    int *b = (int*)malloc(wh*sizeof(int));


    int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
    int * vmin = (int*)malloc(Math_max(w, h)*sizeof(int));

    int divsum = (div + 1) >> 1;
    divsum *= divsum;
    int *dv = (int*)malloc(256 * divsum*sizeof(int));
    for (i = 0; i < 256 * divsum; i++) {
        dv[i] = (i / divsum);
    }

    yw = yi = 0;

    //int[][] stack = new int[div][3];
    int **stack = (int **)malloc(div*sizeof(int*));
    for (x = 0; x < div; ++x){
    	stack[x] = (int *)malloc(3*sizeof(int));
    }


    int stackpointer;
    int stackstart;
    int* sir;

    int rbs;
    int r1 = radius + 1;
    int routsum, goutsum, boutsum;
    int rinsum, ginsum, binsum;

    //char name[128];
    //sprintf(name,"start%d", radius);

    //LOGI(name);

    for (y = 0; y < h; y++) {
        rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;

        for (i = -radius; i <= radius; i++) {
            p = pix[yi + Math_min(wm, Math_max(i, 0))];
            sir = stack[i + radius];


            sir[0] = (p & 0xff0000) >> 16;
            sir[1] = (p & 0x00ff00) >> 8;
            sir[2] = (p & 0x0000ff);


            rbs = r1 - Math_abs(i);
            rsum += sir[0] * rbs;
            gsum += sir[1] * rbs;
            bsum += sir[2] * rbs;
            if (i > 0) {
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
            } else {
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
            }

        }


        stackpointer = radius;


        for (x = 0; x < w; x++) {

            r[yi] = dv[rsum];
            g[yi] = dv[gsum];
            b[yi] = dv[bsum];

            rsum -= routsum;
            gsum -= goutsum;
            bsum -= boutsum;

            stackstart = stackpointer - radius + div;
            sir = stack[stackstart % div];

            routsum -= sir[0];
            goutsum -= sir[1];
            boutsum -= sir[2];

            if (y == 0) {
                vmin[x] = Math_min(x + radius + 1, wm);
            }
            p = pix[yw + vmin[x]];

            sir[0] = (p & 0xff0000) >> 16;
            sir[1] = (p & 0x00ff00) >> 8;
            sir[2] = (p & 0x0000ff);

            rinsum += sir[0];
            ginsum += sir[1];
            binsum += sir[2];

            rsum += rinsum;
            gsum += ginsum;
            bsum += binsum;

            stackpointer = (stackpointer + 1) % div;
            sir = stack[(stackpointer) % div];

            routsum += sir[0];
            goutsum += sir[1];
            boutsum += sir[2];

            rinsum -= sir[0];
            ginsum -= sir[1];
            binsum -= sir[2];

            yi++;
        }

        yw += w;
    }

    //LOGI("Next");



    for (x = 0; x < w; x++) {
        rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
        yp = -radius * w;
        for (i = -radius; i <= radius; i++) {
            yi = Math_max(0, yp) + x;

            sir = stack[i + radius];

            sir[0] = r[yi];
            sir[1] = g[yi];
            sir[2] = b[yi];

            rbs = r1 - Math_abs(i);

            rsum += r[yi] * rbs;
            gsum += g[yi] * rbs;
            bsum += b[yi] * rbs;

            if (i > 0) {
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
            } else {
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
            }

            if (i < hm) {
                yp += w;
            }
        }
        yi = x;
        stackpointer = radius;
        for (y = 0; y < h; y++) {
            // Preserve alpha channel: ( 0xff000000 & pix[yi] )
        	//force to set alpha 255 modified by chao.lc
            pix[yi] = ( 0xff000000 /*& pix[yi]*/ ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

            rsum -= routsum;
            gsum -= goutsum;
            bsum -= boutsum;

            stackstart = stackpointer - radius + div;
            sir = stack[stackstart % div];

            routsum -= sir[0];
            goutsum -= sir[1];
            boutsum -= sir[2];

            if (x == 0) {
                vmin[y] = Math_min(y + r1, hm) * w;
            }
            p = x + vmin[y];

            sir[0] = r[p];
            sir[1] = g[p];
            sir[2] = b[p];

            rinsum += sir[0];
            ginsum += sir[1];
            binsum += sir[2];

            rsum += rinsum;
            gsum += ginsum;
            bsum += binsum;

            stackpointer = (stackpointer + 1) % div;
            sir = stack[stackpointer];

            routsum += sir[0];
            goutsum += sir[1];
            boutsum += sir[2];

            rinsum -= sir[0];
            ginsum -= sir[1];
            binsum -= sir[2];

            yi += w;
        }
    }



    if(vmin != NULL){
    	free(vmin);
    	vmin = NULL;
    }


    if(dv != NULL){
    	free(dv);
    	dv = NULL;
    }

    for (x = 0; x < div; ++x){
        if(stack[x] != NULL){
        	free(stack[x]);
        	stack[x] = NULL;
        }
    }
    if(stack != NULL){
    	free( stack );
    }

    if(r!=NULL){
    	free(r);
    	r = NULL;
    }

    if(g != NULL){
    	free(g);
    	g = NULL;
    }

    if(b != NULL){
    	free(b);
    	b = NULL;
    }
}



static int iOilPaint( int * pDstData, int * pSrcData, int width, int height, int radius)
{
	int dx;
	int dy;

	int i = 0;
	int j = 0;

	int r = 0;
	int g = 0;
	int b = 0;

	int rTemp = 0;
	int gTemp = 0;
	int bTemp = 0;

	int rIndex = 0;
	int gIndex = 0;
	int bIndex = 0;

	int histo[256+256+256];
	int * histoR = histo;
	int * histoG = histo + 256;
	int * histoB = histo + 256 + 256;

	int color;
	int N = 2;
	int offset = width - (2*N+1);

	int * pSrcTempData;

	if( 2*N+1 > width || 2*N + 1 > height )
	{
		return -1;
	}

	//copy pix
	memcpy( pDstData, pSrcData, sizeof(int)*width*height);

	int *pDstTempData = pDstData + N*width;
	for( j = N; j < height - N; j++ )
	{
		for( i = 0; i < width; i++ )
		{
			pSrcTempData = pSrcData;

			memset( histo, 0, sizeof( int )*768 );//

			rTemp = 0;
			gTemp = 0;
			bTemp = 0;

			for( dy = - N; dy <= N; dy++ )
			{
				for( dx = - N; dx <= N; dx++)
				{
					//color = *(pSrcImage->p + dx + dy*pSrcImage->w);
					color = *pSrcTempData;

					//r = (color&0xF800)>>11;
					//g = (color&0x7E0)>>5;
					//b = (color&0x1F);

					//a = ((color&ARGB8888_MASK_ALPHA)>>24)&0xFF;
					r =  (color&ARGB8888_MASK_RED)>>16&0xFF;
					g =  (color&ARGB8888_MASK_GREEN)>>8&0xFF;
					b =  (color&ARGB8888_MASK_BLUE)&0xFF;


					histoR[r]++;
					histoG[g]++;
					histoB[b]++;
					if ( histoR[r] > rTemp )
					{
						rTemp = histoR[r];
						rIndex = r;
					}
					if ( histoG[g] > gTemp )
					{
						gTemp = histoG[g];
						gIndex = g;
					}
					if ( histoB[b] > bTemp )
					{
						bTemp = histoB[b];
						bIndex = b;
					}
					pSrcTempData++;
				}
				pSrcTempData += offset;
			}

			*(pDstTempData++) = ARGB8888_2_COLOR(255,rIndex,gIndex,bIndex);//(rIndex<<11) | (gIndex<<5) | (bIndex);
			pSrcData++;
		}
	}

	return 0;
}




void oilPaintProcess(uint8_t *image, int width, int height, int stride, int radius){

    //LOGI("2");

    int *pDataSave = (int *)malloc(width*height*sizeof(int));
    if(pDataSave == NULL){
    	return;
    }


    iOilPaint(pDataSave, (int *)image, width, height, radius);


    memcpy( image, pDataSave, sizeof(int)*width*height);


    //LOGI("4");

    if(pDataSave != NULL){
    	free(pDataSave);
    	pDataSave = NULL;
    }
}


////////////////////

static int iPartions(uint8_t a[],int low,int high)
{
	int pivotkey=a[low];
	a[0]=a[low];
	while(low<high)
	{
		while(low<high && a[high]>=pivotkey)
			--high;
		a[low]=a[high];
		while(low<high && a[low]<=pivotkey)
			++low;
		a[high]=a[low];
	}
	a[low]=a[0];
	return low;
}

static void iQSort(uint8_t a[],int low,int high)
{
	int pivottag;
	if(low<high)
	{ //µÝ¹éµ÷ÓÃ
		pivottag=iPartions(a,low,high);
		iQSort(a,low,pivottag-1);
		iQSort(a,pivottag+1,high);
	}
}

static void iQuickSort(uint8_t a[],int n)
{
	iQSort(a,1,n);
}

//ÖÐÖµÂË²š//numÎªÆæÊý
static void imMedian( int * pDstImage, int * pSrcImage, int width, int height, int num )
{
	int i = 0;
	int j = 0;

	int k = 0;
	int l = 0;

	uint8_t r[256];
	uint8_t g[256];
	uint8_t b[256];

	uint8_t SortArray[32];

	uint8_t rColor;
	uint8_t gColor;
	uint8_t bColor;

	int color;

	int * pSrcData = pSrcImage;
	int * pDstData = pDstImage;

	int * pSrcDataTemp = pSrcData;

	int m = 0;
	int num2 = num*num;
	int num22 = num*num/2;
	int offset = (width - num);

	//copy pix
	//memcpy( pDstData, pSrcData, sizeof(int)*width*height );

	pDstData = pDstImage + (num/2 - 1)*width;

	//char name[128];

	//sprintf(name,"n=%d,%d,%d",num,width,height);

	//LOGI(name);
	//LOGI("mike");

	for ( j = num/2; j < height - num/2; j++ )
	{
		for ( i = 0; i < width; i++ )
		{
			//
			pSrcDataTemp = pSrcData;

			m = 0;
			for ( k = 0 ; k < num; k++ )
			{
				for ( l = 0; l < num; l++ )
				{
					//
					color = *pSrcDataTemp;

					r[m] = (color&ARGB8888_MASK_RED)>>16&0xFF;
					g[m] = (color&ARGB8888_MASK_GREEN)>>8&0xFF;
					b[m] = (color&ARGB8888_MASK_BLUE)&0xFF;

					pSrcDataTemp++;
					m++;
				}
				pSrcDataTemp += offset;
			}
			/*
			//Sort( r, SortArray, num2 );
			qsort( r, num2 );
			rColor = SortArray[num22];
			qsort( g, num2 );
			//Sort( g, SortArray, num2 );
			gColor = SortArray[num22];
			//Sort( b, SortArray, num2 );
			qsort( b, num2 );
			bColor = SortArray[num22];
			*/
			iQuickSort( r, num2 );
			rColor = r[num22];

			iQuickSort( g, num2 );
			gColor = g[num22];

			iQuickSort( b, num2 );
			bColor = b[num22];

			*pDstData = ARGB8888_2_COLOR( 255, rColor, gColor, bColor );

			pDstData++;
			pSrcData++;
			//LOGI("SSSS");
		}
	}


	//LOGI("SSSS ssussusuu");
	return;
}



static void iColorWaterFilter( int * pDstImage, int * pSrcImage, int width, int height  )
{
	int i = 0;
	int j = 0;

	int rColor;
	int gColor;
	int bColor;

	int color;
	int * pDesData = pDstImage;
	int * pSrcData = pSrcImage;
	int * pSrcDataTemp = pSrcData;

	//int colorAdd;
	//int colorSub;

	int colorSubLow;
	int colorSubHigh;

	int colorAddLow;
	int colorAddHigh;


//	filterCoef[0]=0;   filterCoef[1]=-(1<<15);  filterCoef[2]=0;
//	filterCoef[3]=-(1<<15);  filterCoef[4]=5<<15;   filterCoef[5]=-(1<<15);
//	filterCoef[6]=0;   filterCoef[7]=-(1<<15);  filterCoef[8]=0;


	memcpy( pDesData, pSrcData, sizeof(int)*width*height );

	pDesData += width;

	char name[128];

	for ( j = 1; j < height - 1; j++ )
	{
		for ( i = 0; i < width; i++ )
		{
			//
			pSrcDataTemp = pSrcData;


			color = *(pSrcDataTemp+1);
			//colorSub = (( color | (color << 16) ) & 0x07E0F81F);

			colorSubLow  =  color&0x00FF00FF;
			colorSubHigh = (color&0xFF00FF00)>>8;

			pSrcDataTemp += width;
			color = *(pSrcDataTemp);
			//colorSub += (( color | (color << 16) ) & 0x07E0F81F);

			colorSubLow  +=  color&0x00FF00FF;
			colorSubHigh += (color&0xFF00FF00)>>8;

			//x5
			color = *(pSrcDataTemp+1);
			//colorAdd = (( color | (color << 16) ) & 0x07E0F81F);
			//colorAdd = (colorAdd<<2) + colorAdd;

			//sprintf(name, "color =%x",color);
			//LOGI(name);

			colorAddLow  =      ((color&0x00FF00FF)<<2) +     (color&0x00FF00FF);
			colorAddHigh = (((color&0xFF00FF00)>>8)<<2) + ((color&0xFF00FF00)>>8);

			//sprintf(name, "spit:%x,%x",colorAddLow,colorAddHigh);
			//LOGI(name);

			//colorAddLow  = ((color&0x00FF00FF))*5;
			//colorAddHigh = (((color>>8)&0x00FF00FF))*5;


			color = *(pSrcDataTemp+2);
			//colorSub += (( color | (color << 16) ) & 0x07E0F81F);

			colorSubLow  +=  color&0x00FF00FF;
			colorSubHigh += (color&0xFF00FF00)>>8;

			pSrcDataTemp += width;
			color = *(pSrcDataTemp+1);
			//colorSub += (( color | (color << 16) ) & 0x07E0F81F);
			colorSubLow  +=  color&0x00FF00FF;
			colorSubHigh += (color&0xFF00FF00)>>8;



			rColor = ((colorAddLow)>>16);
			gColor = (colorAddHigh&0xFFFF);
			bColor =  (colorAddLow&0xFFFF);

			//sprintf(name, "rcolor =%x,%x,%x",rColor,gColor,bColor);
			//LOGI(name);

			rColor -= (colorSubLow>>16);
			gColor -= (colorSubHigh&0xFFFF);
			bColor -= (colorSubLow)&0xFFFF;

			if ( rColor > 255 )
			{
				rColor = 255;
			}
			else if ( rColor < 0 )
			{
				rColor = 0;
			}

			if ( gColor > 255 )
			{
				gColor = 255;
			}
			else if ( gColor < 0 )
			{
				gColor = 0;
			}

			if ( bColor > 255 )
			{
				bColor = 255;
			}
			else if ( bColor < 0 )
			{
				bColor = 0;
			}
			color = ARGB8888_2_COLOR(255, rColor, gColor, bColor);//*(pSrcDataTemp+1);//ARGB8888_2_COLOR(255, rColor, gColor, bColor);//ARGB8888_2_COLOR(255, rColor, gColor, bColor );

			//sprintf(name, "last color =%x",color);
			//LOGI(name);

			*pDesData = color;
			pDesData++;
			pSrcData++;
		}
	}
	return;
}

void colorWaterPaint( int * pSrcImage, int width, int height, int stride, int radius ){
//
	//LOGI("start colorWaterPaint  mmmmm");

	int *pDstData = (int *)malloc( sizeof(int)*width*height );
	if ( pDstData == NULL )
	{
		return;
	}

	imMedian( pDstData, pSrcImage, width, height, radius );

	//memcpy(pSrcImage,pDstData,sizeof(int)*width*height);
	//CommonFilter( &DesImageCpy, pDesImage, 3, filterCoef );
	iColorWaterFilter( pSrcImage, pDstData,  width, height );


	if ( pDstData != NULL ){
		free( pDstData );
		pDstData = 0;
	}
}

