package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Create_exercise_suitBean;
import cn.net.dingwei.Bean.Point_jinduBean;
import cn.net.dingwei.Bean.PointsBean;
import cn.net.dingwei.adpater.Home_childView_listview;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * @author Administrator
 *
 */
public class Activity_homeChildView extends ParentActivity implements OnClickListener{
	private TextView text_num1,text_num2,text_num3,text_num5;
	private LinearLayout black;
	private TextView title_text;
	private ListView listview;
	//private Button home_childview_bottom_btn1,home_childview_bottom_btn2;
	private myHandler handler = new myHandler();
	private String subjectid;
	private FYuanTikuDialog dialog;
	private MyApplication application;
	//标题  学习进度
	private TextView title_text1,Learning_progress,xiaji_kaodian,xian,text_ctgg;
	private LinearLayout kaopin_linear,linear_start,linear_ctgg,linear_top_bg;
	private ImageView image_lianxi,image_ctgg,image_ctgg_right;
	private View xian1;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_7=0,Fontcolor_1=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_8=0,Color_4=0,Color_3=0;
	private String Lianxi_w,Ctgg_w;//图片路径
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_home_childview);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_7 = sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_8 = sharedPreferences.getInt("bgcolor_8", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Lianxi_w = sharedPreferences.getString("Lianxi_w", "");
		Ctgg_w = sharedPreferences.getString("Ctgg_w", "");
		initID();
		initData();
		//记录Activity
		MyFlg.listActivity.add(this);
	}
	private void initID() {
		// TODO Auto-generated method stub
		title_text=(TextView)findViewById(R.id.title_tx);
		black=(LinearLayout)findViewById(R.id.linear_Left);
		findViewById(R.id.linear_right).setVisibility(View.GONE);
		text_num1=(TextView)findViewById(R.id.text_num1);
		text_num2=(TextView)findViewById(R.id.text_num2);
		text_num3=(TextView)findViewById(R.id.text_num3);
		text_num5=(TextView)findViewById(R.id.text_num5);
		listview=(ListView)findViewById(R.id.listview);
		//home_childview_bottom_btn1=(Button)findViewById(R.id.home_childview_bottom_btn1);
		//home_childview_bottom_btn2=(Button)findViewById(R.id.home_childview_bottom_btn2);
		TextView title_back_text = (TextView)findViewById(R.id.title_back_text);
		xiaji_kaodian = (TextView)findViewById(R.id.xiaji_kaodian);
		title_text1=(TextView)findViewById(R.id.title_text1);
		Learning_progress=(TextView)findViewById(R.id.Learning_progress);
		kaopin_linear=(LinearLayout)findViewById(R.id.kaopin_linear);

		linear_start=(LinearLayout)findViewById(R.id.linear_start);
		linear_ctgg=(LinearLayout)findViewById(R.id.linear_ctgg);
		linear_top_bg=(LinearLayout)findViewById(R.id.linear_top_bg);
		image_lianxi = (ImageView)findViewById(R.id.image_lianxi);
		image_ctgg = (ImageView)findViewById(R.id.image_ctgg);
		image_ctgg_right=(ImageView)findViewById(R.id.image_ctgg_right);
		xian1 = findViewById(R.id.xian1);
		xian = (TextView)findViewById(R.id.xian);
		text_ctgg=(TextView)findViewById(R.id.text_ctgg);

		title_back_text.setVisibility(View.VISIBLE);
		title_text.setVisibility(View.GONE);
		image_lianxi.setImageBitmap(BitmapFactory.decodeFile(Lianxi_w));
		image_ctgg.setImageBitmap(BitmapFactory.decodeFile(Ctgg_w));
		//设置颜色
		xiaji_kaodian.setTextColor(Fontcolor_7);
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		findViewById(R.id.linear_bg).setBackgroundColor(Bgcolor_1);
		title_text.setTextColor(Fontcolor_1);
		text_num1.setTextColor(Fontcolor_1);
		text_num2.setTextColor(Fontcolor_1);
		xian.setBackgroundColor(Color_4);
		xian1.setBackgroundColor(Color_4);
		listview.setDivider(new ColorDrawable(Color_4));
		listview.setDividerHeight(1);

		linear_top_bg.setBackgroundColor(Bgcolor_1);
		linear_start.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_8, Bgcolor_2, Bgcolor_8, 1, 10));
		linear_start.getBackground().setAlpha(178);
		linear_ctgg.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_8, Bgcolor_2, Bgcolor_8, 1, 10));
		linear_ctgg.getBackground().setAlpha(178);
		//设置监听
		linear_start.setOnClickListener(this);
		linear_ctgg.setOnClickListener(this);
		black.setOnClickListener(this);
	}

	private void initData() {
		//设置默认值
		SpannableString spannableString = new SpannableString("0%");
		// spannableString.setSpan(new TextAppearanceSpan(this, R.style.style_Learning_progress),0 , spannableString.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
		spannableString.setSpan(new AbsoluteSizeSpan(15,true),spannableString.length()-1 , spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		Learning_progress.setText(spannableString);

		Intent getBeanIntent=getIntent();
		//Get_baseinfoBean.points points = (cn.net.dingwei.Bean.Get_baseinfoBean.points) getBeanIntent.getSerializableExtra("Get_baseinfoBean.points");
		//title_text.setText(points.getN());
		String json = getBeanIntent.getStringExtra("point");
		String jindu = getBeanIntent.getStringExtra("jindu");
		if(null==json){ //看到Bugly报错而加的判断
			finish();
			return;
		}

		List<PointsBean> list = getJson(json);
		List<Point_jinduBean> list_jinduBean =getjindu(jindu);
		//合并数据
		if(null != list_jinduBean){
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list_jinduBean.size(); j++) {
					if(list.get(i).getId().equals(list_jinduBean.get(j).getId())){
						list.get(i).setPoint_jinduBean(list_jinduBean.get(j));
					}
				}
			}
		}



		setListView(list);
	}
	private void setListView(final List<PointsBean> list){
		if(list.size()<=0){
			xian.setVisibility(View.GONE);
			//xian1.setVisibility(View.GONE);
			//xiaji_kaodian.setVisibility(View.GONE);
			xiaji_kaodian.setText("无下级考点");
		}
		Home_childView_listview childView_listview = new Home_childView_listview(this, list);
		listview.setAdapter(childView_listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Activity_homeChildView.this, Activity_homeChildView.class);
				intent.putExtra("point", list.get(point).getPoints());
				if(null == list.get(point).getPoint_jinduBean()){

				}else{
					intent.putExtra("jindu", list.get(point).getPoint_jinduBean().getPoints());
				}
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}
		});
	}
	private List<Point_jinduBean> getjindu(String jindu){
		List<Point_jinduBean> list = new ArrayList<Point_jinduBean>();
		if(null==jindu){
			no_ctgg();

			return null;
		}else if("null".equals(jindu)){
			no_ctgg();
		}else{
			try {
				JSONObject jsonObject = new JSONObject(jindu);
				//JSONObject jsonObject =(JSONObject) new JSONArray(jindu).get(0);
				text_num1.setText(jsonObject.getInt("exe_c")+"");

				String tot_r_st = jsonObject.getString("tot_r"); //错题百分比
				if(MyFlg.isInt(tot_r_st)){
					SpannableString spannableString = new SpannableString(tot_r_st+"%");
					spannableString.setSpan(new AbsoluteSizeSpan(15,true),spannableString.length()-1 , spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					Learning_progress.setText(spannableString);
				}
				int number = jsonObject.getInt("wro_t")-jsonObject.getInt("wro_c"); //错题
				text_num3.setText(number+"");
				if(number==0){
					no_ctgg();
				}
				text_num5.setText(jsonObject.getInt("wro_c")+"");
				if(jsonObject.getString("points").equals("null")){
					return null;
				}
				JSONArray jsonArray = jsonObject.getJSONArray("points");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject temp_point =(JSONObject) jsonArray.get(i);
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
					bean.setPoints(temp_point.toString());
					list.add(bean);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	private List<PointsBean> getJson(String json){
		List<PointsBean> list = new ArrayList<PointsBean>();
		try {
			JSONObject jsonObject =new JSONObject(json);
			subjectid = jsonObject.getString("id");
			//title_text.setText(jsonObject.getString("n"));
			title_text1.setText(jsonObject.getString("n"));
			text_num2.setText(jsonObject.getInt("es")+"");
			String kp = jsonObject.getString("kp");
			if(MyFlg.isInt(kp)){
				setKaopin(Integer.parseInt(kp));
			}
			JSONArray array = jsonObject.getJSONArray("points");
			for (int i = 0; i < array.length(); i++) {
				PointsBean bean = new PointsBean();
				JSONObject temp = (JSONObject) array.get(i);
				bean.setId(temp.getString("id"));
				bean.setN(temp.getString("n"));
				//bean.setEs(temp.getString("es"));
				//bean.setPoints(temp.getString("points"));
				bean.setPoints(temp.toString());
				list.add(bean);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public void onClick(View v) {
		List<NameValuePair> params;
		AsyncLoadApi asyncLoadApi;
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.linear_start://开始练习
				//Toast.makeText(Activity_homeChildView.this, "点击开始练习", 0).show();
				dialog.show();
				params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("ver",MyFlg.android_version));
				params.add(new BasicNameValuePair("a",MyFlg.a));
				params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Activity_homeChildView.this)));
				params.add(new BasicNameValuePair("exercises_type","zxlx"));
				params.add(new BasicNameValuePair("exercises_option","["+subjectid+"]"));
				//   Log.i("mylog", "exercises_option="+subjectid);
				// Log.i("mylog", "url="+MyFlg.get_API_URl(application.getCommonInfo_API_functions(Activity_homeChildView.this).getCreate_exercise_suit(), Activity_homeChildView.this));
				asyncLoadApi = new AsyncLoadApi(Activity_homeChildView.this, handler, params, "create_exercise_suit",0,1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Activity_homeChildView.this).getCreate_exercise_suit(),Activity_homeChildView.this));
				asyncLoadApi.execute();

				break;
			case R.id.linear_ctgg://错题巩固
				if(MyApplication.Getget_user_baseinfo(Activity_homeChildView.this).getCtgg_need_vip()==1&&(MyApplication.getuserInfoBean(Activity_homeChildView.this).getMember_status()!=1 && MyApplication.getuserInfoBean(Activity_homeChildView.this).getMember_status()!=2)){
					//不是会员
					MyFlg.showAlertDialogChoose("提示", MyApplication.Getget_user_baseinfo(Activity_homeChildView.this).getCtgg_paymsg(), MyApplication.Getget_user_baseinfo(Activity_homeChildView.this).getCtgg_paybtn_yes(),application.Getget_user_baseinfo(Activity_homeChildView.this).getCtgg_paybtn_no(),Activity_homeChildView.this);

				}else{	//是会员 或者不需要判断
					dialog.show();
					params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("ver",MyFlg.android_version));
					params.add(new BasicNameValuePair("a",MyFlg.a));
					params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Activity_homeChildView.this)));
					params.add(new BasicNameValuePair("exercises_type","ctgg"));
					params.add(new BasicNameValuePair("exercises_option","["+subjectid+"]"));

					asyncLoadApi = new AsyncLoadApi(Activity_homeChildView.this, handler, params, "create_exercise_suit",0,1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Activity_homeChildView.this).getCreate_exercise_suit(),Activity_homeChildView.this));
					asyncLoadApi.execute();
				}


				break;
			case R.id.linear_Left: //点击返回
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				MyFlg.all_activitys.remove(Activity_homeChildView.this);
				MyFlg.listActivity.remove(Activity_homeChildView.this);
				Activity_homeChildView.this.finish();
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
			Create_Exercise_suit_2Bean create_Exercise_suit_2Bean;
			Intent intent;
			Bundle bundle;
			switch (msg.what) {
				case 0:
					dialog.dismiss();
					create_Exercise_suit_2Bean = APPUtil.create_exercise_suit_2(Activity_homeChildView.this);
					if(null==create_Exercise_suit_2Bean){
						Toast.makeText(Activity_homeChildView.this, "创建失败", Toast.LENGTH_SHORT).show();
						return;
					}
					intent = new Intent(Activity_homeChildView.this, Reading_QuestionsActivity.class);
					bundle = new Bundle();
					bundle.putSerializable("bean", create_Exercise_suit_2Bean);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					break;
				case 1:
					dialog.dismiss();
					break;
				default:
					break;
			}
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(Activity_homeChildView.this);
			MyFlg.listActivity.remove(Activity_homeChildView.this);
			finish();
			return false;
		}
		return false;
	}
	private void setKaopin(int sum){
		int dip_20 = DensityUtil.DipToPixels(this, 20);
		LayoutParams params  = new LayoutParams(dip_20, dip_20);
		for (int i = 0; i < sum; i++) {
			ImageView imageView = new ImageView(this);
			if(null==application.imageBean){
				imageView.setImageResource(R.drawable.kaopin);
			}else if(null==application.imageBean.getKaopin()){
				imageView.setImageResource(R.drawable.kaopin);
			}else{
				LoadImageViewUtil.setImageBitmap(imageView, application.imageBean.getKaopin_grey(),Activity_homeChildView.this);
			}
			kaopin_linear.addView(imageView);
			imageView.setLayoutParams(params);
		}
	}
	private void no_ctgg(){
		text_ctgg.setTextColor(Color_4);
		image_ctgg_right.setVisibility(View.GONE);
		linear_ctgg.setEnabled(false);
		linear_ctgg.setBackgroundDrawable(MyFlg.setViewRaduis(Color_3, Color_3, 1, 10));
		linear_ctgg.getBackground().setAlpha(178);
	}
}
