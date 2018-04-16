package cn.net.dingwei.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

public class BitmapUitl {
	/**
	 * 
	 * @param filePath 图片路径  
	 * 获取缩略 不然会导致OOM
	 * @return
	 */
	public static Bitmap createImageThumbnail(String filePath,int minSideLength, int maxNumOfPixels ){  
	     Bitmap bitmap = null;  
	     BitmapFactory.Options opts = new BitmapFactory.Options();  
	     opts.inJustDecodeBounds = true;  
	     BitmapFactory.decodeFile(filePath, opts);  
	     opts.inSampleSize = computeSampleSize(opts, -1, 128*128);  
	     opts.inJustDecodeBounds = false;  
	  
	     try {  
	         bitmap = BitmapFactory.decodeFile(filePath, opts);  
	     }catch (Exception e) {  
	        // TODO: handle exception  
	    }  
	    return bitmap;  
	}  
	/**
	 * 
	 * @param filePath 图片路径  
	 * 获取缩略 不然会导致OOM
	 * @return
	 */
	public static Bitmap createImageThumbnail(Context context,int imageid,int minSideLength, int maxNumOfPixels ){  
	     Bitmap bitmap = null;  
	     BitmapFactory.Options opts = new BitmapFactory.Options();  
	     opts.inJustDecodeBounds = true;  
	     BitmapFactory.decodeResource(context.getResources(), imageid, opts);
	     opts.inSampleSize = computeSampleSize(opts, -1, minSideLength*maxNumOfPixels);  
	     opts.inJustDecodeBounds = false;  
	  
	     try {  
	         bitmap =  BitmapFactory.decodeResource(context.getResources(), imageid, opts);
	     }catch (Exception e) {  
	        // TODO: handle exception  
	    }  
	    return bitmap;  
	}  
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {  
	    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);  
	    int roundedSize;  
	    if (initialSize <= 8) {  
	        roundedSize = 1;  
	        while (roundedSize < initialSize) {  
	            roundedSize <<= 1;  
	        }  
	    } else {  
	        roundedSize = (initialSize + 7) / 8 * 8;  
	    }  
	    return roundedSize;  
	}  
	  
	private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {  
	    double w = options.outWidth;  
	    double h = options.outHeight;  
	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));  
	    int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));  
	    if (upperBound < lowerBound) {  
	        // return the larger one when there is no overlapping zone.  
	        return lowerBound;  
	    }  
	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {  
	        return 1;  
	    } else if (minSideLength == -1) {  
	        return lowerBound;  
	    } else {  
	        return upperBound;  
	    }  
	}  
	
	
	/**
	 * 获得圆角图片的方
	 * @param bitmap  图像
	 * @param roundPx  圆角
	 * @return
	 */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){  
          
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap  
                .getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
   
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
   
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
   
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
   
        return output;  
    }  
}
