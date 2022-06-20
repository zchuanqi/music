package cn.itcast.music.service.impl;

import cn.itcast.music.dao.FavoriteDao;
import cn.itcast.music.dao.impl.FavoriteDaoImpl;
import cn.itcast.music.domain.Favorite;
import cn.itcast.music.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }
}
