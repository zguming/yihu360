package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Get_subjects_progressBean implements Serializable{
	private String exam;
	public subjects[] subjects;
			
	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}

	public subjects[] getSubjects() {
		return subjects;
	}

	public void setSubjects(subjects[] subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "Get_subjects_progressBean [exam=" + exam + ", subjects="
				+ Arrays.toString(subjects) + "]";
	}

	public class subjects{
		private String k;
		private String progress;
		public String getK() {
			return k;
		}
		public void setK(String k) {
			this.k = k;
		}
		public String getProgress() {
			return progress;
		}
		public void setProgress(String progress) {
			this.progress = progress;
		}
		@Override
		public String toString() {
			return "subjects [k=" + k + ", progress=" + progress + "]";
		}
	}
}
