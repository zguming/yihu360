package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;


public class Itropage_templatesBean implements Serializable{
	
	private String k;
	private int priority;
	private String from_date;
	private String to_date;
	private int max_times=-1;
	private int time_interval;
	public pages[] pages;
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public int getMax_times() {
		return max_times;
	}
	public void setMax_times(int max_times) {
		this.max_times = max_times;
	}
	public int getTime_interval() {
		return time_interval;
	}
	public void setTime_interval(int time_interval) {
		this.time_interval = time_interval;
	}
	public pages[] getPages() {
		return pages;
	}
	public void setPages(pages[] pages) {
		this.pages = pages;
	}
	@Override
	public String toString() {
		return "Itropage_templatesBean [k=" + k + ", priority=" + priority
				+ ", from_date=" + from_date + ", to_date=" + to_date
				+ ", max_times=" + max_times + ", time_interval="
				+ time_interval + ", pages=" + Arrays.toString(pages) + "]";
	}
	
		public class pages{
			private String img;
			private String bg_color;
			private btn[] btn;
			public String getImg() {
				return img;
			}
			public void setImg(String img) {
				this.img = img;
			}
			public String getBg_color() {
				return bg_color;
			}
			public void setBg_color(String bg_color) {
				this.bg_color = bg_color;
			}
			public btn[] getBtn() {
				return btn;
			}
			public void setBtn(btn[] btn) {
				this.btn = btn;
			}
			@Override
			public String toString() {
				return "pages [img=" + img + ", bg_color=" + bg_color
						+ ", btn=" + Arrays.toString(btn) + "]";
			}
			
		}
		public class btn{
			private String aciton;
			private String option;
			private String btn_img;
			private String position;
			private Integer x;
			private Integer y;
			private Integer width;
			private Integer height;
			public Integer getWidth() {
				return width;
			}
			public void setWidth(Integer width) {
				this.width = width;
			}
			public Integer getHeight() {
				return height;
			}
			public void setHeight(Integer height) {
				this.height = height;
			}
			public String getAciton() {
				return aciton;
			}
			public void setAciton(String aciton) {
				this.aciton = aciton;
			}
			public String getOption() {
				return option;
			}
			public void setOption(String option) {
				this.option = option;
			}
			public String getBtn_img() {
				return btn_img;
			}
			public void setBtn_img(String btn_img) {
				this.btn_img = btn_img;
			}
			public String getPosition() {
				return position;
			}
			public void setPosition(String position) {
				this.position = position;
			}
			public Integer getX() {
				return x;
			}
			public void setX(Integer x) {
				this.x = x;
			}
			public Integer getY() {
				return y;
			}
			public void setY(Integer y) {
				this.y = y;
			}
			@Override
			public String toString() {
				return "btn [aciton=" + aciton + ", option=" + option
						+ ", btn_img=" + btn_img + ", position=" + position
						+ ", x=" + x + ", y=" + y + ", width=" + width
						+ ", height=" + height + "]";
			}
			
		}
}
