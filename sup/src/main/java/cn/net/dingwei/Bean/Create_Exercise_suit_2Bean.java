package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 阅读题
 * @author Administrator
 *
 */
public class Create_Exercise_suit_2Bean implements Serializable{
	private int id;
	private String title;
	private String type;
	private String sub_title;
	private groups[] groups;
	private String conclusion;

	//继续练习
	private int last_group;
	private int last_infos;
	private int last_question;
	//笔记详情
	private int group;
	private int infos;
	private int question;
	@Override
	public String toString() {
		return "Create_Exercise_suit_2Bean [id=" + id + ", title=" + title
				+ ", type=" + type + ", sub_title=" + sub_title + ", groups="
				+ Arrays.toString(groups) + ", conclusion=" + conclusion
				+ ", last_group=" + last_group + ", last_infos=" + last_infos
				+ ", last_question=" + last_question + ", group=" + group
				+ ", infos=" + infos + ", question=" + question + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getInfos() {
		return infos;
	}

	public void setInfos(int infos) {
		this.infos = infos;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(int question) {
		this.question = question;
	}

	public int getLast_group() {
		return last_group;
	}

	public void setLast_group(int last_group) {
		this.last_group = last_group;
	}

	public int getLast_infos() {
		return last_infos;
	}

	public void setLast_infos(int last_infos) {
		this.last_infos = last_infos;
	}

	public int getLast_question() {
		return last_question;
	}

	public void setLast_question(int last_question) {
		this.last_question = last_question;
	}

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
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public groups[] getGroups() {
		return groups;
	}
	public void setGroups(groups[] groups) {
		this.groups = groups;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public class infos implements Serializable{
		private int info_no;
		private int info_status;
		private info info;
		private questions[] questions;
		//用户做到了当前Group的第几个问题
		private Integer question_index;
		private Integer quanzujiexi_index;//用户全组解析的时候滑动的位置
		private Boolean isShowBottom_button;
		public int getInfo_no() {
			return info_no;
		}
		public void setInfo_no(int info_no) {
			this.info_no = info_no;
		}
		public int getInfo_status() {
			return info_status;
		}
		public void setInfo_status(int info_status) {
			this.info_status = info_status;
		}
		public info getInfo() {
			return info;
		}
		public void setInfo(info info) {
			this.info = info;
		}
		public questions[] getQuestions() {
			return questions;
		}
		public void setQuestions(questions[] questions) {
			this.questions = questions;
		}
		public Integer getQuestion_index() {
			return question_index;
		}
		public void setQuestion_index(Integer question_index) {
			this.question_index = question_index;
		}
		public Integer getQuanzujiexi_index() {
			return quanzujiexi_index;
		}
		public void setQuanzujiexi_index(Integer quanzujiexi_index) {
			this.quanzujiexi_index = quanzujiexi_index;
		}
		public Boolean getIsShowBottom_button() {
			return isShowBottom_button;
		}
		public void setIsShowBottom_button(Boolean isShowBottom_button) {
			this.isShowBottom_button = isShowBottom_button;
		}
		@Override
		public String toString() {
			return "infos [info_no=" + info_no + ", info_status="
					+ info_status + ", info=" + info + ", questions="
					+ Arrays.toString(questions) + ", question_index="
					+ question_index + ", quanzujiexi_index="
					+ quanzujiexi_index + ", isShowBottom_button="
					+ isShowBottom_button + "]";
		}

	}
	public class groups implements Serializable{
		private int  no;
		private infos[] infos;
		public int getNo() {
			return no;
		}
		public void setNo(int no) {
			this.no = no;
		}
		public infos[] getInfos() {
			return infos;
		}
		public void setInfos(infos[] infos) {
			this.infos = infos;
		}
		@Override
		public String toString() {
			return "groups [no=" + no + ", infos=" + Arrays.toString(infos)
					+ "]";
		}

	}

	public class info implements Serializable{
		private String title;
		private String content;
		private img img;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public img getImg() {
			return img;
		}
		public void setImg(img img) {
			this.img = img;
		}
		@Override
		public String toString() {
			return "info [title=" + title + ", content=" + content + ", img="
					+ img + "]";
		}

	}
	public class img implements Serializable{
		private String url;
		private int width;
		private int height;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}

		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		@Override
		public String toString() {
			return "img [url=" + url + ", width=" + width + ", height="
					+ height + "]";
		}

	}
	public class questions implements Serializable{
		private int no;
		private int qid;
		private int cuotijiexi_questionNO;
		private int type;
		private String content;
		private img img;
		private opt[] opt;
		private String[] correct;
		private String answer;
		private analyze analyze;
		private points[] points;
		private String duoxuan;//记录多选题答案
		//笔记部分
		private int is_collect;
		private notes notes;


		public int getQid() {
			return qid;
		}
		public void setQid(int qid) {
			this.qid = qid;
		}
		public int getIs_collect() {
			return is_collect;
		}
		public void setIs_collect(int is_collect) {
			this.is_collect = is_collect;
		}
		public notes getNotes() {
			return notes;
		}
		public void setNotes(notes notes) {
			this.notes = notes;
		}
		public int getCuotijiexi_questionNO() {
			return cuotijiexi_questionNO;
		}
		public void setCuotijiexi_questionNO(int cuotijiexi_questionNO) {
			this.cuotijiexi_questionNO = cuotijiexi_questionNO;
		}
		public String getDuoxuan() {
			return duoxuan;
		}
		public void setDuoxuan(String duoxuan) {
			this.duoxuan = duoxuan;
		}
		public int getNo() {
			return no;
		}
		public void setNo(int no) {
			this.no = no;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public img getImg() {
			return img;
		}
		public void setImg(img img) {
			this.img = img;
		}
		public opt[] getOpt() {
			return opt;
		}
		public void setOpt(opt[] opt) {
			this.opt = opt;
		}
		public String[] getCorrect() {
			return correct;
		}
		public void setCorrect(String[] correct) {
			this.correct = correct;
		}
		public String getAnswer() {
			return answer;
		}
		public void setAnswer(String answer) {
			this.answer = answer;
		}
		public analyze getAnalyze() {
			return analyze;
		}
		public void setAnalyze(analyze analyze) {
			this.analyze = analyze;
		}
		public points[] getPoints() {
			return points;
		}
		public void setPoints(points[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "questions [no=" + no + ", qid=" + qid
					+ ", cuotijiexi_questionNO=" + cuotijiexi_questionNO
					+ ", type=" + type + ", content=" + content + ", img="
					+ img + ", opt=" + Arrays.toString(opt) + ", correct="
					+ Arrays.toString(correct) + ", answer=" + answer
					+ ", analyze=" + analyze + ", points="
					+ Arrays.toString(points) + ", duoxuan=" + duoxuan
					+ ", is_collect=" + is_collect + ", notes=" + notes + "]";
		}

	}
	public class opt implements Serializable{
		private String k;
		private String c;
		public String getK() {
			return k;
		}
		public void setK(String k) {
			this.k = k;
		}
		public String getC() {
			return c;
		}
		public void setC(String c) {
			this.c = c;
		}
		@Override
		public String toString() {
			return "opt [k=" + k + ", c=" + c + "]";
		}

	}
	public class analyze implements Serializable{
		private String content;
		private img img;
		private by by;
		public Create_Exercise_suit_2Bean.img getImg() {
			return img;
		}
		public void setImg(Create_Exercise_suit_2Bean.img img) {
			this.img = img;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public by getBy() {
			return by;
		}
		public void setBy(by by) {
			this.by = by;
		}
		@Override
		public String toString() {
			return "analyze [content=" + content + ", by=" + by + "]";
		}

	}
	public class by implements Serializable{
		private Integer id;
		private String icon;
		private String n;
		private String intro;
		private String url;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getIntro() {
			return intro;
		}
		public void setIntro(String intro) {
			this.intro = intro;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		@Override
		public String toString() {
			return "by [id=" + id + ", icon=" + icon + ", n=" + n + ", intro="
					+ intro + ", url=" + url + "]";
		}

	}
	public class points implements Serializable{
		private Integer id;
		private String n;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		@Override
		public String toString() {
			return "points [id=" + id + ", n=" + n + "]";
		}
	}

	public class notes implements Serializable{
		private note_bean[] me;
		private note_bean[] other;
		public note_bean[] getMe() {
			return me;
		}
		public void setMe(note_bean[] me) {
			this.me = me;
		}
		public note_bean[] getOther() {
			return other;
		}
		public void setOther(note_bean[] other) {
			this.other = other;
		}
		@Override
		public String toString() {
			return "notes [me=" + Arrays.toString(me) + ", other="
					+ Arrays.toString(other) + "]";
		}

	}

	public class note_bean implements Serializable{
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
			return "note_bean [id=" + id + ", author_name=" + author_name
					+ ", icon=" + icon + ", time_text=" + time_text
					+ ", content=" + content + "]";
		}

	}

}
