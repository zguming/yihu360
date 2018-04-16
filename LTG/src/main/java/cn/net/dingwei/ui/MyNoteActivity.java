package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.MyNotesBean;
import cn.net.dingwei.adpater.MyNoteListViewAdapter;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import listview_DownPullAndUpLoad.XListView;
import listview_DownPullAndUpLoad.XListView.IXListViewListener;

/**
 * 我的笔记
 * @author Administrator
 *
 */
public class MyNoteActivity extends BackActivity{
	private SharedPreferences sharedPreferences;
	private int  Fontcolor_3 = 0,Bgcolor_1 = 0;
	private XListView listview;
	private MyApplication application;
	private String lastID="";
	private List<MyNotesBean.notes> list_datas;
	private MyNoteListViewAdapter adapter;
	private FYuanTikuDialog dialog;
	private String datas="";
	private TextView no_data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mynote);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载...");
		initColor();
		initID();
		initData();
	}
	private void initColor() {
		// TODO Auto-generated method stub
		sharedPreferences = getSharedPreferences("commoninfo",Context.MODE_PRIVATE);
		Fontcolor_3 = sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		TextView title_tx = (TextView)findViewById(R.id.title_tx);
		listview = (XListView)findViewById(R.id.listview);
		title_tx.setText("我的笔记");
		listview.setAdapter(null);
		no_data = (TextView)findViewById(R.id.no_data);

		ListviewListener listviewListener = new ListviewListener();
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(listviewListener);
	}
	private void initData() {
		// TODO Auto-generated method stub
		list_datas= new ArrayList<MyNotesBean.notes>();
		getData(1);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				get_note_info(list_datas.get(point-1).getQid());
			}
		});

	}
	//Listview的上拉加载和下拉刷新
	class ListviewListener implements IXListViewListener{

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			getData(1);
		}

		@Override
		public void onLoadMore() {
			getData(2);
		}

	}
	/**
	 * 1  下拉刷新     2上拉加载
	 * @param type
	 */
	private void getData(final int type){
		if(type==1){
			lastID="";
		}
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(MyNoteActivity.this).getGet_user_note(), MyNoteActivity.this);
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(MyNoteActivity.this));
		params.add("last_id", lastID+"");
		client.post(apiUrl, params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String result = Sup.UnZipString(responseBody);
				Gson gson = new Gson();
				if(type==1){
					list_datas.clear();
					listview.stopRefresh();
					listview.setFistLoad(true);
				}else if(type==2){
					listview.stopLoadMore();
				}
				MyNotesBean bean = new MyNotesBean();
				bean = gson.fromJson(result, MyNotesBean.class);
				if(bean.getStatus()==false){
					Toast.makeText(MyNoteActivity.this, "加载失败。", 0).show();
				}else{
					lastID = bean.getData().getLast_id();
					if(null!=bean.getData().getNotes()){
						for (int i = 0; i < bean.getData().getNotes().length; i++) {
							list_datas.add(bean.getData().getNotes()[i]);
						}
					}
				}
				datas = bean.getData().getDatas()+"";
				if(datas.equals("0")){
					no_data.setVisibility(View.VISIBLE);
				}else{
					no_data.setVisibility(View.GONE);
				}
				if(null==adapter){
					adapter = new MyNoteListViewAdapter(MyNoteActivity.this,list_datas,datas);
					listview.setAdapter(adapter);
				}else{
					adapter.user_note = datas;
					if(type==1){
						adapter.notifyDataSetInvalidated();
					}else if(type==2){
						adapter.notifyDataSetChanged();
					}

				}
				//全部加载完  （调用此方法）
				if(bean.getData().getNext()==0){
					listview.setLoading_all_over();
				}else{
					listview.initFotter();
				}


			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody,
								  Throwable error) {
				// TODO Auto-generated method stub
				//	dialog.dismiss();
				if(listview.getFistLoad()==false){
					listview.setNotInterNet_head();
				}
				listview.stopRefresh();
				listview.stopLoadMore();
				Toast.makeText(MyNoteActivity.this, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}

		});
	}
	/**
	 * 1  下拉刷新     2上拉加载
	 * @param type
	 */
	private void get_note_info(String qid){
		dialog.show();
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(MyNoteActivity.this).getGet_note_info(), MyNoteActivity.this);
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(MyNoteActivity.this));
		params.add("qid",qid);
		client.post(apiUrl, params,new AsyncHttpResponseHandler() {


			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String result = Sup.UnZipString(responseBody);
				try {
					Gson gson = new Gson();
					String json = new JSONObject(result).getJSONObject("data").getString("suitdata");
					Create_Exercise_suit_2Bean bean = new Create_Exercise_suit_2Bean();
					bean = gson.fromJson(json, Create_Exercise_suit_2Bean.class);
					if(null==bean){
						Toast.makeText(MyNoteActivity.this, "创建失败", 0).show();
						return;
					}
					Intent intent = new Intent(MyNoteActivity.this, Reading_QuestionsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bean", bean);
					intent.putExtra("type", 4);
					intent.putExtra("group_index", bean.getGroup());
					intent.putExtra("infos_index", bean.getInfos());
					intent.putExtra("question_index",bean.getQuestion());
					intent.putExtras(bundle);
					startActivityForResult(intent, 2);
					overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody,
								  Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(MyNoteActivity.this, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来
			}

		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 2&&resultCode == RESULT_OK){//进行了做题
			getData(1);
		}
	}
}
