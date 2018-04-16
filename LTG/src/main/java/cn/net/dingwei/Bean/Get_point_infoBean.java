package cn.net.dingwei.Bean;

import java.io.Serializable;

public class Get_point_infoBean implements Serializable{
	private String points;
	private Integer exe_c;
	private Integer exe_s;
	private Integer wro_d;
	private Integer kpl;
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public Integer getExe_c() {
		return exe_c;
	}
	public void setExe_c(Integer exe_c) {
		this.exe_c = exe_c;
	}
	public Integer getExe_s() {
		return exe_s;
	}
	public void setExe_s(Integer exe_s) {
		this.exe_s = exe_s;
	}
	public Integer getWro_d() {
		return wro_d;
	}
	public void setWro_d(Integer wro_d) {
		this.wro_d = wro_d;
	}
	public Integer getKpl() {
		return kpl;
	}
	public void setKpl(Integer kpl) {
		this.kpl = kpl;
	}
	@Override
	public String toString() {
		return "Get_point_infoBean [points=" + points + ", exe_c=" + exe_c
				+ ", exe_s=" + exe_s + ", wro_d=" + wro_d + ", kpl=" + kpl
				+ "]";
	}
	
	
}
