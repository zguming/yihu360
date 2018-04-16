package cn.net.dingwei.Bean;

import java.io.Serializable;

public class PointsBean implements Serializable{
	private String id;
	private String n;
	private String es;
	private String points;
	private String json;
	private String wro_r;
	private Point_jinduBean point_jinduBean;
	private Points_jinduBean2 points_jinduBean2;
	
	
	public String getWro_r() {
		return wro_r;
	}
	public void setWro_r(String wro_r) {
		this.wro_r = wro_r;
	}
	public Points_jinduBean2 getPoints_jinduBean2() {
		return points_jinduBean2;
	}
	public void setPoints_jinduBean2(Points_jinduBean2 points_jinduBean2) {
		this.points_jinduBean2 = points_jinduBean2;
	}
	public Point_jinduBean getPoint_jinduBean() {
		return point_jinduBean;
	}
	public void setPoint_jinduBean(Point_jinduBean point_jinduBean) {
		this.point_jinduBean = point_jinduBean;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
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
	public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "PointsBean [id=" + id + ", n=" + n + ", es=" + es + ", points="
				+ points + ", json=" + json + ", wro_r=" + wro_r
				+ ", point_jinduBean=" + point_jinduBean
				+ ", points_jinduBean2=" + points_jinduBean2 + "]";
	}
	
	
}
