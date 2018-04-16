package cn.net.dingwei.Bean;

import java.io.Serializable;

public class UserinfoBean implements Serializable {
	private Boolean registered;
	private String member_type;
	private String member_type_name;

	private String trial_expiry;
	private String member_expiry;
	private String nickname;
	private String mobile;
	private String city;
	private String city_name;
	private String exam;
	private String exam_name;
	private String exam_schedule;
	private String subject;
	private String subject_name;
	private int member_status;
	private String member_status_name;
	private String member_price;
	private String member_button;
	private String member_expiry_text;
	//学霸什么的
	private String level_text;
	private String level_name;
	private String exp_rate;
	private String img;
	private String userd_days;
	private String amount_lt;
	private String amount_gg;
	//指南消息提示
	private Integer new_msg;
	private Integer user_wrongs;
	private Integer user_note;
	private Integer user_collect;
	//游客是否登录过   //1、游客登录过 0、游客未登录
	private String bool;
	private String is_login;
	private String balance;
	private String share_type;//0 分享 1是分享有礼

	public String getImg_sign() {
		return img_sign;
	}

	public void setImg_sign(String img_sign) {
		this.img_sign = img_sign;
	}

	private String img_sign;//腾讯云上传头像签名

	public String getShare_type() {
		return share_type;
	}

	public void setShare_type(String share_type) {
		this.share_type = share_type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getIs_login() {
		return is_login;
	}
	public void setIs_login(String is_login) {
		this.is_login = is_login;
	}
	public String getBool() {
		return bool;
	}
	public void setBool(String bool) {
		this.bool = bool;
	}
	public Integer getUser_wrongs() {
		return user_wrongs;
	}
	public void setUser_wrongs(Integer user_wrongs) {
		this.user_wrongs = user_wrongs;
	}
	public Integer getUser_note() {
		return user_note;
	}
	public void setUser_note(Integer user_note) {
		this.user_note = user_note;
	}
	public Integer getUser_collect() {
		return user_collect;
	}
	public void setUser_collect(Integer user_collect) {
		this.user_collect = user_collect;
	}
	public Integer getNew_msg() {
		return new_msg;
	}
	public void setNew_msg(Integer new_msg) {
		this.new_msg = new_msg;
	}
	public String getLevel_text() {
		return level_text;
	}
	public void setLevel_text(String level_text) {
		this.level_text = level_text;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public String getExp_rate() {
		return exp_rate;
	}
	public void setExp_rate(String exp_rate) {
		this.exp_rate = exp_rate;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUserd_days() {
		return userd_days;
	}
	public void setUserd_days(String userd_days) {
		this.userd_days = userd_days;
	}
	public String getAmount_lt() {
		return amount_lt;
	}
	public void setAmount_lt(String amount_lt) {
		this.amount_lt = amount_lt;
	}
	public String getAmount_gg() {
		return amount_gg;
	}
	public void setAmount_gg(String amount_gg) {
		this.amount_gg = amount_gg;
	}
	public String getMember_button() {
		return member_button;
	}
	public void setMember_button(String member_button) {
		this.member_button = member_button;
	}
	public String getMember_expiry_text() {
		return member_expiry_text;
	}
	public void setMember_expiry_text(String member_expiry_text) {
		this.member_expiry_text = member_expiry_text;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getExam_name() {
		return exam_name;
	}
	public void setExam_name(String exam_name) {
		this.exam_name = exam_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public int getMember_status() {
		return member_status;
	}
	public void setMember_status(int member_status) {
		this.member_status = member_status;
	}
	public String getMember_status_name() {
		return member_status_name;
	}
	public void setMember_status_name(String member_status_name) {
		this.member_status_name = member_status_name;
	}
	public String getMember_price() {
		return member_price;
	}
	public void setMember_price(String member_price) {
		this.member_price = member_price;
	}
	public String getMember_type_name() {
		return member_type_name;
	}
	public void setMember_type_name(String member_type_name) {
		this.member_type_name = member_type_name;
	}
	public Boolean getRegistered() {
		return registered;
	}
	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getTrial_expiry() {
		return trial_expiry;
	}
	public void setTrial_expiry(String trial_expiry) {
		this.trial_expiry = trial_expiry;
	}
	public String getMember_expiry() {
		return member_expiry;
	}
	public void setMember_expiry(String member_expiry) {
		this.member_expiry = member_expiry;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public String getExam_schedule() {
		return exam_schedule;
	}
	public void setExam_schedule(String exam_schedule) {
		this.exam_schedule = exam_schedule;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "UserinfoBean [registered=" + registered + ", member_type="
				+ member_type + ", member_type_name=" + member_type_name
				+ ", trial_expiry=" + trial_expiry + ", member_expiry="
				+ member_expiry + ", nickname=" + nickname + ", mobile="
				+ mobile + ", city=" + city + ", city_name=" + city_name
				+ ", exam=" + exam + ", exam_name=" + exam_name
				+ ", exam_schedule=" + exam_schedule + ", subject=" + subject
				+ ", subject_name=" + subject_name + ", member_status="
				+ member_status + ", member_status_name=" + member_status_name
				+ ", member_price=" + member_price + ", member_button="
				+ member_button + ", member_expiry_text=" + member_expiry_text
				+ ", level_text=" + level_text + ", level_name=" + level_name
				+ ", exp_rate=" + exp_rate + ", img=" + img + ", userd_days="
				+ userd_days + ", amount_lt=" + amount_lt + ", amount_gg="
				+ amount_gg + ", new_msg=" + new_msg + ", user_wrongs="
				+ user_wrongs + ", user_note=" + user_note + ", user_collect="
				+ user_collect + ", bool=" + bool + ", is_login=" + is_login
				+ "]";
	}



}
