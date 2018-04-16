package com.changeCity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.net.dingwei.AsyncUtil.BaiduMapGetCity;
import cn.net.dingwei.Bean.CitysBean;
import cn.net.dingwei.ui.ParentActivity;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.GradientDrawableUtil;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.changeCity.SideBar.OnTouchingLetterChangedListener;
import com.changeCity.SortModel.hot_city;

/**
 * 切换城市
 * @author Administrator
 *
 */
public class ChageCityActivity extends ParentActivity implements OnClickListener{
	private SharedPreferences sharedPreferences;
	private int Bgcolor_1=0,Bgcolor_2=0,Fontcolor_7=0;
	private EditText filter_edit;
	private SideBar sideBar;
	private TextView dialog;
	private ListView sortListView;

	private List<SortModel> list_data;
	private List<SortModel> list_data_now;
	private SortAdapter adapter;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private LinearLayout linear_Left;
	private String location_city;
	private LocationClient locationClient;
	//private myHandler handler=new myHandler();
	private CitysBean citysBean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_city);
		InitColor();
		initID();
		initData();
	}
	private void InitColor() {
		// TODO Auto-generated method stub
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_2);
		filter_edit=(EditText)findViewById(R.id.filter_edit);
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sortListView = (ListView) findViewById(R.id.sortListView);
		TextView title_tx = (TextView) findViewById(R.id.title_tx);
		linear_Left=(LinearLayout)findViewById(R.id.linear_Left);

		title_tx.setText("选择地区");
		sideBar.setTextView(dialog);
		filter_edit.setBackgroundDrawable(GradientDrawableUtil.setGradientDrawable(1, Bgcolor_2, Color.WHITE, 10));
		sideBar.setColor(Bgcolor_2, Bgcolor_1, Color.TRANSPARENT, Color.LTGRAY);
		filter_edit.setTextColor(Bgcolor_1);
		filter_edit.setHintTextColor(Fontcolor_7);

		linear_Left.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		citysBean = APPUtil.getCommonInfo_citys(this);
		list_data = new ArrayList<SortModel>();
		list_data_now = new ArrayList<SortModel>();
		if(null!=citysBean&&null!=citysBean.getHotcity()){
			SortModel model2 = new SortModel();
			model2.setName("Hot");
			model2.setSortLetters("#");
			model2.setKey("#");
			SortModel.hot_city[] hot_citys = new SortModel.hot_city[citysBean.getHotcity().length];
			for (int i = 0; i < citysBean.getHotcity().length; i++) {
				CitysBean.city hot = citysBean.getHotcity()[i];
				SortModel.hot_city hot_city = new SortModel().new hot_city();
				hot_city.setKey(hot.getK());
				hot_city.setName(hot.getN());
				hot_citys[i] = hot_city;
			}
			if(hot_citys.length>0){//如果有热门城市
				model2.setHot_city(hot_citys);
				model2.setSz("#");
				list_data.add(model2);
			}

			for (int i = 0; i < citysBean.getAllcity().length; i++) {
				CitysBean.city allcity = citysBean.getAllcity()[i];
				SortModel model = new SortModel();
				model.setKey(allcity.getK());
				model.setName(allcity.getN());
				model.setSz(allcity.getSz());
				model.setSortLetters(allcity.getPy());
				list_data.add(model);
			}

		}

		location_city = intent.getStringExtra("location_city");
		if(null!=location_city && !location_city.equals("")){
			SortModel model = new SortModel();
			for (int i = 0; i <list_data.size(); i++) {
				if(list_data.get(i).getName().contains(location_city)){
					model.setKey(list_data.get(i).getKey());
					model.setSz(list_data.get(i).getSz());
				}
			}
			model.setName(location_city);
			model.setSortLetters("!");
			list_data.add(model);
		}else{
			SortModel model = new SortModel();
			model.setKey("无");
			model.setName("正在定位...");
			model.setSortLetters("!");
			model.setSz("!");
			list_data.add(model);
			LoactionNow();
			locationClient.start();
		}

		pinyinComparator = new PinyinComparator();
		Collections.sort(list_data, pinyinComparator);
		adapter = new SortAdapter(this, list_data,list_data);
		list_data_now= list_data;

		sortListView.setAdapter(adapter);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("select_city", list_data_now.get(point).getName());
				if (list_data_now.get(point).getKey().equals("bj")){
					intent.putExtra("key", "3");
				}else {
					intent.putExtra("key", list_data_now.get(point).getKey());
				}
				setResult(RESULT_OK, intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);//左进右出
			}

		});
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO Auto-generated method stub
				if(s.toUpperCase().equals("热门")){
					sortListView.setSelection(0);
				}else{
					//该字母首次出现的位置
					int position = adapter.getPositionForSection(s.charAt(0));
					if(position != -1){
						sortListView.setSelection(position);
					}
				}

			}
		});
		//根据输入框输入值的改变来过滤搜索
		filter_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		filterStr=filterStr.toLowerCase();
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if(TextUtils.isEmpty(filterStr)){
			filterDateList = list_data;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : list_data){
				String name = sortModel.getName();
				String sz =sortModel.getSz();
				if(!filterStr.toString().equals("#")&&!filterStr.toString().equals("!")){
					if ((null!=sz&&sz.indexOf(filterStr.toString()) != -1)
							|| (null!=sz&&sz.startsWith(filterStr.toString()))
							|| (null!=name&&name.indexOf(filterStr.toString()) != -1)
							|| (null!=sortModel&&sortModel.getSortLetters().startsWith(filterStr.toString()))) {
						filterDateList.add(sortModel);
					}
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
		list_data_now= filterDateList;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.linear_Left:
				finish();
				overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);//左进右出
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
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
		);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=1000;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		option.setProdName(getResources().getString(R.string.app_name));
		locationClient.setLocOption(option);
		locationClient.registerLocationListener(new BDLocationListener() {
			private String lontitude;
			private String latitude;

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				if(TextUtils.isEmpty(location.getProvince())){
					for (int i = 0; i < list_data.size(); i++) {
						if(list_data.get(i).getSortLetters().equals("!")){
							list_data.get(i).setName("定位失败，请稍后重试!");
							break;
						}
					}
					// 根据a-z进行排序
					Collections.sort(list_data, pinyinComparator);
					adapter.updateListView(list_data);
					return;
				}
				Log.i("mylog",location.getProvince()+"信息"+location.toString());
				MyFlg.baidu_city = location.getProvince();
				MyFlg.baidu_city=MyFlg.baidu_city.replace("市", "");
				MyFlg.baidu_city=MyFlg.baidu_city.replace("省", "");
				MyFlg.baidu_city=MyFlg.baidu_city.replace("市辖区", "");
				if(null!=MyFlg.baidu_city &&!MyFlg.baidu_city.equals("0") ){
					for (int i = 0; i < list_data.size(); i++) {
						if(list_data.get(i).getSortLetters().equals("!")){
							list_data.get(i).setName(MyFlg.baidu_city);
							for (int k = 0; k <list_data.size(); k++) {
								if(list_data.get(k).getName().contains(MyFlg.baidu_city)){
									list_data.get(i).setKey(list_data.get(k).getKey());
									list_data.get(i).setSz(list_data.get(k).getSz());
								}
							}
							break;
						}
					}
					// 根据a-z进行排序
					Collections.sort(list_data, pinyinComparator);
					adapter.updateListView(list_data);
				}
				locationClient.stop();
				locationClient = null;
				/*if(location.getLatitude()!=0 && location.getLongitude()!=0 ){
					lontitude = location.getLongitude()+"";
					latitude = location.getLatitude()+"";
					locationClient.stop();
					BaiduMapGetCity baiduMapGetCity = new BaiduMapGetCity(ChageCityActivity.this,handler,latitude, lontitude);
					baiduMapGetCity.execute();
				}*/

			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			locationClient = null;
		}
	}
	/*class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0://定位回来
					if(null!=MyFlg.baidu_city &&!MyFlg.baidu_city.equals("0") ){
						for (int i = 0; i < list_data.size(); i++) {
							if(list_data.get(i).getSortLetters().equals("!")){
								list_data.get(i).setName(MyFlg.baidu_city);
								for (int k = 0; k <list_data.size(); k++) {
									if(list_data.get(k).getName().contains(MyFlg.baidu_city)){
										list_data.get(i).setKey(list_data.get(k).getKey());
										list_data.get(i).setSz(list_data.get(k).getSz());
									}
								}
								break;
							}
						}
						// 根据a-z进行排序
						Collections.sort(list_data, pinyinComparator);
						adapter.updateListView(list_data);
					}
					break;
				case -1://定位失败
					for (int i = 0; i < list_data.size(); i++) {
						if(list_data.get(i).getSortLetters().equals("!")){
							list_data.get(i).setName("定位失败，请稍后重试!");
							break;
						}
					}
					// 根据a-z进行排序
					Collections.sort(list_data, pinyinComparator);
					adapter.updateListView(list_data);
					break;
				default:
					break;
			}
		}
	}*/
}
