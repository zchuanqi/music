package cn.itcast.music.music_dao;

import cn.itcast.music.music_domain.Favorite;

public interface FavoriteDao {
    void collect(String username, int mid);

    Favorite findOne(String username, int mid);
}
