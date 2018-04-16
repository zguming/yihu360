package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_home_suggestBean implements Serializable{
	private String title;
	private String intro1;
	private String intro2;
	private String img;
	private int progress;
	private action action;

	public action getAction() {
		return action;
	}
	public void setAction(action action) {
		this.action = action;
	}
	public class action {
		private String action_type;
		private action_option action_option;
		public action_option getAction_option() {
			return action_option;
		}
		public void setAction_option(action_option action_option) {
			this.action_option = action_option;
		}
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
		
	}
	public class action_option implements Serializable{
		private String method;
		private String type;
		private String [] option;
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


	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		this.progress = progress;
	}


	@Override
	public String toString() {
		return "Get_home_suggestBean [title=" + title + ", intro1=" + intro1
				+ ", intro2=" + intro2 + ", img=" + img + ", progress="
				+ progress + ", action=" + action + "]";
	}
	
}
