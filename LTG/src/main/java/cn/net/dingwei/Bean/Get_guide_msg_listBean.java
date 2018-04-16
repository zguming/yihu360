package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_guide_msg_listBean implements Serializable{
	private String guide_category_key;
	private String last_msgid;
	private msglist[] msglist;
	private int next;
	
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	@Override
	public String toString() {
		return "Get_guide_msg_listBean [guide_category_key="
				+ guide_category_key + ", last_msgid=" + last_msgid
				+ ", msglist=" + Arrays.toString(msglist) + ", next=" + next
				+ "]";
	}
	public String getGuide_category_key() {
		return guide_category_key;
	}
	public void setGuide_category_key(String guide_category_key) {
		this.guide_category_key = guide_category_key;
	}
	
	public String getLast_msgid() {
		return last_msgid;
	}
	public void setLast_msgid(String last_msgid) {
		this.last_msgid = last_msgid;
	}
	public msglist[] getMsglist() {
		return msglist;
	}
	public void setMsglist(msglist[] msglist) {
		this.msglist = msglist;
	}
	public class msglist implements Serializable{
		private String id;
		private String title;
		private String icon;
		private String author_name;
		private String author_id;
		private String time_text;
		private String content;
		private images[] images;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getAuthor_name() {
			return author_name;
		}
		public void setAuthor_name(String author_name) {
			this.author_name = author_name;
		}
		
		public String getAuthor_id() {
			return author_id;
		}
		public void setAuthor_id(String author_id) {
			this.author_id = author_id;
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
		public images[] getImages() {
			return images;
		}
		public void setImages(images[] images) {
			this.images = images;
		}
		@Override
		public String toString() {
			return "msglist [id=" + id + ", title=" + title + ", icon=" + icon
					+ ", author_name=" + author_name + ", author_id="
					+ author_id + ", time_text=" + time_text + ", content="
					+ content + ", images=" + Arrays.toString(images) + "]";
		}
		
	}
	public class images implements Serializable{
		private String thumb_url;
		private String image_url;
		public String getThumb_url() {
			return thumb_url;
		}
		public void setThumb_url(String thumb_url) {
			this.thumb_url = thumb_url;
		}
		public String getImage_url() {
			return image_url;
		}
		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}
		@Override
		public String toString() {
			return "images [thumb_url=" + thumb_url + ", image_url="
					+ image_url + "]";
		}
		
	}
}
