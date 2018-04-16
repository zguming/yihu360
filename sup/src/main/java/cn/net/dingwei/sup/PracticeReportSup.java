package cn.net.dingwei.sup;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.net.dingwei.Bean.Create_test_suit_2Bean;

/**
 * Created by cd on 2017/5/4.
 */

public class PracticeReportSup {
    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(!Reading_QuestionsSup.flag) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static void setList_infos_cuoti(List<Create_test_suit_2Bean.infos> list_infos_all
            ,List<List<Create_test_suit_2Bean.infos>> list_infos_cuoti) {
        if(!Reading_QuestionsSup.flag) {
            return;
        }
        for (int i = 0; i <list_infos_all.size(); i++) { //把里面的东西丢到集合里面
            for (int j = 0; j < list_infos_cuoti.size(); j++) {
                for (int j2 = 0; j2 < list_infos_cuoti.get(j).size(); j2++) {
                    if(list_infos_all.get(i).getInfo_no()==list_infos_cuoti.get(j).get(j2).getInfo_no()){
                        list_infos_cuoti.get(j).set(j2, list_infos_all.get(i));
                    }
                }
            }
        }
    }

    public static void setList_infos_all(List<Create_test_suit_2Bean.infos> list_infos_all
            ,List<Create_test_suit_2Bean.infos> list_infos) {
        if(!Reading_QuestionsSup.flag) {
            return;
        }
        for (int i = 0; i <list_infos_all.size(); i++) { //把里面的东西丢到全部集合里面
            for (int j = 0; j < list_infos.size(); j++) {
                if(list_infos_all.get(i).getInfo_no()==list_infos.get(j).getInfo_no()){
                    list_infos_all.set(i, list_infos.get(j));
                }
            }
        }
    }
}
