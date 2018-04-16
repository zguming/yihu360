package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_test_baseinfoBean implements Serializable{
	private  test_type[] test_type;
	@Override
	public String toString() {
		return "Get_test_baseinfoBean [test_type=" + Arrays.toString(test_type)
				+ "]";
	}
	public test_type[] getTest_type() {
		return test_type;
	}
	public void setTest_type(test_type[] test_type) {
		this.test_type = test_type;
	}
	public class test_type implements Serializable{
		private String key;
		private String n;
		private String desc;
		private Boolean needpay;
		private String paymsg;
		private String paybtn_yes;
		private String paybtn_no;
		private Boolean has_category;
		private category[] category;
		//修改后的接口
		private String img;
		private String btn_text;
		private String title;


		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
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
		public Boolean getNeedpay() {
			return needpay;
		}
		public void setNeedpay(Boolean needpay) {
			this.needpay = needpay;
		}
		public Boolean getHas_category() {
			return has_category;
		}
		public void setHas_category(Boolean has_category) {
			this.has_category = has_category;
		}
		public category[] getCategory() {
			return category;
		}

		public void setCategory(category[] category) {
			this.category = category;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getPaymsg() {
			return paymsg;
		}
		public void setPaymsg(String paymsg) {
			this.paymsg = paymsg;
		}
		public String getPaybtn_yes() {
			return paybtn_yes;
		}
		public void setPaybtn_yes(String paybtn_yes) {
			this.paybtn_yes = paybtn_yes;
		}
		public String getPaybtn_no() {
			return paybtn_no;
		}
		public void setPaybtn_no(String paybtn_no) {
			this.paybtn_no = paybtn_no;
		}

		@Override
		public String toString() {
			return "test_type [key=" + key + ", n=" + n + ", desc=" + desc
					+ ", needpay=" + needpay + ", paymsg=" + paymsg
					+ ", paybtn_yes=" + paybtn_yes + ", paybtn_no=" + paybtn_no
					+ ", has_category=" + has_category + ", category="
					+ Arrays.toString(category) + ", img=" + img
					+ ", btn_text=" + btn_text + ", title=" + title + "]";
		}

	}
	public class category implements Serializable{
		private String categoryid;
		private String n;
		@Override
		public String toString() {
			return "category [categoryid=" + categoryid + ", n=" + n + "]";
		}
		public String getCategoryid() {
			return categoryid;
		}
		public void setCategoryid(String categoryid) {
			this.categoryid = categoryid;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
	}
}
