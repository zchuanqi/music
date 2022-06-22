package cn.itcast.music.music_domain;

import java.io.Serializable;

public class PlayList implements Serializable {
    private int pid;
    private String pname;
    private String introduction;
    private String img_url;
    private String share_user;

    public PlayList(){
    }

    public PlayList(int pid, String pname, String introduction, String img_url, String share_user) {
        this.pid = pid;
        this.pname = pname;
        this.introduction = introduction;
        this.img_url = img_url;
        this.share_user = share_user;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getShare_user() {
        return share_user;
    }

    public void setShare_user(String share_user) {
        this.share_user = share_user;
    }
}
