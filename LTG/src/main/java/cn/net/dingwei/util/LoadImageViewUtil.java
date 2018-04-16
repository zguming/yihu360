package cn.net.dingwei.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.net.dingwei.AsyncUtil.HomeLoadImage;
import cn.net.dingwei.AsyncUtil.LoadImage;
import cn.net.dingwei.AsyncUtil.LoadImage_backgroud;
import cn.net.dingwei.AsyncUtil.LoadImage_progerss;
import cn.net.dingwei.AsyncUtil.LoadImage_zoomInageView;
import cn.net.dingwei.AsyncUtil.MyImageDown_height;
import cn.net.dingwei.AsyncUtil.MyImageDown_width;
import cn.net.dingwei.myView.TasksCompletedView;
import cn.net.dingwei.myView.TasksCompletedView_jindu;

import com.zhy.view.ZoomImageView;

/**
 * 图片的加载
 * @author Administrator
 *
 */
public class LoadImageViewUtil {
	//根据控件的宽  动态设置高
	public static void resetImageSize(ImageView image, int width, int originalWidth,
									  int originalHeight) {

		int height = (int) ((float) originalHeight * width / originalWidth);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);

		image.setLayoutParams(params);
	}
	//根据控件的高  动态设置宽
	public static void resetImageSize_height(ImageView image, int height, int originalWidth,
											 int originalHeight) {
		int width = (int) ((float) originalWidth * height / originalHeight);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
		image.setLayoutParams(params);
	}

	/**
	 * 动态设置图片的宽度 	高度自适应
	 * @param imageView
	 * @param imageUrl
	 * @param ViewWidth
	 */
	public static void setImageBitmap(ImageView imageView,String imageUrl,int ViewWidth,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		if(null==bitmap){
			MyImageDown_width imageDown_width = new MyImageDown_width(imageView, imageUrl, ViewWidth,context);
			imageDown_width.execute();
		}else{
			int height = bitmap.getHeight();// 获取图片的高度.
			int width = bitmap.getWidth();// 获取图片的宽度
			android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
			layoutParams.height = (height * ViewWidth) / width;
			layoutParams.width = ViewWidth;
			imageView.setLayoutParams(layoutParams);
			imageView.setImageBitmap(bitmap);
		}
	}
	/**
	 * 动态设置图片的高度 	宽度自适应
	 * @param imageView
	 * @param imageUrl
	 * @param ViewWidth
	 */
	public static void setImageBitmap_setwidth(ImageView imageView,String imageUrl,int ViewHeight,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		if(null==bitmap){
			MyImageDown_height imageDown_height = new MyImageDown_height(imageView, imageUrl, ViewHeight,context);
			imageDown_height.execute();
		}else{
			int height = bitmap.getHeight();// 获取图片的高度.
			int width = bitmap.getWidth();// 获取图片的宽度
			android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
			layoutParams.height =ViewHeight;
			layoutParams.width = (width * ViewHeight) / height;
			imageView.setLayoutParams(layoutParams);
			imageView.setImageBitmap(bitmap);
		}
	}
	/**
	 * 加载图片
	 * @param imageView
	 * @param imageUrl
	 */
	public static void setImageBitmap(ImageView imageView,String imageUrl,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		if(null==bitmap){
			LoadImage loadImage = new LoadImage(imageView,imageUrl,context);
			loadImage.execute(imageUrl);
		}else{
			imageView.setImageBitmap(bitmap);
		}
	}
	/**
	 * 加载图片 设置为View的背景
	 * @param
	 * @param imageUrl
	 */
	public static void setImageBitmap_backgroud(View view,String imageUrl,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		Drawable drawable = (BitmapDrawable) BitmapDrawable.createFromPath(imagePath);
		if(null==drawable){
			LoadImage_backgroud loadImage_backgroud = new LoadImage_backgroud(view, imageUrl,context);
			loadImage_backgroud.execute(imageUrl);
			/* LoadImage loadImage = new LoadImage(imageView,imageUrl);
			 loadImage.execute(imageUrl);*/
		}else{
			view.setBackgroundDrawable(drawable);
		}
	}
	/**
	 * 加载图片  可以放大缩小的ImageView
	 * @param imageView
	 * @param imageUrl
	 */
	public static void setImageBitmap(ZoomImageView imageView,String imageUrl,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		if(null==bitmap){
			LoadImage_zoomInageView loadImage = new LoadImage_zoomInageView(imageView,imageUrl,context);
			loadImage.execute(imageUrl);
		}else{
			imageView.setImageBitmap(bitmap);
			// imageView.setImageCenter();
		}
	}
	public static void setImageBitmap_homeViewPager(TasksCompletedView view,String imageUrl,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		File file = new File(imagePath);
		if(file.exists()){
			Drawable drawable =  Drawable.createFromPath(imagePath);
			view.setOndrawBitmap(drawable);
		}else{
			HomeLoadImage homeLoadImage = new HomeLoadImage(view, imageUrl,context);
			homeLoadImage.execute();
		}
	}
	/**
	 * 加载图片
	 * @param imageView
	 * @param imageUrl
	 */
	public static void setImageBitmap_pergress(ImageView imageView,String imageUrl,String Thumb_url,TasksCompletedView_jindu progress,Context context){
		if(null ==imageUrl){
			return;
		}
		String imagePath=MyFlg.setImageFileName(imageUrl,context);
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		if(null==bitmap){
			String Thumb_url_Path = MyFlg.setImageFileName(Thumb_url,context);
			imageView.setImageBitmap(BitmapFactory.decodeFile(Thumb_url_Path));
			LoadImage_progerss loadImage = new LoadImage_progerss(imageView,imageUrl,progress,context);
			loadImage.execute();
		}else{
			progress.setVisibility(View.GONE);
			imageView.setImageBitmap(bitmap);
		}
	}
	/**
	 * 设置图片名称
	 * @param imageUrl
	 * @return
	 *//*
	public static String setImageFileName(String imageUrl){
		imageUrl = imageUrl.replace("/", "");
		imageUrl = imageUrl.replace(":", "");
		imageUrl = imageUrl.replace(".", "");
		String imagePath = MyFlg.ImageDownLoadPath+imageUrl+".jpg";
		return imagePath;
	}*/
}
