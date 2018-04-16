package cn.net.dingwei.AsyncUtil;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyFlg;
/**
 * 适合读取单个API  加载成功返回0  加载失败返回1
 * @author Administrator
 *
 */
public class AsyncLoad_baseInfo extends AsyncTask<String, Integer, Boolean>{
	private LoadAPI loadAPI;
	private Context context;
	private Handler handler;
	private int succeed;
	private int failure;
	private String city;
	private String time;
	public AsyncLoad_baseInfo(Context context,Handler handler,String city,String time,int succeed,int failure) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.succeed=succeed;
		this.failure = failure;
		this.city =city;
		this.time = time;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return loadAPI.getBaseinfo(city);
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result==true) {
			//Toast.makeText(context, "加载成功", 0).show();
			handler.sendEmptyMessage(succeed);
		}else{
			//Toast.makeText(context, "加载失败，请检查您的网络设置!", 0).show();
			Toast.makeText(context, "加载失败", 0).show();
			handler.sendEmptyMessage(failure);
		}
	}
}
