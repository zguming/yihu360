package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_user_baseinfo implements Serializable{
	private int uid;
	private String membership_pay;
	private int member_sjan;
	private share_type share_type;
	private share_info share_info;
	private Good_infoBean good_info;
	private about_us about_us;
	private int open_comment_box;

	private int ctgg_need_vip; //1是需要付费，0是不需要
	private String ctgg_paymsg;
	private String ctgg_paybtn_yes;
	private String ctgg_paybtn_no;

	public Get_user_baseinfo.share_info getShare_info() {
		return share_info;
	}

	public void setShare_info(Get_user_baseinfo.share_info share_info) {
		this.share_info = share_info;
	}

	public Good_infoBean getGood_info() {
		return good_info;
	}

	public void setGood_info(Good_infoBean good_info) {
		this.good_info = good_info;
	}

	public int getCtgg_need_vip() {
		return ctgg_need_vip;
	}
	public void setCtgg_need_vip(int ctgg_need_vip) {
		this.ctgg_need_vip = ctgg_need_vip;
	}
	public String getCtgg_paymsg() {
		return ctgg_paymsg;
	}
	public void setCtgg_paymsg(String ctgg_paymsg) {
		this.ctgg_paymsg = ctgg_paymsg;
	}
	public String getCtgg_paybtn_yes() {
		return ctgg_paybtn_yes;
	}
	public void setCtgg_paybtn_yes(String ctgg_paybtn_yes) {
		this.ctgg_paybtn_yes = ctgg_paybtn_yes;
	}
	public String getCtgg_paybtn_no() {
		return ctgg_paybtn_no;
	}
	public void setCtgg_paybtn_no(String ctgg_paybtn_no) {
		this.ctgg_paybtn_no = ctgg_paybtn_no;
	}
	@Override
	public String toString() {
		return "Get_user_baseinfo [uid=" + uid + ", membership_pay="
				+ membership_pay + ", member_sjan=" + member_sjan
				+ ", share_type=" + share_type + ", about_us=" + about_us
				+ ", open_comment_box=" + open_comment_box + ", ctgg_need_vip="
				+ ctgg_need_vip + ", ctgg_paymsg=" + ctgg_paymsg
				+ ", ctgg_paybtn_yes=" + ctgg_paybtn_yes + ", ctgg_paybtn_no="
				+ ctgg_paybtn_no + "]";
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getMembership_pay() {
		return membership_pay;
	}
	public void setMembership_pay(String membership_pay) {
		this.membership_pay = membership_pay;
	}
	public int getMember_sjan() {
		return member_sjan;
	}
	public void setMember_sjan(int member_sjan) {
		this.member_sjan = member_sjan;
	}
	public int getOpen_comment_box() {
		return open_comment_box;
	}
	public void setOpen_comment_box(int open_comment_box) {
		this.open_comment_box = open_comment_box;
	}
	public share_type getShare_type() {
		return share_type;
	}
	public void setShare_type(share_type share_type) {
		this.share_type = share_type;
	}
	public about_us getAbout_us() {
		return about_us;
	}
	public void setAbout_us(about_us about_us) {
		this.about_us = about_us;
	}
	public class share_type{
		private app app;
		private e_report e_report;
		private t_report t_report;
		private question question;
		public app getApp() {
			return app;
		}
		public void setApp(app app) {
			this.app = app;
		}
		public e_report getE_report() {
			return e_report;
		}
		public void setE_report(e_report e_report) {
			this.e_report = e_report;
		}
		public t_report getT_report() {
			return t_report;
		}
		public void setT_report(t_report t_report) {
			this.t_report = t_report;
		}
		public question getQuestion() {
			return question;
		}
		public void setQuestion(question question) {
			this.question = question;
		}
		@Override
		public String toString() {
			return "share_type [app=" + app + ", e_report=" + e_report
					+ ", t_report=" + t_report + ", question=" + question + "]";
		}

	}
	public class share_info{
		private String share_money;
		private String number;
		private String money;
		private String share_link;
		private String share_link_qrcodeimg;
		private String rule;//规则
		private String share_title;
		private String share_content;
		private String share_img;

		public String getShare_img() {
			return share_img;
		}

		public void setShare_img(String share_img) {
			this.share_img = share_img;
		}

		public String getRule() {
			return rule;
		}

		public void setRule(String rule) {
			this.rule = rule;
		}

		public String getShare_title() {
			return share_title;
		}

		public void setShare_title(String share_title) {
			this.share_title = share_title;
		}

		public String getShare_content() {
			return share_content;
		}

		public void setShare_content(String share_content) {
			this.share_content = share_content;
		}

		public String getShare_money() {
			return share_money;
		}

		public void setShare_money(String share_money) {
			this.share_money = share_money;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getShare_link() {
			return share_link;
		}

		public void setShare_link(String share_link) {
			this.share_link = share_link;
		}

		public String getShare_link_qrcodeimg() {
			return share_link_qrcodeimg;
		}

		public void setShare_link_qrcodeimg(String share_link_qrcodeimg) {
			this.share_link_qrcodeimg = share_link_qrcodeimg;
		}
	}
	public class app{
		private String share_title;
		private String share_content;
		private String share_img;
		private String share_link;
		private String qrcode_title;
		private String qrcode_content;
		private String qrcode_img;
		public String getShare_title() {
			return share_title;
		}
		public void setShare_title(String share_title) {
			this.share_title = share_title;
		}
		public String getShare_content() {
			return share_content;
		}
		public void setShare_content(String share_content) {
			this.share_content = share_content;
		}
		public String getShare_img() {
			return share_img;
		}
		public void setShare_img(String share_img) {
			this.share_img = share_img;
		}
		public String getShare_link() {
			return share_link;
		}
		public void setShare_link(String share_link) {
			this.share_link = share_link;
		}
		public String getQrcode_title() {
			return qrcode_title;
		}
		public void setQrcode_title(String qrcode_title) {
			this.qrcode_title = qrcode_title;
		}
		public String getQrcode_content() {
			return qrcode_content;
		}
		public void setQrcode_content(String qrcode_content) {
			this.qrcode_content = qrcode_content;
		}
		public String getQrcode_img() {
			return qrcode_img;
		}
		public void setQrcode_img(String qrcode_img) {
			this.qrcode_img = qrcode_img;
		}
		@Override
		public String toString() {
			return "app [share_title=" + share_title + ", share_content="
					+ share_content + ", share_img=" + share_img
					+ ", share_link=" + share_link + ", qrcode_title="
					+ qrcode_title + ", qrcode_content=" + qrcode_content
					+ ", qrcode_img=" + qrcode_img + "]";
		}

	}
	public class e_report{
		private String share_title;
		private String share_content;
		private String share_img;
		private String share_link;
		public String getShare_title() {
			return share_title;
		}
		public void setShare_title(String share_title) {
			this.share_title = share_title;
		}
		public String getShare_content() {
			return share_content;
		}
		public void setShare_content(String share_content) {
			this.share_content = share_content;
		}
		public String getShare_img() {
			return share_img;
		}
		public void setShare_img(String share_img) {
			this.share_img = share_img;
		}
		public String getShare_link() {
			return share_link;
		}
		public void setShare_link(String share_link) {
			this.share_link = share_link;
		}
		@Override
		public String toString() {
			return "e_report [share_title=" + share_title + ", share_content="
					+ share_content + ", share_img=" + share_img
					+ ", share_link=" + share_link + "]";
		}

	}
	public class t_report{
		private String share_title;
		private String share_content;
		private String share_img;
		private String share_link;
		public String getShare_title() {
			return share_title;
		}
		public void setShare_title(String share_title) {
			this.share_title = share_title;
		}
		public String getShare_content() {
			return share_content;
		}
		public void setShare_content(String share_content) {
			this.share_content = share_content;
		}
		public String getShare_img() {
			return share_img;
		}
		public void setShare_img(String share_img) {
			this.share_img = share_img;
		}
		public String getShare_link() {
			return share_link;
		}
		public void setShare_link(String share_link) {
			this.share_link = share_link;
		}
		@Override
		public String toString() {
			return "t_report [share_title=" + share_title + ", share_content="
					+ share_content + ", share_img=" + share_img
					+ ", share_link=" + share_link + "]";
		}

	}
	public class question{
		private String share_title;
		private String share_content;
		private String share_img;
		private String share_link;
		public String getShare_title() {
			return share_title;
		}
		public void setShare_title(String share_title) {
			this.share_title = share_title;
		}
		public String getShare_content() {
			return share_content;
		}
		public void setShare_content(String share_content) {
			this.share_content = share_content;
		}
		public String getShare_img() {
			return share_img;
		}
		public void setShare_img(String share_img) {
			this.share_img = share_img;
		}
		public String getShare_link() {
			return share_link;
		}
		public void setShare_link(String share_link) {
			this.share_link = share_link;
		}
		@Override
		public String toString() {
			return "question [share_title=" + share_title + ", share_content="
					+ share_content + ", share_img=" + share_img
					+ ", share_link=" + share_link + "]";
		}

	}
	public class about_us{
		private logos[] logos;
		private String content;
		public logos[] getLogos() {
			return logos;
		}
		public void setLogos(logos[] logos) {
			this.logos = logos;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		@Override
		public String toString() {
			return "about_us [logos=" + Arrays.toString(logos) + ", content="
					+ content + "]";
		}

	}
	public class logos{
		private String img;
		private String title;
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		@Override
		public String toString() {
			return "logos [img=" + img + ", title=" + title + "]";
		}

	}
	public class Good_infoBean{
		private String good_money;
		private String good_link;
		private String good_img;
		private String good_title;
		private String good_content;
		private String good_content1;
		private String good_content2;
		private String good_content3;
		private String good_btn1;
		private String good_btn2;
		private String good_btn3;

		public String getGood_content1() {
			return good_content1;
		}

		public void setGood_content1(String good_content1) {
			this.good_content1 = good_content1;
		}

		public String getGood_content2() {
			return good_content2;
		}

		public void setGood_content2(String good_content2) {
			this.good_content2 = good_content2;
		}

		public String getGood_content3() {
			return good_content3;
		}

		public void setGood_content3(String good_content3) {
			this.good_content3 = good_content3;
		}

		public String getGood_money() {
			return good_money;
		}

		public void setGood_money(String good_money) {
			this.good_money = good_money;
		}

		public String getGood_link() {
			return good_link;
		}

		public void setGood_link(String good_link) {
			this.good_link = good_link;
		}

		public String getGood_img() {
			return good_img;
		}

		public void setGood_img(String good_img) {
			this.good_img = good_img;
		}

		public String getGood_title() {
			return good_title;
		}

		public void setGood_title(String good_title) {
			this.good_title = good_title;
		}

		public String getGood_content() {
			return good_content;
		}

		public void setGood_content(String good_content) {
			this.good_content = good_content;
		}

		public String getGood_btn1() {
			return good_btn1;
		}

		public void setGood_btn1(String good_btn1) {
			this.good_btn1 = good_btn1;
		}

		public String getGood_btn2() {
			return good_btn2;
		}

		public void setGood_btn2(String good_btn2) {
			this.good_btn2 = good_btn2;
		}

		public String getGood_btn3() {
			return good_btn3;
		}

		public void setGood_btn3(String good_btn3) {
			this.good_btn3 = good_btn3;
		}
	}

}
