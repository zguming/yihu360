package cn.net.dingwei.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.liantigou.read_question.Read_Question_Child_ViewPager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.LoadImage;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean.groups;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean.note_bean;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean.questions;
import cn.net.dingwei.Bean.Edit_user_noteBean;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.Bean.Get_point_infoBean;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.Bean.Submit_exercise_suitBean;
import cn.net.dingwei.adpater.ViewPagerAdpater;
import cn.net.dingwei.myView.AnswerDialog;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.myView.MyLinear_readquestion;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.AnswerUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Reading_QuestionsActivity extends ParentActivity implements OnClickListener{
	private ViewPager viewpager_parent;
	private myhandler handler;
	private Create_Exercise_suit_2Bean create_Exercise_suit_2Bean;
	private List<View> list_view;
	//快到期的View集合   如果用户的会员状态变了  要隐藏
	private List<LinearLayout> list_pay_hints;
	private List<LinearLayout> list_content_textviews;
	private List<LinearLayout> list_pay_linears;
	private FYuanTikuDialog dialog;
	private int view_width = 0;//除去左右间隔 剩余的中间全部宽 计算考点

	private int image_true[]  		   = new int []{R.drawable.r_1,R.drawable.r_2,R.drawable.r_3,R.drawable.r_4,R.drawable.r_5,R.drawable.r_6,R.drawable.r_7,R.drawable.rpd_1,R.drawable.rpd_2};
	private int image_false[] 		   = new int []{R.drawable.w_1,R.drawable.w_2,R.drawable.w_3,R.drawable.w_4,R.drawable.w_5,R.drawable.w_6,R.drawable.w_7,R.drawable.wpd_1,R.drawable.wpd_2,};
	//多选按钮 错误
	private int	[] image_duoxuan_false = new int []{R.drawable.dc_1,R.drawable.dc_2,R.drawable.dc_3,R.drawable.dc_4,R.drawable.dc_5,R.drawable.dc_6,R.drawable.dc_7};
	//多选按钮 半对
	private int [] image_duoxuan_tAndf = new int []{R.drawable.ddc_1,R.drawable.ddc_2,R.drawable.ddc_3,R.drawable.ddc_4,R.drawable.ddc_5,R.drawable.ddc_6,R.drawable.ddc_7};
	//多选正确
	private int [] image_duoxuan_true  = new int []{R.drawable.dr_1,R.drawable.dr_2,R.drawable.dr_3,R.drawable.dr_4,R.drawable.dr_5,R.drawable.dr_6,R.drawable.dr_7};
	//多选  未选择的选项
	private int [] image_duoxuan_default = new int[]{R.drawable.dn_1,R.drawable.dn_2,R.drawable.dn_3,R.drawable.dn_4,R.drawable.dn_5,R.drawable.dn_6,R.drawable.dn_7};
	//private groups[] groups;
	viewholder viewholder = new viewholder();

	private int index_sum=0;
	private int index=0;
	//--------------点击考点显示的对话框------------------
	private	AnswerDialog answerDialog ;
	private int dip_45=0,dip_25=0;//可拖动的图标高
	private TextView title_text1,title_xian,title_text2,title_text3,title_text4;//标题栏
	private MyApplication application;//全局变量
	private RelativeLayout title_bg;//标题栏 背景
	private ImageView title_left,image_title_Collection,linear2_image;

	private Question_click[] list_click;
	private LinearLayout layoutLeft,layoutRight;
	private int type=0; //0：正常   1:错题解析 2：整组解析  3:继续练习   4 笔记详情(笔记点击进来的) 5 全部解析（所有题）
	private Intent intent;//获取传递的数据
	private int infos_index,question_index;//解析 传递的位置
	private Button buttom_button;//底部按钮
	/**
	 * //0 默认 直接点击解析  1 选择完成  2 下一题   3 查看小组总结  4 关闭当前Activity 5开始下一组
	 */
	private  int buttom_btn_type=0;
	private Read_Question_Child_ViewPager[] chid_viewpagers;//记录子ViewPager
	private Animation hide_anim,show_anim;//底部按钮的显示与隐藏 淡入淡出效果
	private Boolean botton_isShow=true;//底部按钮 显示  true false 隐藏  用于在显示与隐藏之间切换时判断用动画的标识

	private Create_Exercise_suit_2Bean.infos[] infos; //材料题数组
	private int group_index=0;//当前是第几组
	private int question_no=1;//第一个显示的题的题号
	private MyLinear_readquestion mylinear;
	//右侧菜单
	private LinearLayout linear3_bg;
	private Button linear3_button1,linear3_button2;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_5=0,Bgcolor_6=0;
	private int Screen_width=0,Screen_height=0,StateHeight=0;
	private String jiucuo,shoucang_0,shoucang_1,Huakuai;
	private String questionNO="";//题号
	private String suitid="";//套题ID
	private TextView[] note_texts;//记录笔记的控件
	//提示  收藏什么的
	private SharedPreferences sp_commoninfo;
	private Placeholder_textBean placeholder_textBean;
	//记录每题收藏得TexView 和ImageView
	private TextView [] collection_textview;
	private ImageView [] collection_imageview;
	private Boolean isCilickCollection=false;//是否点击了收藏或取消收藏按钮
	private int flg=0;//1 从我的错题跳转过来的  2  从解析而来 
	private TextView now_text_add_note;//用于写了笔记回来改变值

	private String test_type="";//试卷类型
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isSetTile=false;
		MyFlg.all_activitys.add(this);
		MyFlg.ISupdateHome_viewpager = true;
		setContentView(R.layout.activity_read_question);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_5 = sharedPreferences.getInt("bgcolor_10", 0);//对的颜色
		Bgcolor_6 = sharedPreferences.getInt("bgcolor_9", 0);//错的颜色
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		Screen_height=sharedPreferences.getInt("Screen_height", 0);
		StateHeight=sharedPreferences.getInt("StateHeight", 0);

		jiucuo =  sharedPreferences.getString("jiucuo", "");
		shoucang_0 =  sharedPreferences.getString("shoucang_0", "");
		shoucang_1 =  sharedPreferences.getString("shoucang_1", "");
		Huakuai = sharedPreferences.getString("Huakuai", "");
		try {
			sp_commoninfo= getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
			Gson gson = new Gson();
			placeholder_textBean = gson.fromJson(new JSONObject(sp_commoninfo.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initID();
		initData();
		MyFlg.listActivity.add(this);

	}
	private void initID() {
		// TODO Auto-generated method stub
		//右侧半屏
		mylinear = (MyLinear_readquestion)findViewById(R.id.myLinear);
		mylinear.setActivity(this);

		linear2_image = (ImageView)findViewById(R.id.linear2_image);
		linear3_bg = (LinearLayout)findViewById(R.id.linear3_bg);
		linear3_button1 = (Button)findViewById(R.id.linear3_button1);
		linear3_button2 = (Button)findViewById(R.id.linear3_button2);

		viewpager_parent=(ViewPager)findViewById(R.id.viewpager_parent);
		title_text1=(TextView)findViewById(R.id.title_text1);
		title_xian = (TextView)findViewById(R.id.title_xian);
		title_text2 = (TextView)findViewById(R.id.title_text2);
		title_text3 = (TextView)findViewById(R.id.title_text3);
		title_text4 = (TextView)findViewById(R.id.title_text4);
		title_bg = (RelativeLayout)findViewById(R.id.title_bg);
		title_left = (ImageView)findViewById(R.id.title_left);
		image_title_Collection= (ImageView)findViewById(R.id.image_title_Collection);
		layoutLeft = (LinearLayout)findViewById(R.id.layoutLeft);
		buttom_button = (Button)findViewById(R.id.buttom_button);
		layoutRight = (LinearLayout)findViewById(R.id.layoutRight);
		layoutRight.setVisibility(View.VISIBLE);
		buttom_button.setTextColor(Color.WHITE);
		buttom_button.setBackgroundColor(Bgcolor_2);
		title_text3.setTextColor(Bgcolor_2);

		image_title_Collection.setVisibility(View.VISIBLE);
		//设置颜色
		linear3_bg.setBackgroundColor(Bgcolor_6);
		linear3_button1.setBackgroundColor(Bgcolor_1);
		linear3_button2.setBackgroundColor(Bgcolor_1);
		linear3_button1.setTextColor(Fontcolor_1);
		linear3_button2.setTextColor(Fontcolor_1);

		buttom_button.setOnClickListener(this);
		layoutLeft.setOnClickListener(this);
		linear3_button1.setOnClickListener(this);
		linear3_button2.setOnClickListener(this);
		image_title_Collection.setOnClickListener(this);
		linear2_image.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		hide_anim = AnimationUtils.loadAnimation(this, R.anim.fade_hide_200ms);
		show_anim = AnimationUtils.loadAnimation(this, R.anim.fade_show_200ms);
		dip_45 = DensityUtil.DipToPixels(this, 45);
		dip_25 = DensityUtil.DipToPixels(this, 25);
		list_content_textviews = new ArrayList<>();
		list_pay_linears = new ArrayList<LinearLayout>();
		list_pay_hints = new ArrayList<LinearLayout>();
		answerDialog = new AnswerDialog(this);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		view_width =Screen_width-DensityUtil.DipToPixels(this, 20);

		handler= new myhandler();
		intent = getIntent();
		create_Exercise_suit_2Bean = (Create_Exercise_suit_2Bean) intent.getSerializableExtra("bean");
		if(null==create_Exercise_suit_2Bean.getType()){
			test_type="";
		}else{
			test_type=create_Exercise_suit_2Bean.getType();
		}

		//Log.i("mylog", "test_type="+test_type);
		type = intent.getIntExtra("type",0);
		suitid = create_Exercise_suit_2Bean.getId()+"";
		flg = intent.getIntExtra("flg", 0);
		if(flg==1){
			setResult(RESULT_OK);
		}
		setUI();
	}
	private class myhandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle bundle=null;
			switch (msg.what) {
				case 20://提交套题答案成功
					//Toast.makeText(Reading_QuestionsActivity.this, "查看报告", 0).show();
					Intent intent = new Intent(Reading_QuestionsActivity.this, PracticeReportActivity.class);
					Submit_exercise_suitBean sesBean=APPUtil.Submit_exercise_suitBean(Reading_QuestionsActivity.this);
					bundle = new Bundle();
					bundle.putSerializable("bean", create_Exercise_suit_2Bean);
					bundle.putSerializable("Submit_exercise_suitBean", sesBean);
					intent.putExtras(bundle);
					intent.putExtra("flg", 2);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					dialog.dismiss();
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					MyFlg.all_activitys.remove(Reading_QuestionsActivity.this);
					finish();
					break;
				case 21://提交套题答案失败
					dialog.dismiss();
					break;
				case 9://点击考点 显示
					dialog.dismiss();
					Get_point_infoBean get_point_infoBean = APPUtil.Get_point_infoBean(Reading_QuestionsActivity.this);
					if(null!=get_point_infoBean){
						answerDialog.initUI(get_point_infoBean.getPoints(), get_point_infoBean.getExe_c()+"",get_point_infoBean.getWro_d()+"", get_point_infoBean.getKpl());
						answerDialog.showDialog();
					}else{
						Toast.makeText(Reading_QuestionsActivity.this, "获取考点信息失败", Toast.LENGTH_SHORT).show();
					}
					break;
				case 10: //、点击考点加载失败
					dialog.dismiss();
					break;
				default:
					break;
			}
		}
	}

	private void setUI() {
		// TODO Auto-generated method stub
		title_text1.setText(create_Exercise_suit_2Bean.getTitle());
		list_view = new ArrayList<View>();
		groups[] groups = create_Exercise_suit_2Bean.getGroups();
		group_index = intent.getIntExtra("group_index", 0);
		infos = Sup.getInfos(type,groups,group_index);
		title_text2.setText("第"+(group_index+1)+"组");

		if(type==1){ //错题解析
			infos = Sup.cuotijiexi(infos);
			title_bg.setBackgroundColor(Bgcolor_5);
		}
		if(type==1 || type==2 || type==4|| type==5){
			setBottom_button_content(4, "返回");
		}
		int cuotijiexi_questionNO=0;

//		Log.i("123", "答题：type: "+type);
//		Log.i("123", "答题：infos: "+infos.toString());
//		Log.i("123", "答题：infos: "+infos.length);
//		Log.i("123", "答题：group_index: "+group_index);

		for (int i = 0; i < infos.length; i++) {
			for (int j = 0; j < infos[i].getQuestions().length; j++) {
				cuotijiexi_questionNO=cuotijiexi_questionNO+1;
				infos[i].getQuestions()[j].setCuotijiexi_questionNO(cuotijiexi_questionNO);
			}
			//初始化用户滑动位置
			infos[i].setQuestion_index(null);
			infos[i].setIsShowBottom_button(true);//默认都显示底部按钮
			//记数 题目总数
			question_sum = question_sum+infos[i].getQuestions().length;
			if(infos[i].getInfo_status()==1){ //材料题
				View view = new View(this);
				view.setTag(0);
				list_view.add(view);
			}else{	//非材料题
				for (int j = 0; j < infos[i].getQuestions().length; j++) {
					View view = new View(this);
					view.setTag(0);
					list_view.add(view);
				}
			}
		}
		collection_textview = new TextView[cuotijiexi_questionNO];
		collection_imageview = new  ImageView[cuotijiexi_questionNO];

		list_click= new Question_click[question_sum];
		infos_index = intent.getIntExtra("infos_index", 0);
		question_index = intent.getIntExtra("question_index", 0);
		index = infos_index; //设置默认值
		infos[index].setQuestion_index(question_index);
		Create_Exercise_suit_2Bean.questions question=infos[infos_index].getQuestions()[question_index];
		if(type==0||type==3){
			//list_view.add(lastItem());
			lastItem();//第二页
			if(type==3){ //继续  
				question_no = question.getNo();
				//计算正确率
				go_on_setRight();

			}
		}else if(type==2 || type==3|| type==5){
			title_text2.setVisibility(View.GONE);
			setTitleBG(question);
		}
		//初始底部按钮
		if((type==0||type==3)&&infos[infos_index].getQuestions()[question_index].getType()==2){//如果第一题是多选 设置底部按钮
			setBottom_button_content(1,"选择完成");
		}else if((type==0||type==3)){
			setBottom_button_content(0,"查看解析");
		}

		title_text4.setText(" / "+question_sum);
		questionNO = ""+infos[infos_index].getQuestions()[question_index].getCuotijiexi_questionNO();
		title_text3.setText(questionNO);
		//手指标题栏右上角收藏图标
		chid_viewpagers = new Read_Question_Child_ViewPager[infos.length];
		index_sum = list_view.size();
		viewpager_parent.setAdapter(pagerAdapter);

		//查看解析 报告页面跳转
		viewpager_parent.setCurrentItem(infos_index);
		setcollectImage(infos[infos_index].getQuestions()[question_index].getIs_collect());

		//判断  如果只有一道题的时候也要让滑动
		int questionIndex=0;
		if((type==0||type==3)&&infos.length==1&&questionIndex==infos[infos_index].getQuestions().length-1){ //如果记录位置是最后一个
			mylinear.setIsCanScroll(true);
		}

		viewpager_parent.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int point) {
				if((type==0||type==3)){
					//设置顶部
					if(point==index_sum-1){//最后一页
						int questionIndex=0;
						if(null==infos[point].getQuestion_index()){
							questionIndex= 0;
						}else{
							questionIndex=infos[point].getQuestion_index();
						}
						Create_Exercise_suit_2Bean.questions question  = infos[point].getQuestions()[questionIndex];
						if(questionIndex==infos[point].getQuestions().length-1){ //如果记录位置是最后一个
							mylinear.setIsCanScroll(true);
							if(!question.getAnswer().equals("null")&&!question.getAnswer().equals("")){
								setBottom_button_content(3, "查看小组总结");
							}
						}

					}else if(point==index_sum-2){
						mylinear.setIsCanScroll(false);
					}
					questions question = Sup.getQuestionBean(infos,index);
					String answer = question.getAnswer();

					if(question.getType()==2&&answer.equals("null")&&null!=question.getDuoxuan()&&question.getDuoxuan().length()>0){
						list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan(question.getDuoxuan().replace("、", ""), false);
					}


				}else if(type!=0){
					int questionIndex=0;
					if(null==infos[point].getQuanzujiexi_index()){
						questions question;
						if(point<infos_index){
							question=infos[point].getQuestions()[infos[point].getQuestions().length-1];
						}else{
							question=infos[point].getQuestions()[0];
						}
						setTitleBG(question);
						questionNO = question.getCuotijiexi_questionNO()+"";
						setcollectImage(question.getIs_collect());
						title_text3.setText(questionNO+"");


					}else{
						questionIndex=infos[point].getQuanzujiexi_index();
						Create_Exercise_suit_2Bean.questions question=infos[point].getQuestions()[questionIndex];
						setTitleBG(question);
						questionNO = question.getCuotijiexi_questionNO()+"";
						title_text3.setText(questionNO+"");
						setcollectImage(question.getIs_collect());
					}
				}
				//**********************************************************************
				//设置底部按钮
				if((type==0||type==3)){
					if(infos[point].getIsShowBottom_button()){
						setBottom_button_anim(true);
					}else {
						setBottom_button_anim(false);
					}
					int questionIndex=0;
					if(null==infos[point].getQuestion_index()){
						questionIndex= 0;
					}else{
						questionIndex=infos[point].getQuestion_index();
					}
					Create_Exercise_suit_2Bean.questions question= infos[point].getQuestions()[questionIndex];
					questionNO=question.getCuotijiexi_questionNO()+"";
					title_text3.setText(questionNO+"");
					setcollectImage(question.getIs_collect());
					String answer= question.getAnswer();
					if((type==0||type==3)&&null==answer){//没答
						infos[point].getQuestions()[questionIndex].setAnswer("null");
						answer="null";
					}
					if((type==0||type==3)&&!answer.equals("null")&&!answer.equals("")){//用户已答过
						if(infos[point].getQuestions().length-1==questionIndex && point==index_sum-1){
							setBottom_button_content(3,"查看小组总结");
						}else{
							setBottom_button_content(2,"下一题");
						}

					}else if((type==0||type==3)&&(answer.equals("null")||answer.equals(""))){
						if(question.getType()==2){
							setBottom_button_content(1,"选择完成");
						}else{
							setBottom_button_content(0,"查看解析");
						}
					}
				}else{
					if(infos[point].getIsShowBottom_button()){
						setBottom_button_anim(true);
					}else{
						setBottom_button_anim(false);
					}
					int questionIndex=0;
					if((type==0||type==3)){
						if(null==infos[point].getQuestion_index()){
							questionIndex= 0;
						}else{
							questionIndex=infos[point].getQuestion_index();
						}
						Create_Exercise_suit_2Bean.questions question= infos[point].getQuestions()[questionIndex];
						questionNO = question.getCuotijiexi_questionNO()+"";
						title_text3.setText(questionNO);
					}

				}

				index =point;

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
	 * 继续练题的时候计算初始正确率
	 */
	private void go_on_setRight() {
		// TODO Auto-generated method stub
		for (int i = 0; i < infos.length; i++) {
			for (int j = 0; j < infos[i].getQuestions().length; j++) {
				Create_Exercise_suit_2Bean.questions question= infos[i].getQuestions()[j];
				String correct = "";
				for (int k = 0; k < question.getCorrect().length; k++) {
					correct = correct+question.getCorrect()[k];
				}
				if(correct.equals(question.getAnswer())){
					jisuan_rightsum();
				}

			}
		}
	}

	/**
	 *
	 * @param group
	 * @param group_index 即infos_index  判断是哪个材料题
	 * @param isShowCailiao
	 * @return
	 */
	private View setParent_ViewPagerItem(Create_Exercise_suit_2Bean.infos group,final int group_index,Boolean isShowCailiao){
		View view = View.inflate(this, R.layout.item_read_question_parent_viewpager, null);
		TextView top_title = (TextView) view.findViewById(R.id.top_title);
		TextView top_content = (TextView) view.findViewById(R.id.top_content);
		LinearLayout buttom_linear =  (LinearLayout) view.findViewById(R.id.buttom_linear);
		LinearLayout buttom_tuodong =  (LinearLayout) view.findViewById(R.id.buttom_tuodong);
		final Read_Question_Child_ViewPager viewpager_child=(Read_Question_Child_ViewPager) view.findViewById(R.id.viewpager_child);
		LinearLayout top_linear = (LinearLayout) view.findViewById(R.id.top_linear);
		LinearLayout linear_cailiao_top = (LinearLayout) view.findViewById(R.id.linear_cailiao_top);
		ScrollView scrollview = (ScrollView) view.findViewById(R.id.scrollview);
		ImageView tuodong_img = (ImageView) view.findViewById(R.id.tuodong_img);
		if(null !=application.imageBean && null!=application.imageBean.getHuakuai()){
			LoadImageViewUtil.resetImageSize(tuodong_img, Screen_width/5, 200, 55);
			ImageLoader.getInstance().displayImage(application.imageBean.getHuakuai(), tuodong_img);
		}else{
			ImageLoader.getInstance().displayImage(Huakuai, tuodong_img);
		}
		ImageView cailiao_image = (ImageView) view.findViewById(R.id.cailiao_image);
		//除了文字 剩下的图片和占位的高度   默认设置占位高度 30dp
		int imageview_height =DensityUtil.DipToPixels(Reading_QuestionsActivity.this, 30);
		if(null!=group.getInfo() &&null!=group.getInfo().getImg() ){
			int width = Screen_width-DensityUtil.DipToPixels(Reading_QuestionsActivity.this, 20);
			if(group.getInfo().getImg().getWidth()>width){
				imageview_height =imageview_height+ width*group.getInfo().getImg().getHeight()/group.getInfo().getImg().getWidth();
				ImageLoader.getInstance().displayImage(group.getInfo().getImg().getUrl(), cailiao_image);
			}else{
				imageview_height =imageview_height+ group.getInfo().getImg().getHeight();
				ImageLoader.getInstance().displayImage(group.getInfo().getImg().getUrl(), cailiao_image);
			}
			cailiao_image.setOnClickListener(new imageViewClick(group.getInfo().getImg().getUrl()));
		}

		//设置颜色
		top_title.setTextColor(Fontcolor_3);
		top_content.setTextColor(Fontcolor_3);

		if(isShowCailiao && null!=group.getInfo()){//是否显示材料
			//设置数据
			if(null==group.getInfo().getTitle()){
				top_title.setVisibility(View.GONE);
			}else if(group.getInfo().getTitle().trim().equals("") ||group.getInfo().getTitle().trim().equals("null") ){
				top_title.setVisibility(View.GONE);
			}else{
				top_title.setText(group.getInfo().getTitle());
			}
			if(null!=group.getInfo().getContent()){
				top_content.setText(group.getInfo().getContent());
			}
			//设置子ViewPager的高度
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, dip_45);
			buttom_linear.setLayoutParams(params);
			setAttr(scrollview,linear_cailiao_top,buttom_linear,params,buttom_tuodong,imageview_height);
		}else{
			scrollview.setVisibility(View.GONE);
			tuodong_img.setVisibility(View.GONE);
		}

		Create_Exercise_suit_2Bean.questions [] questions = group.getQuestions();
		List<View> list_childViews=new ArrayList<View>();
		for (int i = 0; i < questions.length; i++) {
			View view_child_item = setViewPager_youhua(viewholder, questions[i], group_index,i,viewpager_child);
			list_childViews.add(view_child_item);
		}
		chid_viewpagers[group_index]=viewpager_child;
		viewpager_child.setAdapter(new ViewPagerAdpater(list_childViews));
		if((type==2 ||type==3)&&group_index==this.infos_index){
			viewpager_child.setCurrentItem(question_index);
		}
		if(group_index<this.infos_index){//如果是之前的材料题  倒叙滑动
			viewpager_child.setCurrentItem(viewpager_child.getAdapter().getCount()-1);
		}
		viewpager_child.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int point) {
				// TODO Auto-generated method stub
				if((type==0||type==3)){
					if(group_index==index_sum-1&&point==viewpager_child.getAdapter().getCount()-1){
						mylinear.setIsCanScroll(true);
						setBottom_button_content(3, "查看总结");
					}else{
						mylinear.setIsCanScroll(false);
					}
					DuoxuanSubmit(group_index, point);
					setBottom_button(group_index, point);
				}else if(type==2){
					Create_Exercise_suit_2Bean.questions question=infos[group_index].getQuestions()[point];
					setTitleBG(question);
					//记录用户滑到几题
					infos[group_index].setQuanzujiexi_index(point);
				}
				Create_Exercise_suit_2Bean.questions question=infos[group_index].getQuestions()[point];
				questionNO = question.getCuotijiexi_questionNO()+"";
				title_text3.setText(questionNO);//设置题号
				setcollectImage(question.getIs_collect());
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
		return view;
	}

	private View setViewPager_youhua(viewholder viewholder,final Create_Exercise_suit_2Bean.questions questtionBean,int group_index,int question_index,ViewPager viewpager_child){
		View view = View.inflate(this, R.layout.item_answer, null);
		//初始化ID
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
		viewholder.temp_text = (TextView) view.findViewById(R.id.temp_text);
		viewholder.answer_text_jiexi_true = (TextView) view.findViewById(R.id.answer_text_jiexi_true);
		viewholder.answer_text_jiexi_false = (TextView) view.findViewById(R.id.answer_text_jiexi_false);
		viewholder.answer_text_person = (TextView) view.findViewById(R.id.answer_text_person);
		viewholder.answer_text_person_identity = (TextView) view.findViewById(R.id.answer_text_person_identity);
		viewholder.answer_text_bentijiexi = (TextView) view.findViewById(R.id.answer_text_bentijiexi);
		viewholder.answer_text_jiexi_content = (TextView) view.findViewById(R.id.answer_text_jiexi_content);
		viewholder.payRmb_vip = (TextView) view.findViewById(R.id.payRmb_vip);
		viewholder.payRmb_vip1 = (TextView) view.findViewById(R.id.payRmb_vip1);
		viewholder.answer_text_kaodian = (TextView) view.findViewById(R.id.answer_text_kaodian);
		viewholder.answer_person =  (ImageView) view.findViewById(R.id.answer_person);
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
		viewholder.linear_notes =(LinearLayout) view.findViewById(R.id.linear_notes);
		viewholder.linear_jiexi_content =(LinearLayout) view.findViewById(R.id.linear_jiexi_content);
		viewholder.answer_a  = (ImageView) view.findViewById(R.id.answer_a);
		viewholder.answer_b  = (ImageView) view.findViewById(R.id.answer_b);
		viewholder.answer_c  = (ImageView) view.findViewById(R.id.answer_c);
		viewholder.answer_d  = (ImageView) view.findViewById(R.id.answer_d);
		viewholder.answer_e  = (ImageView) view.findViewById(R.id.answer_e);
		viewholder.answer_f  = (ImageView) view.findViewById(R.id.answer_f);
		viewholder.answer_g  = (ImageView) view.findViewById(R.id.answer_g);
		viewholder.answer_true  = (ImageView) view.findViewById(R.id.answer_true);
		viewholder.answer_false  = (ImageView) view.findViewById(R.id.answer_false);
		viewholder.image_jiexi  = (ImageView) view.findViewById(R.id.image_jiexi);
		//纠错 收藏  笔记
		viewholder.image_Collection  = (ImageView) view.findViewById(R.id.image_Collection);
		viewholder.image_error  = (ImageView) view.findViewById(R.id.image_error);
		viewholder.text_note = (TextView) view.findViewById(R.id.text_note);
		viewholder.text_add_note = (TextView) view.findViewById(R.id.text_add_note);
		viewholder.text_Collection = (TextView) view.findViewById(R.id.text_Collection);
		viewholder.text_error = (TextView) view.findViewById(R.id.text_error);
		viewholder.text_look_all = (TextView) view.findViewById(R.id.text_look_all);
		viewholder.linear_Collection=(LinearLayout) view.findViewById(R.id.linear_Collection);
		viewholder.linear_error=(LinearLayout) view.findViewById(R.id.linear_error);
		viewholder.linear_answer_text=(LinearLayout) view.findViewById(R.id.linear_answer_text);
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
		viewholder.text_note.setTextColor(Fontcolor_3);
		viewholder.text_add_note.setTextColor(Bgcolor_2);
		viewholder.text_Collection.setTextColor(Fontcolor_3);
		viewholder.text_error.setTextColor(Fontcolor_3);
		viewholder.text_look_all.setTextColor(Fontcolor_7);

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
		viewholder.pay_hint2.setTextColor(Bgcolor_2);
		list_pay_hints.add(viewholder.pay_hint_linear);
		if(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status()==2&&!test_type.equals("qtlx")&&!test_type.equals("ctgg_ap")&&MyApplication.isShowVip){//会员快到期 给提示
			viewholder.pay_hint_linear.setVisibility(View.VISIBLE);
			viewholder.pay_hint1.setText(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status_name());
			viewholder.pay_hint2.setText(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_price());
			viewholder.pay_hint2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Reading_QuestionsActivity.this,PayVIPActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				}
			});
		}
		//设置数据
		viewholder.timu.setText(questtionBean.getContent());
		if(null !=questtionBean.getImg()  && null !=questtionBean.getImg().getUrl()){
			LoadImage image = new LoadImage(viewholder.imageView, questtionBean.getImg().getUrl(),Reading_QuestionsActivity.this);
			image.execute(questtionBean.getImg().getUrl());
			viewholder.imageView.setOnClickListener(new imageViewClick(questtionBean.getImg().getUrl()));
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
			Create_Exercise_suit_2Bean.opt opt = questtionBean.getOpt()[j];
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
		if(questtionBean.getAnalyze().getImg()!=null){
			String url = questtionBean.getAnalyze().getImg().getUrl();
			int viewWidth =Screen_width - DensityUtil.DipToPixels(this,40);
			int width = questtionBean.getAnalyze().getImg().getWidth();
			int height = questtionBean.getAnalyze().getImg().getHeight();
			if(!TextUtils.isEmpty(url)){
				viewholder.image_jiexi.setVisibility(View.VISIBLE);
				LoadImageViewUtil.resetImageSize(viewholder.image_jiexi,viewWidth,width,height);
				ImageLoader.getInstance().displayImage(url, viewholder.image_jiexi);
				viewholder.image_jiexi.setOnClickListener(new imageViewClick(url));
			}else{
				viewholder.image_jiexi.setVisibility(View.GONE);
			}
		}
		AnswerUtil.setWebView(viewholder.answer_text_jiexi_content, questtionBean.getAnalyze().getContent(), this, handler, dialog);
		//viewholder.answer_text_jiexi_content.setText(questtionBean.getAnalyze().getContent());

		//如果用户不是会员  则看不到解析   1 2 表示用户还是会员
		list_pay_linears.add(viewholder.linear_vip);
		list_content_textviews.add(viewholder.linear_jiexi_content);
		if(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status()==1 || application.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status()==2||test_type.equals("qtlx")||test_type.equals("ctgg_ap")||MyApplication.isShowVip==false){

		}else{
			viewholder.linear_vip.setVisibility(View.VISIBLE);
			viewholder.linear_jiexi_content.setVisibility(View.GONE);
			viewholder.payRmb_vip1.setText(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status_name());
			viewholder.payRmb_vip.setText(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_price());
			viewholder.payRmb_vip.setTextColor(Bgcolor_2);
			//viewholder.answer_text_jiexi_content.setVisibility(View.GONE);
			viewholder.payRmb_vip.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Reading_QuestionsActivity.this,PayVIPActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				}
			});
		}

		//添加考点
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(15,15,15,0);//4个参数按顺序分别是左上右下

		LinearLayout liner = new LinearLayout(this);
		viewholder.linear_buttom_kaodian.addView(liner);
		int width_sum = 0;
		for (int j = 0; j < questtionBean.getPoints().length; j++) {
			Button button = new Button(this);
			button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			button.setText(questtionBean.getPoints()[j].getN());
			int dip_15 = DensityUtil.DipToPixels(Reading_QuestionsActivity.this, 15);
			button.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Bgcolor_2,Bgcolor_1, 1, DensityUtil.DipToPixels(Reading_QuestionsActivity.this, 10)));
			button.setPadding(dip_15, dip_15/3, dip_15, dip_15/3);
			button.setLayoutParams(layoutParams);
			button.setTextColor(Fontcolor_1);
			//点击考点 显示对话框
			button.setOnClickListener(new Kaodian_clcik(questtionBean.getPoints()[j].getId()+""));
			int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			button.measure(w, h);
			int height =button.getMeasuredHeight();
			int width =button.getMeasuredWidth();
			if((width_sum +width+30) < view_width){
				liner.addView(button);
				width_sum = width_sum+width+30;
			}else{
				width_sum=width;
				liner = new LinearLayout(this);
				viewholder.linear_buttom_kaodian.addView(liner);
				liner.addView(button);
			}
		}

		//设置事件
		LinearLayout[] linear_arr = new LinearLayout[]{viewholder.linear_answer_a,viewholder.linear_answer_b,viewholder.linear_answer_c,viewholder.linear_answer_d,viewholder.linear_answer_e,viewholder.linear_answer_f,viewholder.linear_answer_g,viewholder.linear_answer_true,viewholder.linear_answer_false,viewholder.linear_answer_no};
		//继续练习 如果用户已经答过了 那么显示解析 并且设置不能选
		/*	if(null !=questtionBean.getAnswer() &&!questtionBean.getAnswer().equals("null")&&!questtionBean.getAnswer().equals("")){
				setAnswer(linear_arr, questtionBean, viewholder);
			}*/
		if((type==0||type==3)){
			Question_click question_click = new Question_click(questtionBean.getCorrect(),create_Exercise_suit_2Bean.getId(),questtionBean.getNo(),questtionBean.getType(),viewholder.answer_text_jiexi_false,viewholder.analysis,viewholder.answer_a,viewholder.answer_b,viewholder.answer_c,viewholder.answer_d,viewholder.answer_e,viewholder.answer_f,viewholder.answer_g,viewholder.answer_true,viewholder.answer_false,linear_arr,group_index,question_index,viewpager_child,viewholder.temp_text);
			list_click[questtionBean.getCuotijiexi_questionNO()-1]=question_click;
			viewholder.linear_answer_a.setOnClickListener(question_click);
			viewholder.linear_answer_b.setOnClickListener(question_click);
			viewholder.linear_answer_c.setOnClickListener(question_click);
			viewholder.linear_answer_d.setOnClickListener(question_click);
			viewholder.linear_answer_e.setOnClickListener(question_click);
			viewholder.linear_answer_f.setOnClickListener(question_click);
			viewholder.linear_answer_g.setOnClickListener(question_click);
			viewholder.linear_answer_true.setOnClickListener(question_click);
			viewholder.linear_answer_false.setOnClickListener(question_click);
			viewholder.linear_answer_no.setOnClickListener(question_click);
		}
		//设置头像
		String headUrl =questtionBean.getAnalyze().getBy().getIcon();
		if(null!=headUrl && !headUrl.equals("null")&& !headUrl.equals("")){
			ImageLoader.getInstance().displayImage(headUrl, viewholder.answer_person);
			//LoadImageViewUtil.setImageBitmap(viewholder.answer_person,headUrl );
		}

		//添加点击头像事件
		cn.net.dingwei.util.AnswerUtil.HeadClick headClick = new AnswerUtil.HeadClick(questtionBean.getAnalyze().getBy().getUrl(), this);
		viewholder.linear_head.setOnClickListener(headClick);
		if(questtionBean.getType()==4 || type==4){//阅读或者笔记详情  不要正确答案那行
			viewholder.linear_answer_text.setVisibility(View.GONE);
			viewholder.linear_answer_no.setVisibility(View.GONE);
		}
		//设置收藏 和纠错
		collection_textview[questtionBean.getCuotijiexi_questionNO()-1]=viewholder.text_Collection;
		collection_imageview[questtionBean.getCuotijiexi_questionNO()-1]=viewholder.image_Collection;

		ImageLoader.getInstance().displayImage(jiucuo, viewholder.image_error, application.getOptions());
		if(questtionBean.getIs_collect()==1){
			viewholder.text_Collection.setText(placeholder_textBean.getSc_ysc());
			ImageLoader.getInstance().displayImage(shoucang_1, viewholder.image_Collection, application.getOptions());
		}else if(questtionBean.getIs_collect()==0){
			viewholder.text_Collection.setText(placeholder_textBean.getSc_wsc());
			ImageLoader.getInstance().displayImage(shoucang_0, viewholder.image_Collection, application.getOptions());
		}
		viewholder.text_error.setText(placeholder_textBean.getJc_bt());
		viewholder.text_look_all.setText(placeholder_textBean.getBj_ckqb());
		//笔记部分添加点击事件
		//设置笔记
		TextView[] temp_notes = new TextView[3];
		temp_notes=SetNotes(questtionBean.getNotes(), viewholder.linear_notes,viewholder.text_look_all,viewholder.text_add_note);
		if(null==temp_notes){

		}else{

		}
		NoteClick noteClick = new NoteClick(questtionBean,temp_notes,viewholder.text_Collection,viewholder.image_Collection);
		viewholder.linear_Collection.setOnClickListener(noteClick);
		viewholder.linear_error.setOnClickListener(noteClick);
		viewholder.text_look_all.setOnClickListener(noteClick);
		viewholder.text_add_note.setOnClickListener(noteClick);

		//当是创建或者继续练习的时候
		if(type==0||type==3){
			viewholder.temp_text.setVisibility(View.VISIBLE);
		}
		//是错误解析 或整组解析的时候 设置答案
		if(type!=0){
			//if(type==3 && group_index<=infos_index && questtionBean.getNo()<question_no){
			if(type==3  && questtionBean.getNo()<question_no){
				setAnswer(linear_arr, questtionBean, viewholder);
			}else if(type!=3){
				setAnswer(linear_arr, questtionBean, viewholder);
			}
		}
		return view;
	}
	/**
	 * 设置笔记
	 */
	private  TextView[] SetNotes(Create_Exercise_suit_2Bean.notes notes,LinearLayout linear_notes,TextView text_look_all,TextView text_add_note){

		TextView[] temp_notes = new TextView[4];
		temp_notes[3]=text_look_all; //把查看全部笔记 添加进去
		if(null!=notes){
			Create_Exercise_suit_2Bean.note_bean[] me = notes.getMe();
			Create_Exercise_suit_2Bean.note_bean[] other = notes.getOther();
			//自己的笔记
			if(null!=me){
				View MeView = View.inflate(Reading_QuestionsActivity.this, R.layout.item_question_jianda, null);
				ImageView head_icon =  (ImageView) MeView.findViewById(R.id.head_icon);
				TextView text_nick_name = (TextView)MeView.findViewById(R.id.text_nick_name);
				TextView text_time = (TextView)MeView.findViewById(R.id.text_time);
				TextView text_content = (TextView)MeView.findViewById(R.id.text_content);

				temp_notes[0]=text_nick_name;//把Textview控件装在数组 
				temp_notes[1]=text_time;
				temp_notes[2]=text_content;

				text_nick_name.setTextColor(Bgcolor_2);
				text_time.setTextColor(Fontcolor_7);
				text_content.setTextColor(Fontcolor_3);
				if(null!=me[0].getIcon()&&!"null".equals(me[0].getIcon())){
					ImageLoader.getInstance().displayImage(me[0].getIcon(), head_icon);
				}else{
					head_icon.setImageResource(R.drawable.answer_person);
				}
				text_nick_name.setText(me[0].getAuthor_name());
				text_time.setText(me[0].getTime_text());
				text_content.setText(me[0].getContent());
				if(me[0].getTime_text().trim().length()<=0){//时间是null  没有个人笔记
					text_content.setTextColor(Fontcolor_7);
					text_add_note.setText("添加笔记");
				}else{
					text_add_note.setText("编辑笔记");
				}
				linear_notes.addView(MeView);
			}
			//其他的笔记
			if(null!=other&&other.length>0){
				text_look_all.setVisibility(View.VISIBLE);
				for (int i = 0; i < other.length; i++) {
					View otherView = View.inflate(Reading_QuestionsActivity.this, R.layout.item_question_jianda_right_head, null);
					ImageView head_icon =  (ImageView) otherView.findViewById(R.id.head_icon);
					TextView text_nick_name = (TextView)otherView.findViewById(R.id.text_nick_name);
					TextView text_time = (TextView)otherView.findViewById(R.id.text_time);
					TextView text_content = (TextView)otherView.findViewById(R.id.text_content);
					text_nick_name.setTextColor(Bgcolor_2);
					text_time.setTextColor(Fontcolor_7);
					text_content.setTextColor(Fontcolor_3);
					if(null!=other[i].getIcon()&&!"null".equals(other[i].getIcon())){
						ImageLoader.getInstance().displayImage(other[i].getIcon(), head_icon);
					}else{
						head_icon.setImageResource(R.drawable.answer_person);
					}
					text_nick_name.setText(other[i].getAuthor_name());
					text_time.setText(other[i].getTime_text());
					text_content.setText(other[i].getContent());
					linear_notes.addView(otherView);
				}
			}else{
				text_look_all.setVisibility(View.GONE);
			}
		}
		return temp_notes;
	}
	//设置答案.
	private void setAnswer(LinearLayout[] linear_arr,Create_Exercise_suit_2Bean.questions questtionBean,viewholder viewholder){
		for (int j = 0; j < linear_arr.length; j++) {
			linear_arr[j].setEnabled(false);
		}
		viewholder.analysis.setVisibility(View.VISIBLE);
		viewholder.temp_text.setVisibility(View.GONE);
		if(null==questtionBean.getAnswer()){
			viewholder.answer_text_jiexi_false.setText("未做答");//如果是空指针  设置答案为"null"
			questtionBean.setAnswer("null");
		}else if(questtionBean.getAnswer().equals("null")){
			viewholder.answer_text_jiexi_false.setText("未做答");
		}else if(questtionBean.getAnswer().equals("f")){
			viewholder.answer_text_jiexi_false.setText("×");
		}else if(questtionBean.getAnswer().equals("t")){
			viewholder.answer_text_jiexi_false.setText("√");
		}else if(questtionBean.getAnswer().equals("?")){
			viewholder.answer_text_jiexi_false.setText("不会");
		}else{
			String st = "";
			for (int j = 0; j < questtionBean.getAnswer().length(); j++) {
				if(j==questtionBean.getAnswer().length()-1){
					st =st+ questtionBean.getAnswer().substring(j, j+1);
				}else{
					st =st+ questtionBean.getAnswer().substring(j, j+1)+"、";
				}
			}
			viewholder.answer_text_jiexi_false.setText(st);
		}


		String st = ""; //正确答案
		for (int j = 0; j < questtionBean.getCorrect().length; j++) {
			st =st+questtionBean.getCorrect()[j];

		}
		//设置选项
		ImageView [] imageviews = new ImageView[]{viewholder.answer_a,viewholder.answer_b,viewholder.answer_c,viewholder.answer_d,viewholder.answer_e,viewholder.answer_f,viewholder.answer_g,viewholder.answer_true,viewholder.answer_false,};
		if(questtionBean.getType()==2){//多选
			if(questtionBean.getAnswer().equals("null")||questtionBean.getAnswer().equals("?") || questtionBean.getAnswer().equals(st)){//没答 或不会 或者答案正确
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
				String answer = questtionBean.getAnswer();

				//String []duoxuan_answer = questtionBean.getDuoxuan_answer();
				if(answer.contains("A") && st.contains("A")){
					imageviews[0].setImageResource(image_duoxuan_tAndf[0]);
				}else if(st.contains("A")&& !answer.contains("A") ){
					imageviews[0].setImageResource(image_duoxuan_true[0]);
				}else if(answer.contains("A") && !st.contains("A")){
					imageviews[0].setImageResource(image_duoxuan_false[0]);
				} else{
				}

				if(answer.contains("B") && st.contains("B")){
					imageviews[1].setImageResource(image_duoxuan_tAndf[1]);
				}else if(st.contains("B")&& !answer.contains("B") ){
					imageviews[1].setImageResource(image_duoxuan_true[1]);
				}else if(answer.contains("B") && !st.contains("B")){
					imageviews[1].setImageResource(image_duoxuan_false[1]);
				}

				if(answer.contains("C") && st.contains("C")){
					imageviews[2].setImageResource(image_duoxuan_tAndf[2]);
				}else if(st.contains("C")&& !answer.contains("C") ){
					imageviews[2].setImageResource(image_duoxuan_true[2]);
				}else if(answer.contains("C") && !st.contains("C")){
					imageviews[2].setImageResource(image_duoxuan_false[2]);
				}

				if(answer.contains("D") && st.contains("D")){
					imageviews[3].setImageResource(image_duoxuan_tAndf[3]);
				}else if(st.contains("D")&& !answer.contains("D") ){
					imageviews[3].setImageResource(image_duoxuan_true[3]);
				}else if(answer.contains("D") && !st.contains("D")){
					imageviews[3].setImageResource(image_duoxuan_false[3]);
				}

				if(answer.contains("E") && st.contains("E")){
					imageviews[4].setImageResource(image_duoxuan_tAndf[4]);
				}else if(st.contains("E")&& !answer.contains("E") ){
					imageviews[4].setImageResource(image_duoxuan_true[4]);
				}else if(answer.contains("E") && !st.contains("E")){
					imageviews[4].setImageResource(image_duoxuan_false[4]);
				}

				if(answer.contains("F") && st.contains("F")){
					imageviews[5].setImageResource(image_duoxuan_tAndf[5]);
				}else if(st.contains("F")&& !answer.contains("F") ){
					imageviews[5].setImageResource(image_duoxuan_true[5]);
				}else if(answer.contains("F") && !st.contains("F")){
					imageviews[5].setImageResource(image_duoxuan_false[5]);
				}

				else if(answer.contains("G") && st.contains("G")){
					imageviews[6].setImageResource(image_duoxuan_tAndf[6]);
				}else if(st.contains("G")&& !answer.contains("G") ){
					imageviews[6].setImageResource(image_duoxuan_true[6]);
				}else if(answer.contains("G") && !st.contains("G")){
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

			//设置用户答错的答案
			String answer=questtionBean.getAnswer();
			if(!answer.equals("?")&&!answer.equals("null")&&!answer.equals(st) ){ //答案错误时显示错误答案
				if(answer.equals("A")){ //
					imageviews[0].setImageResource(image_false[0]);
				}else if(answer.equals("B")){
					imageviews[1].setImageResource(image_false[1]);
				}else if(answer.equals("C")){
					imageviews[2].setImageResource(image_false[2]);
				}else if(answer.equals("D")){
					imageviews[3].setImageResource(image_false[3]);
				}else if(answer.equals("E")){
					imageviews[4].setImageResource(image_false[4]);
				}else if(answer.equals("F")){
					imageviews[5].setImageResource(image_false[5]);
				}else if(answer.equals("G")){
					imageviews[6].setImageResource(image_false[6]);
				}else if(answer.equals("t")){
					imageviews[7].setImageResource(image_false[7]);
				}else if(answer.equals("f")){
					imageviews[8].setImageResource(image_false[8]);
				}
			}
		}
	}

	//笔记 这快的点击事件
	class NoteClick implements OnClickListener{
		private Create_Exercise_suit_2Bean.questions questtionBean;
		private String note_Content="";
		private TextView[] temp_notes;
		private TextView text_Collection;
		private ImageView image_Collection;
		public NoteClick(Create_Exercise_suit_2Bean.questions questtionBean,TextView[] temp_notes,TextView text_Collection,ImageView image_Collection) {
			// TODO Auto-generated constructor stub
			this.questtionBean = questtionBean;
			this.text_Collection = text_Collection;
			this.image_Collection = image_Collection;
			this.temp_notes = temp_notes;
			if(null==temp_notes){
			}else{
			}
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=null;
			int questionIndex;
			note_bean me;
			switch (v.getId()) {
				case R.id.text_add_note://+ 写笔记
					if(ISCanDo()){
						intent = new Intent(Reading_QuestionsActivity.this, WriteNoteAndErrorActivity.class);
						intent.putExtra("flg", 1);
						questionIndex=0;
						if(null==infos[index].getQuestion_index()){
							questionIndex= 0;
						}else{
							questionIndex=infos[index].getQuestion_index();
						}
						me = infos[index].getQuestions()[questionIndex].getNotes().getMe()[0];
						if(null!=me.getContent()&&!me.getTime_text().equals("")){
							note_Content = questtionBean.getNotes().getMe()[0].getContent();
						}else{
							note_Content="";
						}
						intent.putExtra("note_Content", note_Content);
						intent.putExtra("suitid", suitid);
						intent.putExtra("qid", questtionBean.getQid()+"");
						startActivityForResult(intent, 2);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


						note_texts=temp_notes;
						now_text_add_note = (TextView) v;
					}
					break;
				case R.id.text_look_all://查看全部笔记
					questionIndex=0;
					if(null==infos[index].getQuestion_index()){
						questionIndex= 0;
					}else{
						questionIndex=infos[index].getQuestion_index();
					}
					me = infos[index].getQuestions()[questionIndex].getNotes().getMe()[0];

					intent = new Intent(Reading_QuestionsActivity.this, NoteActivity.class);
					intent.putExtra("Author_name", me.getAuthor_name());
					intent.putExtra("Content", me.getContent());
					intent.putExtra("Time_text", me.getTime_text());
					intent.putExtra("HeadUrl", me.getIcon());
					intent.putExtra("qid", questtionBean.getQid()+"");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					break;
				case R.id.linear_Collection://收藏
					if(ISCanDo()){
						viewholder.text_Collection.setText(placeholder_textBean.getSc_wsc());
						int is_collect=infos[index].getQuestions()[Sup.getQUstionIndex(infos,index)].getIs_collect();
						if(is_collect==0){//为收藏
							Postcollect(questtionBean.getQid()+"", 1,text_Collection,image_Collection);
						}else if(is_collect==1){//已收藏 变为收藏
							Postcollect(questtionBean.getQid()+"", 0,text_Collection,image_Collection);
						}
					}
					break;
				case R.id.linear_error://我要纠错
					intent = new Intent(Reading_QuestionsActivity.this, WriteNoteAndErrorActivity.class);
					intent.putExtra("flg", 2);
					intent.putExtra("qid", questtionBean.getQid()+"");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					break;
				default:
					break;
			}
		}

	}
	/**
	 * 判断用户是否可以操作收藏或者写笔记
	 * @return true 是注册用户 可以操作  false 不可操作
	 */
	private Boolean ISCanDo(){
		//游客不让写
		if(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getRegistered()==true){
			return true;
		}else{
			Intent intent = new Intent(Reading_QuestionsActivity.this, Rest_passwordActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("flg", "1");
			intent.putExtra("isNeddSinIn", "0");
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			return false;
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
				View view = null;
				if(infos[position].getInfo_status()==1){//材料题
					view= setParent_ViewPagerItem(infos[position],position,true);
				}else{									//非材料题
					view= setParent_ViewPagerItem(infos[position],position,false);
				}

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
	class viewholder{
		TextView timu,answer_text_a,answer_text_b,answer_text_c,answer_text_d,answer_text_e,answer_text_f,answer_text_g,answer_text_true,answer_text_false,answer_text_no,answer_text_jiexi_true,answer_text_jiexi_false,answer_text_person,answer_text_person_identity,answer_text_bentijiexi,answer_text_jiexi_content,answer_text_kaodian,pay_hint1,pay_hint2,temp_text;
		//购买会员显示
		TextView payRmb_vip;
		TextView payRmb_vip1;
		LinearLayout linear_vip,linear_jiexi_content;
		LinearLayout pay_hint_linear,linear_notes;

		ImageView imageView,answer_a,answer_b,answer_c,answer_d,answer_e,answer_f,answer_g,answer_true,answer_false,answer_person;
		LinearLayout linear_answer_a,linear_answer_b,linear_answer_c,linear_answer_d,linear_answer_e,linear_answer_f,linear_answer_g,linear_answer_true,linear_answer_false,linear_answer_no,linear_buttom_kaodian,analysis,linear_head;
		//	ElasticScrollView_answer myScrollview;
		//笔记部分
		ImageView image_Collection,image_error,image_jiexi;
		TextView text_note,text_add_note,text_look_all,text_Collection,text_error;
		LinearLayout linear_Collection,linear_error,linear_answer_text;
		WebView webView;
	}
	/**
	 * 设置Touch事件
	 * @param
	 *
	 */
	private void setOnToutch(final LinearLayout linear_parent,LinearLayout buttom_tuodong,final LayoutParams params,final int min_height){
		// TODO Auto-generated method stub
		buttom_tuodong.setOnTouchListener(new OnTouchListener() {
			private float downY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final float y = event.getY();
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downY = event.getY();
						break;
					case MotionEvent.ACTION_MOVE:
						float  deltaY= downY-event.getY();
						int height =(int) (params.height+deltaY);
						if(height<min_height){
							params.height = min_height;
						}/*else if(height<dip_45){
			            		params.height = dip_45;
			            	}*/else if(height>(Screen_height*0.8)){
							params.height = (int) (Screen_height*0.8);
						}else{
							params.height = height;
						}
						linear_parent.setLayoutParams(params);
						if(params.height<dip_45){
							setBottom_button_anim(false);
							infos[index].setIsShowBottom_button(false);
						}else{
							setBottom_button_anim(true);
							infos[index].setIsShowBottom_button(true);
						}
						break;
					case MotionEvent.ACTION_UP:
						break;
				}
				return true;
			}
		});

	}

	//Viewpager里面的点击事件
	class Question_click implements OnClickListener{
		private int type;
		private int suit_id;
		private int question_no;
		private String[] correct;
		private LinearLayout analysis;
		private TextView  answer_text_jiexi_false;
		private ImageView answer_a,answer_b,answer_c,answer_d,answer_e,answer_f,answer_g,answer_true,answer_false;
		private LinearLayout [] linear_array;
		public String [] duoxuan_answer=new String[]{"","","","","","",""};
		private Boolean [] duoxuan_answer_boolean=new Boolean[]{false,false,false,false,false,false,false};
		//问题的位置
		private int group_index=0;
		private int question_index=0;
		private ViewPager viewPager_child;
		private TextView temp_text;
		public Question_click(String[] correct,int suit_id,int question_no,int type,TextView answer_text_jiexi_false,LinearLayout analysis,ImageView answer_a,ImageView answer_b,ImageView answer_c,ImageView answer_d,ImageView answer_e,ImageView answer_f,ImageView answer_g,ImageView answer_true,ImageView answer_false,LinearLayout[] linear_array,
							  int group_index,int question_index,ViewPager viewPager_child,TextView temp_text) {
			this.answer_a = answer_a;
			this.answer_b = answer_b;
			this.answer_c = answer_c;
			this.answer_d = answer_d;
			this.answer_e = answer_e;
			this.answer_f = answer_f;
			this.answer_g = answer_g;
			this.answer_true = answer_true;
			this.answer_false = answer_false;
			this.analysis = analysis;
			this.type=type;
			this.suit_id = suit_id;
			this.question_no = question_no;
			this.correct=correct;
			this.answer_text_jiexi_false = answer_text_jiexi_false;
			this.linear_array = linear_array;
			this.group_index =group_index;
			this.question_index = question_index;
			this.viewPager_child = viewPager_child;
			this.temp_text=temp_text;
		}
		@Override
		public void onClick(View v) {
			if (type == 2) { //如果是多选
				switch (v.getId()) {
					case R.id.linear_answer_a:
						setDuoxuanImageView(0, "A", answer_a);
						break;
					case R.id.linear_answer_b:
						setDuoxuanImageView(1, "B", answer_b);
						break;
					case R.id.linear_answer_c:
						setDuoxuanImageView(2, "C", answer_c);
						break;
					case R.id.linear_answer_d:
						setDuoxuanImageView(3, "D", answer_d);
						break;
					case R.id.linear_answer_e:
						setDuoxuanImageView(4, "E", answer_e);
						break;
					case R.id.linear_answer_f:
						setDuoxuanImageView(5, "F", answer_f);
						break;
					case R.id.linear_answer_g:
						setDuoxuanImageView(6, "G", answer_g);
						break;
					case R.id.linear_answer_no:
						setAnsWer_duoxuan("?",false);
						break;
					default:
						break;
				}
			} else { //单选或者判断
				switch (v.getId()) {
					case R.id.linear_answer_a:
						setAnsWer("A", 0, answer_a);
						break;
					case R.id.linear_answer_b:
						setAnsWer("B", 1, answer_b);
						break;
					case R.id.linear_answer_c:
						setAnsWer("C", 2, answer_c);
						break;
					case R.id.linear_answer_d:
						setAnsWer("D", 3, answer_d);
						break;
					case R.id.linear_answer_e:
						setAnsWer("E", 4, answer_e);
						break;
					case R.id.linear_answer_f:
						setAnsWer("F", 5, answer_f);
						break;
					case R.id.linear_answer_g:
						setAnsWer("F", 6, answer_g);
						break;
					case R.id.linear_answer_true:
						setAnsWer("t", 7, answer_true);
						break;
					case R.id.linear_answer_false:
						setAnsWer("f", 8, answer_false);
						break;
					case R.id.linear_answer_no:
						setAnsWer("?", 9, null);
						break;
					default:
						break;
				}
			}
		}
		public void setButtom_btn(){
			if(type ==1 || type==3){ //单选  判断
				setAnsWer("?", 0, null);
			}else{ //多选
				setAnsWer_duoxuan("?",false);
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
			String st= "";
			for (int j = 0; j <duoxuan_answer.length; j++) {
				if(!duoxuan_answer[j].equals("")){
					st=st+duoxuan_answer[j];
				}
			}
			String duoxuan_text="";
			for (int j = 0; j < st.length(); j++) {
				if(j==st.length()-1){
					duoxuan_text =duoxuan_text+ st.substring(j, j+1);
				}else{
					duoxuan_text =duoxuan_text+ st.substring(j, j+1)+"、";
				}
			}
			infos[group_index].getQuestions()[question_index].setDuoxuan(duoxuan_text);
		}
		public void setAnsWer_duoxuan(String answer,Boolean isNext){
			//create_Exercise_suit_2Bean.getGroups()[group_index].getQuestions()[question_index].setAnswer(answer);
			infos[group_index].getQuestions()[question_index].setAnswer(answer);

			if(index==index_sum-1&&(infos[group_index].getQuestions().length-1)==question_index){
				setBottom_button_content(3, "查看小组总结");
			}else{
				setBottom_button_content(2,"下一题");
			}

			String duoxuan="";//用户答案  有,号的
			String st = ""; //正确答案
			String st2="";//显示出来的答案  加了个逗号间隔
			for (int i = 0; i < correct.length; i++) {
				st = st+correct[i];
				if(i==correct.length-1){
					st2=st2+correct[i];
				}else{
					st2=st2+correct[i]+"、";
				}

			}

			for (int j = 0; j < answer.length(); j++) {
				if(j==answer.length()-1){
					duoxuan =duoxuan+ answer.substring(j, j+1);
				}else{
					duoxuan =duoxuan+ answer.substring(j, j+1)+"、";
				}
			}
			if(answer.equals("?")){
				answer_text_jiexi_false.setText("不会");
				analysis.setVisibility(View.VISIBLE);
				temp_text.setVisibility(View.GONE);
				//初始A--G
				answer_a.setImageResource(image_duoxuan_default[0]);
				answer_b.setImageResource(image_duoxuan_default[1]);
				answer_c.setImageResource(image_duoxuan_default[2]);
				answer_d.setImageResource(image_duoxuan_default[3]);
				answer_e.setImageResource(image_duoxuan_default[4]);
				answer_f.setImageResource(image_duoxuan_default[5]);
				answer_g.setImageResource(image_duoxuan_default[6]);

				for (int i = 0; i < correct.length; i++) {
					if(correct[i].equals("A")){
						answer_a.setImageResource(image_duoxuan_true[0]);
					}else if(correct[i].equals("B")){
						answer_b.setImageResource(image_duoxuan_true[1]);
					}else if(correct[i].equals("C")){
						answer_c.setImageResource(image_duoxuan_true[2]);
					}else if(correct[i].equals("D")){
						answer_d.setImageResource(image_duoxuan_true[3]);
					}else if(correct[i].equals("E")){
						answer_e.setImageResource(image_duoxuan_true[4]);
					}else if(correct[i].equals("F")){
						answer_f.setImageResource(image_duoxuan_true[5]);
					}else if(correct[i].equals("G")){
						answer_g.setImageResource(image_duoxuan_true[6]);
					}
				}

			}else if(st.equals(answer)){
				jisuan_rightsum();
				answer_text_jiexi_false.setText(st2);
				analysis.setVisibility(View.VISIBLE);
				temp_text.setVisibility(View.GONE);
				if(isNext==true){
					if(infos[group_index].getQuestions().length!=1 &&(infos[group_index].getQuestions().length-1)>question_index){
						viewPager_child.setCurrentItem(question_index+1);
					} /*else if(index<index_sum-1 &&(create_Exercise_suit_2Bean.getGroups()[group_index].getQuestions().length-1)==question_index){
								 setBottom_button_content(3, "查看小组总结");
							 }*/else if(index<index_sum-1){
						viewpager_parent.setCurrentItem(index+1);
					}else if(index==index_sum-1){ //答案正确 并且是最后一个
						mylinear.snapToScreen_right();
					}

				}

			}else { //答案错误  可能有一部分选对的
				answer_text_jiexi_false.setText(duoxuan);
				analysis.setVisibility(View.VISIBLE);
				temp_text.setVisibility(View.GONE);
				//如果选了A  答案也有A 显示半对
				if(duoxuan_answer[0].equals("A") && st.contains("A")){
					answer_a.setImageResource(image_duoxuan_tAndf[0]);
				}else if(st.contains("A")&& !duoxuan_answer[0].equals("A") ){
					answer_a.setImageResource(image_duoxuan_true[0]);
				}else if(duoxuan_answer[0].equals("A") && !st.contains("A")){
					answer_a.setImageResource(image_duoxuan_false[0]);
				} else{
				}

				if(duoxuan_answer[1].equals("B") && st.contains("B")){
					answer_b.setImageResource(image_duoxuan_tAndf[1]);
				}else if(st.contains("B")&& !duoxuan_answer[1].equals("B") ){
					answer_b.setImageResource(image_duoxuan_true[1]);
				}else if(duoxuan_answer[1].equals("B") && !st.contains("B")){
					answer_b.setImageResource(image_duoxuan_false[1]);
				}

				if(duoxuan_answer[2].equals("C") && st.contains(duoxuan_answer[2])){
					answer_c.setImageResource(image_duoxuan_tAndf[2]);
				}else if(st.contains("C")&& !duoxuan_answer[2].equals("C") ){
					answer_c.setImageResource(image_duoxuan_true[2]);
				}else if(duoxuan_answer[2].equals("C") && !st.contains("C")){
					answer_c.setImageResource(image_duoxuan_false[2]);
				}

				if(duoxuan_answer[3].equals("D") && st.contains(duoxuan_answer[3])){
					answer_d.setImageResource(image_duoxuan_tAndf[3]);
				}else if(st.contains("D")&& !duoxuan_answer[3].equals("D") ){
					answer_d.setImageResource(image_duoxuan_true[3]);
				}else if(duoxuan_answer[3].equals("D") && !st.contains("D")){
					answer_d.setImageResource(image_duoxuan_false[3]);
				}

				if(duoxuan_answer[4].equals("E") && st.contains(duoxuan_answer[4])){
					answer_e.setImageResource(image_duoxuan_tAndf[4]);
				}else if(st.contains("E")&& !duoxuan_answer[4].equals("E") ){
					answer_e.setImageResource(image_duoxuan_true[4]);
				}else if(duoxuan_answer[4].equals("E") && !st.contains("E")){
					answer_e.setImageResource(image_duoxuan_false[4]);
				}

				if(duoxuan_answer[5].equals("F") && st.contains(duoxuan_answer[5])){
					answer_f.setImageResource(image_duoxuan_tAndf[5]);
				}else if(st.contains("F")&& !duoxuan_answer[5].equals("F") ){
					answer_f.setImageResource(image_duoxuan_true[5]);
				}else if(duoxuan_answer[5].equals("F") && !st.contains("F")){
					answer_f.setImageResource(image_duoxuan_false[5]);
				}

				else if(duoxuan_answer[6].equals("G") && st.contains(duoxuan_answer[6])){
					answer_g.setImageResource(image_duoxuan_tAndf[6]);
				}else if(st.contains("G")&& !duoxuan_answer[6].equals("G") ){
					answer_g.setImageResource(image_duoxuan_true[6]);
				}else if(duoxuan_answer[6].equals("G") && !st.contains("G")){
					answer_g.setImageResource(image_duoxuan_false[6]);
				}
			}

			for (int i = 0; i <linear_array.length; i++) {
				linear_array[i].setEnabled(false);
			}
			if(duoxuan.equals("")){
				UploadingAnswer("?", "1");
			}else{
				UploadingAnswer(duoxuan, "1");
			}

		}
		/**
		 * 单选  判断
		 * 答案 判断是否正确
		 */
		public void setAnsWer(String answer,int image_index,ImageView imageView){
			infos[group_index].getQuestions()[question_index].setAnswer(answer); //设置答案
			if(index==index_sum-1&&(infos[group_index].getQuestions().length-1)==question_index){
				setBottom_button_content(3, "查看小组总结");
			}else{
				setBottom_button_content(2,"下一题");
			}
			if(answer.equals("t")){
				answer_text_jiexi_false.setText("√");
			}else if(answer.equals("f")){
				answer_text_jiexi_false.setText("×");
			}else if(answer.equals("?")){
						/*if(infos[group_index].getQuestions()[question_index].getType()==4){//简答题 不会
							answer_false.setImageResource(image_false[8]);
							}*/
				answer_text_jiexi_false.setText("不会");


			}else{
				answer_text_jiexi_false.setText(answer);
			}
			String st = "";
			for (int i = 0; i < correct.length; i++) {
				st = st+correct[i];
			}
			if(st.equals(answer)){ //答案正确
				jisuan_rightsum();
				analysis.setVisibility(View.VISIBLE);
				temp_text.setVisibility(View.GONE);
				if(null !=imageView){
					imageView.setImageResource(image_true[image_index]);
				}
				if(type!=4){
					if(infos[group_index].getQuestions().length!=1 &&(infos[group_index].getQuestions().length-1)>question_index){
						viewPager_child.setCurrentItem(question_index+1);
					} else if(index<index_sum-1){
						viewpager_parent.setCurrentItem(index+1);
					}else if(index==index_sum-1){ //答案正确 并且是最后一个
						mylinear.snapToScreen_right();
					}
				}
			}else{//答案错误
				analysis.setVisibility(View.VISIBLE);
				temp_text.setVisibility(View.GONE);
				if(null !=imageView){
					imageView.setImageResource(image_false[image_index]);
				}
				for (int i = 0; i < correct.length; i++) {
					if(correct[i].equals("A")){
						answer_a.setImageResource(image_true[0]);
					}else if(correct[i].equals("B")){
						answer_b.setImageResource(image_true[1]);
					}else if(correct[i].equals("C")){
						answer_c.setImageResource(image_true[2]);
					}else if(correct[i].equals("D")){
						answer_d.setImageResource(image_true[3]);
					}else if(correct[i].equals("E")){
						answer_e.setImageResource(image_true[4]);
					}else if(correct[i].equals("F")){
						answer_f.setImageResource(image_true[5]);
					}else if(correct[i].equals("G")){
						answer_g.setImageResource(image_true[6]);
					}else if(correct[i].equals("t")){
						answer_true.setImageResource(image_true[7]);
					}else if(correct[i].equals("f")){
						answer_false.setImageResource(image_true[8]);
					}
				}
			}
			for (int i = 0; i <linear_array.length; i++) {
				linear_array[i].setEnabled(false);
			}
			UploadingAnswer(answer, "1");
		}
		/**
		 * 提交单题答案
		 * @param answer
		 * @param answertime
		 */
		private void UploadingAnswer(String answer,String answertime){
			List <NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("a",MyFlg.a));
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Reading_QuestionsActivity.this)));
			params.add(new BasicNameValuePair("suit_id",suit_id+""));
			params.add(new BasicNameValuePair("question_no",question_no+""));
			params.add(new BasicNameValuePair("answer",answer.replace("、", ",")));
			params.add(new BasicNameValuePair("answertime",answertime));
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Reading_QuestionsActivity.this, handler, params, "submit_exercise_answer",0,1,false,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_QuestionsActivity.this).getSubmit_exercise_answer(),Reading_QuestionsActivity.this));
			asyncLoadApi.execute();
		}
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
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Reading_QuestionsActivity.this)));
			params.add(new BasicNameValuePair("point_id",kaodianID));
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Reading_QuestionsActivity.this, handler, params, "get_point_info",9,10,"获取考点信息失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_QuestionsActivity.this).getGet_point_info(),Reading_QuestionsActivity.this));
			asyncLoadApi.execute();
		}
	}
	/**
	 * 动态计算子ViewPager高度 最少为屏幕高的一半 50%
	 * @param view
	 */
	public void setAttr(final ScrollView scrollview,final View view,final LinearLayout buttom_linear, final LayoutParams params, final LinearLayout buttom_tuodong,final int imageview_height) {
		ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			private boolean hasMeasured;
			@Override
			public boolean onPreDraw() {
				if (hasMeasured == false) {
					int height = view.getHeight()+imageview_height;
					if(height>(Screen_height*0.5)){
						params.height=(int) ((Screen_height-dip_45-StateHeight)*0.5);
					}else{
						params.height=(int) ((Screen_height-dip_45-StateHeight)-height);
					}
					buttom_linear.setLayoutParams(params);
					//获得材料题子ViewPager最小的高 必须>45dip
					int min_height=Screen_height-height-StateHeight-dip_45;
					if(min_height<dip_45){
						min_height = 0;
					}
					setOnToutch(buttom_linear,buttom_tuodong, params,min_height);

					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
					scrollview.setLayoutParams(params);
					if (height != 0) {
						hasMeasured = true;

					}
				}
				return true;
			}
		});

	}

	//最后一页的界面
	private LinearLayout linear2_cuotijiexi,linear2_zhengzujiexi;
	private Button linear2_buttom_button;
	private int question_sum=0,right_sum=0;//题目总数
	//答对几题    正确率
	private TextView linear2_text3,linear2_text5,linear2_text6;
	/**
	 * 添加最后一页
	 * @return
	 */
	private void lastItem(){
		LinearLayout linear2_bg = (LinearLayout) findViewById(R.id.linear2_bg);
		linear2_text6 		   = (TextView)findViewById(R.id.linear2_text6);
		TextView linear2_text7 = (TextView)findViewById(R.id.linear2_text7);
		TextView linear2_text4 = (TextView)findViewById(R.id.linear2_text4);
		TextView linear2_text2 = (TextView)findViewById(R.id.linear2_text2);
		linear2_text3 		   = (TextView)findViewById(R.id.linear2_text3);
		linear2_text5 		   = (TextView)findViewById(R.id.linear2_text5);

		linear2_text2.setText("第 "+(group_index+1)+" 组答题完成"+" (共"+create_Exercise_suit_2Bean.getGroups().length+"组)");
		linear2_text4.setText(" / "+question_sum+" 道");
		linear2_text6.setTextColor(Fontcolor_3);
		linear2_text7.setTextColor(Fontcolor_3);
		linear2_cuotijiexi = (LinearLayout)findViewById(R.id.linear2_cuotijiexi);
		linear2_zhengzujiexi = (LinearLayout)findViewById(R.id.linear2_zhengzujiexi);
		LinearLayout linear2_bg2 = (LinearLayout)findViewById(R.id.linear2_bg2);
		linear2_buttom_button = (Button)findViewById(R.id.linear2_buttom_button);
		linear2_buttom_button.setBackgroundColor(Bgcolor_2);
		linear2_bg2.setBackgroundColor(Bgcolor_2);
		linear2_bg.setBackgroundColor(Bgcolor_1);

		if(group_index+1==create_Exercise_suit_2Bean.getGroups().length){
			linear2_buttom_button.setText("查看报告");
			linear3_button1.setText("查看报告");
		}else{
			linear2_buttom_button.setText("开始第"+(group_index+2)+"组 (共"+create_Exercise_suit_2Bean.getGroups().length+"组)");
			linear3_button1.setText("继续练习");
		}

		linear2_cuotijiexi.setOnClickListener(this);
		linear2_zhengzujiexi.setOnClickListener(this);
		linear2_buttom_button.setOnClickListener(this);
	}
	/**
	 * 计算答对了多少题 以及正确率
	 */
	private void jisuan_rightsum(){
		right_sum = right_sum+1;
		linear2_text3.setText(right_sum+"");
		linear2_text5.setText((right_sum*100)/question_sum+"");
		if(right_sum==question_sum){
			linear2_cuotijiexi.setEnabled(false);
			linear2_cuotijiexi.setBackgroundColor(getResources().getColor(R.color.item_gray));
			linear2_text6.setTextColor(Color.GRAY);
		}
	}
	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(Reading_QuestionsActivity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								MyFlg.finshActivitys();
								break;
							default:
								break;
						}

					}
				});
	}
	/**
	 *   者看解析
	 */
	private void StartNext(int type){
		Intent intent = new Intent(this, Reading_QuestionsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", create_Exercise_suit_2Bean);
		intent.putExtras(bundle);
		intent.putExtra("group_index", group_index);
		intent.putExtra("type", type);
		startActivityForResult(intent, 4);
		overridePendingTransition(R.anim.push_up_in, R.anim.nullanim);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if((type==0||type==3)){
				showAlertDialogChoose("提示", "是否结束练习", "取消", "确定");
			}else{
				MyFlg.all_activitys.remove(Reading_QuestionsActivity.this);
				setResultForPracticeReportActivity();
				finish();
				overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设置Title背景  整组解析的时候
	 * @param question
	 */
	private void setTitleBG(Create_Exercise_suit_2Bean.questions question){
		if(type!=4){
			String answer = question.getAnswer();
			String st = Sup.getcorrect(question.getCorrect());
			if(st.equals(answer)){
				title_bg.setBackgroundColor(Bgcolor_6);
			}else{
				title_bg.setBackgroundColor(Bgcolor_5);
			}
		}

	}
	/**
	 * 提交多选题答案  在滑动的时候
	 * @param
	 * @param
	 */
	private void DuoxuanSubmit(int group_index,int point){
		int questionIndex=0;
		if(null==infos[group_index].getQuestion_index()){
			questionIndex= 0;
		}else{
			questionIndex=infos[group_index].getQuestion_index();
		}
		Create_Exercise_suit_2Bean.questions question=infos[group_index].getQuestions()[questionIndex];
		String answer = question.getAnswer();

		if((type==0||type==3)&&null==answer){//没答
			infos[group_index].getQuestions()[questionIndex].setAnswer("null");
			answer="null";
		}
		if(question.getType()==2&&answer.equals("null")&&null!=question.getDuoxuan()&&question.getDuoxuan().length()>0){
			list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan(question.getDuoxuan().replace("、", ""), false);
		}

		//记录用户答到了几题
		infos[group_index].setQuestion_index(point);
	}
	/**
	 * 设置底部按钮
	 * @param groud_index 当前Group位置
	 * @param question_index 当前题目位置
	 */
	private void setBottom_button(int groud_index,int question_index){
		Create_Exercise_suit_2Bean.questions question=infos[groud_index].getQuestions()[question_index];
		String answer = question.getAnswer();

		if((type==0||type==3)&&null==answer){//没答
			infos[groud_index].getQuestions()[question_index].setAnswer("null");
			answer="null";
		}
		if(!answer.equals("null")&&!answer.equals("")){//用户已答过
			if(index==index_sum-1 && infos[groud_index].getQuestions().length-1==question_index){
				setBottom_button_content(3,"查看小组总结");
			}else{
				setBottom_button_content(2,"下一题");
			}

		}else if((type==0||type==3)&&(answer.equals("null")||answer.equals(""))){//用户未答
			if(question.getType()==2){
				setBottom_button_content(1,"选择完成");
			}else{
				setBottom_button_content(0,"查看解析");
			}
		}
	}
	/**
	 * 设置底部按钮内容
	 * @param buttom_btn_type  //0 默认 直接点击解析 1 选择完成 2 下一题 3 查看小组总结 4 关闭当前Activity

	 */
	private void setBottom_button_content(int buttom_btn_type,String content){
		this.buttom_btn_type = buttom_btn_type;
		buttom_button.setText(content);
	}

	/**
	 * 查看报告 或者进行下一组
	 */
	private void nextOrsumbit(){
		Bundle bundle;
		if(group_index+1==create_Exercise_suit_2Bean.getGroups().length){ //查看报告
			//Toast.makeText(Reading_QuestionsActivity.this, "查看报告", 0).show();
			clickReport();
		}else{
			//Toast.makeText(Reading_QuestionsActivity.this, "开始下一组", 0).show();
			intent = new Intent(Reading_QuestionsActivity.this,Reading_QuestionsActivity.class);
			intent.putExtra("type", 0);
			intent.putExtra("group_index", group_index+1);
			bundle = new Bundle();
			bundle.putSerializable("bean", create_Exercise_suit_2Bean);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
		}
	}
	//查看报告 提交套题答案
	private void clickReport(){
		dialog.show();
		dialog.setHint("提交答案中");
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Reading_QuestionsActivity.this)));
		params.add(new BasicNameValuePair("suit_id",create_Exercise_suit_2Bean.getId()+""));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < create_Exercise_suit_2Bean.getGroups().length; i++) {
			Create_Exercise_suit_2Bean.infos[] infos=create_Exercise_suit_2Bean.getGroups()[i].getInfos();
			for (int j2 = 0; j2 < infos.length; j2++) {
				Create_Exercise_suit_2Bean.questions[] questinos = infos[j2].getQuestions();
				for (int j = 0; j <questinos.length; j++) {
					try {
						if (!questinos[j].getAnswer().equals("null")) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("no", questinos[j].getNo());
							if(questinos[j].getType()==2){
								String answer = questinos[j].getAnswer();
								String duoxuan="";
								for (int k = 0; k < answer.length(); k++) {
									if(k==answer.length()-1){
										duoxuan =duoxuan+ answer.substring(k, k+1);
									}else{
										duoxuan =duoxuan+ answer.substring(k, k+1)+",";
									}
								}
								jsonObject.put("answer", duoxuan);
							}else{
								jsonObject.put("answer", questinos[j].getAnswer());
							}
							jsonObject.put("answertime", "1");
							jsonArray.put(jsonObject);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		// Log.i("mylog", "suit_id="+create_Exercise_suit_2Bean.getId()+" answer="+jsonArray.toString());
		// Log.i("mylog", "URL="+MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_QuestionsActivity.this).getSubmit_exercise_suit(),Reading_QuestionsActivity.this));
		params.add(new BasicNameValuePair("suit_answers",jsonArray.toString()));
		AsyncLoadApi asyncLoadApi = new AsyncLoadApi(this, handler, params, "submit_exercise_suit",20,21,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_QuestionsActivity.this).getSubmit_exercise_suit(),Reading_QuestionsActivity.this));
		asyncLoadApi.execute();
	}
	/**
	 * 底部按钮动画  显示与隐藏效果切换
	 * isShow 意向true 显示 false不显示
	 */
	private void setBottom_button_anim(Boolean isShow){
		if(botton_isShow==false && isShow==true){//隐藏进入显示
			buttom_button.setVisibility(View.VISIBLE);
			buttom_button.startAnimation(show_anim);
			botton_isShow = true;
		}else if(botton_isShow==true && isShow==false){//显示变为隐藏
			buttom_button.setVisibility(View.GONE);
			buttom_button.startAnimation(hide_anim);
			botton_isShow = false;
		}
	}
	/**
	 *如果最后一页当前题是多选题 并且没提交过 那么执行此方法
	 */
	public void submit_last_question(){
		if(index_sum-1==index){ //如果处于最后一页

			Create_Exercise_suit_2Bean.questions question=Sup.getQuestionBean(infos,index);
			String answer = question.getAnswer();
			if(question.getType()==2&&answer.equals("null")&&null!=question.getDuoxuan()&&question.getDuoxuan().length()>0){
				list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan(question.getDuoxuan().replace("、", ""), false);
			}
		}

	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.image_title_Collection://收藏
				if(ISCanDo()){
					Create_Exercise_suit_2Bean.questions questionBean =infos[index].getQuestions()[Sup.getQUstionIndex(infos,index)];
					int suoyin = questionBean.getCuotijiexi_questionNO()-1;//索引
					int is_collect = questionBean.getIs_collect();
					if(is_collect==0){//为收藏
						Postcollect(questionBean.getQid()+"", 1,collection_textview[suoyin],collection_imageview[suoyin]);
					}else if(is_collect==1){//已收藏 变为收藏
						Postcollect(questionBean.getQid()+"", 0,collection_textview[suoyin],collection_imageview[suoyin]);
					}
				}
				break;
			case R.id.linear2_cuotijiexi:
				StartNext(1);
				break;
			case R.id.linear2_zhengzujiexi:
				StartNext(2);
				break;
			case R.id.linear3_button1://查看报告 或者进行下一组
				nextOrsumbit();
				break;
			case R.id.linear3_button2:
				showAlertDialogChoose("提示", "是否结束练习", "取消", "确定");
				break;
			case R.id.linear2_buttom_button: //查看报告 或者进行下一组
				nextOrsumbit();
				break;
			case R.id.layoutLeft:
				if((type==0||type==3)){
					showAlertDialogChoose("提示", "是否结束练习", "取消", "确定");
				}else{
					MyFlg.all_activitys.remove(Reading_QuestionsActivity.this);
					setResultForPracticeReportActivity();
					Reading_QuestionsActivity.this.finish();
					overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
				}
				break;
			case R.id.linear2_image:
				if((type==0||type==3)){
					showAlertDialogChoose("提示", "是否结束练习", "取消", "确定");
				}else{
					MyFlg.all_activitys.remove(Reading_QuestionsActivity.this);
					Reading_QuestionsActivity.this.finish();
					overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
				}
				break;
			case R.id.buttom_button://底部按钮
				cn.net.dingwei.Bean.Create_Exercise_suit_2Bean.questions question;
				switch (buttom_btn_type) {
					case 0:// 查看解析
						question=Sup.getQuestionBean(infos,index);
						if(question.getType()==2){
							list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan("?", false);
						}else{
							list_click[question.getCuotijiexi_questionNO()-1].setAnsWer("?", 0, null);
						}
						break;
					case 1:// 选择完成
						question=Sup.getQuestionBean(infos,index);
						if(null==question.getDuoxuan()){
							list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan("?", false);
						}else if(question.getDuoxuan().equals("")){
							list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan("?", false);
						}else{
							list_click[question.getCuotijiexi_questionNO()-1].setAnsWer_duoxuan(question.getDuoxuan().replace("、", ""), true);
						}
						break;
					case 2:// 下一题
						Read_Question_Child_ViewPager childViewPager =chid_viewpagers[index];
						int questionIndex=0;
						if(null==infos[index].getQuestion_index()){
							questionIndex= 0;
						}else{
							questionIndex=infos[index].getQuestion_index();
						}
						if(childViewPager.getAdapter().getCount()-1>questionIndex){
							childViewPager.setCurrentItem(questionIndex+1);
						}else if(childViewPager.getAdapter().getCount()-1==questionIndex){
							viewpager_parent.setCurrentItem(index+1);
							if(chid_viewpagers.length-1>=index){
								if(null!=chid_viewpagers[index]){
									chid_viewpagers[index].setCurrentItem(0); //材料题的情况下 点击下一题直接跳到0
								}
							}
						}
						break;
					case 3:// 跳转到小组总结界面  ViewPager最后一个
						mylinear.snapToScreen_right();
						break;
					case 4://关闭Activity
						MyFlg.all_activitys.remove(Reading_QuestionsActivity.this);
						setResultForPracticeReportActivity();
						finish();
						overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//resultCode  1表示支付成功  -1 表示支付失败
		if(resultCode==RESULT_OK&&requestCode==0 && (MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status()==1 || application.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status()==2)){ //支付成功
			for (int i = 0; i <list_content_textviews.size(); i++) {
				list_pay_linears.get(i).setVisibility(View.GONE);
				list_content_textviews.get(i).setVisibility(View.VISIBLE);
			}
			if(MyApplication.getuserInfoBean(Reading_QuestionsActivity.this).getMember_status()==1){
				for (int i = 0; i < list_pay_hints.size(); i++) {
					list_pay_hints.get(i).setVisibility(View.GONE);
				}
			}

		}else if(requestCode == 2&&resultCode == RESULT_OK){//写完笔记返回
			setResult(RESULT_OK);
			Edit_user_noteBean bean = (Edit_user_noteBean) data.getSerializableExtra("Edit_user_noteBean");
			int questionIndex=0;
			if(null==infos[index].getQuestion_index()){
				questionIndex= 0;
			}else{
				questionIndex=infos[index].getQuestion_index();
			}
			infos[index].getQuestions()[questionIndex].getNotes().getMe()[0].setContent(bean.getData().getContent());
			infos[index].getQuestions()[questionIndex].getNotes().getMe()[0].setTime_text(bean.getData().getTime_text());

			note_bean me = infos[index].getQuestions()[questionIndex].getNotes().getMe()[0];
			note_texts[0].setText(me.getAuthor_name());
			note_texts[1].setText(me.getTime_text());
			note_texts[2].setText(me.getContent());
					/*note_texts[0].setText(bean.getData().getAuthor_name());
					note_texts[1].setText(bean.getData().getTime_text());
					note_texts[2].setText(bean.getData().getContent());*/
			//note_texts[3].setVisibility(View.VISIBLE);
			if(bean.getData().getTime_text().trim().length()<=0){
				now_text_add_note.setText("添加笔记");
				note_texts[2].setTextColor(Fontcolor_7);
			}else{
				now_text_add_note.setText("编辑笔记");
				note_texts[2].setTextColor(Fontcolor_3);
			}

		}else if(requestCode == 4&&resultCode == RESULT_OK){//查看解析返回
			List<Create_Exercise_suit_2Bean.infos> temp_infos =(List<cn.net.dingwei.Bean.Create_Exercise_suit_2Bean.infos>) data.getSerializableExtra("infos");

			Sup.setList_infos_all2(infos,temp_infos);
			for (int i = 0; i < list_view.size(); i++) {
				list_view.get(i).setTag(0);
			}
			type=4;
			viewpager_parent.setAdapter(pagerAdapter);
			viewpager_parent.setCurrentItem(list_view.size()-1);
		}else if(requestCode == 100&&resultCode == RESULT_OK){//完善资料返回
			MyApplication.userInfoBean=APPUtil.getUser_isRegistered(Reading_QuestionsActivity.this);
		}
	}
	//******收藏
	private void Postcollect(String qid,final int type,final TextView textView,final ImageView imageView){
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(Reading_QuestionsActivity.this));
		params.add("qid", qid);
		params.add("type", type+"");
		client.post(MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_QuestionsActivity.this).getEdit_user_collect(),Reading_QuestionsActivity.this), params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				try {
					Boolean status = new JSONObject(result).getBoolean("status");
					if(status==true){
						if(type==1){//收藏
							if(null!=textView){
								textView.setText(placeholder_textBean.getSc_ysc());
							}
							ImageLoader.getInstance().displayImage(shoucang_1, imageView, application.getOptions());
							infos[index].getQuestions()[Sup.getQUstionIndex(infos,index)].setIs_collect(1);
							setcollectImage(1);
						}else{
							if(null!=textView){
								textView.setText(placeholder_textBean.getSc_wsc());
							}

							ImageLoader.getInstance().displayImage(shoucang_0, imageView, application.getOptions());
							infos[index].getQuestions()[Sup.getQUstionIndex(infos,index)].setIs_collect(0);
							setcollectImage(0);
						}
						if(isCilickCollection==false){ //给上一个Activity返回值
							Reading_QuestionsActivity.this.setResult(RESULT_OK);
							isCilickCollection = true;
						}
					}else{
						if(type==1){
							Toast.makeText(Reading_QuestionsActivity.this, "收藏失败，请稍后重试。", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(Reading_QuestionsActivity.this, "取消收藏失败，请稍后重试。", Toast.LENGTH_SHORT).show();
						}

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
				Toast.makeText(Reading_QuestionsActivity.this, "网络异常。", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}
		});
	}

	/**
	 * 设置收藏图片
	 */
	private void setcollectImage(int is_collect){
		if(is_collect==1){
			ImageLoader.getInstance().displayImage(shoucang_1, image_title_Collection, application.getOptions());
		}else if(is_collect==0){
			ImageLoader.getInstance().displayImage(shoucang_0, image_title_Collection, application.getOptions());
		}
	}

	private void setResultForPracticeReportActivity(){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", create_Exercise_suit_2Bean);

		List<Create_Exercise_suit_2Bean.infos> temp_infos = new ArrayList<Create_Exercise_suit_2Bean.infos>();
		for (int i = 0; i < infos.length; i++) {
			temp_infos.add(infos[i]);
		}
		bundle.putSerializable("infos", (Serializable) temp_infos);
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
	}
	class imageViewClick implements OnClickListener{//图片点击
		private String imageUrl;
		private Get_guide_msg_listBean.msglist  msglist;

		public imageViewClick(String imageUrl) {
			this.imageUrl = imageUrl;
			if(imageUrl==null){
				imageUrl = "";
			}
			msglist = new Get_guide_msg_listBean().new msglist();
			Get_guide_msg_listBean.images[] images=new Get_guide_msg_listBean.images[1];
			Get_guide_msg_listBean.images temp_images = new Get_guide_msg_listBean().new images();
			temp_images.setImage_url(imageUrl);
			temp_images.setThumb_url(imageUrl);
			images[0]=temp_images;
			msglist.setImages(images);
		}

		@Override
		public void onClick(View view) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("msglist", msglist);
			//intent.putExtra("imageUrls", list_guides.get((Integer)v.getTag()).getImages());
			Intent intent = new Intent(Reading_QuestionsActivity.this, Image_ViewPager_Activity.class);
			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}
	}

}
