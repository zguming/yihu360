package cn.net.dingwei.adpater;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.net.dingwei.Bean.Get_question_noteBean;
import cn.net.dingwei.myView.RoundImageView;
import cn.net.dingwei.util.MyApplication;
import cn.net.tmjy.mtiku.futures.R;

public class Note_Listview_adapter extends BaseAdapter {
	private Context context;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_1 = 0, Fontcolor_3 = 0, Fontcolor_7 = 0,Bgcolor_1 = 0, Bgcolor_2 = 0, Bgcolor_5 = 0, Bgcolor_6 = 0;
	private LayoutParams params;
	private List<Get_question_noteBean.notes> listData;
	private MyApplication application;
	public Note_Listview_adapter(Context context,List<Get_question_noteBean.notes> listData) {
		// TODO Auto-generated constructor stub
		this.context = context;
		params = new LayoutParams(80, 80);
		sharedPreferences = context.getSharedPreferences("commoninfo",Context.MODE_PRIVATE);
		Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
		Fontcolor_3 = sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7 = sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Bgcolor_5 = sharedPreferences.getInt("bgcolor_5", 0);
		Bgcolor_6 = sharedPreferences.getInt("bgcolor_6", 0);
		this.listData = listData;
		application = MyApplication.myApplication;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(null==view){
			viewHolder = new ViewHolder();
			view = View.inflate(context, R.layout.item_question_jianda_left_head, null);
			viewHolder.list_category_title = (TextView) view.findViewById(R.id.list_category_title);
			viewHolder.text_nick_name = (TextView) view.findViewById(R.id.text_nick_name);
			viewHolder.text_time = (TextView) view.findViewById(R.id.text_time);
			viewHolder.text_content = (TextView) view.findViewById(R.id.text_content);
			viewHolder.head_icon = (RoundImageView) view.findViewById(R.id.head_icon);

			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		//设置数据
		Get_question_noteBean.notes note_bean = listData.get(point);
		viewHolder.head_icon.setLayoutParams(params);
		viewHolder.text_nick_name.setText(note_bean.getAuthor_name());
		viewHolder.text_time.setText(note_bean.getTime_text());
		viewHolder.text_content.setText(note_bean.getContent());

		if(point==0 || point==1){
			viewHolder.list_category_title.setVisibility(View.VISIBLE);
			if(point==0){
				viewHolder.list_category_title.setText("我的笔记");
			}else{
				viewHolder.list_category_title.setText("其他笔记（"+(listData.size()-1)+"）");
			}
		}else{
			viewHolder.list_category_title.setVisibility(View.GONE);
		}
		if(null!=note_bean.getIcon()&&!"null".equals(note_bean.getIcon())){
			ImageLoader.getInstance().displayImage(note_bean.getIcon(), viewHolder.head_icon, application.getOptions());
		}else{
			viewHolder.head_icon.setImageResource(R.drawable.answer_person);
		}

		return view;
	}

	class ViewHolder {
		TextView text_nick_name,text_time,text_content,list_category_title;
		RoundImageView head_icon;
	}
}
