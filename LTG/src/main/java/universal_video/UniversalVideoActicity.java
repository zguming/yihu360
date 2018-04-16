package universal_video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.apache.http.Header;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.dingwei.Bean.UvVideoDetail;
import cn.net.dingwei.Bean.VideoListBean;
import cn.net.dingwei.adpater.MyVideoAdapter;
import cn.net.dingwei.adpater.TabViewPagerAdpater;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.myView.F_IOS_Dialog;
import cn.net.dingwei.myView.PagerSlidingTabStrip;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.ui.HomeActivity2;
import cn.net.dingwei.util.JSONUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.dingwei.util.SharedInfo;
import cn.net.tmjy.mtiku.futures.R;
import cn.net.treeListView.Node;

public class UniversalVideoActicity extends Activity implements View.OnClickListener, UniversalVideoView.VideoViewCallback {
    private static final String TAG = "123";

//    private PolyvQuestionView questionView = null;
//    private PolyvAuditionView auditionView = null;
//    private PolyvPlayerAdvertisementView adView = null;
    private TextView videoAdCountDown = null;
    //取消掉变量引用的注释即可打开logo功能
    private TextView srtTextView = null;
//    private PolyvPlayerFirstStartView playerFirstStartView = null;
    int w = 0, h = 0, adjusted_h = 0;
    private RelativeLayout rl = null;
    private int stopPosition = 0;
    private boolean startNow = false;

    private MyApplication application;
    private List<TreeListViewBean> VideList;//视频列表数据 （传递）
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager viewPager;
    private FYuanTikuDialog dialog;
    private SharedPreferences sp;
    int StateHeight = 0;//状态栏高度
    private int flg = -1; //0 竖屏 1 横屏
    private int tag = -1;//1 播放下一个
    private int next_vid = -1;
    private String next_groupid = "";
    private String vid = "";
    private UvVideoDetail bean;
    //刷新
    private LinearLayout shuaxin_linear;
    private Button shuaxin_button;
    private String Groupid;
    private String id;
    private LinearLayout linear_content;
    private MyVideoAdapter<TreeListViewBean> mAdapter;
    private List<Node> mAllNodes;
    private int position = 0;//初始位置
    private WebView webView;
    ///////////////////////////uv
    private View ll_bottom_layout;
    private View mVideoLayout;
    private UniversalVideoView mVideoView;
    private UniversalMediaController mMediaController;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
//    private String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String VIDEO_URL = "http://ws.streamhls.huya.com/huyalive/92094861-2503514151-10752511403618205696-2013019510-10057-A-1492063494-1_100/playlist.m3u8";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";

    public static Intent newIntent(Context context, UniversalVideoActicity.PlayType playType, String value, boolean startNow, String Groupid,
                                   List<Node> mAllNodes, int position) {
        Intent intent = new Intent(context, UniversalVideoActicity.class);
        intent.putExtra("value", value);
        intent.putExtra("playType", playType.getCode());
        intent.putExtra("startNow", startNow);
        intent.putExtra("Groupid", Groupid);
        intent.putExtra("position", position);
        if (mAllNodes != null) {
            intent.putExtra("allData", (Serializable) mAllNodes);
        }
        return intent;
    }

    public static void intentTo(Context context, UniversalVideoActicity.PlayType playType, String value, boolean startNow, String Groupid,
                                List<Node> mAllNodes, int position) {
        context.startActivity(newIntent(context,playType, value, startNow, Groupid, mAllNodes, position));
    }

    @SuppressLint("ShowToast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_uv);
        ////////////////////////uv
        ll_bottom_layout = findViewById(R.id.ll_bottom_layout);
        mVideoLayout = findViewById(R.id.video_layout);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mMediaController.setMyBackListener(new UniversalMediaController.MyBackListener() {
            @Override
            public void onClick() {//竖屏状态时才生效
                if (mAdapter != null) {
                    mAdapter.tempPosition = -1;
                }
                finish();
            }
        });
        mVideoView.setMediaController(mMediaController);

        mVideoView.setVideoViewCallback(this);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion ");
            }
        });


        //////////////////////////
        dialog = new FYuanTikuDialog(this, R.style.DialogStyle, "正在加载");
        id = getIntent().getStringExtra("value");//我们自己的ID
        Groupid = getIntent().getStringExtra("Groupid");
        position = getIntent().getIntExtra("position", 0);
