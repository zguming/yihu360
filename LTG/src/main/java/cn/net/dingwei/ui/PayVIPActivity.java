package cn.net.dingwei.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.WeiXinPayBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.JSONUtil;
import cn.net.dingwei.util.LogUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.dingwei.util.SharedInfo;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;
import cn.net.treeListView.Node;
import universal_video.UniversalVideoActicity;

public class PayVIPActivity extends ParentActivity implements OnClickListener{
	private RelativeLayout title_bg;
	private LinearLayout linear_Left,linear_right;
	private TextView title_tx;
	private WebView webview;
	private myHandler handler=new myHandler();
	private FYuanTikuDialog dialog;
	private LinearLayout shuaxin_linear;
	private Button shuaxin_button;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private String pay_url = null;
	private IWXAPI api;

	private String id="";//购买密卷的ID
	private String next_url;
	private int flg=-1;//0 充值页面 1 视频购买分组 2 视频单个购买  3.预购直播
	private int pay_type=-1;//0 失败  1 成功
	private int SDK_Version=0;

	//视频
	private String Vid="";//视频ID
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("Mylog", "包名：" + getPackageName());
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_pay_vip);
		SDK_Version = getSDKVersionNumber();
		initID();
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		api = WXAPIFactory.createWXAPI(PayVIPActivity.this, BuildConfig.WEIXIN_APPID);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		flg=intent.getIntExtra("flg",-1);
		Vid=intent.getStringExtra("vid");
		position = intent.getIntExtra("position",position);

		if(!TextUtils.isEmpty(id)||flg!=0&&flg!=-1){
			pay_url =intent.getStringExtra("url");
		}else if(flg==0){
			title_bg.setVisibility(View.VISIBLE);
			pay_url =intent.getStringExtra("url");
		}else{
				pay_url = sharedPreferences.getString("pay_url", "");
				pay_url =pay_url+"?clientcode="+MyFlg.getclientcode(PayVIPActivity.this)+"&os=Android";
		}
		LogUtil.e(pay_url);
		initShuaxin();
		init();
	}

	private void initID() {
		title_bg =(RelativeLayout)findViewById(R.id.title_bg);
		title_bg.setBackgroundColor(getResources().getColor(R.color.bgcolor_1));
		//title_bg.setVisibility(View.GONE);
		linear_Left= (LinearLayout)findViewById(R.id.linear_Left);
		linear_right= (LinearLayout)findViewById(R.id.linear_right);
		title_tx= (TextView)findViewById(R.id.title_tx);

		//设置监听
		linear_Left.setOnClickListener(this);
		linear_right.setOnClickListener(this);

	}

	private void initShuaxin() {
		// TODO Auto-generated method stub
		shuaxin_linear=(LinearLayout)findViewById(R.id.shuaxin_linear);
		shuaxin_button=(Button)findViewById(R.id.shuaxin_button);
		shuaxin_button.setOnClickListener(this);
	}

	private void init() {
		// TODO Auto-generated method stub
		dialog.show();
		webview=(WebView)findViewById(R.id.webview);
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		WebSettings webSettings = webview.getSettings();
		//设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		//设置可以访问文件
		webSettings.setAllowFileAccess(true);
		//设置支持缩放
		//  webSettings.setBuiltInZoomControls(true);
		webview.loadUrl(pay_url);

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				pay_type=-1;
				if (url.equals("http://app_action/payment_close/")) {
					setResult(-1, getIntent());
					MyFlg.all_activitys.remove(PayVIPActivity.this);
					finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					return true;
				}else if (url.startsWith("http://app_action/set_action/")) {//购买结果
					try {
						String st = "http://app_action/set_action/";
						String json =zhuanyiUrl(url).substring(st.length());
						JSONObject jsonObject = new JSONObject(json);
						String type =jsonObject.getString("type");
						String action =jsonObject.getString("action");
						if(type.equals("back_button")&&action.equals("success")){
							pay_type=1;
							setResult(RESULT_OK);
						}
					} catch (JSONException e) {
						//e.printStackTrace();
					}
					return  true;
				}else if (url.startsWith("http://app_action/buys_fail/")) {//失败
					pay_Fail();
					return true;
				} else if (url.equals("http://app_action/payment_success/")) {
					pay_OK();
					return true;
				}else if (url.startsWith("http://app_action/buys_success/")) {//成功
					pay_OK();
					return true;
				}else if (url.startsWith("http://app_action/home/wxpay_recharge/")) {
					String st = "http://app_action/home/wxpay_recharge/";
					String json = url.substring(st.length());
					WeiXinPay(json,0);
					return true;
				}else if (url.startsWith("http://app_action/home/wxpay_recharge_buys/")) {
					String st = "http://app_action/home/wxpay_recharge_buys/";
					String json = url.substring(st.length());
					WeiXinPay(json,1);
					return true;
				} else if (url.startsWith("http://app_action/home/pay/")) {//单独支付
					/*next_url=MyFlg.get_API_URl(application.getCommonInfo_API_functions(WebViewActivity.this).getPay_adv_project(),WebViewActivity.this);
					next_url = next_url+"?a="+MyFlg.a+"&clientcode="+MyFlg.getclientcode(WebViewActivity.this)+"&ver="+MyFlg.android_version+"&id="+id;
					*/
					try {
						String st = "http://app_action/home/pay/";
						String json = url.substring(st.length()).replace("%22", "\"");
						json = json.replace("%7B", "{");
						json = json.replace("%7D", "}");
						String key = new JSONObject(json).getString("ios_key");
						webview.loadUrl(setJson("adv_project",key));
					} catch (JSONException e) {
						//e.printStackTrace();
						Toast.makeText(PayVIPActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
					}
					return true;
				}else if(url.startsWith("http://app_action/recharge_success/")) {
					pay_OK();
				}else if(url.startsWith("http://app_action/recharge_fail/")) {
					pay_Fail();
				}else if (url.startsWith("http://app_action/home/pay_secret_papers/")) {   //密卷支付成功
					try {
						String st = "http://app_action/home/pay_secret_papers/";
						String json = url.substring(st.length()).replace("%22", "\"");
						json = json.replace("%7B", "{");
						json = json.replace("%7D", "}");
						String key = new JSONObject(json).getString("ios_key");
						webview.loadUrl(setJson("adv_project", key));
					} catch (JSONException e) {
						//e.printStackTrace();
						Toast.makeText(PayVIPActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
					}
					return true;
				} else if (url.startsWith("http://app_action/home/secret_papers_info/")) {      //密卷详情
					setResult(RESULT_OK);
					//转编码
					try {
						String decode = URLDecoder.decode(url, "UTF-8");
						int first = decode.indexOf("{");
						int last = decode.lastIndexOf("}");
						JSONObject jsonObject = new JSONObject(decode.substring(first, last + 1));
						String apid = jsonObject.getString("id");
						Intent intent = new Intent(PayVIPActivity.this, JinJieDetailedActivity.class);
						intent.putExtra("pid", apid);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return true;
				} else if (url.startsWith("http://app_action/home/secret_papers_pay/")) {   //密卷支付
					//转编码
					try {
						String decode = URLDecoder.decode(url, "UTF-8");
						int first = decode.indexOf("{");
						int last = decode.lastIndexOf("}");
						JSONObject jsonObject = new JSONObject(decode.substring(first, last + 1));
						String apid = jsonObject.getString("id");
						webview.loadUrl(sharedPreferences.getString("adv_project_pay", "") + "?id=" + apid + "&type=a");

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override //加载失败
			public void onReceivedError(WebView view, int errorCode,
										String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				shuaxin_linear.setVisibility(View.VISIBLE);
				webview.setVisibility(View.GONE);
			}

			@Override //加载完成
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				if(SDK_Version<19){
					view.loadUrl("javascript:var p = document.getElementsByClassName('am-header')[0];p.style.display= 'none';" );
					view.loadUrl("javascript:var p2 = document.getElementsByClassName('top_div')[0];p2.style.display= 'none';" );
				}else{
					view.loadUrl("javascript:var script = document.createElement('script');script.type = 'text/javascript';script.text = \"function deleteHeader() { var header = document.getElementsByClassName('top_div')[0];header.remove();}\";document.getElementsByTagName('head')[0].appendChild(script);");
					view.loadUrl("javascript:deleteHeader();" );
					view.loadUrl("javascript:var script = document.createElement('script');script.type = 'text/javascript';script.text = \"function deleteHeader() { var header = document.getElementsByClassName('am-header')[0];header.remove();}\";document.getElementsByTagName('head')[0].appendChild(script);");
					view.loadUrl("javascript:deleteHeader();" );
				}
				super.onPageFinished(view, url);

			}
		});
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
				title_tx.setText(title);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					dialog.dismiss();
					webview.setVisibility(View.VISIBLE);
					if (webview.canGoBack()) {
						linear_right.setVisibility(View.VISIBLE);
					} else {
						linear_right.setVisibility(View.GONE);
					}
				}

			}
		});
	}
	public void pay_Fail(){ //失败
		setResult(-1, getIntent());
		MyFlg.all_activitys.remove(PayVIPActivity.this);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	public void pay_OK(){ //支付结果
		setResult(RESULT_OK);
		if (!TextUtils.isEmpty(id)) {
			Intent intent = new Intent(PayVIPActivity.this, JinJieDetailedActivity.class);
			intent.putExtra("pid", id);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			finish();
		}if (!TextUtils.isEmpty(Vid)) {
//			List<Node> mAllNodes = (List<Node>) getIntent().getSerializableExtra("allData");
//			List<Node> mAllNodes = SharedInfo.Getdatas();
			UniversalVideoActicity.intentTo(PayVIPActivity.this,
					UniversalVideoActicity.PlayType.vid,Vid, true,0+"",null,position);
			finish();
		}else {
			pay_success();
		}
	}
	//支付成功-刷新用户资料
	private void pay_success(){

		dialog.show();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("clientcode", MyFlg.getclientcode(PayVIPActivity.this)));
		params.add(new BasicNameValuePair("mobile_model", MyFlg.getmobile_model()));
		AsyncLoadApi asyncLoadApi = new AsyncLoadApi(PayVIPActivity.this, handler, params, "get_userinfo", 0, 1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(PayVIPActivity.this).getGet_userinfo(),PayVIPActivity.this));
		asyncLoadApi.execute();
	}

	/**
	 *
	 * @param json
	 * @param flg  0  充值  1 充值购买
     */
	private void WeiXinPay(String json,int flg){
		try {
			json=json.replace("%22","\"");
			json=json.replace("%7B","{");
			json=json.replace("%7D","}");
			JSONObject jsonObject = new JSONObject(json);
			String money = jsonObject.getString("money");
			String orders="";
			if(flg==1){
				orders = jsonObject.getString("orders");
			}
			if(WeiXinCanPay()){
				if(flg==0){
					getOrder(money);
				}else{
					getOrder_pay(money,orders);
				}
			}else{
				Toast.makeText(PayVIPActivity.this, "您的微信版本不支持支付", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			//e.printStackTrace();
			Toast.makeText(PayVIPActivity.this,"数据异常",Toast.LENGTH_SHORT).show();
		}
	}
	private Boolean WeiXinCanPay(){
		boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
		return isPaySupported;
	}
	private void getOrder_pay(String money,String orders){
		RequestParams params = new RequestParams();
		params.add("money", money);
		params.add("orders", orders);
		params.add("os", "Android");
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode",MyFlg.getclientcode(PayVIPActivity.this));
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(PayVIPActivity.this).getCreate_wxpay_recharge_buys_order(),PayVIPActivity.this);
		getJson(0, apiUrl, params,1);
	}
	private void getOrder(String money){
		RequestParams params = new RequestParams();
		params.add("money", money);
		params.add("os", "Android");
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode",MyFlg.getclientcode(PayVIPActivity.this));
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(PayVIPActivity.this).getCreate_wxpay_recharge_order(),PayVIPActivity.this);
		getJson(0, apiUrl, params,0);
	}
	private void getPayValues(WeiXinPayBean bean,int flg){
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode",MyFlg.getclientcode(PayVIPActivity.this));
		params.add("price",bean.getPrice());
		params.add("order_id", bean.getOrder_id());
		String apiUrl = "";
//		if(flg==0){

		if ("teacher".equals(BuildConfig.FLAVOR)) {
			apiUrl = "http://callback.mtiku.net/payment/wxpay/"+BuildConfig.MYAPP_KEY+"/example/pay_recharge.php";
		}else if ("yihu360".equals(BuildConfig.FLAVOR)){
			apiUrl = "http://callback.botian120.com/payment/wxpay/"+BuildConfig.MYAPP_KEY+"/example/pay_recharge.php";
		}else if ("xizhang".equals(BuildConfig.FLAVOR)){
			apiUrl = "http://callback.52zangyu.com/payment/wxpay/"+BuildConfig.MYAPP_KEY+"/example/pay_recharge.php";
		}

//		}else{
//			apiUrl = "http://callback.mtiku.net/payment/wxpay/"+BuildConfig.MYAPP_KEY+"/example/pay_recharge_buys.php";
//		}
		getJson(1, apiUrl, params,flg);
	}
	/**
	 *
	 * @param type  0获取订单   1 获取掉起支付参数
	 * @param Url
	 * @param flg (只对获取调起支付参数有用)   0 充值  1 充值购买
	 */
	private void getJson(final int type,String Url,RequestParams params,final int flg) {
		LogUtil.e(Url+"?"+params.toString());
		dialog.show();
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		client.post(Url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int i, Header[] headers, byte[] responseBody) {
				{
					dialog.dismiss();
					String json = "";
					if (type == 0) {
						json = Sup.UnZipString(responseBody);
						LogUtil.e("--------------"+json);
						Gson gson = new Gson();
						String result = JSONUtil.isNormalBoolen(PayVIPActivity.this, json);
						if (!result.equals("")) {
							WeiXinPayBean bean = new WeiXinPayBean();
							bean = gson.fromJson(result, WeiXinPayBean.class);
							getPayValues(bean,flg);
						}
					} else if (type == 1) {
						json = new String(responseBody);
						String result = JSONUtil.isNormalString(PayVIPActivity.this, json);
						if (!result.equals("")) {
							LogUtil.e("result---------"+result);
							try {
								JSONObject jsonObject = new JSONObject(result);
								api.registerApp(BuildConfig.WEIXIN_APPID);
								registorPayBroadCast();
								PayReq req = new PayReq();
								req.appId = jsonObject.getString("appid");
								req.partnerId = jsonObject.getString("partnerid");
								req.prepayId = jsonObject.getString("prepayid");
								req.nonceStr = jsonObject.getString("noncestr");
								req.timeStamp = jsonObject.getString("timestamp");
								req.packageValue = jsonObject.getString("package");
								req.sign = jsonObject.getString("paysign");
								Toast.makeText(PayVIPActivity.this, "正在调起支付，请稍后...", Toast.LENGTH_SHORT).show();
								// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
								api.registerApp(BuildConfig.WEIXIN_APPID);
								api.sendReq(req);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								Toast.makeText(PayVIPActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
				dialog.dismiss();
				Toast.makeText(PayVIPActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			}
		});
	}
	PayBroadCastRecevier mPayBroadCastRecevier;

	private void registorPayBroadCast() {
		if (mPayBroadCastRecevier != null){
			return;
		}
		// 生成广播处理
		mPayBroadCastRecevier = new PayBroadCastRecevier();
		// 实例化过滤器并设置要过滤的广播
		IntentFilter intentFilter = new IntentFilter(MyFlg.action);
		// 注册广播
		registerReceiver(mPayBroadCastRecevier, intentFilter);
	}


	class PayBroadCastRecevier extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final int code = intent.getIntExtra("code", BaseResp.ErrCode.ERR_COMM);
			Log.i("123", "原因: "+code);
			Log.i("123", "原因: "+code);
			switch (code) {
				case BaseResp.ErrCode.ERR_OK:
					Toast.makeText(PayVIPActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					pay_OK();
					break;
				case BaseResp.ErrCode.ERR_COMM:
					Toast.makeText(PayVIPActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					break;
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					Toast.makeText(PayVIPActivity.this, "取消支付", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 0://成功
					application.userInfoBean = APPUtil.getUser_isRegistered(PayVIPActivity.this);
					MyFlg.ISupdatePerson = true;
					setResult(RESULT_OK, getIntent());
					MyFlg.all_activitys.remove(PayVIPActivity.this);
					finish();
					overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
					dialog.dismiss();
					break;
				case 1://失败
					pay_success();
					break;
				default:
					break;
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			setBack();
			return  true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.shuaxin_button:
				dialog.show();
				shuaxin_linear.setVisibility(View.GONE);
				webview.loadUrl(pay_url+"?clientcode="+MyFlg.getclientcode(PayVIPActivity.this));
				break;
			case R.id.linear_Left:
				setBack();
				break;
			case R.id.linear_right:
				if(pay_type==1) {
					pay_OK();
				}else{
					MyFlg.all_activitys.remove(PayVIPActivity.this);
					finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				}
				break;
			default:
				break;
		}
	}
	private void setBack(){
		if(pay_type==1) {
			pay_OK();
		}else{
			if(webview.canGoBack())
			{
				webview.goBack();//返回上一页面
			}
			else
			{
				thisfinish();
			}
		}

	}
	private void thisfinish(){
		MyFlg.all_activitys.remove(PayVIPActivity.this);
		finish();
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}
	@Override
	protected void onDestroy() {
		unregisterPayBroadCast();
		super.onDestroy();
	}

	private void unregisterPayBroadCast() {
		if (mPayBroadCastRecevier != null) {
			unregisterReceiver(mPayBroadCastRecevier);
		}
	}

	/**
	 * JSON 设置
	 * @param type
	 * @param itemid
	 * @return
	 * @throws JSONException
	 */
	private String setJson(String type,String itemid) throws JSONException {
		next_url = application.buys;
						/*next_url = next_url + "?os=Android&key=adv_project_" + key + "&clientcode=" + MyFlg.getclientcode(PayVIPActivity.this);
						webview.loadUrl(next_url);*/

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type",type);
		jsonObject.put("itemid",itemid);

		JSONArray temp_array = new JSONArray();
		temp_array.put(jsonObject);

		JSONObject temp_json = new JSONObject();
		if(application.get_user_baseinfo!=null){
			temp_json.put("orderid",System.currentTimeMillis()+"_"+application.get_user_baseinfo.getUid());
		}else{
			temp_json.put("orderid",System.currentTimeMillis());
		}
		temp_json.put("items",temp_array);

		next_url = next_url + "?orders="+temp_json.toString()+ "&clientcode=" + MyFlg.getclientcode(PayVIPActivity.this)+"&os=Android";

		return next_url;
	}

	/**
	 * 转义
	 * @param url
	 * @return
	 */
	private String zhuanyiUrl(String url){
		url = url.replace("%7B", "{");
		url = url.replace("%7D", "}");
		url=url.replace("%22","\"");
		return url;
	}
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}
}
