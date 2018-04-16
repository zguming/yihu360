package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import com.changeCity.ChageCityActivity;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import cn.net.dingwei.AsyncUtil.AsyncLoad_baseInfo;
import cn.net.dingwei.AsyncUtil.MyAsyncGetCommoin;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.Itropage_templatesBean;
import cn.net.dingwei.Bean.UserinfoBean;
import cn.net.dingwei.adpater.ViewPagerAdpater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.GradientDrawableUtil;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * LOGO页
 */
public class LogActivity extends InstrumentedActivity implements OnClickListener{
	private static final String TAG = "123";
	private ViewPager viewpager;
	private List<View> listItem = new ArrayList<View>();
	private List<ImageView> list_dians = new ArrayList<ImageView>();
	public static LinearLayout log_dians;
	public static FrameLayout linearLayout;
	private boolean isShowDiago;
	private Builder builder;
	private Itropage_templatesBean itropage_templatesBean;
	private myHandler handler=new myHandler();
	public static LinearLayout shuaxin_linear;
	public  TextView shuaxin_text;
	private Button shuaxin_button;
	private TextView wait_text;
	//************
	private LoadAPI loadApi;
	private SharedPreferences sp;
	private Itropage_templatesBean bean;

	//获取答题里面的图片和文字高
	Boolean hasMeasured = false;
	private TextView answer_text_b;
	private ImageView answer_b;

	private Boolean isloadAPI=true; //如果加载失败 会赋值false
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Screen_width=0,Screen_height=0;
	//注册|登录|游客页面
	private LinearLayout linear_ProgressBar,linear_buttons;
	private String city_key="";
	private FYuanTikuDialog dialog;
	private Animation fade_show;//按钮显示动画

	public static boolean isForeground = false;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_log);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);

		Boolean isNight = sharedPreferences.getBoolean("isNight",false);
		if(isNight==false){
			application.setWindowBrightness(this,application.getScreenBrightness(this),isNight);
		}else{
			application.setWindowBrightness(this,(int) (application.getScreenBrightness(this)*MyFlg.brightnessBiLi),isNight);
		}

		//把StartActivity 放在这里
		initStartActivity();
		viewpager=(ViewPager)findViewById(R.id.viewpager);
		linearLayout=(FrameLayout)findViewById(R.id.linearlayout);
		log_dians=(LinearLayout)findViewById(R.id.log_dians);
		shuaxin_linear=(LinearLayout)findViewById(R.id.shuaxin_linear);
		shuaxin_text=(TextView)findViewById(R.id.shuaxin_text);
		shuaxin_button=(Button)findViewById(R.id.shuaxin_button);
		//注册|登录|游客页面
		Button btn_register = (Button)findViewById(R.id.btn_register);
		Button btn_logging = (Button)findViewById(R.id.btn_logging);
		Button btn_tourist = (Button)findViewById(R.id.btn_tourist);
		linear_ProgressBar = (LinearLayout)findViewById(R.id.linear_ProgressBar);
		linear_buttons = (LinearLayout)findViewById(R.id.linear_buttons);

		int Bgcolor_2 = getResources().getColor(R.color.bgcolor_2);
		int Bgcolor_1 = getResources().getColor(R.color.bgcolor_1);
		int Bgcolor_6 = getResources().getColor(R.color.bgcolor_6);
		btn_register.setTextColor(Color.WHITE);
		btn_logging.setTextColor(Bgcolor_2);
		btn_tourist.setTextColor(Bgcolor_2);
		btn_register.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Bgcolor_2, 1, 0));
		btn_logging.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Bgcolor_6, Bgcolor_2, 1, 0));
		btn_tourist.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Bgcolor_6, Bgcolor_2, 1, 0));
		btn_register.setOnClickListener(this);
		btn_logging.setOnClickListener(this);
		btn_tourist.setOnClickListener(this);
		fade_show = AnimationUtils.loadAnimation(LogActivity.this, R.anim.fade_show_200ms);


		shuaxin_text.setTextColor(Color.GRAY);
		ImageView image = (ImageView)findViewById(R.id.image);
