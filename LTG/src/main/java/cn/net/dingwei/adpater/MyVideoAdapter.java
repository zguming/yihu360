package cn.net.dingwei.adpater;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.ui.PayVIPActivity;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.dingwei.util.SharedInfo;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;
import cn.net.treeListView.Node;
import cn.net.treeListView.TreeHelper;
import cn.net.treeListView.TreeListViewAdapter;
import universal_video.UniversalVideoActicity;

public class MyVideoAdapter<T> extends TreeListViewAdapter<T>{
	private LinearLayout.LayoutParams params1;
	private LinearLayout.LayoutParams params2;
	private MyApplication application;
	private FYuanTikuDialog dialog;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Fontcolor_13=0,Bgcolor_1=0,Bgcolor_2=0,Color_4=0,shoping_color=0;
	private int Screen_width=0;
	private int progress_width=0;
	private int dip_5;
	private int tag=0;

	List<T> datas ;
	private String Nav_2_active,Nav_2;
	private int  flg=0;//0不管 1 是视频详情页面发起的 关闭详情页
	public static int tempPosition = -1;


	public MyVideoAdapter(ListView mTree, Activity context, List<T> datas, int defaultExpandLevel)
			throws IllegalArgumentException,IllegalAccessException{
		super(mTree, context, datas, defaultExpandLevel);
		dip_5 = DensityUtil.DipToPixels(context, 5);
		params1 =  new LinearLayout.LayoutParams(dip_5*3, dip_5*3);
		params2 =  new LinearLayout.LayoutParams(dip_5*2, dip_5*2);
		application = MyApplication.myApplication;
		dialog = new FYuanTikuDialog(context,R.style.DialogStyle,"正在加载...");

		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Color_4= sharedPreferences.getInt("color_4", 0);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Fontcolor_13= sharedPreferences.getInt("fontcolor_13", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		Nav_2_active = sharedPreferences.getString("Nav_2_active", "");
		Nav_2 = sharedPreferences.getString("Nav_2", "");

		progress_width = Screen_width - DensityUtil.DipToPixels(context, 165);//减去其他控件的宽度 剩下进度条的宽度
		this.datas = datas;
		shoping_color = context.getResources().getColor(R.color.video_shoping);
	}

	@Override
	public View getConvertView(final Node node , int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.item_video_listview, parent, false);
			viewHolder = new ViewHolder();
			viewHolder. image_left = (ImageView) convertView.findViewById(R.id.image_left);
			viewHolder.image_play = (ImageView) convertView.findViewById(R.id.image_play);
			viewHolder.linear_shopping = (LinearLayout) convertView.findViewById(R.id.linear_shopping);
			viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
			viewHolder.view_item_jianju = convertView.findViewById(R.id.view_item_jianju);
			viewHolder.progressbar = (ProgressBar) convertView.findViewById(R.id.progressbar);
			viewHolder.xian1 = convertView.findViewById(R.id.xian1);
			viewHolder.xian2 = convertView.findViewById(R.id.xian2);
			viewHolder.view_buttom_xian = convertView.findViewById(R.id.view_buttom_xian);
			viewHolder.text_time.setTextColor(Fontcolor_13);
			viewHolder.text_title.setTextColor(Fontcolor_3);
			viewHolder.image_play.setImageBitmap(BitmapFactory.decodeFile(Nav_2_active));
			viewHolder.view_buttom_xian.setBackgroundColor(Color_4);
			convertView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.progressbar.setProgress(node.getProgress2());
		viewHolder.image_left.setVisibility(View.VISIBLE);
		viewHolder.image_left.setImageResource(node.getIcon());

		//判断 是否需要显示线
		if(node.getIsLastForPid()==1 &&node.isExpand()==false){ //是最后一个 并且没展开
			viewHolder.xian2.setVisibility(View.INVISIBLE);
			viewHolder.view_buttom_xian.setVisibility(View.GONE);
		}else if(node.isExpand()==false&&node.getpId()==0){ //是根节点 并且没展开
			viewHolder.xian2.setVisibility(View.INVISIBLE);
			viewHolder.view_buttom_xian.setVisibility(View.GONE);
		}else {
			viewHolder.xian2.setVisibility(View.VISIBLE);
			viewHolder.view_buttom_xian.setVisibility(View.VISIBLE);
		}

		if(node.getpId()!=0){ //父类不为0  不是第一级节点
			viewHolder.xian1.setVisibility(View.VISIBLE);
		}else{
			viewHolder.xian1.setVisibility(View.INVISIBLE);
		}


		if(node.getLevel()==0){
			viewHolder.image_left.setLayoutParams(params1);
			viewHolder.view_item_jianju.setVisibility(View.VISIBLE);
		}else if(node.getLevel()==1){
			viewHolder.image_left.setLayoutParams(params2);
			viewHolder.view_item_jianju.setVisibility(View.GONE);
		}
		viewHolder.text_title.setText(node.getName());
		viewHolder.text_time.setText(node.getTimes()+"分钟");


		viewHolder.linear_shopping.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "购物", Toast.LENGTH_SHORT).show();
			}
		});

		if(node.getType().equals("0")){//可播放
			viewHolder.image_play.setVisibility(View.VISIBLE);
			viewHolder.linear_shopping.setVisibility(View.GONE);
			if (tempPosition == position) {
				viewHolder.image_play.setImageBitmap(BitmapFactory.decodeFile(Nav_2));
				viewHolder.image_play.setEnabled(false);
			} else {
				viewHolder.image_play.setEnabled(true);
			}
		}else if(node.getType().equals("1")){//要购买
			viewHolder.image_play.setVisibility(View.GONE);
			viewHolder.linear_shopping.setVisibility(View.VISIBLE);
			viewHolder.linear_shopping.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE,Color.LTGRAY,shoping_color,1,dip_5));
		}else if(node.getType().equals("2")){//都要
			viewHolder.image_play.setVisibility(View.VISIBLE);
			viewHolder.linear_shopping.setVisibility(View.VISIBLE);
			viewHolder.linear_shopping.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE,Color.LTGRAY,shoping_color,1,dip_5));
		}
		viewHolder.linear_shopping.setOnClickListener(new Item_click(node.getOption_id(),node.getProgress3(),position));
		viewHolder.image_play.setOnClickListener(new Item_click(node.getOption_id(),node.getProgress3(),position));
		return convertView;
	}

	private final class ViewHolder
	{
		View view_item_jianju,xian1,xian2,view_buttom_xian;
		ImageView image_left,image_play;
		TextView text_title,text_time;
		ProgressBar progressbar;
		LinearLayout linear_shopping;

	}
	class Item_click implements OnClickListener{
		private String id;
		private int isGroup;//是否是分组 0不是  1是
		private int position;
		public Item_click(String id,int isGroup,int position) {
			this.id = id;
			this.isGroup = isGroup;
			this.position =position;
		}

		@Override
		public void onClick(View v) {
			Intent intent=null;
				switch (v.getId()){
					case R.id.linear_shopping://购物车
						intent = new Intent(mContext,PayVIPActivity.class);
						String url = "";
						Log.i("123", "isGroup: "+isGroup);
						if(isGroup==1){//是分组
							if ("teacher".equals(BuildConfig.FLAVOR)) {
								url = "http://wv.mtiku.net/1.0/webview/"+ BuildConfig.MYAPP_KEY+"/video_buys?clientcode="+MyFlg.getclientcode(mContext)+"&os=Android&type=2"+"&id="+id;
							}else if ("yihu360".equals(BuildConfig.FLAVOR)){
								url = "http://wv.botian120.com/1.0/webview/"+ BuildConfig.MYAPP_KEY+"/video_buys?clientcode="+MyFlg.getclientcode(mContext)+"&os=Android&type=2"+"&id="+id;
							}else if ("xizhang".equals(BuildConfig.FLAVOR)){
								url = "http://wv.52zangyu.com/1.0/webview/"+ BuildConfig.MYAPP_KEY+"/video_buys?clientcode="+MyFlg.getclientcode(mContext)+"&os=Android&type=2"+"&id="+id;
							}
							intent.putExtra("flg", 1);
						}else{//不是分组
							if ("teacher".equals(BuildConfig.FLAVOR)) {
								url = "http://wv.mtiku.net/1.0/webview/"+ BuildConfig.MYAPP_KEY+"/video_buys?clientcode="+MyFlg.getclientcode(mContext)+"&os=Android&type=1"+"&id="+id;
							}else if ("yihu360".equals(BuildConfig.FLAVOR)){
								url = "http://wv.botian120.com/1.0/webview/"+ BuildConfig.MYAPP_KEY+"/video_buys?clientcode="+MyFlg.getclientcode(mContext)+"&os=Android&type=1"+"&id="+id;
							}else if ("xizhang".equals(BuildConfig.FLAVOR)){
								url = "http://wv.52zangyu.com/1.0/webview/"+ BuildConfig.MYAPP_KEY+"/video_buys?clientcode="+MyFlg.getclientcode(mContext)+"&os=Android&type=1"+"&id="+id;
							}
							intent.putExtra("flg", 2);
							intent.putExtra("vid", ""+id);
						}
						intent.putExtra("url", url);
						if (mAllNodes != null){
							SharedInfo.getInstance().setdatas(mAllNodes);
						}else {
							SharedInfo.getInstance().clear();
						}
//						intent.putExtra("allData", (Serializable) mAllNodes);
						intent.putExtra("position", position);
						mContext.startActivityForResult(intent,31);
						break;
					case R.id.image_play://播放
						if (mAllNodes != null){
							SharedInfo.getInstance().setdatas(mAllNodes);
						}else {
							SharedInfo.getInstance().clear();
						}
						if(isGroup==1){//是分组
							UniversalVideoActicity.intentTo(mContext,UniversalVideoActicity.PlayType.vid,  0+"", true,id,null,position);
						}else{//不是分组
							UniversalVideoActicity.intentTo(mContext, UniversalVideoActicity.PlayType.vid, id, true,0+"",null,position);
						}
						tempPosition = position;

						if(flg==1){//由详情页发起 关闭当前
							mContext.finish();
						}
						break;

				}
		}
	}

	/**
	 * 仿IOS对话框
	 * @param title
	 * @param content
	 * @param leftBtnText
	 * @param rightBtnText
	 */
	public static void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText,final Context context) {
		F_IOS_Dialog.showAlertDialogChoose(context, title,content, leftBtnText, rightBtnText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case F_IOS_Dialog.BUTTON1:
								dialog.dismiss();
								Intent intent = new Intent(context, PayVIPActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								context.startActivity(intent);
								break;
							case F_IOS_Dialog.BUTTON2:
								dialog.dismiss();
								break;
							default:
								break;
						}

					}
				});
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}


	public void setDatasNew(List<TreeListViewBean> datas){

	}
	/***
	 * 设置数据  更新
	 * @param datas
     */
	public void setDatas(List<TreeListViewBean> datas){
		try {
			List<Node> temp_allNodes = TreeHelper.getSortedNodes(datas, 0);
			for (int i=0;i<temp_allNodes.size();i++){
				String id=temp_allNodes.get(i).getOption_id();
				if(TextUtils.isEmpty(id)){
					continue;
				}
				for (int k=0;k<mAllNodes.size();k++){
					if(id.equals(mAllNodes.get(k).getOption_id())){
						temp_allNodes.get(i).setExpand(mAllNodes.get(k).isExpand());
					}
				}
			}
			mAllNodes.clear();
			mAllNodes.addAll(temp_allNodes);
			mNodes.clear();
			mNodes.addAll(TreeHelper.filterVisibleNode(mAllNodes));
			notifyDataSetChanged();// 刷新视图
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		}
	}

	public void setmAllNodes(List<Node> mAllNodes) {
		this.mAllNodes.clear();
		this.mAllNodes.addAll(mAllNodes);
		/**
		 * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
		 */
		for (int i = 0; i < mAllNodes.size(); i++) {
			Node n = mAllNodes.get(i);
			for (int j = i + 1; j < mAllNodes.size(); j++) {
				Node m = mAllNodes.get(j);
				if (m.getpId() == n.getId()) {
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId() == n.getpId()) {
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}
	}
}
