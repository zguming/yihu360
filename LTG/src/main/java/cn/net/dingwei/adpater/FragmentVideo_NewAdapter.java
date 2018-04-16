package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.net.dingwei.Bean.Video_LiveBean;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.ui.HomeActivity2;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class FragmentVideo_NewAdapter extends BaseAdapter {
    private List<Video_LiveBean.DataBean> datas;
    private Context context;
    private int selectIndex = 0;
    private MyApplication application;
    private LinearLayout.LayoutParams params;
    private int Fontcolor_7 = 0, Fontcolor_1 = 0, Bgcolor_1 = 0, Bgcolor_2 = 0, Bgcolor_8 = 0, Color_4 = 0, Color_3 = 0, Fontcolor_13 = 0;
    private boolean isShop = false;
    private TimeOver timeOver;
    private Animation animation_show;

    public FragmentVideo_NewAdapter(Context context, List<Video_LiveBean.DataBean> datas, TimeOver timeOver) {
        this.context = context;
        this.timeOver = timeOver;
        application = MyApplication.myApplication;
        int width = application.getSharedPreferencesValue_int(context, "Screen_width");
        params = new LinearLayout.LayoutParams(width, width / 2);
        SharedPreferences sharedPreferences = context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_1 = sharedPreferences.getInt("fontcolor_1", 0);
        Fontcolor_7 = sharedPreferences.getInt("fontcolor_7", 0);
        Fontcolor_13 = sharedPreferences.getInt("fontcolor_13", 0);
        Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
        Bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
        Bgcolor_8 = sharedPreferences.getInt("bgcolor_8", 0);
        Color_4 = sharedPreferences.getInt("color_4", 0);
        Color_3 = sharedPreferences.getInt("color_3", 0);
        animation_show = AnimationUtils.loadAnimation(context, R.anim.fade_show_200ms);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_video_new_list, null);
            viewHolder = new ViewHolder();
            viewHolder.linear1 = (LinearLayout) convertView.findViewById(R.id.linear1);
            viewHolder.linear2 = (LinearLayout) convertView.findViewById(R.id.linear2);
            viewHolder.imageview1 = (ImageView) convertView.findViewById(R.id.imageview1);
            viewHolder.imageview2 = (ImageView) convertView.findViewById(R.id.imageview2);
            viewHolder.text_content = (TextView) convertView.findViewById(R.id.text_content);
            viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            viewHolder.text_status = (TextView) convertView.findViewById(R.id.text_status);
            viewHolder.text_title1 = (TextView) convertView.findViewById(R.id.text_title1);
            viewHolder.text_title2 = (TextView) convertView.findViewById(R.id.text_title2);
            viewHolder.text_number1 = (TextView) convertView.findViewById(R.id.text_number1);
            viewHolder.text_number2 = (TextView) convertView.findViewById(R.id.text_number2);
            viewHolder.btn_use = (TextView) convertView.findViewById(R.id.btn_use);
            viewHolder.btn_use2 = (TextView) convertView.findViewById(R.id.btn_use2);
            viewHolder.text_price = (TextView) convertView.findViewById(R.id.text_price);
            viewHolder.text_price2 = (TextView) convertView.findViewById(R.id.text_price2);
            viewHolder.tv_live_time = (TextView) convertView.findViewById(R.id.tv_live_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置颜色
        viewHolder.text_content.setTextColor(Fontcolor_13);
        viewHolder.text_name.setTextColor(Fontcolor_13);
        viewHolder.text_time.setTextColor(Fontcolor_13);
        viewHolder.tv_live_time.setTextColor(Fontcolor_13);
        viewHolder.imageview1.setLayoutParams(params);

        if (position == selectIndex) {
            viewHolder.linear1.setVisibility(View.VISIBLE);
            viewHolder.linear2.setVisibility(View.GONE);
            viewHolder.linear1.startAnimation(animation_show);
        } else {
            viewHolder.linear1.setVisibility(View.GONE);
            viewHolder.linear2.setVisibility(View.VISIBLE);
        }
        //设置数据
        Video_LiveBean.DataBean temp_data = datas.get(position);
        if (!TextUtils.isEmpty(temp_data.getBig_img()) && (viewHolder.imageview1.getTag() == null || !((String) viewHolder.imageview1
                .getTag()).equals(temp_data.getBig_img()))) {
            ImageLoader.getInstance().displayImage(temp_data.getBig_img(), viewHolder.imageview1, application.getOptions());
            viewHolder.imageview1.setTag(temp_data.getBig_img());
        }
        if (!TextUtils.isEmpty(temp_data.getImg()) && (viewHolder.imageview2.getTag() == null || !((String) viewHolder.imageview2.getTag
                ()).equals(temp_data.getImg()))) {
            ImageLoader.getInstance().displayImage(temp_data.getImg(), viewHolder.imageview2, application.getOptions());
            viewHolder.imageview2.setTag(temp_data.getImg());
        }
        Boolean status = temp_data.isStatus();
        if (status == false) {
            viewHolder.text_status.setText("未开始");
        } else if (status) {
            viewHolder.text_status.setText("直播中");
        }
        viewHolder.tv_live_time.setText(temp_data.getTime());
        viewHolder.text_title1.setText(temp_data.getTitle());
        viewHolder.text_title2.setText(temp_data.getTitle());
        viewHolder.text_name.setText("主讲：" + temp_data.getName());
        viewHolder.text_time.setText(temp_data.getTime());
        viewHolder.text_content.setText(temp_data.getIntro());
        viewHolder.text_number1.setText("已有" + temp_data.getNumbers() + "个同学选课");
        viewHolder.text_number2.setText("已有" + temp_data.getNumbers() + "个同学选课");
        viewHolder.btn_use.setText(temp_data.getButton().getBtn_text());
        viewHolder.btn_use2.setText(temp_data.getButton().getBtn_text());
        viewHolder.text_price.setText(temp_data.getPrice());
        viewHolder.text_price2.setText(temp_data.getPrice());

        if (temp_data.getButton().getOrder()==1) {
            viewHolder.text_price.setTextColor(Bgcolor_2);
            viewHolder.text_price2.setTextColor(Bgcolor_2);
        } else {
            viewHolder.text_price.setTextColor(Fontcolor_7);
            viewHolder.text_price2.setTextColor(Fontcolor_7);
        }
        if (temp_data.getButton().getLook()==1) {//可进入
            viewHolder.btn_use.setEnabled(true);
            ButtonClik clik = new ButtonClik(0, position);
            viewHolder.btn_use.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Color.LTGRAY, Bgcolor_2, 0, 10));
            viewHolder.btn_use.setTextColor(Color.WHITE);
            viewHolder.btn_use.setOnClickListener(clik);
            viewHolder.text_price.setTextColor(Fontcolor_7);

            viewHolder.btn_use2.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Bgcolor_2, Color.LTGRAY, Bgcolor_2, 0, 10));
            viewHolder.btn_use2.setTextColor(Color.WHITE);
            viewHolder.btn_use2.setOnClickListener(clik);
            viewHolder.text_price2.setTextColor(Fontcolor_7);
        } else {//不可进入的时候 去判断是否需要预约
            if (temp_data.getButton().getOrder()==1) {//是否需要预约
                viewHolder.btn_use.setEnabled(true);
                ButtonClik clik = new ButtonClik(1, position);
                viewHolder.btn_use.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Color.WHITE, Bgcolor_2, 1, 10));
                viewHolder.btn_use.setTextColor(Bgcolor_2);
                viewHolder.btn_use.setOnClickListener(clik);

                viewHolder.btn_use2.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.WHITE, Color.WHITE, Bgcolor_2, 1, 10));
                viewHolder.btn_use2.setTextColor(Bgcolor_2);
                viewHolder.btn_use2.setOnClickListener(clik);
            } else {
                viewHolder.btn_use.setEnabled(false);
                viewHolder.btn_use.setBackgroundDrawable(MyFlg.setViewRaduis(Color.WHITE, Fontcolor_7, 1, 10));
                viewHolder.btn_use.setTextColor(Fontcolor_7);

                viewHolder.btn_use2.setBackgroundDrawable(MyFlg.setViewRaduis(Color.WHITE, Fontcolor_7, 1, 10));
                viewHolder.btn_use2.setTextColor(Fontcolor_7);
            }
        }
        if (temp_data.getStart_times() > 0) {
            if (viewHolder.btn_use.getTag() == null) {
                Log.i("mylog", "新建线程");
                viewHolder.btn_use.setTag(true);
                viewHolder.handler = new TimeHandler(temp_data.getStart_times() * 1000, 1000);
            } else {
                Log.i("mylog", "设置时间总数");
                viewHolder.handler.setSum_times(temp_data.getStart_times() * 1000);
            }
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout linear1, linear2;
        ImageView imageview1, imageview2;
        TextView text_content, text_name, text_time, text_status, text_title1, text_title2, text_number1, text_number2, btn_use,
                btn_use2, text_price, text_price2, tv_live_time;
        TimeHandler handler;
    }

    public void setSelectIndex(int selectIndex) {
        if (this.selectIndex != selectIndex) {
            this.selectIndex = selectIndex;
            notifyDataSetChanged();
        }

    }

    class ButtonClik implements View.OnClickListener {
        private int flg;//0 可以进入  1 预约
        private int position;

        public ButtonClik(int flg, int position) {
            this.flg = flg;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_use:
                    btn_useClick();
                    break;
                case R.id.btn_use2:
                    btn_useClick();
                    break;
            }
        }

        private void btn_useClick() {
            Video_LiveBean.DataBean temp_data = datas.get(position);
            if (flg == 0) {
                // Toast.makeText(HomeActivity2.instence, "进入", Toast.LENGTH_SHORT).show();
                if (timeOver != null) {
                    timeOver.goDirectSeeding(temp_data.getId(), temp_data.getCode(), temp_data.getIdentifier(), temp_data.getLtid(),
                            temp_data.getSig(),temp_data.getHandouts(), temp_data.getRefresh_time(), temp_data.getTitle());
                }
            } else if (flg == 1) {
                {
                    //Toast.makeText(HomeActivity2.instence, "预约", Toast.LENGTH_SHORT).show();
                    if (timeOver != null) {
                        timeOver.order(temp_data.getId());
                    }
                }
            }
        }
    }

    class TimeHandler extends Handler {
        private long sum_times;//总时间
        private long sleeptime = 1000;
        public Thread thread;
        private MyThread myThread;

        public TimeHandler(long sum_times, long sleeptime) {
            this.sum_times = sum_times;
            this.sleeptime = sleeptime;
            myThread = new MyThread(sum_times, this, sleeptime);
            thread = new Thread(myThread);
            thread.start();
        }

        public void setSum_times(long sum_times) {
            this.sum_times = sum_times;
            myThread.setSum_times(sum_times);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://倒计时中
                    break;
                case 1://倒计时结束
                    //  Log.i("mylog","倒计时结束Handler");
                    if (timeOver != null) {
                        timeOver.TimeOver();
                    }
                    if (thread != null && thread.isAlive()) {
                        thread.interrupt();
                    }
                    break;
            }
        }
    }

    class MyThread implements Runnable {
        private long sum_times;//总时间
        private TimeHandler handler;
        private long sleep_time;

        public MyThread(long sum_times, TimeHandler handler, long sleep_time) {
            this.sum_times = sum_times;
            this.handler = handler;
            this.sleep_time = sleep_time;
        }

        public void setSum_times(long sum_times) {
            this.sum_times = sum_times;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    //  Log.i("mylog",sum_times+"");
                    if (sum_times >= sleep_time) {
                        Thread.sleep(sleep_time);
                        handler.sendEmptyMessage(0);
                        sum_times = sum_times - sleep_time;
                    } else if ((sum_times >= 0 && sum_times < sleep_time) || isShop) {
                        // Log.i("mylog","倒计时结束");
                        sum_times = -1;
                        handler.sendEmptyMessage(1);
                        break;
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    Log.i("mylog", "线程停止");
                }
            }
        }
    }

    public void setShop(boolean shop) {
        isShop = shop;
    }

    public interface TimeOver {
        void TimeOver();

        void goDirectSeeding(String id, String myurl, String identifier, String ltid, String sig,
                             String handouts, int refresh_time, String live_title);

        void order(String id);
    }

}