//		LoadImageViewUtil.resetImageSize(image, Screen_width/2, 535, 214);
		LoadImageViewUtil.resetImageSize(image, (int) (Screen_width*0.9), 550, 200);
		init();

		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(-10959187);
		gd.setCornerRadius(10);
		gd.setStroke(1, -10959187);
		shuaxin_button.setBackgroundDrawable(gd);
		shuaxin_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyFlg.isClickButton=true;
				linearLayout.setVisibility(View.VISIBLE);
				shuaxin_linear.setVisibility(View.GONE);
				MyAsyncGetCommoin getcommion = new MyAsyncGetCommoin(handler,LogActivity.this,MyFlg.getclientcode(LogActivity.this),MyFlg.getmobile_model());
				getcommion.execute();
			}
		});

		MyAsyncGetCommoin getcommion = new MyAsyncGetCommoin(handler,LogActivity.this,MyFlg.getclientcode(LogActivity.this),MyFlg.getmobile_model());
		getcommion.execute();

	}
	private void initStartActivity() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("get_intropage_templates", Context.MODE_PRIVATE);
		application.intropage_templates = sp.getString("get_intropage_templates", "0");
		//测试引导屏数据
		//MyFlg.intropage_templates = "{\"status\":\"updated\",\"error\":\"null\",\"data\":{\"intropage_templates\":[{\"k\":\"it001\",\"priority\":12,\"from_date\":\"2015\/10\/1\",\"to_date\":\"2015\/10\/30\",\"max_times\":99,\"time_interval\":5,\"pages\":[{\"img\":\"http:\/\/wv.mtiku.com\/1.0\/static\/app_style\/img\/intropage\/bg1.png\",\"bg_color\":\"330011\",\"btn\":[]},{\"img\":\"http:\/\/wv.mtiku.com\/1.0\/static\/app_style\/img\/intropage\/bg1.png\",\"bg_color\":\"330011\",\"btn\":[{\"aciton\":\"home\",\"option\":\"null\",\"btn_img\":\"http:\/\/wv.mtiku.com\/1.0\/static\/app_style\/img\/intropage\/bt1.png\",\"position\":\"A2\",\"x\":-20,\"y\":50},{\"aciton\":\"webview\",\"option\":\"http:\/\/aaa.bbb.com\/\",\"btn_img\":\"http:\/\/wv.mtiku.com\/1.0\/static\/app_style\/img\/intropage\/bt2.png\",\"position\":\"C3\",\"x\":-100,\"y\":-100}]}]},{\"k\":\"it002\",\"priority\":10,\"from_date\":\"2018\/6\/1\",\"to_date\":\"2018\/8\/1\",\"max_times\":1,\"time_interval\":86400,\"pages\":[{\"img\":\"http:\/\/wv.mtiku.com\/1.0\/static\/app_style\/img\/intropage\/bg1.png\",\"bg_color\":\"330011\",\"btn\":[]},{\"img\":\"http:\/\/wv.mtiku.com\/1.0\/static\/app_style\/img\/intropage\/bg1.png\",\"bg_color\":\"330011\",\"btn\":[]}]}]}}";
		String st = "{}\"";
		//初始化MyFlg
		MyFlg myFlg = new MyFlg(this);
		getAnserHeight();
		//初始化屏幕宽高
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Editor editor = sharedPreferences.edit();
		Screen_width = dm.widthPixels;
		Screen_height=dm.heightPixels;
		editor.putInt("Screen_width", Screen_width);//宽度
		editor.putInt("Screen_height",Screen_height);//高度
		//设置状态栏高度
		editor.putInt("StateHeight",getStatusBarHeight());
		editor.commit();

	}
	/**
	 * 获取答题页面的图片和一行文字高
	 */
	private void getAnserHeight() {
		// TODO Auto-generated method stub
		answer_b = (ImageView)findViewById(R.id.answer_b);
		answer_text_b = (TextView)findViewById(R.id.answer_text_b);
		setAttr(answer_b);
	}

	/*
     * 动态设置图片的宽高
     */
	public void setAttr(final View view) {
		ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (hasMeasured == false) {
					int height_image = answer_b.getHeight();
					int height_text = answer_text_b.getHeight();

					DataUtil.answer_text_padingtop = (height_image-height_text)/2;
					if (height_image != 0) {
						hasMeasured = true;
					}
				}
				return true;
			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub
		//设置每个Page的间距
		// viewpager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.margin_10dip));
		if(sp.getBoolean("first", false)==false ){//测试   每次都显示LOG图
			int [] imageids = new int[]{R.drawable.log1,R.drawable.log2,R.drawable.log3,R.drawable.log4};
			for (int i = 0; i < imageids.length; i++) {
				View view = View.inflate(this, R.layout.item_log_viewpager_item,null);
				ImageView imageView = (ImageView) view.findViewById(R.id.log_image);
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inSampleSize=2;
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageids[i],options);
				imageView.setImageBitmap(bitmap);
				if(i==3){
					Button button = (Button) view.findViewById(R.id.go_now);
					button.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(this,3,5));
					button.setTextColor(getResources().getColor(R.color.bgcolor_2));
					button.setVisibility(View.VISIBLE);
					button.setOnClickListener(new ButtonClick("home",""));
				}
				listItem.add(view);
			}
			viewpager.setAdapter(new ViewPagerAdpater(listItem));
			sp.edit().putBoolean("first", true).commit();
		}else{
			String json = application.intropage_templates;
			itropage_templatesBean=APPUtil.getYindaoPing(json,sp);
			//设置假数据
			if(json.equals("0") || null == itropage_templatesBean){
				linearLayout.setVisibility(View.VISIBLE);
				viewpager.setVisibility(View.GONE);
				MyFlg.isClickButton=true;
				MyFlg.listActivity.add(LogActivity.this);
			}else{
				getItem(itropage_templatesBean);
				viewpager.setAdapter(new ViewPagerAdpater(listItem));
			}
		}
		//设置点点
		if(listItem.size()>1){
			for (int i = 0; i < listItem.size(); i++) {
				ImageView imageView = new ImageView(this);
				imageView.setPadding(10, 0, 10, 0);
				if(i==0){
					imageView.setImageResource(R.drawable.dian4);
				}else{
					imageView.setImageResource(R.drawable.dian3);
				}
				log_dians.addView(imageView);
				list_dians.add(imageView);
			}
		}
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < list_dians.size(); i++) {
					if(i==arg0){
						list_dians.get(i).setImageResource(R.drawable.dian4);
					}else{
						list_dians.get(i).setImageResource(R.drawable.dian3);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 分解Log页面Item
	 */
	private void getItem(Itropage_templatesBean bean){
		Itropage_templatesBean.pages[] pages = bean.getPages();

		for (int i = 0; i < pages.length; i++) {
			Itropage_templatesBean.pages pages_bean = pages[i];
			View item = View.inflate(LogActivity.this, R.layout.item_log, null);
			ImageView image = (ImageView) item.findViewById(R.id.image);
			// Button button = (Button) item.findViewById(R.id.btn);
			FrameLayout framelayout_btns = (FrameLayout) item.findViewById(R.id.framelayout_btns);
			LoadImageViewUtil.setImageBitmap(image, pages_bean.getImg(),LogActivity.this);
			//设置按钮
			Itropage_templatesBean.btn[] btn_bean = pages_bean.getBtn();
			if(null==btn_bean){

			}else{
				for (int j = 0; j < btn_bean.length; j++) {
					Itropage_templatesBean.btn btn = btn_bean[j];
					ImageView imageview = new ImageView(this);
					LoadImageViewUtil.setImageBitmap(imageview, btn.getBtn_img(),LogActivity.this);
					framelayout_btns.addView(imageview);
					ButtonPosition(imageview, btn.getPosition(),btn.getX(),btn.getY(),btn.getWidth(),btn.getHeight());
					imageview.setOnClickListener(new ButtonClick(btn.getAciton(),btn.getOption()));
				}
			}
			listItem.add(item);
		}
	}

	/**
	 * 设置按钮的位置
	 * @param frameLayout
	 * @param button
	 * @param position
	 */
	private void ButtonPosition(ImageView imageview,String position,int x,int y,int width,int height){
		//位置：top-left, top-center, top-right, center-left, center-center, center-right, bottom-left, bottom-center, bottom-right
		int widthPx=DensityUtil.DipToPixels(this, width);
		int heightPx=DensityUtil.DipToPixels(this, height);
		x = DensityUtil.DipToPixels(this, x);
		y = DensityUtil.DipToPixels(this, y);
		int top_height = DensityUtil.DipToPixels(this, 25);//状态栏（显示电池那个）的高度
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(widthPx,heightPx);
		imageview.setLayoutParams(params);
		if("A1".equals(position)){
			imageview.setX(x);
			imageview.setY(y);
		}else if("A2".equals(position)){
			imageview.setX(Screen_width/2+x);
			imageview.setY(y);
		}else if("A3".equals(position)){
			imageview.setX(Screen_width+x);
			imageview.setY(y);
		}else if("B1".equals(position)){
			imageview.setX(x);
			imageview.setY(Screen_height/2+y);
		}else if("B2".equals(position)){
			imageview.setX(Screen_width/2+x);
			imageview.setY(Screen_height/2+y);
		}else if("B3".equals(position)){
			imageview.setX(Screen_width+x);
			imageview.setY(Screen_height/2+y);
		}else if("C1".equals(position)){
			imageview.setX(x);
			imageview.setY(Screen_height-top_height+y);
		}else if("C2".equals(position)){
			imageview.setX(Screen_width/2+x);
			imageview.setY(Screen_height-top_height+y);
		}else if("C3".equals(position)){
			imageview.setX(Screen_width+x);
			imageview.setY(Screen_height-top_height+y);
		}
	}

	class ButtonClick implements OnClickListener{
		private String action;
		private String url;
		public ButtonClick(String action,String url) {
			// TODO Auto-generated constructor stub
			this.action = action;
			this.url = url;
		}
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(action.equals("webview")){
				Intent intent = new Intent(LogActivity.this, WebViewActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("url", url);
				intent.putExtra("flg", 0);
				startActivityForResult(intent, 0);
			}else if(action.equals("home")){
				toActivity();
			}
		}

	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

				case 0: //判断是否退出程序
					MyFlg.isExit = false;
					break;
				case 1:
					isloadAPI = false;
					if(MyFlg.isClickButton == true){
						isShowDiago = false;
						shuaxin_linear.setVisibility(View.VISIBLE);
						linearLayout.setVisibility(View.GONE);
						viewpager.setVisibility(View.GONE);
					}

					break;
				case 1001://加载空白webview
					WebView webView = new WebView(LogActivity.this);
					webView.loadUrl(application.match_url+"?clientcode="+MyFlg.getclientcode(LogActivity.this));
					webView.setWebChromeClient(new WebChromeClient(){
						@Override
						public void onReceivedTitle(WebView view, String title) {
							// TODO Auto-generated method stub
							super.onReceivedTitle(view, title);
						}
						@Override
						public void onProgressChanged(WebView view, int newProgress) {
							// TODO Auto-generated method stub
							super.onProgressChanged(view, newProgress);
							if(newProgress==100){
							}
						}
					});
					break;
				case 1000://没有注册 并且游客没有登入过
					if(MyFlg.isClickButton == true){
						linear_buttons.startAnimation(fade_show);
						linear_buttons.setVisibility(View.VISIBLE);
						linear_ProgressBar.setVisibility(View.GONE);
						shuaxin_linear.setVisibility(View.GONE);
						linearLayout.setVisibility(View.VISIBLE);
						viewpager.setVisibility(View.GONE);
					}
					break;
				case 10://baseInfo 加载成功

					Intent intent = new Intent(LogActivity.this, HomeActivity2.class);
					intent.putExtra("isLoadHome", true);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					dialog.dismiss();
					break;
				case 11://baseInfo 加载失败
					dialog.dismiss();
					linear_buttons.startAnimation(fade_show);
					linear_buttons.setVisibility(View.VISIBLE);
					linear_ProgressBar.setVisibility(View.GONE);
					shuaxin_linear.setVisibility(View.GONE);
					linearLayout.setVisibility(View.VISIBLE);
					viewpager.setVisibility(View.GONE);
					break;
				case 5:
					//dialog.show();
					//如果是游客登录的 清楚进度数据
					SharedPreferences sp = LogActivity.this.getSharedPreferences("get_situation", Context.MODE_PRIVATE);
					sp.edit().putString("get_situation", "").commit();
					getbaseInfo();
					break;
				case 999://游客登录接口失败
					linear_buttons.startAnimation(fade_show);
					linear_buttons.setVisibility(View.VISIBLE);
					linear_ProgressBar.setVisibility(View.GONE);
					shuaxin_linear.setVisibility(View.GONE);
					linearLayout.setVisibility(View.VISIBLE);
					viewpager.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		}
	}
	/**
	 * 判断应该去哪个Activity
	 */
	private void toActivity(){
		MyFlg.isClickButton = true;
		if( MyFlg.isGet_commoninfo==true && MyFlg.isGet_userinfo==true &&MyFlg.isLoadIcons==true){
			UserinfoBean bean = APPUtil.getUser_isRegistered(LogActivity.this);
			if(null == bean){ //加载用户数据失败 重新加载
				Toast.makeText(LogActivity.this, "加载用户数据失败", Toast.LENGTH_SHORT).show();
				shuaxin_linear.setVisibility(View.VISIBLE);
				linearLayout.setVisibility(View.GONE);
				viewpager.setVisibility(View.GONE);
				MyFlg.isClickButton = true;
			}else if(bean.getRegistered()==true ||(bean.getRegistered()==false&&bean.getBool().equals("1"))){
				if(MyFlg.isHomeActivity==false){
					Intent intent = new Intent(LogActivity.this, HomeActivity2.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("isLoadHome", true);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					MyFlg.all_activitys.remove(LogActivity.this);
					MyFlg.listActivity.remove(LogActivity.this);
					LogActivity.this.finish();
					MyFlg.isHomeActivity=true;
				}
			}else{
				city_key = bean.getCity();
				handler.sendEmptyMessage(1000); //返回界面  显示注册 登录 游客3种
			}
		}else{
			if(isloadAPI == false){ //如果已经加载失败
				viewpager.setVisibility(View.GONE);
				shuaxin_linear.setVisibility(View.VISIBLE);
				linearLayout.setVisibility(View.GONE);
				MyFlg.listActivity.add(LogActivity.this);
			}else{
				linearLayout.setVisibility(View.VISIBLE);
				viewpager.setVisibility(View.GONE);
				MyFlg.isClickButton = true;
				MyFlg.listActivity.add(LogActivity.this);
			}

		}
		//隐藏下面的点
		log_dians.setVisibility(View.GONE);
	}
	/**
	 * 设置测试假数据
	 */
	private Itropage_templatesBean SetBtn(Itropage_templatesBean itropage_templatesBean){
		int[] sg = new int[2];
		Itropage_templatesBean.pages[] pages = new Itropage_templatesBean.pages[2];
		Itropage_templatesBean.btn[] btns =new Itropage_templatesBean.btn[2];
		for (int i = 0; i < 2; i++) {
			Itropage_templatesBean.btn btn = new Itropage_templatesBean().new btn();
			btn.setAciton("webview");
			btn.setOption("http://www.baidu.com/");
			btn.setBtn_img("http://wv.mtiku.net/1.0/static/app_style/img/intropage/bt2.png");
			btn.setPosition("C3");
			btn.setX(0);
			btn.setY(0);
			btn.setWidth(60);
			btn.setHeight(60);
			btns[i]=btn;
		}
		for (int i = 0; i < 2; i++) {
			Itropage_templatesBean.pages page = new Itropage_templatesBean().new pages();
			page.setImg("http://wv.mtiku.net/1.0/static/app_style/img/intropage/bg1.png");
			if(i==1){
				page.setBtn(btns);
			}/*else{
    				page.setBtn(null);
    			}*/
			pages[i]=page;
		}
		itropage_templatesBean.setPages(pages);
		return itropage_templatesBean;
	}
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//JPushInterface.onPause(this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
			MyFlg.all_activitys.remove(this);
			MyFlg.listActivity.remove(this);
			finish();
			for (int i = 0; i <MyFlg.all_activitys.size(); i++) {
				if(null==MyFlg.all_activitys.get(i)){

				}else{
					MyFlg.all_activitys.get(i).finish();
				}
			}
			MyFlg.all_activitys.clear();
			System.exit(0);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_register: //注册
				intent = new Intent(LogActivity.this, NewRegisteredActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				//thisFinish();
				break;
			case R.id.btn_logging://登录
				intent = new Intent(LogActivity.this, LogingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				//thisFinish();
				break;
			case R.id.btn_tourist://游客登录
				if(null==city_key||"".equals(city_key)||"null".equals(city_key)){
					intent = new Intent(LogActivity.this, ChageCityActivity.class);
					startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				}else{
					dialog.show();
					UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, handler, LogActivity.this, MyFlg.getclientcode(LogActivity.this), null, null, null, null, null, null, null, null, true, 5, null, null, city_key,false,"1",null);
					updateUserAsync.execute();
				}

				break;
			default:
				break;
		}
	}
	private void getbaseInfo(){//城市信息
		AsyncLoad_baseInfo asyncLoad_baseInfo = new AsyncLoad_baseInfo(LogActivity.this, handler, city_key, "1", 10, 11);
		asyncLoad_baseInfo.execute();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0&&resultCode == RESULT_OK){
			toActivity();
		}else if(requestCode == 1&&resultCode == RESULT_OK){ //选择城市
			city_key = data.getStringExtra("key");
			linear_buttons.setVisibility(View.GONE);
			linear_ProgressBar.setVisibility(View.VISIBLE);
			if(null!=city_key && !"".equals(city_key)){
				UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, handler, LogActivity.this, MyFlg.getclientcode(LogActivity.this), null, null, null, null, null, null, null, null, true, 5, null, null, city_key,false,"1",null);
				updateUserAsync.execute();
			}

		}
	}
}
