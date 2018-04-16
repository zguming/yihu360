package cn.net.dingwei.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class UvVideoDetail {


    /**
     * group : 0
     * look_time : 0
     * next : 0
     * title : test
     * url : http://wv.liantigou.com/1.0/webview/construction/get_handouts?vid=1
     * vid : 1
     * video : {"basicInfo":{"classificationId":0,"classificationName":"其他","coverUrl":"http://1253531252.vod2.myqcloud
     * .com/e46f5c64vodtransgzp1253531252/e1c8006c9031868222903828565/shotup/f0.100_0.jpg","createTime":1491923269,"description":"",
     * "duration":20,"expireTime":0,"name":"Robotica_720","playerId":75257,"size":16235222,"sourceVideoUrl":"http://1253531252.vod2
     * .myqcloud.com/3047b9bcvodgzp1253531252/e1c8006c9031868222903828565/f0.wmv","status":"normal","type":"wmv",
     * "updateTime":1491923282},"code":0,"codeDesc":"Success","imageSpriteInfo":{},"message":"",
     * "transcodeInfo":{"transcodeList":[{"bitrate":408502,"definition":0,"height":360,"url":"http://1253531252.vod2.myqcloud
     * .com/3047b9bcvodgzp1253531252/e1c8006c9031868222903828565/f0.wmv","width":640},{"bitrate":408502,"definition":20,"height":360,
     * "url":"http://1253531252.vod2.myqcloud.com/e46f5c64vodtransgzp1253531252/e1c8006c9031868222903828565/f0.f20.mp4","width":640}]}}
     */

    public String group;
    public int look_time;
    public int next;
    public String title;
    public String url;
    public String vid;


    public VideoBean video;

    public static class VideoBean {


        public BasicInfoBean basicInfo;
        public int code;
        public String codeDesc;
        public ImageSpriteInfoBean imageSpriteInfo;
        public String message;
        public TranscodeInfoBean transcodeInfo;

        public static class BasicInfoBean {
            public int classificationId;
            public String classificationName;
            public String coverUrl;
            public int createTime;
            public String description;
            public int duration;
            public int expireTime;
            public String name;
            public int playerId;
            public int size;
            public String sourceVideoUrl;
            public String status;
            public String type;
            public int updateTime;
        }

        public static class ImageSpriteInfoBean {
        }

        public static class TranscodeInfoBean {


            public List<TranscodeListBean> transcodeList;

            public static class TranscodeListBean {
                public int bitrate;
                public int definition;
                public int height;
                public String url;
                public int width;
            }
        }
    }
}
