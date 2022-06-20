package cn.itcast.music.music_dao;

import cn.itcast.music.music_domain.User;

public interface UserDao {
    void regist(User user);

    User findByName(String username);

    User login(String username, String password);
}
