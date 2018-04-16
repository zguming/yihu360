package cn.net.dingwei.myView;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinanetcenter.wcs.android.http.AsyncHttpClient;
import com.chinanetcenter.wcs.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import cn.net.dingwei.Bean.Get_user_baseinfo;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.ui.WebViewActivity;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.JSONUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Home_Score_Dialog2 implements OnClickListener{
	private MyApplication application;
	private Context mContext;
	private LayoutInflater layoutInflater;
	private Dialog loadingDialog;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Fontcolor_7=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0;
	private int Screen_width=0;

	private SharedPreferences sp_commoninfo;
	private TextView tx_title,tx_content,tx_cancel,tx_complaints;
	private Button btn_score;
	private ImageView imageView;
	private Get_user_baseinfo.Good_infoBean temp_bean;
	public Home_Score_Dialog2(Context context) {
		this.mContext = context;
		application = MyApplication.myApplication;
		this.layoutInflater = LayoutInflater.from(context);
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Fontcolor_7= sharedPreferences.getInt("fontcolor_7", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);

		sp_commoninfo= mContext.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		onCreateView();
	}

	public void onCreateView() {
		View view = layoutInflater.inflate(R.layout.dialog_home_score, null);
		imageView = (ImageView) view.findViewById(R.id.imageview);
		tx_title = (TextView) view.findViewById(R.id.tx_title);
		tx_content = (TextView)view.findViewById(R.id.tx_content);
		tx_cancel = (TextView)view.findViewById(R.id.tx_cancel);
		tx_complaints =(TextView) view.findViewById(R.id.tx_complaints);
		btn_score =(Button) view.findViewById(R.id.btn_score);

		tx_cancel.setOnClickListener(this);
		tx_complaints.setOnClickListener(this);
		btn_score.setOnClickListener(this);
		initUI();
		// 创建自定义样式
		loadingDialog = new Dialog(mContext, R.style.loading_dialog);
		loadingDialog.setContentView(view);
		loadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
		Window window = loadingDialog.getWindow();
		window.setWindowAnimations(R.style.PopupAnimation);
		loadingDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				return true; //True 为不消失
			}
		});
	}
	public void initUI(){
		temp_bean=MyApplication.Getget_user_baseinfo(mContext).getGood_info();
		tx_title.setText(temp_bean.getGood_title());
		SpannableStringBuilder sp = new SpannableStringBuilder(temp_bean.getGood_content1()+temp_bean.getGood_content2()+temp_bean.getGood_content3());
		sp.setSpan(new TextAppearanceSpan(mContext,R.style.Home_Score_dialog),temp_bean.getGood_content1().length(),(temp_bean.getGood_content1()+temp_bean.getGood_content2()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		tx_content.setText(sp);
		btn_score.setText(temp_bean.getGood_btn1());
		tx_cancel.setText(temp_bean.getGood_btn2());
		tx_complaints.setText(temp_bean.getGood_btn3());
		if(!TextUtils.isEmpty(temp_bean.getGood_img())){
			imageView.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(temp_bean.getGood_img(),imageView,application.getOptions());
		}else{
			imageView.setVisibility(View.GONE);
		}

		tx_title.setTextColor(Fontcolor_3);
		tx_content.setTextColor(Fontcolor_7);
		tx_cancel.setTextColor(Fontcolor_7);
		//tx_complaints.setTextColor(Bgcolor_2);
		btn_score.setTextColor(Color.WHITE);
		int button_bg_color = mContext.getResources().getColor(R.color.home_score_button);
		btn_score.setBackgroundDrawable(MyFlg.setViewRaduis(button_bg_color,button_bg_color,1, DensityUtil.DipToPixels(mContext,25)));
	}

	public void showDialog() {
		loadingDialog.show();
	}
	public void dismissDialog() {
		loadingDialog.dismiss();

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_score://评分
				String url = temp_bean.getGood_link();
				if(TextUtils.isEmpty(url)){
					toScore();
				}else{
					getJson(url);
				}
				dismissDialog();
				break;
			case R.id.tx_complaints://意见反馈
				SharedPreferences sharedPreferences=mContext.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
				String feedback = sharedPreferences.getString("feedback", "");
				intent = new Intent(mContext, WebViewActivity.class);
				intent.putExtra("url", feedback+"?uid="+MyApplication.Getget_user_baseinfo(mContext).getUid());
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				mContext.startActivity(intent);
				dismissDialog();
				break;
			case R.id.tx_cancel://取消
				dismissDialog();
				break;
			default:
				break;
		}
	}
	private void toScore(){
		Uri uri = Uri.parse("market://details?id="+mContext.getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	private void getJson(String url) {
		loadingDialog.show();
		new AsyncHttpClient().post(url,null , new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] responseBody) {
				{
					loadingDialog.dismiss();
					String json = Sup.UnZipString(responseBody);
					Boolean success = JSONUtil.isSuccess(mContext,json);
					if(success){
						toScore();
					}else{
						Toast.makeText(mContext, "数据异常，请稍后再试！", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
				loadingDialog.dismiss();
				toScore();
				 //Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
