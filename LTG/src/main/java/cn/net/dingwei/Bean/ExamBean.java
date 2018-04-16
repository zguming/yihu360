package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ExamBean implements Serializable{
	private String k;
	private String n;
	private String sn;
	private String desc;
	private String status;
	private String[] schedules;
	private String group;

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	@Override
	public String toString() {
		return "ExamBean [k=" + k + ", n=" + n + ", sn=" + sn + ", desc="
				+ desc + ", status=" + status + ", schedules="
				+ Arrays.toString(schedules) + "]";
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String[] getSchedules() {
		return schedules;
	}
	public void setSchedules(String[] schedules) {
		this.schedules = schedules;
	}
	
	
}
