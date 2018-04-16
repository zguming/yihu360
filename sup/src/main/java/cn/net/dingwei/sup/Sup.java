package cn.net.dingwei.sup;

import android.content.Context;

import java.util.List;

import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Create_test_suit_2Bean;

/**
 * Created by cd on 2017/5/3.
 */

public class Sup {
    public static String UnZipString(byte[] responseBody) {
        return ZipUtil.UnZipString(responseBody);
    }
    public static Create_Exercise_suit_2Bean.infos[] cuotijiexi(Create_Exercise_suit_2Bean.infos[] infos) {
        return Reading_QuestionsSup.cuotijiexi(infos);
    }
    public static String getcorrect(String[] data) {
        return Reading_QuestionsSup.getcorrect(data);
    }
    public static Create_Exercise_suit_2Bean.questions getQuestionBean(Create_Exercise_suit_2Bean.infos[] infos,int index) {
        return Reading_QuestionsSup.getQuestionBean(infos,index);
    }
    public static int getQUstionIndex(Create_Exercise_suit_2Bean.infos[] infos,int index) {
        return Reading_QuestionsSup.getQUstionIndex(infos,index);
    }

    public static void init(Context context) {
        SignUtil.init(context);
    }
    public static int getQuestionIndex(List<Create_test_suit_2Bean.infos> list_infos, int point){
        return Reading_testingSup.getQuestionIndex(list_infos,point);
    }
    public static int getRemainSum(List<Create_test_suit_2Bean.infos> list_infos) {
        return Reading_testingSup.getRemainSum(list_infos);
    }
    public static boolean isNumeric(String str){
        return PracticeReportSup.isNumeric(str);
    }
    public static void setList_infos_cuoti(List<Create_test_suit_2Bean.infos> list_infos_all
            ,List<List<Create_test_suit_2Bean.infos>> list_infos_cuoti) {
        PracticeReportSup.setList_infos_cuoti(list_infos_all,list_infos_cuoti);
    }
    public static void setList_infos_all(List<Create_test_suit_2Bean.infos> list_infos_all
            ,List<Create_test_suit_2Bean.infos> list_infos) {
        PracticeReportSup.setList_infos_all(list_infos_all,list_infos);
    }

    public static Create_Exercise_suit_2Bean.infos[] getInfos(int type
            ,Create_Exercise_suit_2Bean.groups[] groups,int group_index) {
        return Reading_QuestionsSup.getInfos(type,groups,group_index);
    }

    public static void setList_infos_all2(Create_Exercise_suit_2Bean.infos[] infos
            ,List<Create_Exercise_suit_2Bean.infos> temp_infos) {
        Reading_QuestionsSup.setList_infos_all2(infos,temp_infos);
    }

}
