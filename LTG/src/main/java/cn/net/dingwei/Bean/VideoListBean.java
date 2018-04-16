package cn.net.dingwei.Bean;


import java.util.List;

public class VideoListBean {


    /**
     * text :
     * vid :
     */

    private GoOnBean go_on;
    /**
     * id : 1
     * title : （5）证券投资基金基础知识（模考班）
     * times : 0 / 230
     * type : 1
     * videos : [{"id":"8","title":"模拟试题1","times":"0 / 23","type":1},{"id":"9","title":"模拟试题2","times":"0 / 27","type":1},{"id":"10","title":"模拟试题3","times":"0 / 26","type":1},{"id":"11","title":"模拟试题4","times":"0 / 26","type":1},{"id":"12","title":"模拟试题5","times":"0 / 27","type":1},{"id":"13","title":"模拟试题6","times":"0 / 18","type":1},{"id":"14","title":"模拟试题7","times":"0 / 20","type":1},{"id":"15","title":"模拟试题8","times":"0 / 19","type":1},{"id":"16","title":"模拟试题9","times":"0 / 19","type":1}]
     */

    private List<DataBean> data;

    public GoOnBean getGo_on() {
        return go_on;
    }

    public void setGo_on(GoOnBean go_on) {
        this.go_on = go_on;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class GoOnBean {
        private String text;
        private String vid;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }
    }

    public static class DataBean {
        private String id;
        private String title;
        private String times;
        private int type;
        private int look;
        private int is_group;
        private String url;

        public int getIs_group() {
            return is_group;
        }

        public void setIs_group(int is_group) {
            this.is_group = is_group;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
        }

        /**
         * id : 8
         * title : 模拟试题1
         * times : 0 / 23
         * type : 1
         */

        private List<VideosBean> videos;

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

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }
    }
    public  class VideosBean {
        private String id;
        private String title;
        private String times;
        private int look;
        private int type;
        private int is_group;
        private String url;

        public int getIs_group() {
            return is_group;
        }

        public void setIs_group(int is_group) {
            this.is_group = is_group;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
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

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
