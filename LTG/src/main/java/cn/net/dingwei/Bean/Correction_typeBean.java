package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class Correction_typeBean implements Serializable {
	private bean[] correction_type;

	public bean[] getCorrection_typeBean() {
		return correction_type;
	}

	public void setCorrection_typeBean(bean[] correction_typeBean) {
		correction_type = correction_typeBean;
	}

	@Override
	public String toString() {
		return "Correction_typeBean [Correction_typeBean="
				+ Arrays.toString(correction_type) + "]";
	}

	public class bean implements Serializable {
		private String k;
		private String n;

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

		@Override
		public String toString() {
			return "bean [k=" + k + ", n=" + n + "]";
		}

	}
}
