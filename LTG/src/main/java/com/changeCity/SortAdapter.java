package com.changeCity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import cn.net.tmjy.mtiku.futures.R;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;
	private Activity mContext;
	private List<SortModel> list_hot = null;
	private SharedPreferences sharedPreferences;
	private int Bgcolor_1=0,Bgcolor_2=0,Fontcolor_7,Color_4=0,Fontcolor_3=0;
	public SortAdapter(Activity mContext, List<SortModel> list, List<SortModel> list_hot) {
		this.mContext = mContext;
		this.list = list;
		this.list_hot=list_hot;
		sharedPreferences =mContext.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_change_city, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.gridView = (GridView) view.findViewById(R.id.gridview);
			viewHolder.xian = (View) view.findViewById(R.id.xian);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvLetter.setTextColor(Color.BLACK);
		viewHolder.xian.setBackgroundColor(Color_4);

		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			String st =  mContent.getSortLetters().toUpperCase();
			if(st.equals("#")){
				viewHolder.tvLetter.setText("热门地区");
			}else if(st.equals("!")){
				//viewHolder.tvLetter.setText("");
				viewHolder.tvLetter.setVisibility(View.GONE);
			}else{
				viewHolder.tvLetter.setText(mContent.getSortLetters().substring(0, 1).toUpperCase());
			}
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		if(getSection(position).equals("#")){
			viewHolder.gridView.setVisibility(View.VISIBLE);
			viewHolder.tvTitle.setVisibility(View.GONE);
			SortGridviewAdapter adapter = new SortGridviewAdapter(mContext, list.get(position).getHot_city(),Fontcolor_3);
			viewHolder.gridView.setAdapter(adapter);
			//viewHolder.gridView.setOnItemClickListener(new GridViewClick(position));
		}else{
			viewHolder.gridView.setVisibility(View.GONE);
			viewHolder.tvTitle.setVisibility(View.VISIBLE);
			String st =  mContent.getSortLetters().toUpperCase();
			if(st.equals("!")){
				String GPS=list.get(position).getName()+"   GPS定位";
				SpannableString sp = new SpannableString(GPS);
				sp.setSpan(new TextAppearanceSpan(mContext, R.style.style_font),GPS.indexOf("GP")-2, GPS.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				viewHolder.tvTitle.setText(sp);
				viewHolder.tvTitle.setTextColor(Bgcolor_2);
			}else{
				viewHolder.tvTitle.setText(this.list.get(position).getName());
				viewHolder.tvTitle.setTextColor(Fontcolor_3);
			}

		}


		return view;

	}
	class GridViewClick implements OnItemClickListener{
	private int position;

		public GridViewClick(int position) {
			this.position = position;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int gridview_point, long id) {
			Intent intent = new Intent();
			intent.putExtra("select_city", list.get(position).getHot_city()[gridview_point].getName());
			intent.putExtra("key", list.get(position).getHot_city()[gridview_point].getKey());
			mContext.setResult(mContext.RESULT_OK, intent);
			mContext.finish();
			mContext.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//左进右出
		}
	}


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		GridView gridView;
		View xian;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().toUpperCase().charAt(0);
	}
	/**
	 * 根据ListView的当前位置获取分类的首字母
	 */
	public String getSection(int position) {
		return list.get(position).getSortLetters().toUpperCase();
	}
	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 *
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}