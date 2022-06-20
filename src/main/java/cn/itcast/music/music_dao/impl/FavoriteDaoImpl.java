package cn.itcast.music.music_dao.impl;

import cn.itcast.music.music_dao.FavoriteDao;
import cn.itcast.music.music_domain.Favorite;
import cn.itcast.music.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class FavoriteDaoImpl implements FavoriteDao {
    @Override
    public void collect(String username, int mid) {
        String sql = "insert into favorite(username,mid) values(?,?)";
        JDBCUtils.getTemplate().update(sql,
                username,
                mid);
    }

    @Override
    public Favorite findOne(String username, int mid) {
        String sql = "select * from favorite where username=? and mid=?";
        Favorite favorite = null;
        favorite = JDBCUtils.getTemplate().queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), username, mid);
        return favorite;
    }
}
