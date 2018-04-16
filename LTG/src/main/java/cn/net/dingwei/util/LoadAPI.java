package cn.net.dingwei.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import cn.net.dingwei.sup.Sup;

public class LoadAPI{
	String  TAG = "123";
	private SharedPreferences sp;
	private Context context;
	private MyApplication application;
	public LoadAPI(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		application = MyApplication.myApplication;
	}
	/**
	 * 测试接口
	 * @param url
	 * @param context
	 * @param APIName
	 */
	public Boolean getTest(String url,Context context,String APIName){
		HttpGet httpRequest = new HttpGet(url); 
		 /*发送请求并等待响应*/
		HttpResponse httpResponse;
		try {
// Can't read [D:\JDK\Java\jdk1.8.0_91\jre\lib\rt.jar] (No such file or directory)
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			 /*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200)
			{
	          /*读*/
				String strResult = EntityUtils.toString(httpResponse.getEntity());

				sp = context.getSharedPreferences(APIName, Context.MODE_PRIVATE);
				sp.edit().putString(APIName, strResult).commit();
				return true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 * @param params    传送的参数
	 * 		//Post运作传送变数必须用NameValuePair[]阵列储存  
	//传参数 服务端获取的方法为request.getParameter("name")
	List <NameValuePair> params=new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("updatetime",time));
	 * @param APIName 方法名 如：get_home_suggest
	 * @param APIurl  API 链接前面部分 如：MyFlg.APIurl  MyFlg.TestAPIurl
	 */
	public Boolean PostAPI_city(List <NameValuePair> params,String APIName,String APIurl){
		sp = context.getSharedPreferences(APIName, Context.MODE_PRIVATE);
		//String url =APIurl+APIName;
		 /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(APIurl);
		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//取得HTTP response
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );

			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult= Sup.UnZipString(responseBody);
				if(isUpdatae(strResult)==false){
					String st = APPUtil.getsend_mobile_verificationcodeError(strResult);
					MyFlg.errorCode = st;
					return false;
				}else{
					sp.edit().putString(APIName, strResult).commit();
					return true;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 *
	 * @param params    传送的参数
	 * 		//Post运作传送变数必须用NameValuePair[]阵列储存  
	//传参数 服务端获取的方法为request.getParameter("name")
	List <NameValuePair> params=new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("updatetime",time));
	 * @param APIName 方法名 如：get_home_suggest
	 * @param APIurl  API 链接前面部分 如：MyFlg.APIurl  MyFlg.TestAPIurl
	 */
	public Boolean PostAPI(List <NameValuePair> params,String APIName,String SharedPreferences_name,String APIurl){
		sp = context.getSharedPreferences(SharedPreferences_name, Context.MODE_PRIVATE);
		//String url =APIurl+APIName;
		 /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(APIurl);
		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//取得HTTP response
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );

			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串

				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult= Sup.UnZipString(responseBody);
				if(isUpdataeBoolean(strResult)==false){
					String st = APPUtil.getsend_mobile_verificationcodeError(strResult);
					MyFlg.errorCode = st;
					return false;
				}else{
					sp.edit().putString(SharedPreferences_name, strResult).commit();
					return true;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 *
	 * @param params    传送的参数
	 * 		//Post运作传送变数必须用NameValuePair[]阵列储存  
	//传参数 服务端获取的方法为request.getParameter("name")
	List <NameValuePair> params=new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("updatetime",time));
	 * @param APIName 方法名 如：get_home_suggest
	 * @param APIurl  API 链接前面部分 如：MyFlg.APIurl  MyFlg.TestAPIurl
	 */
	public Boolean PostAPI(List <NameValuePair> params,String APIName,String APIurl){
		sp = context.getSharedPreferences(APIName, Context.MODE_PRIVATE);
		 /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(APIurl);
		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//取得HTTP response
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );

			HttpResponse httpResponse=client.execute(httpRequest);
//			Log.i(TAG, "APIurl: "+APIurl+"  "+httpResponse.getStatusLine().getStatusCode());
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);
				if(isUpdataeBoolean(strResult)==false){
					String st = APPUtil.getsend_mobile_verificationcodeError(strResult);
					MyFlg.errorCode = st;
					return false;
				}else{
					sp.edit().putString(APIName, strResult).commit();
					return true;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 *
	 * 用户解绑 与绑定
	 * return 1:登录成功  2：登录不成功（账号密码不匹配 ） 3：网络链接失败
	 */
	public int PostAPI_user(List <NameValuePair> params,String APIName,String APIurl){
		sp = context.getSharedPreferences("get_userinfo", Context.MODE_PRIVATE);
		//String url =APIurl+APIName;
		 /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(APIurl);
		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串
				// String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);

				if(isUpdataeBoolean(strResult)){
					sp.edit().putString("get_userinfo", strResult).commit();
					return 1;
				}else{
					return 2;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 3;
	}

	/**
	 * POST请求
	 */
	public  Boolean getCommoninfo(){
		sp = context.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		String url = MyFlg.Commoninfo_APIurl;
//		String url = "http://api.mtiku.net/2.0/get_test/v6";

		    /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(url);
		//Post运作传送变数必须用NameValuePair[]阵列储存
		//传参数 服务端获取的方法为request.getParameter("name")
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("a",MyFlg.a));
		String time = sp.getString("updated_time", "0");
		if(time.equals("0")){
			params.add(new BasicNameValuePair("updatetime",null));
		}else{
			params.add(new BasicNameValuePair("updatetime",time));
		}
		Log.i(TAG, "getCommoninfo   url: "+url);
		Log.i(TAG, "getCommoninfo   params: "+params.toString());

		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok

			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串
				// String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String s = new String(responseBody);
				String strResult=Sup.UnZipString(responseBody);
				Log.i(TAG, "strResult: "+strResult);

				if(isUpdatae(strResult)){
					sp.edit().putString("get_commoninfo", strResult).commit();
					setUpdataTime(sp, strResult);
				}
				String APIURL=new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getString("api_rootpath");
				// application.setApp_key(new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getString("app_key"));
				application.commonInfo_API_functions=APPUtil.getAI_functions(context, sp.getString("get_commoninfo", "0"));
				if(null != APIURL){ //加载的API
					SharedPreferences sharedPreferences = context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
					sharedPreferences.edit().putString("Api_rootpath", APIURL).commit();
				}

//				try {
//					String is_mast=new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("update_version").getString("is_mast");
//					Log.e("aaa-----", is_mast);
//					if ("1".equals(is_mast)){
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}

				//加载用户数据
				MyFlg.isGet_userinfo =getUserInfo(MyFlg.getclientcode(context), MyFlg.getmobile_model());
				//加载引导屏
				get_intropage_templates(MyFlg.getclientcode(context), MyFlg.getmobile_model());
				return true;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return false;
	}
	/**
	 * 更新User
	 * @param clientcode 手机唯一标识
	 * @param city	城市  例：cq
	 * @param nickname 昵称
	 * @param mobile	手机号
	 * @param exam		考试
	 * @param exam_schedule	考试时间
	 * @param subject	科目
	 * @param newpassword 新密码
	 * @param oldpassword 当前密码
	 * @return
	 */
	public Boolean update_userinfo(String clientcode,String city,String nickname,String mobile,String exam,String exam_schedule,String subject,String password,String mobile_verificationcode,String oldpassword,String newpassword,String update_city,
								   String bool,String headurl){
		sp = context.getSharedPreferences("get_userinfo", Context.MODE_PRIVATE);
		String url =MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getUpdate_userinfo(),context);
		 /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(url);
		//Post运作传送变数必须用NameValuePair[]阵列储存
		//传参数 服务端获取的方法为request.getParameter("name")
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode",clientcode));
		params.add(new BasicNameValuePair("city",city));
		params.add(new BasicNameValuePair("nickname",nickname));
		params.add(new BasicNameValuePair("mobile",mobile));
		params.add(new BasicNameValuePair("exam",exam));
		params.add(new BasicNameValuePair("exam_schedule",exam_schedule));
		params.add(new BasicNameValuePair("subject",subject));
		params.add(new BasicNameValuePair("password",password));
		params.add(new BasicNameValuePair("newpassword",newpassword));
		params.add(new BasicNameValuePair("oldpassword",oldpassword));
		params.add(new BasicNameValuePair("mobile_verificationcode",mobile_verificationcode));
		params.add(new BasicNameValuePair("update_city",update_city));
		params.add(new BasicNameValuePair("bool",bool));
		params.add(new BasicNameValuePair("img",headurl));
		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串
				//  String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);
				if(isUpdataeBoolean(strResult)==false){
					String st = APPUtil.getsend_mobile_verificationcodeError(strResult);
					MyFlg.errorCode = st;
					return true;
				}else{
					sp.edit().putString("get_userinfo", strResult).commit();
					return true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;


	}
	/**
	 *
	 * @param clientcode 手机唯一标识
	 * @param mobile_model 手机型号
	 */
	public  Boolean getUserInfo(String clientcode,String mobile_model){
		sp = context.getSharedPreferences("get_userinfo", Context.MODE_PRIVATE);
		//String url = MyFlg.APIurl+"get_userinfo";
		String url = MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_userinfo(),context);
		//测试接口
		    /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(url);
		//Post运作传送变数必须用NameValuePair[]阵列储存
		//传参数 服务端获取的方法为request.getParameter("name")
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode",clientcode));
		params.add(new BasicNameValuePair("mobile_model",mobile_model));

		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//取出回应字串
				//String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);
				sp.edit().putString("get_userinfo", strResult).commit();
				String city = new JSONObject(strResult).getJSONObject("data").getString("city");
				if(city.equals("null") || city.length()<=0){//没有注册城市的
					return true;
				}else{//注册了城市的
					return getBaseinfo(city);
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return false;
	}
	/**
	 * POST请求 获取get_user_baseinfo
	 */
	public  Boolean get_user_baseinfo(){
		String APIName="get_user_baseinfo";
		sp = context.getSharedPreferences(APIName, Context.MODE_PRIVATE);
		String url =MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_user_baseinfo(),context);
		//传参数 服务端获取的方法为request.getParameter("name")
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(context)));
		try {
	    	/*建立HTTP Post连线*/
			HttpPost httpRequest =new HttpPost(url);
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){

				//取出回应字串
				//   String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);
				if(isUpdataeBoolean(strResult)){
					sp.edit().putString(APIName, strResult).commit();
					setUpdataTime(sp, strResult);
				}else{
					return false;
				}
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	/**
	 * POST请求 获取Baseinfo
	 */
	public  Boolean getBaseinfo(String city){

		sp = context.getSharedPreferences("get_baseinfo", Context.MODE_PRIVATE);
		String url =MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_baseinfo(),context);
		//String url ="http://api.liantigou.com/2.0/get_baseinfo/v2";
		    /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(url);
		//Post运作传送变数必须用NameValuePair[]阵列储存
		//传参数 服务端获取的方法为request.getParameter("name")
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(context)));
		String time = sp.getString("updated_time", "0");
		if(time.equals("0")){
			params.add(new BasicNameValuePair("updatetime",null));
		}else{
			params.add(new BasicNameValuePair("updatetime",time));
		}
		params.add(new BasicNameValuePair("city",city));


		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok

			if(httpResponse.getStatusLine().getStatusCode()==200){

				//取出回应字串
				//  String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);

				Log.i(TAG, "getBaseinfo: "+strResult);

				if(isUpdatae(strResult)){
					sp.edit().putString("get_baseinfo", strResult).commit();
					setUpdataTime(sp, strResult);
				}
				application.guideBean = APPUtil.getGuide(context);
				return true;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * POST请求 get_intropage_templates
	 */
	public  Boolean get_intropage_templates(String clientcode,String mobile_model){
		SharedPreferences sp_intropage_templates = context.getSharedPreferences("get_intropage_templates", Context.MODE_PRIVATE);
		String url =MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_intropage_templates(),context);
		    /*建立HTTP Post连线*/
		HttpPost httpRequest =new HttpPost(url);
		//Post运作传送变数必须用NameValuePair[]阵列储存
		//传参数 服务端获取的方法为request.getParameter("name")
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("clientcode",clientcode));
		params.add(new BasicNameValuePair("mobile_model",mobile_model));
		String time = sp.getString("updated_time", "0");
		if(time.equals("0")){
			params.add(new BasicNameValuePair("updatetime",null));
		}else{
			params.add(new BasicNameValuePair("updatetime",time));
		}

		//发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			//// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
			//取得HTTP response
			HttpResponse httpResponse=client.execute(httpRequest);
			//若状态码为200 ok
			if(httpResponse.getStatusLine().getStatusCode()==200){

				//取出回应字串
				//  String strResult=EntityUtils.toString(httpResponse.getEntity());
				byte[] responseBody=EntityUtils.toByteArray(httpResponse.getEntity());
				String strResult=Sup.UnZipString(responseBody);
				if(isUpdatae(strResult)){
					sp_intropage_templates.edit().putString("get_intropage_templates", strResult).commit();
				}




				return true;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * POST请求 get_intropage_templates
	 * 设置默认 第一次启动 的引导屏
	 * @param time
	 */
	/*public  Boolean get_intropage_templates_first(){
			String url = "http://dingwei.ensys.cn/dingwei_api_tester/api_list/api_response?key=zhidian&fn=get_intropage_templates";
		    建立HTTP Post连线  
		    HttpPost httpRequest =new HttpPost(url);  
		    //Post运作传送变数必须用NameValuePair[]阵列储存  
		    //传参数 服务端获取的方法为request.getParameter("name")  
		   List <NameValuePair> params=new ArrayList<NameValuePair>();  
		    params.add(new BasicNameValuePair("clientcode",clientcode));  
		    params.add(new BasicNameValuePair("mobile_model",mobile_model));  
		    params.add(new BasicNameValuePair("updatetime",time)); 
		       
		     //发出HTTP request  
		     try {
				httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpClient client = new DefaultHttpClient();
				 //// 请求超时
				 client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
				// 读取超时
				 client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000    );
				 //取得HTTP response  
			     HttpResponse httpResponse=client.execute(httpRequest);
			     //若状态码为200 ok   
			     if(httpResponse.getStatusLine().getStatusCode()==200){  
			    	 
			      //取出回应字串  
			      String strResult=EntityUtils.toString(httpResponse.getEntity()); 
			      strResult =  APPUtil.decodeUnicode(strResult);
			      sp.edit().putString("get_intropage_templates", strResult).commit();	
			      
				  
			      return true;
			     }
			     
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;  
	}*/
	/**
	 * 判断是否需要更新数据 status为updated 或noupdate
	 */
	private Boolean isUpdatae(String strResult){
		try {
			JSONObject jsonObject = new JSONObject(strResult);
			String update = jsonObject.getString("status");
			if(update.equals("updated")){
				return true;
			}else if(update.equals("noupdate")){
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断是否需要更新数据 status为True 或fasle
	 */
	private Boolean isUpdataeBoolean(String strResult){
		try {
			JSONObject jsonObject = new JSONObject(strResult);
			boolean update = jsonObject.getBoolean("status");
			if(update){
				return true;
			}else{
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	private void setUpdataTime(SharedPreferences sp,String strResult){
		try {
			String uptiem = new JSONObject(strResult).getJSONObject("data").getString("updated_time");
			sp.edit().putString("updated_time", uptiem).commit();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
