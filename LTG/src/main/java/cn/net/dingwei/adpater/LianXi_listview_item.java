package cn.net.dingwei.adpater;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.net.dingwei.Bean.PointsBean;
import cn.net.dingwei.Bean.Points_jinduBean2;
import cn.net.dingwei.util.MyApplication;
import cn.net.tmjy.mtiku.futures.R;

public class LianXi_listview_item extends BaseAdapter {
	private Context context;
	private List<PointsBean> list;
	private ViewHolder viewHolder;
	private Bitmap bitmap;
	private Bitmap bitmap_back;
	OnClickListener clickListener;
	//这是List<List<PointsBean>> list 的Size 为1的好时候表示在最顶层 不需要返回  其他的时候需要
	private int size;
	//0 第一个页面 1第二个页面
	private int type=0;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Color_4=0;
	private String Right_btn,Return_up;
	public LianXi_listview_item(Context context,List<PointsBean> list,OnClickListener clickListener,int size,int type) {
		// TODO Auto-generated constructor stub
		this.context = context;
		application = MyApplication.myApplication;
		this.list = list;
		viewHolder = new ViewHolder();
		this.clickListener = clickListener;
		this.size =size;
		this.type = type;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Right_btn= sharedPreferences.getString("Right_btn", "");
		Return_up= sharedPreferences.getString("Return_up", "");
		bitmap = BitmapFactory.decodeFile(Right_btn);
		bitmap_back = BitmapFactory.decodeFile(Return_up);
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
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub

		if(size!=1 && point==0){
			view = View.inflate(context, R.layout.item_back, null);
			viewHolder.imageview=(ImageView) view.findViewById(R.id.back_image);
			viewHolder.imageview.setImageBitmap(bitmap_back);
			viewHolder.text1 =  (TextView) view.findViewById(R.id.back_text);
			viewHolder.text1.setTextColor(Fontcolor_3);
			viewHolder.xian1 = (TextView) view.findViewById(R.id.xian1);
			viewHolder.xian1.setVisibility(View.VISIBLE);
			viewHolder.xian1.setBackgroundColor(Color_4);
		}else{
			view = View.inflate(context, R.layout.item_listview_lianxi, null);
			viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
			viewHolder.text2 = (TextView) view.findViewById(R.id.text2);
			viewHolder.text3 = (TextView) view.findViewById(R.id.text3);
			viewHolder.imageview = (ImageView) view.findViewById(R.id.imageview);
			//设置颜色
			viewHolder.text1.setTextColor(Fontcolor_3);
			viewHolder.text2.setTextColor(Fontcolor_7);
			viewHolder.text3.setTextColor(Fontcolor_7);

			//设置数据
			PointsBean bean =list.get(point);
			viewHolder.text1.setText(bean.getN());

			String points="";
			if(type==0){
				try {
					Points_jinduBean2 jinduBean = bean.getPoints_jinduBean2();
					if(null!=jinduBean){
						viewHolder.text2.setText(jinduBean.getTot_r()+"");
					}
					points=new JSONObject(bean.getPoints()).getString("points");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{ //type ==1 第二个页面
				points = bean.getPoints();
				viewHolder.text2.setText(bean.getWro_r());
				viewHolder.text3.setVisibility(View.GONE);
			}
			if(!points.equals("null")){
				viewHolder.imageview.setImageBitmap(bitmap);
				viewHolder.imageview.setTag(point);
				viewHolder.imageview.setOnClickListener(clickListener);
			}else{
				viewHolder.imageview.setVisibility(View.GONE);
				viewHolder.text_w_15dip = (TextView) view.findViewById(R.id.text_w_15dip);
				viewHolder.text_w_15dip.setVisibility(View.VISIBLE);
			}
			if(point==0){
				viewHolder.xian1 = (TextView) view.findViewById(R.id.xian1);
				viewHolder.xian1.setVisibility(View.VISIBLE);
				viewHolder.xian1.setBackgroundColor(Color_4);
			}else if(point == list.size()-1){
				viewHolder.xian2 = (TextView) view.findViewById(R.id.xian2);
				viewHolder.xian2.setVisibility(View.VISIBLE);
				viewHolder.xian2.setBackgroundColor(Color_4);
			}
		}
		return view;
	}

	class ViewHolder {
		TextView text1;
		TextView text2;
		TextView text3;
		TextView xian1;
		TextView xian2;
		ImageView imageview;
		TextView  text_w_15dip;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
