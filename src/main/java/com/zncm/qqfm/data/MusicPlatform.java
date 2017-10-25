package com.zncm.qqfm.data;


/**
 *2017年9月11日15:53:51
 * 平台，蜻蜓，考拉
 */
public class MusicPlatform extends Base {
    private String name;
    private String url;
    private int type;
    public MusicPlatform() {
    }

    public MusicPlatform(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public MusicPlatform(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
