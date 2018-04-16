package cn.net.dingwei.ui;

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
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
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
import cn.net.dingwei.Bean.Create_test_suit_2Bean;
import cn.net.dingwei.Bean.Create_test_suit_2Bean.infos;
import cn.net.dingwei.Bean.Create_test_suit_2Bean.note_bean;
import cn.net.dingwei.Bean.Edit_user_noteBean;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.Bean.Get_point_infoBean;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.Bean.Submit_exercise_suitBean;
import cn.net.dingwei.adpater.ViewPagerAdpater;
import cn.net.dingwei.myView.AnswerDialog;
import cn.net.dingwei.myView.ElasticScrollView_answer;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.AnswerUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * 测验 —— 材料题类型
 * @author Administrator
 *
 */
public class Reading_testingActivity extends ParentActivity implements OnClickListener{
	private MyApplication application;
	private LinearLayout layoutLeft,title_text_linear,linear_pause;
	private TextView title_text1,title_text2,title_text3;
	private ImageView riggt_image;
	private ViewPager viewpager_parent;
	private Button buttom_button,pause_button;
	private RelativeLayout title_bg;
	//--------------------------暂停页--------------------------------
	//暂停页面
	private FrameLayout pause_ui_framelayout;
	private Animation slide_in_bottom,slide_out_top;
	private ElasticScrollView_answer myScrollview;
	private Boolean isRestStartTime=true; //True 需要重新获取开始时间  false 不需要
	private TextView pause_time,pause_time_last;
	private LinearLayout linear_Left_pause;
	private Boolean is_load_lastitem=false;//是否加载最后一个交卷页面
	//--------------------------数据--------------------------------
	private Create_test_suit_2Bean bean;
	private List<Create_test_suit_2Bean.infos> list_infos;
	private int question_sum=0;
	private List<View> list_view;
	private int dip_45=0,dip_25=0;//可拖动的图标高
	viewholder viewholder = new viewholder();
	//倒计时
	private int time_sum=5000; //剩余时间
	private boolean ispasue=false;//是否暂停
	private myThread myThread = new myThread();
	private int time_sums=0; //总共时间
	private boolean isStartThread = false; //是否已经开始倒计时
	//快到期的View集合   如果用户的会员状态变了  要隐藏
	private List<LinearLayout> list_pay_hints;
	private List<LinearLayout> list_content_textviews;
	private List<LinearLayout> list_pay_linears;
	private Question_click[] list_click;
	private Read_Question_Child_ViewPager[] chid_viewpagers;//记录子ViewPager
	private List<TextView> list_texts,list_texts_last; //暂停页和尾页的TextView集合

