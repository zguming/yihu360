package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 我的错题
 * @author Administrator
 *
 */
public class MyWrongsBean implements Serializable{
	private Boolean status;
	private data data;

	@Override
	public String toString() {
		return "MyWrongsBean [status=" + status + ", data=" + data + "]";
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public data getData() {
		return data;
	}
	public void setData(data data) {
		this.data = data;
	}
	public class data implements Serializable{
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
		private wrongs[] wrongs;
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		public wrongs[] getWrongs() {
			return wrongs;
		}
		public void setWrongs(wrongs[] wrongs) {
			this.wrongs = wrongs;
		}
		@Override
		public String toString() {
			return "data [total=" + total + ", wro_t=" + wro_t + ", wro_tr="
					+ wro_tr + ", wro_k=" + wro_k + ", wro_kr=" + wro_kr
					+ ", wro_c=" + wro_c + ", wro_cr=" + wro_cr + ", wrongs="
					+ Arrays.toString(wrongs) + "]";
		}

	}
	public class wrongs implements Serializable{ //1级
		private String id;
		private String name;
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
		private points2[] points;
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
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		public points2[] getPoints() {
			return points;
		}
		public void setPoints(points2[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "wrongs [id=" + id + ", name=" + name + ", total=" + total
					+ ", wro_t=" + wro_t + ", wro_tr=" + wro_tr + ", wro_k="
					+ wro_k + ", wro_kr=" + wro_kr + ", wro_c=" + wro_c
					+ ", wro_cr=" + wro_cr + ", points="
					+ Arrays.toString(points) + "]";
		}

	}
	public class points2 implements Serializable{//二级
		private String id;
		private String name;
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
		private points3[] points;
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
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		public points3[] getPoints() {
			return points;
		}
		public void setPoints(points3[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "points2 [id=" + id + ", name=" + name + ", total=" + total
					+ ", wro_t=" + wro_t + ", wro_tr=" + wro_tr + ", wro_k="
					+ wro_k + ", wro_kr=" + wro_kr + ", wro_c=" + wro_c
					+ ", wro_cr=" + wro_cr + ", points="
					+ Arrays.toString(points) + "]";
		}

	}
	public class points3 implements Serializable{//三级
		private String id;
		private String name;
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
		private points4[] points;
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
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		public points4[] getPoints() {
			return points;
		}
		public void setPoints(points4[] points) {
			this.points = points;
		}
		@Override
		public String toString() {
			return "points3 [id=" + id + ", name=" + name + ", total=" + total
					+ ", wro_t=" + wro_t + ", wro_tr=" + wro_tr + ", wro_k="
					+ wro_k + ", wro_kr=" + wro_kr + ", wro_c=" + wro_c
					+ ", wro_cr=" + wro_cr + ", points="
					+ Arrays.toString(points) + "]";
		}

	}
	public class points4 implements Serializable{//四级
		private String id;
		private String name;
		private int total;
		private int wro_t;
		private int wro_tr;
		private int wro_k;
		private int wro_kr;
		private int wro_c;
		private int wro_cr;
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
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getWro_t() {
			return wro_t;
		}
		public void setWro_t(int wro_t) {
			this.wro_t = wro_t;
		}
		public int getWro_tr() {
			return wro_tr;
		}
		public void setWro_tr(int wro_tr) {
			this.wro_tr = wro_tr;
		}
		public int getWro_k() {
			return wro_k;
		}
		public void setWro_k(int wro_k) {
			this.wro_k = wro_k;
		}
		public int getWro_kr() {
			return wro_kr;
		}
		public void setWro_kr(int wro_kr) {
			this.wro_kr = wro_kr;
		}
		public int getWro_c() {
			return wro_c;
		}
		public void setWro_c(int wro_c) {
			this.wro_c = wro_c;
		}
		public int getWro_cr() {
			return wro_cr;
		}
		public void setWro_cr(int wro_cr) {
			this.wro_cr = wro_cr;
		}
		@Override
		public String toString() {
			return "points4 [id=" + id + ", name=" + name + ", total=" + total
					+ ", wro_t=" + wro_t + ", wro_tr=" + wro_tr + ", wro_k="
					+ wro_k + ", wro_kr=" + wro_kr + ", wro_c=" + wro_c
					+ ", wro_cr=" + wro_cr + "]";
		}

	}
}
