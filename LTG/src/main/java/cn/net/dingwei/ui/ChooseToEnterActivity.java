package cn.net.dingwei.ui;

import com.changeCity.ChageCityActivity;

import cn.net.dingwei.AsyncUtil.AsyncLoad_baseInfo;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.ui.NewRegisteredActivity.myHandler;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 选择身份进入    （游客  注册会员  注册）
 * @author Administrator
 *
 */
public class ChooseToEnterActivity extends Exit_parentActivity implements OnClickListener{
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_5=0,Bgcolor_6=0;
	private int Screen_width=0,Screen_height=0,StateHeight=0;
	private String city_key="";//选择的城市Key
	private FYuanTikuDialog dialog;
	private myHandler handler=new myHandler();
	private LinearLayout linear_ProgressBar;
	private LinearLayout linear_buttons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_choose_to_enter);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		initColors();
		initID();
	}

	private void initColors() {
		// TODO Auto-generated method stub
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_5 = sharedPreferences.getInt("bgcolor_5", 0);
		Bgcolor_6 = sharedPreferences.getInt("bgcolor_6", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
	}

	private void initID() {
		// TODO Auto-generated method stub
		//标题
		/*findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		 TextView title_tx = (TextView)findViewById(R.id.title_tx);
		 title_tx.setText("我的错题");*/

		ImageView image = (ImageView)findViewById(R.id.image);
		Button btn_register = (Button)findViewById(R.id.btn_register);
		Button btn_logging = (Button)findViewById(R.id.btn_logging);
		Button btn_tourist = (Button)findViewById(R.id.btn_tourist);
		linear_ProgressBar = (LinearLayout)findViewById(R.id.linear_ProgressBar);
		linear_buttons = (LinearLayout)findViewById(R.id.linear_buttons);

//		LoadImageViewUtil.resetImageSize(image, Screen_width/2, 535, 214);
		LoadImageViewUtil.resetImageSize(image, (int) (Screen_width*0.9), 550, 200);
		//设置颜色 
		btn_register.setTextColor(Color.WHITE);
		btn_logging.setTextColor(Bgcolor_2);
		btn_tourist.setTextColor(Bgcolor_2);
		btn_register.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Bgcolor_2, 1, 0));
		btn_logging.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Bgcolor_6, Bgcolor_2, 1, 0));
		btn_tourist.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Bgcolor_6, Bgcolor_2, 1, 0));

		btn_register.setOnClickListener(this);
		btn_logging.setOnClickListener(this);
		btn_tourist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_register: //注册
				intent = new Intent(ChooseToEnterActivity.this, NewRegisteredActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				//thisFinish();
				break;
			case R.id.btn_logging://登录
				intent = new Intent(ChooseToEnterActivity.this, LogingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				//thisFinish();
				break;
			case R.id.btn_tourist://游客登录
				if(null==city_key||"".equals(city_key)||"null".equals(city_key)){
					intent = new Intent(ChooseToEnterActivity.this, ChageCityActivity.class);
					startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				}else{
					dialog.show();
					UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, handler, ChooseToEnterActivity.this, MyFlg.getclientcode(ChooseToEnterActivity.this), null, null, null, null, null, null, null, null, true, 5, null, null, city_key,false,"1",null);
					updateUserAsync.execute();
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
				case 1://baseInfo 加载成功
					Intent intent = new Intent(ChooseToEnterActivity.this, HomeActivity2.class);
					intent.putExtra("isLoadHome", true);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					dialog.dismiss();
					break;
				case 2://baseInfo 加载失败
					dialog.dismiss();
					linear_buttons.setVisibility(View.VISIBLE);
					linear_ProgressBar.setVisibility(View.GONE);
					break;
				case 5:
					//dialog.show();
					//如果是游客登录的 清楚进度数据
					SharedPreferences sp = ChooseToEnterActivity.this.getSharedPreferences("get_situation", Context.MODE_PRIVATE);
					sp.edit().putString("get_situation", "").commit();
					getbaseInfo();
					break;
				case 999://游客登录接口失败
					linear_buttons.setVisibility(View.VISIBLE);
					linear_ProgressBar.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		}
	}
	private void getbaseInfo(){//城市信息
		AsyncLoad_baseInfo asyncLoad_baseInfo = new AsyncLoad_baseInfo(ChooseToEnterActivity.this, handler, city_key, null, 1, 2);
		asyncLoad_baseInfo.execute();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1&&resultCode == RESULT_OK){ //选择城市 
			city_key = data.getStringExtra("key");
			linear_buttons.setVisibility(View.GONE);
			linear_ProgressBar.setVisibility(View.VISIBLE);
			if(null!=city_key && !"".equals(city_key)){
				UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, handler, ChooseToEnterActivity.this, MyFlg.getclientcode(ChooseToEnterActivity.this), null, null, null, null, null, null, null, null, true, 5, null, null, city_key,false,"1",null);
				updateUserAsync.execute();
			}

		}
	}
}
