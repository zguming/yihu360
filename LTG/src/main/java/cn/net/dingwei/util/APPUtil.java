package cn.net.dingwei.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import cn.net.dingwei.Bean.BaseInfo_guide_categoryBean;
import cn.net.dingwei.Bean.CitysBean;
import cn.net.dingwei.Bean.ColorBean;
import cn.net.dingwei.Bean.CommonCityListBean;
import cn.net.dingwei.Bean.CommonInfo_API_functions;
import cn.net.dingwei.Bean.CommonInfo_colorBean;
import cn.net.dingwei.Bean.Create_Exercise_suit_2Bean;
import cn.net.dingwei.Bean.Create_exercise_suitBean;
import cn.net.dingwei.Bean.Create_test_suitBean;
import cn.net.dingwei.Bean.Create_test_suit_2Bean;
import cn.net.dingwei.Bean.ExamBean;
import cn.net.dingwei.Bean.ExamGroupBean;
import cn.net.dingwei.Bean.Get_baseinfoBean;
import cn.net.dingwei.Bean.Get_exercise_structureBean;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.Bean.Get_home_suggestBean;
import cn.net.dingwei.Bean.Get_home_suggestBean_url;
import cn.net.dingwei.Bean.Get_point_infoBean;
import cn.net.dingwei.Bean.Get_situationBean;
import cn.net.dingwei.Bean.Get_subjects_progressBean;
import cn.net.dingwei.Bean.Get_test_baseinfoBean;
import cn.net.dingwei.Bean.Get_user_baseinfo;
import cn.net.dingwei.Bean.HomeViewPagerBean;
import cn.net.dingwei.Bean.ImageBean;
import cn.net.dingwei.Bean.Itropage_templatesBean;
import cn.net.dingwei.Bean.Placeholder_textBean;
import cn.net.dingwei.Bean.Submit_exercise_suitBean;
import cn.net.dingwei.Bean.UserinfoBean;

import com.google.gson.Gson;

/**
 * 主要用于解析Json的
 * @author Administrator
 *
 */
