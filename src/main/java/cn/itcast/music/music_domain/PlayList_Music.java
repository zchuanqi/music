package cn.itcast.music.music_domain;

import java.io.Serializable;

public class PlayList_Music implements Serializable {
    private int pid;
    private int mid;

    public PlayList_Music(){
    }

    public PlayList_Music(int pid, int mid) {
        this.pid = pid;
        this.mid = mid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}
