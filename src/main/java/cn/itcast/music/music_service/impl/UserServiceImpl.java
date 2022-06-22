package cn.itcast.music.music_service.impl;

import cn.itcast.music.music_dao.UserDao;
import cn.itcast.music.music_dao.impl.UserDaoImpl;
import cn.itcast.music.music_domain.User;
import cn.itcast.music.music_service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        User registUser = userDao.findByName(user.getUsername());
        if (registUser != null) {
            //用户名已存在
            return false;
        } else {
            userDao.regist(user);
            return true;
        }
    }

    @Override
    public User login(User user) {
        return userDao.login(user.getUsername(), user.getPassword());
    }

    @Override
    public String getImageByName(String username) {
        User user = userDao.findByName(username);
        return user.getImg_url();
    }
}
