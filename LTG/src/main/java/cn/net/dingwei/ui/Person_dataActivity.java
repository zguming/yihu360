package cn.net.dingwei.ui;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.dingwei.AsyncUtil.BaiduMapGetCity;
import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.CommonCityListBean;
import cn.net.dingwei.Bean.ExamBean;
import cn.net.dingwei.Bean.Get_baseinfoBean;
import cn.net.dingwei.Bean.Get_baseinfoBean.subjects;
import cn.net.dingwei.adpater.Person_Adpater;
import cn.net.dingwei.adpater.Person_data_Adpater;
import cn.net.dingwei.myView.ElasticScrollView;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 个人资料（修改城市  考试日期什么的）
 * @author Administrator
 *
 */
public class Person_dataActivity extends ParentActivity{
	private ListView listview;
	private ImageView title_left;
	private TextView title_text;
	private String[] stArr;
	private String[] stArr_last;
	private int type=0;//0 :个人资料
	private String [][] stArr_0 =new String[][]{{"",""},{"昵称",""},{"账号信息",""},{"",""},{"修改密码",""}};
	private String [][] stArr_01 =new String[][]{{"",""},{"昵称",""}};
	//地图定位
	private LocationClient locationClient;
	private String latitude;  //纬度
	private String lontitude; //经度
	private myHandler handler = new myHandler();
	private Person_data_Adpater adpater;
	private FYuanTikuDialog dialog;
	private List<CommonCityListBean> list_city; //城市信息
	private List<ExamBean> list_exam; //考试学科
	private String[] stArr_time;
	private subjects[] subjects; //科目
	private MyApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_person_data);
		initID();
		initData();
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在提交");
		application = MyApplication.myApplication;
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setListView();
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
		title_left=(ImageView)findViewById(R.id.title_left);
		title_left.setImageResource(R.drawable.title_black);
		listview=(ListView)findViewById(R.id.listview);
		title_text=(TextView)findViewById(R.id.title_text);
		ElasticScrollView scroll = (ElasticScrollView)findViewById(R.id.scroll);
		//设置颜色
		scroll.setColor(getResources().getColor(R.color.lianxi_bg));
	}
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		title_text.setText(intent.getStringExtra("title"));
		stArr=intent.getStringArrayExtra("stArr");
		stArr_last=intent.getStringArrayExtra("stArr_last");
		type = intent.getIntExtra("type", 0);
	}
	private void setListView(){
		if(type==0){//个人
			if(MyApplication.getuserInfoBean(this).getRegistered()==false &&MyApplication.getuserInfoBean(this).getBool().equals("1")){
				stArr_01[1][1] = MyApplication.getuserInfoBean(Person_dataActivity.this).getNickname();
				listview.setAdapter(new Person_Adpater(this, stArr_01,1));
				listview.setOnItemClickListener(new ListViewOnitemClick());
			}else{
				stArr_0[1][1] = MyApplication.getuserInfoBean(Person_dataActivity.this).getNickname();
				stArr_0[2][1] = MyApplication.getuserInfoBean(Person_dataActivity.this).getMobile();
				listview.setAdapter(new Person_Adpater(this, stArr_0,1));
				listview.setOnItemClickListener(new ListViewOnitemClick());
			}

		}else if(type==1){ //城市
			list_city = APPUtil.getCommonInfo_city(this);
			String [] stArr= new String [list_city.size()];
			for (int i = 0; i < list_city.size(); i++) {
				stArr[i]=list_city.get(i).getAndkey();
			}
			adpater = new Person_data_Adpater(this, stArr,1);
			listview.setAdapter(adpater);
			listview.setOnItemClickListener(new ListViewOnitemClick());
			LoactionNow();
			locationClient.start();
		}else if(type==2){ //报考学科
			list_exam=APPUtil.getexam_structure(this);
			String [] stArr= new String [list_exam.size()];
			String [] status=new String [list_exam.size()];
			for (int i = 0; i < list_exam.size(); i++) {
				stArr[i] = list_exam.get(i).getN();
				status[i]=list_exam.get(i).getStatus();
			}
			adpater = new Person_data_Adpater(this, stArr,status,2);
			listview.setAdapter(adpater);
			listview.setOnItemClickListener(new ListViewOnitemClick());
		}else if(type==3){//报考时间
			list_exam=APPUtil.getexam_structure(this);
			for (int i = 0; i < list_exam.size(); i++) {
				if(list_exam.get(i).getK().equals( MyApplication.getuserInfoBean(Person_dataActivity.this).getExam())){
					ExamBean examBean = list_exam.get(i);
					stArr_time= examBean.getSchedules();
					String [] ThisTimes = new String[stArr_time.length];
					for (int j = 0; j < stArr_time.length; j++) {
						ThisTimes[j]=APPUtil.setTime(stArr_time[j]);
					}
					adpater = new Person_data_Adpater(this, ThisTimes,3);
					listview.setAdapter(adpater);
					listview.setOnItemClickListener(new ListViewOnitemClick());
					return;
				}
			}
		}else if(type==4){//报考科目
			List<Get_baseinfoBean> list_baseinfoBeans = APPUtil.get_baseinfoBean(this);
			for (int i = 0; i < list_baseinfoBeans.size(); i++) {
				if(list_baseinfoBeans.get(i).getK().equals( MyApplication.getuserInfoBean(Person_dataActivity.this).getExam())){
					subjects = list_baseinfoBeans.get(i).getSubjects();
					String [] stArr= new String [subjects.length];
					for (int j = 0; j < subjects.length; j++) {
						stArr[j]=subjects[j].getN();
					}
					adpater = new Person_data_Adpater(this, stArr,4);
					listview.setAdapter(adpater);
					listview.setOnItemClickListener(new ListViewOnitemClick());
					return;
				}
			}
		}
	}
	class ListViewOnitemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int point,
								long arg3) {
			UpdateUserAsync updateUserAsync;
			// TODO Auto-generated method stub
			switch (type) {
				case 0://点击个人
					person_onitemclick(point);
					break;
				case 1://点击城市

					if(!list_city.get(point).getK().equals(MyApplication.getuserInfoBean(Person_dataActivity.this).getCity())){
						dialog.show();
						MyFlg.ISupdateHome = true;
						MyFlg.ISupdateguide = true;
						updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), null, null, null, null, null, null, null, null, true, 1,null,null,list_city.get(point).getK(),null,null);
						updateUserAsync.execute();
					}else{
						MyFlg.all_activitys.remove(Person_dataActivity.this);
						finish();
					}

					break;
				case 2://点击学科
					if(list_exam.get(point).getK().equals(MyApplication.getuserInfoBean(Person_dataActivity.this).getExam()) && list_exam.get(point).getStatus().equals("1")){
						MyFlg.all_activitys.remove(Person_dataActivity.this);
						finish();
					}else if(list_exam.get(point).getStatus().equals("1")){
						dialog.show();
						MyFlg.ISupdateHome = true;
						MyFlg.ISupdateguide = true;
						updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), null, null, null, list_exam.get(point).getK(), null, null, null, null, false, 1,null,null,null,null,null);
						//updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), application.getuserInfoBean(Person_dataActivity.this).getCity(), null, null, list_exam.get(point).getK(), application.getuserInfoBean(Person_dataActivity.this).getExam_schedule(), null, null, null, false, 1,null,null);
						updateUserAsync.execute();
					}

					break;
				case 3://选择考试时间
					if(!stArr_time[point].equals(MyApplication.getuserInfoBean(Person_dataActivity.this).getExam_schedule())){
						dialog.show();
						updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), null, null, null, null, stArr_time[point], null, null, null, false, 1,null,null,null,null,null);
						// updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, application.clientcode, application.getuserInfoBean(Person_dataActivity.this).getCity(), null, null, application.getuserInfoBean(Person_dataActivity.this).getExam(), stArr_time[point], null, null, null, false, 1,null,null);
						updateUserAsync.execute();
					}else{
						MyFlg.all_activitys.remove(Person_dataActivity.this);
						finish();
					}
					break;
				case 4: //选择科目
					if(!subjects[point].getK().equals(MyApplication.getuserInfoBean(Person_dataActivity.this).getSubject())){
						dialog.show();
						MyFlg.ISupdateHome = true;
						MyFlg.ISupdateguide = true;
						updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), null, null, null, null, null, subjects[point].getK(), null, null, false, 1,null,null,null,null,null);
						// updateUserAsync = new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), application.getuserInfoBean(Person_dataActivity.this).getCity(), null, null, application.getuserInfoBean(Person_dataActivity.this).getExam(), application.getuserInfoBean(Person_dataActivity.this).getExam_schedule(), subjects[point].getK(), null, null, false, 1,null,null);
						updateUserAsync.execute();
					}else{
						MyFlg.all_activitys.remove(Person_dataActivity.this);
						finish();
					}

					break;
				default:
					break;
			}
		}

	}

	private void person_onitemclick(int point){

		Intent intent = new Intent(Person_dataActivity.this, User_Update_Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		switch (point) {
			case 1: //昵称
				intent.putExtra("type", 0);
				intent.putExtra("title", "修改昵称");
				startActivity(intent);
				break;
			case 2://账号信息
				intent.putExtra("type", 1);
				intent.putExtra("title", "修改手机号");
				startActivity(intent);
				break;
			case 4://修改密码
				intent.putExtra("type", 2);
				intent.putExtra("title", "修改密码");
				startActivity(intent);
				break;
			default:
				break;
		}
	}
	/**
	 * 定位
	 */
	private void LoactionNow(){
		locationClient = new LocationClient(this);
		//设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);        //是否打开GPS
		option.setCoorType("bd09ll");       //设置返回值的坐标类型。
		//option.setPriority(LocationClientOption.MIN_SCAN_SPAN_NETWORK);  //设置定位优先级
		option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		//option.setScanSpan(1000000);    //设置定时定位的时间间隔。单位毫秒
		locationClient.setLocOption(option);
		locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				if(location.getLatitude()!=0 && location.getLongitude()!=0 ){
					latitude = location.getLatitude()+"";
					lontitude = location.getLongitude()+"";
					locationClient.stop();
					BaiduMapGetCity baiduMapGetCity = new BaiduMapGetCity(Person_dataActivity.this,handler,latitude, lontitude);
					baiduMapGetCity.execute();
				}

			}
		});
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0: //定位
					adpater.notifyDataSetInvalidated();
					//list_city.get(arg0).getAndkey();
					int point = 0;
					if(null !=MyFlg.baidu_city){
						for (int i = 0; i < list_city.size(); i++) {
							if(MyFlg.baidu_city.contains(list_city.get(i).getAndkey())){
								point = i;
								break;
							}
						}
					}
					listview.setSelection(point);
					break;
				case 1://提交用户信息返回
					DataUtil.getUserInfoAndBaseInfo(Person_dataActivity.this);
					if(type==1){ //城市
						//设置极光标签
						Set<String> tagSet = new LinkedHashSet<String>();
						tagSet.add(MyApplication.getuserInfoBean(Person_dataActivity.this).getCity());
						MyFlg.setJPushTag(Person_dataActivity.this, tagSet);
					}
					MyFlg.all_activitys.remove(Person_dataActivity.this);
					finish();
					break;
			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			locationClient = null;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(Person_dataActivity.this);
			finish();
			return false;
		}
		return false;
	}

}
