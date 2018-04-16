package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_baseinfoBean implements Serializable{
	private String k;
	private String n;
	private String sn;
	private String desc;
	private String status;
	private String[] schedules;
	public subjects[] subjects;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Get_baseinfoBean [k=" + k + ", n=" + n + ", sn=" + sn
				+ ", desc=" + desc + ", status=" + status + ", schedules="
				+ Arrays.toString(schedules) + ", subjects="
				+ Arrays.toString(subjects) + "]";
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String[] getSchedules() {
		return schedules;
	}

	public void setSchedules(String[] schedules) {
		this.schedules = schedules;
	}

	public subjects[] getSubjects() {
		return subjects;
	}

	public void setSubjects(subjects[] subjects) {
		this.subjects = subjects;
	}

	public class subjects{
		private String k;
		private String n;
		private String sn;
		private String desc;
		private Get_situationBean get_situationBean;
		public points[] points;
		private String progress;

		public String getProgress() {
			return progress;
		}

		public void setProgress(String progress) {
			this.progress = progress;
		}

		public points[] getPoints() {
			return points;
		}

		public void setPoints(points[] points) {
			this.points = points;
		}

		public Get_situationBean getGet_situationBean() {
			return get_situationBean;
		}

		public void setGet_situationBean(Get_situationBean get_situationBean) {
			this.get_situationBean = get_situationBean;
		}
		public String getK() {
			return k;
		}
		public void setK(String k) {
			this.k = k;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getSn() {
			return sn;
		}
		public void setSn(String sn) {
			this.sn = sn;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		@Override
		public String toString() {
			return
					" points=" + Arrays.toString(points) + ", progress="
							+ progress + "]";
		}

	}

	public class points implements Serializable{ //总论
		private int id;
		private String n;
		private int es;
		private cn.net.dingwei.Bean.Get_situationBean.points[] points1;
		/*private points2[] points;
		
		public points2[] getPoints() {
			return points;
		}
		public void setPoints(points2[] points) {
			this.points = points;
		}*/
		public cn.net.dingwei.Bean.Get_situationBean.points[] getPoints1() {
			return points1;
		}
		public void setPoints1(cn.net.dingwei.Bean.Get_situationBean.points[] points) {
			this.points1 = points;
		}
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
		public int getEs() {
			return es;
		}
		public void setEs(int es) {
			this.es = es;
		}
		@Override
		public String toString() {
			return "points [id=" + id + ", n=" + n + ", es=" + es
					+ ", points1=" + Arrays.toString(points1) + "]";
		}

	}
	public class points2 implements Serializable{ //总论-1   会计的职能与方法
		private String id;
		private String n;
		private int es;
		private cn.net.dingwei.Bean.Get_situationBean.points[] points1;
		public cn.net.dingwei.Bean.Get_situationBean.points[] getPoints1() {
			return points1;
		}
		public void setPoints1(cn.net.dingwei.Bean.Get_situationBean.points[] points) {
			this.points1 = points;
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public int getEs() {
			return es;
		}
		public void setEs(int es) {
			this.es = es;
		}
		@Override
		public String toString() {
			return "points2 [id=" + id + ", n=" + n + ", es=" + es
					+ ", points1=" + Arrays.toString(points1) + "]";
		}

	}
}
