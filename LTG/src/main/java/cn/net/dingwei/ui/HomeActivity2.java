package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.Bugly;

import java.util.List;

import cn.net.dingwei.ui.FramentHome.toLianxiClick;
import cn.net.dingwei.util.ColumValue;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;

public class HomeActivity2 extends FragmentActivity implements OnClickListener,toLianxiClick{
	private DrawerLayout drawerlayout; //左边侧滑菜单
	private LinearLayout home_buttom_linear1; //底部菜单
	private LinearLayout home_buttom_linear2;
	private LinearLayout home_buttom_linear3;
	private LinearLayout home_buttom_linear4;
	private TextView home_buttom_text1;
	private TextView home_buttom_text2;
	private TextView home_buttom_text3;
	private TextView home_buttom_text4,buttom_msg_text;
	private List<Fragment> list_fragment;
	public  FramentHome framentHome; //主页
	private FragmentVideo_New fragmentvideo; //视频
	private FramentGuide framentGuide; //指南
	private FramentPerson framentPerson;//个人

	private Handler handler = new myHandler();
	private LinearLayout title_linear;

	public static int type=-1;//判断当前显示的是那个Fragment
	private ListView left_listview;
	private LinearLayout layoutLeft;
	private LinearLayout layoutRight;
	private FrameLayout frament_1,frament_2,frament_3,frament_4;
	private TextView title_text;
	private ImageView home_buttom_image1,home_buttom_image2,home_buttom_image3,home_buttom_image4,title_left,title_right;
	private MyApplication application;
	private FragmentTransaction fragmentTransaction; // Fragment管理器
	private SharedPreferences sharedPreferences;
	private int Fontcolor_5=0,Fontcolor_6=0,Bgcolor_4,Color_3=0;
	private String Nav_1,Nav_2,Nav_3,Nav_4,Nav_1_active,Nav_2_active,Nav_3_active,Nav_4_active,Home_slidebar,Home_share,share;
	public static HomeActivity2 instence;
	//提示
	private LinearLayout linear_hint_right;
	LinearLayout linear_hint;
	private ImageView home_hint_image,home_hint_bg;

