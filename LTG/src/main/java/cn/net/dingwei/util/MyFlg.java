package cn.net.dingwei.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.net.dingwei.Bean.UserinfoBean;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.ui.PayVIPActivity;
import cn.net.tmjy.mtiku.futures.BuildConfig;

/**
 * 继承此类Application 能保证只要程序不被干掉  这里面的值就一直存在
 * @author Administrator
 *
 */
public class MyFlg extends Application{
	private static Context context;
	public static Boolean isGet_commoninfo = false; //是否加载接口完成
	public static Boolean isGet_userinfo=false;
	public static Boolean isLoadIcons=false;
	public static Boolean isClickButton; //表示是否准许进入主页
	//public static String ImageDownLoadPath="";
	public static String baidu_city=""; //百度定位城市
	public static List<Activity> listActivity=new ArrayList<Activity>();
	public static Boolean isHomeActivity=false; //是否已经启动主页
	public static Boolean isRegistered=false; //是否已经启动注册页
	public static Boolean isClickListView=false; //是否已经启动注册页
	public static Boolean isExit=false; //是否退出程序的标识 false 不可退  true 可以退
	/**
	 * API接口加载是否成功
	 */
	public static Boolean isLoadApi;
	//命题库api
	public static String apiUrl="http://api.mtiku.net/2.0/";
	public static String Commoninfo_APIurl="http://api.mtiku.net/2.0/get_commoninfo/v6";
	//医护360api
//	public static String apiUrl="http://api.botian120.com/2.0/";
//	public static String Commoninfo_APIurl="http://api.botian120.com/2.0/get_commoninfo/v6";
	public static String TestAPIurl="http://dingwei.ensys.cn/dingwei_api_tester/api_list/api_response?key=tmjy&fn=";

	//记录当前菜单显示的位置
	//public static int home_leftMenu_index=0;

	/**
	 * 当改变了科目的时候 重新加载练习数据
	 */
	public static Boolean isLoadData=false;
	public static Boolean isClickLianxi=false;
	/**
	 * 判断当前用户是否点击了科目  用来区别更新指南
	 */
	public  static int clickSum=0;
	/**
	 * 是否更新主页的内容     Person_dataActivity 用
	 */
	public static Boolean ISupdateHome = false;
	public static Boolean ISupdateHome_listview = false; //刷新列表数据
	public static Boolean ISupdateguide = false;
	public static Boolean ISupdateTesting = false;

	/**
	 * 验证码输入错误
	 */
	public static String errorCode="";
	/**
	 * 如果用户做了题  那么刷新首页的ViewPager
	 */
	public static Boolean ISupdateHome_viewpager = false;
	/**
	 * 用户付款之后  个人也要刷新数据
	 */
	public static Boolean ISupdatePerson = false;
	/**
	 * 内部版本号
	 */
	public static String android_version=BuildConfig.MYAPP_VERSION;
	//public static String a="futures";
	public static String a= BuildConfig.MYAPP_KEY;

	private static MyApplication application;

	public static List<Activity> all_activitys=new ArrayList<Activity>();
	public static String action= "WXPayCallBack";
	public static float brightnessBiLi=0.4f;
	public MyFlg(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		application = MyApplication.myApplication;
		isHomeActivity=false;
		isRegistered=false;
		isClickButton=false;
		isGet_commoninfo=false;
		isGet_userinfo=false;
		isLoadApi=true;
		isExit = false;
	}

	public static List<Activity> getListActivity() {
		return listActivity;
	}

