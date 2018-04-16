package cn.net.dingwei.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class Video_LiveBean {
    /**
     * status : true
     * error : null
     * data : [{"id":"1","title":"在线刷题","name":"测试教师","intro":"","big_img":"http://liantigou.oss-cn-hangzhou.aliyuncs
     * .com/app/funds/1474271862/fb8253edd5508fef918a9096ef0d4ee7.png","img":"http://liantigou.oss-cn-hangzhou.aliyuncs
     * .com/app/funds/1474271862/fb8253edd5508fef918a9096ef0d4ee7.png","pay_type":"1","price":"0.01","numbers":"2","time":"今天
     * 8:00-21:00","code":"http://8699.liveplay.myqcloud.com/live/8699_f1f4120bef.m3u8","ltid":"@TGS#aKZYTJYEM",
     * "identifier":"13594622565","sig":"eJxljs1Og0AYRfc8BWFtZH6YAiZdjGJamxpKMAXcEGA*cNIKExhrTeO7q7RGEu
     * -2nNx7T4ZpmtbTOr4uqqp7a3WuPxRY5o1pIevqDyolRV7onPbiH4Sjkj3kRa2hHyFmjBGEpo4U0GpZy1*DMt*ZEcJmbCINYpePS2fH*a4gPqF4qshmhI
     * -30d1DsCztKD7w1IXNO96F3mJl28sjtLjYZkHIqWoSUIu9yzGXvNuI4Bb2KK09bzWEQrDn0i1pWpVrbjvRyzaLIcnsZkj4fD6Z1PIVLod84lIPOdNDB
     * *gH2bWjQBBmmFD0E8v4NL4AZOFb4A__","status":true,"status_text":"直播中","start_times":0,"button":{"btn_text":"进入","look":1,"order":0,
     * "url":""},"handouts":"http://wv.shikaotong.com/1.0/webview/teacher/get_live_handouts?id=1","refresh_time":10}]
     */

    public boolean status;
    public String error;
    /**
     * id : 1
     * title : 在线刷题
     * name : 测试教师
     * intro :
     * big_img : http://liantigou.oss-cn-hangzhou.aliyuncs.com/app/funds/1474271862/fb8253edd5508fef918a9096ef0d4ee7.png
     * img : http://liantigou.oss-cn-hangzhou.aliyuncs.com/app/funds/1474271862/fb8253edd5508fef918a9096ef0d4ee7.png
     * pay_type : 1
     * price : 0.01
     * numbers : 2
     * time : 今天 8:00-21:00
     * code : http://8699.liveplay.myqcloud.com/live/8699_f1f4120bef.m3u8
     * ltid : @TGS#aKZYTJYEM
     * identifier : 13594622565
     * sig : eJxljs1Og0AYRfc8BWFtZH6YAiZdjGJamxpKMAXcEGA*cNIKExhrTeO7q7RGEu
     * -2nNx7T4ZpmtbTOr4uqqp7a3WuPxRY5o1pIevqDyolRV7onPbiH4Sjkj3kRa2hHyFmjBGEpo4U0GpZy1*DMt*ZEcJmbCINYpePS2fH*a4gPqF4qshmhI
     * -30d1DsCztKD7w1IXNO96F3mJl28sjtLjYZkHIqWoSUIu9yzGXvNuI4Bb2KK09bzWEQrDn0i1pWpVrbjvRyzaLIcnsZkj4fD6Z1PIVLod84lIPOdNDB
     * *gH2bWjQBBmmFD0E8v4NL4AZOFb4A__
     * status : true
     * status_text : 直播中
     * start_times : 0
     * button : {"btn_text":"进入","look":1,"order":0,"url":""}
     * handouts : http://wv.shikaotong.com/1.0/webview/teacher/get_live_handouts?id=1
     * refresh_time : 10
     */

    public List<DataBean> data;

    public static class DataBean {
        public String id;
        public String title;
        public String name;
        public String intro;
        public String big_img;
        public String img;
        public String pay_type;
        public String price;
        public String numbers;
        public String time;
        public String code;
        public String ltid;
        public String identifier;
        public String sig;
        public boolean status;
        public String status_text;
        public int start_times;
        /**
         * btn_text : 进入
         * look : 1
         * order : 0
         * url :
         */

        public ButtonBean button;
        public String handouts;
        public int refresh_time;

        public static class ButtonBean {
            public String btn_text;
            public int look;
            public int order;
            public String url;

            public String getBtn_text() {
                return btn_text;
            }

            public void setBtn_text(String btn_text) {
                this.btn_text = btn_text;
            }

            public int getLook() {
                return look;
            }

            public void setLook(int look) {
                this.look = look;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getBig_img() {
            return big_img;
        }

        public void setBig_img(String big_img) {
            this.big_img = big_img;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLtid() {
            return ltid;
        }

        public void setLtid(String ltid) {
            this.ltid = ltid;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public int getStart_times() {
            return start_times;
        }

        public void setStart_times(int start_times) {
            this.start_times = start_times;
        }

        public ButtonBean getButton() {
            return button;
        }

        public void setButton(ButtonBean button) {
            this.button = button;
        }

        public String getHandouts() {
            return handouts;
        }

        public void setHandouts(String handouts) {
            this.handouts = handouts;
        }

        public int getRefresh_time() {
            return refresh_time;
        }

        public void setRefresh_time(int refresh_time) {
            this.refresh_time = refresh_time;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }



}
