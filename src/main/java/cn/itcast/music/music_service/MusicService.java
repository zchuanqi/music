package cn.itcast.music.music_service;

import cn.itcast.music.music_domain.Music;

public interface MusicService {
    void upload(Music music);

    Music findById(int mid);
}
