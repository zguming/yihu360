package cn.net.dingwei.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.changeCity.ChageCityActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
import com.tencent.cos.common.COSEndPoint;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi_user;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.MyCollectBean;
import cn.net.dingwei.Bean.MyWrongsBean;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.adpater.Person_Adpater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.myView.RoundImageView;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;

public class FramentPerson extends Fragment implements OnClickListener{
	private ImageView title_left;
	private TextView title_text,level_text,level_name,userd_days,amount_lt,amount_gg;
	private String[][] stArray = new String[][]{{"个人资料",""},{"我的余额",""},{"",""},{"我的错题",""},{"我的收藏",""},{"我的笔记",""},{"",""},{"报考地区",""},{"选择考试",""},{"考试时间",""},{"练习科目",""},{"夜间模式",""},{"",""},{"推荐给朋友",""},{"意见反馈",""},{"关于我们",""},{"",""},{"退出登录",""}};
	//private String[][] stArray = new String[][]{{"个人资料",""},{"",""},{"报考城市",""},{"报考学科",""},{"考试时间",""},{"练习科目",""},{"",""},{"推荐给朋友",""},{"意见反馈",""},{"关于我们",""},{"",""},{"退出登录",""}};
	//private ScrollView scroll;
	private ListView listview;
	//private DialogMenu dialogMenu; //对话框
	private myHandler myHandler = new myHandler();
	private String [] stArray1 = new String[]{"","昵称","账号信息","","修改密码"};
	private String [] stArray2 = new String[]{"","","","",""};
	private Boolean isLoad=false;//是否加载过此页面
	private int clickSum=0;
	private RoundImageView person_head;
	private FYuanTikuDialog fYuanTikuDialog;
	private TextView buttom_msg_text;
	private Button pay_rmb;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_8=0;
	private int Screen_width=0;
	private String Geren,Unicon,Vipicon;
	private FYuanTikuDialog dialog;
	//时间选择器
	private MyDatePickerDialog datePickerDialog;

	String feedback ="";

