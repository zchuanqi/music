package cn.itcast.music.service;

public interface FavoriteService {
    boolean isFavorite(String rid,int uid);

    void add(String rid,int uid);
}
