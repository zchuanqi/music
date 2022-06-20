package cn.itcast.music.dao;

import cn.itcast.music.domain.Favorite;

public interface FavoriteDao {
    Favorite findByRidAndUid(int rid, int uid);

    int findCountByRid(int rid);

    void add(int rid, int uid);
}
