package cn.net.dingwei.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Create_exercise_suitBean;
import cn.net.dingwei.Bean.Create_test_suitBean;
import cn.net.dingwei.Bean.Create_test_suitBean.questions;
import cn.net.dingwei.Bean.Create_test_suit_2Bean;
import cn.net.dingwei.Bean.Create_test_suit_2Bean.infos;
import cn.net.dingwei.Bean.Get_point_infoBean;
import cn.net.dingwei.Bean.Submit_exercise_suitBean;
import cn.net.dingwei.myView.AnswerDialog;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;


/**
 * 练习报告
 * @author Administrator
 *
 */
public class PracticeReportActivity extends ParentActivity implements OnClickListener{
	private LinearLayout linear_Left,linaer1,linaer2,linaer3,linear_pay;
	private ImageView answer_person;
	private TextView title_tx,text1,text_num1,text_num2,answer_text_person,answer_text_person_identity,answer_text_bentijiexi,answer_text_jiexi_content,text_buttom1,text_xian1,text_buttom2,text_fen,payRmb_vip1,payRmb_vip ;
	private int right_sum,question_sum;
	private Create_exercise_suitBean bean;
	private Submit_exercise_suitBean sesBean;
	//private ProgressBar home_load_progressbar;
	//private DialogMenu dialogMenu;
	private int right_group_sum=0; //计算一组题的数量
	private int[] right_group_sums;
	private FYuanTikuDialog dialog;
	//测验提交答案
	private List<Create_test_suitBean.questions> list_questions;
	private List<List<Create_test_suitBean.questions>> list_group_question;//测验  分组 为了做错题解析
	private String title="";
	private LinearLayout linear_vip;
	//--------------点击考点显示的对话框------------------
	AnswerDialog answerDialog ;
	private TextView pay_hint1,pay_hint2; //快到期了显示
	private LinearLayout pay_hint_linear;
	private LinearLayout linear_webview;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Color_4=0,Color_3=0;
	private int Screen_width=0;
	//测验用 
	private String suitid="";
	private Create_Exercise_suit_2Bean create_Exercise_suit_2Bean; //练习
	private List<Create_test_suit_2Bean.infos> list_infos_all; //测验 全部解析
	private List<List<Create_test_suit_2Bean.infos>> list_infos_cuoti = new ArrayList<List<infos>>();//测验  装 每一组的数据
	private int now_group_index=-1;//测验 查看分解解析用到
	private int flg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_practice_report);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		MyFlg.listActivity.add(this);
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
		answerDialog = new AnswerDialog(this);
		initID();
		initData();
	}

	private void initID() {
		// TODO Auto-generated method stub
		linear_Left=(LinearLayout)findViewById(R.id.linear_Left);
		linaer1=(LinearLayout)findViewById(R.id.linaer1);
		linaer2=(LinearLayout)findViewById(R.id.linaer2);
		linaer3=(LinearLayout)findViewById(R.id.linaer3);
		linear_pay=(LinearLayout)findViewById(R.id.linear_pay);
		answer_person=(ImageView)findViewById(R.id.answer_person);
		text1=(TextView)findViewById(R.id.text1);
		title_tx=(TextView)findViewById(R.id.title_tx);
		text_fen=(TextView)findViewById(R.id.text_fen);
		text_buttom1=(TextView)findViewById(R.id.text_buttom1);
		text_buttom2=(TextView)findViewById(R.id.text_buttom2);
		text_xian1=(TextView)findViewById(R.id.text_xian1);
		text_num1=(TextView)findViewById(R.id.text_num1);
		text_num2 = (TextView)findViewById(R.id.text_num2);
		answer_text_person = (TextView)findViewById(R.id.answer_text_person);
		answer_text_person_identity = (TextView)findViewById(R.id.answer_text_person_identity);
		answer_text_bentijiexi = (TextView)findViewById(R.id.answer_text_bentijiexi);
		answer_text_jiexi_content = (TextView)findViewById(R.id.answer_text_jiexi_content);
		linear_webview = (LinearLayout)findViewById(R.id.linear_webview);
		//快到期了
		pay_hint1 = (TextView)findViewById(R.id.pay_hint1);
		pay_hint2 = (TextView)findViewById(R.id.pay_hint2);
		pay_hint_linear = (LinearLayout) findViewById(R.id.pay_hint_linear);
		//查看解析
		linear_vip = (LinearLayout)findViewById(R.id.linear_vip);
		payRmb_vip1=(TextView)findViewById(R.id.payRmb_vip1);
		payRmb_vip=(TextView)findViewById(R.id.payRmb_vip);
		payRmb_vip1.setTextColor(Fontcolor_3);
		payRmb_vip.setTextColor(Bgcolor_2);
		payRmb_vip.setOnClickListener(this);
		//设置颜色
		pay_hint2.setTextColor(Bgcolor_2);
		findViewById(R.id.linear_bg1).setBackgroundColor(Bgcolor_2);
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		findViewById(R.id.linear_bg).setBackgroundColor(Bgcolor_2);
		text1.setTextColor(Fontcolor_1);
		text_buttom1.setTextColor(Fontcolor_3);
		text_buttom2.setTextColor(Fontcolor_3);
		text_xian1.setBackgroundColor(Color_3);
		text_num1.setTextColor(Fontcolor_1);
		text_num2.setTextColor(Fontcolor_1);
		answer_text_person.setTextColor(Fontcolor_3);
		answer_text_jiexi_content.setTextColor(Fontcolor_3);
		answer_text_bentijiexi.setTextColor(Fontcolor_7);
		//设置监听
		linear_Left.setOnClickListener(this);
		linaer3.setOnClickListener(this);
		pay_hint2.setOnClickListener(this);
		linear_webview.setOnClickListener(this);

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if((flg==2&&(create_Exercise_suit_2Bean.getType().equals("qtlx")||create_Exercise_suit_2Bean.getType().equals("ctgg_ap")))||MyApplication.isShowVip==false){
			linear_pay.setVisibility(View.VISIBLE);
			linear_vip.setVisibility(View.GONE);
		}else{
			//设置快到期了的文字
			if(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==2){//会员快到期 给提示
				pay_hint1.setText(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status_name());
				pay_hint2.setText(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_price());
				pay_hint_linear.setVisibility(View.VISIBLE);
			}else if(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==1){
				pay_hint_linear.setVisibility(View.GONE);
			}
			//判断是否是会员  会员才能查看解析
			if(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==1 || MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==2){
				linear_pay.setVisibility(View.VISIBLE);
				linear_vip.setVisibility(View.GONE);
			}else{
				linear_pay.setVisibility(View.GONE);
				linear_vip.setVisibility(View.VISIBLE);
				payRmb_vip1.setText(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status_name());
				payRmb_vip.setText(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_price());
			}
		}
	}
	private void initData() {
		// TODO Auto-generated method stub
		//加载图片
		Intent intent = getIntent();
		sesBean = (Submit_exercise_suitBean) intent.getSerializableExtra("Submit_exercise_suitBean");
		flg = intent.getIntExtra("flg", 0);
		if(flg==0){//练习
			bean = (Create_exercise_suitBean) intent.getSerializableExtra("bean");
			//动态设置按钮
			setButtons(bean);
			title_tx.setText("练题报告");
			text1.setText(bean.getTitle()+"答对");
			text_num1.setText(right_sum+"");
			text_num2.setText("道 / "+question_sum+"道");
		}else if(flg ==1 ){//测验
			list_group_question = new ArrayList<List<questions>>();
			title = intent.getStringExtra("title");
			title_tx.setText(title);
			text1.setText("总分 ： "+sesBean.getScore_total());
			text_num1.setText(sesBean.getScoret()+"");
			text_fen.setVisibility(View.VISIBLE);

			String usertime=sesBean.getUsed_time();
			if(usertime.equals("false")){
				usertime="0";
			}
			text_num2.setText("用时 ："+usertime+" / "+sesBean.getTest_time_limit()+" （分钟）");

			list_questions=(List<questions>) intent.getSerializableExtra("list_questions");
			setButtons(list_questions);
		}else if(flg==2){//练习-阅读题 (材料题)
			create_Exercise_suit_2Bean = (Create_Exercise_suit_2Bean) intent.getSerializableExtra("bean");
			setButtons(create_Exercise_suit_2Bean);
			title_tx.setText("练题报告");
			text1.setText(create_Exercise_suit_2Bean.getTitle()+"答对");
			text_num1.setText(right_sum+"");
			text_num2.setText("道 / "+question_sum+"道");
		}else if(flg==3){//测验——阅读题 (材料题)
			List<Create_test_suit_2Bean.infos> list_infos = (List<infos>) intent.getSerializableExtra("list_infos");
			suitid= intent.getStringExtra("suitid");
			title = intent.getStringExtra("title");
			title_tx.setText(title);
			setButtons_reading_testing(list_infos);

			text1.setText("总分 ： "+sesBean.getScore_total());
			text_fen.setVisibility(View.VISIBLE);
			text_num1.setText(sesBean.getScoret()+"");
			String usertime=sesBean.getUsed_time();
			if(usertime.equals("false")){
				usertime="0";
			}
			text_num2.setText("用时 ："+usertime+" / "+sesBean.getTest_time_limit()+" （分钟）");
		}

		//设置数据
		//if( flg!=3){
		//加载头像
		if(null != sesBean.getConclusion().getBy().getIcon()){
			ImageLoader.getInstance().displayImage(sesBean.getConclusion().getBy().getIcon(),answer_person,application.getOptions());
		}
		answer_text_person.setText(sesBean.getConclusion().getBy().getN());
		if(sesBean.getConclusion().getBy().getIntro().equals("null")){
			answer_text_person_identity.setText("");
			answer_text_person_identity.setVisibility(View.GONE);
		}else{
			answer_text_person_identity.setText(sesBean.getConclusion().getBy().getIntro());
		}
		answer_text_jiexi_content.setText(sesBean.getConclusion().getContent());
		setWebView( answer_text_jiexi_content, sesBean.getConclusion().getContent());
		setButtomItem(sesBean);
		//}
	}
	/**
	 * 动态设置按钮  测验-阅读题
	 */
	private void setButtons_reading_testing(List<Create_test_suit_2Bean.infos> list_infos){
		int left = DensityUtil.DipToPixels(this, 10);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((Screen_width-left*2)/5, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 50, 0, 0);
		LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(DensityUtil.DipToPixels(this, 30), DensityUtil.DipToPixels(this, 30));

		ViewHolder viewHolder = new ViewHolder();
		LinearLayout linearLayout = null;
		int button_index=0;//计算数量
		int error_sum=0;
		int group_sums=0;//计算组的数量
		//点击题号的时候  全组解析
		list_infos_all = new ArrayList<Create_test_suit_2Bean.infos>();
		List<Create_test_suit_2Bean.infos> list_infos_cuotijiexi = null;
		for (int i = 0; i < list_infos.size(); i++) {
			if(list_infos.get(i).getInfo_no()==-999){ //组
				list_infos_cuotijiexi = new ArrayList<Create_test_suit_2Bean.infos>();
				list_infos_cuoti.add(list_infos_cuotijiexi);
				group_sums =group_sums+1;
				button_index=0;
				error_sum=0;
				View view = View.inflate(this, R.layout.item_report, null);
				linaer1.addView(view);
				viewHolder.report_linear = (LinearLayout) view.findViewById(R.id.report_linear);
				viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
				viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
				//设置颜色
				viewHolder.text1.setTextColor(Fontcolor_3);
				viewHolder.text2.setTextColor(Bgcolor_2);
				//设置数据
				viewHolder.text1.setText(list_infos.get(i).getTitle());
				Reading_testingClick testingClick_cuotijiexi = new Reading_testingClick(list_infos_cuotijiexi, title, 0, 0,1,group_sums-1);
				viewHolder.text2.setOnClickListener(testingClick_cuotijiexi);
			}else{
				Create_test_suit_2Bean.questions[] questions = list_infos.get(i).getQuestions();
				//错题解析的时候 把错题弄出来
				List<Create_test_suit_2Bean.questions> list_temp_question = new ArrayList<Create_test_suit_2Bean.questions>();
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

					String correct="";
					String answer="";
					if(null==questino_bean.getAnswer()){
						answer="null";
					}else{
						answer= questino_bean.getAnswer().trim().replace(",", "");
					}
					for (int k = 0; k < questino_bean.getCorrect().length; k++) {
						correct=correct+questino_bean.getCorrect()[k];
					}
					if(answer.equals("null") || answer.equals("")){//没答
						button.setBackgroundResource(R.drawable.dp_k);
						button.setTextColor(Fontcolor_3);
						viewHolder.text2.setVisibility(View.VISIBLE);
						error_sum=error_sum+1;
						list_infos.get(i).getQuestions()[j].setIsError(false);
						list_infos.get(i).getQuestions()[j].setIndexes(error_sum);
						list_temp_question.add(list_infos.get(i).getQuestions()[j]);
					}else if(answer.equals(correct)){//答对
						right_sum =right_sum+1;//计算答对了多少题
						button.setBackgroundResource(R.drawable.dp_r);
						button.setTextColor(Fontcolor_1);
						list_infos.get(i).getQuestions()[j].setIsError(true);
					}else{									//答错
						button.setBackgroundResource(R.drawable.dp_w);
						button.setTextColor(Fontcolor_1);
						viewHolder.text2.setVisibility(View.VISIBLE);
						error_sum=error_sum+1;
						list_infos.get(i).getQuestions()[j].setIsError(false);
						list_infos.get(i).getQuestions()[j].setIndexes(error_sum);
						list_temp_question.add(list_infos.get(i).getQuestions()[j]);
					}
					//设置监听
					linearLayout2.addView(button);
					Reading_testingClick testingClick = new Reading_testingClick(list_infos_all, title, i-group_sums, j,2,-1);
					button.setOnClickListener(testingClick);
				}
				list_infos.get(i).setQuestion_index(0);
				list_infos_all.add(list_infos.get(i));
				//错题解析
				if(list_temp_question.size()>0){
					Create_test_suit_2Bean.questions []temp_questions=new Create_test_suit_2Bean.questions[list_temp_question.size()];
					for (int k = 0; k < list_temp_question.size(); k++) {
						temp_questions[k] = list_temp_question.get(k);
					}
					Create_test_suit_2Bean.infos tem_infos = new Create_test_suit_2Bean().new infos();
					//tem_infos= list_infos.get(i);
					tem_infos.setInfo_status(list_infos.get(i).getInfo_status());
					tem_infos.setInfo_no(list_infos.get(i).getInfo_no());
					tem_infos.setInfo(list_infos.get(i).getInfo());
					tem_infos.setQuestions(temp_questions);
					list_infos_cuotijiexi.add(tem_infos);
				}
			}

		}

	}
	/**
	 * 测验—— 点击题号查看全组解析
	 */
	class Reading_testingClick implements OnClickListener{
		private List<Create_test_suit_2Bean.infos> list_infos;
		private String title;
		private int infos_index=0; //父ViewPager的位置
		private int question_index=0; //子ViewPager的位置
		private int group_index=-1;
		/**
		 * 0 正常    1 错题解析    2  全部解析
		 */
		private int type=0;
		public Reading_testingClick(List<Create_test_suit_2Bean.infos> list_infos,String title,int infos_index,int question_index,int type,int group_index) {
			// TODO Auto-generated constructor stub
			this.list_infos = list_infos;
			this.title = title;
			this.infos_index = infos_index;
			this.question_index = question_index;
			this.type = type;
			this.group_index = group_index;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(type==1 && list_infos.size()<=0){
				Toast.makeText(PracticeReportActivity.this, "该组没有错题", 0).show();
				return;
			}
			dialog.show();
			Intent intent = new Intent(PracticeReportActivity.this, Reading_testingActivity.class);
			intent.putExtra("type", type);
			intent.putExtra("title", title);
			intent.putExtra("infos_index", infos_index);
			intent.putExtra("question_index", question_index);
			intent.putExtra("suitid", suitid);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			if(type==2){//全部解析
				intent.putExtra("list_infos", (Serializable)list_infos_all);
				startActivityForResult(intent, 3);
			}else if(type==1){
				now_group_index = group_index;
				intent.putExtra("list_infos", (Serializable)list_infos_cuoti.get(group_index));
				startActivityForResult(intent, 4);
			}

			dialog.dismiss();
		}

	}
	/**
	 * 把所有infos 集合在一起 使其Groups只要一个分组 只要1个infos
	 * @param bean
	 */
	private Create_Exercise_suit_2Bean setCreate_Exercise_suit_2Bean(Create_Exercise_suit_2Bean bean){
		Create_Exercise_suit_2Bean temp_bean=new Create_Exercise_suit_2Bean();
		temp_bean.setTitle(bean.getTitle());
		Create_Exercise_suit_2Bean.groups[] temp_groups = new Create_Exercise_suit_2Bean.groups[1];
		List<Create_Exercise_suit_2Bean.infos> list = new ArrayList<Create_Exercise_suit_2Bean.infos>();
		for (int i = 0; i < bean.getGroups().length; i++) {
			Create_Exercise_suit_2Bean.infos[] infos = bean.getGroups()[i].getInfos();
			for (int j = 0; j < infos.length; j++) {
				list.add(infos[j]);
			}
		}
		Create_Exercise_suit_2Bean.infos[] tem_infos = new Create_Exercise_suit_2Bean.infos[list.size()];
		for (int i = 0; i < list.size(); i++) {
			tem_infos[i] = list.get(i);
		}
		Create_Exercise_suit_2Bean.groups temp_group = new Create_Exercise_suit_2Bean().new groups();
		temp_group.setInfos(tem_infos);
		temp_groups[0] = temp_group;
		temp_bean.setGroups(temp_groups);
		return temp_bean;
	}
	/**
	 * 动态设置按钮  阅读题 材料题
	 */
	private void setButtons(Create_Exercise_suit_2Bean bean){
		//Create_Exercise_suit_2Bean temp_bean=setCreate_Exercise_suit_2Bean(bean);
		int left = DensityUtil.DipToPixels(this, 10);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((Screen_width-left*2)/5, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 50, 0, 0);
		LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(DensityUtil.DipToPixels(this, 30), DensityUtil.DipToPixels(this, 30));
		ViewHolder viewHolder = new ViewHolder();

		int infos_index=0;//记录是第几个材料
		for (int i = 0; i <bean.getGroups().length; i++) {
			View view = View.inflate(this, R.layout.item_report, null);
			linaer1.addView(view);
			viewHolder.report_linear = (LinearLayout) view.findViewById(R.id.report_linear);
			viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
			viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
			//设置颜色
			viewHolder.text1.setTextColor(Fontcolor_3);
			viewHolder.text2.setTextColor(Bgcolor_2);
			//设置数据
			viewHolder.text1.setText("第"+(i+1)+"组");
			Create_Exercise_suit_2Bean.infos[] infos=bean.getGroups()[i].getInfos();
			int question_sum_item=0;
			int error_sum=0;
			LinearLayout linearLayout = null;
			for (int z = 0; z <infos.length; z++) {
				infos_index = infos_index+1;
				for (int j = 0; j <infos[z].getQuestions().length; j++) {
					question_sum= question_sum+1;
					Create_Exercise_suit_2Bean.questions question= infos[z].getQuestions()[j];
					question_sum_item = question_sum_item+1;
					if((question_sum_item)%5==1){ //开启新的一行
						linearLayout= new LinearLayout(this);
						viewHolder.report_linear.addView(linearLayout);
					}
					LinearLayout linearLayout2 = new LinearLayout(this);
					linearLayout2.setLayoutParams(params);
					linearLayout.addView(linearLayout2);
					linearLayout2.setGravity(Gravity.CENTER);
					TextView button = new TextView(this);
					button.setText(question.getNo()+"");
					button.setGravity(Gravity.CENTER);
					button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
					String correct="";
					String answer="";
					if(null==question.getAnswer()){
						answer="null";
					}else{
						answer= question.getAnswer().trim().replace(",", "");
					}
					for (int k = 0; k < question.getCorrect().length; k++) {
						correct=correct+question.getCorrect()[k];
					}
					if(answer.equals("null") || answer.equals("")){//没答
						button.setBackgroundResource(R.drawable.dp_k);
						button.setTextColor(Fontcolor_3);
						viewHolder.text2.setVisibility(View.VISIBLE);
						error_sum=error_sum+1;
					}else if(answer.equals(correct)){//答对
						right_sum =right_sum+1;//计算答对了多少题
						button.setBackgroundResource(R.drawable.dp_r);
						button.setTextColor(Fontcolor_1);
					}else{									//答错
						button.setBackgroundResource(R.drawable.dp_w);
						button.setTextColor(Fontcolor_1);
						viewHolder.text2.setVisibility(View.VISIBLE);
						error_sum=error_sum+1;
					}
					Read_question_button button_click = new Read_question_button(5,0,infos_index-1,j);
					button.setOnClickListener(button_click);
					linearLayout2.addView(button);
					button.setLayoutParams(params_button);
					viewHolder.text2.setOnClickListener(new Read_question_cuotijiexi(bean,i,error_sum));
				}
			}
		}
	}
	class Read_question_cuotijiexi implements OnClickListener{
		private int group_index;
		private int error_sum;
		public Read_question_cuotijiexi(Create_Exercise_suit_2Bean bean,int group_index,int error_sum) {
			// TODO Auto-generated constructor stub
			this.group_index = group_index;
			this.error_sum = error_sum;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(error_sum<=0){
				Toast.makeText(PracticeReportActivity.this, "没有错题", 0).show();
				return;
			}
			dialog.show();
			Intent intent = new Intent(PracticeReportActivity.this, Reading_QuestionsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", create_Exercise_suit_2Bean);
			intent.putExtras(bundle);
			intent.putExtra("type", 1);
			intent.putExtra("group_index", group_index);
			intent.putExtra("flg", 2);
			startActivityForResult(intent, 2);
			overridePendingTransition(R.anim.push_up_in, R.anim.nullanim);
			dialog.dismiss();
		}

	}

	class Read_question_button implements OnClickListener{
		private int type;
		private int group_index; //第几组
		private int infos_index;
		private int question_index;
		public Read_question_button(int type,int group_index,int infos_index,int question_index) {
			// TODO Auto-generated constructor stub
			this.type=type;
			this.group_index = group_index;
			this.infos_index =infos_index;
			this.question_index = question_index;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(PracticeReportActivity.this,Reading_QuestionsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("type", type);
			intent.putExtra("group_index", group_index);
			intent.putExtra("infos_index", infos_index);
			intent.putExtra("question_index", question_index);
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", create_Exercise_suit_2Bean);
			intent.putExtras(bundle);
			startActivityForResult(intent, 2);
			overridePendingTransition(R.anim.push_up_in, R.anim.nullanim);
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
			String point = mUrl.substring(mUrl.indexOf("/point/")+7, mUrl.length());
			if(Sup.isNumeric(point)){
				dialog.show();
				List <NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("a",MyFlg.a));
				params.add(new BasicNameValuePair("ver",MyFlg.android_version));
				params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(PracticeReportActivity.this)));
				params.add(new BasicNameValuePair("point_id",point));
				AsyncLoadApi asyncLoadApi = new AsyncLoadApi(PracticeReportActivity.this, myhandler, params, "get_point_info",9,10,"获取考点信息失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(PracticeReportActivity.this).getGet_point_info(),PracticeReportActivity.this));
				asyncLoadApi.execute();
			}

		}
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Bgcolor_2);
			ds.setUnderlineText(false);    //去除超链接的下划线
		}
	}
	/**
	 * 加载WebView
	 * @param webView
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
	 * 动态添加最下面的条目
	 */
	private void setButtomItem(Submit_exercise_suitBean sesBean){


		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Screen_width, 1);
		ViewHolder2 viewHolder = new ViewHolder2();
		for (int i = 0; i < sesBean.getConclusion().getSuggests().length; i++) {
			Submit_exercise_suitBean.suggests suggests = sesBean.getConclusion().getSuggests()[i];
			View view = View.inflate(this, R.layout.item_home_list_head2, null);
			viewHolder.text = (TextView) view.findViewById(R.id.text);
			viewHolder.linear_bg = (LinearLayout) view.findViewById(R.id.linear_bg);


			buttomLinear_item buttomLinear_item = new buttomLinear_item(i);
			viewHolder.linear_bg.setOnClickListener(buttomLinear_item);
			viewHolder.text.setText(suggests.getTitle());
			viewHolder.text.setTextColor(Fontcolor_3);
			linaer2.addView(view);
			TextView textView = new TextView(this);
			textView.setBackgroundColor(Color_4);
			textView.setLayoutParams(params);
			linaer2.addView(textView);
		}
	}
	class buttomLinear_item implements OnClickListener{
		private int i;
		public buttomLinear_item(int i) {
			// TODO Auto-generated constructor stub
			this.i  = i;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Submit_exercise_suitBean.action_option action_option = sesBean.getConclusion().getSuggests()[i].getAction().getAction_option();

			List <NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("a",MyFlg.a));
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(PracticeReportActivity.this)));
			params.add(new BasicNameValuePair("exercises_type",action_option.getType()));

			//action_option.setType("ctgg");
			if(action_option.getType().equals("ctgg")&&MyApplication.Getget_user_baseinfo(PracticeReportActivity.this).getCtgg_need_vip()==1&&(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()!=1 && MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()!=2)){
				//不是会员
				MyFlg.showAlertDialogChoose("提示", MyApplication.Getget_user_baseinfo(PracticeReportActivity.this).getCtgg_paymsg(), MyApplication.Getget_user_baseinfo(PracticeReportActivity.this).getCtgg_paybtn_yes(),MyApplication.Getget_user_baseinfo(PracticeReportActivity.this).getCtgg_paybtn_no(),PracticeReportActivity.this);
			}else if(action_option.getType().equals("ctgg")&&MyApplication.Getget_user_baseinfo(PracticeReportActivity.this).getCtgg_need_vip()==1&&(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==1 || MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==2)){	//是会员 或者不需要判断
				dialog.show();
				params.add(new BasicNameValuePair("exercises_option","["+action_option.getOption()[0]+"]"));
				AsyncLoadApi asyncLoadApi = new AsyncLoadApi(PracticeReportActivity.this, myhandler, params, "create_exercise_suit",0,1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(PracticeReportActivity.this).getCreate_exercise_suit(),PracticeReportActivity.this));
				asyncLoadApi.execute();
			}else if(action_option.getMethod().equals("new")){//新建
				dialog.show();
				params.add(new BasicNameValuePair("exercises_option","["+action_option.getOption()[0]+"]"));
				AsyncLoadApi asyncLoadApi = new AsyncLoadApi(PracticeReportActivity.this, myhandler, params, "create_exercise_suit",0,1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(PracticeReportActivity.this).getCreate_exercise_suit(),PracticeReportActivity.this));
				asyncLoadApi.execute();
			}else if(action_option.getMethod().equals("continue")){//继续
				dialog.show();
				params.add(new BasicNameValuePair("suit_id",action_option.getOption()[0]+""));
				AsyncLoadApi asyncLoadApi = new AsyncLoadApi(PracticeReportActivity.this, myhandler, params,"get_exercise_suit",3,4,"创建失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(PracticeReportActivity.this).getGet_exercise_suit(),PracticeReportActivity.this));
				asyncLoadApi.execute();
			}

		}

	}
	private Handler myhandler = new Handler(){
		public void handleMessage(Message msg) {
			Create_Exercise_suit_2Bean create_Exercise_suit_2Bean;
			Intent intent;
			Bundle bundle;
			switch (msg.what) {
				case 0://创建新的
					dialog.dismiss();
					create_Exercise_suit_2Bean = APPUtil.create_exercise_suit_2(PracticeReportActivity.this);
					if(null==create_Exercise_suit_2Bean){
						Toast.makeText(PracticeReportActivity.this, "创建失败", 0).show();
						return;
					}
					intent = new Intent(PracticeReportActivity.this, Reading_QuestionsActivity.class);
					bundle = new Bundle();
					bundle.putSerializable("bean", create_Exercise_suit_2Bean);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					finish();
					break;
				case 1:
					dialog.dismiss();
					break;
				case 3://继续练习
					dialog.dismiss();
					create_Exercise_suit_2Bean = APPUtil.create_exercise_suit_2_go_on(PracticeReportActivity.this);
					for (int i = 0; i < create_Exercise_suit_2Bean.getGroups().length; i++) {
						for (int j = 0; j < create_Exercise_suit_2Bean.getGroups()[i].getInfos().length; j++) {
							for (int j2 = 0; j2 <create_Exercise_suit_2Bean.getGroups()[i].getInfos()[j].getQuestions().length; j2++) {
								create_Exercise_suit_2Bean.getGroups()[i].getInfos()[j].getQuestions()[j2].setAnswer(create_Exercise_suit_2Bean.getGroups()[i].getInfos()[j].getQuestions()[j2].getAnswer().replace(",", ""));
							}
						}
					}

					intent = new Intent(PracticeReportActivity.this, Reading_QuestionsActivity.class);
					bundle = new Bundle();
					bundle.putSerializable("bean", create_Exercise_suit_2Bean);
					intent.putExtras(bundle);
					intent.putExtra("type", 3);
					intent.putExtra("group_index", create_Exercise_suit_2Bean.getLast_group());
					intent.putExtra("infos_index", create_Exercise_suit_2Bean.getLast_infos());
					intent.putExtra("question_index", create_Exercise_suit_2Bean.getLast_question());
					startActivity(intent);
					PracticeReportActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					finish();
				/*dialog.dismiss();
				 bean = APPUtil.get_exercise_suit(PracticeReportActivity.this);
				if(null==bean){
					Toast.makeText(PracticeReportActivity.this, "创建失败", 0).show();
					return;
				}
				//把Anser里面的、号去掉
				for (int i = 0; i <bean.getGroups().length; i++) {
					for (int j = 0; j <bean.getGroups()[i].getQuestions().length; j++) {
						bean.getGroups()[i].getQuestions()[j].setAnswer(bean.getGroups()[i].getQuestions()[j].getAnswer().replace(",", ""));
					}
				}
				
				intent = new Intent(PracticeReportActivity.this, AnswerActivity.class);
				bundle = new Bundle();
				bundle.putSerializable("bean", bean);
				intent.putExtras(bundle);
				intent.putExtra("index", bean.getLast_question());
				intent.putExtra("WHICH_GROUP", bean.getLast_group());
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);*/
					break;
				case 4:
					dialog.dismiss();
					break;

				case 9://点击考点 显示
					dialog.dismiss();
					Get_point_infoBean get_point_infoBean = APPUtil.Get_point_infoBean(PracticeReportActivity.this);
					if(null!=get_point_infoBean){
						answerDialog.initUI(get_point_infoBean.getPoints(), get_point_infoBean.getExe_c()+"",get_point_infoBean.getWro_d()+"", get_point_infoBean.getKpl());
						answerDialog.showDialog();
					}else{
						Toast.makeText(PracticeReportActivity.this, "获取考点信息失败", 0).show();
					}

					break;
				case 10: //、点击考点加载失败
					dialog.dismiss();
					break;

				default:
					break;
			}
		};
	};


	/**
	 * 动态设置按钮 测验提交过来的
	 */
	private void setButtons(List<Create_test_suitBean.questions> list_questions){
		int left = DensityUtil.DipToPixels(this, 10);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((Screen_width-left*2)/5, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 50, 0, 0);
		LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(DensityUtil.DipToPixels(this, 30), DensityUtil.DipToPixels(this, 30));
		ViewHolder viewHolder = new ViewHolder();

		int question_sum_item=0;
		int groupSum = 0; //计算本题之前有几个Group
		LinearLayout linearLayout = null;
		List<Create_test_suitBean.questions> list_temp=new ArrayList<Create_test_suitBean.questions>();
		for (int i = 0; i <list_questions.size(); i++) {
			Create_test_suitBean.questions question = list_questions.get(i);
			View view = null;
			if(question.getNo()==-999){ //分类
				list_temp = new ArrayList<Create_test_suitBean.questions>();
				list_group_question.add(list_temp);

				groupSum = groupSum+1;
				question_sum_item = 0;
				view = View.inflate(this, R.layout.item_report, null);
				linaer1.addView(view);
				viewHolder.report_linear = (LinearLayout) view.findViewById(R.id.report_linear);
				viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
				viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
				//设置颜色
				viewHolder.text1.setTextColor(Fontcolor_3);
				viewHolder.text2.setTextColor(Bgcolor_2);
				viewHolder.text2.setVisibility(View.GONE);

				//事件
				ButtonClick2 buttonClick = new ButtonClick2(0,list_temp,1);
				viewHolder.text2.setOnClickListener(buttonClick);
				//设置数据
				viewHolder.text1.setText(question.getTitle());
			}else{
				//按钮
				question_sum_item = question_sum_item+1;
				if((question_sum_item)%5==1){ //开启新的一行
					linearLayout= new LinearLayout(this);
					viewHolder.report_linear.addView(linearLayout);
				}
				LinearLayout linearLayout2 = new LinearLayout(this);
				linearLayout2.setLayoutParams(params);
				if(null == linearLayout){

				}
				linearLayout.addView(linearLayout2);
				linearLayout2.setGravity(Gravity.CENTER);
				TextView button = new TextView(this);
				button.setText(question.getNo()+"");
				button.setGravity(Gravity.CENTER);
				button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				String correct="";
				String answer = question.getAnswer().trim().replace(",", "");
				for (int k = 0; k < question.getCorrect().length; k++) {
					correct=correct+question.getCorrect()[k];
				}
				if(answer.equals("null") || answer.equals("")){//没答
					button.setBackgroundResource(R.drawable.dp_k);
					button.setTextColor(Fontcolor_3);

					list_temp.add(question);
					viewHolder.text2.setVisibility(View.VISIBLE);
				}else if(answer.equals(correct)){//答对
					//right_sum =right_sum+1;//计算答对了多少题
					//right_group_sum = right_group_sum+1;
					button.setBackgroundResource(R.drawable.dp_r);
					button.setTextColor(Fontcolor_1);
				}else{									//答错
					button.setBackgroundResource(R.drawable.dp_w);
					button.setTextColor(Fontcolor_1);
					list_temp.add(question);
					viewHolder.text2.setVisibility(View.VISIBLE);
				}
				//添加Button按钮事件
				ButtonClick2 buttonClick = new ButtonClick2(i-groupSum,list_questions,0);
				button.setOnClickListener(buttonClick);
				linearLayout2.addView(button);
				button.setLayoutParams(params_button);
			}
		}
	}
	/**
	 * 动态设置按钮
	 */
	private void setButtons(Create_exercise_suitBean bean){
		int left = DensityUtil.DipToPixels(this, 10);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((Screen_width-left*2)/5, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 50, 0, 0);
		LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(DensityUtil.DipToPixels(this, 30), DensityUtil.DipToPixels(this, 30));
		ViewHolder viewHolder = new ViewHolder();
		right_group_sums =new int[bean.getGroups().length];
		for (int i = 0; i < bean.getGroups().length; i++) {
			View view = View.inflate(this, R.layout.item_report, null);
			viewHolder.report_linear = (LinearLayout) view.findViewById(R.id.report_linear);
			viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
			viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
			//设置颜色
			viewHolder.text1.setTextColor(Fontcolor_3);
			viewHolder.text2.setTextColor(Bgcolor_2);
			//设置数据
			viewHolder.text1.setText("第"+(i+1)+"组");

			Create_exercise_suitBean.groups group = bean.getGroups()[i];
			right_group_sum = 0;
			LinearLayout linearLayout = null;
			for (int j = 0; j <group.getQuestions().length ; j++) {
				question_sum = question_sum+1; //计算总题数
				Create_exercise_suitBean.questions question= group.getQuestions()[j];
				if((j+1)%5==1){ //开启新的一行
					linearLayout= new LinearLayout(this);
					viewHolder.report_linear.addView(linearLayout);
				}
				LinearLayout linearLayout2 = new LinearLayout(this);
				linearLayout2.setLayoutParams(params);
				linearLayout.addView(linearLayout2);
				linearLayout2.setGravity(Gravity.CENTER);
				TextView button = new TextView(this);
				button.setText(question.getNo()+"");
				button.setGravity(Gravity.CENTER);
				button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				String correct="";
				for (int k = 0; k < question.getCorrect().length; k++) {
					correct=correct+question.getCorrect()[k];
				}
				if(question.getAnswer().equals("null")){//没答
					button.setBackgroundResource(R.drawable.dp_k);
					button.setTextColor(Fontcolor_3);
				}else if(question.getAnswer().equals(correct)){//答对
					right_sum =right_sum+1;//计算答对了多少题
					right_group_sum = right_group_sum+1;
					button.setBackgroundResource(R.drawable.dp_r);
					button.setTextColor(Fontcolor_1);
				}else{									//答错
					button.setBackgroundResource(R.drawable.dp_w);
					button.setTextColor(Fontcolor_1);
				}
				//添加Button按钮事件
				ButtonClick buttonClick = new ButtonClick(2, i, 1, question.getNo()-1);
				button.setOnClickListener(buttonClick);
				linearLayout2.addView(button);
				button.setLayoutParams(params_button);
			}
			//设置错题解析监听
			ButtonClick buttonClick_text = new ButtonClick(1, i, 0, 0);
			viewHolder.text2.setOnClickListener(buttonClick_text);
			right_group_sums[i]=right_group_sum;
			linaer1.addView(view);
		}
	}
	//测验
	class ButtonClick2 implements OnClickListener{
		private int index;
		private List<questions> list_questions;
		private int type=0; //0 是点击BUtton 全组解析   1 是错题解析 
		public ButtonClick2(int index, List<questions> list_questions,int type) {
			// TODO Auto-generated constructor stub
			this.index = index;
			this.list_questions = list_questions;
			this.type = type;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(type==1 && list_questions.size()<=0){
				Toast.makeText(PracticeReportActivity.this, "没有错题", 0).show();
				return;
			}
			dialog.show();
			Intent intent = new Intent(PracticeReportActivity.this, Testting_ParseActivity.class);
			intent.putExtra("index", index);
			intent.putExtra("title", title);
			intent.putExtra("list_questions", (Serializable)list_questions);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			overridePendingTransition(R.anim.push_up_in, R.anim.nullanim);
			dialog.dismiss();
		}

	}
	class ButtonClick implements OnClickListener{
		private int type;
		private int WHICH_GROUP;
		private int flg;
		private int index;
		public ButtonClick(int type,int WHICH_GROUP,int flg,int index) {
			// TODO Auto-generated constructor stub
			this.type =type;
			this.WHICH_GROUP = WHICH_GROUP;
			this.flg = flg;
			this.index = index;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() ==R.id.text2){
				if(right_group_sums[WHICH_GROUP]==bean.getGroups()[WHICH_GROUP].getQuestions().length){
					Toast.makeText(PracticeReportActivity.this, "第"+(WHICH_GROUP+1)+"组没有错题", 0).show();
				}else{
					StartNext(type, WHICH_GROUP, flg, index);
				}

			}else{
				StartNext(type, WHICH_GROUP, flg, index);
			}
		}

	}
	/**
	 * 者看解析
	 * type //0：正常    1:错题解析 2：整组解析
	 *  WHICH_GROUP 当前第几组
	 *  flg 0 解析当前组  1  解析全部
	 *  index 第几道题
	 */
	private void StartNext(int type,int WHICH_GROUP,int flg,int index){
		/*Intent intent = new Intent(this, AnswerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", bean);
		intent.putExtras(bundle);
		intent.putExtra("type", type);
		if(type!=0){
			intent.putExtra("WHICH_GROUP", WHICH_GROUP);
		}else{
			intent.putExtra("WHICH_GROUP", WHICH_GROUP+1);
			MyFlg.all_activitys.remove(PracticeReportActivity.this);
			this.finish();
		}
		intent.putExtra("flg", flg);
		intent.putExtra("index", index);
		AnswerActivity.buttom_btn_type=4;
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		overridePendingTransition(R.anim.push_up_in, R.anim.nullanim);*/
	}
	class ViewHolder{
		LinearLayout report_linear;
		TextView text1;
		TextView text2;
	}
	class ViewHolder2{
		TextView text;
		LinearLayout linear_bg;
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.linear_Left:
				showAlertDialogChoose("提示", "是否返回", "取消", "确定");
				break;
			case R.id.linaer3:
				MyFlg.finshActivitys();
				break;
			case R.id.payRmb_vip:
				intent = new Intent(PracticeReportActivity.this,PayVIPActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
				break;
			case R.id.pay_hint2:
				intent = new Intent(PracticeReportActivity.this,PayVIPActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent,0);//REQUESTCODE定义一个整型做为请求对象标识
				break;
			case R.id.linear_webview:
				intent = new Intent(PracticeReportActivity.this, WebViewActivity.class);
				if(null!=sesBean.getConclusion().getBy().getUrl()){
					intent.putExtra("url", sesBean.getConclusion().getBy().getUrl());
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
				}

				break;
			default:
				break;
		}
	}


	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(PracticeReportActivity.this, title,content, leftBtnText, rightBtnText,
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
								overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
								break;
							default:
								break;
						}

					}
				});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//resultCode  1表示支付成功  -1 表示支付失败
		if(resultCode==1 && (MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==1 || application.getuserInfoBean(PracticeReportActivity.this).getMember_status()==2)){ //支付成功
			linear_pay.setVisibility(View.VISIBLE);
			linear_vip.setVisibility(View.GONE);
			if(MyApplication.getuserInfoBean(PracticeReportActivity.this).getMember_status()==1){
				pay_hint_linear.setVisibility(View.GONE);
			}
		}else if(requestCode == 2&&resultCode == RESULT_OK){//练习  查看了解析返回
			create_Exercise_suit_2Bean = (Create_Exercise_suit_2Bean) data.getSerializableExtra("bean");
		}else if(requestCode == 3&&resultCode == RESULT_OK){//测验  全部解析返回
			list_infos_all = (List<infos>) data.getSerializableExtra("list_infos");
			Sup.setList_infos_cuoti(list_infos_all,list_infos_cuoti);

		}else if(requestCode == 4&&resultCode == RESULT_OK){//测验  分组解析返回
			if(now_group_index!=-1){
				List<infos> list_infos = (ArrayList<infos>) data.getSerializableExtra("list_infos");
				list_infos_cuoti.set(now_group_index, list_infos);
				Sup.setList_infos_all(list_infos_all,list_infos);

			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyFlg.finshActivitys();
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			//	finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
