package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.net.dingwei.Bean.Get_user_baseinfo;
import cn.net.dingwei.adpater.ShareGridViewAdapter;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;

import com.umeng.socialize.bean.SHARE_MEDIA;
/**
 * 分享
 * @author Administrator
 *
 */
public class ShareActivity extends ParentActivity{
	private GridView share_gridview;
	//private int[] share_Platform_icons=new int[]{R.drawable.qr_code,R.drawable.umeng_socialize_wechat,R.drawable.umeng_socialize_wxcircle,	R.drawable.umeng_socialize_qq_on,R.drawable.umeng_socialize_qzone_on,R.drawable.umeng_socialize_tx_on,R.drawable.umeng_socialize_sina_on,R.drawable.umeng_socialize_gmail_on,R.drawable.umeng_socialize_sms_on};
	//private String[] share_Platform_name = new String[]{"二维码","微信","朋友圈","QQ","QQ空间","腾讯微博","新浪","邮件","短信"};
	private int[] share_Platform_icons=new int[]{R.drawable.umeng_socialize_wechat,R.drawable.umeng_socialize_wxcircle,	R.drawable.umeng_socialize_qq_on,R.drawable.umeng_socialize_qzone_on,R.drawable.umeng_socialize_tx_on,R.drawable.umeng_socialize_sina_on,R.drawable.umeng_socialize_gmail_on,R.drawable.umeng_socialize_sms_on};
	private String[] share_Platform_name = new String[]{"微信","朋友圈","QQ","QQ空间","腾讯微博","新浪","邮件","短信"};
	private int[] share_Platform_icons360=new int[]{R.drawable.umeng_socialize_wechat,R.drawable.umeng_socialize_wxcircle,	R.drawable.umeng_socialize_qq_on,R.drawable.umeng_socialize_qzone_on,R.drawable.umeng_socialize_tx_on,R.drawable.umeng_socialize_sina_on};
	private String[] share_Platform_name360 = new String[]{"微信","朋友圈","QQ","QQ空间","腾讯微博","新浪"};
	private TextView title_text;
	private ImageView title_left,qr_code_img;
	private TextView qr_code_title,qr_code_buttom;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Screen_width=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_share);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		initID();
		initData();
	}
	private void initID() {
		// TODO Auto-generated method stub
		title_text=(TextView)findViewById(R.id.title_text);
		findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
		title_left=(ImageView)findViewById(R.id.title_left);
		share_gridview=(GridView)findViewById(R.id.share_gridview);
		share_gridview.setBackgroundColor(sharedPreferences.getInt("color_4", 0));
		//二维码
		qr_code_title=(TextView)findViewById(R.id.qr_code_title);
		qr_code_buttom=(TextView)findViewById(R.id.qr_code_buttom);
		qr_code_img=(ImageView)findViewById(R.id.qr_code_img);

		title_text.setText("推荐给朋友");
		title_left.setImageResource(R.drawable.arrow_whrite);
	}
	private void initData() {
		Log.i("123", "赋值: ");
		// TODO Auto-generated method stub
		share_gridview.setAdapter(new ShareGridViewAdapter(this, share_Platform_icons, share_Platform_name));
		Get_user_baseinfo.app shareBean_app=MyApplication.Getget_user_baseinfo(ShareActivity.this).getShare_type().getApp();
		share(shareBean_app.getShare_title(),shareBean_app.getShare_content(),shareBean_app.getShare_img(),shareBean_app.getShare_link());
		//设置二维码
		qr_code_title.setText(shareBean_app.getQrcode_title());
		qr_code_buttom.setText(shareBean_app.getQrcode_content());
		//LoadImageViewUtil.setImageBitmap(qr_code_img, shareBean_app.getQrcode_img());
		LoadImageViewUtil.setImageBitmap(qr_code_img, shareBean_app.getQrcode_img(), Screen_width/4*3,ShareActivity.this);

		share_gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int point,
									long arg3) {
				// TODO Auto-generated method stub
				switch (point) {
				/*case 0://二维码
					Intent intent = new Intent();
					intent.setClass(ShareActivity.this, QR_code_Activity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, 0);
					break;*/
					case 0://微信
						share_Platform(ShareActivity.this, SHARE_MEDIA.WEIXIN);
						break;
					case 1://微信朋友圈
						share_Platform(ShareActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
						break;
					case 2://QQ
						share_Platform(ShareActivity.this, SHARE_MEDIA.QQ);
						break;
					case 3://QQ空间
						share_Platform(ShareActivity.this, SHARE_MEDIA.QZONE);
						break;
					case 4://腾讯微博
						share_Platform(ShareActivity.this, SHARE_MEDIA.TENCENT);
						break;
					case 5://新浪
						share_Platform(ShareActivity.this, SHARE_MEDIA.SINA);
						break;
					case 6://邮件
						share_Platform(ShareActivity.this, SHARE_MEDIA.EMAIL);
						break;
					case 7://短信
						share_Platform(ShareActivity.this, SHARE_MEDIA.SMS);
						break;

					default:
						break;
				}
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0 && resultCode == RESULT_OK){//二维码扫描返回
			Bundle bundle = data.getExtras();
			//显示扫描到的内容
			//Log.i("mylog", "二维码扫描结果："+bundle.getString("result"));
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(ShareActivity.this);
			finish();
			return false;
		}
		return false;
	}
}
