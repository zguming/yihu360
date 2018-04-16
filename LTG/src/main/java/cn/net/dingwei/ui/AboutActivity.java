package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.umeng.socialize.sso.UMSsoHandler;

import cn.net.dingwei.Bean.Get_user_baseinfo;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class AboutActivity extends ParentActivity{
	private TextView title_text,xian1,xian2,version,content,log_title1,log_title2;
	private ImageView title_left,log_icon1,log_icon2;
	private LinearLayout linear_icon1,linear_icon2;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Color_3=0;
	private int Screen_width=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_about);
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3 =sharedPreferences.getInt("fontcolor_3", 0);
		Color_3 =sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);
		initID();
		initData();
	}



	private void initID() {
		// TODO Auto-generated method stub
		application = MyApplication.myApplication;
		title_text=(TextView)findViewById(R.id.title_text);
		findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
		title_left=(ImageView)findViewById(R.id.title_left);
		xian1=(TextView)findViewById(R.id.xian1);
		xian2=(TextView)findViewById(R.id.xian2);
		version=(TextView)findViewById(R.id.version);
		content=(TextView)findViewById(R.id.content);
		log_title1=(TextView)findViewById(R.id.log_title1);
		log_title2=(TextView)findViewById(R.id.log_title2);
		log_icon1=(ImageView)findViewById(R.id.log_icon1);
		log_icon2=(ImageView)findViewById(R.id.log_icon2);
		linear_icon1=(LinearLayout)findViewById(R.id.linear_icon1);
		linear_icon2=(LinearLayout)findViewById(R.id.linear_icon2);
		//设置颜色 

		version.setTextColor(Fontcolor_3);
		content.setTextColor(Fontcolor_3);

	}

	private void initData() {
		// TODO Auto-generated method stub
		//设置数据
		title_text.setText("关于我们");
		title_left.setImageResource(R.drawable.arrow_whrite);
		LayoutParams params = new LayoutParams(Screen_width,1);
		xian1.setLayoutParams(params);
		xian2.setLayoutParams(params);
		xian1.setBackgroundColor(Color_3);
		xian2.setBackgroundColor(Color_3);
		version.setText(getVersion());
		/*Log.i("Mylog","包名："+getPackageName());
		ApplicationInfo appInfo = null;
		try {
			appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
							PackageManager.GET_META_DATA);
			String msg=appInfo.metaData.getString("JPUSH_APPKEY");
			Log.d("mylog", " msg == " + msg);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}*/



		Get_user_baseinfo.about_us about_us= MyApplication.Getget_user_baseinfo(AboutActivity.this).getAbout_us();
		int icon_height =  DensityUtil.DipToPixels(this, 80);
		for (int i = 0; i < about_us.getLogos().length; i++) {
			if(i==0){
				LoadImageViewUtil.setImageBitmap_setwidth(log_icon1, about_us.getLogos()[0].getImg(),icon_height,AboutActivity.this);
				log_title1.setText(about_us.getLogos()[0].getTitle());
				linear_icon1.setVisibility(View.VISIBLE);
			}else if(i==1){
				LoadImageViewUtil.setImageBitmap_setwidth(log_icon2, about_us.getLogos()[1].getImg(),icon_height,AboutActivity.this);
				log_title2.setText(about_us.getLogos()[1].getTitle());
				linear_icon2.setVisibility(View.VISIBLE);
			}
		}

		content.setText(about_us.getContent().replace("\\n", "\n"));
	}

	/**
	 　　* 获取版本号
	 　　* @return 当前应用的版本号
	 　　*/
	public String getVersion() {
		try {
			PackageManager manager = getApplicationContext().getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return "";
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
		if(ssoHandler != null){
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(AboutActivity.this);
			finish();
			return false;
		}
		return false;
	}
}
