package cn.net.dingwei.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.ExamBean;
import cn.net.dingwei.Bean.ExamGroupBean;
import cn.net.dingwei.Bean.Get_baseinfoBean;
import cn.net.dingwei.Bean.Level0Item;
import cn.net.dingwei.Bean.Level1Item;
import cn.net.dingwei.Bean.UtilBean;
import cn.net.dingwei.myView.MyListView;
import cn.net.dingwei.ui.SubjectSelectActivity;
import cn.net.dingwei.util.APPUtil;
import cn.net.dingwei.util.DataUtil;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
//    private HomeLeftListViewAdpater homeLeftListViewAdpater;
    private List<ExamGroupBean> screenList;
    private final MyApplication myApplication;
    private final int bgcolor_2;

    private final SharedPreferences sp2;
    private Person_data_Adpater subjectListViewAdpater;
//    private List<ExamBean> list_exam;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        myApplication = MyApplication.myApplication;
        SharedPreferences sharedPreferences = myApplication.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
        bgcolor_2 = sharedPreferences.getInt("bgcolor_2", 0);
        sp2 = myApplication.getSharedPreferences("screen_list_info", Context.MODE_PRIVATE);

    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                switch (holder.getLayoutPosition() ) {
                    case 0:
                        holder.setImageResource(R.id.iv_head, R.drawable.exams_icon);
                        break;
//                    case 1:
//                        holder.setImageResource(R.id.iv_head, R.drawable.stages_icon);
//                        break;
                }
                final Level0Item lv0 = (Level0Item)item;
                holder.setText(R.id.title, lv0.screenListBean.title)
                        .setText(R.id.subtitle, lv0.screenListBean.subTitle)
                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.drawable.arrow_b : R.drawable.arrow_r);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos,false);
                        } else {
//                            if (pos % 3 == 0) {
//                                expandAll(pos, false);
//                            } else {
                                expand(pos,false);
//                            }
                        }
                    }
                });

                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item)item;
                holder.setText(R.id.title, lv1.contentBean.name);
                String screenKey = sp2.getString("screenKey", "");
                if(lv1.contentBean.key.equals(screenKey)) {
                    holder.setTextColor(R.id.title, bgcolor_2);
                }else {
                    holder.setTextColor(R.id.title, Color.parseColor("#a3a6a6"));
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 1 item pos: " + pos);
                        sp2.edit().putString("screenKey",lv1.contentBean.key).commit();


                        ArrayList<MultiItemEntity> list = generateData(lv1.contentBean.getListTpye()
                                ,lv1.contentBean.name);
                        setNewData(list);
                        notifyDataSetChanged();
                        List<ExamBean> list_exam = APPUtil.getexam_structure(MyApplication.myApplication);
                        List<ExamBean> list1 = new ArrayList<>();

                        if(lv1.contentBean.key.equals("全部")) {
                            list1 = list_exam;
                        }else {
                            for (int i = 0; i < list_exam.size(); i++) {
                                if(lv1.contentBean.key.equals(list_exam.get(i).getGroup())) {
                                    list1.add(list_exam.get(i));
                                }
                            }
                        }
                        String [] stArr= new String [list1.size()];
                        String [] status=new String [list1.size()];
                        for (int i = 0; i < list1.size(); i++) {
                            stArr[i] = list1.get(i).getN();
                            status[i]=list1.get(i).getStatus();
                        }
                        SubjectSelectActivity.list_exam = list1;
                        subjectListViewAdpater.setArr_status(stArr,status,2);
                        subjectListViewAdpater.notifyDataSetChanged();
                        /*for (int i = 0; i < UtilBean.list_Get_baseinfoBean.size(); i++) {
                            Get_baseinfoBean get_baseinfoBean = UtilBean.list_Get_baseinfoBean.get(i);
                            if (null != get_baseinfoBean && null != get_baseinfoBean.getK()) {
                                if (MyApplication.getuserInfoBean(MyApplication.mContext)
                                        .getExam().equals(get_baseinfoBean.getK())) {
                                    UtilBean.screenSubjects = genNewArr(get_baseinfoBean.getSubjects()
                                            ,lv1.contentBean.getListTpye(),lv1.contentBean.key);
                                    list_exam
                                    subjectListViewAdpater.setArr_status(UtilBean.screenSubjects);
                                    subjectListViewAdpater.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }*/

                    }
                });
                break;
        }
    }


    /*public static Get_baseinfoBean.subjects[] genNewArr(Get_baseinfoBean.subjects[] arr, int listType, String key){
//        SharedPreferences sp = MyApplication.mContext.getSharedPreferences("screen_obj", Context.MODE_PRIVATE);
        Get_baseinfoBean.subjects[] result;
        List<Get_baseinfoBean.subjects> list = new ArrayList<>();
        int index = 0;
        switch (listType) {
            case 0://exams
                String stagesKey = MyApplication.userInfoBean.getSubject_stage();
//                String stagesKey = sp.getString("stagesKey", "");
                for (int i = 0; i < arr.length; i++) {
                    Get_baseinfoBean.subjects subjects = arr[i];
                    if(!TextUtils.isEmpty(stagesKey)) {
                        if(key.equals(subjects.getExam())&&stagesKey.equals(subjects.getStage())) {
                            list.add(subjects);
                        }
                    }else {
                        if(key.equals(subjects.getExam())) {
                            list.add(subjects);
                        }
                    }

                }
//                sp.edit().putString("examsKey",key).commit();
                break;
            case 1://stages
                String examsKey = MyApplication.userInfoBean.getSubject_exam();
//                String examsKey = sp.getString("examsKey", "");
                for (int i = 0; i < arr.length; i++) {
                    Get_baseinfoBean.subjects subjects = arr[i];
                    if(!TextUtils.isEmpty(examsKey)) {
                        if(key.equals(subjects.getStage())&&examsKey.equals(subjects.getExam())) {
                            list.add(subjects);
                        }
                    }else {
                        if(key.equals(subjects.getStage())) {
                            list.add(subjects);
                        }
                    }
                }
//                sp.edit().putString("stagesKey",key).commit();
                break;
        }
        result = new Get_baseinfoBean.subjects[list.size()];
        for (Get_baseinfoBean.subjects subjects:list) {
            result[index] = subjects;
            index++;
        }

        return result;
    }*/

    private ArrayList<MultiItemEntity> generateData(int listTpye, String name) {

        List<ExamGroupBean> list = new ArrayList<>();
        list.add(UtilBean.screenExamGroupBean);
        screenList.get(listTpye).setSubTitle(name);
        int lv0Count = screenList.size();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            ExamGroupBean screenListBean = screenList.get(i);
            Level0Item lv0 = new Level0Item(screenListBean);
            for (int j = 0; j < screenListBean.content.size(); j++) {
                ExamGroupBean.ContentBean contentBean = screenList.get(i).content.get(j);
                contentBean.setListTpye(i);
                Level1Item lv1 = new Level1Item(contentBean);
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    public void setSubjectListViewAdpater(Person_data_Adpater subjectListViewAdpater) {
        this.subjectListViewAdpater = subjectListViewAdpater;
    }

    public void setScreenList(List<ExamGroupBean> screenList) {
        this.screenList = screenList;
    }

    /*public void setlist_exam(List<ExamBean> list_exam) {
        this.list_exam = list_exam;
    }*/
}
