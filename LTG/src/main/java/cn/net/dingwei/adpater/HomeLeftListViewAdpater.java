package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.net.dingwei.Bean.Get_baseinfoBean;
import cn.net.dingwei.Bean.UtilBean;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class HomeLeftListViewAdpater extends BaseAdapter{
	private Get_baseinfoBean.subjects[] subjects;
	private Context context;
	private viewHolder viewHolder;
	private String k;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1=0,Fontcolor_4=0,Bgcolor_2=0,Color_4=0;
	private String Subject_1_white,Subject_1;
	public HomeLeftListViewAdpater(Context context,Get_baseinfoBean.subjects[] subjects,String k) {
		// TODO Auto-generated constructor stub
		this.context = context;
		application = MyApplication.myApplication;
		this.subjects = subjects;
		this.k=k;
		viewHolder = new viewHolder();
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_4= sharedPreferences.getInt("fontcolor_4", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Subject_1_white=sharedPreferences.getString("Subject_1_white", "");
		Subject_1=sharedPreferences.getString("Subject_1", "");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return subjects.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return subjects[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Get_baseinfoBean.subjects subject = subjects[point];
		view = View.inflate(context, R.layout.item_home_leftmenu_listview, null);
		viewHolder.imageview   = (ImageView) view.findViewById(R.id.imageview);
		viewHolder.xian 	   = (TextView) view.findViewById(R.id.xian);
		viewHolder.xian.setBackgroundColor(Color_4);

		viewHolder.text 	   = (TextView) view.findViewById(R.id.text);
		viewHolder.text1 	   = (TextView) view.findViewById(R.id.text1);
		viewHolder.baifenhao1  = (TextView) view.findViewById(R.id.baifenhao1);
		viewHolder.itembg 	   = (LinearLayout) view.findViewById(R.id.itembg);
		viewHolder.home_leftmenu_jindu 	   = (RelativeLayout) view.findViewById(R.id.home_leftmenu_jindu);
		//
		viewHolder.text.setText(subject.getN());

		//设置颜色   当用户选择的是这一条
		if(k.equals(MyApplication.getuserInfoBean(context).getExam()) && MyApplication.getuserInfoBean(context).getSubject().equals(subject.getK())){
			//if(MyFlg.subjectsK.equals(subject.getK())){ //选择的某条
			viewHolder.text.setTextColor(Fontcolor_1);
			viewHolder.itembg.setBackgroundColor(Bgcolor_2);
			viewHolder.imageview.setImageBitmap(BitmapFactory.decodeFile(Subject_1_white));
			viewHolder.text1.setTextColor(Fontcolor_1);
			viewHolder.baifenhao1.setTextColor(Fontcolor_1);
			if (null==subject.getProgress()){
				viewHolder.text1.setVisibility(View.GONE);
				viewHolder.baifenhao1.setVisibility(View.GONE);
			}else if(null!=subject.getProgress()|| subject.getProgress().equals("null")){ //进度不为Null的时候
				viewHolder.text1.setText(subject.getProgress()+"");
				viewHolder.home_leftmenu_jindu.setBackgroundColor(Bgcolor_2);
				viewHolder.text1.setTextColor(Fontcolor_1);
				viewHolder.baifenhao1.setTextColor(Fontcolor_1);
			}
		}else{ //没选中的时候
			//viewHolder.text1.setTextColor(context.getResources().getColor(R.color.light_gray));
			//viewHolder.baifenhao1.setTextColor(context.getResources().getColor(R.color.light_gray));
			viewHolder.text1.setTextColor(Color.GRAY);
			viewHolder.baifenhao1.setTextColor(Color.GRAY);
			viewHolder.imageview.setImageBitmap(BitmapFactory.decodeFile(Subject_1));
			if (null==subject.getProgress()){
				viewHolder.text1.setVisibility(View.GONE);
				viewHolder.baifenhao1.setVisibility(View.GONE);
			}else if(null!=subject.getProgress()|| subject.getProgress().equals("null")){ //进度不为Null的时候
				viewHolder.text1.setText(subject.getProgress()+"");
				//viewHolder.text1.setTextColor(MyFlg.colorBean.getFontcolor_4());
				//viewHolder.baifenhao1.setTextColor(MyFlg.colorBean.getFontcolor_4());

			}
			viewHolder.text.setTextColor(Fontcolor_4);
		}
		return view;
	}

	class viewHolder{
		TextView text;
		RelativeLayout home_leftmenu_jindu;
		TextView text1;
		TextView baifenhao1;
		LinearLayout itembg;
		TextView xian;
		ImageView imageview;
	}
}
