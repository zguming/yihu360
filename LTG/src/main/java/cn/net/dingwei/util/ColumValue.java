package cn.net.dingwei.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits")
public class ColumValue {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public ColumValue(Context context) {
		sp = context.getSharedPreferences("personal", Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	//是否登录
	public boolean getIsFirst() {

		return sp.getBoolean("islihaofirst", true);
	}
	public void setIsFirst(boolean islihaofirst) {
		editor.putBoolean("islihaofirst", islihaofirst);
		editor.commit();
	}


	//手机号码
	public void setPhone(String phone){
		editor.putString("phone",phone);
		editor.commit();
	}
	public String getPhone(){
		return sp.getString("phone","");
	}


	//Token
	public void setToken(String access_token){
		editor.putString("access_token",access_token);
		editor.commit();
	}
	public String getToken(){
		return sp.getString("access_token","");
	}


	//Token过期时间
	public void setTokenLifeTime(int token_life_time){
		editor.putInt("token_life_time",token_life_time);
		editor.commit();
	}
	public int getTokenLifeTime(){
		return sp.getInt("token_life_time",0);
	}


}
