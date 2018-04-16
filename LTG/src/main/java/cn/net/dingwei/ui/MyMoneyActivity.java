package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.MyMoneyBean;
import cn.net.dingwei.adpater.MyMoneyAdapter;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.JSONUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import listview_DownPullAndUpLoad.XListView;

/**
 * 我的余额
 */
public class MyMoneyActivity extends BackActivity implements MyMoneyAdapter.Refresh_Back {
    private MyApplication application;
    private SharedPreferences sharedPreferences;
    private int Fontcolor_1 = 0, Fontcolor_3 = 0, Fontcolor_7 = 0,Bgcolor_1 = 0, Bgcolor_2 = 0, Bgcolor_5 = 0, Bgcolor_6 = 0;

    private XListView listview;
    private String last_id;
    private List<MyMoneyBean.lists> lists;
    private MyMoneyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        application = MyApplication.myApplication;
        initColor();
        initid();
        initData();
    }


    private void initColor() {
        // TODO Auto-generated method stub
        sharedPreferences = getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
        Fontcolor_3 = sharedPreferences.getInt("fontcolor_3", 0);
        Fontcolor_7 = sharedPreferences.getInt("fontcolor_7", 0);
        Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
        Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
        Bgcolor_5 = sharedPreferences.getInt("bgcolor_5", 0);
        Bgcolor_6 = sharedPreferences.getInt("bgcolor_6", 0);
    }
    private void initid() {
        findViewById(R.id.title_bg).setBackgroundColor(Bgcolor_1);
        TextView title_text = (TextView) findViewById(R.id.title_text);
        findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
        ImageView title_left = (ImageView) findViewById(R.id.title_left);
        title_left.setImageResource(R.drawable.arrow_whrite);
        title_text.setText("我的余额");
        listview=(XListView)findViewById(R.id.listview);
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.listview_color)));
        listview.setHeadBg(Bgcolor_1);

        ListviewListener listviewListener = new ListviewListener();

        listview.setPullLoadEnable(true);
        listview.setXListViewListener(listviewListener);
        listview.setAdapter(null);
        listview.resetHeaderHeight();
        listview.SetHeadText(View.GONE);
        listview.SetHeadImageView(R.drawable.common_listview_headview_red_arrow_white);
        //listview.setAdapter(new MyMoneyAdapter(this,this));
    }
    private void initData() {
        lists = new ArrayList<>();
        last_id="";
        getData(1);
    }
    @Override //刷新按钮  回调
    public void refresh_Back() {
        listview.ShowHeadViewAndRefresh();
    }

    @Override  //充值按钮
    public void payMoney() {
        Intent intent = new Intent(this,PayVIPActivity.class);
        intent.putExtra("url",application.recharge+"?clientcode="+ MyFlg.getclientcode(MyMoneyActivity.this));
        intent.putExtra("flg", 0);
        startActivityForResult(intent,1);//REQUESTCODE定义一个整型做为请求对象标识
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    //Listview的上拉加载和下拉刷新
    class ListviewListener implements XListView.IXListViewListener {

        @Override
        public void onRefresh() {
            // TODO Auto-generated method stub
           // listview.stopRefresh();
            //Toast.makeText(MyMoneyActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
            last_id="";
            getData(1);
        }

        @Override
        public void onLoadMore() {
            // TODO Auto-generated method stub
          //  Toast.makeText(MyMoneyActivity.this, "上拉加载", Toast.LENGTH_SHORT).show();
           // listview.stopLoadMore();
            getData(2);
        }

    }
    /**
     * 1  下拉刷新     2上拉加载
     * @param type
     */
    private void getData(final int type){
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(MyMoneyActivity.this).getGet_balance_list(), MyMoneyActivity.this);
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        RequestParams params = new RequestParams();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(MyMoneyActivity.this));
        params.add("last_id",last_id);
        client.post(apiUrl, params,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub
                listview.setFistLoad(true);
                listview.stopRefresh();
                listview.stopLoadMore();
                String result = Sup.UnZipString(responseBody);
                Gson gson = new Gson();
                String json = JSONUtil.isNormalBoolen(MyMoneyActivity.this,result);
                    if(!json.equals("")){//加载成功
                        MyMoneyBean bean = new MyMoneyBean();
                        bean =gson.fromJson(json, MyMoneyBean.class);
                        last_id = bean.getLast_id();
                        if(type==1){//下拉
                            lists.clear();
                            MyMoneyBean.lists temp_bean = new MyMoneyBean().new lists();
                            temp_bean.setPrice(bean.getBalance());
                            lists.add(temp_bean);
                            if(bean.getLists()!=null&&bean.getLists().size()>0){
                                lists.addAll(bean.getLists());
                                if(adapter==null){
                                    adapter = new MyMoneyAdapter(MyMoneyActivity.this,MyMoneyActivity.this,lists);
                                    listview.setAdapter(adapter);
                                }else{
                                    adapter.notifyDataSetChanged();
                                }
                            }else{//没有数据
                                if(adapter==null){
                                    adapter = new MyMoneyAdapter(MyMoneyActivity.this,MyMoneyActivity.this,lists);
                                    listview.setAdapter(adapter);
                                }else{
                                    adapter.notifyDataSetChanged();
                                }
                                listview.SetfootView(false);
                            }

                        }else{//上拉
                            if(bean.getLists()!=null&&bean.getLists().size()>0) {
                                lists.addAll(bean.getLists());
                                if (adapter == null) {
                                    adapter = new MyMoneyAdapter(MyMoneyActivity.this, MyMoneyActivity.this, lists);
                                    listview.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }else{
                                Toast.makeText(MyMoneyActivity.this, "已经是最后一条数据了！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //设置是否加载完
                        if(bean.getNext()==0){
                            listview.setLoading_all_over();
                        }else{
                            listview.initFotter();
                        }
                    }else{//失败
                        if(listview.getFistLoad()==false){
                            listview.setNotInterNet_head();
                        }
                        Toast.makeText(MyMoneyActivity.this, "加载失败，请重试", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody,
                                  Throwable error) {
                // TODO Auto-generated method stub
                //	dialog.dismiss();
                if(listview.getFistLoad()==false){
                    listview.setNotInterNet_head();
                }
                listview.stopRefresh();
                listview.stopLoadMore();
                Toast.makeText(MyMoneyActivity.this, "网络异常。", Toast.LENGTH_SHORT).show();
                error.printStackTrace();// 把错误信息打印出轨迹来
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==1){//充值返回 刷新数据
            listview.ShowHeadViewAndRefresh();
        }
    }
}
