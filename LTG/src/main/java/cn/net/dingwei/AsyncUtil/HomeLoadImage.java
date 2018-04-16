package cn.net.dingwei.AsyncUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import cn.net.dingwei.myView.TasksCompletedView;
import cn.net.dingwei.util.MyFlg;

public class HomeLoadImage extends AsyncTask<String, integer, Boolean> {
	private TasksCompletedView imageView;
	private String imagePath;
	private Boolean IsShow=true; //true 为显示  false不显示
	private String ImageUrl;
	private Context context;
	public HomeLoadImage(TasksCompletedView View,String imageUrl,Context context) {
		// TODO Auto-generated constructor stub
		this.imageView = View;
		this.ImageUrl = imageUrl;
		imagePath = MyFlg.setImageFileName(imageUrl,context);
		this.context = context;
	}
	public HomeLoadImage(String imageUrl) {
		// TODO Auto-generated constructor stub
		IsShow = false;
		this.ImageUrl = imageUrl;
		imagePath = MyFlg.setImageFileName(imageUrl,context);

	}

	@Override
	protected Boolean doInBackground(String... params) {// 输入编变长的可变参数
		// 和ＵＩ线程中的Asyna.execute()对应
		// TODO Auto-generated method stub

		//判断图片文件是否存在
		File file = new File(imagePath);
		if(file.exists()){
			return true;
		}else{
		/*
		 * 该方法在OnpreExecute执行以后马上执行，改方法执行在后台线程当中，负责耗时的计算，
		 * 可以调用publishProcess方法来实时更新任务进度
		 */
			try {
				URL url = new URL(ImageUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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



			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//imageView.setImageBitmap(result);
		if(result == true && IsShow==true){
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			//imageView.setImageBitmap(bitmap);
			Drawable drawable =  Drawable.createFromPath(imagePath);

			imageView.setOndrawBitmap(drawable);
		}
	}

}
