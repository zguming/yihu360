package cn.net.dingwei.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.Diresct_OrderBean;
import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.dingwei.Bean.VideoListBean;
import cn.net.dingwei.Bean.Video_LiveBean;
import cn.net.dingwei.adpater.FragmentVideo_NewAdapter;
import cn.net.dingwei.adpater.MyVideoAdapter;
import cn.net.dingwei.adpater.TabViewPagerAdpater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.NoScorllViewPager;
import cn.net.dingwei.myView.PagerSlidingTabStrip;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.util.JSONUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;
import universal_video.UniversalVideoActicity;
import universal_video.UniversalVideoLiveActicity;

/**
 * 视频学习
 * Created by Administrator on 2016/7/14.
 */
public class FragmentVideo_New extends Fragment implements View.OnClickListener,FragmentVideo_NewAdapter.TimeOver{
    private SharedPreferences sharedPreferences;
    private int Fontcolor_1=0,Fontcolor_3=0,Bgcolor_1=0,Bgcolor_2=0,Bgcolor_3=0,Color_4=0,Color_3=0,Fontcolor_13=0;
    private MyApplication application;
    private ListView listview,listview2;
    private List<TreeListViewBean> datas = new ArrayList<TreeListViewBean>();
    private MyVideoAdapter<TreeListViewBean> mAdapter;
    private FYuanTikuDialog dialog;
    private boolean isFirst=false;
    private TextView text_jixutext1,text_other2;
    private LinearLayout linear_top;
    private View view_item_jianju;
    private LinearLayout shuaxin_linear,shuaxin_linear2,linear_Nodata2;
    private Button shuaxin_button,shuaxin_button2;
    private ScrollView myScrollView,myScrollView2;
    private String topVid="";//顶部视频ID
    //----------------------------------华丽的分割线------------------------
    public static  boolean isNeedUpdata=false;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private NoScorllViewPager viewPager;
    private FragmentVideo_NewAdapter adapter;
    private List<Video_LiveBean.DataBean> video_livedatas=new ArrayList<>();
    private LinearLayout linear_Nodata;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = View.inflate(HomeActivity2.instence, R.layout.fragment_video_new,null);
        application = MyApplication.myApplication;
        sharedPreferences =getActivity().getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
        Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
        Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
        Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
        Bgcolor_3 = sharedPreferences.getInt("bgcolor_3", 0);
        Color_4 = sharedPreferences.getInt("color_4", 0);
        Color_3 = sharedPreferences.getInt("color_3", 0);
        Fontcolor_13= sharedPreferences.getInt("fontcolor_13", 0);
        initID(view);

