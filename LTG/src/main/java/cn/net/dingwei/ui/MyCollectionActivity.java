package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.MyCollectBean;
import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.dingwei.adpater.MyCollectAdapter;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.MyScrollView;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.treeListView.Node;
import cn.net.treeListView.TreeListViewAdapter.OnTreeNodeClickListener;
import cn.net.tmjy.mtiku.futures.R;

public class MyCollectionActivity extends BackActivity{
	private SharedPreferences sharedPreferences;
	private int color_101=0,color_102,color_103,color_101_1,color_102_1,color_103_1,Bgcolor_1,Bgcolor_2;
	private ListView listview;
	private MyScrollView myscrollview;
	private TextView no_data;
	private List<TreeListViewBean> datas = new ArrayList<TreeListViewBean>();
	private MyCollectAdapter<TreeListViewBean> mAdapter;
	private FYuanTikuDialog dialog;
	private MyApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(MyCollectionActivity.this,R.style.DialogStyle,"正在加载");
		initColor();
		initID();
		Intent intent = getIntent();
		MyCollectBean bean = (MyCollectBean) intent.getSerializableExtra("bean");
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
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		TextView title_tx = (TextView)findViewById(R.id.title_tx);
		listview = (ListView)findViewById(R.id.listview);
		myscrollview = (MyScrollView)findViewById(R.id.myscrollview);
		no_data = (TextView)findViewById(R.id.no_data);

		title_tx.setText("我的收藏");
	}
	private void initData(MyCollectBean bean) {
		// TODO Auto-generated method stub
		datas.clear();
		MyCollectBean.data[] data = bean.getData();
		if(null==data){
			myscrollview.setVisibility(View.GONE);
			no_data.setVisibility(View.VISIBLE);
		}else{
			if(data.length<=0){
				myscrollview.setVisibility(View.GONE);
				no_data.setVisibility(View.VISIBLE);
				return;
			}
			int length = data.length;//计算数组的长度
			int length2 = 0; //二级
			int length3 = 0;//3级

			for (int i = 0; i < data.length; i++) {
				MyCollectBean.points2[] points2 = data[i].getPoints();
				if(null!=points2 && points2.length>0){ //2级
					length2 = length2+points2.length;
					for (int i2 = 0; i2 < points2.length; i2++) {
						MyCollectBean.points3[] points3 = points2[i2].getPoints();
						if(null!=points3 && points3.length>0){ //3级
							length3 = length3+points3.length;
						}
					}
				}
			}
			length2=length2+length;
			length3 = length3+length2;

			int index_2=length;//第二级分类 起始坐标
			int index_3=length2;//第三级分类 起始坐标
			int index_4=length3;//第四级分类 起始坐标
			for (int i = 0; i < data.length; i++) {//1级
				MyCollectBean.data temp_data= data[i];
				datas.add(new TreeListViewBean(i+1, 0, temp_data.getName(),0,0,0,temp_data.getC_t(),0,""+temp_data.getId()));

				MyCollectBean.points2[] points2 = temp_data.getPoints();
				if(null!=points2 && points2.length>0){ //2级
					for (int i2 = 0; i2 < points2.length; i2++) {
						index_2 =index_2+1; //当前的坐标
						if(i2==points2.length-1){ //设置当前级别最后一个ITem的标识
							datas.add(new TreeListViewBean(index_2, i+1, points2[i2].getName(),0,0,0,points2[i2].getC_t(),1,points2[i2].getId()+""));
						}else{
							datas.add(new TreeListViewBean(index_2, i+1, points2[i2].getName(),0,0,0,points2[i2].getC_t(),0,points2[i2].getId()+""));
						}

						MyCollectBean.points3[] points3 = points2[i2].getPoints();
						if(null!=points3 && points3.length>0){ //3级
							for (int i3 = 0; i3 < points3.length; i3++) {
								index_3 = index_3+1;//当前的坐标
								if(i2==points2.length-1&&i3==points3.length-1){//设置当前级别最后一个ITem的标识
									datas.add(new TreeListViewBean(index_3, index_2, points3[i3].getName(),0,0,0,points3[i3].getC_t(),1,points3[i3].getId()+""));
								}else{
									datas.add(new TreeListViewBean(index_3, index_2, points3[i3].getName(),0,0,0,points3[i3].getC_t(),0,points3[i3].getId()+""));
								}

								MyCollectBean.points4[] points4 = points3[i3].getPoints();
								if(null!=points4 && points4.length>0){ //4级
									for (int i4 = 0; i4< points4.length; i4++) {
										index_4=index_4+1;//当前的坐标
										if(i2==points2.length-1&&i3==points3.length-1&&i4==points4.length-1){
											datas.add(new TreeListViewBean(index_4, index_3, points4[i4].getName(),0,0,0,points3[i3].getC_t(),1,points4[i4].getId()+""));
										}else{
											datas.add(new TreeListViewBean(index_4, index_3, points4[i4].getName(),0,0,0,points3[i3].getC_t(),0,points4[i4].getId()+""));
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
					mAdapter = new MyCollectAdapter<TreeListViewBean>(listview, this, datas, 0);

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
			}
		}
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
			params.add("clientcode",MyFlg.getclientcode(MyCollectionActivity.this));
			PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(MyCollectionActivity.this).getGet_user_collect(),MyCollectionActivity.this));
		}
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
	 * @param type    刷新数据
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
				MyCollectBean bean = new MyCollectBean();
				bean = gson.fromJson(result, MyCollectBean.class);
				if(bean.getStatus()==false){
					Toast.makeText(MyCollectionActivity.this, "加载失败，请稍后重试。", 0).show();
				}else{
					initData(bean);
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(MyCollectionActivity.this, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}
}
