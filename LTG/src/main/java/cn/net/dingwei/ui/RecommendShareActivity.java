package cn.net.dingwei.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.apache.http.Header;

import cn.net.dingwei.Bean.Get_user_baseinfo;
import cn.net.dingwei.Bean.Share_InfoBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.RecommendShareButtomShareDialog;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.JSONUtil;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class RecommendShareActivity extends ParentActivity implements View.OnClickListener{
    private ImageView imageview_bg;
    private TextView title_text,text_guize,text_number;
    private Button btn_more;
    private LinearLayout linear_weixin1,linear_qq,linear_code,linear_weixin2;
    private ScrollView myScrollView;
    private MyApplication application;
    private RecommendShareButtomShareDialog share_dialog;
    private Get_user_baseinfo.share_info shareBean_info;
    private FYuanTikuDialog dialog;

    public  LinearLayout shuaxin_linear;
    private Button shuaxin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_share);
        application = MyApplication.myApplication;
        dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
        initID();
        getJson();
    }
    private void initData(Share_InfoBean bean){
        shareBean_info= MyApplication.Getget_user_baseinfo(this).getShare_info();
       // text_hint1.setText("赠送朋友"+bean.getShare_money()+"元练题狗余额，当TA首次登陆时");
        text_number.setText("已成功邀请"+bean.getNumber()+"人，累计获得奖励"+bean.getMoney()+"元");
        share(shareBean_info.getShare_title(),shareBean_info.getShare_content(),shareBean_info.getShare_img(),shareBean_info.getShare_link());
        share_dialog =new RecommendShareButtomShareDialog(this,R.style.ShareDialogStyle,mController,shareBean_info.getShare_link_qrcodeimg(),bean.getShare_money());
        ImageLoader.getInstance().displayImage(bean.getShare_img(),imageview_bg,application.getOptions());
    }
    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.title_bg).setBackgroundColor(Color.TRANSPARENT);
    }
    private void initID() {
        // TODO Auto-generated method stub
        title_text=(TextView)findViewById(R.id.title_text);
        findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
        imageview_bg=(ImageView)findViewById(R.id.imageview_bg);//background
        btn_more = (Button) findViewById(R.id.btn_more);
        linear_weixin1 = (LinearLayout) findViewById(R.id.linear_weixin1);
        linear_qq = (LinearLayout) findViewById(R.id.linear_qq);
        linear_code = (LinearLayout) findViewById(R.id.linear_code);
        linear_weixin2 = (LinearLayout) findViewById(R.id.linear_weixin2);
        text_guize=(TextView)findViewById(R.id.text_guize);
        text_number=(TextView)findViewById(R.id.text_number);//邀请人数显示
        myScrollView=(ScrollView) findViewById(R.id.myScrollView);
        ImageView title_left = (ImageView) findViewById(R.id.title_left);
        //刷新
        shuaxin_linear= (LinearLayout) findViewById(R.id.shuaxin_linear);
        shuaxin_button= (Button) findViewById(R.id.shuaxin_button);

        int width =application.getSharedPreferencesValue_int(this,"Screen_width");
       /* int height = (int) ((float) 720 * width / 653);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
        imageview_bg.setLayoutParams(params);*/
        LoadImageViewUtil.resetImageSize(imageview_bg,width,720,653);
        int color = getResources().getColor(R.color.share_textcolor);
        btn_more.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(color,Color.LTGRAY,Color.TRANSPARENT,0,0,10));

        title_text.setText("推荐有奖");
        title_left.setImageResource(R.drawable.arrow_whrite);

        linear_weixin1.setOnClickListener(this);
        linear_qq.setOnClickListener(this);
        linear_code.setOnClickListener(this);
        linear_weixin2.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        text_guize.setOnClickListener(this);
        shuaxin_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_weixin1:
                share_Platform(RecommendShareActivity.this, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.linear_weixin2:
                share_Platform(RecommendShareActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.linear_qq:
                share_Platform(RecommendShareActivity.this, SHARE_MEDIA.QQ);
                break;
            case R.id.linear_code:
                share_dialog.setFlg(1);
                share_dialog.show();
                break;
            case R.id.btn_more:
                //mController.openShare(RecommendShareActivity.this,false);
                share_dialog.setFlg(0);
                share_dialog.show();
                break;
            case R.id.text_guize://规则
                Intent intent = new Intent(RecommendShareActivity.this,WebViewActivity.class);
                intent.putExtra("url",shareBean_info.getRule());
                startActivity(intent);
                break;
            case R.id.shuaxin_button:
                getJson();
                break;
        }
    }
    private void getJson() {
        dialog.show();
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(this).getGet_share_info(),this);
        if(TextUtils.isEmpty(apiUrl)){
            return;
        }
        RequestParams params = new RequestParams();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(RecommendShareActivity.this));
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        client.post(apiUrl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                {
                    dialog.dismiss();
                    String json = Sup.UnZipString(responseBody);
                    Gson gson = new Gson();
                    String result= JSONUtil.isNormalBoolen(RecommendShareActivity.this,json);
                    if(!TextUtils.isEmpty(result)){
                        shuaxin_linear.setVisibility(View.GONE);
                        myScrollView.setVisibility(View.VISIBLE);
                        Share_InfoBean bean = gson.fromJson(result,Share_InfoBean.class);
                        initData(bean);
                    }

                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(RecommendShareActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                shuaxin_linear.setVisibility(View.VISIBLE);
                myScrollView.setVisibility(View.GONE);
            }
        });
    }
}
