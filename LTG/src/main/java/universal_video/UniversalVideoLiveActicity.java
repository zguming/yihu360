package universal_video;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.universalvideoview.UniversalMediaController;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.adpater.TabViewPagerAdpater;
import cn.net.dingwei.business.InitBusiness;
import cn.net.dingwei.business.LoginBusiness;
import cn.net.dingwei.myView.PagerSlidingTabStrip;
import cn.net.dingwei.sup.Sup;
import cn.net.dingwei.ui.HomeActivity2;
import cn.net.dingwei.ui.ParentActivity;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.LogUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * 直播
 */
public class UniversalVideoLiveActicity extends ParentActivity implements View.OnClickListener,
        TIMMessageListener {

    private final String TAG = "123";
    //使用ID
    private String identifier = "";
    private String ltid = "";
    private String sig = "";
    private String mId = "";
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager viewPager;
    private int Fontcolor_3 = 0, Bgcolor_1 = 0, Bgcolor_2 = 0;
    private MyApplication application;
    private EditText editText_msg;
    private Button btn_send;
    private WebView webView;
    private TextView text_msg;
    private String webUrl = "", live_title = "";
    private SpannableStringBuilder builder;//
    private ScrollView myscrollview;
    private TabViewPagerAdpater adpater;
    String[] title_texts = new String[]{"聊天", "讲义"};
    UniversalVideoLiveActicity.TimeHandler handler = new UniversalVideoLiveActicity.TimeHandler();
    private int time = 20000;//间隔多少S刷新一次在线人数
    private boolean isLoad = true;//是否加载人数
    int Screen_width = 0;
    //////////////////////////uv
    private View ll_bottom_layout;
    private SharedPreferences sp;
    int StateHeight = 0;//状态栏高度
    private int mSeekPosition = 0;
    private boolean isFullscreen;
    private String VIDEO_URL = "";
    private TIMConversation conversation;
    private InputMethodManager manager;

    //直播
    private View mVideoLayout;
    TXCloudVideoView mPlayerView;
    TXLivePlayer mLivePlayer = null;
    private UniversalMediaController mMediaController;
    RelativeLayout rl;
    private int cachedHeight;
    int w = 0, h = 0, adjusted_h = 0;

    //播放模式
    private int              mCacheStrategy = 0;
    private static final int  CACHE_STRATEGY_FAST  = 1;  //极速
    private static final int  CACHE_STRATEGY_SMOOTH = 2;  //流畅
    private static final int  CACHE_STRATEGY_AUTO = 3;  //自动
    private static final float  CACHE_TIME_FAST = 1.0f;
    private static final float  CACHE_TIME_SMOOTH = 5.0f;
    private TXLivePlayConfig mPlayConfig;
    //横竖屏
    private int mCurrentRenderRotation;

    @Override
    protected void onCreate(Bundle bundle) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_uv_live);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        VIDEO_URL = intent.getStringExtra("myurl");
        identifier = intent.getStringExtra("identifier");
        ltid = intent.getStringExtra("ltid");
        sig = intent.getStringExtra("sig");
        mId = intent.getStringExtra("id");
        webUrl = intent.getStringExtra("webUrl");
        live_title = intent.getStringExtra("live_title");
        time = (intent.getIntExtra("refresh_time", 10)) * 1000;

        Log.i(TAG, "直播地址：: "+VIDEO_URL);
        ll_bottom_layout = findViewById(R.id.ll_bottom_layout);
        mVideoLayout = findViewById(R.id.video_layout);
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        mPlayConfig = new TXLivePlayConfig();

        //mPlayerView即step1中添加的界面view
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        //创建player对象
        mLivePlayer = new TXLivePlayer(this);
        //关键player对象与界面view
        mLivePlayer.setPlayerView(mPlayerView);

        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mMediaController.setMyBackListener(new UniversalMediaController.MyBackListener() {
            @Override
            public void onClick() {//竖屏状态时才生效
                finish();
            }
        });
        mMediaController.hideSeekBar();
        mMediaController.hideplay();
        mMediaController.setTitle(live_title);

        setVideoAreaSize();
        uniClick();
        initIM();
        initID();
        initColor();
        initButtom();

    }

    private void initIM() {
        InitBusiness.start(getApplicationContext());
        LoginBusiness.loginIm(identifier, sig
                , new LoginTIMCallBack());
    }

    /**
     * 用户登录IM监听回调
     */
    private class LoginTIMCallBack implements TIMCallBack {

        @Override
        public void onError(int i, String s) {
            LogUtil.i("用户登录失败，i="+i+",s="+s);
            switch (i) {
                case 6208:
                    //离线状态下被其他终端踢下线
                    Toast.makeText(UniversalVideoLiveActicity.this,"离线状态下被其他终端踢下线",Toast.LENGTH_SHORT).show();
                    break;
                case 6200:
                    Toast.makeText(UniversalVideoLiveActicity.this,"登录失败，当前无网络",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(UniversalVideoLiveActicity.this,"登录失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onSuccess() {
            LogUtil.i("用户登录成功");
            //设置新昵称为cat
            TIMFriendshipManager.getInstance().setNickName(MyApplication.myApplication
                    .userInfoBean.getNickname(), new SetNickNameTIMCallBack());
            TIMGroupManager.getInstance().applyJoinGroup(ltid, "申请理由哈哈", new JoinGroupTIMCallBack());
        }
    }

    /**
     * 设置昵称Im监听回调
     */
    private class SetNickNameTIMCallBack implements TIMCallBack {

        @Override
        public void onError(int code, String desc){
            //错误码code和错误描述desc，可用于定位请求失败原因
            //错误码code列表请参见错误码表
            LogUtil.e("setNickName failed: " + code + " desc");
        }

        @Override
        public void onSuccess(){
            LogUtil.e("setNickName succ");
        }
    }

    /**
     * 加入聊天室IM监听回调
     */
    private class JoinGroupTIMCallBack implements TIMCallBack {
        @Override
        public void onError(int i, String s) {
            LogUtil.i("加入聊天室失败，i="+i+",s"+s);
        }

        @Override
        public void onSuccess() {
            LogUtil.i("加入聊天室成功");
            //获取群聊会话
            String groupId = ltid;  //获取与群组 "TGID1LTTZEAEO" 的会话

            //会话类型：群组
            //群组Id
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group,      //会话类型：群组
                    groupId);
            TIMManager.getInstance().addMessageListener(UniversalVideoLiveActicity.this);

        }
    }


    private void initColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_3 = sharedPreferences.getInt("fontcolor_3", 0);
        Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
        Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
        application = MyApplication.myApplication;
    }

    private void initButtom() {

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        int Screen_height = application.getSharedPreferencesValue_int(this, "Screen_height");
        Screen_width = application.getSharedPreferencesValue_int(this, "Screen_width");
        int height = Screen_height - DensityUtil.DipToPixels(this, 50) - application.getSharedPreferencesValue_int(this, "StateHeight") -
                Screen_width * 9 / 16;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        viewPager.setLayoutParams(params);
        pagerSlidingTabStrip.setTextColor(Fontcolor_3);
        List<View> lsit_views = new ArrayList<>();
        View view = View.inflate(this, R.layout.item_dirctseeding_viewpager, null);
        myscrollview = (ScrollView) view.findViewById(R.id.myscrollview);
        text_msg = (TextView) view.findViewById(R.id.text_msg);
        text_msg.setTextColor(Fontcolor_3);
        editText_msg = (EditText) view.findViewById(R.id.editText_msg);

        btn_send = (Button) view.findViewById(R.id.btn_send);
        lsit_views.add(view);

        View view1 = View.inflate(this, R.layout.item_dirctseeding_viewpager_web, null);
        webView = (WebView) view1.findViewById(R.id.webView);
        initWebView(webView);
        lsit_views.add(view1);
        adpater = new TabViewPagerAdpater(lsit_views, title_texts);
        viewPager.setAdapter(adpater);
        pagerSlidingTabStrip.setViewPager(viewPager);


        btn_send.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Color.LTGRAY, Color.TRANSPARENT, 0, 10));
        pagerSlidingTabStrip.setTextColor(Fontcolor_3);
        btn_send.setOnClickListener(this);

        PostOther();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_send://发送
                closeKey();
                final String temp_msg = editText_msg.getText().toString().trim();
                if (TextUtils.isEmpty(temp_msg)) {
                    Toast.makeText(UniversalVideoLiveActicity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //构造一条消息
                    TIMMessage msg = new TIMMessage();

                    //添加文本内容
                    TIMTextElem elem = new TIMTextElem();
                    elem.setText(temp_msg);

                    //将elem添加到消息
                    if(msg.addElement(elem) != 0) {
                        LogUtil.d("addElement failed");
                        return;
                    }
                    if(conversation == null) {
                        Toast.makeText(UniversalVideoLiveActicity.this,"加入聊天室失败，",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
                        @Override
                        public void onError(int code, String desc) {
                            //错误码code和错误描述desc，可用于定位请求失败原因
                            //错误码code含义请参见错误码表
                            LogUtil.d("send message failed. code: " + code + " errmsg: " + desc);
                            Toast.makeText(UniversalVideoLiveActicity.this,"发送消息失败，请重新发送",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(TIMMessage timMessage) {
                            LogUtil.e("SendMsg ok");
                            setMsg("我",temp_msg);
                            editText_msg.setText("");
                        }
                    });

                }
                break;
            default:
                break;
        }
    }

    private void closeKey() {//关闭键盘
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setMsg(String name, String msg) {
        String st = name + ":" + msg + "\n";
        SpannableStringBuilder temp_builder = new SpannableStringBuilder(st);
        temp_builder.setSpan(new TextAppearanceSpan(this, R.style.style_video_msg), 0, name.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (builder == null) {
            builder = new SpannableStringBuilder();
        }
        builder.append(temp_builder);
        text_msg.setText(builder);
        myscrollview.fullScroll(ScrollView.FOCUS_DOWN);
    }



    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLoad = true;
    }

    @Override
    protected void onDestroy() {

        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView != null){
            mPlayerView.onDestroy();
            mPlayerView = null;
        }

        isLoad = false;
        TIMManager.getInstance().removeMessageListener(this);
        TIMGroupManager.getInstance().quitGroup(ltid, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                LogUtil.e("退出聊天室失败,i="+i+",s="+s);
            }

            @Override
            public void onSuccess() {
                LogUtil.e("退出聊天室成功");
            }
        });
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    private void initWebView(WebView webView) {
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
        webView.setWebViewClient(client);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
        Log.i("123", webUrl);
    }

    /**
     *
     */
    public void PostOther() {
        handler.sendEmptyMessageDelayed(0, time);
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        String apiUrl = MyFlg.get_API_URl(application.getCommonInfo_API_functions(HomeActivity2.instence).getGet_live_number(),
                HomeActivity2.instence);
        RequestParams params = new RequestParams();
        params.add("lid", mId);
        params.add("a", MyFlg.a);
        params.add("ver", MyFlg.android_version);
        params.add("clientcode", MyFlg.getclientcode(HomeActivity2.instence));
        client.post(apiUrl,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] handers, byte[] responseBody) {
                // TODO Auto-generated method stub
                String result = Sup.UnZipString(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String usercount = jsonObject.getJSONObject("data").getString("number");
                    LogUtil.d("在线人数="+usercount);
                    if (!TextUtils.isEmpty(usercount)) {
                        title_texts[0] = "聊天(" + usercount + ")";
                        adpater.set_titles(title_texts);
                        pagerSlidingTabStrip.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] handers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                // Toast.makeText(HomeActivity2.instence, "网络异常。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        /* 消息 */
        for(TIMMessage msg:list) {
            for(int i = 0; i < msg.getElementCount(); ++i) {
                TIMElem elem = msg.getElement(i);

                //获取当前元素的类型
                TIMElemType elemType = elem.getType();
                LogUtil.d("elem type: " + elemType.name());
                if (elemType == TIMElemType.Text) {
                    //处理文本消息
                    TIMTextElem textElem = (TIMTextElem)elem;
                    if(!msg.isSelf()) {//收到消息不是自己发的
                        setMsg(msg.getSenderProfile().getNickName(),textElem.getText());
                    }
                } else if (elemType == TIMElemType.Image) {
                    //处理图片消息
                }//...处理更多消息
            }
        }


        return false;
    }


    class TimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (isLoad) {
                        PostOther();
                    }
                    break;
            }
        }
    }

    private void initID() {
        ///////////////
        /////////////////////
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
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

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mLivePlayer.startPlay(VIDEO_URL, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐FLV

                //直播监听
                mLivePlayer.setPlayListener(new ITXLivePlayListener() {
                    @Override
                    public void onPlayEvent(int i, Bundle bundle) {
                        if (i == TXLiveConstants.PLAY_EVT_PLAY_BEGIN){//开始
                            mMediaController.hideLoading();
                        } else if (i == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || i == TXLiveConstants.PLAY_EVT_PLAY_END) {//结束
                            if (mLivePlayer != null) {
                                mLivePlayer.setPlayListener(null);
                                mLivePlayer.stopPlay(true);
                            }
                        }
                    }

                    @Override
                    public void onNetStatus(Bundle bundle) {

                    }
                });
            }
        });
    }

    /**
     * 横竖屏点击
     */
    private void uniClick(){
        if (mMediaController != null){
            mMediaController.setMyFullListener(new UniversalMediaController.MyFullListener() {
                @Override
                public void onClick() {
                    if (mLivePlayer == null) {
                        return;
                    }
                    if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_PORTRAIT) {//点击时横屏
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        setRequestedOrientation(mCurrentRenderRotation);

                        ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        mVideoLayout.setLayoutParams(layoutParams);

                        Point point = new Point();
                        WindowManager wm = getWindowManager();
                        wm.getDefaultDisplay().getSize(point);
                        w = point.x;
                        h = point.y;
                        rl.setLayoutParams(new LinearLayout.LayoutParams(w, h));
                        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;

                    } else if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_LANDSCAPE) {//点击时竖屏
                        if (mCurrentRenderRotation == 270){
                            mCurrentRenderRotation = 1;
                        }
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        setRequestedOrientation(mCurrentRenderRotation);

                        ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.height = cachedHeight;
                        mVideoLayout.setLayoutParams(layoutParams);

                        Point point = new Point();
                        WindowManager wm = getWindowManager();
                        wm.getDefaultDisplay().getSize(point);
                        w = point.x;
                        h = point.y;
                        //小窗口的比例
                        float ratio = (float) 16 / 9;
                        adjusted_h = (int) Math.ceil((float) w / ratio);
                        rl.setLayoutParams(new LinearLayout.LayoutParams(w, adjusted_h));
                        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
                    }
                    Log.i(TAG, "屏幕旋转状态: "+mCurrentRenderRotation);
//                    mLivePlayer.setRenderRotation(mCurrentRenderRotation);
                }
            });
        }
    }

}
