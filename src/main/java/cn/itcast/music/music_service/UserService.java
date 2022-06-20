package cn.itcast.music.music_service;

import cn.itcast.music.music_domain.User;

public interface UserService {
    boolean regist(User user);

    User login(User user);
}
