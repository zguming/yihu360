package cn.net.dingwei.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.bugly.beta.Beta;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.net.dingwei.Bean.BaseInfo_guide_categoryBean;
import cn.net.dingwei.Bean.CommonInfo_API_functions;
import cn.net.dingwei.Bean.Get_user_baseinfo;
import cn.net.dingwei.Bean.ImageBean;
import cn.net.dingwei.Bean.UserinfoBean;
import cn.net.dingwei.sup.Sup;
import cn.net.tmjy.mtiku.futures.BuildConfig;

public class MyApplication extends MultiDexApplication {
	public static Boolean isShowVip=BuildConfig.isShowVip;//是否显示VIP(练习和测验 用于是否显示解析 false 不需要VIP也能看 true 需要VIP才能看)
	public CommonInfo_API_functions commonInfo_API_functions;  //API接口
	//public String api_rootpath="";
	public  String  intropage_templates="0";
	/**
	 * 子View标题
	 */
	public  String title;

	public  String match_url="";
	public  String guide_listview_loadurl="";
	public  String choose_pay=""; //微信支付（密卷）
	public  String recharge=""; //充值
	public  String buys=""; //余额购买
	//图标类
	public  ImageBean imageBean;
	public static  Get_user_baseinfo get_user_baseinfo;
	//public  String ImageDownLoadPath="";
	public  int home_leftMenu_index=0;
	//一些常用的对象
	public static UserinfoBean userInfoBean;
	public  BaseInfo_guide_categoryBean guideBean;

	public Boolean isLoadTesting=false; //点击左侧listView是否更新测验数据


	public DisplayImageOptions options;  //图片加载
	File cacheDir;