//        mAllNodes = (List<Node>) getIntent().getSerializableExtra("allData");
        mAllNodes = SharedInfo.getInstance().Getdatas();
        PostApi(id, Groupid);
        initID();
		/*String vid="3dd8d256e63ed6db233edace9eb31edd_3";//测试VID
		initVideo(vid);*/
    }

    private void initID() {
        ///////////////uv
        /////////////////////
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linear_content = (LinearLayout) findViewById(R.id.linear_content);
        //刷新
        shuaxin_linear = (LinearLayout) findViewById(R.id.shuaxin_linear);
        shuaxin_button = (Button) findViewById(R.id.shuaxin_button);
        shuaxin_button.setOnClickListener(this);
        Point point = new Point();
        WindowManager wm = this.getWindowManager();
        wm.getDefaultDisplay().getSize(point);
        w = point.x;
        h = point.y;
        sp = getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        StateHeight = sp.getInt("StateHeight", 0);
        //小窗口的比例
        float ratio = (float) 16 / 9;
        adjusted_h = (int) Math.ceil((float) w / ratio);
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setLayoutParams(new LinearLayout.LayoutParams(w, adjusted_h));
    }

    private void initVideo(String VideoID) {


        setVideoAreaSize();
//        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingprogress);
        videoAdCountDown = (TextView) findViewById(R.id.count_down);
        srtTextView = (TextView) findViewById(R.id.srt);

        final String value = VideoID;
        int playTypeCode = getIntent().getIntExtra("playType", 0);
        final UniversalVideoActicity.PlayType playType = UniversalVideoActicity.PlayType.getPlayType(playTypeCode);
        startNow = getIntent().getBooleanExtra("startNow", false);
        if ( playType == null || TextUtils.isEmpty(value)) {
            Log.e(TAG, "Null Data Source");
            finish();
            return;
        }



        if (startNow) {
            if (isWifiConnected(UniversalVideoActicity.this)) {//判断Wifi是否可用
                videoStart();
            } else {
                showAlertDialogChoose("提示", "当前Wifi不可用，是否继续？", "否", "是");
            }

        } else {
            /*if (playType == PlayType.vid) {
                if (playerFirstStartView == null) {
                    playerFirstStartView = new PolyvPlayerFirstStartView(this);
                    playerFirstStartView.setCallback(new PolyvPlayerFirstStartView.Callback() {

                        @Override
                        public void onClickStart() {
//                            videoView.start();
                            playerFirstStartView.hide();
                        }
                    });
                }
            }*/
        }
    }

    private void videoStart() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared ");
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }else {
                    mVideoView.start();
                    mMediaController.setTitle(bean.title);
                }
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        Log.d(TAG, "onSeekComplete ");
                        mVideoView.start();
                        mMediaController.setTitle(bean.title);
                    }
                });
            }
        });
        /*if (mSeekPosition > 0) {
            mVideoView.seekTo(mSeekPosition);
        }
        mVideoView.start();
        mMediaController.setTitle(bean.title);*/
    }


    // 配置文件设置congfigchange 切屏调用一次该方法，hide()之后再次show才会出现在正确位置
    @Override
    public void onConfigurationChanged(Configuration arg0) {
        super.onConfigurationChanged(arg0);

        /*if (questionView != null && questionView.isShowing()) {
            questionView.hide();
            questionView.refresh();
        } else if (auditionView != null && auditionView.isShowing()) {
            auditionView.hide();
            auditionView.refresh();
        }

        if (playerFirstStartView != null && playerFirstStartView.isShowing()) {
            playerFirstStartView.hide();
            playerFirstStartView.refresh();
        }

        if (adView != null && adView.isShowing()) {
            adView.hide();
            adView.refresh();
        }*/
    }


    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            if (mAdapter != null) {
                mAdapter.tempPosition = -1;
            }
            PostTime();
            super.onBackPressed();
        }

       /* if (questionView != null) {
            questionView.hide();
        }

        if (auditionView != null) {
            auditionView.hide();
        }

        if (playerFirstStartView != null) {
            playerFirstStartView.hide();
        }

        if (adView != null) {
            adView.hide();
        }*/
    }

    @Override
    protected void onDestroy() {
//        super.onDestroy();
        ///////////////////////uv

////////////////////////////////////////
        /*if (questionView != null) {
            questionView.hide();
        }

        if (auditionView != null) {
            auditionView.hide();
        }

        if (playerFirstStartView != null) {
            playerFirstStartView.hide();
        }

        if (adView != null) {
            adView.hide();
        }*/


        super.onDestroy();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shuaxin_button:
                PostApi(id, Groupid);
                break;
        }
    }



    /**
     * 获取详情
     *
     * @param id
     * @param Groupid
     */
    public void PostApi(String id, String Groupid) {
        dialog.show();
        if (application == null) {
//            application = (MyApplication) HomeActivity2.instence.getApplicationContext();
            application = MyApplication.myApplication;
        }
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getGet_video_info(),
				HomeActivity2.instence);
        RequestParams params = new RequestParams();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(HomeActivity2.instence));
        params.add("vid", id);
        params.add("group", Groupid);
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        Log.i("123", "视频网址: "+apiUrl);
        Log.i("123", "视频参数：: "+params.toString());
        client.post(apiUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub
                shuaxin_linear.setVisibility(View.GONE);
                linear_content.setVisibility(View.VISIBLE);
                dialog.dismiss();
                String result = Sup.UnZipString(responseBody);
                Log.i(TAG, "视频播放获取result="+result);
                Gson gson = new Gson();
                String json = JSONUtil.isNormalBoolen(HomeActivity2.instence, result);
                if (!json.equals("")) {
                    bean = gson.fromJson(json, UvVideoDetail.class);
                    VIDEO_URL = bean.video.transcodeInfo.transcodeList
                            .get(bean.video.transcodeInfo.transcodeList.size()-1).url;
                    mSeekPosition = bean.look_time * 1000;
                    initVideo(bean.vid);
//                    mVideoView.seekTo(bean.look_time * 1000);
                    next_vid = bean.next;
                    next_groupid = bean.group;
                    vid = bean.vid;
                    if (mAdapter == null) {
                        PostList();
                    } else {
                        if (webView != null) {
                            webView.loadUrl(bean.url);
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
                shuaxin_linear.setVisibility(View.VISIBLE);
                linear_content.setVisibility(View.GONE);
            }
        });
    }

    private void InitViewPager(String load_url) {
        List<View> listViews = new ArrayList<>();
        //第一个View
        webView = new WebView(UniversalVideoActicity.this);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        //  webSettings.setBuiltInZoomControls(true);
        webView.loadUrl(load_url);
        //第二个View
        View view = View.inflate(UniversalVideoActicity.this, R.layout.item_video, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        try {
            if (mAdapter == null) {
                mAdapter = new MyVideoAdapter<TreeListViewBean>(listView, UniversalVideoActicity.this, VideList, 0);
                mAdapter.setFlg(1);
                if (mAllNodes != null) {
                    Log.i(TAG, "不等于null: ");
                    mAdapter.setmAllNodes(mAllNodes);
                }
                mAdapter.setDatas(VideList);
                listView.setAdapter(mAdapter);
            } else {
                mAdapter.setDatas(VideList);
            }
            listView.setSelection(position);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        }
        listViews.add(webView);
        listViews.add(view);
        String[] title_texts = new String[]{"讲义", "选课"};
        viewPager.setAdapter(new TabViewPagerAdpater(listViews, title_texts));
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setListener(new MyTabListener());
    }

    public void PostList() {
        dialog.show();
        if (application == null) {
//            application = (MyApplication) HomeActivity2.instence.getApplicationContext();
            application = MyApplication.myApplication;
        }
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getGet_video_list(), HomeActivity2.instence);
        RequestParams params = new RequestParams();
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
                //Log.i("mylog", "result="+result);
                Gson gson = new Gson();
                String json = JSONUtil.isNormalBoolen(HomeActivity2.instence, result);
                if (!json.equals("")) {
                    VideoListBean listBean = new VideoListBean();
                    listBean = gson.fromJson(json, VideoListBean.class);
                    initData(listBean);
                    InitViewPager(bean.url);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Toast.makeText(HomeActivity2.instence, "网络异常。", Toast.LENGTH_SHORT).show();
                error.printStackTrace();// 把错误信息打印出轨迹来
            }
        });
    }

    private void initData(VideoListBean bean) {
        VideoListBean.GoOnBean temp_go_on = bean.getGo_on();
        VideList = new ArrayList<>();
        VideList.clear();
        List<VideoListBean.DataBean> list_bean = bean.getData();
        if (list_bean != null && list_bean.size() > 0) {
            int index1 = list_bean.size() + 1;//子分类起始坐标
            for (int i = 0; i < list_bean.size(); i++) {
                VideoListBean.DataBean tem_bean = list_bean.get(i);
                int progress = tem_bean.getLook();
                if (i == list_bean.size() - 1) {
                    VideList.add(new TreeListViewBean(i + 1, 0, tem_bean.getTitle(), progress, progress, tem_bean.getIs_group(), tem_bean.getIs_group(), 1, tem_bean.getId(), tem_bean.getTimes(), tem_bean.getType() + ""));//添加父级
                } else {
                    VideList.add(new TreeListViewBean(i + 1, 0, tem_bean.getTitle(), progress, progress, tem_bean.getIs_group(), tem_bean.getIs_group(), 0, tem_bean.getId(), tem_bean.getTimes(), tem_bean.getType() + ""));//添加父级
                }
                List<VideoListBean.VideosBean> videosBeanList = bean.getData().get(i).getVideos();//视频子集
                if (videosBeanList != null && videosBeanList.size() > 0) {
                    for (int k = 0; k < videosBeanList.size(); k++) {
                        VideoListBean.VideosBean temp_video = videosBeanList.get(k);
                        if (k == videosBeanList.size() - 1) {
                            VideList.add(new TreeListViewBean(index1, i + 1, temp_video.getTitle(), temp_video.getLook(), temp_video.getLook(), temp_video.getIs_group(), temp_video.getIs_group(), 1, temp_video.getId(), temp_video.getTimes(), temp_video.getType() + ""));//添加子级
                        } else {
                            VideList.add(new TreeListViewBean(index1, i + 1, temp_video.getTitle(), temp_video.getLook(), temp_video.getLook(), temp_video.getIs_group(), temp_video.getIs_group(), 0, temp_video.getId(), temp_video.getTimes(), temp_video.getType() + ""));//添加子级
                        }
                    }
                    index1 = index1 + 1;
                }
            }
        } else {//没有数据
            Toast.makeText(HomeActivity2.instence, "没有列表数据 ", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 上传时间进度
     */
    public void PostTime() {
        //dialog.show();
        Log.i(TAG,"posttime发起请求");
        if (application == null) {
            application = MyApplication.myApplication;
        }
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getChange_video_times(), HomeActivity2.instence);
        RequestParams params = new RequestParams();
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(HomeActivity2.instence));
        params.add("vid", vid);
//        stopPosition = mVideoView.getCurrentPosition() / 1000;
        stopPosition = mSeekPosition / 1000;
        Log.i(TAG,"stopPosition="+stopPosition);
        params.add("times", stopPosition+"");
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        client.post(apiUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub
                //	dialog.dismiss();
                String result = Sup.UnZipString(responseBody);
                Log.i(TAG,"posttime成功="+result);
            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                //dialog.dismiss();
                Log.i(TAG,"posttime失败");
            }
        });
    }



    class MyTabListener implements PagerSlidingTabStrip.MyTabBack {
        @Override
        public void back(int index) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 31 && resultCode == RESULT_OK) {//购买视频 返回刷新
            PostList();
        }
    }

    public void showAlertDialogChoose(String title, String content, String leftBtnText, String rightBtnText) {
        F_IOS_Dialog.showAlertDialogChoose(UniversalVideoActicity.this, title, content, leftBtnText, rightBtnText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case F_IOS_Dialog.BUTTON1:
                                dialog.dismiss();
                                break;
                            case F_IOS_Dialog.BUTTON2:
                                dialog.dismiss();
                                videoStart();
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    /**
     * 播放类型
     *
     * @author TanQu
     */
    public enum PlayType {
        /**
         * 使用vid播放
         */
        vid(1),
        /**
         * 使用url播放
         */
        url(2);

        private final int code;

        private PlayType(int code) {
            this.code = code;
        }

        /**
         * 取得类型对应的code
         *
         * @return
         */
        public int getCode() {
            return code;
        }

        public static UniversalVideoActicity.PlayType getPlayType(int code) {
            switch (code) {
                case 1:
                    return vid;
                case 2:
                    return url;
            }

            return null;
        }
    }

    /**
     * 判断Wifi状态
     *
     * @param
     * @return
     */
    public boolean isWifiConnected(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    /////////////////////////////////uv
    @Override
    protected void onPause() {
        /*if (mAdapter != null) {
            mAdapter.tempPosition = -1;
        }*/
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
        PostTime();
        super.onPause();
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {

                if (TextUtils.isEmpty(VIDEO_URL)){
                    return;
                }

                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(VIDEO_URL);
                mVideoView.requestFocus();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            ll_bottom_layout.setVisibility(View.GONE);

            Point point = new Point();
            WindowManager wm = this.getWindowManager();
            wm.getDefaultDisplay().getSize(point);
            w = point.x;
            h = point.y;
            rl.setLayoutParams(new LinearLayout.LayoutParams(w, h));

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            ll_bottom_layout.setVisibility(View.VISIBLE);

            Point point = new Point();
            WindowManager wm = this.getWindowManager();
            wm.getDefaultDisplay().getSize(point);
            w = point.x;
            h = point.y;
            sp = getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
            StateHeight = sp.getInt("StateHeight", 0);
            //小窗口的比例
            float ratio = (float) 16 / 9;
            adjusted_h = (int) Math.ceil((float) w / ratio);
            rl.setLayoutParams(new LinearLayout.LayoutParams(w, adjusted_h));
        }

//        switchTitleBar(!isFullscreen);
    }

    /*private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }*/

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }


}
