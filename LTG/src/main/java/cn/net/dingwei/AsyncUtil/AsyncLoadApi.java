package cn.net.dingwei.AsyncUtil;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyFlg;
/**
 * 适合读取单个API  加载成功返回0  加载失败返回1
 * @author Administrator
 *
 */
public class AsyncLoadApi extends AsyncTask<String, Integer, Boolean>{
	private List <NameValuePair> params;
	private LoadAPI loadAPI;
	private Context context;
	private Handler handler;
	private String APIName;
	private int succeed;
	private int failure;
	private Boolean showFailureTask=true; //是否显示Task 加载失败时
	private String FailureTask = "加载失败!";
	private String SharedPreferences_name="";
	private String APIUrl;
	public AsyncLoadApi(Context context,Handler handler,List <NameValuePair> params,String APIName,int succeed,int failure,String APIUrl) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.APIName = APIName;
		this.succeed=succeed;
		this.failure = failure;
		showFailureTask = true;
		this.APIUrl = APIUrl;
	}
	//定义Task输出信息
	public AsyncLoadApi(Context context,Handler handler,List <NameValuePair> params,String APIName,int succeed,int failure,String FailureTask,String APIUrl) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.APIName = APIName;
		this.succeed=succeed;
		this.failure = failure;
		showFailureTask = true;
		this.FailureTask = FailureTask;
		this.APIUrl = APIUrl;
	}
	public AsyncLoadApi(Context context,Handler handler,List <NameValuePair> params,String APIName,int succeed,int failure,Boolean showFailureTask,String APIUrl) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.APIName = APIName;
		this.succeed=succeed;
		this.failure = failure;
		this.showFailureTask= showFailureTask;
		this.APIUrl = APIUrl;
	}
	public AsyncLoadApi(String SharedPreferences_name,Context context,Handler handler,List <NameValuePair> params,String APIName,int succeed,int failure,String APIUrl) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.context = context;
		this.handler = handler;
		loadAPI = new LoadAPI(context);
		this.APIName = APIName;
		this.succeed=succeed;
		this.failure = failure;
		this.SharedPreferences_name =SharedPreferences_name;
		this.APIUrl = APIUrl;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		if(SharedPreferences_name.equals("")){

			if(APIName.equals("get_baseinfo")){
				return loadAPI.PostAPI_city(params, APIName, APIUrl);
			}else{
				return loadAPI.PostAPI(params, APIName, APIUrl);
			}

		}else{
			if(loadAPI.PostAPI(params, APIName,SharedPreferences_name,APIUrl)){
				return true;
			}
		}

		return false;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if(result==true){
			//Toast.makeText(context, "加载成功", 0).show();
			handler.sendEmptyMessage(succeed);
		}else{
			if(!APIName.equals("submit_exercise_answer") && !APIName.equals("submit_test_answer")){
				if(!MyFlg.errorCode.equals("")){
					Toast.makeText(context, MyFlg.errorCode, 0).show();
				}else{
					Toast.makeText(context, FailureTask, 0).show();
				}
				MyFlg.errorCode="";
				handler.sendEmptyMessage(failure);
			}
		}
	}
}
