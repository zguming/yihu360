package com.changeCity;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.net.dingwei.util.DensityUtil;
import cn.net.tmjy.mtiku.futures.R;

public class SortGridviewAdapter extends BaseAdapter{
	private Activity context;
	private SortModel.hot_city[] hot_city;
	private int dip_10;
	private int textColor;
	public SortGridviewAdapter(Activity context, SortModel.hot_city[] hot_city, int TextColor) {
		// TODO Auto-generated constructor stub
		this.context =context;
		this.hot_city =hot_city;
		dip_10= DensityUtil.DipToPixels(context, 10);
		this.textColor = TextColor;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hot_city.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return hot_city[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView textView = new TextView(context);
		textView.setPadding(dip_10, dip_10/2, dip_10, dip_10/2);
		textView.setText(hot_city[point].getName());
		textView.setTextColor(textColor);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.touch_click_wite));
		textView.setOnClickListener(new City_TextClick(point));
		return textView;
	}

	class City_TextClick implements View.OnClickListener{
			int position;

		public City_TextClick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("select_city",hot_city[position].getName());
			intent.putExtra("key",hot_city[position].getKey());
			context.setResult(context.RESULT_OK, intent);
			context.finish();
			context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//左进右出
		}
	}

}
