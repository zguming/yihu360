package cn.net.dingwei.adpater;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Point_jinduBean;
import cn.net.dingwei.Bean.PointsBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.ui.Activity_homeChildView;
import cn.net.dingwei.ui.Reading_QuestionsActivity;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Home_childView_listview extends BaseAdapter{
	private Context context;
	private viewHolder viewHolder;
	private List<PointsBean> list;
	private MyApplication application;
	private LayoutParams params;//进度条宽度
	private FYuanTikuDialog dialog;
	private myHandler handler ;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_4=0,Fontcolor_7=0,Fontcolor_8=0,Fontcolor_9=0,Fontcolor_13=0,Fontcolor_5=0,Bgcolor_3=0;
	private int Screen_width=0;
	private String Lianxi;
	public Home_childView_listview(Context context,List<PointsBean> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_4= sharedPreferences.getInt("fontcolor_4", 0);
		Fontcolor_5= sharedPreferences.getInt("fontcolor_5", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Fontcolor_8= sharedPreferences.getInt("fontcolor_8", 0);
		Fontcolor_9= sharedPreferences.getInt("fontcolor_9", 0);
		Fontcolor_13= sharedPreferences.getInt("fontcolor_13", 0);
		Bgcolor_3 = sharedPreferences.getInt("bgcolor_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		Lianxi=sharedPreferences.getString("Lianxi", "");
		application = MyApplication.myApplication;
		viewHolder = new viewHolder();
		params = new LayoutParams(Screen_width*6/10, DensityUtil.DipToPixels(context, 5));
		params.setMargins(0, DensityUtil.DipToPixels(context, 10), 0, 0);
		dialog = new FYuanTikuDialog(context,R.style.DialogStyle,"正在加载");
		handler =new myHandler();
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
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view = View.inflate(context, R.layout.item_home_list, null);
		//viewHolder.baifenhao1  = (TextView) view.findViewById(R.id.baifenhao1);
		//viewHolder.baifenhao2  = (TextView) view.findViewById(R.id.baifenhao2);
		viewHolder.right_image = (ImageView) view.findViewById(R.id.right_image);
		viewHolder.text1 	   = (TextView) view.findViewById(R.id.text1);
		viewHolder.text2 	   = (TextView) view.findViewById(R.id.text2);
		viewHolder.title1 	   = (TextView) view.findViewById(R.id.title1);
		viewHolder.title2 	   = (TextView) view.findViewById(R.id.title2);
		viewHolder.title3 	   = (TextView) view.findViewById(R.id.title3);
		viewHolder.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		viewHolder.linear_bg   = (LinearLayout) view.findViewById(R.id.linear_bg);
		viewHolder.text1.setTextColor(Fontcolor_5);
		viewHolder.text2.setTextColor(Fontcolor_5);
		viewHolder.title1.setTextColor(Fontcolor_4);
		viewHolder.title2.setTextColor(Fontcolor_13);
		viewHolder.title3.setTextColor(Fontcolor_13);
		viewHolder.linear_bg.setBackgroundDrawable(setTouch_Click());

		viewHolder.progressBar.setLayoutParams(params);
		viewHolder.right_image.setImageBitmap(BitmapFactory.decodeFile(Lianxi));
		viewHolder.right_image.setOnClickListener(new right_imageCilck(point));
		//设置数据
		PointsBean pointsBean = list.get(point);
		viewHolder.title1.setText(pointsBean.getN());
		Point_jinduBean jinduBean=pointsBean.getPoint_jinduBean();
		if(null == jinduBean){
			viewHolder.text1.setTextColor(Fontcolor_7);
			viewHolder.text2.setTextColor(Fontcolor_7);
			//viewHolder.baifenhao1.setTextColor(Fontcolor_7);
			//viewHolder.baifenhao2.setTextColor(Fontcolor_7);
		}else{
			String exe_c_st = jinduBean.getExe_c(); //练题百分比
			String wro_r_st = jinduBean.getWro_r(); //错题百分比
			String tot_r_st = jinduBean.getTot_r(); //错题百分比
			String exe_r_st = jinduBean.getExe_r(); //错题百分比
			int exe_c = -1;
			int wro_r = -1;
			int exe_r = -1;
			if(MyFlg.isInt(tot_r_st)){
				viewHolder.progressBar.setProgress(Integer.parseInt(tot_r_st));
			}
			if(MyFlg.isInt(exe_c_st)){
				exe_c=Integer.parseInt(exe_c_st);
				viewHolder.text1.setText(exe_c+"");
			}
			if(MyFlg.isInt(wro_r_st)){
				wro_r=Integer.parseInt(wro_r_st);
				viewHolder.text2.setText(wro_r+"%");
			}

			if(MyFlg.isInt(exe_r_st)){
				exe_r =Integer.parseInt(exe_r_st);
			}
			//设置颜色 
			if(exe_r==0){
				viewHolder.text1.setTextColor(Fontcolor_7);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_7);
			}else if(exe_r>0 && exe_r<70){
				viewHolder.text1.setTextColor(Fontcolor_3);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_3);
			}else if(exe_r>=70){
				viewHolder.text1.setTextColor(Fontcolor_9);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_9);
			}else{
				viewHolder.text1.setTextColor(Fontcolor_7);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_7);
			}
			if(wro_r>=0 &&wro_r<10){
				viewHolder.text2.setTextColor(Fontcolor_8);
				//viewHolder.baifenhao2.setTextColor(Fontcolor_8);
			}else if(wro_r>=10 &&wro_r<80){
				viewHolder.text2.setTextColor(Fontcolor_3);
				//viewHolder.baifenhao2.setTextColor(Fontcolor_3);
			}else if(wro_r>=80){
				viewHolder.text2.setTextColor(Fontcolor_9);
				//viewHolder.baifenhao2.setTextColor(Fontcolor_9);
			}else{
				viewHolder.text2.setTextColor(Fontcolor_7);
				//viewHolder.baifenhao2.setTextColor(Fontcolor_7);
			}
		}
		return view;
	}
	class viewHolder{
		TextView text1;
		TextView text2;
		TextView title1;
		TextView title2;
		TextView title3;
		LinearLayout linear_bg;
		ImageView right_image;
		//TextView baifenhao1;
		//TextView baifenhao2;
		ProgressBar progressBar;
	}
	private Drawable setTouch_Click(){
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(context.getResources().getColor(R.color.light_gray)));
		drawable.addState(new int[]{}, new ColorDrawable(Bgcolor_3));
		return drawable;
	}
	class right_imageCilck implements OnClickListener{
		private int point;
		public right_imageCilck(int point) {
			// TODO Auto-generated constructor stub
			this.point = point;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog.show();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ver",MyFlg.android_version));
			params.add(new BasicNameValuePair("a",MyFlg.a));
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(context)));
			params.add(new BasicNameValuePair("exercises_type","zxlx"));
			params.add(new BasicNameValuePair("exercises_option","["+list.get(point).getId()+"]"));
			 Log.i("mylog", "exercises_option="+list.get(point).getId());
//			  Log.i("mylog", "url="+MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getCreate_exercise_suit(),context));

			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(context, handler, params, "create_exercise_suit",0,1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getCreate_exercise_suit(),context));
			asyncLoadApi.execute();
			Log.i("mylog", "params="+params.toString());
			Log.i("mylog", "asyncLoadApi="+MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getCreate_exercise_suit(),context));
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Create_Exercise_suit_2Bean create_Exercise_suit_2Bean;
			switch (msg.what) {
				case 0:
					dialog.dismiss();

					Log.i("mylog", "请求结果：：：="+APPUtil.create_exercise_suit_2(context));

					create_Exercise_suit_2Bean = APPUtil.create_exercise_suit_2(context);
					if(null==create_Exercise_suit_2Bean){
						Toast.makeText(context, "创建失败", 0).show();
						return;
					}
					Intent intent = new Intent(context, Reading_QuestionsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bean", create_Exercise_suit_2Bean);
					intent.putExtras(bundle);
					intent.putExtra("WHICH_GROUP", 0);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					break;
				case 1:
					Log.i("mylog", "11111"+APPUtil.create_exercise_suit_2(context));
					dialog.dismiss();
					break;
				default:
					break;
			}
		}
	}
}
