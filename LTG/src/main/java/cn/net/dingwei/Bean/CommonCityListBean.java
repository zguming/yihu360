package cn.net.dingwei.Bean;

import java.io.Serializable;


public class CommonCityListBean implements Serializable{
	private String k;
	private String n;
	private String ioskey;
	private String andkey;
	public String getK() {
		return k;
	}
	@Override
	public String toString() {
		return "CommonCityListBean [k=" + k + ", n=" + n + ", ioskey=" + ioskey
				+ ", andkey=" + andkey + "]";
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
	
}
