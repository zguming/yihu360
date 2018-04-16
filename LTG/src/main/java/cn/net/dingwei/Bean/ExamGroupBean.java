package cn.net.dingwei.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ExamGroupBean {

    /**
     * content : [{"key":"全部","name":"全部"},{"key":"招聘","name":"招聘"},{"key":"资格证","name":"资格证"}]
     * title : 报考学科分类
     */

    public String title;
    /**
     * key : 全部
     * name : 全部
     */

    public List<ContentBean> content;

    public static class ContentBean {
        public String key;
        public String name;
        public int listTpye;
        public int getListTpye() {
            return listTpye;
        }

        public void setListTpye(int listTpye) {
            this.listTpye = listTpye;
        }
    }
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String subTitle;

    @Override
    public String toString() {
        return "ExamGroupBean{" +
                "title='" + title + '\'' +
                ", content=" + content +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }
}
