package cn.net.dingwei.adpater;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.List;

import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.ui.PayVIPActivity;
import cn.net.dingwei.ui.Reading_QuestionsActivity;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.treeListView.Node;
import cn.net.treeListView.TreeListViewAdapter;
import cn.net.tmjy.mtiku.futures.R;

public class MyCollectOrWrongsAdapter<T> extends TreeListViewAdapter<T>{
	private LayoutParams params1;
	private LayoutParams params2;
	private LayoutParams params3;
	private LayoutParams params4;
	private MyApplication application;
	private FYuanTikuDialog dialog;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,color_101=0,color_102=0,color_103=0,Color_4=0;
	private int Screen_width=0;
	private int progress_width=0;
	private int dip_5;
	private int tag=0;

	List<T> datas ;
	public MyCollectOrWrongsAdapter(ListView mTree, Activity context, List<T> datas,int defaultExpandLevel)
			throws IllegalArgumentException,IllegalAccessException{
		super(mTree, context, datas, defaultExpandLevel);
		dip_5 = DensityUtil.DipToPixels(context, 5);
		params1 =  new LayoutParams(dip_5*5, dip_5*5);
		params2 =  new LayoutParams(dip_5*4, dip_5*4);
		params3 =  new LayoutParams(dip_5*3, dip_5*3);
		params4 =  new LayoutParams(dip_5*2, dip_5*2);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(context,R.style.DialogStyle,"正在加载...");

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
		progress_width = Screen_width - DensityUtil.DipToPixels(context, 165);//减去其他控件的宽度 剩下进度条的宽度
		this.datas = datas;
	}

	@Override
	public View getConvertView(final Node node , int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.item_duoji_listview, parent, false);
			viewHolder = new ViewHolder();
			viewHolder. image_left = (ImageView) convertView.findViewById(R.id.image_left);
			viewHolder.image_lianxi = (ImageView) convertView.findViewById(R.id.image_lianxi);
			viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			viewHolder.numbers = (TextView) convertView.findViewById(R.id.numbers);
			viewHolder.text_wro_tr = (TextView) convertView.findViewById(R.id.text_wro_tr);
			viewHolder.text_wro_kr = (TextView) convertView.findViewById(R.id.text_wro_kr);
			viewHolder.text_wro_cr = (TextView) convertView.findViewById(R.id.text_wro_cr);
			viewHolder.view_item_jianju = convertView.findViewById(R.id.view_item_jianju);
			viewHolder.xian1 = convertView.findViewById(R.id.xian1);
			viewHolder.xian2 = convertView.findViewById(R.id.xian2);
			viewHolder.text_wro_tr.setBackgroundColor(color_101);
			viewHolder.text_wro_kr.setBackgroundColor(color_102);
			viewHolder.text_wro_cr.setBackgroundColor(color_103);
			View view_xian = convertView.findViewById(R.id.view_xian);
			view_xian.setBackgroundColor(Color_4);
			convertView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		int sum = node.getProgress()+node.getProgress2()+node.getProgress3();
		int width1 = progress_width*node.getProgress()/sum;
		int width2 = progress_width*node.getProgress2()/sum;
		int width3 = progress_width*node.getProgress3()/sum;

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

		viewHolder.image_left.setVisibility(View.VISIBLE);
		viewHolder.image_left.setImageResource(node.getIcon());

		//判断 是否需要显示线
		if(node.getIsLastForPid()==1 &&node.isExpand()==false){ //是最后一个 并且没展开
			viewHolder.xian2.setVisibility(View.INVISIBLE);
		}else if(node.isExpand()==false&&node.getpId()==0){ //是根节点 并且没展开
			viewHolder.xian2.setVisibility(View.INVISIBLE);
		}else {
			viewHolder.xian2.setVisibility(View.VISIBLE);
		}

		if(node.getpId()!=0){ //父类不为0  不是第一级节点
			viewHolder.xian1.setVisibility(View.VISIBLE);
		}else{
			viewHolder.xian1.setVisibility(View.INVISIBLE);
		}


		if(node.getLevel()==0){
			viewHolder.image_left.setLayoutParams(params1);
			viewHolder.view_item_jianju.setVisibility(View.VISIBLE);
		}else if(node.getLevel()==1){
			viewHolder.image_left.setLayoutParams(params2);
			viewHolder.view_item_jianju.setVisibility(View.GONE);
		}else if(node.getLevel()==2){
			viewHolder.image_left.setLayoutParams(params3);
			viewHolder.view_item_jianju.setVisibility(View.GONE);
		}else if(node.getLevel()==3){
			viewHolder.image_left.setLayoutParams(params4);
			viewHolder.view_item_jianju.setVisibility(View.GONE);
		}
		viewHolder.text_title.setText(node.getName());
		viewHolder.numbers.setText(node.getNumber()+"");
		viewHolder.image_lianxi.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(MyApplication.Getget_user_baseinfo(mContext).getCtgg_need_vip()==1&&(MyApplication.getuserInfoBean(mContext).getMember_status()!=1 && MyApplication.getuserInfoBean(mContext).getMember_status()!=2)){
					//不是会员
					MyFlg.showAlertDialogChoose("提示", MyApplication.Getget_user_baseinfo(mContext).getCtgg_paymsg(), MyApplication.Getget_user_baseinfo(mContext).getCtgg_paybtn_yes(),MyApplication.Getget_user_baseinfo(mContext).getCtgg_paybtn_no(),mContext);

				}else{	//是会员 或者不需要判断
					RequestParams params = new RequestParams();
					params.add("ver",MyFlg.android_version);
					params.add("a",MyFlg.a);
					params.add("clientcode",MyFlg.getclientcode(mContext));
					params.add("exercises_type","ctgg");
					params.add("exercises_option","["+node.getOption_id()+"]");
					String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(mContext).getCreate_exercise_suit(),mContext);
					PostApi(mContext, params, apiUrl);
				}
			}
		});
		/* if(position==datas.size()-1){
				tag=1;
			}
		}*/
		return convertView;
	}

	private final class ViewHolder
	{
		View view_item_jianju,xian1,xian2;
		ImageView image_left,image_lianxi;
		TextView text_title,numbers;
		//ProgressBar progressBar;
		TextView text_wro_tr,text_wro_kr,text_wro_cr; //当做比率设置
	}
	/**
	 * 仿IOS对话框
	 * @param title
	 * @param content
	 * @param leftBtnText
	 * @param rightBtnText
	 */
	public static void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText,final Context context) {
		F_IOS_Dialog.showAlertDialogChoose(context, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								Intent intent = new Intent(context, PayVIPActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								context.startActivity(intent);
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								break;
							default:
								break;
						}

					}
				});
	}
	/**
	 * POST 网络请求    
	 * @param params  参数 
	 * @param apiUrl  API地址
	 */
	private void PostApi(final Context context,RequestParams params,String apiUrl){
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
}
