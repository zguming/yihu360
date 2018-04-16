package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 测验题目JSON Bean
 *
 * @author Administrator
 *
 */
public class Create_test_suitBean implements Serializable {
	private int id;
	private String test_type;
	private String test_type_name;
	private int test_time_limit;
	private int used_time;
	private String title;
	private String sub_title;
	private String conclusion;
	private groups[] groups;

	public class groups implements Serializable {
		private int no;
		private String title;
		private String desc;
		private questions[] questions;

		public int getNo() {
			return no;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public questions[] getQuestions() {
			return questions;
		}

		public void setQuestions(questions[] questions) {
			this.questions = questions;
		}

		@Override
		public String toString() {
			return "groups [no=" + no + ", title=" + title + ", desc=" + desc
					+ ", questions=" + Arrays.toString(questions) + "]";
		}

	}

	public class questions implements Serializable {
		private int no;
		private int type;
		private String content;
		private img img;
		private opt[] opt;
		private String[] correct;
		private String answer;
		private analyze analyze;
		private points[] points;
		private String answertime;
		//造点假数据  为了把Group的内容放到ViewPager上
		private String title;
		private String desc;
		private int lastAnswer;//上次选择的选项
		private String [] duoxuan_answer;
		private int user_choose_index; //用户选择的第几个选项 //单选题用

		public img getImg() {
			return img;
		}

		public void setImg(img img) {
			this.img = img;
		}

		public int getUser_choose_index() {
			return user_choose_index;
		}

		public void setUser_choose_index(int user_choose_index) {
			this.user_choose_index = user_choose_index;
		}

		public String[] getDuoxuan_answer() {
			return duoxuan_answer;
		}

		public void setDuoxuan_answer(String[] duoxuan_answer) {
			this.duoxuan_answer = duoxuan_answer;
		}

		public String getAnswertime() {
			return answertime;
		}

		public void setAnswertime(String answertime) {
			this.answertime = answertime;
		}

		public int getLastAnswer() {
			return lastAnswer;
		}

		public void setLastAnswer(int lastAnswer) {
			this.lastAnswer = lastAnswer;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
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
			return "questions [no=" + no + ", type=" + type + ", content="
					+ content + ", img=" + img + ", opt="
					+ Arrays.toString(opt) + ", correct="
					+ Arrays.toString(correct) + ", answer=" + answer
					+ ", analyze=" + analyze + ", points="
					+ Arrays.toString(points) + ", answertime=" + answertime
					+ ", title=" + title + ", desc=" + desc + ", lastAnswer="
					+ lastAnswer + ", duoxuan_answer="
					+ Arrays.toString(duoxuan_answer) + ", user_choose_index="
					+ user_choose_index + "]";
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
	public class opt implements Serializable {
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

	public class analyze implements Serializable {
		private by by;
		private String content;

		public by getBy() {
			return by;
		}

		public void setBy(by by) {
			this.by = by;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "analyze [by=" + by + ", content=" + content + "]";
		}

	}

	public class by implements Serializable {
		private int id;
		private String icon;
		private String n;
		private String intro;
		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
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

		@Override
		public String toString() {
			return "by [id=" + id + ", icon=" + icon + ", n=" + n + ", intro="
					+ intro + ", url=" + url + "]";
		}

	}

	public class points implements Serializable {
		private int id;
		private String n;

		public int getId() {
			return id;
		}

		public void setId(int id) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTest_type() {
		return test_type;
	}

	public void setTest_type(String test_type) {
		this.test_type = test_type;
	}

	public String getTest_type_name() {
		return test_type_name;
	}

	public void setTest_type_name(String test_type_name) {
		this.test_type_name = test_type_name;
	}

	public int getTest_time_limit() {
		return test_time_limit;
	}

	public void setTest_time_limit(int test_time_limit) {
		this.test_time_limit = test_time_limit;
	}

	public int getUsed_time() {
		return used_time;
	}

	public void setUsed_time(int used_time) {
		this.used_time = used_time;
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

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public groups[] getGroups() {
		return groups;
	}

	public void setGroups(groups[] groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "Create_test_suitBean [id=" + id + ", test_type=" + test_type
				+ ", test_type_name=" + test_type_name + ", test_time_limit="
				+ test_time_limit + ", used_time=" + used_time + ", title="
				+ title + ", sub_title=" + sub_title + ", conclusion="
				+ conclusion + ", groups=" + Arrays.toString(groups) + "]";
	}

}
