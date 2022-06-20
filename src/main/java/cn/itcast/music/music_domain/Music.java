package cn.itcast.music.music_domain;

import java.io.Serializable;

public class Music implements Serializable {
    private int mid;
    private String mname;
    private String url;
    private String img_url;
    private String share_user;

    public Music() {
    }

    public Music(int id, String mname, String url, String img_url, String share_user) {
        this.mid = id;
        this.mname = mname;
        this.url = url;
        this.img_url = img_url;
        this.share_user = share_user;
    }

    public int getId() {
        return mid;
    }

    public void setId(int id) {
        this.mid = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShare_user() {
        return share_user;
    }

    public void setShare_user(String share_user) {
        this.share_user = share_user;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}