	private Boolean botton_isShow=true;//底部按钮 显示  true false 隐藏  用于在显示与隐藏之间切换时判断用动画的标识
	private Animation hide_anim,show_anim;//底部按钮的显示与隐藏 淡入淡出效果
	private int index=0,index_sum=0;//父ViewPager索引
	private int buttom_type=0; //0 正常 滑到下一页  1 ：下一题（滑动子ViewPager或者父ViewPager） 2 交卷  5 关闭activity
	private FYuanTikuDialog dialog;
	//--------------点击考点显示的对话框------------------
	private	AnswerDialog answerDialog ;
	private myhandler handler= new myhandler();
	private int view_width = 0;//除去左右间隔 剩余的中间全部宽 计算考点
	//图片
	private int[] image_default         = new int[]{R.drawable.answer_a,R.drawable.answer_b,R.drawable.answer_c,R.drawable.answer_d,R.drawable.answer_e,R.drawable.answer_f,R.drawable.answer_g,R.drawable.npd_1,R.drawable.npd_2};
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
	/**
	 * 0 正常    1 错题解析    2  全部解析
	 */
	private int type=0;
	private int infos_index=0; //父ViewPager的位置
	private int question_index=0; //子ViewPager的位置

	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_5=0,Bgcolor_6=0,Color_3=0;
	private int Screen_width=0,Screen_height=0,StateHeight=0;
	private String jiucuo,shoucang_0,shoucang_1,Huakuai;
	private String Test_pause;
	//记录每个页面的TextView 和ImgeView  收藏用
	private TextView[] collection_textview;
	private ImageView[] collection_imageview;
	private Placeholder_textBean placeholder_textBean;
	private String suitid="";
	private TextView[] note_texts;//记录笔记的控件
	private TextView now_text_add_note;//用于写了笔记回来改变值
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_testing);
		isSetTile=false;
		MyFlg.all_activitys.add(this);
		MyFlg.ISupdateHome_viewpager = true;
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
		Screen_height=sharedPreferences.getInt("Screen_height", 0);
		StateHeight=sharedPreferences.getInt("StateHeight", 0);
		Test_pause = sharedPreferences.getString("Test_pause", "");
		jiucuo =  sharedPreferences.getString("jiucuo", "");
		shoucang_0 =  sharedPreferences.getString("shoucang_0", "");
		shoucang_1 =  sharedPreferences.getString("shoucang_1", "");
		Huakuai = sharedPreferences.getString("Huakuai", "");
		try {
			SharedPreferences sp_commoninfo = getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
			Gson gson = new Gson();
			placeholder_textBean = gson.fromJson(new JSONObject(sp_commoninfo.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyFlg.ISupdateHome=true;//是否刷新首页
		initID();
		initData();
	}
	private void initData() {
		// TODO Auto-generated method stub
		answerDialog = new AnswerDialog(this);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		hide_anim = AnimationUtils.loadAnimation(this, R.anim.fade_hide_200ms);
		show_anim = AnimationUtils.loadAnimation(this, R.anim.fade_show_200ms);
		dip_45 = DensityUtil.DipToPixels(this, 45);
		dip_25 = DensityUtil.DipToPixels(this, 25);
		list_content_textviews = new ArrayList<>();
		list_pay_linears = new ArrayList<LinearLayout>();
		list_pay_hints = new ArrayList<LinearLayout>();
		view_width =Screen_width-DensityUtil.DipToPixels(this, 20);
		setData();

		viewpager_parent.setAdapter(pagerAdapter);
		viewpager_parent.setCurrentItem(infos_index);
		index = infos_index;

		viewpager_parent.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int point) {
				// TODO Auto-generated method stub
				if(type==0){
					if(point!=list_view.size()-1 ){
						if(list_infos.get(point).getInfo_no()==-999){
							title_text_linear.setVisibility(View.INVISIBLE);

							buttom_type =0;
							if(point ==0){
								buttom_button.setText("开始");
							}else{
								buttom_button.setText("继续");
							}
							myThread.onPause();//暂停倒计时
						}else {
							title_text_linear.setVisibility(View.VISIBLE);
							if(null==list_infos.get(point).getQuestion_index()){
								title_text2.setText(list_infos.get(point).getQuestions()[0].getNo()+"");
							}else{
								title_text2.setText(list_infos.get(point).getQuestions()[list_infos.get(point).getQuestion_index()].getNo()+"");
							}
							buttom_type=1;
							if(point==list_view.size()-2){
								if(list_infos.get(point).getQuestions().length==1){
									buttom_button.setText("完成");
								}else{
									if(null!=list_infos.get(point).getQuestion_index()&&list_infos.get(point).getQuestions().length==list_infos.get(point).getQuestion_index()+1){
										buttom_button.setText("完成");
									}
								}
							}else{
								buttom_button.setText("下一题");
							}

							if(isStartThread ==false){
								myThread.start();
								isStartThread =true;
							}else if(ispasue==true){
								myThread.go_on();
							}
						}
					}else{
						pause_time_last.setText("用时： "+(nowUserTime())+" 分钟  / "+bean.getTest_time_limit()+" 分钟");
						title_text_linear.setVisibility(View.INVISIBLE);
						buttom_type =2;//交卷
						buttom_button.setText("交卷");
						is_load_lastitem=true;
						myThread.onPause();//暂停倒计时
					}

					//提交答案
					if(index!=list_view.size()-1){ //如果不是最后一项
						if(list_infos.get(index).getInfo_no()!=-999){
							int question_index = 0;
							if(null!=list_infos.get(index).getQuestion_index()){
								question_index = list_infos.get(index).getQuestion_index();
							}
							Create_test_suit_2Bean.questions question =list_infos.get(index).getQuestions()[question_index] ;
							if(question.getType()==2&&null!=question.getDuoxuan()&&question.getDuoxuan().length()>0){
								list_click[question.getNo()-1].setAnsWer_duoxuan(question.getDuoxuan().replace("、", ","));
							}
						}
					}
				}else{ //解析什么的  不做任何事
					if(null==list_infos.get(point).getQuestion_index()){
						if(type==1){
							title_text2.setText(list_infos.get(point).getQuestions()[0].getIndexes()+"");
						}else if(type==2){
							title_text2.setText(list_infos.get(point).getQuestions()[0].getNo()+"");
						}
						setcollectImage(list_infos.get(point).getQuestions()[0].getIs_collect());
						setTitleBG(list_infos.get(point).getQuestions()[0].getIsError());
					}else{
						if(type==1){
							title_text2.setText(list_infos.get(point).getQuestions()[list_infos.get(point).getQuestion_index()].getIndexes()+"");
						}else if(type==2){
							title_text2.setText(list_infos.get(point).getQuestions()[list_infos.get(point).getQuestion_index()].getNo()+"");
						}
						setcollectImage(list_infos.get(point).getQuestions()[list_infos.get(point).getQuestion_index()].getIs_collect());
						setTitleBG(list_infos.get(point).getQuestions()[list_infos.get(point).getQuestion_index()].getIsError());
					}
				}

				if(point!=list_view.size()-1 ){
					if(list_infos.get(point).getIsShowBottom_button()){
						setBottom_button_anim(true);
					}else{
						setBottom_button_anim(false);
					}
				}
				index = point;
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
	 *
	 * @param group
	 * @param group_index 即infos_index  判断是哪个材料题
	 * @param isShowCailiao
	 * @return
	 */
	private View setParent_ViewPagerItem(Create_test_suit_2Bean.infos group,final int group_index,Boolean isShowCailiao){
		View view = View.inflate(this, R.layout.item_read_question_parent_viewpager, null);
		TextView top_title = (TextView) view.findViewById(R.id.top_title);
		TextView top_content = (TextView) view.findViewById(R.id.top_content);
		LinearLayout buttom_linear =  (LinearLayout) view.findViewById(R.id.buttom_linear);
		LinearLayout buttom_tuodong =  (LinearLayout) view.findViewById(R.id.buttom_tuodong);
		Read_Question_Child_ViewPager viewpager_child=(Read_Question_Child_ViewPager) view.findViewById(R.id.viewpager_child);
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
		int imageview_height =DensityUtil.DipToPixels(Reading_testingActivity.this, 30);
		if(null!=group.getInfo() &&null!=group.getInfo().getImg() ){
			int width = Screen_width-DensityUtil.DipToPixels(Reading_testingActivity.this, 20);
			if(group.getInfo().getImg().getWidth()>width){
				imageview_height =imageview_height+ width*group.getInfo().getImg().getHeight()/group.getInfo().getImg().getWidth();
				ImageLoader.getInstance().displayImage(group.getInfo().getImg().getUrl(), cailiao_image);
			}else{
				imageview_height =imageview_height+ group.getInfo().getImg().getHeight();
				ImageLoader.getInstance().displayImage(group.getInfo().getImg().getUrl(), cailiao_image);
			}


		}
		//设置颜色
		top_title.setTextColor(Fontcolor_3);
		top_content.setTextColor(Fontcolor_3);
		//buttom_linear.setBackgroundColor(Bgcolor_1);

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
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
			buttom_linear.setLayoutParams(params);
			setAttr(scrollview,linear_cailiao_top,buttom_linear,params,buttom_tuodong,imageview_height);
		}else{
			scrollview.setVisibility(View.GONE);
			tuodong_img.setVisibility(View.GONE);
		}

		Create_test_suit_2Bean.questions [] questions = group.getQuestions();
		List<View> list_childViews=new ArrayList<View>();
		for (int i = 0; i < questions.length; i++) {
			View view_child_item = setViewPager_youhua(viewholder, questions[i], group_index,i,viewpager_child);
			list_childViews.add(view_child_item);
		}
		chid_viewpagers[group_index] = viewpager_child;
		viewpager_child.setAdapter(new ViewPagerAdpater(list_childViews));
		if(group_index<infos_index){
			viewpager_child.setCurrentItem(questions.length-1); //全组解析的时候  小于当前组的  倒序
			list_infos.get(group_index).setQuestion_index(questions.length-1);
		}else if(group_index==infos_index){
			viewpager_child.setCurrentItem(question_index);
			list_infos.get(group_index).setQuestion_index(question_index);
		}
		viewpager_child.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int point) {
				// TODO Auto-generated method stub
				Create_test_suit_2Bean.questions questionBean = list_infos.get(group_index).getQuestions()[point];
				if(type==1){ //错题解析设置索引
					title_text2.setText(questionBean.getIndexes()+"");
				}else {
					title_text2.setText(questionBean.getNo()+"");
				}

				if(type!=0){
					setcollectImage(questionBean.getIs_collect());
					setTitleBG(questionBean.getIsError());
				}

				if(type==0){
					int question_index = 0;
					if(null!=list_infos.get(group_index).getQuestion_index()){
						question_index = list_infos.get(group_index).getQuestion_index();
					}
					Create_test_suit_2Bean.questions question =list_infos.get(group_index).getQuestions()[question_index] ;
					if(question.getType()==2&&null!=question.getDuoxuan()&&question.getDuoxuan().length()>0){
						list_click[question.getNo()-1].setAnsWer_duoxuan(question.getDuoxuan().replace("、", ","));
					}
					if(index==index_sum-2 && list_infos.get(group_index).getQuestions().length-1==point){
						buttom_button.setText("完成");
					}else{
						buttom_button.setText("下一题");
					}
				}
				list_infos.get(group_index).setQuestion_index(point);
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
	private View setViewPager_youhua(viewholder viewholder,final Create_test_suit_2Bean.questions questtionBean,int group_index,int question_index,ViewPager viewpager_child){
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
		viewholder.linear_answer_no.setVisibility(View.GONE);//测验 不需要不会 隐藏掉
		viewholder.linear_buttom_all = (LinearLayout) view.findViewById(R.id.linear_buttom_all);
		//viewholder.linear_buttom_all.setVisibility(View.GONE);

		viewholder.linear_buttom_kaodian = (LinearLayout) view.findViewById(R.id.linear_buttom_kaodian);
		viewholder.linear_head = (LinearLayout) view.findViewById(R.id.linear_head);
		viewholder.analysis =  (LinearLayout) view.findViewById(R.id.analysis);
		//viewholder.analysis.setVisibility(View.VISIBLE);
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
		viewholder.linear_notes =(LinearLayout) view.findViewById(R.id.linear_notes);
		viewholder.linear_jiexi_content =(LinearLayout) view.findViewById(R.id.linear_jiexi_content);
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
		viewholder.pay_hint2.setTextColor(Bgcolor_2);
		list_pay_hints.add(viewholder.pay_hint_linear);
		if(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status()==2&&MyApplication.isShowVip==false){//会员快到期 给提示
			viewholder.pay_hint_linear.setVisibility(View.VISIBLE);
			viewholder.pay_hint1.setText(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status_name());
			viewholder.pay_hint2.setText(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_price());
			viewholder.pay_hint2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Reading_testingActivity.this,PayVIPActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				}
			});
		}
		//设置数据
		viewholder.timu.setText(questtionBean.getContent());
		if(null !=questtionBean.getImg()  && null !=questtionBean.getImg().getUrl()){
		}
		ImageLoader.getInstance().displayImage(questtionBean.getImg().getUrl(),viewholder.imageView,application.getOptions());
		if(questtionBean.getType()==2){

			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_1,viewholder.answer_a,application.getOptions());
			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_2,viewholder.answer_b,application.getOptions());
			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_3,viewholder.answer_c,application.getOptions());
			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_4,viewholder.answer_d,application.getOptions());
			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_5,viewholder.answer_e,application.getOptions());
			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_6,viewholder.answer_f,application.getOptions());
			ImageLoader.getInstance().displayImage("drawable://"+R.drawable.dn_7,viewholder.answer_g,application.getOptions());
		}
		//设置答案
		for (int j = 0; j < questtionBean.getOpt().length; j++) {
			Create_test_suit_2Bean.opt opt = questtionBean.getOpt()[j];
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
		if(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status()==1 || MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status()==2||MyApplication.isShowVip==false){

		}else{
			viewholder.linear_vip.setVisibility(View.VISIBLE);
			viewholder.linear_jiexi_content.setVisibility(View.GONE);
			viewholder.payRmb_vip1.setText(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status_name());
			viewholder.payRmb_vip.setText(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_price());
			viewholder.payRmb_vip.setTextColor(Bgcolor_2);
			viewholder.payRmb_vip.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Reading_testingActivity.this,PayVIPActivity.class);
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
			int dip_15 = DensityUtil.DipToPixels(Reading_testingActivity.this, 15);
			button.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Bgcolor_2,Bgcolor_1, 1, DensityUtil.DipToPixels(Reading_testingActivity.this, 10)));
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
			/*if(null !=questtionBean.getAnswer() &&!questtionBean.getAnswer().equals("null")&&!questtionBean.getAnswer().equals("")){
				setAnswer(linear_arr, questtionBean, viewholder);
			}*/
		if(type==0){
			viewholder.temp_text.setVisibility(View.VISIBLE);
			Question_click question_click = new Question_click(questtionBean.getCorrect(),bean.getId(),questtionBean.getNo(),questtionBean.getType(),viewholder.answer_text_jiexi_false,viewholder.analysis,viewholder.answer_a,viewholder.answer_b,viewholder.answer_c,viewholder.answer_d,viewholder.answer_e,viewholder.answer_f,viewholder.answer_g,viewholder.answer_true,viewholder.answer_false,linear_arr,group_index,question_index,viewpager_child);
			list_click[questtionBean.getNo()-1]=question_click;
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
			ImageLoader.getInstance().displayImage(headUrl,viewholder.answer_person,application.getOptions());
		}

		//添加点击头像事件
		cn.net.dingwei.util.AnswerUtil.HeadClick headClick = new AnswerUtil.HeadClick(questtionBean.getAnalyze().getBy().getUrl(), this);
		viewholder.linear_head.setOnClickListener(headClick);
		//是错误解析 或整组解析的时候 设置答案
		if(type!=0){
			setAnswer(linear_arr, questtionBean, viewholder);
		}else{ //如果是在答题的时候  显示占位

		}

		if(questtionBean.getType()==4){//阅读  不要正确答案那行
			viewholder.linear_answer_text.setVisibility(View.GONE);
			viewholder.linear_answer_no.setVisibility(View.GONE);
		}
		if(type!=0){

			viewholder.text_note.setTextColor(Fontcolor_3);
			viewholder.text_add_note.setTextColor(Bgcolor_2);
			viewholder.text_Collection.setTextColor(Fontcolor_3);
			viewholder.text_error.setTextColor(Fontcolor_3);
			viewholder.text_look_all.setTextColor(Fontcolor_7);
			collection_textview[questtionBean.getIndexes()-1]=viewholder.text_Collection;
			collection_imageview[questtionBean.getIndexes()-1]=viewholder.image_Collection;
			//数据
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
			NoteClick noteClick = new NoteClick(questtionBean,temp_notes,viewholder.text_Collection,viewholder.image_Collection);
			viewholder.linear_Collection.setOnClickListener(noteClick);
			viewholder.linear_error.setOnClickListener(noteClick);
			viewholder.text_look_all.setOnClickListener(noteClick);
			viewholder.text_add_note.setOnClickListener(noteClick);

			//当是创建或者继续练习的时候
			if(type==0||type==3){
				viewholder.temp_text.setVisibility(View.VISIBLE);
			}

		}
		return view;
	}
	/**
	 * 设置笔记
	 */
	private  TextView[] SetNotes(Create_test_suit_2Bean.notes notes,LinearLayout linear_notes,TextView text_look_all,TextView text_add_note){

		TextView[] temp_notes = new TextView[4];
		temp_notes[3]=text_look_all; //把查看全部笔记 添加进去
		if(null!=notes){
			Create_test_suit_2Bean.note_bean[] me = notes.getMe();
			Create_test_suit_2Bean.note_bean[] other = notes.getOther();
			//自己的笔记
			if(null!=me){
				View MeView = View.inflate(Reading_testingActivity.this, R.layout.item_question_jianda, null);
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
					ImageLoader.getInstance().displayImage("drawable://"+R.drawable.answer_person,head_icon,application.getOptions());
				}
				text_nick_name.setText(me[0].getAuthor_name());
				text_time.setText(me[0].getTime_text());
				text_content.setText(me[0].getContent());
				if(me[0].getTime_text().trim().length()<=0){//时间是null
					text_content.setTextColor(Fontcolor_7);
					text_add_note.setText("添加笔记");
				}else{
					text_add_note.setText("编辑笔记");
				}
				linear_notes.addView(MeView);
			}
			//其他的笔记
			if(null!=other&&other.length>0){//有其他人的笔记就显示出来
				text_look_all.setVisibility(View.VISIBLE);
				for (int i = 0; i < other.length; i++) {
					View otherView = View.inflate(Reading_testingActivity.this, R.layout.item_question_jianda_right_head, null);
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
						ImageLoader.getInstance().displayImage("drawable://"+R.drawable.answer_person,head_icon,application.getOptions());
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
	//笔记 这快的点击事件
	class NoteClick implements OnClickListener{
		private Create_test_suit_2Bean.questions questtionBean;
		private String note_Content="";
		private TextView[] temp_notes;
		private TextView text_Collection;
		private ImageView image_Collection;
		public NoteClick(Create_test_suit_2Bean.questions questtionBean,TextView[] temp_notes,TextView text_Collection,ImageView image_Collection) {
			// TODO Auto-generated constructor stub
			this.questtionBean = questtionBean;
			this.text_Collection = text_Collection;
			this.image_Collection = image_Collection;
			this.temp_notes = temp_notes;
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
						questionIndex=0;
						if(null==list_infos.get(index).getQuestion_index()){
							questionIndex= 0;
						}else{
							questionIndex=list_infos.get(index).getQuestion_index();
						}
						me	=list_infos.get(index).getQuestions()[questionIndex].getNotes().getMe()[0];
						if(null!=me.getContent()&&!me.getTime_text().equals("")){
							note_Content = questtionBean.getNotes().getMe()[0].getContent();
						}else{
							note_Content="";
						}
						intent = new Intent(Reading_testingActivity.this, WriteNoteAndErrorActivity.class);
						intent.putExtra("flg", 1);
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
					if(null==list_infos.get(index).getQuestion_index()){
						questionIndex= 0;
					}else{
						questionIndex=list_infos.get(index).getQuestion_index();
					}
					me	=list_infos.get(index).getQuestions()[questionIndex].getNotes().getMe()[0];

					intent = new Intent(Reading_testingActivity.this, NoteActivity.class);
				/*Bundle bundle = new Bundle();
				bundle.putSerializable("me", me);
				intent.putExtras(bundle);*/
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
						int is_collect=list_infos.get(index).getQuestions()[Sup.getQuestionIndex(list_infos,index)].getIs_collect();
						if(is_collect==0){//为收藏
							Postcollect(questtionBean.getQid()+"", 1,text_Collection,image_Collection);
						}else if(is_collect==1){//已收藏 变为收藏
							Postcollect(questtionBean.getQid()+"", 0,text_Collection,image_Collection);
						}
					}
					break;
				case R.id.linear_error://我要纠错
					intent = new Intent(Reading_testingActivity.this, WriteNoteAndErrorActivity.class);
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
		if(MyApplication.getuserInfoBean(Reading_testingActivity.this).getRegistered()==true){
			return true;
		}else{
			Intent intent = new Intent(Reading_testingActivity.this, Rest_passwordActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("flg", "1");
			intent.putExtra("isNeddSinIn", "0");
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			return false;
		}
	}
	//******收藏
	private void Postcollect(String qid,final int type,final TextView textView,final ImageView imageView){
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(Reading_testingActivity.this));
		params.add("qid", qid);
		params.add("type", type+"");
		client.post(MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_testingActivity.this).getEdit_user_collect(),Reading_testingActivity.this), params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				try {
					boolean status = new JSONObject(result).getBoolean("status");
					if(status==true){
						if(type==1){//收藏
							if(null!=textView){
								textView.setText(placeholder_textBean.getSc_ysc());
							}
							ImageLoader.getInstance().displayImage(shoucang_1, imageView, application.getOptions());
							list_infos.get(index).getQuestions()[Sup.getQuestionIndex(list_infos,index)].setIs_collect(1);
							setcollectImage(1);
						}else{
							if(null!=textView){
								textView.setText(placeholder_textBean.getSc_wsc());
							}

							ImageLoader.getInstance().displayImage(shoucang_0, imageView, application.getOptions());
							list_infos.get(index).getQuestions()[Sup.getQuestionIndex(list_infos,index)].setIs_collect(0);
							setcollectImage(0);
						}
					}else{
						if(type==1){
							Toast.makeText(Reading_testingActivity.this, "收藏失败，请稍后重试。", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(Reading_testingActivity.this, "取消收藏失败，请稍后重试。", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(Reading_testingActivity.this, "网络异常。", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}

	/**
	 * 设置收藏图片
	 */
	private void setcollectImage(int is_collect){
		if(is_collect==1){
			ImageLoader.getInstance().displayImage(shoucang_1, riggt_image, application.getOptions());
		}else if(is_collect==0){
			ImageLoader.getInstance().displayImage(shoucang_0, riggt_image, application.getOptions());
		}
	}
	//设置答案.
	private void setAnswer(LinearLayout[] linear_arr,Create_test_suit_2Bean.questions questtionBean,viewholder viewholder){
		for (int j = 0; j < linear_arr.length; j++) {
			linear_arr[j].setEnabled(false);
		}
		viewholder.analysis.setVisibility(View.VISIBLE);
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
			/*for (int j = 0; j < questtionBean.getAnswer().length(); j++) {
				if(j==questtionBean.getAnswer().length()-1){
					st =st+ questtionBean.getAnswer().substring(j, j+1);
				}else{
					st =st+ questtionBean.getAnswer().substring(j, j+1)+"、";
				}
			}*/
			viewholder.answer_text_jiexi_false.setText(questtionBean.getAnswer().replace(",", "、"));
		}


		String st = ""; //正确答案
		for (int j = 0; j < questtionBean.getCorrect().length; j++) {
			st =st+questtionBean.getCorrect()[j];

		}
		//设置选项
		ImageView [] imageviews = new ImageView[]{viewholder.answer_a,viewholder.answer_b,viewholder.answer_c,viewholder.answer_d,viewholder.answer_e,viewholder.answer_f,viewholder.answer_g,viewholder.answer_true,viewholder.answer_false,};
		if(questtionBean.getType()==2){//多选
			if(questtionBean.getAnswer().equals("null")||questtionBean.getAnswer().equals("?") || questtionBean.getAnswer().replace(",", "").equals(st)){//没答 或不会 或者答案正确
				String [] correct = questtionBean.getCorrect();
				for (int k = 0; k < correct.length; k++) {
					if(correct[k].equals("A")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[0],imageviews[0],application.getOptions());
					}else if(correct[k].equals("B")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[1],imageviews[1],application.getOptions());
					}else if(correct[k].equals("C")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[2],imageviews[2],application.getOptions());
					}else if(correct[k].equals("D")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[3],imageviews[3],application.getOptions());
					}else if(correct[k].equals("E")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[4],imageviews[4],application.getOptions());
					}else if(correct[k].equals("F")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[5],imageviews[5],application.getOptions());
					}else if(correct[k].equals("G")){
						ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[6],imageviews[6],application.getOptions());
					}
				}
			}else{//答案错误
				//如果选了A  答案也有A 显示半对
				String answer = questtionBean.getAnswer();

				//String []duoxuan_answer = questtionBean.getDuoxuan_answer();
				if(answer.contains("A") && st.contains("A")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[0],imageviews[0],application.getOptions());
				}else if(st.contains("A")&& !answer.contains("A") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[0],imageviews[0],application.getOptions());
				}else if(answer.contains("A") && !st.contains("A")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[0],imageviews[0],application.getOptions());
				} else{
				}

				if(answer.contains("B") && st.contains("B")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[1],imageviews[1],application.getOptions());
				}else if(st.contains("B")&& !answer.contains("B") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[1],imageviews[1],application.getOptions());
				}else if(answer.contains("B") && !st.contains("B")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[1],imageviews[1],application.getOptions());
				}

				if(answer.contains("C") && st.contains("C")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[2],imageviews[2],application.getOptions());
				}else if(st.contains("C")&& !answer.contains("C") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[2],imageviews[2],application.getOptions());
				}else if(answer.contains("C") && !st.contains("C")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[2],imageviews[2],application.getOptions());
				}

				if(answer.contains("D") && st.contains("D")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[3],imageviews[3],application.getOptions());
				}else if(st.contains("D")&& !answer.contains("D") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[3],imageviews[3],application.getOptions());
				}else if(answer.contains("D") && !st.contains("D")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[3],imageviews[3],application.getOptions());
				}

				if(answer.contains("E") && st.contains("E")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[4],imageviews[4],application.getOptions());
				}else if(st.contains("E")&& !answer.contains("E") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[4],imageviews[4],application.getOptions());
				}else if(answer.contains("E") && !st.contains("E")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[4],imageviews[4],application.getOptions());
				}

				if(answer.contains("F") && st.contains("F")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[5],imageviews[5],application.getOptions());
				}else if(st.contains("F")&& !answer.contains("F") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[5],imageviews[5],application.getOptions());
				}else if(answer.contains("F") && !st.contains("F")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[5],imageviews[5],application.getOptions());
				}

				else if(answer.contains("G") && st.contains("G")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_tAndf[6],imageviews[6],application.getOptions());
				}else if(st.contains("G")&& !answer.contains("G") ){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[6],imageviews[6],application.getOptions());
				}else if(answer.contains("G") && !st.contains("G")){
					ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_false[6],imageviews[6],application.getOptions());
				}
			}
		}else{//单选
			if(st.equals("A")){ //标识正确答案
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_1,imageviews[0],application.getOptions());
			}else if(st.equals("B")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_2,imageviews[1],application.getOptions());
			}else if(st.equals("C")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_3,imageviews[2],application.getOptions());
			}else if(st.equals("D")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_4,imageviews[3],application.getOptions());
			}else if(st.equals("E")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_5,imageviews[4],application.getOptions());
			}else if(st.equals("F")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_6,imageviews[5],application.getOptions());
			}else if(st.equals("G")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.r_7,imageviews[6],application.getOptions());
			}else if(st.equals("t")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.rpd_1,imageviews[7],application.getOptions());
			}else if(st.equals("f")){
				ImageLoader.getInstance().displayImage("drawable://"+R.drawable.rpd_2,imageviews[8],application.getOptions());
				imageviews[8].setImageResource(R.drawable.rpd_2);
			}

			//设置用户答错的答案
			String answer=questtionBean.getAnswer();
			if(!answer.equals("?")&&!answer.equals("null")&&!answer.equals(st) ){ //答案错误时显示错误答案
				if(answer.equals("A")){ //
					ImageLoader.getInstance().displayImage("drawable://"+image_false[0],imageviews[0],application.getOptions());
				}else if(answer.equals("B")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[1],imageviews[1],application.getOptions());
				}else if(answer.equals("C")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[2],imageviews[2],application.getOptions());
				}else if(answer.equals("D")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[3],imageviews[3],application.getOptions());
				}else if(answer.equals("E")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[4],imageviews[4],application.getOptions());
				}else if(answer.equals("F")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[5],imageviews[5],application.getOptions());
				}else if(answer.equals("G")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[6],imageviews[6],application.getOptions());
				}else if(answer.equals("t")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[7],imageviews[7],application.getOptions());
				}else if(answer.equals("f")){
					ImageLoader.getInstance().displayImage("drawable://"+image_false[8],imageviews[8],application.getOptions());
				}
			}
		}
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
		private ImageView[] imageviews ;
		public Question_click(String[] correct,int suit_id,int question_no,int type,TextView answer_text_jiexi_false,LinearLayout analysis,ImageView answer_a,ImageView answer_b,ImageView answer_c,ImageView answer_d,ImageView answer_e,ImageView answer_f,ImageView answer_g,ImageView answer_true,ImageView answer_false,LinearLayout[] linear_array,
							  int group_index,int question_index,ViewPager viewPager_child) {
			this.answer_a = answer_a;
			this.answer_b = answer_b;
			this.answer_c = answer_c;
			this.answer_d = answer_d;
			this.answer_e = answer_e;
			this.answer_f = answer_f;
			this.answer_g = answer_g;
			this.answer_true = answer_true;
			this.answer_false = answer_false;
			imageviews = new ImageView[]{this.answer_a,this.answer_b,this.answer_c,this.answer_d,this.answer_e,this.answer_f,this.answer_g,this.answer_true,this.answer_false};
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
						setAnsWer_duoxuan("?");
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
		/**
		 * 设置多选的图片
		 */
		public void setDuoxuanImageView(int i,String answer,ImageView imageview){
			if(duoxuan_answer[i].equals("")){ //字符串为"" 表示没有选此选项
				duoxuan_answer[i]=answer;
				ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_true[i],imageview,application.getOptions());
			}else{
				duoxuan_answer[i]="";
				ImageLoader.getInstance().displayImage("drawable://"+image_duoxuan_default[i],imageview,application.getOptions());
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
			list_infos.get(group_index).getQuestions()[question_index].setDuoxuan(duoxuan_text);
		}
		public void setAnsWer_duoxuan(String answer){
			list_infos.get(group_index).getQuestions()[question_index].setAnswer(answer);
			list_infos.get(group_index).getQuestions()[question_index].setAnswertime(nowUserTime());
			int question_no = list_infos.get(group_index).getQuestions()[question_index].getNo();
			list_texts.get(question_no-1).setBackgroundResource(R.drawable.dp_r);
			list_texts_last.get(question_no-1).setBackgroundResource(R.drawable.dp_r);
			list_texts.get(question_no-1).setTextColor(Color.WHITE);
			list_texts_last.get(question_no-1).setTextColor(Color.WHITE);
			String duoxuan=answer;//用户答案  有,号的
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
			list_infos.get(group_index).getQuestions()[question_index].setAnswer(answer); //设置答案
			list_infos.get(group_index).getQuestions()[question_index].setAnswertime(nowUserTime());
			int question_no = list_infos.get(group_index).getQuestions()[question_index].getNo();
			list_texts.get(question_no-1).setBackgroundResource(R.drawable.dp_r);
			list_texts_last.get(question_no-1).setBackgroundResource(R.drawable.dp_r);
			list_texts.get(question_no-1).setTextColor(Color.WHITE);
			list_texts_last.get(question_no-1).setTextColor(Color.WHITE);
			if(list_infos.get(group_index).getQuestions().length!=1 &&(list_infos.get(group_index).getQuestions().length-1)>question_index){
				viewPager_child.setCurrentItem(question_index+1);
			} else if(index<index_sum-1){
				viewpager_parent.setCurrentItem(index+1);
			}

			//设置默认
			for (int j = 0; j <imageviews.length; j++) {
				imageviews[j].setImageResource(image_default[j]);
			}

			if(null !=imageView){
				imageView.setImageResource(image_true[image_index]);
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
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Reading_testingActivity.this)));
			params.add(new BasicNameValuePair("suit_id",suit_id+""));
			params.add(new BasicNameValuePair("question_no",question_no+""));
			params.add(new BasicNameValuePair("answer",answer));
			params.add(new BasicNameValuePair("answertime",answertime));
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Reading_testingActivity.this, handler, params, "submit_exercise_answer",0,1,false,MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_testingActivity.this).getSubmit_exercise_answer(),Reading_testingActivity.this));
			asyncLoadApi.execute();
		}
	}
	class viewholder{
		TextView timu,answer_text_a,answer_text_b,answer_text_c,answer_text_d,answer_text_e,answer_text_f,answer_text_g,answer_text_true,answer_text_false,answer_text_no,answer_text_jiexi_true,answer_text_jiexi_false,answer_text_person,answer_text_person_identity,answer_text_bentijiexi,answer_text_jiexi_content,answer_text_kaodian,pay_hint1,pay_hint2,temp_text;
		//购买会员显示
		TextView payRmb_vip;
		TextView payRmb_vip1;
		LinearLayout linear_vip,linear_jiexi_content;
		LinearLayout pay_hint_linear,linear_buttom_all;

		ImageView imageView,answer_a,answer_b,answer_c,answer_d,answer_e,answer_f,answer_g,answer_true,answer_false,answer_person;
		LinearLayout linear_answer_a,linear_answer_b,linear_answer_c,linear_answer_d,linear_answer_e,linear_answer_f,linear_answer_g,linear_answer_true,linear_answer_false,linear_answer_no,linear_buttom_kaodian,analysis,linear_head;
		//	ElasticScrollView_answer myScrollview;

		//笔记部分
		ImageView image_Collection,image_error,image_jiexi;
		TextView text_note,text_add_note,text_look_all,text_Collection,text_error;
		LinearLayout linear_Collection,linear_error,linear_answer_text,linear_notes;
		WebView webView;
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
							list_infos.get(index).setIsShowBottom_button(false);
						}else{
							setBottom_button_anim(true);
							list_infos.get(index).setIsShowBottom_button(true);
						}
						break;
					case MotionEvent.ACTION_UP:
						break;
				}
				return true;
			}
		});

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
				if(list_infos.get(position).getInfo_no()==-999){//材料题
					view=setViewPageItem_group(list_infos.get(position));
				}else{
					if(list_infos.get(position).getInfo_status()==1){//材料题
						view= setParent_ViewPagerItem(list_infos.get(position),position,true);
					}else{									//非材料题
						view= setParent_ViewPagerItem(list_infos.get(position),position,false);
					}
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
	/**
	 * 设置Group分页
	 */
	private View setViewPageItem_group(Create_test_suit_2Bean.infos infos){
		View view =View.inflate(this, R.layout.item_testing_viewpager2, null);
		TextView text1 = (TextView) view.findViewById(R.id.title);
		TextView desc = (TextView) view.findViewById(R.id.desc);
		desc.setTextColor(Fontcolor_3);
		text1.setText(infos.getTitle());
		desc.setText(infos.getDesc());
		return view;
	}
	//整理数据
	private void setData(){
		Intent intent = getIntent();
		list_infos = new ArrayList<Create_test_suit_2Bean.infos>();
		list_view = new ArrayList<View>();
		type = intent.getIntExtra("type", 0);
		if(type==0){
			bean = (Create_test_suit_2Bean) intent.getSerializableExtra("bean");
			suitid = bean.getId()+"";
			//Toast.makeText(this, "套题ID"+bean.getId(), 0).show();

			time_sum = bean.getTest_time_limit()*60*1000;
			time_sums = time_sum;
			for (int i = 0; i < bean.getGroups().length; i++) {
				Create_test_suit_2Bean.groups temp_group =bean.getGroups()[i];
				Create_test_suit_2Bean.infos temp_infos = new Create_test_suit_2Bean().new infos();
				temp_infos.setInfo_no(-999);  //把标题什么的弄成一个对象
				temp_infos.setTitle(temp_group.getTitle());
				temp_infos.setDesc(temp_group.getDesc());
				temp_infos.setIsShowBottom_button(true);
				list_infos.add(temp_infos);
				View view = new View(this);
				view.setTag(0);
				list_view.add(view);
				for (int j = 0; j <temp_group.getInfos().length; j++) {
					temp_group.getInfos()[j].setIsShowBottom_button(true);
					View view2 = new View(this);
					view2.setTag(0);
					list_view.add(view2);
					for (int j2 = 0; j2 < temp_group.getInfos()[j].getQuestions().length; j2++) {
						question_sum = question_sum+1;
						temp_group.getInfos()[j].getQuestions()[j2].setIndexes(question_sum);
					}
					list_infos.add(temp_group.getInfos()[j]);
				}
			}
			title_text1.setText(bean.getTest_type_name());
			list_texts = new ArrayList<TextView>();
			list_texts_last = new ArrayList<TextView>();
			list_view.add(setPauseUI(list_texts_last,1));//设置尾页
			linear_pause.addView(setPauseUI(list_texts,0)); //设置暂停页
			riggt_image.setImageBitmap(BitmapFactory.decodeFile(Test_pause));
		}else{ //type不为0的时候  也就是全部解析 或者错题解析的时候
			//riggt_image.setVisibility(View.GONE);//隐藏暂停按钮
			// title_text_linear.setPadding(0, 0, DensityUtil.DipToPixels(Reading_testingActivity.this,15), 0);
			title_text_linear.setVisibility(View.VISIBLE);//显示文字
			buttom_type =5;//关闭Activiy
			buttom_button.setText("返回");
			String st=intent.getStringExtra("title");
			title_text1.setText(st);
			infos_index = intent.getIntExtra("infos_index", 0);
			question_index = intent.getIntExtra("question_index", 0);
			suitid = intent.getStringExtra("suitid");
			list_infos = (List<infos>) intent.getSerializableExtra("list_infos");
			//设置标题栏  初始位置
			if(type==1){
				setTitleBG(false);
				title_text2.setText("1");
			}else if(type==2){
				setTitleBG(list_infos.get(infos_index).getQuestions()[question_index].getIsError());
				title_text2.setText(list_infos.get(infos_index).getQuestions()[question_index].getNo()+"");
			}

			for (int i = 0; i < list_infos.size(); i++) {
				list_infos.get(i).setIsShowBottom_button(true);
				if(list_infos.get(i).getInfo_no()==-999){
					View view2 = new View(this);
					view2.setTag(0);
					list_view.add(view2);
				}else{
					View view2 = new View(this);
					view2.setTag(0);
					list_view.add(view2);
					for (int j = 0; j < list_infos.get(i).getQuestions().length; j++) {
						question_sum = question_sum+1;
						list_infos.get(i).getQuestions()[j].setIndexes(question_sum);
					}
				}
			}
			setcollectImage(list_infos.get(infos_index).getQuestions()[question_index].getIs_collect());
		}
		//设置标题

		title_text3.setText(" / "+question_sum);
		list_click= new Question_click[question_sum];
		collection_textview = new TextView[question_sum];
		collection_imageview = new ImageView[question_sum];
		index_sum = list_view.size();
		chid_viewpagers = new Read_Question_Child_ViewPager[index_sum];
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

		LinearLayout linearLayout = null;
		for (int i = 0; i < list_infos.size(); i++) {
			if(list_infos.get(i).getInfo_no()==-999){ //组
				button_index=0;
				View view = View.inflate(this, R.layout.item_report, null);
				viewHolder.report_linear = (LinearLayout) view.findViewById(R.id.report_linear);
				viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
				viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
				viewHolder.text2.setVisibility(View.GONE);
				viewHolder.text1.setTextColor(Fontcolor_3);
				viewHolder.text1.setText( list_infos.get(i).getTitle());
				pause_linear.addView(view);
			}else{
				Create_test_suit_2Bean.questions[] questions = list_infos.get(i).getQuestions();
				for (int j = 0; j < questions.length; j++) {
					button_index++;
					Create_test_suit_2Bean.questions questino_bean = questions[j];
					if(button_index%5==1){ //开启新的一行
						linearLayout= new LinearLayout(this);
						viewHolder.report_linear.addView(linearLayout);
					}
					LinearLayout linearLayout2 = new LinearLayout(this);
					linearLayout2.setLayoutParams(params);
					linearLayout.addView(linearLayout2);
					linearLayout2.setGravity(Gravity.CENTER);
					TextView button = new TextView(this);
					button.setText(questino_bean.getNo()+"");
					button.setGravity(Gravity.CENTER);
					button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
					button.setTextColor(Fontcolor_3);
					button.setBackgroundResource(R.drawable.dp_k);
					button.setLayoutParams(params_button);
					//设置监听
					ButtonClick buttonClick = new ButtonClick(i,j,type);
					button.setOnClickListener(buttonClick);
					linearLayout2.addView(button);
					list_texts.add(button);
				}
			}

		}

		view_scrollview.setTag(1);
		return view_scrollview;

	}
	//点击题号 跳转到相应的题目
	class ButtonClick implements OnClickListener{
		private int index; //父ViewPager的坐标
		private int childviewager_index; //父ViewPager的坐标
		private int type=0; //0==暂停页面  1 viewPager最后一个页面
		public ButtonClick(int index,int childviewager_index,int type) {
			// TODO Auto-generated constructor stub
			this.index = index;
			this.childviewager_index = childviewager_index;
			this.type =type;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewpager_parent.setCurrentItem(index);
			if(null!=chid_viewpagers[index]){
				chid_viewpagers[index].setCurrentItem(childviewager_index);
			}
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

	private class myhandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Intent intent;
			Bundle bundle;
			switch (msg.what) {
				case 4: //提交答案成功

					Submit_exercise_suitBean sesBean=APPUtil.submit_test_suit(Reading_testingActivity.this);
					intent = new Intent(Reading_testingActivity.this, PracticeReportActivity.class);
					bundle = new Bundle();
					bundle.putSerializable("Submit_exercise_suitBean", sesBean);
					intent.putExtras(bundle);
					intent.putExtra("list_infos", (Serializable)list_infos);
					intent.putExtra("flg", 3);
					intent.putExtra("title",bean.getTest_type_name());
					intent.putExtra("suitid",suitid+"");
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					dialog.dismiss();
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					MyFlg.all_activitys.remove(Reading_testingActivity.this);
					finish();
					break;
				case 5:	//提交套题失败
					dialog.dismiss();
					break;
				case 9://点击考点 显示
					dialog.dismiss();
					Get_point_infoBean get_point_infoBean = APPUtil.Get_point_infoBean(Reading_testingActivity.this);
					if(null!=get_point_infoBean){
						answerDialog.initUI(get_point_infoBean.getPoints(), get_point_infoBean.getExe_c()+"",get_point_infoBean.getWro_d()+"", get_point_infoBean.getKpl());
						answerDialog.showDialog();
					}else{
						Toast.makeText(Reading_testingActivity.this, "获取考点信息失败", Toast.LENGTH_SHORT).show();
					}
					break;
				case 10: //、点击考点加载失败
					dialog.dismiss();
					break;
				case 101:
					showAlertDialog("提示", "答题时间到,请交卷", "取消", "确定");
					break;
				default:
					break;
			}
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
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(Reading_testingActivity.this)));
			params.add(new BasicNameValuePair("point_id",kaodianID));
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(Reading_testingActivity.this, handler, params, "get_point_info",9,10,"获取考点信息失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_testingActivity.this).getGet_point_info(),Reading_testingActivity.this));
			asyncLoadApi.execute();
		}
	}
	private void initID() {
		// TODO Auto-generated method stub
		pause_ui_framelayout =(FrameLayout) findViewById(R.id.pause_ui_framelayout);
		layoutLeft=(LinearLayout)findViewById(R.id.layoutLeft);
		title_text_linear=(LinearLayout)findViewById(R.id.title_text_linear);
		title_text1=(TextView)findViewById(R.id.title_text1);
		title_text2=(TextView)findViewById(R.id.title_text2);
		title_text3=(TextView)findViewById(R.id.title_text3);
		riggt_image = (ImageView)findViewById(R.id.riggt_image);
		viewpager_parent = (ViewPager)findViewById(R.id.viewpager);
		buttom_button=(Button)findViewById(R.id.buttom_button);
		title_bg = (RelativeLayout)findViewById(R.id.title_bg);
		//暂停页
		linear_pause = (LinearLayout)findViewById(R.id.linear_pause);
		pause_button=(Button)findViewById(R.id.pause_button);
		linear_Left_pause=(LinearLayout)findViewById(R.id.linear_Left_pause);
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
	/**
	 * 当前用时  （分）
	 * @return
	 */
	private String nowUserTime(){

		return (time_sums-time_sum)/1000/60+"";
	}
	//仿IOS对话框
	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(Reading_testingActivity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								MyFlg.all_activitys.remove(Reading_testingActivity.this);
								overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
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
		F_IOS_Dialog.showAlertDialog(Reading_testingActivity.this, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								break;
							case F_IOS_Dialog.BUTTON2:
								Toast.makeText(Reading_testingActivity.this, "时间到,提交测验！", Toast.LENGTH_SHORT).show();
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
		F_IOS_Dialog.showAlertDialogChoose(Reading_testingActivity.this, title,content, leftBtnText, rightBtnText,
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
	/**
	 * 提交套题答案
	 */
	private void clickReport(){
		dialog.show();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list_infos.size(); i++) {
			if(list_infos.get(i).getInfo_no()!=-999){
				for (int j = 0; j < list_infos.get(i).getQuestions().length; j++) {
					try {
						Create_test_suit_2Bean.questions question=list_infos.get(i).getQuestions()[j];
						if(!question.getAnswer().equals("") && !question.getAnswer().equals("null")){
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
			}
		}


		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(Reading_testingActivity.this));
		params.add("suit_id", bean.getId()+"");
		params.add("suit_answers", jsonArray.toString());
		params.add("used_time", nowUserTime());
/*					 Log.d("mylog", "套题提交");
					 Log.d("mylog", "提交ID："+bean.getId());
					 Log.d("mylog", "提交的答案："+jsonArray.toString());
					 Log.d("mylog", "URL="+MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_testingActivity.this).getSubmit_test_suit(),Reading_testingActivity.this));
*/					AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		client.setTimeout(30000);
		client.post(MyFlg.get_API_URl(application.getCommonInfo_API_functions(Reading_testingActivity.this).getSubmit_test_suit(),Reading_testingActivity.this), params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String json = Sup.UnZipString(responseBody);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(json);
					boolean update = jsonObject.getBoolean("status");
					if(update){//提交成功
						Submit_exercise_suitBean sesBean=APPUtil.submit_test_suit(json);
						Intent intent = new Intent(Reading_testingActivity.this, PracticeReportActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("Submit_exercise_suitBean", sesBean);
						intent.putExtras(bundle);
						intent.putExtra("list_infos", (Serializable)list_infos);
						intent.putExtra("flg", 3);
						intent.putExtra("title",bean.getTest_type_name());
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						MyFlg.all_activitys.remove(Reading_testingActivity.this);
						finish();
					}else{//提交失败
						String st = APPUtil.getsend_mobile_verificationcodeError(json);
						Toast.makeText(Reading_testingActivity.this, ""+st, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
			}
			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(Reading_testingActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}
		});
	}
	//计算剩下多少题没做
	private void setShengxia(){
		int shengxiaSum=0;
		shengxiaSum = Sup.getRemainSum(list_infos);
		if(shengxiaSum<=0){
			clickReport();
		}else{
			showAlertDialog2("提示", "还有"+shengxiaSum+"题没答，确认交卷？", "取消", "确定");
		}
	}
	/**
	 * 设置Title背景  整组解析的时候
	 * @param isError true 正确  false 错题
	 */
	private void setTitleBG(Boolean isError){
		if(isError){//错误
			title_bg.setBackgroundColor(Bgcolor_6);
		}else{//答案正确
			title_bg.setBackgroundColor(Bgcolor_5);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {//返回  点击继续
			case R.id.linear_Left_pause: //
				pause_ui_framelayout.startAnimation(slide_out_top);
				pause_ui_framelayout.setVisibility(View.GONE);
				myScrollview.smoothScrollTo(0, 0);
				myThread.go_on();
				break;
			case R.id.layoutLeft:
				if(type==0){
					showAlertDialogChoose("提示", "是否确认退出本次考试", "取消", "确定");
				}else{
					setResultForPracticeReportActivity();
					overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
					MyFlg.all_activitys.remove(Reading_testingActivity.this);
					finish();
				}

				break;
			case R.id.buttom_button://底部按钮
				switch (buttom_type) {
					case 0:
						viewpager_parent.setCurrentItem(index+1);
						if(null!=chid_viewpagers[index]){
							chid_viewpagers[index].setCurrentItem(0);
						}
						break;
					case 1: //下一题
						int questino_index = 0;
						if(null!=list_infos.get(index).getQuestion_index()){
							questino_index = list_infos.get(index).getQuestion_index();
						}
						if(list_infos.get(index).getQuestions().length!=1 &&(list_infos.get(index).getQuestions().length-1)>questino_index){
							chid_viewpagers[index].setCurrentItem(questino_index+1);
						} else if(index<index_sum-1){
							viewpager_parent.setCurrentItem(index+1); //滑动的时候index已经被加1了 所有后面不加了
							if(null!=chid_viewpagers[index]){
								chid_viewpagers[index].setCurrentItem(0);
							}

						}
						break;
					case 2: //交卷
						setShengxia();
						break;
					case 5://关闭Activity
						setResultForPracticeReportActivity();
						overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
						MyFlg.all_activitys.remove(Reading_testingActivity.this);
						finish();
						break;
					default:
						break;
				}

				break;
			case R.id.riggt_image://右上角开始 /暂停按钮   //收藏
				if(type==0){
					pause_ui_framelayout.setVisibility(View.VISIBLE);
					pause_ui_framelayout.startAnimation(slide_in_bottom);
					//user_time = user_time+(System.currentTimeMillis()-startTime);
					isRestStartTime=true;
					pause_time.setText("用时： "+(nowUserTime())+" 分钟  / "+bean.getTest_time_limit()+" 分钟");
					myThread.onPause();//暂停倒计时
					if(is_load_lastitem==true){
						pause_button.setText("交卷");
					}
				}else{//收藏
					if(ISCanDo()){
						Create_test_suit_2Bean.questions questionBean =list_infos.get(index).getQuestions()[Sup.getQuestionIndex(list_infos,index)];
						int suoyin = questionBean.getIndexes()-1;//索引
						int is_collect = questionBean.getIs_collect();
						if(is_collect==0){//为收藏
							Postcollect(questionBean.getQid()+"", 1,collection_textview[suoyin],collection_imageview[suoyin]);
						}else if(is_collect==1){//已收藏 变为收藏
							Postcollect(questionBean.getQid()+"", 0,collection_textview[suoyin],collection_imageview[suoyin]);
						}
					}
				}

				break;
			case R.id.pause_button://暂停页面底部按钮
				if(is_load_lastitem==true){
					//Toast.makeText(Reading_testingActivity.this, "交卷", 0).show();
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode ==KeyEvent.KEYCODE_BACK ) {
			if(type==0){
				if(pause_ui_framelayout.getVisibility()==View.VISIBLE){
					pause_ui_framelayout.startAnimation(slide_out_top);
					pause_ui_framelayout.setVisibility(View.GONE);
					myScrollview.smoothScrollTo(0, 0);
					//startTime = System.currentTimeMillis();
					myThread.go_on();
				}else{
					showAlertDialogChoose("提示", "是否确认退出本次考试", "取消", "确定");
				}
			}else{
				setResultForPracticeReportActivity();
				overridePendingTransition(R.anim.nullanim, R.anim.push_down_out);
				MyFlg.all_activitys.remove(Reading_testingActivity.this);
				finish();
			}
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myThread.onPause();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(index!=list_view.size()-1 &&list_infos.get(index).getInfo_no()!=-999){
			myThread.go_on();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//resultCode  1表示支付成功  -1 表示支付失败
		if(resultCode==RESULT_OK&&requestCode==0 && (MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status()==1 || application.getuserInfoBean(Reading_testingActivity.this).getMember_status()==2)){ //支付成功
			for (int i = 0; i <list_content_textviews.size(); i++) {
				list_pay_linears.get(i).setVisibility(View.GONE);
				list_content_textviews.get(i).setVisibility(View.VISIBLE);
			}
			if(MyApplication.getuserInfoBean(Reading_testingActivity.this).getMember_status()==1){
				for (int i = 0; i < list_pay_hints.size(); i++) {
					list_pay_hints.get(i).setVisibility(View.GONE);
				}
			}

		}else if(requestCode == 2&&resultCode == RESULT_OK){//写完笔记返回
			Edit_user_noteBean bean = (Edit_user_noteBean) data.getSerializableExtra("Edit_user_noteBean");
			int questionIndex=0;
			if(null==list_infos.get(index).getQuestion_index()){
				questionIndex= 0;
			}else{
				questionIndex=list_infos.get(index).getQuestion_index();
			}
			list_infos.get(index).getQuestions()[questionIndex].getNotes().getMe()[0].setContent(bean.getData().getContent());
			list_infos.get(index).getQuestions()[questionIndex].getNotes().getMe()[0].setTime_text(bean.getData().getTime_text());

			note_texts[0].setText(bean.getData().getAuthor_name());
			note_texts[1].setText(bean.getData().getTime_text());
			note_texts[2].setText(bean.getData().getContent());
			note_texts[2].setTextColor(Fontcolor_3);
			//note_texts[3].setVisibility(View.VISIBLE);

			if(bean.getData().getTime_text().trim().length()<=0){
				now_text_add_note.setText("添加笔记");
				note_texts[2].setTextColor(Fontcolor_7);
			}else{
				now_text_add_note.setText("编辑笔记");
				note_texts[2].setTextColor(Fontcolor_3);
			}
		}else if(requestCode == 100&&resultCode == RESULT_OK){//完善资料返回
			MyApplication.userInfoBean=APPUtil.getUser_isRegistered(Reading_testingActivity.this);
		}
	}
	private void setResultForPracticeReportActivity(){
		Intent intent = new Intent();
		intent.putExtra("list_infos", (Serializable)list_infos);
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
			Intent intent = new Intent(Reading_testingActivity.this, Image_ViewPager_Activity.class);
			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}
	}
}
