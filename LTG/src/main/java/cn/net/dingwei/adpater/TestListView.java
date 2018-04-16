package cn.net.dingwei.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestListView extends BaseAdapter{
	String [] st = new String[]{"1","2","3"};
	Context context;
	public TestListView(Context context) {
		// TODO Auto-generated constructor stub
		this.context= context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return st.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return st[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView textView = new TextView(context);
		textView.setText(st[arg0]);
		return textView;
	}

}
