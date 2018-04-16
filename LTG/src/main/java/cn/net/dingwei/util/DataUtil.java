package cn.net.dingwei.util;

import java.util.List;

import android.app.Application;
import android.content.Context;

import cn.net.dingwei.Bean.Get_subjects_progressBean;
import cn.net.dingwei.Bean.UtilBean;

/**
 * 用于处理一些数据的合并等
 * @author Administrator
 *
 */
public class DataUtil extends Application{
	/**
	 * 答题页面 设置文字paddingtop  使其第一行文字位于左边图片的中间位置
	 */
	public static int answer_text_padingtop;
	/**
	 * 获取用户和考试信息
	 */
	public static void getUserInfoAndBaseInfo(Context context) {
		// TODO Auto-generated method stub
		MyApplication application = MyApplication.myApplication;
		//用户信息
		application.userInfoBean =APPUtil.getUser_isRegistered(context);
		//考试  科目等信息
		UtilBean.list_Get_baseinfoBean=APPUtil.get_baseinfoBean(context);
		UtilBean.screenExamGroupBean = APPUtil.getScreenExamGroupBean(context);
	}

	public static void setGet_baseinfoBean_Subject_progress(Context context){
		if(null==UtilBean.list_Get_baseinfoBean){
			UtilBean.list_Get_baseinfoBean=APPUtil.get_baseinfoBean(context);
		}
		List<Get_subjects_progressBean> list = APPUtil.get_subjects_progress(context);
		for (int i = 0; i < UtilBean.list_Get_baseinfoBean.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).getExam().equals(UtilBean.list_Get_baseinfoBean.get(i).getK())){
					for (int j2 = 0; j2 < UtilBean.list_Get_baseinfoBean.get(i).getSubjects().length; j2++) {
						for (int k = 0; k < list.get(j).getSubjects().length; k++) {
							if(UtilBean.list_Get_baseinfoBean.get(i).getSubjects()[j2].getK().equals(list.get(j).getSubjects()[k].getK())){
								UtilBean.list_Get_baseinfoBean.get(i).getSubjects()[j2].setProgress(list.get(j).getSubjects()[k].getProgress()+"");
							}
						}
					}
				}
			}
		}
	}
}
