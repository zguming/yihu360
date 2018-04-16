package cn.net.dingwei.adpater;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.net.dingwei.Bean.CommonCityListBean;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class RegisterListView_cityAdpater extends BaseAdapter{
	private List<CommonCityListBean> list;
	private Context context;
	private ViewHolder viewHolder;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_5=0;
	public RegisterListView_cityAdpater(Context context,List<CommonCityListBean> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
		application = MyApplication.myApplication;
		viewHolder = new ViewHolder();
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_5= sharedPreferences.getInt("fontcolor_5", 0);
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
		viewHolder.city = (TextView) view.findViewById(R.id.city);
		viewHolder.location = (TextView) view.findViewById(R.id.location);
		viewHolder.location.setTextColor(Fontcolor_3);
		String cityName =list.get(arg0).getAndkey();
		viewHolder.city.setTextColor(Fontcolor_3);
		viewHolder.city.setText(cityName);
		if(null !=MyFlg.baidu_city){
			if(MyFlg.baidu_city.contains(cityName)){
				viewHolder.location.setVisibility(View.VISIBLE);
				viewHolder.city.setTextColor(Fontcolor_5);
			}
		}else{
			viewHolder.location.setVisibility(View.GONE);
		}
		return view;
	}
	class ViewHolder {
		TextView city;
		TextView location;
	}
}
