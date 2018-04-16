package cn.net.dingwei.sup;

import java.util.List;

import cn.net.dingwei.Bean.Create_test_suit_2Bean;

/**
 * Created by cd on 2017/5/4.
 */

public class Reading_testingSup {
    public static int getQuestionIndex(List<Create_test_suit_2Bean.infos> list_infos, int point){
        if(!Reading_QuestionsSup.flag) {
            return -1;
        }
        int temp_index=0;
        if(null==list_infos.get(point).getQuestion_index()){
            temp_index=0;
        }else{
            temp_index = list_infos.get(point).getQuestion_index();
        }
        return temp_index;
    }

    public static int getRemainSum(List<Create_test_suit_2Bean.infos> list_infos) {
        if(!Reading_QuestionsSup.flag) {
            return -1;
        }
        int remainsum = 0;
        for (int i = 0; i < list_infos.size(); i++) {
            if(list_infos.get(i).getInfo_no()!=-999){
                for (int j = 0; j < list_infos.get(i).getQuestions().length; j++) {
                    Create_test_suit_2Bean.questions question=list_infos.get(i).getQuestions()[j];
                    if(question.getAnswer().equals("") || question.getAnswer().equals("null")){
                        remainsum++;
                    }
                }
            }
        }
        return remainsum;
    }
}
