package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Submit_exercise_suitBean implements Serializable{
	private String suit_id;
	private String test_time_limit;
	private String used_time;
	private String score_total;
	private String scoret;
	private conclusion conclusion;
	
	public String getTest_time_limit() {
		return test_time_limit;
	}

	public void setTest_time_limit(String test_time_limit) {
		this.test_time_limit = test_time_limit;
	}

	public String getUsed_time() {
		return used_time;
	}

	public void setUsed_time(String used_time) {
		this.used_time = used_time;
	}

	public String getScore_total() {
		return score_total;
	}

	public void setScore_total(String score_total) {
		this.score_total = score_total;
	}

	public String getScoret() {
		return scoret;
	}

	public void setScoret(String scoret) {
		this.scoret = scoret;
	}

	@Override
	public String toString() {
		return "Submit_exercise_suitBean [suit_id=" + suit_id
				+ ", test_time_limit=" + test_time_limit + ", used_time="
				+ used_time + ", score_total=" + score_total + ", scoret="
				+ scoret + ", conclusion=" + conclusion + "]";
	}
	
	public String getSuit_id() {
		return suit_id;
	}

	public void setSuit_id(String suit_id) {
		this.suit_id = suit_id;
	}

	public conclusion getConclusion() {
		return conclusion;
	}
	public void setConclusion(conclusion conclusion) {
		this.conclusion = conclusion;
	}
	public class conclusion implements Serializable{
		private by by;
		private String content;
		private suggests[] suggests;
		public by getBy() {
			return by;
		}
		public void setBy(by by) {
			this.by = by;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public suggests[] getSuggests() {
			return suggests;
		}
		public void setSuggests(suggests[] suggests) {
			this.suggests = suggests;
		}
		@Override
		public String toString() {
			return "conclusion [by=" + by + ", content=" + content
					+ ", suggests=" + Arrays.toString(suggests) + "]";
		}
		
	}
	public class by implements Serializable{
		private int id;
		private String icon;
		private String n;
		private String intro;
		private String url;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getIntro() {
			return intro;
		}
		public void setIntro(String intro) {
			this.intro = intro;
		}
		@Override
		public String toString() {
			return "by [id=" + id + ", icon=" + icon + ", n=" + n + ", intro="
					+ intro + ", url=" + url + "]";
		}
		
	}
	public class suggests implements Serializable{
		private String title;
		private String intro1;
		private String intro2;
		private String img;
		private String btn_text;
		private String progress;
		private action action;
		private String [] role_msg;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getIntro1() {
			return intro1;
		}
		public void setIntro1(String intro1) {
			this.intro1 = intro1;
		}
		public String getIntro2() {
			return intro2;
		}
		public void setIntro2(String intro2) {
			this.intro2 = intro2;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		public String getBtn_text() {
			return btn_text;
		}
		public void setBtn_text(String btn_text) {
			this.btn_text = btn_text;
		}
		public String getProgress() {
			return progress;
		}
		public void setProgress(String progress) {
			this.progress = progress;
		}
	
		public action getAction() {
			return action;
		}
		public void setAction(action action) {
			this.action = action;
		}
		public String[] getRole_msg() {
			return role_msg;
		}
		public void setRole_msg(String[] role_msg) {
			this.role_msg = role_msg;
		}
		@Override
		public String toString() {
			return "suggests [title=" + title + ", intro1=" + intro1
					+ ", intro2=" + intro2 + ", img=" + img + ", btn_text="
					+ btn_text + ", progress=" + progress + ", action="
					+ action + ", role_msg=" + Arrays.toString(role_msg) + "]";
		}
		
	}
	public class action implements Serializable{
		private String action_type;
		private action_option action_option;
		@Override
		public String toString() {
			return "action [action_type=" + action_type + ", action_option="
					+ action_option + "]";
		}
		public String getAction_type() {
			return action_type;
		}
		public void setAction_type(String action_type) {
			this.action_type = action_type;
		}
		public action_option getAction_option() {
			return action_option;
		}
		public void setAction_option(action_option action_option) {
			this.action_option = action_option;
		}
		
	}
	public class action_option implements Serializable{
		private String method;
		private String type;
		private String[] option;
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		public String[] getOption() {
			return option;
		}
		public void setOption(String[] option) {
			this.option = option;
		}
		@Override
		public String toString() {
			return "action_option [method=" + method + ", type=" + type
					+ ", option=" + Arrays.toString(option) + "]";
		}
		
	}
}
