package cn.net.dingwei.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.net.dingwei.Bean.PointsBean;
import cn.net.dingwei.adpater.Home_childView_listview;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * 章节练习
 */
public class ChapterActivity extends ParentActivity {
    private SharedPreferences sharedPreferences;
    private int Color_4=0;
    private MyApplication application;
    private TextView title_text;
    private ImageView title_left;
    private ListView listview;
    private List<PointsBean> list_PointsBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyFlg.listActivity.add(this);
        setContentView(R.layout.activity_chapter);
        initID();
        initData();
    }
    private void initID() {
        application = MyApplication.myApplication;
        sharedPreferences =getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        Color_4 = sharedPreferences.getInt("color_4", 0);
        title_text=(TextView)findViewById(R.id.title_text);
        findViewById(R.id.layoutRight).setVisibility(View.INVISIBLE);
        title_left=(ImageView)findViewById(R.id.title_left);
        title_left.setImageResource(R.drawable.arrow_whrite);
        title_text.setText("章节练习");
        listview=(ListView)findViewById(R.id.listview);
    }
    private void initData() {
        Intent intent = getIntent();
        list_PointsBean = (List<PointsBean>) intent.getSerializableExtra("data");
        Home_childView_listview adpater = new Home_childView_listview(ChapterActivity.this, list_PointsBean);
        listview.setAdapter(adpater);
        listview.setDivider(new ColorDrawable(Color_4));
        listview.setDividerHeight(1);
        listview.setOnItemClickListener(new listviewOnItemClick());
    }
    class listviewOnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int point,
                                long arg3) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(ChapterActivity.this, Activity_homeChildView.class);
            if(null==list_PointsBean){
            }else{
                intent.putExtra("point", list_PointsBean.get(point).getPoints());
                if(null==list_PointsBean.get(point).getPoint_jinduBean()){

                }else{
                    intent.putExtra("jindu", list_PointsBean.get(point).getPoint_jinduBean().getPoints());

                }
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ChapterActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }
}
