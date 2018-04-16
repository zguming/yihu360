package cn.net.dingwei.Bean;

import java.io.Serializable;

public class Lianxi_ctggBean implements Serializable{
		private String id;
		private String name;
		private String wro_r;
		private String points;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWro_r() {
			return wro_r;
		}
		public void setWro_r(String wro_r) {
			this.wro_r = wro_r;
		}
		public String getPoints() {
			return points;
		}
		public void setPoints(String points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "Lianxi_ctggBean [id=" + id + ", name=" + name + ", wro_r="
					+ wro_r + ", points=" + points + "]";
		}
}
