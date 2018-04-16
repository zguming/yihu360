package cn.net.dingwei.AsyncUtil;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.LoadAPI;
import cn.net.dingwei.util.MyFlg;

public class UpdateUserAsync extends AsyncTask<String, integer, Boolean>{
	private String clientcode;
	private String city;
	private String nickname;
	private String mobile;
	private String exam;
	private String exam_schedule;
	private String subject;
	LoadAPI loadAPICommoninfo;
	private Boolean isget_baseinfo = false;
	private Handler handler;
	private int index;
	private String password;
	private String mobile_verificationcode;
	private Context context;
	private FYuanTikuDialog dialog;
	private String oldpassword;
	private String newpassword;
	private String update_city;
	private Boolean dialogNeeddismiss=true;
	private String bool="";
	private String headurl="";

	public UpdateUserAsync(FYuanTikuDialog dialog,Handler handler,Context context,String clientcode,String city,String nickname,String mobile,String exam,String exam_schedule,String subject,String password,String mobile_verificationcode,Boolean isget_baseinfo,int index,String oldpassword,String newpassword,String update_city,String bool,String headurl) {
		// TODO Auto-generated constructor stub
		this.clientcode = clientcode;
		this.city = city;
		this.nickname =nickname;
		this.mobile = mobile;
		this.exam = exam;
		this.exam_schedule = exam_schedule;
		this.subject = subject;
		this.isget_baseinfo = isget_baseinfo;
		this.handler= handler;
		this.index = index;
		this.password = password;
		this.mobile_verificationcode = mobile_verificationcode;
		loadAPICommoninfo = new LoadAPI(context);
		this.context = context;
		this.dialog =dialog;
		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
		this.update_city = update_city;
		this.bool = bool;
		this.headurl = headurl;
	}
	public UpdateUserAsync(FYuanTikuDialog dialog,Handler handler,Context context,String clientcode,String city,String nickname,String mobile,String exam,String exam_schedule,String subject,String password,String mobile_verificationcode,Boolean isget_baseinfo,int index,String oldpassword,String newpassword,String update_city,Boolean dialogNeeddismiss,String bool,String headurl) {
		// TODO Auto-generated constructor stub
		this.clientcode = clientcode;
		this.city = city;
		this.nickname =nickname;
		this.mobile = mobile;
		this.exam = exam;
		this.exam_schedule = exam_schedule;
		this.subject = subject;
		this.isget_baseinfo = isget_baseinfo;
		this.handler= handler;
		this.index = index;
		this.password = password;
		this.mobile_verificationcode = mobile_verificationcode;
		loadAPICommoninfo = new LoadAPI(context);
		this.context = context;
		this.dialog =dialog;
		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
		this.update_city = update_city;
		this.dialogNeeddismiss = dialogNeeddismiss;
		this.bool = bool;
		this.headurl = headurl;
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		Boolean boolean1 = loadAPICommoninfo.update_userinfo(clientcode, city, nickname, mobile, exam, exam_schedule, subject,password,mobile_verificationcode,oldpassword,newpassword,update_city,bool,headurl);
		if(isget_baseinfo){
			if(null!=city){
				return loadAPICommoninfo.getBaseinfo(city);
			}else if(null!=update_city){
				return loadAPICommoninfo.getBaseinfo(update_city);
			}

		}
		return boolean1;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(dialogNeeddismiss){
			if(null!=dialog){
				dialog.dismiss();
			}
		}
		if(result==false){
			dialog.dismiss();
			handler.sendEmptyMessage(999);
			if(null!=mobile_verificationcode){

			}else{

				if(null==oldpassword){ //如果密码是Null 表明不是进行密码修改
					Toast.makeText(context, "加载失败!", 0).show();
				}else{
					Toast.makeText(context, "修改密码失败!", 0).show();
				}
			}
			return;
		}

		if(!MyFlg.errorCode.equals("")){
			Toast.makeText(context, MyFlg.errorCode, 0).show();
			MyFlg.errorCode="";
			//handler.sendEmptyMessage(9);
			return;
		}

		Message message = new Message();
		switch (index) {
			case 1:
				message.what=1;
				break;
			case 2:
				message.what=2;
				break;
			case 3:
				message.what=3;
				break;
			case 5: //设置了昵称 点击下一步
				message.what=5;
				break;
			case 6: //输入手机验证码
				message.what=6;
				break;
			case 99: //头像上传成功
				message.what=99;
				break;
			case 1000: //完善资料OK
				message.what=1000;
				break;
			default:
				break;
		}

		handler.sendMessage(message);
	}
}