	//头像上传功能
	protected String[] menu_name_array = { "拍照", "从图库选择", "取消" };
	protected AlertDialog menuDialog;// menu菜单Dialog
	private String ImageDownLoadPath="";
	private String camera_path="";
	protected static final int IMG_ITEM_CAMERA = 110;
	protected static final int IMG_ITEM_PICTURE = 111;
	protected static final int IMG_ITEM_CANCEL = 112;
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	private String my_balance="0.00";//我的余额
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		application = MyApplication.myApplication;
		sharedPreferences =getActivity().getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_8 = sharedPreferences.getInt("bgcolor_8", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		Geren = sharedPreferences.getString("Geren", "");
		Unicon = sharedPreferences.getString("Unicon", "");
		Vipicon = sharedPreferences.getString("Vipicon", "");
		feedback = sharedPreferences.getString("feedback", "");
		ImageDownLoadPath = sharedPreferences.getString("ImageDownLoadPath", "");
		View view = inflater.inflate(R.layout.activity_person_centered, null, false);
		fYuanTikuDialog = new FYuanTikuDialog(getActivity(),R.style.DialogStyle, "正在退出");
		dialog = new FYuanTikuDialog(getActivity(),R.style.DialogStyle,"正在加载");
		initID(view);
		return view;
	}
	private void initID(View view) {
		// TODO Auto-generated method stub
		view.findViewById(R.id.layoutRight).setVisibility(View.GONE);
		view.findViewById(R.id.layoutLeft).setVisibility(View.GONE);
		title_left=(ImageView)view.findViewById(R.id.title_left);
		title_left.setImageResource(R.drawable.title_black);
		title_text=(TextView)view.findViewById(R.id.title_text);
		listview =(ListView)view.findViewById(R.id.listview);
		level_text=(TextView)view.findViewById(R.id.level_text);
		level_name=(TextView)view.findViewById(R.id.level_name);
		userd_days=(TextView)view.findViewById(R.id.userd_days);
		amount_lt=(TextView)view.findViewById(R.id.amount_lt);
		amount_gg=(TextView)view.findViewById(R.id.amount_gg);
		person_head=(RoundImageView)view.findViewById(R.id.person_head);
		pay_rmb = (Button) view.findViewById(R.id.pay_rmb);
		pay_rmb.setOnClickListener(this);

		View view_scrollview =  view.findViewById(R.id.view_scrollview);
		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(Screen_width,Screen_width/4);
		view_scrollview.setLayoutParams(params1);

		pay_rmb.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_1,Bgcolor_2, Color.TRANSPARENT,0,DensityUtil.DipToPixels(getContext(),25)));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Screen_width/2,LinearLayout.LayoutParams.WRAP_CONTENT );
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(Screen_width/2,5 );
		int dip15=DensityUtil.DipToPixels(getActivity(), 15);
		params.setMargins(0,dip15, 0, 0);
		params2.setMargins(dip15, DensityUtil.DipToPixels(getActivity(), 5), dip15, 0);
		//设置颜色
		//scroll.setColor(getResources().getColor(R.color.lianxi_bg));
		view.findViewById(R.id.person_top_bg).setBackgroundColor(Bgcolor_1);
		view.findViewById(R.id.linear_bg).setBackgroundColor(Bgcolor_2);
		view.findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		//设置用户头像
		//person_head.setImageBitmap(BitmapFactory.decodeFile(Geren));
		person_head.setOnClickListener(this);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(null==getActivity()){
			return;
		}else{
			loadUser();
			if(HomeActivity2.type==3){
				setArray();
				listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
			}
		}
	}
	public void MyRefresh(){
		if(isLoad == true&&clickSum!=MyFlg.clickSum){
			setArray();
			listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
			clickSum = MyFlg.clickSum;
		}else if(isLoad == true&& MyFlg.ISupdatePerson == true){
			setArray();
			listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
			MyFlg.ISupdatePerson = false;
		}
		if(isLoad==true&&isLoad == true){
			loadUser();
		}

		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//加载头像
		String headUrl = MyApplication.getuserInfoBean(getActivity()).getImg();
		Log.i("123", "加载的头像地址: "+headUrl);
		if(null!=headUrl){
			ImageLoader.getInstance().displayImage(headUrl, person_head);
		}
		buttom_msg_text =(TextView)getActivity().findViewById(R.id.buttom_msg_text);
		InitPickerDialog();
		initData();
		isLoad = true;
		clickSum = MyFlg.clickSum;
		//loadUser();

	}
	//设置退出登录或者完善信息
	private void Set_stArray_Last(){
		if(MyApplication.getuserInfoBean(getActivity()).getRegistered()==false &&MyApplication.getuserInfoBean(getActivity()).getBool().equals("1")){
			//获取commoninfo 提示文字
			SharedPreferences sp_commoninfo = getActivity().getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
			Gson gson = new Gson();
			try {
				Placeholder_textBean placeholder_textBean = gson.fromJson(new JSONObject(sp_commoninfo.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
				if(null!=placeholder_textBean&&null!=placeholder_textBean.getYk_wszl()){
					stArray[16][1]=placeholder_textBean.getYk_wszl();
				}
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stArray[stArray.length-1][0]="绑定手机号";
		}else{
			stArray[stArray.length-1][0]="退出登录";
			stArray[stArray.length-1][1]="";
		}
	}
	/**
	 * 时间控件初始化
	 */
	private void InitPickerDialog() {
		// TODO Auto-generated method stub
		Time t1 = new Time();
		t1.setToNow(); // 取得系统时间。
		int year = t1.year;
		int month = t1.month;
		int date = t1.monthDay;
		datePickerDialog = new MyDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,onDateSetListener, year, month, date);
		//datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//datePickerDialog = new MyDatePickerDialog(getActivity(), R.style.time_select,onDateSetListener, year, month, date);
		datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
		datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				DatePicker datePicker = datePickerDialog.getDatePicker();
				int year = datePicker.getYear();
				int month = datePicker.getMonth()+1;//因为是从0开始计算的 所有加1
				int day = datePicker.getDayOfMonth();
				String data = year+"/"+month+"/"+day;
				updataUser(null, data);
			}
		});
		//datePickerDialog.setCanceledOnTouchOutside(false);
		//myDatePickerDialog.show();
	}
	MyDatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {

		}
	};
	private void initData() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				//	Toast.makeText(getActivity(), "点击"+point, 0).show();
				Intent intent;
				RequestParams params;
				switch (point) {
					case 0:
						intent = new Intent(getActivity(), Person_dataActivity.class);
						intent.putExtra("type", 0);
						intent.putExtra("title", "个人信息");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 1://余额
						intent = new Intent(getActivity(), MyMoneyActivity.class);
						intent.putExtra("balance",my_balance);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						break;
					case 3://我的错题
						params = new RequestParams();
						params.add("a", MyFlg.a);
						params.add("ver", MyFlg.android_version);
						params.add("clientcode",MyFlg.getclientcode(getActivity()));
						PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_user_wrongs(),getActivity()), 1);
						break;
					case 4://我的收藏
						params = new RequestParams();
						params.add("a", MyFlg.a);
						params.add("ver", MyFlg.android_version);
						params.add("clientcode",MyFlg.getclientcode(getActivity()));
						PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_user_collect(),getActivity()),2);
						break;
					case 5://我的笔记
						intent = new Intent(getActivity(),MyNoteActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivityForResult(intent, 1);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 7://城市
						intent = new Intent(getActivity(),ChageCityActivity.class);
						Bundle bundle = new Bundle();
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						if(null!=MyFlg.baidu_city &&!MyFlg.baidu_city.equals("0") ){
							intent.putExtra("location_city", MyFlg.baidu_city);
						}
						startActivityForResult(intent, 1);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 8://学科
//						intent = new Intent(getActivity(), Person_dataActivity.class);
						intent = new Intent(getActivity(), SubjectSelectActivity.class);
//						intent.putExtra("type", 2);
//						intent.putExtra("title", "修改报考学科");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 9://时间
					/*intent = new Intent(getActivity(), Person_dataActivity.class);
					intent.putExtra("type", 3);
					intent.putExtra("title", "修改报考时间");
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);*/
						datePickerDialog.show();
						break;
					case 10: //修改练习科目
						intent = new Intent(getActivity(), Person_dataActivity.class);
						intent.putExtra("type", 4);
						intent.putExtra("title", "修改练习科目");
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					case 11: //夜间模式

						break;
					case 13: // 推荐给朋友(分享)
						String share_type=application.getuserInfoBean(HomeActivity2.instence).getShare_type();
						if(!TextUtils.isEmpty(share_type)&&share_type.equals("1")){
							intent = new Intent(HomeActivity2.instence, RecommendShareActivity.class);
						}else{
							intent = new Intent(HomeActivity2.instence, ShareActivity.class);
						}
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						HomeActivity2.instence.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 14://意见反馈
						intent = new Intent(getActivity(), WebViewActivity.class);
						intent.putExtra("url", feedback+"?uid="+MyApplication.Getget_user_baseinfo(getActivity()).getUid());
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						break;
					case 15: // 关于
						intent = new Intent(getActivity(), AboutActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						break;
					case 17:
						if(MyApplication.getuserInfoBean(getActivity()).getRegistered()==false &&MyApplication.getuserInfoBean(getActivity()).getBool().equals("1")){
							intent = new Intent(getActivity(), Rest_passwordActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							if(MyApplication.getuserInfoBean(getActivity()).getIs_login().equals("1")){
								intent.putExtra("isNeddSinIn", "1");
							}else{
								intent.putExtra("isNeddSinIn", "0");
							}
							intent.putExtra("flg", "1");
							startActivityForResult(intent, 100);
							getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
						}else{//退出登录
							showAlertDialogChoose("提示", "是否退出登录", "取消", "确定");
						}

						break;

					default:
						break;
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.pay_rmb:
				intent = new Intent(getActivity(), PayVIPActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				break;
			case R.id.person_head://上传头像
				getImg();
				break;

			default:
				break;
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(null==getActivity()){
			}else{
				super.handleMessage(msg);
				switch (msg.what) {
					case 1: //成功
						fYuanTikuDialog.dismiss();
						Intent intent = new Intent(getActivity(), ChooseToEnterActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
						intent.putExtra("user_phone", MyApplication.getuserInfoBean(getActivity()).getMobile());
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						MyFlg.all_activitys.remove(getActivity());
						MyFlg.listActivity.remove(getActivity());
						getActivity().finish();
						MyFlg.finshActivitys();
						SharedPreferences sp = getActivity().getSharedPreferences("get_situation", Context.MODE_PRIVATE);
						sp.edit().putString("get_situation", "").commit();
						SharedPreferences sp2 = getActivity().getSharedPreferences("get_subjects_progress", Context.MODE_PRIVATE);
						sp2.edit().putString("get_subjects_progress", "").commit();
						break;
					case 3://失败
						fYuanTikuDialog.dismiss();
						break;
					case 10:
						application.userInfoBean = APPUtil.getUser_isRegistered(getActivity());
						setArray();
						listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
						//设置指南Msg
						MyFlg.SetGuide_msg(buttom_msg_text,application.userInfoBean);
						break;
					case 5://更新User城市
						MyFlg.ISupdateHome = true;
						MyFlg.ISupdateHome_listview = true;
						MyFlg.ISupdateguide = true;
						MyFlg.ISupdateTesting =true;
						application.userInfoBean = APPUtil.getUser_isRegistered(getActivity());
						setArray();
						listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
						//设置指南Msg
						MyFlg.SetGuide_msg(buttom_msg_text,application.userInfoBean);

						//设置极光推送标签和别名
						Set<String> tagSet = new LinkedHashSet<String>();
						tagSet.add(MyApplication.getuserInfoBean(getActivity()).getCity());
						MyFlg.setJPushTag(getActivity(), tagSet);
						//别名已经在get_user_baseinfo 设置了  所有在此不在设置
						MyFlg.setJPushAlias(getActivity(), MyApplication.Getget_user_baseinfo(getActivity()).getUid()+"");
						break;
					case 6://更新日期
						application.userInfoBean = APPUtil.getUser_isRegistered(getActivity());
						setArray();
						listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
						//设置指南Msg
						MyFlg.SetGuide_msg(buttom_msg_text,application.userInfoBean);
						break;
					case 99://图片上传成功
						Toast.makeText(getActivity(), "上传头像成功", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						break;
					case 999://图片上传成功
						dialog.dismiss();
						Toast.makeText(getActivity(), "上传头像失败", Toast.LENGTH_SHORT).show();
						String headUrl = MyApplication.getuserInfoBean(getActivity()).getImg();
						if(null!=headUrl){
							ImageLoader.getInstance().displayImage(headUrl, person_head);
						}
						break;
					default:
						break;
				}
			}
		}
	}
	//设置数组
	private void setArray(){
		//昵称
		String nickname = MyApplication.getuserInfoBean(getActivity()).getNickname();
		//会员名称
		String  Member_type_name = MyApplication.getuserInfoBean(getActivity()).getMember_type_name();
		//有效日期
		String Trial_expiry = MyApplication.getuserInfoBean(getActivity()).getMember_expiry_text();
		//按钮信息
		//String Member_price = application.getuserInfoBean(getActivity()).getMember_price();
		String Member_price = MyApplication.getuserInfoBean(getActivity()).getMember_button();
		//报考城市
		String city_name = MyApplication.getuserInfoBean(getActivity()).getCity_name();
		//报考科目
		String exam_name = MyApplication.getuserInfoBean(getActivity()).getExam_name();
		//考试时间
		String exam_schedule = MyApplication.getuserInfoBean(getActivity()).getExam_schedule();
		//练习科目
		String subject_name = MyApplication.getuserInfoBean(getActivity()).getSubject_name();
		//手机号（账号）
		String phone_number = MyApplication.getuserInfoBean(getActivity()).getMobile();
		//错题
		int user_wrongs = MyApplication.getuserInfoBean(getActivity()).getUser_wrongs();
		//笔记
		int user_note = MyApplication.getuserInfoBean(getActivity()).getUser_note();
		//收藏
		int user_collect = MyApplication.getuserInfoBean(getActivity()).getUser_collect();
		String balance = MyApplication.getuserInfoBean(getActivity()).getBalance();


		stArray[3][1] = user_wrongs+" 题";
		stArray[4][1] = user_collect+" 题";
		stArray[5][1] = user_note+" 题";

		if(!TextUtils.isEmpty(balance)){
			stArray[1][1] ="￥ "+balance+getResources().getString(R.string.jinbi);
			my_balance = balance;
		}
		if(!phone_number.equals("null")){
			stArray2[2] = phone_number;
		}
		if(!nickname.equals("null")){
			title_text.setText(""+nickname+"  ");
			stArray[0][1] = nickname;
			stArray2[1]=nickname;
		}
		if(!city_name.equals("null")){
			stArray[7][1] = city_name;
			//stArray[2][1] = city_name;
		}
		if(!exam_name.equals("null")){
			stArray[8][1] = exam_name;
			//stArray[3][1] = exam_name;
		}
		if(!exam_schedule.equals("null")){
			stArray[9][1] = APPUtil.setTime(exam_schedule);
			//stArray[4][1] = APPUtil.setTime(exam_schedule);
		}
		if(!subject_name.equals("null")){
			stArray[10][1] = subject_name;
			//stArray[5][1] = subject_name;
		}

		String level = MyApplication.getuserInfoBean(getActivity()).getLevel_text();
		if(!"null".equals(level)){
			level_text.setText(level);
		}
		String levelName = MyApplication.getuserInfoBean(getActivity()).getLevel_name();
		if(!"null".equals(levelName)){
			level_name.setText(levelName);
		}
		String exp_rate = MyApplication.getuserInfoBean(getActivity()).getExp_rate();
		String days = MyApplication.getuserInfoBean(getActivity()).getUserd_days();
		if(!"null".equals(days)){
			//userd_days.setText("相伴 "+days+" 天");
			userd_days.setText(days);
		}
		String lt = MyApplication.getuserInfoBean(getActivity()).getAmount_lt();
		if(!"null".equals(lt)){
			//amount_lt.setText("已练习 "+lt+" 题");
			amount_lt.setText(lt);
		}
		String gg = MyApplication.getuserInfoBean(getActivity()).getAmount_gg();
		if(!"null".equals(gg)){
			//amount_gg.setText("已巩固 "+gg+" 道错题");
			amount_gg.setText(gg);
		}


		Set_stArray_Last();
	}


	public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
		F_IOS_Dialog.showAlertDialogChoose(getActivity(), title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								fYuanTikuDialog.show();
								List <NameValuePair> params=new ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair("a",MyFlg.a));
								params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(getActivity())));
								params.add(new BasicNameValuePair("ver",MyFlg.android_version));
								//解绑用户
								AsyncLoadApi_user asyncLoadApi_user = new AsyncLoadApi_user(getActivity(), myHandler, params, "unbind_user",MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getUnbind_user(),getActivity()));
								asyncLoadApi_user.execute();
								break;
							default:
								break;
						}

					}
				});
	}
	private void loadUser(){
		List <NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("a",MyFlg.a));
		params.add(new BasicNameValuePair("ver",MyFlg.android_version));
		params.add(new BasicNameValuePair("clientcode", MyFlg.getclientcode(getActivity())));
		params.add(new BasicNameValuePair("mobile_model", MyFlg.getmobile_model()));
		AsyncLoadApi asyncLoadApi = new AsyncLoadApi(getActivity(), myHandler, params, "get_userinfo", 10, 11,MyFlg.get_API_URl(application.getCommonInfo_API_functions(getActivity()).getGet_userinfo(),getActivity()));
		asyncLoadApi.execute();
	}
	private void updataUser(String city_key,String data){
		int success = 5;
		if(null!=city_key){
			success =5;
		}else{
			success =6;
		}

		dialog.show();
		UpdateUserAsync updateUserAsync  =new UpdateUserAsync(dialog, myHandler, getActivity(), MyFlg.getclientcode(getActivity()), null, null, null, null, data, null, null, null, true, success, null, null, city_key,null,null);
		updateUserAsync.execute();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1&&resultCode == getActivity().RESULT_OK){ //选择城市
			String city_key = data.getStringExtra("key");
			Log.i("123", "城市key: "+city_key);
			if(null==city_key){
				Toast.makeText(getActivity(), "当前城市暂未考试信息，请切换其他城市！", Toast.LENGTH_SHORT).show();
			}else{
				updataUser(city_key,null);
			}
		}else if (requestCode == IMG_ITEM_CAMERA) {//相机返回
			startPhotoZoom(Uri.fromFile(new File(camera_path)), 200);
		} else if (requestCode == PHOTO_REQUEST_CUT) {//裁剪图片后操作
			if (data != null)
				setPicToView(data);
		} else if (requestCode == IMG_ITEM_PICTURE) {//相册返回
			if (data != null)
				startPhotoZoom(data.getData(), 200);
		}else if (requestCode == 100 &&resultCode == getActivity().RESULT_OK) {//完善资料返回
			Set_stArray_Last();
			setArray();
			listview.setAdapter(new Person_Adpater(getActivity(), stArray,0,true));
		}
	}
	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			person_head.setImageBitmap(photo);
			final File file = set(photo);
			//uploadFile(file, "www.baidu.com");
			Log.i("123", "头像地址: "+file.toString());
			uploadFile_oss(file);
		}
	}
	String appid;
	String bucket;
	String headUrl;
	public void uploadFile_oss(final File file){
		dialog.show();
		//创建COSClientConfig对象，根据需要修改默认的配置参数
		COSClientConfig config = new COSClientConfig();
		//设置园区
		config.setEndPoint(COSEndPoint.COS_GZ);
		config.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		config.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		config.setMaxConnectionsCount(5); // 最大并发请求书，默认5个
		config.setMaxRetryCount(2); // 失败后最大重试次数，默认2次

		Context context = MyApplication.myApplication;
		if ("teacher".equals(BuildConfig.FLAVOR)) {
			appid = "1252825478";//"腾讯云注册的appid"
		}else if ("yihu360".equals(BuildConfig.FLAVOR)){
			appid = "1254402451";//"腾讯云注册的appid"
		}else if ("xizhang".equals(BuildConfig.FLAVOR)){
			appid = "1255328718";//"腾讯云注册的appid"
		}

		String peristenceId = null;//"持久化Id"，每个 COSClient 需设置一个唯一的 ID 用于持久化保存未完成任务 列表，以便应用退出重进后能够继续进行上传；传入为 Null，则不会进行持久化保存

		//创建COSlient对象，实现对象存储的操作
		final COSClient cos = new COSClient(context, appid, config, peristenceId);
		if ("teacher".equals(BuildConfig.FLAVOR)) {
			bucket = "tmjy";//"cos空间名称"
		}else if ("yihu360".equals(BuildConfig.FLAVOR)){
			bucket = "med360";//"cos空间名称"
		}else if ("xizhang".equals(BuildConfig.FLAVOR)){
			bucket = "zangyu";//"cos空间名称"
		}
		String cosPath = "app/" + MyFlg.a + "/user/photo/"
				+ MyApplication.Getget_user_baseinfo(getActivity()).getUid()
				+ ".png";//"远端路径，即存储到cos上的路径";
		String srcPath = file.getPath();//本地文件的绝对路径
		String sign = MyApplication.userInfoBean.getImg_sign();//"签名，此处使用多次签名"

		//上传
		PutObjectRequest putObjectRequest = new PutObjectRequest();
		putObjectRequest.setBucket(bucket);
		putObjectRequest.setCosPath(cosPath);
		putObjectRequest.setSrcPath(srcPath);
		putObjectRequest.setSign(sign);
		putObjectRequest.setInsertOnly("0");
		putObjectRequest.setListener(new  IUploadTaskListener(){
			@Override
			public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
				dialog.dismiss();
				PutObjectResult result = (PutObjectResult) cosResult;
				if(result != null){
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(" 上传结果： ret=" + result.code + "; msg =" +result.msg + "n");
					stringBuilder.append(" access_url= " + result.access_url == null ? "null" :result.access_url + "n");
					stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" :result.resource_path + "n");
					stringBuilder.append(" url= " + result.url == null ? "null" :result.url);
					Log.w("123","头像上传stringBuilder："+stringBuilder.toString());
				}
				if ("teacher".equals(BuildConfig.FLAVOR)) {
					headUrl = "http://tmjy-1252825478.cosgz.myqcloud.com/app/" + MyFlg.a + "/user/photo/" + MyApplication
							.Getget_user_baseinfo(getActivity()).getUid() + ".png";
				}else if ("yihu360".equals(BuildConfig.FLAVOR)){
					headUrl = "http://med360-1254402451.cosgz.myqcloud.com/app/" + MyFlg.a + "/user/photo/" + MyApplication
							.Getget_user_baseinfo(getActivity()).getUid() + ".png";
				}else if ("xizhang".equals(BuildConfig.FLAVOR)){
					headUrl = "http://zangyu-1255328718.cosgz.myqcloud.com/app/" + MyFlg.a + "/user/photo/" + MyApplication
							.Getget_user_baseinfo(getActivity()).getUid() + ".png";
				}

				UpdateUserAsync updateUserAsync = new UpdateUserAsync(dialog, myHandler, getActivity(), MyFlg.getclientcode
						(getActivity()), null, null, null, null, null, null, null, null, true, 99, null, null, null, null, headUrl);
				updateUserAsync.execute();
				if (file.exists()) {
					file.delete();
				}
			}

			@Override
			public void onFailed(COSRequest COSRequest, final COSResult cosResult) {
				dialog.dismiss();
				Log.w("123","上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg);
			}

			@Override
			public void onProgress(COSRequest cosRequest, final long currentSize, final long totalSize) {
				float progress = (float)currentSize/totalSize;
				progress = progress *100;
				Log.w("123","进度：  " + (int)progress + "%");
			}

			@Override
			public void onCancel(COSRequest cosRequest, COSResult cosResult) {

			}
		});
		cos.putObject(putObjectRequest);//开始上传
	}
	/**
	 * 上传图片
	 * @param file	上传文件
	 * @param url 上传链接
	 */
	public  void uploadFile(File file, String url){
		dialog.show();
		//普通上传图片
		try {
			//File file = new File(path);
			if (file.exists() && file.length() > 0) {
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("uploadfile", file);
				// 上传文件
				client.post(url, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,byte[] responseBody) {
						// 上传成功后要做的工作
						Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
						// 上传失败后要做到工作
						Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});
			} else {
				dialog.dismiss();
				Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_LONG).show();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			dialog.dismiss();
			Toast.makeText(getActivity(), "文件异常", Toast.LENGTH_LONG).show();
		}

	}
	public File set(Bitmap mBitmap) {
		File dirFile = new File(ImageDownLoadPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File imgFile = new File(ImageDownLoadPath+ System.currentTimeMillis() + ".jpg");
		try {
			FileOutputStream bitmapWtriter = new FileOutputStream(imgFile);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapWtriter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imgFile;
	}
	class MyDatePickerDialog extends DatePickerDialog {


		public MyDatePickerDialog(Context context, int theme,OnDateSetListener callBack, int year, int monthOfYear,int dayOfMonth) {
			super(context, theme, callBack, year, monthOfYear, dayOfMonth);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		}

		@Override
		protected void onStop() {
			super.onStop();
		}
	}
	/**
	 * POST 网络请求
	 * @param params  参数
	 * @param apiUrl  API地址
	 * @param type    1 我的错题  2 我的收藏
	 */
	private void PostApi(RequestParams params,String apiUrl,final int type){
		dialog.show();
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		client.post(apiUrl, params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				Gson gson = new Gson();
				if(type==1){
					MyWrongsBean bean = new MyWrongsBean();
					bean = gson.fromJson(result, MyWrongsBean.class);
					if(bean.getStatus()==false){
						Toast.makeText(getActivity(), "加载失败，请稍后重试。", Toast.LENGTH_SHORT).show();
					}else{
						Intent intent = new Intent(getActivity(), MyWrongsActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", bean);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					}
				}else if(type==2){
					MyCollectBean bean = new MyCollectBean();
					bean = gson.fromJson(result, MyCollectBean.class);
					if(bean.getStatus()==false){
						Toast.makeText(getActivity(), "加载失败，请稍后重试。", Toast.LENGTH_SHORT).show();
					}else{
						Intent intent = new Intent(getActivity(), MyCollectionActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", bean);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(getActivity(), "网络异常。", Toast.LENGTH_SHORT).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}
			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
			}
		});
	}

	protected void getImg() {
		// 创建AlertDialog
		AlertDialog.Builder settingDialog = new AlertDialog.Builder(getActivity());
		menuDialog = settingDialog.create();
		menuDialog.show();
		Window window = menuDialog.getWindow();
		window.setContentView(R.layout.dialog_image_select);

		// menuDialog.setView(menuView);

		ListView menu_list = (ListView) window.findViewById(R.id.menu_listview);
		menu_list.setAdapter(getMenuAdapter(menu_name_array));
		/** 监听menu选项 **/
		menu_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				switch (arg2) {
					case 0:
						// 调用系统的拍照功能
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						// 指定调用相机拍照后照片的储存路径
						camera_path = ImageDownLoadPath+"/"+System.currentTimeMillis()+".jpg";
						intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(camera_path)));
						startActivityForResult(intent, IMG_ITEM_CAMERA);
						break;
					case 1:
						Intent intent1 = new Intent(Intent.ACTION_PICK, null);
						intent1.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intent1, IMG_ITEM_PICTURE);
						break;
					case 2:
						break;
				}
				if (menuDialog.isShowing())
					menuDialog.dismiss();
			}
		});
		menuDialog.show();
	}
	protected SimpleAdapter getMenuAdapter(String[] menuNameArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(getActivity(), data,R.layout.dialog_image_select_item, new String[] { "itemText" },
				new int[] { R.id.item_text });
		return simperAdapter;
	}

}
