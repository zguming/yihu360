package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

public class CitysBean implements Serializable{
	private city[] hotcity;
	private city[] allcity;
	
	public class city implements Serializable{
		private String k;
		private String n;
		private String py;
		private String ioskey;
		private String andkey;
		private String sz;
		
		public String getSz() {
			return sz;
		}
		public void setSz(String sz) {
			this.sz = sz;
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
		public String getPy() {
			return py;
		}
		public void setPy(String py) {
			this.py = py;
		}
		public String getIoskey() {
			return ioskey;
		}
		public void setIoskey(String ioskey) {
			this.ioskey = ioskey;
		}
		public String getAndkey() {
			return andkey;
		}
		public void setAndkey(String andkey) {
			this.andkey = andkey;
		}
		@Override
		public String toString() {
			return "city [k=" + k + ", n=" + n + ", py=" + py + ", ioskey="
					+ ioskey + ", andkey=" + andkey + ", sz=" + sz + "]";
		}
		
	}

	public city[] getHotcity() {
		return hotcity;
	}

	public void setHotcity(city[] hotcity) {
		this.hotcity = hotcity;
	}

	public city[] getAllcity() {
		return allcity;
	}

	public void setAllcity(city[] allcity) {
		this.allcity = allcity;
	}

	@Override
	public String toString() {
		return "CitysBean [hotcity=" + Arrays.toString(hotcity) + ", allcity="
				+ Arrays.toString(allcity) + "]";
	}
	
}
