package com.zncm.qqfm.data;

/**
 * Created by jiaomx on 2017/7/20.
 */



/**
 * {
 * "id": "3574652",
 * "method": 4,
 * "downurl": "http://cdnbbbd.shoujiduoduo.com/bb/audio/a48/652/3574652.aac",
 * "filesize": 1092729,
 * "duration": 0,
 * "ismusic": 1,
 * "playcnt": 20933778,
 * "name": "爸爸去哪儿(《爸爸去哪儿》主题曲)",
 * "artist": "林志颖 kimi 张亮 张悦轩 郭涛 郭子睿 王岳伦 王诗龄",
 * "cateid": 15
 * }
 */
public class Song extends Base {
    private String id;
    private String method;
    private String downurl;
    private String filesize;
    private String ismusic;
    private String name;
    private String playcnt;//【1，已下载or听过】
    /**
     *是否在本地列表删除 2为删除
     */
    private String artist;
    /**
     *下载时间
     * 2017年8月4日
     */
    private String cateid;
    private String filePath;

    private Long time;






    /**
     *默认节目名称
     */
    private String ex1;
    private String ex2;
    private String ex3;
    private String ex4;
    private String ex5;

    private int exi1;
    private int exi2;
    private int exi3;
    private int exi4;
    private int exi5;



    public Song(String id, String method, String downurl, String filesize,
            String ismusic, String name, String playcnt, String artist,
            String cateid, String filePath, Long time, String ex1, String ex2,
            String ex3, String ex4, String ex5, int exi1, int exi2, int exi3,
            int exi4, int exi5) {
        this.id = id;
        this.method = method;
        this.downurl = downurl;
        this.filesize = filesize;
        this.ismusic = ismusic;
        this.name = name;
        this.playcnt = playcnt;
        this.artist = artist;
        this.cateid = cateid;
        this.filePath = filePath;
        this.time = time;
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.ex4 = ex4;
        this.ex5 = ex5;
        this.exi1 = exi1;
        this.exi2 = exi2;
        this.exi3 = exi3;
        this.exi4 = exi4;
        this.exi5 = exi5;
    }

    public Song() {
    }


    public Song(Srx srx) {
        this.id = "srx_" + srx.getUrl();
        this.method = "";
        this.downurl = srx.getUrl();
        this.filesize = "";
        this.ismusic = "";
        this.name = srx.getName();
        this.playcnt = "";
        this.artist = "";
        this.cateid = "";
        this.filePath = "";
    }
  
    public Song(Qingting qingting) {
        this.id = "qingting_" + qingting.getUrl();
        this.method = "";
        this.downurl = qingting.getUrl();
        this.filesize = "";
        this.ismusic = "";
        this.name = qingting.getTitle();
        this.playcnt = "";
        this.artist = "";
        this.cateid = "";
        this.filePath = "";
    }
    public Song(Ximalaya ximalaya) {
        this.id = "ximalaya_" + ximalaya.getPlayUrl64();
        this.method = "";
        this.downurl = ximalaya.getPlayUrl64();
        this.filesize = "";
        this.ismusic = "";
        this.name = ximalaya.getTitle();
        this.playcnt = "";
        this.artist = "";
        this.cateid = "";
        this.filePath = "";
    }


    public Song(Fmbaidu fmbaidu) {
        this.id = "fmbaidu_" + fmbaidu.getId();
        this.method = fmbaidu.getId();
        this.downurl = fmbaidu.getId();
        this.filesize = "";
        this.ismusic = "";
        this.name = fmbaidu.getId();
        this.playcnt = "";
        this.artist = "";
        this.cateid = "";
        this.filePath = "";
    }

    public Song(Kaola kaola) {
        this.id = "kaola_" + kaola.getMp3PlayUrl();
        this.method = "";
        this.downurl = kaola.getMp3PlayUrl();
        this.filesize = "";
        this.ismusic = "";
        this.name = kaola.getAudioName();
        this.playcnt = "";
        this.artist = "";
        this.cateid = "";
        this.filePath = "";
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getIsmusic() {
        return ismusic;
    }

    public void setIsmusic(String ismusic) {
        this.ismusic = ismusic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaycnt() {
        return playcnt;
    }

    public void setPlaycnt(String playcnt) {
        this.playcnt = playcnt;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }


    public String getEx1() {
        return ex1;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }

    public String getEx4() {
        return ex4;
    }

    public void setEx4(String ex4) {
        this.ex4 = ex4;
    }

    public String getEx5() {
        return ex5;
    }

    public void setEx5(String ex5) {
        this.ex5 = ex5;
    }

    public int getExi1() {
        return exi1;
    }

    public void setExi1(int exi1) {
        this.exi1 = exi1;
    }

    public int getExi2() {
        return exi2;
    }

    public void setExi2(int exi2) {
        this.exi2 = exi2;
    }

    public int getExi3() {
        return exi3;
    }

    public void setExi3(int exi3) {
        this.exi3 = exi3;
    }

    public int getExi4() {
        return exi4;
    }

    public void setExi4(int exi4) {
        this.exi4 = exi4;
    }

    public int getExi5() {
        return exi5;
    }

    public void setExi5(int exi5) {
        this.exi5 = exi5;
    }
}
