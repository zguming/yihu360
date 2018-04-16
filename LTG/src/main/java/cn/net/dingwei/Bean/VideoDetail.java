package cn.net.dingwei.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class VideoDetail {
    /**
     * title : 模拟试题1
     * video : {"vid":"3dd8d256e67c29d7c5f33e0f11f882eb_3","ptime":"2016-03-23 19:00:59","df":3,"first_image":"http://img.videocc.net/uimage/3/3dd8d256e6/b/3dd8d256e67c29d7c5f33e0f11f882eb_0.jpg","title":"课时1模拟试题1_1","context":"","duration":"00:23:23","filesize":[],"times":"242","cataid":"1458729188890","original_definition":"1920x1080","seed":"1"}
     * url : http://wv.liantigou.com/1.0/webview/cpa/get_handouts?vid=0
     * next : 9
     * group : 1
     * look_time : 300
     */

    private String title;
    /**
     * vid : 3dd8d256e67c29d7c5f33e0f11f882eb_3
     * ptime : 2016-03-23 19:00:59
     * df : 3
     * first_image : http://img.videocc.net/uimage/3/3dd8d256e6/b/3dd8d256e67c29d7c5f33e0f11f882eb_0.jpg
     * title : 课时1模拟试题1_1
     * context :
     * duration : 00:23:23
     * filesize : []
     * times : 242
     * cataid : 1458729188890
     * original_definition : 1920x1080
     * seed : 1
     */

    private VideoBean video;
    private String url;
    private String next;
    private String group;
    private String vid;
    private int look_time;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getLook_time() {
        return look_time;
    }

    public void setLook_time(int look_time) {
        this.look_time = look_time;
    }

    public static class VideoBean {
        private String vid;
        private String ptime;
        private int df;
        private String first_image;
        private String title;
        private String context;
        private String duration;
        private String times;
        private String cataid;
        private String original_definition;
        private String seed;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public int getDf() {
            return df;
        }

        public void setDf(int df) {
            this.df = df;
        }

        public String getFirst_image() {
            return first_image;
        }

        public void setFirst_image(String first_image) {
            this.first_image = first_image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getCataid() {
            return cataid;
        }

        public void setCataid(String cataid) {
            this.cataid = cataid;
        }

        public String getOriginal_definition() {
            return original_definition;
        }

        public void setOriginal_definition(String original_definition) {
            this.original_definition = original_definition;
        }

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }
    }


}
