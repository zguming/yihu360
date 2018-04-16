package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.net.dingwei.AsyncUtil.AsyncGet_user_baseinfo;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * web
 */
public class WebViewActivity extends ParentActivity implements OnClickListener{
	private FYuanTikuDialog dialog;
	private LinearLayout shuaxin_linear;
	private Button shuaxin_button;
	private WebView webview;
	private String load_url;
	private RelativeLayout title_bg;
	private LinearLayout linear_Left,linear_right;
	private TextView title_tx;
	private myHandler handler = new myHandler();
	private SharedPreferences sharedPreferences;
	private String adv_project_pay = "";
	//单独支付id
	private String id="";
	private String next_url="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_webview);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		initID();
		initShuaxin();
		init();
	}
	private void initID() {
		// TODO Auto-generated method stub
		webview=(WebView)findViewById(R.id.webview);
		title_bg =(RelativeLayout)findViewById(R.id.title_bg);
		title_bg.setBackgroundColor(getResources().getColor(R.color.bgcolor_1));
		linear_Left= (LinearLayout)findViewById(R.id.linear_Left);
		linear_right= (LinearLayout)findViewById(R.id.linear_right);
		title_tx= (TextView)findViewById(R.id.title_tx);
		sharedPreferences = getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		adv_project_pay = sharedPreferences.getString("adv_project_pay", "");
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
		//获取链接
		Intent intent = getIntent();
		load_url=intent.getStringExtra("url");
		id = intent.getStringExtra("id");
		if(null!=id&&!"".equals(id)){
			title_bg.setVisibility(View.GONE);
		}
		WebSettings webSettings = webview.getSettings();
		//设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		//设置可以访问文件
		webSettings.setAllowFileAccess(true);
		//设置支持缩放
		//  webSettings.setBuiltInZoomControls(true);
		webview.loadUrl(load_url);
		webview.setWebViewClient(new WebViewClient(){
			@Override //加载的链接
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Log.i("mylog", "url=" + url);
				if(url.equals("http://app_action/feedback_success/")){ //点击了返回
					MyFlg.all_activitys.remove(WebViewActivity.this);
					finish();
					overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
					return true;
				}else if(url.startsWith("http://app_call/phone/number/")){//打电话
					String tel = url.substring(url.indexOf("number/")+7, url.length());
					if(null!=tel&&tel.length()>0){
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tel));
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					}
					return true;
				}else if(url.startsWith("http://app_action/home/pay")){//单独支付
					MyApplication application = MyApplication.myApplication;
					/*next_url=MyFlg.get_API_URl(application.getCommonInfo_API_functions(WebViewActivity.this).getPay_adv_project(),WebViewActivity.this);
					next_url = next_url+"?a="+MyFlg.a+"&clientcode="+MyFlg.getclientcode(WebViewActivity.this)+"&ver="+MyFlg.android_version+"&id="+id;
					*/
					next_url=application.choose_pay;
					next_url=next_url+"?os=Android&key=adv_project_"+"1"+"&clientcode="+MyFlg.getclientcode(WebViewActivity.this);
					webview.loadUrl(next_url);
					return true;
				}else if(url.equals("http://app_action/payment_close/")){
					finish();
					return true;
				}else if(url.equals("http://app_action/payment_success/")){
					setResult(RESULT_OK);
					Intent intent = new Intent(WebViewActivity.this, JinJieDetailedActivity.class);
					intent.putExtra("pid", id);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					finish();
					return true;
				}else if (url.startsWith("http://app_action/home/update_city")) {
					try {
						//转编码
						String decode = URLDecoder.decode(url, "UTF-8");
						int first = decode.indexOf("{");
						int last = decode.lastIndexOf("}");
						JSONObject jsonObject = new JSONObject(decode.substring(first, last + 1));
						String keyCity = (String) jsonObject.get("update_city");
						dialog.show();
						UpdateUserAsync async = new UpdateUserAsync(dialog, handler, WebViewActivity.this, MyFlg.getclientcode
								(WebViewActivity.this), null, null, null, null, null, null, null, null, true, 6, null, null, keyCity,
								null, null);
						async.execute();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return true;
				} else if (url.startsWith("http://app_action/home/secret_papers_info/")) {  //密卷详情
					setResult(RESULT_OK);
					//转编码
					try {
						String decode = URLDecoder.decode(url, "UTF-8");
						int first = decode.indexOf("{");
						int last = decode.lastIndexOf("}");
						JSONObject jsonObject = new JSONObject(decode.substring(first, last + 1));
						String apid =  jsonObject.getString("id");
						Intent intent = new Intent(WebViewActivity.this, JinJieDetailedActivity.class);
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
						String apid =  jsonObject.getString("id");
						Intent intent = new Intent(WebViewActivity.this, PayVIPActivity.class);
						intent.putExtra("url", adv_project_pay + "?id=" + apid + "&type=a");
						intent.putExtra("id", apid);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivityForResult(intent, 0);//REQUESTCODE定义一个整型做为请求对象标识
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
				super.onPageFinished(view, url);

			}
		});
		webview.setWebChromeClient(new WebChromeClient(){
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
				if(newProgress==100){
					dialog.dismiss();
					webview.setVisibility(View.VISIBLE);
					if(webview.canGoBack()){
						linear_right.setVisibility(View.VISIBLE);
					}else{
						linear_right.setVisibility(View.GONE);
					}
				}
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			setBack();
			return false;
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
				webview.loadUrl(load_url);
				break;
			case R.id.linear_Left:
				setBack();
				break;
			case R.id.linear_right:
				MyFlg.all_activitys.remove(WebViewActivity.this);
				finish();
				overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
				break;
			default:
				break;
		}
	}
	private void setBack(){
		if(webview.canGoBack()&&(null==id||"".equals(id)))
		{
			webview.goBack();//返回上一页面
		}
		else
		{
			MyFlg.all_activitys.remove(WebViewActivity.this);
			finish();
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
		}
	}
	class myHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Intent intent;
			switch (msg.what) {
				case 0://get_user_baseinfo加载成功
					Toast.makeText(WebViewActivity.this, "切换报考地区成功", Toast.LENGTH_SHORT).show();
					intent = new Intent(WebViewActivity.this, HomeActivity2.class);
					intent.putExtra("isLoadHome", true);
					intent.putExtra("isShowLeft", true);
					WebViewActivity.this.finish();
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					break;
				case 1://get_user_baseinfo加载失败
					dialog.dismiss();
					break;
				case 6:
					//加载get_user_baseinfo
					AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(WebViewActivity.this, handler, false, 0, 1);
					asyncGet_user_baseinfo.execute();
					break;
				default:
					break;
			}
		}
	}
}
