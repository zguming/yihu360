package cn.net.dingwei.myView;



import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.ui.LogActivity;
import cn.net.dingwei.ui.WebViewActivity;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Home_Score_Dialog implements OnClickListener{
	private MyApplication application;
	private Context mContext;
	private LayoutInflater layoutInflater;
	private Dialog loadingDialog;
	private TextView score_title;
	private Button score_btn1,score_btn2,score_btn3;
	private LinearLayout score_linear;
	private SharedPreferences sharedPreferences;
	private int Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0;
	private int Screen_width=0;

	private SharedPreferences sp_commoninfo;
	private Placeholder_textBean placeholder_textBean;
	public Home_Score_Dialog(Context context) {
		this.mContext = context;
		application = MyApplication.myApplication;
		this.layoutInflater = LayoutInflater.from(context);
		sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
		Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
		Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		Color_3 = sharedPreferences.getInt("color_3", 0);
		Screen_width=sharedPreferences.getInt("Screen_width", 0);

		sp_commoninfo= mContext.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		Gson gson = new Gson();
		try {
			placeholder_textBean = gson.fromJson(new JSONObject(sp_commoninfo.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onCreateView();
	}

	public void onCreateView() {
		View view = layoutInflater.inflate(R.layout.home_score_dialog, null);
		score_linear = (LinearLayout)view.findViewById(R.id.score_linear);
		LayoutParams params = new LayoutParams(Screen_width*3/4, LayoutParams.WRAP_CONTENT);
		score_linear.setLayoutParams(params);
		score_title = (TextView)view.findViewById(R.id.score_title);
		score_btn1 = (Button)view.findViewById(R.id.score_btn1);
		score_btn2 = (Button)view.findViewById(R.id.score_btn2);
		score_btn3 = (Button)view.findViewById(R.id.score_btn3);

		score_btn1.setVisibility(View.GONE);
		//事件
		score_btn1.setOnClickListener(this);
		score_btn2.setOnClickListener(this);
		score_btn3.setOnClickListener(this);

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
		score_title.setTextColor(Fontcolor_3);
		score_linear.setBackgroundDrawable(MyFlg.setViewRaduis(mContext.getResources().getColor(R.color.lianxi_bg), Color_3, 1, 10));

		score_btn1.setTextColor(Color.WHITE);
		score_btn2.setTextColor(Color.WHITE);
		score_btn3.setTextColor(Bgcolor_2);

		//score_btn1.setBackground(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Color_3, 1, 10));
		score_btn2.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Bgcolor_1, Color_3, 1, 10));
		//score_btn2.setBackground(MyFlg.setViewRaduisAndTouch(Color.WHITE, Bgcolor_1, Color_3, 1, 10));
		score_btn3.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Bgcolor_1, Color_3, 1, 10));
		if(null!=placeholder_textBean){
			//设置文字
			score_title.setText(placeholder_textBean.getPfk_bt());
			score_btn1.setText(placeholder_textBean.getPfk_mshp());
			score_btn2.setText(placeholder_textBean.getPfk_qtyj());
			score_btn3.setText(placeholder_textBean.getPfk_yhzs());
		}


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
			case R.id.score_btn1://评分
				intent = new Intent(mContext, WebViewActivity.class);
				intent.putExtra("url", "http://www.mumayi.com/android-1027932.html");
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				mContext.startActivity(intent);
				dismissDialog();
				break;
			case R.id.score_btn2://意见反馈
				SharedPreferences sharedPreferences=mContext.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
				String feedback = sharedPreferences.getString("feedback", "");
				intent = new Intent(mContext, WebViewActivity.class);
				intent.putExtra("url", feedback+"?uid="+MyApplication.Getget_user_baseinfo(mContext).getUid());
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				mContext.startActivity(intent);
				dismissDialog();
				break;
			case R.id.score_btn3://取消
				dismissDialog();
				break;
			default:
				break;
		}
	}


}
