package cn.net.dingwei.myView;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import cn.net.tmjy.mtiku.futures.R;

public class F_IOS_Dialog {
	public static final int BUTTON1 = -1;
	public static final int BUTTON2 = -2;

	public static void showAlertDialogChoose(Context context, String title,
											 String content, String leftBtnText, String rightBtnText,
											 final OnClickListener listener) {
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.common_ui_dialog);

		Button ok = (Button) window.findViewById(R.id.alert_dialog_confirm_btn);
		ok.setVisibility(View.GONE);
		TextView titleTv = (TextView) window
				.findViewById(R.id.alert_dialog_title_tv);
		TextView contentTv = (TextView) window
				.findViewById(R.id.alert_dialog_content_tv);
		Button leftBtn = (Button) window
				.findViewById(R.id.alert_dialog_left_btn);
		Button rightBtn = (Button) window
				.findViewById(R.id.alert_dialog_right_btn);

		leftBtn.setText(leftBtnText);
		rightBtn.setText(rightBtnText);
		titleTv.setText(title);
		contentTv.setText(content);

		leftBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onClick(dlg, BUTTON1);
				}
			}
		});
		rightBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onClick(dlg, BUTTON2);
				}
			}
		});
	}
	/**
	 *
	 * @param context
	 * @param title
	 * @param content
	 * @param leftBtnText
	 * @param rightBtnText
	 * @param listener
	 * @param type 0 隐藏左边按钮
	 */
	public static void showAlertDialog(Context context, String title,
									   String content, String leftBtnText, String rightBtnText,
									   final OnClickListener listener,int type) {
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				return true; //True 为不消失
			}
		});
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.common_ui_dialog);
		window.findViewById(R.id.text_xian).setVisibility(View.GONE);
		Button ok = (Button) window.findViewById(R.id.alert_dialog_confirm_btn);
		ok.setVisibility(View.GONE);
		TextView titleTv = (TextView) window
				.findViewById(R.id.alert_dialog_title_tv);
		TextView contentTv = (TextView) window
				.findViewById(R.id.alert_dialog_content_tv);
		Button leftBtn = (Button) window
				.findViewById(R.id.alert_dialog_left_btn);
		if(type==0){
			leftBtn.setVisibility(View.GONE);
		}
		Button rightBtn = (Button) window
				.findViewById(R.id.alert_dialog_right_btn);

		leftBtn.setText(leftBtnText);
		rightBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.iso_button_click_down));
		rightBtn.setText(rightBtnText);
		titleTv.setText(title);
		contentTv.setText(content);

		leftBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onClick(dlg, BUTTON1);
				}
			}
		});
		rightBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onClick(dlg, BUTTON2);
				}
			}
		});
	}
}