	public static void setListActivity(List<Activity> listActivity) {
		MyFlg.listActivity = listActivity;
	}
	/**
	 * 判断是否有网络
	 * @return
	 */
	public static boolean isOpenNetwork() {
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}
	/**
	 * 设置圆角按钮
	 */
	public static  Drawable setRaduis(int Bgcolor_1){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(Bgcolor_1);
		gd.setCornerRadius(10);
		gd.setStroke(1, Bgcolor_1);
		return gd;
	}
	/**
	 * 设置圆角按钮
	 */
	public static  Drawable setViewRaduis(int bgColor,int strokeColor,int strokeWidth,int Radius){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(bgColor);
		gd.setCornerRadius(Radius);
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}
	/**
	 * 设置圆角按钮和手指触碰效果
	 */
	public static  Drawable setViewRaduisAndTouch(int bgColor,int touchColor,int strokeColor,int strokeWidth,int Radius){
		GradientDrawable gd_default = new GradientDrawable();//创建drawable
		gd_default.setColor(bgColor);
		gd_default.setCornerRadius(Radius);
		gd_default.setStroke(strokeWidth, strokeColor);

		GradientDrawable gd_touch = new GradientDrawable();//创建drawable
		gd_touch.setColor(touchColor);
		gd_touch.setCornerRadius(Radius);
		gd_touch.setStroke(strokeWidth, strokeColor);

		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_pressed}, gd_touch);
		drawable.addState(new int[]{}, gd_default);
		return drawable;
	}
	/**
	 * 设置圆角按钮和手指触碰效果
	 */
	public static  Drawable setViewRaduisAndTouch(int bgColor,int touchColor,int strokeColor,int strokeColor_touch,int strokeWidth,int Radius){
		GradientDrawable gd_default = new GradientDrawable();//创建drawable
		gd_default.setColor(bgColor);
		gd_default.setCornerRadius(Radius);
		gd_default.setStroke(strokeWidth, strokeColor);

		GradientDrawable gd_touch = new GradientDrawable();//创建drawable
		gd_touch.setColor(touchColor);
		gd_touch.setCornerRadius(Radius);
		gd_touch.setStroke(strokeWidth, strokeColor_touch);

		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_pressed}, gd_touch);
		drawable.addState(new int[]{}, gd_default);
		return drawable;
	}
	/**
	 * 设置圆角按钮  一半圆角一半不要之类 根据Float数组决定
	 * 例如： // float[] radii = new float[] { 2f, 5f, 10f, 20f, 50f, 60f, 70f, 80 };
	 */
	public static  Drawable setViewRaduis_other(int bgColor,int strokeColor,int strokeWidth,float [] radii){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(bgColor);
		gd.setCornerRadii(radii);
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}
	/**
	 * 设置指南消息提示
	 * @param textview
	 */
	public static  void SetGuide_msg(TextView textview,UserinfoBean userInfoBean){
		if(null!=userInfoBean&&
				userInfoBean.getNew_msg()>0){
			textview.setText(userInfoBean.getNew_msg()+"");
			textview.setVisibility(View.VISIBLE);
		}else{
			textview.setVisibility(View.GONE);
		}
	}
	public static String setImageFileName(String FileNmae,Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		FileNmae = FileNmae.replace("/", "");
		FileNmae = FileNmae.replace(":", "");
		FileNmae = FileNmae.replace(".", "");
		FileNmae = FileNmae.replace("?", "");
		FileNmae = FileNmae.replace("-", "");

		String imagePath = sharedPreferences.getString("ImageDownLoadPath", setImageDownPath(context))+FileNmae+".jpg";
		return imagePath;
	}
	public static String setImageDownPath(Context context){
		String ImageDownLoadPath="";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sd card 可用
			ImageDownLoadPath = getSDPath()+"/.tmjy.mtiku/";// SD卡存储路径
			File file = new File(ImageDownLoadPath);
			if(!file.exists()){
				file.mkdirs();
			}
		}else {
			//当前不可用
			ImageDownLoadPath = context.getCacheDir().getAbsolutePath().toString()+"/";// 内部存储
			File file = new File(ImageDownLoadPath);
			if(!file.exists()){
				file.mkdirs();
			}
		}
		return ImageDownLoadPath;
	}
	public static String getSDPath(){
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
		if   (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		}
		return sdDir.toString();

	}
	/**
	 * 2.0接口  需要把对象里面的<rootpath>替换为api_rootpath
	 * @param APIURl
	 * @return
	 */
	public static String get_API_URl(String APIURl,Context context){
		String url = "";
		if(TextUtils.isEmpty(APIURl)){
			APIURl="";
		}
		if(null==context){
			url = APIURl.replace("<rootpath>", apiUrl);
		}else{
			SharedPreferences sharedPreferences = context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
			String Api_rootpath= sharedPreferences.getString("Api_rootpath", apiUrl);
			url = APIURl.replace("<rootpath>", Api_rootpath);
		}

		return url;
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
	/**
	 * 设置极光推送标签
	 */
	public static void setJPushTag(Context context,Set<String> tag){
		//设置标签
		JPushInterface.setAliasAndTags(context, null, tag, mTagsCallback);
	}
	/**
	 * 设置极光推送别名
	 */
	public static void setJPushAlias(Context context,String alias){
		//设置别名
		JPushInterface.setAliasAndTags(context, alias, null, mAliasCallback);
	}
	//极光推送   发布时删除
	private final static TagAliasCallback mAliasCallback = new TagAliasCallback() {
		String TAG = "toby";
		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs ;
			switch (code) {
				case 0:
					logs = "Set Alias  success";
					break;

				case 6002:
					logs = "Failed to set Alias due to timeout. Try again after 60s.";
					break;

				default:
					logs = "Failed with errorCode = " + code;
					// Log.e(TAG, logs);
			}

		}

	};
	private final static TagAliasCallback mTagsCallback = new TagAliasCallback() {
		String TAG = "toby";
		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs ;
			switch (code) {
				case 0:
					logs = "Set  Tags success";
					break;

				case 6002:
					logs = "Failed to Tags due to timeout. Try again after 60s.";
					break;

				default:
					logs = "Failed with errorCode = " + code;
			}
		}

	};
	/**
	 * 判断应用是否启动
	 * @param context
	 * @return
	 */
	public static boolean check(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if(null==am){

		}else{
			List<RunningTaskInfo> list = am.getRunningTasks(100);
			for (RunningTaskInfo info : list) {
				if (info.topActivity.getPackageName()
						.equals(BuildConfig.APPLICATION_ID)
						&& info.baseActivity.getPackageName().equals(
						BuildConfig.APPLICATION_ID)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 *
	 * @param st
	 * @return	true 是整型  false  不是
	 */
	public static boolean isInt(String st) {
		// TODO Auto-generated method stub
		if(null!=st&&!st.equals("null")&&!st.equals("")){
			return true;
		}else{
			return false;
		}

	}
	public static void finshActivitys(){
		for (int i = 0; i < MyFlg.listActivity.size(); i++) {
			if(null==MyFlg.listActivity.get(i)){

			}else{
				MyFlg.listActivity.get(i).finish();
			}
		}

		MyFlg.listActivity.clear();
	}
	/**
	 * 获取记录的手机唯一识别码
	 * @param context
	 * @return
	 */
	public static String getclientcode(Context context){
		if(null==context){
			return "";
		}
		String clientcode = "";
		SharedPreferences sharedPreferences = context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		clientcode = sharedPreferences.getString("clientcode", "");
		if(clientcode.equals("")){
			TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			String szImei = TelephonyMgr.getDeviceId();
			sharedPreferences.edit().putString("clientcode", szImei);
			clientcode=szImei;
		}
		if(isNumeric(clientcode)&&Long.valueOf(clientcode)==0){
			clientcode=getmobile_model()+clientcode;
		}
		return clientcode;
	}
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	/**
	 * 获取手机型号
	 * @param context
	 * @return
	 */
	public static String getmobile_model(){
		return android.os.Build.MODEL;
	}
}
