package cn.net.dingwei.Bean;

import java.io.Serializable;

/**
 * 编辑笔记
 * @author Administrator
 *
 */
public class Edit_user_noteBean implements Serializable{
	private Boolean status;
	private data data;

	public class data implements Serializable{
		private String id;
		private String author_name;
		private String icon;
		private String time_text;
		private String content;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAuthor_name() {
			return author_name;
		}
		public void setAuthor_name(String author_name) {
			this.author_name = author_name;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getTime_text() {
			return time_text;
		}
		public void setTime_text(String time_text) {
			this.time_text = time_text;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		@Override
		public String toString() {
			return "data [id=" + id + ", author_name=" + author_name
					+ ", icon=" + icon + ", time_text=" + time_text
					+ ", content=" + content + "]";
		}

	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	/*public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}*/

	public data getData() {
		return data;
	}

	public void setData(data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Edit_user_noteBean [status=" + status + ", data=" + data + "]";
	}


}