	private SharedPreferences sharedPreferences;
	//视频
	private static final String TAG = "123";
	//aeskey,iv 值修改请参考https://github.com/easefun/polyv-android-sdk-demo/wiki/10.%E5%85%B3%E4%BA%8E-SDK%E5%8A%A0%E5%AF%86%E4%B8%B2-%E4%B8%8E-%E7%94%A8%E6%88%B7%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E5%8A%A0%E5%AF%86%E4%BC%A0%E8%BE%93
	private String aeskey = "VXtlHmwfS2oYm0CZ";
	private String iv = "2u9gDPKdX6GyQJKU";
	public static MyApplication myApplication;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "类别区分: "+BuildConfig.FLAVOR);
		if ("teacher".equals(BuildConfig.FLAVOR)) {
			MyFlg.apiUrl="http://api.mtiku.net/2.0/";
			MyFlg.Commoninfo_APIurl="http://api.mtiku.net/2.0/get_commoninfo/v6";
		}else if ("yihu360".equals(BuildConfig.FLAVOR)){
			MyFlg.apiUrl = "http://api.botian120.com/2.0/";
			MyFlg.Commoninfo_APIurl="http://api.botian120.com/2.0/get_commoninfo/v6";
		}else if ("xizhang".equals(BuildConfig.FLAVOR)){
			MyFlg.apiUrl="http://api.52zangyu.com/2.0/";
			MyFlg.Commoninfo_APIurl="http://api.52zangyu.com/2.0/get_commoninfo/v6";
		}
		Beta.initDelay = 1 * 1000;//bugly设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
		myApplication = this;
		Sup.init(this);
		Fresco.initialize(this);
		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);     		// 初始化 JPush

		/*UserStrategy strategy = new UserStrategy(getApplicationContext());
		strategy.setAppChannel(getString(R.string.app_name));
		strategy.setAppVersion(getString(R.string.app_name)+"-"+getVersion());      //App的版本
		CrashReport.initCrashReport(this, "900011499", false,strategy);//QQ崩溃集成*/
		//测试崩溃
		//  CrashReport.testJavaCrash();





		String clientcode = getClientcode();
		//  String  mobile_model = android.os.Build.MODEL; //获取手机型号
		String ImageDownLoadPath = setImageDownPath();
		sharedPreferences =this.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("clientcode", clientcode);
		editor.putString("ImageDownLoadPath", ImageDownLoadPath);
		editor.commit();


		cacheDir= new File(ImageDownLoadPath);//缓存路径
		// cacheDir = new File(getSDPath()+"/.cn.net.imageTest/");//缓存路径
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
				// .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)//线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
						/** * LruMemoryCache（这个类就是这个开源框架默认的内存缓存类，缓存的是bitmap的强引用）
						 * 	UsingFreqLimitedMemoryCache（如果缓存的图片总量超过限定值，先删除使用频率最小的bitmap）
						 LRULimitedMemoryCache（这个也是使用的lru算法，和LruMemoryCache不同的是，他缓存的是bitmap的弱引用）
						 FIFOLimitedMemoryCache（先进先出的缓存策略，当超过设定值，先删除最先加入缓存的bitmap）
						 LargestLimitedMemoryCache(当超过缓存限定值，先删除最大的bitmap对象)
						 LimitedAgeMemoryCache（当 bitmap加入缓存中的时间超过我们设定的值，将其删除）
						 WeakMemoryCache（这个类缓存bitmap的总大小没有限制，唯一不足的地方就是不稳定，缓存的图片容易被回收掉）
						 **-------------------------硬盘缓存机制---------------------------------------**
						 FileCountLimitedDiscCache（可以设定缓存图片的个数，当超过设定值，删除掉最先加入到硬盘的文件）
						 LimitedAgeDiscCache（设定文件存活的最长时间，当超过这个值，就删除该文件）
						 TotalSizeLimitedDiscCache（设定缓存bitmap的最大值，当超过这个值，删除最先加入到硬盘的文件）
						 UnlimitedDiscCache（这个缓存类没有任何的限制）
						 */
				.memoryCacheSize(2 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();//开始构建
		// Initialize ImageLoader with configuration.\
		ImageLoader.getInstance().init(config);

		//显示图片的配置
		options = new DisplayImageOptions.Builder()
				//.showImageOnLoading(R.drawable.default_img) //设置图片在下载期间显示的图片
				//.showImageForEmptyUri(R.drawable.chahao)//设置图片Uri为空或是错误的时候显示的图片
				//.showImageOnFail(R.drawable.chahao)  //设置图片加载/解码过程中错误时候显示的图片
				//.cacheInMemory(false)//设置下载的图片是否缓存在内存中
				//.cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中   已过时
				.cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
						// EXACTLY :图像将完全按比例缩小的目标大小
						//EXACTLY_STRETCHED:图片会缩放到目标大小完全
						//IN_SAMPLE_INT:图像将被二次采样的整数倍
						//IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
						//NONE:图片不会调整
				.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
						//.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
						//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
						//设置图片加入缓存前，对bitmap进行设置
						//.preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
						//.displayer(new RoundedBitmapDisplayer(5000))//是否设置为圆角，弧度为多少
						//.displayer(new FadeInBitmapDisplayer(500))//是否图片加载好后渐入的动画时间
				.build();//构建完成

	}
	public SharedPreferences getSharedPreferences(String name){
		SharedPreferences sp = getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp;
	}
	/*public String getApp_key() {
		if(null==app_key){
			app_key=MyFlg.a;
		}
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}*/

	/* public String getApi_rootpath() {
		return api_rootpath;
	}

	public void setApi_rootpath(String api_rootpath) {
		this.api_rootpath = api_rootpath;
	}*/

	/*public CommonInfo_API_functions getCommonInfo_API_functions() {
		return commonInfo_API_functions;
	}

	public void setCommonInfo_API_functions(CommonInfo_API_functions commonInfo_API_functions) {
		this.commonInfo_API_functions = commonInfo_API_functions;
	}*/



	/**
	 * 获取手机唯一标识
	 * @return
	 */
	private String getClientcode(){
		TelephonyManager TelephonyMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String szImei = TelephonyMgr.getDeviceId();
		return szImei;

	}
	public String setImageDownPath(){
		String ImageDownLoadPath="";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sd card 可用

			ImageDownLoadPath = getSDPath()+"/."+getPackageName()+"/";// SD卡存储路径
			File file = new File(ImageDownLoadPath);
			if(!file.exists()){
				file.mkdirs();
			}
		}else {
			//当前不可用
			ImageDownLoadPath = this.getCacheDir().getAbsolutePath().toString()+"/";// 内部存储
			File file = new File(ImageDownLoadPath);
			if(!file.exists()){
				file.mkdirs();
			}
		}
		return ImageDownLoadPath;
	}
	public String getSDPath(){
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
	 　　* 获取版本号
	 　　* @return 当前应用的版本号
	 　　*/
	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return "";
	}

	public DisplayImageOptions getOptions() {
		return options;
	}
	public static UserinfoBean getuserInfoBean(Context context){
		if(null==userInfoBean){
			//用户信息
			userInfoBean =APPUtil.getUser_isRegistered(context);
		}
		return userInfoBean;
	}
	public CommonInfo_API_functions getCommonInfo_API_functions(Context context){
		if(null==commonInfo_API_functions){
			SharedPreferences sp = context.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
			commonInfo_API_functions=APPUtil.getAI_functions(context, sp.getString("get_commoninfo", "0"));
		}
		return commonInfo_API_functions;
	}
	public static Get_user_baseinfo Getget_user_baseinfo(Context context){
		if(null==get_user_baseinfo){
			get_user_baseinfo=APPUtil.Get_user_baseinfo(context);
		}
		return get_user_baseinfo;
	}
	public BaseInfo_guide_categoryBean GetguideBean(Context context){
		if(null==guideBean){
			guideBean = APPUtil.getGuide(context);
		}
		return guideBean;
	}

	public String getSharedPreferencesValue_string(Context context,String key){
		if(sharedPreferences==null){
			sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		}
		String st = sharedPreferences.getString(key,"");
		return st;
	};
	public void setSharedPreferencesValue_string(Context context,String key,String value){
		if(sharedPreferences==null){
			sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putString(key,value).commit();
	};
	public int getSharedPreferencesValue_int(Context context,String key){
		if(sharedPreferences==null){
			sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		}
		int st = sharedPreferences.getInt(key,0);
		return st;
	};
	public String gethas_live_video(Context context){
		String has_live_video="0";//0 表示没有 隐藏首页课程按钮
		SharedPreferences sp = context.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		try {
			String get_commoninfo = sp.getString("get_commoninfo", "0");
			has_live_video = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getString("has_live_video");

		} catch (JSONException e) {
			//e.printStackTrace();
			return "0";
		}
		return has_live_video;
	};
	//获取屏幕亮度
	public int getScreenBrightness(Context context){
		if(sharedPreferences==null){
			sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		}
		int screenBrightness=sharedPreferences.getInt("screenBrightness",0);
		if(screenBrightness==0){
			try{
				screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
				sharedPreferences.edit().putInt("screenBrightness",screenBrightness).commit();
			}
			catch (Exception localException){
			}
		}
		return screenBrightness;
	};
	//设置【屏幕亮度
	public void setWindowBrightness(Activity context, int brightness,Boolean isNight){
		Window localWindow = context.getWindow();
		WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
		float f = brightness / 255.0F;
		if(isNight==false){
			localLayoutParams.screenBrightness = -1;
		}else{
			localLayoutParams.screenBrightness = f;
		}
		localWindow.setAttributes(localLayoutParams);
	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
