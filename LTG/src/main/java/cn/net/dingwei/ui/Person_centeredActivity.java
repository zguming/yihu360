package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi_user;
import cn.net.dingwei.adpater.Person_Adpater;
import cn.net.dingwei.myView.ElasticScrollView;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.myView.MyScrollView;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;


public class Person_centeredActivity extends ParentActivity implements OnClickListener{
	private ImageView title_left;
	private TextView title_text;
	private Button pay_rmb;
	private String[][] stArray = new String[][]{{"个人资料",""},{"",""},{"报考城市",""},{"报考学科",""},{"考试时间",""},{"练习科目",""},{"",""},{"关于",""},{"",""},{"退出登录",""}};
	private ListView listview;
	//private DialogMenu dialogMenu; //对话框
	private myHandler myHandler = new myHandler();
	private String [] stArray1 = new String[]{"","昵称","账号信息","","修改密码"};
	private String [] stArray2 = new String[]{"","","","",""};
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Bgcolor_2=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_person_centered);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		initID();
		initData();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setArray();
		listview.setAdapter(new Person_Adpater(this, stArray,0));
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
		title_left=(ImageView)findViewById(R.id.title_left);
		title_left.setImageResource(R.drawable.title_black);
		title_text=(TextView)findViewById(R.id.title_text);
		pay_rmb=(Button)findViewById(R.id.pay_rmb);
		listview =(ListView)findViewById(R.id.listview);
		//设置颜色
		findViewById(R.id.person_top_bg).setBackgroundColor(Bgcolor_2);
		findViewById(R.id.linear_bg).setBackgroundColor(Bgcolor_2);
		//设置事件
		pay_rmb.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		title_text.setText("个人中心");

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				Intent intent;
				switch (point) {
					case 0:
						intent = new Intent(Person_centeredActivity.this, Person_dataActivity.class);
						intent.putExtra("type", 0);
						intent.putExtra("title", "个人信息");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 2:
						intent = new Intent(Person_centeredActivity.this, Person_dataActivity.class);
						intent.putExtra("type", 1);
						intent.putExtra("title", "修改城市");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 3:
						intent = new Intent(Person_centeredActivity.this, Person_dataActivity.class);
						intent.putExtra("type", 2);
						intent.putExtra("title", "修改报考学科");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 4:
						intent = new Intent(Person_centeredActivity.this, Person_dataActivity.class);
						intent.putExtra("type", 3);
						intent.putExtra("title", "修改报考时间");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 5:
						intent = new Intent(Person_centeredActivity.this, Person_dataActivity.class);
						intent.putExtra("type", 4);
						intent.putExtra("title", "修改练习科目");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 9: //退出登录
						showAlertDialogChoose("提示", "是否退出登录", "取消", "确定");
						break;

					default:
						break;
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.pay_rmb:
				Intent intent = new Intent(Person_centeredActivity.this, PayVIPActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			default:
				break;
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 1: //成功
					Intent intent = new Intent(Person_centeredActivity.this, LogingActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
					startActivity(intent);
					MyFlg.all_activitys.remove(Person_centeredActivity.this);
					MyFlg.listActivity.remove(Person_centeredActivity.this);
					finish();
					MyFlg.finshActivitys();
					break;
				case 3://失败
					break;
				default:
					break;
			}
		}
	}
	//设置数组
	private void setArray(){
		if(null==MyApplication.userInfoBean){
			MyApplication.getuserInfoBean(this);
		}
		if(null!=MyApplication.userInfoBean){
			//昵称
			String nickname = MyApplication.userInfoBean.getNickname();
			//会员名称
			String  Member_type_name = MyApplication.userInfoBean.getMember_type_name();
			//有效日期
			String Trial_expiry = MyApplication.userInfoBean.getMember_expiry_text();
			//按钮信息
			//String Member_price = application.userInfoBean.getMember_price();
			String Member_price = MyApplication.userInfoBean.getMember_button();
			//报考城市
			String city_name = MyApplication.userInfoBean.getCity_name();
			//报考科目
			String exam_name = MyApplication.userInfoBean.getExam_name();
			//考试时间
			String exam_schedule = MyApplication.userInfoBean.getExam_schedule();
			//练习科目
			String subject_name = MyApplication.userInfoBean.getSubject_name();
			//手机号（账号）
			String phone_number = MyApplication.userInfoBean.getMobile();
			if(!phone_number.equals("null")){
				stArray2[2] = phone_number;
			}
			if(!nickname.equals("null")){
				stArray[0][1] = nickname;
				stArray2[1]=nickname;
			}

			if(!Member_price.equals("null")){
				pay_rmb.setText(Member_price);
			}
			if(!city_name.equals("null")){
				stArray[2][1] = city_name;
			}
			if(!exam_name.equals("null")){
				stArray[3][1] = exam_name;
			}
			if(!exam_schedule.equals("null")){
				stArray[4][1] = APPUtil.setTime(exam_schedule);
			}
			if(!subject_name.equals("null")){
				stArray[5][1] = subject_name;
			}

		}
	}


	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(Person_centeredActivity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								List <NameValuePair> params=new ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair("a",MyFlg.a));
								params.add(new BasicNameValuePair("ver",MyFlg.android_version));
								params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Person_centeredActivity.this)));
								//解绑用户
								AsyncLoadApi_user asyncLoadApi_user = new AsyncLoadApi_user(Person_centeredActivity.this, myHandler, params, "unbind_user",MyFlg.get_API_URl(application.getCommonInfo_API_functions(Person_centeredActivity.this).getUnbind_user(),Person_centeredActivity.this));
								asyncLoadApi_user.execute();
								break;
							default:
								break;
						}

					}
				});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(Person_centeredActivity.this);
			MyFlg.listActivity.remove(Person_centeredActivity.this);
			finish();
			return false;
		}
		return false;
	}
}
