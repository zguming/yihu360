package cn.net.dingwei.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.Create_test_suitBean;
import cn.net.dingwei.Bean.Submit_exercise_suitBean;
import cn.net.dingwei.myView.ElasticScrollView_answer;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Testing_answer_Activity extends Activity implements OnClickListener{
	private LinearLayout layoutLeft,title_text_linear,linear_pause;
	private TextView title_text1,title_text2,title_text3;
	private ImageView riggt_image;
	private Create_test_suitBean bean;
	private ViewPager viewpager;
	private View[] viewsArr;
	private ViewHolder viewHolder = new ViewHolder();
	private List<Create_test_suitBean.questions> list_questions;
	//Item的ID

	private int[] textviewIDs = new int[]{R.id.timu,R.id.answer_text_a,R.id.answer_text_b,R.id.answer_text_c,R.id.answer_text_d,R.id.answer_text_e,R.id.answer_text_f,R.id.answer_text_g,R.id.answer_text_true,R.id.answer_text_false};
	private int[] imageviewIDs =new int[]{R.id.imageview,R.id.answer_a,R.id.answer_b,R.id.answer_c,R.id.answer_d,R.id.answer_e,R.id.answer_f,R.id.answer_g,R.id.answer_true,R.id.answer_false};
	private int[] linearLayoutIDs = new int []{R.id.linear_answer_a,R.id.linear_answer_b,R.id.linear_answer_c,R.id.linear_answer_d,R.id.linear_answer_e,R.id.linear_answer_f,R.id.linear_answer_g,R.id.linear_answer_true,R.id.linear_answer_false};
	//多选正确
	private int [] image_duoxuan_true  = new int []{R.drawable.dr_1,R.drawable.dr_2,R.drawable.dr_3,R.drawable.dr_4,R.drawable.dr_5,R.drawable.dr_6,R.drawable.dr_7};
	//多选  未选择的选项
	private int [] image_duoxuan_default = new int[]{R.drawable.dn_1,R.drawable.dn_2,R.drawable.dn_3,R.drawable.dn_4,R.drawable.dn_5,R.drawable.dn_6,R.drawable.dn_7};
	//单选
	private int image_true[]  		   = new int []{R.drawable.r_1,R.drawable.r_2,R.drawable.r_3,R.drawable.r_4,R.drawable.r_5,R.drawable.r_6,R.drawable.r_7,R.drawable.rpd_1,R.drawable.rpd_2};
	private int[] image_default         = new int[]{R.drawable.answer_a,R.drawable.answer_b,R.drawable.answer_c,R.drawable.answer_d,R.drawable.answer_e,R.drawable.answer_f,R.drawable.answer_g,R.drawable.npd_1,R.drawable.npd_2};
	private Button buttom_button,pause_button;
	private int question_sums;
	private int viewpager_index=0; //ViewPager 滑动到第几页
	private int buttom_type=0; //0 正常 滑到下一页  1 ：多选（选择完成 ） 2 交卷
	//暂停页面
	private FrameLayout pause_ui_framelayout;
	private Animation slide_in_bottom,slide_out_top;
	private List<TextView> list_texts,list_texts_last;
	private myHandler handler = new myHandler();
	private ElasticScrollView_answer myScrollview;
	private FYuanTikuDialog dialog;
	private Boolean isRestStartTime=true; //True 需要重新获取开始时间  false 不需要
	private TextView pause_time,pause_time_last;
	//倒计时
	private int time_sum=5000; //剩余时间
	private int time_sums=0; //总共时间
	private myThread myThread = new myThread();
	private Boolean isStartThread=false;
	private boolean ispasue=false;//是否暂停

	private Boolean is_load_lastitem=false;//是否加载最后一个交卷页面
	private LinearLayout linear_Left_pause;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Bgcolor_2=0,Color_3=0;
	private int Screen_width=0;
	private String Test_play,Test_pause;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_testing_answer);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		Test_play = sharedPreferences.getString("Test_play", "");
		Test_pause = sharedPreferences.getString("Test_pause", "");
		//是否刷新首页ViewPager
		//MyFlg.ISupdateHome_viewpager = true;
		MyFlg.ISupdateHome=true;
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"提交答案中");
		initID();
		initData();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(viewpager_index!=viewsArr.length-1 &&list_questions.get(viewpager_index).getNo()!=-999){
			myThread.go_on();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myThread.onPause();
	}
	private void initID() {
		// TODO Auto-generated method stub
		pause_ui_framelayout =(FrameLayout) findViewById(R.id.pause_ui_framelayout);
		layoutLeft=(LinearLayout)findViewById(R.id.layoutLeft);
		linear_Left_pause=(LinearLayout)findViewById(R.id.linear_Left_pause);
		title_text_linear=(LinearLayout)findViewById(R.id.title_text_linear);
		title_text1=(TextView)findViewById(R.id.title_text1);
		title_text2=(TextView)findViewById(R.id.title_text2);
		title_text3=(TextView)findViewById(R.id.title_text3);
		riggt_image = (ImageView)findViewById(R.id.riggt_image);
		viewpager = (ViewPager)findViewById(R.id.viewpager);
		buttom_button=(Button)findViewById(R.id.buttom_button);
		//暂停页
		linear_pause = (LinearLayout)findViewById(R.id.linear_pause);
		pause_button=(Button)findViewById(R.id.pause_button);
		//设置颜色
		buttom_button.setBackgroundColor(Bgcolor_2);
		buttom_button.setTextColor(Fontcolor_1);
		pause_button.setBackgroundColor(Bgcolor_2);
		pause_button.setTextColor(Fontcolor_1);
		title_text2.setTextColor(Bgcolor_2);
		findViewById(R.id.title_xian).setBackgroundColor(Color_3);
		//设置监听
		buttom_button.setOnClickListener(this);
		pause_button.setOnClickListener(this);
		layoutLeft.setOnClickListener(this);
		riggt_image.setOnClickListener(this);
		linear_Left_pause.setOnClickListener(this);

		//初始化动画
		slide_in_bottom  = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
		slide_out_top  = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
	}
	private void initData() {
		// TODO Auto-generated method stub
		riggt_image.setImageBitmap(BitmapFactory.decodeFile(Test_pause));
		Intent intent = getIntent();
		bean = (Create_test_suitBean) intent.getSerializableExtra("bean");
		time_sum = bean.getTest_time_limit()*60*1000;
		time_sums = time_sum;
		list_questions = new ArrayList<Create_test_suitBean.questions>();
		int sum=0;
		for (int i = 0; i < bean.getGroups().length; i++) {
			sum =sum+bean.getGroups()[i].getQuestions().length;
			Create_test_suitBean.questions question = new Create_test_suitBean().new questions();
			question.setTitle(bean.getGroups()[i].getTitle());
			question.setDesc(bean.getGroups()[i].getDesc());
			question.setNo(-999);
			list_questions.add(question);
			question_sums =question_sums +bean.getGroups()[i].getQuestions().length;
			for (int j = 0; j < bean.getGroups()[i].getQuestions().length; j++) {
				list_questions.add(bean.getGroups()[i].getQuestions()[j]);
			}
		}
		sum= sum+bean.getGroups().length;
		//设置标题
		title_text1.setText(bean.getTest_type_name());
		title_text3.setText(" / "+question_sums);
		viewsArr = new View[sum+1];//加最后一页
		//设置暂停页
		list_texts = new ArrayList<TextView>();
		linear_pause.addView(setPauseUI(list_texts,0));
		//设置ViewPager最后一页
		list_texts_last = new ArrayList<TextView>();
		viewsArr[viewsArr.length-1] = setPauseUI(list_texts_last,1);

		viewpager.setAdapter(pagerAdapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int point) {
				// TODO Auto-generated method stub
				if(point==viewsArr.length-1){
					is_load_lastitem=true;
				}

				if(point == viewsArr.length-1){
					buttom_button.setText("交卷");
					buttom_type = 2;
					title_text_linear.setVisibility(View.GONE);
					//user_time = user_time+(System.currentTimeMillis()-startTime);
					isRestStartTime=true;
					pause_time_last.setText("用时： "+(nowUserTime())+" 分钟  / "+bean.getTest_time_limit()+"分钟");
					myThread.onPause();//暂停倒计时
				}else{
					//设置顶部标题
					if(list_questions.get(point).getNo()==-999){
						myThread.onPause();//暂停倒计时
						//	user_time = user_time+(System.currentTimeMillis()-startTime);
						isRestStartTime=true;
						title_text_linear.setVisibility(View.GONE);
						riggt_image.setImageBitmap(BitmapFactory.decodeFile(Test_play));
						//设置底部按钮
						buttom_type = 0;
						if(point==0){
							buttom_button.setText("开始");
						}else{
							buttom_button.setText("继续");
						}
					}else{
						if(isStartThread==false){
							myThread.start();
							isStartThread =true;
						}else if(ispasue==true){
							myThread.go_on();
						}

						if(isRestStartTime==true){
							//startTime = System.currentTimeMillis();
							isRestStartTime = false;
						}
						title_text2.setText(list_questions.get(point).getNo()+"");
						title_text_linear.setVisibility(View.VISIBLE);
						riggt_image.setImageBitmap(BitmapFactory.decodeFile(Test_pause));
						//设置底部按钮
						if(list_questions.get(point).getType()==2 && ("null".equals(list_questions.get(point).getAnswer()) || "".equals(list_questions.get(point).getAnswer()))){
							buttom_button.setText("下一题");
							buttom_type = 0;
							//question_startTime_duoxuan = question_startTime;
							//question_startTime = System.currentTimeMillis();
						}else{
							//question_startTime = System.currentTimeMillis();
							buttom_type = 0;
							buttom_button.setText("下一题");
						}
					}
					if(point == viewsArr.length-2){
						buttom_button.setText("完成");
					}
				}
				//如果上一题是多选 那么滑动就提交答案  在用户答了的情况下
				if(viewpager_index!=viewsArr.length-1 &&list_questions.get(viewpager_index).getNo()!=-999 && list_questions.get(viewpager_index).getType()==2 && !list_questions.get(viewpager_index).getAnswer().equals("") && !list_questions.get(viewpager_index).getAnswer().equals("null")){
					//list_questions.get(viewpager_index).setAnswertime(zhuanhuanTime(System.currentTimeMillis()-question_startTime_duoxuan));
					list_questions.get(viewpager_index).setAnswertime(nowUserTime());
					//submitOneQuestionAnswer(bean.getId()+"", list_questions.get(viewpager_index), list_questions.get(viewpager_index).getAnswertime());
					submitOneQuestionAnswer(bean.getId()+"", list_questions.get(viewpager_index), nowUserTime()+"");
				}
				viewpager_index = point;
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
	 * 设置暂停页面
	 */
	private View setPauseUI(List<TextView> list_texts,int type){
		View view_scrollview = View.inflate(this, R.layout.item_testing_pause_ui, null);
		LinearLayout pause_linear = (LinearLayout)view_scrollview.findViewById(R.id.pause_linear);
		if(type==0){
			myScrollview = (ElasticScrollView_answer)view_scrollview.findViewById(R.id.myScrollview);
			pause_time = (TextView)view_scrollview.findViewById(R.id.pause_time);

		}else{
			pause_time_last = (TextView)view_scrollview.findViewById(R.id.pause_time);
		}
		int button_index=0;//计算对应的Button点击之后显示的位置
		ViewHolder_pause_UI viewHolder = new ViewHolder_pause_UI();
		int left = DensityUtil.DipToPixels(this, 10);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((Screen_width-left*2)/5, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 50, 0, 0);
		LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(DensityUtil.DipToPixels(this, 30), DensityUtil.DipToPixels(this, 30));
		for (int i = 0; i < bean.getGroups().length; i++) {
			list_texts.add(null);
			button_index++;
			View view = View.inflate(this, R.layout.item_report, null);
			viewHolder.report_linear = (LinearLayout) view.findViewById(R.id.report_linear);
			viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
			viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
			viewHolder.text2.setVisibility(View.GONE);
			viewHolder.text1.setTextColor(Fontcolor_3);
			viewHolder.text1.setText( bean.getGroups()[i].getTitle());
			LinearLayout linearLayout = null;
			for (int j = 0; j <bean.getGroups()[i].getQuestions().length ; j++) {
				button_index++;
				Create_test_suitBean.questions questionBean = bean.getGroups()[i].getQuestions()[j];
				if((j+1)%5==1){ //开启新的一行
					linearLayout= new LinearLayout(this);
					viewHolder.report_linear.addView(linearLayout);
				}
				LinearLayout linearLayout2 = new LinearLayout(this);
				linearLayout2.setLayoutParams(params);
				linearLayout.addView(linearLayout2);
				linearLayout2.setGravity(Gravity.CENTER);
				TextView button = new TextView(this);
				button.setText(questionBean.getNo()+"");
				button.setGravity(Gravity.CENTER);
				button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				button.setTextColor(Fontcolor_3);
				button.setBackgroundResource(R.drawable.dp_k);
				button.setLayoutParams(params_button);
				//设置监听
				ButtonClick buttonClick = new ButtonClick(button_index-1,type);
				button.setOnClickListener(buttonClick);
				linearLayout2.addView(button);
				list_texts.add(button);
			}
			pause_linear.addView(view);
		}
		return view_scrollview;

	}
	//点击题号 跳转到相应的题目
	class ButtonClick implements OnClickListener{
		private int index;
		private int type=0; //0==暂停页面  1 viewPager最后一个页面
		public ButtonClick(int index,int type) {
			// TODO Auto-generated constructor stub
			this.index = index;
			this.type =type;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewpager.setCurrentItem(index);
			if(type==0){
				pause_ui_framelayout.startAnimation(slide_out_top);
				pause_ui_framelayout.setVisibility(View.GONE);
				myScrollview.smoothScrollTo(0, 0);
			}
			myThread.go_on();
		}

	}
	class ViewHolder_pause_UI{
		LinearLayout report_linear;
		TextView text1;
		TextView text2;
	}
	//适配器
	PagerAdapter pagerAdapter = new PagerAdapter() {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewsArr.length;
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
			if(position == viewsArr.length-1){

			}else{

				if(null==viewsArr[position]){
					Create_test_suitBean.questions question =list_questions.get(position);
					if(question.getNo()==-999){ //是Group
						viewsArr[position]=setViewPageItem_group(question);
					}else{
						viewsArr[position]=setViewPageItem_question(question,position);
					}
				}
			}
			((ViewPager) container).addView(viewsArr[position]);
			return viewsArr[position];
		}
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
	};
	/**
	 * 设置Group分页
	 */
	private View setViewPageItem_group(Create_test_suitBean.questions questtionBean){
		View view =View.inflate(this, R.layout.item_testing_viewpager2, null);
		TextView text1 = (TextView) view.findViewById(R.id.title);
		TextView desc = (TextView) view.findViewById(R.id.desc);
		desc.setTextColor(Fontcolor_3);
		text1.setText(questtionBean.getTitle());
		desc.setText(questtionBean.getDesc());
		return view;
	}
	private View setViewPageItem_question(Create_test_suitBean.questions questtionBean,int point){
		View view = View.inflate(this, R.layout.item_testing_viewpager, null);
		TextView[] TextViews = new TextView[]{viewHolder.timu,viewHolder.answer_text_a,viewHolder.answer_text_b,viewHolder.answer_text_c,viewHolder.answer_text_d,viewHolder.answer_text_e,viewHolder.answer_text_f,viewHolder.answer_text_g,viewHolder.answer_text_true,viewHolder.answer_text_false};
		ImageView[] imageViews = new ImageView[]{viewHolder.imageview,viewHolder.answer_a,viewHolder.answer_b,viewHolder.answer_c,viewHolder.answer_d,viewHolder.answer_e,viewHolder.answer_f,viewHolder.answer_g,viewHolder.answer_true,viewHolder.answer_false};
		LinearLayout[] linearLayouts = new LinearLayout[]{viewHolder.linear_answer_a,viewHolder.linear_answer_b,viewHolder.linear_answer_c,viewHolder.linear_answer_d,viewHolder.linear_answer_e,viewHolder.linear_answer_f,viewHolder.linear_answer_g,viewHolder.linear_answer_true,viewHolder.linear_answer_false};
		for (int i = 0; i < TextViews.length; i++) {
			TextViews[i] = (TextView) view.findViewById(textviewIDs[i]);
			//设置居中
			if(i>0){
				TextViews[i].setPadding(30, DataUtil.answer_text_padingtop, 0, 0);
			}
			TextViews[i].setTextColor(Fontcolor_3);
		}
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[i] = (ImageView) view.findViewById(imageviewIDs[i]);
			if(i>0 && questtionBean.getType()==2 && i <imageViews.length-2){
				imageViews[i].setImageResource(image_duoxuan_default[i-1]);
			}
		}
		Question_click question_click = new Question_click(questtionBean.getType(), imageViews, point);
		for (int i = 0; i < linearLayouts.length; i++) {
			linearLayouts[i] = (LinearLayout) view.findViewById(linearLayoutIDs[i]);
			linearLayouts[i].setOnClickListener(question_click);
		}
		//设置数据
		TextViews[0].setText(questtionBean.getContent());
		if(null !=questtionBean.getImg() && null !=questtionBean.getImg().getUrl()){
			LoadImageViewUtil.setImageBitmap(imageViews[0], questtionBean.getImg().getUrl(),Testing_answer_Activity.this);
					/*LoadImage image = new LoadImage(imageViews[0], questtionBean.getImg());
					image.execute(questtionBean.getImg());*/
		}
		for (int i = 0; i < questtionBean.getOpt().length; i++) {
			Create_test_suitBean.opt opt = questtionBean.getOpt()[i];
			if(opt.getK().equals("A")){
				linearLayouts[0].setVisibility(View.VISIBLE);
				TextViews[1].setText(opt.getC());
			}else if(opt.getK().equals("B")){
				linearLayouts[1].setVisibility(View.VISIBLE);
				TextViews[2].setText(opt.getC());
			}else if(opt.getK().equals("C")){
				linearLayouts[2].setVisibility(View.VISIBLE);
				TextViews[3].setText(opt.getC());
			}else if(opt.getK().equals("D")){
				linearLayouts[3].setVisibility(View.VISIBLE);
				TextViews[4].setText(opt.getC());
			}else if(opt.getK().equals("E")){
				linearLayouts[4].setVisibility(View.VISIBLE);
				TextViews[5].setText(opt.getC());

			}else if(opt.getK().equals("F")){
				linearLayouts[5].setVisibility(View.VISIBLE);
				TextViews[6].setText(opt.getC());
			}else if(opt.getK().equals("G")){
				linearLayouts[6].setVisibility(View.VISIBLE);
				TextViews[7].setText(opt.getC());
			}else if(opt.getK().equals("t")){
				linearLayouts[7].setVisibility(View.VISIBLE);
				TextViews[8].setText(opt.getC());
			}else if(opt.getK().equals("f")){
				linearLayouts[8].setVisibility(View.VISIBLE);
				TextViews[9].setText(opt.getC());
			}
		}
		return view;

	}

	//Viewpager里面的点击事件
	class Question_click implements OnClickListener{
		private ImageView [] imageviews;
		private int type;
		private int question_poin;
		public String [] duoxuan_answer=new String[]{"","","","","","",""};
		public Question_click(int type,ImageView [] imageviews,int question_point) {
			// TODO Auto-generated constructor stub
			this.type =type;
			this.imageviews = imageviews;
			this.question_poin = question_point;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (type == 2) { //如果是多选
				switch (v.getId()) {
					case R.id.linear_answer_a:
						setDuoxuanImageView(0, "A", imageviews[1]);
						break;
					case R.id.linear_answer_b:
						setDuoxuanImageView(1, "B", imageviews[2]);
						break;
					case R.id.linear_answer_c:
						setDuoxuanImageView(2, "C", imageviews[3]);
						break;
					case R.id.linear_answer_d:
						setDuoxuanImageView(3, "D", imageviews[4]);
						break;
					case R.id.linear_answer_e:
						setDuoxuanImageView(4, "E", imageviews[5]);
						break;
					case R.id.linear_answer_f:
						setDuoxuanImageView(5, "F", imageviews[6]);
						break;
					case R.id.linear_answer_g:
						setDuoxuanImageView(6, "G", imageviews[7]);
						break;

					default:
						break;
				}
			} else { //单选或者判断
				switch (v.getId()) {
					case R.id.linear_answer_a:
						setAnsWer("A", 0, imageviews[1]);
						break;
					case R.id.linear_answer_b:
						setAnsWer("B", 1, imageviews[2]);
						break;
					case R.id.linear_answer_c:
						setAnsWer("C", 2, imageviews[3]);
						break;
					case R.id.linear_answer_d:
						setAnsWer("D", 3, imageviews[4]);
						break;
					case R.id.linear_answer_e:
						setAnsWer("E", 4, imageviews[5]);
						break;
					case R.id.linear_answer_f:
						setAnsWer("F", 5, imageviews[6]);
						break;
					case R.id.linear_answer_g:
						setAnsWer("F", 6, imageviews[7]);
						break;
					case R.id.linear_answer_true:
						setAnsWer("t", 7, imageviews[8]);
						break;
					case R.id.linear_answer_false:
						setAnsWer("f", 8, imageviews[9]);
						break;
					default:
						break;
				}
			}
		}
		/**
		 * 设置多选的图片
		 */
		public void setDuoxuanImageView(int i,String answer,ImageView imageview){
			if(duoxuan_answer[i].equals("")){ //字符串为"" 表示没有选此选项
				duoxuan_answer[i]=answer;
				imageview.setImageResource(image_duoxuan_true[i]);
			}else{
				duoxuan_answer[i]="";
				imageview.setImageResource(image_duoxuan_default[i]);
			}
			String st = "";
			for (int j = 0; j < duoxuan_answer.length; j++) {
				st=st+duoxuan_answer[j];
			}
			String duoxuan = "";
			for (int j = 0; j < st.length(); j++) {
				if(j==st.length()-1){
					duoxuan =duoxuan+ st.substring(j, j+1);
				}else{
					duoxuan =duoxuan+ st.substring(j, j+1)+",";
				}
			}
			list_questions.get(question_poin).setAnswer(duoxuan);
			list_questions.get(question_poin).setDuoxuan_answer(duoxuan_answer);
			if(!duoxuan.trim().equals("")){
				list_texts.get(viewpager_index).setBackgroundResource(R.drawable.dp_r);
				list_texts.get(viewpager_index).setTextColor(Color.WHITE);
				list_texts_last.get(viewpager_index).setBackgroundResource(R.drawable.dp_r);
				list_texts_last.get(viewpager_index).setTextColor(Color.WHITE);
			}else{
				list_texts.get(viewpager_index).setBackgroundResource(R.drawable.dp_k);
				list_texts.get(viewpager_index).setTextColor(Fontcolor_3);
				list_texts_last.get(viewpager_index).setBackgroundResource(R.drawable.dp_k);
				list_texts_last.get(viewpager_index).setTextColor(Fontcolor_3);
			}
		}
		/**
		 * 单选  判断
		 *
		 */
		public void setAnsWer(String answer,int image_index,ImageView imageView){
			int i  =list_questions.get(question_poin).getLastAnswer();//上一次选择的选项
			if(i!=0){
				imageviews[i].setImageResource(image_default[i-1]);
			}
			imageView.setImageResource(image_true[image_index]);
			list_questions.get(question_poin).setAnswer(answer);
			//设置暂停页面显示已答
			list_texts.get(viewpager_index).setBackgroundResource(R.drawable.dp_r);
			list_texts.get(viewpager_index).setTextColor(Color.WHITE);
			list_texts_last.get(viewpager_index).setBackgroundResource(R.drawable.dp_r);
			list_texts_last.get(viewpager_index).setTextColor(Color.WHITE);
			list_questions.get(question_poin).setLastAnswer(image_index+1);
			//list_questions.get(question_poin).setAnswertime(zhuanhuanTime(System.currentTimeMillis()-question_startTime));
			list_questions.get(question_poin).setAnswertime(nowUserTime());
			list_questions.get(question_poin).setUser_choose_index(image_index);
			//submitOneQuestionAnswer(bean.getId()+"", list_questions.get(question_poin), list_questions.get(question_poin).getAnswertime());
			submitOneQuestionAnswer(bean.getId()+"", list_questions.get(question_poin), nowUserTime());
			viewpager.setCurrentItem(viewpager_index+1);
		}
	}
	class ViewHolder{
		TextView timu,answer_text_a,answer_text_b,answer_text_c,answer_text_d,answer_text_e,answer_text_f,answer_text_g,answer_text_true,answer_text_false;
		ImageView imageview,answer_a,answer_b,answer_c,answer_d,answer_e,answer_f,answer_g,answer_true,answer_false;
		LinearLayout linear_answer_a,linear_answer_b,linear_answer_c,linear_answer_d,linear_answer_e,linear_answer_f,linear_answer_g,linear_answer_true,linear_answer_false;

	}
	//计算剩下多少题没做
	private void setShengxia(){
		int shengxiaSum=0;
		for (int i = 0; i < list_questions.size(); i++) {
			Create_test_suitBean.questions questionBean = list_questions.get(i);
			if(questionBean.getNo()!=-999 && (questionBean.getAnswer().trim().equals("") || questionBean.getAnswer().trim().equals("null"))){
				shengxiaSum = shengxiaSum+1;
			}
		}
		if(shengxiaSum<=0){
			clickReport();
		}else{
			showAlertDialog2("提示", "还有"+shengxiaSum+"题没答，确认交卷？", "取消", "确定");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {//返回  点击继续
			case R.id.linear_Left_pause:
				pause_ui_framelayout.startAnimation(slide_out_top);
				pause_ui_framelayout.setVisibility(View.GONE);
				myScrollview.smoothScrollTo(0, 0);
				//startTime = System.currentTimeMillis();
				myThread.go_on();
				break;
			case R.id.layoutLeft:
				//Toast.makeText(Testing_answer_Activity.this, "返回按钮", 0).show();
				showAlertDialogChoose("提示", "是否确认退出本次考试", "取消", "确定");
				break;
			case R.id.buttom_button://底部按钮
				switch (buttom_type) {
					case 0:
						viewpager.setCurrentItem(viewpager_index+1);
						break;
					case 1: //多选
						if(!list_questions.get(viewpager_index).getAnswer().equals("null") && !list_questions.get(viewpager_index).getAnswer().equals("")){
							//设置暂停页面显示已答
							list_texts.get(viewpager_index).setBackgroundResource(R.drawable.dp_r);
							list_texts_last.get(viewpager_index).setBackgroundResource(R.drawable.dp_r);
						}
						buttom_button.setText("下一题");
						buttom_type=0;
						break;
					case 2: //交卷
						setShengxia();

						break;
					default:
						break;
				}

				break;
			case R.id.riggt_image://右上角开始 /暂停按钮
				if(list_questions.get(viewpager_index).getNo()!=-999){
					pause_ui_framelayout.setVisibility(View.VISIBLE);
					pause_ui_framelayout.startAnimation(slide_in_bottom);
					//user_time = user_time+(System.currentTimeMillis()-startTime);
					isRestStartTime=true;
					pause_time.setText("用时： "+(nowUserTime())+" 分钟  / "+bean.getTest_time_limit()+" 分钟");
					myThread.onPause();//暂停倒计时
					if(is_load_lastitem==true){
						pause_button.setText("交卷");
					}
				}
				break;
			case R.id.pause_button://暂停页面底部按钮
				if(is_load_lastitem==true){
					setShengxia();
				}else{
					pause_ui_framelayout.startAnimation(slide_out_top);
					pause_ui_framelayout.setVisibility(View.GONE);
					myScrollview.smoothScrollTo(0, 0);
					//startTime = System.currentTimeMillis();
					myThread.go_on();
				}
				break;
			default:
				break;
		}
	}


	/**
	 * 提交套题答案
	 */
	private void clickReport(){
		dialog.show();
		JSONArray jsonArray = new JSONArray();
		for (int j = 0; j <list_questions.size(); j++) {
			try {
				Create_test_suitBean.questions question= list_questions.get(j);
				if(question.getNo()!=-999 && !question.getAnswer().equals("") && !question.getAnswer().equals("null")){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("no", question.getNo());
					jsonObject.put("answer", question.getAnswer());
					jsonObject.put("answertime", question.getAnswertime());
					jsonArray.put(jsonObject);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Testing_answer_Activity.this)));
		params.add(new BasicNameValuePair("suit_id",bean.getId()+""));
		params.add(new BasicNameValuePair("suit_answers",jsonArray.toString()));
		params.add(new BasicNameValuePair("used_time",nowUserTime()));
		AsyncLoadApi asyncLoadApi = new AsyncLoadApi(this, handler, params, "submit_test_suit",4,5,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Testing_answer_Activity.this).getSubmit_test_suit(),Testing_answer_Activity.this));
		asyncLoadApi.execute();
	}
	/**
	 * 提交单题答案
	 * @param suit_id 套题ID
	 * @param questionBean 问题Bean
	 * @param time 用时（以分为单位）
	 */

	public void submitOneQuestionAnswer(String suit_id,Create_test_suitBean.questions questionBean,String time){
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Testing_answer_Activity.this)));
		params.add(new BasicNameValuePair("suit_id",suit_id));
		params.add(new BasicNameValuePair("question_no",questionBean.getNo()+""));
		params.add(new BasicNameValuePair("answer",questionBean.getAnswer()));
		params.add(new BasicNameValuePair("used_time",time));
		AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Testing_answer_Activity.this, handler, params, "submit_test_answer",99,100,false,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Testing_answer_Activity.this).getSubmit_test_answer(),Testing_answer_Activity.this));//不用管返回
		asyncLoadApi.execute();
	}
	class myHandler extends Handler{


		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:

					break;
				case 1:
					break;
				case 4: //提交答案成功
					Submit_exercise_suitBean sesBean=APPUtil.submit_test_suit(Testing_answer_Activity.this);
					Intent intent = new Intent(Testing_answer_Activity.this, PracticeReportActivity.class);
					Bundle bundle = new Bundle();
					//bundle.putSerializable("bean", bean);
					bundle.putSerializable("Submit_exercise_suitBean", sesBean);
					intent.putExtras(bundle);
					intent.putExtra("list_questions", (Serializable)list_questions);
					intent.putExtra("flg", 1);
					intent.putExtra("title", bean.getTitle());
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					dialog.dismiss();
					MyFlg.all_activitys.remove(Testing_answer_Activity.this);
					finish();
					break;
				case 5:
					dialog.dismiss();
					break;
				case 99://提交答案成功
					break;
				case 100://提交答案失败
					break;
				case 101:
					showAlertDialog("提示", "答题时间到,请交卷", "取消", "确定");
					break;
				default:
					break;
			}
		}
	}

	private String zhuanhuanTime(long time){
		String st = (time/1000)+"";
		return st;

	}
	//仿IOS对话框
	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(Testing_answer_Activity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								MyFlg.all_activitys.remove(Testing_answer_Activity.this);
								finish();
								break;
							default:
								break;
						}

					}
				});
	}
	//仿IOS对话框  隐藏左边按钮
	public void showAlertDialog(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialog(Testing_answer_Activity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								break;
							case F_IOS_Dialog.BUTTON2:
								Toast.makeText(Testing_answer_Activity.this, "时间到,提交测验！", 0).show();
								dialog.dismiss();
								clickReport();
								break;
							default:
								break;
						}

					}
				},0);
	}
	public void showAlertDialog2(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(Testing_answer_Activity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								clickReport();
								break;
							default:
								break;
						}

					}
				});
	}
	//倒计时  如果答题时间到 弹出提示
	class myThread extends Thread{
		private Boolean flg=true;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while(flg){
				//时间到0时，停止
				if(time_sum/1000 == 0 || ispasue){
					if(time_sum/1000 == 0){
						handler.sendEmptyMessage(101);
						flg = false;
					}
					synchronized(this){
						try {
							this.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				time_sum = time_sum-1000;
				//handler.sendEmptyMessage(101);
				try {
					//一秒钟更新一次
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		private void onPause(){
			ispasue=true;
		}
		private void go_on(){
			synchronized(this){
				//线程已启动,暂停后继续
				this.notify();
				ispasue = false;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode ==KeyEvent.KEYCODE_BACK ) {

			if(pause_ui_framelayout.getVisibility()==View.VISIBLE){
				pause_ui_framelayout.startAnimation(slide_out_top);
				pause_ui_framelayout.setVisibility(View.GONE);
				myScrollview.smoothScrollTo(0, 0);
				//startTime = System.currentTimeMillis();
				myThread.go_on();
			}else{
				showAlertDialogChoose("提示", "是否确认退出本次考试", "取消", "确定");
			}
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 当前用时  （分）
	 * @return
	 */
	private String nowUserTime(){

		return (time_sums-time_sum)/1000/60+"";
	}

}
