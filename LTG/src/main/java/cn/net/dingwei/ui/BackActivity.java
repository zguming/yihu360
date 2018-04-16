package cn.net.dingwei.ui;

import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

public class BackActivity extends Activity{
	public MyApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		application = MyApplication.myApplication;
		SharedPreferences sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Boolean isNight = sharedPreferences.getBoolean("isNight",false);
		if(isNight==false){
			application.setWindowBrightness(this,application.getScreenBrightness(this),isNight);
		}else{
			application.setWindowBrightness(this,(int) (application.getScreenBrightness(this)*MyFlg.brightnessBiLi),isNight);
		}
	}
	private void setWindowBrightness(float brightness){
		//b是一个浮点数 从0~1 ，表示亮度
		WindowManager.LayoutParams lp =getWindow().getAttributes();
		lp.screenBrightness=brightness;
		getWindow().setAttributes(lp);

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LinearLayout linear_Left=(LinearLayout)findViewById(R.id.linear_Left);
		backClick back = new backClick();
		if(null!=linear_Left){
			linear_Left.setOnClickListener(back);
		}
		LinearLayout layoutLeft=(LinearLayout)findViewById(R.id.layoutLeft);
		if(null!=layoutLeft){
			layoutLeft.setOnClickListener(back);
		}
	}
	class backClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			thisFinish();
		}
	}
	/**
	 * 关闭当前Activity
	 */
	public  void thisFinish(){
		finish();
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyFlg.all_activitys.remove(this);
	}
	//点击屏幕 关闭软键盘
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
}
