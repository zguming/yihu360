package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncGet_user_baseinfo;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.GradientDrawableUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
/**
 * 找回密码 或完善资料  FLG 决定
 * @author Administrator
 *
 */
public class Rest_passwordActivity extends Activity implements OnClickListener{
	private ImageView title_back;
	private EditText phoneNumber,authCode,password1,password2;
	private Button getAuthCode;
	private Button submit_btn;
	private myhandler handler = new myhandler();
	private myCountDownTimer countDownTimer = new myCountDownTimer(60000, 1000);
	private FYuanTikuDialog dialog;
	private MyApplication application;
	private int type=0;//0 调用忘记密码接口 1 调用城市接口  2 调用get_user_baseinfo接口
	private SharedPreferences sharedPreferences;
	private int Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0;

	private SharedPreferences sp_commoninfo;
	private Placeholder_textBean placeholder_textBean;


	private String flg="";
	private String isShow="";//是否需要显示新用户注册
	private String isNeddSinIn="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_forget_password);

		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");

		sp_commoninfo= getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		Gson gson = new Gson();
		try {
			placeholder_textBean = gson.fromJson(new JSONObject(sp_commoninfo.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initID();
	}

	private void initID() {
		// TODO Auto-generated method stub


		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		title_back=(ImageView)findViewById(R.id.title_back);
		TextView tx1 = (TextView)findViewById(R.id.tx1);
		TextView tx2 = (TextView)findViewById(R.id.tx2);
		TextView tx3 = (TextView)findViewById(R.id.tx3);
		TextView tx4 = (TextView)findViewById(R.id.tx4);
		TextView   text_title= (TextView)findViewById(R.id.text_title);
		TextView text_loging = (TextView)findViewById(R.id.text_loging);
		phoneNumber = (EditText)findViewById(R.id.phoneNumber);
		authCode = (EditText)findViewById(R.id.authCode);
		password1 = (EditText)findViewById(R.id.password1);
		password2 = (EditText)findViewById(R.id.password2);


		phoneNumber.setTextColor(Bgcolor_2);
		authCode.setTextColor(Bgcolor_2);
		password1.setTextColor(Bgcolor_2);
		password2.setTextColor(Bgcolor_2);
		getAuthCode = (Button)findViewById(R.id.getAuthCode);
		submit_btn = (Button)findViewById(R.id.submit_btn);
		LinearLayout linear_phone_verify = (LinearLayout)findViewById(R.id.linear_phone_verify);

		//颜色
		phoneNumber.setHintTextColor(Fontcolor_7);
		authCode.setHintTextColor(Fontcolor_7);
		password1.setHintTextColor(Fontcolor_7);
		password2.setHintTextColor(Fontcolor_7);

		tx1.setTextColor(Bgcolor_2);
		tx2.setTextColor(Bgcolor_2);
		tx3.setTextColor(Bgcolor_2);
		tx4.setTextColor(Bgcolor_2);
		submit_btn.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Bgcolor_2, 10));
		authCode.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		password1.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		password2.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		linear_phone_verify.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, DensityUtil.DipToPixels(this, 5)));
		phoneNumber.setTextColor(Bgcolor_2);
		authCode.setTextColor(Bgcolor_2);
		password1.setTextColor(Bgcolor_2);
		password2.setTextColor(Bgcolor_2);
		//事件
		getAuthCode.setOnClickListener(this);
		submit_btn.setOnClickListener(this);
		title_back.setOnClickListener(this);

		//数据
		Intent intent = getIntent();
		flg = intent.getStringExtra("flg");
		isShow=intent.getStringExtra("isShow");
		isNeddSinIn= intent.getStringExtra("isNeddSinIn");
		phoneNumber.setText(intent.getStringExtra("phone"));
		if(null!=flg&&flg.equals("1")){
			tx3.setText("登录密码");
			tx4.setText("重复密码");
			text_title.setText("绑定手机号");
			if(null!=isNeddSinIn&&isNeddSinIn.equals("0")){
				text_loging.setVisibility(View.GONE);
			}else{
				text_loging.setVisibility(View.VISIBLE);
			}

			text_loging.setTextColor(Bgcolor_2);
			text_loging.setOnClickListener(this);
			if(null!=placeholder_textBean){
				phoneNumber.setHint(placeholder_textBean.getYz_sjh());
				authCode.setHint(placeholder_textBean.getYz_yzm());
				password1.setHint(placeholder_textBean.getZc_xmm());
				password2.setHint(placeholder_textBean.getZc_cfxmm());
			}
		}else{
			if(null!=placeholder_textBean){
				phoneNumber.setHint(placeholder_textBean.getWjmm_sjh());
				authCode.setHint(placeholder_textBean.getWjmm_yzm());
				password1.setHint(placeholder_textBean.getWjmm_xmm());
				password2.setHint(placeholder_textBean.getWjmm_cfxmm());
			}
		}
	}

	@Override
	public void onClick(View v) {
		String phonenumber="";
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.getAuthCode:

				phonenumber = phoneNumber.getText().toString().trim();
				if(!isMobileNO(phonenumber)){
					Toast.makeText(Rest_passwordActivity.this, "请填写正确的手机号", 0).show();
				}else{//开始调用验证码接口
					getAuthCode.setEnabled(false);
					countDownTimer.start();
					int dip_15 = DensityUtil.DipToPixels(Rest_passwordActivity.this, 15);
					getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Color_3, Color_3, 1, DensityUtil.DipToPixels(Rest_passwordActivity.this, 10)));
					getAuthCode.setPadding(dip_15, dip_15/3, dip_15, dip_15/3);
					List <NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("a",MyFlg.a));
					params.add(new BasicNameValuePair("ver",MyFlg.android_version));
					params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Rest_passwordActivity.this)));
					params.add(new BasicNameValuePair("mobile",phonenumber));
					if(null!=flg&&flg.equals("1")){//完善资料
						AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Rest_passwordActivity.this, handler, params, "send_mobile_verificationcode", 9, 10,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Rest_passwordActivity.this).getSend_mobile_verificationcode(),Rest_passwordActivity.this));
						asyncLoadApi.execute();
					}else{
						AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Rest_passwordActivity.this, handler, params, "forget_password_verificationcode", 9, 10,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Rest_passwordActivity.this).getForget_password_verificationcode(),Rest_passwordActivity.this));
						asyncLoadApi.execute();
					}

				}
				break;
			case R.id.submit_btn://提交按钮
				phonenumber = phoneNumber.getText().toString().trim();
				String code = authCode.getText().toString().trim();
				String pass1 = password1.getText().toString().trim();
				String pass2 = password2.getText().toString().trim();
				if(!isMobileNO(phonenumber)){
					Toast.makeText(Rest_passwordActivity.this, "请填写正确的手机号", 0).show();
					return;
				}else if(code.length()<=0){
					Toast.makeText(Rest_passwordActivity.this, "请填写验证码", 0).show();
					return;
				}else if(pass1.length() >16 ||pass1.length()<6 || pass2.length()>16 ||pass2.length()<6){
					Toast.makeText(Rest_passwordActivity.this, "密码应为6-16位字符", 0).show();
					return;
				}else if(!pass1.equals(pass2)){
					Toast.makeText(Rest_passwordActivity.this, "两次密码不一致,请重新输入", 0).show();
					return;
				}
				if(null!=flg&&flg.equals("1")){//完善资料
					dialog.show();
					UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, handler, Rest_passwordActivity.this, MyFlg.getclientcode(Rest_passwordActivity.this), null, null, phonenumber, null, null, null, pass1, code, true, 1000, null, null, null,"0",null);
					updateUserAsync.execute();
				}else{
					if(type==0){//忘记密码
						dialog.show();
						List <NameValuePair> params=new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("a",MyFlg.a));
						params.add(new BasicNameValuePair("ver",MyFlg.android_version));
						params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Rest_passwordActivity.this)));
						params.add(new BasicNameValuePair("mobile",phonenumber));
						params.add(new BasicNameValuePair("mobile_verificationcode",code));
						params.add(new BasicNameValuePair("newpassword",pass1));
						AsyncLoadApi asyncLoadApi = new AsyncLoadApi("get_userinfo", Rest_passwordActivity.this, handler, params, "forget_password_submit", 99, 100,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Rest_passwordActivity.this).getForget_password_submit(),Rest_passwordActivity.this));
						asyncLoadApi.execute();
					}else if(type==1){//城市
						get_baseinfo();
					}else if(type==2){//get_user_baseinfo

					}
				}
				break;
			case R.id.title_back:
				if(null!=flg&&flg.equals("1")){

				}else{
					Intent intent2 = new Intent(Rest_passwordActivity.this, LogingActivity.class);
					intent2.putExtra("user_phone", phoneNumber.getText().toString());
					if(null!=isShow&&isShow.equals("1")){
						intent2.putExtra("flg", isShow);
					}
					intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent2);
				}
				Rest_passwordActivity.this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
				MyFlg.all_activitys.remove(Rest_passwordActivity.this);
				finish();
				break;
			case R.id.text_loging://跳转登录
				Intent intent2 = new Intent(Rest_passwordActivity.this, LogingActivity.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent2.putExtra("flg", "1");
				startActivity(intent2);
				finish();
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			default:
				break;
		}
	}
	private void get_user_baseinfo(){//get_user_baseinfo接口
		AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(Rest_passwordActivity.this, handler,false,0,1);
		asyncGet_user_baseinfo.execute();
	}
	private void get_baseinfo(){//调用城市接口
		application.userInfoBean = APPUtil.getUser_isRegistered(Rest_passwordActivity.this);
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("city",MyApplication.getuserInfoBean(Rest_passwordActivity.this).getCity()));
		AsyncLoadApi loadApi = new AsyncLoadApi(Rest_passwordActivity.this, handler, params, "get_baseinfo", 3, 4,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Rest_passwordActivity.this).getGet_baseinfo(),Rest_passwordActivity.this));
		loadApi.execute();
	}
	//调用顺序  忘记密码 →城市信息→get_user_baseinfo
	class myhandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 0://get_user_baseinfo 成功
					application.guideBean = APPUtil.getGuide(Rest_passwordActivity.this);
					Intent intent = new Intent(Rest_passwordActivity.this, HomeActivity2.class);
					intent.putExtra("isLoadHome", true);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					MyFlg.all_activitys.remove(Rest_passwordActivity.this);
					Rest_passwordActivity.this.finish();
					break;
				case 1://get_user_baseinfo 失败
					type=2;
					dialog.dismiss();
					break;
				case 99://忘记密码接口调用成功
					//dialog.dismiss();
					get_baseinfo();
					break;
				case 100://忘记密码接口调用失败
					dialog.dismiss();
					type=0;
					break;
				case 3://获取城市信息成功
					get_user_baseinfo();
					break;
				case 4://获取城市信息 失败
					dialog.dismiss();
					type=1;
					break;
				case 10://验证码获取失败
					countDownTimer.cancel();
					getAuthCode.setEnabled(true);
					getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Color_3, 1, DensityUtil.DipToPixels(Rest_passwordActivity.this,5)));
					getAuthCode.setText("重新获取验证码");
					break;
				case 1000://完善资料OK
					setResult(RESULT_OK);
					finish();
					break;
				case 999:
					break;
				default:
					break;
			}
		}
	}
	//点击屏幕 关闭软键盘
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	/**
	 * 验证手机格式
	 */
	public  boolean isMobileNO(String mobiles) {
			/*
			移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
			联通：130、131、132、152、155、156、185、186
			电信：133、153、180、189、（1349卫通）
			总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
			*/
			/*String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
			if (TextUtils.isEmpty(mobiles)) return false;
			else return mobiles.matches(telRegex);*/
		if(mobiles.length()==11){
			return true;
		}else{
			return false;
		}
	}
	class myCountDownTimer  extends CountDownTimer{

		public myCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			getAuthCode.setEnabled(true);
			getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, DensityUtil.DipToPixels(Rest_passwordActivity.this,5)));
			getAuthCode.setText("重新获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			if((millisUntilFinished / 1000)<10){
				getAuthCode.setText("请等待(" + millisUntilFinished / 1000 + ")");
			}else{
				getAuthCode.setText("请等待(" + millisUntilFinished / 1000 + ")");
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(null!=flg&&flg.equals("1")){

			}else{
				Intent intent2 = new Intent(Rest_passwordActivity.this, LogingActivity.class);
				intent2.putExtra("user_phone", phoneNumber.getText().toString());
				if(null!=isShow&&isShow.equals("1")){
					intent2.putExtra("flg", isShow);
				}
				intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent2);
			}
			Rest_passwordActivity.this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(Rest_passwordActivity.this);
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
