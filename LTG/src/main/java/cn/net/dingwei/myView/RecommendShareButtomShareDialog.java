package cn.net.dingwei.myView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;

import cn.net.dingwei.ui.ParentActivity;
import cn.net.dingwei.ui.RecommendShareActivity;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;

public class RecommendShareButtomShareDialog extends AlertDialog {

	UMSocialService mController;
	private int[] ImageIDs= new int[]{R.drawable.share_weixin,R.drawable.share_qq,R.drawable.share_code,R.drawable.share_weixin_friend,R.drawable.share_msg,R.drawable.share_qq_zong,R.drawable.share_sina,R.drawable.share_email};
	private String[] Platform_names=new String[]{"微信好友","QQ","当面扫码","分享朋友圈","短信","QQ空间","新浪微博","邮箱"};
	private int[] ImageIDxz= new int[]{R.drawable.share_weixin,R.drawable.share_qq,R.drawable.share_code,R.drawable.share_weixin_friend,R.drawable.share_msg,R.drawable.share_qq_zong,R.drawable.share_email};
	private String[] Platform_namesxz=new String[]{"微信好友","QQ","当面扫码","分享朋友圈","短信","QQ空间","邮箱"};
	private int[] ImageIDs360= new int[]{R.drawable.share_weixin,R.drawable.share_qq,R.drawable.share_code,R.drawable.share_weixin_friend,R.drawable.share_qq_zong};
	private String[] Platform_names360=new String[]{"微信好友","QQ","当面扫码","分享朋友圈","QQ空间"};
	private GridView gridView;
	private ParentActivity context;
	private ImageView imageView_code;
	private LinearLayout linear_qr_code;
	private TextView text_hint;
	private String code_url,money;
	private MyApplication application;
	private int flg=0;//0默认显示分享  1 显示二维码

	public RecommendShareButtomShareDialog(ParentActivity context, int theme, UMSocialService mController,String code_url,String money) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mController = mController;
		this.code_url =code_url;
		application = MyApplication.myApplication;
		this.money = money;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_dialog);
		LinearLayout linear_bg = (LinearLayout)findViewById(R.id.linear_bg);
		linear_bg.getBackground().setAlpha(179);
		gridView =  (GridView)findViewById(R.id.gridView);
		imageView_code = (ImageView) findViewById(R.id.qr_code_img);
		linear_qr_code =  (LinearLayout) findViewById(R.id.linear_qr_code);
		text_hint =  (TextView) findViewById(R.id.text_hint);
		ImageView image_cancel = (ImageView) findViewById(R.id.image_cancel);

		text_hint.setText("请朋友扫码，领"+money+"元红包");
		if ("teacher".equals(BuildConfig.FLAVOR)) {
			gridView.setAdapter(new ShareAdapter(ImageIDs,Platform_names));
		}else if ("yihu360".equals(BuildConfig.FLAVOR)){
			gridView.setAdapter(new ShareAdapter(ImageIDs360,Platform_names360));
		}else if ("xizhang".equals(BuildConfig.FLAVOR)){
			gridView.setAdapter(new ShareAdapter(ImageIDxz,Platform_namesxz));
		}
		int width =application.getSharedPreferencesValue_int(context,"Screen_width");
		//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/4*3, LinearLayout.LayoutParams.WRAP_CONTENT);
		LoadImageViewUtil.setImageBitmap(imageView_code, code_url, width/3,context);
		//ImageLoader.getInstance().displayImage(code_url,imageView_code, application.getOptions());
		image_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position!=2){//不是查看二维码
					dismiss();
				}
				switch (position){
					case 0:
						context.share_Platform(context, SHARE_MEDIA.WEIXIN);
						break;
					case 1:
						context.share_Platform(context, SHARE_MEDIA.QQ);
						break;
					case 2://显示图片
						if(!TextUtils.isEmpty(code_url)){
							linear_qr_code.setVisibility(View.VISIBLE);
							gridView.setVisibility(View.GONE);
						}
						break;
					case 3:
						context.share_Platform(context, SHARE_MEDIA.WEIXIN_CIRCLE);
						break;
					case 4:
						if ("teacher".equals(BuildConfig.FLAVOR)) {
							context.share_Platform(context, SHARE_MEDIA.SMS);
						}else if ("yihu360".equals(BuildConfig.FLAVOR)){
							context.share_Platform(context, SHARE_MEDIA.QZONE);
						}else if ("xizhang".equals(BuildConfig.FLAVOR)){
							context.share_Platform(context, SHARE_MEDIA.SMS);
						}
						break;
					case 5:
						if ("teacher".equals(BuildConfig.FLAVOR)) {
							context.share_Platform(context, SHARE_MEDIA.QZONE);
						}else if ("yihu360".equals(BuildConfig.FLAVOR)){
							context.share_Platform(context, SHARE_MEDIA.SINA);
						}else if ("xizhang".equals(BuildConfig.FLAVOR)){
							context.share_Platform(context, SHARE_MEDIA.QZONE);
						}
						break;
					case 6:
						if ("xizhang".equals(BuildConfig.FLAVOR)){
							context.share_Platform(context, SHARE_MEDIA.EMAIL);
						}else {
							context.share_Platform(context, SHARE_MEDIA.SINA);
						}
						break;
					case 7:
						context.share_Platform(context, SHARE_MEDIA.EMAIL);
						break;
				}
			}
		});
	}

	class ShareAdapter extends BaseAdapter{
		private int[] ImageIDs;
		private String[] Platform_names;
		public ShareAdapter(int[] imageIDs,String[] Platform_names) {
			ImageIDs = imageIDs;
			this.Platform_names = Platform_names;
		}

		@Override
		public int getCount() {
			return ImageIDs.length;
		}

		@Override
		public Object getItem(int position) {
			return ImageIDs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView =View.inflate(context,R.layout.item_share_dialog,null);
				viewHolder = new ViewHolder();
				viewHolder.image_icon = (ImageView) convertView.findViewById(R.id.image_icon);
				viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.image_icon.setImageResource(ImageIDs[position]);
			viewHolder.text_name.setText(Platform_names[position]);
			return convertView;
		}
	}
	class ViewHolder{
		ImageView image_icon;
		TextView text_name;

	}

	@Override
	public void show() {
		super.show();
		if(flg==0){
			linear_qr_code.setVisibility(View.GONE);
			gridView.setVisibility(View.VISIBLE);
		}else if(flg==1){
			linear_qr_code.setVisibility(View.VISIBLE);
			gridView.setVisibility(View.GONE);
		}
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}
}
