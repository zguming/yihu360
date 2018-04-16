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
import cn.net.dingwei.Bean.Adv_project_infoBean;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Point_jinduBean;
import cn.net.dingwei.Bean.PointsBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.ui.Reading_QuestionsActivity;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Jinjie_left_listviewAdapter extends BaseAdapter{
	private Activity context;
	private List<Adv_project_infoBean.suit_list> list;
	private MyApplication application;
	private LayoutParams params;//进度条宽度
	private FYuanTikuDialog dialog;
	private myHandler handler ;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_4=0,Fontcolor_7=0,Fontcolor_8=0,Fontcolor_9=0,Fontcolor_13=0,Fontcolor_5=0,Bgcolor_3=0;
	private int Screen_width=0;
	private String Lianxi;
	public Jinjie_left_listviewAdapter(Activity context,List<Adv_project_infoBean.suit_list> list) {
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
		viewHolder viewHolder = null;
		if(null==view){
			viewHolder= new viewHolder();
			view = View.inflate(context, R.layout.item_jinjie_listleft, null);
			viewHolder.right_image = (ImageView) view.findViewById(R.id.right_image);
			viewHolder.text2 	   = (TextView) view.findViewById(R.id.text2);
			viewHolder.text4 	   = (TextView) view.findViewById(R.id.text4);
			viewHolder.title1 	   = (TextView) view.findViewById(R.id.title1);
			viewHolder.title3 	   = (TextView) view.findViewById(R.id.title3);
			viewHolder.title4 	   = (TextView) view.findViewById(R.id.title4);
			viewHolder.linear_bg   = (LinearLayout) view.findViewById(R.id.linear_bg);
			view.setTag(viewHolder);
		}else{
			viewHolder= (cn.net.dingwei.adpater.Jinjie_left_listviewAdapter.viewHolder) view.getTag();
		}
		viewHolder.text2.setTextColor(Fontcolor_5);
		viewHolder.title1.setTextColor(Fontcolor_4);
		viewHolder.title3.setTextColor(Fontcolor_13);
		viewHolder.title4.setTextColor(Fontcolor_13);
		viewHolder.linear_bg.setBackgroundDrawable(setTouch_Click());

		viewHolder.linear_bg.setOnClickListener(new right_imageCilck(point));
		//设置数据
		Adv_project_infoBean.suit_list suitBean = list.get(point);
		viewHolder.title1.setText(suitBean.getTitle());

		if(null==suitBean.getExe_r()||suitBean.getExe_r().equals("")||suitBean.getExe_r().equals("null")){
			viewHolder.text2.setTextColor(Fontcolor_7);
		}else {
			int exe_r=Integer.valueOf(suitBean.getExe_r());
			viewHolder.text2.setText(exe_r+"%");
			//设置颜色 
			if(exe_r==0){
				viewHolder.text2.setTextColor(Fontcolor_7);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_7);
			}else if(exe_r>0 && exe_r<70){
				viewHolder.text2.setTextColor(Fontcolor_3);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_3);
			}else if(exe_r>=70){
				viewHolder.text2.setTextColor(Fontcolor_9);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_9);
			}else{
				viewHolder.text2.setTextColor(Fontcolor_7);
				//viewHolder.baifenhao1.setTextColor(Fontcolor_7);
			}
		}
		if(null==suitBean.getWro_r()||suitBean.getWro_r().equals("")||suitBean.getWro_r().equals("null")){
			viewHolder.text4.setTextColor(Fontcolor_7);
		}else{
			int wro_r = Integer.valueOf(suitBean.getWro_r());
			viewHolder.text4.setText(suitBean.getWro_r()+"%");
			if(wro_r>=0 &&wro_r<10){
				viewHolder.text4.setTextColor(Fontcolor_8);
			}else if(wro_r>=10 &&wro_r<80){
				viewHolder.text4.setTextColor(Fontcolor_3);
			}else if(wro_r>=80){
				viewHolder.text4.setTextColor(Fontcolor_9);
			}else{
				viewHolder.text4.setTextColor(Fontcolor_7);
			}
		}


		return view;
	}
	class viewHolder{
		TextView text2;
		TextView text4;
		TextView title1;
		TextView title3;
		TextView title4;
		LinearLayout linear_bg;
		ImageView right_image;
		//TextView baifenhao1;
		//TextView baifenhao2;
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
			params.add(new BasicNameValuePair("a", MyFlg.a));
			params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(context)));
			params.add(new BasicNameValuePair("exercises_type","qtlx"));
			params.add(new BasicNameValuePair("exercises_option","["+list.get(point).getId()+"]"));
			AsyncLoadApi asyncLoadApi = new AsyncLoadApi(context, handler, params, "create_exercise_suit",0,1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getCreate_exercise_suit(),context));
			asyncLoadApi.execute();
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
					intent.putExtra("flg", 1);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivityForResult(intent, 0);
					((Activity) context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
					break;
				case 1:
					dialog.dismiss();
					break;
				default:
					break;
			}
		}
	}
}
