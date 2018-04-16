package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.BuildConfig;
import cn.net.tmjy.mtiku.futures.R;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ParentActivity extends Activity{
	Boolean showONE=true;
	final  UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	public MyApplication application;
	private SharedPreferences sharedPreferences;
	public Boolean isSetTile=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		application = MyApplication.myApplication;
		sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Boolean isNight = sharedPreferences.getBoolean("isNight",false);
		if(isNight==false){
			application.setWindowBrightness(this,application.getScreenBrightness(this),isNight);
		}else{
			application.setWindowBrightness(this,(int) (application.getScreenBrightness(this)*MyFlg.brightnessBiLi),isNight);
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(isSetTile){
			View title_bg = findViewById(R.id.title_bg);
			View layoutLeft = findViewById(R.id.layoutLeft);
			if(title_bg!=null){
				title_bg.setBackgroundColor(sharedPreferences.getInt("bgcolor_1", 0));
			}
			if(layoutLeft!=null){
				layoutLeft.findViewById(R.id.layoutLeft).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyFlg.all_activitys.remove(getApplication());
						MyFlg.listActivity.remove(getApplication());
						finish();
						String out_way = getIntent().getStringExtra("out_way");
						if("top2bottom".equals(out_way)) {
							overridePendingTransition(0,R.anim.slide_out_bottom);
						}else {
							overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
						}
					}
				});
			}
		}

	}
	/**
	 *
	 * @param title		标题
	 * @param content	内容
	 * @param imageurl	图片链接
	 * @param clickUrl	点击链接
	 * @return
	 */
	public  UMSocialService share(String title,String content,String imageurl,String clickUrl){
		// 设置分享内容
		mController.setShareContent(content);
		mController.getConfig().closeToast();//关掉Toast提示信息
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(this,imageurl));
		mController.getConfig().setMailSubject(title);//设置邮件主题
		// 设置分享图片，参数2为本地图片的资源引用
		//mController.setShareMedia(new UMImage(this, R.drawable.a3));
		// 设置分享图片，参数2为本地图片的路径(绝对路径)
		//mController.setShareMedia(new UMImage(getActivity(),
//				                                BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

		// 设置分享音乐
		//UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
		//uMusic.setAuthor("GuGu");
		//uMusic.setTitle("天籁之音");
		// 设置音乐缩略图
		//uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		//mController.setShareMedia(uMusic);

		// 设置分享视频
		//UMVideo umVideo = new UMVideo(
//				          "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
		// 设置视频缩略图
		//umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		//umVideo.setTitle("友盟社会化分享!");
		//mController.setShareMedia(umVideo);
		//--------------QQ分享----------------------------------------------------------------------------------------------
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.（自己申请）
		String  QQAppId= BuildConfig.QQ_APPID;
		String  QQAPPKEY=BuildConfig.QQ_APPKEY;
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, QQAppId,QQAPPKEY);
		qqSsoHandler.addToSocialSDK();
		QQShareContent qqShareContent = new QQShareContent();
		// 设置分享文字
		qqShareContent.setShareContent(content);
		// 设置分享title
		qqShareContent.setTitle(title);
		// 设置分享图片
		qqShareContent.setShareImage(new UMImage(this, imageurl));
		// 设置点击分享内容的跳转链接
		qqShareContent.setTargetUrl(clickUrl);
		mController.setShareMedia(qqShareContent);
		//--------------QQ空间----------------------------------------------------------------------------------------------
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.（自己申请）
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,QQAppId, QQAPPKEY);
		qZoneSsoHandler.addToSocialSDK();
		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent(content);
		// 设置点击消息的跳转URL
		qzone.setTargetUrl(clickUrl);
		// 设置分享内容的标题
		qzone.setTitle(title);
		// 设置分享图片
		qzone.setShareImage(new UMImage(this, imageurl));
		mController.setShareMedia(qzone);
		//--------------腾讯微博----------------------------------------------------------------------------------------------
		// 设置腾讯微博SSO handler
		//mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		//--------------新浪----------------------------------------------------------------------------------------------
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//--------------微信好友----------------------------------------------------------------------------------------------
		// 添加微信的appID appSecret要自己申请
		String appID = BuildConfig.WEIXIN_APPID;
		String appSecret = BuildConfig.WEIXIN_SECRET;
		if(!TextUtils.isEmpty(appID)){
			// 添加微信平台
			UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
			wxHandler.showCompressToast(false);
			wxHandler.addToSocialSDK();
			// 设置微信好友分享内容
			WeiXinShareContent weixinContent = new WeiXinShareContent();
			// 设置分享文字
			weixinContent.setShareContent(content);
			// 设置title
			weixinContent.setTitle(title);
			// 设置分享内容跳转URL
			weixinContent.setTargetUrl(clickUrl);
			// 设置分享图片
			weixinContent.setShareImage(new UMImage(this.getApplicationContext(),imageurl));
			mController.setShareMedia(weixinContent);
			//--------------微信朋友圈----------------------------------------------------------------------------------------------
			// 添加微信朋友圈(自会显示title，不会显示内容，官网这样说的)
			UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
			wxCircleHandler.showCompressToast(false);
			// 设置微信朋友圈分享内容
			CircleShareContent circleMedia = new CircleShareContent();
			circleMedia.setShareContent(content);
			// 设置朋友圈title
			circleMedia.setTitle(title);
			circleMedia.setShareImage(new UMImage(this.getApplicationContext(),imageurl));
			circleMedia.setTargetUrl(clickUrl);
			mController.setShareMedia(circleMedia);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			//--------------微信----------------------------------------------------------------------------------------------
		}

		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();
		//--------------邮件------------------------------------------------------------------------------
		EmailHandler emailHandler = new EmailHandler();
		emailHandler.addToSocialSDK();
		return mController;
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
	/**
	 *
	 * @param context
	 * @param Platform 平台
	 */
	//private void share_Platform(final Context context,SHARE_MEDIA Platform,String title,String content,String imageUrl,String clickUrl) {
	public void share_Platform(final Context context,SHARE_MEDIA Platform) {
		showONE = true;
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(context, Platform,new SnsPostListener() {
			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
				// TODO Auto-generated method stub
				if (eCode == 200) {
					//Toast.makeText(context, "分享成功.",Toast.LENGTH_SHORT).show();
				} else {
					String eMsg = "";
					if (eCode == -101) {
						eMsg = "没有授权";
					}
					//Toast.makeText(context,"分享失败[" + eCode + "] " + eMsg,Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(showONE){
					Toast.makeText(context, "开始分享.", Toast.LENGTH_SHORT).show();
					showONE =false;
				}

			}

		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyFlg.all_activitys.remove(this);
	}
}
