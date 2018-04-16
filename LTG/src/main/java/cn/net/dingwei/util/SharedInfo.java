package cn.net.dingwei.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.net.dingwei.Bean.TreeListViewBean;
import cn.net.treeListView.Node;

/**
 * 获取保存的信息
 */
public class SharedInfo {

    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static SharedInfo instance = null;

    /* 私有构造方法，防止被实例化 */
    private SharedInfo() {
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static SharedInfo getInstance() {
        if (instance == null) {
            instance = new SharedInfo();
        }
        return instance;
    }


    public  List<Node> datas = new ArrayList<>();

    public  void setdatas(List<Node> datas2){
        datas.addAll(datas2);
    }

    public  List<Node> Getdatas(){
        if (datas == null){
            return null;
        }
        return datas;
    }
    public  void clear(){
        datas.clear();
    }
}
