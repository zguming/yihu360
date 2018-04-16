package cn.net.dingwei.adpater;

import java.util.List;

import com.umeng.socialize.utils.Log;

import listview_DownPullAndUpLoad.XListView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.myView.MyGridView;
import cn.net.dingwei.ui.Image_ViewPager_Activity;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.tmjy.mtiku.futures.R;

public class Guide_RefreshListView_Adpater extends BaseAdapter{
	private Context context;
	private List<Get_guide_msg_listBean.msglist> list_guides;
	//private ViewHolder viewHolder;
	private int imageViewWidth;//设置为屏幕一半的宽度
	private int imageViewWidth2; //有2张图的时候宽度
	//跳转
	private Intent intent;
	private LayoutParams layoutParams;//显示2张的时候
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Bgcolor_2=0,Color_4=0,Color_3=0;
	private int Screen_width=0;

	private XListView listview;
	private Boolean IsLoadImage=true;
	public Guide_RefreshListView_Adpater(Context context,List<Get_guide_msg_listBean.msglist> list_guides,XListView listview) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list_guides = list_guides;
		this.listview = listview;
		application = MyApplication.myApplication;

		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_4 = sharedPreferences.getInt("color_4", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);

		imageViewWidth = (Screen_width-DensityUtil.DipToPixels(context, 20))/2;
		intent = new Intent(context, Image_ViewPager_Activity.class);
		imageViewWidth2 =  (Screen_width-DensityUtil.DipToPixels(context, 26))/3;
		layoutParams = new LinearLayout.LayoutParams(imageViewWidth2, imageViewWidth2);

