package cn.net.dingwei.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.BaiduMapGetCity;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.CitysBean;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.Bean.UserinfoBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.MyScrollView;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.GradientDrawableUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.changeCity.ChageCityActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
public class NewRegisteredActivity extends BackActivity implements OnClickListener{
	private SharedPreferences sharedPreferences;
	private int Bgcolor_1=0,Bgcolor_2=0,Fontcolor_7=0;
	private TextView tx1,tx2,tx3,tx4,text_loging,zc_csts;
	private EditText nickName,password_one,password_two;
	private Button btn_city,btn_next;
	//private List<SortModel> list_citys;
	private myHandler handler=new myHandler();
	private LocationClient locationClient;
	private String city_key="3";//默认北京
	private FYuanTikuDialog dialog;
	private MyApplication application;
	private String time;
	private CitysBean citysBean;
	private MyScrollView myScrollView;

	private SharedPreferences sp_commoninfo;
	private Placeholder_textBean placeholder_textBean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_registered);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		application = MyApplication.myApplication;

		InitColor();
		initID();
		initData();
		isNext();
	}
	//判断是否调到下一个页面
	private void isNext() {
		// TODO Auto-generated method stub
		UserinfoBean bean = APPUtil.getUser_isRegistered(this);
		if(!"null".equals(bean.getCity())&&!bean.getNickname().equals("null")){//选择城市
			Intent intent = new Intent(this, Registered_nickNameActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			return;
		}
	}

	private void InitColor() {
		// TODO Auto-generated method stub
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);

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
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		TextView title_tx = (TextView)findViewById(R.id.title_tx);
		title_tx.setText("注册新用户");

		tx1= (TextView)findViewById(R.id.tx1);
		tx2= (TextView)findViewById(R.id.tx2);
		tx3= (TextView)findViewById(R.id.tx3);
		tx4= (TextView)findViewById(R.id.tx4);
		zc_csts=(TextView)findViewById(R.id.zc_csts);
		text_loging= (TextView)findViewById(R.id.text_loging);
		nickName 	 = (EditText)findViewById(R.id.nickName);
		password_one = (EditText)findViewById(R.id.password_one);
		password_two = (EditText)findViewById(R.id.password_two);
		btn_city  = (Button)findViewById(R.id.btn_city);
		btn_next  = (Button)findViewById(R.id.btn_next);
		myScrollView = (MyScrollView)findViewById(R.id.myScrollView);

		myScrollView.setActivity(this);
		//设置颜色
		tx1.setTextColor(Bgcolor_1);
		tx2.setTextColor(Bgcolor_1);
		tx3.setTextColor(Bgcolor_1);
		tx4.setTextColor(Bgcolor_1);
		text_loging.setTextColor(Bgcolor_2);
		nickName.setTextColor(Bgcolor_2);
		password_one.setTextColor(Bgcolor_2);
		password_two.setTextColor(Bgcolor_2);
		btn_city.setTextColor(Bgcolor_2);
		Drawable EditText_storke=GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10);
		nickName.setBackgroundDrawable(EditText_storke);
		password_one.setBackgroundDrawable(EditText_storke);
		password_two.setBackgroundDrawable(EditText_storke);
		//btn_city.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Bgcolor_1, 1, 0));
		btn_city.setBackgroundDrawable(EditText_storke);
		btn_next.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_1, Bgcolor_2, Bgcolor_1, 1, 10));
		//设置提示文字
		if(null!=placeholder_textBean){
			nickName.setHint(placeholder_textBean.getZc_nc());
			password_one.setHint(placeholder_textBean.getZc_xmm());
			password_two.setHint(placeholder_textBean.getZc_cfxmm());
			password_one.setHintTextColor(Fontcolor_7);
			password_two.setHintTextColor(Fontcolor_7);
			nickName.setHintTextColor(Fontcolor_7);
			//提示语句
			zc_csts.setText(placeholder_textBean.getZc_csts());
			zc_csts.setTextColor(Fontcolor_7);
		}


		btn_city.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		text_loging.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		LoactionNow();
		locationClient.start();
		citysBean = APPUtil.getCommonInfo_citys(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_city:
				Intent intent = new Intent(NewRegisteredActivity.this,ChageCityActivity.class);
				Bundle bundle = new Bundle();
				intent.putExtras(bundle);
				if(null!=MyFlg.baidu_city &&!MyFlg.baidu_city.equals("0") ){
					intent.putExtra("location_city", MyFlg.baidu_city);
				}
				startActivityForResult(intent, 1);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			case R.id.btn_next:
				String name = nickName.getText().toString().trim();
				String pass1 = password_one.getText().toString().trim();
				String pass2 = password_two.getText().toString().trim();
				if(name.length()<=0){
					Toast.makeText(NewRegisteredActivity.this, "请填写昵称", 0).show();
				}else if(name.length()<2 || name.length()>12){
					Toast.makeText(NewRegisteredActivity.this, "请填写正确的昵称（2-12字符之间）", 0).show();
				}else if(pass1.length()<=0){
					Toast.makeText(NewRegisteredActivity.this, "请填写密码", 0).show();
				}else if(pass1.length()<=0){
					Toast.makeText(NewRegisteredActivity.this, "请填写重复密码", 0).show();
				}else if(pass1.length()>16 || pass1.length() <6 ||pass2.length()>16 ||pass2.length() <6){
					Toast.makeText(NewRegisteredActivity.this, "密码应为6-16位字符", 0).show();
				}else if(!pass1.equals(pass2)){
					Toast.makeText(NewRegisteredActivity.this, "两次密码不一致,请重新输入", 0).show();
					password_one.setText("");
					password_two.setText("");
				}else if(null==city_key){
					Toast.makeText(NewRegisteredActivity.this, "当前城市暂未考试信息，请切换其他城市！", 0).show();
				}else{
					dialog.show();
					UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, handler, NewRegisteredActivity.this, MyFlg.getclientcode(NewRegisteredActivity.this), city_key, name, null, null, null, null, pass1, null, true, 5, null, null, null,null,null);
					updateUserAsync.execute();
				}


				break;
			case R.id.text_loging://跳转登录
				Intent intent2 = new Intent(this, LogingActivity.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent2);
				finish();
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			default:
				break;
		}
	}
	/**
	 * 定位
	 */
	private void LoactionNow(){
		locationClient = new LocationClient(this);
		//设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
		);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=1000;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		option.setProdName(getResources().getString(R.string.app_name));
		locationClient.setLocOption(option);
		locationClient.registerLocationListener(new BDLocationListener() {
			private String lontitude;
			private String latitude;

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				if(!TextUtils.isEmpty(location.getProvince())){
					MyFlg.baidu_city = location.getProvince();
					MyFlg.baidu_city=MyFlg.baidu_city.replace("市", "");
					MyFlg.baidu_city=MyFlg.baidu_city.replace("省", "");
					MyFlg.baidu_city=MyFlg.baidu_city.replace("市辖区", "");
					btn_city.setText(MyFlg.baidu_city);
					for (int i = 0; i < citysBean.getAllcity().length; i++) {
						if(citysBean.getAllcity()[i].getN().contains(MyFlg.baidu_city)){
							city_key = citysBean.getAllcity()[i].getK();
							Log.i("123", "定位: "+city_key);
						}
					}
				}
				locationClient.stop();
				locationClient = null;
				/*if(location.getLatitude()!=0 && location.getLongitude()!=0 ){
					lontitude = location.getLongitude()+"";
					latitude = location.getLatitude()+"";
					locationClient.stop();
					BaiduMapGetCity baiduMapGetCity = new BaiduMapGetCity(NewRegisteredActivity.this,handler,latitude, lontitude);
					baiduMapGetCity.execute();
				}*/

			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			locationClient = null;
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0://定位回来
					if(null!=MyFlg.baidu_city &&!MyFlg.baidu_city.equals("0") ){
						btn_city.setText(MyFlg.baidu_city);
						for (int i = 0; i < citysBean.getAllcity().length; i++) {
							if(citysBean.getAllcity()[i].getN().contains(MyFlg.baidu_city)){
								city_key = citysBean.getAllcity()[i].getK();
								Log.i("123", "定位hd: "+city_key);
							}
						}
					}
					break;
				case -1://定位失败

					break;
				case 5:
					Intent intent = new Intent(NewRegisteredActivity.this, Registered_nickNameActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					break;

				default:
					break;
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1&&resultCode == RESULT_OK){ //选择城市
			city_key = data.getStringExtra("key");
			String city_name = data.getStringExtra("select_city");
			btn_city.setText(city_name);

			Log.i("123", "定位onActivityResult: "+city_key);
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
}
