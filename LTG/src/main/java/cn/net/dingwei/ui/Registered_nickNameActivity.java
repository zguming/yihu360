package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncGet_user_baseinfo;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.Bean.UserinfoBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.GradientDrawableUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Registered_nickNameActivity extends Exit_parentActivity implements OnClickListener{
	private String TAG = "Registered_nickNameActivity";
	private EditText nickName;
	private EditText password_one;
	private EditText password_two;
	private myHandler handler=new myHandler();
	private EditText phoneNumber;
	private EditText authCode;
	private LinearLayout nickLinear;
	private LinearLayout phoneLinear;

	private int color_font;
	private int color_font1;//按钮字体色
	private int color_btnBg;
	private TextView text,zc_sjts; //顶部信息
	private FrameLayout regis_frame;
	private FYuanTikuDialog dialog;
	private myCountDownTimer countDownTimer = new myCountDownTimer(60000, 1000);
	private Button getAuthCode;
	private MyApplication application;
	private int type=0;//0 调用更新用户接口 1get_user_baseinfo接口

	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0;
	private SharedPreferences sp_commoninfo;
	private Placeholder_textBean placeholder_textBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_registered_nickname);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);

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

		init();
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
	}

	private void init() {
		// TODO Auto-generated method stub

		color_font = Bgcolor_2;
		color_font1 =  Fontcolor_1;
		color_btnBg =  Bgcolor_2;

		text = (TextView)findViewById(R.id.text);
		text.setTextColor(Fontcolor_1);
		text.setBackgroundColor(Bgcolor_1);
		TextView tx1 = (TextView)findViewById(R.id.tx1);
		TextView tx2 = (TextView)findViewById(R.id.tx2);
		TextView tx3 = (TextView)findViewById(R.id.tx3);
		TextView tx4 = (TextView)findViewById(R.id.tx4);
		TextView tx5 = (TextView)findViewById(R.id.tx5);
		TextView loging=(TextView)findViewById(R.id.loging);
		TextView loging2=(TextView)findViewById(R.id.loging2);

		zc_sjts = (TextView)findViewById(R.id.zc_sjts);
		//loging.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		//loging2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		nickLinear = (LinearLayout)findViewById(R.id.nickLinear);
		phoneLinear = (LinearLayout)findViewById(R.id.phoneLinear);
		LinearLayout linear_phone_verify = (LinearLayout)findViewById(R.id.linear_phone_verify);
		linear_phone_verify.setBackgroundDrawable(MyFlg.setViewRaduis(Color.WHITE, Bgcolor_2, 1, 15));
		//

		tx1.setTextColor(color_font);
		tx2.setTextColor(color_font);
		tx3.setTextColor(color_font);
		tx4.setTextColor(color_font);
		tx5.setTextColor(color_font);
		loging.setTextColor(Bgcolor_2);
		loging2.setTextColor(Bgcolor_2);
		zc_sjts.setTextColor(Fontcolor_7);
		if(null!=placeholder_textBean && null!=placeholder_textBean.getZc_sjts()){
			zc_sjts.setText(placeholder_textBean.getZc_sjts());
		}
		setUI();

		loging.setOnClickListener(this);
		loging2.setOnClickListener(this);
	}
	//注册昵称
	private void RegisteredNick() {
		// TODO Auto-generated method stub
		nickLinear.setVisibility(View.VISIBLE);
		phoneLinear.setVisibility(View.GONE);
		nickName = (EditText)findViewById(R.id.nickName);
		password_one = (EditText)findViewById(R.id.password_one);
		password_two = (EditText)findViewById(R.id.password_two);

		nickName.setTextColor(Bgcolor_2);
		password_one.setTextColor(Bgcolor_2);
		password_two.setTextColor(Bgcolor_2);
		nickName.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		password_one.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		password_two.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		int dip_pix = DensityUtil.DipToPixels(this, 10);
		nickName.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		password_one.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		password_two.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		//设置提示文字
		if(null!=placeholder_textBean){
			nickName.setHint(placeholder_textBean.getZc_nc());
			password_one.setHint(placeholder_textBean.getZc_xmm());
			password_two.setHint(placeholder_textBean.getZc_cfxmm());
			password_one.setHintTextColor(Fontcolor_7);
			password_two.setHintTextColor(Fontcolor_7);
			nickName.setHintTextColor(Fontcolor_7);
		}


		Button next = (Button)findViewById(R.id.next);
		next.setOnClickListener(this);
		next.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(0, color_btnBg, color_btnBg, 10));
		next.setTextColor(color_font1);
		//next.setPadding(dip_pix, dip_pix, dip_pix,dip_pix );
	}
	//获取手机验证码
	private void getAuthCode(){
		text.setText("手机验证");
		nickLinear.setVisibility(View.GONE);
		phoneLinear.setVisibility(View.VISIBLE);
		phoneNumber = (EditText)findViewById(R.id.phoneNumber);
		authCode = (EditText)findViewById(R.id.authCode);
		LinearLayout linear_phone_verify = (LinearLayout)findViewById(R.id.linear_phone_verify);

		linear_phone_verify.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		authCode.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		int dip_pix = DensityUtil.DipToPixels(this, 10);
		authCode.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		phoneNumber.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		if(null!=placeholder_textBean){
			phoneNumber.setHint(placeholder_textBean.getZc_sjh());
			authCode.setHint(placeholder_textBean.getZc_yzm());
			phoneNumber.setHintTextColor(Fontcolor_7);
			authCode.setHintTextColor(Fontcolor_7);
		}
		phoneNumber.setTextColor(Bgcolor_2);
		authCode.setTextColor(Bgcolor_2);

		getAuthCode = (Button)findViewById(R.id.getAuthCode);
		Button start = (Button)findViewById(R.id.start_button);
		start.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(0, color_btnBg, color_btnBg, 10));
		getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, DensityUtil.DipToPixels(this, 5)));
		getAuthCode.setTextColor(color_font1);
		start.setTextColor(color_font1);
		getAuthCode.setOnClickListener(this);
		start.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.loging:
				intent = new Intent(Registered_nickNameActivity.this, LogingActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				MyFlg.all_activitys.remove(Registered_nickNameActivity.this);
				finish();
				break;
			case R.id.loging2:
				intent = new Intent(Registered_nickNameActivity.this, LogingActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				MyFlg.all_activitys.remove(Registered_nickNameActivity.this);
				finish();
				break;
			case R.id.next:
				if(nickName.getText().toString().trim().length()<=0){
					Toast.makeText(Registered_nickNameActivity.this, "请填写昵称", Toast.LENGTH_SHORT).show();
				}else if(nickName.getText().toString().trim().length()<2 || nickName.getText().toString().trim().length()>12){
					Toast.makeText(Registered_nickNameActivity.this, "请填写正确的昵称（2-12字符之间）", Toast.LENGTH_SHORT).show();
				}else if(password_one.getText().toString().trim().length()<=0){
					Toast.makeText(Registered_nickNameActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
				}else if(password_two.getText().toString().trim().length()<=0){
					Toast.makeText(Registered_nickNameActivity.this, "请填写重复密码", Toast.LENGTH_SHORT).show();
				}else if(password_one.getText().toString().trim().length()>16 || password_one.getText().toString().trim().length() <6 ||password_two.getText().toString().trim().length()>16 ||password_two.getText().toString().trim().length() <6){
					Toast.makeText(Registered_nickNameActivity.this, "密码应为6-16位字符", Toast.LENGTH_SHORT).show();
				}else if(!password_one.getText().toString().trim().equals(password_two.getText().toString().trim())){
					Toast.makeText(Registered_nickNameActivity.this, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
					password_one.setText("");
					password_two.setText("");
				}else{
					dialog.show();
					UpdateUserAsync updateUserAsync = new UpdateUserAsync(dialog,handler, Registered_nickNameActivity.this, MyFlg.getclientcode(Registered_nickNameActivity.this), null, nickName.getText().toString(), null, null, null, null, password_one.getText().toString(),null, false, 5,null,null,null,null,null);
					updateUserAsync.execute();
				}
				break;
			case R.id.getAuthCode://获取验证码
				String phonenumber = phoneNumber.getText().toString().trim();
				if(!isMobileNO(phonenumber)){
					Toast.makeText(Registered_nickNameActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
				}else{//开始调用验证码接口
					//progressBar_linear.setVisibility(View.VISIBLE);
					getAuthCode.setEnabled(false);
					countDownTimer.start();
					//getAuthCode.setBackgroundDrawable(setRaduis2());
					getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Color_3, Color_3, 1, DensityUtil.DipToPixels(Registered_nickNameActivity.this,5)));
					List <NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("a",MyFlg.a));
					params.add(new BasicNameValuePair("ver",MyFlg.android_version));
					params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Registered_nickNameActivity.this)));
					params.add(new BasicNameValuePair("mobile",phonenumber));
					AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Registered_nickNameActivity.this, handler, params, "send_mobile_verificationcode", 9, 10,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Registered_nickNameActivity.this).getSend_mobile_verificationcode(),Registered_nickNameActivity.this));
					asyncLoadApi.execute();
				}
				break;
			case R.id.start_button://开始
				if(type==0){
					String phonenumber2 = phoneNumber.getText().toString().trim();
					String authcode = authCode.getText().toString().trim();
					if(!isMobileNO(phonenumber2)){
						Toast.makeText(Registered_nickNameActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
					}else if(authcode.length()<=0){
						Toast.makeText(Registered_nickNameActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
					}else{
						dialog.show();
						UpdateUserAsync async = new UpdateUserAsync(dialog,handler, Registered_nickNameActivity.this, MyFlg.getclientcode(Registered_nickNameActivity.this), null, null, phonenumber2, null, null, null, null,authcode, false, 6,null,null,null,null,null);
						async.execute();
					}
				}else if(type==1){
					AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(Registered_nickNameActivity.this, handler,false,0,1);
					asyncGet_user_baseinfo.execute();
				}
				break;
			default:
				break;
		}
	}

	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			Intent intent;
			switch (msg.what) {
				case 0://get_user_baseinfo加载成功
					intent = new Intent(Registered_nickNameActivity.this, HomeActivity2.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("isLoadHome", true);
					intent.putExtra("isShowLeft", true);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					MyFlg.all_activitys.remove(Registered_nickNameActivity.this);
					Registered_nickNameActivity.this.finish();
					break;
				case 1://get_user_baseinfo加载失败
					dialog.dismiss();
					type=1;
					break;
				case 5: //昵称设置成功
					setUI();
					break;
				case 6:

					//加载get_user_baseinfo
					AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(Registered_nickNameActivity.this, handler,false,0,1);
					asyncGet_user_baseinfo.execute();
				/*dialog.dismiss();
				UtilBean.userInfoBean = APPUtil.getUser_isRegistered(Registered_nickNameActivity.this);
				intent = new Intent(Registered_nickNameActivity.this, HomeActivity2.class);
				intent.putExtra("isShowLeft", true);
				intent.putExtra("isLoadHome", true);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				Registered_nickNameActivity.this.finish();*/

					break;
				case 10://短信验证码获取失败
					countDownTimer.cancel();
					getAuthCode.setEnabled(true);
					//getAuthCode.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(0, color_btnBg, color_btnBg, 10));
					getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, DensityUtil.DipToPixels(Registered_nickNameActivity.this, 5)));
					getAuthCode.setText("重新获取验证码");
					break;
				case 999://更新用户数据失败
					type=0;
				default:
					break;
			}
		}
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
		if (TextUtils.isEmpty(mobiles)) 
			return false;
		else 
			return mobiles.matches(telRegex);*/
		if(mobiles.length()==11){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 设置圆角按钮
	 *//*
	private Drawable setRaduis(){
		GradientDrawable gd = new GradientDrawable();//创建drawable
	    gd.setColor(color_btnBg);
	    gd.setCornerRadius(10);
	    gd.setStroke(1, color_btnBg);
		return gd;
	}*/
	/**
	 * 设置圆角按钮
	 */
	private Drawable setRaduis2(){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(getResources().getColor(R.color.erweima_wait));
		gd.setCornerRadius(10);
		gd.setStroke(1, getResources().getColor(R.color.erweima_wait));
		return gd;
	}
	/**
	 * 设置圆角编辑框
	 */
	private Drawable setRaduisEdtext(){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(Color.WHITE);
		gd.setCornerRadius(10);
		gd.setStroke(1, color_btnBg);
		return gd;
	}
	/**
	 * 判断显示哪个UI界面
	 */
	private void setUI(){
		UserinfoBean bean = APPUtil.getUser_isRegistered(this);
		if(null==bean){
			RegisteredNick();
			zc_sjts.setVisibility(View.GONE);
			return;
		}
		if(bean.getNickname().equals("null")){

			RegisteredNick();
			zc_sjts.setVisibility(View.GONE);
		}else if(bean.getMobile().equals("null")){

			getAuthCode();
			zc_sjts.setVisibility(View.VISIBLE);
		}else{
			//加载get_user_baseinfo
			AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(this, handler,false,0,1);
			asyncGet_user_baseinfo.execute();
			//RegisteredNick();
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

	class myCountDownTimer  extends CountDownTimer{

		public myCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			getAuthCode.setEnabled(true);
			getAuthCode.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, DensityUtil.DipToPixels(Registered_nickNameActivity.this, 5)));
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
}
