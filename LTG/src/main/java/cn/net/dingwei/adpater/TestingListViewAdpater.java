package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class TestingListViewAdpater extends BaseAdapter{
	private Context context;
	private String [][] stArr;
	private viewholder viewholder;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0;
	public TestingListViewAdpater(Context context,String [][] stArr) {
		// TODO Auto-generated constructor stub
		this.context = context;
		application = MyApplication.myApplication;
		this.stArr = stArr;
		viewholder = new viewholder();
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stArr.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return stArr[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view = View.inflate(context, R.layout.item_testing_listview, null);
		viewholder.text1 = (TextView) view.findViewById(R.id.text1);
		viewholder.text2 = (TextView) view.findViewById(R.id.text2);
		viewholder.text1.setText(stArr[point][0]);
		if(stArr[point][1].equals("")){
			viewholder.text2.setVisibility(View.GONE);
		}else{
			viewholder.text2.setText(stArr[point][1]);
			viewholder.text2.setTextColor(Fontcolor_3);
		}
		return view;
	}
	class viewholder{
		TextView text1;
		TextView text2;
		
	}
}
