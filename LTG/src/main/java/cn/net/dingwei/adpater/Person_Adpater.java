package cn.net.dingwei.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;
import me.xiaopan.switchbutton.SwitchButton;

public class Person_Adpater extends BaseAdapter{
	private String[][] stArray;
	private Activity context;
	private MyViewholder viewholder;
	private int type=0;
	private int[] images = new int[]{R.drawable.pic7,R.drawable.pic_yue,-1,R.drawable.pic_ct,R.drawable.pic_sc,R.drawable.pic_bj,-1,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,R.drawable.pic_night,-1,R.drawable.share_frienfd1,R.drawable.feedback,R.drawable.pic12};
	//private int[] images = new int[]{R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,R.drawable.share_frienfd,R.drawable.feedback,R.drawable.pic12};
	private Boolean flg=false;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Bgcolor_2=0,Color_3=0;
	public Person_Adpater(Activity context,String[][] stArray,int type) {
		// TODO Auto-generated constructor stub
		this.stArray =stArray;
		this.context = context;
		application = MyApplication.myApplication;
		viewholder = new MyViewholder();
		this.type=type;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
	}

	public Person_Adpater(Activity context,String[][] stArray,int type,Boolean flg) {
		// TODO Auto-generated constructor stub
		this.stArray =stArray;
		this.context = context;
		application = MyApplication.myApplication;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		viewholder = new MyViewholder();
		this.type=type;
		this.flg =flg;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stArray.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return stArray[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(position==stArray.length-1 &&  type==0){ //最后一个
			convertView =View.inflate(context, R.layout.item_list_null, null);
			viewholder.text=(TextView)convertView.findViewById(R.id.text);
			viewholder.text.setTextColor(Bgcolor_2);
			viewholder.text.setText(stArray[position][0]);
			viewholder.linear_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
			viewholder.linear_bg.setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.textview).setVisibility(View.GONE);
			TextView textview = (TextView)convertView.findViewById(R.id.textview_hint);
			if(null!=stArray[position][1]&&!"".equals(stArray[position][1])){
				textview.setText(stArray[position][1]);
				textview.setTextColor(Color.LTGRAY);
				textview.setVisibility(View.VISIBLE);
			}else{
				textview.setVisibility(View.GONE);
			}
		}else{
			//设置数据
			if(stArray[position][0].equals("")){//间隔
				convertView =View.inflate(context, R.layout.item_list_null, null);
			}else{
				convertView =View.inflate(context, R.layout.item_person, null);
				viewholder.text=(TextView)convertView.findViewById(R.id.text);
				viewholder.text2=(TextView)convertView.findViewById(R.id.text2);
				viewholder.left_image=(ImageView)convertView.findViewById(R.id.left_image);
				viewholder.left_image1=(ImageView)convertView.findViewById(R.id.left_image1);
				viewholder.btn_Switch= (SwitchButton) convertView.findViewById(R.id.btn_Switch);
				viewholder.linear_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
				viewholder.linear_night = (LinearLayout) convertView.findViewById(R.id.linear_night);
				viewholder.text2.setVisibility(View.VISIBLE);
				viewholder.text.setTextColor(Fontcolor_3);
				viewholder.text2.setTextColor(Fontcolor_3);
				if(flg==true){
					if(position<images.length){
						viewholder.left_image.setImageResource(images[position]);
					}
				}else{
					viewholder.left_image.setVisibility(View.GONE);
				}

				//设置数据
				viewholder.text.setText(stArray[position][0]);
				Log.e("aaa","stArray[position][1] = " + stArray[position][1]);
				viewholder.text2.setText(stArray[position][1]);
				if(stArray[position][0].equals("夜间模式")){
					viewholder.left_image1.setImageResource(images[position]);
					Boolean isNight = sharedPreferences.getBoolean("isNight",false);
					if(isNight==false){
						viewholder.btn_Switch.setChecked(false);
					}else{
						viewholder.btn_Switch.setChecked(true);
					}
					viewholder.linear_bg.setVisibility(View.GONE);
					viewholder.linear_night.setVisibility(View.VISIBLE);
					viewholder.btn_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton compoundButton, boolean isOpen) {
							if(isOpen){
								application.setWindowBrightness(context,(int) (application.getScreenBrightness(context)*MyFlg.brightnessBiLi),isOpen);
								sharedPreferences.edit().putBoolean("isNight",true).commit();
							}else{
								application.setWindowBrightness(context,application.getScreenBrightness(context),isOpen);
								sharedPreferences.edit().putBoolean("isNight",false).commit();
							}

						}
					});
				}else{
					viewholder.linear_bg.setVisibility(View.VISIBLE);
					viewholder.linear_night.setVisibility(View.GONE);
				}
                if("construction_cp".equals(BuildConfig.MYAPP_KEY)||"funds_cp".equals(BuildConfig.MYAPP_KEY)) {
                    if(position == 13) {
                        viewholder.linear_bg.setVisibility(View.GONE);
                    }
                }

			}
		}

		viewholder.buttom_xian=(TextView)convertView.findViewById(R.id.buttom_xian);
		viewholder.buttom_xian.setBackgroundColor(Color_3);
		viewholder.buttom_xian.setVisibility(View.VISIBLE);
		return convertView;
	}
	class MyViewholder{
		TextView text;
		TextView text2;
		TextView textview;
		TextView buttom_xian;
		ImageView left_image,left_image1;
		LinearLayout linear_bg,linear_night;
		SwitchButton btn_Switch;
	}

}
