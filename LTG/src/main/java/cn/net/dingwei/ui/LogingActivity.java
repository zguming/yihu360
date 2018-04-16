package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncGet_user_baseinfo;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi_user;
import cn.net.dingwei.AsyncUtil.AsyncLoad_baseInfo;
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
 * 登录
 * @author Administrator
 *
 */
public class LogingActivity extends BackActivity implements OnClickListener{

	private int color_font,color_font1,color_btnBg;
	private EditText phone;
	private EditText password;
	private Button loging;
	private myHandler myHandler=new myHandler();
	private TextView loging_text;
	private FYuanTikuDialog dialog;
	private MyApplication application;
	private int type=0;//0 登录 1 调用城市接口  2 调用get_user_baseinfo接口
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0;

	private SharedPreferences sp_commoninfo;
	private Placeholder_textBean placeholder_textBean;
	private String flg="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_loging);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);

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
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"登录中");

	}

	private void initID() {
		// TODO Auto-generated method stub
		color_font  = Bgcolor_2;
		color_font1 = Fontcolor_1;
		color_btnBg =  Bgcolor_2;
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		TextView title_tx = (TextView)findViewById(R.id.title_tx);
		title_tx.setText("用户登录");
		//TextView text = (TextView)findViewById(R.id.text);
		TextView tx1 = (TextView)findViewById(R.id.tx1);
		TextView tx2 = (TextView)findViewById(R.id.tx2);
		TextView forget_password = (TextView)findViewById(R.id.forget_password);
		phone = (EditText)findViewById(R.id.phone);
		password = (EditText)findViewById(R.id.password);
		if(null!=placeholder_textBean){
			phone.setHint(placeholder_textBean.getDl_sjh());
			password.setHint(placeholder_textBean.getDl_mm());
			phone.setHintTextColor(Fontcolor_7);
			password.setHintTextColor(Fontcolor_7);
		}

		loging =(Button)findViewById(R.id.loging);
		loging_text=(TextView)findViewById(R.id.loging_text);
		// text.setTextColor(Color.WHITE);
		//text.setBackgroundColor(Bgcolor_1);
		tx1.setTextColor(color_font);
		tx2.setTextColor(color_font);
		phone.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		password.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		int dip_pix = DensityUtil.DipToPixels(this, 10);
		phone.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		password.setPadding(dip_pix, dip_pix, dip_pix, dip_pix);
		loging.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(0, color_btnBg, color_btnBg, 10));

		loging.setBackgroundDrawable(setRaduis());
		loging.setTextColor(Fontcolor_1);
		// loging_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		loging_text.setTextColor(color_font);
		// forget_password.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		forget_password.setTextColor(color_font);
		phone.setTextColor(color_font);
		password.setTextColor(color_font);
		//设置事件
		loging.setOnClickListener(this);
		loging_text.setOnClickListener(this);
		forget_password.setOnClickListener(this);

		//数据
		Intent intent = getIntent();
		String user_phone = intent.getStringExtra("user_phone");
		phone.setText(user_phone);
		flg = intent.getStringExtra("flg");
		if(null!=flg&&flg.equals("1")){
			loging_text.setVisibility(View.GONE);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.loging_text:
				Intent intent = new Intent(LogingActivity.this, NewRegisteredActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				LogingActivity.this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
				MyFlg.all_activitys.remove(LogingActivity.this);
				finish();
				break;
			case R.id.loging:
				if(type==0){
					String st = phone.getText().toString();
					String st2 = password.getText().toString();
					if(st.trim().equals("") ||isMobileNO(st)==false){
						Toast.makeText(LogingActivity.this, "请填写正确的手机号", 0).show();
					}else if(st2.trim().equals("")){
						Toast.makeText(LogingActivity.this, "请填写密码", 0).show();
					}else if(st2.trim().length()<6){
						Toast.makeText(LogingActivity.this, "密码至少为6位", 0).show();
					}else {
						dialog.show();
						List <NameValuePair> params=new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("a",MyFlg.a));
						params.add(new BasicNameValuePair("ver",MyFlg.android_version));
						params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(LogingActivity.this)));
						params.add(new BasicNameValuePair("mobile",st));
						params.add(new BasicNameValuePair("password",st2));
						//绑定用户
				/*AsyncLoadApi asyncLoadApi = new AsyncLoadApi(LogingActivity.this, myHandler, params, "bind_user", 0, 1);
				asyncLoadApi.execute();*/
						AsyncLoadApi_user asyncLoadApi_user = new AsyncLoadApi_user(LogingActivity.this, myHandler, params, "bind_user",MyFlg.get_API_URl(application.getCommonInfo_API_functions(LogingActivity.this).getBind_user(),LogingActivity.this));
						asyncLoadApi_user.execute();
					}
				}else if(type==1){
					getbaseInfo();
				}else if(type==2){
					get_user_baseinfo();
				}
				break;
			case R.id.forget_password:
				Intent intent2 = new Intent(LogingActivity.this, Rest_passwordActivity.class);
				intent2.putExtra("phone", phone.getText().toString());
				if(null!=flg&&flg.equals("1")){
					intent2.putExtra("isShow",flg);
				}
				intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent2);
				LogingActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				MyFlg.all_activitys.remove(LogingActivity.this);
				finish();
				break;
			default:
				break;
		}
	}
	private void get_user_baseinfo(){//get_user_baseinfo接口
		AsyncGet_user_baseinfo asyncGet_user_baseinfo = new AsyncGet_user_baseinfo(LogingActivity.this, myHandler,false,9,10);
		asyncGet_user_baseinfo.execute();
	}
	private void getbaseInfo(){//城市信息
		application.userInfoBean = APPUtil.getUser_isRegistered(LogingActivity.this);
		AsyncLoad_baseInfo asyncLoad_baseInfo = new AsyncLoad_baseInfo(LogingActivity.this, myHandler, MyApplication.getuserInfoBean(LogingActivity.this).getCity(), "1", 4, 5);
		asyncLoad_baseInfo.execute();
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 1://绑定用户成功
					getbaseInfo();
					break;
				case 2://账号或密码错误  绑定失败
					dialog.dismiss();
					type=0;
					break;
				case 3://网络错误
					dialog.dismiss();
					type=0;
					break;
				case 4://获取城市信息成功
					get_user_baseinfo();
					break;
				case 5://获取城市信息失败
					dialog.dismiss();
					type=1;
					break;
				case 9://get_user_baseinfo 成功
					Intent intent = new Intent(LogingActivity.this, HomeActivity2.class);
					intent.putExtra("isLoadHome", true);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					MyFlg.all_activitys.remove(LogingActivity.this);
					finish();
					break;
				case 10://get_user_baseinfo 失败
					type=2;
					dialog.dismiss();
					break;
				default:
					break;
			}
		}
	}
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
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
	/**
	 * 设置圆角按钮
	 */
	private Drawable setRaduis(){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(color_btnBg);
		gd.setCornerRadius(10);
		gd.setStroke(1, color_btnBg);
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


}