        return view;
    }

    private void initID(View view) {
        TextView title_text = (TextView) getActivity().findViewById(R.id.title_text);
        title_text.setTextColor(Fontcolor_1);
        title_text.setText("课程");
        pagerSlidingTabStrip=(PagerSlidingTabStrip)view.findViewById(R.id.pagerSlidingTabStrip);
        viewPager=(NoScorllViewPager)view.findViewById(R.id.viewPager);

        List<View> views = new ArrayList<>();
        View view1 = View.inflate(getContext(),R.layout.item_fragmentvideo_viewpager,null);
        linear_Nodata = (LinearLayout) view1.findViewById(R.id.linear_Nodata);
        view1.findViewById(R.id.text_other).setVisibility(View.GONE);
        TextView text_no_video = (TextView) view1.findViewById(R.id.text_no_video);
        text_no_video.setText("该科目暂无视频，敬请期待");
        //刷新
        shuaxin_linear=(LinearLayout)view1.findViewById(R.id.shuaxin_linear);
        shuaxin_button=(Button)view1.findViewById(R.id.shuaxin_button);
        shuaxin_button.setOnClickListener(this);
        //第一个
        listview = (ListView) view1.findViewById(R.id.listview);
        text_jixutext1 = (TextView) view1.findViewById(R.id.text_jixutext1);
        view_item_jianju =  view1.findViewById(R.id.view_item_jianju);
        linear_top = (LinearLayout) view1.findViewById(R.id.linear_top);
        myScrollView = (ScrollView) view1.findViewById(R.id.myScrollView);

        View view2 = View.inflate(getContext(),R.layout.item_fragmentvideo_viewpager2,null);
        //刷新
        myScrollView2 = (ScrollView) view2.findViewById(R.id.myScrollView);
        shuaxin_linear2=(LinearLayout)view2.findViewById(R.id.shuaxin_linear);
        linear_Nodata2=(LinearLayout)view2.findViewById(R.id.linear_Nodata);
        text_other2=(TextView)view2.findViewById(R.id.text_other);
        shuaxin_button2=(Button)view2.findViewById(R.id.shuaxin_button);
        text_jixutext1.setTextColor(Fontcolor_13);
        view_item_jianju.setBackgroundColor(Color_4);
        linear_top.setOnClickListener(this);
        //第二个
        listview2 = (ListView) view2.findViewById(R.id.listview);
        text_other2.setTextColor(Bgcolor_2);
        shuaxin_button2.setOnClickListener(new NoInter());
        text_other2.setOnClickListener(this);


        views.add(view1);
        views.add(view2);
        String[] title_texts=new String[]{"课程点播","直播课堂"};
        viewPager.setAdapter(new TabViewPagerAdpater(views, title_texts));
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setTextColor(Fontcolor_3);
        if(application.gethas_live_video(getContext()).equals("1")||application.gethas_live_video(getContext()).equals("2")){
            viewPager.setScanScroll(false);
            pagerSlidingTabStrip.setVisibility(View.GONE);
            if(application.gethas_live_video(getContext()).equals("2")){//直播
                viewPager.setCurrentItem(1);
                text_other2.setVisibility(View.GONE);
            }
        }else  if(application.gethas_live_video(getContext()).equals("3")){
            viewPager.setScanScroll(true);
        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initData();

        initListener();
    }
    private void initListener(){
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectIndex(position);
            }
        });
    }
    private void initData(VideoListBean bean) {
        VideoListBean.GoOnBean temp_go_on=bean.getGo_on();
        if(temp_go_on!=null&& !TextUtils.isEmpty(temp_go_on.getText())){
            text_jixutext1.setText(temp_go_on.getText());
            linear_top.setVisibility(View.VISIBLE);
            view_item_jianju.setVisibility(View.VISIBLE);
            topVid = temp_go_on.getVid();
        }else{
            linear_top.setVisibility(View.GONE);
            view_item_jianju.setVisibility(View.GONE);
        }
        datas.clear();
        List<VideoListBean.DataBean> list_bean= bean.getData();
        if(list_bean!=null&&list_bean.size()>0){
            linear_Nodata.setVisibility(View.GONE);
            myScrollView.setVisibility(View.VISIBLE);
            int index1=list_bean.size()+1;//子分类起始坐标
            for (int i = 0;i<list_bean.size();i++){
                VideoListBean.DataBean tem_bean =list_bean.get(i);
                int progress = tem_bean.getLook();
                if(i==list_bean.size()-1){
                    datas.add(new TreeListViewBean(i + 1, 0,tem_bean.getTitle(), progress,progress, tem_bean.getIs_group(), tem_bean.getIs_group(), 1, tem_bean.getId(),tem_bean.getTimes(),tem_bean.getType()+""));//添加父级
                }else{
                    datas.add(new TreeListViewBean(i + 1, 0,tem_bean.getTitle(),progress,progress, tem_bean.getIs_group(), tem_bean.getIs_group(), 0, tem_bean.getId(),tem_bean.getTimes(),tem_bean.getType()+""));//添加父级
                }
                List<VideoListBean.VideosBean> videosBeanList= bean.getData().get(i).getVideos();//视频子集
                if(videosBeanList!=null&&videosBeanList.size()>0){
                    for (int k=0;k<videosBeanList.size();k++){
                        VideoListBean.VideosBean temp_video = videosBeanList.get(k);
                        if (k == videosBeanList.size() - 1) {
                            datas.add(new TreeListViewBean(index1, i + 1,temp_video.getTitle(), temp_video.getLook(), temp_video.getLook(), temp_video.getIs_group(), temp_video.getIs_group(), 1, temp_video.getId(),temp_video.getTimes(),temp_video.getType()+""));//添加子级
                        }
                        else {
                            datas.add(new TreeListViewBean(index1, i + 1,temp_video.getTitle(), temp_video.getLook(), temp_video.getLook(),temp_video.getIs_group(), temp_video.getIs_group(), 0, temp_video.getId(),temp_video.getTimes(),temp_video.getType()+""));//添加子级
                        }
                    }
                    index1=index1+1;
                }
            }
            try {
                if(mAdapter==null||isNeedUpdata==true){
                    mAdapter = new MyVideoAdapter<TreeListViewBean>(listview, HomeActivity2.instence, datas, 0);
                    listview.setAdapter(mAdapter);
                    isNeedUpdata=false;
                }else{
                    mAdapter.setDatas(datas);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else{//没有数据
            //Toast.makeText(HomeActivity2.instence, "没有数据 ", Toast.LENGTH_SHORT).show();
            linear_Nodata.setVisibility(View.VISIBLE);
            myScrollView.setVisibility(View.GONE);
        }

    }
    public void loadData(){
        PostApi();
        PostApi_live();
    }
    public void PostApi(){
       // if(isFirst==false){
            if(dialog==null){
                dialog = new FYuanTikuDialog(HomeActivity2.instence,R.style.DialogStyle,"正在加载");
            }
            dialog.show();
       // }
        if(application==null){
            application = MyApplication.myApplication;
        }
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getGet_video_list(),HomeActivity2.instence);
        RequestParams params = new RequestParams();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(HomeActivity2.instence));
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        Log.i("123", "课程网址: "+apiUrl);
        Log.i("123", "课程参数：: "+params.toString());

        client.post(apiUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub
                isFirst=true;
                shuaxin_linear.setVisibility(View.GONE);
                myScrollView.setVisibility(View.VISIBLE);
                dialog.dismiss();
                isFirst = true;
                String result = Sup.UnZipString(responseBody);
                Log.i("123", "视频result：: "+result);
                Gson gson = new Gson();
                String json = JSONUtil.isNormalBoolen(HomeActivity2.instence,result);
                if(!json.equals("")){
                    VideoListBean bean = new VideoListBean();
                    bean= gson.fromJson(json,VideoListBean.class);
                    initData(bean);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Toast.makeText(HomeActivity2.instence, "网络异常。", Toast.LENGTH_SHORT).show();
                error.printStackTrace();// 把错误信息打印出轨迹来
                shuaxin_linear.setVisibility(View.VISIBLE);
                myScrollView.setVisibility(View.GONE);
            }
        });
    }
    public void PostApi_live(){//获取直播数据
        // if(isFirst==false){
        if(dialog==null){
            dialog = new FYuanTikuDialog(HomeActivity2.instence,R.style.DialogStyle,"正在加载");
        }
        dialog.show();
        // }
        if(application==null){
            application = MyApplication.myApplication;
        }
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getGet_live_list(),HomeActivity2.instence);
        RequestParams params = new RequestParams();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(HomeActivity2.instence));
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        Log.i("123", "直播网址: "+apiUrl);
        Log.i("123", "直播参数：: "+params.toString());
        client.post(apiUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub
                isFirst=true;
                shuaxin_linear2.setVisibility(View.GONE);

                dialog.dismiss();
                isFirst = true;
                String result = Sup.UnZipString(responseBody);
                Log.i("123", "直播接口获取result: "+result);
                Gson gson = new Gson();
                String json = JSONUtil.isNormalBoolen(HomeActivity2.instence,result);
                if(!json.equals("")){
                    myScrollView2.setVisibility(View.VISIBLE);
                    linear_Nodata2.setVisibility(View.GONE);
                    Video_LiveBean bean= gson.fromJson(result,Video_LiveBean.class);
                    if(bean.getData()!=null&&bean.getData().size()>0){
                        video_livedatas.clear();
                        video_livedatas.addAll(bean.getData());
                        if(adapter==null){
                            adapter = new FragmentVideo_NewAdapter(getContext(),video_livedatas,FragmentVideo_New.this);
                            listview2.setAdapter(adapter);
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        linear_Nodata2.setVisibility(View.VISIBLE);
                        myScrollView2.setVisibility(View.GONE);
                    }
                }else{
                    linear_Nodata2.setVisibility(View.VISIBLE);
                    myScrollView2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                dialog.dismiss();
               // Toast.makeText(HomeActivity2.instence, "网络异常。", Toast.LENGTH_SHORT).show();
                error.printStackTrace();// 把错误信息打印出轨迹来
                shuaxin_linear2.setVisibility(View.VISIBLE);
                myScrollView2.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if(isFirst==true){
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            adapter.setShop(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_top:
                if(!TextUtils.isEmpty(topVid)){
                    UniversalVideoActicity.intentTo(HomeActivity2.instence,
                            UniversalVideoActicity.PlayType.vid,topVid, true,0+"",null,0);
                }
                break;
            case R.id.shuaxin_button:
                PostApi();
                break;
            case R.id.text_other:
                 viewPager.setCurrentItem(0);
                break;
        }
    }
    //主要用于第二个Item 没网络
    class NoInter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            PostApi_live();
        }
    }
    @Override
    public void TimeOver() {//倒计时结束 刷新直播数据
        Log.i("mylog","回调Shaun数据");
        PostApi_live();
    }

    /**
     * 直播点击事件
     * @param id
     * @param myurl
     * @param identifier
     * @param ltid
     * @param sig
     * @param handouts
     * @param refresh_time
     * @param live_title
     */
    @Override
    public void goDirectSeeding(String id, String myurl, String identifier, String ltid, String sig, String handouts, int refresh_time,String live_title) {
        if(TextUtils.isEmpty(myurl)||TextUtils.isEmpty(myurl)){
            return;
        }
        if (TextUtils.isEmpty(identifier)) {
            identifier = "";
        }
        if(TextUtils.isEmpty(handouts)){
            handouts="";
        }

        RequestParams params = new RequestParams();
        params.add("id",id);
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getChange_live_user(),HomeActivity2.instence);
        PostOther(0, apiUrl, params, myurl, identifier, ltid,sig, handouts, refresh_time,live_title,id);
    }

    @Override //预约
    public void order(String id) {
        RequestParams params = new RequestParams();
        params.add("id",id);
        params.add("os","Android");
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getOrder_live(),HomeActivity2.instence);
        PostOther(1, apiUrl, params, "", "","", "", "", 10,null,id);
    }

    /**
     *
     * @param flg 0 进入直播  1.预约
     */
    public void PostOther(final int flg, String apiUrl, RequestParams params, final String myurl, final String identifier, final String ltid, final String sig,
                          final String handouts, final int refresh_time, final String live_title, final String id){
        if(dialog==null){
            dialog = new FYuanTikuDialog(HomeActivity2.instence,R.style.DialogStyle,"正在加载");
        }
        dialog.show();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(HomeActivity2.instence));
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        client.post(apiUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub

                dialog.dismiss();
                String result = Sup.UnZipString(responseBody);
                String json = JSONUtil.isNormalBoolen(HomeActivity2.instence,result);
                if(!json.equals("")){
                    if(flg==0){//直播
                        Intent intent = new Intent(getContext(), UniversalVideoLiveActicity.class);
                        intent.putExtra("myurl", myurl);
                        intent.putExtra("identifier", identifier);
                        intent.putExtra("ltid", ltid);
                        intent.putExtra("sig", sig);
                        intent.putExtra("webUrl", handouts);
                        intent.putExtra("refresh_time", refresh_time);
                        intent.putExtra("live_title", live_title);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }else if(flg==1){
                        Gson gson = new Gson();
                        Diresct_OrderBean bean = gson.fromJson(json,Diresct_OrderBean.class);
                        Log.i("123", "bean.getType(): "+bean.getType());
                        if(bean.getType()==1){//需要付款
                            Intent intent =new  Intent(getContext(), PayVIPActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            intent.putExtra("flg",3);
                            startActivity(intent);
                        }else if(bean.getType()==0){//预约成功-不需要支付 刷新列表
                            PostApi_live();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Toast.makeText(HomeActivity2.instence, "网络异常。", Toast.LENGTH_SHORT).show();
                error.printStackTrace();// 把错误信息打印出轨迹来
                shuaxin_linear2.setVisibility(View.VISIBLE);
                myScrollView2.setVisibility(View.GONE);
            }
        });
    }
}
