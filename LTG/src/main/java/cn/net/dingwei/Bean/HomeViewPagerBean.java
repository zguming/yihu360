package cn.net.dingwei.Bean;

import java.io.Serializable;

/**
 * 首页的ViewPager点击时加载的JSON
 * @author Administrator
 *
 */
public class HomeViewPagerBean implements Serializable{
	private String method;
	private String exercises_type;
	private String exercises_option;
	private String url;
	private String suit_id;

	public String getSuit_id() {
		return suit_id;
	}
	public void setSuit_id(String suit_id) {
		this.suit_id = suit_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getExercises_type() {
		return exercises_type;
	}
	public void setExercises_type(String exercises_type) {
		this.exercises_type = exercises_type;
	}
	public String getExercises_option() {
		return exercises_option;
	}
	public void setExercises_option(String exercises_option) {
		this.exercises_option = exercises_option;
	}
	@Override
	public String toString() {
		return "HomeViewPagerBean [method=" + method + ", exercises_type="
				+ exercises_type + ", exercises_option=" + exercises_option
				+ ", url=" + url + ", suit_id=" + suit_id + "]";
	}

}
