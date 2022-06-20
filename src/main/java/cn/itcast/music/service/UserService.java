package cn.itcast.music.service;

import cn.itcast.music.domain.User;

public interface UserService {
    boolean regist(User user);

    User login(User user);
}
