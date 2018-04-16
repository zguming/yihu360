package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.List;

import cn.net.dingwei.Bean.Products_listBean;
import cn.net.dingwei.util.MyApplication;
import cn.net.tmjy.mtiku.futures.R;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Home_product_adapter extends BaseAdapter {
    private ClickBack clickBack;
    private final MyApplication application;
    private SharedPreferences sharedPreferences;
    private int Fontcolor_3=0;

    private List<Products_listBean.data> data;
    private Context context;

    public ClickBack getClickBack() {
        return clickBack;
    }

    public void setClickBack(ClickBack clickBack) {
        this.clickBack = clickBack;
    }

    public Home_product_adapter(Context context,List<Products_listBean.data> data) {
        this.data = data;
        this.context = context;
        sharedPreferences =context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Fontcolor_3= sharedPreferences.getInt("fontcolor_3", 0);
        application = MyApplication.myApplication;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
            if(view==null){
                viewHolder = new ViewHolder();
                view  = View.inflate(context, R.layout.item_home_product,null);
                viewHolder.framelayout = (FrameLayout) view.findViewById(R.id.framelayout);
                viewHolder.imageview = (SimpleDraweeView) view.findViewById(R.id.imageview);
                viewHolder.imageview2 = (SimpleDraweeView) view.findViewById(R.id.imageview2);
                viewHolder.title = (TextView) view.findViewById(R.id.title);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
        viewHolder.title.setTextColor(Fontcolor_3);
        Products_listBean.data temp_data = data.get(i);
        viewHolder.title.setText(temp_data.getTitle());
        if(!TextUtils.isEmpty(temp_data.getImg())){
            //ImageLoader.getInstance().displayImage(temp_data.getImg(),viewHolder.imageview, application.getOptions());
                LoadGif(temp_data.getImg(),viewHolder.imageview);
        }else{
            viewHolder.imageview.setImageDrawable(new ColorDrawable(Color.GRAY));
        }
        if(!TextUtils.isEmpty(temp_data.getPay_img())){
            //ImageLoader.getInstance().displayImage(temp_data.getImg(),viewHolder.imageview2, application.getOptions());
            LoadGif(temp_data.getPay_img(),viewHolder.imageview2);
            viewHolder.imageview2.setVisibility(View.VISIBLE);
        }else{
            viewHolder.imageview2.setVisibility(View.GONE);
        }
        viewHolder.framelayout.setOnClickListener(new ProductListClick(temp_data));
        return view;
    }
    class ViewHolder{
        SimpleDraweeView imageview,imageview2;
        TextView title;
        FrameLayout framelayout;
    }
    class ProductListClick implements View.OnClickListener{
        private Products_listBean.data temp_data;

        public ProductListClick(Products_listBean.data temp_data) {
            this.temp_data = temp_data;
        }
        @Override
        public void onClick(View view) {
            if(!TextUtils.isEmpty(temp_data.getIs_chick())&&temp_data.getIs_chick().equals("0")){//可点
                    if(!TextUtils.isEmpty(temp_data.getType())){
                        String type=temp_data.getType();
                        Log.i("123", "typetypetype: "+type);
                        if(type.equals("1")){//章节练习
                            clickBack.ZhangJieLianXi();
                        }else  if(type.equals("2")){//我的错题
                                clickBack.MyError();
                        }else  if(type.equals("3")){//智能模拟
                            clickBack.CeYan(temp_data);
                        }else  if(type.equals("4")){//真题模拟
                            clickBack.CeYan(temp_data);
                        }else  if(type.equals("6")){//加载连接
                            String url = temp_data.getUrl();
                            if(!TextUtils.isEmpty(url)){
                                clickBack.toWebView(url);
                            }
                        } else  if(type.equals("8")){//加载连接
                            String url = temp_data.getUrl();
                            if(!TextUtils.isEmpty(url)){
                                clickBack.toWebView(url);
                            }
                        }  else{//进阶
                            clickBack.jinjie(temp_data);
                        }
                    }
            }else{
                //Toast.makeText(context,"即将开放，敬请期待",Toast.LENGTH_SHORT).show();
                clickBack.MyHint();
            }
        }
    }
    public interface  ClickBack{
        void MyError();
        void jinjie(Products_listBean.data temp_data);
        void CeYan(Products_listBean.data temp_data);
        void ZhangJieLianXi();
        void toWebView(String url);
        void MyHint();
    }
    //加载GIF
    private void LoadGif(String url,SimpleDraweeView draweeView){
        Uri uri = Uri.parse(url);
        DraweeController  draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        draweeView.setController(draweeController);
    }
}