	private ColumValue value;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		instence = this;
		application = MyApplication.myApplication;
		if (savedInstanceState != null) {
			String FRAGMENTS_TAG = "android:support:fragments";
			savedInstanceState.remove(FRAGMENTS_TAG);
		}
		super.onCreate(savedInstanceState);
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		value = new ColumValue(this);
		Boolean isNight = sharedPreferences.getBoolean("isNight",false);
		if(isNight==false){
			application.setWindowBrightness(this,application.getScreenBrightness(this),isNight);
		}else{
			application.setWindowBrightness(this,(int) (application.getScreenBrightness(this)*MyFlg.brightnessBiLi),isNight);
		}
		setContentView(R.layout.activity_home2);
		MyFlg.all_activitys.add(this);

		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_5= sharedPreferences.getInt("fontcolor_5", 0);
		Fontcolor_6= sharedPreferences.getInt("fontcolor_6", 0);
		Bgcolor_4 = sharedPreferences.getInt("bgcolor_4", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Nav_1 = sharedPreferences.getString("Nav_1", "");
		Nav_2 = sharedPreferences.getString("Nav_2", "");
		Nav_3 = sharedPreferences.getString("Nav_3", "");
		Nav_4 = sharedPreferences.getString("Nav_4", "");
		Nav_1_active = sharedPreferences.getString("Nav_1_active", "");
		Nav_2_active = sharedPreferences.getString("Nav_2_active", "");
		Nav_3_active = sharedPreferences.getString("Nav_3_active", "");
		Nav_4_active = sharedPreferences.getString("Nav_4_active", "");
		Home_slidebar = sharedPreferences.getString("Home_slidebar", "");
		Home_share = sharedPreferences.getString("Home_share", "");
		share = sharedPreferences.getString("share", "");
		initID();

		//第一次使用APP 弹出左边侧滑栏  (第一次注册 或第一次安装)
		SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
		Boolean isFirst = sp.getBoolean("isFirst", false);
		Intent intent = getIntent();
		Boolean isShowLeft = intent.getBooleanExtra("isShowLeft", false);
		/*if( isFirst==false || isShowLeft==true){
			drawerlayout.openDrawer(Gravity.LEFT);
			sp.edit().putBoolean("isFirst", true).commit();
		}*/
		Boolean isLoadHome = intent.getBooleanExtra("isLoadHome", false);
		if(isLoadHome==true){
			framentHome=null;
		}
		//每次打开APP设置默认值-1
		type=-1;
		setFragMent(0);
		//加载过之后写出0
		type=0;
		left_ListViewOnItemClcik();

		Bugly.init(getApplicationContext(), BuildConfig.Bugly_APPID, false);
		if (value.getIsFirst()){
			intent = new Intent(this, SubjectSelectActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			drawerlayout.openDrawer(Gravity.LEFT);
			value.setIsFirst(false);
		}
	}


	//-----------------------------------------------------------------------------------------------
	/**
	 * 设置左边点击事件
	 */
	private void left_ListViewOnItemClcik(){
		left_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				if(null!=framentHome){
					framentHome.clickLeft(point);
				}

			}
		});
	}
	/**
	 * 设置FragMent
	 * @param i
	 */
	public void setFragMent(int i) {
		// TODO Auto-generated method stub
		fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		switch (i) {
			case 0: //主页（练习）
				title_linear.setVisibility(View.VISIBLE);
				setImage(0);
				layoutLeft.setVisibility(View.VISIBLE);
				frament_1.setVisibility(View.VISIBLE);
				frament_2.setVisibility(View.GONE);
				frament_3.setVisibility(View.GONE);
				frament_4.setVisibility(View.GONE);
                if("construction_cp".equals(BuildConfig.MYAPP_KEY)||"funds_cp".equals(BuildConfig.MYAPP_KEY)) {
                    layoutRight.setVisibility(View.INVISIBLE);
                }else {
                    layoutRight.setVisibility(View.VISIBLE);
                }
				if(null ==framentHome){
					framentHome = new FramentHome();
					framentHome.setLianxiClick(this);
					// 替换当前的页面
					fragmentTransaction.replace(R.id.frame_content1, framentHome);
					// 事务管理提交
					fragmentTransaction.commit();
				}
				if(MyFlg.ISupdateHome_listview==true){
					framentHome.changeCity(HomeActivity2.this);
					MyFlg.ISupdateHome= false;
					MyFlg.ISupdateHome_listview= false;
				}
				if(MyFlg.ISupdateHome==true){
					framentHome.ChangeSubject();
					MyFlg.ISupdateHome= false;
				}
				if(MyFlg.ISupdateHome_viewpager==true){
					framentHome.loadData(1); //刷新进度 以及下面的数量
				}
				break;
			case 1://视频
				if(type!=1){
					title_linear.setVisibility(View.VISIBLE);
					layoutLeft.setVisibility(View.VISIBLE);
					layoutRight.setVisibility(View.INVISIBLE);
					setImage(1);
					frament_2.setVisibility(View.VISIBLE);
					frament_1.setVisibility(View.GONE);
					frament_3.setVisibility(View.GONE);
					frament_4.setVisibility(View.GONE);
					if(null ==fragmentvideo){
						fragmentvideo = new FragmentVideo_New();
						// 替换当前的页面
						fragmentTransaction.replace(R.id.frame_content2, fragmentvideo);
						// 事务管理提交
						fragmentTransaction.commit();
					}
					fragmentvideo.loadData();
				}
				break;
			case 2 ://指南
				if(type!=2){
					title_linear.setVisibility(View.GONE);
					setImage(2);
					frament_3.setVisibility(View.VISIBLE);
					frament_1.setVisibility(View.GONE);
					frament_2.setVisibility(View.GONE);
					frament_4.setVisibility(View.GONE);
					if(null ==framentGuide){
						framentGuide = new FramentGuide();
						// 替换当前的页面
						fragmentTransaction.replace(R.id.frame_content3, framentGuide);
						// 事务管理提交
						fragmentTransaction.commit();
					}

					if(MyFlg.ISupdateguide==true){
						framentGuide.setonRefresh(true);
					}else if(MyApplication.getuserInfoBean(HomeActivity2.this).getNew_msg()>0){
						framentGuide.setonRefresh(true);
					}else{
						framentGuide.setonRefresh(false);
					}
					buttom_msg_text.setVisibility(View.GONE);
				}
				break;
			case 3 ://个人
				if(type!=3){
					title_linear.setVisibility(View.GONE);
					setImage(3);
					frament_4.setVisibility(View.VISIBLE);
					frament_1.setVisibility(View.GONE);
					frament_2.setVisibility(View.GONE);
					frament_3.setVisibility(View.GONE);
					if(null ==framentPerson){
						framentPerson = new FramentPerson();
						// 替换当前的页面
						fragmentTransaction.replace(R.id.frame_content4, framentPerson);
						// 事务管理提交
						fragmentTransaction.commit();
					}
					framentPerson.MyRefresh();
				}
				break;
			default:
				break;
		}
	}
	

	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.home_buttom_bg).setBackgroundColor(Bgcolor_4);
		drawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
		title_linear=(LinearLayout)findViewById(R.id.title_linear);
		layoutLeft=(LinearLayout)findViewById(R.id.layoutLeft);
		layoutRight=(LinearLayout)findViewById(R.id.layoutRight);
		home_buttom_linear1=(LinearLayout)findViewById(R.id.home_buttom_linear1);
		home_buttom_linear2=(LinearLayout)findViewById(R.id.home_buttom_linear2);
		home_buttom_linear3=(LinearLayout)findViewById(R.id.home_buttom_linear3);
		home_buttom_linear4=(LinearLayout)findViewById(R.id.home_buttom_linear4);
		linear_hint=(LinearLayout)findViewById(R.id.linear_hint);
		linear_hint_right=(LinearLayout)findViewById(R.id.linear_hint_right);
		home_buttom_text1 = (TextView)findViewById(R.id.home_buttom_text1);
		home_buttom_text2 = (TextView)findViewById(R.id.home_buttom_text2);
		home_buttom_text3 = (TextView)findViewById(R.id.home_buttom_text3);
		home_buttom_text4 = (TextView)findViewById(R.id.home_buttom_text4);
		home_buttom_image1 = (ImageView)findViewById(R.id.home_buttom_image1);
		home_buttom_image2 = (ImageView)findViewById(R.id.home_buttom_image2);
		home_buttom_image3 = (ImageView)findViewById(R.id.home_buttom_image3);
		home_buttom_image4 = (ImageView)findViewById(R.id.home_buttom_image4);
		home_hint_image = (ImageView)findViewById(R.id.home_hint_image);
		home_hint_bg = (ImageView)findViewById(R.id.home_hint_bg);
		title_left = (ImageView)findViewById(R.id.title_left);
		title_right = (ImageView)findViewById(R.id.title_right);
		title_text = (TextView)findViewById(R.id.title_text);

		int width =application.getSharedPreferencesValue_int(this,"Screen_width")- DensityUtil.DipToPixels(this,15);
		int height = width*1280/720;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
		home_hint_bg.setLayoutParams(params);
		linear_hint.getBackground().setAlpha(178);
		String is_share = application.getSharedPreferencesValue_string(this,"is_share");
		String share_type=application.getuserInfoBean(this).getShare_type();
		if(TextUtils.isEmpty(is_share)&&!TextUtils.isEmpty(share_type)&&share_type.equals("1")){
            if("construction_cp".equals(BuildConfig.MYAPP_KEY)||"funds_cp".equals(BuildConfig.MYAPP_KEY)) {
                linear_hint.setVisibility(View.GONE);
            }else {
                linear_hint.setVisibility(View.VISIBLE);
            }
			drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
			application.setSharedPreferencesValue_string(HomeActivity2.this,"is_share","Y");
		}else{
			linear_hint.setVisibility(View.GONE);
			drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		}
		if(application.gethas_live_video(this).equals("0")){
			home_buttom_linear2.setVisibility(View.GONE);
		}else{
			home_buttom_linear2.setVisibility(View.VISIBLE);
		}
		//指南msg提示点
		buttom_msg_text =(TextView)findViewById(R.id.buttom_msg_text);
		//左边侧边栏
		left_listview = (ListView)findViewById(R.id.left_listview);
		//设置图片
		title_left.setImageBitmap(BitmapFactory.decodeFile(Home_slidebar));

		if(!TextUtils.isEmpty(share_type)&&share_type.equals("1")){
			title_right.setImageBitmap(BitmapFactory.decodeFile(share));
			//linear_hint.setVisibility(View.VISIBLE);
		}else{
			title_right.setImageBitmap(BitmapFactory.decodeFile(Home_share));
			//linear_hint.setVisibility(View.GONE);
		}
		home_hint_image.setImageBitmap(BitmapFactory.decodeFile(share));
		findViewById(R.id.buttom_xian).setBackgroundColor(Color_3);

		frament_1 = (FrameLayout)findViewById(R.id.frament_1);
		frament_2 = (FrameLayout)findViewById(R.id.frament_2);
		frament_3 = (FrameLayout)findViewById(R.id.frament_3);
		frament_4 = (FrameLayout)findViewById(R.id.frament_4);
		//设置点击事件
		home_buttom_linear1.setOnClickListener(this);
		home_buttom_linear2.setOnClickListener(this);
		home_buttom_linear3.setOnClickListener(this);
		home_buttom_linear4.setOnClickListener(this);
		layoutLeft.setOnClickListener(this);
		layoutRight.setOnClickListener(this);
		linear_hint.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent  intent=null;
		switch (view.getId()) {
			case R.id.layoutLeft:
				//Toast.makeText(HomeActivity2.this, "点击顶部标题栏左边菜单", 0).show();
				if(type==0||type==1){
					drawerlayout.openDrawer(Gravity.LEFT);
				}
				break;
			case R.id.layoutRight:
				//Toast.makeText(HomeActivity2.this, "点击顶部标题栏右边菜单", 0).show();
/*			if(type==0){
				Intent intent = new Intent(HomeActivity2.this, Person_centeredActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}*/
//				String share_type=application.getuserInfoBean(HomeActivity2.instence).getShare_type();
				intent = new Intent(HomeActivity2.this, RecommendShareActivity.class);
//				if(!TextUtils.isEmpty(share_type)&&share_type.equals("1")){
//				}else{
//					intent = new Intent(HomeActivity2.this, ShareActivity.class);
//				}
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			case R.id.linear_hint:
				linear_hint.setVisibility(View.GONE);

				SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
				Boolean isFirst = sp.getBoolean("isFirst", false);
				if( isFirst==false ){
					//drawerlayout.openDrawer(Gravity.LEFT);
					sp.edit().putBoolean("isFirst", true).commit();
				}
				break;
			case R.id.home_buttom_linear1:
				title_text.setText(MyApplication.getuserInfoBean(HomeActivity2.this).getSubject_name());
				drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
				setFragMent(0);
				type=0;
				break;
			case R.id.home_buttom_linear2:
				title_text.setText("课程");
				drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
				setFragMent(1);
				type=1;
				break;
			case R.id.home_buttom_linear3:
				title_text.setText("指南");
				drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
				setFragMent(2);
				type=2;
				break;
			case R.id.home_buttom_linear4:
				title_text.setText("个人");
				drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
				setFragMent(3);
				type=3;
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
				case 0: //判断是否退出程序
					MyFlg.isExit = false;
					break;

				default:
					break;
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!MyFlg.isExit) {
			MyFlg.isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			handler.sendEmptyMessageDelayed(0, 2000);
		} else {
			MyFlg.all_activitys.remove(HomeActivity2.this);
			finish();
			for (int i = 0; i <MyFlg.all_activitys.size(); i++) {
				if(null==MyFlg.all_activitys.get(i)){

				}else{
					MyFlg.all_activitys.get(i).finish();
				}
			}
			MyFlg.all_activitys.clear();
			//System.exit(0);
		}
	}

	/**
	 * 医护和命题库切换步骤：
	 * 1.cn.net.dingwei.util.MyFlg 中 apiUrl 和 Commoninfo_APIurl替换(已在application中处理)
	 * 2.setImage中底部图标替换
	 * 3.lib中sup.jar替换 地址——C:\Users\Administrator.USER-20160616ZI\Desktop\知典\新项目\医护360\解压jar
	 * @param index
	 */

	private void setImage(int index) {
		switch (index) {
			case 0:
				//设置图片

				if ("teacher".equals(BuildConfig.FLAVOR)) {
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1_active));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4));
				}else if ("yihu360".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_1_active));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_7));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_4));
				}else if ("xizhang".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1_active));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4));
				}

				home_buttom_text1.setTextColor(Fontcolor_5);
				home_buttom_text2.setTextColor(Fontcolor_6);
				home_buttom_text3.setTextColor(Fontcolor_6);
				home_buttom_text4.setTextColor(Fontcolor_6);
				break;
			case 1:

				if ("teacher".equals(BuildConfig.FLAVOR)) {
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2_active));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4));
				}else if ("yihu360".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_7_active));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_4));
				}else if ("xizhang".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2_active));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4));
				}

				home_buttom_text1.setTextColor(Fontcolor_6);
				home_buttom_text2.setTextColor(Fontcolor_5);
				home_buttom_text3.setTextColor(Fontcolor_6);
				home_buttom_text4.setTextColor(Fontcolor_6);
				break;
			case 2:

				if ("teacher".equals(BuildConfig.FLAVOR)) {
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3_active));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4));
				}else if ("yihu360".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_7));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_3_active));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_4));
				}else if ("xizhang".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3_active));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4));
				}

				home_buttom_text1.setTextColor(Fontcolor_6);
				home_buttom_text2.setTextColor(Fontcolor_6);
				home_buttom_text3.setTextColor(Fontcolor_5);
				home_buttom_text4.setTextColor(Fontcolor_6);
				break;
			case 3:

				if ("teacher".equals(BuildConfig.FLAVOR)) {
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4_active));
				}else if ("yihu360".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_7));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nav_4_active));
				}else if ("xizhang".equals(BuildConfig.FLAVOR)){
					home_buttom_image1.setImageBitmap(BitmapFactory.decodeFile(Nav_1));
					home_buttom_image2.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
					home_buttom_image3.setImageBitmap(BitmapFactory.decodeFile(Nav_3));
					home_buttom_image4.setImageBitmap(BitmapFactory.decodeFile(Nav_4_active));
				}

				home_buttom_text1.setTextColor(Fontcolor_6);
				home_buttom_text2.setTextColor(Fontcolor_6);
				home_buttom_text3.setTextColor(Fontcolor_6);
				home_buttom_text4.setTextColor(Fontcolor_5);
				break;
			default:
				break;
		}
	}
	//FragMent  Home  的回到方法
	@Override
	public void toLianx() {
		// TODO Auto-generated method stub
		title_text.setText("练习");
		drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		setFragMent(1);
		type=1;
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		/* Parcelable p = mFragments.saveAllState();
	        if (p != null) {
	            outState.putParcelable(FRAGMENTS_TAG, p);
	        }*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}
}
