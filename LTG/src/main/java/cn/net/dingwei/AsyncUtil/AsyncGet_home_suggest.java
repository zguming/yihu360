package cn.net.dingwei.AsyncUtil;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;

public class AsyncGet_home_suggest extends AsyncTask<String, Integer, Boolean>{
	private List <NameValuePair> params;
	private LoadAPI loadAPI;
	private Context context;
	private Handler handler;
	private MyApplication application;
	public AsyncGet_home_suggest(Context context,Handler handler,List <NameValuePair> params) {
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
		if(loadAPI.PostAPI(params, "get_home_suggest", MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_home_suggest(),context))){
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
			//handler.sendEmptyMessage(4);
			handler.sendEmptyMessageDelayed(4, 500);//延迟300毫秒
		}else{
			//Toast.makeText(context, "加载失败，请检查您的网络设置!", 0).show();
			Toast.makeText(context, "加载失败", 0).show();
			handler.sendEmptyMessage(2);
		}
	}
}
