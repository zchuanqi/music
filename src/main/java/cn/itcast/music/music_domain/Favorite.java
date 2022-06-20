package cn.itcast.music.music_domain;

import java.io.Serializable;

public class Favorite implements Serializable {
    private String username;
    private String mid;

    public Favorite(){
    }

    public Favorite(String username, String music_id) {
        this.username = username;
        this.mid = music_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMusic_id() {
        return mid;
    }

    public void setMusic_id(String music_id) {
        this.mid = music_id;
    }
}
