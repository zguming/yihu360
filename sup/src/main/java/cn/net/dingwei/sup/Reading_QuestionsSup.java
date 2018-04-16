package cn.net.dingwei.sup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Create_test_suit_2Bean;

/**
 * Created by cd on 2017/5/3.
 */

public class Reading_QuestionsSup {

    public static boolean flag;

    /**
     * 获取错题解析的数据
     */
    public static Create_Exercise_suit_2Bean.infos[] cuotijiexi(Create_Exercise_suit_2Bean.infos[] infos) {
        if(!flag) {
            return null;
        }
        List<Create_Exercise_suit_2Bean.infos> list_infos = new ArrayList<>();
        for (int i = 0; i < infos.length; i++) {
            List<Create_Exercise_suit_2Bean.questions> list_question = new ArrayList<>();
            for (int j = 0; j < infos[i].getQuestions().length; j++) {
                String right_answer = "";
                String[] sts = infos[i].getQuestions()[j].getCorrect();
                for (int k = 0; k < sts.length; k++) {
                    right_answer = right_answer + sts[k];
                }
                String user_answer = infos[i].getQuestions()[j].getAnswer();
                if (!right_answer.equals(user_answer)) {
                    list_question.add(infos[i].getQuestions()[j]);
                }
            }
            if (list_question.size() > 0) {
                Create_Exercise_suit_2Bean.infos group_temp = infos[i];
                Create_Exercise_suit_2Bean.questions[] question = new Create_Exercise_suit_2Bean.questions[list_question.size()];
                for (int j = 0; j < question.length; j++) {
                    question[j] = list_question.get(j);
                }
                group_temp.setQuestions(question);
                list_infos.add(group_temp);
            }
        }
        Create_Exercise_suit_2Bean.infos[] groups_temp = new Create_Exercise_suit_2Bean.infos[list_infos.size()];
        for (int i = 0; i < list_infos.size(); i++) {
            groups_temp[i] = list_infos.get(i);
        }
        infos = groups_temp;
        return infos;

    }

    /**
     * 获取正确答案 字符串类型
     *
     * @param data
     * @return
     */
    public static String getcorrect(String[] data) {
        if(!flag) {
            return null;
        }
        String st = "";
        for (int i = 0; i < data.length; i++) {
            st = st + data[i];
        }
        return st;

    }


    /**
     * 获取问题对象
     * @param infos
     * @param index
     * @return
     */
    public static Create_Exercise_suit_2Bean.questions getQuestionBean(Create_Exercise_suit_2Bean.infos[] infos,int index) {
        if(!flag) {
            return null;
        }
        int questionIndex = 0;
        if (null == infos[index].getQuestion_index()) {
            questionIndex = 0;
        } else {
            questionIndex = infos[index].getQuestion_index();
        }
        Create_Exercise_suit_2Bean.questions question = infos[index].getQuestions()[questionIndex];
        return question;
    }

    /**
     * 获取当前的问题 位置
     *
     * @return
     */
    public static int getQUstionIndex(Create_Exercise_suit_2Bean.infos[] infos,int index) {
        if(!flag) {
            return -1;
        }
        //设置顶部
        int questionIndex = 0;
        if (null == infos[index].getQuestion_index()) {
            questionIndex = 0;
        } else {
            questionIndex = infos[index].getQuestion_index();
        }
        //Create_Exercise_suit_2Bean.questions question  = infos[index].getQuestions()[questionIndex];
        return questionIndex;
    }


    /**
     * 获取infos数据
     * @param type 0：正常   1:错题解析 2：整组解析  3:继续练习   4 笔记详情(笔记点击进来的) 5 全部解析（所有题）
     * @param groups 所有组
     * @param group_index 当前是第几组
     * @return
     */
    public static Create_Exercise_suit_2Bean.infos[] getInfos(int type
            ,Create_Exercise_suit_2Bean.groups[] groups,int group_index) {
        if(!flag) {
            return null;
        }
        Create_Exercise_suit_2Bean.infos[] infos;
        if (type == 5) {
            List<Create_Exercise_suit_2Bean.infos> temp_list = new ArrayList<>();
            for (int i = 0; i < groups.length; i++) {
                Create_Exercise_suit_2Bean.infos[] tem_infos = groups[i].getInfos();
                for (int j = 0; j < tem_infos.length; j++) {
                    temp_list.add(tem_infos[j]);
                }
            }
            infos = new Create_Exercise_suit_2Bean.infos[temp_list.size()];
            for (int i = 0; i < temp_list.size(); i++) {
                infos[i] = temp_list.get(i);
            }
        } else {
            infos = groups[group_index].getInfos();
        }
        return infos;
    }

    public static void setList_infos_all2(Create_Exercise_suit_2Bean.infos[] infos
            ,List<Create_Exercise_suit_2Bean.infos> temp_infos) {
        if(!Reading_QuestionsSup.flag) {
            return;
        }
        for (int i = 0; i < infos.length; i++) {
            for (int j = 0; j < temp_infos.size(); j++) {
                if (infos[i].getInfo_no() == temp_infos.get(j).getInfo_no()) {
                    infos[i] = temp_infos.get(j);
                }
            }
        }
    }

}
