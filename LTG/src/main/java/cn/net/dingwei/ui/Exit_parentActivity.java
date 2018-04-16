package cn.net.dingwei.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import cn.net.dingwei.util.MyFlg;

public class Exit_parentActivity extends Activity{
	private myHandler handler = new myHandler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!MyFlg.isExit) {
			MyFlg.isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			handler.sendEmptyMessageDelayed(0, 2000);
		} else {
			MyFlg.all_activitys.remove(this);
			MyFlg.listActivity.remove(this);
			finish();
			for (int i = 0; i <MyFlg.all_activitys.size(); i++) {
				if(null==MyFlg.all_activitys.get(i)){

				}else{
					MyFlg.all_activitys.get(i).finish();
				}
			}
			MyFlg.all_activitys.clear();
			// System.exit(0);
		}
	}
	class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 0: //判断是否退出程序
					MyFlg.isExit = false;
					break;

				default:
					break;
			}
		}
	}
}
