package cn.net.dingwei.Bean;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Share_InfoBean {

    /**
     * share_money : 10.00
     * number : 0
     * money : 0
     */

    private String share_money;
    private String number;
    private int money;
    private String share_img;

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getShare_money() {
        return share_money;
    }

    public void setShare_money(String share_money) {
        this.share_money = share_money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
