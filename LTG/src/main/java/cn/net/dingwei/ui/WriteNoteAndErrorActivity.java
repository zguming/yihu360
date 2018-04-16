package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

import cn.net.dingwei.Bean.Correction_typeBean;
import cn.net.dingwei.Bean.Edit_user_noteBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.MyScrollView;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
/**
 * 写笔记    和 纠错
 * @author Administrator
 *
 */
public class WriteNoteAndErrorActivity extends BackActivity implements OnClickListener{

	private SharedPreferences sharedPreferences;
	private int Fontcolor_1 = 0, Fontcolor_3 = 0, Fontcolor_7 = 0,Bgcolor_1 = 0, Bgcolor_2 = 0, Bgcolor_5 = 0, Bgcolor_6 = 0;
	private int Screen_width=0,Screen_height=0;
	private int flg=0;// 1 编写笔记   2 纠错
	private LinearLayout linear_jiucuo,linear_buttom;
	//private String[] errorDatas = new String[]{"√ 丨 有错别字","√ 丨 答案有误","√ 丨 特别长的纠错特别长的纠错特别长的纠错","√ 丨 题目不全","√ 丨 图片有误","√ 丨 其他"};
	private int view_width = 0;//除去左右间隔 剩余的中间全部宽 计算按钮
	private EditText editText;
	private MyScrollView myScrollview;
	private MyApplication application;
	//参数
	private String suitid ="",qid ="";
	private FYuanTikuDialog dialog;
	//提示  
	private SharedPreferences sp_commoninfo;
	private Correction_typeBean.bean[] Correction_typeBean;
	private List<String> error_type=new ArrayList<String>();
	private TextView title_tx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_note);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在提交...");
		initColor();
		initID();
		initData();

		editText.requestFocus();
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
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		Screen_height=sharedPreferences.getInt("Screen_height", 0);


	}
	private void initID() {
		// TODO Auto-generated method stub
		findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
		title_tx = (TextView)findViewById(R.id.title_tx);
		TextView title_right = (TextView)findViewById(R.id.title_right);
		LinearLayout linear_right = (LinearLayout)findViewById(R.id.linear_right);
		linear_jiucuo = (LinearLayout)findViewById(R.id.linear_jiucuo);
		linear_buttom = (LinearLayout)findViewById(R.id.linear_buttom);
		editText = (EditText)findViewById(R.id.editText);
		myScrollview =  (MyScrollView)findViewById(R.id.myScrollview);
		TextView list_category_title = (TextView)findViewById(R.id.list_category_title);
		list_category_title.setTextColor(Fontcolor_3);

		linear_right.setVisibility(View.VISIBLE);
		myScrollview.setActivity(this);
		LayoutParams params = new LayoutParams(Screen_width, Screen_height/5);
		editText.setLayoutParams(params);
		editText.setHintTextColor(Fontcolor_7);
		editText.setTextColor(Fontcolor_3);

		Intent intent = getIntent();
		flg= intent.getIntExtra("flg", 0);
		String st =intent.getStringExtra("note_Content");
		suitid  = intent.getStringExtra("suitid");
		qid  = intent.getStringExtra("qid");
		if(flg==1){
			editText.setText(st);
			if(null==st){
				title_tx.setText("添加笔记");
			}else if(st.trim().length()<=0){
				title_tx.setText("添加笔记");
			}else{
				title_tx.setText("编辑笔记");
			}
			title_right.setText("保存");
			linear_buttom.setVisibility(View.GONE);
		}else if(flg==2){
			title_tx.setText("错题举报");
			title_right.setText("提交");
			editText.setHint("请描述题目的具体错误所在，方便工作人员对题目进行校正...");
		}
		linear_right.setOnClickListener(this);
	}
	private void initData() {
		// TODO Auto-generated method stub
		if(flg==1){//笔记


		}else if(flg==2){//纠错
			try {
				sp_commoninfo= getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
				Gson gson = new Gson();
				String json = new JSONObject(sp_commoninfo.getString("get_commoninfo", "0")).getString("data");
				Correction_typeBean = gson.fromJson(json, Correction_typeBean.class).getCorrection_typeBean();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			view_width =Screen_width-DensityUtil.DipToPixels(this, 20);
			//添加
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(15,15,15,0);//4个参数按顺序分别是左上右下

			LinearLayout liner = new LinearLayout(this);
			linear_jiucuo.addView(liner);
			int width_sum = 0;
			for (int j = 0; j < Correction_typeBean.length; j++) {
				Button button = new Button(this);
				button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
				button.setText(Correction_typeBean[j].getN());
				button.setSingleLine();
				button.setEllipsize(TruncateAt.END);
				int dip_15 = DensityUtil.DipToPixels(WriteNoteAndErrorActivity.this, 15);
				button.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Fontcolor_7, Bgcolor_2, Fontcolor_7,Bgcolor_2, 1, 0));
				button.setPadding(dip_15/3*2, dip_15/3, dip_15/3*2, dip_15/3);
				button.setLayoutParams(layoutParams);
				button.setTextColor(Fontcolor_1);
				button.setOnClickListener(new ButtonClick(j,Correction_typeBean[j].getK()));
				int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
				int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
				button.measure(w, h);
				int width =button.getMeasuredWidth();
				if((width_sum +width+30) < view_width){
					liner.addView(button);
					width_sum = width_sum+width+30;
				}else{
					width_sum=width;
					liner = new LinearLayout(this);
					linear_jiucuo.addView(liner);
					liner.addView(button);
				}
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.linear_right://保存(提交笔记)
				if(flg==1){
					String content = editText.getText().toString().trim();
					if(title_tx.getText().toString().trim().equals("添加笔记")&&content.length()<=0){
						Toast.makeText(WriteNoteAndErrorActivity.this, "请输入您想要记录的内容！", 0).show();
						return;
					}
					RequestParams params = new RequestParams();
					params.add("a", MyFlg.a);
					params.add("ver", MyFlg.android_version);
					params.add("clientcode",MyFlg.getclientcode(WriteNoteAndErrorActivity.this));
					params.add("suitid",suitid);
					params.add("qid",qid);
					params.add("content",content);
					PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(WriteNoteAndErrorActivity.this).getEdit_user_note(),WriteNoteAndErrorActivity.this));
				}else if(flg==2){//纠错
					String content = editText.getText().toString().trim();
					String type=error_type.toString().replace("[", "");
					type = type.replace("]", "");
					if(type.length()<=0&&content.length()<=0){
						Toast.makeText(WriteNoteAndErrorActivity.this, "请描述错误或选择错误类型！", 0).show();
						return;
					}
					RequestParams params = new RequestParams();
					params.add("a", MyFlg.a);
					params.add("ver", MyFlg.android_version);
					params.add("clientcode",MyFlg.getclientcode(WriteNoteAndErrorActivity.this));
					params.add("qid",qid);
					params.add("content",content);
					params.add("type",type);
					PostApi(params, MyFlg.get_API_URl(application.getCommonInfo_API_functions(WriteNoteAndErrorActivity.this).getCreate_error_correction(),WriteNoteAndErrorActivity.this));
				}
				break;

			default:
				break;
		}
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
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
				if(flg==1){
					try {
						Boolean status = new JSONObject(result).getBoolean("status");
						if(status==false){
							Toast.makeText(WriteNoteAndErrorActivity.this, "笔记保存失败。"+"", 0).show();
						}else{
							Edit_user_noteBean bean = new Edit_user_noteBean();
							bean = gson.fromJson(result, Edit_user_noteBean.class);
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							bundle.putSerializable("Edit_user_noteBean", bean);
							intent.putExtras(bundle);
							WriteNoteAndErrorActivity.this.setResult(RESULT_OK, intent);
							thisFinish();
							Toast.makeText(WriteNoteAndErrorActivity.this, "笔记保存成功。", 0).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(flg==2){
					try {
						Boolean status = new JSONObject(result).getBoolean("status");
						if(status){
							Toast.makeText(WriteNoteAndErrorActivity.this, "提交成功,感谢您的反馈。", 0).show();
							thisFinish();
						}else{
							String errorMsg= new JSONObject(result).getString("error");
							Toast.makeText(WriteNoteAndErrorActivity.this, errorMsg+"", 0).show();
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(WriteNoteAndErrorActivity.this, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}
	//Button的点击事件
	class ButtonClick implements OnClickListener{
		private int point;
		private String key;
		public ButtonClick(int point,String key) {
			// TODO Auto-generated constructor stub
			this.point= point;
			this.key = key;
		}
		@Override
		public void onClick(View v) { // tag True 表示已选择
			// TODO Auto-generated method stub
			if(null==v.getTag()){
				v.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, 0));
				error_type.add(key);
				v.setTag(true);
			}else if((Boolean)v.getTag()==false){
				v.setBackgroundDrawable(MyFlg.setViewRaduis(Bgcolor_2, Bgcolor_2, 1, 0));
				error_type.add(key);
				v.setTag(true);
			}else if((Boolean)v.getTag()==true){
				v.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Fontcolor_7, Bgcolor_2, Fontcolor_7,Bgcolor_2, 1, 0));
				error_type.remove(key);
				v.setTag(false);
			}
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			WriteNoteAndErrorActivity.this.finish();
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
