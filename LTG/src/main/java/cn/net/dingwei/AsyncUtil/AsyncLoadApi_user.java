package cn.net.dingwei.AsyncUtil;

import java.util.List;

import org.apache.http.NameValuePair;

import com.tencent.wxop.stat.ai;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
/**
 * 适合读取单个API  加载成功返回0  加载失败返回1
 * @author Administrator
 *
 */
public class AsyncLoadApi_user extends AsyncTask<String, Integer, Integer>{
	private List <NameValuePair> params;
	private LoadAPI loadAPI;
	private Context context;
	private Handler handler;
	private String APIName;
	private String APIUrl;
	public AsyncLoadApi_user(Context context,Handler handler,List <NameValuePair> params,String APIName,String APIUrl) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.APIName = APIName;
		this.APIUrl = APIUrl;
	}
	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub

		return  loadAPI.PostAPI_user(params, APIName, APIUrl);
	}
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result==1){
			//Toast.makeText(context, "加载成功", 0).show();
			handler.sendEmptyMessage(1);
		}else if(result==2 &&APIName.equals("bind_user") ){
			Toast.makeText(context, "请检查手机和密码是否正确", 0).show();
			handler.sendEmptyMessage(2);
		}
		else{
			Toast.makeText(context, "网络连接失败！", 0).show();
			//Toast.makeText(context, "加载失败", 0).show();
			handler.sendEmptyMessage(3);
		}
	}
}
