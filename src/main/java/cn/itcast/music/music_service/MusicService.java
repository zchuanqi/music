package cn.itcast.music.music_service;

import cn.itcast.music.music_domain.Music;
import cn.itcast.music.music_domain.PageBean;

public interface MusicService {
    void upload(Music music);

    Music findById(int mid);

    PageBean<Music> queryPage(int currentPage,int pageSize,String keyword);
}
