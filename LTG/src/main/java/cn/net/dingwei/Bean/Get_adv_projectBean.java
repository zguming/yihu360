package cn.net.dingwei.Bean;

import java.util.List;

/**
 * 获取进阶项目
 *
 * @author Administrator
 *
 */
public class Get_adv_projectBean {
	private Boolean status;
	private List<data> data;

	public class data {
		private String id;
		private String title;
		private String stitle;
		private String type;
		private String is_use;
		private String pay_type;
		private pay pay;
		private String expiry;
		private String list_num;
		private String qb_num;
		private String description;
		private img img;
		private String vip_text;


		public String getVip_text() {
			return vip_text;
		}
		public void setVip_text(String vip_text) {
			this.vip_text = vip_text;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public pay getPay() {
			return pay;
		}
		public void setPay(pay pay) {
			this.pay = pay;
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
		public String getIs_use() {
			return is_use;
		}
		public void setIs_use(String is_use) {
			this.is_use = is_use;
		}
		public String getPay_type() {
			return pay_type;
		}
		public void setPay_type(String pay_type) {
			this.pay_type = pay_type;
		}
		public String getExpiry() {
			return expiry;
		}
		public void setExpiry(String expiry) {
			this.expiry = expiry;
		}
		public String getList_num() {
			return list_num;
		}
		public void setList_num(String list_num) {
			this.list_num = list_num;
		}
		public String getQb_num() {
			return qb_num;
		}
		public void setQb_num(String qb_num) {
			this.qb_num = qb_num;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public img getImg() {
			return img;
		}
		public void setImg(img img) {
			this.img = img;
		}
		@Override
		public String toString() {
			return "data [id=" + id + ", title=" + title + ", stitle=" + stitle
					+ ", type=" + type + ", is_use=" + is_use + ", pay_type="
					+ pay_type + ", pay=" + pay + ", expiry=" + expiry
					+ ", list_num=" + list_num + ", qb_num=" + qb_num
					+ ", description=" + description + ", img=" + img
					+ ", vip_text=" + vip_text + "]";
		}

	}
	public class pay {
		private String name;
		private String price;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		@Override
		public String toString() {
			return "pay [name=" + name + ", price=" + price + "]";
		}

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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<data> getData() {
		return data;
	}
	public void setData(List<data> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Get_adv_projectBean [status=" + status + ", data=" + data + "]";
	}

}
