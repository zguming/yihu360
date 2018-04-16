package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.net.dingwei.Bean.UtilBean;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Person_data_Adpater extends BaseAdapter{
	private String[] stArray;
	private Context context;
	private Viewholder viewholder;
	private int type; // 1选择城市
	private String [] status=null;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Color_3=0;
	public Person_data_Adpater(Context context,String[] stArray,int type) {
		// TODO Auto-generated constructor stub
		this.stArray =stArray;
		this.context = context;
		application = MyApplication.myApplication;
		viewholder = new Viewholder();
		this.type =type;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
	}
	public Person_data_Adpater(Context context,String[] stArray,String [] status,int type) {
		// TODO Auto-generated constructor stub
		this.stArray =stArray;
		this.context = context;
		application = MyApplication.myApplication;
		viewholder = new Viewholder();
		this.type =type;
		this.status = status;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stArray.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return stArray[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//设置数据
		if(stArray[0].equals("")){//间隔
			convertView =View.inflate(context, R.layout.item_list_null, null);
		}else{
			convertView =View.inflate(context, R.layout.item_person, null);
			viewholder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			viewholder.left_image = (ImageView) convertView.findViewById(R.id.left_image);
			viewholder.imageview.setVisibility(View.GONE);
			viewholder.left_image.setVisibility(View.GONE);
			viewholder.text=(TextView)convertView.findViewById(R.id.text);
			viewholder.text_gou=(TextView)convertView.findViewById(R.id.text_gou);
			viewholder.text2=(TextView)convertView.findViewById(R.id.text2);
			viewholder.text2.setVisibility(View.VISIBLE);
			viewholder.text2.setTextColor(Fontcolor_3);
			//设置数据
			if(null != status && status[position].equals("0")){
				viewholder.text.setText(stArray[position]+" (即将推出)");
				viewholder.text.setTextColor(Fontcolor_7);
			}else{
				viewholder.text.setText(stArray[position]);
				viewholder.text.setTextColor(Fontcolor_3);
			}

			if(type==1){
				if(null !=MyFlg.baidu_city){
					if(MyFlg.baidu_city.contains(stArray[position])){
						viewholder.text2.setText("当前位置");
						viewholder.text2.setVisibility(View.VISIBLE);
					}
				}
				if(stArray[position].contains(MyApplication.getuserInfoBean(context).getCity_name())){
					viewholder.text_gou.setVisibility(View.VISIBLE);
				}
			}else if(type==2 && stArray[position].equals(MyApplication.getuserInfoBean(context).getExam_name())){
				viewholder.text_gou.setVisibility(View.VISIBLE);
			}else if(type==3 && stArray[position].equals(APPUtil.setTime(MyApplication.getuserInfoBean(context).getExam_schedule()))){
				viewholder.text_gou.setVisibility(View.VISIBLE);
			}else if(type==4 && stArray[position].equals(MyApplication.getuserInfoBean(context).getSubject_name())){
				viewholder.text_gou.setVisibility(View.VISIBLE);
			}

		}

		viewholder.buttom_xian=(TextView)convertView.findViewById(R.id.buttom_xian);
		viewholder.buttom_xian.setBackgroundColor(Color_3);
		viewholder.buttom_xian.setVisibility(View.VISIBLE);
		return convertView;
	}

	public void setArr_status(String[] stArray, String [] status,int type) {
		this.stArray = stArray;
		this.type =type;
		this.status = status;
	}

	class Viewholder{
		TextView text;
		TextView text2;
		TextView textview;
		TextView buttom_xian;
		TextView text_gou;
		LinearLayout linear_bg;
		ImageView imageview,left_image;
	}
}
