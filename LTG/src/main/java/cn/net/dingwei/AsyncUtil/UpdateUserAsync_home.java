package cn.net.dingwei.AsyncUtil;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.LoadAPI;

public class UpdateUserAsync_home extends AsyncTask<String, integer, Boolean>{
	private String clientcode;
	private String city;
	private String nickname;
	private String mobile;
	private String exam;
	private String exam_schedule;
	private String subject;
	LoadAPI loadAPICommoninfo;
	private Handler handler;
	private int index;
	private String password;
	private String mobile_verificationcode;
	private Context context;
	private FYuanTikuDialog dialog;
	private String oldpassword;
	private String newpassword;
	private String bool="";
	private String headurl="";
	public UpdateUserAsync_home(FYuanTikuDialog dialog,Handler handler,Context context,String clientcode,String city,String nickname,String mobile,String exam,String exam_schedule,String subject,String password,String mobile_verificationcode,int index,String oldpassword,String newpassword,String bool,String headurl) {
		// TODO Auto-generated constructor stub
		this.clientcode = clientcode;
		this.city = city;
		this.nickname =nickname;
		this.mobile = mobile;
		this.exam = exam;
		this.exam_schedule = exam_schedule;
		this.subject = subject;
		this.handler= handler;
		this.index = index;
		this.password = password;
		this.mobile_verificationcode = mobile_verificationcode;
		loadAPICommoninfo = new LoadAPI(context);
		this.context = context;
		this.dialog = dialog;
		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
		if(null !=dialog ){
			dialog.show();
		}
		this.bool = bool;
		this.headurl = headurl;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		Boolean boolean1 = loadAPICommoninfo.update_userinfo(clientcode, city, nickname, mobile, exam, exam_schedule, subject,password,mobile_verificationcode,oldpassword,newpassword,null,bool,headurl);
		return boolean1;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		/*if(null !=dialog){
			dialog.dismiss();
		}*/
		if(result==false){
			Toast.makeText(context, "加载失败  请检查网络设置!", 0).show();
			handler.sendEmptyMessageDelayed(99, 200);
			return;
		}
		handler.sendEmptyMessageDelayed(index, 200);
	}
}
