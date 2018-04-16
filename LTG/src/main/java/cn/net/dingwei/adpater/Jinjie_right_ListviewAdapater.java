package cn.net.dingwei.adpater;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.net.dingwei.Bean.Adv_project_infoBean;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.ui.Reading_QuestionsActivity;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Jinjie_right_ListviewAdapater extends BaseAdapter{
	private LayoutParams params1;
	private LayoutParams params2;
	private LayoutParams params3;
	private LayoutParams params4;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,color_101=0,color_102=0,color_103=0,Color_4=0;
	private int Screen_width=0;
	private int progress_width=0;
	private int dip_5;

	private Activity context;
	private List<Adv_project_infoBean.wrongs> list;
	private MyApplication application;
	private Dialog dialog;
	private String pid;
	public Jinjie_right_ListviewAdapater(Activity context,List<Adv_project_infoBean.wrongs> list,String pid,Dialog dialog) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list = list;
		this.dialog =dialog;
		this.pid = pid;
		dip_5 = DensityUtil.DipToPixels(context, 5);
		params1 =  new LayoutParams(dip_5*5, dip_5*5);
		params2 =  new LayoutParams(dip_5*4, dip_5*4);
		params3 =  new LayoutParams(dip_5*3, dip_5*3);
		params4 =  new LayoutParams(dip_5*2, dip_5*2);
		application = MyApplication.myApplication;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		color_101= sharedPreferences.getInt("color_101", 0);
		color_102= sharedPreferences.getInt("color_102", 0);
		color_103= sharedPreferences.getInt("color_103", 0);
		Color_4= sharedPreferences.getInt("color_4", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		progress_width = Screen_width - DensityUtil.DipToPixels(context, 105);//减去其他控件的宽度 剩下进度条的宽度
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(null==convertView){
			convertView = View.inflate(context, R.layout.item_jinjie_ctgg, null);
			viewHolder = new ViewHolder();
			viewHolder = new ViewHolder();
			viewHolder.linear = (LinearLayout) convertView.findViewById(R.id.linear);
			viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			viewHolder.numbers = (TextView) convertView.findViewById(R.id.numbers);
			viewHolder.text_wro_tr = (TextView) convertView.findViewById(R.id.text_wro_tr);
			viewHolder.text_wro_kr = (TextView) convertView.findViewById(R.id.text_wro_kr);
			viewHolder.text_wro_cr = (TextView) convertView.findViewById(R.id.text_wro_cr);
			viewHolder.text_wro_tr.setBackgroundColor(color_101);
			viewHolder.text_wro_kr.setBackgroundColor(color_102);
			viewHolder.text_wro_cr.setBackgroundColor(color_103);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Adv_project_infoBean.wrongs temp_data = list.get(position);
		int sum = temp_data.getWro_tr()+temp_data.getWro_kr()+temp_data.getWro_cr();
		int width1 = progress_width*temp_data.getWro_tr()/sum;
		int width2 = progress_width*temp_data.getWro_kr()/sum;
		int width3 = progress_width*temp_data.getWro_cr()/sum;
		LayoutParams params_progress1 = (LayoutParams)  viewHolder.text_wro_tr.getLayoutParams();
		LayoutParams params_progress2 = (LayoutParams)  viewHolder.text_wro_kr.getLayoutParams();
		LayoutParams params_progress3 = (LayoutParams)  viewHolder.text_wro_cr.getLayoutParams();
		params_progress1.width=width1;
		params_progress2.width=width2;
		params_progress3.width=width3;
		params_progress2.leftMargin=2;
		params_progress3.leftMargin=2;
		viewHolder.text_wro_tr.setLayoutParams(params_progress1);
		viewHolder.text_wro_kr.setLayoutParams(params_progress2);
		viewHolder.text_wro_cr.setLayoutParams(params_progress3);
		viewHolder.text_title.setText(temp_data.getTitle());
		viewHolder.numbers.setText(temp_data.getTotal()+"");
		viewHolder.linear.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RequestParams params = new RequestParams();
				params.add("ver",MyFlg.android_version);
				params.add("a",MyFlg.a);
				params.add("clientcode",MyFlg.getclientcode(context));
				params.add("exercises_type","ctgg_ap");
				params.add("exercises_option","["+temp_data.getId()+"]");
				params.add("apid",pid+"");
				String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getCreate_exercise_suit(),context);
				PostApi(context, params, apiUrl);
			}
		});
		return convertView;
	}
	private final class ViewHolder
	{
		LinearLayout linear;
		TextView text_title,numbers;
		TextView text_wro_tr,text_wro_kr,text_wro_cr; //当做比率设置
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
	 */
	private void PostApi(final Activity context,RequestParams params,String apiUrl){
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
					if(new JSONObject(result).getBoolean("status")==false){
						Toast.makeText(context, "创建失败", 0).show();
					}else{
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
						context.startActivityForResult(intent, 0);
						context.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
				Toast.makeText(context, "网络异常。", 0).show();
				error.printStackTrace();// 把错误信息打印出轨迹来  
			}
		});
	}
}
