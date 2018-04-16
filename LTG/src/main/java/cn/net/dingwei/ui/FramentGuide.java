package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import listview_DownPullAndUpLoad.XListView;
import listview_DownPullAndUpLoad.XListView.IXListViewListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.adpater.Guide_RefreshListView_Adpater;
import cn.net.dingwei.adpater.ViewPagerAdpater;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class FramentGuide extends Fragment implements OnClickListener,IXListViewListener{
	private Button btn_left,btn_right;
	private int flg=0; //0  左边按钮已点击  1 右边
	private XListView listview,listview2;

	private Guide_RefreshListView_Adpater adapter;
	private Guide_RefreshListView_Adpater adapter2;
	private myhandler handler = new myhandler();
	private List<Get_guide_msg_listBean.msglist> list_guides;
	private List<Get_guide_msg_listBean.msglist> list_guides2;
	private ViewPager viewpager;
	private List<View> list_views;


	private Boolean isLoadFirst1=false; //第一次加载是否成功  如果成功 第二次刷新失败也会隐藏头布局
	private Boolean isLoadFirst2=false;
	private TextView text_noData1,text_noData2;
	private int clickSum=0;
	private MyApplication application;

	//顶部按钮需要的背景Drawable
	private Drawable drawable_left_white;
	private Drawable drawable_right_bg2;

	private Drawable drawable_left_bg2;
	private Drawable drawable_right_white;

	private SharedPreferences sharedPreferences;
	private int Bgcolor_1=0,Bgcolor_2=0;
	private String gongjuxiang_path="";
	private String gongjuxiang_url="";
	private ImageView btn_tool;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		application = MyApplication.myApplication;
		sharedPreferences =getActivity().getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		gongjuxiang_path = sharedPreferences.getString("gongjuxiang", "");
		gongjuxiang_url  =sharedPreferences.getString("botmsglist", "");

		View view = inflater.inflate(R.layout.activity_guide, null, false);
		btn_left = (Button)view.findViewById(R.id.btn_left);
		btn_right = (Button)view.findViewById(R.id.btn_right);
		btn_tool = (ImageView)view.findViewById(R.id.btn_tool);
		viewpager=(ViewPager)view.findViewById(R.id.viewpager);
		view.findViewById(R.id.title_linear_bg).setBackgroundColor(Bgcolor_1);

		btn_tool.setImageBitmap(BitmapFactory.decodeFile(gongjuxiang_path));
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
		btn_tool.setOnClickListener(this);
		if(null!=getActivity()&&null!=application.GetguideBean(getActivity()) && application.GetguideBean(getActivity()).getGuide_category().length>=2){
			btn_left.setText(application.GetguideBean(getActivity()).getGuide_category()[0].getN());
			btn_right.setText(application.GetguideBean(getActivity()).getGuide_category()[1].getN());
			btn_left.setTextColor(Bgcolor_2);
			btn_right.setTextColor(Color.WHITE);
		}
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);


		clickSum = MyFlg.clickSum;
		float [] radii_left = new float[]{10,10,0,0,0,0,10,10};
		float [] radii_right = new float[]{0,0,10,10,10,10,0,0};
		drawable_left_white=MyFlg.setViewRaduis_other(Color.WHITE,Color.WHITE, 0, radii_left);
		drawable_right_bg2 =  MyFlg.setViewRaduis_other(Bgcolor_2, Color.WHITE, 2,radii_right);
		drawable_left_bg2=MyFlg.setViewRaduis_other(Bgcolor_2, Color.WHITE, 2,radii_left);;
		drawable_right_white=MyFlg.setViewRaduis_other(Color.WHITE,Color.WHITE, 0, radii_right);;

		btn_right.setBackgroundDrawable(drawable_right_bg2);
		btn_left.setBackgroundDrawable(drawable_left_white);

		list_views = new ArrayList<View>();
		list_guides = new ArrayList<Get_guide_msg_listBean.msglist>();
		list_guides2 = new ArrayList<Get_guide_msg_listBean.msglist>();
		myListViewOnitemClick ListviewOnitemClick = new myListViewOnitemClick();
		for (int i = 0; i < 2; i++) {
			if(i==0){
				View view = View.inflate(getActivity(), R.layout.refresh_listview, null);
				listview = (XListView)view.findViewById(R.id.listview);
				listview.setAdapter(null);
				listview.setPullLoadEnable(true);
				listview.setXListViewListener(this);
				listview.setOnItemClickListener(ListviewOnitemClick);
				text_noData1=(TextView)view.findViewById(R.id.text_noData);
				list_views.add(view);
			}else if(i==1){
				View view = View.inflate(getActivity(), R.layout.refresh_listview, null);
				listview2 = (XListView)view.findViewById(R.id.listview);
				listview2.setAdapter(null);
				listview2.setPullLoadEnable(true);
				listview2.setXListViewListener(this);
				listview2.setOnItemClickListener(ListviewOnitemClick);
				text_noData2=(TextView)view.findViewById(R.id.text_noData);
				list_views.add(view);

			}/*else if(i==2){
					String url = "http://wv.liantigou.com/1.0/webview/accounting/botmsglist";
					
					WebView webview = new WebView(application);
					  WebSettings webSettings = webview.getSettings();  
				      //设置WebView属性，能够执行Javascript脚本    
				        webSettings.setJavaScriptEnabled(true);    
				        //设置可以访问文件  
				        webSettings.setAllowFileAccess(true);  
				      webview.loadUrl(url);
					list_views.add(webview);
				}*/

		}
		viewpager.setAdapter(new ViewPagerAdpater(list_views));
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int point) {
				// TODO Auto-generated method stub
				flg = point;
				setButton(flg);
				if(isLoadFirst2==false && point==1){
					loadApi("", 10, 11,flg);
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
		loadApi("", 0, 1, 0);
	}
	/**
	 * 如果用户更换了科目 那么刷新数据
	 */
	public void setonRefresh(Boolean isLoad){
		if(isLoadFirst1==true&&clickSum!=MyFlg.clickSum){
			loadApi("", 0, 1, 0);
			if(isLoadFirst2==true){
				loadApi("", 10, 11, 1);
			}
			clickSum = MyFlg.clickSum;
		}else if(isLoadFirst1==true && MyFlg.ISupdateguide == true){
			loadApi("", 0, 1, 0);
			if(isLoadFirst2==true){
				loadApi("", 10, 11, 1);
			}
			MyFlg.ISupdateguide = false;
		}else if(isLoadFirst1==true &&isLoad){
			loadApi("", 0, 1, 0);
			if(isLoadFirst2==true){
				loadApi("", 10, 11, 1);
			}
		}


	}

	class myListViewOnitemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int point,
								long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), WebViewActivity.class);
			if(flg==0){//第一个列表
				if(point-1 <list_guides.size() &&point-1>=0 ){
					intent.putExtra("url", application.guide_listview_loadurl+"?id="+list_guides.get(point-1).getId());
				}
			}else{//第二个列表
				if(point-1 <list_guides2.size() &&point-1>=0 ){
					intent.putExtra("url", application.guide_listview_loadurl+"?id="+list_guides2.get(point-1).getId());
				}
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}

	}
	/**
	 *
	 * @param type //0 目前处于点击了左边     1 右边
	 */
	private void setButton(int type){
		if(type==0){ //点击左边
			btn_left.setBackgroundDrawable(drawable_left_white);
			btn_right.setBackgroundDrawable(drawable_right_bg2);
			//	btn_left.setBackgroundResource(R.drawable.guide_button_white_left);
			//btn_right.setBackgroundResource(R.drawable.guide_button_bg2_right);
			btn_left.setTextColor(Bgcolor_2);
			btn_right.setTextColor(Color.WHITE);
		}else if(type==1){
			btn_left.setBackgroundDrawable(drawable_left_bg2);
			btn_right.setBackgroundDrawable(drawable_right_white);
			//btn_left.setBackgroundResource(R.drawable.guide_button_bg2_left);
			//btn_right.setBackgroundResource(R.drawable.guide_button_white_right);
			btn_left.setTextColor(Color.WHITE);
			btn_right.setTextColor(Bgcolor_2);
		}
	}
	@Override//ListView 下拉刷新
	public void onRefresh() {
		// TODO Auto-generated method stub
		if(flg==0){
			loadApi("", 0, 1, flg);
		}else{
			loadApi("", 10, 11, flg);
		}

	}
	@Override//上拉加载
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if(flg==0 && list_guides.size()>=1){
			loadApi(list_guides.get(list_guides.size()-1).getId()+"", 2, 3, flg);
		}else if(flg==1 && list_guides2.size()>=1){
			loadApi(list_guides2.get(list_guides2.size()-1).getId()+"", 20, 30, flg);
		}
	}
	class myhandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(null==getActivity()){
			}else{
				super.handleMessage(msg);
				Get_guide_msg_listBean bean;
				switch (msg.what) {
					case 0: //ListView1加载成功
						isLoadFirst1 =true;
						listview.setFistLoad(true);
						list_guides.clear();
						bean = APPUtil.get_guide_msg_list(getActivity());
						for (int i = 0; i <bean.getMsglist().length; i++) {
							list_guides.add(bean.getMsglist()[i]);
						}
						if(list_guides.size()>0){
							text_noData1.setVisibility(View.GONE);
							listview.setVisibility(View.VISIBLE);
							if(null==adapter){
								adapter = new Guide_RefreshListView_Adpater(getActivity(), list_guides,listview);
								listview.setAdapter(adapter);
							}else{
								adapter.notifyDataSetInvalidated();
								//adapter.notifyDataSetChanged();
								listview.stopRefresh();
							}
							//初始化脚布局
							if(list_guides.size()>=2){
								listview.initFotter();
							}
							//全部加载完
							if(bean.getNext()==0){
								listview.setLoading_all_over();
							}
						}else{
							text_noData1.setVisibility(View.VISIBLE);
							listview.setVisibility(View.GONE);
						}
						break;
					case 1://Listview1加载失败
						if(isLoadFirst1==false){
							listview.setNotInterNet_head();
						}else{
							listview.stopRefresh();
						}
						break;
					case 10://ListView2加载成功
						isLoadFirst2 = true;
						listview2.setFistLoad(true);
						list_guides2.clear();
						bean = APPUtil.get_guide_msg_list(getActivity());
						for (int i = 0; i <bean.getMsglist().length; i++) {
							list_guides2.add(bean.getMsglist()[i]);
						}
						if(list_guides2.size()>0){

							text_noData2.setVisibility(View.GONE);
							listview2.setVisibility(View.VISIBLE);
							if(null==adapter2){
								adapter2 = new Guide_RefreshListView_Adpater(getActivity(), list_guides2,listview2);
								listview2.setAdapter(adapter2);
							}else{
								listview2.stopRefresh();
								adapter2.notifyDataSetInvalidated();

							}
							//初始化脚布局
							if(list_guides2.size()>=2){
								listview2.initFotter();
							}
							//全部加载完
							if(bean.getNext()==0){
								listview2.setLoading_all_over();
							}
						}else{
							text_noData2.setVisibility(View.VISIBLE);
							listview2.setVisibility(View.GONE);
						}
						break;
					case 11://ListView加载失败
						if(isLoadFirst2==false){
							listview2.setNotInterNet_head();
						}else{
							listview2.stopRefresh();
						}
						break;
					case 2://上拉加载  OK
						bean = APPUtil.get_guide_msg_list(getActivity());
						for (int i = 0; i <bean.getMsglist().length; i++) {
							list_guides.add(bean.getMsglist()[i]);
						}
						listview.stopLoadMore();
						adapter.notifyDataSetChanged();

						//已加载完全部数据
						//if(bean.getMsglist().length<2){
						if(bean.getNext()==0){
							listview.setLoading_all_over();
						}
						break;
					case 3:
						listview.setNotInterNet_footer();
						break;
					case 20:
						bean = APPUtil.get_guide_msg_list(getActivity());
						for (int i = 0; i <bean.getMsglist().length; i++) {
							list_guides2.add(bean.getMsglist()[i]);
						}
						listview2.stopLoadMore();
						adapter2.notifyDataSetChanged();

						//已加载完全部数据
						//if(bean.getMsglist().length<2){
						if(bean.getNext()==0){
							listview2.setLoading_all_over();
						}
						break;
					case 30:
						listview2.setNotInterNet_footer();
						break;
					default:
						break;
				}
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_left://点击左边按钮
				if(flg!=0){
					setButton(0);
					flg = 0;
					viewpager.setCurrentItem(flg);
				}

				break;
			case R.id.btn_right://点击右边按钮
				if(flg!=1){
					setButton(1);
					flg = 1;
					viewpager.setCurrentItem(flg);
				}
				break;
			case R.id.btn_tool://工具箱
				//	Log.i("mylog", "链接：="+gongjuxiang_url);
				if(!gongjuxiang_url.equals("")){
					Intent intent = new Intent(getActivity(), WebViewActivity.class);
					intent.putExtra("url", gongjuxiang_url);
					startActivity(intent);
				}
				break;
			default:
				break;
		}
	}

	/**
	 *
	 * @param last_msgid 上拉加载时调用（不需要的时候填""）
	 * @param sucess 成功返回的
	 * @param failure 失败
	 * @param key_index 当前的K是第几个
	 */
	private void loadApi(String last_msgid,int sucess,int failure,int key_index){
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(getActivity())));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		if(null!=application.GetguideBean(getActivity())){
			params.add(new BasicNameValuePair("guide_category_key",application.GetguideBean(getActivity()).getGuide_category()[key_index].getKey()));
		}
		params.add(new BasicNameValuePair("last_msgid",last_msgid));

		AsyncLoadApi asyncLoadApi = new AsyncLoadApi(getActivity(), handler, params, "get_guide_msg_list", sucess, failure,MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_guide_msg_list(),getActivity()));
		asyncLoadApi.execute();
	}

}
