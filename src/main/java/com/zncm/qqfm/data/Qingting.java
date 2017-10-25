package com.zncm.qqfm.data;

/**
 * Created by jiaomx on 2017/7/26.
 */

public class Qingting extends Base {

    private String id;
    private String title;
    private String url;
    private String mediainfo;

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

    public String getMediainfo() {
        return mediainfo;
    }

    public void setMediainfo(String mediainfo) {
        this.mediainfo = mediainfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
