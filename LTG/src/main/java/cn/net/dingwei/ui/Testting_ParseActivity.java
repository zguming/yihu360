package cn.net.dingwei.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.LoadImage;
import cn.net.dingwei.Bean.Create_test_suitBean;
import cn.net.dingwei.Bean.Create_test_suitBean.questions;
import cn.net.dingwei.Bean.Get_point_infoBean;
import cn.net.dingwei.Bean.UtilBean;
import cn.net.dingwei.myView.AnswerDialog;
import cn.net.dingwei.myView.ElasticScrollView_answer;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Testting_ParseActivity extends Activity implements OnClickListener{
	private LinearLayout layoutLeft,layoutRight;
	private TextView title_text1,title_text2,title_text3,title_text4;
	private Button buttom_button;
	private ViewPager viewpager;
	private List<questions> list_questions;
	private List<questions> list_questions2;
	private viewholder viewholder = new viewholder();
	private List<View> list_view;
	private List<LinearLayout> list_pay_linears;
	private List<TextView> list_content_textviews;
	private int image_false[] 		   = new int []{R.drawable.w_1,R.drawable.w_2,R.drawable.w_3,R.drawable.w_4,R.drawable.w_5,R.drawable.w_6,R.drawable.w_7,R.drawable.wpd_1,R.drawable.wpd_2,};
	//多选按钮 错误
	private int	[] image_duoxuan_false = new int []{R.drawable.dc_1,R.drawable.dc_2,R.drawable.dc_3,R.drawable.dc_4,R.drawable.dc_5,R.drawable.dc_6,R.drawable.dc_7};
	//多选按钮 半对
	private int [] image_duoxuan_tAndf = new int []{R.drawable.ddc_1,R.drawable.ddc_2,R.drawable.ddc_3,R.drawable.ddc_4,R.drawable.ddc_5,R.drawable.ddc_6,R.drawable.ddc_7};
	//多选正确
	private int [] image_duoxuan_true  = new int []{R.drawable.dr_1,R.drawable.dr_2,R.drawable.dr_3,R.drawable.dr_4,R.drawable.dr_5,R.drawable.dr_6,R.drawable.dr_7};
	private FYuanTikuDialog dialog;

	private int index;
	private String title;
	private List<Boolean> list_title_bg;//整组解析的时候 Title的背景色  true 为正确  为绿色 false 为红色

	//------------考点的宽 总量--------
	//private int width_sum=0;
	private int view_width = 0;//除去左右间隔 剩余的中间全部宽
	private AnswerDialog answerDialog;
	//----------防止每个ITem自动下滑到webview  解决办法 设置webview父控件焦点
	private LinearLayout[] webview_parent_linears;

	private List<LinearLayout> list_pay_hints; //快到期了
	private MyApplication application;

	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_5=0,Bgcolor_6=0,Color_3=0;
	private int Screen_width=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_5 = sharedPreferences.getInt("bgcolor_5", 0);
		Bgcolor_6 = sharedPreferences.getInt("bgcolor_6", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		setContentView(R.layout.activity_testing_parse);
		view_width =Screen_width-DensityUtil.DipToPixels(this, 20);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载...");
		answerDialog = new AnswerDialog(this);
		list_pay_hints = new ArrayList<LinearLayout>();
		initID();
		//InitData();
		myAsync async = new myAsync();
		async.execute();
	}

	private void InitData() {
		// TODO Auto-generated method stub
		list_pay_linears = new ArrayList<LinearLayout>();
		list_content_textviews = new ArrayList<TextView>();
		Intent intent = getIntent();
		list_title_bg = new ArrayList<Boolean>();
		list_questions=(List<questions>) intent.getSerializableExtra("list_questions");
		list_view = new ArrayList<View>();
		list_questions2 = new ArrayList<Create_test_suitBean.questions>();
		for (int i = 0; i <list_questions.size(); i++) {
			Create_test_suitBean.questions question =list_questions.get(i);
			if(question.getNo()!=-999){
				list_questions2.add(question);
				View view = new View(this);
				view.setTag(0);
				list_view.add(view);
				String useranswer = question.getAnswer().trim().replace(",", "");
				String st = ""; //正确答案
				for (int j = 0; j < question.getCorrect().length; j++) {
					st =st+question.getCorrect()[j];
				}
				if(useranswer.equals(st)){
					list_title_bg.add(true);
				}else{
					list_title_bg.add(false);
				}
			}
		}
		index = intent.getIntExtra("index", 0);
		title = intent.getStringExtra("title");
	}
	class myAsync extends AsyncTask<String, Integer, Boolean>{
		myHandler handler ;
		public myAsync() {
			// TODO Auto-generated constructor stub
			handler = new myHandler();
			dialog.show();
		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			InitData();
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			handler.sendEmptyMessage(0);
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			dialog.dismiss();
			//设置Title
			layoutRight.setVisibility(View.VISIBLE);
			title_text1.setText(title);
			title_text2.setVisibility(View.GONE);
			title_text3.setText((index+1)+"");
			title_text4.setText(" / "+list_questions2.size());
			webview_parent_linears = new LinearLayout[list_questions2.size()];
			viewpager.setAdapter(pagerAdapter);
			viewpager.setCurrentItem(index);
			//设置title背景
			if(list_title_bg.get(index)){
				title_bg.setBackgroundColor(Bgcolor_6);
			}else{
				title_bg.setBackgroundColor(Bgcolor_5);
			}
			viewpager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					//给Webview父控件设置焦点
					if(null != webview_parent_linears[arg0]){
						webview_parent_linears[arg0].setFocusable(true);
						webview_parent_linears[arg0].setFocusableInTouchMode(true);
					}

					title_text3.setText((arg0+1)+"");
					if(list_title_bg.get(arg0)){
						title_bg.setBackgroundColor(Bgcolor_6);
					}else{
						title_bg.setBackgroundColor(Bgcolor_5);
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
	}
	//适配器
	PagerAdapter pagerAdapter = new PagerAdapter() {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_view.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return  arg0 == arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((View) object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			if((Integer)list_view.get(position).getTag()==0){
				View view = setViewPager_youhua(viewholder, list_questions2.get(position),position);
				view.setTag(1);
				list_view.set(position, view);
			}
			((ViewPager) container).addView(list_view.get(position));
			return list_view.get(position);
		}
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
	};
	private RelativeLayout title_bg;
	private View setViewPager_youhua(viewholder viewholder,questions questtionBean,int point){
		View view = View.inflate(this, R.layout.item_answer, null);
		//初始化ID
		viewholder.myScrollview =  (ElasticScrollView_answer) view.findViewById(R.id.myScrollview);
		viewholder.timu = (TextView) view.findViewById(R.id.timu);
		viewholder.answer_text_a = (TextView) view.findViewById(R.id.answer_text_a);
		viewholder.answer_text_b = (TextView) view.findViewById(R.id.answer_text_b);
		viewholder.answer_text_c = (TextView) view.findViewById(R.id.answer_text_c);
		viewholder.answer_text_d = (TextView) view.findViewById(R.id.answer_text_d);
		viewholder.answer_text_e = (TextView) view.findViewById(R.id.answer_text_e);
		viewholder.answer_text_f = (TextView) view.findViewById(R.id.answer_text_f);
		viewholder.answer_text_g = (TextView) view.findViewById(R.id.answer_text_g);
		viewholder.answer_text_true = (TextView) view.findViewById(R.id.answer_text_true);
		viewholder.answer_text_false = (TextView) view.findViewById(R.id.answer_text_false);
		viewholder.answer_text_no = (TextView) view.findViewById(R.id.answer_text_no);
		viewholder.answer_text_jiexi_true = (TextView) view.findViewById(R.id.answer_text_jiexi_true);
		viewholder.answer_text_jiexi_false = (TextView) view.findViewById(R.id.answer_text_jiexi_false);
		viewholder.answer_text_person = (TextView) view.findViewById(R.id.answer_text_person);
		viewholder.answer_text_person_identity = (TextView) view.findViewById(R.id.answer_text_person_identity);
		viewholder.answer_text_bentijiexi = (TextView) view.findViewById(R.id.answer_text_bentijiexi);
		viewholder.answer_text_jiexi_content = (TextView) view.findViewById(R.id.answer_text_jiexi_content);
		viewholder.payRmb_vip = (TextView) view.findViewById(R.id.payRmb_vip);
		viewholder.payRmb_vip1 = (TextView) view.findViewById(R.id.payRmb_vip1);
		viewholder.answer_text_kaodian = (TextView) view.findViewById(R.id.answer_text_kaodian);
		viewholder.imageView =  (ImageView) view.findViewById(R.id.imageview);
		viewholder.linear_vip = (LinearLayout) view.findViewById(R.id.linear_vip);
		viewholder.linear_answer_a = (LinearLayout) view.findViewById(R.id.linear_answer_a);
		viewholder.linear_answer_b = (LinearLayout) view.findViewById(R.id.linear_answer_b);
		viewholder.linear_answer_c = (LinearLayout) view.findViewById(R.id.linear_answer_c);
		viewholder.linear_answer_d = (LinearLayout) view.findViewById(R.id.linear_answer_d);
		viewholder.linear_answer_e = (LinearLayout) view.findViewById(R.id.linear_answer_e);
		viewholder.linear_answer_f = (LinearLayout) view.findViewById(R.id.linear_answer_f);
		viewholder.linear_answer_g = (LinearLayout) view.findViewById(R.id.linear_answer_g);
		viewholder.linear_answer_true = (LinearLayout) view.findViewById(R.id.linear_answer_true);
		viewholder.linear_answer_false = (LinearLayout) view.findViewById(R.id.linear_answer_false);
		viewholder.linear_answer_no = (LinearLayout) view.findViewById(R.id.linear_answer_no);
		viewholder.linear_buttom_kaodian = (LinearLayout) view.findViewById(R.id.linear_buttom_kaodian);
		viewholder.linear_head = (LinearLayout) view.findViewById(R.id.linear_head);
		viewholder.analysis =  (LinearLayout) view.findViewById(R.id.analysis);
		viewholder.answer_a  = (ImageView) view.findViewById(R.id.answer_a);
		viewholder.answer_b  = (ImageView) view.findViewById(R.id.answer_b);
		viewholder.answer_c  = (ImageView) view.findViewById(R.id.answer_c);
		viewholder.answer_d  = (ImageView) view.findViewById(R.id.answer_d);
		viewholder.answer_e  = (ImageView) view.findViewById(R.id.answer_e);
		viewholder.answer_f  = (ImageView) view.findViewById(R.id.answer_f);
		viewholder.answer_g  = (ImageView) view.findViewById(R.id.answer_g);
		viewholder.answer_true  = (ImageView) view.findViewById(R.id.answer_true);
		viewholder.answer_false  = (ImageView) view.findViewById(R.id.answer_false);
		viewholder.answer_person =  (ImageView) view.findViewById(R.id.answer_person);
		//设置居中
		viewholder.answer_text_a.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_b.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_c.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_d.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_e.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_f.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_g.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_true.setPadding(30,DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_false.setPadding(30,DataUtil.answer_text_padingtop, 0, 0);
		viewholder.answer_text_no.setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
		//设置颜色
		viewholder.timu.setTextColor(Fontcolor_3);
		viewholder.answer_text_a.setTextColor(Fontcolor_3);
		viewholder.answer_text_b.setTextColor(Fontcolor_3);
		viewholder.answer_text_c.setTextColor(Fontcolor_3);
		viewholder.answer_text_d.setTextColor(Fontcolor_3);
		viewholder.answer_text_e.setTextColor(Fontcolor_3);
		viewholder.answer_text_f.setTextColor(Fontcolor_3);
		viewholder.answer_text_g.setTextColor(Fontcolor_3);
		viewholder.answer_text_true.setTextColor(Fontcolor_3);
		viewholder.answer_text_false.setTextColor(Fontcolor_3);
		viewholder.answer_text_no.setTextColor(Fontcolor_3);
		viewholder.answer_text_jiexi_true.setTextColor(Bgcolor_2);
		viewholder.answer_text_bentijiexi.setTextColor(Fontcolor_7);
		viewholder.answer_text_kaodian.setTextColor(Fontcolor_3);
		//会员快到期了 设置充值

		viewholder.pay_hint1 = (TextView) view.findViewById(R.id.pay_hint1);
		viewholder.pay_hint2 = (TextView) view.findViewById(R.id.pay_hint2);
		viewholder.pay_hint_linear = (LinearLayout) view.findViewById(R.id.pay_hint_linear);
		//viewholder.pay_hint1.setTextColor(getResources().getColor(R.color.answer_dialog_color1));
		viewholder.pay_hint2.setTextColor(Bgcolor_2);
		list_pay_hints.add(viewholder.pay_hint_linear);
		if(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_status()==2){//会员快到期 给提示
			viewholder.pay_hint_linear.setVisibility(View.VISIBLE);
			viewholder.pay_hint1.setText(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_status_name());
			viewholder.pay_hint2.setText(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_price());
			viewholder.pay_hint2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Testting_ParseActivity.this,PayVIPActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
				}
			});
		}

		//添加webview的父控件
		LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_parent);
		webview_parent_linears[point]=linearLayout;
		//设置头像
		String headUrl =questtionBean.getAnalyze().getBy().getIcon();
		if(null!=headUrl && !headUrl.equals("null")&& !headUrl.equals("")){
			LoadImageViewUtil.setImageBitmap(viewholder.answer_person,headUrl ,Testting_ParseActivity.this);
		}
		//添加点击头像事件
		HeadClick headClick = new HeadClick(questtionBean.getAnalyze().getBy().getUrl());
		viewholder.linear_head.setOnClickListener(headClick);
		//设置数据
		viewholder.timu.setText(questtionBean.getContent());
		if(!questtionBean.getImg().equals("null")){
			LoadImage image = new LoadImage(viewholder.imageView, questtionBean.getImg().getUrl(),Testting_ParseActivity.this);
			image.execute(questtionBean.getImg().getUrl());
		}
		if(questtionBean.getType()==2){
			viewholder.answer_a.setImageResource(R.drawable.dn_1);
			viewholder.answer_b.setImageResource(R.drawable.dn_2);
			viewholder.answer_c.setImageResource(R.drawable.dn_3);
			viewholder.answer_d.setImageResource(R.drawable.dn_4);
			viewholder.answer_e.setImageResource(R.drawable.dn_5);
			viewholder.answer_f.setImageResource(R.drawable.dn_6);
			viewholder.answer_g.setImageResource(R.drawable.dn_7);
		}
		//设置答案
		for (int j = 0; j < questtionBean.getOpt().length; j++) {
			Create_test_suitBean.opt opt = questtionBean.getOpt()[j];
			if(opt.getK().equals("A")){
				viewholder.linear_answer_a.setVisibility(View.VISIBLE);
				viewholder.answer_text_a.setText(opt.getC());
			}else if(opt.getK().equals("B")){
				viewholder.linear_answer_b.setVisibility(View.VISIBLE);
				viewholder.answer_text_b.setText(opt.getC());
			}else if(opt.getK().equals("C")){
				viewholder.linear_answer_c.setVisibility(View.VISIBLE);
				viewholder.answer_text_c.setText(opt.getC());
			}else if(opt.getK().equals("D")){
				viewholder.linear_answer_d.setVisibility(View.VISIBLE);
				viewholder.answer_text_d.setText(opt.getC());
			}else if(opt.getK().equals("E")){
				viewholder.linear_answer_e.setVisibility(View.VISIBLE);
				viewholder.answer_text_e.setText(opt.getC());

			}else if(opt.getK().equals("F")){
				viewholder.linear_answer_f.setVisibility(View.VISIBLE);
				viewholder.answer_text_f.setText(opt.getC());
			}else if(opt.getK().equals("G")){
				viewholder.linear_answer_g.setVisibility(View.VISIBLE);
				viewholder.answer_text_g.setText(opt.getC());
			}else if(opt.getK().equals("t")){
				viewholder.linear_answer_true.setVisibility(View.VISIBLE);
				viewholder.answer_text_true.setText(opt.getC());
			}else if(opt.getK().equals("f")){
				viewholder.linear_answer_false.setVisibility(View.VISIBLE);
				viewholder.answer_text_false.setText(opt.getC());
			}
		}
		String true_answer = "";
		String  true_answer2 = "";
		for (int j = 0; j < questtionBean.getCorrect().length; j++) {
			true_answer =true_answer+questtionBean.getCorrect()[j];
			if(j==questtionBean.getCorrect().length-1){
				true_answer2 =true_answer2+questtionBean.getCorrect()[j];
			}else{
				true_answer2 =true_answer2+questtionBean.getCorrect()[j]+"、";
			}

		}
		//设置下面的解析
		if(true_answer.equals("t")){
			viewholder.answer_text_jiexi_true.setText("√");
		}else if(true_answer.equals("f")){
			viewholder.answer_text_jiexi_true.setText("×");
		}else{
			viewholder.answer_text_jiexi_true.setText(true_answer2);
		}

		viewholder.answer_text_person.setText(questtionBean.getAnalyze().getBy().getN());
		if(questtionBean.getAnalyze().getBy().getIntro().equals("null")){
			viewholder.answer_text_person_identity.setVisibility(View.GONE);
		}else{
			viewholder.answer_text_person_identity.setText(questtionBean.getAnalyze().getBy().getIntro());
		}

		//viewholder.answer_text_jiexi_content.setText(questtionBean.getAnalyze().getContent());
		setWebView(viewholder.answer_text_jiexi_content,questtionBean.getAnalyze().getContent());
		//如果用户不是会员  则看不到解析   1 2 表示用户还是会员
		list_pay_linears.add(viewholder.linear_vip);
		list_content_textviews.add(viewholder.answer_text_jiexi_content);
		if(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_status()==1 || application.getuserInfoBean(Testting_ParseActivity.this).getMember_status()==2){

		}else{
			viewholder.linear_vip.setVisibility(View.VISIBLE);
			viewholder.payRmb_vip1.setText(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_status_name());
			viewholder.payRmb_vip.setText(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_price());
			viewholder.payRmb_vip.setTextColor(Bgcolor_2);
			viewholder.answer_text_jiexi_content.setVisibility(View.GONE);
			viewholder.payRmb_vip.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Testting_ParseActivity.this,PayVIPActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
				}
			});
		}

		//添加考点
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(15,15,15,0);//4个参数按顺序分别是左上右下

		LinearLayout liner = new LinearLayout(Testting_ParseActivity.this);
		viewholder.linear_buttom_kaodian.addView(liner);
		int width_sum = 0;
		for (int j = 0; j < questtionBean.getPoints().length; j++) {
			Button button = new Button(this);
			button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			button.setText(questtionBean.getPoints()[j].getN());
			int dip_15 = DensityUtil.DipToPixels(Testting_ParseActivity.this, 15);
			button.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Bgcolor_2,Bgcolor_1, 1, DensityUtil.DipToPixels(Testting_ParseActivity.this, 10)));
			button.setPadding(dip_15, dip_15/3, dip_15, dip_15/3);
			button.setLayoutParams(layoutParams);
			button.setTextColor(Fontcolor_1);
			button.setOnClickListener(new Kaodian_clcik(questtionBean.getPoints()[j].getId()+""));

			int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			button.measure(w, h);
			int height =button.getMeasuredHeight();
			int width =button.getMeasuredWidth();
			if((width_sum +width+30) < view_width){
				liner.addView(button);
				width_sum = width_sum+width;
			}else{
				width_sum=width;
				liner = new LinearLayout(Testting_ParseActivity.this);
				viewholder.linear_buttom_kaodian.addView(liner);
				liner.addView(button);
			}

		}
		LinearLayout[] linear_arr = new LinearLayout[]{viewholder.linear_answer_a,viewholder.linear_answer_b,viewholder.linear_answer_c,viewholder.linear_answer_d,viewholder.linear_answer_e,viewholder.linear_answer_f,viewholder.linear_answer_g,viewholder.linear_answer_true,viewholder.linear_answer_false,viewholder.linear_answer_no};

		for (int j = 0; j < linear_arr.length; j++) {
			linear_arr[j].setEnabled(false);
		}
		viewholder.analysis.setVisibility(View.VISIBLE);
		if(questtionBean.getAnswer().equals("null")){
			viewholder.answer_text_jiexi_false.setText("未做答");
		}else if(questtionBean.getAnswer().equals("f")){
			viewholder.answer_text_jiexi_false.setText("×");
		}else if(questtionBean.getAnswer().equals("t")){
			viewholder.answer_text_jiexi_false.setText("√");
		}else if(questtionBean.getAnswer().equals("?")){
			viewholder.answer_text_jiexi_false.setText("不会");
		}else{
			viewholder.answer_text_jiexi_false.setText(questtionBean.getAnswer().replace(",", "、"));
		}


		String st = ""; //正确答案
		for (int j = 0; j < questtionBean.getCorrect().length; j++) {
			st =st+questtionBean.getCorrect()[j];
		}
		//设置选项  多选
		ImageView [] imageviews = new ImageView[]{viewholder.answer_a,viewholder.answer_b,viewholder.answer_c,viewholder.answer_d,viewholder.answer_e,viewholder.answer_f,viewholder.answer_g,viewholder.answer_true,viewholder.answer_false,};
		if(questtionBean.getType()==2){//多选
			String useranswer = questtionBean.getAnswer().trim().replace(",", "");
			if(useranswer.equals("null")||useranswer.equals("?") || useranswer.equals(st)){//没答 或不会 或者答案正确
				String [] correct = questtionBean.getCorrect();
				for (int k = 0; k < correct.length; k++) {
					if(correct[k].equals("A")){
						imageviews[0].setImageResource(image_duoxuan_true[0]);
					}else if(correct[k].equals("B")){
						imageviews[1].setImageResource(image_duoxuan_true[1]);
					}else if(correct[k].equals("C")){
						imageviews[2].setImageResource(image_duoxuan_true[2]);
					}else if(correct[k].equals("D")){
						imageviews[3].setImageResource(image_duoxuan_true[3]);
					}else if(correct[k].equals("E")){
						imageviews[4].setImageResource(image_duoxuan_true[4]);
					}else if(correct[k].equals("F")){
						imageviews[5].setImageResource(image_duoxuan_true[5]);
					}else if(correct[k].equals("G")){
						imageviews[6].setImageResource(image_duoxuan_true[6]);
					}
				}
			}else{//答案错误
				//如果选了A  答案也有A 显示半对
				String []duoxuan_answer = questtionBean.getDuoxuan_answer();
				if(duoxuan_answer[0].equals("A") && st.contains("A")){
					imageviews[0].setImageResource(image_duoxuan_tAndf[0]);
				}else if(st.contains("A")&& !duoxuan_answer[0].equals("A") ){
					imageviews[0].setImageResource(image_duoxuan_true[0]);
				}else if(duoxuan_answer[0].equals("A") && !st.contains("A")){
					imageviews[0].setImageResource(image_duoxuan_false[0]);
				} else{
				}

				if(duoxuan_answer[1].equals("B") && st.contains("B")){
					imageviews[1].setImageResource(image_duoxuan_tAndf[1]);
				}else if(st.contains("B")&& !duoxuan_answer[1].equals("B") ){
					imageviews[1].setImageResource(image_duoxuan_true[1]);
				}else if(duoxuan_answer[1].equals("B") && !st.contains("B")){
					imageviews[1].setImageResource(image_duoxuan_false[1]);
				}

				if(duoxuan_answer[2].equals("C") && st.contains(duoxuan_answer[2])){
					imageviews[2].setImageResource(image_duoxuan_tAndf[2]);
				}else if(st.contains("C")&& !duoxuan_answer[2].equals("C") ){
					imageviews[2].setImageResource(image_duoxuan_true[2]);
				}else if(duoxuan_answer[2].equals("C") && !st.contains("C")){
					imageviews[2].setImageResource(image_duoxuan_false[2]);
				}

				if(duoxuan_answer[3].equals("D") && st.contains(duoxuan_answer[3])){
					imageviews[3].setImageResource(image_duoxuan_tAndf[3]);
				}else if(st.contains("D")&& !duoxuan_answer[3].equals("D") ){
					imageviews[3].setImageResource(image_duoxuan_true[3]);
				}else if(duoxuan_answer[3].equals("D") && !st.contains("D")){
					imageviews[3].setImageResource(image_duoxuan_false[3]);
				}

				if(duoxuan_answer[4].equals("E") && st.contains(duoxuan_answer[4])){
					imageviews[4].setImageResource(image_duoxuan_tAndf[4]);
				}else if(st.contains("E")&& !duoxuan_answer[4].equals("E") ){
					imageviews[4].setImageResource(image_duoxuan_true[4]);
				}else if(duoxuan_answer[4].equals("E") && !st.contains("E")){
					imageviews[4].setImageResource(image_duoxuan_false[4]);
				}

				if(duoxuan_answer[5].equals("F") && st.contains(duoxuan_answer[5])){
					imageviews[5].setImageResource(image_duoxuan_tAndf[5]);
				}else if(st.contains("F")&& !duoxuan_answer[5].equals("F") ){
					imageviews[5].setImageResource(image_duoxuan_true[5]);
				}else if(duoxuan_answer[5].equals("F") && !st.contains("F")){
					imageviews[5].setImageResource(image_duoxuan_false[5]);
				}

				else if(duoxuan_answer[6].equals("G") && st.contains(duoxuan_answer[6])){
					imageviews[6].setImageResource(image_duoxuan_tAndf[6]);
				}else if(st.contains("G")&& !duoxuan_answer[6].equals("G") ){
					imageviews[6].setImageResource(image_duoxuan_true[6]);
				}else if(duoxuan_answer[6].equals("G") && !st.contains("G")){
					imageviews[6].setImageResource(image_duoxuan_false[6]);
				}
			}
		}else{//单选
			if(st.equals("A")){ //标识正确答案
				imageviews[0].setImageResource(R.drawable.r_1);
			}else if(st.equals("B")){
				imageviews[1].setImageResource(R.drawable.r_2);
			}else if(st.equals("C")){
				imageviews[2].setImageResource(R.drawable.r_3);
			}else if(st.equals("D")){
				imageviews[3].setImageResource(R.drawable.r_4);
			}else if(st.equals("E")){
				imageviews[4].setImageResource(R.drawable.r_5);
			}else if(st.equals("F")){
				imageviews[5].setImageResource(R.drawable.r_6);
			}else if(st.equals("G")){
				imageviews[6].setImageResource(R.drawable.r_7);
			}else if(st.equals("t")){
				imageviews[7].setImageResource(R.drawable.rpd_1);
			}else if(st.equals("f")){
				imageviews[8].setImageResource(R.drawable.rpd_2);
			}
			if(!questtionBean.getAnswer().equals("?")&&!questtionBean.getAnswer().equals("null")&&!questtionBean.getAnswer().equals(st) &&questtionBean.getUser_choose_index()!=9){ //答案错误时显示错误答案
				imageviews[questtionBean.getUser_choose_index()].setImageResource(image_false[questtionBean.getUser_choose_index()]);
			}
		}

		return view;
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
			mUrl =mUrl.trim();
			String point = mUrl.substring(mUrl.indexOf("/point/")+7, mUrl.length());
			if(isNumeric(point)){
				dialog.show();
				List <NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("a",MyFlg.a));
				params.add(new BasicNameValuePair("ver",MyFlg.android_version));
				params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Testting_ParseActivity.this)));
				params.add(new BasicNameValuePair("point_id",point));
				AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Testting_ParseActivity.this, hadler, params, "get_point_info",9,10,"获取考点信息失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(Testting_ParseActivity.this).getGet_point_info(),Testting_ParseActivity.this));
				asyncLoadApi.execute();
			}
		}

	}
	/**
	 * 加载富文本
	 * @param webView
	 * @param data
	 */
	private void setWebView(TextView tv,String data) {
		// TODO Auto-generated method stub
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
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	private void initID() {
		// TODO Auto-generated method stub
		title_bg=(RelativeLayout)findViewById(R.id.title_bg);
		//标题栏
		layoutLeft=(LinearLayout)findViewById(R.id.layoutLeft);
		layoutRight=(LinearLayout)findViewById(R.id.layoutRight);
		title_text1=(TextView)findViewById(R.id.title_text1);
		title_text2=(TextView)findViewById(R.id.title_text2);
		title_text3=(TextView)findViewById(R.id.title_text3);
		title_text4=(TextView)findViewById(R.id.title_text4);
		buttom_button=(Button)findViewById(R.id.buttom_button);
		viewpager=(ViewPager)findViewById(R.id.viewpager);
		//设置颜色
		findViewById(R.id.title_xian).setBackgroundColor(Color_3);
		title_text3.setTextColor(Bgcolor_2);
		buttom_button.setBackgroundColor(Bgcolor_2);
		buttom_button.setTextColor(Fontcolor_1);
		buttom_button.setText("返回");
		//设置事件
		buttom_button.setOnClickListener(this);
		layoutLeft.setOnClickListener(this);
	}
	class viewholder{
		TextView timu;
		TextView answer_text_a;
		TextView answer_text_b;
		TextView answer_text_c;
		TextView answer_text_d;
		TextView answer_text_e;
		TextView answer_text_f;
		TextView answer_text_g;
		TextView answer_text_true;
		TextView answer_text_false;
		TextView answer_text_no;
		TextView answer_text_jiexi_true;
		TextView answer_text_jiexi_false;
		TextView answer_text_person;
		TextView answer_text_person_identity;
		TextView answer_text_bentijiexi;
		TextView answer_text_jiexi_content;
		TextView answer_text_kaodian;
		TextView pay_hint1;
		TextView pay_hint2;

		//购买会员显示
		TextView payRmb_vip;
		TextView payRmb_vip1;
		LinearLayout linear_vip;
		LinearLayout pay_hint_linear;

		ImageView imageView;
		ImageView answer_a;
		ImageView answer_b;
		ImageView answer_c;
		ImageView answer_d;
		ImageView answer_e;
		ImageView answer_f;
		ImageView answer_g;
		ImageView answer_true;
		ImageView answer_false;
		ImageView answer_person;
		LinearLayout linear_answer_a;
		LinearLayout linear_answer_b;
		LinearLayout linear_answer_c;
		LinearLayout linear_answer_d;
		LinearLayout linear_answer_e;
		LinearLayout linear_answer_f;
		LinearLayout linear_answer_g;
		LinearLayout linear_answer_true;
		LinearLayout linear_answer_false;
		LinearLayout linear_answer_no;
		LinearLayout linear_buttom_kaodian;
		LinearLayout analysis;
		LinearLayout linear_head;
		ElasticScrollView_answer myScrollview;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//resultCode  1表示支付成功  -1 表示支付失败
		if(resultCode==1 && (MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_status()==1 || application.getuserInfoBean(Testting_ParseActivity.this).getMember_status()==2)){ //支付成功
			for (int i = 0; i <list_content_textviews.size(); i++) {
				list_pay_linears.get(i).setVisibility(View.GONE);
				list_content_textviews.get(i).setVisibility(View.VISIBLE);
			}
			if(MyApplication.getuserInfoBean(Testting_ParseActivity.this).getMember_status()==1){
				for (int i = 0; i < list_pay_hints.size(); i++) {
					list_pay_hints.get(i).setVisibility(View.GONE);
				}
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.buttom_button:
				MyFlg.all_activitys.remove(Testting_ParseActivity.this);
				finish();
				overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
				break;
			case R.id.layoutLeft:
				MyFlg.all_activitys.remove(Testting_ParseActivity.this);
				finish();
				overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
				break;
			default:
				break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyFlg.all_activitys.remove(Testting_ParseActivity.this);
			finish();
			overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	//点击考点  弹出对话框
	class Kaodian_clcik implements OnClickListener{
		private String kaodianID="";
		public Kaodian_clcik(String kaodianID) {
			// TODO Auto-generated constructor stub
			this.kaodianID = kaodianID;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			dialog.show();
			List <NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("a",MyFlg.a));
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Testting_ParseActivity.this)));
			params.add(new BasicNameValuePair("point_id",kaodianID));
			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Testting_ParseActivity.this,hadler , params, "get_point_info",9,10,"获取考点信息失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(Testting_ParseActivity.this).getGet_point_info(),Testting_ParseActivity.this));
			asyncLoadApi.execute();

		}

	}
	private myHadler hadler = new myHadler();
	class myHadler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 9://点击考点 显示
					dialog.dismiss();
					Get_point_infoBean get_point_infoBean = APPUtil.Get_point_infoBean(Testting_ParseActivity.this);
					if(null!=get_point_infoBean){
						answerDialog.initUI(get_point_infoBean.getPoints(), get_point_infoBean.getExe_c()+"",get_point_infoBean.getWro_d()+"", get_point_infoBean.getKpl());
						answerDialog.showDialog();
					}else{
						Toast.makeText(Testting_ParseActivity.this, "获取考点信息失败", 0).show();
					}

					break;
				case 10: //、点击考点加载失败
					dialog.dismiss();
					break;
			}
		}
	}

	class HeadClick implements OnClickListener{
		private String url;
		public HeadClick(String url) {
			// TODO Auto-generated constructor stub
			this.url = url;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Testting_ParseActivity.this, WebViewActivity.class);
			intent.putExtra("url", url);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}

	}
}
