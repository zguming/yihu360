package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.net.dingwei.Bean.MyMoneyBean;
import cn.net.dingwei.util.DensityUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * Created by Administrator on 2016/7/5.
 * 我的余额
 */
public class MyMoneyAdapter extends BaseAdapter{
    private final int Screen_height;
    private Context context;
    private Refresh_Back back;
    private SharedPreferences sharedPreferences;
    private int Fontcolor_3=0,Fontcolor_13=0,Bgcolor_8=0,Bgcolor_1 = 0;
    private List<MyMoneyBean.lists> lists;
    public MyMoneyAdapter(Context context,Refresh_Back back,List<MyMoneyBean.lists> lists) {
        this.context = context;
        this.back = back;
        this.lists =lists;
        sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
        Fontcolor_13= sharedPreferences.getInt("fontcolor_13", 0);
        Bgcolor_8 = sharedPreferences.getInt("bgcolor_8", 0);
        Bgcolor_1 = sharedPreferences.getInt("bgcolor_1", 0);
        Screen_height=sharedPreferences.getInt("Screen_height", 0);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view = View.inflate(context, R.layout.item_mymoney,null);
            viewHolder = new ViewHolder();
            viewHolder.linear_first = (LinearLayout) view.findViewById(R.id.linear_first);
            viewHolder.linear_other = (LinearLayout) view.findViewById(R.id.linear_other);
            viewHolder.linear_Nodata = (LinearLayout) view.findViewById(R.id.linear_Nodata);
            viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
            viewHolder.text_title = (TextView) view.findViewById(R.id.text_title);
            viewHolder.text_time = (TextView) view.findViewById(R.id.text_time);
            viewHolder.text_number = (TextView) view.findViewById(R.id.text_number);
            viewHolder.text_first_money = (TextView) view.findViewById(R.id.text_first_money);
            viewHolder.text_price = (TextView) view.findViewById(R.id.text_price);
            viewHolder.view_item_jianju =  view.findViewById(R.id.view_item_jianju);
            viewHolder.btn_Refresh = (Button) view.findViewById(R.id.btn_Refresh);
            viewHolder.btn_pay = (Button) view.findViewById(R.id.btn_pay);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Btn_click btn_click =new Btn_click();
        viewHolder.btn_pay.setOnClickListener(btn_click);
        MyMoneyBean.lists temp_list = lists.get(position);
        if(position==0){
            if(temp_list.getPrice()!=null){
                viewHolder.text_first_money.setText("￥"+temp_list.getPrice());
            }else{
                viewHolder.text_first_money.setText("￥0.00");
            }
            viewHolder.linear_first.setVisibility(View.VISIBLE);
            viewHolder.text1.setVisibility(View.VISIBLE);
            viewHolder.linear_other.setVisibility(View.GONE);
            viewHolder.view_item_jianju.setVisibility(View.GONE);
            viewHolder.btn_Refresh.setOnClickListener(btn_click);
            viewHolder.btn_pay.setBackgroundDrawable(MyFlg.setViewRaduisAndTouch(Color.TRANSPARENT, Bgcolor_8, Color.WHITE, Bgcolor_8, 1, DensityUtil.DipToPixels(context,25)));
            if(lists.size()==1){
                viewHolder.linear_Nodata.setVisibility(View.VISIBLE);
                viewHolder.text1.setVisibility(View.GONE);
                int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                viewHolder.linear_first.measure(w, h);
                int height = viewHolder.linear_first.getMeasuredWidth();
                int shengxia_height = Screen_height-DensityUtil.DipToPixels(context,45)-height;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,shengxia_height);
                viewHolder.linear_Nodata.setLayoutParams(params);
            }else{
                viewHolder.linear_Nodata.setVisibility(View.GONE);
                viewHolder.text1.setVisibility(View.VISIBLE);
            }


        }else{
            if(position == 1) {
                viewHolder.view_item_jianju.setVisibility(View.GONE);
            } else {
                viewHolder.view_item_jianju.setVisibility(View.VISIBLE);
            }
            viewHolder.linear_first.setBackgroundColor(Bgcolor_1);
            viewHolder.linear_first.setVisibility(View.GONE);
            viewHolder.text1.setVisibility(View.GONE);
            viewHolder.linear_other.setVisibility(View.VISIBLE);

            viewHolder.text_title.setTextColor(Fontcolor_3);
            viewHolder.text_time.setTextColor(Fontcolor_13);
            viewHolder.text_number.setTextColor(Fontcolor_13);
            viewHolder.text_price.setTextColor(Color.parseColor("#" + temp_list.getColor()));

            viewHolder.text_title.setText(temp_list.getContent());
            viewHolder.text_time.setText(temp_list.getTime());
            if(!TextUtils.isEmpty(temp_list.getPrice())){
                viewHolder.text_price.setText(temp_list.getPrice());
            }else{
                viewHolder.text_price.setText("￥0.00");
            }

            viewHolder.text_number.setText("交易号："+temp_list.getCode());
        }

        return view;
    }
    class ViewHolder{
            LinearLayout linear_first,linear_other,linear_Nodata;
            TextView text_first_money,text_price,text1,text_title,text_time,text_number;
            View view_item_jianju;
            Button btn_Refresh,btn_pay;
    }
    class Btn_click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_Refresh:
                    back.refresh_Back();
                    break;
                case R.id.btn_pay:
                    back.payMoney();
                    break;
            }

        }
    }
    public interface  Refresh_Back{
        void refresh_Back();
        void payMoney();
    }
}
