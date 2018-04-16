package cn.net.dingwei.util;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * 处理JSON异常的Util
 * @author Andy.li 上午10:33:21
 *
 */
public class JSONUtil {
	/**
	 * 判断JS0N 是否正常
	 * @param context
	 * @param json
	 * @return
	 */
	public static String isNormalBoolen(Context context,String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean status = jsonObject.getBoolean("status");
			String msg =jsonObject.getString("error");
			if(status==false){
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				return "";
			}else{
				return jsonObject.getString("data");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(context, "数据异常", Toast.LENGTH_SHORT).show();
			return "";
		}

	}
	/**
	 * 判断JS0N 是否正常
	 * @param context
	 * @param json
	 * @return
	 */
	public static String isNormalString(Context context,String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			String status = jsonObject.getString("status");
			String msg =jsonObject.getString("info");
			if(status.endsWith("0")){
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				return "";
			}else if(status.equals("1")){
				return jsonObject.getString("data");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(context, "数据异常", Toast.LENGTH_SHORT).show();
			return "";
		}

		return "";
	}
	/**
	 * 状态类JSON  只需要判断成功与否
	 * @param context
	 * @param json
	 * @return
	 */
	public static boolean isSuccess(Context context,String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			Boolean status = jsonObject.getBoolean("status");
			//String msg =jsonObject.getString("msg");
			return status;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(context, "数据异常", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

}
