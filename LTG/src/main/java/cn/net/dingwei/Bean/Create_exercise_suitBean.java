package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Create_exercise_suitBean implements Serializable{
	private Integer id;
	private String title;
	private String sub_title;
	private String conclusion;
	private Integer last_group;
	private Integer last_question;
	public Integer getLast_question() {
		return last_question;
	}
	public void setLast_question(Integer last_question) {
		this.last_question = last_question;
	}
	private groups[] groups;
	public Integer getLast_group() {
		return last_group;
	}
	public void setLast_group(Integer last_group) {
		this.last_group = last_group;
	}
	@Override
	public String toString() {
		return "Create_exercise_suitBean [id=" + id + ", title=" + title
				+ ", sub_title=" + sub_title + ", conclusion=" + conclusion
				+ ", last_group=" + last_group + ", last_question="
				+ last_question + ", groups=" + Arrays.toString(groups)
				+ "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public class groups implements Serializable{
		private Integer no;
		private questions[] questions;
		@Override
		public String toString() {
			return "groups [no=" + no + ", questions="
					+ Arrays.toString(questions) + "]";
		}
		public Integer getNo() {
			return no;
		}
		public void setNo(Integer no) {
			this.no = no;
		}
		public questions[] getQuestions() {
			return questions;
		}
		public void setQuestions(questions[] questions) {
			this.questions = questions;
		}

	}
	public class questions implements Serializable{
		private Integer no;
		private Integer type;
		private String content;
		private img img;
		private opt[] opt;
		private String[] correct;
		private String answer;
		private int user_choose_index; //用户选择的第几个选项 //单选题用
		private  String [] duoxuan_answer;
		private analyze analyze;
		private points[] points;

		public img getImg() {
			return img;
		}
		public void setImg(img img) {
			this.img = img;
		}
		/*public String[] getDuoxuan_answer() {
            return duoxuan_answer;
        }
        public void setDuoxuan_answer(String[] duoxuan_answer) {
            this.duoxuan_answer = duoxuan_answer;
        }*/
		public int getUser_choose_index() {
			return user_choose_index;
		}
		public void setUser_choose_index(int user_choose_index) {
			this.user_choose_index = user_choose_index;
		}
		public Integer getNo() {
			return no;
		}
		public void setNo(Integer no) {
			this.no = no;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
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
		public points[] getPoints() {
			return points;
		}
		public void setPoints(points[] points) {
			this.points = points;
		}
		public analyze getAnalyze() {
			return analyze;
		}
		public void setAnalyze(analyze analyze) {
			this.analyze = analyze;
		}
		@Override
		public String toString() {
			return "questions [no=" + no + ", type=" + type + ", content="
					+ content + ", img=" + img + ", opt="
					+ Arrays.toString(opt) + ", correct="
					+ Arrays.toString(correct) + ", answer=" + answer
					+ ", user_choose_index=" + user_choose_index
					+ ", analyze=" + analyze + ", points="
					+ Arrays.toString(points) + "]";
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
	public class analyze implements Serializable{
		private String content;
		private by by;
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
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
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
		@Override
		public String toString() {
			return "by [id=" + id + ", icon=" + icon + ", n=" + n
					+ ", intro=" + intro + ", url=" + url + "]";
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
}
