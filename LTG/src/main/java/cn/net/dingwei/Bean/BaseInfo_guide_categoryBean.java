package cn.net.dingwei.Bean;

import java.util.Arrays;

public class BaseInfo_guide_categoryBean {
	private guide_category[] guide_category;
	
	@Override
	public String toString() {
		return "BaseInfo_guide_categoryBean [guide_category="
				+ Arrays.toString(guide_category) + "]";
	}

	public guide_category[] getGuide_category() {
		return guide_category;
	}

	public void setGuide_category(guide_category[] guide_category) {
		this.guide_category = guide_category;
	}

	public class guide_category{
		private String key;
		private String n;
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
		@Override
		public String toString() {
			return "guide_category [key=" + key + ", n=" + n + "]";
		}
		
	}
}
