package cn.itcast.music.music_domain;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String img_url;

    public User() {
    }

    public User(String u, String p, String img) {
        username = u;
        password = p;
        img_url = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
