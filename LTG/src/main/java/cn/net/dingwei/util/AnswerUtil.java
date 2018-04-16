package cn.net.dingwei.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.ui.WebViewActivity;
import cn.net.dingwei.ui.WriteNoteAndErrorActivity;

/**
 * 一些答题所要用到的
 * @author Administrator
 *
 */
public class AnswerUtil {
	/**
	 * 加载WebView
	 * @param webView
	 * @param data
	 */
	public static void setWebView(TextView tv,String data,Context context,Handler handler,FYuanTikuDialog dialog) {
		// TODO Auto-generated method stub
		data = data.replace("\n", "<br>");
		tv.setText(Html.fromHtml(data));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		CharSequence text = tv.getText();
		if (text instanceof Spannable) {
			int end = text.length();
			Spannable sp = (Spannable) tv.getText();
			URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
			SpannableStringBuilder style = new SpannableStringBuilder(text);
			style.clearSpans();// should clear old spans
			for (URLSpan url : urls) {
				MyURLSpan myURLSpan = new MyURLSpan(url.getURL(),context,handler,dialog);
				style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			tv.setText(style);
		}
	}
	/**
	 * Textview 的链接点击事件
	 * @author Administrator
	 *
	 */
	public static  class MyURLSpan extends ClickableSpan {
		private String mUrl;
		private Context context;
		private Handler handler;
		private FYuanTikuDialog dialog;
		private MyApplication application;
		private int Bgcolor_2=0;
		MyURLSpan(String url,Context context,Handler handler,FYuanTikuDialog dialog) {
			mUrl = url;
			this.context = context;
			this.handler= handler;
			application = MyApplication.myApplication;
			SharedPreferences  sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
			Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
		}
		@Override
		public void onClick(View widget) {
			mUrl =mUrl.trim();
			String point = mUrl.substring(mUrl.indexOf("/point/")+7, mUrl.length());
			if(isNumeric(point)){
				dialog.show();
				List <NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("a",MyFlg.a));
				params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(context)));
				params.add(new BasicNameValuePair("point_id",point));
				params.add(new BasicNameValuePair("ver",MyFlg.android_version));
				AsyncLoadApi asyncLoadApi = new AsyncLoadApi(context, handler, params, "get_point_info",9,10,"获取考点信息失败",MyFlg.get_API_URl(application.getCommonInfo_API_functions(context).getGet_point_info(),context));
				asyncLoadApi.execute();
			}

		}
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Bgcolor_2);
			ds.setUnderlineText(false);    //去除超链接的下划线
		}
	}
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	/**
	 * 头像点击事件
	 * @author Administrator
	 *
	 */
	public static class HeadClick implements OnClickListener{
		private String url;
		private Context context;
		public HeadClick(String url,Context context) {
			// TODO Auto-generated constructor stub
			this.url = url;
			this.context =context;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, WebViewActivity.class);
			intent.putExtra("url", url);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(intent);
		}

	}
}
