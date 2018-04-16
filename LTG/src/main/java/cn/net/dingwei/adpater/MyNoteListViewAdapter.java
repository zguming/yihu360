package cn.net.dingwei.adpater;

import java.util.List;

import cn.net.dingwei.Bean.MyNotesBean;
import cn.net.dingwei.util.MyApplication;
import cn.net.tmjy.mtiku.futures.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyNoteListViewAdapter extends BaseAdapter{
	private Context context;
	private List<MyNotesBean.notes> list_datas;
	private SharedPreferences sharedPreferences;
	private int  Fontcolor_3 = 0,Fontcolor_7=0;
	public String user_note;
	public MyNoteListViewAdapter(Context context,List<MyNotesBean.notes> list_datas,String user_note) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list_datas = list_datas;
		sharedPreferences = context.getSharedPreferences("commoninfo",Context.MODE_PRIVATE);
		Fontcolor_3 = sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7 = sharedPreferences.getInt("fontcolor_7", 0);
		//笔记数量
		this.user_note = user_note;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list_datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int point, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(null==view){
			view = View.inflate(context, R.layout.item_mynote_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.note_nubers = (TextView) view.findViewById(R.id.note_nubers);
			viewHolder.text_point = (TextView) view.findViewById(R.id.text_point);
			viewHolder.title = (TextView) view.findViewById(R.id.title);
			viewHolder.content = (TextView) view.findViewById(R.id.content);
			viewHolder.time = (TextView) view.findViewById(R.id.time);

			viewHolder.note_nubers.setTextColor(Fontcolor_3);
			viewHolder.text_point.setTextColor(Fontcolor_3);
			viewHolder.title.setTextColor(Fontcolor_3);
			viewHolder.content.setTextColor(Color.BLACK);
			viewHolder.time.setTextColor(Fontcolor_7);
			view.setTag(viewHolder);
		}else{
			viewHolder= (ViewHolder) view.getTag();
		}

		if(point==0){
			viewHolder.note_nubers.setVisibility(View.VISIBLE);
			viewHolder.note_nubers.setText("共"+user_note+"条笔记");
		}else{
			viewHolder.note_nubers.setVisibility(View.GONE);
		}
		MyNotesBean.notes noteBean = list_datas.get(point);
		viewHolder.text_point.setText((point+1)+"");
		viewHolder.title.setText(noteBean.getPoints());
		viewHolder.time.setText(noteBean.getTime_text());
		viewHolder.content.setText(noteBean.getContent());
		return view;
	}
	class ViewHolder{
		TextView note_nubers,text_point,title,content,time;

	}
}
