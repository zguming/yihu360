package cn.net.dingwei.AsyncUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import cn.net.dingwei.myView.TasksCompletedView_jindu;
import cn.net.dingwei.util.MyFlg;

public class LoadImage_progerss extends AsyncTask<String, Integer, Boolean> {
	private ImageView imageView;
	private String imagePath;
	private Boolean IsShow=true; //true 为显示  false不显示
	private String imageUrl;
	private TasksCompletedView_jindu jindu;
	public LoadImage_progerss(ImageView imageView,String imageUrl,TasksCompletedView_jindu jindu,Context context) {
		// TODO Auto-generated constructor stub
		this.imageView = imageView;
		this.imageUrl =imageUrl;
		imagePath = MyFlg.setImageFileName(imageUrl,context);
		this.jindu = jindu;
	}
	public LoadImage_progerss(String imageUrl,Context context) {
		// TODO Auto-generated constructor stub
		IsShow = false;
		imagePath = MyFlg.setImageFileName(imageUrl,context);

	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		jindu.setProgress(values[0]);
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
				URL url = new URL(imageUrl);
				//URL url = new URL("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg15.3lian.com%2F2015%2Ff2%2F50%2Fd%2F71.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1302166611%2C3152269736%26fm%3D21%26gp%3D0.jpg");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream inputStream = connection.getInputStream();
				long length =connection.getContentLength();

				OutputStream fos = new FileOutputStream(imagePath);
				byte[] buffer = new byte[1024];
				int len = 0;
				int total_length=0;
				while ((len = inputStream.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total_length += len;
					int value=(int)((total_length / (float) length) * 100);
					// publishProgress(values)
					publishProgress(value);
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
			imageView.setImageBitmap(bitmap);
			jindu.setVisibility(View.GONE);
		}
	}
}
