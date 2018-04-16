package cn.net.dingwei.adpater;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.net.dingwei.Bean.ExamBean;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class RegisterListView_ExamAdpater extends BaseAdapter{
	private List<ExamBean> list;
	private Context context;
	private ViewHolder viewHolder;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0;
	public RegisterListView_ExamAdpater(Context context,List<ExamBean> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
		application = MyApplication.myApplication;
		viewHolder = new ViewHolder();
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
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
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view = View.inflate(context, R.layout.item_listview, null);
		viewHolder.exam = (TextView) view.findViewById(R.id.city);
		viewHolder.exam.setTextColor(Fontcolor_3);
		viewHolder.location = (TextView) view.findViewById(R.id.location);
		String cityName =list.get(arg0).getN();

		viewHolder.location.setVisibility(View.GONE);

		if(list.get(arg0).getStatus().equals("0")){
			viewHolder.exam.setTextColor(Fontcolor_7);
			viewHolder.exam.setText(cityName+" (即将推出)");
		}else{
			viewHolder.exam.setText(cityName);
		}
		return view;
	}
	class ViewHolder {
		TextView exam;
		TextView location;
	}
}
