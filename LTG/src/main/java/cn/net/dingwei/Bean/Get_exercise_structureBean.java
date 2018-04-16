package cn.net.dingwei.Bean;

import java.io.Serializable;

public class Get_exercise_structureBean implements Serializable{
		private zxlx zxlx;
		private ctgg ctgg;
		private pglx pglx;
		
	@Override
		public String toString() {
			return "Get_exercise_structureBean [zxlx=" + zxlx + ", ctgg="
					+ ctgg + ", pglx=" + pglx + "]";
		}
	public zxlx getZxlx() {
			return zxlx;
		}
		public void setZxlx(zxlx zxlx) {
			this.zxlx = zxlx;
		}
		public ctgg getCtgg() {
			return ctgg;
		}
		public void setCtgg(ctgg ctgg) {
			this.ctgg = ctgg;
		}
		public pglx getPglx() {
			return pglx;
		}
		public void setPglx(pglx pglx) {
			this.pglx = pglx;
		}
	public class pglx implements Serializable{
		private String  n;
		private String  desc;
		private String  btn_text_enable;
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getBtn_text_enable() {
			return btn_text_enable;
		}
		public void setBtn_text_enable(String btn_text_enable) {
			this.btn_text_enable = btn_text_enable;
		}
		@Override
		public String toString() {
			return "pglx [n=" + n + ", desc=" + desc + ", btn_text_enable="
					+ btn_text_enable + "]";
		}
		
	}
	public class ctgg implements Serializable{
		private String  n;
		private String btn_text_enable;
		private String btn_text_disable;
		private Boolean has_ct;
		private String no_ct_desc;
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getBtn_text_enable() {
			return btn_text_enable;
		}
		public void setBtn_text_enable(String btn_text_enable) {
			this.btn_text_enable = btn_text_enable;
		}
		public String getBtn_text_disable() {
			return btn_text_disable;
		}
		public void setBtn_text_disable(String btn_text_disable) {
			this.btn_text_disable = btn_text_disable;
		}
		public Boolean getHas_ct() {
			return has_ct;
		}
		public void setHas_ct(Boolean has_ct) {
			this.has_ct = has_ct;
		}
		public String getNo_ct_desc() {
			return no_ct_desc;
		}
		public void setNo_ct_desc(String no_ct_desc) {
			this.no_ct_desc = no_ct_desc;
		}
		@Override
		public String toString() {
			return "ctgg [n=" + n + ", btn_text_enable=" + btn_text_enable
					+ ", btn_text_disable=" + btn_text_disable + ", has_ct="
					+ has_ct + ", no_ct_desc=" + no_ct_desc + "]";
		}
		
		}
	public class zxlx implements Serializable{
		private String  n;
		private String btn_text_enable;
		private String btn_text_disable;
		@Override
		public String toString() {
			return "zxlx [n=" + n + ", btn_text_enable=" + btn_text_enable
					+ ", btn_text_disable=" + btn_text_disable + "]";
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public String getBtn_text_enable() {
			return btn_text_enable;
		}
		public void setBtn_text_enable(String btn_text_enable) {
			this.btn_text_enable = btn_text_enable;
		}
		public String getBtn_text_disable() {
			return btn_text_disable;
		}
		public void setBtn_text_disable(String btn_text_disable) {
			this.btn_text_disable = btn_text_disable;
		}
	}
}
