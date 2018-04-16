package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.AsyncUtil.AsyncLoadApi;
import cn.net.dingwei.Bean.Create_test_suit_2Bean;
import cn.net.dingwei.Bean.Get_test_baseinfoBean;
import cn.net.dingwei.adpater.TestingListViewAdpater;
import cn.net.dingwei.adpater.ViewPagerAdpater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class TestingActivity extends ParentActivity implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private int Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Color_3=0;
    private MyApplication application;
    private TextView title_text;
    private ImageView title_left;
    private ViewPager viewpager;
    //刷新
    private LinearLayout shuaxin_linear;
    private Button shuaxin_button;
    private FYuanTikuDialog dialog;
    private int position;
    private myhandler handler = new myhandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在加载");
        initID();
        initData();
    }

    private void initID() {
        sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
        Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
        Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
        Color_3 = sharedPreferences.getInt("color_3", 0);

        application = MyApplication.myApplication;
        title_text=(TextView)findViewById(R.id.title_text);
        findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
        title_left=(ImageView)findViewById(R.id.title_left);
        title_left.setImageResource(R.drawable.arrow_whrite);

        viewpager=(ViewPager)findViewById(R.id.viewpager);
        //刷新
        shuaxin_linear=(LinearLayout)findViewById(R.id.shuaxin_linear);
        shuaxin_button=(Button)findViewById(R.id.shuaxin_button);
        shuaxin_button.setOnClickListener(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("index",0);
        String title =  intent.getStringExtra("title");
        title_text.setText(title);
    }
    /**
     * 初始化数据
     */
    public void initData() {
        // TODO Auto-generated method stub

       /* Get_test_baseinfoBean bean = APPUtil.Get_test_baseinfo(TestingActivity.this);
        Get_test_baseinfoBean.test_type[] test_type = bean.getTest_type();
        if(position==0){
            title_text.setText(test_type[position].getN());
        }else if(test_type.length==2 && position==1){
            title_text.setText(test_type[position].getN());
        }else if(test_type.length==3 &&position==1){
            title_text.setText(test_type[position].getN());
        }else if(test_type.length==3 &&position==2){
            title_text.setText(test_type[position].getN());
        }*/
        dialog.show();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("a", MyFlg.a));
        params.add(new BasicNameValuePair("ver", MyFlg.android_version));
        params.add(new BasicNameValuePair("clientcode",MyFlg.getclientcode(TestingActivity.this)));
        AsyncLoadApi asyncLoadApi = new AsyncLoadApi(TestingActivity.this, handler, params, "get_test_baseinfo", 0, 1,MyFlg.get_API_URl(application.getCommonInfo_API_functions(TestingActivity.this).getGet_test_baseinfo(),TestingActivity.this));
        asyncLoadApi.execute();
    }
    private void setView(){
        ArrayList<View> list_view = new ArrayList<View>();
        //设置颜色
        Get_test_baseinfoBean bean = APPUtil.Get_test_baseinfo(TestingActivity.this);
        Get_test_baseinfoBean.test_type[] test_type = bean.getTest_type();
        if(position<test_type.length){
            list_view.add(setViewPager(test_type[position]));
        }
        viewpager.setAdapter(new ViewPagerAdpater(list_view));
        viewpager.setVisibility(View.VISIBLE);
    }
    private View setViewPager(final Get_test_baseinfoBean.test_type test_type){
            View view = View.inflate(TestingActivity.this, R.layout.item_testing_view1, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            TextView text1 = (TextView) view.findViewById(R.id.text1);
            TextView text2 = (TextView) view.findViewById(R.id.text2);
            TextView btn = (Button) view.findViewById(R.id.btn);
            text1.setText(test_type.getTitle());
            text1.setTextColor(Bgcolor_2);
            text2.setText(test_type.getDesc());
            text2.setTextColor(Fontcolor_3);
            ImageLoader.getInstance().displayImage(test_type.getImg(), imageView);
            int dip_20 = DensityUtil.DipToPixels(TestingActivity.this, 20);
            btn.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_1, Bgcolor_2, Bgcolor_1, Bgcolor_2, 1, dip_20 / 4));
            btn.setPadding(dip_20, dip_20 / 4, dip_20, dip_20 / 4);
            btn.setText(test_type.getBtn_text());
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    int member_status = MyApplication.getuserInfoBean(TestingActivity.this).getMember_status();
                    if (test_type.getNeedpay() && member_status != 1 && member_status != 2) {
                        showAlertDialogChoose("提示", test_type.getPaymsg(), test_type.getPaybtn_yes(), test_type.getPaybtn_no());
                        return;
                    }
                    dialog.show();
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("a", MyFlg.a));
                    params.add(new BasicNameValuePair("ver", MyFlg.android_version));
                    params.add(new BasicNameValuePair("clientcode", MyFlg.getclientcode(TestingActivity.this)));
                    params.add(new BasicNameValuePair("test_type", test_type.getKey()));
                    params.add(new BasicNameValuePair("test_option", ""));
                    AsyncLoadApi asyncLoadApi = new AsyncLoadApi(TestingActivity.this, handler, params, "create_test_suit", 2, 3, "创建测验失败", MyFlg.get_API_URl(application.getCommonInfo_API_functions(TestingActivity.this).getCreate_test_suit(), TestingActivity.this));
                    asyncLoadApi.execute();
                }
            });
        return  view;
    }
    class myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(null==TestingActivity.this){
            }else{
                super.handleMessage(msg);
                Intent intent;
                Bundle bundle;
                switch (msg.what) {
                    case 0: //加载成功
                        MyFlg.ISupdateTesting=false;
                        application.isLoadTesting = false;
                        dialog.dismiss();
                        shuaxin_linear.setVisibility(View.GONE);
                        setView();
                        break;
                    case 1://加载失败
                        dialog.dismiss();
                        shuaxin_linear.setVisibility(View.VISIBLE);
                        break;
                    case 2://成功 获取了真题
                        Create_test_suit_2Bean create_test_suit_2Bean =APPUtil.create_test_suit_2Bean(TestingActivity.this);
                        intent = new Intent(TestingActivity.this, Reading_testingActivity.class);
                        bundle = new Bundle();
                        bundle.putSerializable("bean", create_test_suit_2Bean);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        TestingActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        dialog.dismiss();
                        break;
                    case 3://失败
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void showAlertDialogChoose(String title, String content,String leftBtnText, String rightBtnText) {
        F_IOS_Dialog.showAlertDialogChoose(TestingActivity.this, title, content, leftBtnText, rightBtnText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case F_IOS_Dialog.BUTTON1:
                                dialog.dismiss();
                                Intent intent = new Intent(TestingActivity.this, PayVIPActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(intent, 0);
                                break;
                            case F_IOS_Dialog.BUTTON2:
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==0){
            initData();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shuaxin_button:
                initData();
                break;

            default:
                break;
        }
    }
}
