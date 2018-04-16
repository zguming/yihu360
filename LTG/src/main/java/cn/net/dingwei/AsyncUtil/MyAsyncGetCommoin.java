package cn.net.dingwei.AsyncUtil;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.net.dingwei.Bean.Itropage_templatesBean;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;

public class MyAsyncGetCommoin extends AsyncTask<String, integer, Boolean>{
	private Activity context;
	private LoadAPI loadApi;
	private SharedPreferences sp;
	private String clientcode;
	private String mobile_model;
	private ArrayList<Itropage_templatesBean> list;
	private Handler handler;
	private MyApplication application;
	public MyAsyncGetCommoin(Handler handler,Activity context,String clientcode,String mobile_model) {
		// TODO Auto-generated constructor stub
		this.context = context;
		application = MyApplication.myApplication;
		sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
		this.clientcode = clientcode;
		this.mobile_model = mobile_model;
		this.handler = handler;
		loadApi = new LoadAPI(context);
	}

	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		MyFlg.isGet_commoninfo =loadApi.getCommoninfo();

		if(MyFlg.isGet_userinfo && MyFlg.isGet_commoninfo){
			sp.edit().putString("updatetime", APPUtil.getNowTime()).commit();
			return true;
		}

		return false;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result == false){
			MyFlg.isLoadApi =false;
			Message msg = new Message();
			msg.what=1;
			handler.sendMessage(msg);
			return;
		}
		//开始执行加载下次所需Log图片
		if(result==true){
			APPUtil.getColorBean(context);
			//是否跳转到主页
			MyFlg.isLoadApi =true;


			if(!"".equals(application.match_url)){//加载空白webview
				handler.sendEmptyMessage(1001);
			}

			//加载get_user_baseinfo
			AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(context, handler,true,0,1);
			asyncGet_user_baseinfo.execute();
		}

	}


}
