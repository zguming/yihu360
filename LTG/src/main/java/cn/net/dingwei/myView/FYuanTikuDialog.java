package cn.net.dingwei.myView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.net.tmjy.mtiku.futures.R;

public class FYuanTikuDialog extends AlertDialog {

	private TextView text_hint;
	private String hint;
	public FYuanTikuDialog(Context context, int theme,String hint) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.hint = hint;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		LinearLayout myload_wait = (LinearLayout)findViewById(R.id.myload_wait);
		myload_wait.getBackground().setAlpha(179);
		text_hint=(TextView)findViewById(R.id.text_hint);
		text_hint.setText(hint);
	  /*  this.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			  public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
				// TODO Auto-generated method stub
				 if (keyCode == KeyEvent.KEYCODE_BACK)
				    {
				     return true;
				    }
				    else
				    {
				     return false; //默认返回 false
				    }
			}
	    	
	    });*/
	}
	public void setHint(String hint){
		text_hint.setText(hint);
	}
}
