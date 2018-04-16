package cn.net.dingwei.adpater;

import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ShareGridViewAdapter extends BaseAdapter{
	private int[] share_Platform_icons;
	private String[] share_Platform_name;
	private Context context;
	private ViewHolder viewHolder;
	public ShareGridViewAdapter(Context context,int[] share_Platform_icons,String[] share_Platform_name) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.share_Platform_icons = share_Platform_icons;
		this.share_Platform_name = share_Platform_name;
		viewHolder = new ViewHolder();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return share_Platform_icons.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return share_Platform_icons[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(null==view){
			view = View.inflate(context, R.layout.item_share_gridview, null);
			viewHolder.Platform_icon = (ImageView) view.findViewById(R.id.Platform_icon);
			viewHolder.Platform_name = (TextView) view.findViewById(R.id.Platform_name);
			viewHolder.Platform_icon.setImageResource(share_Platform_icons[point]);
			viewHolder.Platform_name.setText(share_Platform_name[point]);
		}
		return view;
	}
	class ViewHolder{
		ImageView Platform_icon;
		TextView Platform_name;
	}
}
