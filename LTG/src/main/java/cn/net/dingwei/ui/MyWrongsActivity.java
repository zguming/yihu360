package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.MyWrongsBean;
import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.dingwei.adpater.MyCollectOrWrongsAdapter;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.MyScrollView;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.treeListView.Node;
import cn.net.treeListView.TreeListViewAdapter.OnTreeNodeClickListener;
import cn.net.tmjy.mtiku.futures.R;

/**
 * 我的错题
 * @author Administrator
 *
 */
public class MyWrongsActivity extends BackActivity implements OnClickListener{
	private SharedPreferences sharedPreferences;
	private int color_101=0,color_102,color_103,color_101_1,color_102_1,color_103_1,Bgcolor_1,Bgcolor_2;
	private TextView title_tx,text1_number,text2_number,text3_number;
	private List<TreeListViewBean> datas = new ArrayList<TreeListViewBean>();
	private ListView listview;
	private MyCollectOrWrongsAdapter<TreeListViewBean> mAdapter;
	private LinearLayout linear1,linear2,linear3;
	private int Screen_width=0;
	private Drawable drawable1,drawable2,drawable3;
	private MyScrollView myscrollview;
	private TextView no_data;
	private FYuanTikuDialog dialog;
	private MyApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wrongs);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(MyWrongsActivity.this,R.style.DialogStyle,"正在加载");
		initColor();
		initID();
		Intent intent = getIntent();
		MyWrongsBean bean = (MyWrongsBean) intent.getSerializableExtra("bean");
		initData(bean);
	}
	private void initColor() {
		// TODO Auto-generated method stub
		sharedPreferences = getSharedPreferences("commoninfo",Context.MODE_PRIVATE);
		color_101 = sharedPreferences.getInt("color_101", 0);
		color_102 = sharedPreferences.getInt("color_102", 0);
		color_103 = sharedPreferences.getInt("color_103", 0);
		color_101_1 = sharedPreferences.getInt("color_101_1", 0);
		color_102_1 = sharedPreferences.getInt("color_102_1", 0);
		color_103_1 = sharedPreferences.getInt("color_103_1", 0);
		Bgcolor_1 =  sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 =  sharedPreferences.getInt("bgcolor_2", 0);
		drawable1 = MyFlg.setViewRaduisAndTouch(color_101, color_101_1, color_101_1, 1, 10);
		drawable2 = MyFlg.setViewRaduisAndTouch(color_102, color_102_1, color_102_1, 1, 10);
		drawable3 = MyFlg.setViewRaduisAndTouch(color_103, color_103_1, color_103_1, 1, 10);
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		title_tx = (TextView)findViewById(R.id.title_tx);
		title_tx.setText("我的错题");

		text1_number = (TextView)findViewById(R.id.text1_number);
		text2_number = (TextView)findViewById(R.id.text2_number);
		text3_number = (TextView)findViewById(R.id.text3_number);
		listview = (ListView)findViewById(R.id.listview);
		linear1 = (LinearLayout)findViewById(R.id.linear1);
		linear2 = (LinearLayout)findViewById(R.id.linear2);
		linear3 = (LinearLayout)findViewById(R.id.linear3);
		myscrollview = (MyScrollView)findViewById(R.id.myscrollview);
		no_data = (TextView)findViewById(R.id.no_data);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);


		linear1.setBackgroundDrawable(drawable1);
		linear2.setBackgroundDrawable(drawable2);
		linear3.setBackgroundDrawable(drawable3);

		linear1.setOnClickListener(this);
		linear2.setOnClickListener(this);
		linear3.setOnClickListener(this);

	}
	private void initData(MyWrongsBean bean) {
		// TODO Auto-generated method stub
		datas.clear();
		MyWrongsBean.data data = bean.getData();
		if(null==data){
			myscrollview.setVisibility(View.GONE);
			no_data.setVisibility(View.VISIBLE);
		}else{
			text1_number.setText(data.getWro_t()+"");
			text2_number.setText(data.getWro_k()+"");
			text3_number.setText(data.getWro_c()+"");

			linear1.setVisibility(View.VISIBLE);
			linear2.setVisibility(View.VISIBLE);
			linear3.setVisibility(View.VISIBLE);
			int dip_2 = DensityUtil.DipToPixels(MyWrongsActivity.this, 1);
			int dip_60 = DensityUtil.DipToPixels(MyWrongsActivity.this, 70);
			//30 左右间隔   4为间距  dip_60*3 3个控件基础宽
			int width = Screen_width-DensityUtil.DipToPixels(MyWrongsActivity.this, 34)-dip_60*3;
			int width1 = width*data.getWro_tr()/100+dip_60;
			int width2 = width*data.getWro_kr()/100+dip_60;
			int width3 = width*data.getWro_cr()/100+dip_60;
				/*if(width1>width-dip_60*2){
					width1=width-dip_60*2;
				}else if(width1<dip_60){
					width1 = dip_60;
				}
				if(width2>width-dip_60*2){
					width2=width-dip_60*2;
				}else if(width2<dip_60){
					width2 = dip_60;
				}
				if(width3>width-dip_60*2){
					width3=width-dip_60*2;
				}else if(width3<dip_60){
					width3 = dip_60;
				}*/
			LayoutParams params_progress1 = new LayoutParams(width1, LayoutParams.WRAP_CONTENT);
			LayoutParams params_progress2 = new LayoutParams(width2, LayoutParams.WRAP_CONTENT);
			params_progress2.setMargins(dip_2, 0, dip_2, 0);
			LayoutParams params_progress3 = new LayoutParams(width3, LayoutParams.WRAP_CONTENT);
			linear1.setLayoutParams(params_progress1);
			linear2.setLayoutParams(params_progress2);
			linear3.setLayoutParams(params_progress3);

			if(null==bean.getData().getWrongs()){
				Toast.makeText(this, "暂无错题 ", 0).show();
			}else{
				int length = bean.getData().getWrongs().length;//计算数组的长度
				int length2 = 0; //二级
				int length3 = 0;//3级
				length2=length2+length;
				for (int i = 0; i < bean.getData().getWrongs().length; i++) {
					MyWrongsBean.points2[] points2 = bean.getData().getWrongs()[i].getPoints();
					if(null!=points2 && points2.length>0){ //2级
						length2 = length2+points2.length;
						for (int i2 = 0; i2 < points2.length; i2++) {
							MyWrongsBean.points3[] points3 = points2[i2].getPoints();
							if(null!=points3 && points3.length>0){ //3级
								length3 = length3+points3.length;
							}
						}
					}
				}
				length3 = length3+length2;


				int index_2=length;//第二级分类 起始坐标
				int index_3=length2;//第三级分类 起始坐标
				int index_4=length3;//第四级分类 起始坐标
				for (int i = 0; i < bean.getData().getWrongs().length; i++) {//1级
					MyWrongsBean.wrongs wrong= bean.getData().getWrongs()[i];
					datas.add(new TreeListViewBean(i+1, 0, wrong.getName(),wrong.getWro_tr(),wrong.getWro_kr(),wrong.getWro_cr(),wrong.getTotal(),0,wrong.getId()));
					MyWrongsBean.points2[] points2 = wrong.getPoints();
					if(null!=points2 && points2.length>0){ //2级
						for (int i2 = 0; i2 < points2.length; i2++) {
							index_2 =index_2+1; //当前的坐标
							if(i2==points2.length-1){ //设置当前级别最后一个ITem的标识
								datas.add(new TreeListViewBean(index_2, i+1, points2[i2].getName(),points2[i2].getWro_tr(),points2[i2].getWro_kr(),points2[i2].getWro_cr(),points2[i2].getTotal(),1,points2[i2].getId()));
							}else{
								datas.add(new TreeListViewBean(index_2, i+1, points2[i2].getName(),points2[i2].getWro_tr(),points2[i2].getWro_kr(),points2[i2].getWro_cr(),points2[i2].getTotal(),0,points2[i2].getId()));
							}

							MyWrongsBean.points3[] points3 = points2[i2].getPoints();
							if(null!=points3 && points3.length>0){ //3级
								for (int i3 = 0; i3 < points3.length; i3++) {
									index_3 = index_3+1;//当前的坐标
									if(i2==points2.length-1&&i3==points3.length-1){//设置当前级别最后一个ITem的标识
										datas.add(new TreeListViewBean(index_3, index_2, points3[i3].getName(),points3[i3].getWro_tr(),points3[i3].getWro_kr(),points3[i3].getWro_cr(),points3[i3].getTotal(),1,points3[i3].getId()));
									}else{
										datas.add(new TreeListViewBean(index_3, index_2, points3[i3].getName(),points3[i3].getWro_tr(),points3[i3].getWro_kr(),points3[i3].getWro_cr(),points3[i3].getTotal(),0,points3[i3].getId()));
									}

									MyWrongsBean.points4[] points4 = points3[i3].getPoints();
									if(null!=points4 && points4.length>0){ //4级
										for (int i4 = 0; i4< points4.length; i4++) {
											index_4=index_4+1;//当前的坐标
											if(i2==points2.length-1&&i3==points3.length-1&&i4==points4.length-1){
												datas.add(new TreeListViewBean(index_4, index_3, points4[i4].getName(),points4[i4].getWro_tr(),points4[i4].getWro_kr(),points4[i4].getWro_cr(),points4[i4].getTotal(),1,points4[i4].getId()));
											}else{
												datas.add(new TreeListViewBean(index_4, index_3, points4[i4].getName(),points4[i4].getWro_tr(),points4[i4].getWro_kr(),points4[i4].getWro_cr(),points4[i4].getTotal(),0,points4[i4].getId()));
											}

										}
									}
								}
							}
						}
					}


				}
			}
		}
		//设置事件  适配器
		try
		{
			mAdapter = new MyCollectOrWrongsAdapter<TreeListViewBean>(listview, this, datas, 0);
			mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener()
			{
				@Override
				public void onClick(Node node, int position)
				{
					if (node.isLeaf())
					{
						//Toast.makeText(getApplicationContext(), node.getName(),Toast.LENGTH_SHORT).show();
					}
				}

			});

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		listview.setAdapter(mAdapter);
		//	mAdapter.notifyDataSetChanged();
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
	 * @param type    1 我的错题  2 我的收藏 
	 */
	private void PostApi(RequestParams params,String apiUrl){
		dialog.show();
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		client.post(apiUrl, params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String result = Sup.UnZipString(responseBody);
				Gson gson = new Gson();
				MyWrongsBean bean = new MyWrongsBean();
				bean = gson.fromJson(result, MyWrongsBean.class);
				if(bean.getStatus()==false){
					Toast.makeText(MyWrongsActivity.this, "加载失败，请稍后重试。", 0).show();
				}else{
					initData(bean);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(MyWrongsActivity.this, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 2&&resultCode == RESULT_OK){//进行了做题
			//刷新列表
			RequestParams params = new RequestParams();
			params.add("a", MyFlg.a);
			params.add("ver", MyFlg.android_version);
			params.add("clientcode",MyFlg.getclientcode(MyWrongsActivity.this));
			PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(MyWrongsActivity.this).getGet_user_wrongs(),MyWrongsActivity.this));
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
					String json = new JSONObject(result).getJSONObject("data").getString("suitdata");
					Create_Exercise_suit_2Bean  create_Exercise_suit_2Bean = gson.fromJson(json, Create_Exercise_suit_2Bean.class);
					if(null==create_Exercise_suit_2Bean){
						Toast.makeText(context, "创建失败", 0).show();
						return;
					}
					Intent intent = new Intent(context, Reading_QuestionsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bean", create_Exercise_suit_2Bean);
					intent.putExtras(bundle);
					intent.putExtra("flg", 1);
					((Activity) context).startActivityForResult(intent, 2);
					((Activity) context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(context, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.add("ver",MyFlg.android_version);
		params.add("a",MyFlg.a);
		params.add("clientcode",MyFlg.getclientcode(MyWrongsActivity.this));
		params.add("exercises_type","ctgg");
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(MyWrongsActivity.this).getCreate_exercise_suit(),MyWrongsActivity.this);
		//params.add("exercises_option","["+node.getOption_id()+"]");
		String num1 =text1_number.getText().toString().trim();
		String num2 =text2_number.getText().toString().trim();
		String num3 =text3_number.getText().toString().trim();

		switch (v.getId()) {
			case R.id.linear1: //待消灭
				if(num1.equals("0")){
					Toast.makeText(MyWrongsActivity.this, "暂无待消灭错题！", 0).show();
				}else{
					params.add("type","2");
					PostApi_create(MyWrongsActivity.this, params, apiUrl);
				}
				break;
			case R.id.linear2://将消灭
				if(num2.equals("0")){
					Toast.makeText(MyWrongsActivity.this, "暂无将消灭错题！", 0).show();
				}else{
					params.add("type","1");
					PostApi_create(MyWrongsActivity.this, params, apiUrl);
				}
				break;
			case R.id.linear3://已消灭
				if(num3.equals("0")){
					Toast.makeText(MyWrongsActivity.this, "暂无已消灭错题！", 0).show();
				}else{
					params.add("type","0");
					PostApi_create(MyWrongsActivity.this, params, apiUrl);
				}
				break;
			default:
				break;
		}


	}
}
