package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.Adv_project_infoBean;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.dingwei.adpater.Jinjie_left_listviewAdapter;
import cn.net.dingwei.adpater.Jinjie_right_ListviewAdapater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class JinJieDetailedActivity extends BackActivity implements OnClickListener{
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0,color_101=0,color_102,color_103,color_101_1,color_102_1,color_103_1;
	private int Screen_width=0;
	private MyApplication application;
	private Button btn_left,btn_right;
	private Drawable drawable_left_white,drawable_right_bg2,drawable_left_bg2,drawable_right_white;
	private FYuanTikuDialog dialog;
	private String pid="";//ID
	private TextView text_title,text_content,text_sum,text_time,text_mysum;
	private ListView listview_left;
	//错题巩固
	private Drawable drawable1,drawable2,drawable3;
	private TextView title_tx,text1_number,text2_number,text3_number;
	private LinearLayout linear1,linear2,linear3;
	private ListView listview_right;
	private List<TreeListViewBean> datas = new ArrayList<TreeListViewBean>();
	private LinearLayout linear_buttom_right;
	private TextView no_data,no_data1;
	private FrameLayout framelayout0,framelayout1;
	private TextView text_refresh;
	private ScrollView scroll;
	private LinearLayout linear_bg;
	private ImageView jinjie_image;
	private LinearLayout linear_time;
	private TextView text_change;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_jinjie_detailed);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		initColor();
		initID();
		initData();
	}
	private void initColor() {
		// TODO Auto-generated method stub
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		color_101 = sharedPreferences.getInt("color_101", 0);
		color_102 = sharedPreferences.getInt("color_102", 0);
		color_103 = sharedPreferences.getInt("color_103", 0);
		color_101_1 = sharedPreferences.getInt("color_101_1", 0);
		color_102_1 = sharedPreferences.getInt("color_102_1", 0);
		color_103_1 = sharedPreferences.getInt("color_103_1", 0);
		drawable1 = MyFlg.setViewRaduisAndTouch(color_101, color_101_1, color_101_1, 1, 10);
		drawable2 = MyFlg.setViewRaduisAndTouch(color_102, color_102_1, color_102_1, 1, 10);
		drawable3 = MyFlg.setViewRaduisAndTouch(color_103, color_103_1, color_103_1, 1, 10);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
	}

	private void initID() {
		// TODO Auto-generated method stub
		float [] radii_left = new float[]{10,10,0,0,0,0,10,10};
		float [] radii_right = new float[]{0,0,10,10,10,10,0,0};
		drawable_left_white=MyFlg.setViewRaduis_other(Color.WHITE,Color.WHITE, 0, radii_left);
		drawable_right_bg2 =  MyFlg.setViewRaduis_other(Bgcolor_1, Color.WHITE, 2,radii_right);
		drawable_left_bg2=MyFlg.setViewRaduis_other(Bgcolor_1, Color.WHITE,2,radii_left);
		drawable_right_white=MyFlg.setViewRaduis_other(Color.WHITE,Color.WHITE, 0, radii_right);


		btn_left=(Button)findViewById(R.id.btn_left);
		btn_right=(Button)findViewById(R.id.btn_right);
		text_title = (TextView)findViewById(R.id.text_title);
		text_content = (TextView)findViewById(R.id.text_content);
		text_sum = (TextView)findViewById(R.id.text_sum);
		text_time = (TextView)findViewById(R.id.text_time);
		text_mysum = (TextView)findViewById(R.id.text_mysum);
		listview_left = (ListView)findViewById(R.id.listview_left);
		no_data = (TextView)findViewById(R.id.no_data);
		no_data1 = (TextView)findViewById(R.id.no_data1);
		framelayout0  = (FrameLayout)findViewById(R.id.framelayout0);
		framelayout1  = (FrameLayout)findViewById(R.id.framelayout1);
		framelayout1.setVisibility(View.GONE);
		text_refresh = (TextView)findViewById(R.id.text_refresh);
		scroll = (ScrollView)findViewById(R.id.scroll);
		jinjie_image = (ImageView)findViewById(R.id.jinjie_image);
		text_change = (TextView)findViewById(R.id.text_change);

		//错题巩固
		text1_number = (TextView) findViewById(R.id.text1_number);
		text2_number = (TextView) findViewById(R.id.text2_number);
		text3_number = (TextView) findViewById(R.id.text3_number);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		listview_right = (ListView)findViewById(R.id.listview_right);
		linear_buttom_right = (LinearLayout)findViewById(R.id.linear_buttom_right);
		//设置背景 颜色 显示
		findViewById(R.id. title_back_text).setVisibility(View.VISIBLE);
		findViewById(R.id.linear_top_bg).setBackgroundColor(Bgcolor_1);
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		findViewById(R.id.top_bg).setBackgroundColor(Bgcolor_1);
		linear_bg=(LinearLayout)findViewById(R.id.linear_bg);
		linear_time =(LinearLayout)findViewById(R.id.linear_time);
		linear_bg.setBackgroundColor(Bgcolor_1);
		btn_left.setBackgroundDrawable(drawable_left_white);
		btn_right.setBackgroundDrawable(drawable_right_bg2);
		linear1.setBackgroundDrawable(drawable1);
		linear2.setBackgroundDrawable(drawable2);
		linear3.setBackgroundDrawable(drawable3);
		btn_left.setTextColor(Bgcolor_2);
		btn_right.setTextColor(Color.WHITE);
		text_change.setTextColor(Fontcolor_3);
		//事件
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
		linear1.setOnClickListener(this);
		linear2.setOnClickListener(this);
		linear3.setOnClickListener(this);
		text_refresh.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		try {
			SharedPreferences sp = getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
			Placeholder_textBean placeholder_textBean = gson.fromJson(new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
			if(null!=placeholder_textBean&&null!=placeholder_textBean.getAdv_explain()){
				text_change.setText(placeholder_textBean.getAdv_explain());
				setWebView( text_change, placeholder_textBean.getAdv_explain());
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}

		Intent intent = getIntent();
		pid = intent.getStringExtra("pid");
		PostApi();

	}
	/**
	 * 加载WebView
	 * @param data
	 */
	private void setWebView(TextView tv,String data) {
		// TODO Auto-generated method stub
		data = data.replace("\n", "<br>");
		tv.setText(Html.fromHtml(data));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		CharSequence text = tv.getText();
		if (text instanceof Spannable) {
			int end = text.length();
			Spannable sp = (Spannable) tv.getText();
			URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
			SpannableStringBuilder style = new SpannableStringBuilder(text);
			style.clearSpans();// should clear old spans
			for (URLSpan url : urls) {
				MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
				style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			tv.setText(style);
		}
	}
	/**
	 * Textview 的链接点击事件
	 * @author Administrator
	 *
	 */
	private  class MyURLSpan extends ClickableSpan {
		private String mUrl;
		MyURLSpan(String url) {
			mUrl = url;
		}
		@Override
		public void onClick(View widget) {
			//Toast.makeText(AnswerActivity.this, mUrl, Toast.LENGTH_LONG).show();
			mUrl =mUrl.trim();
			if(mUrl.equals("http://app_action/change_point")){//切换科目菜单显示
				FramentHome.isShowLeft=true;
				setResult(RESULT_OK);
				finish();
				overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			}
		}
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Bgcolor_2);
			ds.setUnderlineText(false);    //去除超链接的下划线
		}
	}
	/**
	 * POST 网络请求

	 */
	private void PostApi(){
		dialog.show();
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode",MyFlg.getclientcode(this));
		params.add("pid",pid);
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		String url = MyFlg.get_API_URl(application.getCommonInfo_API_functions(this).getGet_adv_project_info(),this);
		client.post(url, params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				Gson gson = new Gson();
				Adv_project_infoBean bean = new Adv_project_infoBean();
				bean =gson.fromJson(result, Adv_project_infoBean.class);
				if(bean.getStatus()==false){
					Toast.makeText(JinJieDetailedActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
					scroll.setVisibility(View.GONE);
					linear_bg.setVisibility(View.GONE);
					text_refresh.setVisibility(View.VISIBLE);
				}else{//加载成功
					scroll.setVisibility(View.VISIBLE);
					text_refresh.setVisibility(View.GONE);
					linear_bg.setVisibility(View.VISIBLE);
					if(null!=bean.getData()){
						Adv_project_infoBean.data temp_data = bean.getData();
						text_title.setText(temp_data.getTitle());
						text_content.setText(temp_data.getDescription());
						text_sum.setText(temp_data.getQb_num()+" 道");
						text_mysum.setText(temp_data.getExe_num()+" 道");
						if(null==temp_data.getExpiry() || "".equals(temp_data.getExpiry())||"null".equals(temp_data.getExpiry())){
							linear_time.setVisibility(View.GONE);
						}else{
							text_time.setText(temp_data.getExpiry());
						}
						if(null!=temp_data.getImg()&&null!=temp_data.getImg().getUrl()&&!"".equals(temp_data.getImg().getUrl())){
							ImageLoader.getInstance().displayImage(temp_data.getImg().getUrl(), jinjie_image, application.getOptions());
						}
						//左边
						if(null!=temp_data.getSuit_list()&&temp_data.getSuit_list().size()>0){
							Jinjie_left_listviewAdapter adapter = new Jinjie_left_listviewAdapter(JinJieDetailedActivity.this, temp_data.getSuit_list());
							listview_left.setAdapter(adapter);
							listview_left.setVisibility(View.VISIBLE);
							no_data.setVisibility(View.GONE);
						}else{
							listview_left.setVisibility(View.GONE);
							no_data.setVisibility(View.VISIBLE);
						}
						// 右边错题巩固
						if(null!=temp_data.getWrong_list()){
							linear_buttom_right.setVisibility(View.VISIBLE);
							no_data1.setVisibility(View.GONE);
							setCTGGData(temp_data.getWrong_list());
						}else{
							linear_buttom_right.setVisibility(View.GONE);
							no_data1.setVisibility(View.VISIBLE);
						}
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				scroll.setVisibility(View.GONE);
				linear_bg.setVisibility(View.GONE);
				text_refresh.setVisibility(View.VISIBLE);
				Toast.makeText(JinJieDetailedActivity.this, "网络异常。", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}
		});
	}
	private void setCTGGData(Adv_project_infoBean.wrong_list data){
		text1_number.setText(data.getWro_t()+"");
		text2_number.setText(data.getWro_k()+"");
		text3_number.setText(data.getWro_c()+"");
		int dip_2 = DensityUtil.DipToPixels(JinJieDetailedActivity.this, 1);
		int dip_60 = DensityUtil.DipToPixels(JinJieDetailedActivity.this, 60);
		//30 左右间隔   4为间距  dip_60*3 3个控件基础宽
		int width = Screen_width-DensityUtil.DipToPixels(JinJieDetailedActivity.this, 34)-dip_60*3;
		int width1 = width*data.getWro_tr()/100+dip_60;
		int width2 = width*data.getWro_kr()/100+dip_60;
		int width3 = width*data.getWro_cr()/100+dip_60;
		LayoutParams params_progress1 = new LayoutParams(width1, LayoutParams.WRAP_CONTENT);
		LayoutParams params_progress2 = new LayoutParams(width2, LayoutParams.WRAP_CONTENT);
		params_progress2.setMargins(dip_2, 0, dip_2, 0);
		LayoutParams params_progress3 = new LayoutParams(width3, LayoutParams.WRAP_CONTENT);
		linear1.setLayoutParams(params_progress1);
		linear2.setLayoutParams(params_progress2);
		linear3.setLayoutParams(params_progress3);
		if(null==data.getWrongs()){
			Toast.makeText(this, "暂无数据 ", Toast.LENGTH_SHORT).show();
		}else{
			Jinjie_right_ListviewAdapater adapater = new Jinjie_right_ListviewAdapater(JinJieDetailedActivity.this, data.getWrongs(),pid,dialog);
			listview_right.setAdapter(adapater);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_left: //
				framelayout0.setVisibility(View.VISIBLE);
				framelayout1.setVisibility(View.GONE);
				setButton(0);
				text_change.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_right:
				framelayout0.setVisibility(View.GONE);
				framelayout1.setVisibility(View.VISIBLE);
				setButton(1);
				text_change.setVisibility(View.GONE);
				break;
			case R.id.text_refresh://刷新
				PostApi();
				break;
			case R.id.linear1: //待消灭
				String num1 =text1_number.getText().toString().trim();
				if(num1.equals("0")){
					Toast.makeText(JinJieDetailedActivity.this, "暂无待消灭错题！", Toast.LENGTH_SHORT).show();
				}else{//请求接口
					Create(2);
				}
				break;
			case R.id.linear2://将消灭
				String num2 =text2_number.getText().toString().trim();
				if(num2.equals("0")){
					Toast.makeText(JinJieDetailedActivity.this, "暂无将消灭错题！", Toast.LENGTH_SHORT).show();
				}else{
					Create(1);
				}
				break;
			case R.id.linear3://已消灭
				String num3 =text3_number.getText().toString().trim();
				if(num3.equals("0")){
					Toast.makeText(JinJieDetailedActivity.this, "暂无已消灭错题！", Toast.LENGTH_SHORT).show();
				}else{
					Create(0);
				}
				break;
			default:
				break;
		}
	}
	//2 待消灭 1 将消灭 0 已消灭   点击顶部
	private void Create(int type){
		RequestParams params = new RequestParams();
		params.add("ver",MyFlg.android_version);
		params.add("a",MyFlg.a);
		params.add("clientcode",MyFlg.getclientcode(JinJieDetailedActivity.this));
		params.add("exercises_type","ctgg_ap");
		params.add("type",type+"");
		params.add("apid",pid+"");
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(JinJieDetailedActivity.this).getCreate_exercise_suit(),JinJieDetailedActivity.this);
		PostApi_create(JinJieDetailedActivity.this, params, apiUrl);
	}
	/**
	 *
	 * @param type //0 目前处于点击了左边     1 右边
	 */
	private void setButton(int type){
		if(type==0){ //点击左边
			btn_left.setBackgroundDrawable(drawable_left_white);
			btn_right.setBackgroundDrawable(drawable_right_bg2);
			btn_left.setTextColor(Bgcolor_2);
			btn_right.setTextColor(Color.WHITE);
		} if(type==1){
			btn_left.setBackgroundDrawable(drawable_left_bg2);
			btn_right.setBackgroundDrawable(drawable_right_white);
			btn_left.setTextColor(Color.WHITE);
			btn_right.setTextColor(Bgcolor_2);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0&&resultCode==RESULT_OK){
			PostApi();
		}
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
	 */
	private void PostApi_create(final Context context,RequestParams params,String apiUrl){
		dialog.show();
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		client.post(apiUrl, params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				Gson gson = new Gson();
				try {
					if(new JSONObject(result).getBoolean("status")==false){
						Toast.makeText(context, "创建失败", Toast.LENGTH_SHORT).show();
					}else{

						String json = new JSONObject(result).getJSONObject("data").getString("suitdata");
						Create_Exercise_suit_2Bean  create_Exercise_suit_2Bean = gson.fromJson(json, Create_Exercise_suit_2Bean.class);
						if(null==create_Exercise_suit_2Bean){
							Toast.makeText(context, "创建失败", Toast.LENGTH_SHORT).show();
							return;
						}
						Intent intent = new Intent(context, Reading_QuestionsActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", create_Exercise_suit_2Bean);
						intent.putExtras(bundle);
						intent.putExtra("flg", 1);
						((Activity) context).startActivityForResult(intent, 0);
						((Activity) context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(context, "网络异常。", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}
}
