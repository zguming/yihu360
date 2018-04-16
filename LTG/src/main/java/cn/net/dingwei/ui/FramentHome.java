package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.net.dingwei.AsyncUtil.AsyncGet_home_suggest;
import cn.net.dingwei.AsyncUtil.AsyncGet_situationAndGet_subjects_progress;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync_home;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Get_adv_projectBean;
import cn.net.dingwei.Bean.Get_adv_projectBean.data;
import cn.net.dingwei.Bean.Get_baseinfoBean;
import cn.net.dingwei.Bean.Get_home_suggestBean_url;
import cn.net.dingwei.Bean.HomeViewPagerBean;
import cn.net.dingwei.Bean.MyCollectBean;
import cn.net.dingwei.Bean.MyWrongsBean;
import cn.net.dingwei.Bean.Point_jinduBean;
import cn.net.dingwei.Bean.PointsBean;
import cn.net.dingwei.Bean.Products_listBean;
import cn.net.dingwei.Bean.UtilBean;
import cn.net.dingwei.adpater.HomeLeftListViewAdpater;
import cn.net.dingwei.adpater.Home_childView_listview;
import cn.net.dingwei.adpater.Home_product_adapter;
import cn.net.dingwei.adpater.ViewPagerAdpater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.myView.Home_Score_Dialog2;
import cn.net.dingwei.myView.MyScrollView;
import cn.net.dingwei.myView.TasksCompletedView;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class FramentHome extends Fragment implements OnClickListener,Home_product_adapter.ClickBack{
	private ViewPager viewpager,viewpager_menu;
	private List<TasksCompletedView> list_taskscompletedViiew;
	private List<View> list;
	private List<ImageView> dians,dians_product;
	private LinearLayout linear_dian,linear_product_dian;
	private LinearLayout linear_choose_subject;
	private ListView listview;

	private ImageView title_left; //顶部菜单
	private ImageView title_right; //顶部菜单
	//判断进度条是否要效果
	//private int now_index;
	private TextView title_text; //标题栏标题
	private myHandler handler = new myHandler();

	//private ProgressBar progressBar1; //加载等待
	private LinearLayout shuaxin_linear;//加载失败显示的

	private DrawerLayout drawerlayout; //左边侧滑菜单
	private TextView home_left_text;
	private ListView left_listview;
	private HomeLeftListViewAdpater homeLeftListViewAdpater; //侧滑菜单的Adpater
	private Get_baseinfoBean get_baseinfoBean;  //设置左边菜单用
	private Animation animation ;

	private List<PointsBean> list_PointsBean;
	private List<Point_jinduBean> list_jinduBean;
	// private List<Get_home_suggestBean> list_Get_home_suggestBean;

	private myYuanClick myYuanClick = new myYuanClick();
	private FYuanTikuDialog dialog;
	private RelativeLayout relativelayout;
	private MyScrollView scroll;
	private LinearLayout myload_wait;
	toLianxiClick lianxiClick;
	private Home_childView_listview adpater;//ListView 列表Adpater
	//--------------更新科目列表----------
	private Boolean updata_subject=false;
	private TextView buttom_msg_text;
	private MyApplication application;
	private int home_leftMenu_index=0;

	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_3=0,Color_4=0,Color_3=0,Fontcolor_13=0;
	private int Screen_width=0;
	private TextView text_data1,text_data2,text_data3;
	private LinearLayout buttom_linear1,buttom_linear2,buttom_linear3; //我的错题 收藏 笔记 撒个地方
	private LinearLayout linear_jixu;//继续练习
	private String adv_project_pay = "";
	List<Get_adv_projectBean.data> jinjieList;

	//判断是否弹出左边菜单
	public static boolean isShowLeft=false;
	private TextView text_jixutext1,text_jixutext2;
	private FrameLayout framen_product;
    private RelativeLayout rl_home_left_top;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		application = MyApplication.myApplication;
		sharedPreferences =getActivity().getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_3 = sharedPreferences.getInt("bgcolor_3", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Fontcolor_13= sharedPreferences.getInt("fontcolor_13", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		adv_project_pay= sharedPreferences.getString("adv_project_pay", "");
		View view = inflater.inflate(R.layout.activity_home, null, false);
		init_onCreateView(view);
		return view;
	}
	private void init_onCreateView(View view) {
		// TODO Auto-generated method stub
		//主页背景
		view.findViewById(R.id.home_bg).setBackgroundColor(Bgcolor_2);
		view.findViewById(R.id.text_top_bg).setBackgroundColor(Bgcolor_2);
		//选择练习科目
		linear_choose_subject=(LinearLayout)view.findViewById(R.id.linear_choose_subject);
		listview=(ListView)view.findViewById(R.id.listview);
		//设置ViewPager高度
		relativelayout = (RelativeLayout)view.findViewById(R.id.relativelayout);
		view.findViewById(R.id.xian).setBackgroundColor(Color_4);
		view.findViewById(R.id.xian2).setBackgroundColor(Color_4);
		viewpager=(ViewPager) view.findViewById(R.id.viewpager);
		viewpager_menu = (ViewPager) view.findViewById(R.id.viewpager_menu);
		linear_dian=(LinearLayout) view.findViewById(R.id.linear_dian);
		linear_product_dian=(LinearLayout) view.findViewById(R.id.linear_product_dian);
		scroll = (MyScrollView)view.findViewById(R.id.scroll);
		TextView text01 = (TextView)view.findViewById(R.id.text01);
		TextView text02 = (TextView)view.findViewById(R.id.text02);
		TextView text03 = (TextView)view.findViewById(R.id.text03);
		TextView text04 = (TextView)view.findViewById(R.id.text04);
		TextView text05 = (TextView)view.findViewById(R.id.text05);
		text_jixutext1 = (TextView)view.findViewById(R.id.text_jixutext1);
		text_jixutext2 = (TextView)view.findViewById(R.id.text_jixutext2);
		text_data1 = (TextView)view.findViewById(R.id.text_data1);
		text_data2 = (TextView)view.findViewById(R.id.text_data2);
		text_data3 = (TextView)view.findViewById(R.id.text_data3);
		buttom_linear1 = (LinearLayout) view.findViewById(R.id.buttom_linear1);
		buttom_linear2= (LinearLayout) view.findViewById(R.id.buttom_linear2);
		buttom_linear3 = (LinearLayout) view.findViewById(R.id.buttom_linear3);
		linear_jixu  = (LinearLayout) view.findViewById(R.id.linear_jixu);
		framen_product  = (FrameLayout) view.findViewById(R.id.framen_product);

		view.findViewById(R.id.xian_product).setBackgroundColor(Color_4);
		text_jixutext1.setTextColor(Fontcolor_13);
		text_jixutext2.setTextColor(Bgcolor_2);
		text01.setTextColor(Fontcolor_13);
		text02.setTextColor(Fontcolor_13);
		text03.setTextColor(Fontcolor_3);
		text04.setTextColor(Fontcolor_3);
		text05.setTextColor(Fontcolor_3);
		text_data1.setTextColor(Fontcolor_3);
		text_data2.setTextColor(Fontcolor_3);
		text_data3.setTextColor(Fontcolor_3);

		buttom_linear1.setOnClickListener(this);
		buttom_linear2.setOnClickListener(this);
		buttom_linear3.setOnClickListener(this);
		linear_jixu.setOnClickListener(this);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		dialog = new FYuanTikuDialog(getActivity(),R.style.DialogStyle,"正在加载");
		animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		//初始集合

		list_PointsBean = new ArrayList<PointsBean>();
		list_jinduBean = new ArrayList<Point_jinduBean>();

		initID(); //初始化ID
		//解析用户信息  suerInfo 和 BaseInfo
		DataUtil.getUserInfoAndBaseInfo(getActivity());

		//指南msg提示点
		buttom_msg_text =(TextView)getActivity().findViewById(R.id.buttom_msg_text);
		MyFlg.SetGuide_msg(buttom_msg_text, MyApplication.getuserInfoBean(getActivity()));
		setListView(true, true);
		setxian();
		setViewPager(null);
		scroll.fullScroll(MyScrollView.FOCUS_UP);
		//加载数据 ViewPager
		loadData(2);
		loadData(1);
		//给左边侧滑菜单设置监听
		SetDrawerlayoutLister();
		setLeft();  //设置侧滑菜单
		//设置评分对话框
		showScoreDiagle();
		//setButtom_data();
		//菜单
		getProductsData();
		//设置极光推送标签和别名
		Set<String> tagSet = new LinkedHashSet<String>();
		if(null==getActivity()){
			return;
		}
		tagSet.add(MyApplication.getuserInfoBean(getActivity()).getCity());
		MyFlg.setJPushTag(getActivity(), tagSet);
		//别名已经在get_user_baseinfo 设置了  所有在此不在设置
		Log.i("123", "别名名称: "+MyApplication.Getget_user_baseinfo(getActivity()).getUid());
		MyFlg.setJPushAlias(getActivity(), MyApplication.Getget_user_baseinfo(getActivity()).getUid()+"");
	}
	/**
	 * 显示评分对话框
	 */
	private void showScoreDiagle(){
		if(MyApplication.Getget_user_baseinfo(getActivity()).getOpen_comment_box()==1){
			Home_Score_Dialog2 home_Score_Dialog = new Home_Score_Dialog2(getActivity());
			home_Score_Dialog.showDialog();
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        if(isShowLeft==true){
			drawerlayout.openDrawer(Gravity.LEFT);
			isShowLeft =false;
		}
        if (MyFlg.ISupdateHome) {
            ChangeSubject();
            MyFlg.ISupdateHome= false;
        }
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(HomeActivity2.type==0){
			settextTile(UtilBean.list_Get_baseinfoBean); //设置标题内容
			if(MyFlg.ISupdateHome_viewpager==true){
				setViewPager(null);
				loadData(2);
				loadData(1);
			}
		}
	}
	//在用户中心改变了学科 科目等 
	public void ChangeSubject(){
		setLeft();  //设置侧滑菜单
		setListView(true, true);
		updata_subject = true;
		loadData(1);
		loadData(2);
		//setListView(true,true);
	}
	public void changeCity(Activity activity){
		//改变了城市
		//解析用户信息  suerInfo 和 BaseInfo
		DataUtil.getUserInfoAndBaseInfo(activity);
		setListView(true, true);
		setLeft();  //设置侧滑菜单
		updata_subject = true;
		loadData(1);
		loadData(2);
	}
	private void initID() {
		// TODO Auto-generated method stub
		//左边侧滑菜单
		drawerlayout=(DrawerLayout)getActivity().findViewById(R.id.drawerlayout);
		home_left_text=(TextView)getActivity().findViewById(R.id.home_left_text);
		home_left_text.setTextColor(Fontcolor_3);
        rl_home_left_top = (RelativeLayout)getActivity().findViewById(R.id.rl_home_left_top);
        rl_home_left_top.setOnClickListener(this);
        getActivity().findViewById(R.id.xian3).setBackgroundColor(Color_3);
		left_listview=(ListView)getActivity().findViewById(R.id.left_listview);
		left_listview.setDividerHeight(1);	//设置间隔线
		//left_listview.setDivider(new ColorDrawable(Color_4));
		left_listview.setDivider(new ColorDrawable(Color_4));
		//顶部
		title_text = (TextView)getActivity().findViewById(R.id.title_text);
		title_text.setTextColor(Fontcolor_1);
		getActivity().findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		//标题栏菜单
		title_left=(ImageView)getActivity().findViewById(R.id.title_left);
		title_right=(ImageView)getActivity().findViewById(R.id.title_right);

		LayoutParams layoutParams = new LinearLayout.LayoutParams(Screen_width, DensityUtil.DipToPixels(HomeActivity2.instence,118));
		relativelayout.setLayoutParams(layoutParams);
		//设置点击事件
	}
	/**
	 * 设置左边侧滑菜单
	 */
	private void setLeft(){
		//UtilBean.list_Get_baseinfoBean
		//可现实的学科数量
		int show_sum = 0;
		if(null!=getActivity()){
			DataUtil.setGet_baseinfoBean_Subject_progress(getActivity()); //设置上一次的科目进度数据
			for (int i = 0; i < UtilBean.list_Get_baseinfoBean.size(); i++) {
				get_baseinfoBean = UtilBean.list_Get_baseinfoBean.get(i);
				if(get_baseinfoBean.getStatus().equals("1")){
					show_sum =show_sum+1;
				}
			}

			for (int i = 0; i < UtilBean.list_Get_baseinfoBean.size(); i++) {
				get_baseinfoBean = UtilBean.list_Get_baseinfoBean.get(i);
				if(null!=get_baseinfoBean&&null!=get_baseinfoBean.getK()){
					if(MyApplication.getuserInfoBean(getActivity()).getExam().equals(get_baseinfoBean.getK())){
						if(null!=home_left_text &&null!=get_baseinfoBean&&null!=get_baseinfoBean.getN() ){
							home_left_text.setText(get_baseinfoBean.getN());
						}
						homeLeftListViewAdpater = new HomeLeftListViewAdpater(getActivity(), get_baseinfoBean.getSubjects(), get_baseinfoBean.getK());
						left_listview.setAdapter(homeLeftListViewAdpater);
						home_leftMenu_index=i;
						break;
					}
				}
			}
		}


		//left_listview.setOnItemClickListener(new left_listViewOnItemClick());
	}

	private void setxian(){
		View view = View.inflate(getActivity(), R.layout.item_home_list_head, null);
		LinearLayout linear_bg = (LinearLayout)view.findViewById(R.id.linear_bg);
		//linear_bg.setBackgroundColor(application.getColorBean().getBgcolor_3());
		linear_bg.setBackgroundDrawable(setTouch_Click());
		TextView text=(TextView)view.findViewById(R.id.text);
		text.setTextColor(Fontcolor_3);

		linear_bg.setOnClickListener(this);
	}

	/**
	 * 设置首页顶部viewpager数据
	 * @param suggestBean_url
	 */
	private void setViewPager(Get_home_suggestBean_url suggestBean_url){
		//now_index=1;
		list_taskscompletedViiew = new ArrayList<TasksCompletedView>();
		list = new ArrayList<View>();
		dians = new ArrayList<ImageView>();

		linear_dian.removeAllViews();
		if(suggestBean_url == null){
			View view = View.inflate(getActivity(), R.layout.load_wait, null);
			// progressBar1 = (ProgressBar)view.findViewById(R.id.progressBar1);
			myload_wait=(LinearLayout)view.findViewById(R.id.myload_wait);
			myload_wait.getBackground().setAlpha(179);
			view.findViewById(R.id.item_bg).setBackgroundColor(Bgcolor_2);
			shuaxin_linear = (LinearLayout)view.findViewById(R.id.shuaxin_linear);
			Button shuaxin_button = (Button)view.findViewById(R.id.shuaxin_button);
			shuaxin_button.setOnClickListener(this);
			list.add(view);
		}else{
			//网页形式
			for (int i = 0; i < suggestBean_url.getSuggests().length; i++) {
				View view = View.inflate(getActivity(), R.layout.item_home_webview, null);
				//WebView webview = new WebView(getActivity());
				WebView webview = (WebView) view.findViewById(R.id.webview);
				webview.setVerticalScrollBarEnabled(false); //垂直不显示
				webview.setHorizontalScrollBarEnabled(false);//水平不显示
				WebSettings webSettings = webview.getSettings();
				//设置WebView属性，能够执行Javascript脚本
				webSettings.setJavaScriptEnabled(true);
				//设置可以访问文件
				webSettings.setAllowFileAccess(true);
				//设置支持缩放
				//  webSettings.setBuiltInZoomControls(true);
				webview.loadUrl(suggestBean_url.getSuggests()[i].getUrl());
				//Log.i("mylog","url="+suggestBean_url.getSuggests()[i].getUrl());
				webview.setBackgroundColor(Bgcolor_2);
				webview.setWebViewClient(new WebViewClient(){
					@Override //加载的链接
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						// TODO Auto-generated method stub
						Log.i("123", "开始点击：：: "+url.toString());
						if(url.contains("home/")){
//							http://app_jump/home/%7B%22method%22:%22open_webview%22,%22url%22:%22http://www.baidu.com/%22%7D
							String json = url.substring(url.indexOf("home/") + 5);
							viewpagerClick(json);
							return true;
						}
						return true;
					}
					@Override //加载失败
					public void onReceivedError(WebView view, int errorCode,
												String description, String failingUrl) {
						// TODO Auto-generated method stub
						super.onReceivedError(view, errorCode, description, failingUrl);
						setViewPager(null);
						myload_wait.setVisibility(View.GONE);
						shuaxin_linear.setVisibility(View.VISIBLE);
					}
					@Override //加载完成
					public void onPageFinished(WebView view, String url) {
						// TODO Auto-generated method stub
						super.onPageFinished(view, url);
					}
				});
				list.add(view);
				ImageView imageView = addDian(i);
				dians.add(imageView);
				linear_dian.addView(imageView);
			}
		}
		if(null!=dians &&dians.size()==1 ){//如果只有一个页面  隐藏这个点
			dians.get(0).setVisibility(View.GONE);
		}
		//viewpager.setOffscreenPageLimit(4);//缓存几个页面
		viewpager.setAdapter(new ViewPagerAdpater(list));
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setDian(arg0,dians);
				/*if(now_index <= arg0){
					new Thread(new ProgressRunable(0,list_beans.get(arg0).getProgress(),list_taskscompletedViiew.get(arg0))).start();
					now_index++;
				}*/
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
		viewpager_menu.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(dians_product!=null&&dians_product.size()>0){
					setDian(arg0,dians_product);
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
	private ImageView addDian(int position){
		ImageView imageView = new ImageView(getActivity());
		int pading = DensityUtil.DipToPixels(getActivity(), 3);
		imageView.setPadding(pading, 0, pading, 0);
		if(position==0){
			imageView.setImageResource(R.drawable.dian1);
		}else{
			imageView.setImageResource(R.drawable.dian4);
		}
		return imageView;
	}
	private void setDian(int index,List<ImageView> temp_dian){
		for (int i = 0; i < temp_dian.size(); i++) {
			if(i==index){
				temp_dian.get(i).setImageResource(R.drawable.dian1);
			}else{
				temp_dian.get(i).setImageResource(R.drawable.dian4);
			}

		}
	}

	@Override  //我的错题
	public void MyError() {
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode",MyFlg.getclientcode(getActivity()));
		PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_user_wrongs(),getActivity()), 1);
	}

	@Override//其他情况  进阶之类
		public void jinjie(Products_listBean.data temp_data) {
		if(temp_data.getIs_use().equals("1")){//需要付款
			Log.i("123", "jinjie: "+temp_data.getPay_type());
			if(temp_data.getPay_type().equals("1")){//Vip 可用
				if(MyApplication.getuserInfoBean(getActivity()).getMember_status()==1 || application.getuserInfoBean(getActivity()).getMember_status()==2){
					GoJinjieDetaileActivity(temp_data.getId());
				}else{//充值
					Log.i("123", "提示: "+temp_data.getVip_text());
					showAlertDialogChoose("提示", temp_data.getVip_text(),MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_yes(),application.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_no());
				}
			}else if(temp_data.getPay_type().equals("2")){//单独支付
				Intent intent = new Intent(getActivity(),PayVIPActivity.class);
				intent.putExtra("url", adv_project_pay+"?id="+temp_data.getId()+"&type=a");
				intent.putExtra("id", temp_data.getId());
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
				getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}else if (temp_data.getPay_type().equals("0")) {
				GoJinjieDetaileActivity(temp_data.getId());
			}
		}else if(temp_data.getIs_use().equals("0")){//不需要付款
			GoJinjieDetaileActivity(temp_data.getId());
		}
	}

	@Override //测验
	public void CeYan(Products_listBean.data temp_data) {
		String type=temp_data.getType();
		Intent intent = new Intent(HomeActivity2.instence,TestingActivity.class);
		intent.putExtra("title",temp_data.getTitle());
		if(temp_data.getIs_use().equals("1")) {//需要付款
			//是会员
			if(MyApplication.getuserInfoBean(getActivity()).getMember_status()==1 || application.getuserInfoBean(getActivity()).getMember_status()==2){
				if(type.equals("3")){//智能模拟
					intent.putExtra("index", 0);
				}else  if(type.equals("4")){//真题模拟
					intent.putExtra("index", 1);
				}
				startActivity(intent);
			}else{//充值
				Log.i("123", "提示: "+temp_data.getVip_text());
				showAlertDialogChoose("提示", temp_data.getVip_text(),MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_yes(),application.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_no());
			}
		}else{
			if(type.equals("3")){//智能模拟
				intent.putExtra("index", 0);
			}else  if(type.equals("4")){//真题模拟
				intent.putExtra("index", 1);
			}
			startActivity(intent);
		}



	}

	@Override//章节练习
	public void ZhangJieLianXi() {
		Intent intent = new Intent(HomeActivity2.instence,ChapterActivity.class);
		intent.putExtra("data", (Serializable) list_PointsBean);
		startActivity(intent);
	}

	@Override //连接
	public void toWebView(String url) {
		Intent intent = new Intent(HomeActivity2.instence,WebViewActivity.class);
		intent.putExtra("url", url);
		startActivity(intent);
	}

	@Override
	public void MyHint() {
		showHint("提示", "即将开放，敬请期待","确定","确定");
	}

	class viewholder {
		TextView text1;
		TextView text2;
		TextView text3;
		FrameLayout item_bg;
		TasksCompletedView tasksCompletedView;
		ImageView imageview_bg;
	}

	/**
	 * viewpager点击
	 * @param json
	 */
	private void viewpagerClick(String json){
		try {
			json = json.replace("%22", "\"");
			json = json.replace("%7B", "{");
			json = json.replace("%7D", "}");
			Log.i("123", "json   : "+json);

			HomeViewPagerBean bean = APPUtil.getViewPagerClick(json);
			if(bean.getMethod().equals("open_webview")){
				Intent intent = new Intent(getActivity(), WebViewActivity.class);
				intent.putExtra("url", bean.getUrl());
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}else{
				List <NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("a",MyFlg.a));
				params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(getActivity())));
				params.add(new BasicNameValuePair("ver",MyFlg.android_version));
				if(bean.getMethod().equals("get_exercise_suit")){//继续练习
					dialog.show();
					params.add(new BasicNameValuePair("suit_id",bean.getSuit_id()));
					AsyncLoadApi asyncLoadApi = new AsyncLoadApi(getActivity(), myYuanClick, params, bean.getMethod(),3,4,"创建失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_exercise_suit(),getActivity()));
					asyncLoadApi.execute();
				}else{//获取套题 Create_exercise_suit
					//判断是否是错题巩固
					if(bean.getExercises_type().equals("ctgg")&&MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_need_vip()==1&&(MyApplication.getuserInfoBean(getActivity()).getMember_status()!=1 && MyApplication.getuserInfoBean(getActivity()).getMember_status()!=2)){
						//不是会员
						MyFlg.showAlertDialogChoose("提示", MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_paymsg(), MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_yes(),MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_no(),getActivity());

					}else{	//是会员 或者不需要判断
						dialog.show();
						params.add(new BasicNameValuePair("exercises_type",bean.getExercises_type()));
						params.add(new BasicNameValuePair("exercises_option",bean.getExercises_option()));
						AsyncLoadApi asyncLoadApi = new AsyncLoadApi(getActivity(), myYuanClick, params, bean.getMethod(),0,1,"创建失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getCreate_exercise_suit(),getActivity()));
						asyncLoadApi.execute();
					}
				}
			}

		}catch (Exception e) {
			//e.printStackTrace();
			Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
		}
	}
	class ProgressRunable implements Runnable {
		private int mTotalProgress; //总共
		private int mCurrentProgress;	//当前
		private TasksCompletedView mTasksView;

		public ProgressRunable(int mCurrentProgress,int mTotalProgress,TasksCompletedView mTasksView) {
			// TODO Auto-generated constructor stub
			this.mTotalProgress =mTotalProgress;
			this.mCurrentProgress = mCurrentProgress;
			this.mTasksView = mTasksView;

		}
		@Override
		public void run() {
			while (mCurrentProgress < mTotalProgress) {
				mCurrentProgress += 1;
				mTasksView.setProgress(mCurrentProgress);
				try {
					Thread.sleep(20);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void onClick(View view) {
		RequestParams params;
		Intent intent;
		// TODO Auto-generated method stub
		switch (view.getId()) {
		/*case R.id.title_left:
		//	Toast.makeText(getActivity(), "点击顶部标题栏左边菜单", 0).show();
			drawerlayout.openDrawer(Gravity.LEFT);
			break;
		case R.id.title_right:
		//	Toast.makeText(getActivity(), "点击顶部标题栏右边菜单", 0).show();
			break;*/
			case R.id.buttom_linear1://我的错题
				MyError();
				break;
			case R.id.buttom_linear2://我的收藏
				params = new RequestParams();
				params.add("a", MyFlg.a);
				params.add("ver", MyFlg.android_version);
				params.add("clientcode",MyFlg.getclientcode(getActivity()));
				PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_user_collect(),getActivity()),2);
				break;
			case R.id.buttom_linear3://我的笔记
				intent = new Intent(getActivity(),MyNoteActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent, 1);
				getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			case R.id.shuaxin_button: //刷新按钮
				shuaxin_linear.setVisibility(View.GONE);
				myload_wait.setVisibility(View.VISIBLE);
				loadData(2);
				break;
			case R.id.linear_bg://点击选择科目
				//drawerlayout.openDrawer(Gravity.LEFT);
				//调到练习
				lianxiClick.toLianx();
				break;
			case R.id.rl_home_left_top://点击左侧侧边栏列表标题
				intent = new Intent(getActivity(), SubjectSelectActivity.class);
//				intent.putExtra("type", 2);
//				intent.putExtra("title", "修改报考学科");
                intent.putExtra("out_way","top2bottom");
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent,0x00066);
				getActivity().overridePendingTransition(R.anim.slide_in_bottom,0);
				break;
			default:
				break;
		}
	}
	class listviewOnItemClick implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int point,
								long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), Activity_homeChildView.class);
			if(null==list_PointsBean){
			}else{
				intent.putExtra("point", list_PointsBean.get(point).getPoints());
				if(null==list_PointsBean.get(point).getPoint_jinduBean()){

				}else{
					intent.putExtra("jindu", list_PointsBean.get(point).getPoint_jinduBean().getPoints());

				}
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}

		}
	}
	private List<Point_jinduBean> get_situation(){
		List<Point_jinduBean> list = new ArrayList<Point_jinduBean>();
		SharedPreferences sp = getActivity().getSharedPreferences("get_situation", Context.MODE_PRIVATE);
		try {
			JSONObject jsonObject = new JSONObject(sp.getString("get_situation", "0")).getJSONObject("data");
			int user_wrongs = jsonObject.getInt("user_wrongs");
			int user_collect = jsonObject.getInt("user_collect");
			int user_note = jsonObject.getInt("user_note");
			text_data1.setText(user_wrongs+" 题");
			text_data2.setText(user_collect+" 题");
			text_data3.setText(user_note+" 题");

			if(jsonObject.getString("exam").equals(MyApplication.getuserInfoBean(getActivity()).getExam())){
				JSONArray array = jsonObject.getJSONArray("subjects");
				for (int i = 0; i < array.length(); i++) {
					JSONObject temp = array.getJSONObject(i);
					if(temp.getString("k").equals(MyApplication.getuserInfoBean(getActivity()).getSubject())){

						JSONArray points = temp.getJSONArray("points");
						for (int j = 0; j < points.length(); j++) {
							JSONObject temp_point = points.getJSONObject(j);

							Point_jinduBean bean = new Point_jinduBean();
							bean.setId(temp_point.getString("id"));

							bean.setTot_r(temp_point.getString("tot_r"));
							bean.setExe_c(temp_point.getString("exe_c"));
							bean.setExe_s(temp_point.getString("exe_s"));
							bean.setExe_r(temp_point.getString("exe_r"));
							bean.setWro_c(temp_point.getString("wro_c"));
							bean.setWro_t(temp_point.getString("wro_t"));
							bean.setWro_w(temp_point.getString("wro_w"));
							bean.setWro_r(temp_point.getString("wro_r"));

							//bean.setPoints("["+temp_point.toString()+"]");
							bean.setPoints(temp_point.toString());
							list.add(bean);
						}
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return list;

	}
	private List<PointsBean>  getPoints(){
		List<PointsBean> list = new ArrayList<PointsBean>();

		SharedPreferences sp = getActivity().getSharedPreferences("get_baseinfo", Context.MODE_PRIVATE);
		try {
			JSONArray array = new JSONObject(sp.getString("get_baseinfo", "0")).getJSONObject("data").getJSONArray("exam_structure");
			for (int i = 0; i < array.length(); i++) {
				JSONObject temp = array.getJSONObject(i);
				if(temp.getString("k").equals(MyApplication.getuserInfoBean(getActivity()).getExam())){
					JSONArray subjects = temp.getJSONArray("subjects");
					for (int j = 0; j < subjects.length(); j++) {
						JSONObject temp2 = subjects.getJSONObject(j);
						if(temp2.getString("k").equals(MyApplication.getuserInfoBean(getActivity()).getSubject())){
							JSONArray points1 = temp2.getJSONArray("points");
							for (int k = 0; k < points1.length(); k++) {
								JSONObject temp_points = points1.getJSONObject(k);
								PointsBean bean = new PointsBean();
								bean.setId(temp_points.getString("id"));
								bean.setN(temp_points.getString("n"));
								bean.setEs(temp_points.getString("es"));
								bean.setPoints(temp_points.toString());
								list.add(bean);
							}
						}
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(null==getActivity()){
			}else{
				super.handleMessage(msg);
				switch (msg.what) {
					case 1://进度接口加载成功
						setListView(updata_subject,false);
						DataUtil.setGet_baseinfoBean_Subject_progress(getActivity());
						//homeLeftListViewAdpater.notifyDataSetChanged();
						setLeft();
						updata_subject = false;
						break;
					case 2: //加载失败
						shuaxin_linear.setVisibility(View.VISIBLE);
						myload_wait.setVisibility(View.GONE);
						break;
					case 3: //点击左侧菜单 成功
						//	Toast.makeText(getActivity(), "点击左侧菜单成功!", 0).show();
						MyApplication.userInfoBean = APPUtil.getUser_isRegistered(getActivity());
						//设置指南Msg
						MyFlg.SetGuide_msg(buttom_msg_text,MyApplication.getuserInfoBean(getActivity()));
						setListView(true,true);
						settextTile(UtilBean.list_Get_baseinfoBean);
						//homeLeftListViewAdpater.notifyDataSetChanged();
						MyFlg.isLoadData = true;
						dialog.dismiss();
						setLeft();
						loadData(2);
						loadData(1);
						break;
					case 99://点击左侧菜单 失败
						dialog.dismiss();
						shuaxin_linear.setVisibility(View.VISIBLE);
						myload_wait.setVisibility(View.GONE);
						break;
					case 4://加载首页ViewPager
						linear_dian.removeAllViews();
						Get_home_suggestBean_url suggestBean_url = APPUtil.get_home_suggest_url(getActivity());
						setViewPager(suggestBean_url);

						if(suggestBean_url!=null&&suggestBean_url.getContinues()!=null&&suggestBean_url.getContinues().length>0){
							text_jixutext1.setText(suggestBean_url.getContinues()[0].getContent());
							linear_jixu.setVisibility(View.VISIBLE);
							linear_jixu.setOnClickListener(new jixulianxiClick(suggestBean_url.getContinues()[0].getUrl()));
						}else{
							linear_jixu.setVisibility(View.GONE);
						}
						MyFlg.ISupdateHome_viewpager=false;
						break;

					default:
						break;
				}
			}
		}
	}
	class jixulianxiClick implements OnClickListener{
		private String json;


		public jixulianxiClick(String json) {
			this.json = json;
		}
		@Override
		public void onClick(View view) {
			viewpagerClick(json);
		}
	}

	//设置ListView                是否要合并数据
	/**
	 * 每次刷新进度
	 * @param loadPointsBean  true加载科目 false 不加载科目
	 * @param isNewAdpater True 新建Adpater false 更新
	 */
	private void setListView(Boolean loadPointsBean,Boolean isNewAdpater) {
		// TODO Auto-generated method stub
		if(loadPointsBean==true){
			if(null!=list_PointsBean){
				list_PointsBean.clear();
			}
			list_PointsBean.addAll(getPoints());
		}
		list_jinduBean.clear();
		list_jinduBean.addAll(get_situation());
		for (int i = 0; i < list_PointsBean.size(); i++) {
			String id = list_PointsBean.get(i).getId();
			for (int j = 0; j <list_jinduBean.size(); j++) {
				if (list_jinduBean.get(j).getId().equals(id)) {
					list_PointsBean.get(i).setPoint_jinduBean(list_jinduBean.get(j));
				}
			}
		}
		if(null==adpater || isNewAdpater){
			adpater = new Home_childView_listview(getActivity(), list_PointsBean);
			listview.setAdapter(adpater);
			listview.setDivider(new ColorDrawable(Color_4));
			listview.setDividerHeight(1);
			listview.setOnItemClickListener(new listviewOnItemClick());
		}else{

			adpater.notifyDataSetChanged();
		}
		scroll.fullScroll(MyScrollView.FOCUS_UP);
		scroll.smoothScrollTo(0,0);
	}
	/**
	 *
	 * @param index  1加载进度  2加载ViewPager
	 */
	public void loadData(int index){
		//加载数据
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		if(null==getActivity()){
			return;
		}
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(getActivity())));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("mobile_model",MyFlg.getmobile_model()));

		switch (index) {
			case 1: //加载进度
				AsyncGet_situationAndGet_subjects_progress asyncGetApi = new AsyncGet_situationAndGet_subjects_progress(getActivity(), handler, params);
				asyncGetApi.execute();
				break;
			case 2: //加载ViewPager
//				Log.i("123", "首页轮播::: "+params.toString());
				AsyncGet_home_suggest asyncGet_home_suggest = new AsyncGet_home_suggest(getActivity(), handler, params);
				asyncGet_home_suggest.execute();
				break;
			default:
				break;
		}
	}
	private void settextTile(List<Get_baseinfoBean> list){
		for (int i = 0; i < list.size(); i++) {
			if(MyApplication.getuserInfoBean(getActivity()).getExam().equals(list.get(i).getK())){
				for (int j = 0; j < list.get(i).getSubjects().length; j++) {
					if(list.get(i).getSubjects()[j].getK().equals(MyApplication.getuserInfoBean(getActivity()).getSubject())){
						title_text.setText(list.get(i).getSubjects()[j].getN());
						Log.i("123", "标题: "+list.get(i).getSubjects()[j].getN());
						break;
					}
				}
			}
		}
	}
	//用户点击左边ListView
	public void clickLeft(int point){
		if(get_baseinfoBean.getK().equals(MyApplication.getuserInfoBean(getActivity()).getExam()) && get_baseinfoBean.getSubjects()[point].getK().equals(application.getuserInfoBean(getActivity()).getSubject())){
			//如果与当前科目是相同的 点击无效
			drawerlayout.closeDrawer(Gravity.LEFT);
		}else{
			FragmentVideo_New.isNeedUpdata=true;
			application.isLoadTesting = true;
			setViewPager(null);
			MyFlg.clickSum++;
			//now_index = 1;
			drawerlayout.closeDrawer(Gravity.LEFT);
			UpdateUserAsync_home async = new UpdateUserAsync_home(null,handler, getActivity(), MyFlg.getclientcode(getActivity()), null, null, null, get_baseinfoBean.getK(), null, get_baseinfoBean.getSubjects()[point].getK(), null, null,3,null,null,null,null);
			async.execute();
			getProductsData();

		}
	}
	class myYuanClick extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(null!=getActivity()){
				Intent intent;
				Bundle bundle;
				Create_Exercise_suit_2Bean create_Exercise_suit_2Bean;
				switch (msg.what) {
					case 0://成功
						dialog.dismiss();
						create_Exercise_suit_2Bean = APPUtil.create_exercise_suit_2(getActivity());
						if(null==create_Exercise_suit_2Bean){
							Toast.makeText(getActivity(), "创建失败", Toast.LENGTH_SHORT).show();
							return;
						}
						intent = new Intent(getActivity(), Reading_QuestionsActivity.class);
						bundle = new Bundle();
						bundle.putSerializable("bean", create_Exercise_suit_2Bean);
						intent.putExtras(bundle);
						intent.putExtra("WHICH_GROUP", 0);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						break;
					case 1://失败
						dialog.dismiss();
						break;

					case 3://继续练习
						dialog.dismiss();
						create_Exercise_suit_2Bean = APPUtil.create_exercise_suit_2_go_on(getActivity());
						for (int i = 0; i < create_Exercise_suit_2Bean.getGroups().length; i++) {
							for (int j = 0; j < create_Exercise_suit_2Bean.getGroups()[i].getInfos().length; j++) {
								for (int j2 = 0; j2 <create_Exercise_suit_2Bean.getGroups()[i].getInfos()[j].getQuestions().length; j2++) {
									create_Exercise_suit_2Bean.getGroups()[i].getInfos()[j].getQuestions()[j2].setAnswer(create_Exercise_suit_2Bean.getGroups()[i].getInfos()[j].getQuestions()[j2].getAnswer().replace(",", ""));
								}
							}
						}

						intent = new Intent(getActivity(), Reading_QuestionsActivity.class);
						bundle = new Bundle();
						bundle.putSerializable("bean", create_Exercise_suit_2Bean);
						intent.putExtras(bundle);
						intent.putExtra("type", 3);
						intent.putExtra("group_index", create_Exercise_suit_2Bean.getLast_group());
						intent.putExtra("infos_index", create_Exercise_suit_2Bean.getLast_infos());
						intent.putExtra("question_index", create_Exercise_suit_2Bean.getLast_question());
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 4:
						dialog.dismiss();
						break;
					default:
						break;
				}
			}
		}
	}
	//设置点击效果
	private Drawable setTouch_Click(){
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(getResources().getColor(R.color.light_gray)));
		drawable.addState(new int[]{}, new ColorDrawable(Bgcolor_3));
		return drawable;
	}

	public interface toLianxiClick {
		public void toLianx();
	}
	public void setLianxiClick(toLianxiClick lianxiClick) {
		this.lianxiClick = lianxiClick;
	}

	private void SetDrawerlayoutLister(){
		drawerlayout.setDrawerListener(new DrawerListener() {
			/**
			 * 当抽屉滑动状态改变的时候被调用
			 * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
			 * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
			 */
			@Override
			public void onDrawerStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			/**
			 * 当抽屉被滑动的时候调用此方法
			 * arg1 表示 滑动的幅度（0-1）
			 */
			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				// TODO Auto-generated method stub

			}

			/**
			 * 当一个抽屉被完全打开的时候被调用
			 */
			@Override
			public void onDrawerOpened(View arg0) {
				// TODO Auto-generated method stub

			}

			/**
			 * 当一个抽屉完全关闭的时候调用此方法
			 */
			@Override
			public void onDrawerClosed(View arg0) {
				// TODO Auto-generated method stub
				setLeft();
			}
		});
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
	 * @param type    1 我的错题  2 我的收藏  3 进阶练习
	 */
	private void PostApi(RequestParams params,String apiUrl,final int type){
		if(type!=3){
			dialog.show();
		}
		Log.i("123", "url="+apiUrl+"   params"+params.toString());
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		client.post(apiUrl, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				//Log.i("mylog", "result="+result);
				Gson gson = new Gson();
				if (type == 1) {
					MyWrongsBean bean = new MyWrongsBean();
					bean = gson.fromJson(result, MyWrongsBean.class);
					if (bean.getStatus() == false) {
						Toast.makeText(getActivity(), "加载失败，请稍后重试。", Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent(getActivity(), MyWrongsActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", bean);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					}
				} else if (type == 2) {
					MyCollectBean bean = new MyCollectBean();
					bean = gson.fromJson(result, MyCollectBean.class);
					if (bean.getStatus() == false) {
						Toast.makeText(getActivity(), "加载失败，请稍后重试。", Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent(getActivity(), MyCollectionActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", bean);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					}
				} else if (type == 3) {//进阶练习
					isFirstAddProduct=false;
					Products_listBean bean = new Products_listBean();
					bean = gson.fromJson(result,Products_listBean.class);
					if(bean!=null&&bean.getData()!=null&&bean.getData().size()>0){
						framen_product.setVisibility(View.VISIBLE);
						productViews = new ArrayList<View>();
						dians_product = new ArrayList<ImageView>();
						dians_product.clear();
						List<Products_listBean.data> temp_data=new ArrayList<Products_listBean.data>();
						linear_product_dian.removeAllViews();

						for (int i=0;i<bean.getData().size();i++){
							if(i!=0&&i%8==0){
								setProduct(temp_data);
								temp_data=new ArrayList<Products_listBean.data>();
								temp_data.add(bean.getData().get(i));
								if(i==bean.getData().size()-1){
									setProduct(temp_data);
								}
							}else{
								temp_data.add(bean.getData().get(i));
								if(i==bean.getData().size()-1){
									setProduct(temp_data);
								}
							}
						}
						if(dians_product!=null&&dians_product.size()>=2){
							linear_product_dian.setVisibility(View.VISIBLE);
						}else{
							linear_product_dian.setVisibility(View.GONE);
						}
						viewpager_menu.setAdapter(new ViewPagerAdpater(productViews));
					}
				}else{
					framen_product.setVisibility(View.GONE);
					viewpager_menu.setVisibility(View.GONE);
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(getActivity(), "网络异常。", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}
		});
	}
	private List<View> productViews;
	private Boolean isFirstAddProduct=false;
	private void setProduct(List<Products_listBean.data> temp_data){
		View view = View.inflate(HomeActivity2.instence,R.layout.item_home_viewpager_products,null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView);
		Home_product_adapter adatper = new Home_product_adapter(HomeActivity2.instence,temp_data);
		adatper.setClickBack(this);
		gridView.setAdapter(adatper);
		if(isFirstAddProduct==false){
			ImageView imageView = addDian(0);
			dians_product.add(imageView);
			linear_product_dian.addView(imageView);
			if(temp_data.size()<=4){ //如果只有一行
				int dip_90=DensityUtil.DipToPixels(HomeActivity2.instence,105);
			FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,dip_90);
				viewpager_menu.setLayoutParams(params);
			}else{
				int dip_170=DensityUtil.DipToPixels(HomeActivity2.instence,200);
				FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,dip_170);
				viewpager_menu.setLayoutParams(params);
			}
			isFirstAddProduct = true;
		}else{
			ImageView imageView = addDian(1);
			dians_product.add(imageView);
			linear_product_dian.addView(imageView);
		}
		linear_product_dian.setVisibility(View.VISIBLE);
		productViews.add(view);
	}
	//获取进阶数据
	private void getProductsData(){

		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(getActivity()));
		if(application.getCommonInfo_API_functions(getActivity()).getGet_products_list()!=null){
			String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_products_list(),getActivity());
			PostApi(params, apiUrl, 3);
		}else{
			framen_product.setVisibility(View.GONE);

		}

	}

	/**
	 * 进击的点击事件
	 * @author Administrator
	 *
	 */
	class JinjieClick implements OnClickListener{
		private int i;
		public JinjieClick(int i) {
			// TODO Auto-generated constructor stub
			this.i=i;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			data temp_data = jinjieList.get(i);
			if(temp_data.getIs_use().equals("1")){//需要付款
				if(temp_data.getPay_type().equals("1")){//Vip 可用
					//是会员
					if(MyApplication.getuserInfoBean(getActivity()).getMember_status()==1 || application.getuserInfoBean(getActivity()).getMember_status()==2){
						GoJinjieDetaileActivity(temp_data.getId());
					}else{//充值
						showAlertDialogChoose("提示", temp_data.getVip_text(),MyApplication.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_yes(),application.Getget_user_baseinfo(getActivity()).getCtgg_paybtn_no());
					}
				}else if(temp_data.getPay_type().equals("2")){//单独支付
					Intent intent = new Intent(getActivity(),PayVIPActivity.class);
					intent.putExtra("url", adv_project_pay+"?id="+temp_data.getId()+"&type=a");
					intent.putExtra("id", temp_data.getId());
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
					getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				}
			}else if(temp_data.getIs_use().equals("0")){//不需要付款
				GoJinjieDetaileActivity(temp_data.getId());
			}
		}
	}
	private void GoJinjieDetaileActivity(String pid){
		Intent intent = new Intent(getActivity(), JinJieDetailedActivity.class);
		intent.putExtra("pid", pid);
		startActivityForResult(intent, 2);
		getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 if(requestCode==2&&resultCode==getActivity().RESULT_OK){//切换科目 显示
			drawerlayout.openDrawer(Gravity.LEFT);
		} else if(requestCode==0&&resultCode==getActivity().RESULT_OK){//充值返回
			 getProductsData();
		}
	}



	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(getActivity(), title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								Intent intent = new Intent(getActivity(),PayVIPActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
								getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								break;
							default:
								break;
						}
					}
				});
	}
	public void showHint(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialog(HomeActivity2.instence, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								break;
							default:
								break;
						}
					}
				},0);
	}
}