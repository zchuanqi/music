package cn.itcast.music.music_dao.impl;

import cn.itcast.music.music_dao.MusicDao;
import cn.itcast.music.music_domain.Music;
import cn.itcast.music.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.ArrayList;
import java.util.List;

public class MusicDaoImpl implements MusicDao {
    @Override
    public void upload(Music music) {
        String sql = "insert into music(mname,url,img_url,share_user) values(?,?,?,?)";
        JDBCUtils.getTemplate().update(sql,
                music.getMname(),
                music.getUrl(),
                music.getImg_url(),
                music.getShare_user());
    }

    @Override
    public Music findById(int mid) {
        String sql = "select * from music where mid=?";
        Music music = null;
        try{
            music = JDBCUtils.getTemplate().queryForObject(sql, new BeanPropertyRowMapper<Music>(Music.class), mid);
        }catch (Exception e){
        }
        return music;
    }

    @Override
    public int findTotal(String keyword) {
        String sql = "select count(*) from music where 1=1";
        if (keyword != null && keyword.length() > 0) {
            sql += " and mname like %" + keyword + "%";
        }
        return JDBCUtils.getTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public List<Music> getMusicPage(int start, int pageSize, String keyword) {
        String sql = "select * from music where 1=1";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();
        if (keyword != null && keyword.length() > 0) {
            sb.append(" and mname like ?");
            params.add("%" + keyword + "%");
        }
        sb.append(" limit ?,?");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        return JDBCUtils.getTemplate().query(sql, new BeanPropertyRowMapper<Music>(Music.class), params.toArray());
    }
}
