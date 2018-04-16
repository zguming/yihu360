package cn.net.dingwei.AsyncUtil;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.net.dingwei.util.MyFlg;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BaiduMapGetCity extends AsyncTask<String, Integer, String>{
	private String latitude;  //纬度
	private String lontitude; //经度
	private Handler handler;
	private Context context;
	public BaiduMapGetCity(Context context,Handler handler,String latitude,String lontitude) {
		// TODO Auto-generated constructor stub
		this.latitude = latitude;
		this.lontitude = lontitude;
		this.handler = handler;
		this.context = context;
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String url = "http://api.map.baidu.com/geocoder?output=json&location="+latitude+","+lontitude+"&key=SkyGE0B6VlVq09b8fT7wwa9E";
		HttpGet httpRequest = new HttpGet(url); 
		 /*发送请求并等待响应*/
		HttpResponse httpResponse;
		try {
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			 /*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200)
			{
	          /*读*/
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObject = new JSONObject(strResult);
				MyFlg.baidu_city = jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("city");
				MyFlg.baidu_city=MyFlg.baidu_city.replace("市", "");
				MyFlg.baidu_city=MyFlg.baidu_city.replace("省", "");
				MyFlg.baidu_city=MyFlg.baidu_city.replace("市辖区", "");
				return MyFlg.baidu_city;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(!result.equals("0")){
			Message message = new Message();
			message.what=0;
			handler.sendMessage(message);
		}else{
			Toast.makeText(context, "定位失败", 0).show();
			handler.sendEmptyMessage(-1);
		}
	}
}
