package cn.net.dingwei.Bean;

import java.io.Serializable;

public class WeiXinPayBean implements Serializable{
	private String order_id;
	private String price;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "WeiXinPayBean [order_id=" + order_id + ", price=" + price + "]";
	}
	
}
