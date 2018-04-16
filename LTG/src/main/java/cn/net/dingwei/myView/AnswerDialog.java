package cn.net.dingwei.myView;



import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class AnswerDialog {
	private Context mContext;
	private View view;
	private LayoutInflater layoutInflater;
	private Dialog loadingDialog;
	private TextView text1,text2,text3,text4;
	private String tx1,tx2,tx3;
	private ImageView chahao;
	private LinearLayout linear_image_parents;
	private List<ImageView> list_imageviews;
	private MyApplication application;
	private SharedPreferences sharedPreferences;
	private String Kaopin;
	public AnswerDialog(Context context) {
		this.mContext = context;
		application = MyApplication.myApplication;
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Kaopin=sharedPreferences.getString("Kaopin", "");
		this.layoutInflater = LayoutInflater.from(context);
		onCreateView();

	}

	public void onCreateView() {
		view = layoutInflater.inflate(R.layout.answer_dialog, null);
		text1 = (TextView)view.findViewById(R.id.text1);
		text2 = (TextView)view.findViewById(R.id.text2);
		text3 = (TextView)view.findViewById(R.id.text3);
		linear_image_parents = (LinearLayout)view.findViewById(R.id.linear_image_parents);
		list_imageviews = new ArrayList<ImageView>();
		for (int i = 0; i < 5; i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setImageBitmap(BitmapFactory.decodeFile(Kaopin));
			imageView.setVisibility(View.INVISIBLE);
			linear_image_parents.addView(imageView);
			list_imageviews.add(imageView);
		}
		chahao = (ImageView)view.findViewById(R.id.chahao);
		chahao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadingDialog.dismiss();
			}
		});
		// 创建自定义样式
		loadingDialog = new Dialog(mContext, R.style.loading_dialog);
		loadingDialog.setContentView(view);
		Window window = loadingDialog.getWindow();
		window.setWindowAnimations(R.style.PopupAnimation);

	}
	public void initUI(String tx1,String tx2,String tx3,int sum){
		text1.setText(tx1);
		text2.setText(tx2);
		text3.setText(tx3);
		for (int i = 0; i < list_imageviews.size(); i++) {
			if(i<sum){
				list_imageviews.get(i).setVisibility(View.VISIBLE);
			}else{
				list_imageviews.get(i).setVisibility(View.INVISIBLE);
			}

		}
	}

	public void showDialog() {
		loadingDialog.show();
	}
	public void dismissDialog() {
		loadingDialog.dismiss();

	}


}
