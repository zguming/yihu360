package cn.net.dingwei.AsyncUtil;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import cn.net.dingwei.ui.HomeActivity2;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
/**
 * @author Administrator
 *
 */
public class AsyncGet_user_baseinfo extends AsyncTask<String, Integer, Boolean>{
	private LoadAPI loadAPI;
	private Activity context;
	private Handler handler;
	private MyApplication application;
	private Boolean isLoadImage=false;//是否需要加载图标 引导图
	private int succeed;
	private int failure;
	public AsyncGet_user_baseinfo(Activity context,Handler handler,Boolean isLoadImage,int succeed,int failure) {
		// TODO Auto-generated constructor stub
		this.context = context;
		loadAPI = new LoadAPI(context);
		this.handler = handler;
		application = MyApplication.myApplication;
		this.isLoadImage = isLoadImage;
		this.succeed =succeed;
		this.failure = failure;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return loadAPI.get_user_baseinfo();
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result==true) {//加载成功
			application.get_user_baseinfo = APPUtil.Get_user_baseinfo(context);
			//极光推送  打别名
			MyFlg.setJPushAlias(context, MyApplication.Getget_user_baseinfo(context).getUid()+"");

			if(isLoadImage){//是否需要加载图标等
				List<String> list = setImageUrl();
				//下载下次Log需要的图片
				SharedPreferences sharedPreferences = context.getSharedPreferences("get_intropage_templates", Context.MODE_PRIVATE);
				List<String> list_imageUrl=APPUtil.getImageUrlList(sharedPreferences.getString("get_intropage_templates", "0"));
				list.addAll(list_imageUrl);
				for (int i = 0; i < list_imageUrl.size(); i++) {
					LoadImage loadImage = new LoadImage(list_imageUrl.get(i),context);
					loadImage.execute(list_imageUrl.get(i));
				}
				LoadIcons_Async loadIcons_Async = new LoadIcons_Async(context, list,handler);
				loadIcons_Async.execute();
			}else{
				handler.sendEmptyMessage(succeed);
			}
		}else{
			//Toast.makeText(context, "加载失败，请检查您的网络设置!", 0).show();
			Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(failure);
		}
	}

	private List<String> setImageUrl(){
		List<String> list_imageurl = new ArrayList<String>();
		list_imageurl.add(application.imageBean.getNav_1());
		list_imageurl.add(application.imageBean.getNav_2());
		list_imageurl.add(application.imageBean.getNav_3());
		list_imageurl.add(application.imageBean.getNav_4());
		list_imageurl.add(application.imageBean.getNav_1_active());
		list_imageurl.add(application.imageBean.getNav_2_active());
		list_imageurl.add(application.imageBean.getNav_3_active());
		list_imageurl.add(application.imageBean.getNav_4_active());
		list_imageurl.add(application.imageBean.getSubject_1());
		list_imageurl.add(application.imageBean.getSubject_1_white());
		list_imageurl.add(application.imageBean.getHome_slidebar());
		list_imageurl.add(application.imageBean.getHome_personal());
		list_imageurl.add(application.imageBean.getRight_btn());
		list_imageurl.add(application.imageBean.getReturn_up());
		list_imageurl.add(application.imageBean.getRadio_uncheck());
		list_imageurl.add(application.imageBean.getRadio_check());
		list_imageurl.add(application.imageBean.getTest_pause());
		list_imageurl.add(application.imageBean.getTest_play());
		list_imageurl.add(application.imageBean.getKaopin());
		list_imageurl.add(application.imageBean.getHome_share());
		list_imageurl.add(application.imageBean.getShare());
		list_imageurl.add(application.imageBean.getLianxi_w());
		list_imageurl.add(application.imageBean.getLianxi());
		list_imageurl.add(application.imageBean.getCtgg_w());
		list_imageurl.add(application.imageBean.getGeren());
		list_imageurl.add(application.imageBean.getUnicon());
		list_imageurl.add(application.imageBean.getVipicon());
		list_imageurl.add(application.imageBean.getKaopin_grey());
		list_imageurl.add(application.imageBean.getHuakuai());
		list_imageurl.add(application.imageBean.getGongjuxiang());
		return list_imageurl;

	}
}
