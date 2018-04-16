package cn.net.dingwei.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MyMoneyBean implements Serializable{
    private String last_id;
    private int next;
    private String balance;
    private List<lists> lists;
    public class lists implements Serializable{
        private String id;
        private String price;
        private String code;
        private String time;
        private String content;
        private String type;
        private String color;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public List<MyMoneyBean.lists> getLists() {
        return lists;
    }

    public void setLists(List<MyMoneyBean.lists> lists) {
        this.lists = lists;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public int getNext() {
        return next;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setNext(int next) {
        this.next = next;
    }
}
