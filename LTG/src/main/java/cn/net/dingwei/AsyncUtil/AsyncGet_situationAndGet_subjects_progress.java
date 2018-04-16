package cn.net.dingwei.AsyncUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.apache.http.NameValuePair;

import java.util.List;

import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;

public class AsyncGet_situationAndGet_subjects_progress extends AsyncTask<String, Integer, Boolean>{
	private List <NameValuePair> params;
	private LoadAPI loadAPI;
	private Context context;
	private Handler handler;
	private MyApplication application;
	public AsyncGet_situationAndGet_subjects_progress(Context context,Handler handler,List <NameValuePair> params) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		application = MyApplication.myApplication;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		if(loadAPI.PostAPI(params, "get_situation", MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_situation(),context))
				&&loadAPI.PostAPI(params, "get_subjects_progress", MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_subjects_progress(),context))){
			return true;
		}
		return false;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result==true){
			//Toast.makeText(context, "加载成功", 0).show();
			//handler.sendEmptyMessage(1);
			handler.sendEmptyMessageDelayed(1, 200);
		}
	}
}
