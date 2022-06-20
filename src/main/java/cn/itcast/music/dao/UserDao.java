package cn.itcast.music.dao;

import cn.itcast.music.domain.User;

public interface UserDao {
    User findByUsername(String username);

    void save(User user);

    User findByUsernameAndPassword(String username,String password);
}
