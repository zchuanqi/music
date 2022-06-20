package cn.itcast.music.music_dao;

import cn.itcast.music.music_domain.Music;

import java.util.List;

public interface MusicDao {
    void upload(Music music);

    Music findById(int mid);

    List<Music> getMusicPage(int start,int pageSize,String keyword);
}
