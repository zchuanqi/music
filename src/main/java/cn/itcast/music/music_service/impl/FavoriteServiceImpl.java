package cn.itcast.music.music_service.impl;

import cn.itcast.music.music_dao.FavoriteDao;
import cn.itcast.music.music_dao.impl.FavoriteDaoImpl;
import cn.itcast.music.music_domain.Favorite;
import cn.itcast.music.music_service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public void collect(String username, int mid) {
        favoriteDao.collect(username, mid);
    }

    @Override
    public boolean isFavorite(String username, int mid) {
        Favorite favorite = favoriteDao.findOne(username, mid);
        return favorite != null;
    }

    @Override
    public void removeCollect(String username, int mid) {
        favoriteDao.removeCollect(username, mid);
    }
}
