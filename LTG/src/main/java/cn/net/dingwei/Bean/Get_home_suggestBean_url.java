package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_home_suggestBean_url implements Serializable{
	private suggests[] suggests;
	private continues[] continues;
	@Override
	public String toString() {
		return "Get_home_suggestBean2 [suggests=" + Arrays.toString(suggests)
				+ "]";
	}

	public Get_home_suggestBean_url.continues[] getContinues() {
		return continues;
	}

	public void setContinues(Get_home_suggestBean_url.continues[] continues) {
		this.continues = continues;
	}

	public suggests[] getSuggests() {
		return suggests;
	}

	public void setSuggests(suggests[] suggests) {
		this.suggests = suggests;
	}

	public class suggests implements Serializable{
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "suggests [url=" + url + "]";
	}
	
	}
	public class continues implements Serializable{
		private String url;
		private String content;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "continues{" +
					"url='" + url + '\'' +
					", content='" + content + '\'' +
					'}';
		}
	}
}
