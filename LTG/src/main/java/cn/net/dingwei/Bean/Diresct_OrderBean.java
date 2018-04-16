package cn.net.dingwei.Bean;

/**
 * Created by Administrator on 2016/9/26.
 */
public class Diresct_OrderBean {

    /**
     * type : 1
     * text : 去支付
     * url : http://wv.liantigou.com/1.0/webview/funds/buys?os=Android&clientcode=860863035544766&is_live=1&orders={"orderid":"20160926111521_4","items":[{"type":"live","itemid":"1"}]}
     */

    private int type;
    private String text;
    private String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
