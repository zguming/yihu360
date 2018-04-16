package cn.net.dingwei.AsyncUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import cn.net.dingwei.Bean.UserinfoBean;
import cn.net.dingwei.ui.HomeActivity2;
import cn.net.dingwei.ui.NewRegisteredActivity;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.MyFlg;

public class LoadIcons_Async extends AsyncTask<String, integer, Boolean> {
	private List<String> imageurl;
	private Activity context;
	private Handler handler;
	public LoadIcons_Async(Activity context,List<String> imageurl,Handler handler) {
		// TODO Auto-generated constructor stub
		this.imageurl = imageurl;
		this.context =context;
		this.handler = handler;
	}

	@Override
	protected Boolean doInBackground(String... params) {// 输入编变长的可变参数
		// 和ＵＩ线程中的Asyna.execute()对应
		// TODO Auto-generated method stub

		for (int i = 0; i < imageurl.size(); i++) {
			if(imageurl.get(i).equals("null")){
				continue;
			}
			String imagePath = MyFlg.setImageFileName(imageurl.get(i),context);
			// 判断图片文件是否存在
			File file = new File(imagePath);
			if (file.exists()) {
				continue;
			} else {
				/*
				 * 该方法在OnpreExecute执行以后马上执行，改方法执行在后台线程当中，负责耗时的计算，
				 * 可以调用publishProcess方法来实时更新任务进度
				 */
				try {
					URL url = new URL(imageurl.get(i));
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setDoInput(true);
					connection.connect();
					InputStream inputStream = connection.getInputStream();

					OutputStream fos = new FileOutputStream(imagePath);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = inputStream.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}

					fos.close();
					inputStream.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return false;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					if (file.exists()) {
						file.delete();
					}
					return false;
				}


			}
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// imageView.setImageBitmap(result);
		if (result == true) {
			MyFlg.isLoadIcons=true;
			if(result==true && MyFlg.isClickButton==true){
				UserinfoBean bean2 =APPUtil.getUser_isRegistered(context);
				if(bean2.getRegistered()==true ||(bean2.getRegistered()==false&&bean2.getBool().equals("1"))){
					//进入主页面
					if(MyFlg.isHomeActivity==false){
						Intent intent = new Intent(context, HomeActivity2.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("isLoadHome", true);
						context.startActivity(intent);
						MyFlg.isHomeActivity=true;
					}
					MyFlg.finshActivitys();//关闭activity
				}else{
					handler.sendEmptyMessage(1000); //返回界面  显示注册 登录 游客3种
				}
			}
		}else{
			handler.sendEmptyMessage(1);
		}
	}

}
