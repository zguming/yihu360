package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;


public class MyCollectBean implements Serializable{
	private Boolean status;
	private data[] data; 
	
	@Override
	public String toString() {
		return "MyCollectBean [status=" + status + ", data="
				+ Arrays.toString(data) + "]";
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public data[] getData() {
		return data;
	}
	public void setData(data[] data) {
		this.data = data;
	}
	public class data implements Serializable{
		private int id;
		private String name;
		private int c_t;
		private points2[] points;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getC_t() {
			return c_t;
		}
		public void setC_t(int c_t) {
			this.c_t = c_t;
		}
		public points2[] getPoints() {
			return points;
		}
		public void setPoints(points2[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "data [id=" + id + ", name=" + name + ", c_t=" + c_t
					+ ", points=" + Arrays.toString(points) + "]";
		}
		
	}
	public class points2 implements Serializable{
		private int id;
		private String name;
		private int c_t;
		private points3[] points;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getC_t() {
			return c_t;
		}
		public void setC_t(int c_t) {
			this.c_t = c_t;
		}
		public points3[] getPoints() {
			return points;
		}
		public void setPoints(points3[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "points2 [id=" + id + ", name=" + name + ", c_t=" + c_t
					+ ", points=" + Arrays.toString(points) + "]";
		}
		
	}
	public class points3 implements Serializable{
		private int id;
		private String name;
		private int c_t;
		private points4[] points;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getC_t() {
			return c_t;
		}
		public void setC_t(int c_t) {
			this.c_t = c_t;
		}
		public points4[] getPoints() {
			return points;
		}
		public void setPoints(points4[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "points3 [id=" + id + ", name=" + name + ", c_t=" + c_t
					+ ", points=" + Arrays.toString(points) + "]";
		}
		
	}
	public class points4 implements Serializable{
		private int id;
		private String name;
		private int c_t;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getC_t() {
			return c_t;
		}
		public void setC_t(int c_t) {
			this.c_t = c_t;
		}
		@Override
		public String toString() {
			return "points4 [id=" + id + ", name=" + name + ", c_t=" + c_t
					+ "]";
		}
		
	}
}
