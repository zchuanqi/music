package cn.itcast.music.music_service;

public interface FavoriteService {
    void collect(String username, int mid);

    boolean isFavorite(String username, int mid);

    void removeCollect(String username, int mid);
}