		this.listview.setOnScrollListener(new OnScrollListener() { //ListView 处理滑动加载图片卡顿

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
					case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://停止
						IsLoadImage = true;
						UpdataUI();
						break;
					case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸滑动
						IsLoadImage = false;
						break;
					case AbsListView.OnScrollListener.SCROLL_STATE_FLING://快速滑动
						IsLoadImage = false;
						break;
					default:
						break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		})	;
	}
	public void UpdataUI(){
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_guides.size();
	}

	@Override
	public Object getItem(int point) {
		// TODO Auto-generated method stub
		return list_guides.get(point);
	}

	@Override
	public long getItemId(int point) {
		// TODO Auto-generated method stub
		return point;
	}

	@Override
	public View getView(int point, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//if(null==view){
		ViewHolder viewHolder=null;
		if(null==view){
			view = View.inflate(context, R.layout.item_guide_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.head_icon = (ImageView) view.findViewById(R.id.head_icon);
			viewHolder.content_image1 = (ImageView) view.findViewById(R.id.content_image1);
			viewHolder.content_image = (ImageView) view.findViewById(R.id.content_image);
			viewHolder.content_image.setTag(point);
			viewHolder.content_image2 = (ImageView) view.findViewById(R.id.content_image2);
			viewHolder.content_image2.setTag(point);
			viewHolder.title = (TextView) view.findViewById(R.id.title);
			viewHolder.author_name = (TextView) view.findViewById(R.id.author_name);
			viewHolder.time_text = (TextView) view.findViewById(R.id.time_text);
			viewHolder.content = (TextView) view.findViewById(R.id.content);
			viewHolder.gridview = (MyGridView) view.findViewById(R.id.gridview);
			//设置颜色
			viewHolder.title.setTextColor(Bgcolor_2);
			viewHolder.author_name.setTextColor(Color_3);
			viewHolder.content.setTextColor(Fontcolor_3);
			viewHolder.time_text.setTextColor(Color_4);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}


		//设置数据
		Get_guide_msg_listBean.msglist msglist = list_guides.get(point);
		if(null!=msglist){
			if(!"".equals(msglist.getIcon()) && !"null".equals(msglist.getIcon())){
				LoadImageViewUtil.setImageBitmap(viewHolder.head_icon, msglist.getIcon(),context);
			}else{
				viewHolder.head_icon.setVisibility(View.GONE);
			}
			if(null !=msglist.getImages()){
				if(msglist.getImages().length==1){
					if(IsLoadImage==true){
						LoadImageViewUtil.setImageBitmap(viewHolder.content_image, msglist.getImages()[0].getThumb_url(), imageViewWidth,context);
					}else{
						viewHolder.content_image.setImageDrawable(new ColorDrawable(Color.LTGRAY));
					}

					viewHolder.content_image.setVisibility(View.VISIBLE);
					viewHolder.content_image2.setVisibility(View.GONE);
					viewHolder.content_image1.setVisibility(View.GONE);
					viewHolder.gridview.setVisibility(View.GONE);
					viewHolder.content_image.setOnClickListener(new imageClick(point));
				}else if(msglist.getImages().length==2){
					viewHolder.content_image.setVisibility(View.GONE);
					viewHolder.content_image1.setVisibility(View.VISIBLE);
					viewHolder.content_image2.setVisibility(View.VISIBLE);
					viewHolder.content_image1.setLayoutParams(layoutParams);
					viewHolder.content_image2.setLayoutParams(layoutParams);
					if(IsLoadImage==true){
						LoadImageViewUtil.setImageBitmap(viewHolder.content_image1, msglist.getImages()[0].getThumb_url(),context);
						LoadImageViewUtil.setImageBitmap(viewHolder.content_image2, msglist.getImages()[1].getThumb_url(),context);
					}else{
						viewHolder.content_image1.setImageDrawable(new ColorDrawable(Color.LTGRAY));
						viewHolder.content_image2.setImageDrawable(new ColorDrawable(Color.LTGRAY));
					}

					viewHolder.gridview.setVisibility(View.GONE);
					viewHolder.content_image1.setOnClickListener(new imageClick(point));
					viewHolder.content_image2.setOnClickListener(new imageClick(point));
				}else if(msglist.getImages().length>=3){
					viewHolder.content_image.setVisibility(View.GONE);
					viewHolder.content_image2.setVisibility(View.GONE);
					viewHolder.content_image1.setVisibility(View.GONE);
					viewHolder.gridview.setVisibility(View.VISIBLE);
					viewHolder.gridview.setTag(point);
					viewHolder.gridview.setAdapter(new Gridview_Adapter(context, msglist.getImages()));
					viewHolder.gridview.setOnItemClickListener(new myGridViewOnItemClick(point));
				}else{

					viewHolder.content_image.setVisibility(View.GONE);
					viewHolder.content_image1.setVisibility(View.GONE);
					viewHolder.content_image2.setVisibility(View.GONE);
					viewHolder.gridview.setVisibility(View.GONE);
				}
			}
			viewHolder.title.setText(msglist.getTitle());
			viewHolder.author_name.setText(msglist.getAuthor_name());
			viewHolder.time_text.setText(msglist.getTime_text());
			viewHolder.content.setText(msglist.getContent());
		}
		//	}
		return view;
	}
	class ViewHolder{
		ImageView head_icon,content_image,content_image2,content_image1;
		TextView title,author_name,time_text,content;
		MyGridView gridview;
		Gridview_Adapter adapter;
	}

	private class imageClick implements OnClickListener{
		private int point;//Item位置
		public imageClick(int point) {
			// TODO Auto-generated constructor stub
			this.point= point;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.content_image:
					startActivity(v,0,point);
					break;
				case R.id.content_image1:
					startActivity(v,0,point);
					break;
				case R.id.content_image2:
					startActivity(v,1,point);
					break;
				default:
					break;
			}
		}

	}
	private void startActivity(View v,int index,int point){
		//传数组对象过去
		Bundle bundle = new Bundle();
		bundle.putSerializable("msglist", list_guides.get(point));
		//intent.putExtra("imageUrls", list_guides.get((Integer)v.getTag()).getImages());
		intent.putExtras(bundle);
		intent.putExtra("index", index);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(intent);
	}
	class myGridViewOnItemClick implements OnItemClickListener{
		private int tag;
		public myGridViewOnItemClick(int tag) {
			// TODO Auto-generated constructor stub
			this.tag =tag;
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int point, long arg3) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putSerializable("msglist", list_guides.get(tag));
			intent.putExtras(bundle);
			intent.putExtra("index", point);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(intent);
		}
	}

}
