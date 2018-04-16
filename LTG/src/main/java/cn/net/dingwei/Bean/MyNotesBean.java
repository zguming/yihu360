package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;
/**
 * 我的笔记
 * @author Administrator
 *
 */
public class MyNotesBean implements Serializable{
	private Boolean status;
	private data data;

	public class data implements Serializable{
		private String last_id;
		private int datas;
		private int next;
		private notes[] notes;

		public int getDatas() {
			return datas;
		}
		public void setDatas(int datas) {
			this.datas = datas;
		}
		public String getLast_id() {
			return last_id;
		}
		public void setLast_id(String last_id) {
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
			return "data [last_id=" + last_id + ", datas=" + datas + ", next="
					+ next + ", notes=" + Arrays.toString(notes) + "]";
		}

	}
	public class notes implements Serializable{
		private String id;
		private String points;
		private String qid;
		private String time_text;
		private String content;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPoints() {
			return points;
		}
		public void setPoints(String points) {
			this.points = points;
		}
		public String getQid() {
			return qid;
		}
		public void setQid(String qid) {
			this.qid = qid;
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
			return "notes [id=" + id + ", points=" + points + ", qid=" + qid
					+ ", time_text=" + time_text + ", content=" + content + "]";
		}

	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public data getData() {
		return data;
	}
	public void setData(data data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "MyNotesBean [status=" + status + ", data=" + data + "]";
	}

}
