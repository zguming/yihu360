package cn.net.dingwei.Bean;

import java.io.Serializable;

public class Edit_user_collectBean implements Serializable{
	private Boolean status; 
	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Edit_user_collectBean [status=" + status + "]";
	}

	


}
