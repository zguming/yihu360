/**
 * 
 */
package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * @author Administrator
 *
 */
public class Gridview_Adapter extends BaseAdapter{
	private Context context;
	private Get_guide_msg_listBean.images images[];
	private int imageViewWidth;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Screen_width=0;
	public Gridview_Adapter(Context context,Get_guide_msg_listBean.images[] images) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.images = images;
		application = MyApplication.myApplication;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		imageViewWidth = (Screen_width-DensityUtil.DipToPixels(context, 26))/3;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(images.length>9){
			return 9;
		}
		return images.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return images[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutParams layoutParams = new LayoutParams(imageViewWidth, imageViewWidth);
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(R.drawable.default_img);
		LoadImageViewUtil.setImageBitmap(imageView, images[arg0].getThumb_url(),context);
		imageView.setLayoutParams(layoutParams);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		return imageView;
	}

}
