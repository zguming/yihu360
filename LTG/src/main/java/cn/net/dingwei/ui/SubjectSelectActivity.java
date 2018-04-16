package cn.net.dingwei.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.AsyncUtil.UpdateUserAsync;
import cn.net.dingwei.Bean.ExamBean;
import cn.net.dingwei.Bean.ExamGroupBean;
import cn.net.dingwei.Bean.Level0Item;
import cn.net.dingwei.Bean.Level1Item;
import cn.net.dingwei.Bean.UtilBean;
import cn.net.dingwei.adpater.ExpandableItemAdapter;
import cn.net.dingwei.adpater.Person_data_Adpater;
import cn.net.dingwei.myView.ElasticScrollView;
import cn.net.dingwei.myView.FYuanTikuDialog;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * 左侧标题栏点击
 */
public class SubjectSelectActivity extends ParentActivity {
    private ImageView title_left;
    private TextView title_text;
    private RecyclerView rv_multiItem;
    private List<ExamGroupBean> screenList;
    private cn.net.dingwei.myView.MyListView subject_listview;
    public static List<ExamBean> list_exam; //考试学科
    private Person_data_Adpater adpater;
    private FYuanTikuDialog dialog;
    private MyHandler handler = new MyHandler();
    private String[] stArr;//考试下的数据
    private String[] status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);
        initView();
        initData();

    }

    private void initData() {
        title_text.setText("选择考试");
        DataUtil.getUserInfoAndBaseInfo(this);
        list_exam= APPUtil.getexam_structure(this);

        SharedPreferences sp = getSharedPreferences("screen_list_info", Context.MODE_PRIVATE);
        String screenKey = sp.getString("screenKey", "");
        if(!TextUtils.isEmpty(screenKey) && !"全部".equals(screenKey)) {
            List<ExamBean> list1 = new ArrayList<>();
            for (int i = 0; i < list_exam.size(); i++) {
                if(screenKey.equals(list_exam.get(i).getGroup())) {
                    list1.add(list_exam.get(i));
                }
            }
            stArr = new String [list1.size()];
            status = new String [list1.size()];
            for (int i = 0; i < list1.size(); i++) {
                stArr[i] = list1.get(i).getN();
                status[i]=list1.get(i).getStatus();
            }
            list_exam = list1;
        }else {
            stArr= new String [list_exam.size()];
            status=new String [list_exam.size()];
            for (int i = 0; i < list_exam.size(); i++) {
                stArr[i] = list_exam.get(i).getN();
                status[i]=list_exam.get(i).getStatus();
            }
        }

        adpater = new Person_data_Adpater(this, stArr,status,2);
        subject_listview.setAdapter(adpater);
        subject_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list_exam.get(position).getK().equals(MyApplication.getuserInfoBean(SubjectSelectActivity.this).getExam()) && list_exam.get(position).getStatus().equals("1")){
                    MyFlg.all_activitys.remove(SubjectSelectActivity.this);
                    finish();
                }else if(list_exam.get(position).getStatus().equals("1")){
                    dialog.show();
                    MyFlg.ISupdateHome = true;
                    MyFlg.ISupdateguide = true;
                    UpdateUserAsync updateUserAsync=new UpdateUserAsync(dialog, handler, SubjectSelectActivity.this, MyFlg.getclientcode(SubjectSelectActivity.this), null, null, null, list_exam.get(position).getK(), null, null, null, null, false, 1,null,null,null,null,null);
                    //updateUserAsync=new UpdateUserAsync(dialog, handler, Person_dataActivity.this, MyFlg.getclientcode(Person_dataActivity.this), application.getuserInfoBean(Person_dataActivity.this).getCity(), null, null, list_exam.get(point).getK(), application.getuserInfoBean(Person_dataActivity.this).getExam_schedule(), null, null, null, false, 1,null,null);
                    updateUserAsync.execute();
                }
            }
        });

        screenList = new ArrayList<>();
        screenList.add(UtilBean.screenExamGroupBean);
        ArrayList<MultiItemEntity> list = generateData();
        ExpandableItemAdapter adapter = new ExpandableItemAdapter(list);
        adapter.setSubjectListViewAdpater(adpater);
        adapter.setScreenList(screenList);

        if (TextUtils.isEmpty(screenKey)) {
            adapter.expandAll();
        }
        rv_multiItem.setAdapter(adapter);
    }

    private void initView() {
        dialog = new FYuanTikuDialog(this,R.style.DialogStyle,"正在提交");
        title_left=(ImageView)findViewById(R.id.title_left);
        title_left.setImageResource(R.drawable.title_black);
        title_text=(TextView)findViewById(R.id.title_text);
        rv_multiItem = (RecyclerView) findViewById(R.id.rv_multiItem);
        rv_multiItem.setLayoutManager(new SyLinearLayoutManager(this));
        subject_listview = (cn.net.dingwei.myView.MyListView) findViewById(R.id.subject_listview);
        ElasticScrollView scroll = (ElasticScrollView)findViewById(R.id.scroll);
        //设置颜色
        scroll.setColor(getResources().getColor(R.color.lianxi_bg));
    }

    //////////////////////////新增左侧列表筛选
    private ArrayList<MultiItemEntity> generateData() {
        String examsName = "请选择";
        SharedPreferences sp = getSharedPreferences("screen_list_info", Context.MODE_PRIVATE);
        String screenKey = sp.getString("screenKey", "");
        if(!TextUtils.isEmpty(screenKey)) {
            examsName = screenKey;
        }
        /*String examsKey = MyApplication.userInfoBean.getSubject_exam();
        for (ScreenListBean.ContentBean contentBean : UtilBean.screenExamsListBean.content) {
            if (examsKey.equals(contentBean.key)) {
                examsName = contentBean.name;
            }
        }*/
        screenList.get(0).setSubTitle(examsName);
        int lv0Count = screenList.size();
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            ExamGroupBean examGroupBean = screenList.get(i);
//            screenListBean.setSubTitle("");
            Level0Item lv0 = new Level0Item(examGroupBean);
            for (int j = 0; j < examGroupBean.content.size(); j++) {
                ExamGroupBean.ContentBean contentBean = screenList.get(i).content.get(j);
                contentBean.setListTpye(i);
                Level1Item lv1 = new Level1Item(contentBean);
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    public class SyLinearLayoutManager extends LinearLayoutManager {

        private static final int CHILD_WIDTH = 0;
        private static final int CHILD_HEIGHT = 1;
        private static final int DEFAULT_CHILD_SIZE = 100;

        private final int[] childDimensions = new int[2];

        private int childSize = DEFAULT_CHILD_SIZE;
        private boolean hasChildSize;

        @SuppressWarnings("UnusedDeclaration")
        public SyLinearLayoutManager(Context context) {
            super(context);
        }

        @SuppressWarnings("UnusedDeclaration")
        public SyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        private int[] mMeasuredDimension = new int[2];


        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;


            for (int i = 0; i < getItemCount(); i++) {

                try {
                    measureScrapChild(recycler, i,
                            widthSpec,
                            View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                            mMeasuredDimension);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }


//        Logger.d("ll width:"+width+";widthSize:"+widthSize+";widthSpec:"+widthSpec);
//        Logger.d("ll height:"+width+";heightSize:"+heightSize+";heightSpec:"+heightSpec);
//        Logger.d("ll widthMode:"+widthMode+";heightMode:"+heightMode);

            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
//                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }
            setMeasuredDimension(widthSpec, height);

        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[]
                measuredDimension) {
            View view = recycler.getViewForPosition(position);

            // For adding Item Decor Insets to view
//        super.measureChildWithMargins(view, 0, 0);

            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(widthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Logger.e("SyLinearLayoutManager state:" + state.toString());
            super.onLayoutChildren(recycler, state);
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://提交用户信息返回
                    DataUtil.getUserInfoAndBaseInfo(SubjectSelectActivity.this);
                    MyFlg.all_activitys.remove(SubjectSelectActivity.this);
                    finish();
                    break;
            }
        }
    }
}
