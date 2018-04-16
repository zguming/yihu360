package cn.net.dingwei.Bean;

import java.io.Serializable;

public class Points_jinduBean2 implements Serializable{
	private String id ;
	private String tot_r;
	private String exe_c;
	private String exe_s;
	private String exe_r;
	private String wro_c;
	private String wro_t;
	private String wro_r;
	private String wro_w;
	private String points;
	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTot_r() {
		return tot_r;
	}
	public void setTot_r(String tot_r) {
		this.tot_r = tot_r;
	}
	public String getExe_c() {
		return exe_c;
	}
	public void setExe_c(String exe_c) {
		this.exe_c = exe_c;
	}
	public String getExe_s() {
		return exe_s;
	}
	public void setExe_s(String exe_s) {
		this.exe_s = exe_s;
	}
	public String getExe_r() {
		return exe_r;
	}
	public void setExe_r(String exe_r) {
		this.exe_r = exe_r;
	}
	public String getWro_c() {
		return wro_c;
	}
	public void setWro_c(String wro_c) {
		this.wro_c = wro_c;
	}
	public String getWro_t() {
		return wro_t;
	}
	public void setWro_t(String wro_t) {
		this.wro_t = wro_t;
	}
	public String getWro_r() {
		return wro_r;
	}
	public void setWro_r(String wro_r) {
		this.wro_r = wro_r;
	}
	public String getWro_w() {
		return wro_w;
	}
	public void setWro_w(String wro_w) {
		this.wro_w = wro_w;
	}
	@Override
	public String toString() {
		return "Point_jinduBean [id=" + id + ", tot_r=" + tot_r + ", exe_c="
				+ exe_c + ", exe_s=" + exe_s + ", exe_r=" + exe_r + ", wro_c="
				+ wro_c + ", wro_t=" + wro_t + ", wro_r=" + wro_r + ", wro_w="
				+ wro_w + ", points=" + points + ", json=" + json + "]";
	}
	
	
}
