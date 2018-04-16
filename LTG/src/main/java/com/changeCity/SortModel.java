package com.changeCity;

import java.io.Serializable;
import java.util.Arrays;

public class SortModel implements Serializable{

	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	private String key;  //Key值
	private String sz;  //简称  例如重庆  cq
	private hot_city[] hot_city;
	public class hot_city implements Serializable{
		private String name;   //显示的数据
		private String key;  //简称  例如重庆  cq
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		@Override
		public String toString() {
			return "hot_city [name=" + name + ", key=" + key + "]";
		}

	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public hot_city[] getHot_city() {
		return hot_city;
	}
	public void setHot_city(hot_city[] hot_city) {
		this.hot_city = hot_city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getSz() {
		return sz;
	}
	public void setSz(String sz) {
		this.sz = sz;
	}
	@Override
	public String toString() {
		return "SortModel [name=" + name + ", sortLetters=" + sortLetters
				+ ", key=" + key + ", sz=" + sz + ", hot_city="
				+ Arrays.toString(hot_city) + "]";
	}

}
