package cn.net.dingwei.myView;



import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.net.tmjy.mtiku.futures.R;

public class DialogMenu {
	private Context mContext;
	private TextView tv_title, tv_context;
	private Button yes, no;
	private View view;
	private LayoutInflater layoutInflater;
	private Dialog loadingDialog;
	private OnClickListener onClickListener;

	public DialogMenu(Context context,OnClickListener onClickListener) {
		this.mContext = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.onClickListener = onClickListener;
		onCreateView();
	}

	public void onCreateView() {
		view = layoutInflater.inflate(R.layout.dialog_popup, null);
		tv_title = (TextView) view.findViewById(R.id.textView1);
		tv_context = (TextView) view.findViewById(R.id.textView2);
		yes = (Button) view.findViewById(R.id.yes);
		no = (Button) view.findViewById(R.id.no);
		loadingDialog = new Dialog(mContext, R.style.loading_dialog);// 创建自定义样式
		loadingDialog.setContentView(view);
		yes.setOnClickListener(onClickListener);
		no.setOnClickListener(onClickListener);
	}
	public void initUI(String context){
		tv_context.setText(context);
	}
	public void showDialog() {
		loadingDialog.show();
	}
	public void dismissDialog() {
		loadingDialog.dismiss();
	}


}
