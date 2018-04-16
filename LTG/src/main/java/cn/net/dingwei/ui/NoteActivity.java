package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.Get_question_noteBean;
import cn.net.dingwei.adpater.Note_Listview_adapter;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import listview_DownPullAndUpLoad.XListView;
import listview_DownPullAndUpLoad.XListView.IXListViewListener;
/**
 * 题目的笔记列表
 * @author Administrator
 *
 */
public class NoteActivity extends BackActivity{
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1 = 0, Fontcolor_3 = 0, Fontcolor_7 = 0,Bgcolor_1 = 0, Bgcolor_2 = 0, Bgcolor_5 = 0, Bgcolor_6 = 0;
	private XListView listview;
	private Note_Listview_adapter adapter;
	private MyApplication application;
	//private FYuanTikuDialog dialog;
	private String qid="";
	private int lastID=0;
	private List<Get_question_noteBean.notes> listData;
	//private note_bean me;
	private String Author_name="";
	private String Content="";
	private String Time_text="";
	private String HeadUrl="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		application = MyApplication.myApplication;
		//dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载...");
		initColor();
		initID();
		initData();
	}
	private void initColor() {
		// TODO Auto-generated method stub
		sharedPreferences = getSharedPreferences("commoninfo",Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3 = sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7 = sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_5 = sharedPreferences.getInt("bgcolor_5", 0);
		Bgcolor_6 = sharedPreferences.getInt("bgcolor_6", 0);
	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		TextView title_tx = (TextView)findViewById(R.id.title_tx);
		title_tx.setText("笔记");
		listview=(XListView)findViewById(R.id.listview);
	}
	private void initData() {
		// TODO Auto-generated method stub
		listData = new ArrayList<Get_question_noteBean.notes>();
		Intent intent = getIntent();
		qid = intent.getStringExtra("qid");
		Author_name=intent.getStringExtra("Author_name");
		Content=intent.getStringExtra("Content");
		Time_text=intent.getStringExtra("Time_text");
		HeadUrl = intent.getStringExtra("HeadUrl");


		ListviewListener listviewListener = new ListviewListener();
		listview.setFistLoad(true);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(listviewListener);
		getData(1);
		
		/*adapter = new Note_Listview_adapter(this);
		listview.setAdapter(adapter);*/
	}
	//Listview的上拉加载和下拉刷新
	class ListviewListener implements IXListViewListener{

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			lastID = 0;
			//listview.stopRefresh();
			getData(1);
		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			//listview.stopLoadMore();
			getData(2);
		}

	}
	/**
	 * 1  下拉刷新     2上拉加载
	 * @param type
	 */
	private void getData(final int type){
		String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(NoteActivity.this).getGet_question_note(), NoteActivity.this);
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.add("a", MyFlg.a);
		params.add("ver", MyFlg.android_version);
		params.add("clientcode", MyFlg.getclientcode(NoteActivity.this));
		params.add("qid", qid);
		params.add("last_id", lastID+"");
		client.post(apiUrl, params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String result = Sup.UnZipString(responseBody);
				Gson gson = new Gson();
				Get_question_noteBean bean = new Get_question_noteBean();
				bean = gson.fromJson(result, Get_question_noteBean.class);
				if(bean.getStatus()==false){
					Toast.makeText(NoteActivity.this, "加载失败，请稍后重试。", 0).show();
				}else{
					lastID = bean.getData().getLast_id();
					if(type==1){//下拉刷新
						listview.setFistLoad(true);
						listData.clear();

						//添加自己的
						Get_question_noteBean.notes temp_me = new Get_question_noteBean().new notes();
						temp_me.setAuthor_name(Author_name);
						temp_me.setContent(Content);
						temp_me.setTime_text(Time_text);
						temp_me.setIcon(HeadUrl);
						listData.add(temp_me);
						for (int i = 0; i < bean.getData().getNotes().length; i++) {
							listData.add(bean.getData().getNotes()[i]);
						}
						if(null==adapter){
							adapter = new Note_Listview_adapter(NoteActivity.this,listData);
							listview.setAdapter(adapter);
						}else{
							adapter.notifyDataSetInvalidated();
						}
						listview.stopRefresh();
						//全部加载完  （调用此方法）
						if(bean.getData().getNext()==0){
							listview.setLoading_all_over();
						}else{
							listview.initFotter();
						}
					}else if(type==2){
						for (int i = 0; i < bean.getData().getNotes().length; i++) {
							listData.add(bean.getData().getNotes()[i]);
						}
						adapter.notifyDataSetChanged();
						listview.stopLoadMore();
						//全部加载完  （调用此方法）
						if(bean.getData().getNext()==0){
							listview.setLoading_all_over();
						}else{
							listview.initFotter();
						}
					}
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
				Toast.makeText(NoteActivity.this, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}

		});
	}
}
