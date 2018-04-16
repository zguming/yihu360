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
public class TestAsyncLoadApi extends AsyncTask<String, Integer, Boolean>{
	private List <NameValuePair> params;
	private LoadAPI loadAPI;
	private Context context;
	private Handler handler;
	private String APIName;
	private int succeed;
	private int failure;
	private String url;
	public TestAsyncLoadApi(Context context,Handler handler,List <NameValuePair> params,String APIName,int succeed,int failure,String url) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.APIName = APIName;
		this.succeed=succeed;
		this.failure = failure;
		this.url = url;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub


		return loadAPI.getTest(url, context, APIName);
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result==true){
			//Toast.makeText(context, "加载成功", 0).show();
			handler.sendEmptyMessage(succeed);
		}else{
			//Toast.makeText(context, "加载失败，请检查您的网络设置!", 0).show();
			Toast.makeText(context, "加载失败", 0).show();
			handler.sendEmptyMessage(failure);
		}
	}
}
