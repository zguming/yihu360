package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2016/6/28.
 */
public class Products_listBean implements Serializable{
   private String status;
    private List<data> data;
    public class data implements Serializable{
        private String id;
        private String title;
        private String stitle;
        private String type;
        private String is_use;
        private String pay_type;
        private String expiry;
        private String list_num;
        private String qb_num;
        private String description;
        private String vip_text;
        private String img;
        private String pay_img;
        private String is_chick;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIs_chick() {
            return is_chick;
        }

        public void setIs_chick(String is_chick) {
            this.is_chick = is_chick;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPay_img() {
            return pay_img;
        }

        public void setPay_img(String pay_img) {
            this.pay_img = pay_img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_use() {
            return is_use;
        }

        public void setIs_use(String is_use) {
            this.is_use = is_use;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getList_num() {
            return list_num;
        }

        public void setList_num(String list_num) {
            this.list_num = list_num;
        }

        public String getQb_num() {
            return qb_num;
        }

        public void setQb_num(String qb_num) {
            this.qb_num = qb_num;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVip_text() {
            return vip_text;
        }

        public void setVip_text(String vip_text) {
            this.vip_text = vip_text;
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Products_listBean.data> getData() {
        return data;
    }

    public void setData(List<Products_listBean.data> data) {
        this.data = data;
    }
}
