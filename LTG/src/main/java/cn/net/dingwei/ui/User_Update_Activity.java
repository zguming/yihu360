package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class User_Update_Activity extends ParentActivity implements OnClickListener{
	private TextView title_text,left_text1,left_text2,left_text3,text,xian1,xian2,xian3;
	private EditText editText1,editText2,editText3;
	private RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3;
	private int type =0; //0:修改昵称  1:修改手机 2：修改密码
	private Button btn;
	private FYuanTikuDialog dialog;
	private myHandler handler = new myHandler();
	private myCountDownTimer countDownTimer = new myCountDownTimer(60000, 1000);
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_user_update);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		initID();
		initData();
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在提交");
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
		ImageView title_left = (ImageView)findViewById(R.id.title_left);
		title_left.setImageResource(R.drawable.title_black);
		title_text=(TextView)findViewById(R.id.title_text);
		left_text1=(TextView)findViewById(R.id.left_text1);
		left_text2=(TextView)findViewById(R.id.left_text2);
		left_text3=(TextView)findViewById(R.id.left_text3);
		xian1=(TextView)findViewById(R.id.xian1);
		xian2=(TextView)findViewById(R.id.xian2);
		xian3=(TextView)findViewById(R.id.xian3);
		text=(TextView)findViewById(R.id.text);
		editText1=(EditText)findViewById(R.id.editText1);
		editText2=(EditText)findViewById(R.id.editText2);
		editText3=(EditText)findViewById(R.id.editText3);
		editText3=(EditText)findViewById(R.id.editText3);
		relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout1);
		relativeLayout2=(RelativeLayout)findViewById(R.id.relativeLayout2);
		relativeLayout3=(RelativeLayout)findViewById(R.id.relativeLayout3);
		btn=(Button)findViewById(R.id.btn);
		//设置颜色
		int Fontcolor3 = Fontcolor_3;
		text.setTextColor(Bgcolor_2);
		left_text1.setTextColor(Fontcolor3);
		left_text2.setTextColor(Fontcolor3);
		left_text3.setTextColor(Fontcolor3);
		editText1.setTextColor(Fontcolor3);
		editText2.setTextColor(Fontcolor3);
		editText3.setTextColor(Fontcolor3);
		btn.setTextColor(Fontcolor_1);
		btn.setBackgroundDrawable(setRaduis());
		xian1.setBackgroundColor(Color_3);
		xian2.setBackgroundColor(Color_3);
		xian3.setBackgroundColor(Color_3);
		//设置事件
		btn.setOnClickListener(this);
		text.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		title_text.setText(intent.getStringExtra("title"));
		switch (type) {//0:修改昵称  1:修改手机 2：修改密码
			case 0:
				left_text1.setText("昵称：");
				editText1.setHint("2-12个字符");
				editText1.setText(MyApplication.getuserInfoBean(User_Update_Activity.this).getNickname());
				editText1.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
				text.setVisibility(View.GONE);
				break;
			case 1:
				relativeLayout2.setVisibility(View.VISIBLE);
				left_text1.setText("手机号：");
				left_text2.setText("验证码：");
				editText1.setHint("请输入新手机号");
				editText1.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
				editText2.setHint("请输入验证码");
				editText2.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
				text.setVisibility(View.VISIBLE);
				break;
			case 2:
				text.setVisibility(View.GONE);
				left_text1.setText("当前密码：");
				left_text2.setText("新的密码：");
				left_text3.setText("重复密码：");
				editText1.setHint("请输入当前密码");
				editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				editText2.setHint("不少于6位");
				editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				editText3.setHint("再次输入新密码");
				editText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				relativeLayout2.setVisibility(View.VISIBLE);
				relativeLayout3.setVisibility(View.VISIBLE);
				btn.setText("提交");
				break;
			default:
				break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String text1 = editText1.getText().toString().trim();
		String text2 = editText2.getText().toString().trim();
		String text3 = editText3.getText().toString().trim();
		switch (v.getId()) {
			case R.id.btn:
				switch (type) {//0:修改昵称  1:修改手机 2：修改密码
					case 0:
						if(text1.length()>=2 &&text1.length()<=12 ){
							dialog.show();
							UpdateUserAsync updateUserAsync = new UpdateUserAsync(dialog,handler, User_Update_Activity.this, MyFlg.getclientcode(User_Update_Activity.this), null, text1, null, null, null, null, null,null, false, 1,null,null,null,null,null);
							updateUserAsync.execute();
						}else{
							Toast.makeText(User_Update_Activity.this, "请填写正确的昵称（2-12字符之间）", 0).show();
						}
						break;
					case 1:
						if(!isMobileNO(text1)){
							Toast.makeText(User_Update_Activity.this, "请填写正确的手机号", 0).show();
						}else if(text2.trim().length()<=0){
							Toast.makeText(User_Update_Activity.this, "请填写正确的验证码", 0).show();
						}else{
							dialog.show();
							UpdateUserAsync updateUserAsync =new UpdateUserAsync(dialog,handler, User_Update_Activity.this, MyFlg.getclientcode(User_Update_Activity.this), null, null, text1, null, null, null, null, text2, false, 1,null,null,null,null,null);
							updateUserAsync.execute();
						}
						break;
					case 2: //目前不只如何验证当前密码
						if(text1.length()==0){
							Toast.makeText(User_Update_Activity.this, "请填写当前密码", 0).show();
						}else if(text1.length()<6 || text1.length()>16){
							Toast.makeText(User_Update_Activity.this, "密码应为6-16位字符", 0).show();
						}else if(text2.length()==0){
							Toast.makeText(User_Update_Activity.this, "请填写新的密码", 0).show();
						}else if(text2.length()<6 || text2.length()>16){
							Toast.makeText(User_Update_Activity.this, "密码应为6-16位字符", 0).show();
						}else if(text3.length()==0){
							Toast.makeText(User_Update_Activity.this, "请填写重复密码", 0).show();
						}else if(text3.length()<6 || text3.length()>16){
							Toast.makeText(User_Update_Activity.this, "请填写重复密码", 0).show();
						}else if(!text2.equals(text3)){
							Toast.makeText(User_Update_Activity.this, "两次密码不相同", 0).show();
							editText2.setText("");
							editText3.setText("");
						}else{
							dialog.show();
							UpdateUserAsync updateUserAsync =new UpdateUserAsync(dialog, handler, User_Update_Activity.this, MyFlg.getclientcode(User_Update_Activity.this), null, null, null, null, null, null, null, null, false, 1,text1,text2,null,null,null);
							updateUserAsync.execute();
						}
						break;

					default:
						break;
				}
				break;
			case R.id.text:
				if(!isMobileNO(text1)){
					Toast.makeText(User_Update_Activity.this, "请填写正确的手机号", 0).show();
				}else{//开始调用验证码接口
					//progressBar_linear.setVisibility(View.VISIBLE);
					dialog.show();
					text.setEnabled(false);
					countDownTimer.start();
					List <NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("a",MyFlg.a));
					params.add(new BasicNameValuePair("ver",MyFlg.android_version));
					params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(User_Update_Activity.this)));
					params.add(new BasicNameValuePair("mobile",text1));
					AsyncLoadApi asyncLoadApi = new AsyncLoadApi(User_Update_Activity.this, handler, params, "send_mobile_verificationcode", 90, 100,MyFlg.get_API_URl(application.getCommonInfo_API_functions(User_Update_Activity.this).getSend_mobile_verificationcode(),User_Update_Activity.this));
					asyncLoadApi.execute();
				}
				break;
			default:
				break;
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					break;
				case 1:
					application.userInfoBean =  APPUtil.getUser_isRegistered(User_Update_Activity.this);
					MyFlg.all_activitys.remove(User_Update_Activity.this);
					finish();
					break;
				case 999:
					dialog.dismiss();
					break;
				case 90: //修改手机成功
					dialog.dismiss();
				
				/*countDownTimer.cancel();
				text.setEnabled(true);
				text.setText("重新获取验证码");
				application.userInfoBean =  APPUtil.getUser_isRegistered(User_Update_Activity.this);
				dialog.dismiss();
				finish();*/
					break;
				case 100://修改手机失败
					countDownTimer.cancel();
					text.setEnabled(true);
					text.setText("重新获取验证码");
					dialog.dismiss();
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
	 * 设置圆角按钮
	 */
	private Drawable setRaduis(){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(Bgcolor_1);
		gd.setCornerRadius(10);
		gd.setStroke(1, Bgcolor_1);
		return gd;
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

	class myCountDownTimer  extends CountDownTimer{

		public myCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			text.setEnabled(true);
			text.setText("重新获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			if((millisUntilFinished / 1000)<10){
				text.setText("请等待(" + millisUntilFinished / 1000 + ")");
			}else{
				text.setText("请等待(" + millisUntilFinished / 1000 + ")");
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(User_Update_Activity.this);
			finish();
			return false;
		}
		return false;
	}
}
