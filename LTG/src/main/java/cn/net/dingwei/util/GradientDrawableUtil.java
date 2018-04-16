package cn.net.dingwei.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import cn.net.tmjy.mtiku.futures.R;

/**
 * 主要用户  各种View的边框设置
 * @author Administrator
 *
 */
public class GradientDrawableUtil {
	/**
	 * 画矩形边 框
	 * @param strokeWidth 边框宽度
	 * @param strokeColor 边框颜色
	 * @param bgColor	      背景色
	 * @param Radius	      圆角  以PX为单位
	 */
	public static GradientDrawable setGradientDrawable(int strokeWidth,int strokeColor,int bgColor,int Radius){
		//设置边框
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		drawable.setStroke(strokeWidth, strokeColor); // 边框粗细及颜色
		drawable.setColor(bgColor);// 边框内部颜色
		drawable.setCornerRadius(Radius);  //圆角
		return drawable;
	}
	public static GradientDrawable setGradientDrawable(Context context,int strokeWidth,int Radius){
		//设置边框
		MyApplication application = MyApplication.myApplication;
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		drawable.setStroke(strokeWidth, context.getResources().getColor(R.color.bgcolor_2)); // 边框粗细及颜色
		drawable.setColor(Color.TRANSPARENT);// 边框内部颜色
		drawable.setCornerRadius(Radius);  //圆角
		return drawable;
	}

}
