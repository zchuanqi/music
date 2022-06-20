package cn.itcast.music.music_dao.impl;

import cn.itcast.music.music_dao.UserDao;
import cn.itcast.music.music_domain.User;
import cn.itcast.music.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    @Override
    public void regist(User user) {
        String sql = "insert into user(username,password,img_url) values(?,?,?)";
        JDBCUtils.getTemplate().update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getImg_url());
    }

    @Override
    public User findByName(String username) {
        String sql = "select * from user where username=?";
        User user = null;
        user = JDBCUtils.getTemplate().queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        return user;
    }

    @Override
    public User login(String username, String password) {
        String sql = "select * from user where username=? and password=?";
        User user = null;
        user = JDBCUtils.getTemplate().queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        return user;
    }
}
