package cn.net.dingwei.Bean;

import java.io.Serializable;

public class CommonInfo_API_functions implements Serializable{
	private String get_baseinfo;
	private String get_intropage_templates;
	private String get_userinfo;
	private String bind_user;
	private String unbind_user;
	private String update_userinfo;
	private String get_situation;
	private String get_subjects_progress;
	private String create_exercise_suit;
	private String get_exercise_suit;
	private String submit_exercise_answer;
	private String submit_exercise_suit;
	private String get_exercise_structure;
	private String pay;
	private String pay_success;
	private String send_mobile_verificationcode;
	private String forget_password_verificationcode;
	private String forget_password_submit;
	private String get_home_suggest;
	private String get_test_baseinfo;
	private String create_test_suit;
	private String submit_test_answer;
	private String submit_test_suit;
	private String get_guide_msg_list;
	private String get_point_info;
	private String create_ios_payment_order;
	private String check_ios_payment_order;
	private String get_user_baseinfo;
	private String create_error_correction; //纠错
	private String edit_user_collect;	//编辑收藏
	private String get_user_collect;	//获取收藏
	private String get_user_wrongs;		//获取错题
	private String edit_user_note;		//编辑笔记
	private String get_question_note;	//获取本题笔记

	private String get_user_note;	//获取我的笔记
	private String get_note_info;	//获取笔记详情
	private String get_adv_project;	//获取进阶详细
	private String get_adv_project_info;	//获取进阶练习 详细
	private String pay_adv_project;	//单独支付

	private String create_wxpay_recharge_order;	//充值-微信支付
	private String create_wxpay_recharge_buys_order;	// 购买充值--微信支付
	private String get_products_list;	//首页产品列表
	private String get_balance_list;	//获取交易记录
	private String get_video_list;	//视频列表
	private String get_video_info;	//视频详情
	private String change_video_times;	//提交时间
	private String get_share_info;	//推荐有奖（分享）
	private String get_live_list;	//推荐有奖（分享）
	private String change_live_user;	//推荐有奖（分享）
	private String order_live;	//视频预约
	public String getGet_live_number() {
		return get_live_number;
	}

	public void setGet_live_number(String get_live_number) {
		this.get_live_number = get_live_number;
	}

	private String get_live_number;	//直播在线人数

	public String getOrder_live() {
		return order_live;
	}

	public void setOrder_live(String order_live) {
		this.order_live = order_live;
	}

	public String getChange_live_user() {
		return change_live_user;
	}

	public void setChange_live_user(String change_live_user) {
		this.change_live_user = change_live_user;
	}

	public String getGet_live_list() {
		return get_live_list;
	}

	public void setGet_live_list(String get_live_list) {
		this.get_live_list = get_live_list;
	}

	public String getGet_share_info() {
		return get_share_info;
	}

	public void setGet_share_info(String get_share_info) {
		this.get_share_info = get_share_info;
	}

	public String getChange_video_times() {
		return change_video_times;
	}

	public void setChange_video_times(String change_video_times) {
		this.change_video_times = change_video_times;
	}

	public String getGet_video_info() {
		return get_video_info;
	}

	public void setGet_video_info(String get_video_info) {
		this.get_video_info = get_video_info;
	}

	public String getGet_video_list() {
		return get_video_list;
	}

	public void setGet_video_list(String get_video_list) {
		this.get_video_list = get_video_list;
	}

	public String getCreate_wxpay_recharge_buys_order() {
		return create_wxpay_recharge_buys_order;
	}

	public void setCreate_wxpay_recharge_buys_order(String create_wxpay_recharge_buys_order) {
		this.create_wxpay_recharge_buys_order = create_wxpay_recharge_buys_order;
	}

	public String getGet_balance_list() {
		return get_balance_list;
	}

	public void setGet_balance_list(String get_balance_list) {
		this.get_balance_list = get_balance_list;
	}

	public String getGet_products_list() {
		return get_products_list;
	}

	public void setGet_products_list(String get_products_list) {
		this.get_products_list = get_products_list;
	}

	public String getCreate_wxpay_recharge_order() {
		return create_wxpay_recharge_order;
	}

	public void setCreate_wxpay_recharge_order(String create_wxpay_recharge_order) {
		this.create_wxpay_recharge_order = create_wxpay_recharge_order;
	}

