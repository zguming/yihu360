package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_question_noteBean implements Serializable{
	private Boolean status;
	private data data;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Get_question_noteBean [status=" + status + ", data=" + data
				+ "]";
	}
	public data getData() {
		return data;
	}
	public void setData(data data) {
		this.data = data;
	}
	public class data implements Serializable{
		private int last_id;
		private int next;
		private notes[] notes;
		public int getLast_id() {
			return last_id;
		}
		public void setLast_id(int last_id) {
			this.last_id = last_id;
		}
		public int getNext() {
			return next;
		}
		public void setNext(int next) {
			this.next = next;
		}
		public notes[] getNotes() {
			return notes;
		}
		public void setNotes(notes[] notes) {
			this.notes = notes;
		}
		@Override
		public String toString() {
			return "data [last_id=" + last_id + ", next=" + next + ", notes="
					+ Arrays.toString(notes) + "]";
		}
		
	}
	public class notes implements Serializable{
		private int id;
		private String author_name;
		private String icon;
		private String time_text;
		private String content;
		public int getId() {
			return id;
		}
		public void setId(int id) {
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
			return "notes [id=" + id + ", author_name=" + author_name
					+ ", icon=" + icon + ", time_text=" + time_text
					+ ", content=" + content + "]";
		}
		
	}
}
