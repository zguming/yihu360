package cn.net.dingwei.util;

import android.content.Context;

/**
 * 根据分辨率的不同设置字体等的大小
 * @author Administrator
 *
 */
public class APPSetSize {
	/**
	 * 返回的是像素单位  
	 * 设置首页圆的大小
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public static int setHomeProgressSize(Context context,int screenWidth,int screenHeight){
		if (screenWidth <= 480){  // 480X800 或 480X854 屏幕  
			return DensityUtil.DipToPixels(context, 55);
		}
		return DensityUtil.DipToPixels(context, 65);
	}
}