	public String getPay_adv_project() {
		return pay_adv_project;
	}
	public void setPay_adv_project(String pay_adv_project) {
		this.pay_adv_project = pay_adv_project;
	}
	public String getGet_adv_project_info() {
		return get_adv_project_info;
	}
	public void setGet_adv_project_info(String get_adv_project_info) {
		this.get_adv_project_info = get_adv_project_info;
	}
	public String getGet_adv_project() {
		return get_adv_project;
	}
	public void setGet_adv_project(String get_adv_project) {
		this.get_adv_project = get_adv_project;
	}
	public String getGet_user_note() {
		return get_user_note;
	}
	public void setGet_user_note(String get_user_note) {
		this.get_user_note = get_user_note;
	}
	public String getGet_note_info() {
		return get_note_info;
	}
	public void setGet_note_info(String get_note_info) {
		this.get_note_info = get_note_info;
	}
	public String getEdit_user_note() {
		return edit_user_note;
	}
	public void setEdit_user_note(String edit_user_note) {
		this.edit_user_note = edit_user_note;
	}
	public String getGet_question_note() {
		return get_question_note;
	}
	public void setGet_question_note(String get_question_note) {
		this.get_question_note = get_question_note;
	}
	public String getCreate_error_correction() {
		return create_error_correction;
	}
	public void setCreate_error_correction(String create_error_correction) {
		this.create_error_correction = create_error_correction;
	}
	public String getEdit_user_collect() {
		return edit_user_collect;
	}
	public void setEdit_user_collect(String edit_user_collect) {
		this.edit_user_collect = edit_user_collect;
	}
	public String getGet_user_collect() {
		return get_user_collect;
	}
	public void setGet_user_collect(String get_user_collect) {
		this.get_user_collect = get_user_collect;
	}
	public String getGet_user_wrongs() {
		return get_user_wrongs;
	}
	public void setGet_user_wrongs(String get_user_wrongs) {
		this.get_user_wrongs = get_user_wrongs;
	}
	public String getGet_user_baseinfo() {
		return get_user_baseinfo;
	}
	public void setGet_user_baseinfo(String get_user_baseinfo) {
		this.get_user_baseinfo = get_user_baseinfo;
	}
	public String getGet_baseinfo() {
		return get_baseinfo;
	}
	public void setGet_baseinfo(String get_baseinfo) {
		this.get_baseinfo = get_baseinfo;
	}
	public String getGet_intropage_templates() {
		return get_intropage_templates;
	}
	public void setGet_intropage_templates(String get_intropage_templates) {
		this.get_intropage_templates = get_intropage_templates;
	}
	public String getGet_userinfo() {
		return get_userinfo;
	}
	public void setGet_userinfo(String get_userinfo) {
		this.get_userinfo = get_userinfo;
	}
	public String getBind_user() {
		return bind_user;
	}
	public void setBind_user(String bind_user) {
		this.bind_user = bind_user;
	}
	public String getUnbind_user() {
		return unbind_user;
	}
	public void setUnbind_user(String unbind_user) {
		this.unbind_user = unbind_user;
	}
	public String getUpdate_userinfo() {
		return update_userinfo;
	}
	public void setUpdate_userinfo(String update_userinfo) {
		this.update_userinfo = update_userinfo;
	}
	public String getGet_situation() {
		return get_situation;
	}
	public void setGet_situation(String get_situation) {
		this.get_situation = get_situation;
	}
	public String getGet_subjects_progress() {
		return get_subjects_progress;
	}
	public void setGet_subjects_progress(String get_subjects_progress) {
		this.get_subjects_progress = get_subjects_progress;
	}
	public String getCreate_exercise_suit() {
		return create_exercise_suit;
	}
	public void setCreate_exercise_suit(String create_exercise_suit) {
		this.create_exercise_suit = create_exercise_suit;
	}
	public String getGet_exercise_suit() {
		return get_exercise_suit;
	}
	public void setGet_exercise_suit(String get_exercise_suit) {
		this.get_exercise_suit = get_exercise_suit;
	}
	public String getSubmit_exercise_answer() {
		return submit_exercise_answer;
	}
	public void setSubmit_exercise_answer(String submit_exercise_answer) {
		this.submit_exercise_answer = submit_exercise_answer;
	}
	public String getSubmit_exercise_suit() {
		return submit_exercise_suit;
	}
	public void setSubmit_exercise_suit(String submit_exercise_suit) {
		this.submit_exercise_suit = submit_exercise_suit;
	}
	public String getGet_exercise_structure() {
		return get_exercise_structure;
	}
	public void setGet_exercise_structure(String get_exercise_structure) {
		this.get_exercise_structure = get_exercise_structure;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getPay_success() {
		return pay_success;
	}
	public void setPay_success(String pay_success) {
		this.pay_success = pay_success;
	}
	public String getSend_mobile_verificationcode() {
		return send_mobile_verificationcode;
	}
	public void setSend_mobile_verificationcode(String send_mobile_verificationcode) {
		this.send_mobile_verificationcode = send_mobile_verificationcode;
	}
	public String getForget_password_verificationcode() {
		return forget_password_verificationcode;
	}
	public void setForget_password_verificationcode(
			String forget_password_verificationcode) {
		this.forget_password_verificationcode = forget_password_verificationcode;
	}
	public String getForget_password_submit() {
		return forget_password_submit;
	}
	public void setForget_password_submit(String forget_password_submit) {
		this.forget_password_submit = forget_password_submit;
	}
	public String getGet_home_suggest() {
		return get_home_suggest;
	}
	public void setGet_home_suggest(String get_home_suggest) {
		this.get_home_suggest = get_home_suggest;
	}
	public String getGet_test_baseinfo() {
		return get_test_baseinfo;
	}
	public void setGet_test_baseinfo(String get_test_baseinfo) {
		this.get_test_baseinfo = get_test_baseinfo;
	}
	public String getCreate_test_suit() {
		return create_test_suit;
	}
	public void setCreate_test_suit(String create_test_suit) {
		this.create_test_suit = create_test_suit;
	}
	public String getSubmit_test_answer() {
		return submit_test_answer;
	}
	public void setSubmit_test_answer(String submit_test_answer) {
		this.submit_test_answer = submit_test_answer;
	}
	public String getSubmit_test_suit() {
		return submit_test_suit;
	}
	public void setSubmit_test_suit(String submit_test_suit) {
		this.submit_test_suit = submit_test_suit;
	}
	public String getGet_guide_msg_list() {
		return get_guide_msg_list;
	}
	public void setGet_guide_msg_list(String get_guide_msg_list) {
		this.get_guide_msg_list = get_guide_msg_list;
	}
	public String getGet_point_info() {
		return get_point_info;
	}
	public void setGet_point_info(String get_point_info) {
		this.get_point_info = get_point_info;
	}
	public String getCreate_ios_payment_order() {
		return create_ios_payment_order;
	}
	public void setCreate_ios_payment_order(String create_ios_payment_order) {
		this.create_ios_payment_order = create_ios_payment_order;
	}
	public String getCheck_ios_payment_order() {
		return check_ios_payment_order;
	}
	public void setCheck_ios_payment_order(String check_ios_payment_order) {
		this.check_ios_payment_order = check_ios_payment_order;
	}
	@Override
	public String toString() {
		return "CommonInfo_API_functions [get_baseinfo=" + get_baseinfo
				+ ", get_intropage_templates=" + get_intropage_templates
				+ ", get_userinfo=" + get_userinfo + ", bind_user="
				+ bind_user + ", unbind_user=" + unbind_user
				+ ", update_userinfo=" + update_userinfo
				+ ", get_situation=" + get_situation
				+ ", get_subjects_progress=" + get_subjects_progress
				+ ", create_exercise_suit=" + create_exercise_suit
				+ ", get_exercise_suit=" + get_exercise_suit
				+ ", submit_exercise_answer=" + submit_exercise_answer
				+ ", submit_exercise_suit=" + submit_exercise_suit
				+ ", get_exercise_structure=" + get_exercise_structure
				+ ", pay=" + pay + ", pay_success=" + pay_success
				+ ", send_mobile_verificationcode="
				+ send_mobile_verificationcode
				+ ", forget_password_verificationcode="
				+ forget_password_verificationcode
				+ ", forget_password_submit=" + forget_password_submit
				+ ", get_home_suggest=" + get_home_suggest
				+ ", get_test_baseinfo=" + get_test_baseinfo
				+ ", create_test_suit=" + create_test_suit
				+ ", submit_test_answer=" + submit_test_answer
				+ ", submit_test_suit=" + submit_test_suit
				+ ", get_guide_msg_list=" + get_guide_msg_list
				+ ", get_point_info=" + get_point_info
				+ ", create_ios_payment_order=" + create_ios_payment_order
				+ ", check_ios_payment_order=" + check_ios_payment_order
				+ ", get_user_baseinfo=" + get_user_baseinfo
				+ ", create_error_correction=" + create_error_correction
				+ ", edit_user_collect=" + edit_user_collect
				+ ", get_user_collect=" + get_user_collect
				+ ", get_user_wrongs=" + get_user_wrongs
				+ ", edit_user_note=" + edit_user_note
				+ ", get_question_note=" + get_question_note
				+ ", get_user_note=" + get_user_note + ", get_note_info="
				+ get_note_info + ", get_adv_project=" + get_adv_project
				+ ", get_adv_project_info=" + get_adv_project_info
				+ ", pay_adv_project=" + pay_adv_project
				+ ", create_wxpay_order=" + "]";
	}

}	
