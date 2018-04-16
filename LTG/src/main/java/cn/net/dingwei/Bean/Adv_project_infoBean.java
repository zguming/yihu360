package cn.net.dingwei.Bean;

import java.util.List;

/**
 * 进阶练习  详情
 * @author Administrator
 *
 */
public class Adv_project_infoBean {
	private Boolean status;
	private data data;
	public class  data{
		private String title;
		private String stitle;
		private String type;
		private String expiry;
		private String qb_num;
		private String exe_num;
		private String description;
		private List<suit_list> suit_list;
		private wrong_list wrong_list;
		private img img;

		public img getImg() {
			return img;
		}
		public void setImg(img img) {
			this.img = img;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getStitle() {
			return stitle;
		}
		public void setStitle(String stitle) {
			this.stitle = stitle;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getExpiry() {
			return expiry;
		}
		public void setExpiry(String expiry) {
			this.expiry = expiry;
		}
		public String getQb_num() {
			return qb_num;
		}
		public void setQb_num(String qb_num) {
			this.qb_num = qb_num;
		}
		public String getExe_num() {
			return exe_num;
		}
		public void setExe_num(String exe_num) {
			this.exe_num = exe_num;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<suit_list> getSuit_list() {
			return suit_list;
		}
		public void setSuit_list(List<suit_list> suit_list) {
			this.suit_list = suit_list;
		}

		public wrong_list getWrong_list() {
			return wrong_list;
		}
		public void setWrong_list(wrong_list wrong_list) {
			this.wrong_list = wrong_list;
		}
		@Override
		public String toString() {
			return "data [title=" + title + ", stitle=" + stitle + ", type="
					+ type + ", expiry=" + expiry + ", qb_num=" + qb_num
					+ ", exe_num=" + exe_num + ", description=" + description
					+ ", suit_list=" + suit_list + ", wrong_list=" + wrong_list
					+ ", img=" + img + "]";
		}

	}
	public class  suit_list{
		private String id;
		private String title;
		private String qb_num;
		private String exe_num;
		private String exe_r;
		private String wro_r;
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
		public String getQb_num() {
			return qb_num;
		}
		public void setQb_num(String qb_num) {
			this.qb_num = qb_num;
		}
		public String getExe_num() {
			return exe_num;
		}
		public void setExe_num(String exe_num) {
			this.exe_num = exe_num;
		}
		public String getExe_r() {
			return exe_r;
		}
		public void setExe_r(String exe_r) {
			this.exe_r = exe_r;
		}
		public String getWro_r() {
			return wro_r;
		}
		public void setWro_r(String wro_r) {
			this.wro_r = wro_r;
		}
		@Override
		public String toString() {
			return "suit_list [id=" + id + ", title=" + title + ", qb_num="
					+ qb_num + ", exe_num=" + exe_num + ", exe_r=" + exe_r
					+ ", wro_r=" + wro_r + "]";
		}

	}
	public class  wrong_list{
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
		private List<wrongs> wrongs;
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		public List<wrongs> getWrongs() {
			return wrongs;
		}
		public void setWrongs(List<wrongs> wrongs) {
			this.wrongs = wrongs;
		}
		@Override
		public String toString() {
			return "wrong_list [total=" + total + ", wro_t=" + wro_t
					+ ", wro_tr=" + wro_tr + ", wro_k=" + wro_k + ", wro_kr="
					+ wro_kr + ", wro_c=" + wro_c + ", wro_cr=" + wro_cr
					+ ", wrongs=" + wrongs + "]";
		}


	}
	public class  wrongs{
		private int id;
		private String title;
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		@Override
		public String toString() {
			return "wrongs [id=" + id + ", title=" + title + ", total=" + total
					+ ", wro_t=" + wro_t + ", wro_tr=" + wro_tr + ", wro_k="
					+ wro_k + ", wro_kr=" + wro_kr + ", wro_c=" + wro_c
					+ ", wro_cr=" + wro_cr + "]";
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
		return "Adv_project_infoBean [status=" + status + ", data=" + data
				+ "]";
	}
	public class img {
		private String url;
		private String width;
		private String height;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		@Override
		public String toString() {
			return "img [url=" + url + ", width=" + width + ", height="
					+ height + "]";
		}

	}
}
