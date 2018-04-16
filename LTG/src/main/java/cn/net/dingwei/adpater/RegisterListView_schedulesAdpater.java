package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.net.dingwei.Bean.ExamBean;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class RegisterListView_schedulesAdpater extends BaseAdapter{
	private ExamBean  examBean;
	private Context context;
	private ViewHolder viewHolder;
	private String [] date;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0;
	public RegisterListView_schedulesAdpater(Context context,ExamBean examBean) {
		// TODO Auto-generated constructor stub
		this.examBean = examBean;
		this.context = context;
		application = MyApplication.myApplication;
		viewHolder = new ViewHolder();
		this.date = examBean.getSchedules();
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return date.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return date[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view = View.inflate(context, R.layout.item_listview, null);
		viewHolder.city = (TextView) view.findViewById(R.id.city);
		
		viewHolder.location = (TextView) view.findViewById(R.id.location);
		String cityName =date[arg0];
		cityName = APPUtil.setTime(cityName);
		viewHolder.city.setText(cityName);
		viewHolder.city.setTextColor(Fontcolor_3);
		return view;
	}
	class ViewHolder {
		TextView city;
		TextView location;
	}
}