public class APPUtil {
	/**
	 * 解析get_user_baseinfo
	 * @param context
	 * @param
	 * @return
	 */
	public static Get_user_baseinfo Get_user_baseinfo(Context context){
		Get_user_baseinfo bean = new Get_user_baseinfo();
		try {
			SharedPreferences sp = context.getSharedPreferences("get_user_baseinfo", Context.MODE_PRIVATE);
			String api_functions = new JSONObject(sp.getString("get_user_baseinfo", "0")).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(api_functions, Get_user_baseinfo.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		MyApplication application = MyApplication.myApplication;

		//application.pay_url = bean.getMembership_pay();
		SharedPreferences sharedPreferences= context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		sharedPreferences.edit().putString("pay_url", bean.getMembership_pay()).commit();
		return bean;

	}
	/**
	 * 获取API接口方法名
	 * @param context
	 * @return
	 */
	public static CommonInfo_API_functions getAI_functions(Context context,String json){
		CommonInfo_API_functions bean = new CommonInfo_API_functions();
		try {
			String api_functions = new JSONObject(json).getJSONObject("data").getString("api_functions");
			Gson gson = new Gson();
			bean = gson.fromJson(api_functions, CommonInfo_API_functions.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;
	}
	/**
	 * 继续
	 * @param context
	 * @return
	 */
	public static Create_Exercise_suit_2Bean create_exercise_suit_2_go_on(Context context){
		Create_Exercise_suit_2Bean bean = new Create_Exercise_suit_2Bean();
		if(null==context){
			return bean;
		}
		try {
			SharedPreferences sp = context.getSharedPreferences("get_exercise_suit", Context.MODE_PRIVATE);
			String st = sp.getString("get_exercise_suit", "0");
			/*st = st.replace("\"images\":\"null\"", "\"images\":null");*/
			String json = new JSONObject(st).getJSONObject("data").getString("suitdata");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Create_Exercise_suit_2Bean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;
	}
	/**
	 * 阅读题
	 * @param
	 * @return
	 */
	public static Create_Exercise_suit_2Bean create_exercise_suit_2(Context context){
		Create_Exercise_suit_2Bean bean = new Create_Exercise_suit_2Bean();
		try {
			SharedPreferences sp = context.getSharedPreferences("create_exercise_suit", Context.MODE_PRIVATE);
			String st = sp.getString("create_exercise_suit", "0");
			/*st = st.replace("\"images\":\"null\"", "\"images\":null");*/
			String json = new JSONObject(st).getJSONObject("data").getString("suitdata");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Create_Exercise_suit_2Bean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// 
		}
		return bean;
	}
	/**
	 * 测验——阅读题
	 * @param
	 * @return
	 */
	public static Create_test_suit_2Bean create_test_suit_2Bean(Context context){
		Create_test_suit_2Bean bean = new Create_test_suit_2Bean();
		try {
			SharedPreferences sp = context.getSharedPreferences("create_test_suit", Context.MODE_PRIVATE);
			String st = sp.getString("create_test_suit", "0");
			/*st = st.replace("\"images\":\"null\"", "\"images\":null");*/
			String json = new JSONObject(st).getJSONObject("data").getString("suitdata");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Create_test_suit_2Bean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// 
		}
		return bean;
	}
	/**
	 * 输入验证码错误
	 * @param json
	 * @return
	 */
	public static String getsend_mobile_verificationcodeError(String json){
		String st = "";
		try {
			st = new JSONObject(json).getJSONObject("error").getString("error_msg");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// 
		}
		return st;
	}
	/**
	 * 解析考点
	 * @param
	 * @return
	 */
	public static Get_point_infoBean Get_point_infoBean(Context context){
		Get_point_infoBean bean = new Get_point_infoBean();
		try {
			SharedPreferences sp = context.getSharedPreferences("get_point_info", Context.MODE_PRIVATE);
			String st = sp.getString("get_point_info", "0");
			String json = new JSONObject(st).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Get_point_infoBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// 
		}
		return bean;
	}
	/**
	 * 解析首页ViewPage里面的点击
	 * @param json
	 * @return
	 */
	public static HomeViewPagerBean getViewPagerClick(String json){
		try {
			json=URLDecoder.decode(json, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//
		}
		Gson gson = new Gson();
		HomeViewPagerBean bean= new HomeViewPagerBean();
		bean = gson.fromJson(json, HomeViewPagerBean.class);
		return bean;
	}
	/**
	 * 解析指南Bean
	 * @param context
	 * @return
	 */
	public static Get_guide_msg_listBean get_guide_msg_list(Context context){
		Get_guide_msg_listBean bean = new Get_guide_msg_listBean();
		try {
			SharedPreferences sp = context.getSharedPreferences("get_guide_msg_list", Context.MODE_PRIVATE);
			String st = sp.getString("get_guide_msg_list", "0");
			st = st.replace("\"images\":\"null\"", "\"images\":null");
			String json = new JSONObject(st).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Get_guide_msg_listBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//
		}
		return bean;
	}
	/**
	 * 获取指南的消息类别
	 * @param context
	 * @return
	 */
	public static BaseInfo_guide_categoryBean getGuide(Context context){
		BaseInfo_guide_categoryBean bean = new BaseInfo_guide_categoryBean();
		try {
			SharedPreferences sp = context.getSharedPreferences("get_baseinfo", Context.MODE_PRIVATE);
			String json = new JSONObject(sp.getString("get_baseinfo", "0")).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, BaseInfo_guide_categoryBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// 
		}
		return bean;
	}

	public static Create_test_suitBean Create_test_suitBean(Context context){
		Create_test_suitBean bean = new Create_test_suitBean();
		try {
			SharedPreferences sp = context.getSharedPreferences("create_test_suit", Context.MODE_PRIVATE);
			String st = sp.getString("create_test_suit", "0");
			st = st.replace("\"img\":\"null\"", "\"img\":null");
			String json = new JSONObject(st).getJSONObject("data").getString("suitdata");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Create_test_suitBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;

	}

	public static Get_test_baseinfoBean Get_test_baseinfo(Context context){
		Get_test_baseinfoBean bean = new Get_test_baseinfoBean();
		try {
			SharedPreferences sp = context.getSharedPreferences("get_test_baseinfo", Context.MODE_PRIVATE);
			String json = new JSONObject(sp.getString("get_test_baseinfo", "0")).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Get_test_baseinfoBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}

		return bean;

	}

	public static Get_exercise_structureBean Get_exercise_structure(Context context){
		Get_exercise_structureBean bean = new Get_exercise_structureBean();
		SharedPreferences sp = context.getSharedPreferences("get_exercise_structure", Context.MODE_PRIVATE);
		try {
			String json = new JSONObject(sp.getString("get_exercise_structure", "0")).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Get_exercise_structureBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;

	}
	/**
	 * 解析测验提交套题答案
	 */
	public static Submit_exercise_suitBean submit_test_suit(Context context){
		Submit_exercise_suitBean bean = new Submit_exercise_suitBean();
		SharedPreferences sp = context.getSharedPreferences("submit_test_suit", Context.MODE_PRIVATE);
		try {
			String json = new JSONObject(sp.getString("submit_test_suit", "0")).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Submit_exercise_suitBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;

	}
	/**
	 * 解析测验提交套题答案2
	 */
	public static Submit_exercise_suitBean submit_test_suit(String suit_json){
		Submit_exercise_suitBean bean = new Submit_exercise_suitBean();
		//SharedPreferences sp = context.getSharedPreferences("submit_test_suit", Context.MODE_PRIVATE);
		try {
			String json = new JSONObject(suit_json).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Submit_exercise_suitBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;

	}

	/**
	 * 解析提交套题答案
	 */
	public static Submit_exercise_suitBean Submit_exercise_suitBean(Context context){
		Submit_exercise_suitBean bean = new Submit_exercise_suitBean();
		SharedPreferences sp = context.getSharedPreferences("submit_exercise_suit", Context.MODE_PRIVATE);
		try {
			String json = new JSONObject(sp.getString("submit_exercise_suit", "0")).getString("data");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Submit_exercise_suitBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;

	}
	/**
	 * 继续做题
	 * @param context
	 * @return
	 */
	public static Create_exercise_suitBean get_exercise_suit(Context context){
		Create_exercise_suitBean bean = new Create_exercise_suitBean();
		SharedPreferences sp = context.getSharedPreferences("get_exercise_suit", Context.MODE_PRIVATE);
		String st = sp.getString("get_exercise_suit", "0");
		st = st.replace("\"img\":\"null\"", "\"img\":null");
		try {
			String json = new JSONObject(st).getJSONObject("data").getString("suitdata");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Create_exercise_suitBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;
	}

	/**
	 * 解析考题
	 * @param context
	 * @return
	 */
	public static Create_exercise_suitBean create_exercise_suit(Context context){
		Create_exercise_suitBean bean = new Create_exercise_suitBean();
		SharedPreferences sp = context.getSharedPreferences("create_exercise_suit", Context.MODE_PRIVATE);
		String st = sp.getString("create_exercise_suit", "0");
		st = st.replace("\"img\":\"null\"", "\"img\":null");
		try {
			String json = new JSONObject(st).getJSONObject("data").getString("suitdata");
			Gson gson = new Gson();
			bean = gson.fromJson(json, Create_exercise_suitBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;
	}
	/**
	 *
	 * @param context
	 * @return
	 */
	public static List<Get_subjects_progressBean> get_subjects_progress(Context context){
		List<Get_subjects_progressBean> list = new ArrayList<Get_subjects_progressBean>();
		if(null!=context){
			SharedPreferences sp = context.getSharedPreferences("get_subjects_progress", Context.MODE_PRIVATE);
			try {
				JSONArray array = new JSONObject(sp.getString("get_subjects_progress", "0")).getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					Get_subjects_progressBean bean = new Get_subjects_progressBean();
					Gson gson = new Gson();
					bean = gson.fromJson(array.get(i).toString(), Get_subjects_progressBean.class);
					list.add(bean);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block

			}
		}
		return list;
	}
	/**
	 * 获取BaseInfo的信息
	 * @param context
	 * @return
	 */
	public static  List<Get_baseinfoBean> get_baseinfoBean(Context context){
		List<Get_baseinfoBean> list = new ArrayList<Get_baseinfoBean>();
		Get_baseinfoBean bean = null;
		SharedPreferences sp = context.getSharedPreferences("get_baseinfo", Context.MODE_PRIVATE);
		try {
			JSONArray array = new JSONObject(sp.getString("get_baseinfo", "0")).getJSONObject("data").getJSONArray("exam_structure");
			for (int i = 0; i < array.length(); i++) {
				bean = new Get_baseinfoBean();
				Gson gson = new Gson();
				bean = gson.fromJson(array.get(i).toString(), Get_baseinfoBean.class);
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return list;
	}

	/**
	 * 报考学科分类
	 * @param context
	 * @return
     */
	public static  ExamGroupBean getScreenExamGroupBean(Context context){
		ExamGroupBean screenListBean = new ExamGroupBean();
		SharedPreferences sp = context.getSharedPreferences("get_baseinfo", Context.MODE_PRIVATE);

		try {
			JSONObject jsonObject = new JSONObject(sp.getString("get_baseinfo", "0")).getJSONObject("data")
					.getJSONObject("exam_group");
			Gson gson = new Gson();
			screenListBean = gson.fromJson(jsonObject.toString(),ExamGroupBean.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return screenListBean;
	}

	/**
	 * 解析 get_situation
	 * @param context
	 * @return
	 */
	public static List<Get_situationBean> Get_situation(Context context){
		if(null==context){
		}
		SharedPreferences sp = context.getSharedPreferences("get_situation", Context.MODE_PRIVATE);
		List<Get_situationBean> list = new ArrayList<Get_situationBean>();
		String json = sp.getString("get_situation", "0");
		if(json.equals("0")){
			return null;
		}

		try {
			JSONObject jsonObject	=new JSONObject(json).getJSONObject("data");
			String exam = jsonObject.getString("exam");
			JSONArray array = jsonObject.getJSONArray("subjects");
			for (int i = 0; i < array.length(); i++) {
				JSONObject temp	=(JSONObject) array.get(i);
				Get_situationBean bean = new Get_situationBean();
				bean.setK(temp.getString("k"));
				bean.setTot_r(temp.getString("tot_r"));
				bean.setExe_c(temp.getString("exe_c"));
				bean.setExe_s(temp.getString("exe_s"));
				bean.setExe_r(temp.getString("exe_r"));
				bean.setWro_c(temp.getString("wro_c"));
				bean.setWro_t(temp.getString("wro_t"));
				bean.setWro_w(temp.getString("wro_w"));
				bean.setWro_r(temp.getString("wro_r"));
				if(temp.getString("points").equals("null")){
					bean.setPoints(null);
				}else{
					JSONArray array2 = temp.getJSONArray("points");
					Get_situationBean.points [] points = new Get_situationBean.points [array2.length()];
					for (int j = 0; j < array2.length(); j++) {
						JSONObject temp2=(JSONObject) array2.get(i);
						Get_situationBean.points point = new Get_situationBean().new points();
						point.setId(temp2.getInt("id"));
						point.setTot_r(temp2.getString("tot_r"));
						point.setExe_c(temp2.getString("exe_c"));
						point.setExe_s(temp2.getString("exe_s"));
						point.setExe_r(temp2.getString("exe_r"));
						point.setWro_c(temp2.getString("wro_c"));
						point.setWro_t(temp2.getString("wro_t"));
						point.setWro_w(temp2.getString("wro_w"));
						point.setWro_r(temp2.getString("wro_r"));
						points[j] = point;
					}
					bean.setPoints(points);
				}


				Gson gson = new Gson();
				bean = gson.fromJson(array.get(i).toString(), Get_situationBean.class);
				bean.setExam(exam);
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return list;

	}
	/**
	 *  解析get_home_suggest
	 */
	public static List<Get_home_suggestBean> get_home_suggest(Context context){
		List<Get_home_suggestBean> list = new ArrayList<Get_home_suggestBean>();
		Get_home_suggestBean bean;
		if(null==context){
		}
		SharedPreferences sp = context.getSharedPreferences("get_home_suggest", Context.MODE_PRIVATE);

		Gson gson = new Gson();
		try {
			JSONArray array = new JSONObject(sp.getString("get_home_suggest", "0")).getJSONObject("data").getJSONArray("suggests");
			for (int i = 0; i < array.length(); i++) {
				bean = new Get_home_suggestBean();
				bean = gson.fromJson(array.get(i).toString(), Get_home_suggestBean.class);
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return list;
	}
	/**
	 *  解析get_home_suggest  是网页链接的时候
	 */
	public static Get_home_suggestBean_url get_home_suggest_url(Context context){
		Get_home_suggestBean_url bean =new Get_home_suggestBean_url();
		try {
			if(null==context){

			}else{
				SharedPreferences sp = context.getSharedPreferences("get_home_suggest", Context.MODE_PRIVATE);
				Gson gson = new Gson();
				String array = new JSONObject(sp.getString("get_home_suggest", "0")).getString("data");
				bean = gson.fromJson(array, Get_home_suggestBean_url.class);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;
	}
	/**
	 * 获取颜色Bean
	 * @param context
	 * @return
	 */
	public static ColorBean getColorBean(Context context){
		MyApplication  application = MyApplication.myApplication;
		CommonInfo_colorBean bean = new CommonInfo_colorBean();
		Gson gson = new Gson();
		SharedPreferences sp = context.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		try {
			JSONObject jsonObject = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("style");
			String json = jsonObject.getString("color");
			bean = gson.fromJson(json, CommonInfo_colorBean.class);
			//加载图片
			String icon = jsonObject.getString("icon");

			application.imageBean = new ImageBean();
			application.imageBean= gson.fromJson(icon, ImageBean.class);
			//加载支付链接
			application.match_url = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("match_url");
			application.choose_pay = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("choose_pay");
			application.buys = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("buys");
			application.recharge = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("recharge");
			String  feedback = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("feedback");
			String  botmsglist = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("botmsglist");
			String  buys_list = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("buys_list");
			//单独支付
			String  adv_project_pay = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("adv_project_pay");

			SharedPreferences sharedPreferences= context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString("feedback", feedback);
			editor.putString("adv_project_pay", adv_project_pay);
			editor.putString("botmsglist", botmsglist);
			editor.commit();

			application.guide_listview_loadurl = new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getJSONObject("webview_url").getString("msg_view");
			//application.placeholder_textBean = gson.fromJson(new JSONObject(sp.getString("get_commoninfo", "0")).getJSONObject("data").getString("placeholder_text"), Placeholder_textBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		if(null==bean){
			return null;
		}
		SharedPreferences sharedPreferences = context.getSharedPreferences("commoninfo", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		ColorBean colorBean = new ColorBean();
		editor.putInt("color_1", Color.parseColor(bean.getColor_1().replace(" ", "")));
		editor.putInt("color_2", Color.parseColor(bean.getColor_2().replace(" ", "")));
		editor.putInt("color_3", Color.parseColor(bean.getColor_3().replace(" ", "")));
		editor.putInt("color_4", Color.parseColor(bean.getColor_4().replace(" ", "")));
		editor.putInt("color_5", Color.parseColor(bean.getColor_5().replace(" ", "")));
		editor.putInt("color_6", Color.parseColor(bean.getColor_6().replace(" ", "")));

		editor.putInt("color_101", Color.parseColor(bean.getColor_101().replace(" ", "")));
		editor.putInt("color_102", Color.parseColor(bean.getColor_102().replace(" ", "")));
		editor.putInt("color_103", Color.parseColor(bean.getColor_103().replace(" ", "")));
		editor.putInt("color_101_1", Color.parseColor(bean.getColor_101_1().replace(" ", "")));
		editor.putInt("color_102_1", Color.parseColor(bean.getColor_102_1().replace(" ", "")));
		editor.putInt("color_103_1", Color.parseColor(bean.getColor_103_1().replace(" ", "")));

		editor.putInt("bgcolor_1", Color.parseColor(bean.getBgcolor_1().replace(" ", "")));
		editor.putInt("bgcolor_2", Color.parseColor(bean.getBgcolor_2().replace(" ", "")));
		editor.putInt("bgcolor_3", Color.parseColor(bean.getBgcolor_3().replace(" ", "")));
		editor.putInt("bgcolor_4", Color.parseColor(bean.getBgcolor_4().replace(" ", "")));
		editor.putInt("bgcolor_5", Color.parseColor(bean.getBgcolor_5().replace(" ", "")));
		editor.putInt("bgcolor_6", Color.parseColor(bean.getBgcolor_6().replace(" ", "")));
		editor.putInt("bgcolor_7", Color.parseColor(bean.getBgcolor_7().replace(" ", "")));
		editor.putInt("bgcolor_8", Color.parseColor(bean.getBgcolor_8().replace(" ", "")));
		editor.putInt("bgcolor_9", Color.parseColor(bean.getBgcolor_9().replace(" ", "")));
		editor.putInt("bgcolor_10", Color.parseColor(bean.getBgcolor_10().replace(" ", "")));
		editor.putInt("fontcolor_1", Color.parseColor(bean.getFontcolor_1().replace(" ", "")));
		editor.putInt("fontcolor_2", Color.parseColor(bean.getFontcolor_2().replace(" ", "")));
		editor.putInt("fontcolor_3", Color.parseColor(bean.getFontcolor_3().replace(" ", "")));
		editor.putInt("fontcolor_4", Color.parseColor(bean.getFontcolor_4().replace(" ", "")));
		editor.putInt("fontcolor_5", Color.parseColor(bean.getFontcolor_5().replace(" ", "")));
		editor.putInt("fontcolor_6", Color.parseColor(bean.getFontcolor_6().replace(" ", "")));
		editor.putInt("fontcolor_7", Color.parseColor(bean.getFontcolor_7().replace(" ", "")));
		editor.putInt("fontcolor_8", Color.parseColor(bean.getFontcolor_8().replace(" ", "")));
		editor.putInt("fontcolor_9", Color.parseColor(bean.getFontcolor_9().replace(" ", "")));
		editor.putInt("fontcolor_10", Color.parseColor(bean.getFontcolor_10().replace(" ", "")));
		editor.putInt("fontcolor_11", Color.parseColor(bean.getFontcolor_11().replace(" ", "")));
		editor.putInt("fontcolor_12", Color.parseColor(bean.getFontcolor_12().replace(" ", "")));
		editor.putInt("fontcolor_13", Color.parseColor(bean.getFontcolor_13().replace(" ", "")));

		//设置图标名称
		editor.putString("Nav_1", MyFlg.setImageFileName(application.imageBean.getNav_1(),context));
		editor.putString("Nav_2", MyFlg.setImageFileName(application.imageBean.getNav_2(),context));
		editor.putString("Nav_3", MyFlg.setImageFileName(application.imageBean.getNav_3(),context));
		editor.putString("Nav_4", MyFlg.setImageFileName(application.imageBean.getNav_4(),context));
		editor.putString("Nav_1_active", MyFlg.setImageFileName(application.imageBean.getNav_1_active(),context));
		editor.putString("Nav_2_active", MyFlg.setImageFileName(application.imageBean.getNav_2_active(),context));
		editor.putString("Nav_3_active", MyFlg.setImageFileName(application.imageBean.getNav_3_active(),context));
		editor.putString("Nav_4_active", MyFlg.setImageFileName(application.imageBean.getNav_4_active(),context));
		editor.putString("Subject_1", MyFlg.setImageFileName(application.imageBean.getSubject_1(),context));
		editor.putString("Subject_1_white", MyFlg.setImageFileName(application.imageBean.getSubject_1_white(),context));
		editor.putString("Home_personal", MyFlg.setImageFileName(application.imageBean.getHome_personal(),context));
		editor.putString("Home_slidebar",MyFlg.setImageFileName(application.imageBean.getHome_slidebar(),context));
		editor.putString("Right_btn", MyFlg.setImageFileName(application.imageBean.getRight_btn(),context));
		editor.putString("Return_up", MyFlg.setImageFileName(application.imageBean.getReturn_up(),context));
		editor.putString("Radio_uncheck",MyFlg.setImageFileName(application.imageBean.getRadio_uncheck(),context));
		editor.putString("Radio_check",MyFlg.setImageFileName(application.imageBean.getRadio_check(),context));
		editor.putString("Test_play",MyFlg.setImageFileName(application.imageBean.getTest_play(),context));
		editor.putString("Test_pause",MyFlg.setImageFileName(application.imageBean.getTest_pause(),context));
		editor.putString("Kaopin",MyFlg.setImageFileName(application.imageBean.getKaopin(),context));
		editor.putString("Home_share",MyFlg.setImageFileName(application.imageBean.getHome_share(),context));
		editor.putString("share",MyFlg.setImageFileName(application.imageBean.getShare(),context));
		editor.putString("Lianxi",MyFlg.setImageFileName(application.imageBean.getLianxi(),context));
		editor.putString("Lianxi_w",MyFlg.setImageFileName(application.imageBean.getLianxi_w(),context));
		editor.putString("Ctgg_w",MyFlg.setImageFileName(application.imageBean.getCtgg_w(),context));
		editor.putString("Geren",MyFlg.setImageFileName(application.imageBean.getGeren(),context));
		editor.putString("Unicon",MyFlg.setImageFileName(application.imageBean.getUnicon(),context));
		editor.putString("Vipicon",MyFlg.setImageFileName(application.imageBean.getVipicon(),context));
		editor.putString("Huakuai",MyFlg.setImageFileName(application.imageBean.getHuakuai(),context));
		editor.putString("gongjuxiang",MyFlg.setImageFileName(application.imageBean.getGongjuxiang(),context));
		//存的URL
		editor.putString("jiucuo",application.imageBean.getJiucuo());
		editor.putString("shoucang_0",application.imageBean.getShoucang_0());
		editor.putString("shoucang_1",application.imageBean.getShoucang_1());
		editor.commit();
		return colorBean;

	}
	/**
	 * 获取考试及时间
	 * @param context
	 * @return
	 */
	public static List<ExamBean> getexam_structure(Context context){
		List<ExamBean> list = new ArrayList<ExamBean>();
		SharedPreferences sp = context.getSharedPreferences("get_baseinfo", Context.MODE_PRIVATE);
		String json = sp.getString("get_baseinfo", "0");
		JSONObject jsonObject;
		ExamBean bean = null;
		try {
			jsonObject = new JSONObject(json);
			JSONArray array = jsonObject.getJSONObject("data").getJSONArray("exam_structure");
			for (int i = 0; i < array.length(); i++) {
				Gson gson = new Gson();
				bean = new ExamBean();
				bean = gson.fromJson(array.get(i).toString(), ExamBean.class);
				list.add(bean);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return list;
	}
	/**
	 * 获取城市列表
	 * @param context
	 * @return
	 */
	public static CitysBean getCommonInfo_citys(Context context){
		List<CommonCityListBean> list = new ArrayList<CommonCityListBean>();
		SharedPreferences sp = context.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		String user_json = sp.getString("get_commoninfo", "0");
		CitysBean bean = new CitysBean();
		try {
			String json = new JSONObject(user_json).getJSONObject("data").getString("citys");
			Gson gson = new Gson();
			bean=gson.fromJson(json, CitysBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return bean;

	}
	/**
	 * 获取城市列表
	 * @param context
	 * @return
	 */
	public static List<CommonCityListBean> getCommonInfo_city(Context context){
		List<CommonCityListBean> list = new ArrayList<CommonCityListBean>();
		SharedPreferences sp = context.getSharedPreferences("get_commoninfo", Context.MODE_PRIVATE);
		String user_json = sp.getString("get_commoninfo", "0");
		try {
			JSONArray array = new JSONObject(user_json).getJSONObject("data").getJSONArray("citylist");
			for (int i = 0; i < array.length(); i++) {
				CommonCityListBean bean = new CommonCityListBean();
				JSONObject temp = (JSONObject) array.get(i);
				bean.setK(temp.getString("k"));
				bean.setN(temp.getString("n"));
				bean.setIoskey(temp.getString("ioskey"));
				bean.setAndkey(temp.getString("andkey"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}
		return list;

	}

	/**
	 * 获取用户信息
	 * @return
	 */
	public static UserinfoBean getUser_isRegistered(Context context){
		SharedPreferences sp = context.getSharedPreferences("get_userinfo", Context.MODE_PRIVATE);
		String user_json = sp.getString("get_userinfo", "0");
		if(user_json.equals("0")){
			return null;
		}

		JSONObject jsonObject = null;
		UserinfoBean bean = null;
		try {
			jsonObject = new JSONObject(user_json);
			String data_json = jsonObject.getJSONObject("data").getString("userinfo");
			Gson gson = new Gson();
			bean = new UserinfoBean();
			bean = gson.fromJson(data_json, UserinfoBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block

			try {
				jsonObject = new JSONObject(user_json);
				String data_json = jsonObject.getString("data");
				Gson gson = new Gson();
				bean = new UserinfoBean();
				bean = gson.fromJson(data_json, UserinfoBean.class);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return bean;
	}
	/**
	 * 获取引导屏所需图片URL集合
	 */
	public static List<String> getImageUrlList(String Itropage_templatesBeanJson){
		List<String> imageURL = new ArrayList<String>();
		JSONArray array;
		try {
			array = new JSONObject(Itropage_templatesBeanJson).getJSONObject("data").getJSONArray("intropage_templates");
			for (int i = 0; i < array.length(); i++) {
				JSONObject temp = (JSONObject) array.get(i);
				JSONArray array2 = new JSONArray(temp.getString("pages"));
				for (int j = 0; j < array2.length(); j++) {
					JSONObject temp2 = (JSONObject) array2.get(j);
					imageURL.add(temp2.getString("img"));
					JSONArray btnArr = new JSONArray(temp2.getString("btn"));
					for (int k = 0; k < btnArr.length(); k++) {
						JSONObject temp_btn = (JSONObject) btnArr.get(k);
						imageURL.add(temp_btn.getString("btn_img"));
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}

		return imageURL;

	}
	/**
	 * 获取当前显示的引导屏方案
	 * @param json
	 * @param sp  引导屏的SharedPreferences
	 * @return
	 */
	public static Itropage_templatesBean getYindaoPing(String json,SharedPreferences sp){
		List<Itropage_templatesBean> list = new ArrayList<Itropage_templatesBean>();
		try {
			JSONArray array = new JSONObject(json).getJSONObject("data").getJSONArray("intropage_templates");
			for (int i = 0; i < array.length(); i++) {
				Itropage_templatesBean bean=new Itropage_templatesBean();
				Gson gson = new Gson();
				bean = gson.fromJson(array.get(i).toString(), Itropage_templatesBean.class);
				list.add(bean);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}

		int small=-1;
		Itropage_templatesBean returnbean=null;
		for (int i = 0; i <list.size(); i++) {
			Itropage_templatesBean bean = list.get(i);
			String from_data = bean.getFrom_date();
			String to_data = bean.getTo_date();
			if( getNowTime(from_data, to_data)){ //判断是否在规定可以显示的时间内
				if(sp.getInt(bean.getK(), 0) < bean.getMax_times() || bean.getMax_times()==0){ //判断显示的次数
					//判断时间间隔
					if(System.currentTimeMillis()  - sp.getLong(bean.getK()+"beforShow", 0)>bean.getTime_interval()*1000){
						if(bean.getPriority()>small){
							small = bean.getPriority();
							returnbean = bean;
						}
					}
				}
			}
		}
		if(null !=returnbean){
			//记录显示的时间
			sp.edit().putLong(returnbean.getK()+"beforShow", System.currentTimeMillis()).commit();
			//如果Bean 不为NUll 记录已显示次数度多一次
			sp.edit().putInt(returnbean.getK(), sp.getInt(returnbean.getK(), 0)+1).commit();
		}
		return returnbean;
	}
	/**
	 * unicode 转换成 中文
	 * @param theString
	 * @return
	 */

	public static String decodeUnicode(String theString) {

		char aChar;

		int len = theString.length();

		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {

			aChar = theString.charAt(x++);

			if (aChar == '\\') {

				aChar = theString.charAt(x++);

				if (aChar == 'u') {

					// Read the xxxx

					int value = 0;

					for (int i = 0; i < 4; i++) {

						aChar = theString.charAt(x++);

						switch (aChar) {

							case '0':

							case '1':

							case '2':

							case '3':

							case '4':

							case '5':

							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
										"Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';

					else if (aChar == 'n')

						aChar = '\n';

					else if (aChar == 'f')

						aChar = '\f';

					outBuffer.append(aChar);

				}

			} else

				outBuffer.append(aChar);

		}

		return outBuffer.toString();

	}
	/**
	 *
	 * @param from_data1
	 * @param to_date1
	 * @return True 为在合理时间内
	 */
	public static Boolean getNowTime(String from_data1,String to_date1){
		Boolean boolean1=false;
		Date date=new Date(System.currentTimeMillis());
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");//24小时制,12小时制则HH为hh
		//String UserTime = sdformat.format(date);
		try {
			Date from_data  =  sdformat.parse(from_data1);
			Date to_date    =  sdformat.parse(to_date1);
			long time_now   =  date.getTime();
			long time_from  =  from_data.getTime();
			long time_to    =  to_date.getTime();
			if(time_now>=time_from &&time_now<=time_to ){
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block

		}


		return boolean1;

	}

	/**
	 * 	返回当前时间  格式 2015/07/15
	 * @return
	 */
	public static String getNowTime(){
		Date date=new Date(System.currentTimeMillis());
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//24小时制,12小时制则HH为hh
		String UserTime = sdformat.format(date);
		return UserTime;
	}
	/**
	 * 转换时间格式
	 * time  2015/5/6
	 * @return 2015 年05月06日
	 */
	public static String setTime(String time){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		String st ="";
		try {
			Date date = ft.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			st =cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DATE)+"日";
		} catch (ParseException e) {
			// TODO Auto-generated catch block

		}
		return st;

	}
}
